
package dal;

import context.DBContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Part;
import model.Account;
import model.Blog;
import model.Category_Blog;
//import model.Setting;

/**
 *
 * @author HP
 */
public class BlogDAO {

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

    public ArrayList<Blog> getBlogs() throws IOException, SQLException {
        ArrayList<Blog> blogs = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "Select b.*, u.name as author, c.name as category_name \n"
                    + "from blog as b\n"
                    + "Inner join users as u on b.username = u.username\n"
                    + "inner join category_blog c on b.category_id = c.id \n"
                    + "order by b.date desc";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Blog b = new Blog();
                b.setBlog_id(rs.getInt("blog_id"));
                b.setTitle(rs.getString("title"));
                b.setBrief(rs.getString("brief"));
                //start
                Blob blob = rs.getBlob("img");
                String noImage = "";
                if (blob != null) {

                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                    inputStream.close();
                    outputStream.close();
                    b.setImg(base64Image);
                } else {
                    b.setImg(noImage);
                }
                //end
                b.setDate(rs.getDate("date"));
                b.setStatus(rs.getBoolean("status"));
                b.setDescribe(rs.getString("describe"));
                b.setAuthor(rs.getString("author"));
                Category_Blog c = new Category_Blog();
                c.setId(rs.getInt("category_id"));
                c.setName(rs.getString("category_name"));
                b.setCategory(c);
                blogs.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return blogs;
    }

    public ArrayList<Blog> getActiveBlogs() throws IOException, SQLException {
        ArrayList<Blog> blogs = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "Select b.*, u.name as author, c.name as category_name\n"
                    + "from blog as b\n"
                    + "Inner join users as u on b.username = u.username\n"
                    + "inner join category_blog c on b.category_id = c.id\n"
                    + "where b.status = 1\n"
                    + "order by b.date desc;";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Blog b = new Blog();
                b.setBlog_id(rs.getInt("blog_id"));
                b.setTitle(rs.getString("title"));
                b.setBrief(rs.getString("brief"));
                //start
                Blob blob = rs.getBlob("img");
                String noImage = "";
                if (blob != null) {

                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                    inputStream.close();
                    outputStream.close();
                    b.setImg(base64Image);
                } else {
                    b.setImg(noImage);
                }
                //end
                b.setDate(rs.getDate("date"));
                b.setStatus(rs.getBoolean("status"));
                b.setDescribe(rs.getString("describe"));
                b.setAuthor(rs.getString("author"));
                Category_Blog c = new Category_Blog();
                c.setId(rs.getInt("category_id"));
                c.setName(rs.getString("category_name"));
                b.setCategory(c);
                blogs.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return blogs;
    }

    public Blog getBlog(int id) throws IOException, SQLException {
        try {
            connection = dbc.getConnection();
            String sql = "Select b.*, u.name as author, c.name as category_name\n"
                    + "from blog as b\n"
                    + "Inner join users as u on b.username = u.username\n"
                    + "inner join category_blog c on b.category_id = c.id \n"
                    + "where b.blog_id = ?\n"
                    + "order by b.date desc";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Blog b = new Blog();
                b.setBlog_id(rs.getInt("blog_id"));
                b.setTitle(rs.getString("title"));
                Blob blob = rs.getBlob("img");
                b.setBrief(rs.getString("brief"));
                String noImage = "";
                if (blob != null) {

                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                    inputStream.close();
                    outputStream.close();
                    b.setImg(base64Image);
                } else {
                    b.setImg(noImage);
                }
                b.setDate(rs.getDate("date"));
                b.setStatus(rs.getBoolean("status"));
                b.setDescribe(rs.getString("describe"));
                b.setFeatured(rs.getBoolean("featured"));
                b.setAuthor(rs.getString("author"));
                Category_Blog c = new Category_Blog();
                c.setId(rs.getInt("category_id"));
                c.setName(rs.getString("category_name"));
                b.setCategory(c);
                return b;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

    public ArrayList<Category_Blog> getCategories() throws SQLException {
        ArrayList<Category_Blog> categories = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "SELECT * FROM category_blog;";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category_Blog c = new Category_Blog();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
//                Setting s = new Setting();
//                s.setId(rs.getInt("setting_id"));
//                c.setSetting(s);
                c.setStatus(rs.getBoolean("status"));
                categories.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return categories;
    }

    public ArrayList<Blog> getBlogsByCategory(int id) throws IOException, SQLException {
        ArrayList<Blog> blogs = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "SELECT b.*, c.name as category_name FROM blog as b\n"
                    + "inner join category_blog as c on b.category_id = c.id\n"
                    + "where category_id = ? and b.status = 1\n"
                    + "order by date desc ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Blog b = new Blog();
                b.setBlog_id(rs.getInt("blog_id"));
                b.setTitle(rs.getString("title"));
                Blob blob = rs.getBlob("img");
                b.setBrief(rs.getString("brief"));
                String noImage = "";
                if (blob != null) {

                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                    inputStream.close();
                    outputStream.close();
                    b.setImg(base64Image);
                } else {
                    b.setImg(noImage);
                }
                b.setDate(rs.getDate("date"));
                b.setDescribe(rs.getString("describe"));
                Category_Blog c = new Category_Blog();
                c.setId(rs.getInt("category_id"));
                c.setName(rs.getString("category_name"));
                b.setCategory(c);
                blogs.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return blogs;
    }

    public ArrayList<Blog> search(String content) throws IOException, SQLException {
        ArrayList<Blog> blogs = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "Select b.*, u.name as author, c.name as category_name \n"
                    + "from blog as b\n"
                    + "Inner join users as u on b.username = u.username\n"
                    + "inner join category_blog c on b.category_id = c.id \n"
                    + "WHERE title LIKE ? ORDER BY date desc";
            String keyword = "%" + content + "%";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, keyword);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Blog b = new Blog();
                b.setBlog_id(rs.getInt("blog_id"));
                b.setTitle(rs.getString("title"));
                Blob blob = rs.getBlob("img");
                b.setBrief(rs.getString("brief"));
                String noImage = "";
                if (blob != null) {

                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                    inputStream.close();
                    outputStream.close();
                    b.setImg(base64Image);
                } else {
                    b.setImg(noImage);
                }
                b.setDate(rs.getDate("date"));
                b.setStatus(rs.getBoolean("status"));
                b.setDescribe(rs.getString("describe"));
                b.setAuthor(rs.getString("author"));
                Category_Blog c = new Category_Blog();
                c.setId(rs.getInt("category_id"));
                c.setName(rs.getString("category_name"));
                b.setCategory(c);
                blogs.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return blogs;
    }

    public ArrayList<Blog> getBlogsByFeatured() throws IOException, SQLException {
        ArrayList<Blog> blogs = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "Select * from blog where featured = 1 and status = 1;";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Blog b = new Blog();
                b.setBlog_id(rs.getInt("blog_id"));
                b.setTitle(rs.getString("title"));
                Blob blob = rs.getBlob("img");
                b.setBrief(rs.getString("describe"));
                String noImage = "";
                if (blob != null) {

                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                    inputStream.close();
                    outputStream.close();
                    b.setImg(base64Image);
                } else {
                    b.setImg(noImage);
                }
                b.setDate(rs.getDate("date"));
                b.setDescribe(rs.getString("describe"));
                blogs.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return blogs;
    }

    public void hideBlog(int blog_id, int status) throws SQLException {
        try {
            connection = dbc.getConnection();
            String sql = "UPDATE `doctris_system`.`blog`\n"
                    + "SET\n"
                    + "`status` = ?\n"
                    + "WHERE `blog_id` = ?;";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, status);
            stm.setInt(2, blog_id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public ArrayList<Blog> sortBlogs(String type) throws IOException, SQLException {
        ArrayList<Blog> blogs = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "Select b.*, u.name as author, c.name as category_name \n"
                    + "from blog as b\n"
                    + "Inner join users as u on b.username = u.username\n"
                    + "inner join category_blog c on b.category_id = c.id \n"
                    + "order by " + type + " , b.date desc";
            PreparedStatement stm = connection.prepareStatement(sql);
            //stm.setString(2, type); // b.sortBlogs("lamtt3", "category_id");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Blog b = new Blog();
                b.setBlog_id(rs.getInt("blog_id"));
                b.setTitle(rs.getString("title"));
                Blob blob = rs.getBlob("img");
                String noImage = "";
                if (blob != null) {

                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                    inputStream.close();
                    outputStream.close();
                    b.setImg(base64Image);
                } else {
                    b.setImg(noImage);
                }
                b.setDate(rs.getDate("date"));
                b.setDescribe(rs.getString("describe"));
                b.setStatus(rs.getBoolean("status"));
                b.setAuthor(rs.getString("author"));
                b.setBrief(rs.getString("brief"));
                Category_Blog c = new Category_Blog();
                c.setId(rs.getInt("category_id"));
                c.setName(rs.getString("category_name"));
                b.setCategory(c);
                blogs.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return blogs;
    }

    public void addBlog(int category_id, String title, InputStream img, String brief,
            String describe, Boolean featured, String username, Boolean status) throws SQLException {
        try {
            connection = dbc.getConnection();
            String sql = "INSERT INTO `doctris_system`.`blog`\n"
                    + "(\n"
                    + "`category_id`,\n"
                    + "`title`,\n"
                    + "`img`,\n"
                    + "`brief`,\n"
                    + "`describe`,\n"
                    + "`date`,\n"
                    + "`featured`,\n"
                    + "`username`,\n"
                    + "`status`)\n"
                    + "VALUES\n"
                    + "(\n"
                    + "? ,\n"
                    + "? ,\n"
                    + "? ,\n"
                    + "? ,\n"
                    + "? ,\n"
                    + "curdate(),\n"
                    + "? ,\n"
                    + "? ,\n"
                    + "? );";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, category_id);
            stm.setString(2, title);
            stm.setBlob(3, img);
            stm.setString(4, brief);
            stm.setString(5, describe);
            stm.setBoolean(6, featured);
            stm.setString(7, username);
            stm.setBoolean(8, status);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void updateBlog(int category_id, String title, String brief,
            String describe, Boolean featured, Boolean status, int blog_id) throws IOException, SQLException {
        try {
            connection = dbc.getConnection();
            String sql = "UPDATE `doctris_system`.`blog`\n"
                    + "SET\n"
                    + "`category_id` = ?,\n"
                    + "`title` = ?,\n"
                    + "`brief` = ?,\n"
                    + "`describe` = ?,\n"
                    + "`featured` = ?,\n"
                    + "`status` = ?\n"
                    + "WHERE `blog_id` = ?;";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, category_id);
            stm.setString(2, title);
            stm.setString(3, brief);
            stm.setString(4, describe);
            stm.setBoolean(5, featured);
            stm.setBoolean(6, status);
            stm.setInt(7, blog_id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void updateImage(int blog_id, Part img) throws IOException, SQLException {

        try {
            connection = dbc.getConnection();
            String sql = "UPDATE `doctris_system`.`blog` SET `img` = ? WHERE `blog_id` = ?;";
            PreparedStatement stm = connection.prepareStatement(sql);
            InputStream image = img.getInputStream();
            stm.setBlob(1, image);
            stm.setInt(2, blog_id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public ArrayList<Account> getAuthors() throws SQLException {
        ArrayList<Account> authors = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "Select * from users where role_id = 5";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account a = new Account();
                a.setUsername(rs.getString("username"));
                a.setName(rs.getString("name"));
                authors.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return authors;
    }

    public ArrayList<Blog> getListByPage(ArrayList<Blog> list,
            int start, int end) {
        ArrayList<Blog> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }
}
