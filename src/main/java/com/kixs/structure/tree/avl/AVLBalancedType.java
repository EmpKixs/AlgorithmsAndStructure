package com.kixs.structure.tree.avl;

/**
 * AVL平衡类型
 *
 * @author wangbing
 * @version 1.0
 * @date 2019/2/13 14:24
 * Copyright: Copyright (c) 2019
 */
public enum AVLBalancedType {

    /**
     * AVL平衡类型
     */
    HIGH_RIGHT(-2, "Right"),
    NORMAL_RIGHT(-1, "Balanced"),
    NORMAL_EQUAL(0, "Balanced"),
    NORMAL_LEFT(1, "Balanced"),
    HIGH_LEFT(2, "Left");

    private int code;

    private String desc;

    AVLBalancedType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AVLBalancedType getBalancedType(int code) {
        for (AVLBalancedType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalStateException("非法的AVL平衡类型code：" + code);
    }

    public boolean isBalanced() {
        return this.equals(NORMAL_EQUAL) || this.equals(NORMAL_LEFT) || this.equals(NORMAL_RIGHT);
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
