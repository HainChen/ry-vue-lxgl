package com.ruoyi.lxgl.domain;

/**
 * @ClassName LxhjHandleStatus
 * @Description 各环节办理状态
 * @Author hainc
 * @Date 2023/3/21 20:47
 * @Version 1.0
 **/
public class LxhjHandleStatus {
    //财务处办理状态
    private Integer cwcFinished;
    private Integer cwcIncomplete;

    //教务处办理状态
    private Integer jwcFinished;
    private Integer jwcIncomplete;

    //宿舍办理状态
    private Integer dormitoryFinished;
    private Integer dormitoryIncomplete;

    //图书馆办理状态
    private Integer tsgFinished;
    private Integer tsgIncomplete;

    public LxhjHandleStatus() {
    }

    public LxhjHandleStatus(Integer cwcFinished, Integer cwcIncomplete, Integer jwcFinished, Integer jwcIncomplete, Integer dormitoryFinished, Integer dormitoryIncomplete, Integer tsgFinished, Integer tsgIncomplete) {
        this.cwcFinished = cwcFinished;
        this.cwcIncomplete = cwcIncomplete;
        this.jwcFinished = jwcFinished;
        this.jwcIncomplete = jwcIncomplete;
        this.dormitoryFinished = dormitoryFinished;
        this.dormitoryIncomplete = dormitoryIncomplete;
        this.tsgFinished = tsgFinished;
        this.tsgIncomplete = tsgIncomplete;
    }

    public Integer getCwcFinished() {
        return cwcFinished;
    }

    public void setCwcFinished(Integer cwcFinished) {
        this.cwcFinished = cwcFinished;
    }

    public Integer getCwcIncomplete() {
        return cwcIncomplete;
    }

    public void setCwcIncomplete(Integer cwcIncomplete) {
        this.cwcIncomplete = cwcIncomplete;
    }

    public Integer getJwcFinished() {
        return jwcFinished;
    }

    public void setJwcFinished(Integer jwcFinished) {
        this.jwcFinished = jwcFinished;
    }

    public Integer getJwcIncomplete() {
        return jwcIncomplete;
    }

    public void setJwcIncomplete(Integer jwcIncomplete) {
        this.jwcIncomplete = jwcIncomplete;
    }

    public Integer getDormitoryFinished() {
        return dormitoryFinished;
    }

    public void setDormitoryFinished(Integer dormitoryFinished) {
        this.dormitoryFinished = dormitoryFinished;
    }

    public Integer getDormitoryIncomplete() {
        return dormitoryIncomplete;
    }

    public void setDormitoryIncomplete(Integer dormitoryIncomplete) {
        this.dormitoryIncomplete = dormitoryIncomplete;
    }

    public Integer getTsgFinished() {
        return tsgFinished;
    }

    public void setTsgFinished(Integer tsgFinished) {
        this.tsgFinished = tsgFinished;
    }

    public Integer getTsgIncomplete() {
        return tsgIncomplete;
    }

    public void setTsgIncomplete(Integer tsgIncomplete) {
        this.tsgIncomplete = tsgIncomplete;
    }

    @Override
    public String toString() {
        return "LxhjHandleStatus{" +
                "cwcFinished=" + cwcFinished +
                ", cwcIncomplete=" + cwcIncomplete +
                ", jwcFinished=" + jwcFinished +
                ", jwcIncomplete=" + jwcIncomplete +
                ", dormitoryFinished=" + dormitoryFinished +
                ", dormitoryIncomplete=" + dormitoryIncomplete +
                ", tsgFinished=" + tsgFinished +
                ", tsgIncomplete=" + tsgIncomplete +
                '}';
    }
}
