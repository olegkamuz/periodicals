package com.training.periodical.entity;

import java.io.Serializable;

/**
 * Root of all entities which have identifier field.
 */
public abstract class Entity implements Serializable {

    public Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
