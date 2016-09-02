/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement2;

import java.util.HashMap;

/**
 *
 * @author Dexter Huang
 */
public class dataReader {

    HashMap<String, Object> dataMap = new HashMap<String, Object>();

    public dataReader(String dataString) {
        String[] l = dataString.split(", ");
        for (String dataPair : l) {
            try {
                String[] ll = dataPair.split(": ");
                String key = ll[0];
                String value = ll[1];
                storeDataInMap(key, value);
            } catch (Exception e) {
                Debug.LogError("error while pharsing data: " + e.getLocalizedMessage());
            }
        }

    }

    public <T> T getValue(String key) {
        return (T) dataMap.get(key);
    }

    void storeDataInMap(String key, String value) {
        if (value.matches("[^\"]*\"[^\"]*")) {
            dataMap.put(key, value);
        } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            try {
                Boolean b = Boolean.parseBoolean(value);
                dataMap.put(key, b);
            } catch (Exception e) {
                Debug.LogError("cannot parse " + value + " to Boolean: " + e.getLocalizedMessage());
            }
        } else if (value.contains(".")) {
            try {
                Double d = Double.parseDouble(value);
                dataMap.put(key, d);
            } catch (Exception e) {
                Debug.LogError("cannot parse " + value + " to Double: " + e.getLocalizedMessage());
            }
        } else {
            try {
                Integer i = Integer.parseInt(value);
                dataMap.put(key, i);
            } catch (Exception e) {
                Debug.LogError("cannot parse " + value + " to Integer: " + e.getLocalizedMessage());
            }
        }
    }
}
