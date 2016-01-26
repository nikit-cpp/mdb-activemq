package p;

/**
 * Created by nik on 27.01.16.
 */
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.jms.*;
import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/jms")
public class TestJMS extends HttpServlet {

    @Resource(lookup = "java:/ConnectionFactory")
    ConnectionFactory cf;

    @Resource(lookup = "java:jboss/activemq/queue/TestQueue")
    private Queue queue;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            example();
            PrintWriter out = response.getWriter();
            out.println("Message sent!");

        } catch (Exception e) {
            e.printStackTrace();
        }

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
        }

        finally
        {
            if(connection != null)
            {
                try  {
                    connection.close();
                }
                catch(Exception e) {
                    throw e;
                }

            }
            closeConnection(connection);
        }

    }
    private void closeConnection(Connection con)            {
        try  {

            if (con != null) {
                con.close();
            }

        }

        catch(JMSException jmse) {

            System.out.println("Could not close connection " + con +" exception was " + jmse);

        }

    }

}