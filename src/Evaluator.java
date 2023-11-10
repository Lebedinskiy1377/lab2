import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * Класс для вычисления выражений.
 */
public class Evaluator {

    /**
     * Главный метод.
     *
     * @param args Аргументы.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String expression = scanner.nextLine();

        try {
            Map<String, Integer> variables = new HashMap<>();
            int result = evaluateExpression(expression, variables);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Метод для вычисления выражения.
     *
     * @param expression выражение в виде строки.
     * @param variables  хэшмапа переменных и их значений.
     * @return Результат вычисления.
     */
    public static int evaluateExpression(String expression, Map<String, Integer> variables) {
        char[] tokens = expression.toCharArray();
        return evaluate(tokens, variables);
    }
    /**
     * Метод для вычисления выражения из токенов.
     *
     * @param tokens    список символов представляющих выражение.
     * @param variables хэшмап переменных и их значений.
     * @return Результат вычисления.
     */
    private static int evaluate(char[] tokens, Map<String, Integer> variables) {
        Stack<Integer> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ') {
                continue;
            }

            if (Character.isDigit(tokens[i]) || tokens[i] == '-') {
                StringBuilder sb = new StringBuilder();
                while (i < tokens.length && (Character.isDigit(tokens[i]) || tokens[i] == '-')) {
                    sb.append(tokens[i++]);
                }
                i--;
                values.push(Integer.parseInt(sb.toString()));
            } else if (Character.isLetter(tokens[i])) {
                StringBuilder variableName = new StringBuilder();
                while (i < tokens.length && Character.isLetter(tokens[i])) {
                    variableName.append(tokens[i++]);
                }
                i--;

                if (variables.containsKey(variableName.toString())) {
                    values.push(variables.get(variableName.toString()));
                } else {
                    // Запрос значения переменной у пользователя
                    System.out.print("Введите значение для переменной " + variableName + ": ");
                    Scanner scanner = new Scanner(System.in);
                    int value = scanner.nextInt();
                    variables.put(variableName.toString(), value);
                    values.push(value);
                }
            } else if (tokens[i] == '(') {
                ops.push(tokens[i]);
            } else if (tokens[i] == ')') {
                while (ops.peek() != '(') {
                    values.push(applyOperation(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
            } else if (isOperator(tokens[i])) {
                while (!ops.isEmpty() && hasPrecedence(tokens[i], ops.peek())) {
                    values.push(applyOperation(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(tokens[i]);
            }
        }

        while (!ops.isEmpty()) {
            values.push(applyOperation(ops.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    /**
     * Проверяет, имеет ли оператор 1 более высокий приоритет чем оператор 2.
     *
     * @param op1 Первый оператор.
     * @param op2 Второй оператор.
     * @return true, если оператор1 имеет более высокий приоритет, иначе - false.
     */
    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    /**
     * Метод для применения операции.
     *
     * @param operator Оператор (+, -, *, /).
     * @param b        Второй операнд.
     * @param a        Первый операнд.
     * @return Результат применения операции.
     * @throws ArithmeticException бросаем исключение при делении на ноль.
     */
    private static int applyOperation(char operator, int b, int a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Деление на ноль запрещено");
                }
                return a / b;
        }
        return 0;
    }
}
