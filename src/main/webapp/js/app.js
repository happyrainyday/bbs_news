angular.module('exampleApp', ['ngRoute', 'ngCookies', 'tm.pagination','exampleApp.services', 'customApp'])
	.config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {
			
			$routeProvider.when('/create', {
				templateUrl: 'partials/create.html',
				controller: CreateController
			});
			
			$routeProvider.when('/edit/:id', {
				templateUrl: 'partials/edit.html',
				controller: EditController
			});

			$routeProvider.when('/login', {
				templateUrl: 'partials/login.html',
				controller: LoginController
			});
      
			$routeProvider.when('/register', {
				templateUrl: 'partials/register.html',
				controller: RegisterController
			});
			
			$routeProvider.when('/password', {
				templateUrl: 'partials/password.html',
				controller: PasswordController
			});
			
			$routeProvider.when('/user', {
				templateUrl: 'partials/user.html',
				controller: UserController
			});
			
			$routeProvider.otherwise({
				templateUrl: 'partials/index.html',
				controller: IndexController
			});
			
			$locationProvider.hashPrefix('!');
			
			/* Register error provider that shows message on failed requests or redirects to login page on
			 * unauthenticated requests */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
			        return {
			        	'responseError': function(rejection) {
			        		var status = rejection.status;
			        		var config = rejection.config;
			        		var method = config.method;
			        		var url = config.url;
			      
			        		if (status == 401) {
			        			$location.path( "/login" );
			        			$rootScope.error = "Username or Password is not correct";
			        		} else {
			        			$rootScope.error = method + " on " + url + " failed with status " + status;
			        		}
			              
			        		return $q.reject(rejection);
			        	}
			        };
			    }
		    );
		    
		    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
		     * as soon as there is an authenticated user */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
		        return {
		        	'request': function(config) {
		        		var isRestCall = config.url.indexOf('rest') == 0;
		        		if (isRestCall && angular.isDefined($rootScope.authToken)) {
		        			var authToken = $rootScope.authToken;
		        			if (exampleAppConfig.useAuthTokenHeader) {
		        				config.headers['X-Auth-Token'] = authToken;
		        			} else {
		        				config.url = config.url + "?token=" + authToken;
		        			}
		        		}
		        		return config || $q.when(config);
		        	}
		        };
		    }
	    );
		   
		} ]
		
	).run(function($rootScope, $location, $cookieStore, UserService) {
		
		/* Reset error when a new view is loaded */
		$rootScope.$on('$viewContentLoaded', function() {
			delete $rootScope.error;
		});
		
		$rootScope.hasRole = function(role) {
			
			if ($rootScope.user === undefined) {
				return false;
			}
			
			if ($rootScope.user.roles[role] === undefined) {
				return false;
			}
			
			return $rootScope.user.roles[role];
		};
		
		$rootScope.logout = function() {
			delete $rootScope.user;
			delete $rootScope.authToken;
			$cookieStore.remove('authToken');
			$location.path("/login");
		};
		
		 /* Try getting valid user from cookie or go to login page */
		var originalPath = $location.path();
		$location.path("/login");
		var authToken = $cookieStore.get('authToken');
		if (authToken !== undefined) {
			$rootScope.authToken = authToken;
			UserService.get(function(user) {
				$rootScope.user = user;
				
				$location.path(originalPath);
			});
		}
		
		$rootScope.initialized = true;
	});


function IndexController($scope, NewsService) {
	
	$scope.getNewsPageList = function() {

		var postData = {
			pageIndex : $scope.paginationConf.currentPage,
			pageSize : $scope.paginationConf.itemsPerPage
		}

		NewsService.pager(postData, function(news) {
			$scope.paginationConf.totalItems = news.data.totalPage;
			$scope.newsEntries = news.data.content;
		});
	}
	
    //配置分页基本参数
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 5
    };
    
    /***************************************************************
    当页码和页面记录数发生变化时监控后台查询
    如果把currentPage和itemsPerPage分开监控的话则会触发两次后台事件。
    ***************************************************************/
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.getNewsPageList);
    
//    $scope.newsEntries = NewsService.query();
	
	$scope.deleteEntry = function(id) {
		NewsService.remove({id: id},{},function() {
			$scope.getNewsPageList();
		});
	};
	
    //	赞更新
	$scope.updateUpVotes = function(id, index){
		NewsService.voter({id: id, action: 'up'},{},function(result){
			$scope.upVotes = $scope.newsEntries[index].upVotes + 1;
			$scope.newsEntries[index].upVotes = $scope.upVotes;
		});
	};
	
    //	踩更新
	$scope.updateDownVotes = function(id, index){
		
		NewsService.voter({id: id, action: 'down'},{},function(result){
			$scope.downVotes = $scope.newsEntries[index].downVotes + 1;
			$scope.newsEntries[index].downVotes = $scope.downVotes;
		});
	};
	
	// 置顶
	$scope.stick = function(id){
		NewsService.stick({id:id, action:'stick', oper: '1'},{}, function(){
			$scope.getNewsPageList();
		});
	};
	
	// 取消置顶
	$scope.cancelStick = function(id){
		NewsService.stick({id:id, action:'stick', oper: '0'},{}, function(){
			$scope.getNewsPageList();
		});
	}
};


function EditController($scope, $routeParams, $location, NewsService, $rootScope, CommentsService) {
    
	$scope.showReply = false;
	$scope.reindex = 0;
	NewsService.voter({id: $routeParams.id, action: 'pageViews'},{},function(result){
	});
	
	$scope.newsEntry = NewsService.get({id: $routeParams.id});
	$scope.comments = CommentsService.query({topicId:$routeParams.id});
	
	$scope.save = function() {
		var postData = {topicId: $routeParams.id, userId: $rootScope.user.id, comment: $scope.content};
		CommentsService.save(postData, function() {
			$scope.content ='';
			$scope.comments = CommentsService.query({topicId:$routeParams.id});
		});
	};
	
	$scope.updateUpVotes = function(id, index){
		var getData = {id: id, action:'up'};
		CommentsService.voter(getData,{},function(data){
			$scope.upVotes = $scope.comments[index].upVotes + 1;
			$scope.comments[index].upVotes = $scope.upVotes;
		});
	};
	
	$scope.ctrlShow = function(){
		if ($scope.showReply){
			$scope.showReply = false;
		}
		else{
			$scope.showReply = true;
		}
	};
	
	$scope.showReply = function(id){
		$scope.reply = CommentsService.get({id: id});
		$scope.replies = CommentsService.query({replyId:id});
	};
	
	// 将元素放在外面需要使用将其弄成对应索引放在外面~~~
	$scope.setIndex = function (id){
		$scope.reindex = id;
	}
	
    //	最好将自定义命令都放在ng-repeat的外面，使用索引来查找对应的元素
	$scope.replyComment = function(){
		$scope.cm = $scope.comments[$scope.reindex];
		var postData = {replyId: $scope.cm.id, userId: $rootScope.user.id, comment: $scope.replyContent};
		CommentsService.save(postData, function() {
			$scope.replyNum = $scope.comments[$scope.reindex].replyNum + 1;
			$scope.comments[$scope.reindex].replyNum = $scope.replyNum;
			$scope.replyContent ="";
		});
	};
};


function CreateController($scope, $rootScope, $location, NewsService) {
	
	$scope.newsEntry = new NewsService();
	
	$scope.newsEntry.userId = $rootScope.user.id;
	$scope.save = function() {
		$scope.newsEntry.$save(function() {
			$location.path('/');
		});
	};
};


function LoginController($scope, $rootScope, $location, $cookieStore, UserService) {
	
	$scope.rememberMe = false;
	
	//一般用httpCode来判断异步返回结果成功或者失败，如httpCode 200-300 为成功， 400-500为失败
	$scope.login = function() {
		UserService.authenticate($.param({
			username : $scope.username,
			password : $scope.password
		}), function(authenticationResult) {
			if (authenticationResult.code == 200) {
				var authToken = authenticationResult.data.token;
				$rootScope.authToken = authToken;
				if ($scope.rememberMe) {
					$cookieStore.put('authToken', authToken);
				}
				UserService.getUser(function(user) {
					$rootScope.user = user;
					$location.path("/");
				}, function(){
					$rootScope.error = 'get info failed';
				});
			} else {
				$rootScope.error = authenticationResult.message
						+ ' failed with status ' + authenticationResult.code
			}
		});
	};
};

function PasswordController($scope, $rootScope, $location, $cookieStore,
		UserService) {

	$scope.user = new UserService();
	$scope.user.username = $rootScope.user.name;
	$scope.reset = function() {
		$scope.user.$password(function(result) {
			if (result.code == 200) {
				delete $rootScope.user;
				delete $rootScope.authToken;
				$cookieStore.remove('authToken');

				$location.path("/login");
			} else {
				$rootScope.error = result.message + ' failed with status '
				+ result.code
			}
		});
	};

};

function UserController($scope, UserService, $location, $log, $rootScope) {

	$scope.fName = '';
	$scope.lName = '';
	$scope.passw1 = '';
	$scope.passw2 = '';
	var name_search = null;
	var ip_search = null;
	$scope.edit = true;
	$scope.error = false;
	$scope.incomplete = false;
	$scope.order_search = 'name_ty';
	
   //	异步回调
	UserService.roles(function(roles){
		$scope.roles = roles.data;
	});
	
	$scope.getUserList = function (){
		
		name_search = $scope.name_search;
		ip_search = $scope.ip_search;
		var postData = {
				pageIndex : $scope.paginationConf.currentPage,
				pageSize : $scope.paginationConf.itemsPerPage,
				name_search : name_search,
				ip_search : ip_search
			}

			UserService.pager(postData, function(users) {
				$scope.paginationConf.totalItems = users.data.totalPage;
				$scope.users = users.data.content;
			});
	}
	
	// 拉黑或者拉白
	$scope.blacklist = function(id) {
		UserService.role({
			id : id,
			blackOrWhite : true
		},{}, function(data) {
            // 一般以前的用户列表当前页，页面值一般都保存在cookie中，angular的插件可以设想也是保存在cookie中
			$scope.getUserList();
		})
	}
    
	//如果认为是post， put操作，第二个参数是application/json（postData）如果
	//不带json body，则consumes参数不允许配置（put）操作
	$scope.whitelist = function(id) {
		UserService.role({
			id : id,
			blackOrWhite : false
		},{}, function(data) {
			$scope.getUserList();
		})
	}
    //配置分页基本参数
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 5
    };
    
    $scope.editUser = function(id) {
		if (id == 'new') {
			$scope.edit = true;
			$scope.incomplete = true;
			$scope.username = '';
		} else {
			$scope.edit = false;
			$scope.username = $scope.users[id - 1].name;
			$scope.index = $scope.users[id - 1].id;
		}
	};
	
	
	// 管理员添加用户
	$scope.addUser = function() {
		var postData = {
			username : $scope.username,
			password : $scope.passw1,
			confirmpassword : $scope.passw2
		};
		UserService.register(postData, function(result) {
			if (result.code == 200){
                // 显示在浏览器上的日志
				$log.log('i just tell you that you have already registered a user');
				// 控制台日志都在浏览器上
				console.info('yuguolaoxian');
				$scope.getUserList();
				$scope.username = '';
				$scope.passw1 = '';
				$scope.passw2 = '';
			}
		});
	};
	
    //	管理员修改用户信息
	$scope.modifyUserInfo = function(id) {
		
		if ($scope.role === undefined){
			$scope.roleid = '';
		} else {
			$scope.roleid = $scope.role.id;
		}
		
		var postData = {
			username : $scope.username,
			password : $scope.passw1,
			confirmpassword : $scope.passw2,
			role : $scope.roleid
		};
		
		UserService.password({
			id : id
		}, postData, function(result) {
			if (result.code == 200) {
				$scope.getUserList();
				$scope.username = '';
				$scope.passw1 = '';
				$scope.passw2 = '';
				
			} else {
				$rootScope.error = result.message + ' failed with status '
						+ result.code
			}
		})
	};
	
    /***************************************************************
    当页码和页面记录数发生变化时监控后台查询
    如果把currentPage和itemsPerPage分开监控的话则会触发两次后台事件。
    ***************************************************************/
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.getUserList);
    
    // 一个检测器
	$scope.$watch('passw1', function() {
		$scope.test();
	});
	$scope.$watch('passw2', function() {
		$scope.test();
	});
	$scope.$watch('username', function() {
		$scope.test();
	});

	$scope.test = function() {
		if ($scope.passw1 !== $scope.passw2) {
			$scope.error = true;
		} else {
			$scope.error = false;
		}
		$scope.incomplete = false;
		if ($scope.edit
				&& (!$scope.username.length 
						|| !$scope.passw1.length || !$scope.passw2.length)) {
			$scope.incomplete = true;
		}
	};
};

function RegisterController($scope, $location, UserService) {
    
	$scope.user = new UserService();

	$scope.register = function() {
		$scope.user.$register(function(result) {
//			var json = angular.toJson(result);
//			alert(json.code);
			$location.path('/login');
		});
	};
};

var services = angular.module('exampleApp.services', ['ngResource']);

services.factory('UserService', function($resource) {
	
    //	user可理解为大模块， :action可理解为小模块,尽量要名词定义
	return $resource('rest/user/:action/:id', {},
			{
				authenticate: {
					method: 'POST',
					params: {'action' : 'authenticate'},
					headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
				},
				
				register: {
					method: 'POST',
					params: {'action' : 'register'},
					headers : {'Content-Type': 'application/json'}
				},
				
				password: {
					method: 'PUT',
					params: {'action' : 'password'},
					headers : {'Content-Type': 'application/json'}
				},
				
				list: {
					method: 'GET',
					params: {'action' : 'list'},
				},
				
				pager: {
					method: 'GET',
					params: {'action' : 'pager'},
				},
				
				roles: {
					method: 'GET',
					params: {'action' : 'roles'},
				},
				
				role: {
					method: 'PUT',
					params: {'action' : 'role'},
				},
				
				getUser: {
					method: 'GET',
					headers : {'Accept-Type': 'application/json'}
				},
			}
		);
});

services.factory('NewsService', function($resource) {
	
    //@的作用是使用资源实例调用时，@id对应的是实例的属性id
	// 在参数一般名称匹配对应是路径参数，不匹配的参数url参数
	return $resource('rest/news/:id/:action', {id: '@id'},
 			{
		pager : {
			method : 'GET',
			params : {
				'action' : 'pager'
			},
		},
		voter : {
			method : 'PUT',
		},
		
		stick : {
			method : 'PUT',
		},
	});
});

services.factory('CommentsService', function($resource) {
	
    //@的作用是使用资源实例调用时，@id对应的是实例的属性id
	// 在参数一般名称匹配对应是路径参数，不匹配的参数url参数
	return $resource('rest/comments/:id/:action', {id: '@id'},
 			{
		voter : {
			method : 'PUT',
		},
	});
});