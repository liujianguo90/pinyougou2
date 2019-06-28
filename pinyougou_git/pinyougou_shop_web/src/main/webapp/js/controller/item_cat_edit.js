//页面初始化完成后再创建Vue对象
window.onload=function () {
    //创建Vue对象
    var app = new Vue({
        //接管id为app的区域
        el:"#app",
        data:{
            //声明数据列表变量，供v-for使用
            list:[],
            //总页数
            pages:1,
            //当前页
            pageNo:1,
            //声明对象
            entity:{},
            //选中的id列表
            ids:[],
            //搜索包装对象
            searchEntity:{},
            //选中的父级id,默认为0
            itemCatId:0,

            //记录面包屑当前级别
            grade:1,
            //一级分类
            entity_1:{name:'顶级分类列表',id:0},
            //二级分类
            entity_2:{},
            //三级分类
            entity_3:{}
        },
        methods:{
            //查询所有
            findAll:function () {
                axios.get("../itemCat/findAll.do").then(function (response) {
                    //vue把数据列表包装在data属性中
                    app.list = response.data;
                }).catch(function (err) {
                    console.log(err);
                });
            },
            //分页查询
            findPage:function (pageNo,id,itemCatName) {
                //记录当前父节点
                this.itemCatId = id;

                //当前在一级分类下
                if(this.grade == 1){
                    //清空2,3级的面包屑
                    this.entity_2 = {};
                    this.entity_3 = {};

                }else if(this.grade == 2){  //当前在二级分类下
                    //this.entity_2 = {name:itemCatName,id:this.itemCatId};
                    this.entity_2.name =itemCatName;
                    this.entity_2.id =this.itemCatId;
                    this.entity_3 = {};

                }else if(this.grade == 3){
                    // this.entity_3 = {name:itemCatName,id:this.itemCatId};
                    this.entity_3.name = itemCatName;
                    this.entity_3.id = this.itemCatId;
                }
                this.grade++;

                if(id==undefined){
                    id=0;
                }
                axios.post("/itemCat/findPage.do?pageNo="+pageNo+"&pageSize="+10+"&id="+id,this.searchEntity)
                    .then(function (response) {
                        app.pages = response.data.pages;  //总页数
                        app.list = response.data.rows;  //数据列表
                        app.pageNo = pageNo;  //更新当前页

                        app.itemCatId = id;//父类id
                    });
            },
            //让分页插件跳转到指定页
            goPage:function (page) {
                app.$children[0].goPage(page);
            },
            //新增
            add:function () {
                this.entity.parentId = this.itemCatId;
                axios.post("/itemCat/add.do", this.entity).then(function (response) {
                    if (response.data.success) {
                        //刷新数据，刷新当前页
                        //window.location.reload();
                        app.findPage(app.pageNo,app.itemCatId);
                        app.entity = {};
                    } else {
                        //失败时显示失败消息
                        alert(response.data.message);
                    }
                });
            },
            //跟据id查询
            getById:function (id) {
                axios.get("../itemCat/getById.do?id="+id).then(function (response) {
                    app.entity = response.data;
                })
            },
            //批量删除数据
            dele:function () {
                axios.get("../itemCat/delete.do?ids="+this.ids).then(function (response) {
                    if(response.data.success){
                        //刷新数据
                        app.findPage(app.pageNo,app.itemCatId);
                        //清空勾选的ids
                        app.ids = [];
                    }else{
                        alert(response.data.message);
                    }
                })
            },
            //提交审核
            submitCheck:function () {
                axios.get("../itemCat/submitCheck.do?ids="+this.ids).then(function (response) {
                    if(response.data.success){
                        //刷新数据
                        app.findPage(app.pageNo,app.itemCatId);
                        //清空勾选的ids
                        app.ids = [];
                    }else{
                        alert(response.data.message);
                    }
                })
            }

        },
        //Vue对象初始化后，调用此逻辑
        created:function () {
            //调用用分页查询，初始化时从第1页开始查询
            this.findPage(1,0);
        }
    });
}