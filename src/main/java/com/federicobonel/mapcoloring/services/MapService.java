package com.federicobonel.mapcoloring.services;

import com.federicobonel.mapcoloring.api.v1.model.MapDTO;
import com.federicobonel.mapcoloring.api.v1.model.MapResponseDTO;

public interface MapService {

    MapResponseDTO getGreedyColoring(MapDTO mapDTO);

    MapResponseDTO getBacktrackingColoring(MapDTO mapDTO);

}
