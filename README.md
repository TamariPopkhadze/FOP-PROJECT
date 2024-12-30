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




