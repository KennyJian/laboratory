/**
 * 初始化实验选课管理详情对话框
 */
var ApplyExperimentInfoDlg = {
    applyExperimentInfoData : {},
    validateFields: {
            experimentId: {
                validators: {
                    notEmpty: {
                        message: '实验id不能为空'
                    }
                }
            },
            studentName: {
                validators: {
                    notEmpty: {
                        message: '学生名不能为空'
                    }
                }
            },
            experimentName: {
                validators: {
                    notEmpty: {
                        message: '实验名不能为空'
                    }
                }
            },
            status: {
                validators: {
                    notEmpty: {
                        message: '状态'
                    }
                }
            },
            studentId: {
                validators: {
                    notEmpty: {
                        message: '学生id不能为空'
                    }
                }
            },
            teacherId: {
                validators: {
                    notEmpty: {
                        message: '老师id不能为空'
                    }
                }
            }
    }
};

/**
 * 验证数据是否为空
 */
ApplyExperimentInfoDlg.validate = function () {
    $('#ApplyExperimentInfoForm').data("bootstrapValidator").resetForm();
    $('#ApplyExperimentInfoForm').bootstrapValidator('validate');
    return $("#ApplyExperimentInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
ApplyExperimentInfoDlg.clearData = function() {
    this.applyExperimentInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ApplyExperimentInfoDlg.set = function(key, val) {
    this.applyExperimentInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ApplyExperimentInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ApplyExperimentInfoDlg.close = function() {
    parent.layer.close(window.parent.ApplyExperiment.layerIndex);
}

/**
 * 收集数据
 */
ApplyExperimentInfoDlg.collectData = function() {
    this
    .set('id')
    .set('experimentId')
    .set('studentName')
    .set('experimentName')
    .set('status')
    .set('studentId')
    .set('teacherId');
}

/**
 * 提交添加
 */
ApplyExperimentInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/applyExperiment/add", function(data){
        Feng.success("添加成功!");
        window.parent.ApplyExperiment.table.refresh();
        ApplyExperimentInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.applyExperimentInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ApplyExperimentInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/applyExperiment/update", function(data){
        Feng.success("修改成功!");
        window.parent.ApplyExperiment.table.refresh();
        ApplyExperimentInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.applyExperimentInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("ApplyExperimentInfoForm", ApplyExperimentInfoDlg.validateFields);
});
