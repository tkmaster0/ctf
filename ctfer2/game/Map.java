/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctfer2.game;

import java.util.Random;
import implementations.Graph;

/**
 *
 * @author Dinis
 */
public class Map {

    private Graph<MapLocation> graph;
    private int size; // Número de localizações no mapa
    private boolean isBidirectional;
    private double edgeDensity;

    /**
     * Construtor para inicializar um mapa.
     *
     * @param size Tamanho do mapa (número de localizações).
     * @param isBidirectional Define se as conexões entre localizações são
     * bidirecionais.
     * @param edgeDensity A densidade das arestas (conexões) no mapa.
     * @param useRandomGeneration Define se o mapa deve ser gerado
     * aleatoriamente.
     */
    public Map(int size, boolean isBidirectional, double edgeDensity, boolean useRandomGeneration) {
        this.size = size;
        this.isBidirectional = isBidirectional;
        this.edgeDensity = edgeDensity;
        this.graph = new Graph<>();
        if (useRandomGeneration) {
            initializeRandomMap();
        } else {
            initializeMap();
        }
    }

    /**
     * Inicializa o mapa com localizações e conexões predefinidas.
     */
    private void initializeMap() {
        // Inicializar o mapa com localizações
        for (int i = 0; i < size; i++) {
            MapLocation location = new MapLocation(i, 0); // Exemplo: localizações em uma linha
            graph.addVertex(location);
        }

        // Adicionar caminhos entre localizações
        // Este é um exemplo simples, onde cada localização é conectada com a próxima
        for (int i = 0; i < size - 1; i++) {
            graph.addEdge(new MapLocation(i, 0), new MapLocation(i + 1, 0));
        }
    }

    /**
     * Inicializa o mapa com localizações e conexões geradas aleatoriamente.
     */
    private void initializeRandomMap() {
        Random random = new Random();
        // Geração aleatória de localizações e caminhos
        for (int i = 0; i < size; i++) {
            MapLocation location = new MapLocation(random.nextInt(size), random.nextInt(size));
            graph.addVertex(location);
        }

        // Geração aleatória de caminhos
        for (int i = 0; i < size; i++) {
            MapLocation from = graph.getVertices().get(random.nextInt(size));
            MapLocation to = graph.getVertices().get(random.nextInt(size));
            if (!from.equals(to)) {
                graph.addEdge(from, to);
            }
        }
    }

    /**
     * Adiciona um caminho entre duas localizações no mapa.
     *
     * @param from Localização de origem.
     * @param to Localização de destino.
     */
    public void addPath(MapLocation from, MapLocation to) {
        if (graph.containsVertex(from) && graph.containsVertex(to)) {
            graph.addEdge(from, to);
        }
    }

    /**
     * Remove um caminho entre duas localizações no mapa.
     *
     * @param from Localização de origem.
     * @param to Localização de destino.
     */
    public void removePath(MapLocation from, MapLocation to) {
        if (graph.containsVertex(from) && graph.containsVertex(to)) {
            graph.removeEdge(from, to);
        }
    }

    /**
     * Verifica se existe um caminho entre duas localizações no mapa.
     *
     * @param from Localização de origem.
     * @param to Localização de destino.
     * @return true se existe um caminho entre as localizações, false caso
     * contrário.
     */
    public boolean hasPath(MapLocation from, MapLocation to) {
        return graph.isEdge(from, to);
    }

    /**
     * Obtém todas as localizações no mapa.
     *
     * @return Uma lista de todas as localizações no mapa.
     */
    public LinkedList<MapLocation> getAllLocations() {
        LinkedList<MapLocation> locations = new LinkedList<>();
        LinkedList.Iterator<MapLocation> iterator = graph.getVertices().iterator();
        while (iterator.hasNext()) {
            locations.add(iterator.next());
        }
        return locations;
    }

    /**
     * Obtém as localizações vizinhas a uma determinada localização no mapa.
     *
     * @param location A localização para a qual se deseja obter os vizinhos.
     * @return Uma lista de localizações vizinhas.
     */
    public LinkedList<MapLocation> getNeighbors(MapLocation location) {
        return graph.getNeighbors(location);
    }

    /**
     * Obtém o tamanho do mapa (número de localizações).
     *
     * @return O tamanho do mapa.
     */
    public int getSize() {
        return size;
    }
}
