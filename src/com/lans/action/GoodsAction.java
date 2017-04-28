package com.lans.action;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.jspsmart.upload.SmartUploadException;
import com.lans.service.IGoodsService;
import com.lans.util.action.ActionUploadUtil;
import com.lans.util.factory.Factory;
import com.lans.util.web.ModelAndView;
import com.lans.util.web.ParameterValueUtil;
import com.lans.vo.Goods;
import com.lans.vo.Item;
import com.lans.vo.Tag;

public class GoodsAction {
	
	public ModelAndView addPre() {
		ModelAndView mav = new ModelAndView("/pages/back/admin/goods/goods_add.jsp");
		IGoodsService goodsService = Factory.getServiceInstance("goods.service");
		try {
			Map<String, Object> map = goodsService.getAddPre();
			List<Item> allItems = (List<Item>) map.get("allItems");
			List<Tag> allTags = (List<Tag>) map.get("allTags");
			mav.add("allItems", allItems);
			mav.add("allTags", allTags);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return mav ;
	}
	public ModelAndView add() throws IOException {
		ModelAndView mav = new ModelAndView("/pages/plugins/forward.jsp");
		Goods vo = new Goods();
		vo.setName(ParameterValueUtil.getParameter("name")) ;
		vo.setPrice(Double.parseDouble(ParameterValueUtil.getParameter("price")));
		vo.setIid(Integer.parseInt(ParameterValueUtil.getParameter("item")));
		String [] tags = ParameterValueUtil.getParameterValues("tag");
		Set<Integer> tagSet = new HashSet<Integer> ();
		for (int i = 0 ; i < tags.length; i ++) {
			tagSet.add(Integer.parseInt(tags[i]));
		}
		ActionUploadUtil goodsUpload = new ActionUploadUtil("goods");
		if (goodsUpload.isUpload()) {
			vo.setPhoto(goodsUpload.createSingleFileName());
		}
		String path = "pages/back/admin/goods/GoodsAction!addPre.action" ;
		String msg = "商品增加失败";
		IGoodsService goodsService = Factory.getServiceInstance("goods.service");
		try {
			if (goodsService.add(vo, tagSet)) {
				msg = "商品增加成功!";
				goodsUpload.saveSingleFile();
			}
		} catch (SmartUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.add("msg", msg);
		mav.add("url", path);
		return mav ;
	}
	public ModelAndView editPre() {
		ModelAndView mav = new ModelAndView("/pages/back/admin/goods/goods_edit.jsp");
		return mav;
	}
	public ModelAndView edit() {
		ModelAndView mav = new ModelAndView("/pages/plugins/forward.jsp");
		return mav;
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
