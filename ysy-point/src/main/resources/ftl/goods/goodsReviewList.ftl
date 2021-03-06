<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>商品审核</title>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/select2/select2.css"/>
    <link rel="stylesheet" href="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/bootstrap-datepicker/css/datepicker.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/bootstrap-doublebox/doublebox-bootstrap.css"/>
    <link href="${rc.contextPath}/assets/global/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
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
                <a href="${rc.contextPath}/goods/review">商品审核</a>
                <i class="fa fa-angle-right"></i>
            </li>
        </ul>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-bars"></i>商品审核</div>
                <div class="actions">
                    <div class="btn-group">
                    <@shiro.hasPermission name="goods-review-Audit">
                        <button onclick="auditList(1)" class="btn blue">
                            <i class="fa fa-plus"></i>
                            <span class="hidden-480">批量同意</span>
                        </button>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="goods-review-Reject">
                        <button onclick="auditList(2)" class="btn red">
                            <i class="fa fa-times"></i>
                            <span class="hidden-480">批量拒绝</span>
                        </button>
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
                    <div id="search" class="table-actions-wrapper" style="100px;">
                        <#--<b class="form-control input-inline input-small input-sm" style="border: 0px; text-align: right;">商城名称：</b>
                        <select id="mallId" name="mallId" class="form-control form-control table-group-action-input form-control input-inline input-small input-sm">
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
                            <th width="5%">商品图片</th>
                            <th width="15%">商品编号</th>
                            <th width="15%">商品名称</th>
                            <th width="15%">参考价</th>
                            <th width="10%">兑换积分</th>
                            <th width="10%">商家</th>
                            <th width="10%">审核状态</th>
                            <th>操作</th>
                        </tr>
                        <tr role="row" class="filter">
                            <td></td>
                            <td></td>
                            <td><input type="text" placeholder="请输入商品编号" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_sn"></td>
                            <td><input type="text" placeholder="请输入商品名称" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_name"></td>
                            <td>
                                <select name="search_LIKE_price" class="form-control form-filter input-sm">
                                    <option selected="selected" value="">请选择参考价</option>
                                <#list priceList as price>
                                    <option value="${price.dicValue}">${price.dicValue}</option>
                                </#list>
                                </select>
                            </td>
                            <td>
                                <select name="search_LIKE_point" class="form-control  form-filter input-sm">
                                    <option selected="selected" value="">请选择兑换积分</option>
                                <#list pointList as point>
                                    <option value="${point.dicValue}">${point.dicValue}</option>
                                </#list>
                                </select>
                            </td>
                            <td>
                                <select name="search_EQ_merchant" class="form-control  form-filter input-sm">
                                    <option selected="selected" value="">请选择商家</option>
                                <#list merchantList as merchant>
                                    <option value="${merchant.id}">${merchant.userName}</option>
                                </#list>
                                </select>
                            </td>
                            <td>
                                <select name="search_EQ_auditState" class="form-control  form-filter input-sm">
                                    <option selected="selected" value="">请选择审核状态</option>
                                    <#list auditStateList as auditState>
                                       <option value="${auditState.auditStateCode}">${auditState.auditStateName}</option>
                                    </#list>
                                </select>
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

                <!-- 发货begin -->
                <div id="goods_audit" class="modal fade" tabindex="-1" aria-hidden="true"  >
                    <input type="hidden" id="goodsId"  value=""/>
                    <input type="hidden" id="goodsIds"  value=""/>
                    <input type="hidden" id="isAllAudit"  value=""/>
                    <div class="modal-dialog" style="width: 700px;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                <div class="portlet-title"  >
                                    <div class="caption" style="color:#cb0101" ><i class="fa fa-cogs"></i>商品审核</div>
                                </div>
                            </div>
                            <div class="modal-body form">
                                <form method="POST" class="form-horizontal"  id="messform" name="messform" >
                                    <div class="form-body">
                                        <div class="form-group">
                                           <label class="col-md-2 control-label">拒绝原因</label>
                                            <div class="col-md-9">
                                                <#--<input type="text" id="logiNum" name="logiNum"  class="form-control" style="width:300px;"  />-->
                                                <textarea id="denialReason"  name="denialReason" placeholder="请输入拒绝原因"  class="form-control"  tyle="width:300px;"   rows="5"  ></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <div class="modal-footer">
                                    <button class="btn green" onclick="auditRefuseGoods();">确定</button>
                                    <button class="btn" data-dismiss="modal" aria-hidden="true">返回</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 发货end -->


                <!-- 发货begin -->
                <div id="goods_audit_agreed" class="modal fade" tabindex="-1" aria-hidden="true"  >
                    <input type="hidden" class="goodsId"  value=""/>
                    <input type="hidden" class="goodsIds"  value=""/>
                    <input type="hidden" class="isAllAudit"  value=""/>
                    <div class="modal-dialog" style="width: 450px;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                <div class="portlet-title"  >
                                    <div class="caption" style="color:#cb0101" ><i class="fa fa-cogs"></i>商品审核</div>
                                </div>
                            </div>
                            <div class="modal-body form">
                                <form method="POST" class="form-horizontal"   >
                                    <div class="form-body">
                                        <div class="form-group">
                                            <label class="col-md-12">您确定要同意该商品的审核吗?</label>
                                        </div>
                                        <div class="form-group" style="margin-left: 0px" >
                                            <#--<label class="col-md-12 control-label">您确定要同意该商品的审核并上架到商城吗?</label>-->
                                            <input  class="col-md-12" id="isUpMall"  checked="checked" type="checkbox" name="isUpMall" >上架到商城</input>
                                        </div>
                                    </div>
                                </form>
                                <div class="modal-footer">
                                    <button class="btn green" onclick="auditAgreedGoods()">确定</button>
                                    <button class="btn" data-dismiss="modal" aria-hidden="true">返回</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 发货end -->


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
    <script src="${rc.contextPath}/assets/global/plugins/layui/layui.js"></script>
    <script type="text/javascript">
        var grid=new Datatable();
        grid.init({
            src:$("#goods_data_table"),
            onSuccess:function(grid){
            },
            onError:function(grid){
            },
            dataTable:{
                "bServerSide":true,
                "sAjaxSource":"${rc.contextPath}/goods/review/list",
                "aoColumnDefs":[
                    { "bSortable":false,"aTargets":[ 0,1,2,3,4,6,7,8 ] }
                ],//设置不排序得列
                "aoColumns":[
                    { "sWidth":"1%","sTitle":'<input type="checkbox" class= "checkAllBox" onclick="checkAllBox(this)"  title="全选" class="group-checkable" />',"sDefaultContent":"","mRender":function(data,type,full){
                        return '<div class="checker"  ><span class=""><input type="checkbox" class="checkboxes" checked="" name="checkBox" data-audit="'+full.auditState+'" value="'+full.goodsId+'"></span></div>';
                    }},
                    { "sWidth":"10%","sTitle":"商品图片","mData":"imageUrl","sDefaultContent":"","mRender":function(data,type,full){
                        if(data.indexOf('http')!=-1){
                            return '<img src="'+data+'?id='+Math.random()+'" width="60px" height="80px" style="margin:auto;" >';
                        }else{
                            return '<img src="${rc.contextPath}/goods/review'+data+'?id='+Math.random()+'" width="60px" height="80px" style="margin:auto 0;" >';
                        }
                    }},
                    {  "sWidth":"10%","sTitle":"商品编号","mData":"sn","sDefaultContent":"","mRender":function(data,type,full){
                        return "<a href='${rc.contextPath}/goods/review/detail/"+full.goodsId+"'>"+data+"</a>";
                    }},
                    {  "sWidth":"15%","sTitle":"商品名称","mData":"name","sDefaultContent":"","mRender":function(data,type,full){
                        return "<a href='${rc.contextPath}/goods/review/detail/"+full.goodsId+"'>"+data+"</a>";
                    }},
                    {  "sWidth":"10%","sTitle":"参考价","mData":"referencePrice","mRender":function(data,type,full){
                        return "￥<del>"+data+"</del>";
                    }},
                    {  "sWidth":"10%","sTitle":"兑换积分","mData":"point","mRender":function(data,type,full){
                        return "<span style='color:red;'>"+data+"积分</span>";
                    }},
                    {  "sWidth":"15%","sTitle":"商家","mData":"merchantId","mRender":function(data,type,full){
                        return $("select[name='search_EQ_merchant'] option[value='"+data+"']").html();
                    }},
                    {  "sWidth":"10%","sTitle":"审核状态","mData":"auditState","mRender":function(data,type,full){
                        if(data == 0){
                            return "<span style='color:green;'>待审核</span>";
                        }else if(data==1){
                            return "已审核";
                        }else if(data==2){
                            return "<a href='javaScript:void(0);'  onclick=\"alertRefuseReason('"+full.auditDisableReason+"')\"   ><span style='color:red;'>审核未通过</span></a>";
                        }
                    }},
                    {  "sWidth":"20%","sTitle":"操作","sDefaultContent":"","mRender":function(data,type,full){
                        var a = "";
                        var b = "";
                        var c = "";

                        if(full.auditState==2){
                            a='<@shiro.hasPermission name="goods-review-Edit"><a  href="${rc.contextPath}/goods/review/update/'+full.goodsId+'" class= "btn btn-xs blue" title ="修改"><i class="fa fa-edit"></i>修改</a></@shiro.hasPermission>';
                            b='<@shiro.hasPermission name="goods-review-Edit"><a  href="javascript:void(0);" onclick="alertRefuseReason(\''+full.auditDisableReason+'\')" class= "btn btn-xs red" title ="查看原因"><i class="fa fa-search"></i>查看原因</a></@shiro.hasPermission>';
                        }else{
                            b='<@shiro.hasPermission name="goods-review-Audit"><a  href="javascript:void(0);" onclick="auditGoods('+full.goodsId+',1)" class= "btn btn-xs blue" title ="同意"><i class="fa fa-plus"></i>同意</a></@shiro.hasPermission>';
                            c='<@shiro.hasPermission name="goods-review-Reject"><a  href="javascript:void(0);" onclick="auditGoods('+full.goodsId+',2)" class= "btn btn-xs red" title ="拒绝"><i class="fa fa-times"></i>拒绝</a></@shiro.hasPermission>';
                        }
                        return "<div style='white-space: nowrap'>"+a+b+c+"</div>";
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

        $('.table-group-action-submit').click(function(){
            selectAjax();
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
        function selectAjax(){
//            grid.setAjaxParam("mallId",mallId);
            grid.getDataTable().fnDraw();
        }

        function alertRefuseReason(msg) {
            bootboxAlert('审核未通过原因：'+msg);
        }


        function auditGoods(goodsId,auditStaut){
            if(auditStaut=="1"){
               <#--bootbox.dialog({-->
                    <#--message:'您确定要同意该商品的审核并上架到商城吗?',-->
                    <#--title:'商品审核',-->
                    <#--buttons:{-->
                        <#--main:{-->
                            <#--label:"确定",-->
                            <#--className:"green",-->
                            <#--callback:function(){-->
                                <#--$.ajax({-->
                                    <#--url:'${rc.contextPath}/goods/review/audit',-->
                                    <#--type:'POST',-->
                                    <#--data:{"goodsId":goodsId,"auditStaut":auditStaut},-->
                                    <#--dataType:"json",-->
                                    <#--traditional:true,-->
                                    <#--success:function(data){-->
                                        <#--if(data&&data.stat){-->
                                            <#--bootboxAlert(data.msg);-->
                                            <#--grid.getDataTable().fnDraw();-->
                                        <#--}else{-->
                                            <#--bootboxAlert(data.msg);-->
                                        <#--}-->
                                    <#--}-->
                                <#--});-->
                            <#--}-->
                        <#--},-->
                        <#--cancel:{-->
                            <#--label:"取消",-->
                            <#--className:"gray",-->
                            <#--callback:function(){-->
                                <#--$(this).hide();-->
                            <#--}-->
                        <#--}-->
                    <#--}-->
                <#--});-->
                $(".goodsId").val(goodsId);
                $(".isAllAudit").val(0);
                $('#goods_audit_agreed').modal('show');
            }else{
                $("#goodsId").val(goodsId);
                $("#isAllAudit").val(0);
                $('#goods_audit').modal('show');
            }
        }

        function auditAgreedGoods() {
            var isAdudit = $(".isAllAudit").val();
            var goodsId = $(".goodsId").val();
            var ischecked = $("#isUpMall").is(":checked");
            var ids = $(".goodsIds").val();
            var isUpMall = 0;
            if (ischecked){
                isUpMall = 1;
            }
            var url = "";
            var data = "";
            if(isAdudit!=null&&isAdudit==0){
                data = {"goodsId":goodsId,"auditStaut":1,"isUpMall":isUpMall}
                url = "${rc.contextPath}/goods/review/audit";
            }else if(isAdudit!=null&&isAdudit==1){
                data = {"ids":ids,"auditStaut":1,"isUpMall":isUpMall}
                url = "${rc.contextPath}/goods/review/all-audit";
            }
            $.ajax({
                url:url,
                type:'POST',
                data:data,
                dataType:"json",
                traditional:true,
                success:function(data){
                     if(data&&data.stat){
                         $('#goods_audit_agreed').modal('hide');
                         bootboxAlert(data.msg);
                         grid.getDataTable().fnDraw();

                    }else{
                        $('#goods_audit_agreed').modal('hide');
                        bootboxAlert(data.msg);
                    }
                }
            });
        }



        function auditRefuseGoods() {
            var isAdudit = $("#isAllAudit").val();
            var goodsId = $("#goodsId").val();
            var denialReason = $('#denialReason').val();
            var ids = $("#goodsIds").val();
            var data = "";
            var url ="";
            if(isAdudit!=null&&isAdudit==0){
                data = {"goodsId":goodsId,"denialReason":denialReason,"auditStaut":2}
                url = "${rc.contextPath}/goods/review/audit";
            }else if(isAdudit!=null&&isAdudit==1){
                data = {"ids":ids,"denialReason":denialReason,"auditStaut":2}
                url = "${rc.contextPath}/goods/review/all-audit";
            }
            $.ajax({
                url:url,
                type:'POST',
                data:data,
                dataType:"json",
                traditional:true,
                success:function(data){
                    if(data&&data.stat){
                        alertHint(data.msg);
                        $('#goods_audit').modal('hide');
                        grid.getDataTable().fnDraw();
                    }else{
                        $('#goods_audit').modal('hide');
                        bootboxAlert(data.msg);
                    }
                }
            });
            $('#goods_audit').modal('hide');
        }

        /**
         *  批量同意、拒绝商品审核
         */
        function auditList(auditStaut){
            var ids=[], finIds=[], ch=$("#goods_data_table span.checked >input[name='checkBox']");
            if(ch){
                ch.each(function(i,n){
                    ids.push(n.value);
                });
            }
            var content = "";
            if(ids==''||ids==null||ids.length==0){
                if(auditStaut==1){
                    bootboxAlert('请选择需要同意的商品');
                }else if(auditStaut==2){
                    bootboxAlert('请选择需要拒绝的商品');
                }
                return false;
            }
            for(var i = 0; i < ch.length; i++){
                var data = $(ch[i]).attr("data-audit");
                if(data != 0){
                    bootboxAlert('选中的数据中有已审核商品,请重新选择!');
                    return false;
                }
            }
//            if(ch){
//                ch.each(function(i,n){
//                    var data = $(ch[i]).data("audit");
//                    if(data.auditState != 0){
//                        bootboxAlert('选中的数据中有已审核商品,请重新选择!');
//                        return false;
//                    }
//                });
//            }
            if(auditStaut==1){
                <#--content = "确认批量同意审批选择的商品?";-->
                <#--commonAudit({"ids":ids,'auditStaut':auditStaut},content,'批量审批商品','${rc.contextPath}/goods/review/all-audit');-->
                $(".goodsIds").val(ids);
                $(".isAllAudit").val(1);
                $('#goods_audit_agreed').modal('show');
            }else if(auditStaut==2){
                $("#goodsIds").val(ids);
                $("#isAllAudit").val(1);
                $('#goods_audit').modal('show');
                $('#denialReason').val("");
            }

        }

        /**
         *  公共审批商品
         * */
        function commonAudit(data,message,title,url){
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
                                        bootboxAlert('操作成功！');
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
