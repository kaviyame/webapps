package database;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.adventnet.db.api.RelationalAPI;
import com.adventnet.ds.query.*;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.*;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.CUSTOMER;
import constants.SHOPOWNER;
import model.Register;

public class CheckDB {
    public boolean authenticate(Register login, HttpServletRequest req, HttpServletResponse res) {

        String userAttr = login.getUserType();
        String username = login.getUsername();
        String password = login.getPassword();

        String table = SHOPOWNER.TABLE;
        if (userAttr.equalsIgnoreCase("customer"))
            table = CUSTOMER.TABLE;

        Table table1 = new Table(table);
        SelectQuery sq = new SelectQueryImpl(table1);


        Column col = new Column(table, "*");

        sq.addSelectColumn(col);

        // select query criteria
        Criteria c = new Criteria(new Column(table, "USERNAME"), username, QueryConstants.EQUAL);
        Criteria c1 = new Criteria(new Column(table, "PASSWORD"), password, QueryConstants.EQUAL);
        sq.setCriteria(c.and(c1));

        try {
            System.out.println("check-- "+RelationalAPI.getInstance().getSelectSQL(sq));
        } catch (QueryConstructionException e) {
            e.printStackTrace();
        }

        boolean result = MickeyQuery.check(sq);


        if (result)
            return true;
        else {
            System.out.println("Authentication result : false ");
            return false;
        }

    }


}
