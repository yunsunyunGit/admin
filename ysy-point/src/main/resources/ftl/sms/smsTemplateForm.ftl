<html>
<head>
    <title>模板新增/修改</title>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/select2/select2.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/bootstrap-datepicker/css/datepicker.css"/>
    <style>
        #empNameId{
            color: #000;
        }
    </style>
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
            <li><a href="${rc.contextPath}/sms/template/create">模板新增</a></li>
        </ul>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-gift"></i><#if action =='create'>新建<#else>修改</#if>模板
                </div>
            </div>
            <div class="portlet-body form">
                <div id="userName"></div>

                <form action="${rc.contextPath}/sms/template/${action}" class="form-horizontal form-bordered" method="POST" id="tempForm">
                    <input type="hidden" name="id" value="${ptTipSmsTemplate.id}"/>
                    <div class="form-body">
                        <div class="alert alert-danger display-hide">
                            <button class="close" data-close="alert"></button>
                            请检查后再提交
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">模板名<span class="required">*</span></label>
                            <div class="col-md-7">
                                <input type="text" autocomplete="off" class="form-control" name="name" placeholder="请输入模板名" value="${ptTipSmsTemplate.name}"/>
                            </div>
                        </div>
                      <#--  <div class="form-group">
                            <label class="col-md-2 control-label">模板类型<span class="required">*</span></label>
                            <div class="col-md-3">
                                <select name="type"  class="select" style="width: 100%;">
                                    <option value="1">消息</option>
                                    <option value="2">短信</option>
                                </select>
                            </div>
                        </div>-->
                        <div class="form-group">
                            <label class="col-md-2 control-label">是否默认<span class="required">*</span></label>
                            <div class="col-md-3">
                                <select name="isDefault"  class="form-control  form-filter input-sm" style="width: 100%;">
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                                设置模板默认则之前设置的默认模板将会失效
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">发送动作类型<span class="required">*</span></label>
                            <div class="col-md-3">
                                <select name="actionType"  class="form-control  form-filter input-sm" style="width: 100%;">
                                    <option value="1">下单时</option>
                                    <option value="2">发货时</option>
                                    <option value="3">收货时</option>
                                    <option value="4">取消时</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">模板内容<span class="required">*</span></label>
                            <div class="col-md-7">
                                <textarea class="form-control" style="resize:none;" rows="3" autocomplete="off" id="remark" placeholder="请输入模板内容" name="content">${ptTipSmsTemplate.content}</textarea>
                                模板占位符设置：[:member] 表示“会员名称”，[:goods] 表示“商品名称”，[:ExpressCompany] 表示“物流公司名称”，[:ExpressNum] 表示“快递单号”
                            </div>
                        </div>
                    </div>
                    <div class="form-actions fluid">
                        <div class="col-md-offset-3 col-md-9">
                        <@shiro.hasPermission name="sms-template-Save">
                            <button type="submit" class="btn green">保存</button>
                        </@shiro.hasPermission>
                            <button type="button" class="btn default" onclick="javascript:window.location.href='${rc.contextPath}/sms/template';">取消</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>

<content tag="script">
    <script src="${rc.contextPath}/assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/select2/select2.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-validation/js/lib/jquery.form.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
    <script type="text/javascript">
        <#if action =='create'>
            $("#tempForm")[0].reset();
        </#if>
        <#if action =='update'>
           $("select[name=type] option[value='${ptTipSmsTemplate.type}']").attr("selected","selected");
           $("select[name=isDefault] option[value='${ptTipSmsTemplate.isDefault}']").attr("selected","selected");
           $("select[name=actionType] option[value='${ptTipSmsTemplate.actionType}']").attr("selected","selected");
        </#if>
        var form=$('#tempForm');
        var error=$('.alert-danger',form);
        form.validate({
            errorElement:'span',
            errorClass:'help-block help-block-error',
            focusInvalid:true,
            rules:{
                name:{
                    required:true,
                    maxlength:30
                },
                content:{
                    required:true,
                    maxlength:200
                }
            },
            invalidHandler:function(event,validator){
                error.show();
                Metronic.scrollTo(error,-200);
            },
            highlight:function(element){
                $(element).closest('.form-group').addClass('has-error');
            },
            unhighlight:function(element){
                $(element).closest('.form-group').removeClass('has-error');
            },
            success:function(label){
                label.closest('.form-group').removeClass('has-error');
            },
            submitHandler:function(form){
                error.hide();
                form.submit();
            }
        });

        function inputTipText(){
            $("input[class*=grayTips]").each(function(){ //所有样式名中含有grayTips的input
                var oldVal=$(this).val();   //默认的提示性文本
                $(this).css({"color":"#808080"}).focus(function(){   //灰色
                    if($(this).val()!=oldVal){
                        $(this).css({"color":"#000"})
                    }else{
                        $(this).val("").css({"color":"#808080"})
                    }
                }).blur(function(){
                    if($(this).val()==""){
                        $(this).val(oldVal).css({"color":"#808080"})
                    }
                }).keydown(function(){
                    $(this).css({"color":"#000"})
                })
            })
        }

    </script>
    <#if action =='create'>
        <script type="text/javascript">
            $(function(){
                inputTipText();  //直接调用就OK了
            })
        </script>
    </#if>
</content>
</html>