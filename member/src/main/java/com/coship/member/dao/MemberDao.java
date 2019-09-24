package com.coship.member.dao;

import com.coship.api.member.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberDao {

    @Select("select id,username,password,phone,email,created,updated from mb_user where id =#{userId}")
    UserEntity findByID(@Param("userId") Long userId);

    @Insert("INSERT  INTO `mb_user` (username,password,phone,email,created,updated) VALUES (#{username}, #{password},#{phone},#{email},#{created},#{updated});")
    Integer insertUser(UserEntity userEntity);

    @Select("select id,username,password,phone,email,created,updated from mb_user where username =#{username} and password =#{password}")
    UserEntity login(@Param("username") String username, @Param("password") String password);
}