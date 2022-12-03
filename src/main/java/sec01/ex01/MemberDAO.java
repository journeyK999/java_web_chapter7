package sec01.ex01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
	
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String user = "scott";
	private static final String pwd = "tiger";
	
	private Statement stmt;
	private Connection con;

	public List<MemberVO> listMembers() {
		
		List<MemberVO> list = new ArrayList<>();
		
		try {
			
			connDB();
			String query = "SELECT * FROM t_member";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
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
			stmt.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	private void connDB() {
		
		try {
			
			Class.forName(driver);
			System.out.println("Oracle 드라이버 로딩 성공");
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("Connection 생성 성공");
			stmt = con.createStatement();
			System.out.println("Statement 생성 성공");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
