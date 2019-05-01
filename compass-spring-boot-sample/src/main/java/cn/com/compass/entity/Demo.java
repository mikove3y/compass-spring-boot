package cn.com.compass.entity;

import cn.com.compass.data.annotation.EnableLogicDelete;
import cn.com.compass.data.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 16:21
 */
@Entity
@Data
@DynamicInsert
@DynamicUpdate
@EnableLogicDelete // 开启逻辑删除
public class Demo extends BaseEntity<Long> {

    private String name;

    private String business;

    private Date time;
}
