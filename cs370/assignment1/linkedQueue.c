#include <stdio.h>
#include <stdlib.h>
#include "queue.h"
#include "Malloc.h"

/*
 * Author: Nick Pope
 * Author: Tim Sizemore
 */

/* Creating a typedef of Node to Node so we can just say node */
typedef struct node Node;

/* 
 * Creating a struct Node. Contains two fields: an int of data and a reference
 * to the next Node.
 */
struct node {
    /* The integer that linked node will hold. */
    int data;

    /* A pointer to the next Node in a linked list. */
    Node* next;
};

/* 
 * Creating a typedef of LinkedQueue to LinkedQueue so we can just say 
 * linkedQueue.
 */
typedef struct LinkedQueue LinkedQueue;

/* 
 * Creating a struct called LinkedQueue. Has three fields: head which is a
 * reference to the Node, tail which is a reference to the Node, and size which
 * is an int.
 */
struct LinkedQueue {
    /* A pointer to the first node in the linked queue. */
    Node* head;

    /* A pointer to the last node of the linked queue. */
    Node* tail;

    /* The size of the queue. */
    int size;
};

/* 
 * This function creates a new node.
 *
 * @param: data - the integer that the linked node will hold.
 * @param: next - a node reference to the next node.
 *
 * @return: newNode - a pointer to a Node object.
 */
static Node* newNode(int data, Node* next) {
    Node* newNode = Malloc(sizeof(Node));

    newNode->data = data;
    newNode->next = next;

    return newNode;
}

/* 
 * This function deletes all dynamically allocated memory that is associated
 * with the given node.
 *
 * @param: node - a pointer to the node in which the memory will be deleted.
 */
static void deleteNode(Node* node) {
    free(node);
}

/*
 * This function will add a new element to the end of the queue.
 *
 * @param: queue - the queue in which to add the element to.
 * @param: value - the integer in which to add to the end of the queue.
 */
static void linkedQueueEnqueue(Queue* queue, int value) {
    LinkedQueue* linkedQueue = (LinkedQueue*) queue->privateData;
    Node* node = newNode(value, NULL);

    if (linkedQueue->size == 0) {
        linkedQueue->head = node;
        linkedQueue->tail = node;
    } else { 
        linkedQueue->tail->next = node;
        linkedQueue->tail = node;
    }
    linkedQueue->size++;
}

/*
 * This function will remove the first element in the queue and reset the head
 * of the linkedQueue appropriately.
 *
 * @pram: queue - The queue in which to delete the first element.
 */
static void linkedQueueDequeue(Queue* queue) {
    LinkedQueue* linkedQueue = (LinkedQueue*)queue->privateData;
    
    Node* tempNode = linkedQueue->head->next;

    deleteNode(linkedQueue->head);
    linkedQueue->head = tempNode;

    linkedQueue->size--;
}

/* This function will return the first element in the linkedQueue of the given
 * queue.
 *
 * @param: queue - The queue in which to get the first element.
 *
 * @return: the first element in the queue which will be an integer
 */
static int linkedQueuePeek(Queue* queue) {
    LinkedQueue* linkedQueue = (LinkedQueue*) queue->privateData;

    return linkedQueue->head->data;
}

/*
 * This function will return the current size of the linkedQueue.
 *
 * @param: queue - The given queue in which to get the size.
 *
 * @return: The number of elements in the linkedQueue.
 */
static int linkedQueueSize(Queue* queue) {
    LinkedQueue* linkedQueue = (LinkedQueue*) queue->privateData;

    return linkedQueue->size;
}

/*
 * This function will print out the elements in the given queue.
 *
 * @param: queue - The queue in which to print the elements.
 */
static void linkedQueuePrint(Queue* queue) {
    LinkedQueue* linkedQueue = (LinkedQueue*) queue->privateData;

    printf("[");
    if (linkedQueue->size > 0) {
        Node* node = linkedQueue->head;
        printf("%d", node->data);

        for (node = node->next; node != NULL; node = node->next) {
            printf(", %d", node->data);
        }
    }
    printf("]\n");
}

/*
 * This function will initialize the given queue to a linkedQueue. It
 * dynamically creates an initialized linkedQueue and stores it into privateData
 * of the Queue. It also initializes all of the pointers to functions to the
 * functions above.
 *
 * @param: queue - The queue in which to initialize to a LinkedQueue.
 */
void newLinkedQueue(Queue* queue) {
    LinkedQueue* linkedQueue = Malloc(sizeof(LinkedQueue));

    queue->privateData = linkedQueue;
    linkedQueue->head = NULL;
    linkedQueue->tail = NULL;
    linkedQueue->size = 0;

    queue->enqueue = linkedQueueEnqueue;
    queue->dequeue = linkedQueueDequeue;
    queue->peek    = linkedQueuePeek;
    queue->size    = linkedQueueSize;
    queue->print   = linkedQueuePrint;
}

/*
 * This function will free any dynamically allocated memory that is associated
 * with the queue parameter. It will also set all pointers to NULL.
 *
 * @param: queue - The queue in whcih to free memory.
 */
void deleteLinkedQueue(Queue* queue) {
    LinkedQueue* linkedQueue = (LinkedQueue*) queue->privateData;
    
    while (linkedQueue->size > 0) {
        linkedQueueDequeue(queue);
    }
    
    free(queue->privateData);

    linkedQueue->head = NULL;
    linkedQueue->tail = NULL;
    linkedQueue->size = 0;
    queue->enqueue = NULL;
    queue->dequeue = NULL;
    queue->peek    = NULL;
    queue->size    = NULL;
    queue->print   = NULL;
}

