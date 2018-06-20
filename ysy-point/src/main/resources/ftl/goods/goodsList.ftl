<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>商品列表</title>
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
                <a href="${rc.contextPath}/goods/stuff">商品列表</a>
                <i class="fa fa-angle-right"></i>
            </li>
        </ul>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-bars"></i>商品列表</div>
                <div class="actions">
                    <div class="btn-group">
                    <@shiro.hasPermission name="goods-stuff-Add">
                        <a href="${rc.contextPath}/goods/stuff/create" class="btn green">
                            <i class="fa fa-plus"></i>
                            <span class="hidden-480" >新增商品</span>
                        </a>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="goods-stuff-Shelf">
                        <a  class="btn blue">
                            <i class="fa fa-cloud-upload"></i>
                            <span onclick="upDownShelf(-1,0);"  class="hidden-480" >批量上/下架</span>
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
                    <div id="search" class="table-actions-wrapper" style="100px;">
                        <b class="form-control input-inline input-small input-sm" style="border: 0px; text-align: right;">商城名称：</b>
                        <select id="mallId" name="mallId" class="form-control form-control table-group-action-input form-control input-inline input-small input-sm">
                            <option value="0">全部</option>
                        <#list mallList as mall>
                            <option value="${mall.id}">${mall.name}</option>
                        </#list>
                        </select>
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
                            <th width="10%">状态</th>
                            <th>操作</th>
                        </tr>
                        <tr role="row" class="filter">
                            <td></td>
                            <td></td>
                            <td><input type="text" placeholder="请输入商品编号" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_sn"></td>
                            <td><input type="text"  placeholder="请输入商品名称" autocomplete="off" class="form-control form-filter input-sm" name="search_LIKE_name"></td>
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
                                <select name="search_EQ_market" class="form-control  form-filter input-sm">
                                    <option selected="selected" value="">请选择状态</option>
                                    <option value="1">已上架</option>
                                    <option value="0">未上架</option>
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


                <!-- 商家入驻begin -->
                <div id="goods_shelf" class="modal fade" tabindex="-1" aria-hidden="true">
                    <input type="hidden" id="checkInGoodsId"  value=""/>
                    <input type="hidden" id="checkInGoodsIds"  value=""/>
                    <input type="hidden" id="saveType"  value=""/>
                    <div class="modal-dialog" style="width: 950px;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                <div class="portlet-title"  >
                                    <div class="caption" style="color:#cb0101" ><i class="fa fa-cogs"></i>商品上下架</div>
                                </div>
                            </div>
                            <div class="modal-body">
                                <select multiple="multiple" size="12" name="doublebox" class="shelfSelector" style="height: 160px;" />
                                </select>
                                <div class="modal-footer">
                                    <button class="btn green" onclick="saveUpDownShelf();">确定</button>
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
                "sAjaxSource":"${rc.contextPath}/goods/stuff/list",
                "aoColumnDefs":[
                    { "bSortable":false,"aTargets":[ 0,1,2,3,4,6,7,8 ] }
                ],//设置不排序得列
                "aoColumns":[
                    { "sWidth":"1%","sTitle":'<input type="checkbox" class= "checkAllBox" onclick="checkAllBox(this)"  title="全选" class="group-checkable" />',"sDefaultContent":"","mRender":function(data,type,full){
                        return '<div class="checker"  ><span class=""><input type="checkbox" class="checkboxes" checked="" name="checkBox" value="'+full.id+'"></span></div>';
                    }},
                    { "sWidth":"10%","sTitle":"商品图片","mData":"imageUrl","sDefaultContent":"","mRender":function(data,type,full){
                        if(data.indexOf('http')!=-1){
                            return '<img src="'+data+'?id='+Math.random()+'" width="60px" height="80px" style="margin:auto;" >';
                        }else{
                            return '<img src="${rc.contextPath}'+data+'?id='+Math.random()+'" width="60px" height="80px" style="margin:auto 0;" >';
                        }
                    }},
                    {  "sWidth":"10%","sTitle":"商品编号","mData":"sn","sDefaultContent":"","mRender":function(data,type,full){
                        return "<a href='${rc.contextPath}/goods/stuff/detail/"+full.id+"'>"+data+"</a>";
                    }},
                    {  "sWidth":"15%","sTitle":"商品名称","mData":"name","sDefaultContent":"","mRender":function(data,type,full){
                        return "<a href='${rc.contextPath}/goods/stuff/detail/"+full.id+"'>"+data+"</a>";
                    }},
                    {  "sWidth":"10%","sTitle":"参考价","mData":"referencePrice","mRender":function(data,type,full){
                        return "￥<del>"+data+"</del>";
                    }},
                    {  "sWidth":"10%","sTitle":"兑换积分","mData":"point","mRender":function(data,type,full){
                        return "<span style='color:red;'>"+data+"积分</span>";
                    }},
                    {  "sWidth":"15%","sTitle":"商家名称","mData":"merchantId","mRender":function(data,type,full){
                        return $("select[name='search_EQ_merchant'] option[value='"+data+"']").html();
                    }},
                    {  "sWidth":"10%","sTitle":"状态","mData":"marketState","mRender":function(data,type,full){
                        if(data == 1){
                            return "<span style='color:green;'>已上架</span>";
                        }else {
                            return "未上架";
                        }
                    }},
                    {  "sWidth":"20%","sTitle":"操作","sDefaultContent":"","mRender":function(data,type,full){
                        var a = "";
                        if(full.isModified==0){
                            a='<@shiro.hasPermission name="goods-stuff-Edit"><a href="${rc.contextPath}/goods/stuff/update/'+full.id+'" class="btn btn-xs blue"  title="修改" ><i class="fa fa-edit"></i>修改</a></@shiro.hasPermission>';
                        }
                        var b='<@shiro.hasPermission name="goods-stuff-Del"><a  href="javascript:void(0);" onclick="deleteGoods('+full.id+')" class= "btn btn-xs red" title ="删除"><i class="fa fa-trash-o"></i>删除</a></@shiro.hasPermission>';
                        var c='<@shiro.hasPermission name="goods-stuff-Shelf"><a  href="javascript:void(0);" onclick="upDownShelf('+full.id+',1)" class= "btn btn-xs blue" title ="上/下架"><i class="fa fa-cloud-upload"></i>上/下架</a></@shiro.hasPermission>';
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

        function upDownShelf(id,isNotSelect) {
            $(".shelfSelector").html("");
            $("#checkInGoodsId").val(id);
            $("#saveType").val(isNotSelect);
            if(isNotSelect==0){
                var ids=[], finIds=[], ch=$("#goods_data_table span.checked >input[name='checkBox']");
                if(ch){
                    ch.each(function(i,n){
                        ids.push(n.value);
                    });
                }
                if(ids==''||ids==null||ids.length==0){
                    bootboxAlert('请选择需要上架的商品');
                    return false;
                }
                $("#checkInGoodsIds").val(ids);
            }

            $.ajax({
                url:'${rc.contextPath}/goods/stuff/get-check-mall',
                type:'POST',
                data:{"id":id,"isNotSelect":isNotSelect},
                dataType:"json",
                traditional:true,
                success:function(data){
                    if(data&&data.stat){
                        var demo2 = $('.shelfSelector').doublebox({
                            nonSelectedListLabel: '可上架商城',
                            selectedListLabel: '已上架商城',
                            preserveSelectionOnMove: 'moved',
                            moveOnSelect: false,
                            nonSelectedList:data.mallNotInList,
                            selectedList:data.mallInList,
                            optionValue:"mallId",
                            optionText:"mallName",
                            doubleMove:true,
                        });
                        $('#goods_shelf').modal('show');
                    }else{
                        bootboxAlert(data.msg);
                    }
                }
            });
        }

        function saveUpDownShelf() {
            var goodsId = $("#checkInGoodsId").val();
            var goodsIds = $("#checkInGoodsIds").val();
            var saveType = $("#saveType").val();
            var mallIds =  $(".shelfSelector").val();
            $.ajax({
                url:'${rc.contextPath}/goods/stuff/save-check-mall',
                type:'POST',
                data:{"id":goodsId,"ids":goodsIds,"mallIds":mallIds,"saveType":saveType},
                dataType:"json",
                traditional:true,
                success:function(data){
                    if(data&&data.stat){
                        alertHint(data.msg);
                        $('#goods_shelf').modal('hide');
                        grid.getDataTable().fnDraw();
                    }else{
                        $('#goods_shelf').modal('hide');
                        bootboxAlert(data.msg);
                    }
                }
            });
            $('#goods_shelf').modal('hide');
        }



        /**
         * 模糊查询应用信息
         * */
        function selectAjax(mallId){
            grid.setAjaxParam("mallId",mallId);
            grid.getDataTable().fnDraw();
        }
        /**
         * 删除单个应用信息
         **/
        function deleteGoods(id){
            commonDelete({"id":id},'确认删除该商品?','删除商品','${rc.contextPath}/goods/stuff/delete');
        }


        /**
         *  删除多个应用信息
         */
        function deleteAllGoods(){
            var ids=[], finIds=[], ch=$("#apply_data_table span.checked >input[name='checkBox']");
            if(ch){
                ch.each(function(i,n){
                    ids.push(n.value);
                });
            }
            if(ids==''||ids==null||ids.length==0){
                bootboxAlert('请选择需要删除的商品');
                return false;
            }
            commonDelete({"ids":ids},'确认批量删除商品?','批量删除商品','${rc.contextPath}/goods/stuff/delete-all');
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
