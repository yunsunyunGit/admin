<html>
<head>
    <title>商品新增/修改</title>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/select2/select2.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.css"/>
    <link rel="stylesheet" type="text/css"
          href="${rc.contextPath}/assets/global/plugins/jstree/dist/themes/default/style.min.css"/>
    <link href="${rc.contextPath}/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/assets/drags/css/layoutit.css"/>
    <link href="${rc.contextPath}/assets/global/plugins/jquery-file-upload/blueimp-gallery/blueimp-gallery.min.css"
          rel="stylesheet"/>
    <link href="${rc.contextPath}/assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet"/>
    <link href="${rc.contextPath}/assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css"
          rel="stylesheet"/>
    <link href="${rc.contextPath}/assets/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet"
          type="text/css">
    <link href="${rc.contextPath}/assets/global/plugins/bootstrap-datepicker/css/datepicker.css" rel="stylesheet"
          type="text/css"/>
    <link href="${rc.contextPath}/assets/global/plugins/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="${rc.contextPath}/assets/global/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .picture-item {
            margin-right: 6px;
            margin-bottom: 6px;
            position: relative;
            display: inline-block;
        }

        .picture-item:hover .toolbar_wrap {
            display: block;
        }

        .toolbar_wrap {
            display: none;
        }

        .opacity {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            width: 100%;
            height: 20px;
            background-color: #000;
            opacity: .3;
            z-index: 108;
        }

        .toolbar {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            width: 100%;
            height: 20px;
            z-index: 109;
        }

        .toolbar .delete {
            background-image: url(${rc.contextPath}/assets/admin/layout/img/icons_edit.png);
            background-position: -180px -92px;
            width: 20px;
            height: 20px;
            top: 0;
            right: 0;
            cursor: pointer;
        }

        .toolbar a {
            background-image: url(${rc.contextPath}/assets/admin/layout/img/icons_edit.png);
            position: absolute;
            outline: 0;
            text-decoration: none;
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
                    <i class="fa fa-gift"></i><#if action =='create'>新建<#else>修改</#if>商品
                </div>
            </div>
            <div class="portlet-body form" style="background-color:#ffffff;">
                <form class="form-horizontal form-bordered" enctype="multipart/form-data"  method="POST" id="goodsForm"  action="${rc.contextPath}/goods/stuff/${action}">
                    <input type="hidden" name="id" id="goodsId" value="${goods.id}"/>
                    <input type="hidden" name="merchantId" value="${goods.merchantId}"/>
                    <div class="col-md-8">
                        <div class="alert alert-danger display-hide">
                            <button class="close" data-close="alert"></button>
                            请检查后再提交
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">经销商名称<span class="required">*</span></label>
                            <div class="col-md-10">
                                <span class="form-control">${merchantName}</span>
                            </div>
                        </div>
                        <div class="form-group" style="height: 140px">
                            <label class="col-md-2 control-label" style="line-height:140px;height: 120px">商品缩略图<span class="required">*</span></label>
                            <div class="col-md-10" style="vertical-align: middle;height:140px;margin-bottom:5px;">
                                <div style="height:100px">
                                    <input type="hidden" name="imageUrl" id="imageUrl" value="${goods.imageUrl}"  />
                                    <span class="btn green btn-file" id="uploadFile_clone1"
                                          onchange="javascript:setImagePreview('goodsImage' ,'showIcon' ,'localImag' ,'100px' ,'96px' ,'100px' ,'96px')"
                                          style="width: 90px;margin-top:60px;display:inline-block;float: left;">
                                        <i class="icon-select_file"></i>
                                        <span class="fileinput-new">请选择图片</span>
                                        <input accept="image/png, image/gif, image/jpg, image/jpeg"
                                               multiple="multiple" type="file"
                                               name="goodsImage" id="goodsImage">
                                    </span>
                                    <span id="localImag"
                                          style="line-height:100px;display:inline-block !important;margin-left: 30px;">
                                        <img src="${goods.imageUrl}"
                                                 style="width:100px;height:96px;" id="showIcon"
                                                 name="showIcon" class="imgWarn"/>
                                    </span>
                                </div>
                                <div style="height:40px;" id="files_label"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">商品名称<span class="required">*</span></label>
                            <div class="col-md-10">
                                <input type="text" class="form-control" autocomplete="off" name="name" placeholder="请输入商品名称"
                                       value="${goods.name}"/>1-50个字符
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">商品编号<span class="required">*</span></label>
                            <div class="col-md-10">
                                <input type="text" class="form-control" autocomplete="off" name="sn" placeholder="请输入商品编号"
                                       value="${goods.sn}"/>1-50个字符
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">商品库存<span class="required">*</span></label>
                            <div class="col-md-10">
                                <input type="text" class="form-control" autocomplete="off" name="stock" placeholder="请输入商品库存"
                                       value="${goods.stock}"/>范围1-100000
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">参考价<span class="required">*</span></label>
                            <div class="col-md-10">
                                <input type="text" class="form-control" autocomplete="off" name="referencePrice" placeholder="请输入参考价"
                                       value="${goods.referencePrice}"/>范围1-100000000
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">兑换积分<span class="required">*</span></label>
                            <div class="col-md-10">
                                <input type="text" class="form-control" autocomplete="off" name="point" placeholder="请输入商品兑换积分"
                                       value="${goods.point}"/>范围1-100000000
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">商品分类<span class="required">*</span></label>

                            <div class="col-md-10">
                            <#list cataList as cata>
                                <label class="checkbox-inline">
                                    <input type="checkbox" name="cataIds" autocomplete="off" value="${cata.id}">${cata.name}
                                </label>
                            </#list>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">推荐到</label>

                            <div class="col-md-10">
                            <#list typeList as type>
                                <label class="radio-inline" style="position: relative;width: 90px;">
                                    <input type="radio" name="goodsType" autocomplete="off" value="${type.id}">
                                    <span style="display:inline-block;position: absolute;width: 100px;top:12px;">${type.dicValue}</span>
                                </label>
                            </#list>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label">商品详情图</label>
                            <div class="col-md-10">
                                <div class="layui-upload">
                                    <button type="button" class="btn green btn-file" id="detailImageChoose">上传图片</button>
                                </div>
                                <div class="layui-upload">
                                    <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
                                        预览图：
                                        <div class="layui-upload-list" id="detailDiv"></div>
                                    </blockquote>
                                </div>
                            </div>
                        </div>

                        <div class="form-group" >
                            <label class="col-md-2 control-label">商品轮播图</label>
                            <div class="col-md-10">
                                <div class="layui-upload">
                                    <button type="button" class="btn green btn-file" id="carouselChoose">上传图片</button>
                                </div>
                                <div class="layui-upload">
                                    <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
                                        预览图：
                                        <div class="layui-upload-list" id="carouselDiv"></div>
                                    </blockquote>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="form-actions fluid">
                        <div class="col-md-offset-5 col-md-9" style="margin-top: 20px">
                        <@shiro.hasPermission name="goods-stuff-Save">
                            <button type="submit"   class="btn green">保存</button>
                        </@shiro.hasPermission>
                        <button type="button" class="btn default"
                                onclick="javascript:window.location.href='${rc.contextPath}/goods/stuff';">取消
                        </button>
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
    <script src="${rc.contextPath}/assets/global/plugins/jquery-validation/js/lib/jquery.form.js"
            type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js"
            type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-validation/js/additional-methods.min.js"
            type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-validation/js/localization/messages_zh.js"
            type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/data-tables/jquery.dataTables.js"
            type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/bootstrap/js/bootstrap.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.iframe-transport.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-validate.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-ui.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/common/commonUtil.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/layui/layui.js"></script>
    <script type="text/javascript">
        <#if action =='create'>
            $("#goodsForm")[0].reset();
        </#if>
        <#if action =='update'>
            $("#showIcon").attr("src","${goods.imageUrl}?id="+Math.random());
        </#if>
        <#if action =='update'>

        $("input[name=goodsType][value=${goods.goodsType}]").attr("checked","checked");

        <#list goodsCataList as goodsCata>
            $("input[name=cataIds][value=${goodsCata.catagoryId}]").attr("checked","checked");
        </#list>

        <#list detailImageList as detailImage >
            $('#detailDiv').append('<div class="picture-item"  id="itemImg${detailImage.imageName}" >' +
                    '<input type="hidden"  name="detailImage"  value="${detailImage.imageUrl}" /><div height="120px"><img src="${detailImage.imageUrl}'+'?id='+Math.random()+'"  style="margin-left: 15px;" class="layui-upload-img" width="150px" height="150px"/></div>' +
                    '<div class="img_cover"></div>' +
                    '<div class="toolbar_wrap">' +
                    '<div class="opacity"></div>' +
                    '<div class="toolbar" data-id="${detailImage.reallyPath}"  data-imageId="${detailImage.imageName}"  ><a  class="delete delete-image"></a> </div></div>' +
                    '</div>');
        </#list>

        <#list carouselImageList as carouselImage >
            $('#carouselDiv').append('<div class="picture-item"  id="itemImg${carouselImage.imageName}" >' +
                    '<input type="hidden"  name="carouselImage"  value="${carouselImage.imageUrl}" /><div height="120px"><img src="${carouselImage.imageUrl}'+'?id='+Math.random()+'"  style="margin-left: 15px;" class="layui-upload-img" width="150px" height="150px"/></div>' +
                    '<div class="img_cover"></div>' +
                    '<div class="toolbar_wrap">' +
                    '<div class="opacity"></div>' +
                    '<div class="toolbar" data-id="${carouselImage.reallyPath}"  data-imageId="${carouselImage.imageName}"  ><a  class="delete delete-image"></a> </div></div>' +
                    '</div>');
        </#list>

        <#--$("input[name=parentId]").val("${catagory.parentId}");-->
        <#--$("input[name=imageUrl]").val("${catagory.imageUrl}");-->
        <#--$('#showIcon').attr("src",'${rc.contextPath}'+'${catagory.imageUrl}');-->

        </#if>
        $(function () {

            <#if action =='create'>
                $(".delete-image").live("click", function () {
                    var filePath = $(this).parents(".toolbar").attr("data-id");
                    var imageId = $(this).parents(".toolbar").attr("data-imageId");
                    $.ajax({
                        url: "${rc.contextPath}/goods/stuff/delete-image",
                        type: "post",
                        dataType: "json",
                        data: {"filePath": filePath},
                        success: function (data) {
                            if (data && data.stat) {
                                $("#itemImg" + imageId).remove();
                            } else {
                                bootboxAlert(data.msg);
                            }
                        }
                    });
                })
            </#if>
            <#if action =='update'>
                $(".delete-image").live("click", function () {
                    var imageId = $(this).parents(".toolbar").attr("data-imageId");
                    $("#itemImg" + imageId).remove();
                })
            </#if>


        });




        layui.use('upload', function () {
            var upload = layui.upload;
            //普通图片上传
            var detailInit = upload.render({
                elem: '#detailImageChoose'
                , url: '${rc.contextPath}/goods/stuff/save-detail-image'
                , before: function (obj) {
                }
                ,done: function (res) {
                    console.log(res);
                    if (res.stat) {
                        //上传成功
                        $('#detailDiv').append('<div class="picture-item"  id="itemImg' + res.imgName + '" >' +
                                '<input type="hidden" id="detailImage" name="detailImage"  value="'+res.imagePath+'" /><div height="120px"><img src="' + res.imagePath + '?id='+Math.random()+'"  style="margin-left: 15px;" class="layui-upload-img " width="150px" height="150px"/></div>' +
                                '<div class="img_cover"></div>' +
                                '<div class="toolbar_wrap">' +
                                '<div class="opacity"></div>' +
                                '<div class="toolbar" data-id="' + res.reallyPath + '"  data-imageId="'+ res.imgName +'"  ><a  class="delete delete-image"></a> </div></div>' +
                                '</div>');
                    }else{
                        bootboxAlert(data.msg);
                        return ;
                    }
                }
                , error: function () {
                }
            });
            var carouselInit = upload.render({
                elem: '#carouselChoose'
                , url: '${rc.contextPath}/goods/stuff/save-detail-image'
                , done: function (res) {
                    console.log(res);
                    if (res.stat) {
                        if ($(".carouselImg").length>4){
                            bootboxAlert("轮播图最多上传五张");
                            return;
                        }
                        //上传成功
                        $('#carouselDiv').append('<div class="picture-item" id="itemImg' + res.imgName + '"  >' +
                                '<input type="hidden" id="carouselImage" name="carouselImage"  value="'+res.imagePath+'" /><div height="120px"><img src="' + res.imagePath + '?id='+Math.random()+'"  class="layui-upload-img carouselImg"  style="margin-left: 15px;" width="150px" height="150px"/></div>' +
                                '<div class="img_cover"></div>' +
                                '<div class="toolbar_wrap">' +
                                '<div class="opacity"></div>' +
                                '<div class="toolbar" data-id="' + res.reallyPath + '"   data-imageId="'+ res.imgName +'"   ><a  class="delete delete-image"></a> </div></div>' +
                                '</div>');
                    }else{
                        bootboxAlert(data.msg);
                        return;
                    }
                }
                ,error: function () {
                }
            });
        });

        var form = $('#goodsForm');
        var error = $('.alert-danger', form);
        form.validate({
            errorElement: 'span',
            errorClass: 'help-block help-block-error',
            focusInvalid: true,
            rules: {
                <#if action =='create'>
                goodsImage:{
                    required: true
                },
                </#if>
                name: {
                    required: true,
                    maxlength:50
                },
                sn: {
                    required: true,
                    maxlength:50
                },
                stock: {
                    required: true,
                    number: true,
                    digits:true,
                    range:[1,100000]
                },
                referencePrice: {
                    required: true,
                    number: true,
                    range:[1,100000000]
                },
                point: {
                    required: true,
                    number: true,
                    range:[1,100000000]
                },
                cataIds: {
                    required: true
                }
            },
            invalidHandler: function (event, validator) {
                error.show();
                Metronic.scrollTo(error, -200);
            },
            errorPlacement: function (e, element) {
            },
            highlight: function (element) {
                $(element).closest('.form-group').removeClass("has-success").addClass('has-error');
            },
            unhighlight: function (element) {
            },
            success: function (label, element) {
                var icon = $(element).parent('.input-icon').children('i');
                $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
                icon.removeClass("fa-warning").addClass("fa-check");
            },
            submitHandler: function (form) {
                error.hide();
                if ($("input[name='detailImage']").length <= 0){
                    bootboxAlert("请上传商品详情图");
                    return false;
                }
                if ($("input[name='carouselImage']").length <= 0){
                    bootboxAlert("请上传商品轮播图");
                    return false;
                }
                form.submit();
                return true;
            }
        });

        function inputTipText() {
            $("input[class*=grayTips]").each(function () { //所有样式名中含有grayTips的input
                var oldVal = $(this).val();   //默认的提示性文本
                $(this).css({"color": "#808080"}).focus(function () {   //灰色
                    if ($(this).val() != oldVal) {
                        $(this).css({"color": "#000"})
                    } else {
                        $(this).val("").css({"color": "#808080"})
                    }
                }).blur(function () {
                    if ($(this).val() == "") {
                        $(this).val(oldVal).css({"color": "#808080"})
                    }
                }).keydown(function () {
                    $(this).css({"color": "#000"})
                })
            })
        }

    </script>
<#if action =='create'>
    <script type="text/javascript">
        $(function () {
            inputTipText();  //直接调用就OK了
        })
    </script>
</#if>
</content>
</html>