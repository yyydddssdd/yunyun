package com.example.helloserver.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.helloserver.common.Result;
import com.example.helloserver.common.ResultCode;
import com.example.helloserver.dto.UserDTO;
import com.example.helloserver.mapper.UserMapper;
import com.example.helloserver.entity.User;
import com.example.helloserver.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Map<String, String> userMap = new HashMap<>();

    // 改用构造注入，解决Field injection is not recommended警告
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Result<String> register(UserDTO dto) {
        if (userMap.containsKey(dto.getUsername())) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }
        userMap.put(dto.getUsername(), dto.getPassword());
        return Result.success("注册成功");
    }

    @Override
    public Result<String> login(UserDTO dto) {
        if (!userMap.containsKey(dto.getUsername())) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        if (!userMap.get(dto.getUsername()).equals(dto.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR);
        }
        return Result.success(UUID.randomUUID().toString());
    }

    @Override
    public Result<Page<User>> getUserPage(Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        // 这里直接传null，代替queryWrapper: null
        userMapper.selectPage(page, null);
        return Result.success(page);
    }
}