package com.br.mensageria.consumer.component;

import com.br.mensageria.consumer.component.Model.MessageDTO;
import com.br.mensageria.consumer.service.ClientService;
import com.br.mensageria.consumer.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
public class MessageReceiver {

    private static final List<MessageDTO> messages = new ArrayList<>();

    @Autowired
    OrderService orderService;

    @Autowired
    ClientService clientService;

    public void receive(String message) throws JsonProcessingException {
        System.out.println(String.format("Message received: %s", message));
        if (!message.isEmpty()) {
            messages.add(new MessageDTO(message));
            if (message.contains("[Client not added]")) {
                var data = message.replace("[Client not added]","");
                clientService.messageError(data);
            }
            if (message.contains("[Client added]")) {
                var data = message.replace("[Client added]","");
                clientService.message(data);
            }
            if (message.contains("[Order NOT purchase]")) {
                var data = message.replace("[Order NOT purchase]","");
                orderService.messageError(data);
            }
            if (message.contains("[Order purchase]")) {
                var data = message.replace("[Order purchase]","");
                orderService.message(data);
            }
        }
    }

    public List<MessageDTO> getMessages() {
        return new ArrayList<>(messages);
    }
}
