import java.util.HashMap;
import java.util.Map;

public class Main {
    private final Map<String, String> stringVariables = new HashMap<>();
    private final Map<String, Integer> integerVariables = new HashMap<>();

    public void eval(String code) {
        String[] lines = code.split("\\n");
        int i = 0;
        while (i < lines.length) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                i++;
                continue;
            }

            if (line.startsWith("FOR") && line.contains("TO")) {
                i = handleFor(line, lines, i);
            } else if (line.contains("=") && !line.startsWith("IF") && !line.startsWith("WHILE")) {
                handleAssignment(line);
            } else if (line.startsWith("PRINT")) {
                handlePrint(line);
            }
            i++;
        }
    }

    private void handleAssignment(String line) {
        String[] parts = line.split("=");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid assignment statement: " + line);
        }
        String varName = parts[0].trim();
        String expression = parts[1].trim();

        if (expression.startsWith("\"") && expression.endsWith("\"")) {
            // Handle string assignment
            stringVariables.put(varName, expression.substring(1, expression.length() - 1));
        } else {
            // Handle integer assignment
            int value = evaluateExpression(expression);
            integerVariables.put(varName, value);
        }
    }

    private int handleFor(String line, String[] lines, int index) {
        // Parsing the FOR loop
        String[] parts = line.split("\\s+");
        if (parts.length != 6 || !"TO".equalsIgnoreCase(parts[4])) {
            throw new IllegalArgumentException("Invalid FOR statement: " + line);
        }

        String varName = parts[1];
        int start = evaluateExpression(parts[3]); // Evaluating LEN(num$) or similar expressions
        int end = evaluateExpression(parts[5]);

        integerVariables.put(varName, start);
        int loopStart = index + 1;

        // Execute the loop
        while (integerVariables.get(varName) <= end) {
            int i = loopStart;
            while (i < lines.length && !lines[i].trim().startsWith("NEXT")) {
                eval(lines[i].trim());
                i++;
            }
            integerVariables.put(varName, integerVariables.get(varName) + 1);
        }

        while (index < lines.length && !lines[index].trim().startsWith("NEXT")) {
            index++;
        }
        return index;
    }

    private void handlePrint(String line) {
        String content = line.substring(line.indexOf(' ') + 1).trim();
        String[] parts = content.split(",");
        StringBuilder output = new StringBuilder();
        for (String part : parts) {
            part = part.trim();
            if (stringVariables.containsKey(part)) {
                output.append(stringVariables.get(part)).append(" ");
            } else if (integerVariables.containsKey(part)) {
                output.append(integerVariables.get(part)).append(" ");
            } else {
                output.append(part.replace("\"", "")).append(" ");
            }
        }
        System.out.println(output.toString().trim());
    }

    private int evaluateExpression(String expression) {
        // Handle LEN and other string-based expressions inside the FOR loop
        if (expression.startsWith("LEN(") && expression.endsWith(")")) {
            String varName = expression.substring(4, expression.length() - 1);
            if (stringVariables.containsKey(varName)) {
                return stringVariables.get(varName).length(); // LEN function for string variables
            } else {
                throw new IllegalArgumentException("Undefined string variable: " + varName);
            }
        }

        // Evaluate arithmetic expressions
        String[] tokens = expression.split("\\s+");
        int result = resolveValue(tokens[0]);
        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            int operand = resolveValue(tokens[i + 1]);
            result = switch (operator) {
                case "+" -> result + operand;
                case "-" -> result - operand;
                case "*" -> result * operand;
                case "/" -> result / operand;
                default -> throw new IllegalArgumentException("Invalid operator: " + operator);
            };
        }
        return result;
    }

    private int resolveValue(String token) {
        if (integerVariables.containsKey(token)) {
            return integerVariables.get(token);
        } else {
            try {
                return Integer.parseInt(token);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid value: " + token);
            }
        }
    }

    private String resolveString(String token) {
        if (stringVariables.containsKey(token)) {
            return stringVariables.get(token);
        }
        throw new IllegalArgumentException("Undefined string variable: " + token);
    }

    public static void main(String[] args) {
        Main interpreter = new Main();

        // Example BASIC-like program with string manipulation
        String program = """
                dim sum as integer
                      sum = 0
                      dim i as integer
                      dim n as integer
                      n = 10
                      FOR i = 1 TO n
                          sum = sum + i
                      NEXT
                      PRINT "Sum of first", n, "numbers is:", sum
        """;


        // Execute the program
        System.out.println("Program Output:");
        interpreter.eval(program);
    }
}
