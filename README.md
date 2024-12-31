Project: BASIC language interpreter
OVERVIEW-This project implements a simple interpreter for a subset of the BASIC programming language. The interpreter processes and executes code written in a BASIC-like syntax, supporting constructs such as variable declarations, loops (FOR, WHILE), conditionals (IF-THEN-ELSE), and PRINT statements. The goal of this project is to demonstrate the construction of a minimal yet functional interpreter capable of parsing and executing BASIC code in Java.


Features

-Variable Declaration: Using the dim keyword for declaring integer and string variables.

-Assignment: Assign values or evaluate expressions to assign to variables.

-Arithmetic and Logical Operations: Support for addition, subtraction, multiplication, division, modulo, and relational operators.

-Control Structures:

-FOR loops with support for numeric ranges.

-WHILE loops for condition-based iteration.

-IF-THEN-ELSE conditionals for branching logic.

-PRINT Statements: Output values, strings, or expressions to the console.

-Error Handling: Robust error handling for invalid syntax or unsupported operations.
Here's a detailed expansion of the algorithm descriptions, focusing on how each algorithm operates step by step:

1. Sum of First N Numbers
Algorithm:

-Initialize a variable sum to 0 to store the cumulative sum.

-Define a variable n to specify the range (e.g., n = 10).

-Use a FOR loop that iterates from 1 to n.

-On each iteration, add the loop variable i to sum.

-After the loop ends, print the value of sum along with a descriptive message.

-dim sum as integer
   sum = 0
    dim n as integer
     n = 10
    FOR i = 1 TO n
        sum = sum + i
    NEXT
    PRINT "Sum of first "; n; " numbers is: "; sum



2. Factorial Calculation
Algorithm:

Initialize a variable fact to 1 to store the product of numbers.
Set the input variable n to the desired number (e.g., n = 5).
Use a FOR loop that iterates from 1 to n.
On each iteration, multiply fact by the loop variable i and update fact.
After the loop completes, print the factorial result with a descriptive message.
Purpose: Illustrates multiplication and how loops can be used for repetitive computation.

dim fact as integer
    fact = 1
    dim n as integer
    n = 5
    FOR i = 1 TO n
        fact = fact * i
    NEXT
    PRINT "Factorial of "; n; " is: "; fact



3. Greatest Common Divisor (GCD)
Algorithm:

Define two integers a and b for which the GCD will be calculated.
While b is not zero:
Assign b to a temporary variable temp.
Update b to a MOD b (remainder when a is divided by b).
Set a to temp.
When the loop ends, a contains the GCD. Print the result.
Purpose: Demonstrates the Euclidean algorithm using arithmetic and WHILE loops.

dim a as integer
    dim b as integer
    a = 56
    b = 98
    dim temp as integer
    WHILE b != 0
        temp = b
        b = a MOD b
        a = temp
    WEND
    PRINT "GCD is: "; a



4. Check if a Number is Palindrome
Algorithm:

Defined four integers num, original, reverse, digit and need to determine if num is a palindrome.
Assign the input number to noth num and original.
Reverse the number using a WHILE loop, extract the las degit using MOD 10, append the digit to reversed, remove the last digit by dividing num bu 10.
Compare original and reversed.

dim num as integer
    dim original as interer
    dim reversed as integer
    dim digir as integer
    num = 12321
    original = num
    revwrsed = 0
    WHILE num != 0
        digit  = num MOD 10
        reverseg = reversed * 10 + digit
        num = num/10
    WEND
    IF original == reversed THEN
       PRINT original;"is a palindrome."
    ELSE original;"is not a palindrome."
    ENDIF
    PRINT original; " is not a palindrome."
    ENDIF

                

5. Sum of difits
Algorithm:

Define three integers N (input number), SUM (to store the sum of digits), and DIGIT (to hold the current digit). Initialize SUM to 0.
Extract each digit using a WHILE loop, extract the last digit using MOD 10, add the digit to SUM, remove the last digit by dividing N by 10.
Print the total sum of the digits

dim N as integer
    dim SUM as integer
    dim DIGIT as integer
    N = 1234
    SUM = 0
    WHILE N != 0
        DIGIT = N MOD 10
        SUM = SUM + DIGIT
        N = N / 10
    WEND
    PRINT "The sum of the digits is: "; SUM


6.Reverse a Number
Algorithm:

This program reverses a given integer.
Define integers: N (input number), REVERSED (to store the reversed number), and DIGIT (to hold the current digit during reversal). Initialize REVERSED to 0.
Reverse the number using a WHILE loop. Extract the last digit using MOD 10, append the digit to REVERSED, remove the last digit by dividing N by 10.

dim N as integer
dim REVERSED as integer
dim DIGIT as integer
N = 456789
REVERSED = 0
WHILE N != 0
    DIGIT = N MOD 10
    REVERSED = REVERSED * 10 + DIGIT
    N = N / 10
WEND
PRINT "The reversed number is: "; REVERSED


