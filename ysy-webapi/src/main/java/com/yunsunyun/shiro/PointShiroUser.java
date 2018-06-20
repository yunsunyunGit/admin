package com.yunsunyun.shiro;

import com.google.common.base.Objects;

import java.io.Serializable;

public class PointShiroUser implements Serializable {
    private static final long serialVersionUID=-1373760761780840081L;
    public Long id;
    public String phoneNum;
    public String name;
    public PointShiroUser(Long id,String phoneNum,String name){
        this.id=id;
        this.phoneNum=phoneNum;
        this.name=name;
    }
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getPhoneNum(){
        return phoneNum;
    }
    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString(){
        return phoneNum;
    }
    /**
     * 重载hashCode,只计算loginName;
     */
    @Override
    public int hashCode(){
        return Objects.hashCode(phoneNum);
    }
    /**
     * 重载equals,只计算loginName;
     */
    @Override
    public boolean equals(Object obj){
        if(this==obj){
            return true;
        }
        if(obj==null){
            return false;
        }
        if(getClass()!=obj.getClass()){
            return false;
        }
        PointShiroUser other=(PointShiroUser)obj;
        if(phoneNum==null){
            if(other.phoneNum!=null){
                return false;
            }
        }else if(!phoneNum.equals(other.phoneNum)){
            return false;
        }
        return true;
    }
}
