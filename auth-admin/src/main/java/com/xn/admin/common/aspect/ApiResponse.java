package com.xn.admin.common.aspect;

import com.xn.common.utils.RepCode;
import java.io.Serializable;

/**
 * @Date 2019/1/3 17:57
 * @Author LHS
 * @ClassName ApiResponse
 * @Description :...
 */
public class ApiResponse implements Serializable {
    private static final long serialVersionUID = 1716942231037185421L;

    private int result;
    private String msg;
    private Object data;

    private ApiResponse() {
        this.result = RepCode.SUCCESS.getCode();
        this.msg = RepCode.SUCCESS.getMsg();
    }

    public static ApiResponse error() {
        return error(RepCode.ERROR_1001);
    }

    public static ApiResponse error(String msg) {
        return error(RepCode.ERROR_1001.getCode(), msg);
    }

    public static ApiResponse error(int code, String msg) {
        ApiResponse r = new ApiResponse();
        r.result = code;
        r.msg = msg;
        return r;
    }

    public static ApiResponse error(int code, String msg, Object data) {
        ApiResponse r = new ApiResponse();
        r.result = code;
        r.msg = msg;
        r.data = data;
        return r;
    }

    public static ApiResponse error(RepCode repCode) {
        ApiResponse r = new ApiResponse();
        r.result = repCode.getCode();
        r.msg = repCode.getMsg();
        return r;
    }

    public static ApiResponse error(RepCode repCode, Object data) {
        ApiResponse r = new ApiResponse();
        r.result = repCode.getCode();
        r.msg = repCode.getMsg();
        r.data = data;
        return r;
    }

    public static ApiResponse ok() {
        return new ApiResponse();
    }

    public static ApiResponse ok(Object data) {
        ApiResponse r = new ApiResponse();
        r.data = data;
        return r;
    }

    public static ApiResponse ok(String msg, Object data) {
        ApiResponse r = new ApiResponse();
        r.msg = msg;
        r.data = data;
        return r;
    }

    public int getResult() {
        return this.result;
    }

    public String getMsg() {
        return this.msg;
    }

    public Object getData() {
        return this.data;
    }
}

