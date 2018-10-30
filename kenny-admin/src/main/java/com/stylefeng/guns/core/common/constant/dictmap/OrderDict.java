package com.stylefeng.guns.core.common.constant.dictmap;

import com.stylefeng.guns.core.common.constant.dictmap.base.AbstractDictMap;

/**
 * 用户的字典
 *
 * @author fengshuonan
 * @date 2017-05-06 15:01
 */
public class OrderDict extends AbstractDictMap {

    @Override
    public void init() {
        put("myOrderId","订单id");
        put("user","下单人名称");
        put("place","地点");
        put("goods","商品名称");
        put("createtime","下单时间");
    }

    @Override
    protected void initBeWrapped() {

    }
}
