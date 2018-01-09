package com.java.xdd.mock;

public class ClassTested {

    private Collaborator listener;

    public void setListener(Collaborator listener) {
        this.listener = listener;
    }

    public void addDocument(String title, String document) {
        System.out.println(title + "----" + document);
    }
}