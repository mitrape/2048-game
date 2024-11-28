package org.example.game2048;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

public class HelloController implements Initializable {
    @FXML
    public Label l00, l01, l02,l03,l10,l11,l12,l13,l20,l21,l22,l23,l30,l31,l32,l33;
    @FXML
    public Label score;
    Node head;
    Stack Undo = new Stack();
    Stack Redo = new Stack();
    int countUndo = 0;
    int countRedo = 0;


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

    public void moveRight() {
        int [][] boardFirst = new int[4][4];
        int [][] boardLast = new int[4][4];
        // تمام گره هارو به صورت سطری جدا کنیم
        for (int k = 0; k < 4; k++) {
            Node original = head;
            Node previous = null;
            Node [] rowNodes = new Node[4];
            int indexRowNodes = 0;
            while (original != null) {
                if (original.row == k) {
                    rowNodes[indexRowNodes] = original;
                    indexRowNodes++;
                    if (previous == null) {
                        head = original.next;
                    } else {
                        previous.next = original.next;
                    }
                } else {
                    previous = original;
                }
                original = original.next;
            }

            //گره های جدا شده به صورت سطری را به ترتیب در جای مناسب در ارایه قرار دهیم
            Node[] sortedRowNodes = new Node[4];
            for (int i = 0; i < indexRowNodes; i++) {
                sortedRowNodes[rowNodes[i].col] = rowNodes[i];
            }
            //تشکیل برد اولیه
            for (int i = 0; i < 4; i++) {
                if(sortedRowNodes[i] != null) {
                    boardFirst[k][i] = sortedRowNodes[i].value;
                }
            }
            //عملیات شیفت دادن برای اینکه هیچ خونه خالی بین اعداد در سمت راست باقی نمونه
            for (int i = 2; i >= 0; i--) {
                if (sortedRowNodes[i] != null) {
                    for (int j = 3; j > i; j--) {
                        if (sortedRowNodes[j] == null) {
                            sortedRowNodes[j] = sortedRowNodes[i];
                            sortedRowNodes[i] = null;
                            break;
                        }
                    }
                }
            }
            //اگر دوتا هم عدد بودن باید ضرب بشن و خونه اولیه باید نال بشه و بقیه شیفت بخورن
            for (int i = 2; i >= 0; i--) {
                if (sortedRowNodes[i] != null) {
                    if (sortedRowNodes[i + 1].value == sortedRowNodes[i].value) {
                        sortedRowNodes[i + 1].value *= 2;
                        setScore(sortedRowNodes[i+1].value);
                        sortedRowNodes[i] = null;
                        for (int j = i - 1; j >= 0; j--) {
                            sortedRowNodes[j + 1] = sortedRowNodes[j];
                            sortedRowNodes[j] = null;
                        }
                    }
                } else {
                    break;
                }
            }
            //تشکیل برد ثانویه
            for (int i = 0; i < 4; i++) {
                if(sortedRowNodes[i] != null) {
                    boardLast[k][i] = sortedRowNodes[i].value;
                }
            }
            //اضافه کردن خانه ها به اول لینکدلیست
            for (int i = 0; i < 4; i++) {
                if(sortedRowNodes[i] != null){
                    Node newNode = new Node(sortedRowNodes[i].value, k, i);
                    newNode.next = head;
                    head = newNode;
                }
            }
        }
        if(isSuccessful(boardFirst , boardLast)){
            addNewTail();
        }
    }

    public void moveLeft() {
        int[][] boardFirst = new int[4][4];
        int[][] boardLast = new int[4][4];

        for (int k = 0; k < 4; k++) {
            Node original = head;
            Node previous = null;
            Node[] rowNodes = new Node[4];
            int indexRowNodes = 0;

            while (original != null) {
                if (original.row == k) {
                    rowNodes[indexRowNodes] = original;
                    indexRowNodes++;
                    if (previous == null) {
                        head = original.next;
                    } else {
                        previous.next = original.next;
                    }
                } else {
                    previous = original;
                }
                original = original.next;
            }

            Node[] sortedRowNodes = new Node[4];
            for (int i = 0; i < indexRowNodes; i++) {
                sortedRowNodes[rowNodes[i].col] = rowNodes[i];
            }

            for (int i = 0; i < 4; i++) {
                if (sortedRowNodes[i] != null) {
                    boardFirst[k][i] = sortedRowNodes[i].value;
                }
            }

            for (int i = 1; i < 4; i++) {
                if (sortedRowNodes[i] != null) {
                    for (int j = 0; j < i; j++) {
                        if (sortedRowNodes[j] == null) {
                            sortedRowNodes[j] = sortedRowNodes[i];
                            sortedRowNodes[i] = null;
                            break;
                        }
                    }
                }
            }

            for (int i = 1; i < 4; i++) {
                if (sortedRowNodes[i] != null) {
                    if (sortedRowNodes[i - 1].value == sortedRowNodes[i].value) {
                        sortedRowNodes[i - 1].value *= 2;
                        setScore(sortedRowNodes[i - 1].value);
                        sortedRowNodes[i] = null;
                        for (int j = i + 1; j < 4; j++) {
                            sortedRowNodes[j - 1] = sortedRowNodes[j];
                            sortedRowNodes[j] = null;
                        }
                    }
                } else {
                    break;
                }
            }

            for (int i = 0; i < 4; i++) {
                if (sortedRowNodes[i] != null) {
                    boardLast[k][i] = sortedRowNodes[i].value;
                }
            }

            for (int i = 3; i >= 0; i--) {
                if (sortedRowNodes[i] != null) {
                    Node newNode = new Node(sortedRowNodes[i].value, k, i);
                    newNode.next = head;
                    head = newNode;
                }
            }
        }

        if (isSuccessful(boardFirst, boardLast)) {
            addNewTail();
        }
    }

    public void moveUp() {
        int[][] boardFirst = new int[4][4];
        int[][] boardLast = new int[4][4];

        for (int k = 0; k < 4; k++) {
            Node original = head;
            Node previous = null;
            Node[] colNodes = new Node[4];
            int indexColNodes = 0;

            while (original != null) {
                if (original.col == k) {
                    colNodes[indexColNodes] = original;
                    indexColNodes++;
                    if (previous == null) {
                        head = original.next;
                    } else {
                        previous.next = original.next;
                    }
                } else {
                    previous = original;
                }
                original = original.next;
            }

            Node[] sortedColNodes = new Node[4];
            for (int i = 0; i < indexColNodes; i++) {
                sortedColNodes[colNodes[i].row] = colNodes[i];
            }

            for (int i = 0; i < 4; i++) {
                if (sortedColNodes[i] != null) {
                    boardFirst[i][k] = sortedColNodes[i].value;
                }
            }

            for (int i = 1; i < 4; i++) {
                if (sortedColNodes[i] != null) {
                    for (int j = 0; j < i; j++) {
                        if (sortedColNodes[j] == null) {
                            sortedColNodes[j] = sortedColNodes[i];
                            sortedColNodes[i] = null;
                            break;
                        }
                    }
                }
            }

            for (int i = 1; i < 4; i++) {
                if (sortedColNodes[i] != null) {
                    if (sortedColNodes[i - 1].value == sortedColNodes[i].value) {
                        sortedColNodes[i - 1].value *= 2;
                        setScore(sortedColNodes[i - 1].value);
                        sortedColNodes[i] = null;
                        for (int j = i + 1; j < 4; j++) {
                            sortedColNodes[j - 1] = sortedColNodes[j];
                            sortedColNodes[j] = null;
                        }
                    }
                } else {
                    break;
                }
            }

            for (int i = 0; i < 4; i++) {
                if (sortedColNodes[i] != null) {
                    boardLast[i][k] = sortedColNodes[i].value;
                }
            }

            for (int i = 3; i >= 0; i--) {
                if (sortedColNodes[i] != null) {
                    Node newNode = new Node(sortedColNodes[i].value, i, k);
                    newNode.next = head;
                    head = newNode;
                }
            }
        }

        if (isSuccessful(boardFirst, boardLast)) {
            addNewTail();
        }
    }

    public void moveDown() {
        int[][] boardFirst = new int[4][4];
        int[][] boardLast = new int[4][4];

        for (int k = 0; k < 4; k++) {
            Node original = head;
            Node previous = null;
            Node[] colNodes = new Node[4];
            int indexColNodes = 0;

            while (original != null) {
                if (original.col == k) {
                    colNodes[indexColNodes] = original;
                    indexColNodes++;
                    if (previous == null) {
                        head = original.next;
                    } else {
                        previous.next = original.next;
                    }
                } else {
                    previous = original;
                }
                original = original.next;
            }

            Node[] sortedColNodes = new Node[4];
            for (int i = 0; i < indexColNodes; i++) {
                sortedColNodes[colNodes[i].row] = colNodes[i];
            }

            for (int i = 0; i < 4; i++) {
                if (sortedColNodes[i] != null) {
                    boardFirst[i][k] = sortedColNodes[i].value;
                }
            }

            for (int i = 2; i >= 0; i--) {
                if (sortedColNodes[i] != null) {
                    for (int j = 3; j > i; j--) {
                        if (sortedColNodes[j] == null) {
                            sortedColNodes[j] = sortedColNodes[i];
                            sortedColNodes[i] = null;
                            break;
                        }
                    }
                }
            }

            for (int i = 2; i >= 0; i--) {
                if (sortedColNodes[i] != null) {
                    if (sortedColNodes[i + 1].value == sortedColNodes[i].value) {
                        sortedColNodes[i + 1].value *= 2;
                        setScore(sortedColNodes[i + 1].value);
                        sortedColNodes[i] = null;
                        for (int j = i - 1; j >= 0; j--) {
                            sortedColNodes[j + 1] = sortedColNodes[j];
                            sortedColNodes[j] = null;
                        }
                    }
                } else {
                    break;
                }
            }

            for (int i = 0; i < 4; i++) {
                if (sortedColNodes[i] != null) {
                    boardLast[i][k] = sortedColNodes[i].value;
                }
            }

            for (int i = 3; i >= 0; i--) {
                if (sortedColNodes[i] != null) {
                    Node newNode = new Node(sortedColNodes[i].value, i, k);
                    newNode.next = head;
                    head = newNode;
                }
            }
        }

        if (isSuccessful(boardFirst, boardLast)) {
            addNewTail();
        }
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



    public void ClickOnUndo (ActionEvent e) throws Exception {
        if(countUndo == 5){
            JOptionPane.showMessageDialog(null, "you can't press undo more than 5 times!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            Redo.push(head);
            head = Undo.pop();
            Display();
        }
    }
    public void ClickOnRedo (ActionEvent e) throws Exception {

    }

    public boolean isSuccessful (int [][] first , int [][] last){
        boolean sw = false ;
        for (int i = 0; i < 4 && !sw ; i++) {
            for (int j = 0; j < 4; j++) {
                if(first[i][j] != last[i][j]){
                    sw = true;
                    break;
                }
            }
        }
        return sw ;
    }

}