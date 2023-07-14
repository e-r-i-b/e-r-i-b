<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/claims/list">
<tiles:insert definition="claimsMain">
<tiles:put name="pageTitle" type="string" value="Список заявок"/>

<tiles:put name="menu" type="string"/>

<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.claim.number"/>
			<tiles:put name="bundle" value="claimsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="number"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.claim.state"/>
			<tiles:put name="bundle" value="claimsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(state)" styleClass="filterSelect" style="width:100px">
					<html:option value="">Все</html:option>
					<html:option value="DISPATCHED,STATEMENT_READY">Принята</html:option>
					<html:option value="ACCEPTED">Одобрена</html:option>
					<html:option value="EXECUTED">Исполнена</html:option>
					<html:option value="REFUSED">Отказана</html:option>
				</html:select>
			</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.claim.type"/>
			<tiles:put name="bundle" value="claimsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(type)" styleClass="filterSelect" style="width:170px">
					<html:option value="">Все</html:option>
					<html:option value="AccountOpeningClaim">
						<bean:message key="claim.type.AccountOpeningClaim" bundle="claimsBundle"/>
					</html:option>
					<html:option value="DepositOpeningClaim">
						<bean:message key="claim.type.DepositOpeningClaim" bundle="claimsBundle"/>
					</html:option>
					<html:option value="AccountClosingClaim">
						<bean:message key="claim.type.AccountClosingClaim" bundle="claimsBundle"/>
					</html:option>
					<html:option value="DepositClosingClaim">
						<bean:message key="claim.type.DepositClosingClaim" bundle="claimsBundle"/>
					</html:option>
					<html:option value="DepositReplenishmentClaim">
						<bean:message key="claim.type.DepositReplenishmentClaim" bundle="claimsBundle"/>
					</html:option>
					<html:option value="SMSInformationClaim">
						<bean:message key="claim.type.SMSInformationClaim" bundle="claimsBundle"/>
					</html:option>
				</html:select>
			</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.giveToBank"/>
		<tiles:put name="bundle" value="claimsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="Date"/>
		<tiles:put name="template" value="DATE_TEMPLATE"/>
	</tiles:insert>

	<script type="text/javascript">
		function initTemplates()
		{
		}

		function submit(event)
		{
			var frm = window.opener.document.forms.item(0);

			frm.action = '';

			frm.submit();

		}
		initTemplates();
	</script>
</tiles:put>

<tiles:put name="data" type="string">
	<c:set var="form" value="${ClaimListForm}"/>
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="ServicesTable"/>
            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.refuse"/>
                    <tiles:put name="commandHelpKey" value="button.refuse.help"/>
                    <tiles:put name="bundle"         value="claimsBundle"/>
                </tiles:insert>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.accept"/>
                    <tiles:put name="commandHelpKey" value="button.accept.help"/>
                    <tiles:put name="bundle"         value="claimsBundle"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="head">
                 <td width="40px">
						<input name="isSelectedAllItems" type="checkbox" style="border:none"
						       onclick="switchSelection('isSelectedAllItems','selectedIds')"/>
					</td>
					<td width="100px" nowrap="true">
						<bean:message key="label.claim.number" bundle="claimsBundle"/>
					</td>
					<td width="100px" nowrap="true">
						<bean:message key="label.claim.date" bundle="claimsBundle"/>
					</td>
					<td width="100px" nowrap="true">
						<bean:message key="label.claim.type" bundle="claimsBundle"/>
					</td>
					<td width="50px" nowrap="true">
						<bean:message key="label.claim.state" bundle="claimsBundle"/>
					</td>
					<td nowrap="true">
						<bean:message key="label.claim.office" bundle="claimsBundle"/>
					</td>
            </tiles:put>
            <tiles:put name="data">
				<c:set var="lineNumber" value="0"/>
				<c:forEach var="claim" items="${form.claims}">
					<c:set var="lineNumber" value="${lineNumber+1}"/>
					<c:set var="office" value="${form.officesMap[claim]}"/>
					<c:choose>
						<c:when test="${lineNumber<=form.listLimit}">
                            <tr class="listLine${lineNumber%2}">
                                <td class="listItem" nowrap="true" align="center">
                                    <html:multibox property="selectedIds" style="border:none">
                                        <bean:write name="claim" property="id"/>
                                    </html:multibox>
                                </td>
                                <td class="listItem" nowrap="true">
                                    &nbsp;
                                    <html:link action="/claims/claim.do?id=${claim.id}">
                                        <bean:write name="claim" property="id"/>
                                    </html:link>
                                    &nbsp;
                                </td>
                                <td class="listItem" nowrap="true">
                                    &nbsp;
                                    <bean:write name="claim" property="operationDate.time" format="dd.MM.yyyy HH:mm"/>
                                    &nbsp;
                                </td>
                                <td class="listItem" nowrap="true">
                                    &nbsp;
                                    <bean:message key="claim.type.${claim.formName}" bundle="claimsBundle"/>
                                    &nbsp;
                                </td>
                                <td class="listItem" nowrap="true">
                                    &nbsp;
                                    <bean:message key="claim.state.${claim.state.code}" bundle="claimsBundle"/>
                                    &nbsp;
                                </td>
                                <td class="listItem" nowrap="true">
                                    &nbsp;
                                    <c:if test="${not empty office}">
                                        <bean:write name="office" property="name"/>
                                    </c:if>
                                    &nbsp;
                                </td>
                            </tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="6" class="listItem">
										В результате поиска найдено слишком много заявок.
										На экран выведены первые
										<c:out value="${form.listLimit}"/>
										.
										Задайте более жесткие условия поиска.
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:forEach>
            </tiles:put>
            <tiles:put name="isEmpty" value="${empty form.claims}"/>
		    <tiles:put name="emptyMessage" value="В списке заявок нет элементов!"/>
	</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>
