package servlets.customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.adventnet.persistence.DataAccessException;
import database.OrdersDB;
import model.Order;


public class MyOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MyOrders() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession(false);
		OrdersDB ordersDB = new OrdersDB();

		

		try {
			String username = (String) session.getAttribute("username");
			List<Order> L = ordersDB.getOrders(username);
			out.print("{\"orders\":[");

			for (int i = 0; i < L.size(); i++) {
				if (i == L.size() - 1)
					out.print(L.get(i).toString());
				else
					out.print(L.get(i).toString() + ",");
			}
			out.print("]}");
		} catch (DataAccessException | SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			out.println("Session not valid. Log in again");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
