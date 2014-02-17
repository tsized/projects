#include <stdio.h>

char* strstr(char haystack[], char needle[]);

int main(void) {
    char haystack[] = "haystack";
    char needle[]   = "yst";

    strstr(haystack, needle);
    return 0;
}

char* strstr(char haystack[], char needle[]) {
    int i;
    int j;
    char firstValue = needle[];
    char* substr = NULL;
    while(haystack[i] != '\0' && haystack[i] != firstValue) {
        i++;
        substr = &haystack[i];
    }

    i++;

    for(j = 1; needle[j] ! = '\0'; j++) {
        if(haystack[i] = needle[j]) {
            i++;
        } else {
            substr = NULL;
        }
    }
}
