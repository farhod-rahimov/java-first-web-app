package edu.school21.cinema.models;

import java.sql.Date;
import java.sql.Time;

public class Authentication {

    private Long authId;

    private Date authDate;
    private Time authTime;
    private String authIp;

    private Long userId;

    public Authentication(Long authId, Date authDate, Time authTime, String authIp, Long userId) {
        this.authId = authId;
        this.authDate = authDate;
        this.authTime = authTime;
        this.authIp = authIp;
        this.userId = userId;
    }

    public Authentication(Date authDate, Time authTime, String authIp, Long userId) {
        this.authDate = authDate;
        this.authTime = authTime;
        this.authIp = authIp;
        this.userId = userId;
    }

    public Long getAuthId() {
        return authId;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public Time getAuthTime() {
        return authTime;
    }

    public String getAuthIp() {
        return authIp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public void setAuthTime(Time authTime) {
        this.authTime = authTime;
    }

    public void setAuthIp(String authIp) {
        this.authIp = authIp;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
