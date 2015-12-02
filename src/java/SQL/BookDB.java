package sql;

import SQL.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *Copyright Colin and Betsey
 * Connects to server
 * @author emccarthy3
 */
public class BookDB {
    
     public static int insert(Book book) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO User (firstName, lastName, email, title) "
                + "VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, book.getEmail());
            ps.setString(2, book.getFirstName());
            ps.setString(3, book.getLastName());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            SQLUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
     public static int delete(Book book) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM User "
                + "WHERE Email = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, book.getEmail());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            SQLUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
     public static ArrayList<Book> selectBooks() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM User";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Book> books = new ArrayList<Book>();
            while (rs.next())
            {
                Book book = new Book();
                book.setFirstName(rs.getString("FirstName"));
                book.setLastName(rs.getString("LastName"));
                book.setEmail(rs.getString("Email"));
                book.setTitle(rs.getString("BookTitle"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            SQLUtil.closeResultSet(rs);
            SQLUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }    
     
}