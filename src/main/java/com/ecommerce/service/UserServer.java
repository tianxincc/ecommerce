package com.ecommerce.service;

import com.ecommerce.error.BusinessException;
import com.ecommerce.service.model.UserModel;

public interface UserServer {
    //通過用戶ID 获取用户对象方法
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;

    /**
     *
     * @param telphone 用户注册的手机号码
     * @param encrptPassword 用户加密后的密码
     */
    UserModel validataLogin(String telphone,String encrptPassword) throws BusinessException;


}
