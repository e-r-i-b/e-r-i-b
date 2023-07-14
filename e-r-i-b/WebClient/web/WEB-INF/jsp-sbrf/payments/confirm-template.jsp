<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/template/confirm" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="template" value="${form.template}"/>
<c:set var="reminderInfo" value="${template.reminderInfo}"/>
<c:set var="listFormName" value="${form.metadata.listFormName}"/>
<c:set var="metadata" value="${form.metadata}"/>
<c:set var="anotherStrategy" value="${form.anotherStrategyAvailable}"/>
<c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
<c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.template)}"/>
<c:set var="isDepositaryClaim"      value="${ metadata.form.name == 'SecurityRegistrationClaim' || metadata.form.name == 'SecuritiesTransferClaim' || metadata.form.name == 'DepositorFormClaim' || metadata.form.name == 'RecallDepositaryClaim'}"/>
<c:set var="isNewP2P" value="${metadata.form.name == 'NewRurPayment' || metadata.form.name == 'CreateP2PAutoTransferClaim' || metadata.form.name == 'EditP2PAutoTransferClaim' || metadata.form.name == 'RecoveryP2PAutoTransferClaim' || metadata.form.name == 'CloseP2PAutoTransferClaim' || metadata.form.name == 'DelayP2PAutoTransferClaim'}"/>
    <c:choose>
       <c:when test="${isDepositaryClaim}">
           <c:set var="mainmenuButton" value="Depo"/>
           <c:set var="ofDocumentView" value="поручения"/><%-- Вид документа: поречение или платеж в родительном падеже. --%>
       </c:when>
       <c:otherwise>
           <c:set var="mainmenuButton" value=""/>
           <c:set var="ofDocumentView" value="платежа"/>
       </c:otherwise>
    </c:choose>

<tiles:insert definition="paymentCurrent">
<tiles:put name="mainmenu" value="${mainmenuButton}" />
<tiles:put name="submenu" type="string" value="${listFormName}"/>
<c:if test="${isNewP2P}">
    <tiles:put name="rightSection" value="false"/>
</c:if>

<%-- заголовок --%>
<tiles:put name="pageTitle" type="string">
	Создание шаблона
</tiles:put>

<%--меню--%>
<tiles:put name="menu" type="string">

	<c:if test="${!form.metadata.needParent}">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.addNew"/>
			<tiles:put name="commandHelpKey" value="button.addNew"/>
			<tiles:put name="bundle" value="paymentsBundle"/>
			<tiles:put name="action" value="/private/payments/add.do?form=${form.formName}"/>
		</tiles:insert>
	</c:if>
	<c:if test="${form.metadata.filterForm != null}">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.close"/>
		<tiles:put name="commandHelpKey" value="button.close.help"/>
		<tiles:put name="bundle"         value="paymentsBundle"/>
		<tiles:put name="action"         value="/private/payments/payments.do?name=${listFormName}"/>
	</tiles:insert>
	</c:if>
</tiles:put>

<%--"хлебные крошки"--%>
 <tiles:put name="breadcrumbs">
    <tiles:insert definition="breadcrumbsLink" flush="false">
        <tiles:put name="main" value="true"/>
        <tiles:put name="action" value="/private/accounts.do"/>
    </tiles:insert>
    <c:choose>
        <c:when test="${isDepositaryClaim}">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Счета депо"/>
                <tiles:put name="action" value="/private/depo/list.do"/>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Мои шаблоны"/>
                <tiles:put name="action" value="/private/favourite/list/PaymentsAndTemplates.do"/>
            </tiles:insert>
        </c:otherwise>
    </c:choose>
    <tiles:insert definition="breadcrumbsLink" flush="false">
        <tiles:put name="name" value="Создание шаблона"/>
        <tiles:put name="last" value="true"/>
    </tiles:insert>
</tiles:put>
<tiles:put name="data" type="string"> ${confirmTemplate}

    <tiles:insert definition="paymentForm" flush="false">
		<tiles:put name="id" value="${metadata.form.name}"/>
		<tiles:put name="name" value="Создание шаблона"/>
        <tiles:put name="title" value="Создание шаблона"/>
        <c:set var="confirmMessage">
            <c:choose>
                <c:when test="${confirmRequest.strategyType eq 'card'}">
                    После этого введите пароль с чека и нажмите на кнопку &laquo;Подтвердить&raquo;.
                </c:when>
                <c:when test="${confirmRequest.strategyType eq 'sms'}">
                    После этого подтвердите операцию SMS-паролем<c:if test="${anotherStrategy}"> или введите пароль с чека</c:if>.
                </c:when>
                <c:when test="${confirmRequest.strategyType eq 'cap'}">
                    После этого введите отображенное на карте число и нажмите на кнопку &laquo;Подтвердить&raquo;.
                </c:when>
                <c:when test="${confirmRequest.strategyType eq 'push'}">
                    После этого подтвердите операцию push-паролем<c:if test="${anotherStrategy}"> или введите пароль с чека</c:if>.
                </c:when>
                <c:otherwise>
                    После этого нажмите на кнопку &laquo;Подтвердить&raquo;.
                </c:otherwise>
            </c:choose>
        </c:set>
       	<tiles:put name="description" value="Внимательно проверьте реквизиты шаблона ${ofDocumentView}. ${confirmMessage}"/>
        <c:if test="${isNewP2P}">
            <tiles:put name="showHeader" value="false"/>
        </c:if>
        <tiles:put name="isTemplate" value="true"/>
		<tiles:put name="alignTable" value="center"/>
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
                <tiles:put name="current" value="true"/>
            </tiles:insert>
            <tiles:insert definition="stripe" flush="false">
                <tiles:put name="name" value="статус операции"/>
            </tiles:insert>
        </tiles:put>
		<tiles:put name="data">

            <c:set var="message">
                <c:choose>
                    <c:when test="${confirmRequest.strategyType eq 'card'}">
                        Внимательно проверьте, правильно ли Вы заполнили реквизиты шаблона ${ofDocumentView}. Для подтверждения операции введите пароль с чека и нажмите на кнопку &laquo;Подтвердить&raquo;.
                    </c:when>
                    <c:when test="${confirmRequest.strategyType eq 'sms'}">
                        <bean:message key="sms.payments.security.param.message" bundle="securityBundle" arg0="реквизиты шаблона ${ofDocumentView}"/>
                    </c:when>
                    <c:when test="${confirmRequest.strategyType eq 'push'}">
                        <bean:message key="push.payments.security.param.message" bundle="securityBundle" arg0="реквизиты шаблона ${ofDocumentView}"/>
                    </c:when>
                    <c:otherwise>
                        Внимательно проверьте, правильно ли Вы заполнили реквизиты шаблона ${ofDocumentView}. Для подтверждения операции нажмите на кнопку &laquo;Подтвердить&raquo;.
                    </c:otherwise>
                </c:choose>
            </c:set>
            <c:set var="formData">
	    	    ${form.html}
                <c:if test="${isNewP2P}"><div class="fakeFormRowNew form-row-edit"></c:if>
                <tiles:insert definition="fieldWithHint" flush="false">
                     <tiles:put name="fieldName" value="Название шаблона:"/>
                     <tiles:put name="externalId" value="templateName"/>
                     <tiles:put name="fieldType" value="text"/>
                     <tiles:put name="description">Название, под которым данный шаблон сохранится в списке Ваших шаблонов.</tiles:put>
                        <tiles:put name="data">
                            <c:choose>
                                <c:when test="${isNewP2P}">
                                    <div class="linear">
                                        <b><bean:write name="form" property="template.templateInfo.name"/></b>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <span class="bold"><bean:write name="form" property="template.templateInfo.name"/></span>
                                </c:otherwise>
                            </c:choose>
                        </tiles:put>
                </tiles:insert>
                <c:if test="${isNewP2P}"></div></c:if>
                <c:if test="${phiz:impliesService('ReminderManagment')}">
                    <c:if test="${isNewP2P}"><div class="fakeFormRowNew form-row-edit"></c:if>
                        <tiles:insert page="/WEB-INF/jsp-sbrf/reminder/reminderFormRow.jsp" flush="false">
                            <tiles:put name="mode"              value="view"/>
                            <tiles:put name="enableReminder"    value="${reminderInfo != null}"/>
                            <tiles:put name="reminderType"      value="${reminderInfo.type}"/>
                            <tiles:put name="dayOfMonth"        value="${reminderInfo.dayOfMonth}"/>
                            <tiles:put name="monthOfQuarter"    value="${reminderInfo.monthOfQuarter}"/>
                            <tiles:put name="onceDate"          value="${phiz:сalendarToString(reminderInfo.onceDate)}"/>
                        </tiles:insert>
                    <c:if test="${isNewP2P}"></div></c:if>
                </c:if>
            </c:set>
            ${formData}
		</tiles:put>
        <tiles:put name="buttons">
            <%@ page contentType="text/html;charset=windows-1251" language="java" %>
            <%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
            <%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
            <%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
            <%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>


            <div class="backToService backToServiceBottom">
                <c:if test="${template.activityInfo.availableEdit}">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.edit_template"/>
                        <tiles:put name="commandHelpKey" value="button.template.edit"/>
                        <tiles:put name="viewType"       value="buttonGrey"/>
                        <tiles:put name="bundle"         value="paymentsBundle"/>
                        <tiles:put name="stateObject"    value="template"/>
                        <tiles:put name="imageUrl"       value="${globalUrl}/commonSkin/images/backIcon.png"/>
                        <tiles:put name="imageHover"     value="${globalUrl}/commonSkin/images/backIconHover.png"/>
                        <tiles:put name="imagePosition"  value="left"/>
                    </tiles:insert>
                </c:if>
            </div>

             <div class="buttonsArea">

                 <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="action" value="/private/payments"/>
                </tiles:insert>
                <c:if test="${form.template.state != 'TEMPLATE'}">
                    <tiles:insert definition="confirmationButton" flush="false">
                        <tiles:put name="winId">confirmationRemoveTemplate</tiles:put>
                        <tiles:put name="title">Подтверждение удаления шаблона</tiles:put>
                        <tiles:put name="currentBundle">commonBundle</tiles:put>
                        <tiles:put name="confirmCommandKey">button.remove</tiles:put>
                        <tiles:put name="message">Вы действительно хотите удалить данный шаблон?</tiles:put>
                    </tiles:insert>
                </c:if>
                <c:if test="${template.activityInfo.availableConfirm}">
                    <c:choose>
                        <c:when test="${not empty confirmRequest}">
                            <c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
                            <c:choose>
                                <c:when test="${reminderInfo != null}">
                                    <c:set var="wordForTitle" value="напоминания"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="wordForTitle" value="шаблона"/>
                                </c:otherwise>
                            </c:choose>
                            <tiles:insert  definition="${confirmTemplate}" flush="false">
                                <tiles:put name="title" value="Подтверждение ${wordForTitle}"/>
                                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                <tiles:put name="anotherStrategy" beanName="anotherStrategy"/>
                                <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                <tiles:put name="message" value="${message}"/>
                                <tiles:put name="confirmCommandKey" value="button.dispatch"/>
                                <tiles:put name="confirmableObject" value="${metadata.form.name}"/>
                                <tiles:put name="data">
                                    ${formData}
                                </tiles:put>
                            </tiles:insert>
                        </c:when>
                        <c:otherwise>
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey"     value="button.dispatch"/>
                                <tiles:put name="commandHelpKey" value="button.dispatch.help"/>
                                <tiles:put name="bundle"         value="paymentsBundle"/>
                                <tiles:put name="isDefault"        value="true"/>
                                <tiles:put name="stateObject"    value="template"/>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <div class="clear"></div>
            </div>

		</tiles:put>
	</tiles:insert>

    <c:if test="${phiz:isScriptsRSAActive()}">
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/hashtable.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>

        <%-- подготовка данных по deviceTokenFSO для ВС ФМ --%>
        <%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso-support.jsp"%>

        <script type="text/javascript">
            <%-- формирование основных данных для ФМ --%>
            new RSAObject().toHiddenParameters();
        </script>
    </c:if>
</tiles:put>
</tiles:insert>

</html:form>

