/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc_xml_json;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maicon
 */
public class Persistencia {
    
    private final Connection conn;

    public Persistencia() throws SQLException {
        ConnectionDB connection = new ConnectionDB();
        conn = connection.connect();
    }  
    
    public void insert(CourseRoot courses) throws SQLException {
        List<Course> courseList = courses.course_listing;        
        for(Course course : courseList) {
            insertCourse(course);
            for(Section section : course.section_listing) {
                insertSection(section, course.course);
            }
        }
    }
    
    private void insertCourse(Course course) throws SQLException {
        String values[] = {course.course,
                           course.note,
                           course.title,
                           course.credits,
                           course.level,
                           course.restrictions};
        String newValues[] = new String[values.length];
        for(int i = 0; i<values.length; i++) {
            if (values[i] == null) {
                values[i] = "";
            }
            String newValue = values[i].replace("'", " ");
            newValue = "'"+newValue+"'"; 
            newValues[i] = newValue;
        }
        String insertCourse = "INSERT INTO course VALUES("+ String.join(",", newValues) +")";
        Statement sttm = conn.createStatement();
        sttm.executeUpdate(insertCourse);
        sttm.close();
    }
    
    private void insertSection(Section section, String course) throws SQLException {
        String values[] = {course,
                           section.section_note,
                           section.section,
                           section.days,
                           section.instructor,
                           section.comments,
                           section.hours.start,
                           section.hours.end,
                           section.bldg_and_rm.bldg,
                           section.bldg_and_rm.rm};
        String newValues[] = new String[values.length];
        for(int i = 0; i<values.length; i++) {
            if (values[i] == null) {
                values[i] = "";
            }
            String newValue = values[i].replace("'", " ");
            newValue = "'"+newValue+"'"; 
            newValues[i] = newValue;
        }
        String SQL = "INSERT INTO section VALUES("+ String.join(",", newValues) +")";
        Statement sttm = conn.createStatement();
        sttm.executeUpdate(SQL);
        sttm.close();
    }
    
    public CourseRoot persiste(String where) throws SQLException {
        String SQL = "SELECT * FROM course NATURAL INNER JOIN section WHERE "+where;
        Statement sttm = conn.createStatement();
        ResultSet result = sttm.executeQuery(SQL);
        CourseRoot courseRoot = new CourseRoot();
        courseRoot.course_listing = new ArrayList<>();
        String aux = "gambiarra";
        while(result.next()) {            
            if(!aux.equals(result.getString("course"))) {
                Course course = new Course();
                course.course = result.getString("course");
                aux = course.course;
                course.note = result.getString("note");
                course.title = result.getString("title");
                course.credits = result.getString("credits");
                course.level = result.getString("level");
                course.restrictions = result.getString("restrictions");
                List<Section> section_listing = persisteSection(course.course, where);            
                course.section_listing = section_listing;
                courseRoot.course_listing.add(course);
            }   
        }        
        return courseRoot;        
    }
    
    public List<Section> persisteSection(String course, String where) throws SQLException {
        List<Section> section_listing = new ArrayList<>();
        String SQL = "SELECT * FROM course NATURAL INNER JOIN section WHERE course = '"+course+"' and "+where;
        Statement sttm = conn.createStatement();
        ResultSet result = sttm.executeQuery(SQL);
        while(result.next()) {
            Section section = new Section();
            section.section_note = result.getString("section_note");
            section.section = result.getString("section");
            section.days = result.getString("days");
            section.instructor = result.getString("instructor");
            Hours hours = new Hours();
            hours.start = result.getString("starthour");
            hours.end = result.getString("endhour");
            section.hours = hours;
            Bldg_and_rm bldg_and_rm = new Bldg_and_rm();
            bldg_and_rm.bldg = result.getString("bldg");
            bldg_and_rm.rm = result.getString("rm");
            section.bldg_and_rm = bldg_and_rm;            
            section_listing.add(section);            
        }
        return section_listing;
    }
}
