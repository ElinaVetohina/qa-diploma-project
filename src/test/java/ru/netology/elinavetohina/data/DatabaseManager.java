package ru.netology.elinavetohina.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import ru.netology.elinavetohina.entities.CreditRequestEntity;
import ru.netology.elinavetohina.entities.OrderEntity;
import ru.netology.elinavetohina.entities.PaymentEntity;

import java.sql.Connection;
import java.sql.DriverManager;

public final class DatabaseManager {
    private static final QueryRunner runner = new QueryRunner();
    private static final String dbUrl = System.getProperty("db.url");
    private static final String dbUser = System.getProperty("db.user");
    private static final String dbPassword = System.getProperty("db.password");
    private static final Connection conn = getConn();

    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection(
                dbUrl,
                dbUser,
                dbPassword
        );
    }

    @SneakyThrows
    public static CreditRequestEntity getCreditRequestEntityData() {
        var codeSQL = "SELECT * FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        return runner.query(conn, codeSQL, new BeanHandler<>(CreditRequestEntity.class));
    }

    @SneakyThrows
    public static OrderEntity getOrderEntityData() {
        var codeSQL = "SELECT * FROM order_entity ORDER BY created DESC LIMIT 1;";
        return runner.query(conn, codeSQL, new BeanHandler<>(OrderEntity.class));
    }

    @SneakyThrows
    public static PaymentEntity getPaymentsEntityData() {
        var codeSQl = "SELECT * FROM payment_entity ORDER BY created DESC LIMIT 1;";
        return runner.query(conn, codeSQl, new BeanHandler<>(PaymentEntity.class));
    }

    @SneakyThrows
    public static long getRowAmountFromCreditRequestEntityTable() {
        var codeSQl = "SELECT count(*) FROM credit_request_entity;";
        return runner.query(conn, codeSQl, new ScalarHandler<>());
    }

    @SneakyThrows
    public static long getRowAmountFromOrderEntityTable() {
        var codeSQl = "SELECT count(*) FROM order_entity;";
        return runner.query(conn, codeSQl, new ScalarHandler<>());
    }

    @SneakyThrows
    public static long getRowsAmountFromPaymentEntityTable() {
        var codeSQL = "SELECT count(*) FROM payment_entity;";
        return runner.query(conn, codeSQL, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void cleanDatabase() {
        runner.execute(conn, "DELETE FROM credit_request_entity;");
        runner.execute(conn, "DELETE FROM order_entity;");
        runner.execute(conn, "DELETE FROM payment_entity;");
    }

}
