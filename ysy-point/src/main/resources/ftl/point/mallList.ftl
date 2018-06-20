<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>商城管理</title>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/select2/select2.css"/>
    <link rel="stylesheet" href="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/bootstrap-datepicker/css/datepicker.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/bootstrap-doublebox/doublebox-bootstrap.css"/>
</head>
<body>
<div class="row">
    <div class="col-md-12 delete-padding-rt">
        <ul class="page-breadcrumb breadcrumb">
            <li>
                <i class="fa fa-home"></i>
                <a href="${rc.contextPath}/">商城管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="${rc.contextPath}/point/mall/list">商城列表</a>
                <i class="fa fa-angle-right"></i>
            </li>
        </ul>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-cogs"></i>商城列表</div>
                <div class="actions">
                    <div class="btn-group">
                    <@shiro.hasPermission name="point-mall-Add">
                        <a href="${rc.contextPath}/point/mall/create" class="btn green">
                            <i class="fa fa-plus"></i>
                            <span class="hidden-480" >新增商城</span>
                        </a>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="point-mall-Del">
                        <a  class="btn red">
                            <i class="fa fa-trash-o"></i>
                            <span class="hidden-480"  onclick="deleteList();">删除商城</span>
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
                        <b class="form-control input-inline input-small input-sm" style="border: 0px; text-align: right;">商城名称：</b>
                        <input placeholder="请输入商城名称" type="text" class="form-control table-group-action-input form-control input-inline input-small input-sm" id="selectInput" maxlength="15">
                        <button class="btn btn-sm yellow table-group-action-submit"><i class="fa fa-search"></i> 搜索</button>
                        <button class="btn btn-sm red table-group-action-times"><i class="fa fa-times"></i> 重置</button>
                    </div>
                    <table class="table table-striped table-bordered table-hover" id="apply_data_table">
                        <thead>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>

                <!-- 商家入驻begin -->
                <div id="mall_check_in" class="modal fade" tabindex="-1" aria-hidden="true">

                    <input type="hidden" id="checkInMallId"  value=""/>
                    <div class="modal-dialog" style="width: 950px;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                <div class="portlet-title"  >
                                    <div class="caption" style="color:#cb0101" ><i class="fa fa-cogs"></i>商城入驻</div>
                                </div>
                            </div>
                            <div class="modal-body">
                                <select multiple="multiple" size="12" name="doublebox" class="marchantSelector" style="height: 160px;" >
                                </select>
                                <div class="modal-footer">
                                    <button class="btn green" onclick="saveCheckIn();">确定</button>
                                    <button class="btn" data-dismiss="modal" aria-hidden="true">返回</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 商家入驻信息end -->

            </div>
        </div>
    </div>
</div>
</body>
<content tag="script">
    <script type="text/javascript" src="${rc.contextPath}/assets/global/plugins/bootstrap-doublebox/doublebox-bootstrap.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/plugins/select2/select2.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/plugins/data-tables/jquery.dataTables.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script src="${rc.contextPath}/assets/global/scripts/datatable.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/common/commonUtil.js"></script>
    <script type="text/javascript">

        var grid=new Datatable();
        grid.init({
            src:$("#apply_data_table"),
            onSuccess:function(grid){
            },
            onError:function(grid){
            },
            dataTable:{
                "bServerSide":true,
                "sAjaxSource":"${rc.contextPath}/point/mall/list",
                "aoColumnDefs":[
                    { "bSortable":false,"aTargets":[ 0,1,2,3,4,5 ] }
                ],//设置不排序得列
                "aoColumns":[
                    { "sWidth":"1%","sTitle":'<input type="checkbox" class= "checkAllBox" onclick="checkAllBox(this)"  title="全选" class="group-checkable" />',"sDefaultContent":"","mRender":function(data,type,full){
                        return '<div class="checker"  ><span class=""><input type="checkbox" class="checkboxes" checked="" name="checkBox" value="'+full.id+'"></span></div>';
                    }},
                    {  "sWidth":"10%","sTitle":"商城名","sDefaultContent":"","mRender":function (data,type,full) {
                        var a='<a href="${rc.contextPath}/point/mall/update/'+full.id+'" title="'+full.name+'" >'+full.name+'</a>';
                        return a;
                    }},
                    {  "sWidth":"35%","sTitle":"商城描述","mData":"description"},
                    {  "sWidth":"20%","sTitle":"所属APP名","mData":"appName"},
                    {  "sWidth":"10%","sTitle":"商家入驻数量","mData":"merchantNum"},
                    {  "sWidth":"20%","sTitle":"操作","sDefaultContent":"","mRender":function(data,type,full){
                        var a='<@shiro.hasPermission name="point-mall-Edit"><a href="${rc.contextPath}/point/mall/update/'+full.id+'" class="btn btn-xs blue"  title="编辑" ><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>';
                        var b='<@shiro.hasPermission name="point-mall-Del"><a  href="javascript:void(0);" onclick="deleteInfo('+full.id+')" class= "btn btn-xs red" title ="删除"><i class="fa fa-trash-o"></i>删除</a></@shiro.hasPermission>';
                        var c='<@shiro.hasPermission name="point-mall-CheckIn"><a  href="javascript:void(0);" onclick="checkIn('+full.id+')" class="btn btn-xs blue" title ="商家入驻"><i class="glyphicon glyphicon-cog"></i>商城入驻</a></@shiro.hasPermission>';
                        return "<div style='white-space: nowrap'>"+a+b+c+"</div>";
                    }}
                ]
            }
        });
        /**
         *  选择框选中事件
         * */
        jQuery('#apply_data_table').on('change','tbody tr .checkboxes',function(){
            $(this).parents('span').toggleClass("checked");
        });
        /**
         * 点击查询 模糊查询应用信息
         * */
        $('.table-group-action-submit').click(function(){
            selectAjax($("#selectInput").val());
        });
        /**
         * 点击重置 按钮
         * */
        $('.table-group-action-times').click(function(){
            $("#selectInput").val('');
            selectAjax('');
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
        
        function checkIn(id) {
            $('.marchantSelector').html("");
            $('#checkInMallId').val(id);
            $.ajax({
                url:'${rc.contextPath}/point/mall/get-check-mall',
                type:'POST',
                data:{"id":id},
                dataType:"json",
                traditional:true,
                success:function(data){
                    if(data&&data.stat){
                        var demo2 = $('.marchantSelector').doublebox({
                            nonSelectedListLabel: '未入驻商家',
                            selectedListLabel: '入驻商家',
                            preserveSelectionOnMove: 'moved',
                            moveOnSelect: false,
                            nonSelectedList:data.merchantNotInList,
                            selectedList:data.merchantInList,
                            optionValue:"merchantId",
                            optionText:"merchantName",
                            doubleMove:true,
                        });
                        $('#mall_check_in').modal('show');
                    }else{
                        bootboxAlert(data.msg);
                    }
                }
            });
        }

        function saveCheckIn() {
            var mallId = $('#checkInMallId').val();
            var merchantIds = $('.marchantSelector').val();
            $.ajax({
                url:'${rc.contextPath}/point/mall/save-check-mall',
                type:'POST',
                data:{"id":mallId,"merchantIds":merchantIds},
                dataType:"json",
                traditional:true,
                success:function(data){
                    if(data&&data.stat){
                        alertHint(data.msg);
                        $('#mall_check_in').modal('hide');
                        grid.getDataTable().fnDraw();
                    }else{
                        $('#mall_check_in').modal('hide');
                        bootboxAlert(data.msg);
                    }
                }
            });
            $('#mall_check_in').modal('hide');
        }
        
        /**
         * 模糊查询应用信息
         * */
        function selectAjax(appName){
            grid.setAjaxParam("mallName",appName);
            grid.getDataTable().fnDraw();
        }
        /**
         * 删除单个应用信息
         **/
        function deleteInfo(id){
            commonDelete({"id":id},'确认删除商城?','删除商城','${rc.contextPath}/point/mall/delete');
        }
        /**
         *  删除多个应用信息
         */
        function deleteList(){
            var ids=[], finIds=[], ch=$("#apply_data_table span.checked >input[name='checkBox']");
            if(ch){
                ch.each(function(i,n){
                    ids.push(n.value);
                });
            }
            if(ids==''||ids==null||ids.length==0){
                bootboxAlert('请选择需要删除的商城');
                return false;
            }
            commonDelete({"ids":ids},'确认批量删除商城?','批量删除商城','${rc.contextPath}/point/mall/delete-all');
        }


        /**
         *  公共删除应用信息
         * */
        function commonDelete(data,message,title,url){
            bootbox.dialog({
                message:message,
                title:title,
                buttons:{
                    main:{
                        label:"确定",
                        className:"green",
                        callback:function(){
                            $.ajax({
                                url:url,
                                type:'POST',
                                data:data,
                                dataType:"json",
                                traditional:true,
                                success:function(data){
                                    if(data&&data.stat){
                                        bootboxAlert('删除成功');
                                        grid.getDataTable().fnDraw();
                                    }else{
                                        bootboxAlert(data.msg);
                                    }
                                }
                            });
                        }
                    },
                    cancel:{
                        label:"取消",
                        className:"gray",
                        callback:function(){
                            $(this).hide();
                        }
                    }
                }
            });
        }

    </script>
</content>
</html>
