package com.kixs.structure.avl;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通平衡二叉树
 * 性质：它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class NormalAVL<T extends Comparable<T>> {

    /**
     * 根节点
     */
    private Node<T> root;

    public void put(T value) {
        if (root == null) {
            root = new Node<>(null, value);
        }
        put(root, value);
    }

    private void put(Node<T> node, T value) {
        int compare = value.compareTo(node.value);
        if (compare > 0) {
            if (node.right != null) {
                put(node.right, value);
            } else {
                node.right = new Node<>(node, value);
            }
        } else if (compare < 0) {
            if (node.left != null) {
                put(node.left, value);
            } else {
                node.left = new Node<>(node, value);
            }
        } else {
            // compare == 0 表示已存在，更新value
            node.value = value;
        }
        balancedAVL(node);
    }

    private void balancedAVL(Node<T> parent) {
        if (parent == null || parent.parent == null) {
            // 空树或只有两级
            return;
        }
        Node<T> balancedNode = parent.parent;
        if (parent.parent.parent != null) {
            balancedNode = parent.parent.parent;
        }
        int lHeight = calcHeight(balancedNode.left);
        int rHeight = calcHeight(balancedNode.right);
        if (Math.abs(lHeight - rHeight) > 1) {
            // 不平衡，需要重新平衡
            if (lHeight > rHeight) {
                // 左高
                fixRightRotation(balancedNode);
            } else {
                // 右高
                fixLeftRotation(balancedNode);
            }
        }
    }

    private void fixLeftRotation(Node<T> balancedNode) {
        final Node<T> rightNode = balancedNode.right;
        if (rightNode.right != null) {
            simpleLeftRotation(balancedNode);
        } else if (rightNode.left != null) {
            simpleRightRotation(balancedNode);
            balancedNode.right = leftRotation(rightNode);
        }
    }

    private void fixRightRotation(Node<T> balancedNode) {
        final Node<T> leftNode = balancedNode.left;
        if (leftNode.left != null) {
            simpleRightRotation(balancedNode);
        } else if (leftNode.right != null) {
            balancedNode.left = leftRotation(leftNode);
            simpleRightRotation(balancedNode);
        }
    }

    private void simpleLeftRotation(Node<T> balancedNode) {
        // 0:balancedNode为根节点;1:balancedNode为右子节点;-1:balancedNode为左子节点
        int childTag = getChildTag(balancedNode);
        if (childTag == 0) {
            root = leftRotation(balancedNode);
        } else if (childTag < 0) {
            balancedNode.parent.left = leftRotation(balancedNode);
        } else {
            balancedNode.parent.right = leftRotation(balancedNode);
        }
    }

    private void simpleRightRotation(Node<T> balancedNode) {
        // 0:balancedNode为根节点;1:balancedNode为右子节点;-1:balancedNode为左子节点
        int childTag = getChildTag(balancedNode);
        if (childTag == 0) {
            root = rightRotation(balancedNode);
        } else if (childTag < 0) {
            balancedNode.parent.left = rightRotation(balancedNode);
        } else {
            balancedNode.parent.right = rightRotation(balancedNode);
        }
    }

    /**
     * 判断balancedNode是父节点的左子节点还是右子节点
     *
     * @param balancedNode 待平衡节点
     * @return 0:balancedNode为根节点;1:balancedNode为右子节点;-1:balancedNode为左子节点
     */
    private int getChildTag(Node<T> balancedNode) {
        int childTag = 0;
        final Node<T> parentBalancedNode = balancedNode.parent;
        if (parentBalancedNode != null) {
            if (balancedNode.equals(parentBalancedNode.left)) {
                childTag = -1;
            } else {
                childTag = 1;
            }
        }
        return childTag;
    }


    private int calcHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(calcHeight(node.left), calcHeight(node.right));
    }

    /**
     * 中序遍历节点
     *
     * @return 数据
     */
    public List<T> inorderTraversal() {
        return inorderTraversal(root, new ArrayList<>());
    }

    private List<T> inorderTraversal(Node<T> node, List<T> data) {
        if (node != null) {
            if (node.left != null) {
                inorderTraversal(node.left, data);
            }
            data.add(node.value);
            if (node.right != null) {
                inorderTraversal(node.right, data);
            }
        }
        return data;
    }

    /**
     * 子树左旋
     *
     * @param tree 子树
     * @return 旋转后子树根节点
     */
    private Node<T> leftRotation(Node<T> tree) {
        if (tree == null) {
            return null;
        }
        Node<T> rightTree = tree.right;
        if (rightTree == null) {
            return tree;
        }

        tree.right = rightTree.left;
        rightTree.left = tree;
        rightTree.parent = tree.parent;
        tree.parent = rightTree;
        return rightTree;
    }

    /**
     * 子树右旋
     *
     * @param tree 子树
     * @return 旋转后子树根节点
     */
    private Node<T> rightRotation(Node<T> tree) {
        if (tree == null) {
            return null;
        }
        Node<T> leftTree = tree.left;
        if (leftTree == null) {
            return tree;
        }
        tree.left = leftTree.right;
        leftTree.right = tree;
        leftTree.parent = tree.parent;
        tree.parent = leftTree;
        return leftTree;
    }

    /**
     * 节点定义
     *
     * @param <T> 节点值类型
     */
    private static class Node<T extends Comparable<T>> {

        private Node<T> parent;

        private Node<T> left;

        private Node<T> right;

        private T value;

        public Node(T value) {
            this(null, null, null, value);
        }

        public Node(Node<T> parent, T value) {
            this(parent, null, null, value);
        }

        public Node(Node<T> parent, Node<T> left, Node<T> right, T value) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.value = value;
        }

        public Node<T> getParent() {
            return parent;
        }

        public void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public Node<T> getLeft() {
            return left;
        }

        public void setLeft(Node<T> left) {
            this.left = left;
        }

        public Node<T> getRight() {
            return right;
        }

        public void setRight(Node<T> right) {
            this.right = right;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
