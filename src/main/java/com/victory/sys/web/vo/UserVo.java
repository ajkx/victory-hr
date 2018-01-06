package com.victory.sys.web.vo;

/**
 * Created by ajkx
 * Date: 2017/9/2.
 * Time:16:54
 */
public class UserVo {

    private String username;

    private String password;

    public UserVo() {

    }

    public UserVo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
