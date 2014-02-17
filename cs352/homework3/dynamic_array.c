#include<stdio.h>
#include<stdlib.h>

/**
 * This program reads numbers from the command line and dynamically allocates 
 * an array for them. It then prints the values entered and gives their sum and average.
 *
 * @author Tim Sizemore
 * @version 17 February 2014
 */

/* A variable visible to the whole file that holds the sum of the numbers. */
static int g_sum = 0;

/* Contains the total size of the array. */
static int g_size = 0;

/*
 * A wrapper for the malloc method that abstracts null checking.
 *
 * @param size - the size of the array
 */
void* Malloc(size_t size) {
    void *address = malloc(size);
    if (address == NULL) {
        printf("Not enough memory\n");
        exit(1);
    }
    return address;
}

/*
 * A wrapper for the realloc method that abstracts null checking.
 *
 * @param ptr - a pointer of where to store the new data
 * @param size - the size of which to allocate
 * */
void* Realloc(void* ptr, size_t size) {
    void *address = realloc(ptr, size);
    if (address == NULL) {
        printf("Not enough memory\n");
        exit(1);
    }
    return address;
}

/*
 * Prints the values entered within the current array set then prints the sum
 * and the average out for the whole array.
 *
 * @param array_p - a pointer to the first element in the array
 * @param size    - the number of elements entered into the array
 */
void display(int* array_p, int size) {
    int i;
    double average;
    int *pointer = NULL;

    g_size += size;
    i = 0;
    average = 0.0;
    for(pointer = array_p; i < size; pointer++) {
        //printf("Entered Num  %d > %d\n", i, *pointer);
        g_sum += *pointer;
        i++;
    }
    printf("Total = %d\n", g_sum);
    average = g_sum / g_size;
    printf("Average = %.2f\n", average);
}

void print_array(int* array_p) {
    int i;
    int *pointer = NULL;
    for(pointer = array_p - 1; i < g_size; pointer++) {
        printf("Entered Num  %d > %d\n", i, *pointer);
        i++;
    }
}

/*
 * Controls the flow of the program by dynamically allocating two arrays of
 * ints and then filling them from values given by the user using the
 * command line. After each iteration of values entered, display is called
 * and the values are printed to the screen.
 *
 * @return zero if the program exits successfully, nonzero otherwise.
 */
int main(void) {
    int size, i;
    int *array_p = NULL;
    int *narray_p = NULL;
    int *pointer = NULL;

    printf("How many values will you enter? > ");
    scanf("%d", &size);
    if (!size) { /* if 0 */
        printf("Enter a nonzero number.\n");
        return 1;
    }
    array_p = (int *) Malloc(size*sizeof(int));

    i = 0;
    for(pointer = array_p; i < size; pointer++) {
        printf("Enter Number > ");
        scanf("%d", pointer);
        i++;
    }
    display(array_p, size);

    printf("How many more values will you enter? > ");
    scanf("%d", &size);
    narray_p = (int *) Realloc(array_p, size*sizeof(int) + 1);

    i = 0;
    for(pointer = narray_p; i < size; pointer++) {
        printf("Enter Number > ");
        scanf("%d", pointer);
        i++;
    }

    display(narray_p, size);
    print_array(array_p);
    free(array_p);
    return 0;
}
