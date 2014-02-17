/*
 * linux/kernel/message_queue.c
 *
 * Authors:     Andriy Rudyk
 *              Chris Ward
 *              Tim Sizemore
 *
 * Date:        29/03/2012
 *
 * Description: Message queue system calls.
 */
#include <asm/uaccess.h>
#include <linux/syscalls.h>
#include <linux/sched.h>
#include <linux/slab.h>
#include <linux/wait.h>

#define PG_SIZE 4096

/** 
 * Enum holding the flags for processes.
 * 
 * ROOT_PROC   - Only processes owned by the root.
 * LOCAL_PROC  - Only processes owned by the same user | root can write to queue.
 * PUBLIC_PROC - Any process can send messages to the message queue.
 */
enum {
    ROOT_PROC   = 0,
    LOCAL_PROC  = 1,
    PUBLIC_PROC = 2,
};

/*
 * Initializes the message queue.
 *
 * Params: struct message_queue_t* mq - The pointer to the given message_queue
 */
void init_message_queue(struct message_queue_t* mq) {
    mq->buffer   = NULL;
    mq->head     = 0;
    mq->tail     = 0;
    mq->size     = 0;
    mq->capacity = 0;
    mq->flags    = 0;
    
    mutex_init(&mq->mutexStruct);
    init_waitqueue_head(&mq->readQueue);
    init_waitqueue_head(&mq->writeQueue);
}

/*
 * System call responsible for allocating the message queue for the calling
 * process.
 *
 * Params:  int capacity - The integer capacity of the message queue.
 *          int flags    - An integer that will contain flags that control the
 *                         behavior of the message queue.
 *
 * Returns: The success status.
 */
SYSCALL_DEFINE2(allocMsgQueue, int, capacity, int, flags) {
    int exitStat = 0;
    
    if (current->msgQueue.buffer != NULL) { 
        exitStat = -EBUSY;
    } else if (capacity <= 0 || capacity > PG_SIZE) {
        exitStat = -EINVAL;
    } else {
        mutex_lock(&current->msgQueue.mutexStruct);
        current->msgQueue.buffer = kmalloc(capacity, GFP_KERNEL);
        current->msgQueue.capacity = capacity;
        current->msgQueue.flags = flags;
        mutex_unlock(&current->msgQueue.mutexStruct);
    }
    
    return exitStat;
}

/*
 * function responsible for deallocating the message queue for the calling
 * process.
 *
 * Returns: The success status.
 */
int __freeMsgQueue(void) {
    int exitStat = 0;

    if (current->msgQueue.buffer == NULL) {
        exitStat = -EINVAL;
    } else {
        mutex_lock(&current->msgQueue.mutexStruct);
        kfree(current->msgQueue.buffer);
        current->msgQueue.buffer    = NULL;
        current->msgQueue.head     = 0;
        current->msgQueue.tail     = 0;
        current->msgQueue.size     = 0;
        current->msgQueue.capacity = 0;
        wake_up(&current->msgQueue.writeQueue);
        mutex_unlock(&current->msgQueue.mutexStruct);
      }
    
    return exitStat;
}

/*
 * System call responsible for deallocating the message queue for the calling
 * process.
 *
 * Returns: The success status.
 */
SYSCALL_DEFINE0(freeMsgQueue) {
    return __freeMsgQueue();
}

/*
 * System call responsible for writting the message queue associated with a
 * process with a given process ID.
 *
 * Params:  int pid    - An integer representing the process is of the process
 *                       whose message queue to which the call wants to write.
 *          char* mesg - A pointer to a character that will be a pointer to some
 *                       message that the calling application wants to send to
 *                       the target process.
 *          int nbytes - An integer representing the number of bytes to send.
 *
 * Returns: The success status.
 */
SYSCALL_DEFINE3(msgQueueWrite, int, pid, char*, mesg, int, nbytes) {
    struct task_struct *cursor;
    struct task_struct *p = NULL;
    int i;
    
    for_each_process(cursor) {
        if (cursor->pid == pid) {
            p = cursor;
            break;
        }
    }

    if (!p) {
        return -EINVAL;
    }

    mutex_lock(&p->msgQueue.mutexStruct);
    
    if (p->msgQueue.capacity < nbytes) {
        mutex_unlock(&p->msgQueue.mutexStruct);
        return -EINVAL;
    }

    if (p->msgQueue.flags & LOCAL_PROC && 
                current->cred->fsuid != ROOT_PROC &&
                current->cred->fsuid != p->cred->fsuid) {
        mutex_unlock(&p->msgQueue.mutexStruct);
        return -EINVAL;
    }
    
    while (p->msgQueue.size + nbytes > p->msgQueue.capacity &&
                p->msgQueue.buffer != NULL) {
        mutex_unlock(&p->msgQueue.mutexStruct);

        if (wait_event_interruptible(p->msgQueue.writeQueue, 
                p->msgQueue.size + nbytes <= p->msgQueue.capacity)) {
            return -ERESTARTSYS;
        }
        mutex_lock(&current->msgQueue.mutexStruct);
    }

    if (p->msgQueue.buffer == NULL) {
        mutex_unlock(&p->msgQueue.mutexStruct);
        return -EPIPE;
    }
    
    for (i = 0; i < nbytes; i++) {
        get_user(p->msgQueue.buffer[p->msgQueue.tail], &mesg[i]);
        p->msgQueue.tail = (p->msgQueue.tail + 1) % p->msgQueue.capacity;
    }
    p->msgQueue.size += nbytes;

    wake_up(&p->msgQueue.readQueue);
    mutex_unlock(&p->msgQueue.mutexStruct);

    return 0;
}


/*
 * System call responsible for reading from the message queue associated with
 * the calling process.
 *
 * Params:  char* buffer - A pointer to a character that will be a pointer to
 *                         some buffer into which to receive a message.
 *          int nbytes   - An integer representing the number of bytes to read.
 *
 * Returns: The success status.
 */
SYSCALL_DEFINE2(msgQueueRead, char*, buffer, int, nbytes) {
    struct task_struct *p = current;
    int i;
    
    mutex_lock(&p->msgQueue.mutexStruct);
    if (p->msgQueue.capacity < nbytes) {
        mutex_unlock(&p->msgQueue.mutexStruct);
        return -EINVAL;
    }
    
    while (p->msgQueue.size + nbytes > p->msgQueue.capacity &&
                p->msgQueue.buffer != NULL) {
        mutex_unlock(&p->msgQueue.mutexStruct);

        if (wait_event_interruptible(p->msgQueue.readQueue, 
                p->msgQueue.size + nbytes <= p->msgQueue.capacity)) {
            return -ERESTARTSYS;
        }
        mutex_lock(&current->msgQueue.mutexStruct);
    }

    for (i = 0; i < nbytes; i++) {
        put_user(p->msgQueue.buffer[p->msgQueue.tail], &buffer[i]);
        p->msgQueue.tail = (p->msgQueue.tail + 1) % p->msgQueue.capacity;
    }
    p->msgQueue.size -= nbytes;

    wake_up(&p->msgQueue.writeQueue);
    mutex_unlock(&p->msgQueue.mutexStruct);

    return 0;
}

