/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement2;

import java.util.ArrayList;
import java.util.List;

public class School extends SearchableClass {

    String schoolName;
    List<Module> modules = new ArrayList<Module>();

    List<Student> students = new ArrayList<Student>();

    List<AssessmentTask> tasks = new ArrayList<AssessmentTask>();

    public School() {
        modules = new ArrayList<Module>();

        students = new ArrayList<Student>();

        tasks = new ArrayList<AssessmentTask>();
    }

    public School(String schoolName) {
        this.schoolName = schoolName;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public boolean addTask(AssessmentTask task) {
        if (getTask(task.getID()) == null) {
            tasks.add(task);
            return true;
        } else {
            return false;
        }
    }

    public AssessmentTask getTask(String id) {
        for (AssessmentTask task : tasks) {
            if (task.id.equalsIgnoreCase(id)) {
                return task;
            }
        }
        return null;
    }

    public boolean removeStudent(String studentID) {
        for (Student s : students) {
            if (s.studentID == null ? studentID == null : s.studentID.equals(studentID)) {
                students.remove(s);
                return true;
            }
        }
        return false;
    }

    public boolean removeModule(String moduleID) {
        for (Module m : modules) {
            if (m.ModuleID == null ? moduleID == null : m.ModuleID.equals(moduleID)) {
                modules.remove(m);
                return true;
            }
        }
        return false;
    }

    public Student getStudent(String id) {
        for (Student s : students) {
            if (s.studentID == null ? id == null : s.studentID.equals(id)) {
                return s;
            }
        }
        return null;
    }

    public Module getModule(String id) {
        for (Module m : modules) {
            if (m.ModuleID == null ? id == null : m.ModuleID.equals(id)) {
                return m;
            }
        }
        return null;
    }

    public void listModules() {
        for (Module m : modules) {
            Debug.Log(m.toString());
        }
    }

    public void listStudents() {
        for (Student s : students) {
            Debug.Log(s.toString());
        }
    }
}
