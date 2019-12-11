package com.github.sjroom.cache.constants;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:缓存框架类型
 */
public enum CacheType {

    /**
     * Redis缓存框架
     */
    CACHE_REDIS(1,"RedisAutoConfigure"),

    /**
     * Memcache缓存框架
     */
    CACHE_MEMCACHE(2,"MemcacheAutoConfigure"),

    /**
     * Local缓存框架
     */
    CACHE_LOCALCACHE(3,"LocalCacheAutoConfigure");

    private CacheType(Integer type, String typeDesc){
        this.type = type;
        this.typeDesc = typeDesc;
    }
    private Integer type;
    private String typeDesc;

    public final Integer getType() {
        return type;
    }


    public String getTypeDesc() {
        return typeDesc;
    }

}
