package com.ecommerce.dao;

import com.ecommerce.dataobject.ItemDo;
import com.ecommerce.service.model.ItemModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemDo record);

    int insertSelective(ItemDo record);

    ItemDo selectByPrimaryKey(Integer id);

    List<ItemDo> listItem();

    int updateByPrimaryKeySelective(ItemDo record);

    int updateByPrimaryKey(ItemDo record);

    int increaseSales(@Param("id")Integer id,@Param("amount")Integer amount);

}