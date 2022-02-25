package toyProject.sixWordsWriter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import toyProject.sixWordsWriter.web.LoginCheckInterceptor;

//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/member/new", "/member/afternew/{memberName}", "/member/login", "/board",
                                     "/css/**", "/*.ico", "/error");
    }
}
