/**
 * 测试第二模板管理初始化
 */
var Second = {
    id: "SecondTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Second.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '商品', field: 'goods', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '质量', field: 'quality', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
Second.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Second.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加测试第二模板
 */
Second.openAddSecond = function () {
    var index = layer.open({
        type: 2,
        title: '添加测试第二模板',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/second/second_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看测试第二模板详情
 */
Second.openSecondDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '测试第二模板详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/second/second_update/' + Second.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除测试第二模板
 */
Second.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/second/delete", function (data) {
            Feng.success("删除成功!");
            Second.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("secondId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询测试第二模板列表
 */
Second.search = function () {
    var queryData = {};
    queryData['goodsid'] = $("#goodsid").val();
    queryData['goodsname'] = $("#goodsname").val();
    queryData['goodsquality'] = $("#goodsquality").val();
    Second.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Second.initColumn();
    var table = new BSTable(Second.id, "/second/list", defaultColunms);
    table.setPaginationType("client");
    Second.table = table.init();
});
