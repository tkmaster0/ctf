/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces.my;

/**
 *
 * @author Dinis
 */
public interface MovementStrategyADT {
    // Método para determinar o próximo movimento com base na localização atual.
    LocationADT nextMove(LocationADT currentLocation);
}

