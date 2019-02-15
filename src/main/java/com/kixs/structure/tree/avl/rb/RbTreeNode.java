package com.kixs.structure.tree.avl.rb;

import com.kixs.structure.tree.Tree;

/**
 * 红黑树节点定义
 *
 * @author wangbing
 * @version 1.0
 * @date 2019/2/15 14:42
 * Copyright: Copyright (c) 2019
 */
public class RbTreeNode<K extends Comparable<K>, V> implements Tree<K, V> {

    /**
     * 红色
     */
    public final static boolean RED = true;

    /**
     * 黑色
     */
    public final static boolean BLACK = false;

    /**
     * 父节点
     */
    private RbTreeNode<K, V> parent;

    /**
     * 左子节点
     */
    private RbTreeNode<K, V> left;

    /**
     * 右子节点
     */
    private RbTreeNode<K, V> right;

    /**
     * 键
     */
    private K key;

    /**
     * 值
     */
    private V value;

    /**
     * 节点颜色
     */
    private boolean color;

    public RbTreeNode() {
        super();
    }

    public RbTreeNode(K key, V value) {
        this(null, key, value, BLACK);
    }

    public RbTreeNode(RbTreeNode<K, V> parent, K key, V value) {
        this(parent, key, value, RED);
    }

    private RbTreeNode(RbTreeNode<K, V> parent, K key, V value, boolean color) {
        this(parent, null, null, key, value, color);
    }

    private RbTreeNode(RbTreeNode<K, V> parent, RbTreeNode<K, V> left, RbTreeNode<K, V> right, K key, V value, boolean color) {
        this.parent = parent;
        this.left = left;
        this.right = right;
        this.key = key;
        this.value = value;
        this.color = color;
    }

    /**
     * 判断节点颜色是否为红色
     */
    public boolean isRed() {
        return RED == color;
    }

    /**
     * 判断节点颜色是否为黑色
     */
    public boolean isBlack() {
        return BLACK == color;
    }

    @Override
    public RbTreeNode<K, V> getParent() {
        return parent;
    }

    @Override
    public RbTreeNode<K, V> getLeft() {
        return left;
    }

    @Override
    public RbTreeNode<K, V> getRight() {
        return right;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public void changeParent(Tree<K, V> parent) {
        if (parent == null) {
            this.parent = null;
            return;
        }
        if (parent instanceof RbTreeNode) {
            this.parent = (RbTreeNode<K, V>) parent;
            return;
        }
        throw new IllegalArgumentException("父节点修改失败：illegal parent " + parent.getClass());
    }

    @Override
    public void changeLeft(Tree<K, V> left) {
        if (left == null) {
            this.left = null;
            return;
        }
        if (left instanceof RbTreeNode) {
            this.left = (RbTreeNode<K, V>) left;
            return;
        }
        throw new IllegalArgumentException("左子树修改失败：illegal left " + left.getClass());
    }

    @Override
    public void changeRight(Tree<K, V> right) {
        if (right == null) {
            this.right = null;
            return;
        }
        if (right instanceof RbTreeNode) {
            this.right = (RbTreeNode<K, V>) right;
            return;
        }
        throw new IllegalArgumentException("右子树修改失败：illegal left " + right.getClass());
    }

    @Override
    public void changeValue(V value) {
        this.value = value;
    }
}
