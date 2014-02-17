#include<stdio.h>

int smallArray[10];

int x[] = {100, 200, 300, 400};

/*
 * arrays accessed via an index
 */
int n1;
for (n1 = 1; n1 < 10; n1++) {
    smallArray[n1] = n1 * 100;
}
