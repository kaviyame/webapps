package servlets.customer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import com.adventnet.persistence.DataAccessException;

import com.google.gson.Gson;

import database.OrdersDB;
import model.CustomerOrder;

public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Order() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		String username = null;
		try {
			username = (String) session.getAttribute("username");
		} catch (NullPointerException e) {
			out.println("Session not valid. Log in again");
		}

		response.setContentType("application/json");

		CustomerOrder order = new Gson().fromJson(request.getReader(), CustomerOrder.class);

		
		String p_name = order.getP_name();
		int selquantity = order.getSelectedquan();

		OrdersDB ordersDB = new OrdersDB();

		int avlquan = ordersDB.getAvailableQuantity(p_name);
		int price = ordersDB.getPrice(p_name);
		int ordquan = 0;

		
		try {
			ordersDB.updateShopProdQuantity(p_name, selquantity);
			avlquan = ordersDB.getAvailableQuantity( p_name);
			try {
				ordersDB.insertOrder(username, p_name, selquantity);
				ordquan = ordersDB.getOrderedQuantity(username, p_name);
				request.setAttribute("msg", "You've ordered " + ordquan + " " + p_name);
			} catch (DataAccessException e) {
				e.printStackTrace();
				request.setAttribute("msg", "Couldn't insert order");
			}

			ordersDB.deleteCartItem(username, p_name);
		} catch (DataAccessException e) {
			ordquan = ordersDB.getOrderedQuantity(username, p_name);
			request.setAttribute("msg", "Check the item quantity");
		} catch(Exception e){
			e.printStackTrace();
		}

		out.println("{\"p_name\":\"" + p_name + "\", " + "\"selquantity\":\"" + selquantity + "\", " + "\"avlquan\":\""
				+ avlquan + "\", " + "\"price\":\"" + price + "\", " + "\"ordquan\": \"" + ordquan + "\", "
				+ "\"msg\":\"" + request.getAttribute("msg") + "\"}");

	}

}
