<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	session.removeAttribute("shiroSavedRequest");
%>
<shiro:user>  
<c:redirect url="/index"></c:redirect>
</shiro:user>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>管理后台</title>
		<link rel="shortcut icon" href="${ctx}/static/img/favicon.ico" type="image/x-icon" />
		<meta name="renderer" content="webkit">
  		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  		<link rel="stylesheet" href="${ctx }/static/login/css/layui.css" media="all">
  		<link rel="stylesheet" href="${ctx }/static/login/css/admin.css" media="all">
  		<link rel="stylesheet" href="${ctx }/static/login/css/login.css" media="all">
  		<script src="${ctx}/static/lib/jquery-1.11.3.min.js" charset="utf-8"></script>
		<script type="text/javascript">
		//防止session超时登陆嵌套
		if (top != window)
			top.location.href = window.location.href;
		</script>
	</head>
	<body>
	<%
		String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String content = "";
		if (StringUtils.isNotBlank(error)) {
			if (error.contains("DisabledAccountException")) {
				content = "用户已失效，请联系管理员";
			} else {
				content = "用户名或密码错误，请重新输入";
			}
		}
	%>
		<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">
		<form id="loginForm" action="${ctx}/login" method="post">
		    <div class="layadmin-user-login-main">
		      <div class="layadmin-user-login-box layadmin-user-login-header">
		        <h2 style="color: darkturquoise">管理后台</h2>
		        <!-- <p>后台管理系统</p> -->
		      </div>
		      <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
		        <div class="layui-form-item">
		          <label class="layadmin-user-login-icon layui-icon layui-icon-username" for="LAY-user-login-username"></label>
		          <input type="text" value="${username }" name="username" id="username" autocomplete="off"  placeholder="用户名" class="layui-input">
		        </div>
		        <div class="layui-form-item">
		          <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-password"></label>
		          <input type="password" name="password" id="password" autocomplete="off"  placeholder="密码" class="layui-input">
		        </div>
		        <p id="login_cont_err_txt" style="display:none;color:red; margin-bottom:10px;"><%=content%></p>
		       		<% if(!"".equals(content)) {%>
       				<script type="text/javascript"> 
       				$("#login_cont_err_txt").show(); 
       				</script>
       				<%} %>
		        
		        <div class="layui-form-item">
		          <button class="layui-btn layui-btn-fluid sihong-login" id="btnSumit" type="button" onclick="isLogin()" lay-submit lay-filter="LAY-user-login-submit">登 录</button>
		        </div>
		      </div>
		    </div>
		</form>    
		   
		</div>

  <script src="${ctx }/static/login/js/layui.js"></script>  
  <script>
  
	function isLogin(){
		var yonghuming=$("#username").val();
		var mima=$("#password").val();
		if(yonghuming ==null || yonghuming ==""){
			$("#login_cont_err_txt").text("用户名或密码不能为空").show();
		}else if(mima ==null || mima ==""){
			$("#login_cont_err_txt").text("用户名或密码不能为空").show();
		}else{ 
			document.getElementById("loginForm").submit();
		}
}
	
	$("#loginForm").keydown(function() {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $('#btnSumit').click();
        }
    });
	
  </script>

	</body>
</html>