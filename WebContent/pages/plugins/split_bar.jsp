<%@ page language="java" pageEncoding="UTF-8"%>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<%-- 
<jsp:include page="/pages/plugins/split_bar.jsp">
	<jsp:param value="<%=currentPage%>" name="currentPage"/>
	<jsp:param value="<%=pageSize%>" name="pageSize"/>
	<jsp:param value="<%=column%>" name="column"/>
	<jsp:param value="<%=defaultColumn%>" name="defaultColumn"/>
	<jsp:param value="<%=keyWord%>" name="keyWord"/>
	<jsp:param value="<%=allRecorders%>" name="allRecorders"/>
	<jsp:param value="<%=EMP_SPLIT_URL%>" name="url"/>		
</jsp:include>
 --%>


<%!
	public static final String SPLIT_URL = "pages/back/admin/emp/emp_list_split.jsp";%>
<%
	request.setCharacterEncoding("UTF-8");

	int currentPage = 1;
	int pageSize = 5;
	int allRecorders = 0;
	int pages = 1;
	String column = "";
	String defaultColumn = "";
	String keyWord = "";
	String url = "";
	StringBuffer actionUrl = new StringBuffer();


	try {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	} catch (Exception e) {
	}
	try {
		pageSize = Integer.parseInt(request.getParameter("pageSize"));
	} catch (Exception e) {
	}
	try {
		allRecorders = Integer.parseInt(request.getParameter("allRecorders"));
	} catch (Exception e) {
	}
	try {
		pages = Integer.parseInt(request.getParameter("pages"));
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
		url = SPLIT_URL;
	}
	actionUrl.append(url).append("?col=").append(column).append("&kw=").append(keyWord).append("&ps=")
			.append(pageSize).append("&cp=");


	pages = (allRecorders + pageSize - 1) / pageSize;
	if (pages == 0) {
		pages = 1 ;
	}
	int pageFlag = 2;
	int startPage = currentPage - pageFlag;
	if (startPage > currentPage) {
		startPage = currentPage ;
	}
	if (startPage < 2) {
		startPage = 2;
	}
	int endPage = currentPage + pageFlag;
	if (endPage < currentPage) {
		endPage = currentPage ;
	}
	if (endPage > pages - 1) {
		endPage = pages - 1;
	}
%>


<div id="split_bar" style="text-align: right;">
	<ul class="pagination">
		<%
			if (currentPage == 1) {
		%>
				<li class="disabled"><span>&lt;&lt;</span></li>
				<li class="active"><span>1</span></li>
		<%
			} else {
		%>
				<li><a href="<%=actionUrl%><%=currentPage-1%>">&lt;&lt;</a></li>
				<li><a href="<%=actionUrl%><%=1%>"><%=1%></a></li>
		<%
			}
			if (startPage > 2) {
		%>
				<li><span>...</span></li>
		<%
			}
			for (int i = startPage; i <= endPage ; i ++) {
		%>
				<li class=<%=currentPage == i ? "active" : "" %>><a href="<%=actionUrl%><%=i%>"><%=i%></a></li>
		<%		
			}
			if (endPage < pages - 1) {
		%>
				<li><span>...</span></li>
		<%
			}
			if (currentPage == pages) {
		%>
				<li class="active"><span><%=pages%></span></li>
				<li class="disabled"><span>&gt;&gt;</span></li>
		<%
			} else {
		%>				
				<li><a href="<%=actionUrl%><%=pages%>"><%=pages%></a></li>
				<li><a href="<%=actionUrl%><%=currentPage+1%>">&gt;&gt;</a></li>
		<%
			}
		%>
	</ul>
</div>
