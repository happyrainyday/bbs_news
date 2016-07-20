var app = angular.module("customApp",[]);

app.filter('limitToNum', function() {  
   return function(input, num) {  
      if(input.length>num){
        input = input.substring(0,num)+"...";
      }
      return input;  
   };  
 });

app.filter('timeFormat',function(){
	return function(time){
//		var nowDate = $filter('date')(new Date(),'sss');
//		var leftTime = time - nowDate;
		var oldTime = (new Date()).getTime();
		var leftTime =  oldTime - time ;
		if (leftTime < 1000){
			return "1秒";
		}
		
		else if (leftTime < 60*1000){
			return parseInt(leftTime/1000) + "秒";
		}
		
		else if (leftTime < 60*60*1000){
			return parseInt(leftTime/(60*1000)) + "分";
		}
		
		else if (leftTime < 24*60*60*1000){
			return parseInt(leftTime/(60*60*1000)) + "小时";
		}
		
		else if (leftTime < 30*24*60*60*1000){
			return parseInt(leftTime/(24*60*60*1000)) + "天";
		}
		else if (leftTime < 12*30*24*60*60*1000){
			return parseInt(leftTime/(30*24*60*60*1000)) + "月";
		}
		else {
			return parseInt(leftTime/(12*30*24*60*60*1000)) + "年";
		}
	};
	
});

app.directive('bsPopup', function ($parse) {
    return {
        require: '^ngModel',
        restrict: 'A',
        scope: {  
        	content123: '='  
        },
        link: function (scope, elem, attrs, ctrl) {
            scope.$watch(function () {
                return $parse(ctrl.$modelValue)(scope);
            }, function (newValue) {
                if (newValue == 0) {
                    $(elem).modal('hide');
                    return;
                }
                if (newValue == 1) {
                    $(elem).modal('show');
                    return;
                }
            });
        }
    }
});

