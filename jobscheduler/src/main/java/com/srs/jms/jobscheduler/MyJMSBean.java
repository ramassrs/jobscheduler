package com.srs.jms.jobscheduler;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.QueueSender;
import javax.jms.DeliveryMode;
import javax.jms.QueueSession;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;

import org.springframework.stereotype.Component;

@Component("myJMSBean")
public class MyJMSBean {
	public void sendMessage() throws Exception {
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		env.put(Context.PROVIDER_URL, "tcp://localhost:61616");
		env.put("queue.queueSampleQueue", "MyNewQueue");
		
		// get the initial context
		InitialContext ctx = new InitialContext(env);

		// lookup the queue object
		Queue queue = (Queue) ctx.lookup("queueSampleQueue");
		// lookup the queue connection factory
		QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory");
		// create a queue connection
		QueueConnection queueConn = connFactory.createQueueConnection();

		// create a queue session
		QueueSession queueSession = queueConn.createQueueSession(false,Session.DUPS_OK_ACKNOWLEDGE);

		// create a queue sender
		QueueSender queueSender = queueSession.createSender(queue);
		queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		// create a simple message to say "Hello"
		TextMessage message = queueSession.createTextMessage("Hello! This is a trigger message from PTD Scheduler being sent to start the job in PTD Application\n");

		// send the message
		queueSender.send(message);

		System.out.println("sent: " + message.getText());

		queueConn.close();
	}
}
