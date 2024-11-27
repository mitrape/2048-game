package org.example.game2048;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public Label l00;
    @FXML
    public Label l01;
    @FXML
    public Label l02;
    @FXML
    public Label l03;
    @FXML
    public Label l10;
    @FXML
    public Label l11;
    @FXML
    public Label l12;
    @FXML
    public Label l13;
    @FXML
    public Label l20;
    @FXML
    public Label l21;
    @FXML
    public Label l22;
    @FXML
    public Label l23;
    @FXML
    public Label l30;
    @FXML
    public Label l31;
    @FXML
    public Label l32;
    @FXML
    public Label l33;

    @FXML
    public Label score;


    Node head;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        head = null;
        score.setText("0");
        setInitialValues();
        Display();
    }

    public void setScore(int X){
        int lastScore = Integer.parseInt(score.getText());
        score.setText(String.valueOf(lastScore+X));
    }
    public void moveUp() {
        for (int col = 0; col < 4; col++) {
            Node[] columnNodes = new Node[4];
            int index = 0;

            // Collect nodes from the current column
            Node current = head;
            Node previous = null;
            while (current != null) {
                if (current.col == col) {
                    columnNodes[index++] = current;
                    if (previous == null) {
                        head = current.next;
                    } else {
                        previous.next = current.next;
                    }
                } else {
                    previous = current;
                }
                current = current.next;
            }

            // Move and combine nodes
            int targetIndex = 0;
            for (int i = 0; i < index; i++) {
                if (columnNodes[i] != null) {
                    if (targetIndex > 0 && columnNodes[targetIndex - 1].value == columnNodes[i].value) {
                        columnNodes[targetIndex - 1].value *= 2;
                        setScore(columnNodes[targetIndex - 1].value);
                        columnNodes[i] = null; // Remove the combined node
                    } else {
                        columnNodes[targetIndex++] = columnNodes[i];
                    }
                }
            }

            // Update positions and reattach the nodes
            Node lastNode = null;
            for (int i = 0; i < targetIndex; i++) {
                columnNodes[i].row = i;
                columnNodes[i].next = null;
                if (lastNode == null) {
                    if (head == null) {
                        head = columnNodes[i];
                    } else {
                        Node temp = head;
                        while (temp.next != null) {
                            temp = temp.next;
                        }
                        temp.next = columnNodes[i];
                    }
                } else {
                    lastNode.next = columnNodes[i];
                }
                lastNode = columnNodes[i];
            }
            if (targetIndex > 0) {
                lastNode.next = null;
            }
        }
        addNewTail();
    }
    public void moveDown() {
        for (int col = 0; col < 4; col++) {
            Node[] columnNodes = new Node[4];
            int index = 0;

            // Collect nodes from the current column
            Node current = head;
            Node previous = null;
            while (current != null) {
                if (current.col == col) {
                    columnNodes[index++] = current;
                    if (previous == null) {
                        head = current.next;
                    } else {
                        previous.next = current.next;
                    }
                } else {
                    previous = current;
                }
                current = current.next;
            }

            // Move and combine nodes
            int targetIndex = 3;
            for (int i = index - 1; i >= 0; i--) {
                if (columnNodes[i] != null) {
                    if (targetIndex < 3 && columnNodes[targetIndex + 1].value == columnNodes[i].value) {
                        columnNodes[targetIndex + 1].value *= 2;
                        setScore(columnNodes[targetIndex + 1].value);
                        columnNodes[i] = null;
                    } else {
                        columnNodes[targetIndex--] = columnNodes[i];
                    }
                }
            }

            // Update positions and reattach the nodes
            Node lastNode = null;
            for (int i = 3; i > targetIndex; i--) {
                columnNodes[i].row = i;
                columnNodes[i].next = null;
                if (lastNode == null) {
                    if (head == null) {
                        head = columnNodes[i];
                    } else {
                        Node temp = head;
                        while (temp.next != null) {
                            temp = temp.next;
                        }
                        temp.next = columnNodes[i];
                    }
                } else {
                    lastNode.next = columnNodes[i];
                }
                lastNode = columnNodes[i];
            }
            if (targetIndex < 3) {
                lastNode.next = null;
            }
        }
        addNewTail();
    }

    public void moveRight() {
        for (int row = 0; row < 4; row++) {
            Node[] rowNodes = new Node[4];
            int index = 0;

            // Collect nodes from the current row
            Node current = head;
            Node previous = null;
            while (current != null) {
                if (current.row == row) {
                    rowNodes[index++] = current;
                    if (previous == null) {
                        head = current.next;
                    } else {
                        previous.next = current.next;
                    }
                } else {
                    previous = current;
                }
                current = current.next;
            }

            // Move and combine nodes
            int targetIndex = 3;
            for (int i = index - 1; i >= 0; i--) {
                if (rowNodes[i] != null) {
                    if (targetIndex < 3 && rowNodes[targetIndex + 1].value == rowNodes[i].value) {
                        rowNodes[targetIndex + 1].value *= 2;
                        setScore(rowNodes[targetIndex + 1].value);
                        rowNodes[i] = null; // Remove the combined node
                    } else {
                        rowNodes[targetIndex--] = rowNodes[i];
                    }
                }
            }

            // Update positions and reattach the nodes
            Node lastNode = null;
            for (int i = 3; i > targetIndex; i--) {
                rowNodes[i].col = i;
                rowNodes[i].next = null;
                if (lastNode == null) {
                    if (head == null) {
                        head = rowNodes[i];
                    } else {
                        Node temp = head;
                        while (temp.next != null) {
                            temp = temp.next;
                        }
                        temp.next = rowNodes[i];
                    }
                } else {
                    lastNode.next = rowNodes[i];
                }
                lastNode = rowNodes[i];
            }
            if (targetIndex < 3) {
                lastNode.next = null;
            }
        }
        addNewTail();
    }

    public void moveLeft() {
        for (int row = 0; row < 4; row++) {
            Node[] rowNodes = new Node[4];
            int index = 0;

            // Collect nodes from the current row
            Node current = head;
            Node previous = null;
            while (current != null) {
                if (current.row == row) {
                    rowNodes[index++] = current;
                    if (previous == null) {
                        head = current.next;
                    } else {
                        previous.next = current.next;
                    }
                } else {
                    previous = current;
                }
                current = current.next;
            }

            // Move and combine nodes
            int targetIndex = 0;
            for (int i = 0; i < index; i++) {
                if (rowNodes[i] != null) {
                    if (targetIndex > 0 && rowNodes[targetIndex - 1].value == rowNodes[i].value) {
                        rowNodes[targetIndex - 1].value *= 2;
                        setScore(rowNodes[targetIndex - 1].value);
                        rowNodes[i] = null; // Remove the combined node
                    } else {
                        rowNodes[targetIndex++] = rowNodes[i];
                    }
                }
            }

            // Update positions and reattach the nodes
            Node lastNode = null;
            for (int i = 0; i < targetIndex; i++) {
                rowNodes[i].col = i;
                rowNodes[i].next = null;
                if (lastNode == null) {
                    if (head == null) {
                        head = rowNodes[i];
                    } else {
                        Node temp = head;
                        while (temp.next != null) {
                            temp = temp.next;
                        }
                        temp.next = rowNodes[i];
                    }
                } else {
                    lastNode.next = rowNodes[i];
                }
                lastNode = rowNodes[i];
            }
            if (targetIndex > 0) {
                lastNode.next = null;
            }
        }
        addNewTail();
    }

    public void addNewTail(){
        Random random = new Random();
        int value = (random.nextInt(10)+1) <= 7 ? 2 : 4;
        boolean find = false;
        int row = 0,column = 0 ;
        while (!find){
            row = random.nextInt(4);
            column = random.nextInt(4);
            Node current = head;
            while (current != null){
                if(current.row == row && current.col== column){
                    break;
                }
                current = current.next;
            }
            if(current == null){
                find = true;
            }
        }
        Node temp = head;
        while (temp.next != null){
            temp = temp.next;
        }
        Node newTail = new Node(value,row,column);
        newTail.next = null;
        temp.next = newTail;
        Display();
    }

    public void setInitialValues() {
        Random random = new Random();

        int value1 = random.nextBoolean() ? 2 : 4;
        int value2 = random.nextBoolean() ? 2 : 4;

        int row1 = random.nextInt(4);
        int col1 = random.nextInt(4);
        head = new Node(value1, row1, col1);
        head.next = null;

        boolean found = false;
        int row2 = 0, col2 = 0;
        while (!found) {
            row2 = random.nextInt(4);
            col2 = random.nextInt(4);
            Node current = head;
            while (current != null) {
                if (current.row == row2 && current.col == col2) {
                    break;
                }
                current = current.next;
            }
            if (current == null) {
                found = true;
            }
        }
        Node newNode = new Node(value2, row2, col2);
        newNode.next = null;
        head.next = newNode;
    }



    public Label getLabel(int row, int col) {
        switch (row) {
            case 0:
                switch (col) {
                    case 0:
                        return l00;
                    case 1:
                        return l01;
                    case 2:
                        return l02;
                    case 3:
                        return l03;
                }
            case 1:
                switch (col) {
                    case 0:
                        return l10;
                    case 1:
                        return l11;
                    case 2:
                        return l12;
                    case 3:
                        return l13;
                }
            case 2:
                switch (col) {
                    case 0:
                        return l20;
                    case 1:
                        return l21;
                    case 2:
                        return l22;
                    case 3:
                        return l23;
                }
            case 3:
                switch (col) {
                    case 0:
                        return l30;
                    case 1:
                        return l31;
                    case 2:
                        return l32;
                    case 3:
                        return l33;
                }
        }
        return null;
    }

    public void Display (){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                getLabel(i,j).setText(null);
            }
        }
        Node current = head;
        while (current != null){
            getLabel(current.row,current.col).setText(String.valueOf(current.value));
            current = current.next;
        }
    }


}