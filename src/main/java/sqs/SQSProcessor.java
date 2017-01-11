package sqs;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 * Created by cov-127 on 11/1/17.
 */
@Component
public class SQSProcessor implements Processor{
    public void process(Exchange exchange) throws Exception {
        System.out.println(exchange.getIn().getBody());
    }
}
