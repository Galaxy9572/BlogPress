package com.blogpress.search.service

import com.blogpress.article.bean.entity.Article
import com.blogpress.common.bean.response.PageVO
import com.blogpress.search.bean.entity.{SearchArticle, SearchUser}
import com.blogpress.user.bean.entity.User

/**
 * 搜索服务
 * @author JY
 */
trait ISearchService {
	
	/**
	 * 保存用户
	 * @param user User
	 */
	def saveUser(user: User): Unit
	
	/**
	 * 保存文章
	 * @param article Article
	 */
	def saveArticle(article: Article): Unit
	
	/**
	 * 根据昵称分页搜索用户
	 * @param pageNo   当前页
	 * @param pageSize 页大小
	 * @param nick     昵称
	 * @return PageVO<SearchUser>
	 */
	def searchUserByNick(pageNo: Integer, pageSize: Integer, nick: String): PageVO[SearchUser]
	
	/**
	 * 根据关键字分页搜索文章
	 * @param pageNo   当前页
	 * @param pageSize 页大小
	 * @param keyword  关键字
	 * @return PageVO<SearchUser>
	 */
	def searchArticle(pageNo: Integer, pageSize: Integer, keyword: String): PageVO[SearchArticle]
	
	/**
	 * 根据用户ID删除用户
	 * @param userId 用户ID
	 */
	def deleteUserByUserId(userId: Long): Unit
}
