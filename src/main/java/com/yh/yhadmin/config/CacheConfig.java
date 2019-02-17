package com.yh.yhadmin.config;

import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Describle This Class Is 没有连接redis 使用默认ConcurrentMap缓存
 * @Author ZengMin
 * @Date 2019/2/17 11:43
 */
@Configuration
public class CacheConfig {

    @Bean
    public ConcurrentMapCacheManager cacheManager(){
        return new ConcurrentMapCacheManager();
    }

}
