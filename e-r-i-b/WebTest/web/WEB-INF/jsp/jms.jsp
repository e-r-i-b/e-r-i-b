<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="javax.jms.*" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<html>
<head><title>Simple JMS test page</title></head>
<body>
    <form action="" method="POST">
    <input type="submit"/>
        <table border="1" width="100%">
            <tr>
                <td width="50%">
                    QUEUE CONNECTION FACTORY <input type="text" name="queueFactoryName" size="100" value="${param.queueFactoryName==null?"jms/TestQueueConnectionFactory":param.queueFactoryName}"/><br/>
                    QUEUE <input type="text" name="queueName" size="100" value="${param.queueName==null?"jms/demoQueue":param.queueName}"/><br/>
                    <input type="checkbox" name="sendQueue" ${param.sendQueue==null?"":"checked"} />send message<input type="text" name="queueRequestText" size="50" value="${param.queueRequestText}"/>
                    with correlationId <input type="text" name="queueRequestCorrelationId" size="30" value="${param.queueRequestCorrelationId}"/><br/>
                    <input type="checkbox" name="receiveQueue" ${param.receiveQueue==null?"":"checked"}/>receive timeout <input type="text" name="queueResponceTimeout" size="10" value="${param.queueResponceTimeout==null?0:param.queueResponceTimeout}"/>
                    selector <input type="text" name="queueResponseSelector" size="30" value="${param.queueResponseSelector}"/> ("JMSCorrelationID = 'blah-blah')<br/>
                </td>
                <td width="50%">
                    TOPIC CONNECTION FACTORY <input type="text" name="topicFactoryName" size="100" value="${param.topicFactoryName==null?"jms/TestTopicConnectionFactory":param.topicFactoryName}"/><br/>
                    TOPIC <input type="text" name="topicName" size="100" value="${param.topicName==null?"jms/demoTopic":param.topicName}"/><br/>
                    <input type="checkbox" name="sendTopic">publish</input> <input type="text" name="topicRequestText" size="100" value="${param.topicRequestText}"/><br/>
                    <input type="checkbox" name="receiveTopic">receive</input> <br/>
                </td>
            </tr>
            <tr>
                <td width="50%">
                    <pre>
<%
    String queueFactoryName = request.getParameter("queueFactoryName");
    String queueName = request.getParameter("queueName");
    if (request.getParameter("sendQueue") != null)
    {
        QueueSender snd = null;
        QueueConnection qc = null;
        QueueSession qs  = null;
        try
        {
            String requestText = request.getParameter("queueRequestText");
            Context ctx = new InitialContext(new Hashtable());
            // 1a. Retrieve the queue connection factory.
            QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup(queueFactoryName);
            // 1b. Retrieve the queue.
            Queue q = (Queue) ctx.lookup(queueName);
            // 2. Create the JMS connection.
            qc = qcf.createQueueConnection();
            // 3. Start the queue connection.
            qc.start();
            // 4. Create the JMS session over the JMS connection.
            qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            //5. Create a sender on the JMS session to send messages.
            snd = qs.createSender(q);
            //6. Create the message using the createMessage method of the
            // JMS session.
            TextMessage msg = qs.createTextMessage();
            msg.setText(requestText);
            msg.setJMSCorrelationID(request.getParameter("queueRequestCorrelationId"));
            //7. Send the message out over the sender (snd) using the
            // send method.
            snd.send(msg);
            out.println(
                    "Message sent:\n\tid=" + msg.getJMSMessageID()+
                    "\n\tcorrelationID=" + msg.getJMSCorrelationID()+
                    "\n\ttext=" + msg.getText()
            );
        }
        catch (Throwable e)
        {
            e.printStackTrace(new PrintWriter(out));
        }
        finally
        {
            //8 & 9 Close the sender, the JMS session and the JMS connection.
            try
            {
                if (snd != null) snd.close();
                if (qs != null) qs.close();
                if (qc != null) qc.close();
            }
            catch (JMSException e)
            {
                e.printStackTrace(new PrintWriter(out));
            }
        }
    }
%>
                    </pre>
                </td>
                <td width="50%">
                <pre>
<%
    String topicFactoryName = request.getParameter("topicFactoryName");
    String topicName = request.getParameter("topicName");
    if (request.getParameter("sendTopic") != null)
    {
        TopicPublisher  snd = null;
        TopicConnection tc = null;
        TopicSession ts  = null;
        try
        {
            String requestText = request.getParameter("topicRequestText");
            out.println("Sending message:\ntext=" + requestText);
            Context ctx = new InitialContext(new Hashtable());
            TopicConnectionFactory tcf = (TopicConnectionFactory) ctx.lookup(topicFactoryName);
            Topic topic = (Topic) ctx.lookup(topicName);
            tc = tcf.createTopicConnection();
            tc.start();
            ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            snd = ts.createPublisher(topic);
            TextMessage msg = ts.createTextMessage();
            msg.setText(requestText);
            snd.publish(msg);
            out.println("Message sent:\nid=" + msg.getJMSMessageID());
        }
        catch (Throwable e)
        {
            e.printStackTrace(new PrintWriter(out));
        }
        finally
        {
            try
            {
                if (snd != null)
                    snd.close();
                if (ts != null)
                    ts.close();
                if (tc != null)
                    tc.close();
            }
            catch (JMSException e)
            {
                e.printStackTrace(new PrintWriter(out));
            }
        }
    }
%>
                </pre>
                </td>
            </tr>
            <tr>
                <td width="50%">
                    <pre>
<%
    if (request.getParameter("receiveQueue") != null)
    {
        QueueReceiver rcv = null;
        QueueConnection qc = null;
        QueueSession qs  = null;
        try
        {
            out.println("Receiving message");
            Context ctx = new InitialContext(new Hashtable());
            // 1a. Retrieve the queue connection factory.
            QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup(queueFactoryName);
            // 1b. Retrieve the queue.
            Queue q = (Queue) ctx.lookup(queueName);
            // 2. Create the JMS connection.
            qc = qcf.createQueueConnection();
            // 3. Start the queue connection.
            qc.start();
            // 4. Create the JMS session over the JMS connection.
            qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            // 5. Create a receiver, as we are receiving off of the queue.
            rcv = qs.createReceiver(q, request.getParameter("queueResponseSelector"));
            // 6. Receive the messages.
            Message msg = rcv.receive(Long.valueOf(request.getParameter("queueResponceTimeout")));
            if (msg != null)
            {
                out.println(
                        "Message sent:\n\tid=" + msg.getJMSMessageID()+
                                "\n\tcorrelationID=" + msg.getJMSCorrelationID()+
                                (msg instanceof TextMessage ?"\n\ttext=" + ((TextMessage)msg).getText():"\n\tclass=" + msg.getClass())
                );
            }
            else
            {
                out.println("message not found");
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace(new PrintWriter(out));
        }
        finally
        {
            // 7 & 8 Close the receiver, the JMS session and the JMS connection.
            try
            {
                if (rcv != null) rcv.close();
                if (qs != null) qs.close();
                if (qc != null) qc.close();
            }
            catch (JMSException e)
            {
                e.printStackTrace(new PrintWriter(out));
            }
        }
    }
%>
                    </pre>
               </td>
               <td  width="50%">
                    <pre>
<%
    if (request.getParameter("receiveTopic") != null)
    {
        TopicSubscriber rcv = null;
        TopicConnection tc = null;
        TopicSession ts  = null;
        try
        {
            out.println("Receiving message");
            Context ctx = new InitialContext(new Hashtable());
            TopicConnectionFactory tcf = (TopicConnectionFactory) ctx.lookup(topicFactoryName);
            Topic t = (Topic) ctx.lookup(topicName);
            tc = tcf.createTopicConnection();
            tc.start();
            ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            rcv = ts.createSubscriber(t);
            TextMessage msg = (TextMessage) rcv.receive(1);
            if (msg != null)
            {
                out.println("message received");
                out.println("id=" + msg.getJMSMessageID());
                out.println("text=" + msg.getText());
            }
            else
            {
                out.println("message not found");
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace(new PrintWriter(out));
        }
        finally
        {
            try
            {
                if (rcv != null) rcv.close();
                if (ts != null) ts.close();
                if (tc != null) tc.close();
            }
            catch (JMSException e)
            {
                e.printStackTrace(new PrintWriter(out));
            }
        }
    }
%>
                </pre>
                </td>
            </tr>
        </table>
        <input type="submit"/>
    </form>
</body>
</html>
