package com.junkai.picture_enhancement_platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.junkai.picture_enhancement_platform.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> selectAll();

    int updateByUsername(User user);

    int deleteByUsername(String username);

    User selectByUsername(String username);

    int updatePassword(String username, String newPassword);
}