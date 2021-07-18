package com.blogpress.count.service;

import com.blogpress.count.bean.dto.CountDTO;
import com.blogpress.count.enums.ContentTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * 统计服务
 * @author JY
 */
public interface ICountService {

    /**
     * 获取count对象
     * @param contentType ContentTypeEnum
     * @param contentId 内容主键
     * @return CountDTO
     */
    CountDTO getCount(ContentTypeEnum contentType, Long contentId);

    /**
     * 批量获取count对象
     * @param contentType ContentTypeEnum
     * @param contentIds 内容主键
     * @return Map<Long, CountDTO>
     */
    Map<Long, CountDTO> listCount(ContentTypeEnum contentType, List<Long> contentIds);

    /**
     * 新增统计对象
     * @param contentType ContentTypeEnum
     * @param contentId 内容主键
     * @return CountDTO
     */
    CountDTO newCount(ContentTypeEnum contentType, Long contentId);

    /**
     * 删除统计对象
     * @param contentType ContentTypeEnum
     * @param contentId 内容主键
     */
    void deleteCount(ContentTypeEnum contentType, Long contentId);
}
