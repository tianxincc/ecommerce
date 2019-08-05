package com.ecommerce.service.impl;


import com.ecommerce.dao.ItemDoMapper;
import com.ecommerce.dao.ItemStockDoMapper;
import com.ecommerce.dataobject.ItemDo;
import com.ecommerce.dataobject.ItemStockDo;
import com.ecommerce.error.BusinessException;
import com.ecommerce.error.EmBusinesError;
import com.ecommerce.service.ItemService;
import com.ecommerce.service.PromoService;
import com.ecommerce.service.model.ItemModel;
import com.ecommerce.service.model.PromoModel;
import com.ecommerce.validator.ValidationResult;
import com.ecommerce.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDoMapper itemDoMapper;

    @Autowired
    private ItemStockDoMapper itemStockDoMapper;

    @Autowired
    private PromoService promoService;

    private ItemDo conveItemDOFromItemModel(ItemModel itemModel){
        if(itemModel==null){
            return  null;
        }

        ItemDo itemDo=new ItemDo();
        BeanUtils.copyProperties(itemModel,itemDo);
        itemDo.setPrice(itemModel.getPrice().doubleValue());

        return  itemDo;
    }

    private ItemStockDo conveItemStockDOFromItemModel(ItemModel itemModel){

        if(itemModel!=null){
            ItemStockDo itemStockDo=new ItemStockDo();
            itemStockDo.setItemId(itemModel.getId());
            itemStockDo.setStock(itemModel.getStock());
            return itemStockDo;
        }
        return null;
    }

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {


            if(itemModel == null){
                throw  new BusinessException(EmBusinesError.PARAMETRT_VALIDATION_ERROR);
            }
            //校验入参
            ValidationResult result=validator.validate(itemModel);
            if(result.isHasErrors()){
               throw  new  BusinessException(EmBusinesError.PARAMETRT_VALIDATION_ERROR,result.getError());
            }
            //转化itemmodel->dataobject
            ItemDo itemDo=this.conveItemDOFromItemModel(itemModel);
            //写入数据库

            itemDoMapper.insertSelective(itemDo);


            itemModel.setId(itemDo.getId());
            ItemStockDo itemStockDo=this.conveItemStockDOFromItemModel(itemModel);
            if(itemStockDo==null){
                return  null;
            }
            itemStockDoMapper.insertSelective(itemStockDo);

            //返回创建完成对象
            return this.getItemById(itemModel.getId());
            //return itemModel;
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDo> itemDolist=itemDoMapper.listItem();
        List<ItemModel> itemModelList=itemDolist.stream().map(itemDo -> {
            ItemStockDo itemStockDo=itemStockDoMapper.selectByItemId(itemDo.getId());
            ItemModel itemModel=this.convertModelFromDataObject(itemDo,itemStockDo);
            return  itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDo itemDo=itemDoMapper.selectByPrimaryKey(id);
        if(itemDo==null){
            return  null;
        }
        //操作获取库存数量
        ItemStockDo itemStockDo=itemStockDoMapper.selectByItemId(itemDo.getId());

        //将dataobject -> model
        ItemModel itemModel=convertModelFromDataObject(itemDo,itemStockDo);

        //获取活动商品信息
        PromoModel promoModel=promoService.getPromoByItemId(itemModel.getId());
        if(promoModel!=null && promoModel.getStatus().intValue()!=3){
            itemModel.setPromoModel(promoModel);
        }

        return itemModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
        int Row=itemStockDoMapper.decreaseStock(itemId,amount);
       if(Row>0){
           //更新库存成功
           return  true;
       }else{
           //更新库存失败
           return false;
       }
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        itemDoMapper.increaseSales(itemId,amount);
    }

    private  ItemModel convertModelFromDataObject(ItemDo itemDo,ItemStockDo itemStockDo){
        ItemModel itemModel=new ItemModel();
        BeanUtils.copyProperties(itemDo,itemModel);
        itemModel.setPrice(new BigDecimal(itemDo.getPrice()));
        itemModel.setStock(itemStockDo.getStock());
        return itemModel;
    }
}
