package jdbc_xml_json;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("root")
public class CourseRoot {

    @XStreamImplicit
    List<Course> course_listing;

}
