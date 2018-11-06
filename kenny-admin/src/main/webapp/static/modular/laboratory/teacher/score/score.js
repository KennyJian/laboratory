/**
 * 成绩管理管理初始化
 */
var Score = {
    id: "ScoreTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Score.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '实验名', field: 'experimentName', visible: true, align: 'center', valign: 'middle', sortable: true},
        {title: '学生名', field: 'studentName', visible: true, align: 'center', valign: 'middle', sortable: true},
        {title: '到勤数', field: 'attendanceNum', visible: true, align: 'center', valign: 'middle', sortable: true},
        {title: '成绩', field: 'score', visible: true, align: 'center', valign: 'middle', sortable: true},
    ];
};

/**
 * 检查是否选中
 */
Score.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Score.seItem = selected[0];
        return true;
    }
};

/**
 * 打开查看成绩管理详情
 */
Score.openScoreDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '成绩管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/teacher/score/score_update/' + Score.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 查询成绩管理列表
 */
Score.search = function () {
    var queryData = {};
        queryData['studentName'] = $("#studentName").val();
        queryData['experimentName'] = $("#experimentName").val();
    Score.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Score.initColumn();
    var table = new BSTable(Score.id, "/teacher/score/list", defaultColunms);
    table.setPaginationType("client");
    Score.table = table.init();
});
