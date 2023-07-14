<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/claims/claims" onsubmit="return setEmptyAction(event)">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<c:set var="metadata" value="${form.metadata}"/>
	
	<c:set var="layoutDefinition" value="claimMain"/>
	<c:if test="${form.name=='LoanClaim'}">
	    <c:set var="layoutDefinition" value="loanMain"/>
	</c:if>

	<tiles:insert definition="${layoutDefinition}">
		<tiles:put name="submenu" value="${form.name}"/>
		<c:if test="${form.name=='LoanClaim'}">
			<tiles:put name="submenu" value="LoanClaimList"/>
		</c:if>
		<tiles:put name="pageTitle" value="${form.title}"/>

		<!-- Меню -->

		<c:if test="${form.name!='LoanClaim'}">
			<tiles:put name="menu" type="string">
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.addNew"/>
					<tiles:put name="commandHelpKey" value="button.addNew"/>
					<tiles:put name="image" value=""/>
					<tiles:put name="bundle" value="claimsBundle"/>
					<tiles:put name="action" value="/private/claims/claim.do?form=${form.name}"/>
				</tiles:insert>
        		<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey" value="button.remove"/>
					<tiles:put name="commandHelpKey" value="button.remove.document.help"/>
					<tiles:put name="bundle" value="paymentsBundle"/>
					<tiles:put name="validationFunction">
                        function()
                        {
                            checkIfOneItem("selectedIds");
						    return checkSelection('selectedIds', 'Выберите платежи');
                        }
					</tiles:put>
				</tiles:insert>
			</tiles:put>
		</c:if>

		<c:if test="${form.name=='LoanClaim'}">
			<tiles:put name="menu" type="string">
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.newClaim"/>
					<tiles:put name="commandHelpKey" value="button.newClaim.help"/>
					<tiles:put name="bundle" value="securityBundle"/>
					<tiles:put name="image" value=""/>
					<tiles:put name="action" value="/private/loans/claim.do?force=true"/>
				</tiles:insert>

				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey" value="button.remove"/>
					<tiles:put name="commandHelpKey" value="button.remove.document.help"/>
					<tiles:put name="bundle" value="paymentsBundle"/>
					<tiles:put name="validationFunction">
                        function()
                        {
                            checkIfOneItem("selectedIds");
						    return checkSelection('selectedIds', 'Выберите платежи');
                        }
					</tiles:put>
				</tiles:insert>
			</tiles:put>
		</c:if>

		<!-- фильтр -->
		<tiles:put name="filter" type="string">
			${form.filterHtml}
		</tiles:put>

		<!-- данные -->
		<tiles:put name="data" type="string">
			<tiles:insert definition="tableTemplate" flush="false">
			<c:choose>
				<c:when test="${form.listHtml != ''}">

						<tiles:put name="id" value="${form.metadata.name}"/>
						<tiles:put name="image" value="iconMid_operationHistory.gif"/>
						<tiles:put name="text" value="История операций. ${metadata.form.description}"/>						
						<tiles:put name="data">  
							${form.listHtml}
						</tiles:put>

				</c:when>
				<c:otherwise>
					<tiles:put name="isEmpty" value="true"/>					
				</c:otherwise>
			</c:choose>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>