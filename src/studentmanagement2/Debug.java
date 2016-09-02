//This project is Developed by Huang Ching
package studentmanagement2;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Huang
 */
public class Debug {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static Console console;

    public static void Log(String str) {
        System.out.println(str);
    }

    public static void LogError(String str) {
        System.out.println(ANSI_RED + "[ERROR]: " + ANSI_RESET + str);

    }

    public static void LogInfo(String str) {
        System.out.println(ANSI_YELLOW + "[INFO]: " + str + ANSI_RESET);
    }

    public static <T> T getFromListWithID(List<?> list, String msg) {
        return getFromListWithID(list, msg, msg);
    }

    public static <T> T getFromListWithID(List<?> list, String msg, String title) {
        List<String> sl = new ArrayList<String>();
        HashMap<String, SearchableClass> idToClass = new HashMap<String, SearchableClass>();
        for (Object o : list) {
            SearchableClass sc = (SearchableClass) o;
            sl.add(ANSI_GREEN + "[" + sc.getID() + "]" + ANSI_RESET + " - " + o.toString());
            idToClass.put(sc.getID(), sc);
        }
        List<String> des = new ArrayList<String>();
        des.add(msg);
        des.add("Please choose an object from the list below(Enter the " + ANSI_GREEN + "[Index]" + ANSI_RESET + ")");
        Log(generateBoxString(sl, title, des));
        String s = getString("Please enter the Index of the object you wish to select or type '-1' to return to main menu");
        if (s.equalsIgnoreCase("-1")) {
            StudentManagement2.openMainMenu();
            return null;
        }
        if (idToClass.containsKey(s)) {
            return (T) idToClass.get(s);
        } else {
            Debug.Log("The ID you have entered does not exist, please try again");
            return getFromListWithID(list, msg);
        }
    }

    public static <T> T getFromList(List<T> list, String msg) {
        return getFromList(list, msg, msg);
    }

    public static <T> T getFromList(List<T> list, String msg, String title) {
        List<String> des = new ArrayList<String>();
        des.add(0, msg);
        return getFromList(list, title, des);
    }

    public static <T> T getFromList(List<T> list, String title, List<String> des) {
        List<String> sl = new ArrayList<String>();
        int index = 0;
        for (T o : list) {
            sl.add(ANSI_GREEN + "[" + index + "]" + ANSI_RESET + " - " + o.toString());
            index++;
        }
        des.add(0, "Please choose an object from the list below(Enter the " + ANSI_GREEN + "[Index]" + ANSI_RESET + ")");
        Log(generateBoxString(sl, title, des));
        Log("");
        int i = getInt("Please enter the Index of the object you wish to select or type '-1' to return to main menu", -1, sl.size() - 1);
        if (i < 0) {
            StudentManagement2.openMainMenu();
            return null;
        }
        try {
            return list.get(i);
        } catch (Exception e) {
            Debug.LogError("Failed to select object, please try again : " + e.getMessage());
            return getFromList(list, title, des);
        }
    }

    public static String generateBoxString(List<String> list, String title, String description) {
        List<String> des = new ArrayList<String>();
        String[] l = description.split("\n");
        for (String s : l) {
            des.add(s);
        }
        return generateBoxString(list, title, des);
    }

    public static String generateBoxString(List<String> list, String title) {
        return generateBoxString(list, title, new ArrayList<String>());
    }

    public static String generateBoxStringFromSearchable(List<?> list, String title, String description) {
        List<String> strList = new ArrayList<String>();
        for (Object o : list) {
            SearchableClass sc = (SearchableClass) o;
            strList.add(sc.toString());
        }
        return generateBoxString(strList, title, description);
    }

    public static String generateBoxString(List<String> list, String title, List<String> des) {
        title = ANSI_CYAN + "[" + title + "]" + ANSI_RESET;
        List<String> temp = new ArrayList<String>();
        temp.add(title);
        temp.addAll(list);
        int length = getLongest(temp);
        int desLength = getLongest(des);
        if (desLength > length) {
            length = desLength;
        }
        temp.remove(0);
        title = "+-" + centerString(title, length, '-') + "-+";
        String topString = "+-";
        for (int i = 1; i <= length; i++) {
            topString += "-";
        }
        topString += "-+";
        String str = title + "\n";
        for (String s : des) {
            str += "| " + centerString(s, length, ' ') + " |\n";
        }
        for (String s : temp) {
            str += "| " + s + getRepeating(length - getNoColor(s).length(), " ") + " |\n";
        }
        str += topString;
        return str;
    }

    private static String centerString(String str, int length, char charactor) {
        int strLength = getNoColor(str).length();
        int diff = length - strLength;
        String space = "";
        String remainderSpace = "";
        for (int i = 0; i < diff / 2; i++) {
            space += charactor;
        }
        if (diff % 2 > 0) {
            remainderSpace += charactor;
        }
        return space + str + space + remainderSpace;
    }

    private static String getRepeating(int amount, String str) {
        String s = "";
        for (int i = 1; i <= amount; i++) {
            s += str;
        }
        return s;
    }

    private static int getLongest(List<?> list) {
        int i = 0;
        for (Object o : list) {
            int length = getNoColor(o.toString()).length();
            if (length > i) {
                i = length;
            }
        }
        return i;
    }

    private static String getNoColor(String str) {
        return str.replaceAll("\u001B\\[[;\\d]*m", "").replaceAll("\n", "");
    }

    public static int getInt(String msg) {
        Scanner sc = new Scanner(System.in);
        System.out.println(msg);
        try {
            return sc.nextInt();
        } catch (Exception e) {
            Debug.LogError("Failed to get Int, please try again : " + e.getMessage());
            return getInt(msg);
        }
    }

    public static int getInt(String msg, int min, int max) {
        int i = getInt(msg);
        if (i < min) {
            Debug.LogError("The value must be greater or equal to " + min);
            return getInt(msg, min, max);
        } else if (i > max) {
            Debug.LogError("The value must be smaller or equal to " + max);
            return getInt(msg, min, max);
        }
        return i;
    }

    static String getString(String msg) {
        Scanner sc = new Scanner(System.in);
        System.out.println(msg);
        try {
            return sc.next();
        } catch (Exception e) {
            Debug.LogError("Failed to get Int, please try again : " + e.getMessage());
            return getString(msg);
        }
    }

    static Boolean getBoolean(String msg) {
        Debug.LogInfo("Enter {Y,Yes,True} to answer yes, all other input will return false");
        String s = getString(msg);
        return s.equalsIgnoreCase("Y") || s.equalsIgnoreCase("YES") || s.equalsIgnoreCase("True");
    }
}
