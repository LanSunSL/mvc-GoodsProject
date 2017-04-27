package com.lans.dao;

import java.sql.SQLException;

import com.lans.util.dao.IBaseDAO;
import com.lans.vo.Goods;

public interface IGoodsDAO extends IBaseDAO<Integer, Goods> {
	public Goods findByName(String name) throws SQLException ;
	
	public Integer findGoodsId() throws SQLException ;
	
}
