package cn.com.compass.swagger.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/3/26 16:49
 */
public class AnnotationUtil {

    /**
     * 获取annotation 属性值
     * @param anno
     * @return
     */
    public static Map<String,Object> getAnnotationMemberValues(Annotation anno){
        InvocationHandler invo = Proxy.getInvocationHandler(anno);
        return (Map<String, Object>) getFieldValue(invo,"memberValues");
    }

    /**
     * 获取对象属性值
     * @param object
     * @param property
     * @param <T>
     * @return
     */
    public static <T> Object getFieldValue(T object, String property) {
        if (object != null && property != null) {
            Class<T> currClass = (Class<T>) object.getClass();
            try {
                Field field = currClass.getDeclaredField(property);
                field.setAccessible(true);
                return field.get(object);
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException(currClass + " has no property: " + property);
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
