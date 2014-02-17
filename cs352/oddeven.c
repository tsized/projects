#include<stdio.h>                                                              
#include"const.h"

/**
 * oddeven.c
 * Prints a list of even and odd numbers.
 *
 * @author Chris Ward
 * @author Tim Sizemore
 * @version 01/24/2014
 */

/**
 * Main entry point of app.
 * Prints each line with a number, and whether or not it's even or odd, up to
 * the maxValue.
 *
 * @return 0 - if exit successfully, otherwise non-0
 */
int main(void) {                                                                
    //Increments in for-loop
    int i;                                                                      
    for(i = startIncrement; i <= maxIncrement; i++) {                                                  
        if(i % modValue == 0) {                                                        
            printf("%i even\n", i);                                            
        } else {                                                                
            printf("%i odd\n", i);                                              
        }                                                                      
    }                                                                          
    return(0);                                                                  
}
