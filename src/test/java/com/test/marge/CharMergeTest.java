package com.test.marge;


import org.junit.Test;

/**
 * @Description:  字符合并测试
 * @ClassName:      CharMergeTest
 * @Author:  ljd
 * @Date:    2022/03/12
 * @Version:    1.0
 */
public class CharMergeTest {

    //问题1
    @Test
    public void stage1(){
        String input = "aabcccbbad";
        String output = CharMerge.merge(input,CharMerge.emptyRepMap);
        System.out.println(input);
        System.out.println(output);
    }

    //问题2
    @Test
    public void stage2(){
        String input = "abcccbad";
        String output = CharMerge.merge(input,CharMerge.repMap);
        System.out.println(input);
        System.out.println(output);
    }

    //其他正常案例测试
    @Test
    public void other(){
        System.out.println("输入为空测试");
        StringBuilder input = new StringBuilder("");
        String output = CharMerge.merge(input.toString(),CharMerge.repMap);
        System.out.println(input);
        System.out.println(output);

        System.out.println("随机案例测试");
        for (int i = 0; i < Math.random()*100; i++) {
            char randomChar = (char)(Math.random()*26+'a');
            input.append(randomChar);
            if(Math.random()*10>5){
                input.append(randomChar).append(randomChar);
            }
        }
        output = CharMerge.merge(input.toString(),CharMerge.repMap);
        System.out.println(input);
        System.out.println(output);

        System.out.println("性能测试");
        input = new StringBuilder("abcccbad");
        for(int i =0;i<1000;i++){
            input.append("abcccbad");
        }
        long t1 = System.currentTimeMillis();
        output = CharMerge.merge(input.toString(),CharMerge.repMap);
        long t2 = System.currentTimeMillis();
        System.out.println(input);
        System.out.println(output);
        long useTime = t2 - t1;
        System.out.println("优化后耗时："+useTime+"ms");
        //旧方法对比
        t1 = System.currentTimeMillis();
        output = CharMerge.oldMerge(input.toString(),CharMerge.repMap);
        t2 = System.currentTimeMillis();
        useTime = t2 - t1;
        System.out.println(input);
        System.out.println(output);
        System.out.println("优化前耗时："+useTime+"ms");
    }

    //异常测试
    @Test
    public void exTest(){
        String input = "348727529ahduiahf162dsu的";
        String output = CharMerge.merge(input,CharMerge.repMap);
        System.out.println(input);
        System.out.println(output);
    }


}
