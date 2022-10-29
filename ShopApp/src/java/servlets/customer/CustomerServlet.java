package servlets.customer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {

		System.out.println("CustomerServlet initialized");
	}

	public CustomerServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("application/json");
//		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		response.setHeader("Credentials", "include");
//		response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Credentials");

//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200/");
//		response.setHeader("credentials", "include");
//		response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin, credentials");
		
		out.println("{\"result\": \"true\"}");
		//request.getRequestDispatcher("/customer_view/prod_list.html").forward(request, response);

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);
	}
	
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
}
