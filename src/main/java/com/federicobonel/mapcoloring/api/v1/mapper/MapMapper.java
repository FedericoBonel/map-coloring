package com.federicobonel.mapcoloring.api.v1.mapper;

import com.federicobonel.mapcoloring.api.v1.model.MapDTO;
import com.federicobonel.mapcoloring.model.MapGraph;

public interface MapMapper {

    MapDTO mapToMapDTO(MapGraph mapGraph);

    MapGraph mapDTOToMap(MapDTO mapDTO);

}
