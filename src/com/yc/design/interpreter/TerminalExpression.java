package com.yc.design.interpreter;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
class Context {
    private Map<String, String> map = new HashMap<>();
}

abstract class Expression {
    public abstract String interpreter(Context context);
}

class NonTerminalExpression extends Expression {
    private final Expression expression1;
    private final Expression expression2;

    public NonTerminalExpression(Expression ... expressions) {
        this.expression1 = expressions[0];
        this.expression2 = expressions[1];
    }

    @Override
    public String interpreter(Context context) {
        return expression1.interpreter(context) + " " + expression2.interpreter(context);
    }
}

public class TerminalExpression extends Expression {
    private final String key;

    public TerminalExpression(String key) {
        this.key = key;
    }

    @Override
    public String interpreter(Context context) {
        return context.getMap().getOrDefault(this.key, this.key);
    }
}

class Direct {
    private final Expression expression;

    public Direct(String expStr) {
        String[] strings = expStr.split(" ");
        Stack<Expression> stack = new Stack<>();

        for (String str : strings) {
            // This {} is {} a {}, hello {}
            // todo This is a {}, hello {}
            if (str.contains("{}")) {
                Expression expression = null;
                Expression prevExpression = null;
                while (!stack.isEmpty()) {
                    if (stack.peek() instanceof TerminalExpression) {
                        prevExpression = stack.pop();
                    } else {
                        expression = new NonTerminalExpression(stack.pop(), prevExpression);
                    }
                }
                stack.push(new NonTerminalExpression(expression == null ? prevExpression : expression, new TerminalExpression("symbol")));
            } else {
                stack.push(new TerminalExpression(str));
            }
        }
        this.expression = stack.pop();
    }

    public String execute(Context context) {
        return this.expression.interpreter(context);
    }
}

class UniversalClient {
    public static void main(String[] args) {
        String expStr = expStr();
        Context context = new Context();
        context.setMap(getValueMap(expStr));

        Direct direct = new Direct(expStr);
        System.out.println(direct.execute(context));

    }

    private static String expStr() {
        return "This {} is {} a {}, hello {}";
    }

    private static Map<String, String> getValueMap(String expStr) {
        String[] strings = expStr.split(" ");
        Map<String, String> map = Arrays.stream(strings).collect(Collectors.toMap(key -> key, value -> value, (v1, v2) -> v2));
        map.put("symbol", "LR");
        return map;
    }
}
