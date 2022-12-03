package sec02.ex02;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member4")
public class MemberServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandle(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("post method");
		doHandle(req, resp);
	}

	private void doHandle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		MemberDAO dao = new MemberDAO();
		
		String command = req.getParameter("command");
		
		if(command != null && command.equals("addMember")) {
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			String name = req.getParameter("name");
			String email = req.getParameter("email");
			MemberVO member = new MemberVO(id, pwd, name, email);
			dao.addMember(member);
		} else if (command != null && command.equals("delMember")) {
			String id = req.getParameter("id");
			dao.delMember(id);
		}
		
		List<MemberVO> list = dao.listMembers();
		
		out.print("<html><body>");
		out.print("<table border=1><tr align='center' bgcolor='lightgreen'>");
		out.print("<td>아이디</td><td>비밀번호</td><td>이름</td><td>이메일</td><td>가입일</td><td>삭제</td></tr>");
		
		for (int i = 0; i < list.size(); i++) {
			MemberVO member = list.get(i);
			String id = member.getId();
			String pwd = member.getPwd();
			String name = member.getName();
			String email = member.getEmail();
			Date joinDate = member.getJoinDate();
			out.print("<tr><td>" + id + "</td><td>" + pwd + "</td><td>" + name + "</td><td>" + email 
					+ "</td><td>" + joinDate + "</td><td>" + "<a href='/pro07/memberJoin?command=delMember&id=" + id 
					+ "'>삭제</a></td></tr>");
		}
		
		out.print("</table></body></html>");
		out.print("<a href='/pro07/memberForm.html'>새 회원 등록하기</a>");
		
	}

}
