package com.maimai.zz.maimai.litepals;

import org.litepal.crud.DataSupport;

/**
 * Created by 92198 on 2017/10/3.
 */

public class wifiInfo extends DataSupport {
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
