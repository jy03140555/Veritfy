package com.example;

import java.util.ArrayList;
import java.util.List;

public class Test1 {

    public static void main(String[] args) {
        //test0  vertify object null
        Vertify.DEBUG = true;

        School school0 = null;

        System.out.println(Vertify.vertifyNotNull(school0));
        System.out.println("测试0↑==============================\n");

        //test1  vertify object null
        School school1 = new School();
        System.out.println(Vertify.vertifyNotNull(school1));
        System.out.println("测试1↑==============================\n");

        //test2  vertify object null
        School school2 = new School();
        school2.classes = new ArrayList<>();
        System.out.println(Vertify.vertifyNotNull(school2));
        System.out.println("测试2↑==============================\n");

        //test3  vertify object empty
        School school3 = new School();
        school3.classes = new ArrayList<>();
        System.out.println(Vertify.vertifyNotEmpty(school3));
        System.out.println("测试3↑==============================\n");

        //test4  vertify object empty
        School school4 = new School();
        school4.classes = new ArrayList<>();
        school4.classes.add(new Clazz("class 1",new Teacher("kobe")));
        System.out.println(Vertify.vertifyNotEmpty(school4));
        System.out.println("测试4↑==============================\n");

        //test5  diy verity based on test4 ,vertify school's name & classes's count for more
        School school5 = new School();
        school5.classes = new ArrayList<>();
        school5.classes.add(new Clazz("class 1",new Teacher("kobe")));
        System.out.println(Vertify.vertifyNotEmpty(school5, new Vertify.VertifyStrategy() {
            @Override
            public boolean judge(Vertify.Entity entity) {
                if(entity.tag.equals("tag1")) //school name with tag
                {
                    boolean result = entity.value.equals("哈尔滨市第三中学");
                    entity.reason = result?"":"wrong school name";
                    return result;
                }
                else if(entity.getFieldName().equals("classes")) //school classes
                {
                    List<Clazz> classes = entity.convert();
                    boolean result = classes.size() == 5;
                    entity.reason = result?"":"wrong classes count";
                    return result;
                }
                return true;
            }

            @Override
            public String getName() {
                return "DIY VERTIFY";
            }
        }));
        System.out.println("测试5↑==============================\n");
    }
}
