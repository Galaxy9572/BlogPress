package com.blogpress.count.bean.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 统计VO
 * @author JY
 */
@Data
public class CountVO implements Serializable {

    private static final long serialVersionUID = 1L;

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

}
