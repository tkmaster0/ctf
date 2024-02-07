/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces.my;

/**
 *
 * @author Dinis
 */
public interface MovementStrategy {
    // Método para determinar o próximo movimento do bot
    Location nextMove(Location currentLocation);
}
