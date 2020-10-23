package com.example.planka.model;

/**
 * Generified observer interface, implemented by Observable
 *
 * @Author Lucas Karlsson
 * @see Observable
 */

public interface Observer<T> {
    void notify(T data);
}
