package com.example;

public class Company {

    @Vertify.VertifyTag
    CEO ceo;

    @Vertify.VertifyTag
    CEO viceCeo;

    Woman secretary;
}

class CEO
{
    String name;

    public CEO(String name) {
        this.name = name;
    }

    Woman wife;
}

class Woman
{
    String name;

    @Vertify.VertifyTag
    int age;

    public Woman(String name,int age) {
        this.name = name;
        this.age = age;
    }
}
