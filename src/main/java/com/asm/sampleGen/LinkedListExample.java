package com.asm.sampleGen;


public class LinkedListExample<T> {
    private Node<T> head;

    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public void add(T data) {
        if (data == null) {
            throw new NullPointerException("Data cannot be null");
        }
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void reverse() throws EmptyListException {
        if (head == null) {
            throw new EmptyListException("Cannot reverse empty list");
        }
        head = reverseRecursive(head, null);
    }

    private Node<T> reverseRecursive(Node<T> current, Node<T> prev) {
        if (current.next == null) {
            current.next = prev;
            return current;
        }
        Node<T> nextNode = current.next;
        current.next = prev;
        return reverseRecursive(nextNode, current);
    }

    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Negative index: " + index);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            if (current == null) {
                throw new IndexOutOfBoundsException("Index: " + index);
            }
            current = current.next;
        }
        return current != null ? current.data : null;
    }

    public static void main(String[] args) {
        try {
            LinkedListExample<String> list = new LinkedListExample<>();
            list.add("A");
            list.add("B");
            list.add("C");
            
            list.reverse();
            System.out.println(list.get(0));
            
            // Force exception
            list.get(10);
        } catch (EmptyListException | IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class EmptyListException extends Exception {
    public EmptyListException(String message) {
        super(message);
    }
}