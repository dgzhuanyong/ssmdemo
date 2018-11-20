<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:choose>
	<c:when test="${obj == null}">
		<c:set var="url" value="${ctx }/sys_user/insert"></c:set>
		<c:set var="msg" value="添加成功"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="url" value="${ctx }/sys_user/update"></c:set>
		<c:set var="msg" value="修改成功"></c:set>
	</c:otherwise>
</c:choose>
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
	    <label class="layui-form-label"><span style="color:red;">*</span>登录名</label>
	    <div class="layui-input-block">
	      <input type="text" name="loginName" lay-verify="loginName" autocomplete="off" placeholder="请输入登录名" class="layui-input" value="${obj.loginName }">
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">密码</label>
	    <div class="layui-input-block">
	      <input type="text"  value="初始密码为：123456" readonly="readonly"  autocomplete="off" class="layui-input">
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label"><span style="color:red;">*</span>姓名</label>
	    <div class="layui-input-block">
	      <input type="text" name="name" lay-verify="name" autocomplete="off" placeholder="请输入姓名" class="layui-input" value="${obj.name }">
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">状态</label>
	    <div class="layui-input-block">
	      <input type="checkbox" <c:if test="${null == obj || !obj.state }">checked="checked"</c:if>  lay-skin="switch" lay-text="启用|停用" lay-filter="stateFilter">
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label"><span style="color:red;">*</span>角色</label>
	    <div class="layui-input-block">
	      <select name="role" lay-verify="role">
	      	<option value="">请选择角色</option>
	      	<c:forEach items="${list }" var="obj">
	      		<option value="${obj.id}" <c:if test="${obj.id == sys_userRole.roleId }">selected="selected"</c:if>>${obj.name }</option>
	      	</c:forEach>
	      </select>
	    </div>
	  </div>
	  
	  <input type="hidden" name="state" id="state" <c:if test="${null == obj || obj.state }">value="0"</c:if>>
	  <input type="hidden" name="id" value="${obj.id }">
	  <button class="layui-btn" lay-submit lay-filter="submmitFilter" style="display: none;" id="sunmitbtn"></button>
	</form>
	     
	<!-- JS -->
	<script src="${ctx }/static/lib/jquery-1.11.3.min.js"></script>
	<script src="${ctx}/static/layui/layui.js" charset="utf-8"></script>
	<script src="${ctx }/static/lib/jquery-form.js"></script>
	<script>
	var msg = '${msg}'
	var oldLoginName = '${obj.loginName }';
	var parentIndex;
	layui.use(['form','layer'], function(){
		  var form = layui.form
		  ,layer = layui.layer
		  //监听开关
		  form.on('switch(stateFilter)',function(data){
			  	if(data.elem.checked){
			  		$("#state").val(0);
			  	}else{
			  		$("#state").val(1);
			  	}
		  })
		  //表单验证
		  form.verify({
			  loginName: function(value, item){ //value：表单的值、item：表单的DOM对象
				value = $.trim(value);
			  	if(value.length == 0){
			  		return '登录名不能为空';
			  	}
			  	if(value.length > 20){
			  		return '登录名长度不能大于20个字';
			  	}
			    if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
			      return '登录名不能有特殊字符';
			    }
			    var text = '';
                $.ajax({
                    url: '${ctx}/sys_user/checkRepeat',
                    type: "post",
                    async: false,
                    dataType: "json",
                    data: {oldLoginName:oldLoginName,newLoginName:value},
                    success: function (result) {
                        if (!result) {
                            text = '登录名已存在';
                        }
                    }, error: function () {
                        text = '登录名已存在';
                    }
                });
                return text;
			  },
			  name: function(value, item){ //value：表单的值、item：表单的DOM对象
				value = $.trim(value);
			  	if(value.length == 0){
			  		return '姓名不能为空';
			  	}
			  	if(value.length > 20){
			  		return '姓名长度不能大于20个字';
			  	}
			    if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
			      return '姓名不能有特殊字符';
			    }
			  },
			  role: function(value, item){ //value：表单的值、item：表单的DOM对象
				value = $.trim(value);
			  	if(value.length == 0){
			  		return '请选择角色';
			  	}
			  }
		  });
		  //监听提交
		  form.on('submit(submmitFilter)', function(data){
			  $("#form").ajaxSubmit({
		    		url: "${url}",
		    		type: "post",
		    		dataType:"json",
		    		success: function(result) {
		    			layer.msg(msg, {
	  		    			  icon: 6
	  		    			  ,time: 1500
	  		    		});
	    				setTimeout(function(){
	    					parent.conditionReset();
	    					parent.layer.close(parentIndex);
	    				}, 1500);
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