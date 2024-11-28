package org.example.game2048;

public class Stack {
    Node top ;

    Stack (){
        this.top = null ;
    }
    public void push (Node X){
        if(top == null){
            top = X;
        }
        else {
            X.next = top;
            top = X;
        }

    }

    public Node pop (){
        if(top == null){
            return null;
        }
        else {
            Node X = top;
            top = top.next;
            return X;
        }
    }
}
