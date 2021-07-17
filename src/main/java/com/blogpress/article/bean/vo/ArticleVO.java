package com.blogpress.article.bean.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章前端视图类
 * @author JY
 */
@Data
public class ArticleVO {

    /** 主键 */
    private Long id;

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

}
