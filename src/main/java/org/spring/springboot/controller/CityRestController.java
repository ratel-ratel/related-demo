package org.spring.springboot.controller;

import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.spring.springboot.cache.HCacheMapUtil;
import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bysocket on 07/02/2017.
 */
@RestController
@Slf4j
public class CityRestController {

    @Autowired
    private CityService cityService;
    @Autowired
    private HazelcastInstance hazelcast;

    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public City findOneCity(@RequestParam(value = "cityName", required = true) String cityName) {
        String key = "hello Word";
        log.info("放入缓存开始.....");
        int put = HCacheMapUtil.put(hazelcast, "HAZELCAST", "key", key);
        log.info("放入缓存结果是 : " + put);
        String str = HCacheMapUtil.get(hazelcast, "HAZELCAST", "key", String.class);
        log.info("从缓存中获取的结果为 : " + str);
        return cityService.findCityByName(cityName);
    }

}
