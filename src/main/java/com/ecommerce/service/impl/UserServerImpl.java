package com.ecommerce.service.impl;

import com.ecommerce.dao.PasswordDoMapper;
import com.ecommerce.dao.UserDoMapper;
import com.ecommerce.dataobject.PasswordDo;
import com.ecommerce.dataobject.UserDo;
import com.ecommerce.service.UserServer;
import com.ecommerce.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServerImpl implements UserServer {

    @Autowired
    private UserDoMapper userDoMapper;

    @Autowired
    private PasswordDoMapper passwordDoMapper;

    @Override
    public UserModel getUserById(Integer id) {
        UserDo userDo=userDoMapper.selectByPrimaryKey(id);
        if(userDo==null){return  null;}
        PasswordDo passwordDo= passwordDoMapper.selectByUserId(userDo.getId());

        return convertFromDataObiect(userDo,passwordDo);
    }

    private  UserModel convertFromDataObiect(UserDo userDo,PasswordDo userPasswordDo){
        if(userDo==null){return  null;}
        UserModel userModel=new UserModel();
        BeanUtils.copyProperties(userDo,userModel);
        if(userPasswordDo!=null){
            userModel.setEncrptPassword(userPasswordDo.getEncrptPassword());
        }
        return userModel;
    }

}
