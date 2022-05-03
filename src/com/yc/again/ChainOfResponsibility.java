package com.yc.again;

import com.alibaba.fastjson.JSON;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@Data
class Response {
    private final String result;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

@AllArgsConstructor
class Request {
    private final Level level;
    @Getter
    private final String msg;

    public Level requestLevel() {
        return this.level;
    }
}

@AllArgsConstructor
@EqualsAndHashCode
class Level {
    private final int rank;
}

abstract class Handler {
    @Setter
    private Handler nextHandler;

    protected final Response handlerMessage(Request request) {
        if (this.handlerLevel().equals(request.requestLevel())) {
            return this.echo(request);
        }
        if (Objects.nonNull(this.nextHandler)) {
            return this.nextHandler.handlerMessage(request);
        } else {
            System.out.println("不能处理。。。");
        }
        return null;
    }

    protected abstract Level handlerLevel();

    protected abstract Response echo(Request request);
}

class ConcreteHandler extends Handler{

    @Override
    protected Level handlerLevel() {
        return new Level(0);
    }

    @Override
    protected Response echo(Request request) {
        System.out.print("ConcreteHandler 执行。。。\t");
        return new Response(request.getMsg());
    }
}

class ConcreteHandler1 extends Handler{

    @Override
    protected Level handlerLevel() {
        return new Level(1);
    }

    @Override
    protected Response echo(Request request) {
        System.out.print("ConcreteHandler1 执行。。。\t");
        return new Response(request.getMsg());
    }
}

class ConcreteHandler2 extends Handler{

    @Override
    protected Level handlerLevel() {
        return new Level(2);
    }

    @Override
    protected Response echo(Request request) {
        System.out.print("ConcreteHandler2 执行。。。\t");
        return new Response(request.getMsg());
    }
}

class ConcreteHandler3 extends Handler{

    @Override
    protected Level handlerLevel() {
        return new Level(3);
    }

    @Override
    protected Response echo(Request request) {
        System.out.print("ConcreteHandler3 执行。。。\t");
        return new Response(request.getMsg());
    }
}

public class ChainOfResponsibility {
    public static void main(String[] args) {
        Handler handler = new ConcreteHandler();
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        Handler handler3 = new ConcreteHandler3();
        handler.setNextHandler(handler1);
        handler1.setNextHandler(handler2);
        handler2.setNextHandler(handler3);

        Request request = new Request(new Level(0), "request");
        Response response = handler.handlerMessage(request);
        System.out.println(response);
        System.out.println("==============================================");
        Request request3 = new Request(new Level(3), "request3");
        Response response3 = handler.handlerMessage(request3);
        System.out.println(response3);
        System.out.println("==============================================");
        Request request5 = new Request(new Level(5), "request5");
        Response response5 = handler.handlerMessage(request5);
        System.out.println(response5);
    }
}
