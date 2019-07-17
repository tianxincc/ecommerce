package com.ecommerce.service;

import com.ecommerce.error.BusinessException;
import com.ecommerce.service.model.ItemModel;

import java.util.List;

public interface ItemService {

    //创建商品

    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //浏览商品

    List<ItemModel> listItem();

    //商品详情浏览

    ItemModel getItemById(Integer id);

    //库存扣减
    boolean decreaseStock(Integer itemid,Integer amount)throws BusinessException;

    //商品销量增加
    void increaseSales(Integer itemId,Integer amount)throws BusinessException;
}
