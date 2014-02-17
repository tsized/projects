##
# Translates a C program for spaced insertion sort
#
#
##
.data 

array: .word 4 5 7 2 5 9 1 5 6 7
space: .asciiz " "
nline: .asciiz "\n"

.text
    
# t0 = i
# t2 = address of the array
# t3 = size of the array
# t4 = address of the array unmodified
main:
    li $t0, 0               # i = 0
    li $t3, 10              # size = 10
    la $t2, array
    move $t4, $t2

# t0 = i
# t1 = conditional value
# t2 = modified address of the array
# t3 = size of the array
loop:
    li $v0, 1
    slt $t1, $t0, $t3       # is i < y
    beq $t1, $zero, loopEnd 
    
    lw $a0, 0($t2)          # print int
    syscall

    addi $t2, $t2, 4        # go to next word in the array
    
    li $v0, 4
    la $a0, space
    syscall

    addi $t0, $t0, 1        # i++
    j loop

# t4 = unmodified address of the array
# a0 = address of the array
# a1 = start index
# a2 = size of the array, length to sort
# a3 = offset
loopEnd:
    li $v0, 4
    la $a0, nline            # prints a new line
    syscall

    move $a0, $t4            # address of the array
    li $a1, 5                # start
    move $a2, $t3            # end
    li $a3, 1                # offset

    jal insert

# t0 = i
# t1 = conditional value
# t2 = modified address of the array
# t3 = size of the array
loop2:
    li $v0, 1

    slt $t1, $t0, $t3       # is i < y
    beq $t1, $zero, programEnd 
    
    lw $a0, 0($t2)          # print int
    syscall

    addi $t2, $t2, 4        # go to next word in the array
    
    li $v0, 4
    la $a0, space
    syscall

    addi $t0, $t0, 1        # i++
    j loop2


programEnd:
    li $v0, 4
    la $a0, nline            # prints a new line
    syscall

    li $v0, 10
    syscall

# t0 = current
# t2 = size of a word in MIPS
insert:
    add $t0, $a1, $a3       # current = start + offset
    li $t2, 4               # size of a word to calc offset

# t0 = current
# t1 = conditional value
# t3 = current * 4, offset - 4, address of the array modified by the index
# a0 = unmodified address of the array
# a2 = length to sort
forLoop1: 
    slt $t1, $t0, $a2       # current < end
    beq $t1, $zero, return  # return if current < end
    
    mul $t3, $t0, $t2       # calculate the offset
    add $t3, $t3, $a0       # int element = address of list[current]
    lw $t3, 0($t3)          # element = list[current]

    sub $t4, $t0, $a3       # index = current - offset

# t0 = current
# t1 = index
# t2 = size of a word in MIPS
# t3 = element aka list[current]
# t4 = unmodified index
# t5 = list[index + offset]
forLoop2:
    move $t1, $t4

    mul $t1, $t1, $t2       # $t1 = index * 4
    add $t1, $t1, $a0       # $t1 = address of list[index]
    lw $t1, 0($t1)          # $t1 = list[index]

    blt $t4, $a1, failCase  # jump if index < start
    bge $t3, $t1, failCase  # jump if element >= list[index]

    add $t5, $t4, $a3       # $t5 = index + offset
    mul $t5, $t5, $t2
    add $t5, $t5, $a0       # $t5 = address of list[index + offset]
    
    sw $t1, 0($t5)          # list[index + offset] = list[index]

    sub $t4, $t4, $a3       # index -= offset
    j forLoop2
    
# t0 = i
# t2 = address of the array
# t3 = size
return:
    li $t0, 0               # i = 0
    li $t3, 10              # size = 10
    la $t2, array
    jr $ra

# t0 = current
# t2 = size of a word
# t3 = element
# t4 = index
# t5 = address of list[index + offset]
# a0 = address of the array
# a3 = offset
failCase:
    add $t5, $t4, $a3       # $t5 = index + offset
    mul $t5, $t5, $t2
    add $t5, $t5, $a0       # $t5 = address of list[index + offset]
    
    sw $t3, 0($t5)           # list[index + offset] = element
    add $t0, $t0, $a3       # current += offset
    j forLoop1
