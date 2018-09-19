package ru.iac.testtask.dao;

import ru.iac.testtask.model.Shop;

import java.util.List;

public interface ShopDao {

    int getShopCount();

    List<Shop> findAll(int offset, int limit);

    List<Shop> searchByAddress(String searchAddress, int offset, int limit);

    Shop findById(int id);

    void create(Shop shop);

    void update(Shop shop);

    void remove(int id);
}
