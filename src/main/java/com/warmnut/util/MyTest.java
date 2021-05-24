package com.warmnut.util;

import java.lang.reflect.Field;

/**
 * 测试时使用的一些方法
 */
public class MyTest {
    /**
     * 通过反射机制打印对象的所有属性
     * 用于测试子包中的代码，因为子包好像打不了断点
     * @param obj 对象
     */
    public static void printSth(Object obj){
        String clazzName = obj.getClass().getName();
        System.out.println("类名: "+clazzName+" {");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 对于每个属性，获取属性名
            String varName = field.getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = field.isAccessible();
                // 修改访问控制权限
                field.setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o;
                try {
                    o = field.get(obj);
                    System.out.println("    "+varName + " : " + o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                // 恢复访问控制权限
                field.setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("}");
    }

}
