package ADT;

public class Bin<T> {
    private static final int DEFAULT_CAPACITY = 25;
    private T[] binArray;
    private int numberOfEntries;
    private int capacity;

    @SuppressWarnings("unchecked")
    public Bin() {
        this.capacity = DEFAULT_CAPACITY;
        this.binArray = (T[]) new Object[capacity];
        this.numberOfEntries = 0;
    }

    @SuppressWarnings("unchecked")
    public Bin(int capacity) {
        this.capacity = capacity;
        this.binArray = (T[]) new Object[capacity];
        this.numberOfEntries = 0;
    }

    public boolean add(T item) {
        if (isFull()) {
            return false;
        }
        binArray[numberOfEntries] = item;
        numberOfEntries++;
        return true;
    }

    public boolean remove(T item) {
        for (int i = 0; i < numberOfEntries; i++) {
            if (binArray[i].equals(item)) {
                binArray[i] = binArray[numberOfEntries - 1]; // Replace with last
                binArray[numberOfEntries - 1] = null;
                numberOfEntries--;
                return true;
            }
        }
        return false;
    }

    public boolean contains(T item) {
        for (int i = 0; i < numberOfEntries; i++) {
            if (binArray[i].equals(item)) {
                return true;
            }
        }
        return false;
    }

    public int getCurrentSize() {
        return numberOfEntries;
    }

    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    public void clear() {
        for (int i = 0; i < numberOfEntries; i++) {
            binArray[i] = null;
        }
        numberOfEntries = 0;
    }

    public Object[] toArray() {
        Object[] result = new Object[numberOfEntries];
        for (int i = 0; i < numberOfEntries; i++) {
            result[i] = binArray[i];
        }
        return result;
    }

    public int getFrequencyOf(T item) {
        int count = 0;
        for (int i = 0; i < numberOfEntries; i++) {
            if (binArray[i].equals(item)) {
                count++;
            }
        }
        return count;
    }

    public boolean isFull() {
        return numberOfEntries >= capacity;
    }

    public void displayAll() {
        for (int i = 0; i < numberOfEntries; i++) {
            System.out.println(binArray[i]);
        }
    }
    
    // Get element at specific index
    public T get(int index) {
        if (index >= 0 && index < numberOfEntries) {
            return binArray[index];
        }
        return null;
    }
    
    // Set element at specific index
    public boolean set(int index, T item) {
        if (index >= 0 && index < numberOfEntries) {
            binArray[index] = item;
            return true;
        }
        return false;
    }
    
    // Remove element at specific index
    public T removeAt(int index) {
        if (index >= 0 && index < numberOfEntries) {
            T removedItem = binArray[index];
            binArray[index] = binArray[numberOfEntries - 1];
            binArray[numberOfEntries - 1] = null;
            numberOfEntries--;
            return removedItem;
        }
        return null;
    }
    
    // Get unique elements (no duplicates)
    public Bin<T> getUniqueElements() {
        Bin<T> uniqueBin = new Bin<>(capacity);
        for (int i = 0; i < numberOfEntries; i++) {
            if (!uniqueBin.contains(binArray[i])) {
                uniqueBin.add(binArray[i]);
            }
        }
        return uniqueBin;
    }
    
    // Check if bin contains all elements from another bin
    public boolean containsAll(Bin<T> otherBin) {
        for (int i = 0; i < otherBin.getCurrentSize(); i++) {
            T item = otherBin.get(i);
            if (getFrequencyOf(item) < otherBin.getFrequencyOf(item)) {
                return false;
            }
        }
        return true;
    }
    
    // Union of two bins
    public Bin<T> union(Bin<T> otherBin) {
        Bin<T> unionBin = new Bin<>(this.capacity + otherBin.capacity);
        
        // Add all elements from this bin
        for (int i = 0; i < numberOfEntries; i++) {
            unionBin.add(binArray[i]);
        }
        
        // Add all elements from other bin
        for (int i = 0; i < otherBin.getCurrentSize(); i++) {
            unionBin.add(otherBin.get(i));
        }
        
        return unionBin;
    }
    
    // Intersection of two bins
    public Bin<T> intersection(Bin<T> otherBin) {
        Bin<T> intersectionBin = new Bin<>(Math.min(this.capacity, otherBin.capacity));
        
        for (int i = 0; i < numberOfEntries; i++) {
            T item = binArray[i];
            int thisFreq = getFrequencyOf(item);
            int otherFreq = otherBin.getFrequencyOf(item);
            int minFreq = Math.min(thisFreq, otherFreq);
            
            // Add minimum frequency times
            for (int j = 0; j < minFreq; j++) {
                if (!intersectionBin.contains(item) || intersectionBin.getFrequencyOf(item) < minFreq) {
                    intersectionBin.add(item);
                }
            }
        }
        
        return intersectionBin;
    }
    
    // Difference: elements in this bin but not in other bin
    public Bin<T> difference(Bin<T> otherBin) {
        Bin<T> differenceBin = new Bin<>(this.capacity);
        
        for (int i = 0; i < numberOfEntries; i++) {
            T item = binArray[i];
            int thisFreq = getFrequencyOf(item);
            int otherFreq = otherBin.getFrequencyOf(item);
            int diffFreq = thisFreq - otherFreq;
            
            if (diffFreq > 0) {
                for (int j = 0; j < diffFreq; j++) {
                    differenceBin.add(item);
                }
            }
        }
        
        return differenceBin;
    }
    
    // Check if two bins are equal
    public boolean equals(Bin<T> otherBin) {
        if (this.numberOfEntries != otherBin.getCurrentSize()) {
            return false;
        }
        
        for (int i = 0; i < numberOfEntries; i++) {
            T item = binArray[i];
            if (getFrequencyOf(item) != otherBin.getFrequencyOf(item)) {
                return false;
            }
        }
        
        return true;
    }
    
    // Get random element
    public T getRandom() {
        if (isEmpty()) {
            return null;
        }
        int randomIndex = (int) (Math.random() * numberOfEntries);
        return binArray[randomIndex];
    }
    
    // Shuffle the bin
    public void shuffle() {
        for (int i = numberOfEntries - 1; i > 0; i--) {
            int randomIndex = (int) (Math.random() * (i + 1));
            T temp = binArray[i];
            binArray[i] = binArray[randomIndex];
            binArray[randomIndex] = temp;
        }
    }
    
    // Get capacity
    public int getCapacity() {
        return capacity;
    }
    
    // Check if bin is nearly full (80% capacity)
    public boolean isNearlyFull() {
        return numberOfEntries >= (capacity * 0.8);
    }
    
    // Get load factor (current size / capacity)
    public double getLoadFactor() {
        return (double) numberOfEntries / capacity;
    }
    
    // Convert to string representation
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bin[");
        for (int i = 0; i < numberOfEntries; i++) {
            sb.append(binArray[i]);
            if (i < numberOfEntries - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
} 