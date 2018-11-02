/**
 * 初始化实验管理详情对话框
 */
var ExperimentInfoDlg = {
    experimentInfoData : {},
    validateFields: {
            experimentName: {
                validators: {
                    notEmpty: {
                        message: '实验名不能为空'
                    }
                }
            },
            courseId: {
                validators: {
                    notEmpty: {
                        message: '课程号不能为空'
                    }
                }
            },
            courseNum: {
                validators: {
                    notEmpty: {
                        message: '实验课时不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '只能为整数'
                    }
                }
            },
            equipmentNeedNum: {
                validators: {
                    notEmpty: {
                        message: '实验所需设备不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '只能为整数'
                    }
                }
            }
    }
};

/**
 * 验证数据是否为空
 */
ExperimentInfoDlg.validate = function () {
    $('#ExperimentInfoForm').data("bootstrapValidator").resetForm();
    $('#ExperimentInfoForm').bootstrapValidator('validate');
    return $("#ExperimentInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
ExperimentInfoDlg.clearData = function() {
    this.experimentInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExperimentInfoDlg.set = function(key, val) {
    this.experimentInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExperimentInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ExperimentInfoDlg.close = function() {
    parent.layer.close(window.parent.Experiment.layerIndex);
}

/**
 * 收集数据
 */
ExperimentInfoDlg.collectData = function() {
    this
    .set('experimentId')
    .set('experimentName')
    .set('courseId')
    .set('courseNum')
    .set('equipmentNeedNum')
}

/**
 * 提交添加
 */
ExperimentInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teacher/experiment/add", function(data){
        Feng.success("添加成功!");
        window.parent.Experiment.table.refresh();
        ExperimentInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.experimentInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ExperimentInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teacher/experiment/update", function(data){
        Feng.success("修改成功!");
        window.parent.Experiment.table.refresh();
        ExperimentInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.experimentInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("ExperimentInfoForm", ExperimentInfoDlg.validateFields);
});
