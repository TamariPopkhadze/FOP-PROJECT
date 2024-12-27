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
            } else if (line.contains("=")) {
                handleAssignment(line);
            }
            i++;
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
        String[] parts = content.split(",");
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

        // Sample BASIC-like code
        String program = """
                dim num$ as string
                num$ = "12345"
                dim reverse$ as string
                reverse$ = ""
                FOR i = LEN(num$) TO 1 STEP -1
                    reverse$ = reverse$ + MID$(num$, i, 1)
                NEXT i
                PRINT "Reversed number: "; reverse$
                """;

        interpreter.eval(program);  // Execute the program
    }
}
