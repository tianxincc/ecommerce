package com.ecommerce.service;

import com.ecommerce.error.BusinessException;
import com.ecommerce.service.model.OrderModel;

public interface OrderService {
    //1.通过前端url上传过来活动id，然后下单接口校验对应ID是否属于对应商品且活动已开始

    OrderModel createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount) throws BusinessException;
}
