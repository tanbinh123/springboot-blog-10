package org.xjt.blog.utils;

import lombok.Data;
import java.io.Serializable;

@Data
public class Result implements Serializable {
    private String code;
    private String msg;
    private Object data;

    public static Result succ(Object data) {
        Result m = new Result();
        m.setCode("0");
        m.setData(data);
        m.setMsg("操作成功");
        return m;
    }
    public static Result succ(String msg, Object data) {
        Result m = new Result();
        m.setCode("0");
        m.setData(data);
        m.setMsg(msg);
        return m;
    }
    public static Result fail(String msg) {
        Result m = new Result();
        m.setCode("-1");
        m.setData(null);
        m.setMsg(msg);
        return m;
    }
    public static Result fail(String msg, Object data) {
        Result m = new Result();
        m.setCode("-1");
        m.setData(data);
        m.setMsg(msg);
        return m;
    }
}
