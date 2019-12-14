package com.sunvalley.framework.core.utils2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public abstract class UtilSpring {


    /**
     * 支持classpath、url的locations
     * @param locations
     * @return
     */
    public static Properties loadProperties(String... locations) {
        Properties ret = new Properties();
        if (UtilCollection.isEmpty(locations)) return ret;

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        for (String location : locations) {
            try {
                for (Resource res : resolver.getResources(location)) {
                    try {
                        PropertiesLoaderUtils.fillProperties(ret, res);
                    } catch (IOException e) {
                        log.info("location={}, res={}, ex={}", location, res, e);
                    }
                }
            } catch (IOException e) {
                log.info("location={}, ex={}", location, e);
            }
        }
        return ret;
    }

}
