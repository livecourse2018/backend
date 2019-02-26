package com.education.backend.db;

import com.education.backend.db.model.User;
import com.education.backend.resources.vos.CourseVO;
import com.education.backend.resources.vos.SignupRequestVO;
import com.education.backend.db.model.CourseRegistration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBDao implements DBClient {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://";
    private static final String DB_SERVER_URL = "localhost:3306";
    private static final String DB_USER_NAME = "root";
    private static final String DB_PASSWORD = "!QA2ws3ed";
    private static final String DB_NAME = "education";
    private static final String TABLE_USER = "user";
    private static final String TABLE_COURSE = "course";
    private static final String TABLE_REGISTRATION = "registration";

    private Connection connection;
    private Properties properties;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @Override
    public User loginWithPassword(String email, String password) {
        prepareDatabase();
        String sqlQuery = "SELECT first_name, last_name, display_name, email FROM user WHERE email='"
                + email
                + "' and password='"
                + password
                + "';";
        return getUserInfo(sqlQuery);
    }

    @Override
    public boolean signupWithPassword(SignupRequestVO signupRequestVO) {
        prepareDatabase();
        String sqlQuery = "INSERT INTO user (first_name, last_name, display_name, email, password) VALUES ('"
                + signupRequestVO.getFirstName()
                + "', '"
                + signupRequestVO.getLastName()
                + "', '"
                + signupRequestVO.getDisplayName()
                + "', '"
                + signupRequestVO.getEmail()
                + "', '"
                + signupRequestVO.getPassword()
                + "');";
        return runUpdate(preparedStatement, sqlQuery, true);
    }

    @Override
    public CourseRegistration getRegisteredCourses(String email) {
        prepareDatabase();
        String sqlQuery = "SELECT course_id FROM registration WHERE email='"
                + email
                + "';";
        return new CourseRegistration(email, getRegistration(sqlQuery));
    }

    @Override
    public boolean registerCourse(String email, String courseId) {
        prepareDatabase();
        String sqlQuery = "INSERT INTO registration (email, course_id) VALUES ('"
                + email
                + "', '"
                + courseId
                + "');";
        return runUpdate(preparedStatement, sqlQuery, true);
    }

    @Override
    public boolean uploadCourse(CourseVO courseVO) {
        prepareDatabase();
        String sqlQuery = "INSERT INTO course (course_id, course_name, teacher, start_date, end_date, start_time, end_time, description) VALUES ('"
                + courseVO.getCourseId()
                + "', '"
                + courseVO.getCourseName()
                + "', '"
                + courseVO.getTeacher()
                + "', '"
                + courseVO.getStartDate()
                + "', '"
                + courseVO.getEndDate()
                + "', '"
                + courseVO.getStartTime()
                + "', '"
                + courseVO.getEndTime()
                + "', '"
                + courseVO.getDescription()
                + "');";
        return runUpdate(preparedStatement, sqlQuery, true);
    }

    @Override
    public boolean getUserCourseRegistrationStatus(String email, String courseId) {
        prepareDatabase();
        String sqlQuery = "SELECT * FROM registration WHERE email='"
                + email
                + "' and course_id='"
                + courseId
                + "';";
        return checkUserRegisteredCourse(sqlQuery);
    }

    @Override
    public User findUser(String email) {
        prepareDatabase();
        String sqlQuery = "SELECT first_name, last_name, display_name, email FROM user WHERE email='"
                + email
                + "';";
        return getUserInfo(sqlQuery);
    }

    private Connection getConnection(boolean dbExisted) {
        try {
            Class.forName(JDBC_DRIVER);
            String jdbcConnection = dbExisted ? JDBC_URL + DB_SERVER_URL + "/" + DB_NAME : JDBC_URL + DB_SERVER_URL;
            return DriverManager.getConnection(jdbcConnection, getProperties());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Properties getProperties() {
        properties = new Properties();
        properties.setProperty("user", DB_USER_NAME);
        properties.setProperty("password", DB_PASSWORD);
        return properties;
    }

    private void dbClose(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet, Properties properties) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
            if (properties != null) {
                properties.clear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet runQuery(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    private boolean runUpdate(PreparedStatement preparedStatement, String sqlQuery, boolean dbExisted) {
        try {
            connection = getConnection(dbExisted);
            preparedStatement = connection.prepareStatement(sqlQuery);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbClose(connection, preparedStatement, resultSet, properties);
        }
        return false;
    }

    private void prepareDatabase() {
        createDatabase();
        createUserTable();
        createCourseTable();
        createRegistrationTable();
    }

    private void createDatabase() {
        String sqlCreateDb = "CREATE DATABASE IF NOT EXISTS `" + DB_NAME + "`;";
        runUpdate(preparedStatement, sqlCreateDb, false);
    }

    private void createUserTable() {
        String sqlCreateUserTable = "CREATE TABLE IF NOT EXISTS " + TABLE_USER
                + "  (email           VARCHAR(500),"
                + "   password        VARCHAR(500),"
                + "   last_name       VARCHAR(500),"
                + "   first_name      VARCHAR(500),"
                + "   display_name    VARCHAR(500),"
                + "   PRIMARY KEY (email))";
        runUpdate(preparedStatement, sqlCreateUserTable, true);
    }

    private void createCourseTable() {
        String sqlCreateCourseTable = "CREATE TABLE IF NOT EXISTS " + TABLE_COURSE
                + "  (course_id       VARCHAR(500),"
                + "   course_name     VARCHAR(500),"
                + "   teacher         VARCHAR(500),"
                + "   start_date      DATE,"
                + "   end_date        DATE,"
                + "   start_time      TIME,"
                + "   end_time        TIME,"
                + "   description     VARCHAR(2000),"
                + "   PRIMARY KEY (course_id))";
        runUpdate(preparedStatement, sqlCreateCourseTable, true);
    }

    private void createRegistrationTable() {
        String sqlCreateCourseTable = "CREATE TABLE IF NOT EXISTS " + TABLE_REGISTRATION
                + "  (email            VARCHAR(500),"
                + "   course_id        VARCHAR(500),"
                + "   PRIMARY KEY (email))";
        runUpdate(preparedStatement, sqlCreateCourseTable, true);
    }

    private User getUserInfo(String sqlQuery) {
        try {
            connection = getConnection(true);
            preparedStatement = connection.prepareStatement(sqlQuery);
            resultSet = runQuery(preparedStatement);

            if (resultSet.first()) {
                return new User(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("display_name"),
                        resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbClose(connection, preparedStatement, resultSet, properties);
        }
        return null;
    }

    private List<String> getRegistration(String sqlQuery) {
        List<String> registeredCourses = new ArrayList<>();

        try {
            connection = getConnection(true);
            preparedStatement = connection.prepareStatement(sqlQuery);
            resultSet = runQuery(preparedStatement);

            while (resultSet.next()) {
                registeredCourses.add(resultSet.getString("course_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbClose(connection, preparedStatement, resultSet, properties);
        }
        return registeredCourses;
    }

    private boolean checkUserRegisteredCourse(String sqlQuery) {
        try {
            connection = getConnection(true);
            preparedStatement = connection.prepareStatement(sqlQuery);
            resultSet = runQuery(preparedStatement);

            if (resultSet.first()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbClose(connection, preparedStatement, resultSet, properties);
        }
        return false;
    }
}
