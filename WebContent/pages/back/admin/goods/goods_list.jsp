<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<%
	String basePath = request.getScheme() + "://" + 
		request.getServerName() + ":" + request.getServerPort() + 
		request.getContextPath() + "/" ;
%>
<%!
	public static final String GOODS_LIST_URL = "pages/back/admin/goods/GoodsServletBack/list";
	public static final String GOODS_ADD_URL = "pages/back/admin/goods/goods_add.jsp" ;
	public static final String GOODS_DELETE_URL = "pages/back/admin/goods/GoodsServletBack/delete" ;
	public static final String GOODS_EDIT_URL = "pages/back/admin/goods/GoodsServletBack/editPre" ;
%>
<base href="<%=basePath%>"/>
<title>商品管理</title>
<meta name="viewport" content="width=device-width,initial-scale=1">
<script type="text/javascript" src="js/common/mldn_util.js"></script>
<script type="text/javascript" src="js/back/admin/goods/goods_list.js"></script>
<script type="text/javascript" src="jquery/jquery.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
	jsDeleteUrl = "<%=basePath + GOODS_DELETE_URL%>"
</script>
<link rel="stylesheet" type="text/css"
	href="bootstrap/css/bootstrap.min.css">
</head>

<body>
	<div class="container">
		<div class="row">
			<div class="h1"><strong><span class="glyphicon glyphicon-th-list"></span>&nbsp;商品信息列表</strong></div>
		</div>
		<div class="row">
		<jsp:include page="/pages/plugins/split_plugin_search_bar.jsp">
			<jsp:param name="url" value="<%=GOODS_LIST_URL%>"/>
			<jsp:param name="column" value="${column}"/>
			<jsp:param name="allRecorders" value="${allRecorders}"/>
			<jsp:param name="columnData" value="${columnData}"/>
			<jsp:param name="keyWord" value="${keyWord}"/>
		</jsp:include>
			<table class="table table-striped table-bordered table-hover">
				<tr>
					<td style="width:5%"><input type="checkbox" id="selectall"/></td>
					<td>商品名称</td>
					<td>商品单价</td>
					<td>商品分类</td>
					<td>操作</td>
				</tr>
				<c:forEach items="${allGoodses}" var="goods">
				<tr>
					<td><input type="checkbox" id="gid" value="${goods.gid}:${goods.photo}"/></td>
					<td>${goods.name}</td>
					<td>${goods.price}</td>
					<td>${allItems[goods.iid]}</td>
					<td><a href="<%=GOODS_EDIT_URL%>?gid=${goods.gid}" class="btn btn-warning btn-xs">
						<span class="glyphicon glyphicon-pencil"></span>&nbsp;编辑</a></td>
				</tr>
				</c:forEach>
			</table>
			<button id="deleteBtn" class="btn btn-danger btn-lg">
				<span class="glyphicon glyphicon-trash"></span>&nbsp;删除选中部信息
			</button>
			<jsp:include page="/pages/plugins/split_plugin_page_bar.jsp">
				<jsp:param name="url" value="<%=GOODS_LIST_URL%>"/>
				<jsp:param name="currentPage" value="${currentPage}"/>
				<jsp:param name="lineSize" value="${lineSize}"/>
				<jsp:param name="allRecorders" value="${allRecorders}"/>
				<jsp:param name="column" value="${ column}"/>
				<jsp:param name="keyWord" value="${keyWord }"/>
			</jsp:include>
		</div>
	</div>
</body>
</html>