package com.federicobonel.mapcoloring.api.v1;

import com.federicobonel.mapcoloring.api.v1.mapper.MapMapper;
import com.federicobonel.mapcoloring.api.v1.mapper.MapMapperImpl;
import com.federicobonel.mapcoloring.api.v1.model.MapDTO;
import com.federicobonel.mapcoloring.model.MapGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapMapperTest {

    public final static String[][] arrayMap
            = {
            {"WesternAustralia", "NorthernTerritory", "SouthAustralia"},
            {"NorthernTerritory", "Queensland", "SouthAustralia", "WesternAustralia"},
            {"SouthAustralia", "Queensland", "NewSouthWales", "Victoria", "NorthernTerritory", "WesternAustralia"},
            {"Queensland", "NewSouthWales", "NorthernTerritory", "SouthAustralia"},
            {"NewSouthWales", "Victoria", "Queensland", "SouthAustralia"},
            {"Victoria", "SouthAustralia", "NewSouthWales", "Tasmania"},
            {"Tasmania", "Victoria"}
    };

    public MapDTO mapDTO;

    public MapGraph mapGraph;

    public MapMapper mapMapper;

    @BeforeEach
    void setUp() {
        mapDTO = new MapDTO();
        mapDTO.setAdjacencyMap(arrayMap);
        mapDTO.setNumberOfNodes(arrayMap.length);

        mapGraph = new MapGraph(arrayMap, arrayMap.length);

        mapMapper = new MapMapperImpl();
    }

    @Test
    void mapToMapDTO() {
        MapDTO convertedMap = mapMapper.mapToMapDTO(mapGraph);

        for (int currNode = 0; currNode < arrayMap.length; currNode++) {

            for (int currAdjNode = 0; currAdjNode < arrayMap[currNode].length; currAdjNode++) {
                assertEquals(arrayMap[currNode][currAdjNode], convertedMap.getAdjacencyMap()[currNode][currAdjNode]);
            }

        }

        assertEquals(arrayMap.length, convertedMap.getNumberOfNodes());
    }

    @Test
    void mapDTOToMap() {
        MapGraph convertedMap = mapMapper.mapDTOToMap(mapDTO);

        List<Integer>[] convertedAdjMap = convertedMap.getAdj();

        for (int currNode = 0; currNode < arrayMap.length; currNode++) {

            assertEquals(arrayMap[currNode][0], convertedMap.getIndexToName()[currNode]);

            for (int currAdjNode = 1; currAdjNode < arrayMap[currNode].length; currAdjNode++) {
                assertEquals(arrayMap[currNode][currAdjNode],
                        convertedMap.getIndexToName()[convertedAdjMap[currNode].get(currAdjNode - 1)]);
            }

        }
    }
}