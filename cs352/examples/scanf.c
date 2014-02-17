#include<stdio.h>
#include<stdlib.h>
int main(void) {
    int Q, D, N, P;
    double sum;
    printf("HOw many Q, D, N, P?\n");
    scanf("Q: %d, D: %d, N: %d, P: %d\n", &Q, &D, &N, &P);
    sum = (Q * .25) + (D * .1) + (N * .05) + (P + .01);
    printf("sum: %f", sum);
}
