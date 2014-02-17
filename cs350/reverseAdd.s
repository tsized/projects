# A MIPS program that takes the value enter and adds each individual value then
# prints the value enter out in reverse.
#
# @author Tim Sizemore
# @author Bill Franklin
# --- Data Segment ---
.data

mesg0: .asciiz "Please enter an integer > "
mesg1: .asciiz "The String backwards is > "
nline: .asciiz "\n"
plus:  .asciiz " + "
equals: .asciiz " = "
theString: .space 8
revString: .space 8

# --- Text Segment ---
.text
# s0 = integer input/integer after shifting
# s1 = first integer
# s2 = second integer
# s3 = third integer
# s4 = fourth integer
# s5 = reversed string
# s8 = total

main:
#Prints mesg0 to the screen
    li $v0, 4
    la $a0, mesg0
    syscall

#Prompt user for String input
    li $v0, 8
    la $a0, theString
    li $a1, 8
    syscall

#Load each byte value or the integer into different registers
    lb $s1, 0($a0)
    lb $s2, 1($a0)
    lb $s3, 2($a0)
    lb $s4, 3($a0)

#Build the reversed String by moving each byte pulled from the integer into the
#destination register with left shifts then OR the value with the shifted bits
    move $s5, $s1
    sll $s5, $s5, 8
    or $s5, $s5, $s2
    sll $s5, $s5, 8
    or $s5, $s5, $s3
    sll $s5, $s5, 8
    or $s5, $s5, $s4
    la $s6, revString
    sw $s5, 0($s6)

#Add all the individual bits with negative ascii value
    addi $s1, $s1, -48
    addi $s2, $s2, -48
    addi $s3, $s3, -48
    addi $s4, $s4, -48
    
#Add the manipulated bits together and place in a signle register
    add $s8, $s1, $s2
    add $s8, $s8, $s3
    add $s8, $s8, $s4

#Print out integer with adding/equals pattern
    li $v0, 1
    move $a0, $s1
    syscall

    li $v0, 4
    la $a0, plus
    syscall

    li $v0, 1
    move $a0, $s2
    syscall
    
    li $v0, 4
    la $a0, plus
    syscall

    li $v0, 1
    move $a0, $s3
    syscall

    li $v0, 4
    la $a0, plus
    syscall
    
    li $v0, 1
    move $a0, $s4
    syscall

    li $v0, 4
    la $a0, equals
    syscall

#Prints the reversed string
    li $v0, 1
    move $a0, $s8
    syscall

    li $v0, 4
    la $a0, nline
    syscall

    li $v0, 4
    la $a0, mesg1
    syscall

    li $v0, 4
    la $a0, revString
    syscall

    li $v0, 4
    la $a0, nline
    syscall

    li $v0, 10
    syscall
