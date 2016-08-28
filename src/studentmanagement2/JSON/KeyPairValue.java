/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement2.JSON;

/**
 *
 * @author Huang
 */
public class KeyPairValue {

    private String key;

    private Object value;

    public KeyPairValue(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public <T> T getValue() {
        return (T) value;
    }

    @Override
    public String toString() {
        if (value instanceof JsonObject) {
            return key + ": " + value.toString();
        }
        return key + ": " + ClassSerializer.objectToJSONValue(value);
    }
}
