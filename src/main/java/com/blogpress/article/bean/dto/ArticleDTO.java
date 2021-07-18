package com.blogpress.article.bean.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章实体DTO类
 * @author JY
 */
@Data
public class ArticleDTO {

    /** 主键 */
    private Long articleId;

    /** 作者的userId */
    private Long userId;

    /** 文章标题 */
    private String title;

    /** 正文 */
    private String content;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 逻辑删除状态 */
    private Boolean isLogicDeleted;

    /** 版本号 */
    private Long version;

}
