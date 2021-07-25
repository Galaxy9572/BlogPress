package com.blogpress.common.config

import com.blogpress.common.util.TimeUtils
import com.fasterxml.jackson.annotation.{JsonAutoDetect, JsonInclude, JsonTypeInfo, PropertyAccessor}
import com.fasterxml.jackson.databind.{DeserializationFeature, MapperFeature, ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.{LocalDateDeserializer, LocalDateTimeDeserializer, LocalTimeDeserializer}
import com.fasterxml.jackson.datatype.jsr310.ser.{LocalDateSerializer, LocalDateTimeSerializer, LocalTimeSerializer}
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

import java.time.{LocalDate, LocalDateTime, LocalTime}
import java.time.format.DateTimeFormatter

/**
 * Redis配置
 *
 * @author JY
 */
@Configuration
@EnableRedisHttpSession
class RedisConfig {
	/**
	 * RedisTemplate配置
	 */
	@Bean def redisTemplate(lettuceConnectionFactory: LettuceConnectionFactory): RedisTemplate[AnyRef, AnyRef] = { // 设置序列化
		val objectMapper = new ObjectMapper
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
		objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false)
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
		// 此项必须配置，否则会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
		objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY)
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
		val javaTimeModule = new JavaTimeModule
		javaTimeModule.addSerializer(classOf[LocalDateTime], new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(TimeUtils.DATE_TIME_FORMAT)))
		javaTimeModule.addDeserializer(classOf[LocalDateTime], new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(TimeUtils.DATE_TIME_FORMAT)))
		javaTimeModule.addSerializer(classOf[LocalDate], new LocalDateSerializer(DateTimeFormatter.ofPattern(TimeUtils.TIME_FORMAT)))
		javaTimeModule.addSerializer(classOf[LocalTime], new LocalTimeSerializer(DateTimeFormatter.ofPattern(TimeUtils.TIME_FORMAT)))
		javaTimeModule.addDeserializer(classOf[LocalDate], new LocalDateDeserializer(DateTimeFormatter.ofPattern(TimeUtils.DATE_FORMAT)))
		javaTimeModule.addDeserializer(classOf[LocalTime], new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TimeUtils.DATE_FORMAT)))
		objectMapper.registerModule(javaTimeModule).registerModule(new ParameterNamesModule)
		val serializer = new GenericJackson2JsonRedisSerializer(objectMapper)
		// 配置redisTemplate
		val redisTemplate = new RedisTemplate[AnyRef, AnyRef]
		redisTemplate.setConnectionFactory(lettuceConnectionFactory)
		// key序列化
		redisTemplate.setKeySerializer(serializer)
		// value序列化
		redisTemplate.setValueSerializer(serializer)
		// Hash key序列化
		redisTemplate.setHashKeySerializer(serializer)
		// Hash value序列化
		redisTemplate.setHashValueSerializer(serializer)
		redisTemplate.afterPropertiesSet()
		redisTemplate
	}
}
