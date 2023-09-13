package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private WeChatProperties weChatProperties;
    public static final  String wxLoginUrl = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private UserMapper userMapper;


    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
       String openid =  getOpenId(userLoginDTO.getCode());
       User userData =  userMapper.getByOpenid(openid);

//      判断当前用户是否为新用户
        if (userData==null) {
            Long id  = userMapper.UserInsert();
            userData =   User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .id(id)
                    .build();


       log.info("-------------------------", id);

        }

//        如果是新用户，自动完成注册





        return userData;
    }

    /**
     * 调用微信接口服务，获得当前用户的openid
     * @param code
     * @return
     */

    private String getOpenId(String code) {

        HashMap<String, String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");

        String  userJson =  HttpClientUtil.doGet(wxLoginUrl,map);
        JSONObject jsonObject = JSON.parseObject(userJson);
        String openid = jsonObject.getString("openid");
        if (openid==null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        return openid;
    }



}
