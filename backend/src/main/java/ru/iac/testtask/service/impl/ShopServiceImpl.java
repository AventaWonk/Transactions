package ru.iac.testtask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.iac.testtask.dao.ShopDao;
import ru.iac.testtask.model.Shop;
import ru.iac.testtask.service.ShopService;

import java.util.List;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    private ShopDao shopDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public int getShopCount() {
        return this.shopDao.getShopCount();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Shop> getAllShops(int offset, int limit) {
        return this.shopDao.findAll(offset, limit);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Shop> searchByAddress(String searchAddress, int offset, int limit) {
        return this.shopDao.searchByAddress(searchAddress, offset, limit);
    }

    /**
     * Gets an existing shop by id
     *
     * @param id id of target shop
     * @return an existing shop
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Shop getShopById(int id) {
        return this.shopDao.findById(id);
    }

    /**
     * Adds a new shop
     *
     * @param shop new shop
     */
    @Override
    public void addShop(Shop shop) {
        this.shopDao.create(shop);
    }

    /**
     * Updates an existing shop
     *
     * @param shop existing shop with updated fields (excepts id)
     */
    @Override
    public void updateShop(Shop shop) {
        this.shopDao.update(shop);
    }

    @Override
    public void deleteShop(int id) {
        this.shopDao.remove(id);
    }

    @Autowired
    public void setShopDao(ShopDao shopDao) {
        this.shopDao = shopDao;
    }
}
