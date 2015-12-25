package com.example;


import java.util.List;

public class School {

    @Vertify.VertifyTag
    List<Clazz> classes;
	
	@Vertify.VertifyTag("tag1")
    String schoolName = "哈尔滨市第三中学";

}	

class Clazz
{
	public Clazz(String clsName, Teacher teacher) {
		this.clsName = clsName;
		this.teacher = teacher;
	}

	String clsName;
	Teacher teacher ;
}

class Teacher
{
	String name;

	public Teacher(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return "Teacher [name=" + name + "]";
	}
}
