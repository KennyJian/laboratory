/**
 * 测试数据范围管理初始化
 */
var Test = {
    id: "TestTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Test.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '订单名', field: 'orderName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '订单数量', field: 'orderNum', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '订单发往地', field: 'orderTo', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '所属公司id', field: 'deptid', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
Test.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Test.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加测试数据范围
 */
Test.openAddTest = function () {
    var index = layer.open({
        type: 2,
        title: '添加测试数据范围',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/test/test_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看测试数据范围详情
 */
Test.openTestDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '测试数据范围详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/test/test_update/' + Test.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除测试数据范围
 */
Test.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/test/delete", function (data) {
            Feng.success("删除成功!");
            Test.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("testId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询测试数据范围列表
 */
Test.search = function () {
    var queryData = {};
        queryData['id'] = $("#id").val();
        queryData['orderName'] = $("#orderName").val();
        queryData['orderNum'] = $("#orderNum").val();
        queryData['orderTo'] = $("#orderTo").val();
        queryData['deptid'] = $("#deptid").val();
    Test.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Test.initColumn();
    var table = new BSTable(Test.id, "/test/list", defaultColunms);
    table.setPaginationType("client");
    Test.table = table.init();
});
