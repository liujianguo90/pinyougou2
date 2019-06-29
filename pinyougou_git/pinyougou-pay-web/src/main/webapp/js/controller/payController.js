app.controller('payController' ,function($scope ,payService,$location){
    //本地生成二维码
    $scope.createNative=function(){
        payService.createNative().success(
            function(response){
                $scope.money =  (response.total_fee/100).toFixed(2) ;	//金额
                $scope.out_trade_no = response.out_trade_no;//订单号
                //二维码
                var qr = new QRious({
                    element:document.getElementById('qrious'),
                    size:250,
                    level:'H',
                    value:response.code_url
                });
                $scope.queryPayStatus(response.out_trade_no);//查询支付状态
            }
        );
    }

    // //查询支付状态
    // $scope.queryPayStatus=function(out_trade_no){
    //     payService.queryPayStatus(out_trade_no).success(
    //         function(response){
    //             if(response.success){
    //                 location.href="paysuccess.html";
    //             }else{
    //                 location.href="payfail.html";
    //             }
    //         }
    //     );
    // }

    //查询支付状态
    $scope.queryPayStatus=function(out_trade_no){
        payService.queryPayStatus(out_trade_no).success(
            function(response){
                if(response.success){
                    location.href="paysuccess.html#?money="+$scope.money;
                }else{
                    if(response.message=='二维码超时'){
                        $scope.createNative();//重新生成二维码
                    }else{
                        location.href="payfail.html";
                    }
                }
            }
        );
    }
    //方法 当paysuccess.html的页面加载 就要调用该方法 就是使用$location 获取页面产地过来的参数

    $scope.getMoney=function () {
        var money = $location.search()['money'];
        $scope.money=money;
    }

});