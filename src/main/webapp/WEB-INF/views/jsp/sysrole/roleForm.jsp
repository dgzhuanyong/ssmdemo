<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:choose>
	<c:when test="${obj == null}">
		<c:set var="url" value="${ctx }/sys_role/insert"></c:set>
		<c:set var="msg" value="添加成功"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="url" value="${ctx }/sys_role/update"></c:set>
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
  <link rel="stylesheet" href="${ctx}/static/ztree/css/metroStyle/metroStyle.css" >
</head>
<body class="contPadding">                   
	<form class="layui-form" id="form" action="${url }" commandName="obj" lay-filter="formFilter">
	
	  <div class="layui-form-item">
	    <label class="layui-form-label"><span style="color:red;">*</span>名称</label>
	    <div class="layui-input-block">
	      <input type="text" name="name" lay-verify="name" autocomplete="off" placeholder="请输入名称" class="layui-input" value="${obj.name }">
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">标识</label>
	    <div class="layui-input-block">
	      <input type="text" name="permissions" lay-verify="permissions" autocomplete="off" placeholder="请输入标识" class="layui-input" value="${obj.permissions }">
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label"><span style="color:red;">*</span>权限</label>
	    <div class="layui-input-block">
	      <ul id="orgTree" class="ztree"></ul>
	    </div>
	  </div>
	  
	  <input type="hidden" lay-verify="privilege" id="privilege" name="privilege" value="">
	  <input type="hidden" name="id" value="${obj.id }">
	  <button class="layui-btn" lay-submit lay-filter="submmitFilter" style="display: none;" id="sunmitbtn"></button>
	</form>
	     
	<!-- JS -->
	<script src="${ctx }/static/lib/jquery-1.11.3.min.js"></script>
	<script src="${ctx}/static/layui/layui.js" charset="utf-8"></script>
	<script src="${ctx }/static/lib/jquery-form.js"></script>
	<script src="${ctx}/static/ztree/js/jquery.ztree.all.min.js"></script>
	<script>
	var msg = '${msg}'
	var oldName = '${obj.name }';
	var ids = '${ids}';
	var parentIndex;
	
	//树形菜单
	var setting = {
	  check: {
	    enable: true,
	    chkboxType :{ "Y" : "ps", "N" : "ps" }
	  },
	  data: {
	    simpleData: {
	      enable: true
	    }
	  }
	};
	
	$.ajax({
		url : '${ctx}/sys_role/tree',		
		type : "post",
		//async:false,
		//contentType : "application/json", 
		dataType:"json",
		success : function(result) {
			loadTree(result);		
		},error : function(){
			layer.msg('加载权限失败', {
	  			  icon: 5
	  			  ,time: 1500
	  		});
		} 
	});
	
	//加载树
	function loadTree(zNodes){
		if(ids.length > 0){
		  var idsArray = ids.split(",");
		  for(var i in zNodes){
	        for(var j in idsArray){
	          if(zNodes[i].id == idsArray[j]){
	            zNodes[i]['checked'] = true;
	          }
	        }
	      }
		}
		orgTree = $.fn.zTree.init($("#orgTree"), setting, zNodes);
	    orgTree.expandAll(true);
	}
	
	//选择权限 
	function checkTree(){
		var array = new Array();
		var nodes = orgTree.getCheckedNodes(true);
		for (var i = 0; i < nodes.length; i++) {
			var node = nodes[i];
			array.push(node.id);
		}
		return array.join(",");
	}
	
	
	
	
	layui.use(['form','layer'], function(){
		  var form = layui.form
		  ,layer = layui.layer
		  //表单验证
		  form.verify({
			  name: function(value, item){ //value：表单的值、item：表单的DOM对象
				value = $.trim(value);
			  	if(value.length == 0){
			  		return '名称不能为空';
			  	}
			  	if(value.length > 20){
			  		return '名称长度不能大于20个字';
			  	}
			    if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
			      return '名称不能有特殊字符';
			    }
			    var text = '';
                $.ajax({
                    url: '${ctx}/sys_role/checkRepeat',
                    type: "post",
                    async: false,
                    dataType: "json",
                    data: {oldName:oldName,newName:value},
                    success: function (result) {
                        if (!result) {
                            text = '名称已存在';
                        }
                    }, error: function () {
                        text = '名称已存在';
                    }
                });
                return text;
			  },
			  permissions: function(value, item){ //value：表单的值、item：表单的DOM对象
				value = $.trim(value);
			  	if(value.length > 20){
			  		return '标识长度不能大于20个字';
			  	}
			    if(value.length > 0 && !new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
			      return '标识不能有特殊字符';
			    }
			  },
			  privilege: function(value, item){  //value：表单的值、item：表单的DOM对象
			  	//选择权限 
			  	var value = checkTree();
			  	$("#privilege").val(value);
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