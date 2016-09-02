/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jack2
 */
public class MyReader {

    public static List<String> getLines(String filePath) {
        List<String> list = new ArrayList<String>();
        File file = new File(filePath);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    list.add(line);
                }
            } catch (Exception e) {
                Debug.LogError("Error druing readline operation: " + e.getLocalizedMessage());
            }
        }
        return list;
    }

    public static <T> T getArgument(String str, int index, Class type) {
        try {
            if (str.contains(" ")) {
                String[] l = str.split(" ");
                if (l.length >= index - 1) {
                    if (type.getName().contains("String") == false) {
                        return (T) type.getMethod("valueOf", new Class[]{String.class}).invoke(null, new Object[]{l[index]});
                    } else {
                        return (T) l[index];
                    }

                } else {
                    Debug.LogError("could not find argument " + index + " in " + str);
                }
            }
        } catch (NoSuchMethodException ns) {
            ns.printStackTrace();
            Debug.LogError(ns.getMessage());
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MyReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MyReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MyReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
