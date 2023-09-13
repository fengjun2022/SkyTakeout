package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    /**
     * 根据opid查询用户
     * @param openid
     * @return
     */
@Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);


    Long UserInsert();
}

