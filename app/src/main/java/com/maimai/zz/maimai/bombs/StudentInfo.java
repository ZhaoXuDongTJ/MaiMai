package com.maimai.zz.maimai.bombs;

import cn.bmob.v3.BmobObject;

/**
 * Created by 92198 on 2017/10/4.
 */

public class StudentInfo extends BmobObject {
    //基本信息
    private String userName;
    private String studentID;
    private String password;
    // 地址 宿舍楼号 宿舍号
    private Integer blockNumber;
    private Integer roomNumber;
    // 流量 贡献 回报
    private Integer wifiContribute;
    private Integer wifiIncome;
    //  节约书本 贡献书本
    private Integer bookBuy;
    private Integer bookSell;
    //  待发货  待收货
    private Integer deliverGood;
    private Integer receiptGood;
    // 模板 贡献 数量
    private Integer mouldContribute;
    // 时候 公社公成员
    private Boolean isMember;
    private String wifiPassword;
    private Boolean hasWiFIpassword;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getWifiContribute() {
        return wifiContribute;
    }

    public void setWifiContribute(Integer wifiContribute) {
        this.wifiContribute = wifiContribute;
    }

    public Integer getWifiIncome() {
        return wifiIncome;
    }

    public void setWifiIncome(Integer wifiIncome) {
        this.wifiIncome = wifiIncome;
    }

    public Integer getBookBuy() {
        return bookBuy;
    }

    public void setBookBuy(Integer bookBuy) {
        this.bookBuy = bookBuy;
    }

    public Integer getBookSell() {
        return bookSell;
    }

    public void setBookSell(Integer bookSell) {
        this.bookSell = bookSell;
    }

    public Integer getDeliverGood() {
        return deliverGood;
    }

    public void setDeliverGood(Integer deliverGood) {
        this.deliverGood = deliverGood;
    }

    public Integer getReceiptGood() {
        return receiptGood;
    }

    public void setReceiptGood(Integer receiptGood) {
        this.receiptGood = receiptGood;
    }

    public Integer getMouldContribute() {
        return mouldContribute;
    }

    public void setMouldContribute(Integer mouldContribute) {
        this.mouldContribute = mouldContribute;
    }

    public Boolean getMember() {
        return isMember;
    }

    public void setMember(Boolean member) {
        isMember = member;
    }

    public void setWifiPassword(String a){this.wifiPassword = a;}

    public String getWifiPassword(){return  wifiPassword;}

    public Boolean getHasWiFIpassword() {
        return hasWiFIpassword;
    }

    public void setHasWiFIpassword(Boolean hasWiFIpassword) {
        this.hasWiFIpassword = hasWiFIpassword;
    }
}
