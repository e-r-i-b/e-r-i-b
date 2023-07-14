<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ page import="com.rssl.phizicgate.sbrf.ws.listener.proxy.generated.OfflineSrvLocator" %>
<%@ page import="com.rssl.phizicgate.sbrf.ws.listener.proxy.generated.OfflineSrvSoap_PortType" %>


<%

    String responseStr = null;
    String requestStr = request.getParameter("requestText");
    if (requestStr != null)
    {

        //noinspection UnhandledExceptionInJSP
        try
        {
            OfflineSrvLocator codLocator = new OfflineSrvLocator();
            codLocator.setOfflineSrvSoapEndpointAddress(request.getParameter("url"));
            OfflineSrvSoap_PortType portType = codLocator.getOfflineSrvSoap();
            responseStr = portType.getXMLmessage(requestStr);
        }
        catch (Exception e)
        {
            responseStr = e.getClass().getName() + ":" + e.getMessage();
            e.printStackTrace();
        }
    }
    else
    {
        requestStr =
"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
"<message>\n" +
"   <messageId>cd575babb612a143a086581c0bf9ceed</messageId>\n" +
"   <messageDate>2007-03-14+03:00</messageDate>\n" +
"   <fromAbonent>COD</fromAbonent>\n" +
"   <confirmation_offline_a>\n" +
"       <debitRow>\n" +
"           <date>2007-03-02+03:00</date>\n" +
"           <name>Списание</name>\n" +
"           <debit>true</debit>\n" +
"           <sum>100.00</sum>\n" +
"           <balance>597092.34</balance>\n" +
"       </debitRow>\n" +
"       <creditRow>\n" +
"           <date>2007-03-02+03:00</date>\n" +
"           <name>Зачисление</name>\n" +
"           <debit>false</debit>\n" +
"           <sum>12.00</sum>\n" +
"           <balance>503220.61</balance>\n" +
"       </creditRow>\n" +
"       <register>23FHK567DGF347BNK</register>\n" +
"       <line>12</line>\n" +
"   </confirmation_offline_a>\n" +
"   <parentId>\n" +
"       <messageId>ВСТАВЬ_ИДЕНТИФИКАТОР_СЮДА</messageId>\n" +
"       <messageDate>ДАТА СОЗДАНИЯ ЗАЯВКИ В ФОРМАТЕ YYYY-MM-DD+XX:XX</messageDate>\n" +
"       <fromAbonent>ESK</fromAbonent>\n" +
"   </parentId>\n" +
"</message>";
    }
%>
<html>
<head><title>COD to ERIB Web-Services Listener TEST</title></head>
<body>
<form action="" method="POST">
    Укажите адрес веб-сервиса:
    <select id="urlType" onchange="changeUrl()">
        <option value="Listener">Listener</option>
        <option value="CODProxyListener">CODProxyListener</option>
        <option value="OTHER">Другой</option>
    </select>
    <input type="text" name="url" id="url" size="100"/>
    <input type="submit"/><br/>
    <textarea name="requestText" type="text" style="width:100%;height:400px"><%=requestStr%></textarea>
    ******* Ответ из Listener: *********
    <textarea style="width:100%;height:400px"><%pageContext.setAttribute("responseStr", responseStr);%><%=responseStr%></textarea>
    <script type="text/javascript">
        function changeUrl()
        {
            var type = document.getElementById('urlType').value;
            var urlField = document.getElementById('url');
            if (type != 'OTHER')
                urlField.value = "http://localhost:8888/" + type + "/services/Listener";
            else
                urlField.value = "";
        }
        changeUrl();
    </script>
</form>
</body>
</html>
