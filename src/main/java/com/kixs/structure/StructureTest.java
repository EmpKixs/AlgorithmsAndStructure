package com.kixs.structure;

import com.google.common.collect.Lists;
import com.kixs.structure.avl.NormalAVL;
import com.kixs.util.RandomIntGenerator;

import java.util.List;
import java.util.stream.Collector;
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
        NormalAVL<Integer> avl = new NormalAVL<>();
        /*List<Integer> data = Lists.newArrayList(5,6,7,4,4,5,3,8,1,9,2,6,8,7,9,3,1);*/
        String dataFileName = RandomIntGenerator.getDataFileName(32);
        Integer[] data = RandomIntGenerator.getRandomIntFromFile(dataFileName);
        Stream.of(data).forEach(avl::put);
        System.out.println(avl.inorderTraversal().stream().map(String::valueOf).collect(Collectors.joining(",")));
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
