/**
 * 实验选课管理管理初始化
 */
var ApplyExperiment = {
    id: "ApplyExperimentTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ApplyExperiment.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '实验名', field: 'experimentName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '学生名', field: 'studentName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '审核状态', field: 'status', visible: true, align: 'center', valign: 'middle', sortable: true},
    ];
};

/**
 * 检查是否选中
 */
ApplyExperiment.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ApplyExperiment.seItem = selected[0];
        return true;
    }
};

/**
 * 通过实验选课
 */
ApplyExperiment.success = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/teacher/auditing/success", function (data) {
            Feng.success("审核成功!");
            ApplyExperiment.table.refresh();
        }, function (data) {
            Feng.error("审核失败!" + data.responseJSON.message + "!");
        });
        ajax.set("applyExperimentId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 不通过实验选课
 */
ApplyExperiment.fail = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/teacher/auditing/fail", function (data) {
            Feng.success("审核成功!");
            ApplyExperiment.table.refresh();
        }, function (data) {
            Feng.error("审核失败!" + data.responseJSON.message + "!");
        });
        ajax.set("applyExperimentId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询实验选课管理列表
 */
ApplyExperiment.search = function () {
    var queryData = {};
        queryData['id'] = $("#id").val();
        queryData['experimentId'] = $("#experimentId").val();
        queryData['studentName'] = $("#studentName").val();
        queryData['experimentName'] = $("#experimentName").val();
        queryData['status'] = $("#status").val();
        queryData['studentId'] = $("#studentId").val();
        queryData['teacherId'] = $("#teacherId").val();
    ApplyExperiment.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ApplyExperiment.initColumn();
    var table = new BSTable(ApplyExperiment.id, "/teacher/auditing/list", defaultColunms);
    table.setPaginationType("client");
    ApplyExperiment.table = table.init();
});
