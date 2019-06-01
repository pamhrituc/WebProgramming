package jsp.model;

import jsp.domain.URL;
import jsp.domain.User;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    private Statement stmt;

    public DBManager() {
        connect();
    }

    public void connect() {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.gjt.mm.mysql.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/web", "root", "");
            stmt = con.createStatement();
        } catch (Exception ex) {
            System.out.println("Connection error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public User authenticate(String username, String password) {
        ResultSet rs;
        User u = null;
        System.out.println(username + " " + password);
        try {
            rs = stmt.executeQuery("select * from users where username = '" + username + "' and password = '" + password + "'");
            if (rs.next()) {
                u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public ArrayList<URL> getUserURLs(int userid) {
        ArrayList<URL> urls = new ArrayList<>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("select * from urls where userid = " + userid);
            while (rs.next()) {
                urls.add(new URL(rs.getInt("id"), rs.getInt("userid"), rs.getString("link"), rs.getInt("accessed")));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return urls;
    }

    public boolean insertURL(URL url) {
        int r = 0;
        try {
            r = stmt.executeUpdate("insert into urls values (" + url.getId() + ", " + url.getUserid() + ", '" + url.getLink() + "', " + url.getAccessed() + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r > 0;
    }

    public boolean deleteURL(int id) {
        int r = 0;
        try {
            r = stmt.executeUpdate("delete from urls where id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r > 0;
    }

    public ArrayList<URL> getTopURLs(int n) {
        ArrayList<URL> urls = new ArrayList<>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM `urls` order by accessed desc limit " + n);
            while (rs.next()) {
                urls.add(new URL(rs.getInt("id"), rs.getInt("userid"), rs.getString("link"), rs.getInt("accessed")));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return urls;
    }
}
