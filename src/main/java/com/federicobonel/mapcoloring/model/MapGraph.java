package com.federicobonel.mapcoloring.model;

import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Implementation of a 2D region map as an undirected unweighted graph
 * We use two arrays:
 *      - one to represent the graph as an adjacency list (where each index is a region),
 *      - and another one to keep each of the region names mapped to it's assigned index
 *
 * @author Bonel Tozzi Federico Jorge
 */
@Data
public class MapGraph {

    /**
     * Adjacency list
     */
    private final List<Integer>[] adj;

    /**
     * Region names
     */
    private final String[] indexToName;

    /**
     * Number of regions (i.e. nodes in the graph)
     */
    private final int v;

    /**
     * Constructs Map from 2D array of strings
     * The data must be as an adjacency list in the following format:
     * ----------------------------------------------------------------------------
     * numberOfRegions
     * region1 regionAdjacentToRegion1 regionAdjacentToRegion1...
     * region2 regionAdjacentToRegion2 regionAdjacentToRegion2...
     * ...
     * ----------------------------------------------------------------------------
     * NOTE: (If the region1 and region2 are adjacent both of them must declare the relationship in its corresponding lines)
     *
     * @param adjancencyMap Map represented as an adjacency list of strings
     */
    public MapGraph(String[][] adjancencyMap, int v) {
        int currIndex = 0;
        this.v = v;
        this.adj = new LinkedList[v];
        this.indexToName = new String[v];

        // We'll use this hashtable to keep track of each name to each index as we construct the graph
        Hashtable<String, Integer> nameToIndex = new Hashtable<>(v);

        for (int i = 0; i < adj.length; i++) adj[i] = new LinkedList<>();

        // Iterate through the file building up the graph/map
        for (String[] currNode : adjancencyMap) {
            // if any of the regions does not contain a neighbor region add it by itself (i.e. isles)
            if (currNode.length == 1 && !currNode[0].isEmpty()) {
                currIndex = addNode(currNode[0], currIndex, nameToIndex);
            } else {
                for (int i = 1; i < currNode.length; i++) {
                    currIndex = addEdge(currNode[0], currNode[i], currIndex, nameToIndex);
                }
            }
        }
    }

    /**
     * Constructor: It picks the data from a file
     * The data must be as an adjacency list in a txt file in the following format:
     * ----------------------------------------------------------------------------
     * numberOfRegions
     * region1 regionAdjacentToRegion1 regionAdjacentToRegion1...
     * region2 regionAdjacentToRegion2 regionAdjacentToRegion2...
     * ...
     * ----------------------------------------------------------------------------
     * NOTE: (If the region1 and region2 are adjacent both of them must declare the relationship in its corresponding lines)
     *
     * @param file txt file that contains the adjacency list
     * @throws FileNotFoundException if the file passed as a parameter does not exist in its path
     */
    public MapGraph(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        String[] currLine;
        int currIndex = 0;
        this.v = sc.nextInt();
        this.adj = new LinkedList[v];
        this.indexToName = new String[v];

        // We'll use this hashtable to keep track of each name to each index as we construct the graph
        Hashtable<String, Integer> nameToIndex = new Hashtable<>(v);

        for (int i = 0; i < adj.length; i++) adj[i] = new LinkedList<>();
        // Iterate through the file building up the graph/map
        while (sc.hasNext()) {
            currLine = sc.nextLine().split(" ");
            // if any of the regions does not contain a neighbor region add it by itself (i.e. isles)
            if (currLine.length == 1 && !currLine[0].isEmpty()) {
                currIndex = addNode(currLine[0], currIndex, nameToIndex);
            } else {
                for (int i = 1; i < currLine.length; i++) {
                    currIndex = addEdge(currLine[0], currLine[i], currIndex, nameToIndex);
                }
            }
        }
    }

    /**
     * Add an edge between the passed nodes and return the current empty index in the adjacency list
     *
     * @param endpoint1 Name of the first endpoint in the edge
     * @param endpoint2 Name of the second endpoint in the edge
     * @param currIndex The current empty index in the adjacency list
     * @param nameToIndex Hashtable containing the assignment of each region name to each index
     * @return Updated empty index in the adjacency list
     */
    private int addEdge(String endpoint1, String endpoint2, int currIndex, Hashtable<String, Integer> nameToIndex) {
        // If any of the nodes are not registered yet, register them
        if (!nameToIndex.containsKey(endpoint1)) {
            indexToName[currIndex] = endpoint1;
            nameToIndex.put(endpoint1, currIndex);
            currIndex++;
        }
        if (!nameToIndex.containsKey(endpoint2)) {
            indexToName[currIndex] = endpoint2;
            nameToIndex.put(endpoint2, currIndex);
            currIndex++;
        }
        // Add them to the adjacency list
        adj[nameToIndex.get(endpoint1)].add(nameToIndex.get(endpoint2));
        return currIndex;
    }

    /**
     * Adds one node to the graph
     *
     * @param node Node to add
     * @param currIndex The current empty index in the adjacency list
     * @param nameToIndex Hashtable containing the mapping of each region name to each index
     * @return Updated empty index in the adjacency list
     */
    private int addNode(String node, int currIndex, Hashtable<String, Integer> nameToIndex) {
        if (!nameToIndex.containsKey(node)) {
            nameToIndex.put(node, currIndex);
            indexToName[currIndex] = node;
            currIndex++;
        }
        return currIndex;
    }

    /**
     * Greedy implementation of the map coloring problem with only 4 colors,
     * it's correctness depends on the order in which the graph is transversed
     *
     * Time complexity = O(numberVertices * numberColors + numberEdges)
     *                 = O(numberVertices + numberEdges) [Since numberColors is always == 4]
     *
     * Space complexity = O(numberVertices)
     *
     * @return A hashtable that contains each region of the map (key) and its corresponding assigned color (value)
     */
    public Map<String, Integer> getGreedyMapColoring() {
        int[] result = new int[v];
        Arrays.fill(result, -1);
        boolean[] availableColors = new boolean[4];
        Arrays.fill(availableColors, true);
        // Color each one of the regions, including those that are unconnected (i.e. isles)
        for (int i = 0; i < adj.length; i++) {
            if (result[i] == -1) bfsColor(i, availableColors, result);
        }
        // Create the final result with the name of each region, and it's corresponding color as number
        Map<String, Integer> finalColoration = new HashMap<>(v);
        for (int i = 0; i < result.length; i++) {
            finalColoration.put(indexToName[i], result[i]);
        }
        return finalColoration;
    }

    /**
     * Transverses the graph in BFS ordering painting each region in a different color than it's neighbors
     *
     * @param startPoint     Starting point in the map
     * @param availableColor List of available colors (true if available, false otherwise)
     * @param result         The end result where each index represents each node
     */
    private void bfsColor(int startPoint, boolean[] availableColor, int[] result) {
        List<Integer> neighbors;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startPoint);
        while (!queue.isEmpty()) {
            int currNode = queue.remove();
            neighbors = adj[currNode];
            // For each neighbor, check if it's not painted and not in the queue (result == -1)
            // and if it's painted mark it's color as not available
            for (Integer neighbor : neighbors) {
                if (result[neighbor] == -1) {
                    queue.add(neighbor);
                    result[neighbor] = -2;
                } else if (result[neighbor] >= 0) {
                    availableColor[result[neighbor]] = false;
                }
            }
            // Get the first available color as result for this node
            for (int color = 0; color < availableColor.length; color++) {
                if (availableColor[color]) {
                    result[currNode] = color;
                    break;
                }
            }
            // If after that loop the node is still no painted, then the algorithm can't find a solution
            if (result[currNode] == -1)
                throw new RuntimeException("Greedy Algorithm couldn't find a solution using only 4 colors");
            Arrays.fill(availableColor, true);
        }
    }


    /**
     * Backtracking implementation of the map coloring problem,
     * it's always correct since it explores all possible configurations
     *
     * Time complexity = O(numberColors ^ numberVertices)
     *                 = O(4 ^ numberVertices) [Since numberColors is always == 4]
     * Space complexity = O(numberVertices)
     *
     * @return A hashtable that contains each region of the map (key) and its corresponding color (value)
     */
    public Map<String, Integer> getBacktrackingMapColoring() {
        int availableColors = 4;
        int currentNodeIndex = 0;
        int[] result = new int[v];
        Arrays.fill(result, -1);
        if (!backtrackingColor(result, currentNodeIndex, availableColors))
            throw new RuntimeException("Back tracking algorithm couldn't find a solution with only 4 colors, this means that the graph does not represents a map");
        Map<String, Integer> finalColoration = new HashMap<>(v);
        for (int i = 0; i < result.length; i++) {
            finalColoration.put(indexToName[i], result[i]);
        }
        return finalColoration;
    }

    /**
     * Main backtracking recursive call, it explores all possible combinations (state space) of the colored map
     *
     * @param resultSoFar      Contains the result so far for each node
     * @param currentNodeIndex Current node to be painted
     * @param availableColors  Number of available colors to paint the map
     * @return True if it reached a valid coloration, false otherwise
     */
    private boolean backtrackingColor(int[] resultSoFar,
                                      int currentNodeIndex, int availableColors) {
        // Base case: All nodes are colored
        if (currentNodeIndex == v) return true;
        // Go through all available colors and paint and recurse when possible
        for (int color = 0; color < availableColors; color++) {
            if (notAssigned(currentNodeIndex, color, resultSoFar)) {
                resultSoFar[currentNodeIndex] = color;
                // If the recursive call returns true, then we have hit a valid coloration, return true
                if (backtrackingColor(resultSoFar, currentNodeIndex + 1, availableColors)) return true;
                // Otherwise, reset the color of this node to check the next available one
                resultSoFar[currentNodeIndex] = -1;
            }
        }
        // If we checked all colors and none of them was valid,
        // then it's not possible to reach a solution with the current configuration, return false
        return false;
    }

    /**
     * Checks if the passed color is assigned to any of the current node neighbors
     *
     * @param currNode    Current node's index
     * @param color       Number that represents the color to check
     * @param resultSoFar Current coloration
     * @return true if it's not assigned to any neighbor, false otherwise
     */
    private boolean notAssigned(int currNode, int color, int[] resultSoFar) {
        List<Integer> neighbors = adj[currNode];
        for (Integer neighbor : neighbors) {
            if (color == resultSoFar[neighbor]) return false;
        }
        return true;
    }

}
