package studentmanagement2;

import studentmanagement2.SortingHandler.SortableData;
import studentmanagement2.SortingHandler.SortingHandler;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import studentmanagement2.JSON.ClassSerializer;
import studentmanagement2.JSON.JsonObject;

public class StudentManagement2 {

    public static School school = new School("Bogus");
    public static InterfaceOption openMainMenuOption;
    public static InterfaceOption loadStudentDataOption;
    public static InterfaceOption listStudentOption;
    public static InterfaceOption registerModuleOption;
    public static InterfaceOption registerModuleForStudentOption;
    public static InterfaceOption defineTaskForModuleOption;
    public static InterfaceOption defineTaskOption;
    public static InterfaceOption saveAndExitOption;
    public static InterfaceOption inputStudentMarksOption;
    public static InterfaceOption showMarksOption;
    public static Interface mainMenuInterface;

    public static void main(String[] args) {
        load();
        init();
        openMainMenu();
    }

    public static void debug() {
        School sc = new School("huehuehue");
        Student s = new Student("a123", "dexter");
        Student s2 = new Student("a124", "huang");
        sc.students.add(s);
        sc.students.add(s2);
        String jsonString = ClassSerializer.toJSON(sc);
        Debug.Log(jsonString);
        Debug.Log(new JsonObject(jsonString).toString());
        School nsc = ClassSerializer.fromJSON(school.getClass(), jsonString);
        Debug.Log(nsc.students.get(0).name);
        Debug.Log(nsc.students.get(1).name);
    }

    public static void init() {
        openMainMenuOption = new InterfaceOption("Go to Main Menu", new Runnable() {
            @Override
            public void run() {
                openMainMenu();
            }
        });
        loadStudentDataOption = new InterfaceOption("Load Student Data", new Runnable() {
            @Override
            public void run() {
                loadStudentData();
            }
        }
        );
        listStudentOption = new InterfaceOption("List Students", new Runnable() {
            @Override
            public void run() {
                listStudents();
            }
        });
        registerModuleOption = new InterfaceOption("Register Module", new Runnable() {
            @Override
            public void run() {
                registerModule();
            }
        });
        registerModuleForStudentOption = new InterfaceOption("Register Module For Student", new Runnable() {
            @Override
            public void run() {
                registerModuleForStudent();
            }
        });

        defineTaskForModuleOption = new InterfaceOption("Define task for module", new Runnable() {
            @Override
            public void run() {
                defineTaskForModule();
            }
        });
        defineTaskOption = new InterfaceOption("Define Task", new Runnable() {
            @Override
            public void run() {
                defineTask();
            }

        });
        saveAndExitOption = new InterfaceOption("Save and Exit", new Runnable() {
            @Override
            public void run() {
                saveAndExit();
            }
        });

        inputStudentMarksOption = new InterfaceOption("Input Student Marks", new Runnable() {
            @Override
            public void run() {
                inputStudentMarks();
            }
        });
        showMarksOption = new InterfaceOption("Show Marks", new Runnable() {
            @Override
            public void run() {
                showMarks();
            }
        });
        mainMenuInterface = new Interface("Main Menu");
        mainMenuInterface.addOption(loadStudentDataOption);
        mainMenuInterface.addOption(listStudentOption);
        mainMenuInterface.addOption(registerModuleOption);
        mainMenuInterface.addOption(registerModuleForStudentOption);
        mainMenuInterface.addOption(defineTaskOption);
        mainMenuInterface.addOption(defineTaskForModuleOption);
        mainMenuInterface.addOption(inputStudentMarksOption);
        mainMenuInterface.addOption(showMarksOption);
        mainMenuInterface.addOption(saveAndExitOption);
    }

    public static void loadStudentData() {
        school.students.clear();
        String fileName = Debug.getString("Please enter the student data file name");
        List<String> l = MyReader.getLines(fileName);
        for (String str : l) {
            Student s = new Student(str);
            school.students.add(s);
        }
        Debug.LogInfo("Student files has been loaded!");
        pauseAndGoMainMenu();
    }

    public static void registerModule() {
        String moduleCode = Debug.getString("Please enter Module Code for the new module.");
        if (school.getModule(moduleCode) == null) {
            String moduleName = Debug.getString("Please enter module name.");
            Module m = new Module(moduleCode, moduleName);
            school.addModule(m);
            Debug.LogInfo(m.toString() + " is successfully added!");
            openMainMenu();
        } else {
            Debug.LogError("Module with this code already exist");
            retryOrReturnMainMenu(registerModuleOption);
        }
    }

    public static void registerModuleForStudent() {
        Module module = Debug.getFromListWithID(school.modules, "for the sake of simplicity, please enter a module code for all student");
        for (Student s : school.students) {
            s.modules.add(module.ModuleID);
            Debug.LogInfo(s.toString() + " is now enrolled in " + module.toString());
        }
        pauseAndGoMainMenu();
    }

    public static void defineTask() {
        String id = Debug.getString("Please enter ID for task.");
        String title = Debug.getString("Please enter title");
        float weight = Debug.getInt("Please enter weight.");
        int fullMark = Debug.getInt("Please enter the full mark of this task.");
        String description = Debug.getString("Please enter description for this task.");

        AssessmentTask task = new AssessmentTask(id, title, weight, fullMark, description);
        if (school.addTask(task) == false) {
            Debug.LogError("There is already a task with this ID!");
            retryOrReturnMainMenu(defineTaskOption);
        } else {

            Debug.LogInfo("task defined");
            pauseAndGoMainMenu();
        }
    }

    public static void defineTaskForModule() {
        Module m = Debug.getFromList(school.modules, "Please select module");
        String id = Debug.getFromList(m.taskIds, "Please select which task would you like to modify");
        int index = m.taskIds.indexOf(id);
        AssessmentTask task = Debug.getFromList(school.tasks, "Please select task you want to add or replace to the module");
        m.taskIds.set(index, task.getID());
        Debug.Log(task.toString() + " has been added to " + index + "rd task in " + m.ModuleID);
        pauseAndGoMainMenu();
    }

    public static void listStudents() {
        Debug.Log(Debug.generateBoxStringFromSearchable(school.students, "Student List", "Here is the list of students"));
        pauseAndGoMainMenu();
    }

    public static void inputStudentMarks() {
        int i = Debug.getInt("Do you want to enter student's mark, or use randomly generated marks? (manual input -> 0, random -> 1)");
        for (Student student : school.students) {
            for (String moduleID : student.modules) {
                Module module = school.getModule(moduleID);
                if (module != null) {
                    for (String taskID : module.taskIds) {
                        AssessmentTask task = school.getTask(taskID);
                        if (task != null) {
                            if (i > 0) {
                                int r = (int) (Math.random() * task.fullMarks);
                                student.taskMarks.put(taskID, r);
                                Debug.LogInfo(student.toString() + "'s " + task.toString() + " mark has been set to " + r);
                            } else {
                                int mark = Debug.getInt("Please enter " + student.name + "'s " + task.title + " mark");
                                student.taskMarks.put(taskID, mark);
                                Debug.LogInfo(student.toString() + "'s " + task.toString() + " mark has been set to " + mark);
                            }
                        } else {
                            Debug.LogError("Could not find task: " + taskID);
                        }
                    }
                } else {
                    Debug.LogError("Could not find module: " + moduleID);
                }
            }
        }
        pauseAndGoMainMenu();
    }

    public static void showMarks() {

        String seperator = "\t";
        Module m = Debug.getFromList(school.modules, "Please select the module you would like to view");
        List<SortableData> dataList = new ArrayList<SortableData>();
        for (Student s : school.students) {
            if (s.modules.contains(m.ModuleID)) {
                dataList.add(s.getMarkSortableData(m));
            }
        }
        int i = Debug.getInt("do you want to sort the list? (0 -> No, 1 -> Yes)", 0, 1);
        SortableData[] dataArray = dataList.toArray(new SortableData[dataList.size()]);
        if (i >= 1) {
            SortingHandler sh = new SortingHandler(dataList);
            dataArray = sh.sort();
        }
        List<String> stringList = new ArrayList<String>();
        String firstLine = "Module" + seperator + "StudentID" + seperator;
        for (String taskId : m.taskIds) {
            firstLine += taskId + seperator;
        }
        stringList.add(firstLine);

        for (SortableData sd : dataArray) {
            stringList.add(sd.data.toString());
        }

        for (String s : stringList) {
            Debug.Log(s);
        }
        pauseAndGoMainMenu();
    }

    public static void saveAndExit() {
        save();
        Debug.LogInfo("Bye bye!~");
        System.exit(0);
    }

    public static void openMainMenu() {
        mainMenuInterface.showAndGetOption().run();
    }

    public static void retryOrReturnMainMenu(InterfaceOption option) {
        Interface inter = new Interface("Do you want to try again?");
        inter.addOption(option.clone().setName("Try again"));
        inter.addOption(openMainMenuOption);
        inter.showAndGetOption().run();
    }

    public static void pauseAndGoMainMenu() {
        Debug.getString("Enter any key to go to main menu.");
        openMainMenu();
    }

    public static void load() {
        List<String> l = MyReader.getLines("save.json");
        String str = "";
        for (String s : l) {
            str += s;
        }
        if (str.length() > 0) {
            school = ClassSerializer.fromJSON(School.class, str);
            Debug.LogInfo("Save is loaded!");
        }
    }

    public static void save() {
        try {
            String jsonString = ClassSerializer.toJSON(school);
            List<String> lines = new ArrayList<String>();
            lines.add(jsonString);
            Path file = Paths.get("save.json");
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(StudentManagement2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
