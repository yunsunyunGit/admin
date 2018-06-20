<html>
<head>
    <title>内容新增/修改</title>
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
                <a href="${rc.contextPath}/">积分商城</a>
                <i class="fa fa-angle-right"></i>
            </li>

            <li><a href="${rc.contextPath}/point/banner/">内容管理</a>
            </li>
        </ul>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-gift"></i><#if action =='create'>新建<#else>修改</#if>内容
                </div>
            </div>
            <div class="portlet-body form">
                <div id="userName"></div>

                <form action="${rc.contextPath}/point/banner/${action}" class="form-horizontal form-bordered" method="POST" id="bannerForm" enctype="multipart/form-data">
                    <input type="hidden" name="id" value="${banner.id}"/>
                    <div class="form-body">
                        <div class="alert alert-danger display-hide">
                            <button class="close" data-close="alert"></button>
                            请检查后再提交
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label">类型<span class="required">*</span></label>
                            <div class="col-md-6">
                                <select name="type"  id="selectType" onselect="selectType()" class="form-control  form-filter input-sm">
                                    <option selected="selected" value>请选择</option>
                                <#list bannerType as type>
                                    <option value="${type.id}">${type.dicValue}</option>
                                </#list>
                                </select>
                            </div>
                        </div>

                        <div class="form-group" style="height: 180px">
                            <label class="col-md-2 control-label" style="line-height:140px;height: 120px">图片<span class="required">*</span></label>

                            <div class="col-md-10" style="vertical-align: middle;height:140px;margin-bottom:5px;">
                                <div style="height:100px">
                                    <input type="hidden" name="imageUrl" id="imageUrl" value="${banner.imageUrl}"/>
                                    <span class="btn green btn-file" id="uploadFile_clone1"
                                          onchange="imagePreviewType()" style="width: 90px;margin-top:60px;display:inline-block;float: left;"><i class="icon-select_file"></i>
                                        <span class="fileinput-new">选择照片</span>
                                        <input  accept="image/png, image/gif, image/jpg, image/jpeg"  multiple="multiple" type="file" name="files" id="files"></span>
                                    <span id="localImag" style="line-height:100px;display:inline-block !important;margin-left: 30px;">
                                                <img src="" style="<#if banner.type !='52'>width:350px;height:150px;<#else>width:150px;height:150px;</#if>" id="showIcon" name="showIcon"/>
                                            </span>
                                </div>
                                <div style="height:40px;" id="files_label"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">内容名称<span class="required">*</span></label>
                            <div class="col-md-6">
                                <input type="text" class="form-control" autocomplete="off" name="name" placeholder="请输入内容名称" value="${banner.name}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">文字</label>
                            <div class="col-md-6">
                                <input type="text" class="form-control" autocomplete="off" name="content" placeholder="请输入文字" value="${banner.content}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">链接地址<span class="required">*</span></label>
                            <div class="col-md-6">
                                <#if banner.url!=null>
                                    <input type="text" class="form-control" autocomplete="off" name="url" placeholder="请输入内容所要跳转的地址 例如：(http://www.baidu.com)"  value="${banner.url}"/>
                                </#if>
                                <#if banner.url==null>
                                    <input type="text" class="form-control" autocomplete="off" name="url" placeholder="请输入内容所要跳转的地址 例如：(http://www.baidu.com)"  value="http://"/>
                                </#if>
                                <span hidden="hidden" id="url_tip" style="color: #A94442;font-size: 14px;">请输入正确的URL地址(http://www.baidu.com)</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label">状态<span class="required">*</span></label>
                            <div class="col-md-6">
                                <select name="state" class="form-control  form-filter input-sm">
                                    <option selected="selected" value="1">启用</option>
                                    <option value="0">禁用</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">排序<span class="required">*</span></label>
                            <div class="col-md-6">
                                <input type="number" onkeyup="this.value=this.value.replace(/\D/g,'')" autocomplete="off" onafterpaste="this.value=this.value.replace(/\D/g,'')" class="form-control" name="sort" placeholder="请输入排序(1-100000)" value="${banner.sort}"/>
                                范围1-100000（按数字大小升序排列）
                            </div>
                        </div>

                    </div>
                    <div class="form-actions fluid">
                        <div class="col-md-offset-3 col-md-9">
                        <@shiro.hasPermission name="point-banner-Save">
                            <button type="submit" class="btn green">保存</button>
                        </@shiro.hasPermission>
                            <button type="button" class="btn default" onclick="javascript:window.location.href='${rc.contextPath}/point/banner';">取消</button>
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
            $("#bannerForm")[0].reset();
        </#if>
        <#if action =='update'>
            $("select[name=type] option[value='${banner.type}']").attr("selected","selected");
            $("select[name=state] option[value='${banner.state}']").attr("selected","selected");
        </#if>
         <#if action =='update'>
            $("#showIcon").attr("src","${banner.imageUrl}?id="+Math.random());
         </#if>
        var form=$('#bannerForm');
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
                name:{
                    required:true,
                    maxlength:30
                },
                url:{
                    required:true,
                    url:true,
                    maxlength:150
                },
                type:{
                    required:true
                },
                state:{
                    required:true
                },
                sort:{
                    required:true,
                    digits:true,
                    range:[1,100000]
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
                if( $(element).attr("name") === 'url'){
                    $('#url_tip').show();
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
                if( $(element).attr("name") === 'url'){
                    $('#url_tip').hide();
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

        function imagePreviewType() {
            var txt =  $("#selectType").children('option:selected').text();
            if(txt=="首页ICON"){
                setImagePreview('files' ,'showIcon' ,'localImag' ,'150px' ,'150px' ,'150px' ,'150px')
            }else{
                setImagePreview('files' ,'showIcon' ,'localImag' ,'350px' ,'150px' ,'350px' ,'150px')
            }
        }
        

        $("#selectType").change(function () {
            var txt =  $(this).children('option:selected').text();
            if(txt=="首页ICON"){
                $("#showIcon").css({width:"150px",height:"150px"});
            }else{
                $("#showIcon").css({width:"350px",height:"150px"});
            }
        });

        
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