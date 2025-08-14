package ADT;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.Objects;

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
    @Override
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

    // Statistics operations: calculate count, sum, average, min, max, and standard deviation
    // Purpose: To provide statistical analysis of the list items based on a numeric extractor function
    public ListStatistics<T> getStatistics(Function<T, Number> numericExtractor) {
        if (isEmpty()) {
            return new ListStatistics<>(0, 0, 0, 0, 0);
        }

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

    //sorting methods based on quicksort algorithm
    public void quickSort(Comparator<T> comparator) {
        quickSort(0, size - 1, comparator);
    }

    private void quickSort(int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pi = partition(low, high, comparator);
            quickSort(low, pi - 1, comparator);
            quickSort(pi + 1, high, comparator);
        }
    }

    @SuppressWarnings("unchecked")
    private int partition(int low, int high, Comparator<T> comparator) {
        T pivot = data[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(data[j], pivot) <= 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    private void swap(int i, int j) {
        T temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /*apply the sorting method to the doctor module:

    Sort by doctor name
    doctors.quickSort(Comparator.comparing(d -> ((Doctor) d).getName()));

    Sort patients by age
    patients.quickSort(Comparator.comparing(p -> ((Patient) p).getAge()));

    Sort treatments by date
    treatments.quickSort(Comparator.comparing(t -> ((MedicalTreatment) t).getTreatmentDate()));
    
    Sort consultations by date
    consultations.quickSort(Comparator.comparing(c -> ((Consultation) c).getConsultationDate()));

    Sort the medicine list by name
    medicines.quickSort(Comparator.comparing(m -> ((Medicine) m).getName()));

     */
    // clone method that create a deep copy of the list, save a snapshot of the current state before any modifications
    @SuppressWarnings("unchecked")
    @Override
    public DynamicList<T> clone() {
        DynamicList<T> clonedList = new DynamicList<>(this.data.length);

        // Copy all elements
        for (int i = 0; i < this.size; i++) {
            clonedList.add(this.data[i]);
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
        if (size != other.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!Objects.equals(data[i], other.data[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (int i = 0; i < size; i++) {
            result = 31 * result + (data[i] == null ? 0 : data[i].hashCode());
        }
        return result;
    }
}
