package jdbc_xml_json;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Maicon
 */
public class ConnectionDB {
    
    private final String url = "jdbc:postgresql://localhost/postgres";
    private final String user = "postgres";
    private final String password = "root";
 
    public Connection connect() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);  
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        createTableCourse(conn);
        createTableSection(conn);
        return conn;
    }
    
    private void createTableCourse(Connection conn) throws SQLException {
        String SQL = "drop table if exists course cascade;\n" +
                        "create table course (\n" +
                        "	course TEXT UNIQUE,\n" +
                        "	note TEXT,\n" +
                        "	title TEXT,\n" +
                        "	credits TEXT,\n" +
                        "	level TEXT,\n" +
                        "	restrictions TEXT,\n" +
                        "	PRIMARY KEY (course)\n" +
                        ");";
        Statement sttm = conn.createStatement();
        sttm.executeUpdate(SQL);
        sttm.close();
}
    
    private void createTableSection(Connection conn) throws SQLException {
        String SQL = "drop table section cascade;\n" +
                        "create table section (\n" +
                        "	course TEXT,\n" +
                        "	section_note TEXT,\n" +
                        "	section TEXT,\n" +
                        "	days TEXT,\n" +
                        "	instructor TEXT,\n" +
                        "	comments TEXT,\n" +
                        "	starthour TEXT,\n" +
                        "	endhour TEXT,\n" +
                        "	bldg TEXT,\n" +
                        "	rm TEXT,	\n" +
                        "	PRIMARY KEY (section, course),\n" +
                        "	FOREIGN KEY (course) REFERENCES course\n" +
                        ");";
        Statement sttm = conn.createStatement();
        sttm.executeUpdate(SQL);
        sttm.close();
    }
}
