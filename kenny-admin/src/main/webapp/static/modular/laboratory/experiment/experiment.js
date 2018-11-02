/**
 * 实验管理管理初始化
 */
var Experiment = {
    id: "ExperimentTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Experiment.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'experimentId', visible: false, align: 'center', valign: 'middle'},
            {title: '实验名', field: 'experimentName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '课程号', field: 'courseId', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '实验课时', field: 'courseNum', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '实验所需设备', field: 'equipmentNeedNum', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '老师用户ID', field: 'teacherId', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
Experiment.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Experiment.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加实验管理
 */
Experiment.openAddExperiment = function () {
    var index = layer.open({
        type: 2,
        title: '添加实验管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/experiment/experiment_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看实验管理详情
 */
Experiment.openExperimentDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '实验管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/experiment/experiment_update/' + Experiment.seItem.experimentId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除实验管理
 */
Experiment.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/experiment/delete", function (data) {
            Feng.success("删除成功!");
            Experiment.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("experimentId",this.seItem.experimentId);
        ajax.start();
    }
};

/**
 * 查询实验管理列表
 */
Experiment.search = function () {
    var queryData = {};
        queryData['experimentId'] = $("#experimentId").val();
        queryData['experimentName'] = $("#experimentName").val();
        queryData['courseId'] = $("#courseId").val();
        queryData['courseNum'] = $("#courseNum").val();
        queryData['equipmentNeedNum'] = $("#equipmentNeedNum").val();
        queryData['teacherId'] = $("#teacherId").val();
    Experiment.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Experiment.initColumn();
    var table = new BSTable(Experiment.id, "/experiment/list", defaultColunms);
    table.setPaginationType("client");
    Experiment.table = table.init();
});
