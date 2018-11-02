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
            {title: '实验id', field: 'experimentId', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '学生名', field: 'studentName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '实验名', field: 'experimentName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '状态 0:待审核 1:成功 2:失败', field: 'status', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '学生id', field: 'studentId', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '老师id', field: 'teacherId', visible: true, align: 'center', valign: 'middle', sortable: true}
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
 * 点击添加实验选课管理
 */
ApplyExperiment.openAddApplyExperiment = function () {
    var index = layer.open({
        type: 2,
        title: '添加实验选课管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/applyExperiment/applyExperiment_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看实验选课管理详情
 */
ApplyExperiment.openApplyExperimentDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '实验选课管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/applyExperiment/applyExperiment_update/' + ApplyExperiment.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除实验选课管理
 */
ApplyExperiment.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/applyExperiment/delete", function (data) {
            Feng.success("删除成功!");
            ApplyExperiment.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
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
    var table = new BSTable(ApplyExperiment.id, "/applyExperiment/list", defaultColunms);
    table.setPaginationType("client");
    ApplyExperiment.table = table.init();
});
