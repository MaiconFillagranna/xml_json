package jdbc_xml_json;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Maicon
 */
public class Main {
    
    public static void main(String[] args) {                
        try {
            XStream xStream = new XStream();
            xStream.processAnnotations(new Class[]{CourseRoot.class, Course.class});

            CourseRoot courses = (CourseRoot) xStream.fromXML(new FileReader("D:/Documents/NetBeansProjects/uwm.xml"));
            
            Persistencia persistencia = new Persistencia();
            persistencia.insert(courses);
            
            // Base de dados para DTO
            String where = JOptionPane.showInputDialog("SELECT *\nFROM course\nNATURAL INNER JOIN section\nWHERE:");;
            CourseRoot courseRoot = persistencia.persiste(where);            

            // DTO para JSON
            String jsonFile = new Gson().toJson(courseRoot, CourseRoot.class);
            
            File file = new File("json.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonFile);
            fileWriter.flush();
            fileWriter.close();
        }
        catch(Exception e){
            int a = 1;
        }
    }
    
    
    
    
    
}
