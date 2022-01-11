package com.yc.design.chain_of_responsibility;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@EqualsAndHashCode
@AllArgsConstructor
class Level {
    private final int rank;
}

@AllArgsConstructor
class Request {
    private final Level level;
    private final String msg;

    public Level requestLevel() {
        return this.level;
    }

    public String getMsg() {
        return msg;
    }
}

@AllArgsConstructor
@Data
class Response {
    private final String result;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

abstract class Handler {
    private Handler nextHandler;

    public final Response handlerMessage(Request request) {
        Response response = null;
        if (this.handlerLevel().equals(request.requestLevel())) {
            response = this.echo(request);
        } else {
            if (Objects.nonNull(nextHandler)) {
                response = this.nextHandler.handlerMessage(request);
            } else {
                System.out.println("没有适当的处理者");
            }
        }
        return response;
    }

    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }

    protected abstract Level handlerLevel();

    protected abstract Response echo(Request request);
}

public class ConcreteHandler extends Handler {
    @Override
    protected Level handlerLevel() {
        return new Level(0);
    }

    @Override
    protected Response echo(Request request) {
        System.out.print("ConcreteHandler 执行。。。 \t");
        return new Response(request.getMsg());
    }
}

class ConcreteHandler1 extends Handler {

    @Override
    protected Level handlerLevel() {
        return new Level(1);
    }

    @Override
    protected Response echo(Request request) {
        System.out.print("ConcreteHandler1 执行。。。 \t");
        return new Response(request.getMsg());
    }
}

class ConcreteHandler2 extends Handler {

    @Override
    protected Level handlerLevel() {
        return new Level(2);
    }

    @Override
    protected Response echo(Request request) {
        System.out.print("ConcreteHandler2 执行。。。 \t");
        return new Response(request.getMsg());
    }
}

class Client {
    public static void main(String[] args) {
        Request request = new Request(new Level(0), "request");
        // 链中顺序 1->2->3
        Handler handler = new ConcreteHandler();
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        handler.setNext(handler1);
        handler1.setNext(handler2);
        System.out.println(handler.handlerMessage(request));
        System.out.println("==================================================");

        Request request1 = new Request(new Level(1), "request1");
        System.out.println(handler.handlerMessage(request1));
        System.out.println("===================================================");

        Request request2 = new Request(new Level(2), "request2");
        System.out.println(handler.handlerMessage(request2));
        System.out.println("===================================================");

        Request request3 = new Request(new Level(3), "request3");
        System.out.println(handler.handlerMessage(request3));
    }
}
