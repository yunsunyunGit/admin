<html>
<head>
    <title>经销商管理</title>
    <link rel="stylesheet" href="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.css" type="text/css"/>
    <link rel="stylesheet" href="${rc.contextPath}/assets/global/plugins/select2/select2.css" type="text/css"/>
    <link rel="stylesheet" href="${rc.contextPath}/assets/global/plugins/bootstrap/css/bootstrap.css" type="text/css"/>
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
                <a href="#">经销商管理</a>
            </li>
        </ul>
    </div>
</div>
<div class="row main">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-group"></i>商家列表
                </div>
                <div class="actions">
                    <@shiro.hasPermission name="point-merchant-Add">
                        <a href="${rc.contextPath}/point/merchant/create" class="btn green">
                            <i class="fa fa-plus"></i>
                            <span class="hidden-480">新增经销商</span>
                        </a>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="point-merchant-ResetPwd">
                        <a href="javascript:void(0);" onclick="resetPass(null)" class="btn blue">
                            <i class="fa fa-reply"></i>
                            <span class="hidden-480">批量重置密码</span>
                        </a>
                    </@shiro.hasPermission>
                </div>
            </div>
            <div class="row">
            </div>
            <div class="portlet-body">
            <#if message>
                <div class="alert alert-success">
                    <button data-dismiss="alert" class="close">×</button>
                ${(message)!}
                </div>
            </#if>
                <div class="table-container">
                    <table class="table table-striped table-bordered table-hover" id="emp_list_table">
                        <thead>
                        <tr role="row" class="heading">
                        	<th width="5%"><input type="checkbox" class="group-checkable"></th>
                            <th width="5%">照片</th>
                            <th width="10%">登录名</th>
                            <th width="10%">姓名</th>
                            <th width="15%">邮箱</th>
                            <th width="10%">状态</th>
                            <th width="10%">类型</th>
                            <th width="18%">操作</th>
                        </tr>
                        <tr role="row" class="filter">
                            <td></td>
                            <td></td>
                            <td><input type="text" placeholder="请输入商家登录名" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_loginName"></td>
                            <td><input type="text" placeholder="请输入商家姓名" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_userName"></td>
                            <td><input type="text" placeholder="请输入商家邮箱" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_email"></td>
                            <td>
                                <select name="search_EQ_job" class="form-control  form-filter input-sm">
                                    <option selected="selected" value>请选择类型</option>
                                    <option value="ENABLE">启用</option>
                                    <option value="DISABLE">禁用</option>
                                </select>
                            </td>
                            <td></td>
                            <td>
                                <button class="btn btn-sm yellow filter-submit margin-bottom"><i class="fa fa-search"></i> 搜索</button>
                                <button class="btn btn-sm red filter-cancel"><i class="fa fa-times"></i>重置</button>
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
    <script src="${rc.contextPath}/assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/select2/select2.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/data-tables/jquery.dataTables.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/common/commonUtil.js"></script>
    <script type="text/javascript">
        var grid=new Datatable();
        grid.init({
            src:$('#emp_list_table'),
            onSuccess:function(grid){
            },
            onError:function(grid){
            },
            dataTable:{
                "bServerSide":true,
                "sAjaxSource":"${rc.contextPath}/point/merchant",
                "aoColumnDefs":[
                    { "bSortable":false,"aTargets":[ 0,6] }
                ],//设置不排序得列
                "aoColumns":[
                	{ "mData":"id","mRender":function(data,type){
		                return'<input type="checkbox" class="group-checkable" value="'+data+'">';
		            }},
                    { "sTitle":"商家logo","mData":"imageAddress","sDefaultContent":"","mRender":function(data,type,full){
                        if(data){
                            if(data.indexOf('http')!=-1){
                                return '<img src="'+data+'?id='+Math.random()+'" width="60px" height="80px" style="margin:auto 0;" >';
                            }else{
                                return '<img src="${rc.contextPath}'+data+'?id='+Math.random()+'" width="60px" height="80px" style="margin:auto 0;" >';
                            }
                        }else{
                            return '<img src="${rc.contextPath}'+'/assets/global/img/default.jpg'+'" width="60px" height="80px" style="margin:auto 0;" >';
                        }
                    }},
                    { "sTitle":"登录名","mData":"loginName","mRender":function(data,type,row){
                        return'<a href="${rc.contextPath}/point/merchant/update/'+row.id+'">'+data+'</a>';
                    }},
                    { "sTitle":"商家名","mData":"userName"},
                    { "sTitle":"邮箱","mData":"email","mRender":function(data){
                        return "<a href='mailto:"+data+"'>"+data+"</a>";
                    }},
                    { "sTitle":"状态","mData":"job","sDefaultContent":"","mRender":function(data){
                        return "ENABLE"==data?"启用":"禁用";
                    }},
                    { "sTitle":"类型","mData":"userType","mRender":function(data){
                        return "merchant"==data?"经销商":"运营管理员";
                    }},
                    { "sTitle":"操作","mData":"id","mRender":function(data,type,row){
                        return '<@shiro.hasPermission name="point-merchant-Del"><a class="btn btn-xs red " href="javascript:void(0);" onclick="deleteOne('+row.id+')" ><i class="fa fa-trash-o"></i>删除</a></@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="point-merchant-Edit"><a class="btn btn-xs green" href="${rc.contextPath}/point/merchant/update/'+data+'"><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="point-merchant-ResetPwd"><a class="btn btn-xs blue" href="javascript:void(0);" onclick="resetPass('+data+')"><i class="fa fa-reply"></i>重置密码</a></@shiro.hasPermission>';
                    }}
                ]
            }
        });

        function deleteOne(id){
            bootbox.dialog({
            	message: "您是否确认删除?一旦删除将从已入住的商城的此商家也将会被删除",
                buttons: {
                	success: {
                     	label: "确定",
                     	className: "green",
                     	callback: function() {
                        	Metronic.startPageLoading();
                        	$.ajax({
	                            url:'${rc.contextPath}/point/merchant/delete/'+id,
	                            type:'POST',
	                            dataType:"json",
	                            traditional:true,
	                            success:function(data){
                                	Metronic.stopPageLoading();
                                	grid.getDataTable().fnDraw();
                            	}
                        	});
                     	}
                   	},
                	main: {
                    	label: "取消",
                    	className: "gray",
                    	callback: function() {
                        	$(this).hide();
                     	}
                 	}
            	}
            });
        }
        
        /**
		 * 批量重置
		 * @param data  用户的ids
		 */
		function resetPass(data){
		    var ids=[];
		    if(data){
		        ids.push(data);
		    }else{
		        $.each(grid.getSelectedRows(),function(key,val){
		            ids.push(val['value']);
		        });
		        if(ids.length==0){
		            Metronic.alert({type:'danger',icon:'warning',message:'请选择需要重置的员工',container:grid.getTableWrapper(),place:'prepend'});
		            setTimeout("$('.alert').alert('close');",4000);
		            return;
		        }
		    }
		    bootbox.dialog({
		         message: "您是否确认重置密码?",
		         buttons: {
		           main: {
		             label: "确定",
		             className: "green",
		             callback: function() {
		                Metronic.startPageLoading();
		                $.ajax({
		                    url:'${rc.contextPath}/system/user/resetPass',
		                    type:'POST',
		                    data:{"ids":ids},
		                    dataType:"json",
		                    traditional:true,
		                    success:function(data){
		                        Metronic.stopPageLoading();
		                        if(data.stat){
		                            grid.getDataTable().fnDraw();
		                        }
		                        bootboxAlert(data.msg)
		                    }
		                });
		             }
		           },
		           cancel: {
		           	 label: "取消",
		             className: "gray",
		             callback: function() {
		                $(this).hide();
		             }
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
                $(".alert").alert('close');
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