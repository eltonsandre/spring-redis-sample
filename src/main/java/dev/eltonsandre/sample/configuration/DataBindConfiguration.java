package dev.eltonsandre.sample.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class DataBindConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder
                .featuresToEnable(
                        MapperFeature.DEFAULT_VIEW_INCLUSION,
                        DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
                .failOnUnknownProperties(false)
                .serializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
