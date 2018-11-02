/**
 * 初始化成绩管理详情对话框
 */
var ScoreInfoDlg = {
    scoreInfoData : {},
    validateFields: {
            studentId: {
                validators: {
                    notEmpty: {
                        message: '学生id不能为空'
                    }
                }
            },
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
            attendanceNum: {
                validators: {
                    notEmpty: {
                        message: '到勤数不能为空'
                    }
                }
            },
            score: {
                validators: {
                    notEmpty: {
                        message: '成绩不能为空'
                    },
                    between: {
                        min: 0,
                        max: 100,
                        message: '成绩在0-100范围'
                    }
                }
            },
            teacherName: {
                validators: {
                    notEmpty: {
                        message: '老师名字不能为空'
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
ScoreInfoDlg.validate = function () {
    $('#ScoreInfoForm').data("bootstrapValidator").resetForm();
    $('#ScoreInfoForm').bootstrapValidator('validate');
    return $("#ScoreInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
ScoreInfoDlg.clearData = function() {
    this.scoreInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScoreInfoDlg.set = function(key, val) {
    this.scoreInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScoreInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ScoreInfoDlg.close = function() {
    parent.layer.close(window.parent.Score.layerIndex);
}

/**
 * 收集数据
 */
ScoreInfoDlg.collectData = function() {
    this
    .set('id')
    .set('studentId')
    .set('experimentId')
    .set('studentName')
    .set('experimentName')
    .set('attendanceNum')
    .set('score')
    .set('teacherName')
    .set('teacherId');
}

/**
 * 提交添加
 */
ScoreInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/score/add", function(data){
        Feng.success("添加成功!");
        window.parent.Score.table.refresh();
        ScoreInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scoreInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ScoreInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/score/update", function(data){
        Feng.success("修改成功!");
        window.parent.Score.table.refresh();
        ScoreInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scoreInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("ScoreInfoForm", ScoreInfoDlg.validateFields);
});
