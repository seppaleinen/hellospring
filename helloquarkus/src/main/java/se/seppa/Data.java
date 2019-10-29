package se.seppa;

import io.agroal.api.AgroalDataSource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.SQLException;

@ApplicationScoped
public class Data {
    private Connection connection;

    @Inject
    public Data(AgroalDataSource defaultDataSource) {
        try {
            this.connection = defaultDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String get(String id) {
        try {
            return connection.nativeSQL("select * from taable");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
