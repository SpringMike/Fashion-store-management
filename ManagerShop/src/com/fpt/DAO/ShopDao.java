/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import java.util.List;


public abstract class ShopDAO<Entity, Key> {

    abstract public void insert(Entity e);

    abstract public void update(Entity e);

    abstract public void delete(Key k);

    abstract public List<Entity> selectAll();

    abstract public Entity selectById(Key k);

    abstract protected List<Entity> selectBySql(String sql, Object... args);
}
