package com.cheese.MapServer.bean;

import com.cheese.MapServer.utils.BackgroundType;

/**
 * @author Icemap
 * @date 2019/4/28 5:00 PM
 */
public class ThreadReqParamInfo {
    private BackgroundType type;
    private Integer x;
    private Integer y;
    private Integer z;

    public ThreadReqParamInfo() {
    }

    public ThreadReqParamInfo(BackgroundType type, Integer x, Integer y, Integer z) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BackgroundType getType() {
        return type;
    }

    public void setType(BackgroundType type) {
        this.type = type;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }
}
