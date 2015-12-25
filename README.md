<h1 align="center">**Vertify** vertifies your model</h1><br>
####to vertify your model object ridiculously like this? Oh Aaauuh~
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
####Advantage Feathures
* **1  use strategy mode to add individual vertifcation**

  ```Java
  Vertify.VertifyStrategy strategy = new Vertify.VertifyStrategy()
  {
    @Override
    public boolean judge(Vertify.Entity entity) {
      if(entity.getFieldName().equals("classes")) // List<Clazz> classes 
      {
          List<Clazz> classes = entity.convert();
          return classes.size()==2;
      }
      return true;
    }
  }
  ```
* **2  very detailed and formatted output info for your vertifcation**

  ```c
  NOT NULL VERTIFY:            ×		reason:NULL Object	
  	filed:classes->interface java.util.List
  	tag:
  	value:null
  	parent:null->null
  NOT NULL VERTIFY:            √	
  	filed:schoolName->class java.lang.String
  	tag:tag1
  	value:哈尔滨市第三中学
  	parent:null->null
false
  ```
* **3  very flexible way to combine strategies**

    **there are predefined vertify strategies for judge null or empty object**<br>
  ```Java
    public static boolean vertifyNotNull(Object obj);
    public static boolean vertifyNotEmpty(Object obj);
  ```
    **to use your own strategy together with predefined strategy**<br>
  ```Java
    public static boolean vertifyNotNull(Object obj,VertifyStrategy... strategies)
    public static boolean vertifyNotEmpty(Object obj,VertifyStrategy... strategies)
  ```
    **to vertify object all by yourself**<br>
  ```Java
    public static boolean vertify(Object obj,VertifyStrategy... strategies)
  ```
see TestCase & [wiki](https://github.com/jy01331184/magicLib/wiki) for more usage & information<br>
ps.for android proguarding  
```c
-keep class com.example.Vertify$* {*;}
```
