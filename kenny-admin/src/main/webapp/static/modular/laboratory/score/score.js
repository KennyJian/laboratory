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
            {title: '学生id', field: 'studentId', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '实验id', field: 'experimentId', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '学生名', field: 'studentName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '实验名', field: 'experimentName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '到勤数', field: 'attendanceNum', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '成绩', field: 'score', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '老师名字', field: 'teacherName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '老师id', field: 'teacherId', visible: true, align: 'center', valign: 'middle', sortable: true}
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
 * 点击添加成绩管理
 */
Score.openAddScore = function () {
    var index = layer.open({
        type: 2,
        title: '添加成绩管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/score/score_add'
    });
    this.layerIndex = index;
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
            content: Feng.ctxPath + '/score/score_update/' + Score.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除成绩管理
 */
Score.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/score/delete", function (data) {
            Feng.success("删除成功!");
            Score.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
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
        queryData['id'] = $("#id").val();
        queryData['studentId'] = $("#studentId").val();
        queryData['experimentId'] = $("#experimentId").val();
        queryData['studentName'] = $("#studentName").val();
        queryData['experimentName'] = $("#experimentName").val();
        queryData['attendanceNum'] = $("#attendanceNum").val();
        queryData['score'] = $("#score").val();
        queryData['teacherName'] = $("#teacherName").val();
        queryData['teacherId'] = $("#teacherId").val();
    Score.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Score.initColumn();
    var table = new BSTable(Score.id, "/score/list", defaultColunms);
    table.setPaginationType("client");
    Score.table = table.init();
});
