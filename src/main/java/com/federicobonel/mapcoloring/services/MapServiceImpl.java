package com.federicobonel.mapcoloring.services;

import com.federicobonel.mapcoloring.api.v1.model.MapDTO;
import com.federicobonel.mapcoloring.api.v1.mapper.MapMapper;
import com.federicobonel.mapcoloring.model.MapGraph;
import org.springframework.stereotype.Service;

@Service
public class MapServiceImpl implements MapService {

    private final MapMapper mapMapper;

    public MapServiceImpl(MapMapper mapMapper) {
        this.mapMapper = mapMapper;
    }

    @Override
    public MapDTO getGreedyColoring(MapDTO mapDTO) {
        MapGraph mapGraph = mapMapper.mapDTOToMap(mapDTO);
        mapDTO.setColorsPerRegion(mapGraph.getGreedyMapColoring());
        return mapDTO;
    }

    @Override
    public MapDTO getBacktrackingColoring(MapDTO mapDTO) {
        MapGraph mapGraph = mapMapper.mapDTOToMap(mapDTO);
        mapDTO.setColorsPerRegion(mapGraph.getBacktrackingMapColoring());
        return mapDTO;
    }
}
