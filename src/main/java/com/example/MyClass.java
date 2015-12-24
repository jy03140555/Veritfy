package com.example;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.TimeZone;

public class MyClass {

    @interface MA
    {
        String type() default "abc";

    }

    public static void main(String[] args)
    {

        Calendar c1 = convert("1443628800");
        Calendar c2 = convert("1444736829");
//        Calendar c2 = convert("1444559497");

        Calendar c3 = Calendar.getInstance();
        c3.add(Calendar.HOUR, 3);

        System.out.println(c3.getTimeInMillis());

        System.out.println(c1.getTime().toLocaleString());
        System.out.println(c2.getTime().toLocaleString());

        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>(0,0.75f,true);


        linkedHashMap.put("1","11");
        linkedHashMap.put("2","22");
        linkedHashMap.put("3","33");
        linkedHashMap.put("4", "44");


        linkedHashMap.get("2");
        linkedHashMap.get("3");


        System.out.println(linkedHashMap.entrySet());


        int array[] = {1,3,5,7,9};

        int index = bSearch(array,8);

        index = index>=0?index:~index;

        System.out.println(index);


        System.out.println("=========");
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTimeInMillis());
        calendar.add(Calendar.HOUR,15);
        System.out.println(calendar.getTimeInMillis());


        System.out.println(String.format("%-18s", "abcd") +"0123");
        System.out.println(String.format("%-18s", "abcdefg") +"0456");
    }


    public static int bSearch(int[] array,int target)
    {
        int start = 0;
        int end = array.length -1;
        int index = (end + start)/2;

        while(start <= end )
        {
            int value = array[index];

            if(value > target)
                end = index-1;
            else if(value < target)
                start = index+1;
            else
                return index;

            index = (end + start)/2;
        }

        return ~index;
    }


    private static Calendar convert(String str) {
        try {
            Calendar result = Calendar.getInstance();
            result.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            result.setTimeInMillis(Long.parseLong(str+"000"));

            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
