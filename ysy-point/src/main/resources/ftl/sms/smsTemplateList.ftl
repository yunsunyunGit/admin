<html>
<head>
    <title>短信模板管理</title>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/select2/select2.css"/>
    <link rel="stylesheet" href="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/bootstrap-datepicker/css/datepicker.css"/>
</head>
<body>
<div class="row">
    <div class="col-md-12 delete-padding-rt">
        <ul class="page-breadcrumb breadcrumb">
            <li>
                <i class="fa fa-home"></i>
                <a href="${rc.contextPath}/">模板管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="${rc.contextPath}/sms/template/list">模板列表</a>
                <i class="fa fa-angle-right"></i>
            </li>
        </ul>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-cogs"></i>模板列表</div>
                <div class="actions">
                    <div class="btn-group">
                    <@shiro.hasPermission name="sms-template-Add">
                        <a href="${rc.contextPath}/sms/template/create" class="btn green">
                            <i class="fa fa-plus"></i>
                            <span class="hidden-480" >新增模板</span>
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
                   <#-- <div id="search" class="table-actions-wrapper" >
                        <b class="form-control input-inline input-small input-sm" style="border: 0px; text-align: right;">模板标题/内容：</b>
                        <input type="text" class="form-control table-group-action-input form-control input-inline input-small input-sm" id="selectInput" maxlength="15">
                        <button class="btn btn-sm yellow table-group-action-submit"><i class="fa fa-search"></i> 搜索</button>
                        <button class="btn btn-sm red table-group-action-times"><i class="fa fa-times"></i> 重置</button>
                    </div>-->
                    <table class="table table-striped table-bordered table-hover" id="apply_data_table">
                        <thead>
                            <tr role="row" class="heading">
                                <th width="5%"><input type="checkbox" class="group-checkable"></th>
                                <th width="5%">模板名</th>
                                <th width="10%">模板内容</th>
                               <#-- <th width="10%">模板类型</th>-->
                                <th width="10%">发送动作类型</th>
                                <th width="10%">是否默认</th>
                                <th width="18%">操作</th>
                            </tr>
                            <tr role="row" class="filter">
                                <td></td>
                                <td><input type="text" placeholder="请输入模板名" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_name"></td>
                                <td><input type="text"  placeholder="请输入内容" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_content"></td>
                             <#--   <td>
                                    <select name="search_EQ_type" class="form-control  form-filter input-sm">
                                        <option selected="selected" value="">全部</option>
                                        <option value="1">短信</option>
                                        <option value="2">消息</option>
                                    </select>
                                </td>-->
                                <td>
                                    <select name="search_EQ_actionType" class="form-control  form-filter input-sm">
                                        <option selected="selected" value="">全部</option>
                                        <option value="1">下单时</option>
                                        <option value="2">发货时</option>
                                        <option value="3">收货时</option>
                                        <option value="4">取消时</option>
                                    </select>
                                </td>
                                <td>
                                    <select name="search_EQ_isDefault" class="form-control  form-filter input-sm">
                                        <option selected="selected" value="">全部</option>
                                        <option value="0">否</option>
                                        <option value="1">是</option>
                                    </select>
                                </td>
                                <td>
                                    <button class="btn btn-sm yellow filter-submit margin-bottom"><i class="fa fa-search"></i> 搜索</button>
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
        var grid=new Datatable();
        grid.init({
            src:$("#apply_data_table"),
            onSuccess:function(grid){
            },
            onError:function(grid){
            },
            dataTable:{
                "bServerSide":true,
                "sAjaxSource":"${rc.contextPath}/sms/template/list",
                "aoColumnDefs":[
                    { "bSortable":false,"aTargets":[ 0,1,2,3,4,5] }
                ],//设置不排序得列
                "aoColumns":[
                    { "sWidth":"1%","sTitle":'<input type="checkbox" class= "checkAllBox" onclick="checkAllBox(this)"  title="全选" class="group-checkable" />',"sDefaultContent":"","mRender":function(data,type,full){
                        return '<div class="checker"  ><span class=""><input type="checkbox" class="checkboxes" checked="" name="checkBox" value="'+full.id+'"></span></div>';
                    }},
                    {  "sWidth":"10%","sTitle":"模板名","sDefaultContent":"","mRender":function (data,type,full) {
                        var a='<a href="${rc.contextPath}/sms/template/update/'+full.id+'" title="'+full.name+'" >'+full.name+'</a>';
                        return a;
                    }},
                    {  "sWidth":"45%","sTitle":"模板内容","mData":"content"},
                 /*   {  "sWidth":"10%","sTitle":"模板类型","sDefaultContent":"","mRender":function (data,type,full) {
                        if(full.type==1){
                            return "短信"
                        }else if(full.type==2){
                            return "消息"
                        }
                    }},*/
                    {  "sWidth":"10%","sTitle":"发送动作类型","sDefaultContent":"","mRender":function (data,type,full) {
                        if(full.actionType==1){
                            return "下单时"
                        }else if(full.actionType==2){
                            return "发货时"
                        }else if(full.actionType==3){
                            return "收货时"
                        }else if(full.actionType==4){
                            return "取消时"
                        }
                    }},
                    {  "sWidth":"10%","sTitle":"是否默认","sDefaultContent":"","mRender":function (data,type,full) {
                        if(full.isDefault==0){
                            return "否"
                        }else if(full.isDefault==1){
                            return "是"
                        }
                    }},
                    {"sWidth":"20%","sTitle":"操作","sDefaultContent":"","mRender":function(data,type,full){
                        var a='<@shiro.hasPermission name="sms-template-Edit"><a href="${rc.contextPath}/sms/template/update/'+full.id+'" class="btn btn-xs blue"  title="编辑" ><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>';
                        var b='';
                        var c = '';
                        if(full.isDefault==1){
                            b='<@shiro.hasPermission name="sms-template-Del"><a  href="javascript:void(0);" onclick="javascript:return false;"  class= "btn btn-xs gray" title ="需要取消默认才可删除"><i class="fa fa-trash-o"></i>删除</a></@shiro.hasPermission>';
                        }else{
                            b='<@shiro.hasPermission name="sms-template-Del"><a  href="javascript:void(0);" onclick="deleteInfo('+full.id+')"  class= "btn btn-xs red" title ="删除"><i class="fa fa-trash-o"></i>删除</a></@shiro.hasPermission>';
                            c ='<@shiro.hasPermission name="sms-template-Default"><a  href="javascript:void(0);" onclick="setDefault('+full.id+','+full.actionType+')"  class= "btn btn-xs blue" title ="设为默认"><i class="glyphicon glyphicon-cog"></i>设为默认</a></@shiro.hasPermission>'
                        }
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

        function setDefault(id,actionType) {
            bootbox.dialog({
                message:"确认设置此模板为默认模板？",
                title:"设置默认",
                buttons:{
                    main:{
                        label:"确定",
                        className:"green",
                        callback:function(){
                            $.ajax({
                                url:'${rc.contextPath}/sms/template/set-default',
                                type:'POST',
                                data:{"id":id,"actionType":actionType},
                                dataType:"json",
                                traditional:true,
                                success:function(data){
                                    if(data&&data.stat){
                                        bootboxAlert('设置成功！');
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

        /**
         * 模糊查询应用信息
         * */
        function selectAjax(appName){
            grid.setAjaxParam("templateName",appName);
            grid.getDataTable().fnDraw();
        }
        /**
         * 删除单个应用信息
         **/
        function deleteInfo(id){
            commonDelete({"id":id},'确认删除模板？','删除模板','${rc.contextPath}/sms/template/delete');
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
            commonDelete({"ids":ids},'确认批量删除商城?','批量删除商城','${rc.contextPath}/sms/mall/delete-all');
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
