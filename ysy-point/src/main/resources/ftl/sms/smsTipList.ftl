<html>
<head>
    <title>消息列表</title>
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
                <a href="${rc.contextPath}/">短信列表</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="${rc.contextPath}/sms/list">短信列表</a>
                <i class="fa fa-angle-right"></i>
            </li>
        </ul>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-cogs"></i>短信列表</div>
                <div class="actions">
                    <div class="btn-group">
                         <@shiro.hasPermission name="sms-tip-Read">
                             <a href="javascript:void(0);"    onclick="setReadList()" class="btn blue">
                                 <i class="fa fa-plus"></i>
                                 <span class="hidden-480" >批量标识已读</span>
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
                                <th width="20%">发送内容</th>
                                <th width="10%">发送者ID</th>
                                <th width="8%">发送者昵称/th>
                                <th width="10%">接受者ID</th>
                                <th width="8%">接受者昵称</th>
                                <th width="10%">发送时间</th>
                                <th width="10%">接受时间</th>
                                <th width="8%">读取状态</th>
                                <th width="22%">操作</th>
                            </tr>
                            <tr role="row" class="filter">
                                <td></td>
                                <td><input type="text" placeholder="请输入发送内容" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_content"></td>
                                <td><input type="text" placeholder="请输入发送者ID" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_senderId"></td>
                                <td><input type="text" placeholder="请输入发送者昵称" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_senderName"></td>
                                <td><input type="text" placeholder="请输入接受者ID" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_receiverId"></td>
                                <td><input type="text" placeholder="请输入接受者昵称" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_receiverName"></td>
                                <td></td>
                                <td></td>
                                <td>
                                    <select name="search_EQ_readStatus" class="form-control  form-filter input-sm">
                                        <option selected="selected" value="">请选择状态</option>
                                        <option value="0">未读</option>
                                        <option value="1">已读</option>
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
                "sAjaxSource":"${rc.contextPath}/sms/tip/list",
                "aoColumnDefs":[
                    { "bSortable":false,"aTargets":[ 0,1,2,3,4,5,6,7,8] }
                ],//设置不排序得列
                "aoColumns":[
                    { "sWidth":"1%","sTitle":'<input type="checkbox" class= "checkAllBox" onclick="checkAllBox(this)"  title="全选" class="group-checkable" />',"sDefaultContent":"","mRender":function(data,type,full){
                        return '<div class="checker"  ><span class=""><input type="checkbox" class="checkboxes" checked="" name="checkBox" value="'+full.id+'"></span></div>';
                    }},
                    {  "sWidth":"20%","sTitle":"消息内容","mData":"content"},
                    {  "sWidth":"10%","sTitle":"发送者ID","mData":"senderId"},
                    {  "sWidth":"8%","sTitle":"发送者昵称","mData":"senderName"},
                    {  "sWidth":"10%","sTitle":"接受者ID","mData":"receiverId"},
                    {  "sWidth":"8%","sTitle":"接受者昵称","mData":"receiverName"},
                    {  "sWidth":"10%","sTitle":"发送时间","mData":"sendTime","sDefaultContent":"","mRender":function(data,type,full){
                        var time='';
                        if(data){
                            time=new Date(data).format('yyyy-MM-dd hh:mm:ss');
                        }
                        return time;
                    }},
                    {  "sWidth":"10%","sTitle":"接受时间","mData":"receiveTime","sDefaultContent":"","mRender":function(data,type,full){
                        var time='';
                        if(data){
                            time=new Date(data).format('yyyy-MM-dd hh:mm:ss');
                        }
                        return time;
                    }},
                    {  "sWidth":"8%","sTitle":"读取状态","sDefaultContent":"","mRender":function(data,type,full){
                        if(full.readStatus==0){
                            return "未读";
                        }else if(full.readStatus==1){
                            return "已读";
                        }
                    }},
                    {"sWidth":"22%","sTitle":"操作","sDefaultContent":"","mRender":function(data,type,full){
                        var a='';
                        if (full.readStatus==0) {
                            a='<@shiro.hasPermission name="sms-tip-Read"><a href="javascript:void(0);"  onclick="setRead('+full.id+')" class="btn btn-xs blue"  title="标记已读" ><i class="fa fa-plus"></i>标记已读</a></@shiro.hasPermission>';
                        }
                        return "<div style='white-space: nowrap'>"+a+"</div>";
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

        function  setReadList(){
            var ids=[], finIds=[], ch=$("#apply_data_table span.checked >input[name='checkBox']");
            if(ch){
                ch.each(function(i,n){
                    ids.push(n.value);
                });
            }
            if(ids==''||ids==null||ids.length==0){
                bootboxAlert('请选择需要标记已读的消息');
                return false;
            }
            $.ajax({
                url:'${rc.contextPath}/sms/tip/set-read-list',
                type:'POST',
                data:{"ids":ids},
                dataType:"json",
                traditional:true,
                success:function(data){
                    if(data&&data.stat){
                        bootboxAlert(data.msg);
                        grid.getDataTable().fnDraw();
                    }else{
                        bootboxAlert(data.msg);
                    }
                }
            });
        }

        function setRead(id) {
            $.ajax({
                url:'${rc.contextPath}/sms/tip/set-read',
                type:'POST',
                data:{"id":id},
                dataType:"json",
                traditional:true,
                success:function(data){
                    if(data&&data.stat){
                        bootboxAlert(data.msg);
                        grid.getDataTable().fnDraw();
                    }else{
                        bootboxAlert(data.msg);
                    }
                }
            });
        }


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

    </script>
</content>
</html>
