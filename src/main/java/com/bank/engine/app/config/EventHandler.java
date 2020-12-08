package com.bank.engine.app.config;

public interface EventHandler<T> {

    void handler(T object);
}
