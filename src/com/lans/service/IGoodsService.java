package com.lans.service;

import java.util.Map;
import java.util.Set;

import com.lans.vo.Goods;

public interface IGoodsService {
	
	public Map<String, Object> getAddPre() throws Exception ;
	
	public boolean add(Goods vo, Set<Integer> tids) throws Exception;
	
	public Map<String, Object> getEditPre(int gid) throws Exception ;
	
	public boolean edit(Goods vo, Set<Integer> tids) throws Exception;
	
	public boolean delete(Set<Integer> ids) throws Exception ;
	
	public Map<String, Object> listWithBug(String column, String keyWord, int currentPage, int pageSize) throws Exception;
	
	public Map<String, Object> list(String column, String keyWord, int currentPage, int pageSize) throws Exception;
}
