package com.lans.dao;

import java.sql.SQLException;
import java.util.Set;

import com.lans.util.dao.IBaseDAO;
import com.lans.vo.Tag;

public interface ITagDAO extends IBaseDAO<Integer, Tag> {
	public boolean doCreateGoodsAndTag(Integer gid, Set<Integer> tids) throws SQLException;

	public boolean doRemoveGoodsAndTag(Integer gid) throws SQLException;
	
	public Set<Integer> findAllByGoods(Integer gid) throws SQLException;
}
