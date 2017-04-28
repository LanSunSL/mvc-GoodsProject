package com.lans.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.lans.dao.IGoodsDAO;
import com.lans.dao.abs.AbstractDAO;
import com.lans.vo.Goods;

public class GoodsDAOImpl extends AbstractDAO implements IGoodsDAO {

	@Override
	public boolean doCreate(Goods vo) throws SQLException {
		String sql = "INSERT INTO goods (name,price,photo,iid) VALUES(?,?,?,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, vo.getName());
		pstmt.setDouble(2, vo.getPrice());
		pstmt.setString(3, vo.getPhoto());
		pstmt.setInt(4, vo.getIid());
		return pstmt.executeUpdate() > 0;
	}

	@Override
	public boolean doUpdate(Goods vo) throws SQLException {
		String sql = "UPDATE goods SET name=?,price=?,iid=? WHERE gid=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, vo.getName());
		pstmt.setDouble(2, vo.getPrice());
		pstmt.setInt(3, vo.getIid());
		pstmt.setInt(4, vo.getGid());
		return pstmt.executeUpdate() > 0;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		return super.handleDeleteByInteger("goods", "gid", ids);
	}

	@Override
	public boolean doRemove(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Goods findById(Integer id) throws SQLException {
		Goods vo = null ;
		String sql = "SELECT gid,name,price,photo,iid FROM goods WHERE gid=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			vo = new Goods();
			vo.setGid(id);
			vo.setName(rs.getString(2));
			vo.setPrice(rs.getDouble(3));
			vo.setPhoto(rs.getString(4));
			vo.setIid(rs.getInt(5));
		}
		return vo;
	}

	@Override
	public List<Goods> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Goods> findAllSplit(Integer currentPage, Integer pageSize) throws SQLException {
		List<Goods> all = new ArrayList<Goods>();
		String sql = "SELECT * FROM ( "
				+ " SELECT gid,name,price,photo,iid,ROWNUM rn FROM goods WHERE ROWNUM<=?) temp "
				+ " WHERE temp.rn>?" ;
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, currentPage * pageSize);
		pstmt.setInt(2, (currentPage - 1) * pageSize); 
		rs = pstmt.executeQuery() ;
		while (rs.next()) {
			Goods vo = new Goods();
			vo.setGid(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setPrice(rs.getDouble(3));
			vo.setPhoto(rs.getString(4));
			vo.setIid(rs.getInt(5));
			all.add(vo);
		}
		return all;
	}

	@Override
	public List<Goods> findAllSplit(String column, String keyWord, Integer currentPage, Integer pageSize)
			throws SQLException {
		List<Goods> all = new ArrayList<Goods>();
		String sql = "SELECT * FROM ( "
				+ " SELECT gid,name,price,photo,iid,ROWNUM rn FROM goods WHERE " + column + " LIKE ? AND ROWNUM<=?) temp "
				+ " WHERE temp.rn>?" ;
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "%" + keyWord + "%");
		pstmt.setInt(2, currentPage * pageSize);
		pstmt.setInt(3, (currentPage - 1) * pageSize); 
		rs = pstmt.executeQuery() ;
		while (rs.next()) {
			Goods vo = new Goods();
			vo.setGid(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setPrice(rs.getDouble(3));
			vo.setPhoto(rs.getString(4));
			vo.setIid(rs.getInt(5));
			all.add(vo);
		}
		return all;
	}

	@Override
	public Integer getAllCount() throws SQLException {
		String sql = "SELECT COUNT(*) FROM goods" ;
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}

	@Override
	public Integer getAllCount(String column, String keyWord) throws SQLException {
		String sql = "SELECT COUNT(*) FROM goods WHERE " + column + " LIKE ?" ;
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "%" + keyWord + "%") ;
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}

	@Override
	public Goods findByName(String name) throws SQLException {
		Goods vo = null;
		String sql = "SELECT gid,price,photo,iid FROM goods WHERE name=?" ;
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, name);
		rs = pstmt.executeQuery() ;
		if (rs.next()) {
			vo = new Goods();
			vo.setGid(rs.getInt(1));
			vo.setName(name);
			vo.setPrice(rs.getDouble(2));
			vo.setPhoto(rs.getString(3));
			vo.setIid(rs.getInt(4));
		}
		return vo;
	}

	@Override
	public Integer findGoodsId() throws SQLException {
		String sql = "SELECT last_insert_id()";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}

}
