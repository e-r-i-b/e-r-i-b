<%@ page import="javax.servlet.ServletRequest"%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/edit" onsubmit="return setEmptyAction(event);">

<c:set var="form" value="${EditPersonForm}"/>

<tiles:insert definition="personEdit">
	<tiles:put name="submenu" type="string" value="Edit"/>
	<tiles:put name="pageTitle" type="string">
		<bean:message key="edit.user.title" bundle="personsBundle"/>
	</tiles:put>

<tiles:put name="menu" type="string">
    <c:set var="isNew"      value="${empty form.activePerson.id}"/>
    <c:set var="isTemplate" value="${form.activePerson.status == 'T'}"/>
	<c:set var="isAgreementSign" value="${form.activePerson.status == 'S'}"/>
    <c:set var="isDeleted"  value="${form.activePerson.status == 'D'}"/>
	<c:set var="isShowSaves" value="${not (form.activePerson.status == 'T' && phiz:isAgreementSignMandatory()) }"/>
	<c:set var="isModified" value="${form.modified}"/>
    <c:set var="isActive" value="${form.activePerson.status == 'A'}"/>
    <c:set var="isCancellation" value="${form.activePerson.status == 'W'}"/>

    <c:if test="${isActive or isCancellation}">
	    <c:choose>
			<c:when test="${not empty form.activePerson.login.blocks}">
				<tiles:insert definition="commandButton" flush="false" operation="ChangeLockPersonOperation">
					<tiles:put name="commandKey" value="button.unlock"/>
					<tiles:put name="commandHelpKey" value="button.unlock.help"/>
					<tiles:put name="bundle" value="personsBundle"/>
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
	<c:if test="${isAgreementSign}">
		<tiles:insert definition="commandButton" flush="false" operation="EditPersonOperation">
			<tiles:put name="commandKey"     value="button.partly.save.person"/>
			<tiles:put name="commandHelpKey" value="button.partly.save.person.help"/>
			<tiles:put name="bundle"  value="personsBundle"/>
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="viewType" value="buttonGrey"/>
		</tiles:insert>

		<tiles:insert definition="commandButton" flush="false" operation="SignAgreementOperation">
			<tiles:put name="commandKey"     value="button.sign.agreement"/>
			<tiles:put name="commandHelpKey" value="button.sign.agreement.help"/>
			<tiles:put name="bundle"  value="personsBundle"/>
			<tiles:put name="confirmText" value="¬ы уверены, что пользователь подписал за€вление о предоставлении услуг?"/>
            <tiles:put name="viewType" value="buttonGrey"/>
		</tiles:insert>
    </c:if>

    <c:if test="${not isNew and isTemplate}">
		<tiles:insert definition="commandButton" flush="false" operation="RegisterClientOperation">
			<tiles:put name="commandKey"     value="button.activate.person"/>
			<tiles:put name="commandHelpKey" value="button.activate.person.help"/>
			<tiles:put name="bundle"  value="personsBundle"/>
            <tiles:put name="viewType" value="buttonGrey"/>
		</tiles:insert>
	</c:if>

    <c:if test="${isTemplate}">
		<tiles:insert definition="commandButton" flush="false" operation="EditPersonOperation">
			<tiles:put name="commandKey"     value="button.edit.return"/>
			<tiles:put name="commandHelpKey" value="button.edit.return.help"/>
			<tiles:put name="bundle"  value="personsBundle"/>
            <tiles:put name="viewType" value="buttonGrey"/>
		</tiles:insert>
	</c:if>


    <c:if test="${not (isNew or isTemplate or isDeleted or isAgreementSign)}">
		<tiles:insert definition="commandButton" flush="false" operation="EditPersonOperation">
			<tiles:put name="commandKey"     value="button.partly.save.person"/>
			<tiles:put name="commandHelpKey" value="button.partly.save.person.help"/>
			<tiles:put name="bundle"  value="personsBundle"/>
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="viewType" value="buttonGrey"/>
		</tiles:insert>
	    <c:if test="${isModified}">
			<tiles:insert definition="commandButton" flush="false" operation="RegisterPersonChangesOperation">
				<tiles:put name="commandKey"     value="button.register.changes.person"/>
				<tiles:put name="commandHelpKey" value="button.register.changes.person.help"/>
				<tiles:put name="bundle"  value="personsBundle"/>
				<tiles:put name="validationFunction">
					confirmRegistration();
				</tiles:put>
                <tiles:put name="viewType" value="buttonGrey"/>
			</tiles:insert>
		</c:if>
	</c:if>
	<c:if test="${not isNew}">
	    <c:choose>
		    <c:when test="${isTemplate or isAgreementSign}">
				<tiles:insert definition="commandButton" flush="false" operation="RemovePersonOperation">
					<tiles:put name="commandKey"     value="button.remove.template.person"/>
					<tiles:put name="commandHelpKey" value="button.remove"/>
					<tiles:put name="bundle"  value="personsBundle"/>
					<tiles:put name="confirmText"    value="¬ы действительно хотите удалить клиента?"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
				</tiles:insert>
		    </c:when>
		    <c:otherwise>
				<tiles:insert definition="commandButton" flush="false" operation="RemovePersonOperation">
					<tiles:put name="commandKey"     value="button.remove"/>
					<tiles:put name="commandHelpKey" value="button.remove"/>
					<tiles:put name="bundle"  value="personsBundle"/>
					<tiles:put name="validationFunction">askForDeleting();</tiles:put>
                    <tiles:put name="viewType" value="buttonGrey"/>
				</tiles:insert>
		    </c:otherwise>
	    </c:choose>
	</c:if>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.close"/>
		<tiles:put name="commandHelpKey" value="button.close.help"/>		
		<tiles:put name="bundle"         value="personsBundle"/>
		<tiles:put name="action"         value="/persons/list.do"/>
        <tiles:put name="viewType" value="buttonGrey"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
	<input type="hidden" name="blockReason" id="blockReason" value=""/>
	<input type="hidden" name="blockStartDate" id="blockStartDate" value=""/>
	<input type="hidden" name="blockEndDate" id="blockEndDate" value=""/>
	<input type="hidden" name="askForDeleting" id="askForDeleting" value="${form.askForDeleting}"/>
	<input type="hidden" name="hasDebts" id="hasDebts" value="${form.hasDebts}"/>
	<input type="hidden" name="hasPayments" id="hasPayments" value="${form.hasPayments}"/>

	<table cellpadding="0" cellspacing="0" class="MaxSize">
	<tr>
	<td height="100%">
	<div class="MaxSize">

	<%@ include file="editFields.jsp" %>


	<script type="text/javascript">

		/*если дата подписани€ договора (=дата нач.обслуживани€) не совпадает с текущей,
		предупредим об этом пользовател€.*/
		function checkAgreementDate()
		{
			var agreement = document.getElementById("field(serviceInsertionDate)");
			var currDate = "<%=String.format("%1$td.%1$tm.%1$tY",Calendar.getInstance().getTime())%>";

			if ( agreement.value != currDate)
			{
				if (!confirm ("ƒата начала обслуживани€ не совпадает с текущей. ѕодключить клиента?"))
					return false;
			}
			return true;
		}
		/*если у пользовател€ есть долги, спрашиваем его о подтверждении удалени€*/
		function askForDeleting()
		{
			var res = true;
			if(!confirm("¬ы действительно хотите удалить клиента?"))
				return false;
			if (document.getElementById("hasDebts").value == 'true')
			{
				var askForDel = document.getElementById("askForDeleting");

				if (confirm(" лиент имеет задолженности. ¬ы уверены, что хотите расторгнуть с ним договор?"))
				{
					askForDel.value = true;
					res = true;
				}
				else
				{
					askForDel.value = false;
					res = false;
				}
			 }
			if (res && document.getElementById("hasPayments").value == 'true')
			{
				return confirm("” клиента есть непроведенные платежи. \n ѕри расторжении договора эти платежи будут отозваны. \n ¬ы уверены, что хотите расторгнуть с ним договор?");
		    }
			return res;
		}

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
		function confirmRegistration()
		{
			if (isDataChanged())
			{
				window.alert("ƒл€  регистрации изменений необходимо сначала сохранить анкету клиента");
				return false;
			}
			return true;
		}

		function printClientInfo(event, personId, operation)
		{
			if (personId != null && personId != '' && !isDataChanged())
			{
				openWindow(event, 'print.do?person=' + personId);
			}
			else
			{
                window.alert("ѕеред печатью анкеты клиента необходимо ее сохранить");
			}
		}

        function printAdditionalContract (event, personId)
        {
            if (personId != null && personId != '' && !isDataChanged())
			{
               openWindow(event,'printContract9.do?person=' + personId,'5',null);
               openWindow(event,'printContract8_pr5.do?person=' + personId,'2',null);
              }
			else
			{
                window.alert("ѕеред печатью договора необходимо сохранить анкету клиента");
			}
	    }
		function clearMasks(event)
		{
			clearInputTemplate('field(migratoryCardFromDate)', DATE_TEMPLATE);
			clearInputTemplate('field(migratoryCardTimeUpDate)', DATE_TEMPLATE);
		}
		initData();
	</script>
	</div>
	  </td>
	</tr>
	</table>

</tiles:put>
</tiles:insert>
</html:form>