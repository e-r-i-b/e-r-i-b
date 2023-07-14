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

<tiles:insert definition="${definition}">
    <tiles:put name="data" type="string">
        <table width="100%" cellspacing="0" cellpadding="0" class="MaxSize">
            <tr>
                <td valign="middle" align="center">
                    <font size="4" color="red">
                        <bean:message bundle="notFoundExceptionMessagesBundle" key="${exceptionMessage}"/>
                    </font>
                </td>
            </tr>
        </table>
    </tiles:put>
</tiles:insert>
