#include <stdio.h>
#include <math.h> // See warning below
#include "veclib.h"
double v1[] = {3.0, 4.0, 5.0};
double v2[] = {4.0, -1.0, 2.0};
int main(){
    double v3[3];
    double v4[3];
    double v;
    vecprn3("v2", v2);
    vecprn3("v3", v3);
    diff3(v1, v2, v3);
    vecprn3("v2 - v1 = ", v3);
    v = dot3(v1, v2);
    printf("v1 dot v2 is %8.3lf \n", v);
    v = length3(v1);
    printf("Length of v1 is %8.3lf \n", v);
    scale3(1 / v, v1, v3);
    vecprn3("v1 scaled by its 1/ length:", v3);
    unitvec(v1, v4);
    vecprn3("unit vector in v1 direction:", v4);
    return 0;
}
