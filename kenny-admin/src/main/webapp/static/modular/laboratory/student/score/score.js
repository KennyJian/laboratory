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
            {title: '到勤数', field: 'attendanceNum', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '成绩', field: 'score', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '授课老师', field: 'teacherName', visible: true, align: 'center', valign: 'middle', sortable: true},
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
 * 签到
 */
Score.attend = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/student/score/attend", function (data) {
            Feng.success("签到成功!");
            Score.table.refresh();
        }, function (data) {
            Feng.error("签到失败!" + data.responseJSON.message + "!");
        });
        ajax.set("scoreId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询成绩管理列表
 */
Score.search = function () {
    var queryData = {};
        queryData['experimentName'] = $("#experimentName").val();
        queryData['teacherName'] = $("#teacherName").val();
    Score.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Score.initColumn();
    var table = new BSTable(Score.id, "/student/score/list", defaultColunms);
    table.setPaginationType("client");
    Score.table = table.init();
});
