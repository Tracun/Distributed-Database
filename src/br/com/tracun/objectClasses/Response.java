package br.com.tracun.objectClasses;


import br.com.tracun.interfaces.IResponse;
import java.io.Serializable;


public class Response implements Serializable, IResponse{

    private String status;
    private Object obj;
    private static final long serialVersionUID = 1L;

    public Response(String status) {
        this.status = status;
    }
    
    public Response(String status, Object obj) {
        this.status = status;
        this.obj = obj;
    }

    public String getStatus() {
        return status;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
    
}
