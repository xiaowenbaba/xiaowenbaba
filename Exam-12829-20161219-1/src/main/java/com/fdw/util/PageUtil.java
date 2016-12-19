package com.fdw.util;

import java.util.List;

public class PageUtil<T>{
    int currentPage ; //当前第几页       由参数currentPage给定
    int pageSize ;   //每页显示的记录数  由参数pageSize给定
    int totalRecord; //总记录数           参数sql执行查询获得的总记录数  如参数sql为 select * from emp 返回14条记录，则此值为14
    int pageCount;   //总页数           pageCount =totalRecord/pageSize   需考虑有余数的情况
    List<T> data;//分页查询结果数据存到此属性中   
    public PageUtil(){
    	
    }
    public PageUtil(int currentPage,int pageSize, int totalRecord,int pageCount,List<T> data){
    	this.currentPage = currentPage;
    	this.pageSize = pageSize;
    	this.totalRecord = totalRecord;
    	this.pageCount = pageCount;
    	this.data = data;
    }
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
}