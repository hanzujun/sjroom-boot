package github.sjroom.mybatis.fill;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import github.sjroom.common.context.SpringExtensionLoader;
import github.sjroom.common.exception.BusinessException;
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
                if (!field.getName().endsWith("Name")) {
                    continue;
                }
                //找到对应的字段值,才能进行填充
                String getFieldValue = field.getName().substring(0, field.getName().length() - 4);
                Optional<Field> fieldOptional = Arrays.stream(fields).filter(x -> x.getName().equals(getFieldValue)).findFirst();
                if (!fieldOptional.isPresent()) {
                    continue;
                }

                fillFieldNameObject.getFieldInfoSet().add(new FillFieldNameObject.FieldInfo(fieldOptional.get(), field, fillFieldName.invoke(), fillFieldName.methodName(), new HashSet<>(), new HashMap()));
                classFillFieldNameObjectMap.put(retValObjectClass, fillFieldNameObject);
            }
        } else {
            fillFieldNameObject = classFillFieldNameObjectMap.get(retValObjectClass);
        }

        fillVal(retCollection, fillFieldNameObject);
    }


    /**
     * 开始反射赋值
     *
     * @param retCollection
     * @param fillFieldNameObject
     */
    private void fillVal(Collection retCollection, FillFieldNameObject fillFieldNameObject) {
        if (CollectionUtil.isEmpty(fillFieldNameObject.getFieldInfoSet())) {
            return;
        }

        // 获取所有值,将其变成集合
        retCollection.forEach(o -> fillFieldNameObject.getFieldInfoSet().forEach(fieldInfo -> {
            Field fieldValue = fieldInfo.getFieldValue();
            Field fieldText = fieldInfo.getFieldText();
            fieldValue.setAccessible(true);
            fieldText.setAccessible(true);
            try {
                fieldInfo.setInvokeArgs(null);
                fieldInfo.getInvokeArgs().add(fieldInfo.getFieldValue().get(o));
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
        retCollection.forEach(o -> fillFieldNameObject.getFieldInfoSet().forEach(fieldInfo -> {
            Field fieldValue = fieldInfo.getFieldValue();
            Field fieldText = fieldInfo.getFieldText();
            try {
                fieldText.set(o, fieldInfo.getMapData().getOrDefault(fieldValue.get(o), StringPool.EMPTY));
            } catch (IllegalAccessException e) {
                throw new BusinessException(e);
            }
        }));
    }
}
