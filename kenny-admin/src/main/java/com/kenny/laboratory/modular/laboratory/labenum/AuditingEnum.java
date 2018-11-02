package com.kenny.laboratory.modular.laboratory.labenum;

public enum AuditingEnum {

    WAIT(0,"待审核"),
    SUCCESS(1,"成功"),
    FAIL(2,"失败");

    int code;
    String msg;

    AuditingEnum(int code, String msg) {
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
