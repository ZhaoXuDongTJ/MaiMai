package com.maimai.zz.maimai;

import java.io.IOException;

/**
 * Created by 92198 on 2017/10/1.
 */

public class GETHttpClientLogin {
    private String username;
    private String password;
    GETHttpClientLogin(String username,String password) throws IOException {
        this.username = username;
        this.password = password;
    }
}
