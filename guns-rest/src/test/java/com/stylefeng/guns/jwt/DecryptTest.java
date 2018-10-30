package com.stylefeng.guns.jwt;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.SimpleObject;
import com.stylefeng.guns.rest.modular.auth.converter.BaseTransferEntity;
import com.stylefeng.guns.rest.modular.auth.security.impl.Base64SecurityAction;

/**
 * jwt测试
 *
 * @author fengshuonan
 * @date 2017-08-21 16:34
 */
public class DecryptTest {

    public static void main(String[] args) {

        String salt = "3nhagm";

        SimpleObject simpleObject = new SimpleObject();
        simpleObject.setUser("kenny");
        simpleObject.setAge(12);
        simpleObject.setName("jian");
        simpleObject.setTips("code");

        String jsonString = JSON.toJSONString(simpleObject); //发送数据传json
        String encode = new Base64SecurityAction().doAction(jsonString); //进行BASE64编码
        String md5 = MD5Util.encrypt(encode + salt); //进行md5加密

        BaseTransferEntity baseTransferEntity = new BaseTransferEntity();
        baseTransferEntity.setObject(encode);
        baseTransferEntity.setSign(md5);

        System.out.println(JSON.toJSONString(baseTransferEntity));
    }
}
