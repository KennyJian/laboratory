package com.kenny.laboratory.modular.laboratory.labenum;

public enum ElectrifyEnum {

    OPEN(1,"通电"),
    CLOSE(2,"未通电");

    int code;
    String msg;

    ElectrifyEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(int code){
        for(ElectrifyEnum electrifyEnum:ElectrifyEnum.values()){
            if(electrifyEnum.getCode()==code){
                return electrifyEnum.getMsg();
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
