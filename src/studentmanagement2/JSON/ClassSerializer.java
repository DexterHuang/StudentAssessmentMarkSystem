/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement2.JSON;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dexter Huang (Huang Ching)
 */
public class ClassSerializer {

    public static String toJSON(Object o) {
        String str = "{ ";
        try {
            Field[] fields = o.getClass().getDeclaredFields();
            int count = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                Object value = field.get(o);
                str += name + ": " + objectToJSONValue(value);
                if (count < fields.length) {
                    str += ", ";
                }
                count++;
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ClassSerializer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ClassSerializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        str += "} ";
        return str;
    }

    public static <T> T fromJSON(Class<T> Class, String jsonString) {
        Object o = null;
        try {
            o = Class.newInstance();
            Field[] fields = Class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                JsonObject jo = new JsonObject(jsonString);
                Object value = jo.getValue(field.getName());
                Object co = field.get(o);
                if (value != null) {
                    if (co instanceof List) {
                        List<?> l = (List<?>) co;

                        field.set(o, jo.getList(field.getName()));
                    } else if (o instanceof JsonObject) {
                        JsonObject inner = (JsonObject) o;
                        value = fromJSON(field.getDeclaringClass(), inner.toString());
                        field.set(o, value);
                    } else {
                        field.set(o, value);
                    }
                }
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(ClassSerializer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ClassSerializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (T) o;
    }

    private static String keyPairsToJSON(KeyPairValue[] pairs) {
        String str = "{ ";
        int count = 1;
        for (KeyPairValue pair : pairs) {
            str += "\"" + pair.getKey() + "\": " + objectToJSONValue(pair.getValue());
            if (count < pairs.length) {
                str += ", ";
            }
        }
        str += "} ";
        return str;
    }

    public static String objectToJSONValue(Object object) {
        if (object instanceof String) {
            return "\"" + (String) object + "\"";
        } else if (object instanceof Integer || object instanceof Double || object instanceof Float) {
            return object + "";
        } else if (object instanceof HashMap) {
            HashMap<?, ?> map = (HashMap<?, ?>) object;
            KeyPairValue[] pairs = hashMapToKeyPairs(map);
            return keyPairsToJSON(pairs);
        } else if (object instanceof Collection) {
            Collection c = (Collection) object;
            return collectionToJSON(c);
        } else if (object instanceof JsonObject || object instanceof KeyPairValue) {
            return object.toString();
        } else {
            return toJSON(object);
        }
    }

    private static String collectionToJSON(Collection c) {
        String str = "{ ";
        int count = 1;
        for (Object o : c) {
            str += objectToJSONValue(o);
            if (count < c.size()) {
                str += ", ";
            }
            count++;
        }
        str += "} ";
        return str;
    }

    private static KeyPairValue[] hashMapToKeyPairs(HashMap<?, ?> map) {
        KeyPairValue[] pairs = new KeyPairValue[map.keySet().size()];
        int index = 0;
        for (Object ko : map.keySet()) {
            Object vo = map.get(ko);
            String key = ko.toString();
            KeyPairValue kpv = new KeyPairValue(key, vo);
            pairs[index] = kpv;
            index++;
        }
        return pairs;
    }
}
