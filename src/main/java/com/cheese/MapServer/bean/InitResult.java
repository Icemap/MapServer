package com.cheese.MapServer.bean;

public class InitResult
{
    private Boolean success;
    private String type;
    private Integer level;
    private Object errorList;

    public Boolean getSuccess()
    {
        return success;
    }

    public void setSuccess(Boolean success)
    {
        this.success = success;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Integer getLevel()
    {
        return level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    public Object getErrorList() {
        return errorList;
    }

    public void setErrorList(Object errorList) {
        this.errorList = errorList;
    }
}
