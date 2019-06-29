//品优购用户中心-个人信息
var app = new Vue({
    el: "#app",
    data: {
        list: [],
        entity: {},
        id: '',
        smsCode: '',
        username: '',
        searchEntity: {},
        birthday:{year:1990,month:1,day:1},//生日
        imgi:'',//保存图片地址
        addressEntity:{'isDefault':'0'},
        addressList:[],
        address11List:[],//一级地址的列表 变量
        address22List:[],//二级地址的列表 变量
        address33List:[],//三级地址的列表 变量
    },
    methods: {
        //根据地址ID查询地址,回显到修改页面
        findOne:function(id){
            axios.get('/address/findOne/'+id+'.shtml').then(
                function (response) {
                    app.addressEntity=response.data;
                }
            )
        },
        //获取一级分类的类别的方法
        findaddress11List:function () {
            axios.get('/address/findByParent11Id.shtml').then(
                function (response) {
                    //获取列表数据
                    app.address11List=response.data;
                }
            )
        },
        //根据用户名查找用户拥有的地址
        findAll:function(){
            axios.get('/address/findAll.shtml').then(
                function (response) {
                    app.addressList = response.data;
                }
            )
        },
        //修改地址的方法
        update: function () {
            axios.post('/address/update.shtml', this.addressEntity).then(function (response) {
                alert(response.data.message);
                if(response.data.success){
                    window.location.href="home-setting-address.html";
                }
            }).catch(function (error) {
                console.log("更新失败");
            });
        },
        //添加地址的方法
        add:function(){
            axios.post('/address/add.shtml', this.addressEntity).then(function (response) {
                alert(response.data.message);
                if(response.data.success){
                    window.location.href="home-setting-address.html";
                }
            }).catch(function (error) {
                console.log("更新失败");
            });
        },
        save: function () {
            if (this.addressEntity.id != null) {
                this.update();
            } else {
                this.add();
            }
        },
        dele: function (id) {
            axios.get('/address/delete/'+id+'.shtml').then(function (response) {
                if (response.data.success) {
                    //删除成功
                    alert(response.data.message);

                    app.findAll();
                }
            }).catch(function (error) {
                //删除失败
                alert(response.data.message);
            });
        },
        createSmsCode: function () {
            axios.post('user/sendCode.shtml?phone=' + this.entity.phone).then(function (response) {
                if (response.data.success) {
                    alert(response.data.message);
                } else {
                    //验证码发送失败
                    alert(response.data.message);
                }
            }).catch(function (error) {
                console.log("验证码获取失败")
            });
        },
        //根据用户名查找用户信息,用于页面回显
        one: function () {
            axios.get('/user/one.shtml').then(function (response) {
                app.entity = response.data;
                app.imgi = response.data.headPic
                var date = app.entity.birthday;
                //把字符串截取成数组
                var timearr = date.replace(" ", ":").replace(/\:/g, "-").split("-");
                //不需要0几,所有先把字符串转换成int整型,
                app.birthday.year=parseInt(timearr[0]);
                app.birthday.month=parseInt(timearr[1]);
                app.birthday.day=parseInt(timearr[2]);


            }).catch(function (error) {
                console.log("查询单个数据失败");
            });
        },
        getName: function () {
            axios.get('/user/getName.shtml').then(function (response) {
                app.username = response.data;

                app.one()

            }).catch(function (error) {
                console.log("获取用户名失败")
            });
        }
    },
    //定义一个监听
    watch:{
        //监听变量：entity.goods.category1Id 的变化  触发 一个函数 发送请求 获取 一级分类的下的二级分类的列表
        'addressEntity.provinceId':function (newval,oldval) {
            if(newval!=undefined) {
                axios.get('/address/findByParent22Id/' + newval + '.shtml').then(
                    function (response) {
                        //获取列表数据
                        app.address22List = '';
                        app.address33List = '';
                        app.address22List = response.data;
                    }
                )
            }
        },
        //监听二级分类的id的变化  查询 二级分类下的三级分类的列表数据
        'addressEntity.cityId':function (newval,oldval) {
            if(newval!=undefined) {
                axios.get('/address/findByParent33Id/' + newval + '.shtml').then(
                    function (response) {
                        //获取列表数据 三级分类的列表
                        app.address33List = response.data;
                    }
                )
            }
        }
    },
    //钩子函数 初始化了事件和
    created: function () {
        this.getName();
        this.findAll();
    }
});
