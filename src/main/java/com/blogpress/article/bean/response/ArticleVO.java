package com.blogpress.article.bean.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文章前端视图类
 * @author JY
 */
@Data
public class ArticleVO {

    /** 主键 */
    @ApiModelProperty(value = "文章ID", example = "123456789")
    private Long articleId;

    /** 作者的userId */
    @ApiModelProperty(value = "作者用户ID", example = "123456789")
    private Long userId;

    /** 文章标题 */
    @ApiModelProperty(value = "文章标题", example = "我是文章标题")
    private String title;

    /** 正文 */
    @ApiModelProperty(value = "文章内容", example = "我是文章内容")
    private String content;

    /** 创建时间 */
    @ApiModelProperty(value = "文章创建时间", example = "2021-01-01")
    private String createTime;

    /** 更新时间 */
    @ApiModelProperty(value = "文章修改时间", example = "2021-01-01")
    private String updateTime;

}
