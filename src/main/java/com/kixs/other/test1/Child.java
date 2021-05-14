package com.kixs.other.test1;

/**
 * TODO 功能描述
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/5/12 14:47
 */
public class Child extends Parent {
    static {
        System.out.print("B");
    }
    {
        System.out.print("b");
    }

    Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Child () {
        System.out.print("2");
        defaultInt();
        name = "child";
    }

    public static void main(String[] args) {
        Child child = new Child();
        //Child child1 = new Child();
        // child.defaultInt();
        Integer age = 5;
        child.setAge(age);
        System.out.println(child.getAge());
        change(child);
        System.out.println(child.getAge());
    }

    public static void change(Child child) {
        child = new Child();
        child.setAge(6);
        System.out.println(child.getAge());
    }
    public static void change(Integer age) {
        age = 6;
        System.out.println(age);
    }

}
