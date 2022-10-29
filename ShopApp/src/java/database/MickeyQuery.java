package database;

import com.adventnet.db.api.RelationalAPI;
import com.adventnet.ds.query.*;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.Persistence;
import com.sun.org.apache.bcel.internal.generic.Select;
import constants.CUSTOMER;
import constants.ORDERS;
import constants.SHOPPRODUCT;
import model.Order;

import java.sql.SQLException;

public class MickeyQuery {
    public static boolean check(Query sq) {

        try (java.sql.Connection c = RelationalAPI.getInstance().getConnection();
             DataSet ds = RelationalAPI.getInstance().executeQuery(sq, c);){

            if (ds.next()) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (QueryConstructionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getResult(Query sq) {

        try (java.sql.Connection c = RelationalAPI.getInstance().getConnection();
             DataSet ds = RelationalAPI.getInstance().executeQuery(sq, c);){

            if (ds.next()) {
                return ((Long)ds.getValue(1)).intValue();
            }
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (QueryConstructionException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static SelectQuery getSelectQuery(String table, String selectedcol,Criteria c) {
        Table table1 = new Table(table);
        SelectQuery sq = new SelectQueryImpl(table1);


        Column col = new Column(table, selectedcol);

        sq.addSelectColumn(col);

        //Criteria c = new Criteria(new Column(table, criteriacol), value, QueryConstants.EQUAL);
        sq.setCriteria(c);

        return sq;
    }

    public static void updateQuery(String table,Criteria c,String updateCol,Object value) throws Exception{
        Persistence per;

        per = (Persistence) BeanUtil.lookup("Persistence");
        // Sets the criteria to applied while updating the data into the datasource.
        UpdateQuery s = new UpdateQueryImpl(table);

        s.setCriteria(c);
        s.setUpdateColumn(updateCol,value);

        //update row
        per.update(s);

    }


}
