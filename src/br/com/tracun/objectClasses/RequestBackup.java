/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tracun.objectClasses;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.control.ListView;

/**
 *
 * @author lab1504p2
 */
public class RequestBackup implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String operation;
    private ArrayList obj;
    private boolean copy;

    public RequestBackup(String operation, ArrayList obj) {
        this.operation = operation;
        this.obj = obj;
        this.copy = true;
    }
    
    public String getOperation() {
        return operation;
    }

    public Object getObj() {
        return obj;
    }

    public boolean isCopy() {
        return copy;
    }

    public void setCopy(boolean copy) {
        this.copy = copy;
    }
    
}
