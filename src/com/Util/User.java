package com.Util;

public class User {
    //用户唯一ID标识号
    private String userId;
    //用户密码
    private String password;
    //电话区号
    private String disCode = "86";
    //电话号码
    private String phoneNum;
    //昵称
    private String nickName;
    //头像字符串
    private String headIcon;
    //校区代码
    private int campusCode;
    //身份证号
    private String IDCode;
    //真实姓名
    private String realName;
    //学号
    private String campusIdCode;
    //默认收货地址
    private String defaultAddress;
    //是否通过实名认证
    private boolean isVerified;
    
  //Setter and getter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisCode() {
        return disCode;
    }

    public void setDisCode(String disCode) {
        this.disCode = disCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public int getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(int campusCode) {
        this.campusCode = campusCode;
    }

    public void setIDCode(String IDCode) {
        this.IDCode = IDCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCampusIdCode() {
        return campusIdCode;
    }

    public void setCampusIdCode(String campusIdCode) {
        this.campusIdCode = campusIdCode;
    }

    public String getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getIDCode() {
        return IDCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsVerified(boolean isVerified) {
    	this.isVerified = isVerified;
    }
    
    public boolean isVerified() {
        return isVerified;
    }
}
