package org.example.game2048;


public class Stack<T> {
    public Node<T> top;

    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    public Stack() {
        top = null;
    }

    // Push operation
    public void push(T data) {
        Node<T> node = new Node<>(data);
        node.next = top;
        top = node;
    }

    // Pop operation
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T data = top.data;
        top = top.next;
        return data;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return top.data;
    }
    public boolean isEmpty() {
        return top == null;
    }
}

