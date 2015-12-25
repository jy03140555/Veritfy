package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Vertify Tool
 * @author jy01331184
 * @version 1.0
 */
public class Vertify {

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface VertifyTag {
        /**
         * field's tag for vertify
         */
        String value() default "";

        /**
         * weather this field should be skipped by predifined strategy
         */
        boolean exclude() default false;
    }

    /**
     * vertify a object and it's field recursively to confirm they are not null.
     * @param obj the target object
     * @return this object is not null and it's fields which has been marked VertifyTag are not null.
     */
    public static boolean vertifyNotNull(Object obj)
    {
        return vertify(obj, DEFAULT_NOT_NULL_STRATEGY);
    }

    /**
     * vertify a object and it's field recursively to confirm they are not null.
     * notice that the predefined NOT_NULL vertifacation will be execute before your own strategies,
     * so that you can avoid NULLPOINTER EXECEPTION
     * @param obj the target object
     * @param strategies your own strategies to vertify your object
     * @return the obj and it's fields which has been marked VertifyTag are not null and well vertified by your own strategies.
     */
    public static boolean vertifyNotNull(Object obj,VertifyStrategy... strategies)
    {
        if(strategies == null || strategies.length == 0)
            return vertifyNotNull(obj);
        VertifyStrategy[] arr = new VertifyStrategy[strategies.length + 1];
        arr[0] = DEFAULT_NOT_NULL_STRATEGY;
        System.arraycopy(strategies,0,arr,1,strategies.length);
        return vertify(obj, arr);
    }

    /**
     * vertify a object and it's field recursively to confirm they are not null & empty.
     * @see Vertify#vertifyNotNull(Object)
     * @param obj the target object
     * @return the obj and it's fields which has been marked VertifyTag are not null & empty.
     */
    public static boolean vertifyNotEmpty(Object obj)
    {
        return vertify(obj, DEFAULT_NOT_EMPTY_STRATEGY);
    }

    /**
     * @see Vertify#vertifyNotEmpty(Object)
     * @see Vertify#vertifyNotNull(Object, VertifyStrategy...)
     * @param obj the target object
     * @param strategies your own strategies to vertify your object
     * @return the obj and it's fields which has been marked VertifyTag are not null & empty and well vertified by your own strategies.
     */
    public static boolean vertifyNotEmpty(Object obj,VertifyStrategy... strategies)
    {
        if(strategies == null || strategies.length == 0)
            return vertifyNotEmpty(obj);
        VertifyStrategy[] arr = new VertifyStrategy[strategies.length + 1];

        arr[0] = DEFAULT_NOT_EMPTY_STRATEGY;
        System.arraycopy(strategies,0,arr,1,strategies.length);
        return vertify(obj, arr);
    }

    /**
     * do the vertifaction all by your own strategies.
     * notice that you should avoid NullPointer Exception in VertifyStrategy#judge() by your self
     * @param obj the target object
     * @param strategies your own strategies to vertify your object
     * @return the obj and it's fields which has been marked VertifyTag are well vertified by your own strategies.
     */
    public static boolean vertify(Object obj,VertifyStrategy... strategies)
    {
        if(obj == null || strategies == null || strategies.length == 0)
            return false;
        try {
            List<Entity> entities = new ArrayList<>();
            collect(obj.getClass(),obj, entities,null,null);

            for (Entity entity : entities) {
                for (VertifyStrategy strategy : strategies) {

                    if(strategy.baseStrategy && entity.exclude)
                    {
                        logSkip(strategy,entity);
                        continue;
                    }

                    if(!strategy.judge(entity))
                    {
                        logEntity(strategy, entity,false);
                        return false;
                    }
                    else
                        logEntity(strategy, entity,true);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private static void collect(Class<?> cls,Object obj,  List<Entity> entities,ParentField parentField,Field pfield)
    {
        try {
            Field[] fs = cls.getDeclaredFields();
            for (Field field : fs) {
                VertifyTag ano = field.getAnnotation(VertifyTag.class);
                field.setAccessible(true);
                Entity entity = new Entity();
                entity.field = field;
                entity.value = obj == null?null:field.get(obj);
                entity.parent = new ParentField(parentField);
                entity.parent.parentList.add(pfield);
                if(ano != null )
                {
                    entity.exclude = ano.exclude();
                    entity.tag = ano.value();
                    entities.add(entity);
                }

                if(field.getType().getClassLoader()== Vertify.class.getClassLoader())
                {
                    collect(field.getType(), entity.value, entities,entity.parent,field);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * predifined strategy which can help you vertify null object
     */
    public static final VertifyStrategy DEFAULT_NOT_NULL_STRATEGY = new VertifyStrategy(){
        @Override
        public boolean judge(Entity entity) {
            boolean isNull = entity.value == null;
            entity.reason = isNull?"NULL Object":"";
            return !isNull;
        }

        public String getName() {
            return "NOT NULL VERTIFY";
        }
    };

    /**
     * predifined strategy which can help you vertify null & empty object.
     * you can improve the rules yourself.
     * java.lang.String -> null or ""
     * java.util.Collection -> null or 0 size collection
     * java.util.AbstractMap -> null or 0 size map
     * Array -> null or 0 length array
     */
    public static final VertifyStrategy DEFAULT_NOT_EMPTY_STRATEGY = new VertifyStrategy()
    {
        public boolean judge(Entity entity) {
            if(entity.value == null)
            {
                entity.reason = "NULL Object";
                return false;
            }
            else if(entity.getType() == String.class)
            {
                boolean empty = isTextEmpty(entity.value.toString());
                entity.reason = empty?"empty String":"";
                return !empty;
            }
            else if(entity.value instanceof Collection)
            {
                Collection<?> collection = (Collection<?>) entity.value;
                boolean empty = collection.isEmpty();
                entity.reason = empty?"empty collection":"";
                return !empty;
            }
            else if(entity.value instanceof AbstractMap)
            {
                AbstractMap<?, ?> map = (AbstractMap<?, ?>) entity.value;
                boolean empty = map.isEmpty();
                entity.reason = empty?"empty collection":"";
                return !empty;
            }
            else if(entity.value instanceof Object[])
            {
                Object[] array = (Object[]) entity.value;
                boolean empty = array.length == 0;
                entity.reason = (empty?"empty collection":"");
                return !empty;
            }

            return true;
        };

        public String getName() {
            return "NOT EMPTY VERTIFY";
        };
    };

    static
    {
        DEFAULT_NOT_EMPTY_STRATEGY.baseStrategy = DEFAULT_NOT_NULL_STRATEGY.baseStrategy = true;
    }

    /**
     * detialed log switcher
     */
    public static boolean DEBUG = true;

    private static final void logEntity(VertifyStrategy strategy, Entity entity,boolean result)
    {
        if(DEBUG)
        {
            System.out.println(format(strategy.getName()+":",25)+ "    "+(result?"√":("×"+  (isTextEmpty(entity.reason)?"":("\t\treason:"+entity.reason)  )))+"	"+entity);
        }
    }

    private static final void logSkip(VertifyStrategy strategy, Entity entity)
    {
        if(DEBUG)
            System.out.println(format(strategy.getName()+":",25)+ " skip"+"	"+entity);
    }

    public static abstract class VertifyStrategy
    {
        private boolean baseStrategy = false;

        public abstract boolean judge(Entity entity);

        public String getName(){
            return getClass().getName();
        }
    }

    public static class ParentField
    {
        /**
         * store the parent field link,the first element are null,stands for the root
         * the last element are the entity's direct parent field
         */
        public List<Field> parentList = new ArrayList<>();

        public ParentField(ParentField parentField) {
            if(parentField != null)
                parentList.addAll(parentField.parentList);
        }
    }

    public static class Entity
    {
        boolean exclude;
        Field field;
        Object value;
        ParentField parent;
        String tag;

        String reason;

        @Override
        public String toString() {
            return "\n\tfiled:"+formatField(field)    + "\n\ttag:"+ tag   + "\n\tvalue:"+value+ "\n\tparent:"+ formatField(parent) ;
        }

        public Class<?> getType()
        {
            return field.getType();
        }

        public String getParentFieldName()
        {
            if(parent != null){
                Field lastParent = parent.parentList.get(parent.parentList.size() - 1);
                if(lastParent != null)
                    return lastParent.getName();
            }
            return null;
        }

        public String getFieldName()
        {
            return field.getName();
        }

        public <T> T convert()
        {
            if(value == null)
                return null;
            else
                return (T) value;
        }

    }

    private static String formatField(Field field)
    {
        if(field == null)
            return "";
        return field.getName()+"->"+field.getType();
    }

    private static String formatField(ParentField parentField)
    {
        if(parentField == null)
            return "";
        else
        {
            StringBuffer stringBuffer = new StringBuffer();

            List<Field> parFieldList = parentField.parentList;

            for (int i = parFieldList.size() - 1;i>=0;i--) {
                Field field = parFieldList.get(i);
                if(field != null)
                    stringBuffer.append(field.getName()+"->"+field.getType().getName()+"\t==>\t");
                else
                    stringBuffer.append("null->null");
            }

            return stringBuffer.toString();
        }
    }

    private static boolean isTextEmpty(String str)
    {
        return str == null || str.length() == 0;
    }

    private static String format(String str,int length)
    {
        return String.format("%-"+length+"s", str);
    }

}
