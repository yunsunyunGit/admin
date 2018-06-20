<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>登录页</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <link href="${ctx}/assets/global/css/openSans.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/global/css/components.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/global/css/plugins.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
    <link id="style_color" href="${ctx}/assets/admin/layout/css/themes/default.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/admin/layout/css/loginSelf.css" rel="stylesheet" type="text/css"/>
    <link rel="shortcut icon" href="${ctx}/favicon.ico"/>
    <script>var ctx="${ctx}";</script>
</head>
<body class="login">
<div class="logo">
    <!--<a href="${ctx}/">
        <img id ="logo" src="${ctx}/assets/admin/layout/img/logo-big.png" alt=""/>
    </a>-->
</div>
<div class="menu-toggler sidebar-toggler">
</div>
<div class="content">
    <div class="erweima-container">
        <img src="${ctx}/assets/admin/layout/img/photo2.jpg" alt="">
    </div>
    <div class="login-way">
        <div class="way1" title="密码登录"></div>
        <%--<div class="way2" title="扫码登录"></div>--%>
    </div>
    <form class="login-form" action="${ctx}/login" method="post">
        <h3 class="form-title">登录系统账号</h3>
        <%
            String error=(String)request.getAttribute(org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
            if(error!=null){
        %>
        <div class="alert alert-danger">
            <button class="close" data-close="alert"></button>
            <span><%
                if(error.contains("DisabledAccountException")){
                    out.print("用户已被屏蔽,请登录其他用户.");
                }else if(error.contains("UnknownAccountException")){
                    out.print("用户不存在,请检查后重试!");
                }else{
                    out.print("登录失败，请重试.");
                }
            %></span>
        </div>
        <%}%>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">用户名</label>

            <div class="input-icon">
                <i class="fa fa-user"></i>
                <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="username" value="${username}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">密&nbsp;&nbsp;码</label>

            <div class="input-icon">
                <i class="fa fa-lock"></i>
                <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="" name="password"/>
            </div>
        </div>
        <div class="form-actions">
            <label class="checkbox"><input type="checkbox" name="rememberMe" id="rememberMe" value="true"/> 记住密码 </label>
        </div>
        <div class="form-actions form-btn">
            <button type="submit" class="btn blue pull-right">登录 <i class="m-icon-swapright m-icon-white"></i></button>
        </div>
    </form>
</div>
<!--<div class="copyright">@2016 &copy; 深圳市云尚云智能科技有限公司.</div>-->
<!--[if lt IE 9]>
<script src="${ctx}/assets/global/plugins/respond.min.js"></script>
<script src="${ctx}/assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="${ctx}/assets/global/plugins/jquery-1.11.0.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
        type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/assets/global/plugins/select2/select2.min.js"></script>
<script src="${ctx}/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${ctx}/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script>
    jQuery(document).ready(function(){
        Metronic.init();
        Layout.init();
    });
    $('.login-form').validate({
        errorElement:'span',
        errorClass:'help-block',
        focusInvalid:false,
        rules:{
            username:{
                required:true
            },
            password:{
                required:true
            },
            remember:{
                required:false
            }
        },
        messages:{
            username:{
                required:"用户名不能为空."
            },
            password:{
                required:"密码不能为空."
            }
        },
        invalidHandler:function(event,validator){
            $('.alert-danger',$('.login-form')).show();
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
        submitHandler:function(form){
            form.submit();
        }
    });
    $('.login-form input').keypress(function(e){
        if(e.which==13){
            if($('.login-form').validate().form()){
                $('.login-form').submit();
            }
            return false;
        }
    });
    $.backstretch([ctx+"/assets/admin/pages/media/bg/bg.jpg"],{
        fade:1000,
        duration:8000
    });
    $(".login-way .way1").on('click', function () {
        $(".login-form").show();
        $(this).siblings('.way2').show();
        $('.erweima-container').hide();
        $(this).hide();
    });
    $(".login-way .way2").on('click', function () {
        $(".login-form").hide();
        $(this).siblings('.way1').show();
        $('.erweima-container').show();
        $(this).hide();
    });
</script>
</body>
</html>
