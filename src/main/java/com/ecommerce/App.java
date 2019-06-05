package com.ecommerce;

import com.ecommerce.dao.PasswordDoMapper;
import com.ecommerce.dao.UserDoMapper;
import com.ecommerce.dataobject.UserDo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"com.ecommerce"})
@RestController
@MapperScan("com.ecommerce.dao")
public class App 
{
    @Autowired
    private UserDoMapper userdoMapper;
    @Autowired
    private PasswordDoMapper passwordDoMapper;
    @RequestMapping("/")
    public  String home(){
       UserDo user=userdoMapper.selectByPrimaryKey(1);
       if(user==null){
            return "用户对象不存在";
       }else{
           return user.getName();
       }
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);
    }
}
