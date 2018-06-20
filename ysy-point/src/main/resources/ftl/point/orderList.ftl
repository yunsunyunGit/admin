<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>订单管理</title>
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
                <a href="${rc.contextPath}/">订单管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="${rc.contextPath}/point/order/list">订单管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
        </ul>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-cogs"></i>订单管理</div>
                <div class="actions">
                    <div class="btn-group">
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
                        <b class="form-control input-inline input-small input-sm" style="border: 0px; text-align: right;">商城选择：</b>
                        <select name="mallName" id="mallName"
                                style="width: 150px;" class="table-group-action-input form-control select2me">
                            <option value="" selected="selected">全部</option>
                            <#list mallList as mall>
                                 <option value="${mall.id}">${mall.name}</option>
                            </#list>
                        </select>
                       <#-- <button class="btn btn-sm yellow table-group-action-submit"><i class="fa fa-search"></i> 搜索</button>
                        <button class="btn btn-sm red table-group-action-times"><i class="fa fa-times"></i> 重置</button>-->
                    </div>
                    <table class="table table-striped table-bordered table-hover" id="apply_data_table">
                        <thead>
                            <tr role="row" class="heading">
                                <th width="1%"><input type="checkbox" class="group-checkable"></th>
                                <th width="10%">订单号</th>
                                <#if userRole=='platformma'>
                                <th width="10%">商家</th>
                                </#if>
                                <th width="20%">下单日期</th>
                                <th width="10%">兑换积分</th>
                                <th width="10%">收货人</th>
                                <th width="10%">订单状态</th>
                                <th width="10%">操作</th>
                            </tr>

                            <tr role="row" class="filter">
                                <td></td>
                                <td><input type="text" class="form-control form-filter input-sm" autocomplete="off" placeholder="请输入订单编号" name="search_EQ_orderNum"></td>
                                <#if userRole=='platformma'>
                                    <td>
                                        <select name="search_EQ_merchantId" class="form-control  form-filter input-sm">
                                            <option selected="selected" value>请选择商家</option>
                                            <#list merchantList as merchant >
                                                 <option value="${merchant.id}">${merchant.userName}</option>
                                            </#list>
                                        </select>
                                    </td>
                                </#if>
                                <td>
                                    <div id="beginTime" class="input-group date date-picker margin-bottom-5" data-date-format="yyyy-mm-dd">
                                        <input readonly="true" id="begin"  type="text" autocomplete="off" class="form-control form-filter  input-sm" name="search_LIKE_orderStartTime" placeholder="开始时间">
                                        <span class="input-group-btn"><button class="btn btn-sm default" type="button"><i class="fa fa-calendar"></i></button></span>
                                    </div>
                                    <div id="endTime" class="input-group date date-picker" data-date-format="yyyy-mm-dd">
                                        <input readonly="true" id="end" type="text" autocomplete="off" class="form-control form-filter  input-sm" name="search_LIKE_orderEndTime" placeholder="结束时间">
                                        <span class="input-group-btn"><button class="btn btn-sm default" type="button"><i class="fa fa-calendar"></i></button></span>
                                    </div>
                                </td>
                                <td>
                                    <select name="search_EQ_orderGoodsPoint" autocomplete="off" class="form-control  form-filter input-sm">
                                        <option selected="selected" value>请选择兑换积分</option>
                                        <#list  knDictionaryList as dictionary >
                                            <option  value="${dictionary.dicValue}">${dictionary.dicValue}</option>
                                        </#list>
                                    </select>
                                </td>
                                <td><input type="text" autocomplete="off" class="form-control form-filter input-sm" placeholder="请输入收货人"  name="search_LIKE_receiverName"></td>
                                <td>
                                    <select name="search_EQ_orderStatus" class="form-control  form-filter input-sm">
                                        <option selected="selected" value>请选择订单状态</option>
                                        <#list  orderStatusList as orderStatus >
                                            <option  value="${orderStatus.orderTypeStatus}">${orderStatus.orderTypeString}</option>
                                        </#list>
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

                <!-- 发货begin -->
                <@shiro.hasPermission name="point-order-Send">
                <div id="order_logi" class="modal fade" tabindex="-1" aria-hidden="true"  >
                    <input type="hidden" id="orderId"  value=""/>
                    <div class="modal-dialog" style="width: 500px;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                <div class="portlet-title"  >
                                    <div class="caption" style="color:#cb0101" ><i class="fa fa-cogs"></i>订单发货</div>
                                </div>
                            </div>
                            <div class="modal-body form">
                                <form method="POST" class="form-horizontal"  id="messform" name="messform" >
                                <div class="form-body">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label" id="userinfoxCol">快递公司</label>
                                        <div class="col-md-6">
                                            <select id="logiId"  name="logiId"  class="form-control" style="width:300px;">
                                                <#list  expressList as express >
                                                    <option  value="${express.id}">${express.dicValue}</option>
                                                </#list>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">快递单号</label>
                                        <div class="col-md-6">
                                            <input type="text" id="logiNum" name="logiNum" autocomplete="off" class="form-control" style="width:300px;"  />
                                        </div>
                                    </div>
                                </div>
                                </form>
                                <div class="modal-footer">
                                    <button class="btn green" onclick="saveExpreeNum();">确定</button>
                                    <button class="btn" data-dismiss="modal" aria-hidden="true">返回</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </@shiro.hasPermission>
                <!-- 发货end -->


                <!-- 取消订单begin -->
                <@shiro.hasPermission name="point-order-Cancel">
                <div id="order_cancel" class="modal fade" tabindex="-1" aria-hidden="true"  >
                    <input type="hidden" id="cancelOrderId"  value=""/>
                    <div class="modal-dialog" style="width: 800px;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                <div class="portlet-title"  >
                                    <div class="caption" style="color:#cb0101" ><i class="fa fa-cogs"></i>取消订单</div>
                                </div>
                            </div>
                            <div class="modal-body form">
                                <form method="POST" class="form-horizontal"  id="cancelform" name="messform" >
                                    <div class="form-body">
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">取消原因</label>
                                            <div class="col-md-9">
                                                <textarea id="cancelReason"  name="cancelReason"  class="form-control"  tyle="width:300px;"   rows="5"  ></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <div class="modal-footer">
                                    <button class="btn green" onclick="cancelOrder();">确定</button>
                                    <button class="btn" data-dismiss="modal" aria-hidden="true">返回</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </@shiro.hasPermission>
                <!-- 取消订单end -->


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
                "sAjaxSource":"${rc.contextPath}/point/order/list",
                "aoColumnDefs":[
                    { "bSortable":false,"aTargets":[ 0,1,2,3,4,5,6,7] }
                ],//设置不排序得列
                "aoColumns":[
                    { "sWidth":"1%","sTitle":'<input type="checkbox" class= "checkAllBox" onclick="checkAllBox(this)"  title="全选" class="group-checkable" />',"sDefaultContent":"","mRender":function(data,type,full){
                        return '<div class="checker"  ><span class=""><input type="checkbox" class="checkboxes" checked="" name="checkBox" value="'+full.id+'"></span></div>';
                    }},
                    {  "sWidth":"20%","sTitle":"订单编号","sDefaultContent":"","mRender":function(data,type,full){
                        var a='<a href="${rc.contextPath}/point/order/detail/'+full.id+'" title="'+full.orderNum+'" >'+full.orderNum+'</a>';
                        return a;
                    }},
                    <#if userRole=='platformma'>
                        {  "sWidth":"10%","sTitle":"商家","mData":"userName"},
                    </#if>
                    {  "sWidth":"20%","sTitle":"下单日期","mData":"createTime","sDefaultContent":"","mRender":function(data,type,full){
                        var time='';
                        if(data){
                            time=new Date(data).format('yyyy-MM-dd hh:mm:ss');
                        }
                        return time;
                    }},
                    {  "sWidth":"10%","sTitle":"兑换积分","mData":"orderGoodsPoint"},
                    {  "sWidth":"15%","sTitle":"收货人","mData":"receiverName"},
                    {  "sWidth":"15%","sTitle":"订单状态","sDefaultContent":"","mRender":function(data,type,full){
                        if(full.orderStatus==0){
                            return "待发货";
                        }else if(full.orderStatus==1){
                            return "已发货";
                        }else if(full.orderStatus==2){
                            return "已完成";
                        }else if(full.orderStatus==3){
                            return "已取消";
                        }
                    }},
                    {  "sWidth":"20%","sTitle":"操作","sDefaultContent":"","mRender":function(data,type,full){
                        var a='';
                        var b='';
                        if (full.orderStatus==0){
                            a='<@shiro.hasPermission name="point-order-Send"><a href="javascript:void(0);"   onclick="sendGoods('+ full.id +')"  class="btn btn-xs blue"  title="发货" ><i class="fa icon-envelope-letter"></i>发货</a></@shiro.hasPermission>';
                        }
                        if (full.orderStatus==0||full.orderStatus==1) {
                            b = '<@shiro.hasPermission name="point-order-Cancel"><a  href="javascript:void(0);" onclick="dailogCencel('+ full.id +')" class= "btn btn-xs red" title ="取消订单"><i class="fa fa-trash-o"></i>取消订单</a></@shiro.hasPermission>';
                        }
                        return "<div style='white-space: nowrap'>"+a+b+"</div>";
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

        /**
         * 时间控件查询
         * */
        $('#mallName').on('change',function(){
            var mallId = $("#mallName").val();
            grid.setAjaxParam("search_EQ_mallId",mallId);
            grid.getDataTable().fnDraw();
        });
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

        function sendGoods(orderId) {
            $('#orderId').val(orderId);
            $('#order_logi').modal('show');
        }

        function saveExpreeNum() {
            var orderId = $("#orderId").val();
            var logiId = $('#logiId').val();
            var logiNum = $('#logiNum').val();
            $.ajax({
                url:'${rc.contextPath}/point/order/send-goods',
                type:'POST',
                data:{"orderId":orderId,"logiId":logiId,"logiNum":logiNum},
                dataType:"json",
                traditional:true,
                success:function(data){
                    if(data&&data.stat){
                        alertHint(data.msg);
                        $('#order_logi').modal('hide');
                        $('#logiNum').val("");
                        grid.getDataTable().fnDraw();

                    }else{
                        $('#order_logi').modal('hide');
                        bootboxAlert(data.msg);
                    }
                }
            });
            $('#order_logi').modal('hide');
        }


        function dailogCencel(orderId) {
            $("#cancelOrderId").val(orderId);
            $('#order_cancel').modal('show');
            $("#cancelReason").val("");
        }
        
        function cancelOrder() {
            var id = $("#cancelOrderId").val();
            var cancelReason = $("#cancelReason").val();
            bootbox.dialog({
                message:'你确定要取消这笔订单吗？',
                title:'取消订单',
                buttons:{
                    main:{
                        label:"确定",
                        className:"green",
                        callback:function(){
                            $.ajax({
                                url:'${rc.contextPath}/point/order/cancel-order',
                                type:'POST',
                                data:{"orderId":id,"cancelReason":cancelReason},
                                dataType:"json",
                                traditional:true,
                                success:function(data){
                                    if(data&&data.stat){
                                        bootboxAlert('取消成功！');
                                        $('#order_cancel').modal('hide');
                                        $('#cancelReason').val("");
                                        grid.getDataTable().fnDraw();
                                    }else{
                                        bootboxAlert(data.msg);
                                        $('#order_cancel').modal('hide');
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
                            $('#order_cancel').modal('hide');
                        }
                    }
                }
            });
        }
        

    </script>
</content>
</html>
