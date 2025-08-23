/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ADT;

import java.util.function.Predicate;

/**
 *
 * @author User
 */
public class SmartList<T> implements ListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;
    private int skipDistance = 5;

    public SmartList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T data) {
        Node<T> newNode = new Node<>(data, tail, null);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        size++;
        updateSkips();
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node<T> current = head;

        int skipJumps = index / skipDistance;
        int steps = index % skipDistance;

        for (int i = 0; i < skipJumps && current.skipNext != null; i++) {
            current = current.skipNext;
        }

        for (int i = 0; i < steps && current.next != null; i++) {
            current = current.next;
        }

        return current.data;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            return;
        }
        Node<T> current = head;
        int currentIndex = 0;

        // Find node using skip jumps
        int skipJumps = index / skipDistance;
        int steps = index % skipDistance;

        for (int i = 0; i < skipJumps && current.skipNext != null; i++) {
            current = current.skipNext;
        }
        for (int i = 0; i < steps && current.next != null; i++) {
            current = current.next;
        }

        // Remove node
        if (current.previous != null) {
            current.previous.next = current.next;
        } else {
            head = current.next;
        }

        if (current.next != null) {
            current.next.previous = current.previous;
        } else {
            tail = current.previous;
        }

        size--;
        updateSkips();
    }

    @Override
    public boolean anyMatch(Predicate<T> predicate) {
        Node<T> current = head;
        while (current != null) {
            if (predicate.test(current.data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean contains(T item) {
        Node<T> current = head;
        while (current != null) {
            if ((item == null && current.data == null) || (item != null && item.equals(current.data))) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ListInterface<T> clone() {
        ListInterface<T> clonedList = new SmartList<>();
        Node<T> current = head;
        while (current != null) {
            clonedList.add(current.data);
            current = current.next;
        }
        return clonedList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public T getFirst() {
        return isEmpty() ? null : head.data;
    }

    public T getLast() {
        return isEmpty() ? null : tail.data;
    }

    private void updateSkips() {
        Node<T> current = head;
        int count = 0;
        Node<T> lastSkip = null;

        while (current != null) {
            if (count % skipDistance == 0) {
                if (lastSkip != null) {
                    lastSkip.skipNext = current;
                }
                lastSkip = current;
            }
            current = current.next;
            count++;
        }
        if (lastSkip != null) {
            lastSkip.skipNext = null;
        }
    }

    public class Node<T> {

        T data;
        Node<T> previous;
        Node<T> next;
        Node<T> skipNext;

        Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.previous = prev;
            this.next = next;
            this.skipNext = null;
        }
    }

}
