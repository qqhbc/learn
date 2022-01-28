package com.yc.design.memento;

import lombok.Data;
import net.sf.cglib.beans.BeanCopier;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

class BeanUtils {
    private static final BeanCopier beanCopier = BeanCopier.create(MultiFieldOriginator.class, MultiFieldOriginator.class, false);

    public static MultiFieldOriginator backupBean(MultiFieldOriginator multiFieldOriginator) {
        MultiFieldOriginator multiFieldOriginator1 = new MultiFieldOriginator();
        beanCopier.copy(multiFieldOriginator, multiFieldOriginator1, null);
        return multiFieldOriginator1;
    }

    public static void restoreMultiFieldOriginator(MultiFieldOriginator multiFieldOriginator, MultiFieldMemento memento) {
        beanCopier.copy(memento.getOriginator(), multiFieldOriginator, null);
    }
}

@Data
public class MultiFieldOriginator {
    private String state1;
    private String state2;
    private String state3;

    public void changeState(String state1, String state2, String state3) {
        this.state1 = state1;
        this.state2 = state2;
        this.state3 = state3;
    }

    public MultiFieldMemento createMultiFieldMemento() {
        return new MultiFieldMemento(BeanUtils.backupBean(this));
    }

    public void restoreMultiFieldMemento(MultiFieldMemento memento) {
        BeanUtils.restoreMultiFieldOriginator(this, memento);
    }

    @Override
    public String toString() {
        return this.state1 + "\t" + this.state2 + "\t" + this.state3;
    }

}

@Data
class MultiFieldMemento {
    private MultiFieldOriginator originator;

    public MultiFieldMemento(MultiFieldOriginator multiFieldOriginator) {
        this.originator = multiFieldOriginator;
    }
}

class MultiFieldCaretaker {
    private final Map<String, MultiFieldMemento> map = new HashMap<>();

    public MultiFieldMemento getMultiFieldMemento(String idx) {
        return map.get(idx);
    }

    public void setMultiFieldMemento(String idx, MultiFieldMemento memento) {
        map.put(idx, memento);
    }
}

class MultiFieldClient {
    public static void main(String[] args) {
        MultiFieldOriginator multiFieldOriginator = new MultiFieldOriginator();
        multiFieldOriginator.setState1("初始化状态1。。。");
        multiFieldOriginator.setState2("初始化状态2。。。");
        multiFieldOriginator.setState3("初始化状态3。。。");
        System.out.println(multiFieldOriginator);

        MultiFieldCaretaker caretaker = new MultiFieldCaretaker();

        String intiIdx = LocalTime.now().toString();
        caretaker.setMultiFieldMemento(intiIdx, multiFieldOriginator.createMultiFieldMemento());

        System.out.println("================================================");
        multiFieldOriginator.changeState("变更状态1-1。。。", "变更状态1-2", "变更状态1-3");
        System.out.println(multiFieldOriginator);

        String changeIdx = LocalTime.now().toString();
        caretaker.setMultiFieldMemento(changeIdx, multiFieldOriginator.createMultiFieldMemento());

        multiFieldOriginator.changeState("变更状态2-1。。。", "变更状态2-2", "变更状态2-3");
        System.out.println("===================最新初始状态===================");
        System.out.println(multiFieldOriginator);

        System.out.println("===================恢复初始状态===================");
        multiFieldOriginator.restoreMultiFieldMemento(caretaker.getMultiFieldMemento(intiIdx));
        System.out.println(multiFieldOriginator);

        System.out.println("===================恢复变更状态===================");
        multiFieldOriginator.restoreMultiFieldMemento(caretaker.getMultiFieldMemento(changeIdx));
        System.out.println(multiFieldOriginator);



    }
}
