<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>分类管理</title>
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
                <a href="${rc.contextPath}/">商品管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="${rc.contextPath}/point/mall/list">分类管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
        </ul>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-sitemap"></i>分类列表</div>
                <div class="actions">
                    <div class="btn-group">
                    <@shiro.hasPermission name="goods-catagory-Add">
                        <a href="${rc.contextPath}/goods/catagory/create" class="btn green">
                            <i class="fa fa-plus"></i>
                            <span class="hidden-480" >新增分类</span>
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
                    <#--<div id="search" class="table-actions-wrapper" >-->
                        <#--<b class="form-control input-inline input-small input-sm" style="border: 0px; text-align: right;">商城名称：</b>-->
                        <#--<select id="mallId" name="mallId" class="form-control">-->
                        <#--<#list mallList as mall>-->
                            <#--<option value="${mall.id}">${mall.name}</option>-->
                        <#--</#list>-->
                        <#--</select>-->
                    <#--</div>-->
                    <table class="table table-striped table-bordered table-hover" id="catagory_data_table" style="text-align: center;">
                        <thead>
                        <tr role="row" class="heading">
                            <th></th>
                            <th width="5%">分类图片</th>
                            <th width="15%">分类名称</th>
                            <th width="15%">所属商城数</th>
                            <th width="15%">状态</th>
                            <th width="10%">排序</th>
                            <th>操作</th>
                        </tr>
                        <tr role="row" class="filter">
                            <td></td>
                            <td></td>
                            <td><input type="text" placeholder="请输入分类名称" class="form-control form-filter input-sm" name="search_LIKE_name"></td>
                            <td></td>
                            <td>
                                <select name="search_EQ_isShow" class="form-control  form-filter input-sm">
                                    <option selected="selected" value>请选择</option>
                                    <option value="0">禁用</option>
                                    <option value="1">启用</option>
                                </select>
                            </td>
                            <td><input type="text" class="form-control form-filter input-sm" name="search_LIKE_seq"></td>
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
    <script type="text/javascript" src="${rc.contextPath}/assets/global/plugins/bootstrap-doublebox/doublebox-bootstrap.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/plugins/select2/select2.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/plugins/data-tables/jquery.dataTables.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script src="${rc.contextPath}/assets/global/scripts/datatable.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/common/commonUtil.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.iframe-transport.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/data-tables/jquery.dataTables.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-validation/js/lib/jquery.form.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jstree/dist/jstree.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/drags/bootstrap-paginator.js"></script>
    <script src="${rc.contextPath}/assets/global/scripts/datatable.js"></script>
    <script type="text/javascript">

        var grid=new Datatable();
        grid.init({
            src:$("#catagory_data_table"),
            onSuccess:function(grid){
            },
            onError:function(grid){
            },
            dataTable:{
                "bServerSide":true,
                "sAjaxSource":"${rc.contextPath}/goods/catagory/list",
                "aoColumnDefs":[
                    { "bSortable":false,"aTargets":[ 0,1,2,3,4,5 ] }
                ],//设置不排序得列
                "aoColumns":[
                    { "sWidth":"1%","sTitle":'<input type="checkbox" class= "checkAllBox" onclick="checkAllBox(this)"  title="全选" class="group-checkable" />',"sDefaultContent":"","mRender":function(data,type,full){
                        return '<div class="checker"  ><span class=""><input type="checkbox" class="checkboxes" checked="" name="checkBox" value="'+full.id+'"></span></div>';
                    }},
                    { "sWidth":"15%","sTitle":"分类图片","mData":"imageUrl","sDefaultContent":"","mRender":function(data,type,full){
                        if(data.indexOf('http')!=-1){
                            return '<img src="'+data+'?id='+Math.random()+'" width="60px" height="80px" style="margin:auto;" >';
                        }else{
                            return '<img src="${rc.contextPath}'+data+'?id='+Math.random()+'" width="60px" height="80px" style="margin:auto 0;" >';
                        }
                    }},
                    {  "sWidth":"20%","sTitle":"分类名称","mData":"name"},
                    {  "sWidth":"15%","sTitle":"所属商城","mData":"mallNameList"},
                    {  "sWidth":"15%","sTitle":"状态","mData":"isShow","mRender":function(data,type,full){
                        return 0 === data ? "禁用":"启用";
                    }},
                    {  "sWidth":"15%","sTitle":"排序","mData":"seq"},
                    {  "sWidth":"20%","sTitle":"操作","sDefaultContent":"","mRender":function(data,type,full){
                        var a='<@shiro.hasPermission name="goods-catagory-Edit"><a href="${rc.contextPath}/goods/catagory/update/'+full.id+'" class="btn btn-xs blue"  title="编辑" ><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>';
                        var b='<@shiro.hasPermission name="goods-catagory-Del"><a  href="javascript:void(0);" onclick="deleteInfo('+full.id+')" class= "btn btn-xs red" title ="删除"><i class="fa fa-trash-o"></i>删除</a></@shiro.hasPermission>';
                        return "<div style='white-space: nowrap'>"+a+b+"</div>";
                    }}
                ]
            }
        });
        /**
         *  选择框选中事件
         * */
        jQuery('#catagory_data_table').on('change','tbody tr .checkboxes',function(){
            $(this).parents('span').toggleClass("checked");
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
            commonDelete({"ids":id},'确认删除该分类?','删除分类','${rc.contextPath}/goods/catagory/delete');
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
                            Metronic.startPageLoading();
                            $.ajax({
                                url:url,
                                type:'POST',
                                data:data,
                                dataType:"json",
                                traditional:true,
                                success:function(data){
                                    console.log(data);
                                    Metronic.stopPageLoading();
                                    if(data.success){
                                        window.location.reload();
                                    }else{
                                        bootbox.dialog({
                                            message: data.message,
                                            buttons: {
                                                main: {
                                                    label: "确定",
                                                    className: "green",
                                                    callback: function () {
                                                        $(this).hide();
                                                    }
                                                }
                                            }
                                        });
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

        $(".settingUp-btns").hide();
    </script>
</content>
</html>
