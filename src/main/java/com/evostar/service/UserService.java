package com.evostar.service;

import com.evostar.dao.UserDAO;
import com.evostar.exception.ServiceException;
import com.evostar.model.MsgCodeEnum;
import com.evostar.model.User;
import com.evostar.utils.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public int register(String username, String password) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isBlank(username)) {
            throw new ServiceException(MsgCodeEnum.ACCOUNT_EMPTY);
        }

        if (StringUtils.isBlank(password)) {
            throw new ServiceException(MsgCodeEnum.PASSWORD_EMPTY);
        }
        //判断是否已经注册过
        User user = userDAO.selectByName(username);

        if (user != null) {
            throw new ServiceException(MsgCodeEnum.ACCOUNT_REGISTERED);
        }
        // 密码强度
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        String head = "/default.jpg";//默认头像
        user.setHeadUrl(head);
        user.setPassword(DigestUtils.md5Hex(password + user.getSalt()));
        return userDAO.addUser(user);
    }

    public User login(String username, String password, boolean rememberme) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            throw new ServiceException(MsgCodeEnum.ACCOUNT_EMPTY);
        }

        if (StringUtils.isBlank(password)) {
            throw new ServiceException(MsgCodeEnum.PASSWORD_EMPTY);
        }

        User user = userDAO.selectByName(username);

        if (user == null) {
            throw new ServiceException(MsgCodeEnum.ACCOUNT_ERROR);
        }

        if (!DigestUtils.md5Hex(password + user.getSalt()).equals(user.getPassword())) {
            throw new ServiceException(MsgCodeEnum.PASSWORD_ERROR);
        }
        //是否7天免登录
        int expire = rememberme ? 7 * 24 * 60 : 120;
        String token = JwtUtils.createJWTToken(user.getId(), expire);
        user.setToken(token);
        user.setPassword(null);
        user.setSalt(null);
        return user;
    }

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public List<User> getUserByIds(List<Integer> userIdList) {
        return userDAO.selectByIds(userIdList);
    }
}
