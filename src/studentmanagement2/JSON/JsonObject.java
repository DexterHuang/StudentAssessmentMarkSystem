/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement2.JSON;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import studentmanagement2.Debug;

/**
 *
 * @author Huang
 */
public class JsonObject {

    List<KeyPairValue> pairs = new ArrayList<KeyPairValue>();

    public JsonObject(String jsonString) {
        jsonString = jsonString.trim();
        // Debug.LogInfo("trying to parse: " + jsonString);
        pairs = getKeyPairValues(jsonString);
        if (pairs.size() <= 0) {
        }
    }

    public Object getValue(String key) {
        for (KeyPairValue kpv : pairs) {
            if (kpv.getKey().equals(key)) {
                return kpv.getValue();
            }
        }
        return null;
    }

    public List<?> getList(Class Class, String name) {
        Object o = getValue(name);

        if (o instanceof Collection) {
            List<Object> l = (ArrayList<Object>) o;
            List<Object> ll = new ArrayList<Object>();
            for (Object jo : l) {
                if (Class.getName().contains("String")) {
                    String str = (String) jo;
                    str = (String) stringToObject(str);;
                    ll.add(str);
                } else {
                    Object no = ClassSerializer.fromJSON(Class, jo.toString());
                    //Debug.Log(jo.toString() + "  to  " + Class.getName() + "  RESULT : " + no);
                    ll.add(no);
                }
            }
            return ll;
        } else {
            Debug.LogError(name + " is not a collection it is " + o.getClass().toString());
        }
        return new ArrayList<Class>();
    }

    public JsonObject() {
    }

    private String getFirstInBetween(String str, char c1, char c2) {
        String string = "";
        int c1Count = 0;
        boolean first = true;
        str = str.substring(str.indexOf(c1), str.length());
        for (char c : str.toCharArray()) {
            if (c == c1) {
                c1Count++;
                if (first) {
                    first = false;
                    continue;
                }
            } else if (c == c2) {
                c1Count--;
            }
            if (c1Count <= 0) {
                break;
            } else {
                string += c;
            }
        }
        return string;
    }

    private List<KeyPairValue> getKeyPairValues(String jsonString) {

        jsonString = jsonString + "";
        List<KeyPairValue> pairs = new ArrayList<KeyPairValue>();
        for (String column : getColumns(jsonString)) {
            try {
                //  Debug.Log(Debug.ANSI_GREEN + "parsing column: " + column + Debug.ANSI_RESET);
                int firstColumn = column.indexOf(": ");
                if (firstColumn < 0) {
                    continue;
                }
                int end = column.length();
                String key = column.substring(0, firstColumn).trim();
                key = (String) stringToObject(key);
                String value = column.substring(firstColumn + 1, end).trim();
                //Debug.LogInfo(key + "  :  " + value + " ==> " + stringToObject(value).toString());
                pairs.add(new KeyPairValue(key, stringToObject(value)));
            } catch (Exception e) {
                Debug.LogError("Error trying to parse " + column + " to jasonObject");
                e.printStackTrace();
            }
        }

        return pairs;
    }

    public List<String> getColumns(String str) {

        //  Debug.Log(Debug.ANSI_CYAN + "analizing string1: " + str + Debug.ANSI_RESET);
        if (str.startsWith("{") && str.endsWith("}")) {
            str = str.substring(1, str.length() - 1);
        }
        // Debug.Log(Debug.ANSI_CYAN + "analizing string2: " + str + Debug.ANSI_RESET);
        List<String> list = new ArrayList<String>();
        int c1Count = 0;
        String read = "";
        for (char c : str.toCharArray()) {
            if (c == '{') {
                c1Count++;
                read += c;
            } else if (c == '}') {
                c1Count--;
                read += c;
            } else if (c1Count <= 0) {
                if (c == ',') {
                    if (read.trim().equals("") == false) {
                        list.add(read.trim());
                        read = "";
                    }
                } else {
                    read += c;
                }
            } else {
                read += c;
            }

        }
        if (read.trim().equals("") == false) {
            list.add(read.trim());
        }
        return list;
    }

    private Object stringToObject(String str) {
        str.trim();
        if (str.startsWith("{") && str.contains(":")) {
            List<Object> ol = new ArrayList<Object>();
            List<String> l = getColumns(str);
            if (l.get(0).startsWith("{")) {
                for (String s : l) {
                    //Debug.LogInfo(s);
                    Object ns = stringToObject(s);
                    ol.add(ns);
                }
                return ol;
            }
            return new JsonObject(str);

        } else if (str.startsWith("{")) {
            List<String> l = new ArrayList<String>();
            for (String s : getColumns(str)) {
                l.add(s.trim());
            }
            return l;
        } else if (str.startsWith("\"")) {
            str = str.replace("}", "");
            str.trim();
            while (str.startsWith("\"") && str.endsWith("\"")) {
                str.trim();
                str = str.substring(1, str.length() - 1);
            }
        }
        return str;
    }

    @Override
    public String toString() {
        String str = "{ ";
        int index = 1;
        for (KeyPairValue pair : pairs) {
            str += pair.toString();
            if (index < pairs.size()) {
                str += ", ";
            }
            index++;
        }
        str += "} ";
        return str;
    }

    public HashMap<?, ?> getHashMap(Class c, Class c2, String name) {
        if (getValue(name) instanceof JsonObject) {
            JsonObject jo = (JsonObject) getValue(name);
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            for (KeyPairValue pair : jo.pairs) {
                map.put(pair.getKey(), parseObject(c2, pair.getValue()));
            }
            return (HashMap<?, ?>) map;
        }
        return (HashMap<?, ?>) new HashMap<>();

    }

    public Object parseObject(Class<?> c, String str) {
        if (c == Integer.class) {
            return Integer.parseInt(str);
        }

        return str;
    }
}
