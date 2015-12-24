package com.example;

/**
 * Created by a2014-498 on 15/12/24.
 */
public class Test {

    public static void main(String[] args) {

        School school = new School();

        //boolean result = Vertify.vertifyNotNull(school);
        boolean result = Vertify.vertifyNotEmpty(school, new Vertify.VertifyStrategy() {
            @Override
            public boolean judge(Vertify.Entity entity) {
                if(entity.getFieldName().equals("schoolName"))
                {
                    return entity.value.equals("17");
                }
                if(entity.tag.equals("2"))
                {
                    Clazz clazz = entity.convert();
                    return clazz.clsName.equals("081421");

                }
                return true;
            }

        });
        System.out.println("result:"+result);



    }
}
