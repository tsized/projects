#include<stdio.h>

#define PRINT(A,B,C) printf("%f, %f, %f\n", A, B, C)
#define PRINTA(A,B,C) printf("%p, %p, %p\n", &A, &B, &C)

void swap(float* a, float* b) {
    float temp = *a;
    *a = *b;
    *b = temp;
}

int main(void) {

    float f1, f2, f3;

    printf("Enter three float values > \n");
    scanf("%f %f %f", &f1, &f2, &f3);
    PRINT(f1, f2, f3);
    PRINTA(f1, f2, f3);

    swap(&f1, &f3);
    swap(&f1, &f2);
    PRINT(f1, f2, f3);
    PRINTA(f1, f2, f3);
    return 0;
}
