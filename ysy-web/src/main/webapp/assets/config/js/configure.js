//数据存储
var store = {
    currentPage :1,
    pageSize: 10,
    addFlag:false,
    queryOpt:{
        key:"",
        value:"",
        comment:"",
        currPage:1,
        pageSize:10
    }
}
//配置模块
Vue.component('configure-part', {
    template: '#configurePage',
    props: {
        model: Object
    },
    data: function () {
        var _data ={};
        _data.store=store;
        _data.newObj = {
            key:"",
            value:"",
            comment:""
        }
        return _data;
    },
    methods:{
        canEdit:function(id){
            var list = document.getElementsByClassName('canEdit');
            for(var i=0;i<list.length;i++){
            	var judgekey = list[i].getAttribute('data-id');
            	if(judgekey == id){
            		list[i].removeAttribute('disabled');
            	}
                
            }
        },
        save: function (obj) {
            var _data = {
                configId:obj.configId,
                key:obj.key,
                value:obj.value,
                comment:obj.comment
            };
            this.store.addFlag = false;
            this.newObj = {
                key:"",
                value:"",
                comment:""
            }
            myAjax('save',_data,function(e){
                if(e.status == true){
                	alertBox(e.msg);
                    initConfigure(store.queryOpt,function(obj){
                        app_configure.$data.listData = obj;
                    })
                }else {
                    alertBox(e.msg);
                }
            })
        },
        //分页操作
        prePage: function () {
            var vm = this;
            if (vm.store.currentPage == 1) {
                return;
            }
            vm.store.currentPage--;
            store.currentPage = vm.store.currentPage;
            var _data = {
                currPage:vm.store.currentPage,
                pageSize:store.pageSize,
                key:store.queryOpt.key,
                value:store.queryOpt.value,
                comment:store.queryOpt.comment
            }
            initConfigure(_data,function(obj){
                app_configure.$data.listData = obj;
            })
        },
        nextPage: function () {
            var vm = this;
            if (vm.store.currentPage >= vm.model.totalPage) {
                return;
            }
            vm.store.currentPage++;
            store.currentPage = vm.store.currentPage;
            var _data = {
        		currPage:vm.store.currentPage,
                pageSize:store.pageSize,
                key:store.queryOpt.key,
                value:store.queryOpt.value,
                comment:store.queryOpt.comment
            }
            initConfigure(_data,function(obj){
                app_configure.$data.listData = obj;
            })
        },
        toPage: function() {
        	var toPage = document.getElementById("toPage").value;
        	var vm = this;
        	vm.store.currentPage = toPage;
        	store.currentPage = toPage;
            var _data = {
        		currPage:store.currentPage,
                pageSize:store.pageSize,
                key:store.queryOpt.key,
                value:store.queryOpt.value,
                comment:store.queryOpt.comment
            }
            initConfigure(_data,function(obj){
                app_configure.$data.listData = obj;
            })
        },
        toPageSize: function (obj) {
            var vm = this;
            var _data = {
        		currPage:vm.store.currentPage,
                pageSize:store.pageSize,
                key:store.queryOpt.key,
                value:store.queryOpt.value,
                comment:store.queryOpt.comment
            }
            initConfigure(_data,function(obj){
                app_configure.$data.listData = obj;
            })
        },
        add: function () {
            this.store.addFlag =true;
        },
        deleteById:function(obj){
        	var data = {
        		configId: obj.configId
        	}
            if(obj.configId != undefined){
                myAjax('delete', data, function(e){
                    if(e.status == true){
                    	alertBox(e.msg);
                        initConfigure(store.queryOpt,function(obj){
                            app_configure.$data.listData = obj;
                        })
                    }else {
                    	alertBox(e.msg);
                    }
                })
            }else {
                this.newObj = {
                    key:"",
                    value:"",
                    comment:""
                }
                this.store.addFlag = false;
            }
        },
        // 搜索操作
        searchOpt: function() {
            var $opt = {
            		key:this.store.queryOpt.key,
            		value:this.store.queryOpt.value,
            		comment:this.store.queryOpt.comment,
            		currPage:store.currentPage,
            		pageSize:this.store.pageSize
            	}
//            console.log($opt);
            initConfigure($opt,function(obj){
                app_configure.$data.listData = obj;
            })
        },
        //重置
        resetOpt:function(){
            this.store.queryOpt.key = "";
            this.store.queryOpt.value = "";
            this.store.queryOpt.comment = "";
        }
    }
})
//app
var app_configure ={};
function initConfigure(opt, callback){
    var $opt = opt;
    myAjax('listData',$opt,function(resp){
        if (typeof(callback) === "function") {
            callback(resp);
        }
    })
}

initConfigure(store.queryOpt,function(resp){
    app_configure  =new Vue({
        el: "#app",
        data: function () {
            var _data = {};
            _data.listData = resp;
            return _data;
        }
    })
})

//设置每页显示数目
function setPageSize(size){
	store.pageSize = size;
}