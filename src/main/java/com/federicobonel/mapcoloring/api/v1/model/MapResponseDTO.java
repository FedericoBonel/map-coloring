package com.federicobonel.mapcoloring.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class MapResponseDTO {

    @ApiModelProperty(value = "The solution's map")
    @JsonProperty("map")
    private MapDTO mapDTO;

    @ApiModelProperty(value = "The solution for the map coloring problem")
    @JsonProperty("colors_per_region")
    private Map<String, Integer> colorsPerRegion;
}
