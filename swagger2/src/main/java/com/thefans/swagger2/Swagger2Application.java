package com.thefans.swagger2;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import org.springframework.cloud.sleuth.metric.SpanMetricReporter;
import org.springframework.cloud.sleuth.zipkin2.ZipkinProperties;
import org.springframework.cloud.sleuth.zipkin2.ZipkinSpanReporter;

@EnableSwagger2
@RestController
@SpringBootApplication
public class Swagger2Application {
    private static final Logger LOG = Logger.getLogger(Swagger2Application.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(Swagger2Application.class, args);
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage(
                "com.thefans.swagger2"
            )).build();
    }

    @RequestMapping("/")
    public String index() {
        LOG.log(Level.INFO, "Index API is calling");
        return "Welcome Sleuth!";
    }

    @Autowired
    private SpanMetricReporter spanMetricReporter;
     
    @Autowired
    private ZipkinProperties zipkinProperties;
     
    @Value("${spring.sleuth.web.skipPattern}")
    private String skipPattern;
    
    @Value("${spring.zipkin.baseUrl}")
    private String baseUrl;

    @Bean
    public ZipkinSpanReporter makeZipkinSpanReporter() {
        return new ZipkinSpanReporter() {
            private HttpZipkinSpanReporter delegate;
    
            @Override
            public void report(Span span) {
                if (delegate != null) delegate = new HttpZipkinSpanReporter(new RestTemplate(), 
                    baseUrl, zipkinProperties.getFlushInterval(), spanMetricReporter);
                if (!span.name.matches(skipPattern)) delegate.report(span);
            }
        };
    }
}
