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

	public boolean save(Picture p) {
		String saveSql = "insert into picture (picture_id,pictue_url) values(?,?)";
		PreparedStatement ps;
		try {
			ps = c.prepareStatement(saveSql);
			ps.setInt(1, p.getPicture_id());
			ps.setString(2, p.getPicture_url());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL“Ï≥£");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
}
