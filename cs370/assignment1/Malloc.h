#ifndef MALLOC_H
#define MALLOC_H
#include <stdlib.h>

/* 
 * A function prototype for the function Malloc. It returns nothing and takes
 * one parameter.
 *
 * param: size - the size of which to allocate
 */
void *Malloc(size_t size);

/* 
 * function prototype for the function Realloc. It returns nothing and takes
 * two parameters.
 *
 * param: ptr - a pointer in which to store the new data
 * param: size - the size of which to allocate
 */
void *Realloc(void *ptr, size_t size);

#endif
