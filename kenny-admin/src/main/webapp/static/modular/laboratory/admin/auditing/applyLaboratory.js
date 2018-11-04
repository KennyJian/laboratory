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
            {title: '申请的实验室id', field: 'laboratoryId', visible: false, align: 'center', valign: 'middle', sortable: true},
            {title: '实验名', field: 'experimentName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '实验室名', field: 'laboratoryName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '申请使用时间', field: 'applyBeginTime', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '申请结束时间', field: 'applyEndTime', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '审核状态', field: 'status', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '申请老师名字', field: 'teacherName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '备注', field: 'comment', visible: true, align: 'center', valign: 'middle', sortable: true}
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
 * 通过审核
 */
ApplyLaboratory.auditingSuccess = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/admin/auditingLaboratory/auditing/success", function (data) {
            Feng.success("操作成功!");
            ApplyLaboratory.table.refresh();
        }, function (data) {
            Feng.error("操作失败!" + data.responseJSON.message + "!");
        });
        ajax.set("applyLaboratoryId",this.seItem.id);
        ajax.start();
    }
};


/**
 * 不通过审核
 */
ApplyLaboratory.auditingFail = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/admin/auditingLaboratory/auditing/fail", function (data) {
            Feng.success("操作成功!");
            ApplyLaboratory.table.refresh();
        }, function (data) {
            Feng.error("操作失败!" + data.responseJSON.message + "!");
        });
        ajax.set("applyLaboratoryId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 填写备注
 */
ApplyLaboratory.openApplyLaboratoryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '实验室申请管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/admin/auditingLaboratory/applyLaboratory_comment/' + ApplyLaboratory.seItem.id
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
        queryData['applyBeginTime'] = $("#applyBeginTime").val();
        queryData['applyEndTime'] = $("#applyEndTime").val();
        queryData['status'] = $("#status").val();
    ApplyLaboratory.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ApplyLaboratory.initColumn();
    var table = new BSTable(ApplyLaboratory.id, "/admin/auditingLaboratory/list", defaultColunms);
    table.setPaginationType("client");
    ApplyLaboratory.table = table.init();
});
