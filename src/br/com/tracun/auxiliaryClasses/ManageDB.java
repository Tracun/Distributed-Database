package br.com.tracun.auxiliaryClasses;

import br.com.tracun.objectClasses.Response;
import br.com.tracun.objectClasses.Note;
import br.com.tracun.objectClasses.User;
import br.com.tracun.objectClasses.Contact;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author bastolu
 */
public class ManageDB {

    static final String NOTE_OBJ = "objectClasses.Note";
    static final String USER_OBJ = "objectClasses.User";
    static final String CONTACT_OBJ = "objectClasses.Contact";
    static final String NOTE_PATH = "Notes\\";
    static final String USER_PATH = "Users\\";
    static final String CONTACT_PATH = "Contacts\\";
    static FileOutputStream fos = null;
    static ObjectOutputStream oos = null;
    static String fileKey = null;
    static File file = null;

    public static int saveObj(File file, Object obj) {
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);

            // write object to file
            oos.writeObject(obj);
            oos.flush();
            // closing resources
            oos.close();
            fos.close();
            return 0;
        } catch (IOException e) {
            System.out.println("ManageDB Says: Erro - saveObj - " + e.getMessage());
            return -1;
        }
    }

    public static Response consultAll(String PATH) {

        System.out.println("ManageDB Says: ConsultAll Manage");
        file = new File(PATH);
        File[] listFiles;
        listFiles = file.listFiles();
        System.out.println("listFiles consultAll - " + listFiles.length);

        return new Response(Status.CODE200, listFiles);
    }

    public static Response create(Object obj, String PATH) {

        switch (obj.getClass().getName()) {
            case NOTE_OBJ:
                return createNote(obj, PATH + NOTE_PATH);
            case USER_OBJ:
                return createUser(obj, PATH + USER_PATH);
            case CONTACT_OBJ:
                return createContact(obj, PATH + CONTACT_PATH);
            default:
                return new Response(Status.CODE500);
        }
    }

    public static Response update(Object obj, String PATH) {

        switch (obj.getClass().getName()) {
            case NOTE_OBJ:
                return updateNote(obj, PATH + NOTE_PATH);
            case USER_OBJ:
                return updateUser(obj, PATH + USER_PATH);
            case CONTACT_OBJ:
                return updateContact(obj, PATH + CONTACT_PATH);
            default:
                return new Response(Status.CODE500);
        }
    }

    public static Response delete(Object obj, String PATH) {

        switch (obj.getClass().getName()) {
            case NOTE_OBJ:
                return deleteNote(obj, PATH + NOTE_PATH);
            case USER_OBJ:
                return deleteUser(obj, PATH + USER_PATH);
            case CONTACT_OBJ:
                return updateContact(obj, PATH + CONTACT_PATH);
            default:
                return new Response(Status.CODE500);
        }
    }

    public static Response consult(Object obj, String PATH) {

        switch (obj.getClass().getName()) {
            case NOTE_OBJ:
                return consultNote(obj, PATH + NOTE_PATH);
            case USER_OBJ:
                return consultUser(obj, PATH + USER_PATH);
            case CONTACT_OBJ:
                return consultContact(obj, PATH + CONTACT_PATH);
            default:
                return new Response(Status.CODE500);
        }
    }

    static Response createNote(Object obj, String PATH) {

        Note note = (Note) obj;
        fileKey = note.getKey();
        file = new File(PATH + fileKey + ".ser");

        if (file.exists()) {
            return new Response(Status.CODE409);
        } else if (note.getData().equals("") || note.getKey().equals("")) {
            return new Response(Status.CODE408);
        } else {
            //Persiste o objeto
            int status = saveObj(file, note);
            if (status != -1) {
                return new Response(Status.CODE200);
            }
        }
        return new Response(Status.CODE500);
    }

    static Response createNoteBkp(ArrayList list, String PATH) {

        for (Object object : list) {

            createNote(object, PATH);
        }

        return new Response(Status.CODE200);
    }

    static Response createUserBkp(ArrayList list, String PATH) {

        for (Object object : list) {

            createUser(object, PATH);
        }

        return new Response(Status.CODE200);
    }

    static Response createContactBkp(ArrayList list, String PATH) {

        for (Object object : list) {

            createContact(object, PATH);
        }

        return new Response(Status.CODE200);
    }

    static Response updateNote(Object obj, String PATH) {

        Note note = (Note) obj;
        fileKey = note.getKey();
        file = new File(PATH + fileKey + ".ser");

        if (!file.exists()) {
            return new Response(Status.CODE404);
        } else if (note.getKey().equals("") || note.getData().equals("")) {
            return new Response(Status.CODE408);
        } else {

            //Persiste o objeto
            int status = saveObj(file, note);
            if (status != -1) {
                return new Response(Status.CODE200);
            }
        }
        return new Response(Status.CODE500);
    }

    static Response deleteNote(Object obj, String PATH) {

        try {
            Note note = (Note) obj;
            fileKey = note.getKey();
            file = new File(PATH + fileKey + ".ser");

            if (!file.exists()) {
                return new Response(Status.CODE404);
            } else if (note.getKey().equals("")) {
                return new Response(Status.CODE408);
            } else {
                file.delete();
                return new Response(Status.CODE200);
            }
        } catch (Exception e) {
            System.out.println("ManageDB Says: Erro - deleteData - " + e.getMessage());
        }
        return new Response(Status.CODE500);
    }

    static Response consultNote(Object obj, String PATH) {

        try {
            Note note = (Note) obj;
            fileKey = note.getKey();
            file = new File(PATH + fileKey + ".ser");

            if (!file.exists()) {
                return new Response(Status.CODE404);
            } else if (note.getKey().equals("")) {
                return new Response(Status.CODE408);
            } else {

                ObjectInputStream ois;
                try (FileInputStream is = new FileInputStream(file)) {
                    ois = new ObjectInputStream(is);
                    return new Response(Status.CODE200, ois.readObject());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ManageDB Says: Erro - consultData - " + e.getMessage());
        }
        return new Response(Status.CODE500);
    }

    static Response createUser(Object obj, String PATH) {

        User user = (User) obj;
        fileKey = user.getKey();
        file = new File(PATH + fileKey + ".ser");

        if (file.exists()) {
            return new Response(Status.CODE409);
        } else if (user.getUser().equals("") || user.getPassword().equals("")) {
            return new Response(Status.CODE408);
        } else {
            //Persiste o objeto
            int status = saveObj(file, user);
            if (status != -1) {
                return new Response(Status.CODE200);
            }
        }
        return new Response(Status.CODE500);
    }

    static Response updateUser(Object obj, String PATH) {

        User user = (User) obj;
        fileKey = user.getKey();
        file = new File(PATH + fileKey + ".ser");
        File newFile = new File(PATH + user.getUser() + ".ser");

        if (!file.exists()) {
            return new Response(Status.CODE404);
        } else if (user.getUser().equals("") || user.getPassword().equals("")) {
            return new Response(Status.CODE408);
        } else {

            if (!file.renameTo(newFile)) {
                return new Response(Status.CODE404);
            }

            user.setKey(user.getUser());
            //Persiste o objeto
            int status = saveObj(newFile, user);
            if (status != -1) {
                return new Response(Status.CODE200);
            }
        }
        return new Response(Status.CODE500);
    }

    static Response deleteUser(Object obj, String PATH) {

        try {
            User req = (User) obj;
            fileKey = req.getKey();
            file = new File(PATH + fileKey + ".ser");

            if (!file.exists()) {
                return new Response(Status.CODE404);
            } else if (req.getKey().equals("")) {
                return new Response(Status.CODE408);
            } else {
                file.delete();
                return new Response(Status.CODE200);
            }
        } catch (Exception e) {
            System.out.println("ManageDB Says: Erro - deleteUser - " + e.getMessage());
            e.printStackTrace();
        }
        return new Response(Status.CODE500);
    }

    static Response consultUser(Object obj, String PATH) {

        try {
            User user = (User) obj;
            fileKey = user.getKey();
            file = new File(PATH + fileKey + ".ser");

            if (!file.exists()) {
                return new Response(Status.CODE404);
            } else if (user.getKey().equals("")) {
                return new Response(Status.CODE408);
            } else {

                ObjectInputStream ois;
                try (FileInputStream is = new FileInputStream(file)) {
                    ois = new ObjectInputStream(is);
                    return new Response(Status.CODE200, ois.readObject());
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ManageDB Says: Erro - consultUser - " + e.getMessage());
            e.printStackTrace();
        }
        return new Response(Status.CODE500);
    }

    static Response createContact(Object obj, String PATH) {

        Contact contact = (Contact) obj;
        fileKey = contact.getKey();
        file = new File(PATH + fileKey + ".ser");

        if (file.exists()) {
            return new Response(Status.CODE409);
        } else if (contact.getKey().equals("") || contact.getName().equals("") || contact.getPhone().equals("")) {
            return new Response(Status.CODE408);
        } else {
            //Persiste o objeto
            int status = saveObj(file, contact);
            if (status != -1) {
                return new Response(Status.CODE200);
            }
        }
        return new Response(Status.CODE500);
    }

    static Response updateContact(Object obj, String PATH) {

        Contact contact = (Contact) obj;
        fileKey = contact.getKey();
        file = new File(PATH + fileKey + ".ser");

        if (!file.exists()) {
            return new Response(Status.CODE404);
        } else if (contact.getName().equals("") || contact.getPhone().equals("")) {
            return new Response(Status.CODE408);
        } else {

            //Persiste o objeto
            int status = saveObj(file, contact);
            if (status != -1) {
                return new Response(Status.CODE200);
            }
        }
        return new Response(Status.CODE500);
    }

    static Response deleteContact(Object obj, String PATH) {

        try {
            Contact req = (Contact) obj;
            fileKey = req.getKey();
            file = new File(PATH + fileKey + ".ser");

            if (!file.exists()) {
                return new Response(Status.CODE404);
            } else if (req.getKey().equals("")) {
                return new Response(Status.CODE408);
            } else {
                file.delete();
                return new Response(Status.CODE200);
            }
        } catch (Exception e) {
            System.out.println("ManageDB Says: Erro - deleteContact - " + e.getMessage());
            e.printStackTrace();
        }
        return new Response(Status.CODE500);
    }

    static Response consultContact(Object obj, String PATH) {

        try {
            Contact contact = (Contact) obj;
            fileKey = contact.getKey();
            file = new File(PATH + fileKey + ".ser");

            if (!file.exists()) {
                return new Response(Status.CODE404);
            } else if (contact.getKey().equals("")) {
                return new Response(Status.CODE408);
            } else {

                ObjectInputStream ois;
                try (FileInputStream is = new FileInputStream(file)) {
                    ois = new ObjectInputStream(is);
                    return new Response(Status.CODE200, ois.readObject());
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ManageDB Says: Erro - consultContact - " + e.getMessage());
            e.printStackTrace();
        }
        return new Response(Status.CODE500);
    }
}
