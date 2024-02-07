package ctfer2.game;

import interfaces.my.LocationADT;
import interfaces.my.MovementStrategyADT;

/**
 *
 * @author Dinis
 */
public class Bot {
    private LocationADT currentLocation;
    private MovementStrategyADT strategy;

    public Bot(LocationADT startingLocation, MovementStrategyADT strategy) {
        this.currentLocation = startingLocation;
        this.strategy = strategy;
    }

    public void move() {
        this.currentLocation = strategy.nextMove(currentLocation);
    }

    // Outros métodos conforme necessário
}

