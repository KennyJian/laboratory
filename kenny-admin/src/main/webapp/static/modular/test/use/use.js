/**
 * 测试管理初始化
 */
var Use = {
    id: "UseTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Use.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '货物', field: 'good', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '怕怕', field: 'papap', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
Use.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Use.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加测试
 */
Use.openAddUse = function () {
    var index = layer.open({
        type: 2,
        title: '添加测试',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/use/use_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看测试详情
 */
Use.openUseDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '测试详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/use/use_update/' + Use.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除测试
 */
Use.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/use/delete", function (data) {
            Feng.success("删除成功!");
            Use.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("useId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询测试列表
 */
Use.search = function () {
    var queryData = {};
        queryData['id'] = $("#id").val();
        queryData['good'] = $("#good").val();
        queryData['papap'] = $("#papap").val();
    Use.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Use.initColumn();
    var table = new BSTable(Use.id, "/use/list", defaultColunms);
    table.setPaginationType("client");
    Use.table = table.init();
});
