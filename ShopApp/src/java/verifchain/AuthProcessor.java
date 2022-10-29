package verifchain;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import database.CheckDB;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Register;

class AuthProcessor extends Processor {
	public AuthProcessor(Processor nextProcessor) {
		super(nextProcessor);

	}

	public void process(Type type, Register login,HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException {

		if (type.getType().equals("auth")) {
			try {
				if (isAuthenticated(login,req, res)) {
					createSession(login,req, res);
					chain.doFilter(req, res);
				} else {
					PrintWriter out = res.getWriter();
					res.setContentType("application/json");
					out.println("{\"result\": \"Wrong Credentials\"}");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			super.process(type,login, req, res, chain);
		}

	}

	private boolean isAuthenticated(Register login,HttpServletRequest req, HttpServletResponse res){
		return new CheckDB().authenticate(login,req, res);
	}

	private void createSession(Register login,HttpServletRequest req, HttpServletResponse res) throws JsonSyntaxException, JsonIOException, IOException {
		HttpSession oldsession = req.getSession(false);
		HttpSession session;
		
		//model.Login login = new Gson().fromJson(req.getReader(), model.Login.class);
		
		//String userAttr = req.getParameter("userType");
		String userAttr = login.getUserType();
		
		if (oldsession != null)
			oldsession.invalidate();
		session = req.getSession();
		session.setAttribute("userAttr", userAttr);
		session.setAttribute("selectedprods", null);
		session.setAttribute("location", null);
		session.setAttribute("username", login.getUsername());
		//session.setAttribute("username", req.getParameter("username"));

	}
}
