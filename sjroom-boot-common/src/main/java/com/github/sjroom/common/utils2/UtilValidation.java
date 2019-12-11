package com.github.sjroom.common.utils2;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;

import javax.validation.*;
import java.util.Set;

/**
 * @Auther: smj
 * @Date: 2019/08/07
 * @Version 1.8
 * @Description: base on HibernateValidator.class
 * reference: http://docs.jboss.org/hibernate/validator/4.2/reference/zh-CN/html_single/#validator-gettingstarted
 */
@Slf4j
public abstract class UtilValidation {

    public static final Validator validatorFast = validatorFast();//快速失败模式
    public static final Validator validator = validatorSlow();// 备用

//    public static String fast  = System.getProperty("profile","true");

    /**
     * * 校验整个对象,如果有错误，包装成异常throw出去
     *
     * @param t
     * @param groups
     * @param <T>
     */
    public static <T> void validator(T t, Class... groups) {
        Set<ConstraintViolation<T>> set = validatorFast.validate(t, groups);
        growValidMessageEx(set);
    }

    /**
     * 校验整个对象的属性,如果有错误，包装成异常throw出去
     *
     * @param t
     * @param prop
     * @param groups
     * @param <T>
     */
    public static <T> void validatorProp(T t, String prop, Class... groups) {
        Set<ConstraintViolation<T>> set = validatorFast.validateProperty(t, prop, groups);
        growValidMessageEx(set);
    }


    public static <T> void growValidMessageEx(Set<ConstraintViolation<T>> set) {
        if (set == null || set.isEmpty())
            return;
        throw new ConstraintViolationException(set);
    }


    private static Validator validatorFast() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
//                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return validator;
    }

    private static Validator validatorSlow() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(false)
//                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return validator;
    }


//    public static void main(String[] args) {
//        UtilValidation.validator(new Demo());
//    }
//
//
//    private static class Demo {
//        @NotNull
//        private Integer id;
//        @Range(min = 2)
//        private Integer age = 0;
//
//        public Integer getId() {
//            return id;
//        }
//
//        public void setId(Integer id) {
//            this.id = id;
//        }
//
//        public Integer getAge() {
//            return age;
//        }
//
//        public void setAge(Integer age) {
//            this.age = age;
//        }
//    }
}


