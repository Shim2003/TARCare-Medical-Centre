/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ADT;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

public class DynamicList<T> implements MyList<T> {

    private static final int BLOCK_SIZE = 128; // Block size for better memory management
    private static final int INITIAL_BLOCKS = 4;
    
    private Object[][] blocks;
    private int size;
    private int blockCount;
    private int maxBlocks;

    public DynamicList() {
        maxBlocks = INITIAL_BLOCKS;
        blocks = new Object[maxBlocks][];
        blocks[0] = new Object[BLOCK_SIZE];
        blockCount = 1;
        size = 0;
    }

    public DynamicList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        
        int neededBlocks = Math.max(1, (initialCapacity + BLOCK_SIZE - 1) / BLOCK_SIZE);
        maxBlocks = Math.max(neededBlocks, INITIAL_BLOCKS);
        blocks = new Object[maxBlocks][];
        blocks[0] = new Object[BLOCK_SIZE];
        blockCount = 1;
        size = 0;
    }

    @Override
    public void add(T item) {
        add(size, item);
    }

    @Override
    public void add(int index, T item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        if (index == size) {
            // Fast path: append to end
            appendElement(item);
        } else {
            // Insert at specific position
            insertElement(index, item);
        }
        size++;
    }

    private void appendElement(T item) {
        int blockIndex = size / BLOCK_SIZE;
        int innerIndex = size % BLOCK_SIZE;
        
        ensureBlock(blockIndex);
        blocks[blockIndex][innerIndex] = item;
    }

    private void insertElement(int index, T item) {
        int blockIndex = index / BLOCK_SIZE;
        int innerIndex = index % BLOCK_SIZE;
        
        ensureBlock(blockIndex);
        
        // If current block would overflow, split it
        if (needsBlockSplit(blockIndex)) {
            splitBlock(blockIndex);
            
            // Recalculate position after split
            if (innerIndex >= BLOCK_SIZE / 2) {
                blockIndex++;
                innerIndex -= BLOCK_SIZE / 2;
            }
        }
        
        // Shift elements within block to make space
        shiftElementsRight(blockIndex, innerIndex);
        blocks[blockIndex][innerIndex] = item;
    }

    private boolean needsBlockSplit(int blockIndex) {
        // Check if adding one more element would require shifting across blocks
        int elementsInBlock = 0;
        if (blockIndex < blockCount - 1) {
            elementsInBlock = BLOCK_SIZE;
        } else {
            elementsInBlock = size % BLOCK_SIZE;
            if (elementsInBlock == 0 && size > 0) {
                elementsInBlock = BLOCK_SIZE;
            }
        }
        
        return elementsInBlock == BLOCK_SIZE;
    }

    private void splitBlock(int blockIndex) {
        ensureBlockCapacity(blockCount + 1);
        
        // Shift all blocks after blockIndex to the right
        for (int i = blockCount; i > blockIndex + 1; i--) {
            blocks[i] = blocks[i - 1];
        }
        
        // Create new block
        Object[] newBlock = new Object[BLOCK_SIZE];
        blocks[blockIndex + 1] = newBlock;
        blockCount++;
        
        // Move half of the elements from old block to new block
        Object[] oldBlock = blocks[blockIndex];
        int half = BLOCK_SIZE / 2;
        
        System.arraycopy(oldBlock, half, newBlock, 0, half);
        
        // Clear moved elements in old block
        for (int i = half; i < BLOCK_SIZE; i++) {
            oldBlock[i] = null;
        }
    }

    private void shiftElementsRight(int blockIndex, int innerIndex) {
        // First, shift elements within the target block
        if (innerIndex < BLOCK_SIZE - 1) {
            System.arraycopy(
                blocks[blockIndex], innerIndex,
                blocks[blockIndex], innerIndex + 1,
                BLOCK_SIZE - innerIndex - 1
            );
        }
        
        // If we have a carry-over, handle it
        Object carryOver = blocks[blockIndex][BLOCK_SIZE - 1];
        blocks[blockIndex][BLOCK_SIZE - 1] = null;
        
        if (carryOver != null) {
            // Propagate the carry-over to subsequent blocks
            for (int i = blockIndex + 1; i < blockCount && carryOver != null; i++) {
                Object temp = blocks[i][BLOCK_SIZE - 1];
                System.arraycopy(
                    blocks[i], 0,
                    blocks[i], 1,
                    BLOCK_SIZE - 1
                );
                blocks[i][0] = carryOver;
                carryOver = temp;
            }
            
            // If we still have carry-over, we need a new block
            if (carryOver != null) {
                ensureBlock(blockCount);
                blocks[blockCount - 1][0] = carryOver;
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        int blockIndex = index / BLOCK_SIZE;
        int innerIndex = index % BLOCK_SIZE;
        return (T) blocks[blockIndex][innerIndex];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        int blockIndex = index / BLOCK_SIZE;
        int innerIndex = index % BLOCK_SIZE;
        
        T removed = (T) blocks[blockIndex][innerIndex];
        
        // Shift elements left within the block
        System.arraycopy(
            blocks[blockIndex], innerIndex + 1,
            blocks[blockIndex], innerIndex,
            BLOCK_SIZE - innerIndex - 1
        );
        
        // Handle elements from subsequent blocks
        shiftElementsLeft(blockIndex);
        
        size--;
        return removed;
    }

    private void shiftElementsLeft(int fromBlockIndex) {
        for (int i = fromBlockIndex; i < blockCount - 1; i++) {
            // Move first element of next block to end of current block
            blocks[i][BLOCK_SIZE - 1] = blocks[i + 1][0];
            
            // Shift all elements in next block left
            System.arraycopy(
                blocks[i + 1], 1,
                blocks[i + 1], 0,
                BLOCK_SIZE - 1
            );
            blocks[i + 1][BLOCK_SIZE - 1] = null;
        }
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
        for (int i = 0; i < size; i++) {
            T element = get(i);
            if (item == null ? element == null : item.equals(element)) {
                return i;
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
        // Clear all blocks
        for (int i = 0; i < blockCount; i++) {
            if (blocks[i] != null) {
                for (int j = 0; j < BLOCK_SIZE; j++) {
                    blocks[i][j] = null;
                }
            }
        }
        
        // Reset to initial state
        blockCount = 1;
        size = 0;
    }

    @Override
    public T getFirst() {
        return isEmpty() ? null : get(0);
    }

    @Override
    public T getLast() {
        return isEmpty() ? null : get(size - 1);
    }

    @Override
    public T findFirst(Predicate<T> predicate) {
        for (int i = 0; i < size; i++) {
            T item = get(i);
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
    public MyList<T> findAll(Predicate<T> predicate) {
        MyList<T> result = new DynamicList<>();
        for (int i = 0; i < size; i++) {
            T item = get(i);
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public void replace(int index, T newItem) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        int blockIndex = index / BLOCK_SIZE;
        int innerIndex = index % BLOCK_SIZE;
        blocks[blockIndex][innerIndex] = newItem;
    }

    @Override
    public int findIndex(Predicate<T> predicate) {
        for (int i = 0; i < size; i++) {
            T item = get(i);
            if (predicate.test(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean removeIf(Predicate<T> predicate) {
        boolean removed = false;
        for (int i = size - 1; i >= 0; i--) {
            if (predicate.test(get(i))) {
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
            result[i] = get(i);
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
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return get(currentIndex++);
        }
    }

    @Override
    public ListStatistics<T> getStatistics(Function<T, Number> numericExtractor) {
        if (isEmpty()) {
            return new ListStatistics<>(0, 0, 0, 0, 0);
        }

        double sum = 0, min = Double.MAX_VALUE, max = Double.MIN_VALUE;
        int count = 0;

        for (int i = 0; i < size; i++) {
            T item = get(i);
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
            for (int i = 0; i < size; i++) {
                T item = get(i);
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
        
        public int getCount() {
            return count;
        }

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
    public MyList<T> clone() {
        MyList<T> clonedList = new DynamicList<>(this.size);
        for (int i = 0; i < size; i++) {
            clonedList.add(get(i));
        }
        return clonedList;
    }

    @Override
    public MyList<T> filter(Predicate<T> predicate) {
        MyList<T> result = new DynamicList<>();
        for (int i = 0; i < size; i++) {
            T item = get(i);
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    // Helper methods for block management
    private void ensureBlock(int blockIndex) {
        ensureBlockCapacity(blockIndex + 1);
        
        if (blockIndex >= blockCount) {
            for (int i = blockCount; i <= blockIndex; i++) {
                blocks[i] = new Object[BLOCK_SIZE];
            }
            blockCount = blockIndex + 1;
        }
    }

    private void ensureBlockCapacity(int requiredBlocks) {
        if (requiredBlocks > maxBlocks) {
            int newMaxBlocks = Math.max(maxBlocks * 2, requiredBlocks);
            Object[][] newBlocks = new Object[newMaxBlocks][];
            System.arraycopy(blocks, 0, newBlocks, 0, blockCount);
            blocks = newBlocks;
            maxBlocks = newMaxBlocks;
        }
    }
}