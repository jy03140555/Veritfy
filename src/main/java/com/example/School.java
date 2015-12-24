package com.example;


public class School {
	
	@Vertify.VertifyTag("2")
	Clazz clazzA = new Clazz("081421","yt");
	
	//@Vertify.VertifyTag("2")
	Clazz clazzB = new Clazz("081421","kz");
	
	@Vertify.VertifyTag
	private String schoolName = "17";
}	

class Clazz
{
	
	
	public Clazz(String clsName, String teacher) {
		super();
		this.clsName = clsName;
		this.teacher = new Teacher(teacher);
	}

	String clsName;
	

	Teacher teacher ;
 

	@Override
	public String toString() {
		return "Clazz [clsName=" + clsName + "]";
	}
	
	
}

class Teacher
{
	@Vertify.VertifyTag("z")
	String name;

	public Teacher(String name) {
		super();
		this.name = name;
	}
	
	@Vertify.VertifyTag(exclude = true)
	String[] students = new String[10];
	
	@Override
	public String toString() {
		return "Teacher [name=" + name + "]";
	}
	
}
