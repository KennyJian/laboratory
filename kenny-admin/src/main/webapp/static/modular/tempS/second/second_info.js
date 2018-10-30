/**
 * 初始化测试第二模板详情对话框
 */
var SecondInfoDlg = {
    secondInfoData : {}
};

/**
 * 清除数据
 */
SecondInfoDlg.clearData = function() {
    this.secondInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SecondInfoDlg.set = function(key, val) {
    this.secondInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SecondInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SecondInfoDlg.close = function() {
    parent.layer.close(window.parent.Second.layerIndex);
}

/**
 * 收集数据
 */
SecondInfoDlg.collectData = function() {
    this
    .set('id')
    .set('goods')
    .set('quality');
}

/**
 * 提交添加
 */
SecondInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/second/add", function(data){
        Feng.success("添加成功!");
        window.parent.Second.table.refresh();
        SecondInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.secondInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SecondInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/second/update", function(data){
        Feng.success("修改成功!");
        window.parent.Second.table.refresh();
        SecondInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.secondInfoData);
    ajax.start();
}

$(function() {

});
