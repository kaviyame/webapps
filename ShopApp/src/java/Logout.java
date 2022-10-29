
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Logout() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		HttpSession currentsession = request.getSession(false);
		if (currentsession != null && currentsession.getAttribute("userAttr").equals("ShopOwner")) {
			currentsession.invalidate();
			response.getWriter().println("{\"userType\": \"ShopOwner\"}");
		} else if (currentsession != null && currentsession.getAttribute("userAttr").equals("Customer")) {
			currentsession.invalidate();
			response.getWriter().println("{\"userType\": \"Customer\"}");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
