<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/payments/template/view" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="listFormName" value="${form.metadata.listFormName}"/>
    <c:set var="metadataPath" value="${form.metadataPath}"/>
    <c:set var="metadata" value="${form.metadata}"/>
    <c:set var="template" value="${form.template}"/>
    <c:set var="reminderInfo" value="${template.reminderInfo}"/>
    <c:set var="formName" value="${metadata.form.name}"/>
    <c:set var="isDepositaryClaim" value="${formName == 'SecurityRegistrationClaim' || formName == 'SecuritiesTransferClaim' || formName == 'DepositorFormClaim' || formName == 'RecallDepositaryClaim'}"/>
    <c:set var="fromFinanceCalendar" value="${form.fromFinanceCalendar}"/>
    <c:set var="extractId" value="${form.extractId}"/>
    <c:set var="isNewP2P" value="${metadata.form.name == 'NewRurPayment' || metadata.form.name == 'CreateP2PAutoTransferClaim' || metadata.form.name == 'EditP2PAutoTransferClaim' || metadata.form.name == 'RecoveryP2PAutoTransferClaim' || metadata.form.name == 'CloseP2PAutoTransferClaim' || metadata.form.name == 'DelayP2PAutoTransferClaim'}"/>

    <c:choose>
        <c:when test="${isDepositaryClaim}">
            <c:set var="ofDocumentView" value="поручения"/><%-- Вид документа: поречение или платеж в родительном падеже. --%>
            <c:set var="payButtonBundleName" value="button.payDepo"/>
            <c:set var="payButtonBundleHelpName" value="button.payDepo.help"/>
        </c:when>
        <c:otherwise>
            <c:set var="ofDocumentView" value="платежа"/>
            <c:set var="payButtonBundleName" value="button.pay"/>
            <c:set var="payButtonBundleHelpName" value="button.pay.help"/>
        </c:otherwise>
    </c:choose>
    <c:set var="formDescription"
           value="На этой странице Вы можете совершить платеж или перевод, изменить или удалить шаблон ${ofDocumentView}.
       Для выполнения ${isDepositaryClaim ? 'операции' : 'платежа'} нажмите на кнопку &laquo;${isDepositaryClaim ? 'Выполнить' :'Оплатить'}&raquo;."/>


    <html:hidden property="fromFinanceCalendar" value="${fromFinanceCalendar}"/>
    <html:hidden property="extractId" value="${extractId}"/>
    <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="isTemplate" value="true"/>
        <tiles:put name="title"><c:out value="${form.title}"/></tiles:put>
        <tiles:put name="id" value="${formName}"/>
        <tiles:put name="alignTable" value="center"/>
        <tiles:put name="name" value="${form.title}"/>
        <tiles:put name="stripe">
            <tiles:insert definition="stripe" flush="false">
                <tiles:put name="name" value="выбор операции"/>
                <tiles:put name="future" value="false"/>
            </tiles:insert>
            <tiles:insert definition="stripe" flush="false">
                <tiles:put name="name" value="заполнение реквизитов"/>
                <tiles:put name="future" value="false"/>
            </tiles:insert>
            <tiles:insert definition="stripe" flush="false">
                <tiles:put name="name" value="подтверждение"/>
                <tiles:put name="future" value="false"/>
            </tiles:insert>
            <tiles:insert definition="stripe" flush="false">
                <tiles:put name="name" value="статус операции"/>
                <tiles:put name="current" value="true"/>
            </tiles:insert>
        </tiles:put>
        <c:if test="${isNewP2P}">
            <tiles:put name="showHeader" value="false"/>
        </c:if>
        <tiles:put name="data">
            ${form.html}
            <c:if test="${isNewP2P}"><div class="fakeFormRowNew form-row-edit"></c:if>
            <tiles:insert definition="fieldWithHint" flush="false">
                <tiles:put name="fieldName" value="Название шаблона:"/>
                <tiles:put name="externalId" value="templateName"/>
                <tiles:put name="fieldType" value="text"/>
                <tiles:put name="description">Введите название, под которым данный шаблон сохранится в списке Ваших шаблонов.</tiles:put>
                <tiles:put name="data">
                    <c:choose>
                        <c:when test="${isNewP2P}">
                            <div class="linear">
                                        <span id="nameTemplate">
                                           <b><bean:write name="form" property="title"/></b>
                                        </span> &nbsp;
                            </div>
                        </c:when>
                        <c:otherwise>
                                   <span id="nameTemplate" class="bold">
                                       <bean:write name="form" property="title"/>
                                    </span>&nbsp;
                        </c:otherwise>
                    </c:choose>
                </tiles:put>
            </tiles:insert>
            <c:if test="${isNewP2P}"></div></c:if>
            <c:if test="${phiz:impliesService('ReminderManagment')}">
                <c:if test="${isNewP2P}"><div class="fakeFormRowNew form-row-edit"></c:if>
                <tiles:insert page="/WEB-INF/jsp-sbrf/reminder/reminderFormRow.jsp" flush="false">
                    <tiles:put name="id"                value="${template.id}"/>
                    <tiles:put name="editable"          value="true"/>
                    <tiles:put name="selfSave"          value="true"/>
                    <tiles:put name="enableReminder"    value="${reminderInfo != null}"/>
                    <tiles:put name="reminderType"      value="${reminderInfo.type}"/>
                    <tiles:put name="dayOfMonth"        value="${reminderInfo.dayOfMonth}"/>
                    <tiles:put name="monthOfQuarter"    value="${reminderInfo.monthOfQuarter}"/>
                    <tiles:put name="onceDate"          value="${phiz:сalendarToString(reminderInfo.onceDate)}"/>
                </tiles:insert>
                <c:if test="${isNewP2P}"></div></c:if>
            </c:if>

        </tiles:put>

    </tiles:insert>
</html:form>

