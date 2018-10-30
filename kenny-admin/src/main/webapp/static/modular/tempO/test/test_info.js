/**
 * 初始化测试数据范围详情对话框
 */
var TestInfoDlg = {
    testInfoData : {},
    validateFields: {
            orderName: {
                validators: {
                    notEmpty: {
                        message: '订单名不能为空'
                    }
                }
            },
            orderNum: {
                validators: {
                    notEmpty: {
                        message: '订单数量不能为空'
                    }
                }
            },
            orderTo: {
                validators: {
                    notEmpty: {
                        message: '订单发往地不能为空'
                    }
                }
            }
    }
};

/**
 * 验证数据是否为空
 */
TestInfoDlg.validate = function () {
    $('#TestInfoForm').data("bootstrapValidator").resetForm();
    $('#TestInfoForm').bootstrapValidator('validate');
    return $("#TestInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
TestInfoDlg.clearData = function() {
    this.testInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TestInfoDlg.set = function(key, val) {
    this.testInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TestInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TestInfoDlg.close = function() {
    parent.layer.close(window.parent.Test.layerIndex);
}

/**
 * 收集数据
 */
TestInfoDlg.collectData = function() {
    this
    .set('id')
    .set('orderName')
    .set('orderNum')
    .set('orderTo')
    .set('deptid');
}

/**
 * 提交添加
 */
TestInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/test/add", function(data){
        Feng.success("添加成功!");
        window.parent.Test.table.refresh();
        TestInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.testInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TestInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/test/update", function(data){
        Feng.success("修改成功!");
        window.parent.Test.table.refresh();
        TestInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.testInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("TestInfoForm", TestInfoDlg.validateFields);
});
