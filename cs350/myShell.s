##
# Translates a C program for spaced insertion sort in a MIPS program with the
# same functionality.
#
# @author Tim Sizemore
# @author Bill Franklin
#
# @version 11 March 2013
##
.data 

array: .word 1 2 3
array1: .word 4 5 7 8 5 9 1 3 6 9 1 87 15 -2 0 0 0 0 0 0
array2: .word 4 5 7 8 5 9 1 3 6 9 1 87 15 -2 0 0 0 0 0 0
listBefore: .asciiz "list before: "
listAfter: .asciiz "list after: "
list2Before: .asciiz "list2 before: "
list2After: .asciiz "list2 after: "
space: .asciiz " "
nline: .asciiz "\n"

.text
    
# t0 = i
# t2 = address of the array
# t3 = size of the array
# t4 = address of the array unmodified
main:
    la $a0, listBefore
    la $a1, array1
    li $a2, 20              # size = 20
    jal printArray

    la $a0, array1        # list
    li $a1, 0             # start
    li $a2, 10            # end
    li $a3, 20            # size
    jal sort

    la $a0, listAfter
    la $a1, array1
    li $a2, 20              # size = 20
    jal printArray
    
    li $v0, 4
    la $a0, nline            # prints a new line
    syscall

    la $a0, list2Before
    la $a1, array2
    li $a2, 20              # size = 20
    jal printArray

    la $a0, array2        # list
    li $a1, 0             # start
    li $a2, 15            # end
    li $a3, 20            # size
    jal sort

    la $a0, list2After
    la $a1, array2
    li $a2, 20              # size = 20
    jal printArray

    li $v0, 10
    syscall

# $a0 = address of message
# $a1 = address of array
# $a2 = size of array
# $t0 = i
# $t1 = conditional variable
# $t2 = modified address of the array
printArray:
    li $t0, 0               # i = 0
    move $t2, $a1

    ble $a2, $zero, return  # size <= 0 { return }
    
    li $v0, 4
    syscall

printForLoop:
    slt $t1, $t0, $a2       # is i < size
    beq $t1, $zero, printForLoopEnd 
    
    li $v0, 1
    lw $a0, 0($t2)          # print int
    syscall

    addi $t2, $t2, 4        # go to next word in the array
    
    li $v0, 4
    la $a0, space
    syscall

    addi $t0, $t0, 1        # i++
    j printForLoop

# t4 = unmodified address of the array
# a0 = address of the array
# a1 = start index
# a2 = size of the array, length to sort
# a3 = offset
printForLoopEnd:
    li $v0, 4
    la $a0, nline           # prints a new line
    syscall
    
return:
    jr $ra

programEnd:
    li $v0, 4
    la $a0, nline           # prints a new line
    syscall
#
# $t0 = n
# $t1 = modifier
# $t2 = modded value
sort:
    sub $t0, $a2, $a1       # n = end - start 
    addi $t0, $t0, 1        # n = n + 1
    
    li $t8, 2

    div $t0, $t8            # modifier = n / 2
    mflo $t1                # modifier

sortForLoop1:
    ble $t1, $zero, return  # if modifier <= 0
    
    div $t1, $t8            # modifier % 2
    mfhi $t2               
    bne $t2, $zero, sortForLoop2setup
    addi $t1, $t1, 1

sortForLoop2setup:
    move $t3, $a1              # current = start
    add $t4, $a1, $t1          # start + modifier 

sortForLoop2:
    bge $t3, $t4, sortForLoopEscape # current >= start + modifier
    
    move $a1, $t3
    move $a3, $t1

    jal insert
    addi $t3, $t3, 1
    j sortForLoop2

sortForLoopEscape:
    div $t1, $t8            # modifer /= 2
    mflo $t1
    j sortForLoop1

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
    beq $t1, $zero, ret     # return if current < end
    
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
ret:
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
