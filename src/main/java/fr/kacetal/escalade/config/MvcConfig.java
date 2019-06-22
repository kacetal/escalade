package fr.kacetal.escalade.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    
    @Value("${upload.path}")
    private String uploadPath;
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry
                    .addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
        
//        if (!registry.hasMappingForPattern("/**")) {
//            registry
//                    .addResourceHandler("/**")
//                    .addResourceLocations("classpath:/META-INF/resources/**");
//        }
    
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/images/");
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/static/");
    }
}
