<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="javax.jms.*" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="com.rssl.phizic.utils.StringHelper" %>
<html>
<head><title>Basket JMS test page</title></head>
<body>
    <form action="" method="POST">
        <table border="1" width="70%">
            <tr>
                <td>
                    <h1>Отправка сообщений напрямую из автопэя</h1>
                    QUEUE CONNECTION FACTORY
                    <select id="autopayQueueFactoryName" name="autopayQueueFactoryName">
                        <option value="jms/basket/AddBillBasketQCF"
                                <c:if test="${param.queueFactoryName == 'jms/basket/AddBillBasketQCF'}">selected="true"</c:if>>jms/basket/AddBillBasketQCF</option>
                        <option value="jms/basket/AcceptBillBasketExecuteQCF"
                                <c:if test="${param.queueFactoryName == 'jms/basket/AcceptBillBasketExecuteQCF'}">selected="true"</c:if>>jms/basket/AcceptBillBasketExecuteQCF</option>
                    </select>
                    <br/>
                    QUEUE
                    <select name="autopayQueueName" id="autopayQueueName">
                        <option value="jms/basket/AddBillBasketQueue"
                                <c:if test="${param.queueName == 'jms/basket/AddBillBasketQueue'}">selected="true"</c:if>>jms/basket/AddBillBasketQueue</option>
                        <option value="jms/basket/AcceptBillBasketExecuteQueue"
                                <c:if test="${param.queueName == 'jms/basket/AcceptBillBasketExecuteQueue'}">selected="true"</c:if>>jms/basket/AcceptBillBasketExecuteQueue</option>
                    </select>
                    <br/>
                    USER_HEADER_INFO
                    <select name="extSystemCode" id="extSystemCode">
                        <option value="BP_ERIB_BASKET_INFO"
                                <c:if test="${param.extSystemCode == 'BP_ERIB_BASKET_INFO'}">selected="true"</c:if>>BP_ERIB_BASKET_INFO</option>
                        <option value="BP_ERIB_BASKET_EXEC"
                                <c:if test="${param.extSystemCode == 'BP_ERIB_BASKET_EXEC'}">selected="true"</c:if>>BP_ERIB_BASKET_EXEC</option>
                    </select>
                    <br/>
                    <input type="checkbox" name="sendQueue" ${param.sendQueue==null?"":"checked"}/>send message
                    <c:set var="textValue">
                        <c:choose>
                            <c:when test="${param.autopayQueueRequestText!=null}">
                                ${param.autopayQueueRequestText}
                            </c:when>
                            <c:otherwise>
                                <jsp:include page="/WEB-INF/jsp/basket/autopay-messages.jsp"/>
                            </c:otherwise>
                        </c:choose>
                    </c:set>
                    <textarea name="autopayQueueRequestText" type="text" style="width:100%;height:400px"><c:out value="${textValue}"/></textarea>
                    with correlationId <input type="text" name="queueRequestCorrelationId" size="30" value="${param.queueRequestCorrelationId}"/><br/>

                    <button type="submit" name="operation" value="autopaySend" style="margin: 9px 10px">Отправить</button>
                </td>
            </tr>

            <tr>
                <td>
                    <h1>Отправка сообщений через шину</h1>
                    QUEUE CONNECTION FACTORY
                    <input name="esbQueueFactoryName" id="esbQueueFactoryName" type="text" size="30" value="jms/EjbBasketListenerQCF"/>
                    <br/>
                    QUEUE
                    <input name="esbQueueName" id="esbQueueName" type="text" size="30" value="jms/EjbBasketListenerQueue">
                    <br/>
                    JMSXGroupID
                    <input name="JMSXGroupID" id="JMSXGroupID" type="text" size="30" value="4241534B4554000000000000000000000000000000000000">
                    <br/>
                    <c:set var="textValue">
                        <c:choose>
                            <c:when test="${param.esbQueueRequestText!=null}">
                                ${param.esbQueueRequestText}
                            </c:when>
                            <c:otherwise>
                                <jsp:include page="/WEB-INF/jsp/basket/esb-messages.jsp"/>
                            </c:otherwise>
                        </c:choose>
                    </c:set>
                    <textarea name="esbQueueRequestText" type="text" style="width:100%;height:400px"><c:out value="${textValue}"/></textarea>
                    with correlationId <input type="text" name="queueRequestCorrelationId" size="30" value="${param.queueRequestCorrelationId}"/><br/>
                    <button type="submit" name="operation" value="esbSend" style="margin: 9px 10px">Отправить</button>
                </td>
            </tr>
            <tr>
                <td>
                    <pre>
                    <%
                        String queueFactoryName = null;
                        String queueName = null;
                        String requestText = null;
                        if("esbSend".equals(request.getParameter("operation")))
                        {
                            queueFactoryName = request.getParameter("esbQueueFactoryName");
                            queueName = request.getParameter("esbQueueName");
                            requestText = request.getParameter("esbQueueRequestText");
                        }
                        else if("autopaySend".equals(request.getParameter("operation")))
                        {
                            queueFactoryName = request.getParameter("autopayQueueFactoryName");
                            queueName = request.getParameter("autopayQueueName");
                            requestText = request.getParameter("autopayQueueRequestText");
                        }

                        // если нажали отправить
                        if (StringHelper.isNotEmpty(request.getParameter("operation")))
                        {
                            QueueSender snd = null;
                            QueueConnection qc = null;
                            QueueSession qs  = null;
                            try
                            {
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
                                msg.setStringProperty("JMSXGroupID", request.getParameter("JMSXGroupID"));
                                msg.setStringProperty("ExtSystemCode",request.getParameter("extSystemCode"));
                                //7. Send the message out over the sender (snd) using the
                                // send method.
                                snd.send(msg);
                                out.println(
                                        "Message sent:\n\tid=" + msg.getJMSMessageID()+
                                                "\n\tcorrelationID=" + msg.getJMSCorrelationID()
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
            </tr>
        </table>
    </form>
</body>
</html>
