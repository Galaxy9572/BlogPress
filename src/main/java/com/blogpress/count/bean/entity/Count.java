package com.blogpress.count.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * 统计对象
 * @author JY
 */
@Data
@TableName("count")
public class Count {

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
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
    @Version
    private Long version;

}
