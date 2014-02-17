#ifndef QUEUE_H
#define QUEUE_H

typedef struct queue Queue;

/* This struct will contain the functionality of our Queue. */
struct queue {
    /* A pointer to void that will reference tha data associated with a concrete
     * Queue implementation.
     */
    void* privateData;

    /* A pointer to a function that returns nothing and takes two parameters:
     * a pointer to a Queue and an int
     */
    void (*enqueue) (Queue* q, int value);

    /* A pointer to a function that returns nothing and takes one parameter:
     * a pointer to a Queue.
     */
    void (*dequeue) (Queue* q);

    /* A pointer to a function that returns nothing and takes one paramter:
     * a pointer to a Queue.
     */
    void (*print) (Queue* q);

    /* A pointer to a function that returns an int and takes one parameter:
     * a pointer to a Queue.
     */
    int  (*peek) (Queue* q);

    /* A pointer to a function that returns an int and takes one parameter:
     * a pointer to a Queue.
     */
    int  (*size) (Queue* q);
};

#endif
