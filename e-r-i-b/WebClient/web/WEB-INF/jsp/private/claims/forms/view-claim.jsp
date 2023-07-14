<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/claims/view" onsubmit="return setEmptyAction(event)">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<c:set var="listFormName" value="${form.metadata.listFormName}"/>	

	<c:set var="layoutDefinition" value="claimMain"/>
	<c:if test="${form.form=='LoanClaim'}">
	    <c:set var="layoutDefinition" value="loanMain"/>
	</c:if>

	<tiles:insert definition="${layoutDefinition}">
		<!-- заголовок -->
		<tiles:put name="pageTitle" value="${form.title}"/>

		<!--меню-->
		<tiles:put name="menu" type="string">
			<c:if test="${form.form!='LoanClaim'}">
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.addNew"/>
					<tiles:put name="commandHelpKey" value="button.addNew"/>
					<tiles:put name="image" value=""/>
					<tiles:put name="bundle" value="claimsBundle"/>
					<tiles:put name="action" value="/private/claims/claim.do?form=${form.form}"/>
				</tiles:insert>

				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.listClaims"/>
					<tiles:put name="commandHelpKey" value="button.listClaims.help"/>
					<tiles:put name="bundle"         value="claimsBundle"/>
					<tiles:put name="image"          value=""/>
					<tiles:put name="action"         value="/private/claims.do"/>
				</tiles:insert>
			</c:if>

			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.close"/>
				<tiles:put name="commandHelpKey" value="button.close.help"/>
				<tiles:put name="bundle"         value="claimsBundle"/>
				<tiles:put name="image"          value=""/>
				<tiles:put name="action"         value="/private/claims/claims.do?name=${listFormName}"/>
			</tiles:insert>

			<c:if test="${form.form!='LoanClaim'}">
                <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/claims/print.do')}"/>

				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.print"/>
					<tiles:put name="commandHelpKey" value="button.print"/>
					<tiles:put name="bundle"         value="claimsBundle"/>
					<tiles:put name="image"          value=""/>
					<tiles:put name="onclick">
						openWindow(null, '${url}?id=${form.id}')
					</tiles:put>
				</tiles:insert>
			</c:if>
		</tiles:put>

		<!-- данные -->
		<tiles:put name="data" type="string">  
		<tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="stamp" value="${(form.document.state.code=='DISPATCHED'||form.document.state.code=='STATEMENT_READY')?'received':''}"/>
			<tiles:put name="alignTable" value="center"/>
			<tiles:put name="id" value="${form.metadata.form.name}"/>
			<tiles:put name="name" value="${form.metadata.form.description}"/>
			<tiles:put name="description" value="${form.metadata.form.detailedDescription}"/>			
			<tiles:put name="data">
				${form.html}
			</tiles:put>

		</tiles:insert>
			<c:if test="${not empty form.document.state.code}">
					<div id="stateDescription" onmouseover="showLayer('state','stateDescription','default');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:37px;overflow:auto;">
                        <bean:message key="payment.state.hint.${form.document.state.code}" bundle="paymentsBundle"/>
                    </div>
			</c:if>
		</tiles:put>



	</tiles:insert>


</html:form>

