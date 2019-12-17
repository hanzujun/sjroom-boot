package com.sunvalley.framework.core.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * ObjectUtil Tester.
 *
 * @author manson.zhou
 */
public class ObjectUtilTest {

    /**
     * Method: isNull(@Nullable Object object)
     */
    @Test
    public void testIsNull() {
        Object object = null;
        boolean aNull = ObjectUtil.isNull(object);
        Assert.assertTrue(aNull);

        Object object1 = new Object();
        boolean bNull = ObjectUtil.isNull(object1);
        Assert.assertFalse(bNull);
    }

    /**
     * Method: isNotNull(@Nullable Object object)
     */
    @Test
    public void testIsNotNull() {
        Object object = null;
        boolean aNull = ObjectUtil.isNotNull(object);
        Assert.assertFalse(aNull);

        Object object1 = new Object();
        boolean bNull = ObjectUtil.isNotNull(object1);
        Assert.assertTrue(bNull);
    }

    /**
     * Method: isTrue(boolean bool)
     */
    @Test
    public void testIsTrueBool() {
        boolean bool = true;
        boolean isTrue = ObjectUtil.isTrue(bool);
        Assert.assertTrue(isTrue);

        boolean bool1 = false;
        boolean isTrue1 = ObjectUtil.isTrue(bool1);
        Assert.assertFalse(isTrue1);
    }

    /**
     * Method: isFalse(boolean bool)
     */
    @Test
    public void testIsFalseBool() {
        Boolean bool = null;
        boolean isTrue = ObjectUtil.isTrue(bool);
        Assert.assertFalse(isTrue);

        Boolean bool1 = Boolean.FALSE;
        boolean isTrue1 = ObjectUtil.isTrue(bool1);
        Assert.assertFalse(isTrue1);

        Boolean bool2 = Boolean.TRUE;
        boolean isTrue2 = ObjectUtil.isTrue(bool2);
        Assert.assertTrue(isTrue2);
    }

} 
