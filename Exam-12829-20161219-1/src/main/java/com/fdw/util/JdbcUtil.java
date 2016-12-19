package com.fdw.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 此工具提供静态方法，1，获得数据库连接。
 * @author Administrator
 *
 */
public class JdbcUtil {
	
	
	//加载驱动 只要加载一次
	static{//静态代码块
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static final  String  USER_NAME="root";
	public static final  String  PASS_WORD="123456";
	public static final  String  URL="jdbc:mysql://localhost:3306/sakila?characterEncoding=utf8";
	
	
	public static Connection getConnection() throws SQLException{
		
		return DriverManager.getConnection(URL, USER_NAME, PASS_WORD);
		
	}
	
	
	public static void closeConnection(Connection conn,ResultSet rs ,Statement stmt){
		try {
			if (rs!=null)
				rs.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 try {
			 	if (stmt!=null)
				stmt.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				stmt = null;
			}
			 
			 
			 try {
				 if (conn!=null)
				 conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				conn = null;
			}
		
		
	}

}
