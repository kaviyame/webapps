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
import model.CustomerCart;

public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddToCart() {
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
//		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		response.setHeader("Credentials", "include");
//		response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Credentials");

		CustomerCart cart = new Gson().fromJson(request.getReader(), CustomerCart.class);

		System.out.println(cart.getP_name());
		System.out.println(cart.getSelectedquan());
		String p_name = cart.getP_name();
		int selquantity = cart.getSelectedquan();

		OrdersDB ordersDB = new OrdersDB();

		int prevquan = ordersDB.getCartQuantity(username, p_name);
		int avlquan = ordersDB.getAvailableQuantity( p_name);
		int price = ordersDB.getPrice( p_name);
		int cartquan = ordersDB.getCartQuantity(username, p_name);

		int netprice = prevquan * price;

		System.out.println(
				username + " " + " p_name " + p_name + " selquantity " + selquantity + " prevquan " + prevquan);



		if (prevquan > 0 && selquantity <= avlquan) {
			try {
				ordersDB.updateCartQuantity(username, p_name, selquantity);
				netprice = selquantity * price;
				cartquan = ordersDB.getCartQuantity(username, p_name);
				request.setAttribute("msg", p_name + " quantity changed to " + selquantity);
			} catch (DataAccessException e) {
				request.setAttribute("msg", "Couldn't update order");
			} catch(Exception e){
				request.setAttribute("msg", "Couldn't update order");
				e.printStackTrace();
			}
		} else if (selquantity <= avlquan) {
			try {
				ordersDB.insertCart(username, p_name, selquantity);
				netprice = selquantity * price;
				cartquan = ordersDB.getCartQuantity(username, p_name);
				request.setAttribute("msg", selquantity + " " + p_name + " added to cart");
			} catch (DataAccessException e) {
				request.setAttribute("msg", "Couldn't insert order");
			}
		} else {
			request.setAttribute("msg", "Check the item quantity");
		}

		out.println("{\"p_name\":\"" + p_name + "\", " + "\"selquantity\":\"" + selquantity + "\", " + "\"avlquan\":\""
				+ avlquan + "\", " + "\"price\":\"" + price + "\", " + "\"netprice\":\"" + netprice + "\", "
				+ "\"cartquan\": \"" + cartquan + "\", " + "\"msg\":\"" + request.getAttribute("msg") + "\"}");

	}

}
