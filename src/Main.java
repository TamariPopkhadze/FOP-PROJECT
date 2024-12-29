import java.util.HashMap;
import java.util.Map;

public class Main {
    private final Map<String, Integer> intVariables = new HashMap<>();
    private final Map<String, String> stringVariables = new HashMap<>();

    public void eval(String code) {
        String[] lines = code.split("\n");
        int i = 0;
        while (i < lines.length) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                i++;
                continue;
            }
<<<<<<< HEAD
=======

>>>>>>> d398e4162251399812223bb2caf17c7cfca71b1d
            if (line.startsWith("FOR")) {
                i = handleFor(line, lines, i);
            } else if (line.startsWith("WHILE")) {
                i = handleWhile(line, lines, i);
            } else if (line.startsWith("PRINT")) {
                handlePrint(line);
            } else if (line.startsWith("dim")) {
                handleDim(line);
            } else if (line.startsWith("IF")) {
                i = handleIf(line, lines, i);
            } else if (line.contains("=")) {
                handleAssignment(line);
            }
            i++;
        }
    }

    private void handleDim(String line) {
        String[] parts = line.split(" as ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid dim statement: " + line);
        }
        String varName = parts[0].replace("dim", "").trim();
        String varType = parts[1].trim();
        if ("integer".equals(varType)) {
            intVariables.put(varName, 0);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + varType);
        }
    }

    private void handleAssignment(String line) {
        String[] parts = line.split("=");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid assignment: " + line);
        }
        String varName = parts[0].trim();
        String value = parts[1].trim();
        if (value.startsWith("\"") && value.endsWith("\"")) {
            stringVariables.put(varName, value.substring(1, value.length() - 1));
        } else {
            int intValue = evaluateExpression(value);
            intVariables.put(varName, intValue);
        }
    }

    private int evaluateExpression(String expression) {
        String[] tokens = expression.split("\\s+");
        int result = resolveValue(tokens[0]);
        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            int operand = resolveValue(tokens[i + 1]);
<<<<<<< HEAD
            switch (operator.toUpperCase()) { // Case-insensitive for "MOD"
                case "+" -> result += operand;
                case "-" -> result -= operand;
                case "*" -> result *= operand;
                case "/" -> result /= operand;
                case "MOD", "%" -> result %= operand;
                default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
=======
            switch (operator) {
                case "+":
                    result += operand;
                    break;
                case "-":
                    result -= operand;
                    break;
                case "*":
                    result *= operand;
                    break;
                case "/":
                    result /= operand;
                    break;
                case "%":
                    result %= operand;
                    break;
                case "MOD":
                    result %= operand;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
>>>>>>> d398e4162251399812223bb2caf17c7cfca71b1d
            }
        }
        return result;
    }

    private int resolveValue(String token) {
        if (intVariables.containsKey(token)) {
            return intVariables.get(token);
        } else {
            try {
                return Integer.parseInt(token);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid value: " + token);
            }
        }
    }

    private void handlePrint(String line) {
        String content = line.substring(5).trim();
        String[] parts = content.split(";");
        StringBuilder output = new StringBuilder();
        for (String part : parts) {
            part = part.trim();
            if (stringVariables.containsKey(part)) {
                output.append(stringVariables.get(part)).append(" ");
            } else if (intVariables.containsKey(part)) {
                output.append(intVariables.get(part)).append(" ");
            } else {
                output.append(part).append(" ");
            }
        }
        System.out.println(output.toString().trim());
    }

    private int handleFor(String line, String[] lines, int index) {
        String[] parts = line.split(" ");
        if (parts.length != 6 || !"TO".equals(parts[4])) {
            throw new IllegalArgumentException("Invalid FOR loop syntax: " + line);
        }

        String varName = parts[1];
        int start = evaluateExpression(parts[3]);
        int end = evaluateExpression(parts[5]);
        intVariables.put(varName, start);

        int loopStart = index + 1;
        while (intVariables.get(varName) <= end) {
            int i = loopStart;

            // This loop will now iterate through the loop body, executing each line
            while (i < lines.length && !lines[i].trim().equals("NEXT")) {
                String innerLine = lines[i].trim();
                if (!innerLine.isEmpty()) {
                    eval(innerLine); // Execute inner line
                }
                i++;
            }

            intVariables.put(varName, intVariables.get(varName) + 1);
        }

        while (index < lines.length && !lines[index].trim().equals("NEXT")) {
            index++;
        }

        return index;
    }





    private int handleWhile(String line, String[] lines, int index) {
        String condition = line.substring(6).trim();
        int startIndex = index + 1;
        while (evaluateCondition(condition)) {
            int i = startIndex;
            while (i < lines.length && !lines[i].trim().equals("WEND")) {
                eval(lines[i].trim());
                i++;
            }
        }
        while (index < lines.length && !lines[index].trim().equals("WEND")) {
            index++;
        }
        return index;
    }

    private boolean evaluateCondition(String condition) {
        String[] parts = condition.split(" ");
        int left = resolveValue(parts[0]);
        int right = resolveValue(parts[2]);
<<<<<<< HEAD
        return switch (parts[1].toUpperCase()) {
            case "!=" -> left != right;
            case ">" -> left > right;
            case "<" -> left < right;
            case ">=" -> left >= right;
            case "<=" -> left <= right;
            case "==" -> left == right;
            case "MOD" -> left % right == 0;
            default -> throw new IllegalArgumentException("Unsupported operator: " + parts[1]);
        };
=======
        switch (parts[1]) {
            case "!=":
                return left != right;
            case ">":
                return left > right;
            case "<":
                return left < right;
            case ">=":
                return left >= right;
            case "<=":
                return left <= right;
            case "==":
                return left == right;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + parts[1]);
        }
>>>>>>> d398e4162251399812223bb2caf17c7cfca71b1d
    }


    private int handleIf(String line, String[] lines, int index) {
        String condition = line.substring(2, line.indexOf("THEN")).trim();
        boolean conditionResult = evaluateCondition(condition);
        if (conditionResult) {
            int i = index + 1;
            while (i < lines.length && !lines[i].trim().equals("ELSE") && !lines[i].trim().equals("ENDIF")) {
                eval(lines[i].trim());
                i++;
            }
            while (i < lines.length && !lines[i].trim().equals("ENDIF")) {
                i++;
            }
            return i;
        } else {
            int i = index + 1;
            while (i < lines.length && !lines[i].trim().equals("ELSE") && !lines[i].trim().equals("ENDIF")) {
                i++;
            }
            if (i < lines.length && lines[i].trim().equals("ELSE")) {
                i++;
                while (i < lines.length && !lines[i].trim().equals("ENDIF")) {
                    eval(lines[i].trim());
                    i++;
                }
            }
            return i;
        }
    }

    public static void main(String[] args) {
        Main interpreter = new Main();
<<<<<<< HEAD

        String program = """
                    dim sum as integer
                    sum = 0
                    dim n as integer
                    n = 10
                    FOR i = 1 TO n
                        sum = sum + i
                    NEXT
                    PRINT "Sum of first "; n; " numbers is: "; sum
                """;
        interpreter.eval(program);

        String program2 = """
                    dim fact as integer
                    fact = 1
                    dim n as integer
                    n = 5
                    FOR i = 1 TO n
                        fact = fact * i
                    NEXT
                    PRINT "Factorial of "; n; " is: "; fact
                """;
        interpreter.eval(program2);

        String program3 = """
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
                """;
        interpreter.eval(program3);


        String program4 = """
                    dim num as integer
                    dim original as integer
                    dim reversed as integer
                    dim digit as integer
                    num = 12321  
                    original = num
                    reversed = 0
                
                    WHILE num != 0
                        digit = num MOD 10
                        reversed = reversed * 10 + digit
                        num = num / 10
                    WEND
                
                    IF original == reversed THEN
                        PRINT original; " is a palindrome."
                    ELSE
                        PRINT original; " is not a palindrome."
                    ENDIF
                """;
        interpreter.eval(program4);


        //Example: Sum of digits
        String program5 = """
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
                """;
        interpreter.eval(program5);
        //Reversing a number
        String program6 = """
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
                """;
        interpreter.eval(program6);
        //Example: Finding the largest digit in a number

        String program7 = """
                    dim N as integer
                    dim LARGEST as integer
                    dim DIGIT as integer
                    N = 457839
                    LARGEST = 0
                    WHILE N != 0
                        DIGIT = N MOD 10
                        IF DIGIT > LARGEST THEN
                            LARGEST = DIGIT
                        ENDIF
                        N = N / 10
                    WEND
                    PRINT "The largest digit is: "; LARGEST
                """;
        interpreter.eval(program7);

        String program8 = """
                    dim num as integer
                    dim isPrime as integer
                    dim i as integer
                    dim limit as integer
                    num = 29 
                    isPrime = 1
                    limit = num / 2
                    IF num <= 1 THEN
                        isPrime = 0 
                    ELSE
                        FOR i = 2 TO limit
                            IF num MOD i == 0 THEN
                                isPrime = 0
                                EXIT
                            ENDIF
                        NEXT
                    ENDIF
                
                    IF isPrime == 1 THEN
                        PRINT num; " is a prime number."
                    ELSE
                        PRINT num; " is not a prime number."
                    ENDIF
                """;
        interpreter.eval(program8);

        String program9 = """
                    dim num as integer
                    dim i as integer
                    dim result as integer
                    num = 5
                    PRINT "Multiplication Table for "; num
                    FOR i = 1 TO 10
                        result = num * i
                        PRINT num; " * "; i; " = "; result
                    NEXT
                """;
        interpreter.eval(program9);


        String program10 = """
                      String program10= ""
                           dim N as integer
                           dim a as integer
                           dim b as integer
                           dim temp as integer
                           a = 0
                           b = 1
                           N = 10 
                           FOR i = 2 TO N
                               temp = a + b
                               a = b
                               b = temp
                           NEXT
                           PRINT "The "; N; "th Fibonacci number is: "; b
                     
                      
               """;
        interpreter.eval(program10);

    }
=======
    
        // Example: Sum of First N Numbers
        String program1 = 
            "dim sum as integer\n" +
            "sum = 0\n" +
            "dim n as integer\n" +
            "n = 10\n" +
            "FOR i = 1 TO n\n" +
            "    sum = sum + i\n" +
            "NEXT\n" +
            "PRINT \"Sum of first \"; n; \" numbers is: \"; sum\n";
    
        interpreter.eval(program1);
    
            // Example: Sum of First N Numbers
            String program2 = 
                "dim sum as integer\n" +
                "sum = 0\n" +
                "dim n as integer\n" +
                "n = 10\n" +
                "FOR i = 1 TO n\n" +
                "    sum = sum + i\n" +
                "NEXT\n" +
                "PRINT \"Sum of first \"; n; \" numbers is: \"; sum\n";
        
            interpreter.eval(program2);
        

        // Example: Palindrome Check
    String program3 = 
    "dim num as integer\n" +
    "dim reversed as integer\n" +
    "dim original as integer\n" +
    "num = 12321\n" +
    "original = num\n" +
    "reversed = 0\n" +
    "WHILE num != 0\n" +
    "    reversed = reversed * 10 + num MOD 10\n" +
    "    num = num / 10\n" +
    "WEND\n" +
    "IF original == reversed THEN\n" +
    "    PRINT \"Number is a palindrome\"\n" +
    "ELSE\n" +
    "    PRINT \"Number is not a palindrome\"\n" +
    "END IF\n";

interpreter.eval(program3);
}

// Example: Sum of digits
String program5 = 
    "dim N as integer\n" +
    "dim SUM as integer\n" +
    "dim DIGIT as integer\n" +
    "N = 1234\n" +
    "SUM = 0\n" +
    "WHILE N != 0\n" +
    "    DIGIT = N MOD 10\n" +
    "    SUM = SUM + DIGIT\n" +
    "    N = N / 10\n" +
    "WEND\n" +
    "PRINT \"The sum of the digits is: \"; SUM\n";

interpreter.eval(program5);

// Reversing a number
String program6 = 
    "dim N as integer\n" +
    "dim REVERSED as integer\n" +
    "dim DIGIT as integer\n" +
    "N = 456789\n" +
    "REVERSED = 0\n" +
    "WHILE N != 0\n" +
    "    DIGIT = N MOD 10\n" +
    "    REVERSED = REVERSED * 10 + DIGIT\n" +
    "    N = N / 10\n" +
    "WEND\n" +
    "PRINT \"The reversed number is: \"; REVERSED\n";
    interpreter.eval(program6);

    //Example: Finding the largest digit in a number 
    
        // Example: Finding the largest digit in a number
    String program7 = 
    "dim N as integer\n" +
    "dim LARGEST as integer\n" +
    "dim DIGIT as integer\n" +
    "N = 457839\n" +
    "LARGEST = 0\n" +
    "WHILE N != 0\n" +
    "    DIGIT = N MOD 10\n" +
    "    IF DIGIT > LARGEST THEN\n" +
    "        LARGEST = DIGIT\n" +
    "    ENDIF\n" +
    "    N = N / 10\n" +
    "WEND\n" +
    "PRINT \"The largest digit is: \"; LARGEST\n";

interpreter.eval(program7);
}
>>>>>>> d398e4162251399812223bb2caf17c7cfca71b1d
}