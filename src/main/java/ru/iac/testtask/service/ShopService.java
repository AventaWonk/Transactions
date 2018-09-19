package ru.iac.testtask.service;

import org.springframework.stereotype.Service;
import ru.iac.testtask.model.Product;
import ru.iac.testtask.model.Shop;

import java.util.List;

public interface ShopService {

    int getShopCount();

    List<Shop> getAllShops(int offset, int limit);

    List<Shop> searchByAddress(String searchAddress, int offset, int limit);

    /**
     * Gets an existing shop by id
     *
     * @param id id of target shop
     * @return   an existing shop
     */
    Shop getShopById(int id);

    /**
     * Adds a new shop
     *
     * @param shop new shop
     */
    void addShop(Shop shop);

    /**
     * Updates an existing shop
     *
     * @param shop existing shop with updated fields (excepts id)
     */
    void updateShop(Shop shop);

    /**
     * Deletes an existing shop
     *
     * @param id existing shop id
     */
    void deleteShop(int id);
}
