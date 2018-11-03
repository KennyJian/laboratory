/**
 * 初始化实验室管理详情对话框
 */
var LaboratoryInfoDlg = {
    laboratoryInfoData : {},
    validateFields: {
            laboratoryName: {
                validators: {
                    notEmpty: {
                        message: '实验室名字不能为空'
                    }
                }
            },
            laboratoryLocation: {
                validators: {
                    notEmpty: {
                        message: '实验室地点不能为空'
                    }
                }
            },
            laboratoryEquipmentNum: {
                validators: {
                    notEmpty: {
                        message: '实验室设备数不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '只能为整数'
                    }
                }
            },
            isOpen: {
                validators: {
                    notEmpty: {
                        message: '实验室是否开放'
                    }
                }
            },
            isElectrify: {
                validators: {
                    notEmpty: {
                        message: '实验室是否通电'
                    }
                }
            }
    }
};

/**
 * 验证数据是否为空
 */
LaboratoryInfoDlg.validate = function () {
    $('#LaboratoryInfoForm').data("bootstrapValidator").resetForm();
    $('#LaboratoryInfoForm').bootstrapValidator('validate');
    return $("#LaboratoryInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
LaboratoryInfoDlg.clearData = function() {
    this.laboratoryInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
LaboratoryInfoDlg.set = function(key, val) {
    this.laboratoryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
LaboratoryInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
LaboratoryInfoDlg.close = function() {
    parent.layer.close(window.parent.Laboratory.layerIndex);
}

/**
 * 收集数据
 */
LaboratoryInfoDlg.collectData = function() {
    this
    .set('laboratoryId')
    .set('laboratoryName')
    .set('laboratoryLocation')
    .set('laboratoryEquipmentNum')
    .set('isOpen')
    .set('isElectrify');
}

/**
 * 提交申请
 */
LaboratoryInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teacher/laboratory/apply", function(data){
        Feng.success("申请成功!");
        window.parent.Laboratory.table.refresh();
        LaboratoryInfoDlg.close();
    },function(data){
        Feng.error("申请失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.laboratoryInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("LaboratoryInfoForm", LaboratoryInfoDlg.validateFields);
});
