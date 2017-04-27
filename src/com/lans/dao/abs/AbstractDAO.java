package com.lans.dao.abs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import com.lans.util.dbc.DatabaseConnection;

public abstract class AbstractDAO {
	protected Connection conn;
	protected PreparedStatement pstmt ;
	protected ResultSet rs ;
	public AbstractDAO() {
		conn = DatabaseConnection.getConnection();
	}
	public boolean handleDeleteByInteger(String tableName, String columnName, Set<Integer> ids) throws SQLException {
		StringBuffer buf = new StringBuffer("DELETE FROM " + tableName + " WHERE " + columnName + " IN (") ;
		Iterator<Integer> iter = ids.iterator();
		while (iter.hasNext()) {
			buf.append(iter.next()).append(",");
		}
		buf.delete(buf.length() - 1, buf.length()).append(")") ;
		pstmt = conn.prepareStatement(buf.toString()) ;
		return pstmt.executeUpdate() > 0;
	}
	public boolean handleDeleteByString(String tableName, String columnName, Set<String> ids) throws SQLException {
		StringBuffer buf = new StringBuffer("DELETE FROM " + tableName + " WHERE " + columnName + " IN (") ;
		Iterator<String> iter = ids.iterator();
		while (iter.hasNext()) {
			buf.append("'").append(iter.next()).append("',");
		}
		buf.delete(buf.length() - 1, buf.length()).append(")") ;
		pstmt = conn.prepareStatement(buf.toString()) ;
		return pstmt.executeUpdate() > 0;
	}
}
