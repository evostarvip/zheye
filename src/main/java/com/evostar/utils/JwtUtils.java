package com.evostar.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

public final class JwtUtils {
    public static final String secret="evoStar2020";//服务端秘钥
    /**
     * 生成 JWT Token
     * */
    public static String createJWTToken(Integer userId, int expire) throws JWTCreationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //jwt 头部信息
        Map<String,Object> map=new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        Date nowDate = new Date();
        Date expireDate = AddDate(nowDate,expire);//多少分钟过期

        String token= JWT.create()
                .withHeader(map)
                .withClaim("userId", userId.toString())
                .withClaim("version", "1.0")
                .withIssuedAt(nowDate)//对应 paylaod iat 节点：生效时间
                .withExpiresAt(expireDate) //对应 paylaod  exp 签发人 节点：过期时间
                .sign(algorithm);
        return  token;
    }
    /**
     * 验证 token
     * */
    public static Map<String, Claim> verifyJWTToken(String token) throws JWTVerificationException {
        Algorithm algorithm= Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt =verifier.verify(token);
        return jwt.getClaims();
    }

    /**
     * 时间加减法
     * */
    private static Date AddDate(Date date, Integer minute){
        if(null==date)
            date=new Date();
        Calendar cal=new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }
}
