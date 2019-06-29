app.service('payService',function($http){
    //本地支付
    this.createNative=function(){
        return $http.get('http://localhost:9107/pay/createNative.do',{'withCredentials':true});
    }

    //查询支付状态
    this.queryPayStatus=function(out_trade_no){
        return $http.get('http://localhost:9107/pay/queryPayStatus.do?out_trade_no='+out_trade_no,
            {'withCredentials':true});
    }
});