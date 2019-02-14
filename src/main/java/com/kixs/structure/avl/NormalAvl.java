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
public class NormalAvl<T extends Comparable<T>> {

    /**
     * 根节点
     */
    private Node<T> root;

    /**
     * 节点数
     */
    private int size = 0;

    /**
     * 获取二叉平衡树大小（节点数）
     *
     * @return 节点数
     */
    public int size() {
        return size;
    }

    public T max() {
        if (root == null) {
            return null;
        }
        return max(root).value;
    }

    public Node<T> max(Node<T> node) {
        if (node == null) {
            return null;
        }
        if (node.right == null) {
            return node;
        }
        return max(node.right);
    }

    /**
     * 删除指定节点
     *
     * @param value 节点value
     * @return 删除成功返回对应value；删除失败返回null（无对应value）
     */
    public T remove(T value) {
        if (value == null) {
            return null;
        }
        Node<T> removeNode = find(value);
        if (removeNode == null) {
            return null;
        }
        return remove(removeNode).value;
    }

    /**
     * 删除指定节点
     *
     * @param removeNode 指定节点
     * @return 指定节点
     */
    private Node<T> remove(Node<T> removeNode) {
        if (removeNode.isLeaf()) {
            removeLeafNode(removeNode);
        } else {
            removeTreeNode(removeNode);
        }
        size--;
        return removeNode;
    }

    /**
     * 删除 有子节点
     *
     * @param treeNode 有子节点
     */
    private void removeTreeNode(Node<T> treeNode) {
        if (treeNode.isSingleLeaf()) {
            removeSingleLeafTreeNode(treeNode);
        } else {
            removeDoubleChildTreeNode(treeNode);
        }
    }

    /**
     * 删除双子节点
     *
     * @param treeNode 双子节点
     */
    private void removeDoubleChildTreeNode(Node<T> treeNode) {
        if (!treeNode.hasDoubleChild()) {
            throw new UnsupportedOperationException("节点：" + treeNode.value + "非双子树节点");
        }
        // 采用treeNode的左子树的最右侧节点代替当前节点
        // 定位特征节点
        Node<T> leftMax = max(treeNode.left);
        Node<T> left = treeNode.left;
        Node<T> right = treeNode.right;
        Node<T> p = treeNode.parent;
        Node<T> leftMaxP = leftMax.parent;
        // 清除treeNode及特征节点的上下关联
        treeNode.left = null;
        treeNode.right = null;
        right.parent = null;
        left.parent = null;
        leftMax.parent = null;
        leftMaxP.right = null;
        // 连接父节点
        leftMax.parent = p;
        if (p != null) {
            boolean isLeft = treeNode.equals(p.left);
            if (isLeft) {
                p.left = leftMax;
            } else {
                p.right = leftMax;
            }
        }
        // 连接右子树
        leftMax.right = right;
        right.parent = leftMax;
        // 连接左子树
        if (!left.equals(leftMax)) {
            leftMax.left = left;
            left.parent = leftMax;
        }
        Node<T> balancedNode;
        if (leftMaxP.equals(treeNode)) {
            balancedNode = leftMax;
        } else {
            AVLBalancedType balancedType = checkBalanced(leftMaxP);
            if (balancedType.isBalanced()) {
                balancedNode = leftMaxP;
            } else {
                balancedNode = leftMaxP.left;
            }
        }
        while (balancedNode != null) {
            balancedNode = balancedAVL(balancedNode);
            balancedNode = balancedNode.parent;
        }
    }

    /**
     * 删除单叶节点
     *
     * @param treeNode 单叶节点
     */
    private void removeSingleLeafTreeNode(Node<T> treeNode) {
        if (!treeNode.isSingleLeaf()) {
            throw new UnsupportedOperationException("节点：" + treeNode.value + "非单叶树节点");
        }
        Node<T> p = treeNode.parent;
        Node<T> leaf = treeNode.left == null ? treeNode.right : treeNode.left;

        leaf.parent = null;
        treeNode.parent = null;
        leaf.parent = p;
        if (p == null) {
            // 删除单叶根节点
            root = leaf;
            return;
        }
        boolean isLeft = treeNode.equals(p.left);
        if (isLeft) {
            p.left = leaf;
        } else {
            p.right = leaf;
        }

        Node<T> balancedNode;
        AVLBalancedType balancedType = checkBalanced(p);
        if (balancedType.isBalanced()) {
            balancedNode = p;
        } else {
            if (isLeft) {
                balancedType = checkBalanced(p.right);
                if (AVLBalancedType.NORMAL_LEFT.equals(balancedType)) {
                    balancedNode = p.right.left;
                } else {
                    balancedNode = p.right;
                }
            } else {
                balancedType = checkBalanced(p.left);
                if (AVLBalancedType.NORMAL_RIGHT.equals(balancedType)) {
                    balancedNode = p.left.right;
                } else {
                    balancedNode = p.left;
                }
            }
        }
        while (balancedNode != null) {
            balancedNode = balancedAVL(balancedNode);
            balancedNode = balancedNode.parent;
        }
    }

    /**
     * 删除指定的叶子节点
     *
     * @param leafNode 被删除的叶子检点
     * @return 被删除的叶子检点
     */
    private void removeLeafNode(Node<T> leafNode) {
        if (!leafNode.isLeaf()) {
            throw new UnsupportedOperationException("节点：" + leafNode.value + "非叶子节点");
        }
        Node<T> p = leafNode.parent;
        leafNode.parent = null;
        if (p != null) {
            if (leafNode.equals(p.left)) {
                p.left = null;
            } else {
                p.right = null;
            }
            Node<T> balancedNode = p;
            AVLBalancedType balancedType = checkBalanced(p);
            if (balancedType.isBalanced()) {
                if (p.parent != null) {
                    balancedType = checkBalanced(p.parent);
                    if (balancedType.isBalanced()) {
                        balancedNode = p.parent;
                    } else {
                        if (AVLBalancedType.HIGH_LEFT.equals(balancedType)) {
                            balancedNode = p.parent.left;
                            if (AVLBalancedType.NORMAL_RIGHT.equals(checkBalanced(p.parent.left))) {
                                balancedNode = p.parent.left.right;
                            }
                        } else {
                            balancedNode = p.parent.right;
                            if (AVLBalancedType.NORMAL_LEFT.equals(checkBalanced(p.parent.right))) {
                                balancedNode = p.parent.right.left;
                            }
                        }
                    }
                }
            } else {
                if (AVLBalancedType.HIGH_LEFT.equals(balancedType)) {
                    balancedNode = p.left;
                } else {
                    balancedNode = p.right;
                }
            }
            /*int height = calcHeight(p);
            if (height == 0) {
                balancedNode = p.parent;
            } else if (height == 1) {
                balancedNode = p;
            } else {
                balancedNode = p.left == null ? p.right : p.left;
            }*/
            while (balancedNode != null) {
                balancedNode = balancedAVL(balancedNode);
                balancedNode = balancedNode.parent;
            }
        } else {
            root = null;
        }
    }

    private Node<T> find(T value) {
        if (root == null) {
            return null;
        }
        return find(root, value);
    }

    private Node<T> find(Node<T> node, T value) {
        if (node == null) {
            return null;
        }
        int cmp = value.compareTo(node.value);
        if (cmp > 0) {
            return find(node.right, value);
        } else if (cmp < 0) {
            return find(node.left, value);
        } else {
            return node;
        }
    }

    /**
     * 插入新值
     *
     * @param value 值
     */
    public void put(T value) {
        if (value == null) {
            return;
        }
        if (root == null) {
            root = new Node<>(value);
            size = 1;
            return;
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
                size++;
            }
        } else if (compare < 0) {
            if (node.left != null) {
                put(node.left, value);
            } else {
                node.left = new Node<>(node, value);
                size++;
            }
        } else {
            // compare == 0 表示已存在，更新value
            node.value = value;
        }
        balancedAVL(node);
    }

    /**
     * 校验AVL是否平衡
     *
     * @return true：平衡；false：不平衡
     */
    public boolean isBalanced() {
        if (root == null) {
            return true;
        }
        return isBalanced(root);
    }

    private boolean isBalanced(Node<T> node) {
        if (node.isLeaf()) {
            return true;
        }
        AVLBalancedType balancedType = checkBalanced(node);
        if (!balancedType.isBalanced()) {
            return false;
        }
        if (node.left != null && !node.left.isLeaf() && !isBalanced(node.left)) {
            return false;
        }
        if (node.right != null && !node.right.isLeaf() && !isBalanced(node.right)) {
            return false;
        }
        return true;
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
     * @return 平衡后的子树根节点
     */
    private Node<T> balancedAVL(Node<T> parent) {
        Node<T> balancedNode = parent;
        if (parent == null || parent.parent == null) {
            // 空树或只有两级
            return balancedNode;
        }
        // 保证节点自身平衡
        AVLBalancedType balancedType = checkBalanced(balancedNode);
        if (!balancedType.isBalanced()) {
            if (AVLBalancedType.HIGH_LEFT.equals(balancedType)) {
                balancedNode = balancedAVL(balancedNode.left);
            } else {
                balancedNode = balancedAVL(balancedNode.right);
            }
        }
        balancedType = checkBalanced(balancedNode.parent);
        if (!balancedType.isBalanced()) {
            if (AVLBalancedType.HIGH_LEFT.equals(balancedType)) {
                balancedNode = simpleRightRotation(parent);
            } else {
                balancedNode = simpleLeftRotation(parent);
            }
        } else {
            if (parent.parent.parent != null) {
                balancedType = checkBalanced(parent.parent.parent);
                if (!balancedType.isBalanced()) {
                    if (AVLBalancedType.HIGH_LEFT.equals(balancedType)) {
                        balancedNode = fixRightRotation(parent);
                    } else {
                        balancedNode = fixLeftRotation(parent);
                    }
                }
            }
        }
        return balancedNode;
    }

    private Node<T> simpleLeftRotation(Node<T> balancedNode) {
        if (balancedNode.left == null) {
            return leftRotation(balancedNode);
        } else {
            Node<T> childRoot = rebuildRL(balancedNode);
            return leftRotation(childRoot);
        }
    }

    private Node<T> simpleRightRotation(Node<T> balancedNode) {
        if (balancedNode.right == null) {
            return rightRotation(balancedNode);
        } else {
            Node<T> childRoot = rebuildLR(balancedNode);
            return rightRotation(childRoot);
        }
    }

    private Node<T> fixLeftRotation(Node<T> balancedNode) {
        if (balancedNode.equals(balancedNode.parent.right)) {
            return leftRotation(balancedNode.parent);
        } else {
            Node<T> childRoot = rightRotation(balancedNode);
            return leftRotation(childRoot);
        }
    }

    private Node<T> fixRightRotation(Node<T> balancedNode) {
        if (balancedNode.equals(balancedNode.parent.left)) {
            return rightRotation(balancedNode.parent);
        } else {
            Node<T> childRoot = leftRotation(balancedNode);
            return rightRotation(childRoot);
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
     * 先序遍历节点
     *
     * @return 数据
     */
    public List<T> preOrderTraversal() {
        return preOrderTraversal(root, new ArrayList<>());
    }

    private List<T> preOrderTraversal(Node<T> node, List<T> data) {
        if (node != null) {
            data.add(node.value);
            if (node.left != null) {
                preOrderTraversal(node.left, data);
            }
            if (node.right != null) {
                preOrderTraversal(node.right, data);
            }
        }
        return data;
    }

    /**
     * 结构重塑
     * <p>
     * 重塑前：先序（5,6,7），中序（5,7,6），后序（6,7,5）
     * 重塑后：先序（5,6,7），中序（5,6,7），后序（7,6,5）
     * </p>
     *
     * @param x 重塑节点
     * @return 重塑后节点
     */
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

    /**
     * 结构重塑
     * <p>
     * 重塑前：先序（7,5,6），中序（5,6,7），后序（6,5,7）
     * 重塑后：先序（7,6,5），中序（5,6,7），后序（5,6,7）
     * </p>
     *
     * @param x 重塑节点
     * @return 重塑后节点
     */
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

        /**
         * 判断是否为叶子节点
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * 判断节点是否为单叶节点
         */
        public boolean isSingleLeaf() {
            if (isLeaf()) {
                return false;
            }
            if (left != null && right != null) {
                return false;
            }
            return true;
        }

        /**
         * 判断节点是否为双子节点
         */
        public boolean hasDoubleChild() {
            return left != null && right != null;
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
