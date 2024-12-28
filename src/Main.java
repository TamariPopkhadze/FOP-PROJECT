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
                case "+" -> result += operand;
                case "-" -> result -= operand;
                case "*" -> result *= operand;
                case "/" -> result /= operand;
                case "%" -> result %= operand;
                case "MOD" -> result %= operand;
                default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
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
        return switch (parts[1]) {
            case "!=" -> left != right;
            case ">" -> left > right;
            case "<" -> left < right;
            case ">=" -> left >= right;
            case "<=" -> left <= right;
            case "==" -> left == right;
            default -> throw new IllegalArgumentException("Unsupported operator: " + parts[1]);
        };
    }

    public static void main(String[] args) {
        Main interpreter = new Main();

        // Example: Sum of First N Numbers
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

        // Example: Factorial of N
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

        // Example: GCD of Two Numbers
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

        // Example: Palindrome Check
        String program4 = """
            dim num as integer
            dim reversed as integer
            dim original as integer
            dim remainder as integer
            num = 12321
            original = num
            reversed = 0
            WHILE num > 0
                remainder = num MOD 10
                reversed = reversed * 10 + remainder
                num = num / 10
            WEND
            IF original = reversed THEN
                PRINT original; " is a palindrome"
            ELSE
                PRINT original; " is not a palindrome"
            END IF
        """;
        interpreter.eval(program4);
        
        //Example: Sum of digits 
        String program5="""
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
        String program6="""
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

    }
}
