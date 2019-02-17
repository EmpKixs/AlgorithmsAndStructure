package com.kixs.structure.tree;

/**
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public interface AbstractTreeRoot<K extends Comparable<K>, V> {

    Tree<K, V> getRoot();

    void changeRoot(Tree<K, V> root);
}
