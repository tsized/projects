#
# Makefile
#
# A Makefile to build CS 370 Assignment 1
#
# Dr. Dalton
# January 26, 2012
#
CC=gcc

# Build in debugging flags
CFLAGS=-ggdb

# Enables all the warnings about constructions that are questionable, and that
# are easy to avoid.
CFLAGS+=-Wall

# Enables some extra warning flags that are not enabled by -Wall
CFLAGS+=-Wextra

# Warn whenever a switch statement has an index of enumerate type and lacks
# a case for one or more of them (or a default)
CFLAGS+=-Wswitch

# Warn whenever a swtich statement does not have a default case.
CFLAGS+=-Wswitch-default

# Reject progrs that use forbidden extensions
CFLAGS+=-pedantic-errors

# Turns off certain non-standard gcc extensions
CFLAGS+=-ansi

# Make all warnings into errors
CFLAGS+=-Werror


default:         a.out
Malloc.o:		 Malloc.c
arrayQueue.o:	 arrayQueue.c
linkedQueue.o:	 linkedQueue.c
main.o:		 	 main.c

# Build the executable
a.out: main.o arrayQueue.o linkedQueue.o Malloc.o
	$(CC) $(CFLAGS) $^ $(LIBS)

# Clean out all transient files
clean:
	$(RM) a.out *.o

# Run the executable through valgrind
valgrind: a.out
	valgrind --leak-check=full --malloc-fill=AA --free-fill=BB ./a.out

# Run the executable through gdb
gdb: a.out
	gdb ./a.out

# Just run the executable
run: a.out
	./a.out
