/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementations;

/**
 * Searches the specified array of objects using a linear search algorithm.
 *
 * @param data the array to be sorted
 * @param min the integer representation of the min value
 * @param max the integer representation of the max value
 * @param target the element being searched for
 * @return true if the desired element is found
 */
public static <T extends Comparable<? super T>> boolean linearSearch (T[] data, int min, int max, T target) {
 int index = min;
 boolean found = false;
 while (!found && index <= max) 
 {
 if (data[index].compareTo(target) == 0) 
 found = true;
 index++;
 }
 return found;
 }
