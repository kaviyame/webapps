package servlets.customer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CustomerCartItemRemove;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import com.google.gson.Gson;

import database.OrdersDB;

public class RemoveCartItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RemoveCartItem() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		OrdersDB ordersDB = new OrdersDB();

		String username = null;
		try {
			username = (String) session.getAttribute("username");
		} catch (NullPointerException e) {
			out.println("Session not valid. Log in again");
		}

		CustomerCartItemRemove removeItem = new Gson().fromJson(request.getReader(), CustomerCartItemRemove.class);

		
		String p_name = removeItem.getP_name();

		

		try {
			ordersDB.deleteCartItem(username, p_name);
			request.setAttribute("msg", "Item removed from cart");
		} catch (SQLException e) {
			request.setAttribute("msg", "Item already removed from cart");
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}

		int cartquan = ordersDB.getCartQuantity(username, p_name);
		int price = ordersDB.getPrice( p_name);

		out.println("{\"p_name\":\"" + p_name + "\", " + "\"cartquan\":\"" + cartquan + "\", " + "\"price\":\"" + price
				+ "\", " + "\"msg\":\"" + request.getAttribute("msg") + "\"}");

	}

}
