package br.com.tracun.objectClasses;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;
import br.com.tracun.interfaces.IUser;
import java.net.UnknownHostException;

/**
 *
 * @author bastolu
 */
public class User implements Serializable, IUser {

    private static final long serialVersionUID = 1L;
    private String key;
    private String user;
    private String password;
    private Date creationDate;
    private InetAddress addrCliente;

    public User(String key, String user, String password) {
        this.key = key;
        this.user = user;
        this.password = password;

        try {
            addrCliente = InetAddress.getLocalHost();
            creationDate = new Date(System.currentTimeMillis());

        } catch (UnknownHostException e) {
            System.out.println("Erro UserObj: " + e.getMessage());
        }
    }

    public User(String user, String password) {
        this.key = user;
        this.user = user;
        this.password = password;

        try {
            addrCliente = InetAddress.getLocalHost();
            creationDate = new Date(System.currentTimeMillis());

        } catch (UnknownHostException e) {
            System.out.println("Erro UserObj: " + e.getMessage());
        }
    }

    public User(String key) {
        this.key = key;

        try {
            addrCliente = InetAddress.getLocalHost();
            creationDate = new Date(System.currentTimeMillis());

        } catch (UnknownHostException e) {
            System.out.println("Erro UserObj: " + e.getMessage());
        }
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public InetAddress getAddrCliente() {
        return addrCliente;
    }

    @Override
    public String print() {

        return "**********************************************\n"
                + "*Key: " + this.key
                + "\n*User: " + this.user
                + "\n*Password: " + this.password
                + "\n*Date: " + this.creationDate
                + "\n*Cliente: " + this.addrCliente
                + "\n**********************************************";
    }
}
