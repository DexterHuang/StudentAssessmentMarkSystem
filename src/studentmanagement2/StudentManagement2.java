package studentmanagement2;

import java.util.List;

public class StudentManagement2 {

    public static School school = new School("Bogus");
    public static InterfaceOption openMainMenuOption;
    public static InterfaceOption loadStudentDataOption;
    public static InterfaceOption registerModuleOption;
    public static InterfaceOption enterStudentSpecificOption;

    public static Interface mainMenuInterface;

    public static void main(String[] args) {
        init();
        openMainMenu();
    }

    public static void init() {
        loadStudentDataOption = new InterfaceOption("Load Student Data", new Runnable() {
            @Override
            public void run() {
                loadStudentData();
            }
        }
        );
        registerModuleOption = new InterfaceOption("Register Module", new Runnable() {
            @Override
            public void run() {
                registerModule();
            }
        });
        enterStudentSpecificOption = new InterfaceOption("Enter Student Specific", new Runnable() {
            @Override
            public void run() {
                enterStudentSpecific();
            }
        });
        mainMenuInterface = new Interface("Main Menu");
        mainMenuInterface.addOption(loadStudentDataOption);
    }

    public static void loadStudentData() {
        school.students.clear();
        String fileName = Debug.getString("Please enter the student data file name");
        List<String> l = MyReader.getLines(fileName);
        for (String str : l) {
            Student s = new Student(str);
            school.students.add(s);
        }
    }

    public static void registerModule() {
        String moduleCode = Debug.getString("Please enter Module Code for the new module.");
        if (school.getModule(moduleCode) == null) {
            String moduleName = Debug.getString("Please enter module name.");
            Module m = new Module(moduleCode, moduleName);
            school.addModule(m);
            Debug.LogInfo(m.toString() + " is successfully added!");
        } else {
            Debug.LogError("Module with this code already exist");
        }
    }

    public static void enterStudentSpecific() {
        String moduleCode = Debug.getString("for the sake of simplicity, please enter a module code for all student");

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
}
