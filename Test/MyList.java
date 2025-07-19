/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author jecsh
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
}