package ADT;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
public class DynamicList<T> implements MyList<T> {

    private Object[] buffer;
    private int gapStart;
    private int gapEnd;
    private int capacity;
    
    private static final int INITIAL_CAPACITY = 16;

    public DynamicList() {
        capacity = INITIAL_CAPACITY;
        buffer = new Object[capacity];
        gapStart = 0;
        gapEnd = capacity;
    }

    public DynamicList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        capacity = Math.max(initialCapacity, INITIAL_CAPACITY);
        buffer = new Object[capacity];
        gapStart = 0;
        gapEnd = capacity;
    }

    @Override
    public void add(T item) {
        add(size(), item);
    }

    @Override
    public void add(int index, T item) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        
        moveGapTo(index);
        
        if (gapStart == gapEnd) {
            expandGap();
        }
        
        buffer[gapStart] = item;
        gapStart++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        
        if (index < gapStart) {
            return (T) buffer[index];
        } else {
            return (T) buffer[index + gapSize()];
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        
        T removed;
        
        if (index < gapStart) {
            removed = (T) buffer[index];
            System.arraycopy(buffer, index + 1, buffer, index, gapStart - index - 1);
            gapStart--;
            buffer[gapStart] = null;
        } else {
            int actualIndex = index + gapSize();
            removed = (T) buffer[actualIndex];
            System.arraycopy(buffer, actualIndex + 1, buffer, actualIndex, capacity - actualIndex - 1);
            gapEnd++;
            buffer[capacity - 1] = null;
        }
        
        return removed;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return capacity - gapSize();
    }

    @Override
    public int indexOf(T item) {
        if (item == null) {
            // Check before gap
            for (int i = 0; i < gapStart; i++) {
                if (buffer[i] == null) {
                    return i;
                }
            }
            // Check after gap
            for (int i = gapEnd; i < capacity; i++) {
                if (buffer[i] == null) {
                    return i - gapSize();
                }
            }
        } else {
            // Check before gap
            for (int i = 0; i < gapStart; i++) {
                if (item.equals(buffer[i])) {
                    return i;
                }
            }
            // Check after gap
            for (int i = gapEnd; i < capacity; i++) {
                if (item.equals(buffer[i])) {
                    return i - gapSize();
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
        // Clear all elements and reset gap to cover entire buffer
        for (int i = 0; i < capacity; i++) {
            buffer[i] = null;
        }
        gapStart = 0;
        gapEnd = capacity;
    }

    @Override
    public T getFirst() {
        if (isEmpty()) {
            return null;
        } else {
            return get(0);
        }
    }

    @Override
    public T getLast() {
        if (isEmpty()) {
            return null;
        } else {
            return get(size() - 1);
        }
    }

    @Override
    public T findFirst(Predicate<T> predicate) {
        // Check before gap
        for (int i = 0; i < gapStart; i++) {
            @SuppressWarnings("unchecked")
            T item = (T) buffer[i];
            if (predicate.test(item)) {
                return item;
            }
        }
        // Check after gap
        for (int i = gapEnd; i < capacity; i++) {
            @SuppressWarnings("unchecked")
            T item = (T) buffer[i];
            if (predicate.test(item)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public boolean anyMatch(Predicate<T> predicate) {
        return findFirst(predicate) != null;
    }

    @Override
    public DynamicList<T> findAll(Predicate<T> predicate) {
        DynamicList<T> result = new DynamicList<>();
        // Check before gap
        for (int i = 0; i < gapStart; i++) {
            @SuppressWarnings("unchecked")
            T item = (T) buffer[i];
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        // Check after gap
        for (int i = gapEnd; i < capacity; i++) {
            @SuppressWarnings("unchecked")
            T item = (T) buffer[i];
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public void replace(int index, T newItem) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        
        if (index < gapStart) {
            buffer[index] = newItem;
        } else {
            buffer[index + gapSize()] = newItem;
        }
    }

    @Override
    public int findIndex(Predicate<T> predicate) {
        // Check before gap
        for (int i = 0; i < gapStart; i++) {
            @SuppressWarnings("unchecked")
            T item = (T) buffer[i];
            if (predicate.test(item)) {
                return i;
            }
        }
        // Check after gap
        for (int i = gapEnd; i < capacity; i++) {
            @SuppressWarnings("unchecked")
            T item = (T) buffer[i];
            if (predicate.test(item)) {
                return i - gapSize();
            }
        }
        return -1;
    }

    @Override
    public boolean removeIf(Predicate<T> predicate) {
        boolean removed = false;
        // Remove from back to front to avoid index shifting issues
        for (int i = size() - 1; i >= 0; i--) {
            if (predicate.test(get(i))) {
                remove(i);
                removed = true;
            }
        }
        return removed;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] result = (T[]) new Object[size()];
        int resultIndex = 0;
        
        // Copy elements before gap
        for (int i = 0; i < gapStart; i++) {
            result[resultIndex++] = (T) buffer[i];
        }
        
        // Copy elements after gap
        for (int i = gapEnd; i < capacity; i++) {
            result[resultIndex++] = (T) buffer[i];
        }
        
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new DynamicListIterator();
    }

    private class DynamicListIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return get(currentIndex++);
        }
    }

    public ListStatistics<T> getStatistics(Function<T, Number> numericExtractor) {
        if (isEmpty()) {
            return new ListStatistics<>(0, 0, 0, 0, 0);
        }

        double sum = 0, min = Double.MAX_VALUE, max = Double.MIN_VALUE;
        int count = 0;

        // Process elements before gap
        for (int i = 0; i < gapStart; i++) {
            @SuppressWarnings("unchecked")
            T item = (T) buffer[i];
            Number value = numericExtractor.apply(item);
            if (value != null) {
                double doubleValue = value.doubleValue();
                sum += doubleValue;
                min = Math.min(min, doubleValue);
                max = Math.max(max, doubleValue);
                count++;
            }
        }
        
        // Process elements after gap
        for (int i = gapEnd; i < capacity; i++) {
            @SuppressWarnings("unchecked")
            T item = (T) buffer[i];
            Number value = numericExtractor.apply(item);
            if (value != null) {
                double doubleValue = value.doubleValue();
                sum += doubleValue;
                min = Math.min(min, doubleValue);
                max = Math.max(max, doubleValue);
                count++;
            }
        }

        double average = count > 0 ? sum / count : 0;
        double variance = 0;

        if (count > 1) {
            // Calculate variance - process elements before gap
            for (int i = 0; i < gapStart; i++) {
                @SuppressWarnings("unchecked")
                T item = (T) buffer[i];
                Number value = numericExtractor.apply(item);
                if (value != null) {
                    double diff = value.doubleValue() - average;
                    variance += diff * diff;
                }
            }
            
            // Calculate variance - process elements after gap
            for (int i = gapEnd; i < capacity; i++) {
                @SuppressWarnings("unchecked")
                T item = (T) buffer[i];
                Number value = numericExtractor.apply(item);
                if (value != null) {
                    double diff = value.doubleValue() - average;
                    variance += diff * diff;
                }
            }
            variance /= count - 1;
        }

        return new ListStatistics<>(count, sum, average, min, max, Math.sqrt(variance));
    }

    public static class ListStatistics<T> {
        public final int count;
        public final double sum;
        public final double average;
        public final double min;
        public final double max;
        public final double standardDeviation;

        public ListStatistics(double count, double sum, double average, double min, double max) {
            this(count, sum, average, min, max, 0);
        }

        public ListStatistics(double count, double sum, double average, double min, double max, double stdDev) {
            this.count = (int) count;
            this.sum = sum;
            this.average = average;
            this.min = min;
            this.max = max;
            this.standardDeviation = stdDev;
        }
    }

    @Override
    public void quickSort(Comparator<T> comparator) {
        if (size() <= 1) return;
        
        // Create a temporary array with all elements in order
        T[] tempArray = toArray();
        
        // Sort the temporary array
        quickSortArray(tempArray, 0, tempArray.length - 1, comparator);
        
        // Clear current buffer and rebuild from sorted array
        clear();
        for (T item : tempArray) {
            add(item);
        }
    }

    @SuppressWarnings("unchecked")
    private void quickSortArray(T[] arr, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pi = partitionArray(arr, low, high, comparator);
            quickSortArray(arr, low, pi - 1, comparator);
            quickSortArray(arr, pi + 1, high, comparator);
        }
    }

    private int partitionArray(T[] arr, int low, int high, Comparator<T> comparator) {
        T pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(arr[j], pivot) <= 0) {
                i++;
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        T temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    @Override
    public DynamicList<T> clone() {
        DynamicList<T> clonedList = new DynamicList<>(this.capacity);
        
        // Copy all elements in order
        for (int i = 0; i < size(); i++) {
            clonedList.add(get(i));
        }
        
        return clonedList;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DynamicList)) {
            return false;
        }
        DynamicList<?> other = (DynamicList<?>) obj;
        if (size() != other.size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            if (!Objects.equals(get(i), other.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (int i = 0; i < size(); i++) {
            T item = get(i);
            result = 31 * result + (item == null ? 0 : item.hashCode());
        }
        return result;
    }

    // Gap buffer specific helper methods
    private int gapSize() {
        return gapEnd - gapStart;
    }

    private void moveGapTo(int index) {
        if (index == gapStart) return;
        
        if (index < gapStart) {
            // Move gap left
            int moveCount = gapStart - index;
            System.arraycopy(buffer, index, buffer, gapEnd - moveCount, moveCount);
            gapStart = index;
            gapEnd -= moveCount;
        } else {
            // Move gap right
            int moveCount = index - gapStart;
            System.arraycopy(buffer, gapEnd, buffer, gapStart, moveCount);
            gapStart += moveCount;
            gapEnd += moveCount;
        }
    }

    private void expandGap() {
        int newCapacity = nextFibonacci(capacity + 1);
        Object[] newBuffer = new Object[newCapacity];

        // Copy elements before gap
        System.arraycopy(buffer, 0, newBuffer, 0, gapStart);

        // Copy elements after gap
        int afterGapCount = capacity - gapEnd;
        System.arraycopy(buffer, gapEnd, newBuffer, newCapacity - afterGapCount, afterGapCount);

        buffer = newBuffer;
        gapEnd = newCapacity - afterGapCount;
        capacity = newCapacity;
    }
    
    private int nextFibonacci(int n) {
        int a = 1, b = 2;
        while (b < n) {
            int temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }
}