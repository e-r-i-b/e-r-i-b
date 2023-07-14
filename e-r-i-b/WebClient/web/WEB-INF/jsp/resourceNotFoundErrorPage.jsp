<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<%--
Страница ошибки получения объекта (ResourceNotFoundBusinessException)
	logonUser           - залогинился ли пользователь (true - залогинился);
	exceptionMessage    - текст выводимого сообщения.
--%>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:set var="definition" value="blank"/>

<c:if test="${logonUser}">
    <c:set var="definition" value="resourceNotFound"/>
</c:if>

<c:if test="${isAjax}">
    <c:set var="definition" value="resourceNotFoundAsync"/>
</c:if>

<tiles:insert definition="${definition}">
    <tiles:put name="pageTitle" type="string">Ошибка</tiles:put>
    <tiles:put name="needShowMessages" value="false"/>
    <tiles:put name="data" type="string">
        <tiles:insert definition="roundBorderLight" flush="false">
            <tiles:put name="color" value="orange" />
            <tiles:put name="data">
                <div class="message">
                    <bean:message bundle="notFoundExceptionMessagesBundle" key="${exceptionMessage}"/>
                </div>
                <div class="clear"></div>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
