/**
 * 初始化实验室申请管理详情对话框
 */
var ApplyLaboratoryInfoDlg = {
    applyLaboratoryInfoData : {},
    validateFields: {
            experimentId: {
                validators: {
                    notEmpty: {
                        message: '申请的实验id不能为空'
                    }
                }
            },
            laboratoryId: {
                validators: {
                    notEmpty: {
                        message: '申请的实验室id不能为空'
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
            laboratoryName: {
                validators: {
                    notEmpty: {
                        message: '实验室名不能为空'
                    }
                }
            },
            applyBeginTime: {
                validators: {
                    notEmpty: {
                        message: '申请使用时间不能为空'
                    },
                    date: {
                        message: '必须时间类型'
                    }
                }
            },
            applyEndTime: {
                validators: {
                    notEmpty: {
                        message: '申请结束时间不能为空'
                    },
                    date: {
                        message: '必须时间类型'
                    }
                }
            },
            status: {
                validators: {
                    notEmpty: {
                        message: '状态 0:待审核 1:成功 2:失败不能为空'
                    }
                }
            },
            comment: {
                validators: {
                    notEmpty: {
                        message: '备注不能为空'
                    }
                }
            },
            teacherId: {
                validators: {
                    notEmpty: {
                        message: '老师ID不能为空'
                    }
                }
            }
    }
};

/**
 * 验证数据是否为空
 */
ApplyLaboratoryInfoDlg.validate = function () {
    $('#ApplyLaboratoryInfoForm').data("bootstrapValidator").resetForm();
    $('#ApplyLaboratoryInfoForm').bootstrapValidator('validate');
    return $("#ApplyLaboratoryInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
ApplyLaboratoryInfoDlg.clearData = function() {
    this.applyLaboratoryInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ApplyLaboratoryInfoDlg.set = function(key, val) {
    this.applyLaboratoryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ApplyLaboratoryInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ApplyLaboratoryInfoDlg.close = function() {
    parent.layer.close(window.parent.ApplyLaboratory.layerIndex);
}

/**
 * 收集数据
 */
ApplyLaboratoryInfoDlg.collectData = function() {
    this
    .set('id')
    .set('experimentId')
    .set('laboratoryId')
    .set('experimentName')
    .set('laboratoryName')
    .set('applyBeginTime')
    .set('applyEndTime')
    .set('status')
    .set('comment')
    .set('teacherId');
}

/**
 * 提交添加
 */
ApplyLaboratoryInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/applyLaboratory/add", function(data){
        Feng.success("添加成功!");
        window.parent.ApplyLaboratory.table.refresh();
        ApplyLaboratoryInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.applyLaboratoryInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ApplyLaboratoryInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/applyLaboratory/update", function(data){
        Feng.success("修改成功!");
        window.parent.ApplyLaboratory.table.refresh();
        ApplyLaboratoryInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.applyLaboratoryInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("ApplyLaboratoryInfoForm", ApplyLaboratoryInfoDlg.validateFields);
});
