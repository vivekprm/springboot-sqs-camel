package sqs;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.aws.sqs.SqsComponent;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cov-127 on 11/1/17.
 */
@Configuration
public class CamelSQSConfig {
    @Autowired
    private SQSRouteBuilder routeBuilder;

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${sqs.accessKey}")
    private String accessKey;
    @Value("${sqs.secretKey}")
    private String secretKey;
    @Value("${sqs.proxyhost:localhost}")
    private String proxyHost;
    @Value("${sqs.proxyport:8087}")
    private int proxyPort;

    @Bean
    public AmazonSQS getSQSClient(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setProxyHost("http://"+proxyHost);
        clientConfiguration.setProxyPort(proxyPort);
        AmazonSQS client = new AmazonSQSClient(awsCredentials);
        client.setRegion( Region.getRegion(Regions.US_WEST_2)); // Setting the Region, you can set your assigned regions
        return client;
    }
    @Bean
    public SpringCamelContext getCamelContext(AmazonSQS client) throws Exception {
        SimpleRegistry registry = new SimpleRegistry();
        registry.put("client", client);
        SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
        camelContext.setRegistry(registry);
        SqsComponent component = new SqsComponent();
        component.setCamelContext(camelContext);
        component.start();
        addRoutes(camelContext);
        return camelContext;
    }
    @Bean
    public ProducerTemplate producerTemplate(CamelContext camelContext){
        ProducerTemplate template = camelContext.createProducerTemplate();
        return template;
    }
    private void addRoutes(CamelContext camelContext) throws Exception {
        camelContext.addRoutes(routeBuilder);
    }
}
