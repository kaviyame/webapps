package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.persistence.*;
import constants.CUSTOMER;
import constants.SHOPOWNER;
import model.Register;

public class EntryDB {
    public void doInsert(Register login, HttpServletRequest req, HttpServletResponse res) throws IOException, DataAccessException {

        HttpSession session;

        String userAttr = login.getUserType();

        Row r = new Row(CUSTOMER.TABLE);
        r.set(CUSTOMER.NAME, login.getName());
        r.set(CUSTOMER.USERNAME, login.getUsername());
        r.set(CUSTOMER.PASSWORD, login.getPassword());
        r.set(CUSTOMER.CONTACT, login.getContact());
        r.set(CUSTOMER.GENDER, login.getGender());
        r.set(CUSTOMER.AGE, login.getAge());

        DataObject d = new WritableDataObject();

        d.addRow(r);
        DataAccess.add(d);


        HttpSession oldsession = req.getSession(false);

        if (oldsession != null) oldsession.invalidate();

        session = req.getSession();
        session.setAttribute("userAttr", userAttr);

        session.setAttribute("username", login.getUsername());
        session.setAttribute("list", null);

    }

}

