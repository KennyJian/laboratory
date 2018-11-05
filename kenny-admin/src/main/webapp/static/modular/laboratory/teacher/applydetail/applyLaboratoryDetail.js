/**
 * 实验室申请管理管理初始化
 */
var ApplyLaboratory = {
    id: "ApplyLaboratoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ApplyLaboratory.initColumn = function () {
    return [
        {field: 'selectItem', radio: false},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '实验名', field: 'experimentName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '实验室名', field: 'laboratoryName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '申请使用时间', field: 'applyBeginTime', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '申请结束时间', field: 'applyEndTime', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '审核状态', field: 'status', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '备注', field: 'comment', visible: true, align: 'center', valign: 'middle', sortable: true},
    ];
};


/**
 * 查询实验室申请管理列表
 */
ApplyLaboratory.search = function () {
    var queryData = {};
        queryData['experimentName'] = $("#experimentName").val();
        queryData['laboratoryName'] = $("#laboratoryName").val();
        queryData['applyBeginTime'] = $("#applyBeginTime").val();
        queryData['applyEndTime'] = $("#applyEndTime").val();
        queryData['status'] = $("#status").val();
    ApplyLaboratory.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ApplyLaboratory.initColumn();
    var table = new BSTable(ApplyLaboratory.id, "/teacher/applydetail/list", defaultColunms);
    table.setPaginationType("client");
    ApplyLaboratory.table = table.init();
});
