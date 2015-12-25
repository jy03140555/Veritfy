<h1 align="center">Vertify 验证model</h1><br>
####你还在用垃圾代码在对你的model进行判断么 <br>to vertify your model object like this? Oh Aaauuh~
```Java
if(school != null && school.classes != null && school.classes.size()>0 && school.teacher != null && school.teacher.name != null ....)
  doSomething();
```
使用Vertify 只需要一行代码帮你搞定所有的事<br>
Now it can be done by a single line code below
```Java
if(Vertify.vertifyNotEmpty(school))
  doSomething();
```
model类很简单,只需要在你关注的对象属性上加一个VertifyTag的注解就可以.剩下的事...Vertify来帮你完成
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
更多功能详见[wiki](https://github.com/jy01331184/magicLib/wiki)<br>
more [information](https://github.com/jy01331184/magicLib/wiki)<br>
ps.如果在android中使用的话 请添加混淆<br>
for android proguard, 
```c
-keep class com.meilishuo.app.utils.Vertify$* {*;}
```
