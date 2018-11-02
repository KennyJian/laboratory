package com.kenny.laboratory.modular.laboratory.labenum;

public enum OpenEnum {

    OPEN(1,"开放"),
    CLOSE(2,"未开放");

    int code;
    String msg;

    OpenEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(int code){
        for(OpenEnum openEnum:OpenEnum.values()){
            if(openEnum.getCode()==code){
                return openEnum.getMsg();
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
