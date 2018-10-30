/**
 * 初始化测试验证详情对话框
 */
var ValidatorInfoDlg = {
    validatorInfoData : {},
    validateFields: {
            orderName: {
                validators: {
                    notEmpty: {
                        message: '订单名字不能为空'
                    }
                }
            },
            orderFrom: {
                validators: {
                    notEmpty: {
                        message: '订单发货地不能为空'
                    }
                }
            },
            orderLeft: {
                validators: {
                    notEmpty: {
                        message: '订单剩余量不能为空'
                    },
                    digits: {
                        message: '该值只能包含数字。'
                    }
                }
            }
    }
};

/**
 * 验证数据是否为空
 */
ValidatorInfoDlg.validate = function () {
    $('#ValidatorInfoForm').data("bootstrapValidator").resetForm();
    $('#ValidatorInfoForm').bootstrapValidator('validate');
    return $("#ValidatorInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
ValidatorInfoDlg.clearData = function() {
    this.validatorInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ValidatorInfoDlg.set = function(key, val) {
    this.validatorInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ValidatorInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ValidatorInfoDlg.close = function() {
    parent.layer.close(window.parent.Validator.layerIndex);
}

/**
 * 收集数据
 */
ValidatorInfoDlg.collectData = function() {
    this
    .set('id')
    .set('orderName')
    .set('orderFrom')
    .set('orderLeft');
}

/**
 * 提交添加
 */
ValidatorInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/validator/add", function(data){
        Feng.success("添加成功!");
        window.parent.Validator.table.refresh();
        ValidatorInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.validatorInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ValidatorInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/validator/update", function(data){
        Feng.success("修改成功!");
        window.parent.Validator.table.refresh();
        ValidatorInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.validatorInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("ValidatorInfoForm", ValidatorInfoDlg.validateFields);
});
