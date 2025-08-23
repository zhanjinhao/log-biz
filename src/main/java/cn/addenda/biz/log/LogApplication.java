package cn.addenda.biz.log;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author addenda
 * @since 2022/1/14 15:44
 */
@EnableTransactionManagement(order = Ordered.LOWEST_PRECEDENCE - 70)
@MapperScan("cn.addenda.biz.log.mapper")
@SpringBootApplication
public class LogApplication {

  public static void main(String[] args) {
    SpringApplication.run(LogApplication.class, args);
  }

}
