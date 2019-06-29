//服务层
app.service('userInfoService',function($http){

	//增加 
	this.add=function(entity) {
        return $http.post('../user/add.do', entity);
    };

    this.uploadFile=function() {
        var formData = new FormData();
        formData.append("file", file.files[0]);
        return $http({
            method: 'POST',
            url: "../user/upload.do",
            data: formData,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        });
    }

    });
