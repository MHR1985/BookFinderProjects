package com.example.bookfinderalerts.kafka;

import com.example.bookfinderalerts.dto.BookListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

@Service
public class ProducerServiceCallback {

    private static String topic = "books";
    private static Logger logger = LoggerFactory.getLogger(ProducerServiceCallback.class);

    @Autowired
    private KafkaTemplate<String,String> template;

    public void sendMessageCallBack(String topic, String message)
    {
        ListenableFuture<SendResult<String, String>> future = template.send(topic, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>()
        {
            @Override
            public void onSuccess(SendResult<String, String> result)
            {
                logger.info("### Producer sends message [{}]", message);
                logger.info("$$$ Message [{}] delivered to partition [{}] with offset [{}]",
                        message, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex)
            {
                logger.warn("$$$ Unable to deliver message [{}], with exception {}", message, ex.getMessage());
            }
        });
    }

}
