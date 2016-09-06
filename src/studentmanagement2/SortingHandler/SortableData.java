/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement2.SortingHandler;

/**
 *
 * @author Huang
 */
public class SortableData {

    public Integer sortValue;
    public Object data;

    public SortableData(Object data, Integer sortValue) {
        this.sortValue = sortValue;
        this.data = data; // ss
    }
}
