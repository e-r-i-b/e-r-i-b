<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<c:set var="form" value="${RecoverPasswordForm}"/>
<tiles:insert definition="jSonResponseFail">
    <tiles:put name="errorType" value="showError"/>
    <tiles:put name="messageId" value="${form.messageId}"/>
    <tiles:put name="messageText" value="${form.messageText}"/>
    <tiles:put name="token" value="${sessionScope['org.apache.struts.action.TOKEN']}"/>
</tiles:insert>