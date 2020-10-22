package model;

public interface Observer<T> {

    void notify(T data);

}
