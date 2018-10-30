/**
 * 初始化订单管理详情对话框
 */
var MyOrderInfoDlg = {
    myOrderInfoData : {},
    validateFields: {
        user: {
            validators: {
                notEmpty: {
                    message: '下单人名称不能为空'
                }
            }
        },
        place: {
            validators: {
                notEmpty: {
                    message: '地点不能为空'
                }
            }
        },
        goods: {
            validators: {
                notEmpty: {
                    message: '商品名称不能为空'
                }
            }
        },
        createtime: {
            validators: {
                notEmpty: {
                    message: '下单时间不能为空'
                }
            }
        },
    }
};

/**
 * 验证数据是否为空
 */
MyOrderInfoDlg.validate = function () {
    $('#MyOrderInfoForm').data("bootstrapValidator").resetForm();
    $('#MyOrderInfoForm').bootstrapValidator('validate');
    return $("#MyOrderInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
MyOrderInfoDlg.clearData = function() {
    this.myOrderInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MyOrderInfoDlg.set = function(key, val) {
    this.myOrderInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MyOrderInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MyOrderInfoDlg.close = function() {
    parent.layer.close(window.parent.MyOrder.layerIndex);
}

/**
 * 收集数据
 */
MyOrderInfoDlg.collectData = function() {
    this
    .set('id')
    .set('user')
    .set('place')
    .set('goods')
    .set('createtime');
}

/**
 * 提交添加
 */
MyOrderInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/myOrder/add", function(data){
        Feng.success("添加成功!");
        window.parent.MyOrder.table.refresh();
        MyOrderInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.myOrderInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MyOrderInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/myOrder/update", function(data){
        Feng.success("修改成功!");
        window.parent.MyOrder.table.refresh();
        MyOrderInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.myOrderInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("MyOrderInfoForm", MyOrderInfoDlg.validateFields);
});
