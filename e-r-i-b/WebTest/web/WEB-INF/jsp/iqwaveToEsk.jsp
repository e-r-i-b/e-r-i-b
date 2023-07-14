<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ page import="com.rssl.phizicgate.iqwave.listener.proxy.generated.Esk4IQWaveProtType" %>
<%@ page import="com.rssl.phizicgate.iqwave.listener.proxy.generated.Esk4IQWaveService" %>
<%@ page import="com.rssl.phizicgate.iqwave.listener.proxy.generated.Esk4IQWaveService_Impl" %>
<%@ page import="javax.xml.rpc.Stub" %>


<%

    String responseStr = null;
    String requestStr = request.getParameter("requestText");
    if (requestStr != null)
    {

        //noinspection UnhandledExceptionInJSP
        try
        {
            Esk4IQWaveService service = new Esk4IQWaveService_Impl();
            Esk4IQWaveProtType stub = service.getEsk4IQWave();
            ((com.sun.xml.rpc.client.StubBase) stub)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, request.getParameter("url"));
            responseStr = stub.getXMLmessage(requestStr.trim());
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
"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<SimplePaymentResponse>\n" +
"   <Head>\n" +
"       <MessUID>\n" +
"           <MessageId>07e816c51ea911a5b0c01061667256f3</MessageId>\n" +
"           <MessageDate>2010-05-26</MessageDate>\n" +
"           <FromAbonent>IQW</FromAbonent>\n" +
"       </MessUID>\n" +
"       <MessType>SimplePaymentResponse</MessType>\n" +
"       <Version>1</Version>\n" +
"       <Error>\n" +
"           <ErrCode>0</ErrCode>\n" +
"       </Error>\n" +
"       <parentId>\n" +
"           <MessageId>������_�������������_����</MessageId>\n" +
"           <MessageDate>2010-05-26</MessageDate>\n" +
"           <FromAbonent>SBOL</FromAbonent>\n" +
"       </parentId>\n" +
"   </Head>\n" +
"   <Body>\n" +
"       <AuthorizeCode>AuthorizeCode</AuthorizeCode>\n" +
"       <!-- ������������� �������� SVFE -->\n" +
"       <OperationIdentifier>123456789</OperationIdentifier>\n" +        
"       <RecAcc>12345678901234567890</RecAcc>\n" +
"       <RecBic>044525402</RecBic>\n" +
"       <RecCorrAcc>30101810100000000402</RecCorrAcc>\n" +
"       <RecInn>123456789012</RecInn>\n" +
"       <RecCompName>�� \"�����������������\" (���)</RecCompName>\n" +
"       <RecCheque>��� ��� ���</RecCheque>" +
"   </Body>\n" +
"</SimplePaymentResponse>\n" +
"----------------------------------------------------------\n" +
"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<SimplePaymentResponse>\n" +
"   <Head>\n" +
"      <MessUID>\n" +
"           <MessageId>f65bbca3ac8f5253792ada9e5a64ade2</MessageId>\n" +
"           <MessageDate>2010-05-26</MessageDate>\n" +
"          <FromAbonent>IQW</FromAbonent>\n" +
"       </MessUID>\n" +
"      <MessType>SimplePaymentResponse</MessType>\n" +
"       <Version>1</Version>\n" +
"      <Error>\n" +
"           <ErrCode>500</ErrCode>\n" +
"           <ErrMes>������� ������!!!! ����������� ������</ErrMes>\n" +
"      </Error>\n" +
"       <parentId>\n" +
"           <MessageId>������_�������������_����</MessageId>\n" +
"           <MessageDate>2010-05-26</MessageDate>\n" +
"           <FromAbonent>SBOL</FromAbonent>\n" +
"       </parentId>\n" +
"   </Head>\n" +
"</SimplePaymentResponse>\n" +
"--------------- ����� �� ����������� ���������---------------\n" +
"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<PaymentRESResponse>\n" +
"    <Head>\n" +
"        <MessUID>\n" +
"            <MessageId>f65bbca3ac8f5253792ada9e5a648691</MessageId>\n" +
"            <MessageDate>2010-05-26</MessageDate>\n" +
"            <FromAbonent>IQW</FromAbonent>\n" +
"        </MessUID>\n" +
"        <MessType>PaymentRESResponse</MessType>\n" +
"        <Version>1</Version>\n" +
"        <Error>\n" +
"            <ErrCode>0</ErrCode>\n" +
"        </Error>\n" +
"        <parentId>\n" +
"            <MessageId>������_�������������_����</MessageId>\n" +
"            <MessageDate>2010-05-26</MessageDate>\n" +
"            <FromAbonent>SBOL</FromAbonent>\n" +
"        </parentId>\n" +
"    </Head>\n" +
"    <Body>\n" +
"        <RecIdentifier>123456</RecIdentifier>\n" +
"        <TicketsNumbers>2</TicketsNumbers>\n" +
"        <TicketsList>\n" +
"            <TicketNumber>1234567891514524</TicketNumber>\n" +
"        </TicketsList>\n" +
"        <TicketsList>\n" +
"            <TicketNumber>1234898991514524</TicketNumber>\n" +
"        </TicketsList>\n" +
"        <TicketsStatus>1</TicketsStatus>\n" +
"        <ItineraryUrl>http://www.aeroflot.ru/cms/</ItineraryUrl>\n" +
"        <UserMessage>��������� ��� �������</UserMessage>\n" +
"    </Body>\n" +
"</PaymentRESResponse>\n" +
"----------------������� ������� �������� ������-----------------\n" +
"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<RefundSimplePaymentEcommerceResponse>\n" +
"   <Head>\n" +
"       <MessUID>\n" +
"           <MessageId>07e816c51ea911a5b0c01061667256f3</MessageId>\n" +
"           <MessageDate>2010-05-26</MessageDate>\n" +
"           <FromAbonent>IQW</FromAbonent>\n" +
"       </MessUID>\n" +
"       <MessType>RefundSimplePaymentEcommerceResponse</MessType>\n" +
"       <Version>1</Version>\n" +
"       <Error>\n" +
"           <ErrCode>0</ErrCode>\n" +
"       </Error>\n" +
"       <parentId>\n" +
"           <MessageId>������_�������������_����</MessageId>\n" +
"            <MessageDate>2010-05-26</MessageDate>\n" +
"            <FromAbonent>SBOL</FromAbonent>\n" +
"       </parentId>\n" +
"   </Head>\n" +
"    <Body>\n" +
"       <AuthorizeCode>AuthorizeCode</AuthorizeCode>\n" +
"       <!-- ������������� �������� SVFE -->\n" +
"       <OperationIdentifier>987654321</OperationIdentifier>\n" +
"    </Body>\n" +
"</RefundSimplePaymentEcommerceResponse>\n" +
"--------------������ �������� ������--------------\n"+
"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<ReversalSimplePaymentEcommerceResponse>\n" +
"   <Head>\n" +
"       <MessUID>\n" +
"           <MessageId>07e816c51ea911a5b0c01061667256f3</MessageId>\n" +
"           <MessageDate>2010-05-26</MessageDate>\n" +
"           <FromAbonent>IQW</FromAbonent>\n" +
"       </MessUID>\n" +
"       <MessType>ReversalSimplePaymentEcommerceResponse</MessType>\n" +
"       <Version>1</Version>\n" +
"       <Error>\n" +
"           <ErrCode>0</ErrCode>\n" +
"       </Error>\n" +
"       <parentId>\n" +
"           <MessageId>������_�������������_����</MessageId>\n" +
"            <MessageDate>2010-05-26</MessageDate>\n" +
"            <FromAbonent>SBOL</FromAbonent>\n" +
"       </parentId>\n" +
"   </Head>\n" +
"    <Body>\n" +
"       <AuthorizeCode>AuthorizeCode</AuthorizeCode>\n" +
"       <!-- ������������� �������� SVFE -->\n" +
"       <OperationIdentifier>987654321</OperationIdentifier>\n" +
"    </Body>\n" +
"</ReversalSimplePaymentEcommerceResponse>\n"+
"--------------������ �������� ������--------------\n"+
"<SimplePaymentEcommerceResponse>\n" +
"   <Head>\n" +
"       <MessUID>\n" +
"           <MessageId>07e816c51ea911a5b0c01061667256f3</MessageId>\n" +
"           <MessageDate>2010-05-26</MessageDate>\n" +
"           <FromAbonent>IQW</FromAbonent>\n" +
"       </MessUID>\n" +
"       <MessType>SimplePaymentEcommerceResponse</MessType>\n" +
"       <Version>1</Version>\n" +
"       <Error>\n" +
"           <ErrCode>0</ErrCode>\n" +
"       </Error>\n" +
"       <parentId>\n" +
"           <MessageId>������_�������������_����</MessageId>\n" +
"           <MessageDate>2010-05-26</MessageDate>\n" +
"           <FromAbonent>SBOL</FromAbonent>\n" +
"       </parentId>\n" +
"   </Head>\n" +
"   <Body>\n" +
"       <AuthorizeCode>AuthorizeCode</AuthorizeCode>\n" +
"       <!-- ������������� �������� SVFE -->\n" +
"       <OperationIdentifier>123456789</OperationIdentifier>\n" +
"   </Body>\n" +
"</SimplePaymentEcommerceResponse>\n";
}
%>
<html>
<head><title>IQWAVE to SBOL Web-Services TEST</title></head>
<body>
<form action="" method="POST">
    ������� ����� ���-�������:
    <select id="urlType" onchange="changeUrl()">
        <option value="IQWaveListener">IQWaveListener</option>
        <option value="IQWaveProxyListener">IQWaveProxyListener</option>
        <option value="OTHER">������</option>
    </select>
    <input type="text" name="url" id="url" size="100"/>
    <input type="submit"/><br/>
    <textarea name="requestText" type="text" style="width:100%;height:400px"><%=requestStr%></textarea>
    <hr/>
    ******* ����� �� IQWaveListener: *********
    <textarea style="width:100%;height:400px"><%pageContext.setAttribute("responseStr", responseStr);%> <%=responseStr%></textarea>
    <script type="text/javascript">
        function changeUrl()
        {
            var type = document.getElementById('urlType').value;
            var urlField = document.getElementById('url');
            if (type != 'OTHER')
                urlField.value = "http://localhost:8888/" + type + "/services/Esk4IQWaveService";
            else
                urlField.value = "";
        }
        changeUrl();
    </script>
</form>
</body>
</html>
