package com.federicobonel.mapcoloring.api.v1.mapper;

import com.federicobonel.mapcoloring.api.v1.model.MapDTO;
import com.federicobonel.mapcoloring.model.MapGraph;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapMapperImpl implements MapMapper {

    @Override
    public MapDTO mapToMapDTO(MapGraph mapGraph) {
        MapDTO mapDTO = new MapDTO();

        String[][] map = extractMapAs2DArr(mapGraph);

        mapDTO.setAdjacencyMap(map);
        mapDTO.setNumberOfNodes(mapGraph.getV());

        return mapDTO;
    }

    private String[][] extractMapAs2DArr(MapGraph mapGraph) {
        String[][] map = new String[mapGraph.getV()][];

        List<Integer>[] adjacencyMap = mapGraph.getAdj();

        for (int currNode = 0; currNode < adjacencyMap.length; currNode++) {
            map[currNode] = new String[adjacencyMap.length + 1];
            map[currNode][0] = mapGraph.getIndexToName()[currNode];
            int currIndex = 1;

            for (int adjNodeIndex : adjacencyMap[currNode]) {
                map[currNode][currIndex++] = mapGraph.getIndexToName()[adjNodeIndex];
            }
        }
        return map;
    }

    @Override
    public MapGraph mapDTOToMap(MapDTO mapDTO) {
        return new MapGraph(mapDTO.getAdjacencyMap(), mapDTO.getNumberOfNodes());
    }
}
