#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "queue.h"
#include "Malloc.h"

/*
 * Maintains the default values of the arrayQueue
 */
enum {
    /*
     * Default capacity of the array that does not change
     */
    DEFAULT_CAPACITY = 10,

    /*
     * Amount to resize the array when size = capacity
     */
    RESIZE_FACTOR    = 2
};

/*
 * Aliasing the ArrayQueue struct
 */
typedef struct ArrayQueue ArrayQueue;

/*
 * Struct of the ArrayQueue that abstracts the fields below
 */
struct ArrayQueue {
    /*
     * Holds the int value stroed in the arrayQueue
     */
    int* data;

    /*
     * Holds the maximum number of data values that described
     */
    int  capacity;

    /*
     * Holds the number of data values in the arrayQueue
     */ 
    int  size;
};

/*
 * Adds a data value to the front of the arrayQueue
 *
 * param: queue - the queue to be cast to an ArrayQueue
 * param: value - the value to be added to the data of the ArrayQueue
 */
static void arrayQueueEnqueue(Queue *queue, int value) {
    ArrayQueue* arrayQueue = (ArrayQueue*) queue->privateData;

    if (arrayQueue->size == arrayQueue->capacity) {
        arrayQueue->capacity = RESIZE_FACTOR * arrayQueue->capacity;
        arrayQueue->data     =
                  Realloc(arrayQueue->data, sizeof(int) * arrayQueue->capacity);
    }

    arrayQueue->data[arrayQueue->size] = value;
    arrayQueue->size++;
}

/*
 * Removes the data value from the front of the ArrayQueue
 *
 * param: queue - the queue to be cast to an ArrayQueue
 */
static void arrayQueueDequeue(Queue *queue) {
    ArrayQueue* aQ = (ArrayQueue*) queue->privateData;

    memmove(aQ->data, aQ->data + 1, sizeof(int) * (aQ->size - 1));
    aQ->size--;
}

/*
 * Return the value of the ArrayQueue's first element's head
 *
 * param: queue - the queue to be cast to an ArrayQueue
 */
static int arrayQueuePeek(Queue *queue) {
    ArrayQueue* aQ = (ArrayQueue*) queue->privateData;

    return aQ->data[0];
}

/*
 * Returns the value of size which is the number of elements in the ArrayQueue
 *
 * param: queue - the queue to be cast to an ArrayQueue
 */
static int arrayQueueSize(Queue *queue) {
    ArrayQueue* aQ = (ArrayQueue*) queue->privateData;
    return aQ->size;
}

/*
 * A function that loops through the ArrayQueue and prints the elements of first
 * to last
 *
 * param: queue- the queue to be cast to an ArrayQueue
 */
static void arrayQueuePrint(Queue *queue) {
    ArrayQueue* arrayQueue = (ArrayQueue*) queue->privateData;

    printf("[");
    if (arrayQueue->size > 0) {
        int i;
        printf("%d", arrayQueue->data[0]);
        for(i = 1; i < arrayQueue->size; i++) {
            printf(", %d", arrayQueue->data[i]);
        }
    }
    printf("]\n");
}

/*
 * Initializes the ArrayQueue to its default values and Mallocs the necessary
 * memory for both the ArrayQueue and ArrayQueue's data
 *
 * param: queue - the queue to be cast to an ArrayQueue
 */
void newArrayQueue(Queue* queue) {
    ArrayQueue* aQ = Malloc(sizeof(ArrayQueue));
    aQ->data = Malloc(sizeof(int) * DEFAULT_CAPACITY);    

    queue->privateData = aQ;
    aQ->size           = 0;
    aQ->capacity       = DEFAULT_CAPACITY;
    
    queue->enqueue = arrayQueueEnqueue;
    queue->print   = arrayQueuePrint;
    queue->dequeue = arrayQueueDequeue;
    queue->peek    = arrayQueuePeek;
    queue->size    = arrayQueueSize;
}

/*
 * Frees all dynamically allocated memory and sets queue's pointers to functions
 * null
 *
 * param: queue - the queue to be cast to an ArrayQueue
 */
void deleteArrayQueue(Queue* queue) {
    ArrayQueue* arrayQueue = (ArrayQueue*) queue->privateData;
    
    free(arrayQueue->data);
    free(arrayQueue);

    queue->enqueue = NULL;
    queue->print   = NULL;
    queue->dequeue = NULL;
    queue->peek    = NULL;
    queue->size    = NULL;
}
