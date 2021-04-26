/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jms_producer;

import java.util.Properties;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;

/**
 *
 * @author fvasq
 */
public class Producer {

    private JMSContext context;
    private Queue queue;

    public void conectar() {
        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put("jboss.naming.client.ejb.context", true);
        jndiProperties.put(Context.SECURITY_PRINCIPAL, "admin");
        jndiProperties.put(Context.SECURITY_CREDENTIALS, "admin");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

        Context ctx;
        try {
            ctx = new InitialContext(jndiProperties);
            this.queue = (Queue) ctx.lookup("jms/JmsQueue");
            ConnectionFactory cf = (ConnectionFactory) ctx.lookup("jms/RemoteConnectionFactory");
            this.context = cf.createContext("ACTIVEMQ.CLUSTER.ADMIN.USER", "admin");
            System.out.println("Conectado...");
            JOptionPane.showMessageDialog(null, "Conectado!!", "JMS", JOptionPane.INFORMATION_MESSAGE);
        } catch (NamingException ex) {
            System.out.println("No se pudo conectar al servidor...");
            JOptionPane.showMessageDialog(null, "Error al conectar!!", "JMS", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void enviarMensaje(String mensaje) {
        try {
            JMSProducer messageProducer = context.createProducer();

            TextMessage message = context.createTextMessage();
            message.setText(mensaje);
            message.setBooleanProperty("Importante", true);

            messageProducer.send(queue, message);
            
            System.out.println("Mensaje enviado...");
            JOptionPane.showMessageDialog(null, "Mensaje enviado!!", "JMS", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println("Error al enviar el mensaje...");
            JOptionPane.showMessageDialog(null, "Error al enviar el mensaje!!", "JMS", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void desconectar() {
        this.context.close();
        System.out.println("Desconectado...");
        JOptionPane.showMessageDialog(null, "Desconectado!!", "JMS", JOptionPane.INFORMATION_MESSAGE);
        
    }

}
