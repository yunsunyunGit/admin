var hostUrl='/mxm';
	Vue.component('list-part',{
		template:'#list_Part',
		props:{
			model:Object,
			search:Object
		},
		data:function(){
			console.log(this.model);
			 var grouplist=[];
			return {
				grouplist:this.model.option
			}
		},
		methods:{
			addGroup:function(){
				obj.$data.isShow=false;
			},
			resetFn:function(){
				obj.$data.searchData={};
			},
			searchGroup:function(){
				var _data=this.search;
				myAjax("json/groupList.json",_data,function(resp){
					obj.$data.listData=resp;
				})
			},
			groupEdit:function(data){
				console.log(data);
				var _data=data;
				
				myAjax("json/groupDetail.json",_data,function(resp){
					obj.$data.membersData=resp;
					obj.$data.isShow=false;
				})
			},
			removeGroup:function(no){
				for(var i=0;i<this.grouplist.length;i++){
					if(this.grouplist[i].no==no){
						this.grouplist.splice(i,1);
					}
				}
				var _data=this.grouplist;
				myAjax("json/groupList.json",_data,function(resp){
					console.log("delete成功,更新群组列表")
				})
			}
		}
	})
	Vue.component('add-part',{
		template:'#add_Part',
		props:{
			model2:Object,
			searchmem:Object,
			personlist:Object
		},
		data:function(){
			console.log(this.model2);
			var membersList=[];
			return {
				isOn:false,
				membersList:this.model2.members,
				memberMes:{},
				isVip:""
			}
		},
		ready:function(){

		},
		methods:{
			chosenFn:function(){
				this.isOn=true;
				this.isVip=1;
			},
			selectManager:function(){
				this.isOn=true;
				this.isVip=2;
			},
			pop_cancel:function(){
				this.isOn=false;
			},
			pop_ensure:function(){				
				if(this.memberMes!={}){
					if(this.isVip==1){
						this.membersList.push(this.memberMes);
					}else{
						this.model2.groupManager=this.memberMes.name;
					}
					this.isOn=false;
				}else{
					alert("无选中")
				}
			},
			selectPerson:function(member){
				console.log(member);
				this.memberMes=member;
			},
			resetFn:function(){
				obj.$data.searchMemData={};
			},
			toBack:function(){
				obj.$data.isShow=true;
			},
			toSure:function(){
				obj.$data.isShow=true;
				var _data=this.model2;
				console.log(this);
				console.log(_data);
				myAjax("json/dataSubmit.json",_data,function(resp){
					console.log("提交成功");
				})
				var _searchData=obj.$data.searchData;
				myAjax("json/groupList.json",_searchData,function(resp){
					obj.$data.listData=resp;
				})
			},
			personSearch_btn:function(){
				console.log(this.searchmem);
				var _data={"pageSize":"10","pageNum":"1","userName":"1","loginName":"1","name":"1"};
				_data=JSON.stringify(_data);
				myAjax(hostUrl+'/system/fixGroup/choosePeople',_data,function(resp){
					console.log(resp)
					obj.$data.personlistData=resp;
				})
			},
			remmovePerson:function(username){
				for(var i=0;i<this.membersList.length;i++){
					if(this.membersList[i].username==username){
						this.membersList.splice(i,1);
					}
				}
			}
		}
	})
	var data4={
		id:"",
		no:"",
		groupName:"",
		groupManager:"",
		membersNum:"",
		createdTime:"",
		status:"",
		members:[]
	}
	var obj={};
	myAjax("json/groupList.json","",function(resp){
		console.log(resp)
		obj=new Vue({
			el:'#index',
			data:{
				searchMemData:{},
				searchData:{},
				listData:resp,
				membersData:data4,
				personlistData:{},
				isShow:true
			}
		})
	})