package com.ecommerce.service.impl;

import com.ecommerce.dao.PasswordDoMapper;
import com.ecommerce.dao.UserDoMapper;
import com.ecommerce.dataobject.PasswordDo;
import com.ecommerce.dataobject.UserDo;
import com.ecommerce.error.BusinessException;
import com.ecommerce.error.EmBusinesError;
import com.ecommerce.service.UserServer;
import com.ecommerce.service.model.UserModel;
import com.ecommerce.validator.ValidationResult;
import com.ecommerce.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.beans.Transient;

@Service
public class UserServerImpl implements UserServer {

    @Autowired
    private UserDoMapper userDoMapper;

    @Autowired
    private PasswordDoMapper passwordDoMapper;

    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        UserDo userDo=userDoMapper.selectByPrimaryKey(id);
        if(userDo==null){return  null;}
        PasswordDo passwordDo= passwordDoMapper.selectByUserId(userDo.getId());

        return convertFromDataObiect(userDo,passwordDo);
    }

    @Override
    @Transient
    public void register(UserModel userModel) throws  BusinessException {
        if(userModel == null){
            throw  new BusinessException(EmBusinesError.PARAMETRT_VALIDATION_ERROR);
        }
//        if(StringUtils.isEmpty(userModel.getName()) ||userModel.getGender()==null || userModel.getAge()==null || StringUtils.isEmpty(userModel.getTelphone())){
//            throw  new BusinessException(EmBusinesError.PARAMETRT_VALIDATION_ERROR,"注册用户不合法");
//        }

        ValidationResult result=validator.validate(userModel);
        if(result.isHasErrors()){
          throw   new BusinessException(EmBusinesError.PARAMETRT_VALIDATION_ERROR,result.getError());
        }

        UserDo userDo=conveFromModel(userModel);

       try {
           userDoMapper.insertSelective(userDo);
       }catch (DuplicateKeyException ex){
           throw  new BusinessException(EmBusinesError.PARAMETRT_VALIDATION_ERROR,"手机号已重复注册");
       }


        userModel.setId(userDo.getId());

        PasswordDo passwordDo=convePasswordFromModel(userModel);
        passwordDoMapper.insertSelective(passwordDo);

        return;
    }

    @Override
    public UserModel validataLogin(String telphone, String encrptPassword) throws BusinessException {
        //通过用户手机获取用户信息
       UserDo userDo=userDoMapper.selectByTelphone(telphone);

       if(userDo==null){
          throw  new BusinessException(EmBusinesError.USER_lOGIN_FAIL);
       }
       PasswordDo passwordDo=passwordDoMapper.selectByPrimaryKey(userDo.getId());

       UserModel userModel=convertFromDataObiect(userDo,passwordDo);


        //对比用户信息内加密的密码是否和传输进来的密码相匹配

        if(StringUtils.equals(encrptPassword,userModel.getEncrptPassword())){
            throw  new BusinessException(EmBusinesError.USER_lOGIN_FAIL);
        }

        return userModel;
    }

    private PasswordDo convePasswordFromModel(UserModel userModel){
        if(userModel == null){
            return  null;
        }

        PasswordDo passwordDo=new PasswordDo();
        passwordDo.setEncrptPassword(userModel.getEncrptPassword());
        passwordDo.setUserId(userModel.getId());
        return  passwordDo;

    }

    private UserDo conveFromModel(UserModel userModel){
        if(userModel == null){
            return  null;
        }
        UserDo userDo=new UserDo();
        BeanUtils.copyProperties(userModel,userDo);

        return  userDo;
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
