package com.kixs.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * url:https://leetcode-cn.com/problems/all-nodes-distance-k-in-binary-tree/
 * <p>
 * 给定一个二叉树（具有根结点 root）， 一个目标结点 target ，和一个整数值 K 。
 * 返回到目标结点 target 距离为 K 的所有结点的值的列表。 答案可以以任何顺序返回。
 * <p>
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/4/26 10:14
 */
public class LeetCode_00863_AllNodesDistanceKInBinaryTree {

    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> res = new ArrayList<>();
        Stack<TagTreeNode> stack = new Stack<>();
        TreeNode node = findTarget(root, target, stack);
        if (node == null) {
            return res;
        }

        fillDistanceKDownVal(node, K, 0, res);
        fillDistanceKUpVal(stack, K, 1, res);

        return res;
    }

    private TreeNode findTarget(TreeNode root, TreeNode target, Stack<TagTreeNode> stack) {
        if (root.val == target.val) {
            return root;
        }
        boolean pushed = false;
        if (root.left != null) {
            stack.push(new TagTreeNode(true, root));
            TreeNode node = findTarget(root.left, target, stack);
            if (node != null) {
                return node;
            }
            pushed = true;
        }
        if (root.right != null) {
            if (pushed) {
                stack.pop();
            }
            stack.push(new TagTreeNode(false, root));
            TreeNode node = findTarget(root.right, target, stack);
            if (node != null) {
                return node;
            }
            pushed = true;
        }
        if (pushed) {
            stack.pop();
        }
        return null;
    }

    private void fillDistanceKDownVal(TreeNode target, int K, int distance, List<Integer> res) {
        if (distance == K) {
            res.add(target.val);
        } else {
            TreeNode left = target.left;
            TreeNode right = target.right;
            if (left != null) {
                fillDistanceKDownVal(left, K, distance + 1, res);
            }

            if (right != null) {
                fillDistanceKDownVal(right, K, distance + 1, res);
            }
        }
    }

    private void fillDistanceKUpVal(Stack<TagTreeNode> stack, int K, int distance, List<Integer> res) {
        if (distance <= K && !stack.empty()) {
            TagTreeNode tagNode = stack.pop();
            TreeNode node = tagNode.node;
            if (distance == K) {
                res.add(node.val);
            }
            TreeNode child;
            if (tagNode.tag) {
                child = node.right;
            } else {
                child = node.left;
            }
            if (child != null) {
                fillDistanceKDownVal(child, K, distance + 1, res);
            }
            fillDistanceKUpVal(stack, K, distance + 1, res);
        }
    }

    private static class TagTreeNode {
        // 当前节点是是通过左节点[true]还是右节点[false]入栈
        boolean tag;

        TreeNode node;

        public TagTreeNode(boolean tag, TreeNode node) {
            this.tag = tag;
            this.node = node;
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        test1();
        test2();
    }

    private static void test1() {
        TreeNode node3 = new TreeNode(3);
        TreeNode node5 = new TreeNode(5);
        TreeNode node1 = new TreeNode(1);
        TreeNode node6 = new TreeNode(6);
        TreeNode node2 = new TreeNode(2);
        TreeNode node0 = new TreeNode(0);
        TreeNode node8 = new TreeNode(8);
        TreeNode node7 = new TreeNode(7);
        TreeNode node4 = new TreeNode(4);

        node3.left = node5;
        node3.right = node1;

        node5.left = node6;
        node5.right = node2;

        node2.left = node7;
        node2.right = node4;

        node1.left = node0;
        node1.right = node8;

        System.out.println(new LeetCode_00863_AllNodesDistanceKInBinaryTree().distanceK(node3, node5, 3));
    }

    private static void test2() {
        TreeNode node0 = new TreeNode(0);
        TreeNode node2 = new TreeNode(2);
        TreeNode node1 = new TreeNode(1);
        TreeNode node3 = new TreeNode(3);

        node0.left = node2;
        node0.right = node1;

        node1.left = node3;

        System.out.println(new LeetCode_00863_AllNodesDistanceKInBinaryTree().distanceK(node0, node3, 3));
    }

}
