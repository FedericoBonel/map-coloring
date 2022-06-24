package com.federicobonel.mapcoloring.services;

import com.federicobonel.mapcoloring.api.v1.model.MapDTO;
import com.federicobonel.mapcoloring.api.v1.mapper.MapMapper;
import com.federicobonel.mapcoloring.api.v1.model.MapResponseDTO;
import com.federicobonel.mapcoloring.model.MapGraph;
import org.springframework.stereotype.Service;

@Service
public class MapServiceImpl implements MapService {

    private final MapMapper mapMapper;

    public MapServiceImpl(MapMapper mapMapper) {
        this.mapMapper = mapMapper;
    }

    @Override
    public MapResponseDTO getGreedyColoring(MapDTO mapDTO) {
        MapGraph mapGraph = mapMapper.mapDTOToMap(mapDTO);
        return new MapResponseDTO(mapDTO, mapGraph.getGreedyMapColoring());
    }

    @Override
    public MapResponseDTO getBacktrackingColoring(MapDTO mapDTO) {
        MapGraph mapGraph = mapMapper.mapDTOToMap(mapDTO);
        return new MapResponseDTO(mapDTO, mapGraph.getBacktrackingMapColoring());
    }
}
