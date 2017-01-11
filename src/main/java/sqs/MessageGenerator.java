package sqs;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by cov-127 on 11/1/17.
 */
@Service
public class MessageGenerator {
    @Autowired
    private ProducerTemplate producerTemplate;

    @Scheduled(fixedDelay = 5000)
    public void generateMessage(){
        String json = "{'message':'Hello World'}";
        producerTemplate.sendBody("aws-sqs://rnd1?amazonSQSClient=#client", json );
    }
}
