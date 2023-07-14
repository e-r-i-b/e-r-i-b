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

<html:form action="/private/payments/template/view" onsubmit="return setEmptyAction(event)">
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
            <c:set var="ofDocumentView" value="���������"/><%-- ��� ���������: ��������� ��� ������ � ����������� ������. --%>
            <c:set var="payButtonBundleName" value="button.payDepo"/>
            <c:set var="payButtonBundleHelpName" value="button.payDepo.help"/>
        </c:when>
        <c:otherwise>
            <c:set var="ofDocumentView" value="�������"/>
            <c:set var="payButtonBundleName" value="button.pay"/>
            <c:set var="payButtonBundleHelpName" value="button.pay.help"/>
        </c:otherwise>
    </c:choose>
    <c:set var="formDescription"
       value="�� ���� �������� �� ������ ��������� ������ ��� �������, �������� ��� ������� ������ ${ofDocumentView}.
       ��� ���������� ${isDepositaryClaim ? '��������' : '�������'} ������� �� ������ &laquo;${isDepositaryClaim ? '���������' :'��������'}&raquo;."/>


	<tiles:insert definition="paymentCurrent">
        <tiles:put name="mainmenu" value=""/>
		<tiles:put name="submenu" type="string" value="${metadataPath}"/>
		<%-- ��������� --%>
		<tiles:put name="pageTitle"><c:out value="${form.title}"/></tiles:put>
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="��� �������"/>
                <tiles:put name="action" value="/private/favourite/list/PaymentsAndTemplates.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="������ �������"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        <c:if test="${isNewP2P}">
            <tiles:put name="rightSection" value="false"/>
        </c:if>

		<%-- ������ --%>
		<tiles:put name="data" type="string">
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
                        <tiles:put name="name" value="����� ��������"/>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="���������� ����������"/>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="�������������"/>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="������ ��������"/>
                        <tiles:put name="current" value="true"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="description" value="${formDescription}"/>
                <c:if test="${isNewP2P}">
                    <tiles:put name="showHeader" value="false"/>
                </c:if>
				<tiles:put name="data">
					${form.html}
                    <c:if test="${isNewP2P}"><div class="fakeFormRowNew form-row-edit"></c:if>
                    <tiles:insert definition="fieldWithHint" flush="false">
                        <tiles:put name="fieldName" value="�������� �������:"/>
                        <tiles:put name="externalId" value="templateName"/>
                        <tiles:put name="fieldType" value="text"/>
                        <tiles:put name="description">������� ��������, ��� ������� ������ ������ ���������� � ������ ����� ��������.</tiles:put>
                        <tiles:put name="data">
                            <c:choose>
                                <c:when test="${isNewP2P}">
                                    <div class="linear">
                                        <span id="nameTemplate">
                                           <b><bean:write name="form" property="title"/></b>
                                        </span> &nbsp;
                                        <a class="blueGrayLinkDotted" onclick="javascript:openChageNameWindow();">�������������</a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                   <span id="nameTemplate" class="bold">
                                       <bean:write name="form" property="title"/>
                                    </span>&nbsp;
                                    <a class="blueGrayLinkDotted" onclick="javascript:openChageNameWindow();">�������������</a>
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
                                <tiles:put name="onceDate"          value="${phiz:�alendarToString(reminderInfo.onceDate)}"/>
                            </tiles:insert>
                        <c:if test="${isNewP2P}"></div></c:if>
                    </c:if>

				</tiles:put>
                <%--������ ������������� � �������--%>
                <tiles:put name="buttons">
                    <c:choose>
                        <c:when test="${fromFinanceCalendar}">
                            <c:set var="action" value="/private/finances/financeCalendar.do?fromFinanceCalendar=true&extractId=${extractId}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="action" value="/private/payments"/>
                        </c:otherwise>
                    </c:choose>
                    <script type="text/javascript">

                        var templateId = "${form.id}";
                        var templateUrl = "${phiz:calculateActionURL(pageContext,'/private/payments/template.do')}";
                        function editTemplate()
                        {
                            window.location = templateUrl + "?id="+templateId;
                        }

                        <c:set var="linkPayment" value="${phiz:getLinkByTemplate(form.template)}"/>
                        function makePayment()
                        {
                            window.location = "${phiz:calculateActionURL(pageContext, linkPayment)}?template="+templateId;
                        }
                    </script>
                    <div class="buttonsArea">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey"    value="button.cancel"/>
                            <tiles:put name="commandHelpKey"    value="button.cancel"/>
                            <tiles:put name="bundle"            value="paymentsBundle"/>
                            <tiles:put name="viewType"          value="buttonGrey"/>
                            <tiles:put name="action"            value="${action}"/>
                        </tiles:insert>

                        <c:if test="${form.externalAccountPaymentAllowed && template.activityInfo.availablePay}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="${payButtonBundleName}"/>
                                <tiles:put name="commandHelpKey" value="${payButtonBundleHelpName}"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="onclick" value="makePayment();"/>
                            </tiles:insert>
                        </c:if>
                        <c:if test="${form.externalAccountPaymentAllowed && template.activityInfo.availableEdit}">
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey"     value="button.edit_template"/>
                                <tiles:put name="commandHelpKey" value="button.template.edit"/>
                                <tiles:put name="viewType"       value="buttonGrey"/>
                                <tiles:put name="bundle"         value="paymentsBundle"/>
                                <tiles:put name="stateObject"    value="template"/>
                            </tiles:insert>
                        </c:if>
                        <tiles:insert definition="confirmationButton" flush="false">
                            <tiles:put name="winId" value="confirmationRemoveTemplate" />
                            <tiles:put name="title" value="������������� �������� �������"/>
                            <tiles:put name="message" value="�� ������������� ������ ������� ��������� ������?"/>
                            <tiles:put name="currentBundle" value="paymentsBundle"/>
                            <tiles:put name="confirmKey" value="button.template.delete"/>
                            <tiles:put name="confirmCommandKey" value="button.remove"/>
                            <tiles:put name="buttonViewType" value="simpleLink"/>
                        </tiles:insert>
                        <div class="clear"></div>
                    </div>
				</tiles:put>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
    <%@ include file="/WEB-INF/jsp/private/payments/forms/changeNameTemplateWindow.jsp"%>
</html:form>

