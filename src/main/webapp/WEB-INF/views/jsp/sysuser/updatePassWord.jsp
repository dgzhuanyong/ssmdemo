<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="url" value="${ctx }/sys_user/updatePassWord"></c:set>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="${ctx}/static/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx}/static/css/admin-reset.css" media="all">
</head>
<body class="contPadding">                   
	<form class="layui-form" id="form" action="${url }" commandName="obj" lay-filter="formFilter">
	  <div class="layui-form-item">
	    <label class="layui-form-label"><span style="color:red;">*</span>旧密码</label>
	    <div class="layui-input-block">
	      <input type="password" name="oldpass" lay-verify="oldpass" autocomplete="off" class="layui-input" placeholder="请输入当前密码">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label"><span style="color:red;">*</span>新密码</label>
	    <div class="layui-input-block">
	      <input type="password" name="newpass" lay-verify="newpass" autocomplete="off" class="layui-input" placeholder="请输入新密码">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label"><span style="color:red;">*</span>重复密码</label>
	    <div class="layui-input-block">
	      <input type="password" name="repeatpass" lay-verify="repeatpass" autocomplete="off" class="layui-input" placeholder="请再次输入新的密码">
	    </div>
	  </div>
	  <button class="layui-btn" lay-submit lay-filter="submmitFilter" style="display: none;" id="sunmitbtn"></button>
	</form>
	     
	<!-- JS -->
	<script src="${ctx }/static/lib/jquery-1.11.3.min.js"></script>
	<script src="${ctx}/static/layui/layui.js" charset="utf-8"></script>
	<script src="${ctx }/static/lib/jquery-form.js"></script>
	<script>
	var parentIndex;
	layui.use(['form'], function(){
		  var form = layui.form
		  ,layer = layui.layer
		  //表单验证
		  form.verify({
			  oldpass: function(value, item){ //value：表单的值、item：表单的DOM对象
				value = $.trim(value);
			  	if(value.length == 0){
			  		return '密码不能为空';
			  	}
			  	if(value.length > 20){
			  		return '密码长度不能大于20个字'
			  	}
			    if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
			      return '密码不能有特殊字符';
			    }
			  },
			  newpass: function(value, item){ //value：表单的值、item：表单的DOM对象
				value = $.trim(value);
				if(value.length == 0){
			  		return '密码不能为空';
			  	}
			  	if(value.length > 20){
			  		return '密码长度不能大于20个字'
			  	}
			    if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
			      return '密码不能有特殊字符';
			    }
			  },
			  repeatpass: function(value, item){ //value：表单的值、item：表单的DOM对象
				value = $.trim(value);
				if(value.length == 0){
			  		return '密码不能为空';
			  	}
			  	if(value.length > 20){
			  		return '密码长度不能大于20个字'
			  	}
			    if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
			      return '密码不能有特殊字符';
			    }
			  }
		  });
		  //监听提交
		  form.on('submit(submmitFilter)', function(data){
			  $("#form").ajaxSubmit({
		    		url: "${url}",
		    		type: "post",
		    		success: function(result) {
		    			var code = result.code;
		    			var type = result.type;
		    			if(code == 'parameter'){
		    				if(type == 'password'){
		    					layer.msg('参数不正确', {
		    		    			  icon: 5
		    		    			  ,time: 1500
		    		    		});
		    				}
		    			}
		    			if(code == 'incorrect'){
		    				if(type == 'password'){
		    					layer.msg('旧密码不正确', {
		    		    			  icon: 5
		    		    			  ,time: 1500
		    		    		});
		    				}
		    			}
		    			if(code == 'atypism'){
		    				if(type == 'password'){
		    					layer.msg('两次输入的密码不一致', {
		    		    			  icon: 5
		    		    			  ,time: 1500
		    		    		});
		    				}
		    			}
		    			if(code == 'agreement'){
		    				if(type == 'password'){
		    					layer.msg('新密码与旧密码不能相同', {
		    		    			  icon: 5
		    		    			  ,time: 1500
		    		    		});
		    				}
		    			}
		    			if(code == 'success'){
		    				if(type == 'password'){
		    					layer.msg('修改成功', {
		    		    			  icon: 6
		    		    			  ,time: 1500
		    		    		});
		    				}
		    				setTimeout(function(){
		    					window.location.href='${ctx}/logout'
		    				}, 1500);
		    			}
		    		},
		    		error: function() {
		    			layer.msg('提交失败', {
			    			  icon: 5
			    			  ,time: 1500
			    		});
		    		}
		    	});
			    return false;
		  });
		  
	});
	//模拟按钮提交 
	function submitForm(index){
		parentIndex = index;
		$("#sunmitbtn").click();
	}
	</script>

</body>
</html>