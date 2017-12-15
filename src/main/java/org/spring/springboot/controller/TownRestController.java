package org.spring.springboot.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.spring.springboot.cache.HCacheMapUtil;
import org.spring.springboot.domain.City;
import org.spring.springboot.domain.Town;
import org.spring.springboot.repository.TownRepository;
import org.spring.springboot.service.CityService;
import org.spring.springboot.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bysocket on 07/02/2017.
 */
@RestController
@Slf4j
@RequestMapping("/town")
public class TownRestController {

    @Autowired
    private TownRepository townRepository;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Town saveTown(@RequestBody Town request) {
        Town save = townRepository.save(request);
        return save;
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Town queryTown(@RequestBody Town request) {
        log.info("queryTown  request " + JsonUtil.objectToJson(request));
        Town town = townRepository.findOne(request.getId());
        return town;
    }
}
