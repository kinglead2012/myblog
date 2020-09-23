package com.kinglead.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kinglead.demo.domain.User;

//未来使用mybatis-plus的公共接口，必须继承BaseMapper
public interface UserMapper extends BaseMapper<User> {
}
