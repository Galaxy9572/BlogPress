package com.blogpress.count.bean.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 统计DTO对象
 * @author JY
 */
@Data
public class CountDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long countId;

    /** 内容类型 */
    private String contentType;

    /** 内容主键 */
    private Long contentId;

    /** 点赞数 */
    private Integer likeCount;

    /** 收藏数 */
    private Integer collectCount;

    /** 评论数 */
    private Integer commentCount;

    /** 版本号 */
    private Long version;

}
