/**
 * 初始化测试详情对话框
 */
var UseInfoDlg = {
    useInfoData : {},
    validateFields: {
            good: {
                validators: {
                    notEmpty: {
                        message: '货物不能为空'
                    }
                }
            },
            papap: {
                validators: {
                    notEmpty: {
                        message: '怕怕不能为空'
                    }
                }
            }
    }
};

/**
 * 验证数据是否为空
 */
UseInfoDlg.validate = function () {
    $('#UseInfoForm').data("bootstrapValidator").resetForm();
    $('#UseInfoForm').bootstrapValidator('validate');
    return $("#UseInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
UseInfoDlg.clearData = function() {
    this.useInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UseInfoDlg.set = function(key, val) {
    this.useInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UseInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UseInfoDlg.close = function() {
    parent.layer.close(window.parent.Use.layerIndex);
}

/**
 * 收集数据
 */
UseInfoDlg.collectData = function() {
    this
    .set('id')
    .set('good')
    .set('papap');
}

/**
 * 提交添加
 */
UseInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/use/add", function(data){
        Feng.success("添加成功!");
        window.parent.Use.table.refresh();
        UseInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.useInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UseInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/use/update", function(data){
        Feng.success("修改成功!");
        window.parent.Use.table.refresh();
        UseInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.useInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("UseInfoForm", UseInfoDlg.validateFields);
});
