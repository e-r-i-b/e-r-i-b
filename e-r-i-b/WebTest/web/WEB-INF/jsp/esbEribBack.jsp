<%@ page import="com.rssl.phizic.business.BusinessException" %>
<%@ page import="com.rssl.phizic.business.documents.AbstractDepoAccountClaim" %>
<%@ page import="com.rssl.phizic.business.documents.BusinessDocumentService" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.esberibback.ESBEribBackServiceClientTest" %>
<%@ page import="com.rssl.phizic.utils.StringHelper" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.rssl.phizic.dataaccess.hibernate.HibernateExecutor" %>
<%@ page import="org.hibernate.criterion.DetachedCriteria" %>
<%@ page import="org.hibernate.criterion.Expression" %>
<%@ page import="com.rssl.phizic.dataaccess.hibernate.HibernateAction" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="com.rssl.phizic.business.SimpleService" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%
	String[] executeIds = request.getParameterValues("executeIds");
    String[] rejectIds = request.getParameterValues("rejectIds");
    String requestStr = request.getParameter("requestText");
    boolean isSubmit = request.getParameter("send") != null;
    boolean isSubmitXML = request.getParameter("sendRequestText") != null;
    String error ="";
    String responseStr ="";
    List<AbstractDepoAccountClaim> documents = new ArrayList<AbstractDepoAccountClaim>();
    List<AbstractDepoAccountClaim> execDocuments = new ArrayList<AbstractDepoAccountClaim>();
    if(isSubmit)
    {
        ESBEribBackServiceClientTest service = new ESBEribBackServiceClientTest(request.getParameter("url"));
        {
            try
            {
                responseStr = service.updateDocumentsState(executeIds,rejectIds);
            }
            catch (Exception e)
            {
                error = e.getMessage();
                e.printStackTrace();
            }
        }
    }
    else if(isSubmitXML && requestStr != null)
    {
        ESBEribBackServiceClientTest service = new ESBEribBackServiceClientTest(request.getParameter("url"));
        {
            try
            {
                responseStr = service.updateDocumentsStateXML(requestStr);
            }
            catch (Exception e)
            {
                error = e.getMessage();
                e.printStackTrace();
            }
        }
    }


    try
    {

        documents = new SimpleService().find(DetachedCriteria.forClass(AbstractDepoAccountClaim.class).add(Expression.eq("state.code","DISPATCHED")));
        execDocuments = new SimpleService().find(DetachedCriteria.forClass(AbstractDepoAccountClaim.class).add(Expression.eq("state.code","EXECUTED")));
    }
    catch (BusinessException e)
    {
        error = e.getMessage();
        e.printStackTrace();
    }


%>

<html>
    <head><title>Test ESB Back WebService</title></head>
    <body>
        <script type="text/javascript">
            function showExecutedDepo(checkboxSelected)
            {
                var table = document.getElementById('execDepoDocuments');
                if (checkboxSelected)
                {
                    table.style.display = 'block';
                }
                else
                {
                    table.style.display = 'none';
                }
            }
        </script>
        <form action="" method="POST">
            Укажите адрес веб-сервиса:
            <select id="urlType" onchange="changeUrl()">
                <option value="ESBERIBListener">ESBERIBListener</option>
                <option value="ESBERIBProxyListener">ESBERIBProxyListener</option>
                <option value="OTHER">Другой</option>
            </select>
            <input type="text" name="url" id="url" size="100"/>
            <c:if test="<%=!documents.isEmpty()%>">
                <table border="1">
                    <tr>
                        <td>Номер документа</td>
                        <td>Дата создания</td>
                        <td>Имя формы</td>
                        <td>Исполнить</td>
                        <td>Отказать</td>
                    </tr>
                    <c:forEach items="<%=documents%>" var="document">
                        <tr>
                            <td>
                                ${document.documentNumber}
                            </td>
                            <td>
                                <fmt:formatDate value="${document.dateCreated.time}" pattern="dd.MM.yyyy"/>
                            </td>
                            <td>
                                ${document.formName}
                            </td>
                            <td>
                                <input type="checkbox" name="executeIds" value="${document.externalId}"/>
                            </td>
                            <td>
                                <input type="checkbox" name="rejectIds" value="${document.externalId}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <br/>
                <input type="submit" name="send" value="изменить статус документов"/><br/>
            </c:if>
            <br/>
            <table border="1">
                <tr>
                    <td><input type="checkbox" onclick="showExecutedDepo(this.checked)"/>Показать исполненные депозитарные документы</td>
                </tr>
            </table>
            <c:if test="<%=execDocuments.isEmpty()%>">
                <h2>Нет исполненных депозитарных документов</h2>
            </c:if>
            <c:if test="<%=!execDocuments.isEmpty()%>">
                <table id="execDepoDocuments" border="1" style="display:none;">
                    <tr>
                        <td>Номер документа</td>
                        <td>Дата создания</td>
                        <td>Имя формы</td>
                        <td>Отказать</td>
                    </tr>
                    <c:forEach items="<%=execDocuments%>" var="document">
                        <tr>
                            <td>
                                ${document.documentNumber}
                            </td>
                            <td>
                                <fmt:formatDate value="${document.dateCreated.time}" pattern="dd.MM.yyyy"/>
                            </td>
                            <td>
                                ${document.formName}
                            </td>
                            <td>
                                <input type="checkbox" name="rejectIds" value="${document.externalId}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <br/>
                <input type="submit" name="send" value="изменить статус документов"/><br/>
            </c:if>
            <c:if test="<%=!StringHelper.isEmpty(error)%>">
                При выполнении произошла ошибка: <font color="red"><%=error%></font>
            </c:if>
            <%--textArea для проверки XML сообщения на изменение статуса документов--%>
            <br/>
            Запрос в ESBEribListener :
            <br/>
            <textarea name="requestText" type="text"  style="width:60%;height:400px" cols="30" rows="30">${param.requestText}</textarea>
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