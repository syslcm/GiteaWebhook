package org.james.persistance;

import oracle.jdbc.OraclePreparedStatement;
import org.james.model.ContentObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Component
@Service
public class IntoDb {

    private DataSource dataSource;
    private String giteaEvent;
    private String giteaDelivery;
//"SMARTQUOTE"."ISEQ$$_67981".nextval
    public int insert(ContentObject contentObject) {
        int eventId=0;
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        OraclePreparedStatement ps0;
        try {
            String generatedColumns[] = {"ID"};
            ps0 = (OraclePreparedStatement) jt.getDataSource().getConnection().prepareStatement("insert into git_event (ID,X_GITEA_EVENT,X_GITEA_DELIVERY) values (ISEQ$$_67981.NEXTVAL,?,?) ",generatedColumns); //Statement.RETURN_GENERATED_KEYS
            ps0.setNString(1, this.getGiteaEvent());
            ps0.setNString(2,  this.getGiteaDelivery());
            ps0.executeUpdate();
            ResultSet rset = ps0.getGeneratedKeys();
            rset.next();
            // The generated order id
            long orderId = rset.getLong(1);
            eventId = Integer.parseInt(String.valueOf(orderId));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            eventId = -2;
        }
        return eventId;

    }

    public String insert2(ContentObject contentObject){
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        String newid = "0";
        Statement sta = null;
        String sql = "insert into git_event (ID,CREATE_DATE) values (SEQ01.NEXTVAL,sysdate)";
        ResultSet resultSet = null;
        try {
            sta = jt.getDataSource().getConnection().createStatement();

            sta.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            resultSet = sta.getGeneratedKeys();
            if (resultSet.next()) {
                newid = resultSet.getObject(Statement.RETURN_GENERATED_KEYS).toString();
            } else {
                System.out.println("No RETURN_GENERATED_KEYS ");
            }
            if (sta != null) sta.close();
            if (resultSet != null) resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return newid;

    }


    public DataSource getDataSource() {
        return dataSource;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getGiteaEvent() {
        return giteaEvent;
    }

    public void setGiteaEvent(String giteaEvent) {
        this.giteaEvent = giteaEvent;
    }

    public String getGiteaDelivery() {
        return giteaDelivery;
    }

    public void setGiteaDelivery(String giteaDelivery) {
        this.giteaDelivery = giteaDelivery;
    }
}
