/**
 * 测试验证管理初始化
 */
var Validator = {
    id: "ValidatorTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Validator.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '模板id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '订单名字', field: 'orderName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '订单发货地', field: 'orderFrom', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '订单剩余量', field: 'orderLeft', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
Validator.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Validator.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加测试验证
 */
Validator.openAddValidator = function () {
    var index = layer.open({
        type: 2,
        title: '添加测试验证',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/validator/validator_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看测试验证详情
 */
Validator.openValidatorDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '测试验证详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/validator/validator_update/' + Validator.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除测试验证
 */
Validator.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/validator/delete", function (data) {
            Feng.success("删除成功!");
            Validator.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("validatorId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询测试验证列表
 */
Validator.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Validator.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Validator.initColumn();
    var table = new BSTable(Validator.id, "/validator/list", defaultColunms);
    table.setPaginationType("client");
    Validator.table = table.init();
});
