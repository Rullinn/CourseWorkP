package org.example.courseworkp;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/tour_system");
        config.setUsername("postgres");
        config.setPassword("Admin"); // Замініть на реальний пароль
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000); // 30 секунд
        config.setLeakDetectionThreshold(60000); // Виявлення "витоку" з'єднань

        // Додаткові налаштування для стабільності:
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    //Метод для закриття пулу (викликати при завершенні роботи додатка)
    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}