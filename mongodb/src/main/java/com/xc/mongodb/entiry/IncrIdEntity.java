package com.xc.mongodb.entiry;

import org.springframework.data.annotation.Id;

public abstract class IncrIdEntity<T extends Number> {
    @Id
    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
