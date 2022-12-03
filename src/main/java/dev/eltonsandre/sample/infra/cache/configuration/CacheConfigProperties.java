package dev.eltonsandre.sample.infra.cache.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Map;


@Getter
@Setter
@ConfigurationProperties("cache-config")
public class CacheConfigProperties {

    private Map<String, Config> caches = Map.of();

    @Data
    public static class Config {

       private boolean disabled;
        private Duration timeToLive = Duration.ofMinutes(1);

    }


}
