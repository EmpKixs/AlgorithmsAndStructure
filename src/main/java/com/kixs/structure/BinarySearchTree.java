package com.kixs.structure;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 二叉搜索树-BST
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class BinarySearchTree<K extends Comparable<K>, V> {

    /**
     * 二叉树根节点
     */
    private Node<K, V> root;

    /**
     * 二叉树节点总数
     */
    private int size = 0;

    public BinarySearchTree() {
    }

    /**
     * 删除二叉树中的键值
     */
    public void delete(K key) {
        root = delete(root, key);
    }

    private Node<K, V> delete(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.leftChild = delete(node.leftChild, key);
        } else if (cmp > 0) {
            node.rightChild = delete(node.rightChild, key);
        } else {
            size--;
            if (node.rightChild == null) {
                return node.leftChild;
            }
            if (node.leftChild == null) {
                return node.rightChild;
            }
            Node<K, V> tmp = node;
            node = min(tmp.rightChild);
            node.rightChild = deleteMin(tmp.rightChild);
            node.leftChild = tmp.leftChild;
        }
        return node;
    }

    /**
     * 删除二叉树中的最大键值
     */
    public void deleteMax() {
        if (!isEmpty()) {
            root = deleteMax(root);
            size--;
        }
    }

    private Node<K, V> deleteMax(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        if (node.rightChild == null) {
            return node.leftChild;
        }
        node.rightChild = deleteMax(node.rightChild);
        if (node.rightChild != null) {
            node.rightChild.parent = node;
        }
        return node;
    }

    /**
     * 删除二叉树中的最小键值
     */
    public void deleteMin() {
        if (!isEmpty()) {
            root = deleteMin(root);
            size--;
        }
    }

    private Node<K, V> deleteMin(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        if (node.leftChild == null) {
            return node.rightChild;
        }
        node.leftChild = deleteMin(node.leftChild);
        if (node.leftChild != null) {
            node.leftChild.parent = node;
        }
        return node;
    }

    /**
     * 获取二叉树中的最大键值
     */
    public K max() {
        return max(root);
    }

    private K max(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        if (node.rightChild == null) {
            return node.key;
        }
        return max(node.rightChild);
    }

    /**
     * 获取二叉树中的最小键值
     */
    public K min() {
        if (isEmpty()) {
            return null;
        }
        Node<K, V> min = min(root);
        return min == null ? null : min.key;
    }

    private Node<K, V> min(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        if (node.leftChild == null) {
            return node;
        }
        return min(node.leftChild);
    }

    /**
     * 插入新键值对
     *
     * @param key   键
     * @param value 值
     */
    public void put(K key, V value) {
        root = put(root, key, value);
        size++;
    }

    private Node<K, V> put(Node<K, V> node, K key, V value) {
        if (node == null) {
            return new Node<>(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.leftChild = put(node.leftChild, key, value);
            node.leftChild.parent = node;
        } else if (cmp > 0) {
            node.rightChild = put(node.rightChild, key, value);
            node.rightChild.parent = node;
        } else {
            node.value = value;
        }
        return node;
    }

    /**
     * 查找二叉树中键的对应值
     *
     * @param key 键
     * @return value
     */
    public V get(K key) {
        if (isEmpty()) {
            return null;
        }
        Node<K, V> node = get(root, key);
        return node == null ? null : node.value;
    }

    private Node<K, V> get(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return get(node.leftChild, key);
        } else if (cmp > 0) {
            return get(node.rightChild, key);
        } else {
            return node;
        }
    }

    /**
     * 获取全部二叉树的键，默认中序遍历
     */
    public List<K> keyList() {
        if (root == null) {
            return null;
        }
        return inOrderTraversal(Lists.newArrayList(), root);
    }

    /**
     * 先序获取全部二叉树的键
     */
    public List<K> preOrderKey() {
        if (root == null) {
            return null;
        }
        return preOrderTraversal(Lists.newArrayList(), root);
    }

    /**
     * 中序获取全部二叉树的键
     */
    public List<K> inOrderKey() {
        if (root == null) {
            return null;
        }
        return inOrderTraversal(Lists.newArrayList(), root);
    }

    /**
     * 后序获取全部二叉树的键
     */
    public List<K> postOrderKey() {
        if (root == null) {
            return null;
        }
        return postOrderTraversal(Lists.newArrayList(), root);
    }

    // 先序遍历-根左右
    private List<K> preOrderTraversal(List<K> keyList, Node<K, V> node) {
        if (node == null) {
            return keyList;
        }
        keyList.add(node.key);
        if (node.leftChild != null) {
            keyList = preOrderTraversal(keyList, node.leftChild);
        }
        if (node.rightChild != null) {
            keyList = preOrderTraversal(keyList, node.rightChild);
        }
        return keyList;
    }

    // 中序遍历-左根右
    private List<K> inOrderTraversal(List<K> keyList, Node<K, V> node) {
        if (node == null) {
            return keyList;
        }
        if (node.leftChild != null) {
            keyList = inOrderTraversal(keyList, node.leftChild);
        }
        keyList.add(node.key);
        if (node.rightChild != null) {
            keyList = inOrderTraversal(keyList, node.rightChild);
        }
        return keyList;
    }

    // 后序遍历-左右根
    private List<K> postOrderTraversal(List<K> keyList, Node<K, V> node) {
        if (node == null) {
            return keyList;
        }
        if (node.leftChild != null) {
            keyList = postOrderTraversal(keyList, node.leftChild);
        }
        if (node.rightChild != null) {
            keyList = postOrderTraversal(keyList, node.rightChild);
        }
        keyList.add(node.key);
        return keyList;
    }

    /**
     * 返回当前二叉树节点数
     */
    public int size() {
        return size;
    }

    /**
     * 返回当前二叉树是否为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 树节点定义
     */
    private class Node<K extends Comparable<K>, V> {

        /**
         * 父节点，当节点为根节点时，父节点为空
         */
        private Node<K, V> parent;

        /**
         * 左节点
         */
        private Node<K, V> leftChild;

        /**
         * 右节点
         */
        private Node<K, V> rightChild;

        /**
         * 节点键
         */
        private K key;

        /**
         * 节点值
         */
        private V value;

        private Node(Node<K, V> parent, Node<K, V> leftChild, Node<K, V> rightChild, K key, V value) {
            this.parent = parent;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.key = key;
            this.value = value;
        }

        private Node(K key, V value) {
            this(null, null, null, key, value);
        }
    }
}
