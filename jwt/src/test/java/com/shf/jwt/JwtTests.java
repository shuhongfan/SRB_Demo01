package com.shf.jwt;

import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class JwtTests {

    private static long tokenExpiration = 1000 * 60 * 60 * 24;

    private static String tokenSignKey = "shuhongfan";

    @Test
    public void testCreatedToken() {
        JwtBuilder jwtBuilder = Jwts.builder();

//        头、载荷、签名哈希
        String compact = jwtBuilder
//                头
                .setHeaderParam("alg", "")
                .setHeaderParam("typ", "JWT")

//                载荷：自定义信息
                .claim("nickName", "shuhongfan")
                .claim("avatar", "1.jpg")
                .claim("role", "srb_user")

//                载荷：默认信息
                .setSubject("srb-user") //令牌主题
                .setIssuer("shuhongfan") //签发者
                .setAudience("shuhongfan") // 接收者
                .setIssuedAt(new Date()) // 令牌的签发时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)) // 过期时间
                .setNotBefore(new Date(System.currentTimeMillis() + 1000 * 20)) //生效时间
                .setId(UUID.randomUUID().toString())

//                签名哈希
                .signWith(SignatureAlgorithm.HS256, tokenSignKey)
//                组装JWT
                .compact();


        System.out.println(compact);

    }

    @Test
    public void testGetUserInfo() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuaWNrTmFtZSI6InNodWhvbmdmYW4iLCJhdmF0YXIiOiIxLmpwZyIsInJvbGUiOiJzcmJfdXNlciIsInN1YiI6InNyYi11c2VyIiwiaXNzIjoic2h1aG9uZ2ZhbiIsImF1ZCI6InNodWhvbmdmYW4iLCJpYXQiOjE2NTg3NDI1MjAsImV4cCI6MTY1ODgyODkyMCwibmJmIjoxNjU4NzQyNTQwLCJqdGkiOiI0M2RjMDdkZC1lMzUxLTQ0YzgtYjAxOS1iNjY1ODU5OWZhN2YifQ.9SKuFTUnYKVcjUi03Ha07T7KRaOyUHGuNcBA4SCPRV4";

        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(tokenSignKey).parseClaimsJws(token);

        Claims claims = claimsJws.getBody();

        String nickname = (String) claims.get("nickName");
        String avatar = (String) claims.get("avatar");
        String role = (String) claims.get("role");

        System.out.println(nickname); // shuhongfan
        System.out.println(avatar); // 1.jpg
        System.out.println(role); // srb_user

        String id = claims.getId(); // 43dc07dd-e351-44c8-b019-b6658599fa7f
        System.out.println(id);
    }
}
