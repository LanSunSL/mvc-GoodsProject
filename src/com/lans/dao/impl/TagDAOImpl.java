package com.lans.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.lans.dao.ITagDAO;
import com.lans.dao.abs.AbstractDAO;
import com.lans.vo.Tag;

public class TagDAOImpl extends AbstractDAO implements ITagDAO {

	@Override
	public boolean doCreate(Tag vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doUpdate(Tag vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doRemove(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Tag findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tag> findAll() throws SQLException {
		List<Tag> all = new ArrayList<Tag> ();
		String sql = "SELECT tid, title FROM tag";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			Tag vo = new Tag();
			vo.setTid(rs.getInt(1));
			vo.setTitle(rs.getString(2));
			all.add(vo);
		}
		return all;
	}

	@Override
	public List<Tag> findAllSplit(Integer currentPage, Integer pageSize) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tag> findAllSplit(String column, String keyWord, Integer currentPage, Integer pageSize)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getAllCount() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getAllCount(String column, String keyWord) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean doCreateGoodsAndTag(Integer gid, Set<Integer> tids) throws SQLException {
		String sql = "INSERT INTO goods_tag (gid, tid) VALUES(?,?)" ;
		pstmt = conn.prepareStatement(sql);
		Iterator<Integer> iter = tids.iterator();
		while (iter.hasNext()) {
			pstmt.setInt(1, gid);
			pstmt.setInt(2, iter.next());
			pstmt.addBatch();
		}
		int[] result = pstmt.executeBatch();
		for (int i = 0 ; i < result.length ; i ++) {
			if (result[i] == 0) {
				return false;
			}
		}
		return true ;
	}

	@Override
	public boolean doRemoveGoodsAndTag(Integer gid) throws SQLException {
		String sql = "DELETE FROM goods_tag WHERE gid=?" ;
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, gid);
		return pstmt.executeUpdate() > 0;
	}

	@Override
	public Set<Integer> findAllByGoods(Integer gid) throws SQLException {
		Set<Integer> set = new HashSet<Integer>();
		String sql = "SELECT tid FROM goods_tag WHERE gid=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, gid);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			set.add(rs.getInt(1));
		}
		return set;
	}

}
