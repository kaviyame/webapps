package QueryAbstractFactory;

import java.io.IOException;
import java.io.PrintWriter;

import com.adventnet.persistence.*;
import com.google.gson.Gson;

import constants.SHOPPRODUCT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ShopProduct;

public class AddDML implements DML {

	@Override
	public void getDML() {
		System.out.println("add dml");
	}

	@Override
	public void doDMLOperation(HttpServletRequest req, HttpServletResponse res) throws IOException {

		
		res.setContentType("application/json");

		ShopProduct shopproduct = new Gson().fromJson(req.getReader(), ShopProduct.class);

		String p_name = shopproduct.getP_name();
		int avlquan = shopproduct.getavlquan();
		int price = shopproduct.getPrice();
		String msg = null;

		PrintWriter out = res.getWriter();

		Row r = new Row(SHOPPRODUCT.TABLE);
		r.set(SHOPPRODUCT.PNAME, p_name);
		r.set(SHOPPRODUCT.PRICE, price);
		r.set(SHOPPRODUCT.QUANTITY, avlquan);


		DataObject d = new WritableDataObject();

		try {
			d.addRow(r);
			DataAccess.add(d);
		} catch (DataAccessException e) {
			System.out.println(e);
			msg = "Product already exists !!";
		} catch (NumberFormatException e) {
			System.out.println(e);
			msg = "Ensure you have filled all details";
		} catch (NullPointerException e) {
			System.out.println(e);
			msg = "Ensure you have filled all details";
		}



		out.println("{\"p_name\":\"" + p_name + "\", " + "\"msg\":\"" + msg + "\"}");
	}
}
