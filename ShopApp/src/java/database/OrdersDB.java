package database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.adventnet.db.api.RelationalAPI;
import com.adventnet.ds.query.*;
import com.adventnet.persistence.*;
import constants.*;
import model.Cart;
import model.Order;
import model.Product;
import model.ShopProduct;
public class OrdersDB {

	public int getOrderedQuantity(String username, String p_name) {

		Table table1 = new Table(ORDERS.TABLE);
		SelectQuery sq = new SelectQueryImpl(table1);


		Column col = new Column(ORDERS.TABLE, ORDERS.QUANTITY);

		sq.addSelectColumn(col);

		Criteria c = new Criteria(new Column(ORDERS.TABLE, ORDERS.USERNAME), username, QueryConstants.EQUAL);
		Criteria c1 = new Criteria(new Column(ORDERS.TABLE, ORDERS.PNAME), p_name, QueryConstants.EQUAL);
		sq.setCriteria(c.and(c1));

		return MickeyQuery.getResult(sq);
	}

	public int getAvailableQuantity(String p_name) {

		Criteria c=new Criteria(new Column(SHOPPRODUCT.TABLE,SHOPPRODUCT.PNAME), p_name, QueryConstants.EQUAL);

		SelectQuery sq=MickeyQuery.getSelectQuery(SHOPPRODUCT.TABLE,SHOPPRODUCT.QUANTITY,c);

		return MickeyQuery.getResult(sq);

	}

	public int getPrice( String p_name) {

		Criteria c=new Criteria(new Column(SHOPPRODUCT.TABLE,SHOPPRODUCT.PNAME), p_name, QueryConstants.EQUAL);

		SelectQuery sq=MickeyQuery.getSelectQuery(SHOPPRODUCT.TABLE,SHOPPRODUCT.PRICE,c);

		return MickeyQuery.getResult(sq);

	}

	public void updateShopProdQuantity( String p_name, int sel_quantity) throws Exception {

		int quantity=getAvailableQuantity(p_name);

		if(sel_quantity<=0)
			throw new DataAccessException();


		Criteria c = new Criteria(new Column(SHOPPRODUCT.TABLE, SHOPPRODUCT.PNAME),
									p_name, QueryConstants.EQUAL);
		Criteria c1 = new Criteria(new Column(SHOPPRODUCT.TABLE, SHOPPRODUCT.QUANTITY),
									sel_quantity, QueryConstants.GREATER_EQUAL);

		MickeyQuery.updateQuery(SHOPPRODUCT.TABLE,c.and(c1),SHOPPRODUCT.QUANTITY,
								quantity-sel_quantity);

		//System.out.println(RelationalAPI.getInstance().getSelectSQL(query));
		System.out.println("Updated ShopProdQuantity");
	}

	public void insertOrder(String username, String p_name, int sel_quantity) throws DataAccessException {

		Row r = new Row(ORDERS.TABLE);
		r.set(ORDERS.USERNAME, username);
		r.set(ORDERS.SNAME, SHOPOWNER.SNAME);
		r.set(ORDERS.PNAME, p_name);
		r.set(ORDERS.QUANTITY, sel_quantity);

		DataObject d = new WritableDataObject();

		d.addRow(r);
		DataAccess.add(d);

		System.out.println("insertCart ");
	}

	public void updateCartQuantity(String username, String p_name, int sel_quantity) throws Exception {

		Criteria c = new Criteria(new Column(CART.TABLE, CART.USERNAME),
				username, QueryConstants.EQUAL);
		Criteria c1 = new Criteria(new Column(CART.TABLE, CART.PNAME),
				p_name, QueryConstants.EQUAL);

		MickeyQuery.updateQuery(CART.TABLE,c.and(c1),CART.QUANTITY, sel_quantity);

		System.out.println("Updated Cart Quantity");
	}

	public void insertCart(String username, String p_name, int sel_quantity) throws DataAccessException {

		Row r = new Row(CART.TABLE);
		r.set(CART.USERNAME, username);
		r.set(CART.SNAME, SHOPOWNER.SNAME);
		r.set(CART.PNAME, p_name);
		r.set(CART.QUANTITY, sel_quantity);

		DataObject d = new WritableDataObject();

		d.addRow(r);
		DataAccess.add(d);

		System.out.println("insertCart ");
	}

	public List<Order> getOrders(String username) throws DataAccessException, SQLException {
		String p_name ;
		int ord_quan ;
		int price ;
		int net_price ;
		Order order;
		List<Order> L = new ArrayList<>();

		Table table1 = new Table(SHOPPRODUCT.TABLE);
		SelectQuery sq = new SelectQueryImpl(table1);
		Column col1 = new Column(ORDERS.TABLE,ORDERS.PNAME);
		Column col2 = new Column(ORDERS.TABLE,ORDERS.QUANTITY);
		Column col3 = new Column(SHOPPRODUCT.TABLE,SHOPPRODUCT.PRICE);
		Column col4 = Column.createOperation(Operation.operationType.MULTIPLY,col2,col3);

 
		ArrayList<Column> colList = new ArrayList<>();
		colList.add(col1);
		colList.add(col2);
		colList.add(col3);
		colList.add(col4);


		sq.addSelectColumns(colList);

  
		Criteria c = new Criteria(new
				Column(ORDERS.TABLE, ORDERS.USERNAME),username,
				QueryConstants.EQUAL);

		sq.setCriteria(c);

   
		Join join = new Join(SHOPPRODUCT.TABLE, ORDERS.TABLE, new String[]{SHOPPRODUCT.PNAME}, new String[]{ORDERS.PNAME}, Join.LEFT_JOIN);
		sq.addJoin(join);

		SortColumn sc = new SortColumn(col1,true);
		sq.addSortColumn(sc);

		try (java.sql.Connection conn = RelationalAPI.getInstance().getConnection();
			 DataSet ds = RelationalAPI.getInstance().executeQuery(sq, conn)){
			if(ds==null)
				throw new DataAccessException();

			while (ds.next()) {
				p_name = ds.getAsString(1);
				ord_quan = ds.getAsLong(2).intValue();
				price = ds.getAsLong(3).intValue();
				net_price = ds.getAsLong(4).intValue();

				order = new Order(username, p_name, ord_quan, price, net_price);
				L.add(order);
			}

		} catch (QueryConstructionException e) {
			e.printStackTrace();
		}

		return L;
	}

	public List<Product> getProducts(String username) throws SQLException, DataAccessException {
		String p_name ;
		int price ;
		int avl_quan ;
		int cart_quan ;

		Product product;
		List<Product> L = new ArrayList<>();

		Table table1 = new Table(SHOPPRODUCT.TABLE);
		SelectQuery sq = new SelectQueryImpl(table1);
		Column col1 = new Column(SHOPPRODUCT.TABLE,SHOPPRODUCT.PNAME);
		Column col2 = new Column(SHOPPRODUCT.TABLE,SHOPPRODUCT.PRICE);
		Column col3 = new Column(SHOPPRODUCT.TABLE,SHOPPRODUCT.QUANTITY);

		ArrayList<Column> colList = new ArrayList<>();
		colList.add(col1);
		colList.add(col2);
		colList.add(col3);
		sq.addSelectColumns(colList);

		SortColumn sc = new SortColumn(col1,true);
		sq.addSortColumn(sc);

		try (java.sql.Connection conn = RelationalAPI.getInstance().getConnection();
			 DataSet ds = RelationalAPI.getInstance().executeQuery(sq, conn)){
			while (ds.next()) {
				p_name = ds.getAsString(1);
				price = ds.getAsLong(2).intValue();
				avl_quan = ds.getAsLong(3).intValue();
				cart_quan = 0;

				Criteria c=new Criteria(new Column(CART.TABLE,CART.USERNAME), username, QueryConstants.EQUAL);
				Criteria c1=new Criteria(new Column(CART.TABLE,CART.PNAME), p_name, QueryConstants.EQUAL);

				SelectQuery sq1=MickeyQuery.getSelectQuery(CART.TABLE,CART.QUANTITY,c.and(c1));
				try {
					System.out.println("getCartQuan-- "+RelationalAPI.getInstance().getSelectSQL(sq1));
				} catch (QueryConstructionException e) {
					e.printStackTrace();
				}
				try (DataSet ds1 = RelationalAPI.getInstance().executeQuery(sq1, conn)) {

					if (ds1.next()) cart_quan = ds1.getAsLong(1).intValue();
					product = new Product(p_name, price, avl_quan, cart_quan);
					L.add(product);
				}
			}
		} catch (QueryConstructionException e) {
			e.printStackTrace();
		}


		return L;

	}

	public int getCartQuantity(String username, String p_name) {

		Criteria c = new Criteria(new Column(CART.TABLE,CART.USERNAME),username, QueryConstants.EQUAL);
		Criteria c1 = new Criteria(new Column(CART.TABLE,CART.PNAME),p_name, QueryConstants.EQUAL);

		SelectQuery sq = MickeyQuery.getSelectQuery(CART.TABLE,CART.QUANTITY,c.and(c1));

		return MickeyQuery.getResult(sq);

	}

	public List<Cart> getCart(String username) throws SQLException, DataAccessException {
		String p_name ;
		int cart_quan ;
		int price ;
		int net_price;
		Cart cart;
		List<Cart> L = new ArrayList<>();

		Table table1 = new Table(SHOPPRODUCT.TABLE);
		SelectQuery sq = new SelectQueryImpl(table1);
		Column col1 = new Column(CART.TABLE,CART.PNAME);
		Column col2 = new Column(CART.TABLE,ORDERS.QUANTITY);
		Column col3 = new Column(SHOPPRODUCT.TABLE,SHOPPRODUCT.PRICE);
		Column col4 = Column.createOperation(Operation.operationType.MULTIPLY,col2,col3);

 
		ArrayList<Column> colList = new ArrayList<>();
		colList.add(col1);
		colList.add(col2);
		colList.add(col3);
		colList.add(col4);


		sq.addSelectColumns(colList);

  
		Criteria c = new Criteria(new
				Column(CART.TABLE, CART.USERNAME),username,
				QueryConstants.EQUAL);


		sq.setCriteria(c);

   
		Join join = new Join(SHOPPRODUCT.TABLE, CART.TABLE, new String[]{SHOPPRODUCT.PNAME}, new String[]{CART.PNAME}, Join.LEFT_JOIN);
		sq.addJoin(join);


   
		SortColumn sc = new SortColumn(col1,true);
		sq.addSortColumn(sc);

		try {
			System.out.println("getCart "+RelationalAPI.getInstance().getSelectSQL(sq));
		} catch (QueryConstructionException e) {
			e.printStackTrace();
		}

		try (java.sql.Connection conn = RelationalAPI.getInstance().getConnection();
			 DataSet ds = RelationalAPI.getInstance().executeQuery(sq, conn)) {

			if (ds == null)
				throw new DataAccessException();

			while (ds.next()) {
				p_name = ds.getAsString(1);
				cart_quan = ds.getAsLong(2).intValue();
				price = ds.getAsLong(3).intValue();
				net_price = ds.getAsLong(4).intValue();

				cart = new Cart(p_name, cart_quan, price, net_price);
				L.add(cart);
			}
		} catch (QueryConstructionException e) {
			e.printStackTrace();
		}

		return L;

	}

	public void deleteCartItem(String username, String p_name) throws Exception {

		Criteria c = new Criteria(new Column(CART.TABLE, CART.USERNAME),
				username, QueryConstants.EQUAL);
		Criteria c1 = new Criteria(new Column(CART.TABLE, CART.PNAME),
				p_name, QueryConstants.EQUAL);

		DataAccess.delete(c.and(c1));

		System.out.println("Updated deleteCartItem");

	}

	public List<Order> getOrders() throws SQLException, DataAccessException {
		String username;
		String p_name;
		int ord_quan;
		int price ;
		int net_price;
		Order order;
		List<Order> L = new ArrayList<>();

		Table table1 = new Table(SHOPPRODUCT.TABLE);
		SelectQuery sq = new SelectQueryImpl(table1);
		Column col1 = new Column(ORDERS.TABLE,ORDERS.USERNAME);
		Column col2 = new Column(ORDERS.TABLE,ORDERS.PNAME);
		Column col3 = new Column(ORDERS.TABLE,ORDERS.QUANTITY);
		Column col4 = new Column(SHOPPRODUCT.TABLE,SHOPPRODUCT.PRICE);
		Column col5 = Column.createOperation(Operation.operationType.MULTIPLY,col3,col4);

 
		ArrayList<Column> colList = new ArrayList<>();
		colList.add(col1);
		colList.add(col2);
		colList.add(col3);
		colList.add(col4);
		colList.add(col5);

		sq.addSelectColumns(colList);

   
		Join join = new Join(SHOPPRODUCT.TABLE, ORDERS.TABLE, new String[]{ORDERS.PNAME}, new String[]{SHOPPRODUCT.PNAME}, Join.INNER_JOIN);
		sq.addJoin(join);


   
		SortColumn sc = new SortColumn(col2,true);
		sq.addSortColumn(sc);

		try {
			System.out.println(RelationalAPI.getInstance().getSelectSQL(sq));
		} catch (QueryConstructionException e) {
			e.printStackTrace();
		}


		try (java.sql.Connection conn = RelationalAPI.getInstance().getConnection();
			 DataSet ds = RelationalAPI.getInstance().executeQuery(sq, conn)) {

			if (ds == null)
				throw new DataAccessException();

			while (ds.next()) {
				username = ds.getAsString(1);
				p_name = ds.getAsString(2);
				ord_quan = ds.getAsLong(3).intValue();
				price = ds.getAsLong(4).intValue();
				net_price = ds.getAsLong(5).intValue();

				order = new Order(username, p_name, ord_quan, price, net_price);
				L.add(order);
			}
		} catch (QueryConstructionException e) {
			e.printStackTrace();
		}
		return L;

	}

	public List<ShopProduct> getProducts() throws SQLException, DataAccessException {
		String p_name ;
		int price ;
		int avl_quan ;

		ShopProduct shopproduct;
		List<ShopProduct> L = new ArrayList<>();

		Table table1 = new Table(SHOPPRODUCT.TABLE);
		SelectQuery sq = new SelectQueryImpl(table1);
		Column col1 = new Column(SHOPPRODUCT.TABLE,SHOPPRODUCT.PNAME);
		Column col2 = new Column(SHOPPRODUCT.TABLE,SHOPPRODUCT.PRICE);
		Column col3 = new Column(SHOPPRODUCT.TABLE,SHOPPRODUCT.QUANTITY);

 
		ArrayList<Column> colList = new ArrayList<>();
		colList.add(col1);
		colList.add(col2);
		colList.add(col3);


		sq.addSelectColumns(colList);

		SortColumn sc = new SortColumn(col1,true);
		sq.addSortColumn(sc);

		try (java.sql.Connection conn = RelationalAPI.getInstance().getConnection();
			 DataSet ds = RelationalAPI.getInstance().executeQuery(sq, conn)) {

			if (ds == null)
				throw new DataAccessException();

			while (ds.next()) {
				p_name = ds.getAsString(1);
				price = ds.getAsLong(2).intValue();
				avl_quan = ds.getAsLong(3).intValue();

				shopproduct = new ShopProduct(p_name, price, avl_quan);
				L.add(shopproduct);
			}
		} catch (QueryConstructionException e) {
			e.printStackTrace();
		}
		return L;

	}

}
