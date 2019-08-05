package com.ecommerce.service.impl;

import com.ecommerce.dao.PromoDoMapper;
import com.ecommerce.dataobject.PromoDo;
import com.ecommerce.service.PromoService;
import com.ecommerce.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDoMapper promoDoMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        //获取对应商品的秒杀活动信息
        PromoDo promoDo=promoDoMapper.selectByItemId(itemId);

        //dataobject -> model
        PromoModel promoModel=convertFromDataObject(promoDo);

        if(promoModel==null){
            return null;
        }

        //判断当前时间是否秒杀活动即将开始或正在进行
        DateTime now=new DateTime();
        if(promoModel.getStartDate().isAfterNow()){ // 判断获取到的时间是否早于当前时间
            promoModel.setStatus(1);//若当前时间比获取到的时间早 则活动未开始
        }else if(promoModel.getEndDate().isBeforeNow()){ // 判断获取到的时间是否小于当前时间
            promoModel.setStatus(3); //若当前时间大于获取时间 则活动已结束
        }else{
            promoModel.setStatus(2);
        }
        return promoModel;
    }

    private  PromoModel convertFromDataObject(PromoDo promoDo){

        if(promoDo==null){
            return  null;
        }
        PromoModel promoModel=new PromoModel();
        BeanUtils.copyProperties(promoDo,promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promoDo.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDo.getStartDate()));
        promoModel.setEndDate(new DateTime(promoModel.getEndDate()));
        return promoModel;
    }

}
