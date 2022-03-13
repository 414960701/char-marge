package com.test.marge;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Description: 字符合并
 * @ClassName: CharMerge
 * @Author: ljd
 * @Date: 2022/03/12
 * @Version: 1.0
 */
public class CharMerge {

    private static String charRegex = "[a-z]*";

    //字母前一个字母字典
    public static Map<Character, Character> repMap = new HashMap<>();

    //字符替换为空字符
    public static Map<Character, Character> emptyRepMap = new HashMap<>();

    //生成前字母字典
    static {
        for (int i = 'b'; i <= 'z'; i++) {
            repMap.put((char) i, (char) (i - 1));
        }
    }

    /**
     * create by: ljd
     * description: 字符合并算法（优化前）
     * create time: 2022/03/12
     *
     * @param ori    原字符串
     * @param repMap 字符替换字典
     * @return String
     */
    //优化前的算法
    public static String oldMerge(String ori, Map<Character, Character> repMap) {
        if (!Pattern.matches(charRegex, ori)) {
            throw new RuntimeException("输入必须是[a-z]");
        }
        int low = 0;
        int high = 1;
        StringBuilder result = new StringBuilder(ori);
        while (high < result.length()) {
            if (result.charAt(low) != result.charAt(high)) {
                if (high - low >= 3) {
                    StringBuilder temp = new StringBuilder();
                    temp.append(result, 0, low);
                    if (repMap.get(result.charAt(low)) != null) {
                        temp.append(repMap.get(result.charAt(low)));
                    }
                    temp.append(result, high, result.length());
                    result = temp;
                    low = low - 2 < 0 ? 0 : low - 2;
                    high = low;
                } else {
                    low = high;
                }
            }
            high++;
        }
        if (high - low >= 3) {
            Character preChar = repMap.get(result.charAt(result.length() - 1));
            String resultTemp = result.substring(0, result.length() - (high - low));
            return preChar == null ? resultTemp : resultTemp + preChar;
        }
        return result.toString();
    }

    private static class Node {
        char val;
        Node next;
        Node last;

        public Node(char val) {
            this.val = val;
        }
    }


    /**
     * create by: ljd
     * description: 字符合并算法（优化后）
     * create time: 2022/03/12
     *
     * @param ori    原字符串
     * @param repMap 字符替换字典
     * @return String
     */
    public static String merge(String ori, Map<Character, Character> repMap) {
        if (!Pattern.matches(charRegex, ori)) {
            throw new RuntimeException("输入必须是[a-z]");
        }
        //空字符串直接返回
        if (ori.length() == 0) {
            return ori;
        }

        //将字符串转为双向链表
        Node head = new Node(ori.charAt(0));
        Node temp = head;
        for (int i = 1; i < ori.length(); i++) {
            temp.next = new Node(ori.charAt(i));
            Node last = temp;
            temp = temp.next;
            temp.last = last;
        }

        //滑动窗口起始和结束指针
        Node low = head;
        Node high = head;

        //滑动窗口大小
        int len = 0;
        //遍历字符串
        while (high != null) {
            //判断起始和结束指针所指向值是否相同
            if (low.val != high.val) {
                //如果窗口大小大于三，此时需要将窗口里的值替换，并回退窗口指针,否则以high位置重置窗口。
                if (len >= 3) {
                    Node mid = null;
                    //获取替换值
                    if (repMap.get(low.val) != null) {
                        mid = new Node(repMap.get(low.val));
                    }
                    Node templow = low.last;
                    //判断窗口是否能向前滑
                    if (templow != null) {
                        //替换滑动窗口里的值
                        if (mid != null) {
                            templow.next = mid;
                            mid.next = high;
                            high.last = mid;
                            mid.last = templow;
                        } else {
                            templow.next = high;
                            high.last = templow;
                        }
                        low = templow.last == null ? templow : templow.last;
                        high = low;
                    } else {
                        high.last = mid;
                        if (mid != null) {
                            mid.next = high;
                            high = mid;
                        }
                        head = high;
                        low = high;
                    }
                } else {
                    low = high;
                }
                //重置窗口大小
                len = 0;
            }
            //窗口扩大
            high = high.next;
            len++;
        }
        //将双向列表转为字符串
        StringBuilder result = new StringBuilder();
        while (head != null) {
            result.append(head.val);
            head = head.next;
        }
        if (len >= 3) {
            Character preChar = repMap.get(result.charAt(result.length() - 1));
            String resultTemp = result.substring(0, result.length() - len);
            return preChar == null ? resultTemp : resultTemp + preChar;
        }
        return result.toString();
    }


}
