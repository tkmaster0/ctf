package ctfer2.game;

import interfaces.Graph;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonHelper {

    // Converte um Graph<Integer> em uma representação JSON e salva em um arquivo
    public static void exportGraphToJsonFile(Graph<Integer> graph, String filePath) {
        JSONArray graphArray = new JSONArray();
        // Assumindo que Graph<Integer> tem um método para obter todos os vértices e outro para obter vértices adjacentes
        for (Integer vertex : graph.getVertices()) {
            JSONObject vertexObj = new JSONObject();
            vertexObj.put("vertex", vertex);
            JSONArray adjacentVertices = new JSONArray();
            for (Integer adjacent : graph.getAdjacentVertices(vertex)) {
                adjacentVertices.add(adjacent);
            }
            vertexObj.put("adjacentVertices", adjacentVertices);
            graphArray.add(vertexObj);
        }

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(graphArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
            // Pode-se adicionar mais lógica de tratamento de erro conforme necessário
        }
    }

    // Lê uma representação JSON de um grafo de um arquivo e converte de volta em um Graph<Integer>
    public static Graph<Integer> importGraphFromJsonFile(String filePath) {
        JSONParser jsonParser = new JSONParser();
        Graph<Integer> graph = new SimpleGraph<>(); // Substitua SimpleGraph pela sua implementação concreta de Graph

        try (FileReader reader = new FileReader(filePath)) {
            JSONArray graphList = (JSONArray) jsonParser.parse(reader);
            for (Object o : graphList) {
                JSONObject vertexObj = (JSONObject) o;
                Integer vertex = ((Long) vertexObj.get("vertex")).intValue();
                graph.addVertex(vertex);
                JSONArray adjacentVertices = (JSONArray) vertexObj.get("adjacentVertices");
                for (Object adjVertexObj : adjacentVertices) {
                    Integer adjVertex = ((Long) adjVertexObj).intValue();
                    graph.addEdge(vertex, adjVertex);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            // Pode-se adicionar mais lógica de tratamento de erro conforme necessário
        }

        return graph;
    }
}

