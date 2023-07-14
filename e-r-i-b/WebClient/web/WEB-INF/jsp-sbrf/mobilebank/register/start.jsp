<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
    Первый шаг процедуры подключения МБ: предложение подключиться, выбор тарифа
--%>

<tiles:importAttribute/>
<%--@elvariable id="globalUrl" type="java.lang.String"--%>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<%--@elvariable id="skinUrl" type="java.lang.String"--%>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:insert definition="main">
    <tiles:put name="pageTitle" type="string" value="Подключение услуги Мобильный Банк"/>
    <tiles:put name="headerGroup" value="true"/>
    <tiles:put name="data" type="string">

        <br/>

        <html:form action="/private/register-mobilebank/start">
            <%@ include file="start_data.jsp" %>
        </html:form>

    </tiles:put>
</tiles:insert>