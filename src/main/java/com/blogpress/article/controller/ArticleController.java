package com.blogpress.article.controller;

import com.blogpress.article.bean.converter.ArticleBeanConverter;
import com.blogpress.article.bean.dto.ArticleDTO;
import com.blogpress.article.bean.response.ArticleVO;
import com.blogpress.article.request.CreateArticleRequest;
import com.blogpress.article.service.IArticleService;
import com.blogpress.common.annotation.RequireLogin;
import com.blogpress.common.rest.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @RequireLogin
    @PostMapping("")
    @ApiOperation(value = "新增文章", notes = "新增文章接口")
    @ApiResponse(code = 200, message = "OK", response = ArticleVO.class)
    public ResponseVO<ArticleVO> createArticle(@ApiParam(name = "新增文章入参", required = true)
        @Valid @RequestBody CreateArticleRequest request) {
        ArticleDTO articleDTO = ArticleBeanConverter.toArticleDTO(request);
        ArticleVO vo = iArticleService.createArticle(articleDTO);
        return ResponseVO.success(vo);
    }

    @GetMapping("/{articleId}")
    @ApiOperation(value = "获取文章详情", notes = "获取文章详情接口")
    @ApiResponse(code = 200, message = "OK", response = ArticleVO.class)
    public ResponseVO<ArticleVO> createArticle(@Valid @PathVariable("articleId") Long articleId) {
        ArticleVO vo = iArticleService.getArticleById(articleId);
        return ResponseVO.success(vo);
    }

    @RequireLogin
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

}