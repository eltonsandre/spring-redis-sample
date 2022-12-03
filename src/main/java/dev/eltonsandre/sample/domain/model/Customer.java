package dev.eltonsandre.sample.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.springframework.web.bind.annotation.PutMapping;

import java.io.Serializable;

@Data
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Customer implements Serializable {

    @NotBlank(groups = {PutMapping.class})
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String document;

}
