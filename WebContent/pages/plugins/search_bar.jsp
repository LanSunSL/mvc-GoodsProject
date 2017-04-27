<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<%-- 
<jsp:include page="/pages/plugins/search_bar.jsp">
	<jsp:param value="<%=column%>" name="column"/>
	<jsp:param value="<%=defaultColumn%>" name="defaultColumn"/>
	<jsp:param value="<%=keyWord%>" name="keyWord"/>
	<jsp:param value="<%=columnData%>" name="columnData"/>
	<jsp:param value="<%=allRecorders%>" name="allRecorders"/>
	<jsp:param value="<%=SEARCH_URL%>" name="url"/>		
</jsp:include>
 --%>


<%!
	public static final String SEARCH_URL = "pages/back/admin/emp/emp_list_split.jsp";%>
<%
	request.setCharacterEncoding("UTF-8");

	int allRecorders = 0;
	String column = "";
	String defaultColumn = "";
	String keyWord = "";
	String columnData = "";
	String url = "";
	StringBuffer actionUrl = new StringBuffer();


	try {
		allRecorders = Integer.parseInt(request.getParameter("allRecorders"));
	} catch (Exception e) {
	}
	defaultColumn = request.getParameter("defaultColumn");
	if (defaultColumn == null) {
		defaultColumn = "ename";
	}
	column = request.getParameter("column");
	if (column == null) {
		column = defaultColumn;
	}
	keyWord = request.getParameter("keyWord");
	if (keyWord == null) {
		keyWord = "";
	}

	url = request.getParameter("url");
	if (url == null) {
		url = SEARCH_URL;
	}

%>


<div id="search_bar" style="text-align: center;">
	<form action="<%=url%>" method="post">
	<c:if test="(columnData == null || "".equals(columnData))"></c:if>
	<%
		if (!(columnData == null || "".equals(columnData))) {
			
		}
	%>
		<select id="col" name="col">
		<%
			String[] result = columnData.split("\\|");
			for (int i = 0 ; i < result.length ; i ++) {
				String[] temp = result[i].split(":") ;
			
		%>
			<option  value="<%=temp[1]%>" <%=temp[1].equals(column)?"selected":""%>><%=temp[0] %></option>
		<%
			}
		%>
		</select>
		<input type="text" id="kw" name="kw" value="<%=keyWord%>">
		<input type="submit" value="SEARCH"> 
		<p>查询一共返回<%=allRecorders%>条记录</p>
	</form>
</div>
