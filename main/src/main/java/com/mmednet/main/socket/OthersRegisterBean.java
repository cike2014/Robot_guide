package com.mmednet.main.socket;

/**
 * 第三方注册bean
 * 
 * @author ruandan
 *
 */
public class OthersRegisterBean {
    private String name;
    private String identification;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public OthersRegisterBean(String name, String identification) {
        super();
        this.name = name;
        this.identification = identification;
    }

    public OthersRegisterBean() {
        super();
    }

}
