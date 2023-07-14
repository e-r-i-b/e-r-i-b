<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
    Третий шаг процедуры подключения МБ: подтверждение подключения
--%>

<tiles:insert definition="main">
    <tiles:put name="pageTitle" type="string">
        Подключение услуги "Мобильный Банк"
    </tiles:put>
    <tiles:put name="headerGroup" value="true"/>
    <tiles:put name="data" type="string">

        <br/>
        <c:set var="fromSystem" value="true" scope="request"/>
        <html:form action="/private/register-mobilebank/view">
            <%@ include file="view_data.jsp" %>
        </html:form>

    </tiles:put>
</tiles:insert>
