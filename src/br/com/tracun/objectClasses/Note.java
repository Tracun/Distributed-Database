package br.com.tracun.objectClasses;


import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import br.com.tracun.interfaces.IData;

public class Note implements Serializable, IData {

    private String key;
    private String data;
    private InetAddress addrCliente;
    private Date creationDate;
    private static final long serialVersionUID = 1L;

    public Note(String key, String data) {
        this.key = key;
        this.data = data;

        try {
            addrCliente = InetAddress.getLocalHost();
            creationDate = new Date(System.currentTimeMillis());

        } catch (Exception e) {
        }
    }

    public Note(String key) {
        this.key = key;
        this.data = "";

        try {
            addrCliente = InetAddress.getLocalHost();
            creationDate = new Date(System.currentTimeMillis());

        } catch (Exception e) {
        }
    }

    public String getKey() {
        return key;
    }

    public String getData() {
        return data;
    }

    public InetAddress getAddrCliente() {
        return addrCliente;
    }

    public Date getDate() {
        return creationDate;
    }

    @Override
    public String print() {

        return "**********************************************\n"
                + "*Key: " + this.key
                + "\n*Data: " + this.data
                + "\n*Date: " + this.creationDate
                + "\n*Cliente: " + this.addrCliente
                + "\n**********************************************";
    }

}
