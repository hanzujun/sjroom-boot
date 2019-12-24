package github.sjroom.mybatis.fill;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import github.sjroom.common.context.SpringExtensionLoader;
import github.sjroom.common.exception.BusinessException;
import github.sjroom.common.util.*;
import github.sjroom.common.web.AccessWebConfigurer;
import github.sjroom.mybatis.annotation.FillFieldName;
import github.sjroom.mybatis.page.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
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
     * <p>
     * This is the method which I would like to execute after a selected method execution.
     */
    @Around(value = "@annotation(github.sjroom.mybatis.annotation.FillField))")
    public Object aroundApi(ProceedingJoinPoint point) throws Throwable {
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        Class<?> returnType = method.getReturnType();
        Object retVal = null;

        if (void.class == returnType) {
            point.proceed();
            return retVal;
        }
        retVal = point.proceed();

        Object retValObject;
        Collection retCollection;
        if (retVal instanceof Collection) {
            retCollection = (Collection) retVal;
            if (CollectionUtil.isEmpty(retCollection)) {
                return null;
            }
            retValObject = retCollection.iterator().next();
        } else if (retVal instanceof PageResult) {
            retCollection = ((PageResult) retVal).getRecords();
            if (CollectionUtil.isEmpty(retCollection)) {
                return null;
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
                fillFieldNameObject.getFieldInfoSet().add(new FillFieldNameObject.FieldInfo(field, fillFieldName.invoke(),
                        fillFieldName.invokeMethod(), new HashSet<>(), new HashMap()));
                classFillFieldNameObjectMap.put(retValObjectClass, fillFieldNameObject);
            }
        } else {
            fillFieldNameObject = classFillFieldNameObjectMap.get(retValObjectClass);
        }

        return fillVal(retVal, retCollection, fillFieldNameObject);
    }


    /**
     * 开始反射赋值
     *
     * @param fillFieldNameObject
     */
    private Object fillVal(Object retVal, Collection retCollection, FillFieldNameObject fillFieldNameObject) {
        if (CollectionUtil.isEmpty(fillFieldNameObject.getFieldInfoSet())) {
            return retVal;
        }

        // 获取所有值,将其变成集合
        retCollection.forEach(o -> fillFieldNameObject.getFieldInfoSet().forEach(fieldInfo -> {
            Field field = fieldInfo.getField();
            field.setAccessible(true);
            try {
                fieldInfo.getInvokeArgs().add(field.get(o));
            } catch (IllegalAccessException e) {
                throw new BusinessException(e);
            }
        }));

        // 获取所有值,调用第三方法返回的值
        fillFieldNameObject.getFieldInfoSet().forEach(fieldInfo -> {
            try {
                Object invokeClass = SpringExtensionLoader.getSpringBean(fieldInfo.getInvokeClass());
                Method thirdPartyMethod = invokeClass.getClass().getMethod(fieldInfo.getInvokeMethod(), Set.class);
                fieldInfo.setMapData((Map) thirdPartyMethod.invoke(invokeClass, fieldInfo.getInvokeArgs()));
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new BusinessException(e);
            }
        });


        //进行赋值操作.
        List<Object> objects = new ArrayList<>();
        retCollection.forEach(o -> {
            Map<String, Object> propertyMap = new HashMap<>();
            fillFieldNameObject.getFieldInfoSet().forEach(fieldInfo -> {
                try {
                    Field field = fieldInfo.getField();
                    String fieldText = field.getName().endsWith("id") ? field.getName().substring(0, field.getName().lastIndexOf("id")) : field.getName();
                    fieldText = fieldText + "Name";
                    propertyMap.put(fieldText, fieldInfo.getMapData().getOrDefault(field.get(o), StringPool.EMPTY));
                } catch (IllegalAccessException e) {
                    throw new BusinessException(e);
                }
            });
            Object object = AddPropertiesUtil.getTarget(o, propertyMap);
            objects.add(object);
        });

        if (retVal instanceof Collection) {
            return objects;
        } else if (retVal instanceof PageResult) {
            ((PageResult) retVal).setRecords(objects);
            return retVal;
        } else {
            return objects.get(0);
        }
    }
}
