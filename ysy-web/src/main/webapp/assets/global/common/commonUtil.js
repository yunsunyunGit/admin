/**
 * 公共js组件 @all
 **/
//公共设置默认图片地址:
var commonImgAddress = "/assets/global/img/default.jpg";
/**
 *
 *docName   file组件的name
 *viewId  img的id
 *localId  div的id
 * imgWidth  img的宽度
 * imgHeight img的高度
 * divWidth div的宽度
 * divHeight div的高度
 */
function setImagePreview(docName,viewId,localId,imgWidth,imgHeight,divWidth,divHeight,tip){
    // return;
    $('#uploadFiletIcon').text('');
    var docObj=document.getElementById(docName); //"doc");
    var imgObjPreview=document.getElementById(viewId); //"preview");
    var fileObj=$('#'+docName).val();
    if(tip==1){
        $('#tip').val(1);
        $('#uploadFileIconx').val('');
        $('#uploadFileIcon').text('');
    }else{
        $('#iconTip').val(1);
    }
    if(fileObj){
        if(docObj.files&&docObj.files[0]){
            //火狐下，直接设img属性
            imgObjPreview.style.display='inline-block';
            imgObjPreview.style.width=imgWidth; //'60px';
            imgObjPreview.style.height=imgHeight; //'60px';
            //imgObjPreview.src = docObj.files[0].getAsDataURL();
            //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
            imgObjPreview.src=window.URL.createObjectURL(docObj.files[0]);
        }else{
            //IE下，使用滤镜
            docObj.select();
            var imgSrc=document.selection.createRange().text;
            var localImagId=document.getElementById(localId); //"localImag");
            //必须设置初始大小
            localImagId.style.width=divWidth; //"300px";
            localImagId.style.height=divHeight; //"120px";
            //图片异常的捕捉，防止用户修改后缀来伪造图片
            try{
                localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src=imgSrc;
            }catch(e){
                alert("您上传的图片格式不正确，请重新选择!");
                return false;
            }
            imgObjPreview.style.display='none';
            document.selection.empty();
        }
    }
    return true;
}
//图片格式
var extArray=new Array(".jpg",".jpeg",".png",".gif");
//压缩包格式
var zipArray=new Array(".zip",".rar");
/**
 * 判断上传文件的类型
 * @param file
 * @param tip
 * @returns {Boolean}
 */
function limitAttach(file,tip){
    var finaFile="";
    if(tip==1){
        finaFile=extArray;
    }else{
        finaFile=zipArray;
    }
    var allowSubmit=false;
    if(!file){
        allowSubmit=true;
    }
    while(file.indexOf("\\")!= -1){
        file=file.slice(file.indexOf("\\")+1);
    }
    var ext=file.slice(file.indexOf(".")).toLowerCase();
    for(var i=0; i<finaFile.length; i++){
        if(finaFile[i]==ext){
            allowSubmit=true;
            break;
        }
    }
    if(!allowSubmit){
        alert("只能上传以下格式的文件:"+(finaFile.join(""))+"\n 请重新选择再上传.");
        return false;
    }
    return allowSubmit;
}
/**
 * 全选 按钮
 * @param th
 * @param name
 */
function checkAllBox(th,name){
    if(th){
        if(th.checked){
            if(name){
                $("input[name='"+name+"']").attr("checked",true).parent('span').addClass('checked');
                $(th).parent("span").addClass("checked");
            }else{
                $("input[name='checkBox']").attr("checked",true).parent('span').addClass('checked');
            }
        }else{
            if(name){
                $(th).parent("span").removeClass("checked");
                $("input[name='"+name+"']").attr("checked",false).parent('span').removeClass('checked');
            }else{
                $("input[name='checkBox']").attr("checked",false).parent('span').removeClass('checked');
            }
        }
    }
}
/**
 * 格式化时间
 * @param objDate 需要格式化的时间
 * @returns clock 返回 2014-10-10 10:10:10
 */
function changeDate(objDate){
    var clock="";
    if(objDate!=null&&objDate){
        var now=new Date(objDate);
        var year=now.getFullYear();
        var month=now.getMonth()+1;
        var day=now.getDate();
        var hh=now.getHours();
        var mm=now.getMinutes();
        clock=year+"-";
        if(month<10)
            clock+="0";
        clock+=month+"-";
        if(day<10)
            clock+="0";
        clock+=day+" ";
        if(hh<10)
            clock+="0";
        clock+=hh+":";
        if(mm<10) clock+='0';
        clock+=mm;
        if(now.getSeconds()<10){
            clock+=":0"+now.getSeconds();
        }else{
            clock+=":"+now.getSeconds();
        }
    }
    return clock;
}
/**
 *  根据格式来格式化时间
 * @param format 格式化时间的格式 如 yyyy-MM-dd
 * @returns 返回 格式化后时间
 */

Date.prototype.format=function(format){
    var o={
        "M+":this.getMonth()+1, //month
        "d+":this.getDate(),    //day
        "h+":this.getHours(),   //hour
        "m+":this.getMinutes(), //minute
        "s+":this.getSeconds(), //second
        "q+":Math.floor((this.getMonth()+3)/3),  //quarter
        "S":this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4-RegExp.$1.length));
    for(var k in o)if(new RegExp("("+k+")").test(format))
        format=format.replace(RegExp.$1,RegExp.$1.length==1?o[k]:("00"+o[k]).substr((""+o[k]).length));
    return format;
}
//操作成功后的提示框，两秒钟后自动关闭
function alertHint(msg,callback){
    if(!msg){
        msg="操作成功.";
    }
    bootbox.alert({  
        buttons: {  
            ok: {  
                 label: '确定' 
             }  
         },  
         message: msg
     });
    setTimeout(function(){
        bootbox.hideAll();
        if(callback){
            callback();
        }
    },2000);
}
// 封装bootbox.alert()
function bootboxAlert(msg) {
    bootbox.alert({
        buttons: {
            ok: {
                label: '确定'
            }
        },
        message: msg
    });
}
// 简化bootbox.dialog()
function bootboxDialog(msg, success, title) {
    bootbox.dialog({
        message: msg,
        title: title,
        buttons: {
            success: {
                label: "确定",
                className: "green",
                callback: success
            },
            main: {label: "取消",className: "gray",callback: function() {$(this).hide();}}
        }
    });
}

/**
 * 拼接字符串信息
 * @param num
 * @returns {Number}
 */
function factorial(obj,tip){
    var finstring;
    if(obj){
        if(obj.length<=tip){
            finstring=obj;
        }else{
            var temp=arguments.callee((obj.substring(tip,obj.length)),tip);
            finstring+=obj.substring(0,tip)+"</br>"+temp
        }
    }
    return finstring.replace("undefined","");
}
/**
 * 获取 http://localhost:8080
 */
function getURL(projoName){
    var curWwwPath=window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName); //获取主机地址，如： http://localhost:8080
    var localhostPaht=curWwwPath.substring(0,pos); //获取带"/"的项目名，如：/cis
  /*   var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);// TODO 不需要项目名称,注释掉
     var rootPath = localhostPaht + projectName;*/
    var finalName ;
    if(projoName){
        finalName =  localhostPaht+projoName;
    }else{
        finalName=localhostPaht;
    }
    return finalName;
}
/**
 * 获取上传文件的名字
 * @param obj
 * @param id
 */
function getFullPath(obj,id){
    if(obj){
        var path=obj.value;
        var index=path.lastIndexOf("\\")+1;
        if(index>0){
            $('#'+id).html(path.substr(index));
        }else{
            $('#'+id).html(path);
        }
    }
}

//dataTable搜索
function search(btn,grid){
    var search_div=$(btn).parent().parent("div");
    var inputs=search_div.find('input').filter(".form-filter");
    for(var i=0;i<inputs.length;i++){
        $(inputs[i]).val($(inputs[i]).val().trim());
    }
    grid.search(search_div);
}
//dataTable重置
function reset(btn,excludes,grid){
    var search_div=$(btn).parent().parent("div");
    grid.reset(search_div,excludes);
}

function outputmoney(number) {
if(isNaN(number)){
    number = number.replace(/\,/g, "");
}
if(isNaN(number) || number == "")return "";
number = Math.round(number * 100) / 100;
    if (number < 0)
        return '-' + outputdollars(Math.floor(Math.abs(number) - 0) + '') + outputcents(Math.abs(number) - 0);
    else
        return outputdollars(Math.floor(number - 0) + '') + outputcents(number - 0);
}
//格式化金额
function outputdollars(number) {
    if (number.length <= 3)
        return (number == '' ? '0' : number);
    else {
        var mod = number.length % 3;
        var output = (mod == 0 ? '' : (number.substring(0, mod)));
        for (i = 0; i < Math.floor(number.length / 3); i++) {
            if ((mod == 0) && (i == 0))
                output += number.substring(mod + 3 * i, mod + 3 * i + 3);
            else
                output += ',' + number.substring(mod + 3 * i, mod + 3 * i + 3);
        }
        return (output);
    }
}
function outputcents(amount) {
    amount = Math.round(((amount) - Math.floor(amount)) * 100);
    return (amount < 10 ? '.0' + amount : '.' + amount);
}