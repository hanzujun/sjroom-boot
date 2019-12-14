package com.sunvalley.framework.lock.annotation.spel;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Katrel.Zhou
 * @date 2019/8/2 16:26
 */
@Getter
@AllArgsConstructor
public class ExpressionRootObject {
    private final Object object;

    private final Object[] args;
}
