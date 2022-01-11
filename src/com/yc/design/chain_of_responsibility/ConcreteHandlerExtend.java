package com.yc.design.chain_of_responsibility;

import java.util.Objects;

abstract class HandlerExtend {
    private HandlerExtend nextHandlerExtend;
    private static int curCountHandlerExtend = 1;
    private static int maxCountHandlerExtend;

    public final Response handlerMessage(Request request) {
        Response response = null;
        if (this.handlerLevel().equals(request.requestLevel())) {
            response = this.echo(request);
        } else {
            if (Objects.nonNull(nextHandlerExtend)) {
                response = this.nextHandlerExtend.handlerMessage(request);
            } else {
                System.out.println("没有适当的处理者");
            }
        }
        return response;
    }

    public void setNext(HandlerExtend handler) {
       setNext(handler, false);
    }

    public void setNext(HandlerExtend handler, boolean isThrow) {
        if (++curCountHandlerExtend > maxCountHandlerExtend) {
            System.err.println("责任链长度超过最大允许长度！！！");
            if (isThrow) {
                throw new IllegalStateException("责任链长度超过最大允许长度！！！");
            }
        } else {
            this.nextHandlerExtend = handler;
        }
    }

    public static void setMaxCountHandlerExtend(int maxCountHandlerExtend) {
        HandlerExtend.maxCountHandlerExtend = maxCountHandlerExtend;
    }

    protected abstract Level handlerLevel();

    protected abstract Response echo(Request request);
}

public class ConcreteHandlerExtend extends HandlerExtend {
    @Override
    protected Level handlerLevel() {
        return new Level(0);
    }

    @Override
    protected Response echo(Request request) {
        System.out.print("ConcreteHandlerExtend 执行。。。 \t");
        return new Response(request.getMsg());
    }
}

class ConcreteHandlerExtend1 extends HandlerExtend {

    @Override
    protected Level handlerLevel() {
        return new Level(1);
    }

    @Override
    protected Response echo(Request request) {
        System.out.print("ConcreteHandlerExtend1 执行。。。 \t");
        return new Response(request.getMsg());
    }
}

class ConcreteHandlerExtend2 extends HandlerExtend {

    @Override
    protected Level handlerLevel() {
        return new Level(2);
    }

    @Override
    protected Response echo(Request request) {
        System.out.print("ConcreteHandlerExtend2 执行。。。 \t");
        return new Response(request.getMsg());
    }
}

class ClientExtend {
    public static void main(String[] args) {
        Request request = new Request(new Level(0), "requestExtend");
        HandlerExtend.setMaxCountHandlerExtend(3);
        // 链中顺序 1->2->3
        HandlerExtend handlerExtend = new ConcreteHandlerExtend();
        HandlerExtend handlerExtend1 = new ConcreteHandlerExtend1();
        HandlerExtend handlerExtend2 = new ConcreteHandlerExtend1();
        handlerExtend.setNext(handlerExtend1);
        handlerExtend1.setNext(handlerExtend2);
        System.out.println(handlerExtend.handlerMessage(request));
        System.out.println("==================================================");

        Request request1 = new Request(new Level(1), "requestExtend1");
        System.out.println(handlerExtend.handlerMessage(request1));
        System.out.println("===================================================");

        Request request2 = new Request(new Level(2), "requestExtend2");
        System.out.println(handlerExtend.handlerMessage(request2));
        System.out.println("===================================================");

        Request request3 = new Request(new Level(3), "requestExtend3");
        System.out.println(handlerExtend.handlerMessage(request3));
    }
}
