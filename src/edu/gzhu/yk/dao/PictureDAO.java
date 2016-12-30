package edu.gzhu.yk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import edu.gzhu.fuckyk.pojo.Picture;
import edu.gzhu.yk.util.DBConnector;

public class PictureDAO {

	private Connection c;

	public PictureDAO() {
		c = DBConnector.getConnection();
	}

	public boolean save(String picture_url) {
		String saveSql = "insert into picture (picture_url) values(?)";
		PreparedStatement ps;
		try {
			ps = c.prepareStatement(saveSql);
			ps.setString(1, picture_url);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL“Ï≥£");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
}
