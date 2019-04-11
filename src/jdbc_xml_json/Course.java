package jdbc_xml_json;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class Course {

    String note;
    String course;
    String title;
    String credits;
    String level;
    String restrictions;
    String comments;
    @XStreamImplicit
    List<Section> section_listing;

}
