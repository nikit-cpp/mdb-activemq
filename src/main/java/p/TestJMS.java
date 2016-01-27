package p;

/**
 * Created by nik on 27.01.16.
 */
import java.io.IOException;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Stateless
@Path("/")
public class TestJMS {

    @Resource(lookup = "java:/ConnectionFactory")
    ConnectionFactory cf;

    @Resource(lookup = "java:jboss/activemq/queue/TestQueue")
    private Queue queue;

    @GET
    @Path("/jms")
    public Response doGet() throws IOException {

        try {
            example();

            return Response.ok("Message sent!").build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.serverError().build();
    }

    public void example() throws Exception     {

        Connection connection =  null;
        try {
            connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer(queue);

            connection.start();

            TextMessage message = session.createTextMessage("Hello!");
            publisher.send(message);
        } finally {
            closeConnection(connection);
        }

    }
    private void closeConnection(Connection con)            {
        try  {
            if (con != null) {
                con.close();
            }
        } catch(JMSException jmse) {
            System.out.println("Could not close connection " + con +" exception was " + jmse);
        }
    }
}