package com.evostar.service;

import java.util.*;

import com.evostar.dao.UserDAO;
import com.evostar.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }
    public List<User> getUserByIds(List<Integer> userIdList){
        return userDAO.selectByIds(userIdList);
    }
}
