package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.enteties.Course;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CourseBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(long code, String name) {
        Course course = new Course(code, name);
        entityManager.persist(course);
    }

    public List<Course> getAllCourses() {
        // remember, maps to: “SELECT c FROM Courses c ORDER BY c.name”
        return (List<Course>) entityManager.createNamedQuery("getAllCourses").getResultList();
    }

    public Course findCourse(long code) {
        return entityManager.find(Course.class, code);
    }
}
