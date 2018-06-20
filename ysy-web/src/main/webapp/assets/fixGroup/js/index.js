var hostUrl = workspace.contextPath;
var store = {
    groupList: [],
    pageCount: 0,
    optionList: [],
    queryOpt: {
        pageNum: "1",
        "manageName": "",
        "startTime": "",
        "endTime": "",
        "status": "",
        "groupNameKey": "",
        "type_status": "1",
        "managePlat": "0",
        "pageSize": "10"
    },
    selectPersonPageCount: 0,
    selectPersonOptionList: [],
    querySelectPersonOpt: {
        "pageSize": "10",
        "pageNum": "1",
        "userName": "",
        "loginName": "",
        "name": ""
    },
    selectPersonList: [],     //可选择的人列表
    currentGroup: {},        //点击编辑群组的信息
    editFlag: "",
    oldManager: {}           //群管理员信息，编辑时调用
};

function initOptionList() {
    store.optionList = [];
    for (i = 1; i <= store.pageCount; i++) {
        store.optionList.push(i);
    }
}


function initSelectPersonOptionList() {
    store.selectPersonOptionList = [];
    for (i = 1; i <= store.selectPersonPageCount; i++) {
        store.selectPersonOptionList.push(i);
    }
}

var obj ={};


initPageList(store.queryOpt, function () {
    obj = new Vue({
        el: '#index',
        data: function () {
            var _data = {};
            _data.isShow = true;
            _data.editFlag = "add";
            // _data.membersData = data4;
            return _data;
        },
        events: {
            'child-listData': function (resp) {
                var vm = this;
                this.listData = resp;
            }
        }
    });
});

function initPageList(opt, callback) {
    var $opt = {
        "manageName": opt.manageName || "",
        "startTime": opt.startTime || "",
        "endTime": opt.endTime || "",
        "status": opt.status || "",
        "groupNameKey": opt.groupNameKey || "",
        "pageSize": opt.pageSize || "10",
        "pageNum": opt.pageNum || "1",
        "type_status": "1",
        "managePlat": "0"
    };
    console.log(JSON.stringify($opt));
    myAjax(hostUrl + "/system/fixGroup/groupList", "params=" + JSON.stringify($opt), function (resp) {
        console.log(JSON.stringify(resp));
        for (var i = 0; i < resp.list.length; i++) {
            if (resp.list[i].status == "1") {
                resp.list[i].status = "启用";
            } else {
                resp.list[i].status = "禁用";
            }
        }
        store.groupList = resp.list;
        if (resp.list.length > 0) {
            store.pageCount = resp.list[0].pageCount;
        }
        initOptionList();
        if (typeof(callback) === "function") {
            callback(resp);
        }
    });
}


function initQuerySelectPersonOpt() {
    store.querySelectPersonOpt = {
        "pageSize": "10",
        "pageNum": "1",
        "userName": "",
        "loginName": "",
        "name": ""
    }
}


/***
 * 选择人时的搜索
 * @param opt
 * @param callback
 */
function initSelectPersonList(opt, callback) {
    var $opt = {
        "pageSize": opt.pageSize || "10",
        "pageNum": opt.pageNum || "1",
        "userName": opt.userName || "",
        "loginName": opt.loginName || "",
        "name": opt.name || ""
    };
    var _data = JSON.stringify($opt);
    myAjax(hostUrl + '/system/fixGroup/choosePeople', "params=" + _data, function (resp) {
        store.selectPersonList = resp.list;
        store.selectPersonPageCount = resp.list[0].pageCount;
        initSelectPersonOptionList();
        if (typeof(callback) === "function") {
            callback(resp);
        }
    })
}


function updateGroupMembers(group, insertList, removeList, callback) {
    updateGroupRemoveMembers(group, removeList, function () {
        updateGroupInsertMembers(group, insertList, function () {
            updateGroupInfos(group, function () {
                replaceGroupManager(group, function () {
                    if (typeof (callback) === "function") {
                        callback();
                    }
                });
            });
        });
    });
}

function updateGroupRemoveMembers(group, removeList, callback) {
    if (removeList.length == 0) {
        if (typeof (callback) === "function") {
            callback();
        }
        return;
    }
    var mems = [];
    for (var i = 0; i < removeList.length; i++) {
        var $item = {};
        $item.un = removeList[i].user_name;
        $item.ln = removeList[i].id;
        mems.push($item);
    }
    var _data = {};
    _data.mems = mems;
    _data.id = group.id;
    _data.cu = group.oldGroupManagerName;
    _data.tip = "remove";
    _data.name = group.name;
    myAjax(hostUrl + "/system/fixGroup/fixGroupUpdate", "params=" + JSON.stringify(_data), function (resp) {
        console.log(JSON.stringify(resp));
        if (resp.errorCode == "0") {
            if (typeof (callback) === "function") {
                callback();
            }
        } else {
            alert("删除群成员失败" + resp.msg);
        }
    });
}

function updateGroupInsertMembers(group, insertList, callback) {
    if (insertList.length == 0) {
        if (typeof (callback) === "function") {
            callback();
        }
        return;
    }
    var mems = [];
    for (var i = 0; i < insertList.length; i++) {
        var $item = {};
        $item.un = insertList[i].user_name;
        $item.ln = insertList[i].id;
        mems.push($item);
    }
    var _data = {};
    _data.mems = mems;
    _data.id = group.id;
    _data.cu = group.oldGroupManagerName;
    _data.tip = "add";
    _data.name = group.name;
    myAjax(hostUrl + "/system/fixGroup/fixGroupUpdate", "params=" + JSON.stringify(_data), function (resp) {
        console.log(JSON.stringify(resp));
        if (resp.errorCode == "0") {
            if (typeof (callback) === "function") {
                callback();
            }
        } else {
            alert("新增失败" + resp.msg);
            return;
        }

    });
}

function updateGroupInfos(group, callback) {
    //console.log(store.currentGroup)
    if (store.currentGroup.name == group.name && store.currentGroup.status == group.status) {
        if (typeof (callback) === "function") {
            callback();
        }
        return;
    }
    var _data = {};
    _data.id = group.id;
    _data.tip = "updateGroupInfo";
    _data.name = group.name;
    if (group.status == "启用") {
        _data.status = "1";
    } else if (group.status == "禁用") {
        _data.status = "2";
    }
    myAjax(hostUrl + "/system/fixGroup/fixGroupUpdate", "params=" + JSON.stringify(_data), function (resp) {
        console.log(JSON.stringify(resp));
        if (resp.errorCode == "0") {
            if (typeof (callback) === "function") {
                callback();
            }
        } else {
            alert("修改群信息失败" + resp.msg);
        }

    });
}

function replaceGroupManager(group, callback) {
    //console.log(JSON.stringify(group));
    if (group.oldGroupManagerId == group.cuId) {
        if (typeof (callback) === "function") {
            callback();
        }
        return;
    }
    var _data = {};
    var sb = {};
    _data.mems = [];
    sb.reMasterId = group.oldGroupManagerId
    sb.reMasterName = group.oldGroupManagerName;
    sb.masterId = group.cuId;
    sb.masterName = group.cu;
    _data.mems.push(sb);
    _data.id = group.id;
    _data.tip = "replaceMaster";

    console.log(JSON.stringify(_data));

    myAjax(hostUrl + "/system/fixGroup/fixGroupUpdate", "params=" + JSON.stringify(_data), function (resp) {
        console.log(JSON.stringify(resp));
        if (resp.errorCode == "0") {
            if (typeof (callback) === "function") {
                callback();
            }
        } else {
            alert("修改群管理员失败" + resp.msg);
        }

    });
}

// 固定群列表模块
Vue.component('list-part', {
    template: '#list_Part',
    props: {
        model: Object
    },
    data: function () {
        var _data = {};
        _data.store = store;
        _data.grouplist = store.groupList;
        _data.pageCount = store.pageCount;
        _data.groupName = "";
        _data.groupManagerName = "";
        //_data.createdTime="";
        _data.status = "";
        _data.pageNum = store.queryOpt.pageNum;
        _data.searchBeginDate = "";
        _data.searchEndDate = "";
        //console.log(JSON.stringify(_data));
        return _data;
    },
    ready: function () {
        $('.date-picker').datepicker({
            // rtl:Metronic.isRTL(),
            autoclose: true
        });
        $('#beginTime').on('change', function (e) {
            $('#endTime').datepicker('setStartDate', $('#begin').val());
        });
        $('#endTime').on('change', function (e) {
            $('#beginTime').datepicker('setEndDate', $('#end').val());
        })
    },
    methods: {
        addGroup: function () {
            obj.$data.isShow = false;
            store.editFlag = "add";

        },
        listReset: function () {
            var vm = this;
            var queryOpt={
                " pageNum": "1",
                "manageName": "",
                "startTime": "",
                "endTime": "",
                "status": "",
                "groupNameKey": "",
                "type_status": "1",
                "managePlat": "0",
                "pageSize": "10"
             };
            vm.groupName = "";
            vm.groupManagerName = "";
            vm.status = "";
            vm.searchBeginDate = "";
            vm.searchEndDate = "";
            // obj.$data.searchData={};
            initPageList(queryOpt, function (resp) {
            });
        },
        searchGroup: function () {
            var vm = this;
            store.queryOpt.groupNameKey = vm.groupName;
            store.queryOpt.manageName = vm.groupManagerName;
            if (vm.searchBeginDate != "") {
                store.queryOpt.startTime = vm.searchBeginDate + " 00:00";
            } else {
                store.queryOpt.startTime = "";
            }
            if (vm.searchEndDate != "") {
                store.queryOpt.endTime = vm.searchEndDate + " 23:59";
            }
            else {
                store.queryOpt.endTime = "";
            }
            console.log(store.queryOpt);
            if (vm.status == "启用") {
                store.queryOpt.status = "1";
            } else if (vm.status == "禁用") {
                store.queryOpt.status = "2";
            } else {
                store.queryOpt.status = "";
            }
            store.queryOpt.pageNum = 1;
            // _data.pageSize="10";
            // _data.pageNum="1";
            // _data.type_status="2";
            // _data.managePlat="0";
            initPageList(store.queryOpt, function (resp) {
//                store.groupList = resp.list;
                //vm.grouplist = resp.list;
            });
        },
        // 编辑固定群
        groupEdit: function (data) {
            //console.log(JSON.stringify(data));
            var _data = {
                "groupId": data.id
            };
            store.currentGroup = data;
            console.log(JSON.stringify(data));
            myAjax(hostUrl + "/system/fixGroup/fixGroupEdit", "params=" + JSON.stringify(_data), function (resp) {
                console.log(JSON.stringify(resp));
                store.currentGroup.memberList = [];
                for (var i = 0; i < resp.list.length; i++) {
                    var item = resp.list[i];
                    if (item.job == 1) {
                        store.currentGroup.managerName = item.user_name;
                        store.currentGroup.managerId = item.id;
//                        var arr=[];
//                        arr=item.name.split("-");
//                        if(arr.length!=0){
//                           item.name=arr[arr.length-1];
//                        }
                        store.oldManager = item;
                    } else {
                        store.currentGroup.memberList.push(item);
                    }
                }

                obj.$data.isShow = false;
                store.editFlag = "edit";
            })
        },
        removeGroup: function (data) {
            var vm = this;

            var $url = hostUrl + "/system/fixGroup/groupDelete";
            var _data = {};
            _data.groupId = data.id;
            _data.groupName = data.name;
            if (confirm("是否确定删除固定群组'" + data.name + "'")) {
                Metronic.startPageLoading();
                myAjax($url, "params=" + JSON.stringify(_data), function (resp) {
                    Metronic.stopPageLoading();
                    initPageList(store.queryOpt, function (resp) {
//                store.groupList = resp.list;
                        //vm.grouplist = resp.list;
                    });
                });
            }
        },
        // 分页操作
        prePage: function () {
            var vm = this;
            if (vm.pageNum == 1) {
                return;
            }

            vm.pageNum--;
            store.queryOpt.pageNum = vm.pageNum;
            initPageList(store.queryOpt, function (resp) {
//                store.groupList = resp.list;
                //vm.grouplist = resp.list;
            });
        },
        nextPage: function () {
            var vm = this;
            if (vm.pageNum >= store.pageCount) {
                return;
            }
            vm.pageNum++;
            store.queryOpt.pageNum = vm.pageNum;
            initPageList(store.queryOpt, function (resp) {
//                store.groupList = resp.list;
                //vm.grouplist = resp.list;
            });
        },
        pageChange: function (ele) {
            var vm = this;
            vm.pageNum = ele.target.value;
            store.queryOpt.pageNum = ele.target.value;
            initPageList(store.queryOpt, function (resp) {
//                store.groupList = resp.list;
                //vm.grouplist = resp.list;
            });
        },
        chooseStartTime: function () {

        }
    }
})
// 新增固定群模块
Vue.component('add-part', {
    template: '#add_Part',
    //props: {
    //    personlist: Object
    //},
    data: function () {
        var _data = {};
        _data.store = store;
        _data.pageNum = 1;
        _data.searchSelectMemOpt = {
            name: "",
            username: "",
            department: ""
        };   //选择人员界面，搜索人员列表
        _data.isManagerSelect = "";     //是否群管理员选择人
        _data.isOn = false;
        _data.searchMemberOpt = {
            login_name: "",
            user_name: "",
            name: ""
        };

        _data.currentGroup = {};     //当前群组信息
        _data.currentGroup.selectManagerInfo = {};
        _data.currentGroup.selectMemberInfo = {};
        _data.currentGroup.membersListMap = {};
        // console.log(this.model.editFlag);
        if (store.editFlag == "add") {
            _data.currentGroup.membersList = [];
            _data.currentGroup.groupName = "";
            _data.currentGroup.groupManagerName = "";
            _data.currentGroup.status = "启用";
            _data.currentGroup.groupManagerId = "";
            //console.log(_data.currentGroup.membersList);
        } else {
            _data.currentGroup.groupName = store.currentGroup.name;
            _data.currentGroup.groupManagerId = store.currentGroup.managerId;
            _data.currentGroup.groupManagerName = store.currentGroup.managerName;
            _data.currentGroup.oldGroupManagerId = store.currentGroup.managerId;
            _data.currentGroup.oldGroupManagerName = store.currentGroup.managerName;
            _data.currentGroup.groupId = store.currentGroup.id;
            // _data.groupManagerId=this.model.groupManagerId;
            _data.currentGroup.status = store.currentGroup.status;
            _data.currentGroup.membersList = store.currentGroup.memberList;
            _data.currentGroup.mems = [];
//            for(var i=0;i<_data.currentGroup.membersList.length; i++){
//                var arr=[];
//                arr=_data.currentGroup.membersList[i].name.split('-');
//                if(arr.length!=0){
//                    _data.currentGroup.membersList[i].name=arr[arr.length-1];
//                }
//            }
            for (var i = 0; i < _data.currentGroup.membersList.length; i++) {
                var item = _data.currentGroup.membersList[i];
                _data.currentGroup.mems.push(item);
            }
            //_data.mems=this.model.list;
        }
        // 群组状态为禁用，群组除状态外不可编辑
        if(store.currentGroup.status == "禁用"){
            _data.isForbid = true;
        }
        
        return _data;
    },
    ready: function () {
        this.initMembersListMap();
        var list = document.getElementsByClassName('isForbid');
        for(var i=0;i<list.length;i++){
            list[i].setAttribute('disabled',true);
        }
    },
    methods: {
        // 选择普通成员操作
        initMembersListMap: function () {
            var vm = this;
            for (var i = 0; i < vm.currentGroup.membersList.length; i++) {
                var item = vm.currentGroup.membersList[i]
                vm.currentGroup.membersListMap[item.id] = item;
            }
        },
        chosenFn: function () {
            var vm = this;
            initQuerySelectPersonOpt();
            this.isOn = true;
            this.isManagerSelect = 1;
            this.searchSelectMemOpt.name = "";
            this.searchSelectMemOpt.username = "";
            this.searchSelectMemOpt.department = "";
            vm.currentGroup.selectMemberInfo = {};
            console.log("::::");
            initSelectPersonList(store.querySelectPersonOpt, function (resp) {
                //vm.selectPersonList = resp.list;
            });
        },
        // 选择群管理员操作
        selectManager: function () {
            var vm = this;
            initQuerySelectPersonOpt();
            this.searchSelectMemOpt.name = "";
            this.searchSelectMemOpt.username = "";
            this.searchSelectMemOpt.department = "";
            this.isOn = true;
            this.isManagerSelect = 2;

            initSelectPersonList(store.querySelectPersonOpt, function (resp) {
                //vm.selectPersonList = resp.list;
            });
        },
        toBack: function () {
            obj.$data.isShow = true;
        },
        // 固定群成立，修改完成
        toSure: function () {
            var vm = this;


                    if (store.editFlag == "add") {
                        var abc={};
                        abc.groupName=vm.currentGroup.groupName;
                        myAjax(hostUrl+"/system/fixGroup/selectGroupName","params=" +JSON.stringify(abc), function (resp) {
                            console.log(resp);
                            if(resp.errorCode==0){
                                alert("群组名称已存在");
                                return;
                            }else if(resp.errorCode==2){
                                if (vm.currentGroup.groupName.trim() == "") {
                                    alert("群组名称不能为空");
                                    return;
                                }
                                if (vm.currentGroup.groupManagerId.trim() == "") {
                                    alert("群组负责人不能为空");
                                    return;
                                }
                                if (vm.currentGroup.membersList.length == 0) {
                                    alert("群组成员不能为空");
                                    return;
                                }
                                var _data={};
                                var _memberList = [];
                                for (var i = 0; i < vm.currentGroup.membersList.length; i++) {
                                    var _obj = {};
                                    _obj.ln = vm.currentGroup.membersList[i].id;
                                    _obj.un = vm.currentGroup.membersList[i].user_name;
                                    //if(vm.currentGroup.memberList[i]!={}){
                                    _memberList.push(_obj);
                                    //}
                                }
                                _data.mems = _memberList;
                                _data.name = vm.currentGroup.groupName;
                                _data.cu = vm.currentGroup.groupManagerName;
                                _data.cuId = vm.currentGroup.groupManagerId;
                                _data.type = "1";
                                _data.status = "1";
                                _data = JSON.stringify(_data);
                                Metronic.startPageLoading();
                                $('.submitBtn1').attr('disabled', 'disabled');
                                myAjax(hostUrl + "/system/fixGroup/addGroup", "params=" + _data, function (resp) {
                                    console.log(JSON.stringify(resp));
                                    //封装后调用
                                    Metronic.stopPageLoading();
                                    // $('.submitBtn1').attr('disabled', 'false');
                                    if (resp.errorCode == "0") {
                                        initPageList(store.queryOpt, function (resp2) {
                                            obj.$data.isShow = true;
                                        });
                                    } else {
                                        alert("新增失败" + resp.msg);
                                    }

                                });
                             }
                        })
                    } else {
                        //编辑入口
                        console.log("编辑入口");
                        var abc={};
                        abc.groupName=vm.currentGroup.groupName;
                        myAjax(hostUrl+"/system/fixGroup/selectGroupName","params=" +JSON.stringify(abc), function (resp) {
                            console.log(resp);
                            if(resp.errorCode==0 && store.currentGroup.name!=vm.currentGroup.groupName){
                                alert("群组名称已存在");
                                return;
                            }else if(resp.errorCode==2 || store.currentGroup.name == vm.currentGroup.groupName){
                            if (vm.currentGroup.groupName.trim() == "") {
                                alert("群组名称不能为空");
                                return;
                            }
                            if (vm.currentGroup.groupManagerName.trim() == "") {
                                alert("群组负责人不能为空");
                                return;
                            }
                            if (vm.currentGroup.membersList.length == 0) {
                                alert("群组成员不能为空");
                                return;
                            }
                            var insertList = [];
                            var removeList = [];

                            var oldMembers = {};
                            var oldArray = vm.currentGroup.mems;
                            for (var i = 0; i < oldArray.length; i++) {
                                var m = oldArray[i];
                                oldMembers[m.id] = m;
                            }
                            var newMembers = {};
                            var newArray = vm.currentGroup.membersList;
                            // 获取新加入的成员列表
                            console.log(store.oldManager.id);
                            for (var i = 0; i < newArray.length; i++) {
                                var m = newArray[i];
                                newMembers[m.id] = m;
                                console.log(m.id);
                                if(store.oldManager.id!= m.id){
                                    if (oldMembers[m.id] == null) {
                                        insertList.push(m);
                                    }
                                }

                            }
                            console.log(insertList);
                            // 获取已移除的成员列表
                            for (var i = 0; i < oldArray.length; i++) {
                                var m = oldArray[i];
                                if(vm.currentGroup.groupManagerId!= m.id){
                                    if (newMembers[m.id] == null) {
                                        removeList.push(m);
                                    }
                                }

                            }
                            console.log(removeList);
                            // 没有增加也没有移除的情况
                            if (insertList.length == 0 && removeList.length == 0) {
                                console.log("成员没有更新");
                                //return;
                            }
                            var group = {};
                            group.name = vm.currentGroup.groupName;
                            group.id = vm.currentGroup.groupId;
                            group.cu = vm.currentGroup.groupManagerName;
                            group.cuId = vm.currentGroup.groupManagerId;
                            group.oldGroupManagerName = vm.currentGroup.oldGroupManagerName;
                            group.oldGroupManagerId = vm.currentGroup.oldGroupManagerId;
                            group.status = vm.currentGroup.status;
                            //console.log(JSON.stringify(group));
                            //console.log(JSON.stringify(insertList));
                            //console.log(JSON.stringify(removeList));
                            //封装后调用
                            Metronic.startPageLoading();
                            $('.submitBtn1').attr('disabled', 'disabled');
                            updateGroupMembers(group, insertList, removeList, function (data) {
                                initPageList(store.queryOpt, function (resp2) {
                                    //封装后调用
                                    Metronic.stopPageLoading();
                                    $('.submitBtn1').attr('disabled', 'false');
                                    obj.$data.isShow = true;
                                });
                            });
                        }
                    })
                    }

            //提交的数据

        },
        // 弹出框的重置，查询操作
        personReset_btn: function () {
            this.searchSelectMemOpt = {};
            var querySelectPersonOpt={
                "pageSize": "10",
                    "pageNum": "1",
                    "userName": "",
                    "loginName": "",
                    "name": ""
            };
            initSelectPersonList(querySelectPersonOpt, function (resp) {
            });
        },
        personSearch_btn: function () {
            var vm = this;
            store.querySelectPersonOpt.userName = this.searchSelectMemOpt.name;
            store.querySelectPersonOpt.loginName = this.searchSelectMemOpt.username;
            store.querySelectPersonOpt.name = this.searchSelectMemOpt.department;
            store.querySelectPersonOpt.pageNum = "1";
            initSelectPersonList(store.querySelectPersonOpt, function (resp) {
                //vm.selectPersonList = resp.list;
                //console.log(vm.searchSelectMemOpt);
            });
        },
        //弹出框的选择成员确认，选中操作
        pop_cancel: function () {
            this.isOn = false;
            this.pageNum = 1;
        },
        pop_ensure: function () {
            var vm = this;
            if (vm.isManagerSelect == "2") {
                if (vm.currentGroup.selectManagerInfo != {}) {
                    //管理员选择
                    if (vm.currentGroup.membersListMap[vm.currentGroup.selectManagerInfo.id]) {
                        //如果该用户已经被选择了
                        vm.currentGroup.groupManagerId = vm.currentGroup.selectManagerInfo.id;
                        vm.currentGroup.groupManagerName = vm.currentGroup.selectManagerInfo.user_name;
                        var $index = -1;
                        for (var i = 0; i < vm.currentGroup.membersList.length; i++) {
                            var m = vm.currentGroup.membersList[i];
                            if (m.id == vm.currentGroup.selectManagerInfo.id) {
                                $index = i;
                            }
                        }
                        delete this.currentGroup.membersListMap[vm.currentGroup.selectManagerInfo.id];
                        vm.currentGroup.membersList.splice($index, 1);
                    } else {
                        vm.currentGroup.groupManagerId = vm.currentGroup.selectManagerInfo.id;
                        vm.currentGroup.groupManagerName = vm.currentGroup.selectManagerInfo.user_name;
                    }

                    if (vm.currentGroup.selectManagerInfo.id != vm.currentGroup.oldGroupManagerId) {
                        if(store.editFlag!="add"){
                            vm.currentGroup.membersList.push(store.oldManager);
//                            store.oldManager = vm.currentGroup.selectManagerInfo;
                        }
                    }
                }
            } else {
                if (vm.currentGroup.selectMemberInfo != {}) {
                    //console.log("::::::::::::::"+vm.groupManagerId+":::::::::::::"+vm.selectManagerInfo.id);
                    //vm.currentGroup.membersListMap
                    console.log(JSON.stringify(vm.currentGroup.membersListMap));
                    var $selectInfo = vm.currentGroup.selectMemberInfo;
                    for (var key in $selectInfo) {
                        if (key == vm.currentGroup.groupManagerId) {

                        } else if (typeof (vm.currentGroup.membersListMap[key]) !== "undefined") {
                            console.log("已有");
                        } else {
                            vm.currentGroup.membersList.push($selectInfo[key]);
                        }
                    }
                    vm.initMembersListMap();
//                    if(vm.currentGroup.groupManagerId==vm.currentGroup.selectManagerInfo.id){
//                        alert("该用户是群组管理员，不可以选择");
//                    }else{
//
//                    }
                }
            }
            vm.isOn = false;
        },
        clickManager: function (member) {
            var vm = this;
            vm.currentGroup.selectManagerInfo = member;
        },
        selectMember: function (event, member) {
            var vm = this;
            var target = event.target;
            var id = member.id;
            //console.log(target.checked);
            if (target.checked) {
                vm.currentGroup.selectMemberInfo[id] = member;
            } else {
                delete vm.currentGroup.selectMemberInfo[id];
            }
            //console.log(JSON.stringify(vm.currentGroup.selectMemberInfo));
        },
        selectAllMember:function(event){
            var vm = this;
           var list = document.getElementsByClassName('selectAll');
           var target = event.target;
           if(target.checked) {
                for(var i=0;i<list.length;i++){
                    list[i].checked=true;
                }
                for(var i=0;i<store.selectPersonList.length;i++){
                    var id=store.selectPersonList[i].id;
                    vm.currentGroup.selectMemberInfo[id] =store.selectPersonList[i];
                }
            }else {
                for(var i=0;i<list.length;i++){
                    list[i].checked=false;
                }
                for(var i=0;i<store.selectPersonList.length;i++){
                    var id=store.selectPersonList[i].id;
                    delete vm.currentGroup.selectMemberInfo[id];
                }
            }
        },
        // 删除成员
        remmovePerson: function (index) {
            var id = this.currentGroup.membersList[index].id;
            this.currentGroup.membersList.splice(index, 1);
            delete this.currentGroup.membersListMap[id];
        },
        // 分页操作
        prePage: function () {
            var vm = this;
            if (vm.pageNum == 1) {
                return;
            }

            vm.pageNum--;
            store.querySelectPersonOpt.pageNum = vm.pageNum;
            initSelectPersonList(store.querySelectPersonOpt, function (resp) {
//                store.groupList = resp.list;
                //vm.grouplist = resp.list;
            });
        },
        nextPage: function () {
            var vm = this;
            if (vm.pageNum >= store.selectPersonPageCount) {
                return;
            }
            vm.pageNum++;
            store.querySelectPersonOpt.pageNum = vm.pageNum;
            initSelectPersonList(store.querySelectPersonOpt, function (resp) {
//                store.groupList = resp.list;
                //vm.grouplist = resp.list;
            });
        },
        pageChange: function (ele) {
            var vm = this;
            vm.pageNum = ele.target.value;
            store.querySelectPersonOpt.pageNum = ele.target.value;
            initSelectPersonList(store.querySelectPersonOpt, function (resp) {
//                store.groupList = resp.list;
                //vm.grouplist = resp.list;
            });
        }
    }
});
Vue.filter("memberSearch", function (value) {
    //console.log(JSON.stringify(value));
    return value;
});
