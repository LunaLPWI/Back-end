package com.luna.luna_project.models;

public class Stack<T> {

    // 01) Atributos
    private T[] pilha;
    private int topo;

    // 02) Construtor
    public Stack() {
        pilha = (T[]) new Object[1];
        topo = -1;
    }

    // 03) Método isEmpty
    public Boolean isEmpty() {
        return topo == -1;
    }



    // 05) Método push
    public void push(T info) {
        T[] aux = (T[]) new Object[pilha.length+1];
        for (int i = 0; i<pilha.length; i++){
            aux[i] = pilha[i];
        }
        aux[++topo] = info;
        pilha = aux;
    }

    // 06) Método pop
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        return pilha[topo--];
    }

    // 07) Método peek
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return pilha[topo];
    }

    // 08) Método exibe
    public void exibe() {
        if (isEmpty()) {
            System.out.println("Pilha vazia!");
        }
        else {
            System.out.println("\nElementos da pilha:");
            for (int i = topo; i >= 0; i--) {
                System.out.println(pilha[i]);
            }
        }
    }


    //Getters & Setters (manter)
    public int getTopo() {
        return topo;
    }
}