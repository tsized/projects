##
# A MIPS program of shell sort.
# 
# @author Tim Sizemore
# @author Bill Franklin
# @version 5 April, 2013
##
.data
list1Before: .asciiz "list before: "
list1After: .asciiz "list after : "
list2Before: .asciiz "list2 before: "
list2After: .asciiz "list2 after : "
nline: .asciiz "\n"
space: .asciiz " "

.text

# $t0 = temp memory for array values
# $t1 = starting address of the first array
# $t2 = starting address of the second array
# $t3 = size
main:
    addi $sp, $sp, -16  # allocate space for registers
    sw $t0, 0($sp)      
    sw $t1, 4($sp)      
    sw $t2, 8($sp)
    sw $t3, 12($sp)     

    li $t3, 20          # int size = 20
    addi $sp, $sp, -160 # make space for both arrays
    move $t1, $sp       # start address of the array1

    li $t0, 4           # save to the stack for all the numbers
    sw $t0, 0($sp)      
    li $t0, 5
    sw $t0, 4($sp)      
    li $t0, 7
    sw $t0, 8($sp)      
    li $t0, 8
    sw $t0, 12($sp)     
    li $t0, 5
    sw $t0, 16($sp)     
    li $t0, 9
    sw $t0, 20($sp)     
    li $t0, 1
    sw $t0, 24($sp)     
    li $t0, 3
    sw $t0, 28($sp)     
    li $t0, 6
    sw $t0, 32($sp)     
    li $t0, 9
    sw $t0, 36($sp)     
    li $t0, 1
    sw $t0, 40($sp)     
    li $t0, 87
    sw $t0, 44($sp)     
    li $t0, 15
    sw $t0, 48($sp)     
    li $t0, -2
    sw $t0, 52($sp)     
    li $t0, 0
    sw $t0, 56($sp)   
    li $t0, 0
    sw $t0, 60($sp)   
    li $t0, 0
    sw $t0, 64($sp)   
    li $t0, 0
    sw $t0, 68($sp)   
    li $t0, 0
    sw $t0, 72($sp)   
    li $t0, 0
    sw $t0, 76($sp)   

    move $t2, $sp               # save the start of second array

    li $t0, 4                   # save array2 to the stack             
    sw $t0, 80($sp)                                           
    li $t0, 5                                                
    sw $t0, 84($sp)                                           
    li $t0, 7                                                
    sw $t0, 88($sp)                                           
    li $t0, 8                                                
    sw $t0, 92($sp)                                           
    li $t0, 5                                                
    sw $t0, 96($sp)                                           
    li $t0, 9                                                
    sw $t0, 100($sp)                                          
    li $t0, 1                                                
    sw $t0, 104($sp)                                          
    li $t0, 3                                                
    sw $t0, 108($sp)                                          
    li $t0, 6                                                
    sw $t0, 112($sp)                                          
    li $t0, 9                                                
    sw $t0, 116($sp)                                          
    li $t0, 1                                                
    sw $t0, 120($sp)                                          
    li $t0, 87                                               
    sw $t0, 124($sp)                                          
    li $t0, 15                                               
    sw $t0, 128($sp)                                          
    li $t0, -2                                               
    sw $t0, 132($sp)          
    li $t0, 0
    sw $t0, 136($sp)      
    li $t0, 0
    sw $t0, 140($sp)      
    li $t0, 0
    sw $t0, 144($sp)      
    li $t0, 0
    sw $t0, 148($sp)      
    li $t0, 0
    sw $t0, 152($sp)      
    li $t0, 0
    sw $t0, 156($sp)           
    
    la $a0, list1Before     # print array1     
    move $a1, $t1       
    move $a2, $t3       
    jal printArray     
    
    move $a0, $t1           # sort array1
    li $a1, 0       
    li $a2, 10      
    jal shellSort  

    la $a0, list1After      #print sorted array1
    move $a1, $t1     
    move $a2, $t3     
    jal printArray    

    la $a0, nline              # new line
    li $v0, 4       
    syscall         

    la $a0, list2Before     # print array2
    move $a1, $t2       
    move $a2, $t3       
    jal printArray     
    
    move $a0, $t2           # sort array2
    li $a1, 0     
    li $a2, 15    
    jal shellSort 
    
    la $a0, list2After      # print sorted array2
    move $a1, $t2      
    move $a2, $t3      
    jal printArray     

    addi $sp, $sp, 160  # restore the stack pointer
    lw $t0, 0($sp)
    lw $t1, 4($sp)      # restore values 
    lw $t2, 8($sp)      
    lw $t3, 12($sp)    
    addi $sp, $sp, 16   # restore the stack pointer 

    li $v0, 10      
    syscall         

# $t0 = i
printArray:
    addi $sp, $sp, -4   # allocate space for register
    sw $t0, 0($sp)      # save to the stack

    li $t0, 0       
    li $v0, 4       
    syscall         

printLoop:
    bge $t0, $a2, exitLoop # if size >= i
    
    lw $a0, 0($a1)          # load  word to $a0            
    li $v0, 1               
    syscall                 
    addi $a1, $a1, 4        # add 4 to the address       
    addi $t0, $t0, 1        # add 1 to i       

    la $a0, space           # print a space
    li $v0, 4       
    syscall         

    j printLoop      

exitLoop:
    la $a0, nline       # print new line
    li $v0, 4      
    syscall        

    lw $t0, 0($sp)      # load $t0 to stack
    addi $sp, $sp, 4    # move stack pointer back

    jr $ra          

# $t0 = n
# $t1 = modifier
# $t2 = value of 2
# $t3 = current
# $t4 = copy of $a0
# $t5 = copy of $a1
# $t6 = copy of $a2
# $t7 = start + modefier, temp
shellSort:
    addi $sp, $sp, -36  # allocate space on the stack
    sw $t0, 0($sp)      # Save all registers 
    sw $t1, 4($sp)      
    sw $t2, 8($sp)      
    sw $t3, 12($sp)     
    sw $t4, 16($sp)
    sw $t5, 20($sp)     
    sw $t6, 24($sp)     
    sw $t7, 28($sp)     
    sw $ra, 32($sp)

    move $t4, $a0       # list[]
    move $t5, $a1       # start
    move $t6, $a2       # end

    sub $t0, $t6, $t5   # n = end - start 
    addi $t0, $t0, 1    # n = n + 1    
    li $t2, 2           # load 2 into $s2
    
    div $t0, $t2        # n / 2 
    mflo $t1            # get lo 

sortForLoop1:
    ble $t1, $zero, sortExitLoop1   # if modifier <= 0
    
    div $t1, $t2                # modifier / 2
    mfhi $t0                    # move hi $s0
    bne $t0, $zero, else        # modifier % 2 != 0)
    addi $t1, $t1, 1            # modifier ++

else:
    move $t3, $t5

sortForLoop2:
    add $t7, $t5, $t1           # start + modefier
    bge $t3, $t7, sortExitLoop2     # current >= start + modefier
    
    move $a0, $t4
    move $a1, $t3     
    move $a2, $t6
    move $a3, $t1    
    jal insert

    addi $t3, $t3, 1    # current ++
    j sortForLoop2

sortExitLoop2:
    div $t1, $t2
    mflo $t1        # modifier /= 2
    j sortForLoop1

sortExitLoop1:
    lw $t0, 0($sp)      # put values back 
    lw $t1, 4($sp)      
    lw $t2, 8($sp)
    lw $t3, 12($sp)
    lw $t4, 16($sp)
    lw $t5, 20($sp)     
    lw $t6, 24($sp)     
    lw $t7, 28($sp)    
    lw $ra, 32($sp)
    addi $sp, $sp, 36   # move the stack pointer back
    jr $ra          

# $t0 = current
# $t1 = current * 4 
# $t2 = copy of list address
# $t3 = copy of list address
# $t4 = 4 
# $t5 = element
# $t6 = index
# $t7 = temp memory for stack storage
insert:
    addi $sp $sp, -32   
    sw $t0, 0($sp)      
    sw $t1, 4($sp)      
    sw $t2, 8($sp)      
    sw $t3, 12($sp)
    sw $t4, 16($sp)
    sw $t5, 20($sp)
    sw $t6, 24($sp)         
    sw $t7, 28($sp)     
    
    move $t2, $a0       # copy list address to $t2
    move $t2, $a0       # copy list address to $t3
    li $t4, 4           
    add $t0, $a1, $a3   # current = start + offset

insertForLoop1:
    bge $t0, $a2, insertExitLoop1 # if current >= end 
    mul $t1, $t0, $t4       # current * 4
    add $t2, $a0, $t1       # list address copy add 4
    lw $t5, 0($t2)          # get element
    sub $t6, $t0, $a3       # current - offset 

insertForLoop2:
    bgt $a1, $t6, insertExitLoop2 # if index <= start
    mul $t1, $t6, $t4       # index * 4
    add $t3, $a0, $t1       # list address + $t1
    lw $t7, 0($t3)          # load word to $t7

    bge $t5, $t7, insertExitLoop2 # if element >= list[index]
    add $t1, $a3, $t6       # offset + index
    mul $t1, $t1, $t4       # 1 * 4 
    add $t1, $a0, $t1       # address of list
    sw $t7, 0($t1)          # save word at $t1

    sub $t6, $t6, $a3       # index = index - offset
    j insertForLoop2

insertExitLoop2:
    add $t1, $t6, $a3   # index + offset
    mul $t1, $t1, $t4   # $t1 * 4
    add $t1, $a0, $t1   # $t1 + address of list
    sw $t5, 0($t1)      # save word 

    add $t0, $t0, $a3   # element + offset
    j insertForLoop1         

insertExitLoop1:
    lw $t0, 0($sp)      # put stack back
    lw $t1, 4($sp)      
    lw $t2, 8($sp)
    lw $t3, 12($sp)
    lw $t4, 16($sp)
    lw $t5, 20($sp)
    lw $t6, 24($sp)
    lw $t7, 28($sp)     
    addi $sp, $sp, 32   # move stack pointer back
    jr $ra          
