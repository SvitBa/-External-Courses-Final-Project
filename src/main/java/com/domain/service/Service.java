package com.domain.service;

import java.util.List;

public interface Service<Entity, Key> {

    void create(Entity entity);
    Entity read(Key key);
    List<Entity> readAll();
    void update(Entity entity);
    void delete(Entity entity);

}
