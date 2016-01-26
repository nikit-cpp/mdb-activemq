package p;

/**
 * Created by nik on 27.01.16.
 */
import javax.ejb.*;
import javax.jms.*;

@MessageDriven(activationConfig = {

        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),

        @ActivationConfigProperty(propertyName = "destination", propertyValue = "activemq/queue/TestQueue") })

public class ActiveMQMDB implements MessageListener {

    @Override
    public void onMessage(Message message) {

        try {

            if (message instanceof TextMessage) {

                System.out.println("Got Message "

                        + ((TextMessage) message).getText());

            }

        } catch (JMSException e) {

            e.printStackTrace();

        }

    }

}