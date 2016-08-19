//This project is Developed by Huang Ching
package studentmanagement2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author jack2
 */
public class SerializationHandler {

    public static Boolean saveClass(Object o, String fileName) {
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("/" + fileName + ".dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(o);
            out.close();
            fileOut.close();
            File f = new File("/" + fileName + ".dat");
            Debug.Log("data is saved in " + f.getAbsolutePath());
            return true;
        } catch (FileNotFoundException ex) {
            Debug.LogError("File not found : " + ex.getLocalizedMessage());
        } catch (Exception ex) {
            Debug.LogError("save file failed : " + ex.getLocalizedMessage());
        }
        try {
            fileOut.close();
        } catch (Exception ex) {
        }
        return false;
    }

    public static <T> T loadClass(String fileName) {
        FileInputStream fileIn = null;
        T o = null;
        try {
            fileIn = new FileInputStream("/" + fileName + ".dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            o = (T) in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException ex) {
            Debug.LogError("File not found error: " + ex.getLocalizedMessage());
        } catch (IOException ex) {
            Debug.LogError("error while loading save file: " + ex.getLocalizedMessage());
        } catch (ClassNotFoundException ex) {
            Debug.LogError("class not found error: " + ex.getLocalizedMessage());
        } finally {
            try {
                fileIn.close();
            } catch (IOException ex) {

            } catch (NullPointerException e) {

            }
        }
        return o;

    }
}
