package com.blogpress.search.controller

import com.blogpress.common.bean.response.{PageVO, ResponseVO}
import com.blogpress.search.bean.entity.{SearchArticle, SearchUser}
import com.blogpress.search.service.ISearchService
import io.swagger.annotations.{Api, ApiOperation, ApiResponse}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.bind.DefaultValue
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RequestParam, RestController}

import javax.validation.Valid
import javax.validation.constraints.{Min, NotBlank}

/**
 * 搜索controller
 *
 * @author JY
 */
@Api(value = "搜索操作相关API", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping(Array("/search"))
class SearchController @Autowired() (iSearchService: ISearchService){
	
	@GetMapping(Array("/user"))
	@ApiOperation(value = "分页搜索用户列表", notes = "分页搜索用户列表接口")
	@ApiResponse(code = 200, message = "OK", response = classOf[SearchUser])
	def searchUser(@Valid @Min(value = 1, message = "page.param.invalid") @RequestParam @DefaultValue(Array("1")) pageNo: Integer,
	               @Valid @Min(value = 1, message = "page.param.invalid") @RequestParam @DefaultValue(Array("10")) pageSize: Integer,
	               @RequestParam @Valid @NotBlank(message = "search.key.cannot.empty") nick: String): ResponseVO[PageVO[SearchUser]] = {
		val pageVO = iSearchService.searchUserByNick(pageNo, pageSize, nick)
		ResponseVO.success(pageVO)
	}
	
	@GetMapping(Array("/article"))
	@ApiOperation(value = "分页搜索文章列表", notes = "分页搜索文章列表接口")
	@ApiResponse(code = 200, message = "OK", response = classOf[SearchUser])
	def searchArticle(@Valid @Min(value = 1, message = "page.param.invalid") @RequestParam @DefaultValue(Array("1")) pageNo: Integer,
	                  @Valid @Min(value = 1, message = "page.param.invalid") @RequestParam @DefaultValue(Array("10")) pageSize: Integer,
	                  @RequestParam @Valid @NotBlank(message = "search.key.cannot.empty") keyword: String): ResponseVO[PageVO[SearchArticle]] = {
		val pageVO = iSearchService.searchArticle(pageNo, pageSize, keyword)
		ResponseVO.success(pageVO)
	}
}
