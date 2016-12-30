package edu.gzhu.yk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.gzhu.fuckyk.pojo.Member;
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
	public List<Picture> getAll(){
		String saveSql = "select picture_id, picture_url from picture";
		PreparedStatement ps;
		try {
			ps = c.prepareStatement(saveSql);
			ResultSet rs = ps.executeQuery();
			List<Picture> list=new ArrayList<Picture>();
			while (rs.next()) {
				Picture m = new Picture();
				m.setPicture_id(rs.getInt("picture_id"));
				m.setPicture_url(rs.getString("picture_url"));
				list.add(m);
			}
			return list;
		} catch (SQLException e) {
			System.out.println("SQL“Ï≥£");
			e.printStackTrace();
			return null;
		}
		
	}
	
}
