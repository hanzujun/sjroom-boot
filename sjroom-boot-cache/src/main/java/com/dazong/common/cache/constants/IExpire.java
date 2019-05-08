package com.dazong.common.cache.constants;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:
 */
public final class IExpire {

    private IExpire(){

    }
    public static final int EXPIRE_MAX = Integer.MAX_VALUE;

    public static final int ONE_MILL_SECOND = 1;

    public static final int ONE_SEC = 1000 * ONE_MILL_SECOND;

    public static final int FIVE_SEC = 5 * ONE_SEC;

    public static final int TEN_SEC = 2 * FIVE_SEC;

    public static final int ONE_MIN = 6 * TEN_SEC;

    public static final int FIVE_MIN = 5 * ONE_MIN;

    public static final int TEN_MIN = 2 * FIVE_MIN;

    public static final int HALF_HOUR = 3 * TEN_MIN;

    public static final int ONE_HOUR = 2 * HALF_HOUR;

    public static final int TWO_HOUR = 2 * ONE_HOUR;

    public static final int SIX_HOUR = 3 * TWO_HOUR;

    public static final int TWELVE_HOUR = 2 * SIX_HOUR;

    public static final int ONE_DAY = 2 * TWELVE_HOUR;

    public static final int TWO_DAY = 2 * ONE_DAY;

    public static final int ONE_WEEK = 7 * ONE_DAY;
}