package edu.gzhu.yk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.gzhu.fuckyk.pojo.Member;
import edu.gzhu.yk.util.DBConnector;

public class MemberDAO {
	private Connection c;

	public MemberDAO() {
		c = DBConnector.getConnection();
	}

	public boolean save(Member m) {
		String saveSql = "insert into member (member_id,name,password) values(?,?,?)";
		PreparedStatement ps;
		try {
			ps = c.prepareStatement(saveSql);
			ps.setInt(1, m.getMember_id());
			ps.setString(2, m.getName());
			ps.setString(3, m.getPassword());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL“Ï≥£");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Member> findByMembername(String name) {
		// TODO Auto-generated method stub
		String findByNameSql = "select * from member where name=?";
		PreparedStatement ps;
		try {
			ps = c.prepareStatement(findByNameSql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			List<Member> list = new ArrayList<Member>();
			while (rs.next()) {
				Member m = new Member();
				m.setMember_id(rs.getInt("member_id"));
				m.setName(rs.getString("name"));
				m.setPassword(rs.getString("password"));
				list.add(m);
			}
			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
}
