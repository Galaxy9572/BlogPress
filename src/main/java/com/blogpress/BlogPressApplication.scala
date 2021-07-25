package com.blogpress

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * 启动类
 * @author JY
 */
@EnableAsync
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = Array("com.blogpress"))
class BlogPressApplication()

object BlogPressApplication {
	
	def main(args: Array[String]): Unit = {
		SpringApplication.run(classOf[BlogPressApplication], args: _*)
	}
	
}
