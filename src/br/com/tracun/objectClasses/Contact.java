/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tracun.objectClasses;

import br.com.tracun.interfaces.IContact;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 *
 * @author lab1504p2
 */
public class Contact implements IContact, Serializable{

    private String key;
    private String name;
    private String phone;
    private String mail;
    private InetAddress addrCliente;
    private Date creationDate;
    private static final long serialVersionUID = 1L;

    public Contact(String key, String name, String phone, String mail){
        
        this.key = key;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        
        try {
            addrCliente = InetAddress.getLocalHost();
            creationDate = new Date(System.currentTimeMillis());

        } catch (Exception e) {
        }
    }
    
    public Contact(String key){
        this.key = key;
        
        try {
            addrCliente = InetAddress.getLocalHost();
            creationDate = new Date(System.currentTimeMillis());

        } catch (UnknownHostException e) {
            System.out.println("Erro UserObj: " + e.getMessage());
        }
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String print() {

        return "**********************************************\n"
                + "*Key: " + this.key
                + "\n*Name: " + this.name
                + "\n*Phone: " + this.phone
                + "\n*Mail: " + this.mail
                + "\n*Date: " + this.creationDate
                + "\n*Cliente: " + this.addrCliente
                + "\n**********************************************";
    }
}
