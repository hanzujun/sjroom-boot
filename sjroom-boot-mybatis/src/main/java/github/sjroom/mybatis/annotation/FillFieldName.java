package github.sjroom.mybatis.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * @author george.ouyang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FillFieldName {

	/**
	 * 调用的类名
	 *
	 */
	Class invoke();

	/**
	 * 调用的方法名
	 *
	 */
	String methodName() default "";

}
