package org.example.game2048;

public class Stack {
    Node top ;

    Stack (){
        this.top = null ;
    }
    public void push (Node X){
        X.next = top;
        top = X;
    }

    public Node pop (){
        Node X = top;
        top = top.next;
        return X;
    }

}
