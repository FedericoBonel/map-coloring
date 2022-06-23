package com.federicobonel.mapcoloring.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class MapGraphTest {

    @Autowired
    ResourceLoader resourceLoader;

    MapGraph mapGraph;

    @Test
    void getGreedyMapColoringAustralia() throws IOException {

        Resource fileRes = resourceLoader.getResource("classpath:static/samplemaps/InputSampleAustralia.txt");

        File file = fileRes.getFile();
        MapGraph map = new MapGraph(file);
        Map<String, Integer> mapColoringGreedy = map.getGreedyMapColoring();

        assertDifferentColors(mapColoringGreedy, map.getAdj(), map.getIndexToName());
    }

    @Test
    void getGreedyMapColoringUs() throws IOException {

        Resource fileRes = resourceLoader.getResource("classpath:static/samplemaps/InputSampleUSA.txt");

        File file = fileRes.getFile();
        MapGraph map = new MapGraph(file);
        Map<String, Integer> mapColoringGreedy = map.getGreedyMapColoring();

        assertDifferentColors(mapColoringGreedy, map.getAdj(), map.getIndexToName());
    }

    @Test
    void getBackTrackingMapColoringAustralia() throws IOException {

        Resource fileRes = resourceLoader.getResource("classpath:static/samplemaps/InputSampleAustralia.txt");

        File file = fileRes.getFile();
        MapGraph map = new MapGraph(file);
        Map<String, Integer> mapColoringGreedy = map.getBacktrackingMapColoring();

        assertDifferentColors(mapColoringGreedy, map.getAdj(), map.getIndexToName());
    }

    @Test
    void getBackTrackingMapColoringUs() throws IOException {

        Resource fileRes = resourceLoader.getResource("classpath:static/samplemaps/InputSampleUSA.txt");

        File file = fileRes.getFile();
        MapGraph map = new MapGraph(file);
        Map<String, Integer> mapColoringGreedy = map.getBacktrackingMapColoring();

        assertDifferentColors(mapColoringGreedy, map.getAdj(), map.getIndexToName());
    }

    private void assertDifferentColors(Map<String, Integer> mapColoring,
                                              List<Integer>[] map,
                                              String[] indexToName) {

        for (int region = 0; region < map.length; region ++) {

            String currRegion = indexToName[region];

            for (int adjRegion = 0; adjRegion < map[region].size(); adjRegion ++) {

                String currAdjRegion = indexToName[map[region].get(adjRegion)];

                assertNotEquals(mapColoring.get(currRegion), mapColoring.get(currAdjRegion));
            }
        }

    }
}
