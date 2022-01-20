package com.yc.design.adapter;

class AdapteeExtend {
    public void specificRequest() {
        System.out.println("AdapteeExtend specificRequest");
    }
}

class AdapteeExtend1 {
    public void specificRequest1() {
        System.out.println("AdapteeExtend1 specificRequest");
    }
}

/**
 * 通过组合解决不能多继承问题
 */
public class AdapterExtend implements Target {
    private final AdapteeExtend adapteeExtend;
    private final AdapteeExtend1 adapteeExtend1;

    public AdapterExtend(AdapteeExtend adapteeExtend, AdapteeExtend1 adapteeExtend1) {
        this.adapteeExtend = adapteeExtend;
        this.adapteeExtend1 = adapteeExtend1;
    }

    @Override
    public void request() {
        adapteeExtend.specificRequest();
        adapteeExtend1.specificRequest1();
    }
}

class ClientExtend {
    public static void main(String[] args) {
        Target target = new Target() {
            @Override
            public void request() {
                System.out.println("extend request");
            }
        };
        target.request();
        System.out.println("===========================");
        AdapteeExtend adapteeExtend = new AdapteeExtend();
        AdapteeExtend1 adapteeExtend1 = new AdapteeExtend1();
        Target target1 = new AdapterExtend(adapteeExtend, adapteeExtend1);
        target1.request();
    }
}
