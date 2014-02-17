#include <stdio.h>
#include "queue.h"
#include "arrayQueue.h"
#include "linkedQueue.h"

/*
 * Author: Tim Sizemore
 * Author: Nick Pope
 */

/*
 * Uses C's version of polymorphism to black box test the queue.
 */
static void testQueue(Queue* queue) {
    queue->enqueue(queue, 1);
    printf("peek should be 1: %d\n", queue->peek(queue));
    queue->enqueue(queue, 2);
    printf("size should be 2: %d\n", queue->size(queue));
    queue->print(queue);
    queue->dequeue(queue);
    queue->print(queue);
    queue->dequeue(queue);
    queue->print(queue);
}

/*
 * Realizes the queue ADT with an Array and then a LinkedList
 */
int main(void) {
    Queue queue;

    printf("Array Queue Tests\n");
    newArrayQueue(&queue);
    testQueue(&queue);
    deleteArrayQueue(&queue);

    printf("\n");
    printf("Linked Queue Tests\n");
    newLinkedQueue(&queue);
    testQueue(&queue);
    deleteLinkedQueue(&queue);
    return 0;
}

