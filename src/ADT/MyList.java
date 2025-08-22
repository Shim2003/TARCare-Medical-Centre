package ADT;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author shim
 */
public interface MyList<T> {
    void add(T item);
    void add(int index, T item);
    T get(int index);   
    T remove(int index);     
    boolean isEmpty();  
    int size();         
    int indexOf(T item);  
    boolean contains(T item); 
    void clear();
    T getFirst();
    T getLast();
    T findFirst(Predicate<T> predicate);
    boolean anyMatch(Predicate<T> predicate);
    DynamicList<T> findAll(Predicate<T> predicate);
    void replace(int index, T newItem);
    int findIndex(Predicate<T> predicate);
    boolean removeIf(Predicate<T> predicate);
    Iterator<T> iterator();
    void quickSort(Comparator<T> comparator);
    DynamicList<T> clone();
    DynamicList<T> filter(Predicate<T> predicate);
}