import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;


class EvaluatorTest {
    @org.junit.jupiter.api.Test
    void evaluateExpressionTest() {
        //Тест выражения без переменных
        assertEquals(20, Evaluator.evaluateExpression("(2 + 3) * 4", new HashMap<>()));

        //Тест выражения с переменными
        Map<String, Integer> variables = new HashMap<>();
        variables.put("a", 2);
        variables.put("b", 3);
        assertEquals(15, Evaluator.evaluateExpression("(a + b) * (1 + 2)", variables));

        //Дополнительные тесты
        assertEquals(1, Evaluator.evaluateExpression("1", variables));

        assertEquals(5, Evaluator.evaluateExpression("(5 + 12) / 17 + 4", variables));
    }
}