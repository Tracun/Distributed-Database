package br.com.tracun.objectClasses;


import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author bastolu
 */
public class Request implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String key;
    private String operation;
    private Object obj;
    private ArrayList objList;
    private int toServerPort = 0;
    private boolean isBackup = false;
    private String folder;
    
    public Request(String operation, String folder, Object obj) {
        this.operation = operation;
        this.folder = folder;
        this.obj = obj;
    }
    
    public Request(String operation, Object obj, String key) {
        this.operation = operation;
        this.obj = obj;
        this.key = key;
    }
    
    public Request(String operation, ArrayList objList) {
        this.operation = operation;
        this.objList = objList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    
    public String getOperation() {
        return operation;
    }

    public Object getObj() {
        return obj;
    }

    public ArrayList getObjList() {
        return objList;
    }

    public void setObjList(ArrayList objList) {
        this.objList = objList;
    }

    public int getToServerPort() {
        return toServerPort;
    }

    public void setToServerPort(int toServerPort) {
        this.toServerPort = toServerPort;
    }

    public boolean isIsBackup() {
        return isBackup;
    }

    public void setIsBackup(boolean isBackup) {
        this.isBackup = isBackup;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

}
