package cn.com.compass;

import cn.com.compass.data.repository.BaseEntityRepositoryFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan("cn.com.compass.**.entity.**") // 扫描实体entityManager
@EnableTransactionManagement(proxyTargetClass = true) // 开启事物
@EnableJpaRepositories(value = "cn.com.compass.**.repository.**", repositoryFactoryBeanClass = BaseEntityRepositoryFactoryBean.class)
// 开启jpa
@MapperScan("cn.com.compass.**.mapper.**") // 扫描mybatis mapper接口，免@Mapper注解
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
