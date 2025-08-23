package cn.addenda.biz.log.config;

import com.github.pagehelper.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageInterceptorConfig {

  @Bean
  public PageInterceptor pageInterceptor() {
    return new PageInterceptor();
  }

}
