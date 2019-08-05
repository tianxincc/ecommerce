package com.ecommerce.service;

import com.ecommerce.service.model.PromoModel;

public interface PromoService {

    //根据itemid获取即将进行的和正在进行的活动
    PromoModel getPromoByItemId(Integer itemId);
}
