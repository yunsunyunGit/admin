var userToolParams={};
var userToolResult={};
var orgParams={"empName":"","roleCode":"","orgId":"0"};
var roleCode,orgId=0,includeEmpids,staticOtherParam;
function bindEmp(paramsObj){
    if(paramsObj){
        roleCode=(paramsObj.roleCode || '');
        orgId= (paramsObj.orgId || 0);
        includeEmpids=(paramsObj.orgId || '');
        if(!paramsObj.includeEmpids){
            alert('需要制定接受人员结果id');
        }else{
            userTool.init()
        }
        staticOtherParam=["orgId",orgId,"roleCode",roleCode];
    }
}
var userTool={
    callBackFn:"test2",
    init:function(){
        initOrgTree();
        $("#empTreeDiv").modal('toggle');
        },
        search:function(){
            var empname=$("#empname").val();
            orgParams={"empname":empname,"role":"","org":""};
            $.post("${rc.contextPath}/system/organization/treeData",orgParams,function(data){
                initOrgTree(data);
            });
        }
};
function initOrgTree(){
    var zSetting= {
        view: {
            selectedMulti: false
        },
        async:{
            enable: true,
            contentType: "application/json",
            type:"get",
            url: "${rc.contextPath}/system/organization/orgEmpData",
            autoParam: ["id=orgId"],
            otherParam: staticOtherParam,
            dataFilter: ajaxDataFilter
        },
        check: {
            enable: true,
            autoCheckTrigger: true,
            chkboxType: { "Y": "ps", "N": "ps" }
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 0
            }
        }
    };
    zTreeObj = $.fn.zTree.init($("#empTree"), zSetting, null);
}

function makeSure(){
    var treeObj = $.fn.zTree.getZTreeObj("empTree");
    var nodes = treeObj.getCheckedNodes(true);
    if(nodes){
        var result=""
        for(var i=0;i<nodes.length;i++){
            if(nodes[i].type=='P'){
                result+=nodes[i].id+",";
            }
        }
        $("#"+includeEmpids).val(result);
        $("#empTreeDiv").modal('toggle');
        treeObj.destroy();
    }
}

function ajaxDataFilter(treeId, parentNode, responseData) {
    if (responseData) {
        for(var i =0; i < responseData.length; i++) {
            if(responseData[i].type=='P'){
                responseData[i].name += "(人员)";
            }else if(responseData[i].type=='o'){
            }
        }
    }
    return responseData;
};
