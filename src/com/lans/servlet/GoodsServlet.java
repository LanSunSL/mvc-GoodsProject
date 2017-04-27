package com.lans.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.lans.service.IGoodsService;
import com.lans.util.factory.Factory;
import com.lans.vo.Goods;
import com.lans.vo.Item;
import com.lans.vo.Tag;

@SuppressWarnings("serial")
@WebServlet(urlPatterns="/pages/back/admin/goods/GoodServlet/*")
public class GoodsServlet extends HttpServlet {
	private static final String FORWARD = "/pages/plugins/forward.jsp";
	private static final String EDIT = "/pages/back/admin/goods/goods_edit.jsp";
	private static final String LIST = "/pages/back/admin/goods/goods_list.jsp";
	private SmartUpload smart ;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html");
		String uri = request.getRequestURI();
		String status = uri.substring(uri.lastIndexOf("/") + 1);
//		String status = request.getParameter("status");
//		if (status == null ) {
//			smart = new SmartUpload() ;
//			smart.initialize(this.getServletConfig(), request, response);
//			try {
//				smart.upload();
//			} catch (SmartUploadException e) {
//				e.printStackTrace();
//			}
//			status = smart.getRequest().getParameter("status");
//		}
		String forwardPath = "/pages/errors.jsp" ;
		if (!(status == null || "".equals(status))) {
			if (status.equalsIgnoreCase("add")) {
				forwardPath = this.add(request, response);
			} else if (status.equalsIgnoreCase("editPre")) {
				forwardPath = this.editPre(request, response);
			} else if (status.equalsIgnoreCase("edit")) {
				forwardPath = this.edit(request, response);
			} else if (status.equalsIgnoreCase("delete")) {
				forwardPath = this.delete(request, response);
			}	else if (status.equalsIgnoreCase("list")) {
				forwardPath = this.list(request, response);
			}
		}
		request.getRequestDispatcher(forwardPath).forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Goods vo = new Goods();
		vo.setName(smart.getRequest().getParameter("name")) ;
		vo.setPrice(Double.parseDouble(smart.getRequest().getParameter("price")));
		vo.setIid(Integer.parseInt(smart.getRequest().getParameter("item")));
		String [] tags = smart.getRequest().getParameterValues("tag");
		Set<Integer> tagSet = new HashSet<Integer> ();
		for (int i = 0 ; i < tags.length; i ++) {
			tagSet.add(Integer.parseInt(tags[i]));
		}
		if (smart.getFiles().getSize() > 0) {
			vo.setPhoto(UUID.randomUUID() + "." + smart.getFiles().getFile(0).getFileExt());
		}
		String path = "pages/back/admin/goods/goods_add.jsp" ;
		String msg = "商品增加失败";
		IGoodsService goodsService = Factory.getServiceInstance("goods.service");
		try {
			if (goodsService.add(vo, tagSet)) {
				msg = "商品增加成功!";
				String filePath = getServletContext().getRealPath("/upload/goods/" + vo.getPhoto() );
				smart.getFiles().getFile(0).saveAs(filePath);
			}
		} catch (SmartUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", path);
		return FORWARD ;
	}

	public String editPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int gid = Integer.parseInt(request.getParameter("gid"));
		IGoodsService goodsService = Factory.getServiceInstance("goods.service");
		try {
			Map<String, Object> map = goodsService.getEditPre(gid);
			Goods goods = (Goods) map.get("goods");
			List<Item> allItems = (List<Item>) map.get("allItems");
			List<Tag> allTags = (List<Tag>) map.get("allTags");
			Set<Integer> goodsTag = (Set<Integer>) map.get("goodsTag");
			request.setAttribute("goods", goods);
			request.setAttribute("allItems", allItems);
			request.setAttribute("allTags", allTags);
			request.setAttribute("goodsTag", goodsTag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EDIT ;
	}
	
	public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Goods vo = new Goods();
		vo.setGid(Integer.parseInt(smart.getRequest().getParameter("gid")));
		vo.setName(smart.getRequest().getParameter("name")) ;
		vo.setPrice(Double.parseDouble(smart.getRequest().getParameter("price")));
		vo.setIid(Integer.parseInt(smart.getRequest().getParameter("item")));
		String [] tags = smart.getRequest().getParameterValues("tag");
		Set<Integer> tagSet = new HashSet<Integer> ();
		for (int i = 0 ; i < tags.length; i ++) {
			tagSet.add(Integer.parseInt(tags[i]));
		}
		if (smart.getFiles().getSize() > 0) {
			vo.setPhoto(UUID.randomUUID() + "." + smart.getFiles().getFile(0).getFileExt());
		}
		String msg = "商品修改失败";
		IGoodsService goodsService = Factory.getServiceInstance("goods.service");
		try {
			if (goodsService.edit(vo, tagSet)) {
				msg = "商品修改成功!";
				if (smart.getFiles().getSize() > 0) {
					String filePath = getServletContext().getRealPath(smart.getRequest().getParameter("oldphoto") );
					smart.getFiles().getFile(0).saveAs(filePath);
				}
			}
		} catch (SmartUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", "/pages/back/admin/goods/GoodsSevlet/list");
		return FORWARD ;
	}
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ids = request.getParameter("ids");
		Set<Integer> idSet = new HashSet<Integer>();
		Set<String> photoSet = new HashSet<String>();
		String[] result = ids.split(",");
		for (int i = 0 ; i < result.length ; i ++) {
			String[] temp = result[i].split(":");
			idSet.add(Integer.parseInt(temp[0]));
			photoSet.add(temp[1]);
		}
		String msg = "商品删除失败";
		IGoodsService goodsService = Factory.getServiceInstance("goods.service");
		try {
			if (goodsService.delete(idSet)) {
				msg = "商品删除成功!";
				Iterator<String> iter = photoSet.iterator();
			 	while (iter.hasNext()) {
			 		String filePath = getServletContext().getRealPath("/upload/goods/" + iter.next() );
			 		new File(filePath).delete();
			 	}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", "/pages/back/admin/goods/GoodsSevlet/list");
		return FORWARD ;
	}
	public String list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String column = null ;
		String keyWord = null ;
		String columnData = "商品名称:name";
		int currentPage = 1; 
		int lineSize = 10 ;
		column = request.getParameter("col");
		keyWord = request.getParameter("kw");
		try {
			currentPage = Integer.parseInt(request.getParameter("cp"));
		}catch(Exception e){}
		try {
			lineSize = Integer.parseInt(request.getParameter("ps"));
		}catch(Exception e){}
		request.setAttribute("column", column);
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("columnData", columnData);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("lineSize", lineSize);
		
		IGoodsService goodsService = Factory.getServiceInstance("goods.service");
		try {
			Map<String, Object> map = goodsService.list(column, keyWord, currentPage, lineSize);
			List<Goods> allGoodses = (List<Goods>) map.get("allGoodses");
			Map<Integer, String> allItems = (Map<Integer, String>) map.get("allItems");
			int allRecorders = (Integer) map.get("goodsCount");
			request.setAttribute("allGoodses", allGoodses);
			request.setAttribute("allItems", allItems);
			request.setAttribute("allRecorders", allRecorders);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return LIST ;
	}
}
