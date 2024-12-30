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

Initialize a variable sum to 0 to store the cumulative sum.
Define a variable n to specify the range (e.g., n = 10).
Use a FOR loop that iterates from 1 to n.
On each iteration, add the loop variable i to sum.
After the loop ends, print the value of sum along with a descriptive message.

dim sum as integer
    sum = 0
    dim n as integer
    n = 10
    FOR i = 1 TO n
        sum = sum + i
    NEXT
    PRINT "Sum of first "; n; " numbers is: "; sum
-----
