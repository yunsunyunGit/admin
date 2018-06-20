<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>积分兑换列表</title>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/select2/select2.css"/>
    <link rel="stylesheet" href="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/bootstrap-datepicker/css/datepicker.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/bootstrap-doublebox/doublebox-bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/bootstrap-datepicker/css/datepicker.css"/>
</head>
<body>
<div class="row">
    <div class="col-md-12 delete-padding-rt">
        <ul class="page-breadcrumb breadcrumb">
            <li>
                <i class="fa fa-home"></i>
                <a href="${rc.contextPath}/">商品管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="${rc.contextPath}/goods/record">积分兑换列表</a>
                <i class="fa fa-angle-right"></i>
            </li>
        </ul>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-bars"></i>积分兑换列表</div>
                <div class="actions">
                    <div class="btn-group">
                   <@shiro.hasPermission name="goods-record-Export">
                        <a href="javaScript:void(0);" onclick="exportExcle()"  class="btn green">
                            <i class="fa fa-plus"></i>
                            <span class="hidden-480" >导出Excel</span>
                        </a>
                    </@shiro.hasPermission>
                    </div>
                </div>
            </div>
            <div class="portlet-body">
            <#if message>
                <div class="alert alert-success">
                    <button data-dismiss="alert" class="close">×</button>
                ${(message)!}
                </div>
            </#if>
                <div class="table-container">
                    <div id="search" class="table-actions-wrapper" >
                       <#-- <b class="form-control input-inline input-small input-sm" style="border: 0px; text-align: right;">商城名称：</b>
                        <select id="mallId" name="mallId" class="form-control">
                            <option value="0">全部</option>
                        <#list mallList as mall>
                            <option value="${mall.id}">${mall.name}</option>
                        </#list>
                        </select>-->
                    </div>
                    <table class="table table-striped table-bordered table-hover" id="goods_data_table">
                        <thead>
                        <tr role="row" class="heading">
                            <th></th>
                            <th></th>
                            <th width="5%">商城名称</th>
                            <th width="15%">会员名称</th>
                            <th width="10%">会员手机</th>
                            <th width="15%">商家名称</th>
                            <th width="10%">兑换商品</th>
                            <th width="10%">兑换数量</th>
                            <th width="10%">总积分</th>
                            <th width="10%">兑换时间</th>
                            <th width="20%">操作</th>
                        </tr>

                        <tr role="row" class="filter">
                            <td></td>
                            <td></td>
                            <td><input placeholder="请输入商城名称" type="text" class="form-control form-filter input-sm" name="search_LIKE_mallName"></td>
                            <td><input placeholder="请输入会员名称" type="text" class="form-control form-filter input-sm" name="search_LIKE_memberName"></td>
                            <td><input placeholder="请输入会员手机" type="text" class="form-control form-filter input-sm" name="search_LIKE_memberPhone"></td>
                            <td><input placeholder="请输入商家名称" type="text" class="form-control form-filter input-sm" name="search_LIKE_merchantName"></td>
                            <td><input placeholder="请输入商品名" type="text" class="form-control form-filter input-sm" name="search_LIKE_goodsName"></td>
                            <td><input placeholder="请输入商品数量" type="text" class="form-control form-filter input-sm" name="search_LIKE_goodsNum"></td>
                            <td><input placeholder="请输入上兑换积分" type="text" class="form-control form-filter input-sm" name="search_LIKE_goodsPoint"></td>
                            <td>
                                <div id="beginTime" class="input-group date date-picker margin-bottom-5" data-date-format="yyyy-mm-dd">
                                    <input readonly="true" id="begin"  type="text" class="form-control form-filter  input-sm" name="search_LIKE_startTime" placeholder="开始时间">
                                    <span class="input-group-btn"><button class="btn btn-sm default" type="button"><i class="fa fa-calendar"></i></button></span>
                                </div>
                                <div id="endTime" class="input-group date date-picker" data-date-format="yyyy-mm-dd">
                                    <input readonly="true" id="end" type="text" class="form-control form-filter  input-sm" name="search_LIKE_endTime" placeholder="结束时间">
                                    <span class="input-group-btn"><button class="btn btn-sm default" type="button"><i class="fa fa-calendar"></i></button></span>
                                </div>
                            </td>
                            <td>
                                <button class="btn btn-sm yellow filter-submit margin-bottom"><i class="fa fa-search"></i> 搜索
                                </button>
                                <button class="btn btn-sm red filter-cancel"><i class="fa fa-times"></i> 重置</button>
                            </td>
                        </tr>

                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<content tag="script">
    <script type="text/javascript" src="${rc.contextPath}/assets/global/plugins/select2/select2.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/plugins/data-tables/jquery.dataTables.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script src="${rc.contextPath}/assets/global/scripts/datatable.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/common/commonUtil.js"></script>
    <script type="text/javascript">
        $("#mallId option[value='${mallIdSelect}']").attr("selected","selected");
        var grid=new Datatable();
        grid.init({
            src:$("#goods_data_table"),
            onSuccess:function(grid){
            },
            onError:function(grid){
            },
            dataTable:{
                "bServerSide":true,
                "sAjaxSource":"${rc.contextPath}/goods/record/list",
                "aoColumnDefs":[
                    { "bSortable":false,"aTargets":[ 0,1,2,3,4,5,6,7,8,9] }
                ],//设置不排序得列
                "aoColumns":[
                    { "sWidth":"1%","sTitle":'<input type="checkbox" class= "checkAllBox" onclick="checkAllBox(this)"  title="全选" class="group-checkable" />',"sDefaultContent":"","mRender":function(data,type,full){
                        return '<div class="checker"  ><span class=""><input type="checkbox" class="checkboxes" checked="" name="checkBox" value="'+full.id+'"></span></div>';
                    }},
                    { "sWidth":"10%","sTitle":"订单编号","mData":"orderNum","sDefaultContent":"","mRender":function(data,type,full){
                            var a='<a href="${rc.contextPath}/point/order/detail/'+full.orderId+'" >'+full.orderNum+'</a>';
                            return a;
                    }},
                    { "sWidth":"13%","sTitle":"商城名称","mData":"mallName"},
                    {  "sWidth":"10%","sTitle":"会员名称","mData":"memberName","mRender":function(data,type,full){
                        return full.memberName+'('+full.memberId+')';
                    }},
                    {  "sWidth":"15%","sTitle":"会员手机","mData":"memberPhone"},
                    {  "sWidth":"10%","sTitle":"商家名称","mData":"merchantName"},
                    {  "sWidth":"10%","sTitle":"兑换商品","mData":"goodsName"},
                    {  "sWidth":"10%","sTitle":"兑换数量","mData":"goodsNum"},
                    {  "sWidth":"5%","sTitle":"总积分","sDefaultContent":"","mRender":function(data,type,full){
                        var sumPoint = full.goodsNum*full.goodsPoint;
                        return  sumPoint;
                    }},
                    {  "sWidth":"10%","sTitle":"兑换时间","mData":"createTime","sDefaultContent":"","mRender":function(data,type,full){
                        var time='';
                        if(data){
                            time=new Date(data).format('yyyy-MM-dd hh:mm:ss');
                        }
                        return time;
                    }},
                    {  "sWidth":"20%","sTitle":"操作","sDefaultContent":"","mRender":function(data,type,full){
                        var a= '';
                        return "<div style='white-space: nowrap'>"+a+"</div>";
                    }}

                ]
            }
        });
        /**
         *  选择框选中事件
         * */
        jQuery('#goods_data_table').on('change','tbody tr .checkboxes',function(){
            $(this).parents('span').toggleClass("checked");
        });

        /**
         * 点击查询 模糊查询应用信息
         * */
        $("#mallId").change(function () {
            selectAjax($("#mallId").val());
        });


        $('.table-group-action-submit').click(function(){
            selectAjax($("#mallId").val());
        });
        /**
         * 关闭提示信息
         * */
        function alertClose(){
            $(".alert").alert('close');
        }
        jQuery(function($){
            var message="${message}", stat="${stat}";
            if(null==message||''==message){
                setInterval(alertClose,3*1000);
            }else{
                if('true'==stat){
                    $('#message').removeClass('alert-danger');
                    $('#message').addClass('alert-success');
                    setInterval(alertClose,3*1000);
                }else{
                    $('#message').addClass('alert-danger');
                    $('#message').removeClass('alert-success');
                }
            }
        });

        /**
         * 模糊查询应用信息
         * */
        function selectAjax(mallId){
            grid.setAjaxParam("mallId",mallId);
            grid.getDataTable().fnDraw();
        }

        $('.date-picker').datepicker({
            rtl:Metronic.isRTL(),
            autoclose:true,
            language: 'zh-CN'
        });
        $('#beginTime').on('change',function(e){
            $('#endTime').datepicker('setStartDate', $('#begin').val());
        });
        $('#endTime').on('change',function(e){
            $('#beginTime').datepicker('setEndDate', $('#end').val());
        });


        function exportExcle() {
            var url = "${rc.contextPath}/goods/record/export";
            var mallName = $("input[name='search_LIKE_mallName']").val();
            var memberName = $("input[name='search_LIKE_memberName']").val();
            var memberPhone = $("input[name='search_LIKE_memberPhone']").val();
            var merchantName = $("input[name='search_LIKE_merchantName']").val();
            var goodsName = $("input[name='search_LIKE_goodsName']").val();
            var goodsNum = $("input[name='search_LIKE_goodsNum']").val();
            var goodsPoint = $("input[name='search_LIKE_goodsPoint']").val();
            var startTime = $("input[name='search_LIKE_startTime']").val();
            var endTime = $("input[name='search_LIKE_endTime]").val();

            var form = $("<form></form>").attr("action", url).attr("method", "post");

            form.append($("<input></input>").attr("type", "hidden").attr("name", "search_LIKE_mallName").attr("value", mallName));
            form.append($("<input></input>").attr("type", "hidden").attr("name", "search_LIKE_memberName").attr("value", memberName));
            form.append($("<input></input>").attr("type", "hidden").attr("name", "search_LIKE_memberPhone").attr("value", memberPhone));
            form.append($("<input></input>").attr("type", "hidden").attr("name", "search_LIKE_merchantName").attr("value", merchantName));
            form.append($("<input></input>").attr("type", "hidden").attr("name", "search_LIKE_goodsName").attr("value", goodsName));
            form.append($("<input></input>").attr("type", "hidden").attr("name", "search_LIKE_goodsNum").attr("value", goodsNum));
            form.append($("<input></input>").attr("type", "hidden").attr("name", "search_LIKE_goodsPoint").attr("value", goodsPoint));
            form.append($("<input></input>").attr("type", "hidden").attr("name", "search_LIKE_startTime").attr("value", startTime));
            form.append($("<input></input>").attr("type", "hidden").attr("name", "search_LIKE_endTime").attr("value", endTime));
            form.appendTo('body').submit().remove();
        }
        
    </script>
</content>
</html>
