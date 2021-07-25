package com.blogpress.common.constants

import com.blogpress.count.enums.ContentTypeEnum

import java.text.MessageFormat

class RedisKeyConstants()

object RedisKeyConstants {
	
	val ARTICLE_DETAIL_KEY = "article_id_{0}"
	
	val COUNT_DETAIL_KEY = "content_type_{0}_id_{1}"
	
	def articleKey(articleId: Long): String = {
		MessageFormat.format(ARTICLE_DETAIL_KEY, articleId)
	}
	
	def countKey(contentTypeEnum: ContentTypeEnum, contentId: Long): String = MessageFormat.format(COUNT_DETAIL_KEY, contentTypeEnum.getCode, contentId)

}
