package verifchain;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;

import com.adventnet.persistence.DataAccessException;
import database.EntryDB;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Register;

class ValProcessor extends Processor {
	public ValProcessor(Processor nextProcessor) {
		super(nextProcessor);
	}

	public void process(Type type, Register login, HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException {
		if (type.getType().equals("validate")) {
			try {
				if (isValidated(login, req, res)) {
					try {
						new EntryDB().doInsert(login,req, res);
						createSession(login, req, res);
						chain.doFilter(req, res);

					} catch (DataAccessException e) {
						PrintWriter out = res.getWriter();
						res.setContentType("application/json");
						out.println("{\"result\": \"Username already exists\"}");
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			super.process(type, login, req, res, chain);
		}

	}

	private boolean isValidated(Register login, HttpServletRequest req, HttpServletResponse res) throws IOException {

		res.setContentType("application/json");
		PrintWriter out = res.getWriter();
		
		String msg="";
		String name = login.getName();
		int age = login.getAge();
		String gender = login.getGender();
		String username = login.getUsername();
		String password = login.getPassword();
		String confirmpassword = login.getConfirmpassword();
		String contact = login.getContact();

		boolean result = true;
		System.out.println(name);
		if (!name.matches("[a-zA-Z]+")) {
			result = false;
			msg+="Name should contain only alphabets<br><br>";
		}

		if (!(age >= 0)) {
			result = false;
			msg+="Age should be greater than 0<br><br>";
		}
		if (!username.matches("[a-zA-Z0-9]+")) {
			result = false;
			msg+="Username should contain only digits and alphabets<br><br>";
		}
		if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")) {
			result = false;
			msg+=
					"Password should contain minimum eight characters,<br>at least one letter, one number and one special character<br><br>";
		}
		if (!confirmpassword.equals(password)) {
			result = false;
			msg+="Password didn't match the confirm password<br><br>";
		}

		if (!contact.matches("[0-9]{10,}")) {
			result = false;
			msg+="Contact no should be of 10 digits<br><br>";
		}
		if(msg!="") {
			out.println("{\"result\": \""+msg+"\"}");
		}
		return result;
	}

	private void createSession(Register login, HttpServletRequest req, HttpServletResponse res) {
		HttpSession oldsession = req.getSession(false);
		HttpSession session;

		String userAttr = login.getUserType();
		// String userAttr = req.getParameter("userType");
		if (oldsession != null)
			oldsession.invalidate();
		session = req.getSession();
		session.setAttribute("userAttr", userAttr);
		session.setAttribute("selectedprods", null);
		session.setAttribute("location", null);
		session.setAttribute("username", login.getUsername());

	}
}
