package ADT;

import java.util.Iterator;
import java.util.function.Function;
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

    //Purpose: This allows DynamicList to be iterable, means can loop through it with a for-each loop
    public Iterator<T> iterator() {
        return new DynamicListIterator();
    }

    private class DynamicListIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return data[currentIndex++];
        }
    }

    //time-based operations: add an item after a delay
    //Purpose: To allow scheduling of item addition to the list after a specified delay
    // This can be useful for tasks that need to be deferred or executed later
    public void scheduleAdd(T item, long delayMillis) {
        new Thread(() -> {
            try {
                Thread.sleep(delayMillis);
                add(item);
                System.out.println("Scheduled item added: " + item);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    // Grouping operations: group items by a primary and secondary key
    //Purpose: To group items based on two criteria, allowing for hierarchical categorization
    public GroupedData<T> groupBy(Function<T, String> primaryGrouper, 
                                 Function<T, String> secondaryGrouper) {
        GroupedData<T> result = new GroupedData<>();
        
        for (int i = 0; i < size; i++) {
            String primaryKey = primaryGrouper.apply(data[i]);
            String secondaryKey = secondaryGrouper.apply(data[i]);
            result.addItem(primaryKey, secondaryKey, data[i]);
        }
        
        return result;
    }

    public static class GroupedData<T> {
        private final DynamicList<Group<T>> groups = new DynamicList<>();
        
        public void addItem(String primaryKey, String secondaryKey, T item) {
            Group<T> group = findGroup(primaryKey);
            if (group == null) {
                group = new Group<>(primaryKey);
                groups.add(group);
            }
            group.addItem(secondaryKey, item);
        }
        
        private Group<T> findGroup(String key) {
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).key.equals(key)) {
                    return groups.get(i);
                }
            }
            return null;
        }
        
        public DynamicList<Group<T>> getGroups() {
            return groups;
        }
    }
    
    public static class Group<T> {
        public final String key;
        private final DynamicList<SubGroup<T>> subGroups = new DynamicList<>();
        
        public Group(String key) {
            this.key = key;
        }
        
        public void addItem(String subKey, T item) {
            SubGroup<T> subGroup = findSubGroup(subKey);
            if (subGroup == null) {
                subGroup = new SubGroup<>(subKey);
                subGroups.add(subGroup);
            }
            subGroup.items.add(item);
        }
        
        private SubGroup<T> findSubGroup(String key) {
            for (int i = 0; i < subGroups.size(); i++) {
                if (subGroups.get(i).key.equals(key)) {
                    return subGroups.get(i);
                }
            }
            return null;
        }
        
        public DynamicList<SubGroup<T>> getSubGroups() {
            return subGroups;
        }
    }
    
    public static class SubGroup<T> {
        public final String key;
        public final DynamicList<T> items = new DynamicList<>();
        
        public SubGroup(String key) {
            this.key = key;
        }
    }

    // Statistics operations: calculate count, sum, average, min, max, and standard deviation
    //Purpose: To provide statistical analysis of the list items based on a numeric extractor function
    public ListStatistics<T> getStatistics(Function<T, Number> numericExtractor) {
        if (isEmpty()) return new ListStatistics<>(0, 0, 0, 0, 0);
        
        double sum = 0, min = Double.MAX_VALUE, max = Double.MIN_VALUE;
        int count = 0;
        
        for (int i = 0; i < size; i++) {
            Number value = numericExtractor.apply(data[i]);
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
            for (int i = 0; i < size; i++) {
                Number value = numericExtractor.apply(data[i]);
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
}