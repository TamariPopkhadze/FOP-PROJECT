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

            if (line.startsWith("FOR")) {
                i = handleFor(line, lines, i);
            } else if (line.startsWith("WHILE")) {
                i = handleWhile(line, lines, i);
            } else if (line.startsWith("PRINT")) {
                handlePrint(line);
            } else if (line.startsWith("dim")) {
                handleDim(line);
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
            while (i < lines.length && !lines[i].trim().equals("NEXT")) {
                eval(lines[i].trim());
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
    }

    public static void main(String[] args) {
        Main interpreter = new Main();
    
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
}