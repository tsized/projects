#include<stdio.h>
#include"const.h"

/*
 * Prints the temperatures from 20 to 212 and determines if it is cold, warm, or
 * hot.
 *
 * @author Tim Sizemore
 * @author Chris Ward
 * @version 27 January, 2014
 *
 */
int main(void) {
    //Incremented in for-loop
    double i;
    //The current temp
    int count = startIncrement;

    for(i = minTemp; i <= maxTemp; i += incTemp, count++) {
        if(i < wrmTemp) {
            printf("%d cold %.4f\n", count, i);
        } else if(i < hotTemp) {
            printf("%d warm %.4f\n", count, i);
        } else {
            printf("%d hot %.4f\n", count, i);
        }
    }

    return(0);
}
