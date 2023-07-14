<%@ page import="com.rssl.phizic.test.wsgateclient.esberib.EsbEribBackServiceClientBase" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.esberib.UpdateDocStateClientTest" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<!--
    Пример запроса

    <DocStateUpdateRq>
    <RqUID>12312312313</RqUID>
    <RqTm>123123</RqTm>
    <SPName>BP_ERIB</SPName>
    <Document>
    <DocNumber>2801</DocNumber>
    <BankInfo>
    <RbTbBrchId>77741573</RbTbBrchId>
    </BankInfo>
    <Status>
    <StatusCode>0</StatusCode>
    </Status>
    </Document>
    </DocStateUpdateRq>
-->

<%
    String responseStr = "";
    String requestStr = request.getParameter("requestText");
    EsbEribBackServiceClientBase service = new UpdateDocStateClientTest();

    if (!StringUtils.isEmpty(requestStr))
    {
        responseStr = service.sendRequest(requestStr, request.getParameter("url"));
    }
    else
    {
        requestStr = service.createDefaultRequest();
    }
%>

<html>
  <head><title>Test WebService</title></head>
  <body>
    <form action="" method="POST">
        Укажите адрес веб-сервиса:
        <select id="urlType" onchange="changeUrl()">
            <option value="ESBERIBListener">ESBERIBListener</option>
            <option value="ESBERIBProxyListener">ESBERIBProxyListener</option>
            <option value="OTHER">Другой</option>
        </select>
        <input type="text" name="url" id="url" size="100"/>
	    <input type="submit" name="docStateUpdateSubmit"/><br/>
	    <textarea name="requestText" type="text" style="width:100%;height:400px"><%=requestStr%></textarea>
		<hr/>
	    <textarea style="width:100%;height:400px"><%pageContext.setAttribute("responseStr", responseStr);%><c:out value="${responseStr}"/></textarea>
        <script type="text/javascript">
            function changeUrl()
            {
                var type = document.getElementById('urlType').value;
                var urlField = document.getElementById('url');
                if (type != 'OTHER')
                    urlField.value = "http://localhost:8888/" + type + "/axis-services/EsbEribBackService";
                else
                    urlField.value = "";
            }
            changeUrl();
        </script>
    </form>
  </body>
</html>