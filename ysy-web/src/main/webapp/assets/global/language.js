

 var objLanguage = {
 	// 第一层
 	"主页":"home",
 	"系统设置":"system",
 	"第三方权限":"",
 	"应用管理":"",
 	"安全管理":"",
 	"平台管理":"",
 	"导入管理":"",
 	"数据字典管理":"",
 	"客户管理":"",
 	"群组管理":"",
 	"项目设置管理":"",
 	"工作计划管理":"",
 	// 第二层  系统设置下的子目录
 	"资源管理":"",
 	"角色管理":"",
 	"用户管理":"",
 	"员工管理":"",
 	"组织管理":"",
 	"岗位管理":"",
 	"团队管理":"",
 	"数据权限配置":"",
 	"定时任务管理":"",
 	// 第二层  第三方权限下的子目录
 	"第三方用户":"",
 	"第三方系统":""

 };

 // jQuery.ready(function(){
 	
 // });
 $(document).ready(function(){
 	initLanguage();
 })

 function initLanguage(){
 		$("[data-lang]").each(function(o, n){
 			var chinese = $(this).attr("data-lang");
 			if(!!objLanguage[chinese])
 				$(this).text(objLanguage[chinese]);
 		});
 }
