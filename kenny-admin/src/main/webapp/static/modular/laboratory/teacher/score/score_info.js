/**
 * 初始化成绩管理详情对话框
 */
var ScoreInfoDlg = {
    scoreInfoData : {},
    validateFields: {
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
    .set('score');
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
    var ajax = new $ax(Feng.ctxPath + "/teacher/score/update", function(data){
        Feng.success("打分成功!");
        window.parent.Score.table.refresh();
        ScoreInfoDlg.close();
    },function(data){
        Feng.error("打分失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scoreInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("ScoreInfoForm", ScoreInfoDlg.validateFields);
});
