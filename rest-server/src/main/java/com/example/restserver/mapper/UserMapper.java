package com.example.restserver.mapper;

import com.example.restserver.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM cas_user_base WHERE user_name = #{username}")
    @Results({
            @Result(property = "username",  column = "user_name"),
            @Result(property = "password", column = "user_psd"),
            @Result(property = "isDisable", column = "is_disabled"),
            @Result(property = "isLocked", column = "is_locked"),
            @Result(property = "isExpired", column = "is_expired")
    })
    User findUserByUserName(String userName);
}
