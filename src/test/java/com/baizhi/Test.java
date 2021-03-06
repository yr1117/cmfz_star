package com.baizhi;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest(classes = CmfzStarApplication.class)
public class Test {
    //99乘法表
    @org.junit.jupiter.api.Test
    public void test(){
        for (int i = 1 ; i<= 9 ; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + "*" + i + "=" + (j * i)+"\t");
            }
            System.out.println();
        }
    }

    //字符串输出每个字符出现的次数
    @org.junit.jupiter.api.Test
    public void test1(){
        Map<Character, Integer> map = new HashMap<>();
        String str = "aaaccfgas";
       for (int i = 0 ; i < str.length(); i++){
           char c = str.charAt(i);
          if(map.containsKey(c)){
            map.put(c,map.get(c)+1);
          }else{
              map.put(c,1);
          }
       }
        Set<Map.Entry<Character, Integer>> entry = map.entrySet();
        for (Map.Entry<Character, Integer> entries : entry) {
            System.out.println(entries.getKey()+"出现了"+entries.getValue()+"次");
        }
    }

    //List<String> num = Arrays.asList("4","6","3","1","5","2") ;排序  升序（使用for循环，写出代码）
    @org.junit.jupiter.api.Test
    public void test2() {
        List<String> num = Arrays.asList("4", "6", "3", "1", "5", "2");
        String[] array = (String[]) num.toArray();
        for (int i = 0; i < array.length; i++) {

        }
    }
}
