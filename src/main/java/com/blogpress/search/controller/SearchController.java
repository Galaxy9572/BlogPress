package com.blogpress.search.controller;

import com.blogpress.common.bean.response.PageVO;
import com.blogpress.common.bean.response.ResponseVO;
import com.blogpress.search.bean.entity.SearchArticle;
import com.blogpress.search.bean.entity.SearchUser;
import com.blogpress.search.service.ISearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 搜索controller
 * @author JY
 */
@Api(value = "搜索操作相关API", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ISearchService iSearchService;

    @GetMapping("/user")
    @ApiOperation(value = "分页搜索用户列表", notes = "分页搜索用户列表接口")
    @ApiResponse(code = 200, message = "OK", response = SearchUser.class)
    public ResponseVO<PageVO<SearchUser>> searchUser(@Valid @Min(value = 1, message = "page.param.invalid")
        @RequestParam @DefaultValue("1") Integer pageNo, @Valid @Min(value = 1, message = "page.param.invalid")
        @RequestParam @DefaultValue("10")Integer pageSize, @RequestParam @Valid @NotBlank(message = "search.key.cannot.empty") String nick) {
        PageVO<SearchUser> pageVO = iSearchService.searchUserByNick(pageNo, pageSize, nick);
        return ResponseVO.success(pageVO);
    }

    @GetMapping("/article")
    @ApiOperation(value = "分页搜索文章列表", notes = "分页搜索文章列表接口")
    @ApiResponse(code = 200, message = "OK", response = SearchUser.class)
    public ResponseVO<PageVO<SearchArticle>> searchArticle(@Valid @Min(value = 1, message = "page.param.invalid")
        @RequestParam @DefaultValue("1") Integer pageNo, @Valid @Min(value = 1, message = "page.param.invalid")
        @RequestParam @DefaultValue("10") Integer pageSize, @RequestParam @Valid @NotBlank(message = "search.key.cannot.empty") String keyword) {
        PageVO<SearchArticle> pageVO = iSearchService.searchArticle(pageNo, pageSize, keyword);
        return ResponseVO.success(pageVO);
    }

}
