package com.xc.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 */
@Configuration
// 开启Swagger功能
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)// DocumentationType.SWAGGER_2 固定的，代表swagger2
                //.groupName("A项目")// 配置多个文档时,可以配置groupName来分组标识
                .apiInfo(apiInfo())// 生成API信息
                .select() // select()返回一个ApiSelectorBuilder实例,用来控制哪些接口被swagger做成文档
                .apis(RequestHandlerSelectors.basePackage("com.xc.swagger.controller"))// 指定扫描哪个包下的接口
                // 对所有路径进行监控
                .paths(PathSelectors.any()) // 选择所有的API,可以只为部分API生成文档
                //忽略以/error开头的路径,可防止显示如404错误接口
                .paths(PathSelectors.regex("/error.*").negate())
                // 忽略以/actuator开头的路径
                .paths(PathSelectors.regex("/actuator.*").negate())
                .build();
    }

    /**
     * 定义API主界面的信息，如声明所有API的总标题、描述、版本
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SWAGGER-XC—TEST") // 自定义API的主标题
                .description("springfox swagger first experience") // 描述整体的API
                .termsOfServiceUrl("") // 定义服务的域名
                .version("1.0") // 定义版本
                .build();
    }
}
