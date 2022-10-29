package servlets.customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.adventnet.persistence.DataAccessException;
import model.Product;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import database.OrdersDB;

public class ViewProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewProducts() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
//		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		response.setHeader("Credentials", "include");
//		response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Credentials");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession(false);
		OrdersDB ordersDB = new OrdersDB();


		try {
			String username = (String) session.getAttribute("username");

			List<Product> L = ordersDB.getProducts(username);
			out.print("{\"products\":[");

			for (int i = 0; i < L.size(); i++) {
				if (i == L.size() - 1)
					out.print(L.get(i).toString());
				else
					out.print(L.get(i).toString() + ",");
			}
			out.print("]}");
		} catch (SQLException | DataAccessException e) {
			request.setAttribute("msg", "Couldn't get order details");
			e.printStackTrace();
		} catch (NullPointerException e) {
			out.println("Session not valid. Log in again");
		}
	}

}
