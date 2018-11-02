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
            {title: '申请的实验id', field: 'experimentId', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '申请的实验室id', field: 'laboratoryId', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '实验名', field: 'experimentName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '实验室名', field: 'laboratoryName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '申请使用时间', field: 'applyBeginTime', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '申请结束时间', field: 'applyEndTime', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '状态 0:待审核 1:成功 2:失败', field: 'status', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '备注', field: 'comment', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '老师ID', field: 'teacherId', visible: true, align: 'center', valign: 'middle', sortable: true}
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

/**
 * 点击添加实验室申请管理
 */
ApplyLaboratory.openAddApplyLaboratory = function () {
    var index = layer.open({
        type: 2,
        title: '添加实验室申请管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/applyLaboratory/applyLaboratory_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看实验室申请管理详情
 */
ApplyLaboratory.openApplyLaboratoryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '实验室申请管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/applyLaboratory/applyLaboratory_update/' + ApplyLaboratory.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除实验室申请管理
 */
ApplyLaboratory.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/applyLaboratory/delete", function (data) {
            Feng.success("删除成功!");
            ApplyLaboratory.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("applyLaboratoryId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询实验室申请管理列表
 */
ApplyLaboratory.search = function () {
    var queryData = {};
        queryData['id'] = $("#id").val();
        queryData['experimentId'] = $("#experimentId").val();
        queryData['laboratoryId'] = $("#laboratoryId").val();
        queryData['experimentName'] = $("#experimentName").val();
        queryData['laboratoryName'] = $("#laboratoryName").val();
        queryData['applyBeginTime'] = $("#applyBeginTime").val();
        queryData['applyEndTime'] = $("#applyEndTime").val();
        queryData['status'] = $("#status").val();
        queryData['comment'] = $("#comment").val();
        queryData['teacherId'] = $("#teacherId").val();
    ApplyLaboratory.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ApplyLaboratory.initColumn();
    var table = new BSTable(ApplyLaboratory.id, "/applyLaboratory/list", defaultColunms);
    table.setPaginationType("client");
    ApplyLaboratory.table = table.init();
});
