package com.blogpress.search.bean.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 搜索请求类
 * @author JY
 */
@Data
public class SearchRequest {

    @NotEmpty
    private List<String> searchType;

    @NotBlank
    private String keyword;

}
