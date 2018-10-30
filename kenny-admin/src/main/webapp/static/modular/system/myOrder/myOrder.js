/**
 * 订单管理管理初始化
 */

var MyOrder = {
    id: "MyOrderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MyOrder.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '订单id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '下单人名称', field: 'user', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '地点', field: 'place', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '商品名称', field: 'goods', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '下单时间', field: 'createtime', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
MyOrder.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MyOrder.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加订单管理
 */
MyOrder.openAddMyOrder = function () {
    var index = layer.open({
        type: 2,
        title: '添加订单管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/myOrder/myOrder_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看订单管理详情
 */
MyOrder.openMyOrderDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '订单管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/myOrder/myOrder_update/' + MyOrder.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除订单管理
 */
MyOrder.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/myOrder/delete", function (data) {
            Feng.success("删除成功!");
            MyOrder.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("myOrderId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询订单管理列表
 */
MyOrder.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MyOrder.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MyOrder.initColumn();
    var table = new BSTable(MyOrder.id, "/myOrder/list", defaultColunms);
    table.setPaginationType("client");
    MyOrder.table = table.init();
});
