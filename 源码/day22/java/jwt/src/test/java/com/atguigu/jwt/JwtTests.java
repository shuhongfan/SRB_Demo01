package com.atguigu.jwt;

import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class JwtTests {

    private static long tokenExpiration = 1000; //1000 * 60 * 60 * 24;
    private static String tokenSignKey = "atguigu123";

    @Test
    public void testCreatedToken(){

        JwtBuilder jwtBuilder = Jwts.builder();
        //头、载荷、签名哈希
        String jwtToken = jwtBuilder
                //头
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")

                //载荷：自定义信息
                .claim("nickname", "Helen")
                .claim("avatar", "1.jpg")
                .claim("role", "admin")

                //载荷：默认信息
                .setSubject("srb-user") //令牌主题
                .setIssuer("atguigu") //签发者
                .setAudience("atguigu") //接收方
                .setIssuedAt(new Date()) //令牌的签发时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)) //过期时间
                .setNotBefore(new Date(System.currentTimeMillis() + 1000 * 20)) //生效时间
                .setId(UUID.randomUUID().toString())

                //签名哈希
                .signWith(SignatureAlgorithm.HS256, tokenSignKey)

                //组装jwt字符串
                .compact();

        System.out.println(jwtToken);

    }

    @Test
    public void testGetUserInfo(){

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuaWNrbmFtZSI6IkhlbGVuIiwiYXZhdGFyIjoiMS5qcGciLCJyb2xlIjoiYWRtaW4iLCJzdWIiOiJzcmItdXNlciIsImlzcyI6ImF0Z3VpZ3UiLCJhdWQiOiJhdGd1aWd1IiwiaWF0IjoxNjE0NTYyMTg4LCJleHAiOjE2MTQ1NjIxODksIm5iZiI6MTYxNDU2MjIwOCwianRpIjoiZTNlMmE4ZWYtZGJhYi00ZTk1LTgyZjgtMjAzNmRiNjM5ZWUyIn0.E2C9YvJwK4t7slZxHydBq1IIRI3IuLPXo0S8dUI6KDI";

        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(tokenSignKey).parseClaimsJws(token);

        Claims claims = claimsJws.getBody();

        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");
        String role = (String)claims.get("role");
        System.out.println(nickname);
        System.out.println(avatar);
        System.out.println(role);

        String id = claims.getId();
        System.out.println(id);
    }
}
