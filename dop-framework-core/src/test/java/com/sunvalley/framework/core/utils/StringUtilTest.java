package com.sunvalley.framework.core.utils;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {

    @Test
    public void test1() {
        String stringWithUnderline = "a_b_c_d";
        String stringWithCamelCase = StringUtil.toCamelCase(stringWithUnderline);
        String underline = StringUtil.toUnderline(stringWithCamelCase);
        Assert.assertEquals(stringWithUnderline, underline);
    }

    @Test
    public void test2() {
        String stringWithUnderline = "a-b-c-d";
        String stringWithCamelCase = StringUtil.toCamelCase(stringWithUnderline);
        String underline = StringUtil.toUnderline(stringWithCamelCase);
        Assert.assertEquals(stringWithUnderline, underline);
    }

}
