package com.example.helloserver.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.helloserver.common.Result;
import com.example.helloserver.dto.UserDTO;
import com.example.helloserver.entity.User;
import com.example.helloserver.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // 构造注入
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 注册接口：POST /api/user/register
    @PostMapping("/register")
    public Result<String> register(@RequestBody UserDTO dto) {
        return userService.register(dto);
    }

    // 登录接口：POST /api/user/login
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserDTO dto) {
        return userService.login(dto);
    }

    // 查询接口：GET /api/user/{id}
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable Long id) {
        return Result.success("查询成功，ID：" + id);
    }

    // 更新接口：PUT /api/user/{id}
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable Long id) {
        return Result.success("更新成功");
    }

    // 删除接口：DELETE /api/user/{id}
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        return Result.success("删除成功");
    }

    // 分页查询接口：GET /api/user/page
    @GetMapping("/page")
    public Result<Page<User>> getUserPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        return userService.getUserPage(pageNum, pageSize);
    }
}