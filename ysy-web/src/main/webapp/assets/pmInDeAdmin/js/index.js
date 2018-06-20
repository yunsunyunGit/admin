	var datas=[{
		name:"123",
		companyName:"圆舟"
	},
	{
		name:"456",
		companyName:"前海"
	}]
	var demo=new Vue({
		el:'#index',
		data:datas,
	})
 	Vue.component('demo-gid',{
 		template:"#indexPage",
 		data:function(e){
 			console.log(e);
 		},
 		methods:{}
 	})