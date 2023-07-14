<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="javax.jms.*" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="com.rssl.phizic.utils.StringHelper" %>
<html>
<head><title>Simple JMS test page</title></head>
<body>
<form action="" method="POST">
    <input type="submit"/>

    <div id="queuePanel" style="width:100%">
        <fieldset style="width:45%;display:inline">
            <legend><label><input type="checkbox" name="sendQueue" ${param.sendQueue==null?"":"checked"} /> Отправить сообщение в очередь</label></legend>
                <label>Фабрика <input type="text" name="sendQueueFactoryName" value="${param.sendQueueFactoryName==null?"jms/erib/way4u/InputQCF":param.sendQueueFactoryName}"/></label>
                <label>Очередь <input type="text" name="sendQueueName" value="${param.sendQueueName==null?"jms/erib/way4u/InputQueue":param.sendQueueName}"/></label>
                <label>Связь <input type="text" name="queueRequestCorrelationId" value="${param.queueRequestCorrelationId}"/></label><br>
                <label>Идентификатор группы <input type="text" name="groupId" value="${param.groupId}"/></label>
                <br>
                <textarea name="queueRequestText" rows="30" style="width:100%">${param.queueRequestText}</textarea>
                <%
                    String queueFactoryName = request.getParameter("sendQueueFactoryName");
                    String queueName = request.getParameter("sendQueueName");
                    if (request.getParameter("sendQueue") != null)
                    {
                        QueueSender snd = null;
                        QueueConnection qc = null;
                        QueueSession qs  = null;
                        try
                        {
                            String requestText = request.getParameter("queueRequestText");
                            Context ctx = new InitialContext();
                            QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup(queueFactoryName);
                            Queue q = (Queue) ctx.lookup(queueName);
                            qc = qcf.createQueueConnection();
                            qc.start();
                            qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                            TextMessage msg = qs.createTextMessage();
                            msg.setText(requestText);
                            if(StringHelper.isNotEmpty(request.getParameter("groupId")))
                                msg.setStringProperty("JMSXGroupID", request.getParameter("groupId"));
                            msg.setJMSCorrelationID(request.getParameter("queueRequestCorrelationId"));
                            snd = qs.createSender(q);
                            snd.send(msg);
                            out.println("Идентификатор исходящего сообщения " + msg.getJMSMessageID());
                        }
                        catch (Throwable e)
                        {
                            out.print(e);
                            e.printStackTrace();
                        }
                        finally
                        {
                            try
                            {
                                if (snd != null) snd.close();
                                if (qs != null) qs.close();
                                if (qc != null) qc.close();
                            }
                            catch (Throwable e)
                            {
                                out.print(e);
                                e.printStackTrace();
                            }
                        }
                    }
                %>
            </fieldset>

            <fieldset style="width:45%;display:inline">
                <legend><label><input type="checkbox" name="receiveQueue" ${param.receiveQueue==null?"":"checked"}/> Получить сообщение из очереди</label></legend>
                <label>Фабрика <input type="text" name="receiveQueueFactoryName" value="${param.receiveQueueFactoryName==null?"jms/erib/way4u/OutputQCF":param.receiveQueueFactoryName}"/></label>
                <label>Очередь <input type="text" name="receiveQueueName" value="${param.receiveQueueName==null?"jms/erib/way4u/OutputQueue":param.receiveQueueName}"/></label>
                <label>Таймаут <input type="text" name="queueResponceTimeout" size="5" value="${param.queueResponceTimeout==null?10000:param.queueResponceTimeout}"/></label>
                <label>Селектор <input type="text" name="queueResponseSelector" size="20" value="${param.queueResponseSelector}"/></label>
                <br>
                <textarea name="queueRequestText" rows="30" style="width:100%">
                <%
                    queueFactoryName = request.getParameter("receiveQueueFactoryName");
                    queueName = request.getParameter("receiveQueueName");
                    Message msg = null;
                    if (request.getParameter("receiveQueue") != null)
                    {
                        QueueReceiver rcv = null;
                        QueueConnection qc = null;
                        QueueSession qs  = null;
                        try
                        {
                            Context ctx = new InitialContext();
                            QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup(queueFactoryName);
                            Queue q = (Queue) ctx.lookup(queueName);
                            qc = qcf.createQueueConnection();
                            qc.start();
                            qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                            rcv = qs.createReceiver(q, request.getParameter("queueResponseSelector"));
                            msg = rcv.receive(Long.valueOf(request.getParameter("queueResponceTimeout")));
                            if (msg!= null)
                            {
                                out.println((msg instanceof TextMessage ? "" + ((TextMessage) msg).getText() : msg));
                            }
                        }
                        catch (Throwable e)
                        {
                            out.print(e);
                            e.printStackTrace();
                        }
                        finally
                        {
                            try
                            {
                                if (rcv != null) rcv.close();
                                if (qs != null) qs.close();
                                if (qc != null) qc.close();
                            }
                            catch (Throwable e)
                            {
                                out.print(e);
                            }
                        }
                    }
                %>
                </textarea>
                <%
                    if (request.getParameter("receiveQueue") != null && msg != null)
                    {
                        out.println("Идентификатор входящего сообщения " + msg.getJMSMessageID());
                    }
                    else
                    {
                        out.println("Message not found");
                    }
                %>
            </fieldset>
        </div>
        <input type="submit"/>
    </form>
</body>
</html>
