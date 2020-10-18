package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER =
            "SELECT * FROM users WHERE column = ?";
    private static final String UPDATE_USER =
            "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?";
    private static final String DELETE_USER =
            "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_USERS =
            "SELECT * FROM users";
    private static final String DELETE_ALL_USERS =
            "DELETE FROM users WHERE 1=1";

    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User read(int userId) {
        return readByColumn("id",userId+"");
    }

    private User readByColumn(String column, String value){
        String sql = READ_USER.replace("column",column);
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement pStat = conn.prepareStatement(sql);
            pStat.setString(1,value);
            ResultSet result = pStat.executeQuery();
            if(result.next()){
                return new User()
                        .setEmail(result.getString("email"))
                        .setId(result.getInt("id"))
                        .setPassword(result.getString("password"))
                        .setUserName(result.getString("username"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement pStat = conn.prepareStatement(UPDATE_USER);
            pStat.setString(1,user.getEmail());
            pStat.setString(2,user.getUserName());
            pStat.setString(3,hashPassword(user.getPassword()));
            pStat.setInt(4,user.getId());
            pStat.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement pStat = conn.prepareStatement(DELETE_USER);
            pStat.setInt(1,userId);
            pStat.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public User[] findAll() {
        User[] users = new User[0];
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement pStat = conn.prepareStatement(FIND_ALL_USERS);
            ResultSet result = pStat.executeQuery();
            while(result.next()){
                User u = new User()
                        .setEmail(result.getString("email"))
                        .setId(result.getInt("id"))
                        .setPassword(result.getString("password"))
                        .setUserName(result.getString("username"));
                users = addToArray(u,users);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }

    public void deleteAll() {
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement pStat = conn.prepareStatement(DELETE_ALL_USERS);
            pStat.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
