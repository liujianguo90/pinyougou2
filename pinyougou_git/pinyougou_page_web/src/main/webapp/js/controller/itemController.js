window.onload=function () {
    var app=new Vue({
        el:"#app",
        data:{
            num :1,
            //定义一个变量用于记录用户的规格
            specificationItems:{},
            //定义一个变量用于存储sku信息
            sku:{}
        },
        methods:{
            //添加购物车的方法
            //定义一个功能用于加入到购物车
            addToCart:function () {
                axios.get('http://localhost:18094/cart/addGoodsToCartList.do?itemId='
                    + this.sku.id +'&num='+this.num).then(function (response) {
                    if(response.data.success){
                        //跳转到购物车页面
                        window.location.href='http://localhost:18094/cart.html';
                    }else{
                        alert(response.data.message);
                    }
                });

            },

            addNum:function (x) {
                this.num=x;
                if(this.num<1){
                    this.num=1;
                }
            },
            //选定规格后查询相应的sku
            searchSku:function () {
              for(var i=0;i<skuList.length;i++){
                  //用用户的选中的规格信息与sku列表的规格信息对比
                  if(this.matchObject(skuList[i].spec,this.specificationItems)){
                             this.sku=skuList[i];
                             return;
                    }
                  }
                  //如果没有匹配到的
                this.sku={id:0,title:"---------",price:0};
            },
            //将用户勾选的规格加入到集合中去
            selectSpecification: function (specName, optionName) {
                //this.specificationItems[specName] = optionName;
                this.$set(this.specificationItems, specName, optionName);
                //勾选后将sku更新
                this.searchSku();
            },

            // 下面定义方法判断用户是否选中
            isSelected: function (specName, optionName) {
                return this.specificationItems[specName] == optionName;
            },
            //加载默认的购买商品
            loadSku:function () {
                //记录默认的要购买的商品我们是按照默认排序查的所以这里我们选择第一条
                this.sku=skuList[0];
                //将默认产品的规格选项加入到规格选项中
                //为了防止选择的变动我们这里采用的是深克隆
                this.specificationItems= JSON.parse(JSON.stringify(this.sku.spec));
            },
            //匹配两个内容是否一致
            matchObject:function (map1,map2) {
                for(var k in map1){
                    if(map1[k]!=map2[k]){
                         return false;
                    }
                }
                for(var k in map2){
                    if(map2[k]!=map1[k]){
                        return false;
                    }
                    return true;
                }

            }
        },
            //初始化调用
            created:function () {
                //选中默认商品
                this.loadSku();
            }

        })
}