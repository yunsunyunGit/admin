<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh-cn" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-cn" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-cn" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
    <meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
    <meta http-equiv="expires" content="-1">
    <meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <link rel="shortcut icon" href="${ctx}/assets/favicon.png" type="image/x-icon">
    <title><sitemesh:title/> - 云尚云AiCloud</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link href="${ctx}/assets/global/css/openSans.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
    <!-- END GLOBAL MANDATORY STYLES -->
    <!-- BEGIN PAGE LEVEL STYLES -->
    <link href="${ctx}/assets/global/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/global/plugins/fancybox/source/jquery.fancybox.css" rel="stylesheet"/>
    <!-- BEGIN:File Upload Plugin CSS files-->
    <link href="${ctx}/assets/global/plugins/jquery-file-upload/blueimp-gallery/blueimp-gallery.min.css" rel="stylesheet"/>
    <!-- END:File Upload Plugin CSS files-->
    <!-- END PAGE LEVEL STYLES -->
    <!-- BEGIN PAGE LEVEL STYLES -->
    <sitemesh:head/>
    <!-- END PAGE LEVEL STYLES -->
    <!-- BEGIN THEME STYLES -->
    <link href="${ctx}/assets/global/css/components.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/global/css/plugins.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
    <link id="style_color" href="${ctx}/assets/admin/layout/css/themes/default.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/assets/global/plugins/jquery-1.11.0.min.js" type="text/javascript"></script>
    <!-- END THEME STYLES -->
    <script>var ctx="${ctx}";</script>
    <style type="text/css">
        #pwdForm .help-block{
            margin-left: 163px;
        }
    </style>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed ">
<%@ include file="/WEB-INF/layouts/header.jsp" %>
<div class="clearfix"></div>
<!-- BEGIN CONTAINER -->
<div class="page-container">
    <%@ include file="/WEB-INF/layouts/sidebar.jsp" %>
    <!-- BEGIN CONTENT -->
    <div class="page-content-wrapper">
        <div class="page-content">
            <sitemesh:body/>
        </div>
    </div>

    <div id="myModal" class="modal fade" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 id="title" class="modal-title">修改密码</h4>
                </div>
                <div class="modal-body">
                    <form id="pwdForm" style="height: 260px;" class="form-horizontal edit-password-form"  method="POST" enctype="multipart/form-data">
                        <div class="form-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label class="control-label col-md-3">原密码<span class="required">
																 *
															</span>
                                        </label>

                                        <div class="col-md-9 input-icon">
                                            <i class="fa fa-lock"></i>
                                            <input id="oldPwd" style="font-family: Microsoft YaHei;" class="form-control placeholder-no-fix required" type="password" autocomplete="off" placeholder="初始密码" name="password"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label class="control-label col-md-3">新密码<span class="required">
																 *
															</span>
                                        </label>

                                        <div class="col-md-9 input-icon">
                                            <i class="fa fa-lock"></i>
                                            <input id="newPwd" style="font-family: Microsoft YaHei;" class="form-control placeholder-no-fix required" type="password" autocomplete="off" placeholder="初始密码" name="newPassword"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label class="control-label col-md-3">确认新密码<span class="required">
																 *
															</span>
                                        </label>

                                        <div class="col-md-9 input-icon">
                                            <i class="fa fa-lock"></i>
                                            <input id="comfirmPwd" name="comfirmPassword" style="font-family: Microsoft YaHei;" class="form-control placeholder-no-fix required" type="password" autocomplete="off" placeholder="确认密码"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-actions fluid">
                            <div class="col-md-offset-3 col-md-9">
                                <button type="button" class="btn default" onclick="javascript:$('#myModal').modal('hide');return false;">取消</button>
                                <button type="button" class="btn green"  onclick="javascript:savePwd();">保存</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<%@ include file="/WEB-INF/layouts/footer.jsp" %>
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="${ctx}/assets/global/plugins/respond.min.js"></script>
<script src="${ctx}/assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="${ctx}/assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
        type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/jquery-validation/js/jquery.validate.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN: Page level plugins -->
<script src="${ctx}/assets/global/plugins/fancybox/source/jquery.fancybox.pack.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-wysihtml5/wysihtml5-0.3.0.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.js" type="text/javascript"></script>
<!-- The main application script -->
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]>
<script src="${ctx}/assets/global/plugins/jquery-file-upload/js/cors/jquery.xdr-transport.js"></script>
<![endif]-->
<!-- END:File Upload Plugin JS files-->
<!-- END: Page level plugins -->
<script src="${ctx}/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${ctx}/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script>
    jQuery(document).ready(function(){
        $("#data_table_search").bind("keypress",function(e){ //键盘监听，按enter键搜索
            if(e.keyCode == 13){
                $("#data_table_search .filter-submit").click();
            }
        });
        // initiate layout and plugins
        Metronic.init(); // init metronic core components
        Layout.init(); // init current layout
    });
    window.onload=function(){
        if($('.page-sidebar-menu .open a')[0]!=undefined){
            var home=$('.page-sidebar-menu .open a')[0].innerText;
            $('.page-breadcrumb li a')[0].innerText=home;
        }
        if($('.page-sidebar-menu .open ul .active')[0]!=undefined){
            var current=$('.page-sidebar-menu .open ul .active')[0].innerText;
            var currentUrl=$('.page-sidebar-menu .open ul .active')[0].baseURI;
            $('.page-breadcrumb li a')[1].innerText=clearBr(current);
            $('.page-breadcrumb li a')[1].href=currentUrl;
        }
        $('.page-breadcrumb li a')[0].href="javascript:void(0);"
        $('.page-breadcrumb li a')[1].href="javascript:void(0);"
    }

    function clearBr(key){
        if(key){
            key = key.replace(/<\/?.+?>/g,"");
            key = key.replace(/[\r\n]/g, "");
        }
        return key;
    }

    function editPwd(){
        $('#myModal').modal('show');
    }

    function stopBubble(e){
            // 如果传入了事件对象，那么就是非ie浏览器
            if(e&&e.stopPropagation){
                //因此它支持W3C的stopPropagation()方法
                e.stopPropagation();
            }else{
                //否则我们使用ie的方法来取消事件冒泡
                window.event.cancelBubble = true;
            }
        }

    $("#pwdForm").bind(
            "submit",
            function(event){
                 alert("阻止冒泡");
                 stopBubble(event);
            }
        );

    $('.edit-password-form').validate({
        errorElement:'span',
        errorClass:'help-block',
        focusInvalid:false,
        rules:{
            password:{
                required:true,
                remote:'${rc.contextPath}/system/user/check'
            },
            newPassword:{
                required:true
            },
            comfirmPassword:{
                required:true,
                equalTo: "#newPwd"
            }
        },
        messages:{
            newPassword:{
                required:"新密码不能为空."
            },
            password:{
                required:"密码不能为空.",
                remote:"密码错误."
            },
            comfirmPassword:{
                required:"确认密码不能为空.",
                equalTo: "两次输入密码不一致."
            }
        },
        invalidHandler:function(event,validator){

        },
        highlight:function(element){
            $(element).closest('.form-group').addClass('has-error');
        },
        success:function(label){
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement:function(error,element){
            error.insertAfter(element.closest('.input-icon'));
        },
        submitHandler:function(event,form){
            form.submit();
            stopBubble(event);

        }
    });

    function savePwd(){
        if($('#pwdForm').valid()==true){
                var oldPwd=$('#oldPwd').val();
                var newPwd=$('#newPwd').val();
                var comfirmPwd=$('#comfirmPwd').val();
                Metronic.startPageLoading();
                $.ajax({
                    url:'${rc.contextPath}/system/user/change-password',
                    type:'POST',
                    data:{"password":comfirmPwd},
                    dataType:"json",
                    traditional:true,
                    success:function(data){
                        Metronic.stopPageLoading();
                        bootbox.alert("密码修改成功");
                        if(data){
                            $('#myModal').modal('hide');
                        }
                    }
                });
            }
        }
</script>
<sitemesh:getProperty property="page.script"/>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>