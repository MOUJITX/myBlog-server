package com.moujitx.myblog.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moujitx.myblog.server.common.AuthAccess;
import com.moujitx.myblog.server.common.Result;
import com.moujitx.myblog.server.entity.User;
import com.moujitx.myblog.server.service.UserService;
import com.moujitx.myblog.server.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Function: (User)表控制层接口
 * Author: MOUJITX
 * Date: 2024-07-21 17:19:44
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @AuthAccess
    @PostMapping("/login")
    public Result login(@RequestBody User login) {
        User user = userService.selectByUsernameAndPassword(login.getUsername(), login.getPassword());
        if (user == null) {
            return Result.error(401, "用户名或密码错误");
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("userid", user.getUuid());
            map.put("token", TokenUtils.generateToken(user.getUuid()));
            map.put("nickname", user.getNickname());
            return Result.success("登录成功", map);
        }
    }

    /* 新增数据 */
    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        if (userService.selectByUsername(user.getUsername()) != null)
            return Result.error("该用户已存在");

        userService.insert(user);
        return Result.success("新增数据成功");
    }

    /* 修改数据 */
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.success("更新数据成功");
    }

    /* 通过主键删除单条数据 */
    @DeleteMapping("/del/{uuid}")
    public Result delete(@PathVariable String uuid) {
        userService.delete(uuid);
        return Result.success("删除数据成功");
    }

    /* 删除多条数据 */
    @DeleteMapping("/del/batch")
    public Result batchDelete(@RequestBody List<String> ids) {
        userService.batchDelete(ids);
        return Result.success("删除数据成功");
    }

    /* 查询全部数据 */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<User> list = userService.selectAll();
        return Result.success(list);
    }

    /* 通过ID查询单条数据 */
    @GetMapping("/selectById/{uuid}")
    public Result selectById(@PathVariable String uuid) {
        User list = userService.selectById(uuid);
        return Result.success(list);
    }

    /* 多条件模糊查询 */
    @PostMapping("/select")
    public Result select(@RequestBody User user) {
        List<User> list = userService.select(user);
        return Result.success(list);
    }

    /* 分页模糊多条件 */
    @PostMapping("/pageSelect")
    public Result pageSelect(HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody User user) {
        Page<User> selectPage = userService.selectPage(user, pageNum, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("list", selectPage.getRecords());
        result.put("total", selectPage.getTotal());
        result.put("totalPage", selectPage.getPages());
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }
}
