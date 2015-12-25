package com.example;


import java.lang.reflect.Field;

public class Test2 {

    public static void main(String[] args) {
        //test for a complex situation
        //to sim a company with ceo(CEO),vice ceo(CEO),secretary(Woman). both ceo&viceCeo has a wife(Woman)
        //and vertify CEO's name and Woman's age
        Vertify.DEBUG = true;
        Company company = new Company();

        company.ceo = new CEO("boss");
        company.ceo.wife = new Woman("boss wife",30);

        company.viceCeo = new CEO("second boss");
        company.viceCeo.wife = new Woman("second boss wife",18);

        company.secretary = new Woman("company secretary",20);

        boolean result = Vertify.vertify(company, new Vertify.VertifyStrategy() {  //vertify without notNUll or notEmpty,all task will be done by yourself
            @Override
            public boolean judge(Vertify.Entity entity) {

                if(entity.getFieldName().equals("ceo")) //vertify ceo
                {
                    CEO ceo = entity.convert();
                    return ceo.name.equals("boss");
                }
                else if(entity.getFieldName().equals("viceCeo")) //vertify viceCeo
                {
                    CEO viceCeo = entity.convert();
                    return viceCeo.name.equals("second boss");
                }
                else if(entity.getFieldName().equals("age")) //vertify women's age
                {
                    int age = entity.convert();
                    if(entity.getParentFieldName().equals("secretary")) // company secretary's age
                    {
                        return age == 20;
                    }
                    else if(entity.getParentFieldName().equals("wife")) // CEO's wife
                    {
                        Field parent_parent = entity.parent.parentList.get(1);
                        if(parent_parent.getName().equals("ceo"))   //ceo's wife
                        {
                            return age == 30;
                        }
                        else if(parent_parent.getName().equals("viceCeo")) // viceCeo's wife
                        {
                            return age == 18;
                        }
                    }
                }
                return true;
            }
        });

        System.out.println("vertify result:"+result);
    }

}
