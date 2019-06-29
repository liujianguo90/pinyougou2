//控制层
app.controller('userInfoController', function ($scope, userInfoService,userService) {

    //写一个方法  当点击注册时候调用 数据插入到数据库表中
    $scope.regsiter = function () {
        userInfoService.add($scope.entity).success(
            function (response) {//result
                if (response.success) {
                    //跳转到奥登录页面
                    alert("注册成功");
                    location.href = "home-index.html"
                } else {
                    alert(response.message);
                }
            }
        )
    };

    $scope.uploadFile=function(){
        userInfoService.uploadFile().success(function(response) {
            if(response.success){//如果上传成功，取出url
               alert(response.message)//设置文件地址
            }else{
                alert(response.message);
            }
        }).error(function() {
            alert("上传发生错误");
        });
    };
    $scope.entity={User:{},TbAddress:{}}


    //获取到登录人的账号
    $scope.getUser = function () {
        userService.getUser().success(
            function (response) {
                $scope.tbUser = response;
            }
        )
    };
});
