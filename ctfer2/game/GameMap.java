package ctfer2.game;

import interfaces.my.LocationADT;
import implementations.Network; // Ou outra estrutura de dados conforme necessário

public class GameMap {
    private Network<LocationADT> map;

    public GameMap() {
        this.map = new Network<>();
    }

    public void addLocation(LocationADT location) {
        map.addVertex(location);
    }

    public void connectLocations(LocationADT loc1, LocationADT loc2, double distance) {
        map.addEdge(loc1, loc2, distance);
    }

    // Métodos adicionais conforme necessário
}

