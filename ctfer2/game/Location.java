/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctfer2.game;

import interfaces.my.LocationADT;

/**
 *
 * @author Dinis
 */
public class Location implements LocationADT {
    private String name;
    // Outros atributos conforme necessário, como coordenadas se aplicável.

    public Location(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    // Implementações de outros métodos conforme necessário
}

