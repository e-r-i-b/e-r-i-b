<%@ page import="com.rssl.phizic.business.BusinessException" %>
<%@ page import="com.rssl.phizic.business.documents.AbstractDepoAccountClaim" %>
<%@ page import="com.rssl.phizic.business.documents.BusinessDocumentService" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.esberibback.ESBEribBackServiceClientTest" %>
<%@ page import="com.rssl.phizic.utils.StringHelper" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%
	String issuer = request.getParameter("Issuer");
	String securityName = request.getParameter("SecurityName");
	String securityNumber = request.getParameter("SecurityNumber");
	String securityType = request.getParameter("SecurityType");
	String securityNominal = request.getParameter("SecurityNominal");
	String securityNominalCur = request.getParameter("SecurityNominalCur");
	String insideCode = request.getParameter("InsideCode");
	String isDelete = request.getParameter("IsDelete");
    boolean isSubmit = request.getParameter("send") != null;
    boolean isSubmitXML = request.getParameter("sendRequestText") != null;
    String error ="";
    String responseStr ="";
    String requestStr ="";

    if (isSubmit || isSubmitXML)
    {
        try
        {
        ESBEribBackServiceClientTest service = new ESBEribBackServiceClientTest(request.getParameter("url"));
            if(isSubmit)
            {
                requestStr = service.updateSecDicInfoReg(issuer, securityName, securityNumber, securityType,securityNominal,securityNominalCur, insideCode, isDelete);
            }
            else if(isSubmitXML)
            {
                requestStr = request.getParameter("requestText");
            }
            responseStr = service.updateSecDicInfo(requestStr);
        }
        catch (Exception e)
        {
            error = e.getMessage();
            e.printStackTrace();
        }
    }

%>

<html>
    <head><title>Test ESB Back WebService</title></head>
    <body>
        <form action="" method="POST">
            Укажите адрес веб-сервиса:
            <select id="urlType" onchange="changeUrl()">
                <option value="ESBERIBListener">ESBERIBListener</option>
                <option value="ESBERIBProxyListener">ESBERIBProxyListener</option>
                <option value="OTHER">Другой</option>
            </select>
            <input type="text" name="url" id="url" size="100"/>
            <table>
                <tr><td>Issuer</td><td><input type="text" name="Issuer" size="100" value=""/></td></tr>
                <tr><td>SecurityName</td><td><input type="text" name="SecurityName" size="100" value=""/></td></tr>
                <tr><td>SecurityNumber</td><td><input type="text" name="SecurityNumber" size="100" value=""/></td></tr>
                <tr><td>SecurityType</td><td><input type="text" name="SecurityType" size="100" value=""/></td></tr>
                <tr><td>SecurityNominal</td><td><input type="text" name="SecurityNominal" size="100" value=""/></td></tr>
                <tr><td>SecurityNominalCur</td><td><input type="text" name="SecurityNominalCur" size="100" value=""/></td></tr>
                <tr><td>InsideCode</td><td><input type="text" name="InsideCode" size="100" value=""/></td></tr>
                <tr><td>IsDelete</td><td><input type="text" name="IsDelete" size="100" value=""/></td></tr>
            </table>

            <input type="submit" name="send" value="обновить справочник"/><br/>

            <c:if test="<%=!StringHelper.isEmpty(error)%>">
                При выполнении произошла ошибка: <font color="red"><%=error%></font>
            </c:if>

            Запрос к ESBEribListener :
            <br/>
            <textarea name="requestText" style="width:60%;height:400px" cols="30" rows="30">${param.requestText}</textarea>
            <input type="submit" name="sendRequestText" value="отправить XML"/><br/>

            <c:if test="<%=!StringHelper.isEmpty(responseStr)%>">
                Ответ от ESBEribListener :
                <br/>
                <textarea style="width:60%;height:400px" cols="30" rows="30"><%=responseStr%></textarea>
            </c:if>

            <script type="text/javascript">
                function changeUrl()
                {
                    var type = document.getElementById('urlType').value;
                    var urlField = document.getElementById('url');
                    if (type != 'OTHER')
                        urlField.value = "http://localhost:8888/" + type + "/axis-services/backService";
                    else
                        urlField.value = "";
                }
                changeUrl();
            </script>
        </form>
    </body>
</html>