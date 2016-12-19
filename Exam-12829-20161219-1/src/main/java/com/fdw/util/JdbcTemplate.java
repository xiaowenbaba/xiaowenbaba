package com.fdw.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;


/**
 * 此类提供一些通用的CRUD方法
 * 
 * @author Administrator
 * 
 */
public class JdbcTemplate {

	/**
	 * 
	 * @param sql
	 *            传入可执行的SQL语句
	 * @return 返回-1表示执行SQL出现异常 >=0表示成功执行SQL
	 */
	public static int insertOrUpdateOrDelete(String sql) {
		Statement stmt = null;
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();// 此对象可以发送sql到数据库
			// String sql = "update emp set sal = sal+5";
			int res = stmt.executeUpdate(sql);
			// System.out.println("此次操作影响的行数为："+res);
			return res;
		} catch (SQLException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			JdbcUtil.closeConnection(conn, null, stmt);
		}
		return -1;
	}
	// insert into emp values(?,?,?,?,?,?...);
	// update emp set sal = sal+?

	// ThreadLocal
	public static int insertOrUpdateOrDelete2(String sql, Object[] values) throws SQLException {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement(sql);// 此对象可以发送sql到数据库
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					stmt.setObject(i + 1, values[i]);
				}
			}
			int res = stmt.executeUpdate();
			// System.out.println("此次操作影响的行数为："+res);
			return res;

	
		} finally {
			JdbcUtil.closeConnection(conn, null, stmt);
		}
		
	}
	//select * from emp;
	//select * from dept
	//泛型方法
	public static<T> List<T> queryData(String sql, Object[] values,Class<T> cls) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<T> data = new ArrayList<T>();
		try {
			connection = JdbcUtil.getConnection();
			pstmt = connection.prepareStatement(sql);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					pstmt.setObject(i + 1, values[i]);
				}
			}
			rs = pstmt.executeQuery();
			// 元数据
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			//数据库中的每一条数据应该放到对应的Vo类中
			
			while (rs.next()) {
				HashMap<String, Object> rowData = new HashMap<String, Object>();//一个Map存一行数据
				
				//以列为为key,数据为value构造一个HashMap
				//empno 7788
				//enname SCOTT;
				for(int i=0;i<columnCount;i++){
					//取得列名
					String columnName =  metaData.getColumnName(i+1);
					//取得列的数据类型，再调用rs.getXXX方法将数据转换对应的java类型
					String columnType = metaData.getColumnTypeName(i+1);
					
				//System.out.println("列名："+columnName + "数据类型:"+columnType +" "+metaData.getScale(i+1));
					//根据列名取列值
					Object val = null;
					//根据数据库表的数据类型，转成成对应的java数据类型
					//目的是减少反射的数据类型判断
					if (columnType.equals("NUMBER") && metaData.getScale(i+1)>0){//有小数位
						val = rs.getDouble(columnName);
					}else if (columnType.startsWith("VARCHAR")){
						val = rs.getString(columnName);
					}else if (columnType.equals("NUMBER")){//没有小数位的
						val = rs.getInt(columnName);
					}else if (columnType.equals("DATE")){
						val = rs.getDate(columnName);
					}else if (columnType.equals("SHORT")){
						val=rs.getObject(columnName);
					}else{
						val=rs.getObject(columnName);
					
					}
					
					rowData.put(columnName.toLowerCase(), val);
					///rs.getObject(columnIndex)
					
				}
				
				//System.out.println("----------------------------------");
				//System.out.println(rowData);
				T t =   MapValueToVo.setMapValueToVo(rowData, cls);//使用反射将数据封装到Vo中
				data.add(t);//将vo存到集合中
				
			}

		} finally {
			JdbcUtil.closeConnection(connection, rs, pstmt);
		}

		return data;
	}
	
	
	
	public static List<Object[]> queryData(String sql, Object[] values) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Object[]> data = new ArrayList<Object[]>();
		try {
			connection = JdbcUtil.getConnection();
			pstmt = connection.prepareStatement(sql);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					pstmt.setObject(i + 1, values[i]);
				}
			}
			rs = pstmt.executeQuery();
			// 元数据
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (rs.next()) {

				// 根据columnCount创建相应长度的Object[]数组
				Object[] rowData = new Object[columnCount];

				// rs.getObject(1);
				for (int i = 1; i <= columnCount; i++) {
					rowData[i - 1] = rs.getObject(i);// 把取出来的数据放到Object[]数组中

				}

				// 将数据加到集合中
				data.add(rowData);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.closeConnection(connection, rs, pstmt);
		}

		return data;

	}
	
	public static <T> PageUtil<T> queryDataByPage(String sql, Object[] values,
			int currentPage, int pageSize,Class<T> cls) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			// 1根据传入SQL求出SQL查询的总记录数
			String countSql = "select count(*) from (" + sql + ")";

			pstmt = conn.prepareStatement(countSql);

			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					pstmt.setObject(i + 1, values[i]);
				}
			}
			rs = pstmt.executeQuery();
			int totalRecord = 0; // 总记录数
			while (rs.next()) {
				totalRecord = rs.getInt(1);// 取出第一列的值
			}

			// 根据pageSize（5）和currentPage（2）计算出分页的初始索引和结束索引
			int rn1 = (currentPage - 1) * pageSize;
			int rn2 = currentPage * pageSize;

			// 2做分页查询
			String queryDataSql = " select * from ("
					+ "	select tmp.*,rownum rn from (" + sql
					+ ") tmp) where rn>" + rn1 + " and rn<=" + rn2;
			// sql语句中可能有?号点位符，需要为其传递参数，分页查询中还有两个参数需要传递

			List<T> listData = queryData(queryDataSql, values,cls);

			// 取分页查询结果
			// 计算总页数:
			int pageCount = totalRecord / pageSize;
			if (totalRecord % pageSize != 0) {
				pageCount = totalRecord / pageSize + 1;
			}

			PageUtil<T> pageUtil = new PageUtil<T>(currentPage, pageSize,
					totalRecord, pageCount, listData);

			return pageUtil;
		} finally {
			JdbcUtil.closeConnection(conn, rs, pstmt);
		}

		
	}
	

	public static PageUtil queryDataByPage(String sql, Object[] values,
			int currentPage, int pageSize) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			// 1根据传入SQL求出SQL查询的总记录数
			String countSql = "select count(*) from (" + sql + ")";

			pstmt = conn.prepareStatement(countSql);

			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					pstmt.setObject(i + 1, values[i]);
				}
			}
			rs = pstmt.executeQuery();
			int totalRecord = 0; // 总记录数
			while (rs.next()) {
				totalRecord = rs.getInt(1);// 取出第一列的值
			}

			// 根据pageSize（5）和currentPage（2）计算出分页的初始索引和结束索引
			int rn1 = (currentPage - 1) * pageSize;
			int rn2 = currentPage * pageSize;

			// 2做分页查询
			String queryDataSql = " select * from ("
					+ "	select tmp.*,rownum rn from (" + sql
					+ ") tmp) where rn>" + rn1 + " and rn<=" + rn2;
			// sql语句中可能有?号点位符，需要为其传递参数，分页查询中还有两个参数需要传递

			List<Object[]> listData = queryData(queryDataSql, values);

			// 取分页查询结果
			// 计算总页数:
			int pageCount = totalRecord / pageSize;
			if (totalRecord % pageSize != 0) {
				pageCount = totalRecord / pageSize + 1;
			}

			PageUtil pageUtil = new PageUtil(currentPage, pageSize,
					totalRecord, pageCount, listData);

			return pageUtil;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.closeConnection(conn, rs, pstmt);
		}

		return null;

	}

}
