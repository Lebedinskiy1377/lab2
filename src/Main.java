import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String expression = scanner.nextLine();

        try {
            Map<String, Integer> variables = new HashMap<>();
            int result = Evaluator.evaluateExpression(expression, variables);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: Неверное выражение");
        }
    }
}

