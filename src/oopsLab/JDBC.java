package oopsLab;

import java.sql.*;

public class JDBC {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/csbs",
                "root",
                ""
            );

            Statement st = con.createStatement();

            // Create TABLE, not DATABASE
            st.executeUpdate("DROP TABLE IF EXISTS students");

           st.executeUpdate("CREATE TABLE students (name VARCHAR(50),rollno INT PRIMARY KEY,marks INT)"
            );

            st.executeUpdate("INSERT INTO students VALUES('kumudini', 3242, 87)");
            st.executeUpdate("INSERT INTO students VALUES('keerthana', 3249, 93)");
              PreparedStatement  pt=con.prepareStatement("INSERT INTO students VALUES(?,?,?)");
              pt.setString(1,"Hashmitha");
              pt.setInt(2, 3258);
              pt.setInt(3, 76);
              pt.executeUpdate();
              
              

  
            
            pt=con.prepareStatement("UPDATE students set marks=? where rollno=?");
            pt.setInt(1, 95);
            pt.setInt(2, 3242);
            pt.executeUpdate();
            
            pt=con.prepareStatement("DELETE FROM students where rollno=?");
            pt.setInt(1, 3249);
            pt.executeUpdate();
            
            ResultSet rs = st.executeQuery("SELECT * FROM students");
            while (rs.next()) {
                System.out.println(
                    rs.getString(1) + " " +
                    rs.getInt(2) + " " +
                    rs.getInt(3)
                );
            }
            
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
