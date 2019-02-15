package com.kixs.structure.tree;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 树形接口定义
 *
 * @author wangbing
 * @version 1.0
 * @date 2019/2/15 15:15
 * Copyright: Copyright (c) 2019
 */
public interface Tree<K extends Comparable<K>, V> {

    /**
     * 获取父节点
     *
     * @return 父节点
     */
    Tree<K, V> getParent();

    /**
     * 修改父节点
     *
     * @param parent 新父节点
     */
    void changeParent(Tree<K, V> parent);

    /**
     * 获取左子树
     *
     * @return 左子树
     */
    Tree<K, V> getLeft();

    /**
     * 修改左子树
     *
     * @param left 新子树
     */
    void changeLeft(Tree<K, V> left);

    /**
     * 获取右子树
     *
     * @return 右子树
     */
    Tree<K, V> getRight();

    /**
     * 修改右子树
     *
     * @param right 新子树
     */
    void changeRight(Tree<K, V> right);

    /**
     * 获取键
     *
     * @return 键
     */
    K getKey();

    /**
     * 获取值
     *
     * @return 值
     */
    V getValue();

    /**
     * 修改节点值
     *
     * @param value 新值
     */
    void changeValue(V value);

    /**
     * 判断是否为叶子节点
     *
     * @return true|false
     */
    default boolean isLeaf() {
        return getLeft() == null && getRight() == null;
    }

    /**
     * 判断节点是否为单叶节点
     *
     * @return true|false
     */
    default boolean isSingleLeaf() {
        if (isLeaf()) {
            return false;
        }
        if (hasDoubleChild()) {
            return false;
        }
        return true;
    }

    /**
     * 判断节点是否为双子节点
     *
     * @return true|false
     */
    default boolean hasDoubleChild() {
        return getLeft() != null && getRight() != null;
    }

    /**
     * 获取所有键-默认中序遍历
     *
     * @return keys
     */
    default List<K> keys() {
        return inTraversal();
    }

    /**
     * 先序遍历
     *
     * @return keys
     */
    default List<K> preTraversal() {
        return preTraversal(Lists.newArrayList(), this);
    }

    /**
     * 中序遍历
     *
     * @return keys
     */
    default List<K> inTraversal() {
        return inTraversal(Lists.newArrayList(), this);
    }

    /**
     * 后序遍历
     *
     * @return keys
     */
    default List<K> postTraversal() {
        return postTraversal(Lists.newArrayList(), this);
    }

    /**
     * 先序遍历-根左右
     *
     * @param keyList 键列表
     * @param tree    树
     * @return keys
     */
    default List<K> preTraversal(List<K> keyList, Tree<K, V> tree) {
        if (tree == null) {
            return keyList;
        }
        keyList.add(tree.getKey());
        if (tree.getLeft() != null) {
            keyList = preTraversal(keyList, tree.getLeft());
        }
        if (tree.getRight() != null) {
            keyList = preTraversal(keyList, tree.getRight());
        }
        return keyList;
    }

    /**
     * 中序遍历-左根右
     *
     * @param keyList 键列表
     * @param tree    树
     * @return keys
     */
    default List<K> inTraversal(List<K> keyList, Tree<K, V> tree) {
        if (tree == null) {
            return keyList;
        }
        if (tree.getLeft() != null) {
            keyList = inTraversal(keyList, tree.getLeft());
        }
        keyList.add(tree.getKey());
        if (tree.getRight() != null) {
            keyList = inTraversal(keyList, tree.getRight());
        }
        return keyList;
    }

    /**
     * 后序遍历-左右根
     *
     * @param keyList 键列表
     * @param tree    树
     * @return keys
     */
    default List<K> postTraversal(List<K> keyList, Tree<K, V> tree) {
        if (tree == null) {
            return keyList;
        }
        if (tree.getLeft() != null) {
            keyList = postTraversal(keyList, tree.getLeft());
        }
        if (tree.getRight() != null) {
            keyList = postTraversal(keyList, tree.getRight());
        }
        keyList.add(tree.getKey());
        return keyList;
    }

    /**
     * 计算节点高度
     *
     * @return 节点高度
     */
    default int height() {
        int leftHeight = this.getLeft() == null ? 0 : this.getLeft().height();
        int rightHeight = this.getRight() == null ? 0 : this.getRight().height();
        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * 子树左旋
     *
     * @param root 根节点
     * @return 旋转后子树根节点
     */
    default Tree<K, V> leftRotation(Tree<K, V> root) {
        // 1、定位父节点P和左子节点left，P = X.parent，left = X.left；定位P的父节点PP，PP = P.parent；
        Tree<K, V> p = this.getParent();
        // x不能为根节点且x为P的右子节点
        if (p != null && this.equals(p.getRight())) {
            Tree<K, V> left = this.getLeft();
            Tree<K, V> pp = p.getParent();
            // 2、解除X与P和left的关系，X.parent = null，P.right = null，left.parent = null, X.left = null；
            // 解除P与PP的关系，P.parent = null;
            this.changeParent(null);
            p.changeRight(null);
            if (left != null) {
                left.changeParent(null);
            }
            this.changeLeft(null);
            // 3、建立P与left根右关系，P.right = left，left.parent = P;
            p.changeRight(left);
            if (left != null) {
                left.changeParent(p);
            }
            // 4、建立X与P的根左关系，X.left = P，P.parent = X;
            this.changeLeft(p);
            p.changeParent(this);
            // 5、建立X与PP的关系，X.parent = PP；
            // 根据P与PP的关系建立PP与X的关系：
            // 5.1、P为根节点，即PP == null，则更新根节点为X；
            // 5.2、P为PP的左子节点，即P == PP.left，则PP.left = X；
            // 5.3、P为PP的右子节点，即P == PP.right，则PP.right = X；
            this.changeParent(pp);
            if (pp == null) {
                root = this;
            } else if (p.equals(pp.getLeft())) {
                pp.changeLeft(this);
            } else {
                pp.changeRight(this);
            }
        }
        return this;
    }

    /**
     * 子树右旋
     *
     * @param root 根节点
     * @return 旋转后子树根节点
     */
    default Tree<K, V> rightRotation(Tree<K, V> root) {
        // 1、定位父节点P和左子节点right，P = X.parent，right = X.right；定位P的父节点PP，PP = P.parent；
        Tree<K, V> p = this.getParent();
        // x不能为根节点且x为P的左子节点
        if (p != null && this.equals(p.getLeft())) {
            Tree<K, V> right = this.getRight();
            Tree<K, V> pp = p.getParent();
            // 2、解除X与P和right的关系，X.parent = null，P.left = null，right.parent = null, X.right = null；
            // 解除P与PP的关系，P.parent = null;
            this.changeParent(null);
            p.changeLeft(null);
            if (right != null) {
                right.changeParent(null);
            }
            this.changeRight(null);
            // 3、建立P与right根左关系，P.left = right，right.parent = P;
            p.changeLeft(right);
            if (right != null) {
                right.changeParent(p);
            }
            // 4、建立X与P的根右关系，X.right = P，P.parent = X;
            this.changeRight(p);
            p.changeParent(this);
            // 5、建立X与PP的关系，X.parent = PP；
            // 根据P与PP的关系建立PP与X的关系：
            // 5.1、P为根节点，即PP == null，则更新根节点为X；
            // 5.2、P为PP的左子节点，即P == PP.left，则PP.left = X；
            // 5.3、P为PP的右子节点，即P == PP.right，则PP.right = X；
            this.changeParent(pp);
            if (pp == null) {
                root = this;
            } else if (p.equals(pp.getLeft())) {
                pp.changeLeft(this);
            } else {
                pp.changeRight(this);
            }
        }
        return this;
    }
}
