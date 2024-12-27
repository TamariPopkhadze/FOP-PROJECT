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
            } else if (line.startsWith("PRINT")) {
                handlePrint(line);
            } else if (line.startsWith("dim")) {
                handleDim(line);  // Handle variable declarations
            } else if (line.contains("=")) {
                handleAssignment(line);
            }
            i++;
        }
    }

    private void handleDim(String line) {
        // Handle variable declarations (dim <var_name> as <type>)
        String[] parts = line.split(" as ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid dim statement: " + line);
        }
        String varName = parts[0].replace("dim", "").trim();
        String varType = parts[1].trim();

        if ("integer".equals(varType)) {
            intVariables.put(varName, 0);  // Initialize integer variable with a default value of 0
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

        // Handle integer assignment
        if (value.startsWith("\"") && value.endsWith("\"")) {
            // String assignment
            stringVariables.put(varName, value.substring(1, value.length() - 1));
        } else {
            // Integer assignment
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
                case "%" -> result %= operand;  // Adding support for modulo operator
                case "MOD" -> result %= operand; // Handle MOD as alias for %
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
        String content = line.substring(5).trim();  // Remove "PRINT"
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

        // Loop through the range
        while (intVariables.get(varName) <= end) {
            int i = loopStart;
            while (i < lines.length && !lines[i].trim().equals("NEXT")) {
                eval(lines[i].trim());  // Evaluate the inner line
                i++;
            }
            intVariables.put(varName, intVariables.get(varName) + 1);  // Increment the loop variable
        }

        while (index < lines.length && !lines[index].trim().equals("NEXT")) {
            index++;
        }

        return index;
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
        interpreter.eval(program);  // Execute the program

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

        // Example: GCD of Two Numbers (Using MOD for the GCD calculation)
        String program3 = """
            dim a as integer
            dim b as integer
            a = 56
            b = 98
            dim temp as integer
            WHILE b != 0
                temp = b
                b = a MOD b  // Use MOD for modulo operation
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
                remainder = num MOD 10  // Use MOD for modulo operation
                reversed = reversed * 10 + remainder
                num = num \\ 10
            WEND
            IF original = reversed THEN
                PRINT original; " is a palindrome"
            ELSE
                PRINT original; " is not a palindrome"
            END IF
        """;
        interpreter.eval(program4);
    }
}
