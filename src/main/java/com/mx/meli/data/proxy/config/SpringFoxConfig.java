package com.mx.meli.data.proxy.config;

/** David Garcia Isario
 * Esta clase establece la configuracion de Swagger
 * El basepackage es de donde va a buscar las clases a mostrar
 * 
 * 
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
	
	@Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build().pathMapping("/")
                .apiInfo(apiInfo()).useDefaultResponseMessages(false);
    }
	
	 @Bean
	   ApiInfo apiInfo() {
	       final ApiInfoBuilder builder = new ApiInfoBuilder();
	       builder.title("MELI API proxy").version("1.0").license("")
	               .description("List of all endpoints used in API");
	       return builder.build();
	   }
}
