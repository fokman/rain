package com.rain.web;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Test2 {
    public static void main(String[] args) {
        ListNode node1 = new ListNode(10);
        ListNode node2 = new ListNode(9);
        node1.next = node2;
        ListNode node3 = new ListNode(8);
        node2.next = node3;
        ListNode node4 = new ListNode(7);
        node3.next = node4;
        Test2 test2 = new Test2();
        ArrayList<Integer> t = test2.printListFromTailToHead(node1);
        for (Integer s : t) {
            System.out.println(s);
        }

    }


    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        ListNode tmp = listNode;
        while(tmp !=null){
            Integer v = tmp.val;
            list.add(0,v);
            tmp = tmp.next;

        }
        return list;
    }
}
