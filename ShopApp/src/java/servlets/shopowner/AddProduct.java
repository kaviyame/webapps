package servlets.shopowner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import QueryAbstractFactory.CRUDFactoryProducer;
import QueryAbstractFactory.DML;

public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddProduct() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			CRUDFactoryProducer crudFactoryProducer = new CRUDFactoryProducer();

			DML dml = crudFactoryProducer.getFactory("dml").getDML("Add");

			dml.doDMLOperation(request, response);

		} catch (NullPointerException e) {
			System.out.println(e);
		}

	}

}
