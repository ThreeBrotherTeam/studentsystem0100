<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=path%>/ui/js/jQuery/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
$(function(){
	
	//删除
	var $delProperty = $("a.deleteProperty");
	
	$(document).on("click","a.deleteProperty",function(){
		var num =$(this).attr("num");
		console.info("num:" + num);
		var $tr = $("#tr_"+num);
		var isNew = $(this).attr("isNew");
		$tr.remove();
		reSortTable();
		if(isNew){
			console.info("is new ");
			return;
		}
		
		var key = $(this).attr("key");
		$.ajax({
			url:"config/delete",
			data:{"key":key},
			type:"get",
			success:function(result){
				console.info("result:" + result);
			}
		});
	});
	
	//新增   修改
	var $updateProperty = $("a.updateProperty");
	
	$(document).on("click","a.updateProperty",function(){
		var num =$(this).attr("num");
		var isNew = $(this).attr("isNew");
		var key = $(this).attr("key");
		if(isNew){
			key = $("#property_key_" + num).val();
		}

		var value=$("#property_value_" + num).val();
		
		$.ajax({
			url:"config/update",
			data:{"key":key,"value":value},
			type:"get",
			success:function(result){
				console.info("result:" + result);
			}
		});
	});
	
	//新加属性
	var $addProperty = $("a.addProperty");
	
	$addProperty.on("click",function(){
		//1. 表格第一行空出来，做为新加入行
	$tbody = $("#tbody");	
	var tableLength = $tbody.find("tr").length + 1;
	var tr = "<tr id='tr_"+tableLength+"'>";
	var td1 = "<td align='center'>"+tableLength+"</td>";
	var td2 = "<td><input type='text' id='property_key_"+tableLength+"'></td>";	
	var td3 = "<td><input id='property_value_"+tableLength+"'/></td>";
	var td4 = "<td><a href='javascript:void();' id='property_update_"+tableLength+"' class='updateProperty' isNew='true' num='"+tableLength+"'>submit</a></td>";
	var td5 = "<td><a href='javascript:void();' id='property_del_"+tableLength+"' class='deleteProperty' isNew='true' num='"+tableLength+"'>delete</a></td>";	
	
	var $trTemp = $(tr + "</tr>");
	$trTemp.html(td1 + td2 + td3 + td4 + td5);
	$tbody.append($trTemp);
		
		
	reSortTable();
		
		
		//2. 获取新增加属性，做安全检验
		
		//3. 保存数据 
		
		//4. 重新排序
		
	});
	
	
	
});

//重新对表格进行排序
function reSortTable(){
	$tbody = $("#tbody");
	$tbody.find("tr").each(function(i,v){
		var tr = $(v);
		tr.find("td").eq(0).text(i+1);
	});
}
</script>
<title>Insert title here</title>
</head>
<body>
	<c:set var="hasLogin" value="false"/>
	<c:if test="${not empty sessionScope.userName }">
		<c:set var="hasLogin" value="true"/>
	</c:if>
	这个是配置信息页面，
	<c:if test="${hasLogin }">
		当前登录用户是<font color="red">&nbsp;${sessionScope.userName }</font>
		<c:url var="logoutUrl" value="/logout2"/>
	<form action="${logoutUrl}" method="post">
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	    <input type="submit" value="Log out" />
	</form>
	</c:if>
	<br>

	<div align="center">
		<a class="addProperty" href="javascript:void();">新增属性</a>
		<table border="1">
			<thead>
				<tr>
					<td>序号</td>
					<td>属性名</td>
					<td>属性值</td>
					<td>确认修改</td>
					<td>删除</td>
				</tr>
			</thead>
			<tbody id="tbody">
				<c:forEach items="${properties }" var="property" varStatus="status">
					<tr id="tr_${status.index+1}">
						<td align="center">${status.index+1 }</td>
						<td>${property.key }</td>
						<td><input id="property_value_${status.index+1 }" value="${property.value }"/></td>
						<td><a href="javascript:void();" id="property_update_${status.index+1 }" class="updateProperty" key="${property.key }" num="${status.index+1}">submit</a></td>
						<td><a href="javascript:void();" id="property_del_${status.index+1 }" class="deleteProperty" key="${property.key }" num="${status.index+1}">delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>