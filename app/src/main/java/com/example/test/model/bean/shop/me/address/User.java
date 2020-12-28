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
    private String mAddress;
    private boolean aBoolean;
    @Generated(hash = 1405732797)
    public User(Long mid, String mName, String mPhone, String mAddress,
            boolean aBoolean) {
        this.mid = mid;
        this.mName = mName;
        this.mPhone = mPhone;
        this.mAddress = mAddress;
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
    public String getMAddress() {
        return this.mAddress;
    }
    public void setMAddress(String mAddress) {
        this.mAddress = mAddress;
    }
    public boolean getABoolean() {
        return this.aBoolean;
    }
    public void setABoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

}
