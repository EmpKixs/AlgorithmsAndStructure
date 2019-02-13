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

    /**
     * 检查节点平衡状态
     *
     * @param node 节点
     * @return 平衡状态
     */
    private AVLBalancedType checkBalanced(Node<T> node) {
        return AVLBalancedType.getBalancedType(calcHeight(node.left) - calcHeight(node.right));
    }

    /**
     * 平衡操作
     *
     * @param parent 新加入节点的父节点
     */
    private void balancedAVL(Node<T> parent) {
        if (parent == null || parent.parent == null) {
            // 空树或只有两级
            return;
        }
        AVLBalancedType balancedType = checkBalanced(parent.parent);
        if (!balancedType.isBalanced()) {
            if (AVLBalancedType.HIGH_LEFT.equals(balancedType)) {
                simpleRightRotation(parent);
            } else {
                simpleLeftRotation(parent);
            }
        } else {
            if (parent.parent.parent != null) {
                balancedType = checkBalanced(parent.parent.parent);
                if (!balancedType.isBalanced()) {
                    if (AVLBalancedType.HIGH_LEFT.equals(balancedType)) {
                        fixRightRotation(parent);
                    } else {
                        fixLeftRotation(parent);
                    }
                }
            }
        }
    }

    private void simpleLeftRotation(Node<T> balancedNode) {
        if (balancedNode.left == null) {
            leftRotation(balancedNode);
        } else {
            Node<T> childRoot = rightRotation(balancedNode);
            leftRotation(childRoot);
        }
    }

    private void simpleRightRotation(Node<T> balancedNode) {
        if (balancedNode.right == null) {
            rightRotation(balancedNode);
        } else {
            Node<T> childRoot = leftRotation(balancedNode);
            rightRotation(childRoot);
        }
    }

    private void fixLeftRotation(Node<T> balancedNode) {
        if (balancedNode.equals(balancedNode.parent.right)) {
            leftRotation(balancedNode.parent);
        } else {
            Node<T> childRoot = rightRotation(balancedNode);
            leftRotation(childRoot.parent);
        }
    }

    private void fixRightRotation(Node<T> balancedNode) {
        if (balancedNode.equals(balancedNode.parent.left)) {
            rightRotation(balancedNode.parent);
        } else {
            Node<T> childRoot = leftRotation(balancedNode);
            rightRotation(childRoot.parent);
        }
    }

    /**
     * 计算节点高度
     *
     * @param node 节点
     * @return 节点高度
     */
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
     * @param x 子树
     * @return 旋转后子树根节点
     */
    private Node<T> leftRotation(Node<T> x) {
        if (x == null) {
            return null;
        }
        // 1、确定旋转节点[X]的父节点[P]
        Node<T> p = x.parent;
        // 左旋判断x是p的左子节点还是右子节点
        if (x.equals(p.right)) {
            // 2、修改X的父节点为P的父节点
            x.parent = p.parent;
            // 3、修改X.left指向P
            p.right = x.left;
            if (x.left != null) {
                x.left.parent = p;
            }
            // 4、修改P指向X
            x.right = p;
            p.parent = x;
            if (x.parent == null) {
                root = x;
            }
            return x;
        } else {
            p.left = x.right;
            x.right.parent = p;
            // 优化
            x.left = p.right.right;
            p.right.right = x;
            x.parent = p.right.right;
            return p.right;
        }

    }

    /**
     * 子树右旋
     *
     * @param x 子树
     * @return 旋转后子树根节点
     */
    private Node<T> rightRotation(Node<T> x) {
        if (x == null) {
            return null;
        }
        // 1、确定旋转节点[X]的父节点[P]
        Node<T> p = x.parent;
        // 右旋判断x是p的左子节点还是右子节点
        if (x.equals(p.left)) {
            // 2、修改X的父节点为P的父节点
            x.parent = p.parent;
            // 3、修改X.right指向P
            p.left = x.right;
            if (x.right != null) {
                x.right.parent = p;
            }
            // 4、修改P指向X
            x.right = p;
            p.parent = x;
            if (x.parent == null) {
                root = x;
            }
            return x;
        } else {
            p.right = x.left;
            x.left.parent = p;
            // 优化
            x.left = p.right.right;
            p.right.right = x;
            x.parent = p.right.right;
            return p.right;
        }
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
