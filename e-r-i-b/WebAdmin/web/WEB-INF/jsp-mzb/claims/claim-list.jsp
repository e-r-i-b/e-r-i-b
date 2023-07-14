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

<%-- фильтр --%>
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
					<html:option value="W">Принята</html:option>
					<html:option value="A">Одобрена</html:option>
					<html:option value="S">Исполнена</html:option>
					<html:option value="E">Отказана</html:option>
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
					<html:option value="UnblockingCardClaim">
						<bean:message key="claim.type.UnblockingCardClaim" bundle="claimsBundle"/>
					</html:option>
					<html:option value="SMSInformationClaim">
						<bean:message key="claim.type.SMSInformationClaim" bundle="claimsBundle"/>
					</html:option>
					<html:option value="NotReIssueCardClaim">
						<bean:message key="claim.type.NotReIssueCardClaim" bundle="claimsBundle"/>
					</html:option>
					<html:option value="CardChargeLimitClaim">
						<bean:message key="claim.type.CardChargeLimitClaim" bundle="claimsBundle"/>
					</html:option>
					<html:option value="IssueCardClaim">
						<bean:message key="claim.type.IssueCardClaim" bundle="claimsBundle"/>
					</html:option>
					<html:option value="ReIssueCardClaim">
						<bean:message key="claim.type.ReIssueCardClaim" bundle="claimsBundle"/>
					</html:option>
					<html:option value="StopListCardClaim">
						<bean:message key="claim.type.StopListCardClaim" bundle="claimsBundle"/>
					</html:option>
					<html:option value="CardMootTransClaim">
						<bean:message key="claim.type.CardMootTransClaim" bundle="claimsBundle"/>
					</html:option>
				</html:select>
			</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.giveToBank"/>
		<tiles:put name="bundle" value="claimsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="Date"/>
	</tiles:insert>

        <tiles:importAttribute/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
	    <c:set var="imagePath" value="${skinUrl}/images"/>

	<script type="text/javascript">
		document.imgPath = "${imagePath}/";
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

<%-- данные --%>
<tiles:put name="data" type="string">
	<table cellpadding="0" cellspacing="0" class="MaxSize">
    <tr>
	<td height="100%">
	<c:set var="form" value="${ClaimListForm}"/>

	<c:choose>
		<c:when test="${not empty form.claims}">
			<table cellspacing="0" cellpadding="0" width="100%" class="userTab"
			       style="margin-top:4px;" id="ServicesTable">

				<tr class="titleTable">
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
				</tr>

				<c:forEach var="claim" items="${form.claims}" varStatus="lineInfo">
					<c:if test="${not (claim.formName=='IssueAdditionalCardClaim')}">
						<c:set var="office" value="${form.officesMap[claim]}"/>
						<tr class="listLine${lineInfo.count % 2}">
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
								<bean:write name="claim" property="dateCreated.time" format="dd.MM.yyyy"/>
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
					</c:if>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<table width="100%" cellpadding="4">
				<tr>
					<td class="messageTab" align="center">В списке заявок нет элементов!</td>
				</tr>
			</table>
			<script>hideTitle("ServicesTable");</script>
		</c:otherwise>
	</c:choose>
	</td>
	</tr>
	</table>
</tiles:put>
</tiles:insert>
</html:form>
