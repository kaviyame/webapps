package QueryAbstractFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.adventnet.persistence.DataAccessException;
import database.OrdersDB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ShopProduct;

public class ShowDQL implements DQL {

	@Override
	public void getDQL() {
		System.out.println("show DQL");
	}

	@Override
	public void doDQLOperation(HttpServletRequest req, HttpServletResponse res) throws IOException {

		PrintWriter out = res.getWriter();
		res.setContentType("text/html");

		OrdersDB ordersDB = new OrdersDB();

		try {

			List<ShopProduct> L = ordersDB.getProducts();

			out.print("{\"products\": [");

			for (int i = 0; i < L.size(); i++) {
				if (i == L.size() - 1)
					out.print(L.get(i).toString());
				else
					out.print(L.get(i).toString() + ", ");
			}
			out.print("]}");
		} catch (SQLException | DataAccessException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			out.println("Session not valid. Log in again");
		}

	}
}
