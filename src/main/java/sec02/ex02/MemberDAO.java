package sec02.ex02;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberDAO {
	
	/*
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String user = "scott";
	private static final String pwd = "tiger";
	*/
	
	private PreparedStatement pstmt;
	private Connection con;
	private DataSource dataFactory;
	
	public MemberDAO() {
		try {
			
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/jspPrac");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<MemberVO> listMembers() {
		
		List<MemberVO> list = new ArrayList<>();
		
		try {
			
//			connDB();
			con = dataFactory.getConnection();
			String query = "SELECT * FROM t_member";
			System.out.println("preparedStatement : " + query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				
				MemberVO member = new MemberVO();
				member.setId(id);
				member.setPwd(pwd);
				member.setName(name);
				member.setEmail(email);
				member.setJoinDate(joinDate);
				
				list.add(member);
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public void addMember(MemberVO member) {
		try {
			
			con = dataFactory.getConnection();
			String query = "INSERT INTO t_member (id,pwd,name,email) VALUES (?,?,?,?)";
			System.out.println("prepareStatement: " + query);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPwd());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getEmail());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void delMember(String id) {
		// TODO Auto-generated method stub
		
	}
	
//	private void connDB() {
//		
//		try {
//			
//			Class.forName(driver);
//			System.out.println("Oracle 드라이버 로딩 성공");
//			con = DriverManager.getConnection(url, user, pwd);
//			System.out.println("Connection 생성 성공");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
