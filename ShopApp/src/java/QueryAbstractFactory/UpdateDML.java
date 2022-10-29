package QueryAbstractFactory;

import java.io.IOException;
import java.io.PrintWriter;

import com.adventnet.ds.query.*;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.*;
import com.google.gson.Gson;

import constants.SHOPPRODUCT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ShopProduct;

public class UpdateDML implements DML {

	@Override
	public void getDML() {
		System.out.println("update dml");
	}

	@Override
	public void doDMLOperation(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		HttpSession session = req.getSession(false);

		res.setContentType("application/json");

		ShopProduct shopproduct = new Gson().fromJson(req.getReader(), ShopProduct.class);

		String p_name = shopproduct.getP_name();
		int price = shopproduct.getPrice();
		int avlquan = shopproduct.getavlquan();
		String msg = "Check price and quantity details !!";

		PrintWriter out = res.getWriter();
		try {
			if (price <= 0 && avlquan <= 0) {
				throw new DataAccessException();
			}

			Criteria c = new Criteria(new Column(SHOPPRODUCT.TABLE, SHOPPRODUCT.PNAME),
					p_name, QueryConstants.EQUAL);


			Persistence per = null;

			per = (Persistence) BeanUtil.lookup("Persistence");
			// Sets the criteria to applied while updating the data into the datasource.
			UpdateQuery s = new UpdateQueryImpl(SHOPPRODUCT.TABLE);

			s.setCriteria(c);
			s.setUpdateColumn(SHOPPRODUCT.PRICE, price);
			s.setUpdateColumn(SHOPPRODUCT.QUANTITY, avlquan);

			//update row
			per.update(s);
			msg = "Product details updated";

			System.out.println("Updated Cart Quantity");

		}catch (DataAccessException e) {
			System.out.println(e);
			msg = "Check price and quantity details !!";

		} catch (NumberFormatException | NullPointerException e) {
			System.out.println(e);
			msg = "Ensure you have filled all details";
		} catch (Exception e) {
			e.printStackTrace();
		}

		out.println("{\"p_name\":\"" + p_name + "\", " + "\"avlquan\":\"" + avlquan + "\", " + "\"price\":\"" + price
				+ "\", " + "\"msg\":\"" + msg + "\"}");
	}

}
