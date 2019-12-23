package github.sjroom.mybatis.fill;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import github.sjroom.common.util.CollectionUtil;
import github.sjroom.common.util.JsonUtil;
import github.sjroom.common.util.StringPool;
import github.sjroom.common.util.StringUtil;
import github.sjroom.mybatis.annotation.FillFieldName;
import github.sjroom.mybatis.page.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 填充字典值和用户名称
 *
 * @author george.ouyang
 */
@Aspect
@Configuration
@Slf4j
@SuppressWarnings("unchecked")
public class FillFieldNameAspect {

    private Map<Class, FillFieldNameObject> classFillFieldNameObjectMap = Maps.newHashMapWithExpectedSize(8);

    /**
     * 1.拦截FillFieldNameText.class
     * 2.从返回值对象上获取字段注解信息
     * 3.循环返回值设置字典值
     * 4.如果有用户注解，再次循环设置用户名，否则直接返回
     * <p>
     * This is the method which I would like to execute after a selected method execution.
     */
    @AfterReturning(pointcut = "@annotation(github.sjroom.mybatis.annotation.FillField))", returning = "retVal")
    public void afterReturningAdvice(JoinPoint jp, Object retVal) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        if (Objects.isNull(method)) {
            return;
        }

        Object retValObject;
        Collection retCollection;
        if (retVal instanceof Collection) {
            retCollection = (Collection) retVal;
            if (CollectionUtil.isEmpty(retCollection)) {
                return;
            }
            retValObject = retCollection.iterator().next();
        } else if (retVal instanceof PageResult) {
            retCollection = ((PageResult) retVal).getRecords();
            if (CollectionUtil.isEmpty(retCollection)) {
                return;
            }
            retValObject = retCollection.iterator().next();
        } else {
            retCollection = Lists.newArrayList(retVal);
            retValObject = retVal;
        }
        FillFieldNameObject fillFieldNameObject = new FillFieldNameObject();
        Class<?> retValObjectClass = retValObject.getClass();
        if (classFillFieldNameObjectMap.get(retValObjectClass) == null) {
            Field[] fields = retValObjectClass.getDeclaredFields();

            for (Field field : fields) {
                FillFieldName fillFieldName = field.getAnnotation(FillFieldName.class);
                if (Objects.isNull(fillFieldName)) {
                    continue;
                }
                Class invokeClass = fillFieldName.invoke();
                String methodName = fillFieldName.methodName();
                String getFieldValue = "get" + StringUtil.capitalize(field.getName().replace("Name", ""));
                fillFieldNameObject.getFieldInfoSet().add(new FillFieldNameObject.FieldInfo(getFieldValue, field));
//                classFillFieldNameObjectMap.put(retValObjectClass, fillFieldNameObject);

            }
        } else {
            fillFieldNameObject = classFillFieldNameObjectMap.get(retValObjectClass);
        }

        fillVal(retCollection, fillFieldNameObject);
    }


    private void fillVal(Collection retCollection, FillFieldNameObject fillFieldNameObject) {
        if (CollectionUtil.isEmpty(fillFieldNameObject.getFieldInfoSet())) {
            return;
        }

        Set<Long> userIdSet = Sets.newHashSetWithExpectedSize(retCollection.size());

        retCollection.forEach(o -> fillFieldNameObject.getFieldInfoSet().forEach(fieldInfo -> {
            Field field = fieldInfo.getField();
            field.setAccessible(true);

            try {
                Method fieldedMethod = o.getClass().getMethod(fieldInfo.getFieldName());
                Object fieldValue = fieldedMethod.invoke(o);

//                Method fieldedMethod1 = o.getClass().getMethod(fieldInfo.getFieldName(), String.class);
//                fieldedMethod1.invoke(o, fieldValue);
                field.set(o, String.valueOf(fieldValue));
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                log.error("fillVal error cause:", e);
            }
        }));

        if (CollectionUtil.isEmpty(userIdSet)) {
            return;
        }
    }
}
