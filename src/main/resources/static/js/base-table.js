$(function() {
    'use strict';

    var condition = {};
    var $this = {};

    var baseTable = function(url, columns, getOtherCondition, callback, options) {
        $this = $(this);

        baseTable.columns = columns ? columns : baseTable.columns;
        baseTable.options = options ? options : baseTable.options;
        baseTable.url = url ? url : baseTable.url;
        baseTable.getOtherCondition = getOtherCondition ? getOtherCondition
                : baseTable.getOtherCondition;
        baseTable.callback = callback?callback:baseTable.callback;

        init();
    }
    baseTable.callback = function() {
        //Do nothing
    };
    baseTable.getOtherCondition = function() {
        return {};
    };
    baseTable._getCondition = function() {
        var other = {
            currentPage : 1
        };
        $.extend(other, baseTable.getOtherCondition());
        $.extend(condition, other);
        return condition;
    };

    baseTable.query = function() {
        condition = baseTable._getCondition();
        $this.bootstrapTable("refreshOptions", {
            pageNumber : 1
        });
    };
    function _queryParams(params) {
        condition.pageSize = params.pageSize;
        condition.currentPage = params.pageNumber;
        return condition;
    }

    function init() {
        $this.bootstrapTable({
            url : baseTable.url,// 数据源
            dataField : "data",// 服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
            undefinedText : '',
            pagination : true,// 是否分页
            pageSize : 10,// 单页记录数
            pageList : [ 5, 10, 20, 50 ],// 分页步进值
            sidePagination : "server",// 服务端分页
            contentType : "application/x-www-form-urlencoded",// 请求数据内容格式 默认是
            // application/json
            // 自己根据格式自行服务端处理
            dataType : "json",// 期待返回数据类型
            method : "get",// 请求方式
            searchAlign : "left",// 查询框对齐方式
            searchOnEnterKey : false,// 回车搜索
            buttonsAlign : "left",// 按钮对齐方式
            toolbar : "#toolbar",// 指定工具栏
            toolbarAlign : "right",// 工具栏对齐方式
            queryParamsType : "",
            escape: true, // html 转义
            queryParams : _queryParams,
            columns : baseTable.columns,
            locale : "zh-CN",// 中文支持,
            detailView : false, // 是否显示详情折叠
            detailFormatter : function(index, row, element) {
                var html = '';
                $.each(row, function(key, val) {
                    html += "<p>" + key + ":" + val + "</p>"
                });
                return html;
            },
            onLoadSuccess : function(data) {
                baseTable.callback(data);
            },
            onLoadError : function() {
            }

        });
    }

    baseTable.options = []; // 这个字段暂时用不上
    baseTable.columns = [ {
        title : "ID",
        field : "id",
        width : "20",
        align : "center"
    }, {
        title : "公司名称",
        field : "name",
        width : "15",
        align : "center"
    }, {
        title : "负责人",
        field : "principal",
        width : "10",
        align : "center"
    }, {
        title : "电话",
        field : "phone",
        width : "20",
        align : "center"
    }, {
        title : "申请时间",
        field : "applyTime",
        width : "20",
        align : "center",
        formatter : function(value) {
            var date = new Date();
            date.setTime(value * 1000);
            return date.format("yyyy-MM-dd");
        }
    } ];

    $.fn.baseTable = baseTable;

    //$("#dg").baseTable("/api/apply/company/list", columns, getOtherCondition, callback);
    //调用方式传入三个参数，请求的url，table每列的样式，如何获取参数, 每次查询成功的回调
});