package com.practice.shop.dto;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/7/29 18:08
 * @Description:
 **/

public class ResponseDTO {
    private Boolean success;
    private String message;
    private Object data;

    public ResponseDTO(){}

    public ResponseDTO(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
