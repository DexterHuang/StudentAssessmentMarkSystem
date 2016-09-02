/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement2.JSON;

import java.util.ArrayList;
import java.util.List;

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
    }

    public Object getValue(String key) {
        for (KeyPairValue kpv : pairs) {
            if (kpv.getKey().equals(key)) {
                return kpv.getValue();
            }
        }
        return null;
    }

    public List<?> getList(String name) {
        return null;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
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
                int end = column.length();
                String key = column.substring(0, firstColumn).trim();
                String value = column.substring(firstColumn + 1, end).trim();
                if (value.startsWith("{ ")) {
                    //Debug.LogInfo("nested value: " + value);
                    List<String> c = getColumns(value);
                    //IS LIST
                    if (c.size() > 0) {
                        if (c.get(0).startsWith("{")) {
                            List<JsonObject> jl = new ArrayList<JsonObject>();
                            for (String cs : c) {
                                jl.add(new JsonObject(cs));
                            }
                            pairs.add(new KeyPairValue(key, jl));
                        }
                    } else {
                        pairs.add(new KeyPairValue(key, new JsonObject(value + "")));
                    }
                } else {
                    pairs.add(new KeyPairValue(key, stringToObject(value)));
                    //Debug.Log("key: " + key + ", value: " + value);
                }
            } catch (Exception e) {
                //Debug.LogError("Error trying to parse " + column + " to jasonObject");
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
        if (str.startsWith("\"") && str.endsWith("\"")) {
            return str.substring(1, str.length() - 1);
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

}
