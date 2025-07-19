/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ADT;

/**
 *
 * @author user
 */
public interface BagInterface<T> {
    
    public boolean add(T object);
    
    public boolean remove(T object);
    
    public boolean contain(T object);
    
    public int getCurrentSize();
    
    public boolean isEmpty();
    
    public void displayAll();
    
    public Object[] toArray();
    
    public void clear();
    
    public int getFrequencyOf(T object);
    
    public boolean isFull();
    
    public boolean equals(Bag<T> otherBag);
    
    public T getMostFrequent();
}
