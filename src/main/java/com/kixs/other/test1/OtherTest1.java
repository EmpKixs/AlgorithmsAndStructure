package com.kixs.other.test1;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * @author wangbing
 * @version 1.0
 * @date 2019/3/21 11:12
 * Copyright: Copyright (c) 2019
 */
public class OtherTest1 {

    public static void main(String[] args) {
        // quoteTest();
        cycleTreeTest();
    }

    private static void cycleTreeTest() {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node31 = new TreeNode(3);
        TreeNode node21 = new TreeNode(2);
        TreeNode node11 = new TreeNode(1);


        node1.addChild(node2);
        node1.addChild(node3);
        node2.addChild(node31);
        node3.addChild(node21);
        node3.addChild(node4);
        node21.addChild(node11);

        System.out.println(cycleTreeDFS(node1));
    }

    private static void quoteTest() {
        int a = 5;
        Integer b = 5;
        change(a);
        change(b);
        System.out.println(a + b);
    }

    private static void change(int a) {
        a = 4;
    }
    private static void change(Integer b) {
        b = 4;
    }

    public static String cycleTreeDFS(TreeNode root) {
        Stack<Integer> stack = new Stack<>();
        return cycleTreeDFS(root, stack);
    }

    private static String cycleTreeDFS(TreeNode root, Stack<Integer> stack) {
        if (stack.contains(root.val)) {
            Stack<String> path = new Stack<>();
            path.push(root.val + "");
            while (!stack.empty()) {
                path.push(" --> ");
                path.push(stack.pop() + "");
            }
            StringBuilder builder = new StringBuilder();
            while (!path.empty()) {
                builder.append(path.pop());
            }
            return builder.toString();
        }
        if (root.children == null || root.children.size() == 0) {
            return null;
        }
        stack.push(root.val);
        for (TreeNode child : root.children) {
            String path = cycleTreeDFS(child, stack);
            if (path != null) {
                return path;
            }
        }
        stack.pop();
        return null;
    }

    public static class TreeNode {
        int val;
        List<TreeNode> children;
        TreeNode(int x) {
            val = x;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public List<TreeNode> getChildren() {
            return children;
        }

        public void setChildren(List<TreeNode> children) {
            this.children = children;
        }

        public void addChild(TreeNode child) {
            if (Objects.isNull(this.children)) {
                this.children = Lists.newArrayList();
            }
            this.children.add(child);
        }
    }

}
