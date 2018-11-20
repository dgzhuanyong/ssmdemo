<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="menu" value="${sessionScope.menu}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
        <div class="layui-side layui-side-menu">
        <div class="layui-side-scroll" >
          <div class="layui-logo" style="text-align:none; padding:0 10px;" >
              	管理后台
          </div>
          <ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu" lay-filter="layadmin-system-side-menu">
            <c:forEach var="item1" items="${menu.menu1}">
	            <li data-name="" class="layui-nav-item">
	            <c:if test="${item1.url ne '#'}">
	            	<a lay-href="${ctx}${item1.url}" lay-tips="${item1.name}" lay-direction="2">
		            	<%-- <i class="layui-icon ${item1.icon}"></i> --%>
		                <cite>${item1.name}</cite>
	            	</a>
	            </c:if>
	            <c:if test="${item1.url eq '#'}">
	              <a href="javascript:;" lay-tips="${item1.name}" lay-direction="2">
	                <%-- <i class="layui-icon ${item1.icon}"></i> --%>
	                <cite>${item1.name}</cite>
	              </a>
	              <dl class="layui-nav-child">
	              	<c:forEach var="item2" items="${menu.menu2}">
	              	<c:if test="${item1.id eq item2.pId && item2.url ne '#'}">
	                <dd data-name="">
	                  <a lay-href="${ctx}${item2.url}">${item2.name}</a>
	                </dd>
	                </c:if>
	                <c:if test="${item1.id eq item2.pId && item2.url eq '#'}">
	                  <dd data-name="">
	                  	<a href="javascript:;">${item2.name}</a>
	                  	<dl class="layui-nav-child">
	                  		<c:forEach var="item3" items="${menu.menu3}">
	                  		<c:if test="${item2.id eq item3.pId && item3.url ne '#'}">
	                  			<dd data-name=""><a lay-href="${ctx}${item3.url}">${item3.name}</a></dd>
	                  		</c:if>
	                  		<c:if test="${item2.id eq item3.pId && item3.url eq '#'}">
	                  			<dd data-name="">
				                  	<a href="javascript:;">${item3.name}</a>
				                  	<dl class="layui-nav-child">
				                  		<c:forEach var="item4" items="${menu.menu4}">
				                  		<c:if test="${item3.id eq item4.pId && item4.url ne '#'}">
				                  			<dd data-name=""><a lay-href="${ctx}${item4.url}">${item4.name}</a></dd>
				                  		</c:if>
				                  		<c:if test="${item3.id eq item4.pId && item4.url eq '#'}">
				                  			<%-- <dd data-name="">
							                  	<a href="javascript:;">${item4.name}</a>
							                  	<dl class="layui-nav-child">
							                  		<c:forEach var="item5" items="${menu.menu5}">
							                  		<c:if test="${item4.id eq item5.pId && item5.url ne '#'}">
							                  			<dd data-name=""><a lay-href="${ctx}${item5.url}">${item5.name}</a></dd>
							                  		</c:if>
							                  		<c:if test="${item4.id eq item5.pId && item5.url eq '#'}">
							                  			<dd data-name="">
										                  	<a href="javascript:;">${item5.name}</a>
										                  	<dl class="layui-nav-child">
										                  		<c:forEach var="item6" items="${menu.menu6}">
										                  		<c:if test="${item5.id eq item6.pId && item6.url ne '#'}">
										                  			<dd data-name=""><a lay-href="${ctx}${item6.url}">${item6.name}</a></dd>
										                  		</c:if>
										                  		<c:if test="${item5.id eq item6.pId && item6.url eq '#'}">
										                  			
										                  		</c:if>
										                  		</c:forEach>
										                  	</dl>
										                </dd>
							                  		</c:if>
							                  		</c:forEach>
							                  	</dl>
							                </dd> --%>
				                  		</c:if>
				                  		</c:forEach>
				                  	</dl>
				                </dd>
	                  		</c:if>
	                  		</c:forEach>
	                  	</dl>
	                  </dd>
	                </c:if>
	                </c:forEach>
	              </dl>
	              </c:if>
	            </li>
            </c:forEach>
          </ul>
        </div>
      </div>
</html>