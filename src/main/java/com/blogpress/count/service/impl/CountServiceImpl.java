package com.blogpress.count.service.impl;

import com.blogpress.common.constants.RedisKeyConstants;
import com.blogpress.common.exception.BusinessException;
import com.blogpress.count.bean.converter.CountBeanConverter;
import com.blogpress.count.bean.dto.CountDTO;
import com.blogpress.count.bean.entity.Count;
import com.blogpress.count.dao.CountMapper;
import com.blogpress.count.enums.ContentTypeEnum;
import com.blogpress.count.service.ICountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 统计服务实现类
 * @author JY
 */
@Slf4j
@Service
public class CountServiceImpl implements ICountService {

    @Autowired
    private CountMapper countMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public CountDTO getCount(ContentTypeEnum contentType, Long contentId) {
        String countKey = RedisKeyConstants.countKey(contentType, contentId);
        Object o = redisTemplate.opsForValue().get(countKey);
        CountDTO countDTO = (CountDTO) o;
        if(countDTO != null){
            return countDTO;
        }
        Count count = countMapper.selectByContentTypeAndId(contentType, contentId);
        countDTO = CountBeanConverter.toCountDTO(count);
        redisTemplate.opsForValue().set(countKey, countDTO);
        return countDTO;
    }

    @Override
    public Map<Long, CountDTO> listCount(ContentTypeEnum contentType, List<Long> contentIds) {
        if(CollectionUtils.isEmpty(contentIds)){
            return new HashMap<>();
        }
        List<Object> ids = contentIds.stream().map(e -> (Object) RedisKeyConstants.countKey(contentType, e)).collect(Collectors.toList());
        List<Object> objects = redisTemplate.opsForValue().multiGet(ids);
        objects = Optional.ofNullable(objects).orElse(new ArrayList<>()).stream().filter(Objects::nonNull).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(objects)){
            return objects.stream().map(e -> (CountDTO) e).collect(Collectors.toMap(CountDTO::getContentId, e -> e));
        }
        Map<Long, Count> countMap = countMapper.selectByContentTypeAndIds(contentType, contentIds);
        if(CollectionUtils.isEmpty(countMap)){
            return new HashMap<>();
        }
        Map<Long, CountDTO> map = new HashMap<>(countMap.size());
        countMap.forEach((k, v) -> map.put(k, CountBeanConverter.toCountDTO(v)));

        Map<String, CountDTO> cacheMap = new HashMap<>(countMap.size());
        map.forEach((k, v) -> cacheMap.put(RedisKeyConstants.countKey(contentType, k), v));
        redisTemplate.opsForValue().multiSet(cacheMap);
        return map;
    }

    @Override
    public CountDTO newCount(ContentTypeEnum contentType, Long contentId) {
        String countKey = RedisKeyConstants.countKey(contentType, contentId);
        CountDTO countDTO = (CountDTO) redisTemplate.opsForValue().get(countKey);
        if(countDTO != null){
            return countDTO;
        }
        Count count = countMapper.selectByContentTypeAndId(contentType, contentId);
        if(count != null) {
            countDTO = CountBeanConverter.toCountDTO(count);
            redisTemplate.opsForValue().set(countKey, countDTO);
            return countDTO;
        }
        count = new Count();
        count.setContentId(contentId);
        count.setContentType(ContentTypeEnum.ARTICLE.getCode());
        int countCount = countMapper.insert(count);
        if(countCount > 0) {
            count = countMapper.selectByContentTypeAndId(contentType, contentId);
            countDTO = CountBeanConverter.toCountDTO(count);
            redisTemplate.opsForValue().set(countKey, countDTO);
            return countDTO;
        }else{
            log.error("Create article failed, contentType: {}, contentId: {}", contentType, contentId);
            throw BusinessException.of("operate.failed");
        }
    }

    @Override
    public void deleteCount(ContentTypeEnum contentType, Long contentId){
        countMapper.deleteByContentTypeAndId(contentType, contentId);
        String countKey = RedisKeyConstants.countKey(contentType, contentId);
        redisTemplate.delete(countKey);
    }

}
