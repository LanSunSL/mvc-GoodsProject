package com.lans.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.lans.dao.IGoodsDAO;
import com.lans.dao.IItemDAO;
import com.lans.dao.ITagDAO;
import com.lans.service.IGoodsService;
import com.lans.service.abs.AbstractService;
import com.lans.util.factory.Factory;
import com.lans.vo.Goods;

public class GoodsServiceImpl extends AbstractService implements IGoodsService {

	@Override
	public Map<String, Object> getAddPre() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		IItemDAO itemDAO = Factory.getDAOInstance("item.dao") ;
		ITagDAO tagDAO = Factory.getDAOInstance("tag.dao");
		map.put("allItems", itemDAO.findAll());
		map.put("allTags", tagDAO.findAll());
		return map;
	}

	@Override
	public boolean add(Goods vo, Set<Integer> tids) throws Exception {
		if (vo.getIid() == null || vo.getIid().equals(0) || vo.getPrice() <= 0 
				|| vo.getPhoto() == null || "".equals(vo.getPhoto()))  {
			return false ;
		}
		IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		if (goodsDAO.findByName(vo.getName()) == null) { //商品名称不重复，可以进行保存
			if (goodsDAO.doCreate(vo)) { //保存商品信息
				Integer gid = goodsDAO.findGoodsId();  //取得自动增长的ID
				ITagDAO tagDAO = Factory.getDAOInstance("tag.dao");
				tagDAO.doCreateGoodsAndTag(gid, tids);
				return true ;
			}
		}
		return false;
	}

	@Override
	public Map<String, Object> getEditPre(int gid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		IItemDAO itemDAO = Factory.getDAOInstance("item.dao") ;
		map.put("allItems", itemDAO.findAll());
		ITagDAO tagDAO = Factory.getDAOInstance("tag.dao");
		map.put("allTags", tagDAO.findAll());
		IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		map.put("goods", goodsDAO.findById(gid));
		map.put("goodsTag", tagDAO.findAllByGoods(gid));
		return map;
	}

	@Override
	public boolean edit(Goods vo, Set<Integer> tids) throws Exception {
		if (vo.getIid() == null || vo.getIid().equals(0) || vo.getPrice() <= 0 )  {
			return false ;
		}
		IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		Goods goods = goodsDAO.findByName(vo.getName());
		if ( goods == null || goods.getGid() == vo.getGid()) { //商品名称不重复，或者是本商品可以进行保存
			if (goodsDAO.doUpdate(vo)) { //保存商品信息
				ITagDAO tagDAO = Factory.getDAOInstance("tag.dao");
				int gid = vo.getGid();
				tagDAO.doRemoveGoodsAndTag(gid);
				tagDAO.doCreateGoodsAndTag(gid, tids);
				return true ;
			}
		}
		return false;
	}

	@Override
	public Map<String, Object> listWithBug(String column, String keyWord, int currentPage, int pageSize)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		if (column == null || "".equals(column) || keyWord == null || "".equals(keyWord)) {
			map.put("allGoodses", goodsDAO.findAllSplit(currentPage, pageSize));
			map.put("goodsCount", goodsDAO.getAllCount());
		} else {
			map.put("allGoodses", goodsDAO.findAllSplit(column, keyWord, currentPage, pageSize));
			map.put("goodsCount", goodsDAO.getAllCount(column, keyWord));
		}
		return map;
	}

	@Override
	public Map<String, Object> list(String column, String keyWord, int currentPage, int pageSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		
		if (column == null || "".equals(column) || keyWord == null || "".equals(keyWord)) {
			map.put("allGoodses", goodsDAO.findAllSplit(currentPage, pageSize));
			map.put("goodsCount", goodsDAO.getAllCount());
		} else {
			map.put("allGoodses", goodsDAO.findAllSplit(column, keyWord, currentPage, pageSize));
			map.put("goodsCount", goodsDAO.getAllCount(column, keyWord));
		}
		IItemDAO itemDAO = Factory.getDAOInstance("item.dao");
		map.put("allItems", itemDAO.findAllForMap());
		return map;
	}

	@Override
	public boolean delete(Set<Integer> ids) throws Exception {
		IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		return goodsDAO.doRemoveBatch(ids);
	}

}
