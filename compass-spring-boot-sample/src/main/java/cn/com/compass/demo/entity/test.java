package cn.com.compass.demo.entity;

import cn.com.compass.data.util.LogicDeleteUtil;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/5/3 22:40
 */
public class test {

    public static void main(String[] args) throws Exception{
       Class<Demo> demoClass = (Class<Demo>) LogicDeleteUtil.addDeleteAnnotation2DomainClass(Demo.class);
        SQLDelete delete = demoClass.getAnnotation(SQLDelete.class);
        Where where = demoClass.getAnnotation(Where.class);
        System.out.println(delete.sql());
        System.out.println(where.clause());
    }
}
