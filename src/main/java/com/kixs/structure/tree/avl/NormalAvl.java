package com.kixs.structure.tree.avl;

import com.kixs.structure.tree.AbstractTreeRoot;
import com.kixs.structure.tree.Tree;
import com.kixs.structure.tree.TreeNode;

import java.util.List;

/**
 * 普通平衡二叉树
 * 性质：它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class NormalAvl<K extends Comparable<K>, V> implements AbstractTreeRoot<K, V> {

    /**
     * 根节点
     */
    private TreeNode<K, V> root;

    /**
     * 节点数
     */
    private int size = 0;

    @Override
    public TreeNode<K, V> getRoot() {
        return root;
    }

    @Override
    public void changeRoot(Tree<K, V> root) {
        if (root == null) {
            this.root = null;
            return;
        }
        if (root instanceof TreeNode) {
            this.root = (TreeNode<K, V>) root;
            return;
        }
        throw new IllegalArgumentException("根节点修改失败：illegal root " + root.getClass());
    }

    /**
     * 获取二叉平衡树大小（节点数）
     *
     * @return 节点数
     */
    public int size() {
        return size;
    }

    /**
     * 获取最大值
     */
    public V max() {
        if (root == null) {
            return null;
        }
        return max(root).getValue();
    }

    /**
     * 获取以指定节点为根的最大值节点
     *
     * @param tree 指定节点
     * @return 最大值节点
     */
    private TreeNode<K, V> max(TreeNode<K, V> tree) {
        if (tree == null) {
            return null;
        }
        if (tree.getRight() == null) {
            return tree;
        }
        return max(tree.getRight());
    }

    /**
     * 删除指定节点
     *
     * @param key 节点key
     * @return 删除成功返回对应value；删除失败返回null（无对应value）
     */
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        TreeNode<K, V> removeNode = find(key);
        if (removeNode == null) {
            return null;
        }
        return remove(removeNode).getValue();
    }

    /**
     * 删除指定节点
     *
     * @param removeNode 指定节点
     * @return 指定节点
     */
    private TreeNode<K, V> remove(TreeNode<K, V> removeNode) {
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
    private void removeTreeNode(TreeNode<K, V> treeNode) {
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
    private void removeDoubleChildTreeNode(TreeNode<K, V> treeNode) {
        if (!treeNode.hasDoubleChild()) {
            throw new UnsupportedOperationException("节点：" + treeNode.getKey() + "非双子树节点");
        }
        // 采用treeNode的左子树的最右侧节点代替当前节点
        // 定位特征节点
        TreeNode<K, V> leftMax = max(treeNode.getLeft());
        TreeNode<K, V> left = treeNode.getLeft();
        TreeNode<K, V> right = treeNode.getRight();
        TreeNode<K, V> p = treeNode.getParent();
        TreeNode<K, V> leftMaxP = leftMax.getParent();
        // 清除treeNode及特征节点的上下关联
        treeNode.changeLeft(null);
        treeNode.changeRight(null);
        right.changeParent(null);
        left.changeParent(null);
        leftMax.changeParent(null);
        leftMaxP.changeRight(null);
        // 连接父节点
        leftMax.changeParent(p);
        if (p != null) {
            boolean isLeft = treeNode.equals(p.getLeft());
            if (isLeft) {
                p.changeLeft(leftMax);
            } else {
                p.changeRight(leftMax);
            }
        } else {
            // p == null表示删除根节点
            root = leftMax;
        }
        if (leftMax.getLeft() != null) {
            leftMaxP.changeRight(leftMax.getLeft());
            leftMax.changeLeft(leftMaxP);
        }
        // 连接右子树
        leftMax.changeRight(right);
        right.changeParent(leftMax);
        // 连接左子树
        if (!left.equals(leftMax)) {
            leftMax.changeLeft(left);
            left.changeParent(leftMax);
        }
        TreeNode<K, V> balancedNode;
        if (leftMaxP.equals(treeNode)) {
            balancedNode = leftMax;
        } else {
            AVLBalancedType balancedType = checkBalanced(leftMaxP);
            if (balancedType.isBalanced()) {
                balancedNode = leftMaxP;
            } else {
                balancedNode = leftMaxP.getLeft();
                if (AVLBalancedType.NORMAL_RIGHT.equals(checkBalanced(leftMaxP.getLeft()))) {
                    balancedNode = leftMaxP.getLeft().getRight();
                }
            }
        }
        while (balancedNode != null) {
            balancedNode = removingBalancedAVL(balancedNode);
            balancedNode = balancedNode.getParent();
        }
    }

    /**
     * 删除单叶节点
     *
     * @param treeNode 单叶节点
     */
    private void removeSingleLeafTreeNode(TreeNode<K, V> treeNode) {
        if (!treeNode.isSingleLeaf()) {
            throw new UnsupportedOperationException("节点：" + treeNode.getKey() + "非单叶树节点");
        }
        TreeNode<K, V> p = treeNode.getParent();
        TreeNode<K, V> leaf = treeNode.getLeft() == null ? treeNode.getRight() : treeNode.getLeft();

        leaf.changeParent(null);
        treeNode.changeParent(null);
        leaf.changeParent(p);
        if (p == null) {
            // 删除单叶根节点
            root = leaf;
            return;
        }
        boolean isLeft = treeNode.equals(p.getLeft());
        if (isLeft) {
            p.changeLeft(leaf);
        } else {
            p.changeRight(leaf);
        }

        TreeNode<K, V> balancedNode;
        AVLBalancedType balancedType = checkBalanced(p);
        if (balancedType.isBalanced()) {
            balancedNode = p;
        } else {
            if (isLeft) {
                balancedType = checkBalanced(p.getRight());
                if (AVLBalancedType.NORMAL_LEFT.equals(balancedType)) {
                    balancedNode = p.getRight().getLeft();
                } else {
                    balancedNode = p.getRight();
                }
            } else {
                balancedType = checkBalanced(p.getLeft());
                if (AVLBalancedType.NORMAL_RIGHT.equals(balancedType)) {
                    balancedNode = p.getLeft().getRight();
                } else {
                    balancedNode = p.getLeft();
                }
            }
        }
        while (balancedNode != null) {
            balancedNode = removingBalancedAVL(balancedNode);
            balancedNode = balancedNode.getParent();
        }
    }

    /**
     * 删除指定的叶子节点
     *
     * @param leafNode 被删除的叶子检点
     */
    private void removeLeafNode(TreeNode<K, V> leafNode) {
        if (!leafNode.isLeaf()) {
            throw new UnsupportedOperationException("节点：" + leafNode.getKey() + "非叶子节点");
        }
        TreeNode<K, V> p = leafNode.getParent();
        leafNode.changeParent(null);
        if (p != null) {
            if (leafNode.equals(p.getLeft())) {
                p.changeLeft(null);
            } else {
                p.changeRight(null);
            }
            TreeNode<K, V> balancedNode = p;
            AVLBalancedType balancedType = checkBalanced(p);
            if (balancedType.isBalanced()) {
                if (p.getParent() != null) {
                    balancedType = checkBalanced(p.getParent());
                    if (balancedType.isBalanced()) {
                        balancedNode = p.getParent();
                    } else {
                        if (AVLBalancedType.HIGH_LEFT.equals(balancedType)) {
                            balancedNode = p.getParent().getLeft();
                            if (AVLBalancedType.NORMAL_RIGHT.equals(checkBalanced(p.getParent().getLeft()))) {
                                balancedNode = p.getParent().getLeft().getRight();
                            }
                        } else {
                            balancedNode = p.getParent().getRight();
                            if (AVLBalancedType.NORMAL_LEFT.equals(checkBalanced(p.getParent().getRight()))) {
                                balancedNode = p.getParent().getRight().getLeft();
                            }
                        }
                    }
                }
            } else {
                if (AVLBalancedType.HIGH_LEFT.equals(balancedType)) {
                    balancedNode = p.getLeft();
                } else {
                    balancedNode = p.getRight();
                }
            }
            while (balancedNode != null) {
                balancedNode = removingBalancedAVL(balancedNode);
                balancedNode = balancedNode.getParent();
            }
        } else {
            root = null;
        }
    }

    private TreeNode<K, V> find(K key) {
        if (root == null) {
            return null;
        }
        return find(root, key);
    }

    private TreeNode<K, V> find(TreeNode<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.getKey());
        if (cmp == 0) {
            return node;
        } else if (cmp > 0) {
            return find(node.getRight(), key);
        } else {
            return find(node.getLeft(), key);
        }
    }

    /**
     * 插入新值
     *
     * @param value 值
     */
    public void put(K key, V value) {
        if (value == null) {
            return;
        }
        if (root == null) {
            root = new TreeNode<>(key, value);
            size = 1;
            return;
        }
        put(root, key, value);
    }

    private void put(TreeNode<K, V> node, K key, V value) {
        int compare = key.compareTo(node.getKey());
        if (compare == 0) {
            // compare == 0 表示已存在，更新value，不需要重新执行平衡操作
            node.changeValue(value);
            return;
        }
        if (compare > 0) {
            if (node.getRight() != null) {
                put(node.getRight(), key, value);
            } else {
                node.changeRight(new TreeNode<>(node, key, value));
                size++;
            }
        } else {
            if (node.getLeft() != null) {
                put(node.getLeft(), key, value);
            } else {
                node.changeLeft(new TreeNode<>(node, key, value));
                size++;
            }
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

    private boolean isBalanced(TreeNode<K, V> node) {
        if (node.isLeaf()) {
            return true;
        }
        AVLBalancedType balancedType = checkBalanced(node);
        if (!balancedType.isBalanced()) {
            return false;
        }
        boolean leftFlag = node.getLeft() != null && !node.getLeft().isLeaf() && !isBalanced(node.getLeft());
        if (leftFlag) {
            return false;
        }
        boolean rightFlag = node.getRight() != null && !node.getRight().isLeaf() && !isBalanced(node.getRight());
        if (rightFlag) {
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
    private AVLBalancedType checkBalanced(TreeNode<K, V> node) {
        int leftHeight = node.getLeft() == null ? 0 : node.getLeft().height();
        int rightHeight = node.getRight() == null ? 0 : node.getRight().height();
        try {
            return AVLBalancedType.getBalancedType(leftHeight - rightHeight);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 平衡操作（删除平衡）
     *
     * @param parent 待平衡节点
     * @return 平衡后的子树根节点
     */
    @SuppressWarnings("all")
    private TreeNode<K, V> removingBalancedAVL(TreeNode<K, V> parent) {
        TreeNode<K, V> balancedNode = parent;
        if (parent == null || parent.getParent() == null) {
            // 空树或只有两级
            return balancedNode;
        }
        // 保证节点自身平衡
        AVLBalancedType balancedType = checkBalanced(balancedNode);
        if (!balancedType.isBalanced()) {
            if (AVLBalancedType.HIGH_LEFT.equals(balancedType)) {
                balancedNode = removingBalancedAVL(balancedNode.getLeft());
            } else {
                balancedNode = removingBalancedAVL(balancedNode.getRight());
            }
        }
        balancedType = checkBalanced(balancedNode.getParent());
        if (!balancedType.isBalanced()) {
            if (AVLBalancedType.HIGH_LEFT.equals(balancedType)) {
                if (balancedNode.getParent().getRight() == null) {
                    balancedNode = simpleRightRotation(balancedNode);
                } else {
                    balancedNode = (TreeNode<K, V>) balancedNode.rightRotation(this);
                }
            } else {
                if (balancedNode.getParent().getLeft() == null) {
                    balancedNode = simpleLeftRotation(balancedNode);
                } else {
                    balancedNode = (TreeNode<K, V>) balancedNode.leftRotation(this);
                }
            }
        } else {
            if (balancedNode.getParent().getParent() != null) {
                balancedType = checkBalanced(balancedNode.getParent().getParent());
                if (!balancedType.isBalanced()) {
                    if (AVLBalancedType.HIGH_LEFT.equals(balancedType)) {
                        balancedNode = fixRightRotation(balancedNode);
                    } else {
                        balancedNode = fixLeftRotation(balancedNode);
                    }
                }
            }
        }
        return balancedNode;
    }

    /**
     * 平衡操作（添加平衡）
     *
     * @param parent 新加入节点的父节点
     * @return 平衡后的子树根节点
     */
    @SuppressWarnings("all")
    private TreeNode<K, V> balancedAVL(TreeNode<K, V> parent) {
        TreeNode<K, V> balancedNode = parent;
        if (parent == null || parent.getParent() == null) {
            // 空树或只有两级
            return balancedNode;
        }
        AVLBalancedType balancedType = checkBalanced(balancedNode.getParent());
        if (!balancedType.isBalanced()) {
            if (AVLBalancedType.HIGH_LEFT.equals(balancedType)) {
                balancedNode = simpleRightRotation(parent);
            } else {
                balancedNode = simpleLeftRotation(parent);
            }
        } else {
            if (parent.getParent().getParent() != null) {
                balancedType = checkBalanced(parent.getParent().getParent());
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

    private TreeNode<K, V> simpleLeftRotation(TreeNode<K, V> balancedNode) {
        if (balancedNode.getLeft() == null) {
            return (TreeNode<K, V>) balancedNode.leftRotation(this);
        } else {
            // TODO 待优化
            TreeNode<K, V> childRoot = rebuildRL(balancedNode);
            return (TreeNode<K, V>) childRoot.leftRotation(this);
        }
    }

    private TreeNode<K, V> simpleRightRotation(TreeNode<K, V> balancedNode) {
        if (balancedNode.getRight() == null) {
            return (TreeNode<K, V>) balancedNode.rightRotation(this);
        } else {
            // TODO 待优化
            TreeNode<K, V> childRoot = rebuildLR(balancedNode);
            return (TreeNode<K, V>) childRoot.rightRotation(this);
        }
    }

    private TreeNode<K, V> fixLeftRotation(TreeNode<K, V> balancedNode) {
        if (balancedNode.equals(balancedNode.getParent().getRight())) {
            return (TreeNode<K, V>) balancedNode.getParent().leftRotation(this);
        } else {
            TreeNode<K, V> childRoot = (TreeNode<K, V>) balancedNode.rightRotation(this);
            return (TreeNode<K, V>) childRoot.leftRotation(this);
        }
    }

    private TreeNode<K, V> fixRightRotation(TreeNode<K, V> balancedNode) {
        if (balancedNode.equals(balancedNode.getParent().getLeft())) {
            return (TreeNode<K, V>) balancedNode.getParent().rightRotation(this);
        } else {
            TreeNode<K, V> childRoot = (TreeNode<K, V>) balancedNode.leftRotation(this);
            return (TreeNode<K, V>) childRoot.rightRotation(this);
        }
    }

    /**
     * 中序遍历节点
     *
     * @return 数据
     */
    public List<K> inorderTraversal() {
        if (root == null) {
            return null;
        }
        return root.keys();
    }

    /**
     * 先序遍历节点
     *
     * @return 数据
     */
    public List<K> preOrderTraversal() {
        if (root == null) {
            return null;
        }
        return root.preTraversal();
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
    private TreeNode<K, V> rebuildRL(TreeNode<K, V> x) {
        if (x == null) {
            return null;
        }
        TreeNode<K, V> p = x.getParent();
        TreeNode<K, V> left = x.getLeft();

        left.changeParent(null);
        x.changeLeft(null);
        x.changeParent(null);
        p.changeRight(null);

        p.changeRight(left);
        left.changeParent(p);
        x.changeParent(left);
        left.changeRight(x);

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
    private TreeNode<K, V> rebuildLR(TreeNode<K, V> x) {
        if (x == null) {
            return null;
        }
        TreeNode<K, V> p = x.getParent();
        TreeNode<K, V> right = x.getRight();

        x.changeRight(null);
        right.changeParent(null);
        p.changeLeft(null);
        x.changeParent(null);

        p.changeLeft(right);
        right.changeParent(p);
        x.changeParent(right);
        right.changeLeft(x);

        return right;
    }
}
