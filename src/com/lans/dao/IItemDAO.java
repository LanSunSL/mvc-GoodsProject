package com.lans.dao;

import java.sql.SQLException;
import java.util.Map;

import com.lans.util.dao.IBaseDAO;
import com.lans.vo.Item;

public interface IItemDAO extends IBaseDAO<Integer, Item> {
	public Map<Integer, String> findAllForMap() throws SQLException;
}
