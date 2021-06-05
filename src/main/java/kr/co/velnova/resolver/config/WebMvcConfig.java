package kr.co.velnova.resolver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.velnova.resolver.resolver.CustomArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        CustomArgumentResolver customArgumentResolver = new CustomArgumentResolver(objectMapper);
        resolvers.add(customArgumentResolver);
    }

}
