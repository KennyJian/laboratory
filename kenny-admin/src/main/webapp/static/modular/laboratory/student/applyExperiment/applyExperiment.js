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
            {title: '实验id', field: 'experimentId', visible: false, align: 'center', valign: 'middle', sortable: true},
            {title: '实验名', field: 'experimentName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '课程号', field: 'courseId', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '课程数', field: 'courseNum', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '授课老师', field: 'teacherName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '老师ID', field: 'teacherId', visible: false, align: 'center', valign: 'middle', sortable: true}
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
 * 申请实验
 */
ApplyExperiment.apply = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/student/apply/apply", function (data) {
            Feng.success("申请成功!");
            ApplyExperiment.table.refresh();
        }, function (data) {
            Feng.error("申请失败!" + data.responseJSON.message + "!");
        });
        ajax.set("experimentId",this.seItem.experimentId);
        ajax.set("experimentName",this.seItem.experimentName);
        ajax.set("teacherId",this.seItem.teacherId);
        ajax.start();
    }
};

/**
 * 查询实验选课管理列表
 */
ApplyExperiment.search = function () {
    var queryData = {};
        queryData['experimentName'] = $("#experimentName").val();
    ApplyExperiment.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ApplyExperiment.initColumn();
    var table = new BSTable(ApplyExperiment.id, "/student/apply/list", defaultColunms);
    table.setPaginationType("client");
    ApplyExperiment.table = table.init();
});
