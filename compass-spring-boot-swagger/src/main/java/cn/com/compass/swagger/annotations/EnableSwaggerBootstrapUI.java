package cn.com.compass.swagger.annotations;

import cn.com.compass.swagger.configuration.SecurityConfiguration;
import cn.com.compass.swagger.configuration.SwaggerBootstrapUIConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/***
 * Enable SwaggerBootstrapUi enhanced annotation and use @EnableSwagger2 annotation together.
 *
 * inlude:
 * <ul>
 *     <li>Interface sorting </li>
 *     <li>Interface document download  (word)</li>
 * </ul>
 *
 * @since 1.8.5
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({SwaggerBootstrapUIConfiguration.class, SecurityConfiguration.class})
public @interface EnableSwaggerBootstrapUI {

}
