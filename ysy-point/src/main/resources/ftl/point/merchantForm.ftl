<html>
<head>
    <title>商家管理|新建商家</title>
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
</head>
<body>
<div class="row">
    <div class="col-md-12 delete-padding-rt">
        <ul class="page-breadcrumb breadcrumb">
            <li>
                <a href="#">积分商城</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li><a href="${rc.contextPath}/point/merchant">商家管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
        </ul>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green-haze">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-plus-square"></i><#if action?? && action == 'create'>新增</#if><#if action?? && action == 'update'>编辑</#if>经销商
                </div>
            </div>
            <div class="portlet-body form">
                <form class="form-horizontal form-bordered" role="form" id="employeeForm" action="${rc.contextPath}/point/merchant/${action}" method="post" enctype="multipart/form-data">
                    <input name="id" type="hidden" value="${e.id}"/>
                    <input type="hidden" name="salt" value="${u.salt}"/>
                    <div class="form-body">
                        <div class="alert alert-danger display-hide">
                            <button class="close" data-close="alert"></button>
                            请检查后再提交
                        </div>
                        <div class="row">
                            <div class="col-md-6" style="height:140px">
                                <div class="form-group">
                                    <label class="col-md-3 control-label" style="line-height:140px;height: 120px">照片<span class="required">&nbsp;</span></label>

                                    <div class="col-md-9" style="vertical-align: middle;height:140px;margin-bottom:5px;">
                                        <div style="height:100px">
                                            <input type="hidden" name="imageAddress" id="imageAddress" value="${e.imageAddress}"/>
                                            <span class="btn green btn-file" id="uploadFile_clone1"
                                                  onchange="javascript:setImagePreview('files' ,'showIcon' ,'localImag' ,'74px' ,'74px' ,'74px' ,'74px')" style="width: 90px;margin-top:30px;display:inline-block;float: left;"><i class="icon-select_file"></i><span class="fileinput-new">选择照片</span><input  accept="image/png, image/gif, image/jpg, image/jpeg"  multiple="multiple" type="file" name="files" id="files"></span>
                                            <span id="localImag" style="line-height:100px;display:inline-block !important;margin-left: 30px;">
                            <img src="${e.imageAddress}" style="width:100px;height:100px;" id="showIcon" name="showIcon"/>
                        </span>
                                        </div>
                                        <div style="height:40px;" id="files_label"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">经销商名称<span class="required">*</span></label>

                                    <div class="col-md-9">
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                            <input name="userName" type="text" autocomplete="off" class="form-control" placeholder="请输入商家名称" value="${e.userName}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">登录名<span class="required">*</span></label>

                                    <div class="col-md-9">
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-key"></i></span>
                                            <input check-type='required' name="loginName" autocomplete="off" type="text" class="form-control" placeholder="请输入登录名" value="${e.loginName}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                        <#if action?? && action == 'create'>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">登录密码<span class="required">*</span></label>

                                    <div class="col-md-9">
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                                            <input check-type='required' name="plainPassword" autocomplete="off" type="password" class="form-control" placeholder="请输入登录密码" >
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#if>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">电子邮件<span class="required">*</span></label>

                                    <div class="col-md-9">
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
                                            <input name="email" type="text" autocomplete="off" class="grayTips form-control" placeholder="电子邮件地址" value="${e.email}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">状态<span class="required">*</span></label>

                                    <div class="col-md-9">
                                        <select name="status" class="form-control">
                                            <option value="ENABLE">启用</option>
                                            <option value="DISABLE">禁用</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">手机</label>

                                    <div class="col-md-9">
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span>
                                            <input type="text" class="form-control" autocomplete="off" isMobile="true" name="phone" minlength="11" value="${e.phone}">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-6" hidden="hidden">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">座机</label>

                                    <div class="col-md-9">
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span>
                                            <input type="text" class="form-control" autocomplete="off" isTel="true" name="telephone" minlength="3" value="${e.telephone}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" hidden="hidden">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">入职日期<span class="required">&nbsp;</span></label>
                                    <div class="col-md-9">
                                        <div class="input-group date date-picker margin-bottom-5" data-date-format="yyyy-mm-dd">
                                            <input id="workingTime" type="text" class="form-control  input-sm" autocomplete="off" name="workT" placeholder="入职日期" value="${e.workT}">
                                            <span class="input-group-btn"><button class="btn btn-sm default" type="button"><i class="fa fa-calendar"></i></button></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">离职日期</label>
                                    <div class="col-md-9">
                                        <div class="input-group date date-picker margin-bottom-5" data-date-format="yyyy-mm-dd">
                                            <input id="turnoverTime" type="text" class="form-control  input-sm" autocomplete="off" name="turnoverT" placeholder="离职日期" value="${e.tunT}">
                                            <span class="input-group-btn"><button class="btn btn-sm default" type="button"><i class="fa fa-calendar"></i></button></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" hidden="hidden">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">角色</label>

                                    <div class="col-md-9">
                                        <select name="roles" id="roles" multiple="multiple" class="select" style="width: 100%;">
                                        <#list roles as r>
                                            <option value="${r.id}">${r.name}</option>
                                        </#list>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6" hidden="hidden">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">组织<span class="required">&nbsp;</span></label>

                                    <div class="col-md-9" style="heigiht:300px">
                                        <input type="hidden" name="empOrg">
                                        <div id="orgTree"></div>
                                        <table class="table table-striped table-hover table-bordered" id="orgTable">
                                            <thead>
                                            <tr>
                                                <th>组织ID</th>
                                                <th width="40%">岗位名称</th>
                                                <th width="20%">主负责</th>
                                                <th width="20%">主组织</th>
                                                <th width="20%">操作</th>
                                            </tr>
                                            </thead>
                                            <tbody></tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        <#--<div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-3 control-label">岗位<span class="required">&nbsp;</span></label>
                                <div class="col-md-9" style="heigiht:300px">
                                    <input type="hidden" name="empPos">
                                    <div id="posTree"></div>
                                    <table class="table table-striped table-hover table-bordered" id="posTable">
                                        <thead>
                                        <tr>
                                            <th>岗位ID</th>
                                            <th width="60%">岗位名称</th>
                                            <th width="20%">主岗位</th>
                                            <th width="20%">操作</th>
                                        </tr>
                                        </thead>
                                        <tbody></tbody>
                                    </table>
                                </div>
                            </div>
                        </div>-->
                        <#--<div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-3 control-label">团队<span class="required">&nbsp;</span></label>

                                <div class="col-md-9">
                                    <select name="teams" id="teams" multiple="multiple" class="select" style="width: 100%;">
                                    <#list teams as r>
                                        <option value="${r.id}">${r.name}</option>
                                    </#list>
                                    </select>
                                </div>
                            </div>
                        </div>-->
                        </div>
                    <#--<#if action?? && action == 'update'>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-2 control-label">上级领导ID</label>

                                <div class="col-md-10">
                                    <input type="text" class="form-control" value="${leaderId}" readonly="true" />
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-2 control-label">上级领导姓名</label>

                                <div class="col-md-10">
                                    <input type="text" class="form-control" value="${leaderName}" readonly="true" />
                                </div>
                            </div>
                        </div>
                    </div>
                    </#if>-->
                        <div class="form-actions fluid">
                            <div class="col-md-offset-5 ">
                            <@shiro.hasPermission name="point-merchant-Save">
                                <button type="submit" class="btn green">保存</button>
                            </@shiro.hasPermission>
                                <button type="button" class="btn default return" onclick="javascript:window.location.href='${rc.contextPath}/point/merchant';">取消</button>
                            </div>
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
    <script src="${rc.contextPath}/assets/global/plugins/data-tables/jquery.dataTables.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/select2/select2.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jstree/dist/jstree.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-validation/js/lib/jquery.form.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-validation/js/jquery.validate.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.iframe-transport.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-validate.js"></script>
    <script src="${rc.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-ui.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/assets/global/common/commonUtil.js"></script>
    <script type="text/javascript">
        <#if action =='create'>
            $("#employeeForm")[0].reset();
        </#if>
        <#if action =='update'>
        $("#showIcon").attr("src","${e.imageAddress}?id="+Math.random());
        </#if>

        $('.date-picker').datepicker({
            rtl:Metronic.isRTL(),
            language: 'zh-CN',
            format:"yyyy-mm-dd",
            autoclose:true
        });
        var orgTableData={}, posTableData={};
        var teams=$("#teams").select2(), roles=$("#roles").select2();
        var orgTable=$('#orgTable').dataTable({
            bPaginate:false,
            bFilter:false,
            bInfo:false,
            "oLanguage":{"sEmptyTable":"没设置组织"},
            "aoColumns":[
                { "sTitle":"编号","mData":"id","bVisible":false},
                { "sTitle":"名称","mData":"name","bSortable":false},
                { "sTitle":"主负责人","mData":"charge","bSortable":false,"mRender":function(data,type,full){
                    return '<div class="checker"><span class="'+(data==true?"checked":"")+'"><input type="checkbox" class="checkboxes charge" name="checkBox"></span></div>';
                }},
                { "sTitle":"主组织",swidth:"30px","mData":"major","bSortable":false,"mRender":function(data,type,full){
                    return '<div class="checker"><span class="'+(data==true?"checked":"")+'"><input type="checkbox" class="checkboxes major" name="checkBox"></span></div>';
                }},
                { "sTitle":"操作","mData":"name","bSortable":false,"mRender":function(data,type,full){
                    return'<a class="delete btn default btn-xs black" href="javascript:;" ><i class="fa fa-trash-o"></i>删除</a>';
                }}
            ]
        }).on('change','tbody tr .checkboxes.charge',function(){
            $(this).parents('span').toggleClass("checked");
        }).on('change','tbody tr .checkboxes.major',function(){
            $(this).parents('span').toggleClass("checked");
        });
        $('#orgTable a.delete').live('click',function(e){
            e.preventDefault();
            var nRow=$(this).parents('tr')[0];
            var Row=orgTable.fnGetData(nRow);
            var id=Row.id;
            delete orgTableData[id];
            orgTable.fnDeleteRow(nRow);
        });
        $("#orgTree").jstree({
            "core":{
                "animation":0,
                "themes":{
                    theme:"classic",
                    "dots":true,
                    "icons":true
                },
                "check_callback":true,
                'data':${orgTree}
            },
            "types":{
                "default":{
                    "valid_children":["default","file"]
                }
            },
            "plugins":["types","wholerow"],
        }).on('select_node.jstree',function(node,selectd){
            var id=selectd.node.id;
            var json={id:id,name:selectd.node.text,charge:false,major:false};
            if(orgTableData[id]==undefined){
                orgTableData[id]=json;
                orgTable.fnAddData(json);
            }
        });
        var posTable=$('#posTable').dataTable({
            bPaginate:false,
            bFilter:false,
            bInfo:false,
            "oLanguage":{
                "sEmptyTable":"没设置岗位",
                "sLengthMenu":"_MENU_ records"
            },
            "aoColumns":[
                { "sTitle":"编号","mData":"id","bVisible":false },
                { "sTitle":"名称","mData":"name","bSortable":false},
                { "sTitle":"主岗位",swidth:"30px","mData":"major","bSortable":false,"mRender":function(data,type,full){
                    return '<div class="checker"><span class="'+(data==true?"checked":"")+'"><input type="checkbox" class="checkboxes major" name="checkBox"></span></div>';
                }},
                { "sTitle":"操作","mData":"name","bSortable":false,"mRender":function(data,type,full){
                    return'<a class="delete btn default btn-xs black" href="javascript:;" ><i class="fa fa-trash-o"></i>删除</a>';
                }}
            ]
        }).on('change','tbody tr .checkboxes.major',function(){
            $(this).parents('span').toggleClass("checked");
        });
        $('#posTable a.delete').live('click',function(e){
            e.preventDefault();
            var nRow=$(this).parents('tr')[0];
            var Row=posTable.fnGetData(nRow);
            var id=Row.id;
            delete posTableData[id];
            posTable.fnDeleteRow(nRow);
        });
        $("#posTree").jstree({
            "core":{
                "animation":0,
                "themes":{
                    theme:"classic",
                    "dots":true,
                    "icons":true
                },
                "check_callback":true,
                'data':${posTree}
            },
            "types":{
                "default":{
                    "valid_children":["default","file"]
                }
            },
            "plugins":["types","wholerow"],
        }).on('select_node.jstree',function(node,selectd){
            var id=selectd.node.id;
            var json={id:id,name:selectd.node.text,major:false};
            if(posTableData[id]==undefined){
                posTableData[id]=json;
                posTable.fnAddData(json);
            }
        });
        var form=$('#employeeForm');
        var error=$('.alert-danger',form);
        form.validate({
            errorElement:'span',
            errorClass:'help-block help-block-error',
            focusInvalid:true,
            messages:{
                loginName:{remote:"登录名已经存在"}
            },
            rules:{
                loginName:{
                    minlength:2,
                    maxlength:30,
                    required:true,
                    remote:'${rc.contextPath}/point/merchant/check/${u.id}'
                },
                userName:{
                    required:true,
                    maxlength:30
                },
                email:{
                    required:true,
                    email:true
                },
                status:{
                    required:true
                },
                plainPassword:{
                    required:true,
                    maxlength:16
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
                var empOrg=[];
                var empPos=[];
                $("#orgTable tbody tr").each(function(){
                    var json={};
                    var nRow=orgTable.fnGetData(this);
                    if(nRow){
                        json.id=nRow.id;
                        json.major=$(this).find("input.checkboxes.major").parents("span").hasClass("checked");
                        json.charge=$(this).find("input.checkboxes.charge").parents("span").hasClass("checked");
                        empOrg.push(json);
                    }
                });
                $("#posTable tbody tr").each(function(){
                    var json={};
                    var nRow=posTable.fnGetData(this);
                    if(nRow){
                        json.id=nRow.id;
                        json.major=$(this).find("input.checkboxes").parents("span").hasClass("checked");
                        empPos.push(json);
                    }
                });
                $('input[name="empOrg"]').val(JSON.stringify(empOrg));
                $('input[name="empPos"]').val(JSON.stringify(empPos));
                error.hide();
                form.submit();
            }
        });
        <#if action =='update'>
        $("select[name=status] option[value='${u.status}']").attr("selected","selected");
        var data=[], empOrg=[], empPos=[],teamsData=[];
            <#list u.role as r>
            data.push({id:${r.id},text:'${r.name}'});
            </#list>
        roles.select2("data",data);
            <#list e.team as et>
            teamsData.push({id:${et.id},text:'${et.name}'});
            </#list>
        teams.select2("data",teamsData);
            <#list e.org as eo>
            orgTableData[${eo.id.org.id}]={id:${eo.id.org.id},name:"${eo.id.org.name}",charge:<#if eo.charge=1>true<#else>false</#if>,major:<#if eo.major=1>true<#else>false</#if>};
            empOrg.push(orgTableData[${eo.id.org.id}]);
            </#list>
        orgTable.fnAddData(empOrg);
            <#list e.pos as ep>
            posTableData[${ep.id.pos.id}]={id:${ep.id.pos.id},name:"${ep.id.pos.name}",major:<#if ep.major='ENABLE'>true<#else>false</#if>};
            empPos.push(posTableData[${ep.id.pos.id}]);
            </#list>
        posTable.fnAddData(empPos);
        </#if>

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

        <#if action =='create'>
        $(function(){
            inputTipText();  //直接调用就OK了
        })
        </#if>
    </script>
</content>
</html>