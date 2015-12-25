<h1 align="center">Vertify 验证model</h1><br>
####to vertify your model object like this? Oh Aaauuh~
```Java
if(school != null && school.classes != null && school.classes.size()>0 && school.teacher != null && school.teacher.name != null ....)
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
  }
```
more [information](https://github.com/jy01331184/magicLib/wiki)<br>
ps.for android proguarding  
```c
-keep class com.example.Vertify$* {*;}
```
