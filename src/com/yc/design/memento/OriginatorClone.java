package com.yc.design.memento;

/**
 * 发起者同时承担备忘录角色、备忘录管理角色
 */
public class OriginatorClone implements Cloneable {
    private OriginatorClone backup;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void changeState(String state) {
        this.state = state;
    }

    public void createOriginatorClone() {
        this.backup = this.clone();
    }

    public void restoreOriginatorClone() {
        this.setState(this.backup.getState());
    }

    @Override
    public OriginatorClone clone() {
        try {
            return (OriginatorClone) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class ClientClone {
    public static void main(String[] args) {
        OriginatorClone originatorClone = new OriginatorClone();
        originatorClone.setState("初始状态。。。");
        System.out.println(originatorClone.getState());

        // 创建备忘录
        originatorClone.createOriginatorClone();

        originatorClone.changeState("改变状态。。。");
        System.out.println(originatorClone.getState());

        // 恢复备忘录
        originatorClone.restoreOriginatorClone();
        System.out.println(originatorClone.getState());

    }
}
