package sqs;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by cov-127 on 11/1/17.
 */
@Component
public class SQSRouteBuilder extends RouteBuilder {
    @Autowired
    private SQSProcessor sqsProcessor;

    @Value("${sqs.queue:demo}")
    private String queueName;

    private String uriFormat="aws-sqs://%s?amazonSQSClient=#client";
    @Override
    public void configure() throws Exception {
        System.out.println("CamelContext routers about to add.");
        // TODO Auto-generated method stub
        String uri = String.format(uriFormat, queueName);
        from(uri).process(sqsProcessor);
    }
}
