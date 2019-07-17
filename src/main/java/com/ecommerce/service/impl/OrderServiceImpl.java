package com.ecommerce.service.impl;

import com.ecommerce.dao.OrderDoMapper;
import com.ecommerce.dao.SequenceDoMapper;
import com.ecommerce.dataobject.OrderDo;
import com.ecommerce.dataobject.SequenceDo;
import com.ecommerce.error.BusinessException;
import com.ecommerce.error.EmBusinesError;
import com.ecommerce.service.ItemService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.UserServer;
import com.ecommerce.service.model.ItemModel;
import com.ecommerce.service.model.OrderModel;
import com.ecommerce.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserServer userServer;

    @Autowired
    private OrderDoMapper orderDoMapper;

    @Autowired
    private SequenceDoMapper sequenceDoMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        //1.检验下单状态，下单的商品是否存在 ，用户是否合法 购买数量是否正确
        ItemModel itemModel= itemService.getItemById(itemId);
        if(itemModel==null){
            throw  new BusinessException(EmBusinesError.PARAMETRT_VALIDATION_ERROR,"商品信息不存在");
        }
        UserModel userModel=userServer.getUserById(userId);
        if(userModel==null){
            throw  new BusinessException(EmBusinesError.PARAMETRT_VALIDATION_ERROR,"用户信息不存在");
        }
        if(amount<=0 || amount>99){
            throw  new BusinessException(EmBusinesError.PARAMETRT_VALIDATION_ERROR,"数量信息不存在");
        }

        //2.落单减库存
          boolean result=itemService.decreaseStock(itemId,amount);
          if(!result){
              throw  new BusinessException(EmBusinesError.STOCK_NOT_ENOUGH);
          }

        //3.订单入库
          OrderModel orderModel=new OrderModel();
          orderModel.setUserId(userId);
          orderModel.setItemId(itemId);
          orderModel.setAmount(amount);
          orderModel.setItemPrice(itemModel.getPrice());
          orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));

          //生成交易流水号
          orderModel.setId(generateOrderNo());
          OrderDo orderDo=convertFromOrderModel(orderModel);
          orderDoMapper.insertSelective(orderDo);

          //加上商品的销量
        itemService.increaseSales(itemId,amount);

        //4.返回前端


        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public  String generateOrderNo(){
        //订单号有16位
        //前8位为时间信息 年月日
        StringBuilder stringBuilder=new StringBuilder();
        LocalDateTime now=LocalDateTime.now();
        String nowDate=now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);

        //中间6位位自增序列
        //获取当前Sequence
        int sequence=0;
        SequenceDo sequenceDo=sequenceDoMapper.getSequenceByName("order_info");
        sequence=sequenceDo.getCurrentValue();
        sequenceDo.setCurrentValue(sequenceDo.getCurrentValue() + sequenceDo.getStop());
        sequenceDoMapper.updateByPrimaryKeySelective(sequenceDo);
        String sequenceStr=String.valueOf(sequence);

        for (int i=0;i<6-sequenceStr.length();i++){
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);

        //最后2位位分库分表位

        stringBuilder.append("00");

        return  stringBuilder.toString();
    }

    private OrderDo convertFromOrderModel(OrderModel orderModel){
        if(orderModel==null){
            return  null;
        }
        OrderDo orderDo=new OrderDo();
        BeanUtils.copyProperties(orderModel,orderDo);
        orderDo.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDo.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDo;

    }

}
