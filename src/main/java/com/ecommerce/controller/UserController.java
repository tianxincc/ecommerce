package com.ecommerce.controller;

import com.ecommerce.controller.ViewObject.UserView;
import com.ecommerce.error.BusinessException;
import com.ecommerce.error.EmBusinesError;
import com.ecommerce.response.CommonReturnType;
import com.ecommerce.service.UserServer;
import com.ecommerce.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController{

    @Autowired
    private UserServer userServer;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //获取用户otp短信接口
    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},consumes = CONTENT_TYPE_FROMED)
    @ResponseBody
    public  CommonReturnType getOtp(@RequestParam(name="telphone")String telphone){
        //需要按照一定的规则生成otp验证码
         Random random=new Random();
         int randomInt=random.nextInt(99999);
         randomInt+=100000;
         String otpCode=String.valueOf(randomInt);

         //将OTP验证码同时对于用户的手机号码关联，使用httpsession绑定于手机于otpcode
        httpServletRequest.getSession().setAttribute(telphone,otpCode);

        //将OTP验证码通过短信通道发送给用户，省略
        System.out.println("telphone="+telphone+"otp验证码="+otpCode);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="id")Integer id) throws BusinessException {
        UserModel userModel=userServer.getUserById(id);
        //将核心领域模型装换为供前端使用的Model
        UserView userView = convertFromModel(userModel);

        //返回通用对象
        return CommonReturnType.create(userView);
    }

    private  UserView convertFromModel(UserModel userModel){
        if (userModel == null) { return  null; }
        UserView  userView=new UserView();
        BeanUtils.copyProperties(userModel,userView);
        return  userView;
    }
}
