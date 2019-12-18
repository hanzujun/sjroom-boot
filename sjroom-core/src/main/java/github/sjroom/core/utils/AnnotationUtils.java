package github.sjroom.core.utils;

import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Katrel.Zhou
 * @version 1.0.0
 * @date 2019/5/17 14:16
 */
public class AnnotationUtils extends org.springframework.core.annotation.AnnotationUtils {

    /**
     * Get the <em>repeatable</em> {@linkplain Annotation annotations} of
     * {@code annotationType} from the supplied {@link AnnotatedElement}, where
     * such annotations are either <em>present</em>, <em>indirectly present</em>,
     * or <em>meta-present</em> on the element.
     *
     * @param element        AnnotatedElement
     * @param annotationType annotationType
     * @param <T>            泛型
     * @return 注解列表
     */
    @Nullable
    public static <T extends Annotation> List<T> findAnnotations(@Nullable AnnotatedElement element, @Nullable Class<T> annotationType) {
        if (null == element || null == annotationType) {
            return null;
        }
        Collection<T> collection = getRepeatableAnnotations(element, annotationType);
        return Collections.unmodifiableList(new ArrayList<>(collection));
    }

    public static <T extends Annotation> boolean isAnnotationPresent(AnnotatedElement element, Class<T> annotation) {
        return element.isAnnotationPresent(annotation);
    }

    /**
     * @param element        AnnotatedElement
     * @param annotationType annotationType
     * @param <A>            泛型
     * @return 注解列表
     */
    public static <A extends Annotation> Collection<A> getAllMergedAnnotations(AnnotatedElement element, Class<A> annotationType) {
        return AnnotatedElementUtils.getAllMergedAnnotations(element, annotationType);
    }

    /**
     * 获取Annotation
     *
     * @param method         Method
     * @param annotationType 注解类
     * @param <A>            泛型标记
     * @return {Annotation}
     */
    @Nullable
    public static <A extends Annotation> A getAnnotation(Method method, Class<A> annotationType) {
        Class<?> targetClass = method.getDeclaringClass();
        // The method may be on an interface, but we need attributes from the target class.
        // If the target class is null, the method will be unchanged.
        Method specificMethod = ClassUtil.getMostSpecificMethod(method, targetClass);
        // If we are dealing with method with generic parameters, find the original method.
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
        // 先找方法，再找方法上的类
        A annotation = AnnotatedElementUtils.findMergedAnnotation(specificMethod, annotationType);
        if (null != annotation) {
            return annotation;
        }
        // 获取类上面的Annotation，可能包含组合注解，故采用spring的工具类
        return AnnotatedElementUtils.findMergedAnnotation(specificMethod.getDeclaringClass(), annotationType);
    }

    /**
     * 获取Annotation
     *
     * @param handlerMethod  HandlerMethod
     * @param annotationType 注解类
     * @param <A>            泛型标记
     * @return {Annotation}
     */
//    @Nullable
//    public static <A extends Annotation> A getAnnotation(HandlerMethod handlerMethod, Class<A> annotationType) {
//        // 先找方法，再找方法上的类
//        A annotation = handlerMethod.getMethodAnnotation(annotationType);
//        if (null != annotation) {
//            return annotation;
//        }
//        // 获取类上面的Annotation，可能包含组合注解，故采用spring的工具类
//        Class<?> beanType = handlerMethod.getBeanType();
//        return AnnotatedElementUtils.findMergedAnnotation(beanType, annotationType);
//    }

}
