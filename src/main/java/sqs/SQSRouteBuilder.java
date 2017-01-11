package sqs;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by cov-127 on 11/1/17.
 */
@Component
public class SQSRouteBuilder extends RouteBuilder {
    @Autowired
    private SQSProcessor sqsProcessor;

    @Override
    public void configure() throws Exception {
        System.out.println("CamelContext routers about to add.");
        // TODO Auto-generated method stub
        from ("aws-sqs://rnd1?amazonSQSClient=#client").process(sqsProcessor);
    }
}
