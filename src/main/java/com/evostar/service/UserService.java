package com.evostar.service;

import java.util.*;

import com.evostar.dao.UserDAO;
import com.evostar.exception.ServiceException;
import com.evostar.model.MsgCodeEnum;
import com.evostar.model.User;
import com.evostar.utils.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public int register(String username, String password) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isBlank(username)) {
            throw new ServiceException(MsgCodeEnum.ACCOUNT_EMPTY.getCode(), MsgCodeEnum.ACCOUNT_EMPTY.getMsg());
        }

        if (StringUtils.isBlank(password)) {
            throw new ServiceException(MsgCodeEnum.PASSWORD_EMPTY.getCode(), MsgCodeEnum.PASSWORD_EMPTY.getMsg());
        }
        //判断是否已经注册过
        User user = userDAO.selectByName(username);

        if (user != null) {
            throw new ServiceException(MsgCodeEnum.ACCOUNT_REGISTERED.getCode(), MsgCodeEnum.ACCOUNT_REGISTERED.getMsg());
        }
        // 密码强度
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        String head = "/default.jpg";//默认头像
        user.setHeadUrl(head);
        user.setPassword(DigestUtils.md5Hex(password+user.getSalt()));
        return userDAO.addUser(user);
    }

    public User login(String username, String password,boolean rememberme) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            throw new ServiceException(MsgCodeEnum.ACCOUNT_EMPTY.getCode(), MsgCodeEnum.ACCOUNT_EMPTY.getMsg());
        }

        if (StringUtils.isBlank(password)) {
            throw new ServiceException(MsgCodeEnum.PASSWORD_EMPTY.getCode(), MsgCodeEnum.PASSWORD_EMPTY.getMsg());
        }

        User user = userDAO.selectByName(username);

        if (user == null) {
            throw new ServiceException(MsgCodeEnum.ACCOUNT_ERROR.getCode(), MsgCodeEnum.ACCOUNT_ERROR.getMsg());
        }

        if (!DigestUtils.md5Hex(password+user.getSalt()).equals(user.getPassword())) {
            throw new ServiceException(MsgCodeEnum.PASSWORD_ERROR.getCode(), MsgCodeEnum.PASSWORD_ERROR.getMsg());
        }
        //是否7天免登录
        int expire = rememberme ? 7 * 24 * 60 : 120;
        String token = JwtUtils.createJWTToken(user.getId(),expire);
        user.setToken(token);
        user.setPassword(null);
        user.setSalt(null);
        return user;
    }

    public User getUser(int id) {
        return userDAO.selectById(id);
    }
    public List<User> getUserByIds(List<Integer> userIdList){
        return userDAO.selectByIds(userIdList);
    }
}
