import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException{

        Connection connection = DriverManager.getConnection (
                "jdbc:mysql://127.0.0.1:3306/", //localhost might be instead of 127.0.0.1
                "root",                        // .../databaseName - to get a certain DB
                "2419839"
        );
        PreparedStatement statement=
                connection.prepareStatement("create database if not exists abcdef character set 'utf8'");
        statement.executeUpdate();

        statement.execute("use abcdef");
        statement.execute("create table if not exists users" +
                "(id int primary key auto_increment," +
                "name varchar (45))");

       // statement.execute("insert into users (name) values ('kokos');"); //filling from psvm

       save(connection, "vasya");  // - it is used only once
        save(connection, "petya");  // otherwise it will create those users at any run of programe
        save(connection, "tomat");

        System.out.println(findAll(connection));
        System.out.println("---------------------");

        System.out.println(findAllUsers(connection));
    }

    public static void save(Connection connection, String name) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(
                "insert into users (name) values (?);");
        statement.setString(1, name);
        statement.executeUpdate();
    }

    public static List<String> findAll (Connection connection) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(
                "select *from users;" );
        ResultSet resultSet = statement.executeQuery();
        List<String> strings = new ArrayList<String>();
        while (resultSet.next()){
            int id= resultSet.getInt(1); // might be "id" instead of 1
            String name =resultSet.getString(2); // might be "name"
            String obj = id+" "+name;
            strings.add(obj);
        }
        return strings;
    }

    public static List<User> findAllUsers(Connection connection) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(
          "select *from users;");
        ResultSet resultSet = statement.executeQuery();
        List<User> users = new ArrayList<User>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            users.add(new User(id, name));
        }
        return users;
    }
}
