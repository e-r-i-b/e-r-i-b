<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/view" onsubmit="return setEmptyAction(event)">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<c:set var="listFormName" value="${form.metadata.listFormName}"/>
	<c:set var="metadataPath" value="${form.metadataPath}"/>
	<c:set var="metadata" value="${form.metadata}"/>
    <c:set var="state" value="${form.document.state.code}"/>

	<tiles:insert definition="paymentCurrent">
		<tiles:put name="submenu" type="string" value="${metadataPath}"/>
		<!-- заголовок -->
		<tiles:put name="pageTitle" value="${form.title}"/>

		<!--меню-->
		<tiles:put name="menu" type="string">

			<c:if test="${!form.metadata.needParent}">
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.addNew"/>
					<tiles:put name="commandHelpKey" value="button.addNew"/>
					<tiles:put name="bundle" value="paymentsBundle"/>
					<tiles:put name="action" value="/private/payments/add.do?form=${form.form}"/>
				</tiles:insert>
			</c:if>

			<c:if test="${form.metadata.name == 'RecallPayment'}">
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.close"/>
					<tiles:put name="commandHelpKey" value="button.close.help"/>
					<tiles:put name="bundle"         value="paymentsBundle"/>
					<tiles:put name="image"          value="back.gif"/>
					<tiles:put name="action"         value="/private/payments/common.do?status=all"/>
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

		<!-- данные -->
		<tiles:put name="data" type="string">
			<div>
			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value="${metadata.form.name}"/>
				<tiles:put name="alignTable" value="center"/>
				<tiles:put name="name" value="${metadata.form.description}"/>
				<tiles:put name="description" value="${metadata.form.detailedDescription}"/>
				<tiles:put name="docDate" value="${form.dateString}"/>
				<c:if test="${state=='DISPATCHED' || state=='STATEMENT_READY'}">
                    <tiles:put name="stamp" value="received"/>
                </c:if>
                <c:if test="${state=='EXECUTED'}">
                    <tiles:put name="stamp" value="executed"/>
                </c:if>
                <c:if test="${state=='REFUSED'}">
                    <tiles:put name="stamp" value="refused"/>
                </c:if>
				<tiles:put name="bankBIC" value="${form.bankBIC}"/>
				<tiles:put name="bankName" value="${form.bankName}"/>
				<tiles:put name="data">
					${form.html}
				</tiles:put>
				<tiles:put name="buttons">
					<c:if test="${form.canWithdraw}">
						<tiles:insert definition="clientButton" service="RecallPayment" flush="false">
							<tiles:put name="commandTextKey" value="button.withdraw"/>
							<tiles:put name="commandHelpKey" value="button.withdraw.help"/>
							<tiles:put name="bundle" value="paymentsBundle"/>
							<tiles:put name="image" value="iconSm_withdraw.gif"/>
							<tiles:put name="action"
							   value="/private/payments/add.do?form=RecallPayment&parentId=${form.id}"/>
						</tiles:insert>
					</c:if>
					<c:if test="${form.canEdit}">
						<tiles:insert definition="clientButton" flush="false">
							<tiles:put name="commandTextKey" value="button.edit-payment"/>
							<tiles:put name="commandHelpKey" value="button.edit-payment.help"/>
							<tiles:put name="image" value="iconSm_edit.gif"/>
							<tiles:put name="bundle" value="paymentsBundle"/>
							<tiles:put name="action"
									   value="/private/payments/add.do?form=${form.metadata.editOptions.formName}&parentId=${form.id}"/>
						</tiles:insert>
					</c:if>
                    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/payments/print.do')}"/>
					<tiles:insert definition="clientButton" flush="false" operation="PrintDocumentOperation" service="${listFormName}">
						<tiles:put name="commandTextKey" value="button.print"/>
						<tiles:put name="commandHelpKey" value="button.print"/>
						<tiles:put name="bundle" value="paymentsBundle"/>
						<tiles:put name="image" value="iconSm_print.gif"/>
						<tiles:put name="onclick">
								openWindow(null, '${url}?id=${form.id}')
							</tiles:put>
					</tiles:insert>
				</tiles:put>
			</tiles:insert>
			<c:if test="${not empty state}">
					<div id="stateDescription" onmouseover="showLayer('state','stateDescription','default');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:37px;overflow:auto;">
                        <bean:message key="payment.state.hint.${state}" bundle="paymentsBundle"/>
                    </div>
			</c:if>
		</tiles:put>
		</div>
	</tiles:insert>

</html:form>