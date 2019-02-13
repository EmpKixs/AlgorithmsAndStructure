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
            root = new Node<>(value);
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
            Node<T> childRoot = rebuildRL(balancedNode);
            leftRotation(childRoot);
        }
    }

    private void simpleRightRotation(Node<T> balancedNode) {
        if (balancedNode.right == null) {
            rightRotation(balancedNode);
        } else {
            Node<T> childRoot = rebuildLR(balancedNode);
            rightRotation(childRoot);
        }
    }

    private void fixLeftRotation(Node<T> balancedNode) {
        if (balancedNode.equals(balancedNode.parent.right)) {
            leftRotation(balancedNode.parent);
        } else {
            Node<T> childRoot = rightRotation(balancedNode);
            leftRotation(childRoot);
        }
    }

    private void fixRightRotation(Node<T> balancedNode) {
        if (balancedNode.equals(balancedNode.parent.left)) {
            rightRotation(balancedNode.parent);
        } else {
            Node<T> childRoot = leftRotation(balancedNode);
            rightRotation(childRoot);
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

    private Node<T> rebuildRL(Node<T> x) {
        if (x == null) {
            return null;
        }
        Node<T> p = x.parent;
        Node<T> left = x.left;

        left.parent = null;
        x.left = null;
        x.parent = null;
        p.right = null;

        p.right = left;
        left.parent = p;
        x.parent = left;
        left.right = x;

        return left;
    }

    private Node<T> rebuildLR(Node<T> x) {
        if (x == null) {
            return null;
        }
        Node<T> p = x.parent;
        Node<T> right = x.right;

        x.right = null;
        right.parent = null;
        p.left = null;
        x.parent = null;

        p.left = right;
        right.parent = p;
        x.parent = right;
        right.left = x;

        return right;
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
        // 1、定位父节点P和左子节点left，P = X.parent，left = X.left；定位P的父节点PP，PP = P.parent；
        Node<T> p = x.parent;
        // x不能为根节点且x为P的右子节点
        if (p != null && x.equals(p.right)) {
            Node<T> left = x.left;
            Node<T> pp = p.parent;
            // 2、解除X与P和left的关系，X.parent = null，P.right = null，left.parent = null, X.left = null；
            // 解除P与PP的关系，P.parent = null;
            x.parent = null;
            p.right = null;
            if (left != null) {
                left.parent = null;
            }
            x.left = null;
            // 3、建立P与left根右关系，P.right = left，left.parent = P;
            p.right = left;
            if (left != null) {
                left.parent = p;
            }
            // 4、建立X与P的根左关系，X.left = P，P.parent = X;
            x.left = p;
            p.parent = x;
            // 5、建立X与PP的关系，X.parent = PP；
            // 根据P与PP的关系建立PP与X的关系：
            // 5.1、P为根节点，即PP == null，则更新根节点为X；
            // 5.2、P为PP的左子节点，即P == PP.left，则PP.left = X；
            // 5.3、P为PP的右子节点，即P == PP.right，则PP.right = X；
            x.parent = pp;
            if (pp == null) {
                root = x;
            } else if (p.equals(pp.left)) {
                pp.left = x;
            } else {
                pp.right = x;
            }
        }
        return x;
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
        // 1、定位父节点P和左子节点right，P = X.parent，right = X.right；定位P的父节点PP，PP = P.parent；
        Node<T> p = x.parent;
        // x不能为根节点且x为P的左子节点
        if (p != null && x.equals(p.left)) {
            Node<T> right = x.right;
            Node<T> pp = p.parent;
            // 2、解除X与P和right的关系，X.parent = null，P.left = null，right.parent = null, X.right = null；
            // 解除P与PP的关系，P.parent = null;
            x.parent = null;
            p.left = null;
            if (right != null) {
                right.parent = null;
            }
            x.right = null;
            // 3、建立P与right根左关系，P.left = right，right.parent = P;
            p.left = right;
            if (right != null) {
                right.parent = p;
            }
            // 4、建立X与P的根右关系，X.right = P，P.parent = X;
            x.right = p;
            p.parent = x;
            // 5、建立X与PP的关系，X.parent = PP；
            // 根据P与PP的关系建立PP与X的关系：
            // 5.1、P为根节点，即PP == null，则更新根节点为X；
            // 5.2、P为PP的左子节点，即P == PP.left，则PP.left = X；
            // 5.3、P为PP的右子节点，即P == PP.right，则PP.right = X；
            x.parent = pp;
            if (pp == null) {
                root = x;
            } else if (p.equals(pp.left)) {
                pp.left = x;
            } else {
                pp.right = x;
            }
        }
        return x;
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
