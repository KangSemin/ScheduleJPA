package jpa.schedule.config;

import jpa.schedule.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    private final LoginCheckInterceptor loginCheckInterceptor;

    public WebMvcConfig(LoginCheckInterceptor loginCheckInterceptor) {
        this.loginCheckInterceptor = loginCheckInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/api/**")        // 인증이 필요한 API 경로
                .excludePathPatterns(              // 인증이 필요없는 API 경로
                    "/api/users/login",
                    "/api/users/signup",
                    "/api/schedules",              // GET 요청만 허용
                    "/api/schedules/*/view"        // 상세 조회도 허용
                );
    }
}
