package com.blogpress.count.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blogpress.count.bean.entity.Count;
import com.blogpress.count.enums.ContentTypeEnum;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 统计Mapper
 * @author JY
 */
public interface CountMapper extends BaseMapper<Count> {

    /**
     * 根据内容类型和内容的ID查询统计信息
     * @param contentType 内容类型
     * @param contentId 内容的ID
     * @return Count
     */
    Count selectByContentTypeAndId(@Param("contentType") ContentTypeEnum contentType, @Param("contentId") Long contentId);

    /**
     * 根据内容类型和内容的ID查询统计信息
     * @param contentType 内容类型
     * @param contentIds 内容的ID列表
     * @return Count
     */
    @MapKey("contentId")
    Map<Long, Count> selectByContentTypeAndIds(@Param("contentType") ContentTypeEnum contentType, @Param("contentIds") List<Long> contentIds);

}
