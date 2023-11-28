package org.kirillandrey.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.kirillandrey.config.DBConfig;
import org.kirillandrey.config.DBConst;

/**
 * Класс {@code DateBaseHandler} реализует взаимодействие с базой данных MySQL.
 * Он включает в себя методы для управления данными пользователей, состояниями, настройками и уведомлениями.
 * Класс зависит от объекта {@code DBConfig} для настройки соединения с базой данных.
 * Два атрибута:
 * - {@code dbConfig} - конфигурация для основной и тестовой баз данных.
 * - {@code dbConnection} - соединение с базой данных.
 */
public class DateBaseHandler {
    private DBConfig dbConfig;
    private Connection dbConnection;

    /**
     * Конструирует объект {@code DateBaseHandler} с предоставленным {@code DBConfig}.
     *
     * @param _dbConfig_ конфигурация базы данных
     */
    public DateBaseHandler(DBConfig _dbConfig_) {
        this.dbConfig = new DBConfig().clone(_dbConfig_);
    }

    /**
     * Конструирует объект {@code DateBaseHandler} с использованием стандартной конфигурации {@code DBConfig}.
     */
    public DateBaseHandler() {
        this(new DBConfig());
    }

    /**
     * Конструирует объект {@code DateBaseHandler} с предоставленным {@code DBConfig} и {@code Connection}.
     *
     * @param dbConfig       конфигурация базы данных
     * @param dbConnection  соединение с базой данных
     */
    public DateBaseHandler(DBConfig dbConfig, Connection dbConnection) {
        this.dbConfig = dbConfig;
        this.dbConnection = dbConnection;
    }

    /**
     * Создает или возвращает текущее соединение с базой данных.
     *
     * @return объект класса {@code Connection}
     * @throws ClassNotFoundException если класс драйвера JDBC не найден
     * @throws SQLException           если происходит ошибка доступа к базе данных
     */
    public Connection dbGetConnection() throws ClassNotFoundException, SQLException {
        if (dbConnection != null && !dbConnection.isClosed()) {
            return dbConnection;
        }

        String connectionString = "jdbc:mysql://" + dbConfig.getdbHost() + ":" + dbConfig.getdbPort() + "/" + dbConfig.getdbName() + "?useSSL=false";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbConfig.getdbUser(), dbConfig.getdbPass());
        return dbConnection;
    }

    /**
     * Регистрирует пользователя с указанным идентификатором чата.
     *
     * @param chatId идентификатор чата пользователя
     * @return "1", если пользователь успешно зарегистрирован, "2", если пользователь уже существует,
     *         "0", если произошла ошибка
     */
    public String signUpUser(Long chatId) {
        if (!userInDB(chatId)) {
            String insert = "INSERT INTO " + dbConfig.getdbTableName() + "(" + DBConst.USER_CHATID +
                    "," + DBConst.USER_STATE + "," + DBConst.USER_SETTINGS + ")" + "VALUES(?,?,?)";

            try (Connection connection = dbGetConnection();
                 PreparedStatement prSt = connection.prepareStatement(insert)) {
                prSt.setString(1, chatId.toString());
                prSt.setString(2, "0");
                prSt.setString(3, new SettingJson().toJson());

                prSt.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                return "0";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return "1";
        }
        return "2";
    }

    /**
     * Проверяет, существует ли пользователь с указанным идентификатором чата в основной таблице базы данных.
     *
     * @param chatId идентификатор чата пользователя
     * @return true, если пользователь существует, false в противном случае
     */
    public boolean userInDB(Long chatId) {
        String selectQuery = "SELECT " + DBConst.USER_ID + " FROM " + dbConfig.getdbTableName() + " WHERE " + DBConst.USER_CHATID + "=?";

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, String.valueOf(chatId));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Проверяет, существует ли пользователь с указанным идентификатором чата в таблице уведомлений.
     *
     * @param chatId идентификатор чата пользователя
     * @return true, если пользователь существует, false в противном случае
     */
    public boolean userInNotificationTable(Long chatId) {
        String selectQuery = "SELECT " + DBConst.USER_ID + " FROM " + dbConfig.getdbNotificTableName() + " WHERE " + DBConst.USER_CHATID + "=?";

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, String.valueOf(chatId));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Получает состояние пользователя с указанным идентификатором чата.
     *
     * @param chatId идентификатор чата пользователя
     * @return состояние пользователя или null, если произошла ошибка
     */
    public String getState(Long chatId) {
        String selectQuery = "SELECT " + DBConst.USER_STATE + " FROM " + dbConfig.getdbTableName() + " WHERE " + DBConst.USER_CHATID + "=?";

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, chatId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(DBConst.USER_STATE);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Устанавливает состояние пользователя с указанным идентификатором чата.
     *
     * @param chatId   идентификатор чата пользователя
     * @param newState новое состояние
     * @return true, если состояние успешно обновлено, false в противном случае
     */
    public boolean setState(Long chatId, String newState) {
        String updateQuery = "UPDATE " + dbConfig.getdbTableName() + " SET " + DBConst.USER_STATE + "=? WHERE " + DBConst.USER_CHATID + "=?";

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newState);
            preparedStatement.setString(2, chatId.toString());

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Устанавливает настройки пользователя с указанным идентификатором чата.
     *
     * @param chatId   идентификатор чата пользователя
     * @param settings объект класса {@code SettingJson}
     * @return true, если настройки успешно обновлены, false в противном случае
     */
    public boolean setSettings(Long chatId, SettingJson settings) {
        String updateQuery = "UPDATE " + dbConfig.getdbTableName() + " SET " + DBConst.USER_SETTINGS + "=? WHERE " + DBConst.USER_CHATID + "=?";

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, settings.toJson());
            preparedStatement.setString(2, chatId.toString());

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получает настройки пользователя с указанным идентификатором чата.
     *
     * @param chatId идентификатор чата пользователя
     * @return объект класса {@code SettingJson} или null, если произошла ошибка
     */
    public SettingJson getSettings(Long chatId) {
        String selectQuery = "SELECT " + DBConst.USER_SETTINGS + " FROM " + dbConfig.getdbTableName() + " WHERE " + DBConst.USER_CHATID + "=?";

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, chatId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String settingsJson = resultSet.getString(DBConst.USER_SETTINGS);
                    return SettingJson.fromJson(settingsJson);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Очищает тестовые данные в базе данных.
     */
    public void clearTest() {
        String truncateQuery1 = "TRUNCATE TABLE " + dbConfig.getdbTableName();
        String truncateQuery2 = "TRUNCATE TABLE " + dbConfig.getdbNotificTableName();
        try {
            Connection connection = dbGetConnection();
            try (PreparedStatement preparedStatement1 = connection.prepareStatement(truncateQuery1)) {
                preparedStatement1.executeUpdate();
            }
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(truncateQuery2)) {
                preparedStatement2.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Добавляет пользователя в таблицу уведомлений с указанным временем.
     *
     * @param chatid идентификатор чата пользователя
     * @param time   время уведомлений
     * @return true, если операция выполнена успешно, false в противном случае
     */
    public boolean addUserInNotificationTable(Long chatid, String time) {
        if (!userInNotificationTable(chatid)) {
            String insertQuery = "INSERT INTO " + dbConfig.getdbNotificTableName() + "(" + DBConst.USER_CHATID + ", " +
                    DBConst.USER_TIME + ")" + " VALUES(?,?)";
            try (Connection connection = dbGetConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, chatid.toString());
                preparedStatement.setString(2, time);

                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            String updateQuery = "UPDATE " + dbConfig.getdbNotificTableName() +
                    " SET " + DBConst.USER_TIME + "=? WHERE " + DBConst.USER_CHATID + "=?";

            try (Connection connection = dbGetConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, time);
                preparedStatement.setString(2, chatid.toString());

                int rowsUpdated = preparedStatement.executeUpdate();
                return rowsUpdated > 0;
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * Удаляет пользователя из таблицы уведомлений с указанным идентификатором чата.
     *
     * @param chatid идентификатор чата пользователя
     * @return true, если операция выполнена успешно, false в противном случае
     */
    public boolean removeUserFromNotificationTable(Long chatid) {
        if (userInNotificationTable(chatid)){
        String deleteQuery = "DELETE FROM " + dbConfig.getdbNotificTableName() + " WHERE " + DBConst.USER_CHATID + "=?";

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, chatid.toString());

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
        return false;
    }

    /**
     * Получает данные времени для уведомлений из таблицы уведомлений.
     *
     * @return {@code HashMap} с парами "идентификатор чата"-"время уведомления"
     */
    public HashMap<String, String> getTimesForAlerts() {
        HashMap<String, String> result = new HashMap<>();

        String selectQuery = "SELECT " + DBConst.USER_CHATID + ", " + DBConst.USER_TIME +
                " FROM " + dbConfig.getdbNotificTableName();

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String chatId = resultSet.getString(DBConst.USER_CHATID);
                String time = resultSet.getString(DBConst.USER_TIME);

                result.put(chatId, time);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }
}
