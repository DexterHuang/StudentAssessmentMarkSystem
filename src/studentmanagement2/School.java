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

    public School(String schoolName) {
        this.schoolName = schoolName;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public boolean removeStudent(int studentID) {
        for (Student s : students) {
            if (s.studentID == studentID) {
                students.remove(s);
                return true;
            }
        }
        return false;
    }

    public boolean removeModule(int moduleID) {
        for (Module m : modules) {
            if (m.ModuleID == moduleID) {
                modules.remove(m);
                return true;
            }
        }
        return false;
    }

    public Student getStudent(int id) {
        for (Student s : students) {
            if (s.studentID == id) {
                return s;
            }
        }
        return null;
    }

    public Module getModule(int id) {
        for (Module m : modules) {
            if (m.ModuleID == id) {
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

    public boolean isModuleFull(int moduleID) {
        Module m = getModule(moduleID);
        if (m == null) {
            Debug.Log("No module with this ID registered.");
            return false;
        }
        return m.isFull();
    }

    public boolean isModuleOfferedInSemester(int moduleID, int semesterID) {
        Module m = getModule(moduleID);
        if (m == null) {
            Debug.Log("No module with this ID registered");
            return false;
        }
        return m.isOfferedInSemester(semesterID);

    }

    public boolean registerModule(int studentID, int moduleID, int semesterID) {
        Student student = getStudent(moduleID);
        if (student != null) {
            Module module = getModule(moduleID);
            if (module != null) {
                if (student.tookModule(module) == false) {
                    student.takeModule(module);
                }
            } else {
                Debug.Log("No module with this ID registered");
            }
        } else {
            Debug.Log("No Student with this ID registered");
        }
        return false;
    }

    public void listModuleTaken(int studentID) {
        Student student = getStudent(studentID);
        if (student != null) {
            for (Module m : student.modules) {
                Debug.Log(m.toString());
            }
        } else {
            Debug.Log("No student with this ID registered");
        }
    }
}
