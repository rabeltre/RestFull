package org.rest.app.database;


import org.rest.app.model.Message;
import org.rest.app.model.Profile;

import java.util.HashMap;
import java.util.Map;

public class DatabaseClass {
    private static Map<Long, Message> messages = new HashMap<Long, Message>();
    private static Map<String, Profile> profiles = new HashMap<String, Profile>();


    public DatabaseClass()
    {

    }
    public static Map<Long, Message> getMessages()
    {
        return  messages;
    }
    public  static Map<String, Profile> getProfiles ()
    {
        return profiles;
    }
}
