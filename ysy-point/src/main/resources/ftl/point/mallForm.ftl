<html>
<head>
    <title>商城新增/修改</title>
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
                <a href="${rc.contextPath}/">商城管理</a>
                <i class="fa fa-angle-right"></i>
            </li>

            <li><a href="${rc.contextPath}/system/user/">商城新增</a>
            </li>
        </ul>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-gift"></i><#if action =='create'>新建<#else>修改</#if>商城
                </div>
            </div>
            <div class="portlet-body form">
                <div id="userName"></div>

                <form action="${rc.contextPath}/point/mall/${action}" class="form-horizontal form-bordered" method="POST" id="mallForm">
                    <input type="hidden" name="id" value="${ptMall.id}"/>
                    <div class="form-body">
                        <div class="alert alert-danger display-hide">
                            <button class="close" data-close="alert"></button>
                            请检查后再提交
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">商城名<span class="required">*</span></label>
                            <div class="col-md-6">
                                <input type="text" class="form-control" autocomplete="off" name="name" placeholder="请输入商城名" value="${ptMall.name}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">所属APP名<span class="required">*</span></label>
                            <div class="col-md-6">
                                <select name="appName"  class="form-control  form-filter input-sm" style="width: 100%;">
                                <#list appNameDictList as appNameDict>
                                    <option value="${appNameDict.dicValue}">${appNameDict.dicValue}</option>
                                </#list>
                                </select>
                            </div>

                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">商城描述<span class="required">*</span></label>
                            <div class="col-md-6">
                                <textarea class="form-control" style="resize:none;" rows="3" id="remark" placeholder="请输入商城描述" name="description">${ptMall.description}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="form-actions fluid">
                        <div class="col-md-offset-3 col-md-9">
                        <@shiro.hasPermission name="point-mall-Save">
                            <button type="submit" class="btn green">保存</button>
                        </@shiro.hasPermission>
                            <button type="button" class="btn default" onclick="javascript:window.location.href='${rc.contextPath}/point/mall';">取消</button>
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
            $("#mallForm")[0].reset();
        </#if>
        <#if action =='update'>
            $("select[name=appName] option[value='${ptMall.appName}']").attr("selected","selected");
        </#if>
        var form=$('#mallForm');
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
                description:{
                    required:true,
                    maxlength:200
                },
                appName:{
                    required:true,
                    maxlength:30
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