package az.shopery.configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "claude.api")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClaudeApiConfig {
    String key;
    String url;
    String model;
    Integer maxTokens;
    String version;
}
