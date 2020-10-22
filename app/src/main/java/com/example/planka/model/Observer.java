package com.example.planka.model;

public interface Observer<T> {

    void notify(T data);

}
