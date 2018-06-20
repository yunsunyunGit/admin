var hostUrl = workspace.contextPath;
var _data = {"manageName":"","startTime":"","endTime":"","status":"",
    "groupNameKey":"","pageSize":"10","pageNum":"1","type_status":"2","managePlat":"0"};
_data=JSON.stringify(_data);

myAjax(hostUrl+"/system/fixGroup/groupList","params="+_data,function(resp){
    console.log(resp)
    console.log("数据初始化成功！")
    for(var i=0;i<resp.list.length;i++){
        if(resp.list[i].status=="1"){
            resp.list[i].status="启动";
        }else{
            resp.list[i].status="未启动";
        }
    }
//		obj.$data.listData=resp;
    obj=new Vue({
        el:'#index',
        data:{
            listData:resp,
            membersData:data4,
            personlistData:{},
            isShow:true
        },
        ready: function () {
        },
        events:{
            'child-listData':function(msg){
                this.listData=msg;
                console.log(JSON.stringify(this.listData));
            }
        }
    });
})


// 固定群列表模块
	Vue.component('list-part',{
		template:'#list_Part',
		props:{
			model:Object
		},
		data:function(){
			var _data={};
			_data.grouplist=this.model.list;
			_data.groupName="";
			_data.groupManager="";
			_data.createdTime="";
			_data.status="";
			_data.pageNum=1;
            console.log(JSON.stringify(_data));
			return _data;
		},
		methods:{
			addGroup:function(){
				obj.$data.isShow=false;
			},
			resetFn:function(){
				// obj.$data.searchData={};
			},
			searchGroup:function(){
				var vm=this;
				// var _data={};
				var _data={"manageName":"","startTime":"","endTime":"","status":"","groupNameKey":"","pageSize":"10","pageNum":"1","type_status":"2","managePlat":"0"};
				_data.manageName=vm.groupManager;
				_data.startTime="";
				_data.endTime="";
				if(vm.status=="启动"){
					_data.status="1";
				}else if(vm.status=="未启动"){
					_data.status="2";
				}else{
					_data.status="";
				}
				_data.groupNameKey=vm.groupName;
				// _data.pageSize="10";
				// _data.pageNum="1";
				// _data.type_status="2";
				// _data.managePlat="0";
				_data=JSON.stringify(_data);
				console.log(_data);
				myAjax(hostUrl+"/system/fixGroup/groupList","params="+_data,function(resp){
					console.log("查询固定群成功")
					vm.$dispatch('child-listData',resp);
                    //vm.grouplist=resp.list;
				})
			},
			// 编辑固定群
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
			},
			// 分页操作
			lastPage:function(){
				var vm=this;
				if(this.pageNum!=1){
					this.pageNum=this.pageNum-1;
					var _data={"manageName":"","startTime":"","endTime":"","status":"","groupNameKey":"","pageSize":"10","pageNum":"1","type_status":"2","managePlat":"0"};
					_data.pageNum=this.pageNum;
					_data=JSON.stringify(_data);
					myAjax(hostUrl+"/system/fixGroup/groupList","params="+_data,function(resp){
						console.log(resp)
						console.log("分页操作成功");
						for(var i=0;i<resp.list.length;i++){
							if(resp.list[i].status=="1"){
								resp.list[i].status="启动";
							}else{
								resp.list[i].status="未启动";
							}
						}
						vm.$dispatch('child-listData',resp);
                        vm.grouplist=resp.list;
					})
				}
			},
			nextPage:function(){
				var vm=this;
					this.pageNum=this.pageNum-(-1);
					var _data={"manageName":"","startTime":"","endTime":"","status":"","groupNameKey":"","pageSize":"10","pageNum":"1","type_status":"2","managePlat":"0"};
					_data.pageNum=this.pageNum;
					_data=JSON.stringify(_data);
					myAjax(hostUrl+"/system/fixGroup/groupList","params="+_data,function(resp){
						console.log(JSON.stringify(resp.list))
						console.log("分页操作成功");
						for(var i=0;i<resp.list.length;i++){
							if(resp.list[i].status=="1"){
								resp.list[i].status="启动";
							}else{
								resp.list[i].status="未启动";
							}
						}
						vm.$dispatch('child-listData',resp);
                        vm.grouplist=resp.list;
					})
			}
		}
	})
	// 新增固定群模块
	Vue.component('add-part',{
		template:'#add_Part',
		props:{
			model2:Object,
			personlist:Object
		},
		data:function(){
			console.log(this.model);
			var _data={};
				_data.memberList=this.model.list;
				_data.groupName=this.model.name;
				_data.groupManager=this.model.manageName;
				_data.status=this.model.status;
				_data.isOn=false;
				_data.isVip="";
				_data.searchmem={};
				_data.searchmemCopy={};
			return _data;
			// {
			// 	isOn:false,
			// 	membersList:this.model2.members,
			// 	memberMes:{},
			// 	isVip:"",
			// 	searchmem:{},
			// 	searchmemCopy:{}
			// }
		},
		ready:function(){

		},
		methods:{
			// 选择普通成员操作
			chosenFn:function(){
				this.isOn=true;
				this.isVip=1;
				var _data={"pageSize":"10","pageNum":"1","userName":"","loginName":"","name":""};
				_data=JSON.stringify(_data);
				myAjax(hostUrl+'/system/fixGroup/choosePeople',"params="+_data,function(resp){
					console.log(resp)
					obj.$data.personlistData=resp;
				})
			},
			// 选择群管理员操作
			selectManager:function(){
				this.isOn=true;
				this.isVip=2;
				var _data={"pageSize":"10","pageNum":"1","userName":"","loginName":"","name":""};
				_data=JSON.stringify(_data);
				myAjax(hostUrl+'/system/fixGroup/choosePeople',"params="+_data,function(resp){
					console.log(resp)
					obj.$data.personlistData=resp;
				})
			},						
			toBack:function(){
				obj.$data.isShow=true;
			},
			// 固定群成立，修改完成
			toSure:function(){
				var vm=this;
				// obj.$data.isShow=true;
				var _data={};//提交的数据
				var _memberList=[];
				for(var i=0;i<this.membersList.length;i++){
					var _obj={};
					_obj.ln=this.membersList[i].id;
					_obj.un=this.membersList[i].name;
					_memberList.push(_obj);
				}
				_data.mems=_memberList;
				_data.name=this.model2.groupName;
				_data.cu=this.model2.groupManager;
				_data=JSON.stringify(_data);
				myAjax(hostUrl+"/system/fixGroup/addGroup","params="+_data,function(resp){
					console.log("提交成功");
				})
				var _dataTwo={"manageName":"","startTime":"","endTime":"","status":"",
				"groupNameKey":"","pageSize":"10","pageNum":"1","type_status":"1","managePlat":"0"};
				myAjax(hostUrl+"/system/fixGroup/groupList","params="+_dataTwo,function(resp){
					console.log(resp)
					for(var i=0;i<resp.list.length;i++){
						if(resp.list[i].status=="1"){
							resp.list[i].status="启动";
						}else{
							resp.list[i].status="未启动";
						}
					}
					vm.$dispatch('child-listData',resp);
                    vm.grouplist=resp.list;
					obj.$data.isShow=true;
				})
			},
			// 弹出框的重置，查询操作
			personReset_btn:function(){
				this.searchmem={};
			},
			personSearch_btn:function(){
				console.log(this.searchmem);
				var _data={"pageSize":"10","pageNum":"1","userName":"","loginName":"","name":""};
				_data.userName=this.searchmem.name;
				_data.loginName=this.searchmem.username;
				_data.name=this.searchmem.department;
				_data=JSON.stringify(_data);
				myAjax(hostUrl+'/system/fixGroup/choosePeople',"params="+_data,function(resp){
					console.log(resp)
					obj.$data.personlistData=resp;
				})
				// myAjax('json/member.json',"",function(resp){
				// 	obj.$data.personlistData=resp;
				// })
			},
			//弹出框的选择成员确认，选中操作
			pop_cancel:function(){
				this.isOn=false;
			},
			pop_ensure:function(){				
				if(this.memberMes!={}){
					obj.$data.personlistData={};
					if(this.isVip==1){
						this.membersList.push(this.memberMes);
					}else{
						this.groupManager=this.memberMes.user_name;
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
			// 删除成员
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
		list:[]
	}
var obj = {};
//	var obj=new Vue({
//			el:'#index',
//			data:{
//				listData:{},
//				membersData:data4,
//				personlistData:{},
//				isShow:true
//			},
//			events:{
//				'child-listData':function(msg){
//					this.listData=msg;
//				}
//			}
//		})
