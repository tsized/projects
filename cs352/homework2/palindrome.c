#include<stdio.h>
#include<string.h>
#include<ctype.h>

/**
 * A program that takes a string and returns its reverse while determining if
 * it is a palindrome.
 *
 * @author Tim Sizemore
 * @version 14 February 2014
 */

/*
 * One of the coolest implementations I've seen that isn't esoteric. Uses XOR
 * and runs in O(n) and in situ.
 */
void rev_in_n(char* str)
{
  int end   = strlen(str) - 1;
  int start = 0;

  while(start < end) {
    str[start] ^= str[end];
    str[end]   ^= str[start];
    str[start] ^= str[end];

    ++start;
    --end;
  }
}

/*
 * The reverse I actually call in main. Takes advantage of pointer arithmetic
 * and flips a string in situ.
 */
void rev(char* str) {
    char *src = str;
    char *end = src + strlen(src) - 1;
    while (end > src) {
        char t = *end;
        *end-- = *src;
        *src++ = t;
    }
}

/*
 * Checks if the given string is a palindrome and returns a value based on the
 * answer.
 */
int palindrome(char* string) {
    size_t len = strlen(string);

    // handle empty string and string of length 1:
    if (len == 0) return 0;
    if (len == 1) return 1;

    char *ptr1 = string;
    char *ptr2 = string + len - 1;
    while(ptr2 >= ptr1) {
        if (!isalpha(*ptr2)) {
            ptr2--;
            continue;
        }
        if (!isalpha(*ptr1)) {
            ptr1++;
            continue;
        }
        if(tolower(*ptr1) != tolower(*ptr2)) {
            return 0;
        }
        ptr1++;
        ptr2--;
    }
    return 1;
}

/*
 * Executes each aspect of the program and returns success or failure.
 */
int main(void) {
    char string_p[256];
    char copy_p[256];
    printf("Enter a string.\n");
    fgets(string_p, sizeof(string_p), stdin);
    rev(string_p);
    puts(string_p);

    if(palindrome(string_p)) {
        printf("String IS a palindrome.\n");
    } else {
        printf("String IS NOT a palindrome.\n");
    }
    return 0;
}
