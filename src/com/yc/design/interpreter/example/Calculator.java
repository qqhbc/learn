package com.yc.design.interpreter.example;

import java.util.*;

abstract class Expression {
    public abstract int interpreter(Map<String, Integer> map);
}

class VarExpression extends Expression {
    private final String key;

    public VarExpression(String key) {
        this.key = key;
    }

    @Override
    public int interpreter(Map<String, Integer> map) {
        return map.get(this.key);
    }
}

abstract class SymbolExpression extends Expression {
    protected Expression left;
    protected Expression right;

    public SymbolExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}

class AddExpression extends SymbolExpression {

    public AddExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpreter(Map<String, Integer> map) {
        return super.left.interpreter(map) + super.right.interpreter(map);
    }
}

class SubExpression extends SymbolExpression {

    public SubExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpreter(Map<String, Integer> map) {
        return super.left.interpreter(map) - super.right.interpreter(map);
    }
}

// todo '*' '/' 还未实现
public class Calculator {
    private final Expression expression;

    public Calculator(String expStr) {
        Stack<Expression> stack = new Stack<>();
        Expression left;
        Expression right;

        char[] chars = expStr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!Objects.equals(' ', chars[i])) {
                switch (chars[i]) {
                    case '+':
                        left = stack.pop();
                        // a + b -c
                        while (Objects.equals(' ', chars[i + 1])) {
                            i++;
                        }
                        right = new VarExpression(String.valueOf(chars[++i]));
                        stack.push(new AddExpression(left, right));
                        break;
                    case '-':
                        left = stack.pop();
                        while (Objects.equals(' ', chars[i + 1])) {
                            i++;
                        }
                        right = new VarExpression(String.valueOf(chars[++i]));
                        stack.push(new SubExpression(left, right));
                        break;
                    default:
                        stack.push(new VarExpression(String.valueOf(chars[i])));
                }
            }
        }
        this.expression = stack.pop();
    }

    public int run(Map<String, Integer> map) {
        return this.expression.interpreter(map);
    }
}

class Client {
    public static void main(String[] args) {
        String expStr = getExpStr();
        Calculator calculator = new Calculator(expStr);
        int run = calculator.run(getValueMap(expStr));
        System.out.println("运算结果: " + run);
    }

    private static String getExpStr() {
        return "a + b -c + d";
    }

    private static Map<String, Integer> getValueMap(String expStr) {
        String str = expStr.toLowerCase();
        System.out.println("表达式: " + str);
        Map<String, Integer> map = new HashMap<>();
        Random random = new Random();
        for (char c : str.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                int var = random.nextInt(9) + 1;
                System.out.println(c + " : " + var);
                map.put(String.valueOf(c), var);
            }
        }
        return map;
    }


}
