package com.br.mensageria.consumer.component;

import com.br.mensageria.consumer.component.Model.MessageDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
public class MessageReceiver {

    private static final List<MessageDTO> messages = new ArrayList<>();

    public void receive(String message) {
        System.out.println(String.format("Message received: %s", message));
        if (!message.isEmpty()) {
            messages.add(new MessageDTO(message));
        }
    }

    public List<MessageDTO> getMessages() {
        return new ArrayList<>(messages);
    }
}
