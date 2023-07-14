<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/confirm" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.document)}"/>
<c:set var="listFormName" value="${form.metadata.listFormName}"/>
<c:set var="metadata" value="${form.metadata}"/>

<tiles:insert definition="paymentCurrent">
<tiles:put name="submenu" type="string" value="${form.metadataPath}"/>
<!-- заголовок -->
<tiles:put name="pageTitle" type="string">
	<c:out value="${form.formDescription}"/>
</tiles:put>

<!--меню-->
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

<!-- собственно данные -->
<tiles:put name="data" type="string"> ${confirmTemplate}
	<tiles:insert definition="paymentForm" flush="false">
		<tiles:put name="id" value="${metadata.form.name}"/>
		<tiles:put name="name" value="${metadata.form.description}"/>
		<tiles:put name="description" value="${metadata.form.detailedDescription}"/>
		<tiles:put name="alignTable" value="center"/>
		<tiles:put name="data">
	    	${form.html}
		</tiles:put>
		<tiles:put name="confirmInfo">
			<c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
				<tiles:insert  definition="${confirmTemplate}" flush="false">
					<tiles:put name="confirmRequest" beanName="confirmRequest"/>
					<tiles:put name="message" value="Проверьте правильность заполнения полей документа и нажмите кнопку \"Подтвердить\", чтобы передать документ в банк на исполнение."/>
				</tiles:insert>
		</tiles:put>
		<tiles:put name="buttons">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey" value="button.dispatch"/>
				<tiles:put name="commandHelpKey" value="button.dispatch.help"/>
				<tiles:put name="image" value="iconSm_edit.gif"/>
				<tiles:put name="bundle" value="paymentsBundle"/>
				<tiles:put name="image" value="iconSm_confirm.gif"/>
				<tiles:put name="isDefault" value="true"/>
				<tiles:put name="stateObject" value="document"/>
			</tiles:insert>

			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey" value="button.edit"/>
				<tiles:put name="commandHelpKey" value="button.edit.help"/>
				<tiles:put name="image" value="iconSm_edit.gif"/>
				<tiles:put name="bundle" value="paymentsBundle"/>
				<tiles:put name="stateObject" value="document"/>
			</tiles:insert>

			<c:if test="${form.formName != 'RurPayJurSB'}">
            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/payments/print.do')}"/>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.print"/>
				<tiles:put name="commandHelpKey" value="button.print"/>
				<tiles:put name="bundle"         value="paymentsBundle"/>
				<tiles:put name="image"          value="iconSm_print.gif"/>
				<tiles:put name="onclick">
					openWindow(null, '${url}?id=${form.id}')
				</tiles:put>
			</tiles:insert>
			</c:if>
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

