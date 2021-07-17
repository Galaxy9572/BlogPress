package com.blogpress.article.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章实体类
 * @author JY
 */
@Data
public class Article {

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
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

    /** 逻辑删除状态 */
    @TableLogic
    private Boolean isLogicDeleted;

}
