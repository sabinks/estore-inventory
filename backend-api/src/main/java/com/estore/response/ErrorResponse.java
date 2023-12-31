package com.estore.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private Integer status;
    private String message;
    private String timeStamp;

    public void setStatus(Integer status) {
        this.status = status;
    }
}
