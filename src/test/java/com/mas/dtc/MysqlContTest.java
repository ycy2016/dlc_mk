package com.mas.dtc;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;

import com.mysql.jdbc.Connection;

public class MysqlContTest {

	@Test
	public void test() {

		System.out.println("hello world!!!");
		String url = "jdbc:mysql://localhost:3306/dtcmk";
		String username = "root";
		String password = "123123";
		Connection con;
		Statement stmt;
		ResultSet rs;
		// 定位驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("加载驱动成功!");
		} catch (ClassNotFoundException e) {
			System.out.println("加载驱动失败!");
			e.printStackTrace();
		}
		// 建立连接
		try {
			con = (Connection) DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			System.out.println("数据库连接成功!");
			rs = stmt.executeQuery(" select * from user where account='ycy' ");
			ResultSetMetaData meta_data = rs.getMetaData();// 列名
			for (int i_col = 1; i_col <= meta_data.getColumnCount(); i_col++) {
				System.out.print(meta_data.getColumnLabel(i_col) + "   ");
			}
			System.out.println();
			while (rs.next()) {
				for (int i_col = 1; i_col <= meta_data.getColumnCount(); i_col++) {
					System.out.println(rs.getString(i_col) + "  ");
				}
			}
			rs.close();

		} catch (SQLException e) {
			System.out.println("数据库连接失败!");
		}

	}

}
