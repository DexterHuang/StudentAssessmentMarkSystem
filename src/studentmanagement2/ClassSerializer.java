/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement2;

import com.google.gson.Gson;
import java.lang.reflect.Field;

/**
 *
 * @author Dexter Huang (Huang Ching)
 */
public class ClassSerializer {

    public ClassSerializer(Object o) {
        for (Field field : o.getClass().getFields()) {
            Gson g = new Gson();
        }

    }
}
