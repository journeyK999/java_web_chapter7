package sec01.ex02;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
	
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String user = "scott";
	private static final String pwd = "tiger";
	
	private PreparedStatement pstmt;
	private Connection con;

	public List<MemberVO> listMembers() {
		
		List<MemberVO> list = new ArrayList<>();
		
		try {
			
			connDB();
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
	
	private void connDB() {
		
		try {
			
			Class.forName(driver);
			System.out.println("Oracle ����̹� �ε� ����");
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("Connection ���� ����");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
