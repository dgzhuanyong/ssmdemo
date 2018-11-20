<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
        <div class="layui-header" >
        <!-- 头部区域 -->
        <ul class="layui-nav layui-layout-left">
          <li class="layui-nav-item layadmin-flexible" lay-unselect>
            <a href="javascript:;" layadmin-event="flexible" title="侧边伸缩">
              <i class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"></i>
            </a>
          </li>
          <!-- <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="" target="_blank" title="前台">
              <i class="layui-icon layui-icon-website"></i>
            </a>
          </li> -->
          <li class="layui-nav-item" lay-unselect>
            <a href="javascript:;" layadmin-event="refresh" title="刷新">
              <i class="layui-icon layui-icon-refresh-1"></i>
            </a>
          </li>
        </ul>
        <ul class="layui-nav layui-layout-right" lay-filter="layadmin-layout-right">
           <li class="layui-nav-item layui-hide-xs" lay-unselect>
	            <a href="javascript:;">
	     		<i class="layui-icon layui-icon-username"></i>        
	       		  <shiro:principal/>
	       		</a>
          	</li>
        	<!--修改密码 -->
           <li class="layui-nav-item layui-hide-xs" lay-unselect>
           		<!-- JS在index页面 -->
	            <a href="javascript:;" onclick="updatePassWord();">
	            <i class="layui-icon layui-icon-password"></i>   
	           	修改密码
	           	</a>
          	</li>
          	<!-- 退出 -->
          	<li class="layui-nav-item layui-hide-xs" lay-unselect>
	            <a lay-href="${ctx}/logout" >
	            <i class="layui-icon layui-icon-close-fill"></i> 
	           	退出
	           	</a>
          	</li>
          <li class="layui-nav-item layui-show-xs-inline-block layui-hide-sm" lay-unselect>
            <a href="javascript:;" layadmin-event="more"><i class="layui-icon layui-icon-more-vertical"></i></a>
          </li>
        </ul>
      </div>
</html>