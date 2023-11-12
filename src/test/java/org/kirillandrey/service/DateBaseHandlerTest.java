package org.kirillandrey.service;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.kirillandrey.config.DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DateBaseHandlerTest {
    private DateBaseHandler dbHandler;
    private DBConfig dbConfig = new DBConfigTest();
    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        dbHandler = new DateBaseHandler(dbConfig);
        // Устанавливаем соединение с временной базой данных (H2)
        connection = DriverManager.getConnection("jdbc:h2:mem:test");
    }

    @Test
    public void testDbGetConnection() throws SQLException, ClassNotFoundException {
        Connection connection = dbHandler.dbGetConnection();
        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }

    @Test
    public void testSignUpUser() throws SQLException {
        // Подготавливаем данные
        Long chatId = 12345L;
        String firstName = "John";
        String secondName = "Doe";

        // Проверяем успешную регистрацию
        String result1 = dbHandler.signUpUser(firstName, secondName, chatId);
        assertEquals("1", result1);

        // Проверяем успешную авторизацию
        String result2 = dbHandler.signUpUser(firstName, secondName, chatId);
        assertEquals("2", result2);
    }

    @Test
    public void testUserInDB() throws SQLException {
        // Подготавливаем данные
        Long chatId1 = 54321L;
        Long chatId2 = 99999L;

        // Регистрируем пользователя
        dbHandler.signUpUser("Alice", "Smith", chatId1);

        // Проверяем наличие пользователя в базе данных
        assertTrue(dbHandler.userInDB(chatId1));
        assertFalse(dbHandler.userInDB(chatId2));
    }

    @Test
    public void testGetState() throws SQLException {
        // Подготавливаем данные
        Long chatId1 = 77777L;
        Long chatId2 = 88888L;

        // Регистрируем пользователя
        dbHandler.signUpUser("Bob", "Johnson", chatId1);

        // Проверяем состояние пользователя
        assertEquals("0", dbHandler.getState(chatId1));
        assertNull(dbHandler.getState(chatId2));
    }

    @Test
    public void testSetState() throws SQLException {
        // Подготавливаем данные
        Long chatId = 99999L;

        // Регистрируем пользователя
        dbHandler.signUpUser("Eve", "Brown", chatId);

        // Устанавливаем новое состояние
        assertTrue(dbHandler.setState(chatId, "1"));

        // Проверяем установленное состояние
        assertEquals("1", dbHandler.getState(chatId));
    }
}
