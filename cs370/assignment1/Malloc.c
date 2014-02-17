#include <stdio.h>
#include <stdlib.h>
/*
 * Author: Tim Sizemore
 * Author: Nick Pope
 */

/* 
 * A wrapper for the malloc method that abstracts null checking.
 *
 * param: size - the size of which to allocate
 */
void *Malloc(size_t size) {
    void *address = malloc(size);
    if (address == NULL) {
        printf("Not enough memory");
        exit(1);
    }
    return address;
}

/*
 * A wrapper for the realloc method that abstracts null checking.
 *
 * param: ptr - a pointer of where to store the new data
 * param: size - the size of which to allocate
 * */
void *Realloc(void *ptr, size_t size) {
    void *address = realloc(ptr, size);
    if (address == NULL) {
        printf("Not enough memory");
        exit(1);
    }
    return address;
}
