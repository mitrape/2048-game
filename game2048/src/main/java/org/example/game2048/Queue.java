package org.example.game2048;

public class Queue {
    Node first ;
    Node last ;
    Queue(){
        this.first = null;
        this.last = null;
    }

    public void AddQ(Node X){
        X.next = this.first;
        first = X;
    }

    public void RemoveQ(){
        Node temp = first;
        while (temp.next.next != null){
            temp = temp.next;
        }
        temp.next = null;
        last = temp;
    }
}
