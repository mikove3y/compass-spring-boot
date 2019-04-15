package cn.com.compass.data.interceptor;

import javax.persistence.*;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/12 11:21
 */
public class JpaCrudInterceptor {

    /**
     * 在保存之前调用
     */
    @PrePersist
    public void prePersist(Object source){
        System.out.println("@PrePersist：" + source);
    }

    /**
     * 在保存之后调用
     */
    @PostPersist
    public void postPersist(Object source){
        System.out.println("@PostPersist：" + source);
    }

    /**
     * 删除前调用
     * @param source
     */
    @PreRemove
    public void preRemove(Object source){
        System.out.println("@PreRemove：" + source);
    }

    /**
     * 删除之后调用
     * @param source
     */
    @PostRemove
    public void postRemove(Object source){
        System.out.println("@PostRemove：" + source);
    }

    @PreUpdate
    public void preUpdate(Object source){
        System.out.println("@PostRemove：" + source);
    }

    @PostUpdate
    public void postUpdate(Object source){
        System.out.println("@PostRemove：" + source);
    }

    /**
     * 查询后调用
     * @param source
     */
    @PostLoad
    public void postLoad(Object source){
        System.out.println("@PostLoad：" + source);
    }


}
