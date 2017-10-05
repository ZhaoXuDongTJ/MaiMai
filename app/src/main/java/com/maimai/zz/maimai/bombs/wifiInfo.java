package com.maimai.zz.maimai.bombs;

import cn.bmob.v3.BmobObject;

/**
 * Created by 92198 on 2017/10/4.
 */

public class wifiInfo extends BmobObject {
    private Integer member;
    private Integer energy;


    public Integer getMember() {
        return member;
    }
    public void setMember(Integer member) {
        this.member = member;
    }
    public Integer getEnergy() {
        return energy;
    }
    public void setEnergy(Integer energy) {
        this.energy = energy;
    }
}
