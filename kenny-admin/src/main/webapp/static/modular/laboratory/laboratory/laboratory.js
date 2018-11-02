/**
 * 实验室管理管理初始化
 */
var Laboratory = {
    id: "LaboratoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Laboratory.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'laboratoryId', visible: false, align: 'center', valign: 'middle'},
            {title: '实验室名字', field: 'laboratoryName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '实验室地点', field: 'laboratoryLocation', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '实验室设备数', field: 'laboratoryEquipmentNum', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '实验室是否开放  1:开放 2:不开放', field: 'isOpen', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '实验室是否通电 1:通电 2:不通电', field: 'isElectrify', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
Laboratory.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Laboratory.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加实验室管理
 */
Laboratory.openAddLaboratory = function () {
    var index = layer.open({
        type: 2,
        title: '添加实验室管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/laboratory/laboratory_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看实验室管理详情
 */
Laboratory.openLaboratoryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '实验室管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/laboratory/laboratory_update/' + Laboratory.seItem.laboratoryId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除实验室管理
 */
Laboratory.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/laboratory/delete", function (data) {
            Feng.success("删除成功!");
            Laboratory.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("laboratoryId",this.seItem.laboratoryId);
        ajax.start();
    }
};

/**
 * 查询实验室管理列表
 */
Laboratory.search = function () {
    var queryData = {};
        queryData['laboratoryId'] = $("#laboratoryId").val();
        queryData['laboratoryName'] = $("#laboratoryName").val();
        queryData['laboratoryLocation'] = $("#laboratoryLocation").val();
        queryData['laboratoryEquipmentNum'] = $("#laboratoryEquipmentNum").val();
        queryData['isOpen'] = $("#isOpen").val();
        queryData['isElectrify'] = $("#isElectrify").val();
    Laboratory.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Laboratory.initColumn();
    var table = new BSTable(Laboratory.id, "/laboratory/list", defaultColunms);
    table.setPaginationType("client");
    Laboratory.table = table.init();
});
