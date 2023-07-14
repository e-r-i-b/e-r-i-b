<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
    <c:set var="form" value="${RecoverPasswordForm}"/>
    <c:set var="redirect" value ="${csa:calculateActionURL(pageContext, '/index')}"/>
    <c:set var="popupId" value="CustomMessage"/>
    <c:set var="messageText" value="Пароль успешно изменён"/>
        <tiles:insert definition="jSonResponseFail">
            <tiles:put name="token" value="${sessionScope['org.apache.struts.action.TOKEN']}"/>
            <tiles:put name="errorType" value="showPopup"/>
            <tiles:put name="popupId" value="${popupId}"/>
            <tiles:put name="messageText" value="${messageText}"/>
            <tiles:put name="onCloseRedirect" value="${redirect}"/>
        </tiles:insert>