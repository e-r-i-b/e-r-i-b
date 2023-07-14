<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<c:set  scope="request" var="operationName"             value="EditPaymentOperation"/>
<c:set  scope="request" var="serviceName"               value="EmployeeEditMoneyBoxClaim"/>

<c:set  scope="request" var="subMenu"                   value="MoneyBox"/>

<c:set  scope="request" var="title"                     value="Заявка на редактирование копилки."/>
<c:set  scope="request" var="description"               value="Укажите параметры копилки и нажмите на кнопку «Сохранить»."/>

<c:set  scope="request" var="linkButtonCancel"          value="/moneyBox/list.do"/>
<c:set  scope="request" var="linkButtonBackToList"      value="/moneyBox/list.do"/>

<c:set  scope="request" var="buttonSaveName"            value="button.save"/>

<c:set scope="request" var="printActionType">
    <bean:message key="actionType.edit" bundle="autopaymentsBundle"/>
</c:set>
<c:set scope="request" var="printActionDescription">
    <bean:message key="actionType.edit.description" bundle="autopaymentsBundle"/>
</c:set>

<c:set  scope="request" var="isAutoSubscription"        value="${false}"/>
<c:set  scope="request" var="isAutoTransfer"            value="${false}"/>
<c:set  scope="request" var="isMoneyBox"                value="${true}"/>

<c:set  scope="request" var="isCreationClaim"           value="${false}"/>
<c:set  scope="request" var="isEditClaim"               value="${false}"/>
<c:set  scope="request" var="isCloseClaim"              value="${false}"/>
<c:set  scope="request" var="isDelayClaim"              value="${false}"/>
<c:set  scope="request" var="isRecoverClaim"            value="${false}"/>
