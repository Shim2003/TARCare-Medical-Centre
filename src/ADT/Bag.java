/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ADT;

/**
 *
 * @author user
 */
public class Bag<T> implements BagInterface<T> {

    private Object[] objects;
    private int size;
    private final int capacity;

    public Bag(int capacity) {
        this.capacity = capacity;
        this.objects = new Object[capacity];
    }

    @Override
    public boolean add(T object) {

        if (size >= capacity) {
            return false;
        } else {
            objects[size++] = object;
            return true;
        }

    }

    @Override
    public boolean remove(T object) {

        for (int i = 0; i < size; i++) {

            if (objects[i].equals(object)) {
                objects[i] = objects[size - 1];
                size--;
                return true;
            }

        }

        return false;

    }

    @Override
    public int getFrequencyOf(T object) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (objects[i].equals(object)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean contain(T item) {
        return getFrequencyOf(item) > 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int getCurrentSize() {
        return size;
    }

    @Override
    public void clear() {

        for (int i = 0; i < size; i++) {
            objects[i] = null;
        }

        size = 0;
    }

    @Override
    public T[] toArray() {
        // Create a new array of the right type and size
        T[] result = (T[]) new Object[size];

        for (int i = 0; i < size; i++) {
            result[i] = (T) objects[i];
        }

        return result;
    }

    @Override
    public void displayAll() {
        if (size == 0) {
            System.out.println("The bag is empty.");
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println(objects[i]);
        }
    }

    @Override
    public boolean isFull() {
        return size >= capacity;
    }

    @Override
    public boolean equals(Bag<T> otherBag) {
        if (otherBag == null || otherBag.getCurrentSize() != this.size) {
            return false;
        }

        // Copy both arrays to temporary arrays
        T[] thisArray = this.toArray();
        T[] otherArray = otherBag.toArray();

        // Check if each unique item has the same frequency in both bags
        for (int i = 0; i < thisArray.length; i++) {
            T item = thisArray[i];
            if (this.getFrequencyOf(item) != otherBag.getFrequencyOf(item)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public T getMostFrequent() {
        if (isEmpty()) {
            return null; // or throw an exception if preferred
        }

        T[] array = toArray();
        T mostFrequentItem = array[0];
        int highestFrequency = 0;

        for (int i = 0; i < array.length; i++) {
            int freq = getFrequencyOf(array[i]);
            if (freq > highestFrequency) {
                highestFrequency = freq;
                mostFrequentItem = array[i];
            }
        }

        return mostFrequentItem;
    }

}
