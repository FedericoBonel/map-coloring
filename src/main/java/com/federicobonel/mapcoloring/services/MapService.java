package com.federicobonel.mapcoloring.services;

import com.federicobonel.mapcoloring.api.v1.model.MapDTO;

public interface MapService {

    MapDTO getGreedyColoring(MapDTO mapDTO);

    MapDTO getBacktrackingColoring(MapDTO mapDTO);

}
