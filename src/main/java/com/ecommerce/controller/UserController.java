package com.ecommerce.controller;

import com.alibaba.druid.util.StringUtils;
import com.ecommerce.controller.ViewObject.UserView;
import com.ecommerce.error.BusinessException;
import com.ecommerce.error.EmBusinesError;
import com.ecommerce.response.CommonReturnType;
import com.ecommerce.service.UserServer;
import com.ecommerce.service.model.UserModel;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    private UserServer userServer;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //用户登录接口
    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes = CONTENT_TYPE_FROMED)
    @ResponseBody
    public  CommonReturnType login(@RequestParam(name="telphone") String telphone,@RequestParam(name="password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {


        //入参检验
        if(StringUtils.isEmpty(telphone) || StringUtils.isEmpty(password)){
                new BusinessException(EmBusinesError.PARAMETRT_VALIDATION_ERROR);
        }

        //用户登录服务，检验用户登录是否合法
       UserModel userModel=userServer.validataLogin(telphone,this.EncodeByMd5(password));


        //将用户凭证加入到用户成功的session中
        httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);

        return CommonReturnType.create(null);

    }

    //用户注册接口
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = CONTENT_TYPE_FROMED)
    @ResponseBody
    public  CommonReturnType register(@RequestParam(name="telphone") String telphone,
                                      @RequestParam(name="otpCode") String otpCode,
                                      @RequestParam(name="name") String name,
                                      @RequestParam(name="gender") String gender,
                                      @RequestParam(name="age") String age,
                                      @RequestParam(name="password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和对应的otpCode 是否相符合
        String inSessionOtpCode= (String) this.httpServletRequest.getSession().getAttribute(telphone);
        if(!StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new  BusinessException(EmBusinesError.PARAMETRT_VALIDATION_ERROR,"短信验证码不符合");
        }

        //用户注册流程
        UserModel userModel=new UserModel();
        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(EncodeByMd5(password));

        userServer.register(userModel);
        return  CommonReturnType.create(null);
    }

    public  String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder=new BASE64Encoder();
        //加密字符串
        String newstr=base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return  newstr;
    }


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
