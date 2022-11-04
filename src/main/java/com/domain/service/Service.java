package com.domain.service;

import java.util.List;

public interface Service<Entity, Key> {

    Entity create(Entity entity);
    Entity read(Key key);
    List<Entity> readAll();
    Entity update(Entity entity);
    void delete(Entity entity);

}
