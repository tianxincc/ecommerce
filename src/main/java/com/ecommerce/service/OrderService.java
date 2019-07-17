package com.ecommerce.service;

import com.ecommerce.error.BusinessException;
import com.ecommerce.service.model.OrderModel;

public interface OrderService {
    OrderModel createOrder(Integer userId,Integer itemId,Integer amount) throws BusinessException;
}
