//控制层
app.controller('userController', function ($scope, $location, userService) {

    //写一个方法  当点击注册时候调用 数据插入到数据库表中

    $scope.regsiter = function () {
        if ($scope.entity.password != $scope.confirmPassword) {
            alert("密码不一致");
            return;
        }
        userService.add($scope.entity, $scope.code).success(
            function (response) {//result
                if (response.success) {
                    //跳转到奥登录页面
                    alert("成功");
                    location.href = "login.html"
                } else {
                    alert(response.message);
                }
            }
        )
    }


    //写一个方法  当点击 获取验证码 的按钮的时候调用
    $scope.createSmsCode = function () {
        userService.createSmsCode($scope.entity.phone).success(
            function (response) {//result
                alert(response.message);
            }
        )
    };

    //获取到登录人的账号
    $scope.getUser = function () {
        userService.getUser().success(
            function (response) {
                $scope.tbUser = response;
            }
        )
    };



    $scope.status = ["去付款", "待发货", "未发货", "已发货", "交易成功", "交易关闭", "待评价"];

    $scope.payment = ["在线支付", "货到付款"]

    $scope.stat = 0;
    $scope.getOrderInfo = function () {
        userService.getOrderInfo().success(
            function (response) {
                $scope.list = response;

            }
        )
    };


});
