package org.spring.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.spring.springboot.cache.HCacheMapUtil;
import org.spring.springboot.domain.City;
import org.spring.springboot.domain.RedisBean;
import org.spring.springboot.redis.RedisService;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bysocket on 07/02/2017.
 */
@RestController
@Slf4j
public class RedisController {
    @Autowired
    private RedisService redisService;

    /**
     * 放入redis
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/redis/set", method = RequestMethod.POST)
    public boolean putString(@RequestBody RedisBean request) {
        boolean set = redisService.set(request.getKey(), request.getValue());
        return set;
    }

    /**
     * 通过key 取值
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/redis/get", method = RequestMethod.POST)
    public Object getString(@RequestBody RedisBean request) {
        Object object = redisService.get(request.getKey());
        return object;
    }

    /**
     *对key 增量整数
     * @param request
     * @return
     */
    @RequestMapping(value = "/redis/increment", method = RequestMethod.POST)
    public Long incrementString(@RequestBody RedisBean request) {
        Long increment = redisService.increment(request.getKey(), request.getExpireTime());
        return increment;
    }

    /**
     * 对key 增量 小数
     * @param request
     * @return
     */
    @RequestMapping(value = "/redis/incrementDouble", method = RequestMethod.POST)
    public Double increment(@RequestBody RedisBean request) {
        Double increment = redisService.increment(request.getKey(), Double.parseDouble(request.getDelta()));
        return increment;
    }
}
