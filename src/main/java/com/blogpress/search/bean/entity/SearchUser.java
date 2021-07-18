package com.blogpress.search.bean.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

/**
 * 搜索用户类
 * @author JY
 */
@Data
@Document(indexName = "index_user")
public class SearchUser {

    /** 主键 */
    @Id
    private Long userId;

    /** 昵称 */
    @Field(type = FieldType.Keyword)
    private String nick;

    /** 性别 */
    @Field(type = FieldType.Keyword)
    private String gender;

    /** 生日 */
    @Field(type = FieldType.Keyword)
    private LocalDate birthday;

    /** 国家 */
    @Field(type = FieldType.Keyword)
    private String country;

    /** 省份 */
    @Field(type = FieldType.Keyword)
    private String province;

    /** 城市 */
    @Field(type = FieldType.Keyword)
    private String city;

    /** 街道 */
    @Field(type = FieldType.Keyword)
    private String street;

    /** 用户状态 */
    @Field(type = FieldType.Keyword)
    private String userStatus;

    /** 逻辑删除状态 */
    @Field(type = FieldType.Keyword)
    private Boolean isLogicDeleted;

}
