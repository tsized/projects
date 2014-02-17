#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include "veclib.h"

/*
 * This function serves as the entry point of our program. It tests the 
 * implementation of the vertex library.
 */
int main(void) {
    double v1[] = {1,2,3};
    double v2[] = {5,0,2};
    int length = 3;
    double retVec[3];

    printf("=== Testing Veclib ===\n");
    // Print test
    vecprn3("Vector 1: ", v1);
    printf("\n");
    vecprn3("Vector 2: ", v2);
    printf("\n");
    printf("\n");

    // Dot test
    printf("-- Testing Dot --\n");
    printf("v1 . v2 = %f\n", dot3(v1, v2));
    printf("v2 . v1 = %f\n", dot3(v2, v1));
    printf("v1 . v1 = %f\n", dot3(v1, v1));
    printf("v2 . v2 = %f\n", dot3(v2, v2));
    printf("Printing vectors to make sure the haven't changed\n");
    vecprn3("Vector 1: ", v1);
    printf("\n");
    vecprn3("Vector 2: ", v2);
    printf("\n");
    printf("\n");

    // Scale test
    printf("-- Testing Scale --\n");
    // Scale by 1
    printf("v1  * 1\n");
    scale3(1, v1, retVec);
    vecprn3("Vector 1: ", retVec);
    printf("\n");
    printf("v2  * 1\n");
    scale3(1, v2, retVec);
    vecprn3("Vector 2: ", retVec);
    printf("\n");
    printf("Printing vectors to make sure the haven't changed\n");
    vecprn3("Vector 1: ", v1);
    printf("\n");
    vecprn3("Vector 2: ", v2);
    printf("\n");
    printf("Passing vector as both parameters\n");
    printf("v1  * 1\n");
    scale3(1, v1, v1);
    vecprn3("Vector 1: ", v1);
    printf("\n");
    printf("v2  * 1\n");
    scale3(1, v2, v2);
    vecprn3("Vector 2: ", v2);
    printf("\n");
    printf("Printing vectors to make sure they have changed\n");
    vecprn3("Vector 1: ", v1);
    printf("\n");
    vecprn3("Vector 2: ", v2);
    printf("\n");
    printf("\n");

    // Scale by 5
    printf("v1  * 5\n");
    scale3(5, v1, retVec);
    vecprn3("Vector 1: ", retVec);
    printf("\n");
    printf("v2  * 5\n");
    scale3(5, v2, retVec);
    vecprn3("Vector 2: ", retVec);
    printf("\n");
    printf("Printing vectors to make sure the haven't changed\n");
    vecprn3("Vector 1: ", v1);
    printf("\n");
    vecprn3("Vector 2: ", v2);
    printf("\n");
    printf("Passing vector as both parameters\n");
    printf("v1  * 5\n");
    scale3(5, v1, v1);
    vecprn3("Vector 1: ", v1);
    printf("\n");
    printf("v2  * 5\n");
    scale3(5, v2, v2);
    vecprn3("Vector 2: ", v2);
    printf("\n");
    printf("Printing vectors to make sure they have changed\n");
    vecprn3("Vector 1: ", v1);
    printf("\n");
    vecprn3("Vector 2: ", v2);
    printf("\n");
    printf("\n");

    // Length test
    printf("-- Testing Length --\n");
    // Resetting vectors 
    printf ("Changing Vectors\n");
    //v1 = {5, 6, 1.6};
    //v2 = {0, 4, 0};

    vecprn3("Vector 1: ", v1);
    printf("\n");
    vecprn3("Vector 2: ", v2);
    printf("\n");
    printf("Vector 1 length: %f\n", length3(v1));
    printf("Vector 2 length: %f\n", length3(v2));

    // Changing vectors again
    printf ("Changing Vectors\n");
    //v1 = {1, 1, 1};
    //v2 = {4, 1, 7};
    vecprn3("Vector 1: ", v1);
    printf("\n");
    vecprn3("Vector 2: ", v2);
    printf("\n");
    printf("Vector 1 length: %f\n", length3(v1));
    printf("Vector 2 length: %f\n", length3(v2));
    printf("\n");
    printf("\n");

    // Diff Test
    printf("-- Testing Diff -- \n");
    printf("\n");
    vecprn3("Vector 1: ", v1);
    printf("\n");
    vecprn3("Vector 2: ", v2);
    printf("\n");
    diff3(v1, v2, retVec);
    vecprn3("v1 - v2 ", retVec);
    printf("\n");
    diff3(v2, v1, retVec);
    vecprn3("v2 - v1 ", retVec);
    printf("\n");
    diff3(v1, v1, retVec);
    vecprn3("v1 - v1 ", retVec);
    printf("\n");
    diff3(v2, v2, retVec);
    vecprn3("v2 - v2 ", retVec);
    printf("\n");
    printf("\n");


    // Sum Test
    printf("-- Testing Sum -- \n");
    vecprn3("Vector 1: ", v1);
    printf("\n");
    vecprn3("Vector 2: ", v2);
    printf("\n");
    sum(v1, v2, retVec);
    vecprn3("v1 + v2 ", retVec);
    printf("\n");
    sum(v2, v1, retVec);
    vecprn3("v2 + v1 ", retVec);
    printf("\n");
    sum(v1, v1, retVec);
    vecprn3("v1 + v1 ", retVec);
    printf("\n");
    sum(v2, v2, retVec);
    vecprn3("v2 + v2 ", retVec);
    printf("\n");
    printf("\n");

    // Unit vetortize Test
    printf("-- Testing Unitize --\n");
    vecprn3("Vector 1: ", v1);
    printf("\n");
    vecprn3("Vector 2: ", v2);
    printf("\n");
    unitvec(v1, retVec);
    vecprn3("Vector 1 unit length: ", retVec);
    printf("\n");
    printf("New length (please be 1): %f\n", length3(retVec));
    unitvec(v2, retVec);
    vecprn3("Vector 2 unit length: ", retVec);
    printf("\n");
    printf("New length (please be 1): %f\n", length3(retVec));
    printf("Using vector as both params\n");
    unitvec(v1, v1);
    vecprn3("Vector 1", v1);
    printf("\n");
    printf("New length (please be 1): %f\n", length3(v1));
    unitvec(v2, v2);
    vecprn3("Vector 2", v2);
    printf("\n");
    printf("New length (please be 1): %f\n", length3(v2));

    return 0;
}
