package org.rest.app.service;


import org.rest.app.database.DatabaseClass;
import org.rest.app.exception.DataNotFoundException;
import org.rest.app.model.Message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class MessageService {

    public MessageService() {
        messages.put(1L, new Message(1L, "Hola esto es un mesaje", "Rafael Beltre"));
        messages.put(2L, new Message(2L, "Este es el segundo mensaje", "Arturo Castro"));
        messages.put(3L, new Message(3L, "Mensaje del destino", "Julio Baez"));
        messages.put(4L, new Message(4L, "Maria la del barrio", "Maria Mercedes"));
        messages.put(5L, new Message(5, "Hello World", "koushik"));
        messages.put(6L, new Message(6, "Hello Jersey", "koushik"));

    }

    private Map<Long, Message> messages = DatabaseClass.getMessages();

    public List<Message> getAllMessages() {
        return new ArrayList<Message>(messages.values());
    }

    public List<Message> getAllMessagesForYear(int year)
    {
        List<Message> messagesForYear = new ArrayList<Message>();
        Calendar calendar = Calendar.getInstance();
        for (Message message : messages.values())
        {
            if (calendar.get(Calendar.YEAR) == year)
                messagesForYear.add(message);
        }
        return  messagesForYear;
    }

    public List<Message> getAllMessagePaginated(int start, int size)
    {
        ArrayList<Message>  list = new ArrayList<Message>(messages.values());
        if (start + size > list.size())
        {
            return new ArrayList<Message>();
        }
        return list.subList(start, start+size);
    }

    public Message getMessage(long id) {
        Message message  = messages.get(id);
        if (message ==null)
        {
            throw new DataNotFoundException("El registro " + id + " no fue encontrado");
        }
        return message;
    }

    public Message addMessage(Message message) {
        message.setId(messages.size() + 1);
        messages.put(message.getId(), message);
        return message;
    }

    public Message updateMessage(Message message) {
        if (message.getId() <= 0) {
            return null;
        }
        messages.put(message.getId(), message);
        return message;
    }

    public Message removeMessage(long id) {
        return messages.remove(id);
    }

}
