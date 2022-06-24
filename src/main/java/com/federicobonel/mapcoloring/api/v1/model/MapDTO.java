package com.federicobonel.mapcoloring.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class MapDTO {

    @ApiModelProperty(value = "Number of regions in the map", example = "5", required = true)
    @JsonProperty("number_of_nodes")
    private int numberOfNodes;

    @ApiModelProperty(value = "The map represented as an adjacency list", required = true)
    @JsonProperty("adjacency_map")
    private String[][] adjacencyMap;
}
