package com.along.springboot.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JwtService {

    //密钥
    public static final String SECRET = "36D40B0FBDECA764E989E48B9CA99A55C3A58D9A6A03CFF2C86219308B9761639C1179031206F9C1E285FE9191ECD4F1D1C877274553C9ABF62D3E91AF1BCD2C";
    //过期时间 一个月
    public static final Date VALIDITY = new Date(System.currentTimeMillis() + 1000 * 60 * 24 * 30);

    public String generateJwt(UserDetails userDetails) {
        /*
            如果我们想要添加更多的信息
                Map<String,String> claims = new HashMap<>();
                claims.put("tao","taotao");
                    Jwts.builder()
                        .claims(claims)
         */
        Map<String, String> claims = new HashMap<>();
        claims.put("tao", "taotao");
        return Jwts.builder()
                //自定义额外的信息
                .claims(claims)
                //设置主题
                .subject(userDetails.getUsername())
                //颁发时间
                .issuedAt(new Date(System.currentTimeMillis()))
                //过期时间
                .expiration(VALIDITY)
                //提供一个加密的密钥
                .signWith(generateKey())
                //转换为String
                .compact();

    }


    private SecretKey generateKey() {
        //解码密钥
        byte[] decode = Base64.getDecoder().decode(SECRET);
        //重新生成密钥的Key
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUsername(String jwt) {
        Claims claims = Jwts.parser()
                //提供一个验证解密的密钥（需和加密密钥一致）
                .verifyWith(generateKey())
                .build()
                //解析
                .parseSignedClaims(jwt)
                //获取负载
                .getPayload();
        //获取到主题就返回（这里的主题是看你生成 subject 的内容）
        return claims.getSubject();

    }

    public boolean isjwtValid(String jwt) {
        Claims claims = Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        // 如果过期时间 在调用该方法验证的时间之后 就是表示jwt过期
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

}
