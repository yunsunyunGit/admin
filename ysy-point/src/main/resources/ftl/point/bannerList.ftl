<html>
<head>
    <title>内容管理</title>
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
                <a href="${rc.contextPath}/">积分商城</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="${rc.contextPath}/application/list">内容管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
        </ul>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-cogs"></i>内容列表</div>
                <div class="actions">
                    <div class="btn-group">
                    <@shiro.hasPermission name="point-banner-Add">
                        <a href="${rc.contextPath}/point/banner/create" class="btn green">
                            <i class="fa fa-plus"></i>
                            <span class="hidden-480" >新增</span>
                        </a>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="point-banner-Del">
                        <a  class="btn red">
                            <i class="fa fa-trash-o"></i>
                            <span class="hidden-480"  onclick="deleteList();">批量删除</span>
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
                    <table class="table table-striped table-bordered table-hover" id="banner_list_table">
                        <thead>
                        <tr role="row" class="heading">
                            <th></th>
                            <th width="15%">内容名称</th>
                            <th width="15%">文字</th>
                            <th width="15%">跳转地址</th>
                            <th width="10%">类型</th>
                            <th width="10%">状态</th>
                            <th width="15%">创建时间</th>
                            <th>操作</th>
                        </tr>
                        <tr role="row" class="filter">
                            <td></td>
                            <td><input type="text" placeholder="请输入内容名称" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_name"></td>
                            <td><input type="text" placeholder="请输入内容文字" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_content"></td>
                            <td><input type="text" placeholder="请输入跳转地址" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_url"></td>
                            <td>
                                <select name="search_EQ_type" class="form-control  form-filter input-sm">
                                    <option selected="selected" value>请选择类型</option>
                                <#list bannerType as banner>
                                    <option value="${banner.id}">${banner.dicValue}</option>
                                </#list>
                                </select>
                            </td>
                            <td>
                                <select name="search_EQ_state" class="form-control  form-filter input-sm">
                                    <option selected="selected" value>请选择状态</option>
                                    <option value="0">禁用</option>
                                    <option value="1">启用</option>
                                </select>
                            </td>
                            <td></td>
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
    <script type="text/javascript">
        var grid=new Datatable();
        grid.init({
            src:$("#banner_list_table"),
            onSuccess:function(grid){
            },
            onError:function(grid){
            },
            dataTable:{
                "bServerSide":true,
                "sAjaxSource":"${rc.contextPath}/point/banner/list",
                "aoColumnDefs":[
                    { "bSortable":false,"aTargets":[ 0,2,3 ] }
                ],//设置不排序得列
                "aoColumns":[
                    { "sWidth":"1%","sTitle":'<input type="checkbox" class= "checkAllBox" onclick="checkAllBox(this)"  title="全选" class="group-checkable" />',"sDefaultContent":"","mRender":function(data,type,full){
                        return '<div class="checker"  ><span class=""><input type="checkbox" class="checkboxes" checked="" name="checkBox" value="'+full.id+'"></span></div>';
                    }},
                    {  "sWidth":"10%","sTitle":"内容名称","sDefaultContent":"","mRender":function (data,type,full) {
                        var a='<a href="${rc.contextPath}/point/banner/update/'+full.id+'" title="'+full.name+'" >'+full.name+'</a>';
                        return a;
                    }},
                    {  "sWidth":"20%","sTitle":"文字内容","mData":"content"},
                    {  "sWidth":"20%","sTitle":"链接地址","mData":"url"},
                    {  "sWidth":"10%","sTitle":"类型","mData":"typeValue"},
                    {  "sWidth":"10%","sTitle":"状态","mData":"state","sDefaultContent":"","mRender":function (data,type,full) {
                        return 1==data ? "启用":"禁用";
                    }},
                    {  "sWidth":"15%","sTitle":"创建时间","mData":"createTime"},
                    {  "sWidth":"15%","sTitle":"操作","sDefaultContent":"","mRender":function(data,type,full){
                        var a='<@shiro.hasPermission name="point-banner-Edit"><a href="${rc.contextPath}/point/banner/update/'+full.id+'" class="btn btn-xs blue"  title="编辑" ><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>';
                        var b='<@shiro.hasPermission name="point-banner-Del"><a  href="javascript:void(0);" onclick="deleteInfo('+full.id+')" class= "btn btn-xs red" title ="删除"><i class="fa fa-trash-o"></i>删除</a></@shiro.hasPermission>';
                        return "<div style='white-space: nowrap'>"+a+b+"</div>";
                    }}
                ]
            }
        });
        /**
         *  选择框选中事件
         * */
        jQuery('#banner_list_table').on('change','tbody tr .checkboxes',function(){
            $(this).parents('span').toggleClass("checked");
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
         * 删除单个应用信息
         **/
        function deleteInfo(id){
            commonDelete({"ids":id},'确认删除所选择的内容信息?','删除内容','${rc.contextPath}/point/banner/delete');
        }
        /**
         *  删除多个应用信息
         */
        function deleteList(){
            var ids=[], ch=$("#banner_list_table span.checked >input[name='checkBox']");
            if(ch){
                ch.each(function(i,n){
                    ids.push(n.value);
                });
            }
            if(ids==''||ids==null||ids.length==0){
                bootboxAlert('请选择需要删除的内容');
                return false;
            }
            commonDelete({"ids":ids},'确认批量删除内容?','批量删除内容','${rc.contextPath}/point/banner/delete');
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
                                    bootboxAlert(data.message);
                                    if(data.success){
                                        grid.getDataTable().fnDraw();
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
