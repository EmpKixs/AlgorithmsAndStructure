package com.kixs.other.test1;

/**
 * TODO 功能描述
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/5/13 9:41
 */
public interface InterfaceB {
    default void echo() {
        System.out.println("InterfaceB.echo");
    }
}
