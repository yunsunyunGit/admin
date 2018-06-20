<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>订单详情</title>
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
                <a href="${rc.contextPath}/">订单详情</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="${rc.contextPath}/point/order/list">订单详情</a>
                <i class="fa fa-angle-right"></i>
            </li>
        </ul>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-cogs"></i>订单号：${ptOrder.orderNum}</div>
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

                    <table class="table table-striped table-bordered table-hover" id="apply_data_table">
                        <thead>
                            <tr role="row" class="heading">
                                <th width="10%">商品编号</th>
                                <th width="20%">商品名称</th>
                                <th width="20%">购买数量</th>
                                <th width="10%">兑换积分</th>
                                <th width="10%">消费总积分</th>
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


<div class="row">
    <div class="col-md-4">
        <div class="portlet box green-haze"  style="height: 230px;background-color: #ffffff" >
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-cogs"></i>订单信息</div>
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
                    <div class="form-group">
                        <label class="col-md-3 control-label">订单号：</label>
                        <div  class="hiddenDiv">${ptOrder.orderNum}</div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">订单状态：</label>
                            <#if ptOrder.orderStatus==0>
                                <div  class="hiddenDiv">待发货</div>
                            </#if>
                            <#if ptOrder.orderStatus==1>
                                <div  class="hiddenDiv">已发货</div>
                            </#if>
                            <#if ptOrder.orderStatus==2>
                                <div  class="hiddenDiv">已完成</div>
                            </#if>
                            <#if ptOrder.orderStatus==3>
                                <div  class="hiddenDiv">已取消</div>
                            </#if>
                    </div>
                    <#if ptOrder.orderStatus==1>
                        <div class="form-group">
                            <label class="col-md-3 control-label">快递公司：</label>
                            <div  class="hiddenDiv">${logiCompy.dicValue}</div>
                        </div>
                    </#if>
                    <#if ptOrder.orderStatus==1>
                        <div class="form-group">
                            <label class="col-md-3 control-label">物流单号：</label>
                            <div  class="hiddenDiv">${ptOrder.logiNum}</div>
                        </div>
                    </#if>
                    <div class="form-group">
                        <label class="col-md-3 control-label">消费总积分：</label>
                        <div  class="hiddenDiv">${ptOrder.orderGoodsPoint}</div>
                    </div>
            </div>
        </div>
    </div>

    <div class="col-md-4">
        <div class="portlet box green-haze"  style="height: 230px;background-color: #ffffff">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-cogs"></i>商家信息</div>
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
                <div class="form-group">
                    <label class="col-md-3 control-label">商家名：</label>
                    <div  class="hiddenDiv">${merchant.userName}</div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">电话：</label>
                    <div  class="hiddenDiv">${merchant.phone}&nbsp;&nbsp;</div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">邮箱：</label>
                    <div  class="hiddenDiv">${merchant.email}&nbsp;&nbsp;</div>
                </div>
            </div>
        </div>
    </div>


    <div class="col-md-4">
        <div class="portlet box green-haze" style="height: 230px;background-color: #ffffff">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-cogs"></i>会员信息</div>
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
                <div class="form-group">
                    <label class="col-md-3 control-label">用户名：</label>
                    <div  class="hiddenDiv">${userName}&nbsp;&nbsp;&nbsp;</div>
                </div>

                <div class="form-group">
                    <label class="col-md-3 control-label">电话：</label>
                    <div  class="hiddenDiv">${phoneNum}&nbsp;&nbsp;</div>
                </div>
            </div>
        </div>
    </div>


    <div class="col-md-4">
        <div class="portlet box green-haze" style="height: 230px;background-color: #ffffff">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-cogs"></i>收货人信息</div>
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
                <div class="form-group">
                    <label class="col-md-3 control-label">收件人：</label>
                    <div  class="hiddenDiv">${address.receiverName}&nbsp;&nbsp;&nbsp;</div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">电话：</label>
                    <div  class="hiddenDiv">${address.receiverPhone}&nbsp;&nbsp;&nbsp;</div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">收货地址：</label>
                    <div  class="hiddenDiv">${address.province}-${address.city}-${address.region}${address.detailAddress}&nbsp;&nbsp;&nbsp;</div>
                </div>
            </div>
        </div>
    </div>


    <div class="col-md-4">
        <div class="portlet box green-haze" style="height: 230px;background-color: #ffffff">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-cogs"></i>订单日志</div>
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
                <div class="form-group">
                    <label class="col-md-3 control-label">创建时间：</label>
                    <div  class="hiddenDiv" id="createTime" ></div>
                </div>
            <#if ptOrder.orderStatus==1 >
                <div class="form-group">
                    <label class="col-md-3 control-label">发货时间：</label>
                    <div  class="hiddenDiv" id="shipTime"  ></div>
                </div>
            </#if>
            <#if ptOrder.orderStatus==2>
                <div class="form-group">
                    <label class="col-md-3 control-label">签收时间：</label>
                    <div  class="hiddenDiv" id="completTime"  ></div>
                </div>
            </#if>
            <#if ptOrder.orderStatus==3>
                <div class="form-group">
                    <label class="col-md-3 control-label">取消时间：</label>
                    <div  class="hiddenDiv" id="cancelTime" ></div>
                </div>
            </#if>
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
                "sAjaxSource":"${rc.contextPath}/point/order/detail-list/${ptOrder.id}",
                "aoColumnDefs":[
                    { "bSortable":false,"aTargets":[ 0,1,2,3,4] }
                ],//设置不排序得列
                "aoColumns":[
                    {  "sWidth":"10%","sTitle":"商品编号","mData":"sn"},
                    {  "sWidth":"10%","sTitle":"商品名称","mData":"name"},
                    {  "sWidth":"10%","sTitle":"购买数量","mData":"num","sDefaultContent":"","mRender":function(data,type,full){
                        var tempNum='x'+full.num;
                        return tempNum;
                    }},
                    {  "sWidth":"10%","sTitle":"兑换积分","mData":"point"},
                    {  "sWidth":"10%","sTitle":"消费总计","sDefaultContent":"","mRender":function(data,type,full){
                        var sumPoint = full.num*full.point;
                        return  sumPoint;
                    }}
                ]
            }
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

        $("#createTime").html(formatDateTime(${ptOrder.createTime}));
        if(${ptOrder.orderStatus}==1){
            $("#shipTime").html(formatDateTime(${ptOrder.shipTime}));
        }
        if(${ptOrder.orderStatus}==2){
            $("#completTime").html(formatDateTime(${ptOrder.completeTime}));
        }
        if(${ptOrder.orderStatus}==3){
            $("#cancelTime").html(formatDateTime(${ptOrder.cancelTime}));
        }

        function formatDateTime(timeStamp) {
            var date = new Date();
            date.setTime(timeStamp);
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            m = m < 10 ? ('0' + m) : m;
            var d = date.getDate();
            d = d < 10 ? ('0' + d) : d;
            var h = date.getHours();
            h = h < 10 ? ('0' + h) : h;
            var minute = date.getMinutes();
            var second = date.getSeconds();
            minute = minute < 10 ? ('0' + minute) : minute;
            second = second < 10 ? ('0' + second) : second;
            return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
        };


    </script>
</content>
</html>
