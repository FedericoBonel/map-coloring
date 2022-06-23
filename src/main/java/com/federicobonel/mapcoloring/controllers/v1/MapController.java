package com.federicobonel.mapcoloring.controllers.v1;

import com.federicobonel.mapcoloring.api.v1.model.MapDTO;
import com.federicobonel.mapcoloring.config.SwaggerConfig;
import com.federicobonel.mapcoloring.exceptions.InvalidQueryFormat;
import com.federicobonel.mapcoloring.services.MapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = {SwaggerConfig.MAP_TAG})
@RestController
@RequestMapping(MapController.MAPS_URL)
public class MapController {

    public static final String MAPS_URL = "/api/v1/maps";
    public static final String BACKTRACKING = "backtracking";
    public static final String GREEDY = "greedy";

    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @ApiOperation(value = "Finds the solution for a map represented as an adjacency list",
            notes = """ 
                    While the greedy algorithm is faster it's not complete, which means that finding a solution depends completely on the order of traversal through the regions.
                    The solution is given in the form of a set of names of each region with a number assigned to it, each number represents a distinct color.
                    """)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MapDTO getMapColoring(@RequestParam("algorithm")
                                     @ApiParam(name = "Algorithm type", value = "It can either be 'greedy' or 'backtracking'",
                                             example = "greedy") String algorithm,
                                 @RequestBody
                                 @ApiParam(name = "Map representation") MapDTO mapDTO) {

        if (mapDTO.getAdjacencyMap() == null || mapDTO.getAdjacencyMap().length == 0) {
            throw new InvalidQueryFormat();
        }

        if (algorithm.equalsIgnoreCase(BACKTRACKING)) {

            log.info("Getting backtracking map coloring");
            return mapService.getBacktrackingColoring(mapDTO);

        } else if (algorithm.equalsIgnoreCase(GREEDY)) {

            log.info("Getting greedy map coloring");
            return mapService.getGreedyColoring(mapDTO);

        } else {

            throw new InvalidQueryFormat();

        }
    }
}
