package com.blogpress.article.controller;

import com.blogpress.article.bean.response.ArticleVO;
import com.blogpress.article.request.CreateArticleRequest;
import com.blogpress.article.service.IArticleService;
import com.blogpress.common.annotation.Permission;
import com.blogpress.common.bean.response.PageVO;
import com.blogpress.common.bean.response.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * 文章controller
 * @author JY
 */
@Slf4j
@Api(value = "文章操作相关API", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final IArticleService iArticleService;

    @Autowired
    public ArticleController(IArticleService iArticleService) {
        this.iArticleService = iArticleService;
    }

    @Permission
    @PostMapping("")
    @ApiOperation(value = "新增文章", notes = "新增文章接口")
    @ApiResponse(code = 200, message = "OK", response = ArticleVO.class)
    public ResponseVO<ArticleVO> createArticle(@ApiParam(name = "新增文章入参", required = true)
        @Valid @RequestBody CreateArticleRequest request) {
        ArticleVO vo = iArticleService.createArticle(request);
        return ResponseVO.success(vo);
    }

    @GetMapping("/{articleId}")
    @ApiOperation(value = "获取文章详情", notes = "获取文章详情接口")
    @ApiResponse(code = 200, message = "OK", response = ArticleVO.class)
    public ResponseVO<ArticleVO> getArticleDetail(@Valid @PathVariable("articleId") Long articleId) {
        ArticleVO vo = iArticleService.getArticleById(articleId);
        return ResponseVO.success(vo);
    }

    @Permission
    @DeleteMapping("/{articleId}")
    @ApiOperation(value = "用户删除文章", notes = "用户删除文章接口")
    @ApiResponse(code = 200, message = "OK", response = ArticleVO.class)
    public ResponseVO<String> deleteArticle(@Valid @PathVariable("articleId") Long articleId) {
        Boolean isDeleted = iArticleService.deleteArticle(articleId);
        if (isDeleted) {
            return ResponseVO.success("operate.success");
        } else {
            return ResponseVO.failed("operate.failed");
        }
    }

    @GetMapping("/{userId}/articles")
    @ApiOperation(value = "分页获取用户的文章列表", notes = "分页获取用户的文章列表接口")
    @ApiResponse(code = 200, message = "OK", response = ArticleVO.class)
    public ResponseVO<PageVO<ArticleVO>> listArticles(@Valid @Min(value = 1, message = "page.param.invalid")
        @RequestParam @DefaultValue("1")Integer pageNo, @Valid @Min(value = 1, message = "page.param.invalid")
        @RequestParam @DefaultValue("10") Integer pageSize, @PathVariable("userId") Long userId) {
        PageVO<ArticleVO> articleVoPage = iArticleService.listUserArticles(userId, pageNo, pageSize);
        return ResponseVO.success(articleVoPage);
    }

}
