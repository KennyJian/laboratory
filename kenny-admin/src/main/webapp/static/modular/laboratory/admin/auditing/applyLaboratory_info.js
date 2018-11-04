/**
 * 初始化实验室申请管理详情对话框
 */
var ApplyLaboratoryInfoDlg = {
    applyLaboratoryInfoData : {},
    validateFields: {
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
    .set('comment')

}


/**
 * 提交备注
 */
ApplyLaboratoryInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/admin/auditingLaboratory/setComment", function(data){
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
