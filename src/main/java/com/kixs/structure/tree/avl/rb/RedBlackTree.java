package com.kixs.structure.tree.avl.rb;

import com.kixs.structure.tree.AbstractTreeRoot;
import com.kixs.structure.tree.Tree;

/**
 * 红黑树
 * 性质：
 * <p>
 * 0、二叉查找树基本性质；
 * 1、每个节点或是红色的，或是黑色的；
 * 2、根节点是黑色的；
 * 3、每个叶节点（NIL节点，空节点）是黑色的；
 * 4、如果一个节点是红色的，则它的两个子节点都是黑色的；（从每个叶节点（NIL节点，空节点）到根的所有路径上不能有两个连续的红色节点）
 * 5、从任一节点到其每个叶节点（NIL节点，空节点）的所有路径都包含相同数目的黑色节点。
 * </p>
 *
 * @author wangbing
 * @version 1.0
 * @date 2019/2/15 10:48
 * Copyright: Copyright (c) 2019
 */
public class RedBlackTree<K extends Comparable<K>, V> implements AbstractTreeRoot<K, V> {

    private RbTreeNode<K, V> root;

    private int size = 0;

    @Override
    public Tree<K, V> getRoot() {
        return root;
    }

    @Override
    public void changeRoot(Tree<K, V> root) {
        if (root == null) {
            this.root = null;
            return;
        }
        if (root instanceof RbTreeNode) {
            this.root = (RbTreeNode<K, V>) root;
            return;
        }
        throw new IllegalArgumentException("根节点修改失败：illegal root " + root.getClass());
    }

    /**
     * 节点数
     *
     * @return 节点数
     */
    public int size() {
        return size;
    }
}
