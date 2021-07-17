package com.blogpress.article.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 新增文章请求类
 * @author JY
 */
@Data
@ApiModel(description="新增文章入参" )
public class CreateArticleRequest {

    @ApiModelProperty(value = "文章标题", example="我是文章标题")
    @NotBlank(message = "{article.title.cannot.empty}")
    private String title;

    @ApiModelProperty(value = "文章内容", example="我是文章内容")
    @NotBlank(message = "{article.content.cannot.empty}")
    private String content;

}
