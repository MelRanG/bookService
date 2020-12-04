package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"dao", "service", "controller"})
@Import({DBConfig.class})

public class ApplicationConfig {
}
