/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jms_consumer;

import java.util.Properties;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author fvasq
 */
public class JMS_Consumer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JMSException {
        // TODO code application logic here
        
        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put("jboss.naming.client.ejb.context", true);
        jndiProperties.put(Context.SECURITY_PRINCIPAL, "admin");
        jndiProperties.put(Context.SECURITY_CREDENTIALS, "admin");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        
//        Connection connection = null;
        
        try {
            Context ctx = new InitialContext(jndiProperties);
            Queue queue = (Queue)ctx.lookup("jms/JmsQueue");
            ConnectionFactory cf = (ConnectionFactory)ctx.lookup("jms/RemoteConnectionFactory");
            JMSContext context = cf.createContext();
            
            JMSConsumer consumer = context.createConsumer(queue);
            
            while (true) {                
                Message m = consumer.receive(100000);
                
                if (m != null) {
                    System.out.println("ID mensaje: " + m.getJMSMessageID() + ", Mensaje: '" + ((TextMessage)m).getText() + "'");
                } else {
                    break;
                }
            }
            
        } catch (NamingException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Timeout, good bay");
        }
    }
}
