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
            {title: '审核状态', field: 'status', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '授课老师', field: 'teacherName', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};


/**
 * 查询实验选课管理列表
 */
ApplyExperiment.search = function () {
    var queryData = {};
        queryData['id'] = $("#id").val();
        queryData['experimentName'] = $("#experimentName").val();
        queryData['status'] = $("#status").val();
        queryData['teacherName'] = $("#teacherName").val();
    ApplyExperiment.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ApplyExperiment.initColumn();
    var table = new BSTable(ApplyExperiment.id, "/student/applydetail/list", defaultColunms);
    table.setPaginationType("client");
    ApplyExperiment.table = table.init();
});
