#include <stdio.h>
#include <math.h>
#include "veclib.h"

/**
 * @author Tim Sizemore
 * @author Bill Franklin
 */

/*
 * Returns the inner product of two input vectors.
 *
 * @param first a vector
 * @param second a vector
 */
double dot3(double *first, double *second) {
    double sum;
    int i;
    for(i = 0; i < 3; i++) {
        sum += first[i] * second[i];
    }
    return sum;
}

/*
 * Scale a 3d vector.
 *
 * @param factor the value to scale the vector by
 * @param input a vector
 * @param output the vector altered by the scaling factor
 */
void scale3(double factor, double *input, double *output) {
    int i;
    for(i =0; i < 3; i++) {
        output[i] = input[i] * factor;
    }
}

/*
 * Returns the length of a 3d vector.
 *
 * @param first a vector
 */
double length3(double *first) {
    double length = 0;
    int i;
    for(i = 0; i < 3; i++) {
        length += pow(first[i], 2);
    }
    return sqrt(length);
}

/* 
 * Computes the difference of two vectors.
 *
 * @param first a vector
 * @param second a vector
 * @param diff the vector containing the subtracted values
 */
void diff3(double *first, double *second, double *diff) {
    int i;
    for(i = 0; i < 3; i++) {
        diff[i] = second[i] - first[i];
    }
}

/*
 * Compute the sum of two vectors.
 *
 * @param first a vector
 * @param second a vector
 * @param sum the vector containing the summed values
 */
void sum(double *first, double *second, double *sum) {
    int i;
    for(i = 0; i < 3; i++) {
        sum[i] = second[i] + first[i];
    }
}

/*
 * Constructs a unit vector in the direction of first.
 * 
 * @param first a vector
 * @param second the vector containing the unit values
 */
void unitvec(double *first, double *second) {
    double unit = 1 / (sqrt(dot3(first, first)));
    int i;
    for(i = 0; i < 3; i++) {
        second[i] = first[i] * unit;
    }
}

/*
 * Prints a label and the contents of a vector.
 *
 * @param label a label for the vector to print
 * @param first the vector to print
 */
void vecprn3(char *label, double *first) {
    printf("%s = <%f, %f, %f>", label, first[0], first[1], first[2]);
}
