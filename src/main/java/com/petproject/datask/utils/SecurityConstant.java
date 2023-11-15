package com.petproject.datask.utils;

public class SecurityConstant {
    // 10 days
    public static final long TOKEN_EXPIRATION = 864000000L;
    // todo: mount secret key from docker env
    public static final String SECRET_KEY_STRING = "dinhngocupdinhngocupdinhngocupdinhngocupdinhngocup";
    public static final String BEARER_PRFIX = "Bearer ";
    public static final String AUTHORIZATION_KEY_HEADER = "Authorization";
    // todo: mount secret key from docker env
    public static final String SUPER_ADMIN_API_KEY = "dinhngocuyenphuong";
}
