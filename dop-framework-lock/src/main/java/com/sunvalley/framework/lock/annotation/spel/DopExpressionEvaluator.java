package com.sunvalley.framework.lock.annotation.spel;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;

/**
 * @author Katrel.Zhou
 * @date 2019/8/2 16:10
 */
public interface DopExpressionEvaluator {

    /**
     * 创建解析器上下文
     *
     * @param target
     * @param targetClass
     * @param method
     * @param args
     * @return
     */
    EvaluationContext createEvaluationContext(Object target, Class<?> targetClass,
                                              Method method, Object[] args);

    /**
     * key
     *
     * @param keyExpression
     * @param elementKey
     * @param evalContext
     * @return
     */
    Object key(String keyExpression, AnnotatedElementKey elementKey, EvaluationContext evalContext);
}
