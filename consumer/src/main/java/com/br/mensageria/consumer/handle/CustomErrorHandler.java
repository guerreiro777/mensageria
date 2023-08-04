package com.br.mensageria.consumer.handle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

@Component
public class CustomErrorHandler extends Throwable implements RabbitListenerErrorHandler {

    private static final Logger logger = LogManager.getLogger(CustomErrorHandler.class);

    @Override
    public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message,
                              ListenerExecutionFailedException exception) throws Exception {
        if (amqpMessage.getMessageProperties().isRedelivered()) {
            logger.error(amqpMessage.getMessageProperties().isRedelivered()
                    + "AMQP Message:{} Message:{}", amqpMessage, message, exception);
            throw new AmqpRejectAndDontRequeueException(exception);
        } else {
            logger.error("AMQP Message:{} Message:{}", amqpMessage, message, exception);
            throw exception;
        }
    }

}
