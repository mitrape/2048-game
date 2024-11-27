package org.example.game2048;

class Node {
    int value;
    int row, col;
    Node next;

    Node(int value, int row, int col) {
        this.value = value;
        this.row = row;
        this.col = col;
        this.next = null;
    }
}

