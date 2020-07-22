package com.evostar.netty.utils;

import com.evostar.VO.UserVO;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Session会话（用户信息）
 * 实际生产环境中 Session 中的字段可能较多，比如头像 url，年龄，性别等等
 */
@Data
@NoArgsConstructor
public class Session {

    /**
     * 用户唯一标识
     */
    private UserVO userVO;

    public Session(UserVO userVO) {
        this.userVO = userVO;
    }

    @Override
    public String toString() {
        return userVO + ":" + userVO;
    }
}
