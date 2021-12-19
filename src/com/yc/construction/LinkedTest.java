package com.yc.construction;

import org.junit.Test;

import java.util.Objects;

public class LinkedTest {
    @Test
    public void addTest() {
        Linked<String> linked = new Linked<>();
        linked.add("hty");
        linked.add("ssl");
        linked.add("wpx");
        linked.add("lwj");
        assert Objects.equals("hty、ssl、wpx、lwj", linked.print());
    }

    @Test
    public void addIndexTest() {
        Linked<String> linked = new Linked<>();
        linked.add("hty");
        linked.add("ssl");
        linked.add("wpx");
        linked.add("lwj");
        linked.add(0, "pyw");
        assert Objects.equals("pyw、hty、ssl、wpx、lwj", linked.print());
        linked.remove("pyw");
        linked.add(4, "pyw");
        assert Objects.equals("hty、ssl、wpx、lwj、pyw", linked.print());
        linked.remove("pyw");
        linked.add(5, "pyw");
        assert Objects.equals("hty、ssl、wpx、lwj、pyw", linked.print());
        linked.remove("pyw");
        linked.add(2, "pyw");
        assert Objects.equals("hty、ssl、pyw、wpx、lwj", linked.print());
    }

    @Test
    public void removedTest() {
        Linked<String> linked = new Linked<>();
        linked.add("hty");
        linked.add("ssl");
        linked.add("wpx");
        linked.add("lwj");
        assert Objects.equals(true, linked.remove("ssl"));
        assert Objects.equals("hty、wpx、lwj", linked.print());
    }

    @Test
    public void removedIndex() {
        Linked<String> linked = new Linked<>();
        linked.add("hty");
        linked.add("ssl");
        linked.add("wpx");
        linked.add("lwj");
        String element = "hty";
        assert Objects.equals(element, linked.remove(0));
        linked.add(0, "hty");
        element = "ssl";
        assert Objects.equals(element, linked.remove(1));
        linked.add(1, element);
        element = "wpx";
        assert Objects.equals(element, linked.remove(2));
        linked.add(2, element);
        element = "lwj";
        assert Objects.equals(element, linked.remove(3));

    }

    @Test
    public void findTest() {
        Linked<String> linked = new Linked<>();
        linked.add("hty");
        linked.add("ssl");
        linked.add("wpx");
        linked.add("lwj");
        for (int i = 0; i < 4; i++) {
            assert Objects.nonNull(linked.find(i));
        }
    }

    @Test
    public void findFirstTest() {
        Linked<String> linked = new Linked<>();
        linked.add("hty");
        linked.add("ssl");
        linked.add("wpx");
        linked.add("lwj");
        assert Objects.equals(0, linked.findFirst("hty"));
        assert Objects.equals(1, linked.findFirst("ssl"));
        assert Objects.equals(2, linked.findFirst("wpx"));
        assert Objects.equals(3, linked.findFirst("lwj"));
    }
}
