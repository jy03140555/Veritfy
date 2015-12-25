<h1 align="center">**Vertify** vertifies your model</h1><br>
####to vertify your model object like this? Oh Aaauuh~
```Java
if(school != null && school.classes != null && school.classes.size()>0 && school.teacher != null
                  && school.teacher.name != null && school.teacher.student != null && school.teacher.student.name != null)
  doSomething();
```
Now it can be done by a single line code below
```Java
if(Vertify.vertifyNotEmpty(school))
  doSomething();
```
a simple **VertifyTag** annotation is enough. the **Vertify** do the rest for you
```Java
  public class School {

    @Vertify.VertifyTag
    List<Clazz> classes;

    Teacher teacher;
  }

  class Teacher
  {
    @Vertify.VertifyTag
    String name;
    
    Student student;
  }
  
  class Student
  {
    @Vertify.VertifyTag
    String name;
  }
```
see TestCase & [wiki](https://github.com/jy01331184/magicLib/wiki) for more information<br>
ps.for android proguarding  
```c
-keep class com.example.Vertify$* {*;}
```
