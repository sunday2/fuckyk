package edu.gzhu.yk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.gzhu.fuckyk.pojo.Admintor;
import edu.gzhu.yk.util.DBConnector;

public class AdmintorDAO {
	private Connection c;

	public AdmintorDAO() {
		c = DBConnector.getConnection();
	}

	public boolean save(Admintor a) {
		String saveSql = "insert into Admintor (admintor_id,name,password) values(?,?,?)";
		PreparedStatement ps;
		try {
			ps = c.prepareStatement(saveSql);
			ps.setInt(1, a.getAdmintor_id());
			ps.setString(2, a.getName());
			ps.setString(3, a.getPassword());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL“Ï≥£");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
