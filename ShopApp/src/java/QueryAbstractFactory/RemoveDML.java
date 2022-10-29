package QueryAbstractFactory;

import java.io.IOException;
import java.io.PrintWriter;

import com.adventnet.ds.query.*;
import com.adventnet.persistence.*;
import com.google.gson.Gson;

import constants.SHOPPRODUCT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.OrderRemove;

public class RemoveDML implements DML {

	@Override
	public void getDML() {
		System.out.println("remove DML");
	}

	@Override
	public void doDMLOperation(HttpServletRequest req, HttpServletResponse res) throws IOException {

		HttpSession session = req.getSession(false);

		res.setContentType("application/json");

		OrderRemove orderremove = new Gson().fromJson(req.getReader(), OrderRemove.class);

		String p_name = orderremove.getP_name();
		String msg =  "Already removed!!";;

		PrintWriter out = res.getWriter();

		try {

			Criteria c = new Criteria(new Column(SHOPPRODUCT.TABLE, SHOPPRODUCT.PNAME),p_name, QueryConstants.EQUAL);
			DataAccess.delete(c);
			msg = "Product removed successfully";

		} catch (DataAccessException e) {
			System.out.println(e);
			msg = "Check price and quantity details !!";
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
