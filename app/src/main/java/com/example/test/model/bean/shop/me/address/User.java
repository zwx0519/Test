package com.example.test.model.bean.shop.me.address;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Id(autoincrement=true)
    private Long mid;
    private String mName;
    private String mPhone;
    private String mCity;
    private String mDetail;
    private boolean aBoolean;
    @Generated(hash = 1471376360)
    public User(Long mid, String mName, String mPhone, String mCity, String mDetail,
            boolean aBoolean) {
        this.mid = mid;
        this.mName = mName;
        this.mPhone = mPhone;
        this.mCity = mCity;
        this.mDetail = mDetail;
        this.aBoolean = aBoolean;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getMid() {
        return this.mid;
    }
    public void setMid(Long mid) {
        this.mid = mid;
    }
    public String getMName() {
        return this.mName;
    }
    public void setMName(String mName) {
        this.mName = mName;
    }
    public String getMPhone() {
        return this.mPhone;
    }
    public void setMPhone(String mPhone) {
        this.mPhone = mPhone;
    }
    public String getMCity() {
        return this.mCity;
    }
    public void setMCity(String mCity) {
        this.mCity = mCity;
    }
    public String getMDetail() {
        return this.mDetail;
    }
    public void setMDetail(String mDetail) {
        this.mDetail = mDetail;
    }
    public boolean getABoolean() {
        return this.aBoolean;
    }
    public void setABoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

}
