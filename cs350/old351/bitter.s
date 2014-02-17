# Tim Sizemore
# Nick Pope
#------ Data Segment ------
.data

# Declare messages that prompt the user
mesg0: .asciiz "Please enter an integer > "
mesg1: .asciiz "Please enter a Byte position (0 - 3) > "
mesg2: .asciiz "Please enter a bit to sum (0 or 1) > "
mesg3: .asciiz "The number of "
mesg4: .asciiz "'s in "
mesg5: .asciiz " is: "
nline: .asciiz "\n"

#------ Text Segment ------
.text
# s0 = integer input, s1 = byte position/and value, s2 = bit, 
# s3 = amount to shift by, s4 = shifted bits, s5 = sum, s6 = xor value, 
# s7 = nor value

main:
# Prints mesg0 to the screen
    li $v0, 4
    la $a0, mesg0
    syscall

# Prompt user for integer input
    li $v0, 5
    syscall
    move $s0, $v0

# Prints mesg1 to the screen
    li $v0, 4
    la $a0, mesg1
    syscall

# Prompts user for Byte position
    li $v0, 5
    syscall
    move $s1, $v0

# Prints mesg2 to the screen
    li $v0, 4
    la $a0, mesg2
    syscall

# Prompts user for bit to sum
    li $v0, 5
    syscall
    move $s2, $v0

# Calculating shift amount
    mul $s3, $s1, 8

# Shift bits based on given byte position
    srlv $s4, $s0, $s3 

# I need to xor by the given bit to sum and then nor by 0 and then and to get 
# the final value and we must do this 8 times
    xor $s6, $s4, $s2
    nor $s7, $s6, 0x0
    and $s1, $s7, 0x1
    add $s5, $s5, $s1
    srl $s4, $s4, 1

    xor $s6, $s4, $s2
    nor $s7, $s6, 0x0
    and $s1, $s7, 0x1
    add $s5, $s5, $s1
    srl $s4, $s4, 1

    xor $s6, $s4, $s2
    nor $s7, $s6, 0x0
    and $s1, $s7, 0x1
    add $s5, $s5, $s1
    srl $s4, $s4, 1
    
    xor $s6, $s4, $s2
    nor $s7, $s6, 0x0
    and $s1, $s7, 0x1
    add $s5, $s5, $s1
    srl $s4, $s4, 1

    xor $s6, $s4, $s2
    nor $s7, $s6, 0x0
    and $s1, $s7, 0x1
    add $s5, $s5, $s1
    srl $s4, $s4, 1

    xor $s6, $s4, $s2

    nor $s7, $s6, 0x0
    and $s1, $s7, 0x1
    add $s5, $s5, $s1
    srl $s4, $s4, 1

    xor $s6, $s4, $s2
    nor $s7, $s6, 0x0
    and $s1, $s7, 0x1
    add $s5, $s5, $s1
    srl $s4, $s4, 1

    xor $s6, $s4, $s2
    nor $s7, $s6, 0x0
    and $s1, $s7, 0x1
    add $s5, $s5, $s1

# Prints the combined final message to the screen   
    li $v0, 4
    la $a0, mesg3
    syscall

    li $v0, 1
    move $a0, $s2
    syscall

    li $v0, 4
    la $a0, mesg4
    syscall

    li $v0, 1
    move $a0, $s0
    syscall

    li $v0, 4
    la $a0, mesg5
    syscall

    li $v0, 1
    move $a0, $s5
    syscall

    li $v0, 4
    la $a0, nline
    syscall

# Executes the "exit" system call
    li $v0, 10
    syscall

