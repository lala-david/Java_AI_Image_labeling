
import java.sql.*;
import java.util.ArrayList;

public class DB {
    Connection conn = null;
    ResultSet rs = null;
    Statement st = null;

    public DB()  {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ftpinfo","lala",
                    "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 회원 추가
    public void insertMember(FTP_INFO FTP_INFO) {
        try {
            st = conn.createStatement();
            int stmt = st.executeUpdate(
                    "insert into ftp values ('" + FTP_INFO.Host + "', '" + FTP_INFO.Port + "', '" + FTP_INFO.name + "', '" + FTP_INFO.password + "', '" + FTP_INFO.Server + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 회원 목록 출력
    public ArrayList<FTP_INFO> readMember() {
        ArrayList<FTP_INFO> arr = new ArrayList<FTP_INFO>();
        System.out.println(arr);
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select * from ftp;");
            while (rs.next()) {
                arr.add(new FTP_INFO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arr;
    }

 

    // 회원 검색
    public ArrayList<FTP_INFO> searchMember(String content) {
        ArrayList<FTP_INFO> arr = new ArrayList<FTP_INFO>();
        System.out.println(arr);
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select * from ftp where name like '%" + content + "%';");
            while (rs.next()) {
                arr.add(new FTP_INFO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arr;
    }
}
