/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ADT;

import java.util.function.Predicate;

/**
 *
 * @author User
 */
public interface ListInterface<T> {

    void add(T data);

    T get(int index);

    void remove(int index);

    boolean anyMatch(Predicate<T> predicate);

    void clear();

    boolean contains(T item);

    int size();

    ListInterface<T> clone();
    
    boolean isEmpty();
}
