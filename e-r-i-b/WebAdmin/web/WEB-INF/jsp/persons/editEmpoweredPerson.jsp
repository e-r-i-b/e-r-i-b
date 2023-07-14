<%@ page import="javax.servlet.ServletContext" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/empowered/edit" onsubmit="return setEmptyAction(event);">

<c:set var="form" value="${EditEmpoweredPersonForm}"/>

<tiles:insert definition="personEdit">
	<tiles:put name="submenu" type="string" value="EmpoweredPersons"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="edit.user.title" bundle="personsBundle"/>
</tiles:put>

<tiles:put name="menu" type="string">
    <script type="text/javascript">
        function getOffice()
        {
			if (${phiz:getDepartmentById(form.activePerson.departmentId).synchKey == null})
            {
                alert("Невозможно подключить представителя. Подразделение не обслуживается в ИКФЛ.");
                return false;
            }
            return true;
        }
    </script>
	<c:set var="isNewOrTemplate" value="${empty form.empoweredPerson.id or form.empoweredPerson.status == 'T'}"/>
    <c:set var="isNew"      value="${empty form.empoweredPerson.id}"/>
    <c:set var="isTemplate" value="${form.empoweredPerson.status == 'T'}"/>
    <c:set var="personId" value="${form.empoweredPerson.id}"/>
	<c:set var="isShowSaves" value="true"/>
	<c:if test="${isNewOrTemplate}">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.partly.save.person"/>
			<tiles:put name="commandHelpKey" value="button.partly.save.person.help"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="image" value=""/>
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
    </c:if>

	<c:if test="${not isNew and isTemplate and form.activePerson.status == 'A'}">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.activate.person"/>
			<tiles:put name="commandHelpKey" value="button.activate.empowered.help"/>
			<tiles:put name="commandTextKey" value="button.activate.empowered"/>
            <tiles:put name="validationFunction" value="getOffice()" />
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="image" value=""/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
	</c:if>

	<c:if test="${not isNewOrTemplate}">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.save.person"/>
			<tiles:put name="commandHelpKey" value="button.save.person.help"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="image" value=""/>
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
	</c:if>
	<tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.print"/>
        <tiles:put name="commandHelpKey" value="button.print"/>
        <tiles:put name="bundle" value="personsBundle"/>
        <tiles:put name="image" value=""/>
        <tiles:put name="onclick" value="javascript:printClientInfo(event,${empty form.id ? 'null': form.id},'');"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>

	<c:if test="${not empty form.person}">
		<c:set var="blocked" value="${!empty form.empoweredPerson.login.blocks}"/>
		<c:if test="${not isNewOrTemplate}">
			<c:choose>
				<c:when test="${blocked}">
					<tiles:insert definition="commandButton" flush="false">
						<tiles:put name="commandKey" value="button.unlock"/>
						<tiles:put name="commandHelpKey" value="button.unlock"/>
						<tiles:put name="bundle" value="personsBundle"/>
						<tiles:put name="image" value=""/>
						<tiles:put name="confirmText"    value="Вы действительно хотите снять блокировку с пользователя?"/>
                        <tiles:put name="viewType" value="blueBorder"/>
					</tiles:insert>
				</c:when>
				<c:otherwise>
					<tiles:insert definition="commandButton" flush="false">
						<tiles:put name="commandKey" value="button.lock"/>
						<tiles:put name="commandHelpKey" value="button.lock"/>
						<tiles:put name="bundle" value="personsBundle"/>
						<tiles:put name="image" value=""/>
						<tiles:put name="confirmText"    value="Вы действительно хотите заблокировать пользователя?"/>
                        <tiles:put name="viewType" value="blueBorder"/>
					</tiles:insert>
				</c:otherwise>
			</c:choose>
		</c:if>
	</c:if>

	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.close"/>
		<tiles:put name="commandHelpKey" value="button.close.help"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="image" value=""/>
        <tiles:put name="action" value="/persons/empowered/list.do?person=${form.person}"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>

</tiles:put>
<tiles:put name="data" type="string">
	<%@ include file="editFieldsEmpowered.jsp" %>

	<!-- Счета -->
	<c:set var="allAccountLinks" value="${form.allAccountLinks}"/>
	<br/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="availableAccountsList"/>
		<tiles:put name="text" value="Список доступных счетов"/>
		<tiles:put name="head">
	    	<td align="center" width="20px">
				<input type="checkbox" name="isSelectAllAccountLinks" style="border:none"
				       onclick="switchSelection('isSelectAllAccountLinks','selectedAccountLinks')"/>
			</td>
			<td>Перечень доступных счетов</td>
		</tiles:put>
		<tiles:put name="data">
			<% int i=0;%>
			<c:forEach var="accountLink" items="${allAccountLinks}">
				<tr>
					<td class="listItem" align="center" width="20px">
						<html:multibox property="selectedAccountLinks" style="border:none">
							${accountLink.account.number}
						</html:multibox>
						<%i++;%>
					</td>
					<td class="listItem">${accountLink.account.number}</td>
				</tr>
			</c:forEach>
			<%if(i==0){%>
				<tiles:put name="isEmpty" value="true"/>
				<tiles:put name="emptyMessage" value=""/>
			<%}%>
		</tiles:put>
	</tiles:insert>

	<!-- Карты -->
	<c:set var="allCardLinks" value="${form.allCardLinks}"/>
	<br/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="availableCardsList"/>
		<tiles:put name="text" value="Список доступных карт"/>
		<tiles:put name="head">
			<td align="center" width="20px">
				<input type="checkbox" name="isSelectAllCardLinks" style="border:none"
					   onclick="switchSelection('isSelectAllCardLinks','selectedCardLinks')"/>
			</td>
			<td>Перечень доступных карт</td>
		</tiles:put>
		<tiles:put name="data">
			<%int i=0;%>
			<c:forEach var="cardLink" items="${allCardLinks}">
				<tr>
					<td class="listItem" align="center" width="20px">
						<html:multibox property="selectedCardLinks" style="border:none">
							${cardLink.number}
						</html:multibox>
						<%i++;%>
					</td>
					<td class="listItem">${cardLink.number}</td>
				</tr>
			</c:forEach>
			<%if(i==0){%>
				<tiles:put name="isEmpty" value="true"/>
				<tiles:put name="emptyMessage" value=""/>
			<%}%>
		</tiles:put>
	</tiles:insert>
	
	<!-- Услуги -->
	<%@ include file="editAccessScript.jsp"%>
	<br/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="accessList"/>
		<tiles:put name="text" value="Разрешенные операции"/>
		<tiles:put name="head">
	    	<td align="center" colspan="2">Перечень разрешенных операций</td>
		</tiles:put>
		<tiles:put name="data">
			<tr>
				<td width="50%">
					<c:set var="subform" value="${form.simpleAccess}"/>
					<c:set var="policy" value="${subform.policy}"/>
					<c:if test="${!empty policy}">
						<%@ include file="editEmpoweredPersonAccess.jsp"%>
					</c:if>
				</td>
				<td width="50%">
					<c:set var="subform" value="${form.secureAccess}"/>
					<c:set var="policy" value="${subform.policy}"/>
					<c:if test="${!empty policy}">
						<%@ include file="editEmpoweredPersonAccess.jsp"%>
					</c:if>
				</td>
			</tr>
		</tiles:put>
	</tiles:insert>

	<script type="text/javascript">
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
       function printContract (event, personId)
        {
           if (personId != null && personId != '' && personId != 'null' && !isDataChanged())
			{
               openWindow(event,'../printContract7.do?person=' + personId,'4',null);
             }
			else
			{
                window.alert("Перед печатью договора необходимо сохранить анкету представителя");
			}
        }
	</script>

</tiles:put>
</tiles:insert>
</html:form>

