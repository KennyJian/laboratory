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
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '实验名', field: 'experimentName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '实验室地点', field: 'laboratoryName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '申请使用时间', field: 'applyBeginTime', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '申请结束时间', field: 'applyEndTime', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '通电情况', field: 'isElectrify', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
ApplyLaboratory.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ApplyLaboratory.seItem = selected[0];
        return true;
    }
};

ApplyLaboratory.openApplyLaboratoryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '实验室申请管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/teacher/sign/sign_in/'+ ApplyLaboratory.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 查询实验室申请管理列表
 */
ApplyLaboratory.search = function () {
    var queryData = {};
        queryData['experimentName'] = $("#experimentName").val();
        queryData['laboratoryName'] = $("#laboratoryName").val();
    ApplyLaboratory.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ApplyLaboratory.initColumn();
    var table = new BSTable(ApplyLaboratory.id, "/teacher/sign/list", defaultColunms);
    table.setPaginationType("client");
    ApplyLaboratory.table = table.init();
});
