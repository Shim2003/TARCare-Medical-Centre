package ADT;

import java.util.function.Predicate;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
public class DynamicList<T> implements MyList<T> {

    private T[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public DynamicList() {
        data = (T[]) new Object[10];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public DynamicList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        data = (T[]) new Object[initialCapacity];
        size = 0;
    }

    @Override
    public void add(T item) {
        ensureCapacity();
        data[size++] = item;
    }

    @Override
    public void add(int index, T item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        ensureCapacity();
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = item;
        size++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return data[index];
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T removed = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[--size] = null;
        return removed;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int indexOf(T item) {
        if (item == null) {
            for (int i = 0; i < size; i++) {
                if (data[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (item.equals(data[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean contains(T item) {
        return indexOf(item) >= 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    @Override
    public T getFirst() {
        if (isEmpty()) {
            return null;
        } else {
            return data[0];
        }
    }

    @Override
    public T getLast() {
        if (isEmpty()) {
            return null;
        } else {
            return data[size - 1];
        }
    }

    @Override
    public T findFirst(Predicate<T> predicate) {
        for (int i = 0; i < size; i++) {
            if (predicate.test(data[i])) {
                return data[i];
            }
        }
        return null;
    }

    @Override
    public boolean anyMatch(Predicate<T> predicate) {
        for (int i = 0; i < size; i++) {
            if (predicate.test(data[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public DynamicList<T> findAll(Predicate<T> predicate) {
        DynamicList<T> result = new DynamicList<>();
        for (int i = 0; i < size; i++) {
            if (predicate.test(data[i])) {
                result.add(data[i]);
            }
        }
        return result;
    }

    @Override
    public void replace(int index, T newItem) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        data[index] = newItem;
    }

    @Override
    public int findIndex(Predicate<T> predicate) {
        for (int i = 0; i < size; i++) {
            if (predicate.test(data[i])) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (size == data.length) {
            T[] newData = (T[]) new Object[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }
            data = newData;
        }
    }

    @Override
    public boolean removeIf(Predicate<T> predicate) {
        boolean removed = false;
        for (int i = size - 1; i >= 0; i--) {  // Iterate backwards to avoid index shifting issues
            if (predicate.test(data[i])) {
                remove(i);
                removed = true;
            }
        }
        return removed;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] result = (T[]) new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = data[i];
        }
        return result;
    }
}
