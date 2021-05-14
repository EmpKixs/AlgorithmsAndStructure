package com.kixs.other.test1;

import java.lang.reflect.InvocationTargetException;

/**
 * TODO 功能描述
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/5/12 14:46
 */
public class Parent {
    static {
        System.out.print("A");
    }

    {
        System.out.print("a");
    }

    String name;

    public Parent() {
        System.out.print("1");
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, CloneNotSupportedException {
        Parent parent1 = new Parent();
        Parent parent2 = Parent.class.newInstance();
        Parent parent3 = Parent.class.getConstructor().newInstance();
        Parent parent4 = parent1.clone();
    }

    int defaultInt() {
        return 4;
    }

    @Override
    protected Parent clone() {
        Parent parent = null;
        try {
            parent = (Parent) super.clone();
        } catch (CloneNotSupportedException e) {

        }
        return parent;
    }
}
