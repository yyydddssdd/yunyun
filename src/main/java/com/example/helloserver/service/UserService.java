package com.example.helloserver.service;

import com.example.helloserver.common.Result;
import com.example.helloserver.dto.UserDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.helloserver.entity.User;

public interface UserService {
    Result<String> register(UserDTO dto);
    Result<String> login(UserDTO dto);
    // 注意：这里的返回类型必须和实现类完全匹配
    Result<Page<User>> getUserPage(Integer pageNum, Integer pageSize);
}