package servlets.shopowner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import QueryAbstractFactory.*;

public class ShopOwnerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShopOwnerServlet() {
		super();

	}

	public void init(ServletConfig config) throws ServletException {

		System.out.println("ShopOwnerServlet initialized");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("application/json");
//		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		response.setHeader("Credentials", "include");
//		response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Credentials");
//
// 		response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200/");
//		response.setHeader("credentials", "include");
//		response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin, credentials");
		
		out.println("{\"result\": \"true\"}");
		//request.getRequestDispatcher("/shopowner_view/orders.html").forward(request, response);

	}

}
