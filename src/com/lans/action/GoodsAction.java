package com.lans.action;

import java.util.List;
import java.util.Map;

import com.lans.service.IGoodsService;
import com.lans.util.factory.Factory;
import com.lans.util.web.ModelAndView;
import com.lans.vo.Goods;

public class GoodsAction {
	public void show() {
		
	}
	public ModelAndView addPre() {
		ModelAndView mav = new ModelAndView("/pages/back/admin/goods/goods_add.jsp");
		return mav ;
	}
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView("/pages/back/admin/goods/goods_list.jsp");
		
		
		
		int currentPage = 1; 
		int lineSize = 10 ;
		int allRecorders = 0 ;
		int pages = 1 ;
		
		String columnData = "商品名称:name";
		
		String column = null ;
		String keyWord = null ;
		
//		column = ParameterValueUtil.getParameter("col");
//		keyWord = request.getParameter("kw");
//		try {
//			currentPage = Integer.parseInt(request.getParameter("cp"));
//		}catch(Exception e){}
//		try {
//			lineSize = Integer.parseInt(request.getParameter("ps"));
//		}catch(Exception e){}
//		request.setAttribute("column", column);
//		request.setAttribute("keyWord", keyWord);
//		request.setAttribute("columnData", columnData);
//		request.setAttribute("currentPage", currentPage);
//		request.setAttribute("lineSize", lineSize);
//		
//		IGoodsService goodsService = Factory.getServiceInstance("goods.service");
//		try {
//			Map<String, Object> map = goodsService.list(column, keyWord, currentPage, lineSize);
//			List<Goods> allGoodses = (List<Goods>) map.get("allGoodses");
//			Map<Integer, String> allItems = (Map<Integer, String>) map.get("allItems");
//			int allRecorders = (Integer) map.get("goodsCount");
//			request.setAttribute("allGoodses", allGoodses);
//			request.setAttribute("allItems", allItems);
//			request.setAttribute("allRecorders", allRecorders);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return LIST ;
		
		return mav ;
	}
}
