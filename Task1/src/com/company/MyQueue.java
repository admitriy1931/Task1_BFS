package com.company;

public class MyQueue {
    private Point[] array;
    private int size;
    private int head;
    private int tail;
    private int count;
    public MyQueue(int size){
        this.size = size;
        array = new Point[size];
        head = 0;
        tail = -1;
        count = 0;
    }
    public void  insert(Point x){
        if (tail == size-1){
            tail = -1;
        }
        array[++tail] = x;
        count++;
    }
    public Point remove(){
        if (head == size){
            head = 0;
        }
        count--;
        return  array[head++];
    }

    public boolean isEmpty(){
        return count == 0;
    }

}
