package com.borisov.DZ_4.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

public class HateoasUserResponseDTO extends UserResponseDTO {

    @Schema(description = "HATEOAS-ссылки")
    private Map<String, Object> _links;

    public Map<String, Object> get_links() {
        return _links;
    }

    public void set_links(Map<String, Object> _links) {
        this._links = _links;
    }
}

