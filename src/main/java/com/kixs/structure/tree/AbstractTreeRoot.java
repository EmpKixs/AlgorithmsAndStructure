package com.kixs.structure.tree;

/**
 * 抽象树根节点定义
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public interface AbstractTreeRoot<K extends Comparable<K>, V> {

    /**
     * 获取树根节点
     *
     * @return 根节点
     */
    Tree<K, V> getRoot();

    /**
     * 修改树根节点
     *
     * @param root 新根节点
     */
    void changeRoot(Tree<K, V> root);
}
