package com.kixs.other.test1;

/**
 * TODO 功能描述
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/5/13 9:16
 */
public class Person implements InterfaceA, InterfaceB {
    private String name;
    public Person(String name) {
        this.name = name;
    }
    @Override
    public void echo() {
        System.out.println("Hello，我是" + name);
    }
    public static void main(String[] args) {
        Person person = new Person("张三");
        person.echo();
        InterfaceA a = person;
        a.echo();
        InterfaceB b = person;
        b.echo();
    }
}
