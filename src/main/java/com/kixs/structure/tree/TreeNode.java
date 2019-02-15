package com.kixs.structure.tree;

/**
 * 树形节点定义
 *
 * @author wangbing
 * @version 1.0
 * @date 2019/2/15 14:09
 * Copyright: Copyright (c) 2019
 */
public class TreeNode<K extends Comparable<K>, V> implements Tree<K, V> {

    /**
     * 父节点
     */
    private TreeNode<K, V> parent;

    /**
     * 左子节点
     */
    private TreeNode<K, V> left;

    /**
     * 右子节点
     */
    private TreeNode<K, V> right;

    /**
     * 键
     */
    private K key;

    /**
     * 值
     */
    private V value;

    public TreeNode() {
    }

    public TreeNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public TreeNode(TreeNode<K, V> parent, K key, V value) {
        this.parent = parent;
        this.key = key;
        this.value = value;
    }

    public TreeNode(TreeNode<K, V> parent, TreeNode<K, V> left, TreeNode<K, V> right, K key, V value) {
        this.parent = parent;
        this.left = left;
        this.right = right;
        this.key = key;
        this.value = value;
    }

    @Override
    public TreeNode<K, V> getParent() {
        return parent;
    }

    @Override
    public TreeNode<K, V> getLeft() {
        return left;
    }

    @Override
    public TreeNode<K, V> getRight() {
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
        if (parent instanceof TreeNode) {
            this.parent = (TreeNode<K, V>) parent;
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
        if (left instanceof TreeNode) {
            this.left = (TreeNode<K, V>) left;
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
        if (right instanceof TreeNode) {
            this.right = (TreeNode<K, V>) right;
            return;
        }
        throw new IllegalArgumentException("右子树修改失败：illegal left " + right.getClass());
    }

    @Override
    public void changeValue(V value) {
        this.value = value;
    }
}
