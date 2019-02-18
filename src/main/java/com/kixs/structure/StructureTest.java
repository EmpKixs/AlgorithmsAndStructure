package com.kixs.structure;

import com.kixs.structure.tree.BinarySearchTree;
import com.kixs.structure.tree.avl.NormalAvl;
import com.kixs.util.RandomIntGenerator;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class StructureTest {

    public static void main(String[] args) {
        // binarySearchTreeTest();
        normalAVLTest();
    }

    private static void normalAVLTest() {
        NormalAvl<Integer, Integer> avl = new NormalAvl<>();
        String dataFileName = RandomIntGenerator.getDataFileName(50);
        Integer[] data = RandomIntGenerator.getRandomIntFromFile(dataFileName);
        Stream.of(data).forEach(e -> avl.put(e, e));
        // removeLeafTest(avl);
        // removeSingleLeaf(avl);
        // removeDoubleChild(avl);
        boolean isBalanced = avl.isBalanced();
        System.out.println(isBalanced ? "AVL is balanced!" : "AVL is not balanced!");
        System.out.println(avl.inorderTraversal().stream().map(String::valueOf).collect(Collectors.joining(",")));
        System.out.println(avl.preOrderTraversal().stream().map(String::valueOf).collect(Collectors.joining(",")));
    }

    private static void removeDoubleChild(NormalAvl<Integer, Integer> avl) {
//        avl.remove(1);
//        avl.remove(5);
//        avl.remove(8);
//        avl.remove(12);
        avl.remove(17);
//        avl.remove(21);
//        avl.remove(23);
//        avl.remove(28);
//        avl.remove(36);
//        avl.remove(42);
//        avl.remove(44);
    }

    private static void removeSingleLeaf(NormalAvl<Integer, Integer> avl) {
//        avl.remove(2);
//        avl.remove(7);
//        avl.remove(16);
//        avl.remove(16);
//        avl.remove(19);
//        avl.remove(27);
        avl.remove(38);
    }

    private static void removeLeafTest(NormalAvl<Integer, Integer> avl) {
//        avl.remove(0);
//        avl.remove(3);
//        avl.remove(6);
//        avl.remove(11);
//        avl.remove(15);
//        avl.remove(20);
//        avl.remove(22);
//        avl.remove(24);
//        avl.remove(34);
//        avl.remove(41);
//        avl.remove(43);
        avl.remove(45);
    }

    private static void binarySearchTreeTest() {
        String dataFileName = RandomIntGenerator.getDataFileName(10);
        Integer[] data = RandomIntGenerator.getRandomIntFromFile(dataFileName);
        BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
        Stream.of(data).forEach(e -> {
            Integer count = bst.get(e);
            if (count == null) {
                bst.put(e, 1);
            } else {
                bst.put(e, count + 1);
            }
        });
        System.out.println("preOrder:" + bst.preOrderKey());
        System.out.println("inOrder:" + bst.inOrderKey());
        System.out.println("postOrder:" + bst.postOrderKey());
        System.out.println();
        bst.delete(6);
        System.out.println("preOrder:" + bst.preOrderKey());
        System.out.println("inOrder:" + bst.inOrderKey());
        System.out.println("postOrder:" + bst.postOrderKey());
    }
}
