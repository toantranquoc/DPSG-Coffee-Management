/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author pc
 */
public class account {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private String username;    
    private String password;    
    private int loaiTk;
    public account()
    {}
    public account(int matk, String Username, String Password, int loaitk)
    {
        id = matk;
        username = Username;
        password = Password;
        loaiTk = loaitk;
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

    public int getLoaiTk() {
        return loaiTk;
    }

    public void setLoaiTk(int loaiTk) {
        this.loaiTk = loaiTk;
    }

}
