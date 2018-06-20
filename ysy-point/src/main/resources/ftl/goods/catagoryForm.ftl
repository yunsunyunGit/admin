<html>
<head>
    <title>分类新增/修改</title>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/select2/select2.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/select2/select2.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/jstree/dist/themes/default/style.min.css"/>
    <link href="${rc.contextPath}/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/drags/css/layoutit.css"/>
    <link href="${rc.contextPath}/assets/global/plugins/jquery-file-upload/blueimp-gallery/blueimp-gallery.min.css" rel="stylesheet"/>
    <link href="${rc.contextPath}/assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet"/>
    <link href="${rc.contextPath}/assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet"/>
    <link href="${rc.contextPath}/assets/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
    <link href="${rc.contextPath}/assets/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css">
    <link href="${rc.contextPath}/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css"/>
    <link href="${rc.contextPath}/assets/global/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" type="text/css"/>
    <link href="${rc.contextPath}/assets/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
    <link href="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.css" rel="stylesheet"/>
    <link href="${rc.contextPath}/assets/global/plugins/bootstrap-datepicker/css/datepicker.css" rel="stylesheet" type="text/css"/>
    <link href="${rc.contextPath}/assets/global/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" type="text/css"/>
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
                <a href="${rc.contextPath}/">商品管理</a>
                <i class="fa fa-angle-right"></i>
            </li>

            <li><a href="${rc.contextPath}/goods/catagory">分类管理</a>
            </li>
        </ul>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-gift"></i><#if action =='create'>新建<#else>修改</#if>分类
                </div>
            </div>
            <div class="portlet-body form">
                <div id="userName"></div>
                <input type="text" class="form-control" name="name" placeholder="请输入分类名称" style="visibility: hidden"/>
                <form action="${rc.contextPath}/goods/catagory/save" class="form-horizontal form-bordered" method="POST" id="catagoryForm" enctype="multipart/form-data">
                    <input type="hidden" name="id" value="${catagory.id}"/>
                    <input type="hidden" name="parentId" value="0"/>
                    <div class="form-body">
                        <div class="alert alert-danger display-hide">
                            <button class="close" data-close="alert"></button>
                            请检查后再提交
                        </div>
                        <div class="form-group" style="height: 180px">
                            <label class="col-md-2 control-label" style="line-height:140px;height: 120px">图片<span class="required">*</span></label>

                            <div class="col-md-10" style="vertical-align: middle;height:140px;margin-bottom:5px;">
                                <div style="height:100px">
                                    <input type="hidden" name="imageUrl" id="imageUrl"/>
                                    <span class="btn green btn-file" id="uploadFile_clone1"
                                          onchange="javascript:setImagePreview('files' ,'showIcon' ,'localImag' ,'100px' ,'96px' ,'100px' ,'96px')" style="width: 90px;margin-top:60px;display:inline-block;float: left;">
                                                            <i class="icon-select_file"></i>
                                                            <span class="fileinput-new">选择照片</span>
                                                            <input  accept="image/png, image/gif, image/jpg, image/jpeg"  multiple="multiple" type="file" name="files" id="files">
                                                        </span>
                                    <span id="localImag" style="line-height:100px;display:inline-block !important;margin-left: 30px;">
                                                            <img src="" style="width:100px;height:96px;" id="showIcon" name="showIcon" class="imgWarn"/>
                                                        </span>
                                </div>
                                <div style="height:40px;" id="files_label"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">分类名称<span class="required">*</span></label>
                            <div class="col-md-6">
                                <input type="text" class="form-control" name="name" placeholder="请输入分类名称" value="${catagory.name}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">所属商城<span class="required">*</span></label>

                            <div class="col-md-10">
                            <#list mallList as mall>
                                <label class="checkbox-inline">
                                    <input type="checkbox" name="mallIds" value="${mall.id}">${mall.name}
                                </label>
                            </#list>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">排序</label>
                            <div class="col-md-6">
                                <input type="number" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" class="form-control" name="seq" placeholder="请输入排序" value="${catagory.seq}"/><span>范围1-100000（按数字大小升序排列）</span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">状态<span class="required">*</span></label>
                            <div class="col-md-6">
                                <select name="isShow" class="form-control  form-filter input-sm">
                                    <option selected="selected" value="1">启用</option>
                                    <option value="0">禁用</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-actions fluid">
                        <div class="col-md-offset-3 col-md-9">
                        <@shiro.hasPermission name="goods-catagory-Save">
                            <button type="submit" class="btn green">保存</button>
                        </@shiro.hasPermission>
                            <button type="button" class="btn default" onclick="javascript:window.location.href='${rc.contextPath}/goods/catagory';">取消</button>
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
    <script src="${rc.contextPath}/assets/global/plugins/data-tables/jquery.dataTables.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/select2/select2.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.iframe-transport.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-validate.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-ui.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/common/commonUtil.js"></script>
    <script type="text/javascript">
        <#if action =='create'>
            $("#catagoryForm")[0].reset();
        </#if>
        <#if action =='update'>
            $("select[name=isShow] option[value='${catagory.isShow}']").attr("selected","selected");
            <#list mallIds as mallCata>
            $("input[name=mallIds][value=${mallCata.mallId}]").attr("checked","checked");
            </#list>
            $("input[name=parentId]").val("${catagory.parentId}");
            $("input[name=imageUrl]").val("${catagory.imageUrl}");
            $('#showIcon').attr("src",'${catagory.imageUrl}'+'?id='+Math.random());
        </#if>
        var form=$('#catagoryForm');
        var error=$('.alert-danger',form);
        form.validate({
            errorElement:'span',
            errorClass:'help-block help-block-error',
            focusInvalid:true,
            rules:{
                <#if action == 'create'>
                files:{
                    required:true
                },
                </#if>
                mallIds:{
                    required:true
                },
                seq:{
                    number:true,
                    digits:true,
                    range:[1,100000]
                },
                name:{
                    required:true,
                    maxlength:30
                },
                isShow:{
                    required:true
                }
            },
            invalidHandler:function(event,validator){
                error.show();
                Metronic.scrollTo(error,-200);
            },
            errorPlacement:function(e,element){
                if( $(element).attr("type") === 'file'){
                    $('#showIcon').css({border: "1px solid red"});
                }
            },
            highlight:function(element){
                $(element).closest('.form-group').removeClass("has-success").addClass('has-error');
            },
            unhighlight:function(element){
            },
            success:function(label,element){
                if( $(element).attr("type") === 'file'){
                    $('#showIcon').css({border: "none"});
                }
                var icon=$(element).parent('.input-icon').children('i');
                $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
                icon.removeClass("fa-warning").addClass("fa-check");
            },
            submitHandler:function(form){
                error.hide();
                form.submit();
                return true ;
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