package com.ecommerce.controller;

import com.ecommerce.error.BusinessException;
import com.ecommerce.error.EmBusinesError;
import com.ecommerce.response.CommonReturnType;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.model.OrderModel;
import com.ecommerce.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController extends  BaseController {

    @Autowired
    private  OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;


    //封装下单请求

    @RequestMapping(value = "/createOrder",method = {RequestMethod.POST},consumes = CONTENT_TYPE_FROMED)
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name="itemId")Integer itemId,
                                        @RequestParam(name="amount")Integer amount,
                                        @RequestParam(name="promoId",required = false)Integer promoId) throws BusinessException {

        Boolean isLogin= (boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin==null || !isLogin.booleanValue()){
            throw  new BusinessException(EmBusinesError.USER_NOT_lOGIN,"用户还未登录,不能下单");
        }
        //获取用户登录信息
        //UserModel userModel= (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER ");
        UserModel userModel= (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");

        OrderModel orderModel=orderService.createOrder(userModel.getId(),itemId,promoId,amount);
        return  CommonReturnType.create(orderModel);
    }
}
