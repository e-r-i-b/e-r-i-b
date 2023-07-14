<%@ page import="javax.servlet.ServletContext" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/persons/empowered/edit" onsubmit="return setEmptyAction(event);">

<c:set var="form" value="${EditEmpoweredPersonForm}"/>
<c:set var="isNew"      value="${empty form.empoweredPerson.id}"/>

<tiles:insert definition="personEdit">
	<tiles:put name="submenu" type="string" value="EmpoweredPersons"/>
<tiles:put name="pageTitle" type="string">
	<c:choose>
		<c:when test="${isNew}">
			<bean:message key="edit.user.new.empowered.title" bundle="personsBundle"/>
		</c:when>
		<c:otherwise>
			<bean:message key="edit.user.empowered.title" bundle="personsBundle"/>
		</c:otherwise>
	</c:choose>
</tiles:put>

<tiles:put name="menu" type="string">
	<c:set var="isNewOrTemplate" value="${empty form.empoweredPerson.id or form.empoweredPerson.status == 'T'}"/>
    <c:set var="isTemplate" value="${form.empoweredPerson.status == 'T'}"/>
    <c:set var="personId" value="${form.empoweredPerson.id}"/>
	<c:set var="person" value="${form.activePerson}"/>
	<c:set var="isShowSaves" value="${not (person.status == 'T' && phiz:isAgreementSignMandatory()) }"/>
	<c:if test="${isNewOrTemplate && isShowSaves}">
		<tiles:insert definition="commandButton" flush="false" operation="EditEmpoweredPersonOperation">
			<tiles:put name="commandKey" value="button.partly.save.person"/>
			<tiles:put name="commandHelpKey" value="button.partly.save.empowered.help"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="viewType" value="buttonGrey"/>
		</tiles:insert>
    </c:if>

	<c:if test="${not isNew and isTemplate and form.activePerson.status == 'A' and isShowSaves}">
		<tiles:insert definition="commandButton" flush="false" operation="RegisterClientOperation">
			<tiles:put name="commandKey" value="button.activate.person"/>
			<tiles:put name="commandHelpKey" value="button.activate.empowered.help"/>
			<tiles:put name="commandTextKey" value="button.activate.empowered"/>
			<tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="viewType" value="buttonGrey"/>
		</tiles:insert>
	</c:if>

	<c:if test="${not isNewOrTemplate && isShowSaves}">
		<tiles:insert definition="commandButton" flush="false" operation="EditEmpoweredPersonOperation">
			<tiles:put name="commandKey" value="button.save"/>
			<tiles:put name="commandHelpKey" value="button.save.empowered.help"/>
			<tiles:put name="commandTextKey" value="button.save.empowered"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="viewType" value="buttonGrey"/>
		</tiles:insert>
	</c:if>
	<c:if test="${isNewOrTemplate or isSignAgreement}">
		<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.print"/>
				<tiles:put name="commandHelpKey" value="button.print"/>
				<tiles:put name="bundle" value="personsBundle"/>
				<tiles:put name="onclick" value="javascript:printClientInfo(event,${empty form.id ? 'null': form.id},'');"/>
            <tiles:put name="viewType" value="buttonGrey"/>
		</tiles:insert>
	</c:if>

	<c:if test="${not empty form.person && isShowSaves}">
		<c:set var="blocked" value="${!empty form.empoweredPerson.login.blocks}"/>
		<c:if test="${not isNewOrTemplate}">
			<c:choose>
				<c:when test="${blocked}">
					<tiles:insert definition="commandButton" flush="false" operation="ChangeLockPersonOperation">
						<tiles:put name="commandKey" value="button.unlock"/>
						<tiles:put name="commandHelpKey" value="button.unlock"/>
						<tiles:put name="bundle" value="personsBundle"/>
						<tiles:put name="confirmText"    value="Вы действительно хотите снять блокировку с пользователя?"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
					</tiles:insert>
				</c:when>
				<c:otherwise>
					<tiles:insert definition="clientButton" flush="false" operation="ChangeLockPersonOperation">
						<tiles:put name="commandTextKey" value="button.lock"/>
						<tiles:put name="commandHelpKey" value="button.lock"/>
						<tiles:put name="bundle" value="personsBundle"/>
						<tiles:put name="onclick" value="CallReasonWindow()"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
					</tiles:insert>
				</c:otherwise>
			</c:choose>
		</c:if>
	</c:if>

	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.close"/>
		<tiles:put name="commandHelpKey" value="button.close.empowered-list.help"/>
		<tiles:put name="bundle" value="personsBundle"/>
        <tiles:put name="action" value="/persons/empowered/list.do?person=${personId}"/>
        <tiles:put name="viewType" value="buttonGrey"/>
	</tiles:insert>

</tiles:put>
<tiles:put name="data" type="string">
	<table cellpadding="0" cellspacing="0" class="MaxSize">
	<tr>
	<td height="100%">
	<div class="MaxSize">
	<%@ include file="/WEB-INF/jsp-sevb/persons/empowered/editFieldsEmpowered.jsp" %>

	<!-- Счета -->
	<c:set var="allAccountLinks" value="${form.allAccountLinks}"/>
	<br/>
	<table cellspacing="0" cellpadding="0" width="100%" class="userTab">
		<tr class="titleTable">
			<td align="center" width="20px">
				<input type="checkbox" name="isSelectAllAccountLinks" style="border:none"
				       onclick="switchSelection('isSelectAllAccountLinks','selectedAccountLinks')"
						<c:if test="${not isShowSaves}">disabled</c:if>
				/>
			</td>
			<td>Перечень доступных счетов</td>
		</tr>
		<c:forEach var="accountLink" items="${allAccountLinks}">
			<tr>
				<td class="listItem" align="center" width="20px">        
					<html:multibox property="selectedAccountLinks" style="border:none" disabled="${not isShowSaves}">
						${accountLink.account.number}
					</html:multibox>

				</td>
				<td class="listItem">${accountLink.account.number}</td>
			</tr>
		</c:forEach>
	</table>

	<!-- Карты -->
	<c:set var="allCardLinks" value="${form.allCardLinks}"/>
	<br/>
	<table cellspacing="0" cellpadding="0" width="100%" class="userTab">
		<tr class="titleTable">
			<td align="center" width="20px">
				<input type="checkbox" name="isSelectAllCardLinks" style="border:none"
				       onclick="switchSelection('isSelectAllCardLinks','selectedCardLinks')"
					   <c:if test="${not isShowSaves}">disabled</c:if>
						/>
			</td>
			<td>Перечень доступных карт</td>
		</tr>
		<c:forEach var="cardLink" items="${allCardLinks}">
			<tr>
				<td class="listItem" align="center" width="20px">
					<html:multibox property="selectedCardLinks" style="border:none" disabled="${not isShowSaves}">
						${cardLink.number}
					</html:multibox>

				</td>
				<td class="listItem">${cardLink.number}</td>
			</tr>
		</c:forEach>
	</table>
	<!-- Услуги -->
	<br/>
	<%@ include file="/WEB-INF/jsp/persons/editAccessScript.jsp"%>
	<table cellspacing="0" cellpadding="0" width="100%" class="userTab">
		<tr>
			<td colspan="2">
				<table  cellspacing="0" cellpadding="0" width="100%">
					<tr class="titleTable" height="20px">
						<td width="20px">&nbsp;</td>
						<td>Перечень доступных операций</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td width="50%">
				<c:set var="subform" value="${form.simpleAccess}"/>
				<c:set var="policy" value="${subform.policy}"/>
				<c:if test="${!empty policy}">
					<%@ include file="/WEB-INF/jsp/persons/editEmpoweredPersonAccess.jsp"%>
				</c:if>
			</td>
			<td width="50%">
				<c:set var="subform" value="${form.secureAccess}"/>
				<c:set var="policy" value="${subform.policy}"/>
				<c:if test="${!empty policy}">
					<%@ include file="/WEB-INF/jsp/persons/editEmpoweredPersonAccess.jsp"%>
				</c:if>
			</td>
		</tr>
	</table>
	<input type="hidden" name="blockReason" id="blockReason" value=""/>
	<input type="hidden" name="blockStartDate" id="blockStartDate" value=""/>
	<input type="hidden" name="blockEndDate" id="blockEndDate" value=""/>

	<script type="text/javascript">
		function CallReasonWindow()
		{
			window.open("${phiz:calculateActionURL(pageContext, '/blocks/userblock.do')}", "", "width=700,height=140,resizable=0,menubar=0,toolbar=0,scrollbars=0");
		}
		function setReason(reason, startDate, endDate)
		{
			var blockReason = document.getElementById("blockReason");
			blockReason.value = reason;

			var blockStartDate = document.getElementById("blockStartDate");
			blockStartDate.value = startDate;
			if (endDate!=null)
			{
				var blockEndDate = document.getElementById("blockEndDate");
				blockEndDate.value = endDate;
			}

			var button = new CommandButton("button.lock", "");
			button.click();
		}
		function printClientInfo(event, personId, operation)
		{
			if (personId != null && personId != '' && !isDataChanged())
			{
				openWindow(event, '../print.do?person=' + personId);
			}
			else
			{
                window.alert("Перед печатью анкеты представителя клиента необходимо ее сохранить");
			}
		}
		initData();
	</script>
	</td>
	</tr>
	</table>
</tiles:put>
</tiles:insert>
</html:form>

