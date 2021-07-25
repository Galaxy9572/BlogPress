package com.blogpress.search.service.impl

import com.blogpress.article.bean.entity.Article
import com.blogpress.common.bean.response.PageVO
import com.blogpress.common.util.PageUtils
import com.blogpress.search.bean.SearchBeanConverter
import com.blogpress.search.bean.entity.{SearchArticle, SearchUser}
import com.blogpress.search.dao.{ArticleRepository, UserRepository}
import com.blogpress.search.service.ISearchService
import com.blogpress.user.bean.entity.User
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

/**
 * 搜索服务实现类
 * @author JY
 */
@Slf4j
@Service
class SearchServiceImpl @Autowired() (userRepository: UserRepository, articleRepository: ArticleRepository) extends ISearchService{
	
	def saveUser(user: User): Unit = {
		val searchUser = SearchBeanConverter.toSearchUser(user)
		if (searchUser == null) return
		userRepository.save(searchUser)
	}
	
	def saveArticle(article: Article): Unit = {
		val searchArticle = SearchBeanConverter.toSearchArticle(article)
		if (searchArticle == null) return
		articleRepository.save(searchArticle)
	}
	
	def searchUserByNick(pageNo: Integer, pageSize: Integer, nick: String): PageVO[SearchUser] = {
		// ES分页从0开始
		val page = userRepository.findByNickContaining(PageRequest.of(pageNo - 1, pageSize), nick)
		PageUtils.of(page)
	}
	
	def searchArticle(pageNo: Integer, pageSize: Integer, keyword: String): PageVO[SearchArticle] = {
		val page = articleRepository.findByTitleContainingOrContentContaining(PageRequest.of(pageNo - 1, pageSize), keyword, keyword)
		PageUtils.of(page)
	}
	
	def deleteUserByUserId(userId: Long): Unit = {
		userRepository.deleteByUserId(userId)
	}
}
