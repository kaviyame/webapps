package servlets.shopowner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Product;
import model.ShopProduct;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import QueryAbstractFactory.CRUDFactoryProducer;
import QueryAbstractFactory.DML;
import QueryAbstractFactory.DQL;
import database.OrdersDB;

public class MyProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MyProducts() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			CRUDFactoryProducer crudFactoryProducer = new CRUDFactoryProducer();

			DQL dql = crudFactoryProducer.getFactory("dql").getDQL("Show");

			dql.doDQLOperation(request, response);

		} catch (NullPointerException e) {
			System.out.println(e);
		}

	}

}
