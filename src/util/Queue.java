package util;

import java.util.ArrayList;
import java.util.List;

public class Queue<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    public Queue() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }

    public void enqueue(T newEntry) {
        Node<T> newNode = new Node<T>(newEntry);

        if (isEmpty()) {
            firstNode = newNode;
            lastNode = newNode;
        } else {
            lastNode.setNext(newNode);
            lastNode = lastNode.getNext();
        }

        size++;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return firstNode.getData();
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T data = firstNode.getData();
        
        firstNode = firstNode.getNext();

        size--;
        
        return data;
    }

    public List<T> toList() {
        if (isEmpty()) {
            return null;
        }

        ArrayList<T> list = new ArrayList<T>();
        Node<T> currentNode = firstNode;

        while (currentNode != null) {
            list.add(currentNode.getData());
            currentNode = currentNode.getNext();
        }

        return list;
    }
}
