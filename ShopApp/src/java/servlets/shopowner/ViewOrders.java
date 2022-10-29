package servlets.shopowner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.adventnet.persistence.DataAccessException;
import model.Order;
import model.Product;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import database.OrdersDB;

public class ViewOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewOrders() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		OrdersDB ordersDB = new OrdersDB();

		try {

			List<Order> L = ordersDB.getOrders();
			out.print("{\"orders\":[");

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
