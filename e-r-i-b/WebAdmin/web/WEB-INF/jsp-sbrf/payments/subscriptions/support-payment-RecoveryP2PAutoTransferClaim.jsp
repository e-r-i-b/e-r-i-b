<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<c:set  scope="request" var="operationName"             value="EditPaymentOperation"/>
<c:set  scope="request" var="serviceName"               value="EmployeeRecoveryP2PAutoTransferClaim"/>

<c:set  scope="request" var="subMenu"                   value="AutoTransfers"/>

<c:set  scope="request" var="title"                     value="Заявка на возобновление выполнения автоперевода."/>
<c:set  scope="request" var="description"               value="Для того чтобы возобновить автоперевод, проверьте реквизиты заявки и нажмите на кнопку «Сохранить». В результате после исполнения операции автоперевод будет подключен."/>

<c:set  scope="request" var="linkButtonCancel"          value="/autotransfer/info.do?id=${frm.document.number}"/>
<c:set  scope="request" var="linkButtonBackToList"      value="/autotransfer/list.do"/>

<c:set  scope="request" var="buttonSaveName"            value="button.save"/>

<c:set scope="request" var="printActionType">
    <bean:message key="actionType.renew" bundle="autopaymentsBundle"/>
</c:set>
<c:set scope="request" var="printActionDescription">
    <bean:message key="actionType.renew.description" bundle="autopaymentsBundle"/>
</c:set>

<c:set  scope="request" var="isAutoSubscription"        value="${false}"/>
<c:set  scope="request" var="isAutoTransfer"            value="${true}"/>
<c:set  scope="request" var="isMoneyBox"                value="${false}"/>

<c:set  scope="request" var="isCreationClaim"           value="${false}"/>
<c:set  scope="request" var="isEditClaim"               value="${false}"/>
<c:set  scope="request" var="isCloseClaim"              value="${false}"/>
<c:set  scope="request" var="isDelayClaim"              value="${false}"/>
<c:set  scope="request" var="isRecoverClaim"            value="${true}"/>

