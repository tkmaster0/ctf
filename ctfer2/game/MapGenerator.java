package ctfer2.game;

import interfaces.GraphADT;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

public class MapGenerator {
    private Graph<Integer> map;
    private int numberOfLocations;
    private double density;

    public MapGenerator(GraphADT<Integer> graph, int numberOfLocations, double density) {
        this.map = graph;
        this.numberOfLocations = numberOfLocations;
        this.density = density;
    }

    public void generateMap() {
        Random rand = new Random();
        for (int i = 0; i < numberOfLocations; i++) {
            map.addVertex(i);
        }

        int possibleEdges = numberOfLocations * (numberOfLocations - 1) / 2;
        int numberOfEdges = (int) (density * possibleEdges);
        
        while (numberOfEdges > 0) {
            int vertex1 = rand.nextInt(numberOfLocations);
            int vertex2 = rand.nextInt(numberOfLocations);
            if (vertex1 != vertex2 && !map.areVerticesConnected(vertex1, vertex2)) {
                map.addEdge(vertex1, vertex2);
                numberOfEdges--;
            }
        }
    }

    public void exportMap(String filePath) {
        JSONArray graphArray = new JSONArray();
        for (Integer vertex : map.getVertices()) {
            JSONObject vertexObj = new JSONObject();
            vertexObj.put("vertex", vertex);
            JSONArray adjacentVertices = new JSONArray();
            for (Integer adjacentVertex : map.getAdjacentVertices(vertex)) {
                adjacentVertices.add(adjacentVertex);
            }
            vertexObj.put("adjacentVertices", adjacentVertices);
            graphArray.add(vertexObj);
        }

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(graphArray.toJSONString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importMap(String filePath) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            JSONArray graphList = (JSONArray) jsonParser.parse(reader);
            for (Object o : graphList) {
                JSONObject vertexObj = (JSONObject) o;
                Integer vertex = ((Long) vertexObj.get("vertex")).intValue();
                map.addVertex(vertex);
                JSONArray adjacentVertices = (JSONArray) vertexObj.get("adjacentVertices");
                for (Object adjVertexObj : adjacentVertices) {
                    Integer adjVertex = ((Long) adjVertexObj).intValue();
                    map.addEdge(vertex, adjVertex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Graph<Integer> getMap() {
        return map;
    }
}
