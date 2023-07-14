<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<c:set  scope="request" var="operationName"             value="EditPaymentOperation"/>
<c:set  scope="request" var="serviceName"               value="RecoveryEmployeeAutoSubscriptionPayment"/>

<c:set  scope="request" var="subMenu"                   value="AutoSubscriptions"/>

<c:set  scope="request" var="title"                     value="Заявка на возобновление выполнения автоплатежа."/>
<c:set  scope="request" var="description"               value="Для того чтобы возобновить автоплатеж, проверьте реквизиты заявки и нажмите на кнопку «Сохранить». В результате после исполнения операции автоплатеж будет подключен."/>

<c:set  scope="request" var="linkButtonCancel"          value="/autopayment/info.do?id=${frm.document.number}"/>
<c:set  scope="request" var="linkButtonBackToList"      value="/autopayment/list.do"/>

<c:set  scope="request" var="buttonSaveName"            value="button.save"/>

<c:set scope="request" var="printActionType">
    <bean:message key="actionType.renew" bundle="autopaymentsBundle"/>
</c:set>
<c:set scope="request" var="printActionDescription">
    <bean:message key="actionType.renew.description" bundle="autopaymentsBundle"/>
</c:set>

<c:set  scope="request" var="isAutoSubscription"        value="${true}"/>
<c:set  scope="request" var="isAutoTransfer"            value="${false}"/>
<c:set  scope="request" var="isMoneyBox"                value="${false}"/>

<c:set  scope="request" var="isCreationClaim"           value="${false}"/>
<c:set  scope="request" var="isEditClaim"               value="${false}"/>
<c:set  scope="request" var="isCloseClaim"              value="${false}"/>
<c:set  scope="request" var="isDelayClaim"              value="${false}"/>
<c:set  scope="request" var="isRecoverClaim"            value="${true}"/>

