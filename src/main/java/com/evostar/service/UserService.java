package com.evostar.service;

import java.util.*;

import com.evostar.dao.UserDAO;
import com.evostar.model.User;
import com.evostar.utils.CommonUtils;
import com.evostar.utils.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public Map<String, Object> register(String username, String password,boolean rememberme) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        //判断是否已经注册过
        User user = userDAO.selectByName(username);

        if (user != null) {
            map.put("msg", "用户名已经被注册");
            return map;
        }
        // 密码强度
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        String head = "/default.jpg";//默认头像
        user.setHeadUrl(head);
        user.setPassword(CommonUtils.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        // 登陆
        //是否7天免登录
        int expire = rememberme ? 7*24*60 : 120;
        String token = JwtUtils.createJWTToken(user.getId(),expire);
        map.put("token", token);
        map.put("name",user.getName());
        map.put("head_url",user.getHeadUrl());
        return map;
    }

    public Map<String, Object> login(String username, String password,boolean rememberme) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);

        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }

        if (!CommonUtils.MD5(password+user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码不正确");
            return map;
        }
        //是否7天免登录
        int expire = rememberme ? 7*24*60 : 120;
        String token = JwtUtils.createJWTToken(user.getId(),expire);
        map.put("token", token);
        map.put("name",user.getName());
        map.put("head_url",user.getHeadUrl());
        return map;
    }

    public User getUser(int id) {
        return userDAO.selectById(id);
    }
}
