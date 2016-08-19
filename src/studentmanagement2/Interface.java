/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement2;

import java.util.ArrayList;
import java.util.List;

public class Interface {

    List<InterfaceOption> options = new ArrayList<InterfaceOption>();
    String title;
    List<String> header = new ArrayList<String>();

    public Interface(String title) {
        this.title = title;
        header.add("Please choose what function you wish to use.");
    }

    public void addOption(InterfaceOption option) {
        options.add(option);
    }

    public void addHeader(String str) {
        header.add(str);
    }

    public InterfaceOption showAndGetOption() {
        for (int i = 1; i <= 10; i++) {
            Debug.Log("");
        }
        return Debug.getFromList(options, title, header);
    }
}
