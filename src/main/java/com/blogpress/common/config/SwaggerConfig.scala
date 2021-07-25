package com.blogpress.common.config

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.servlet.config.annotation.{ResourceHandlerRegistry, WebMvcConfigurationSupport}
import springfox.documentation.builders.{ApiInfoBuilder, PathSelectors, RequestHandlerSelectors}
import springfox.documentation.service.{ApiInfo, Contact}
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * Swagger2 API文档配置类
 *
 * @author JY
 */
@Configuration
@EnableSwagger2
class SwaggerConfig extends WebMvcConfigurationSupport {
	
	override def addResourceHandlers(registry: ResourceHandlerRegistry): Unit = {
		registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/")
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
	}
	
	@Bean def createRestApi: Docket = {
		new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo)
			.pathMapping("/")
			.select
			.apis(RequestHandlerSelectors.basePackage("com.blogpress"))
			.paths(PathSelectors.any)
			.build
	}
	
	private def apiInfo: ApiInfo = {
		new ApiInfoBuilder()
			.title("Blog Press API")
			.contact(new Contact("JY", "", ""))
			.version("1.0")
			.description("API 描述")
			.build
	}
}