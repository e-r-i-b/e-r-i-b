<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
  User: Zhuravleva
  Date: 18.11.2005
  Time: 18:30:52
--%>
<c:choose>
	<c:when test="${!(empty EditPersonForm)}">
		<c:set var="form" value="${EditPersonForm}"/>
		<c:set var="path" value=""/>
	</c:when>
	<c:when test="${!(empty AssignPersonAccessForm)}">
		<c:set var="form" value="${AssignPersonAccessForm}"/>
		<c:set var="path" value=""/>
	</c:when>
	<c:when test="${!(empty ListPersonResourcesForm)}">
		<c:set var="form" value="${ListPersonResourcesForm}"/>
		<c:set var="path" value=""/>
	</c:when>
	<c:when test="${!(empty EmpoweredPersonsListForm)}">
		<c:set var="form" value="${EmpoweredPersonsListForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
	<c:when test="${!(empty ShowPaymentReceiverListForm)}">
		<c:set var="form" value="${ShowPaymentReceiverListForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
	<c:when test="${!(empty EditEmpoweredPersonForm)}">
		<c:set var="form" value="${EditEmpoweredPersonForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
	<c:when test="${!(empty EditPaymentReceiverForm)}">
		<c:set var="form" value="${EditPaymentReceiverForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
	<c:when test="${!(empty ShowGorodCardInfoForm)}">
		<c:set var="form" value="${ShowGorodCardInfoForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
	<c:when test="${!(empty ChargeOffListForm)}">
		<c:set var="form" value="${ChargeOffListForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
</c:choose>

<c:set var="person" value="${form.activePerson}"/>
<c:set var="personId" value="${empty param.person or param.person==0 ? '$$newId' : param.person}"/>
<c:set var="login"  value="${person.login}"/>
<c:set var="isNewOrTemplate" value="${empty person or person.status == 'T'}"/>
<c:set var="isCancelation" value="${person.status == 'W'}"/>
<c:set var="isErrorCancelation" value="${person.status == 'E'}"/>
<c:set var="isSignAgreement" value="${person.status == 'S'}"/>
<c:set var="isActive" value="${person.status == 'A'}"/>
<c:set var="isModified" value="${form.modified}"/>


<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tr><td>
<p class="infoTitle backTransparent">
 Пользователь:<br>
	<span class="menuInsertText backTransparent" style="width:180px">${person.fullName}</span>
 <br/>
 <br/>
 Статус:&nbsp;
<c:choose>
    <c:when test="${isNewOrTemplate}">
        <img src="${imagePath}/activate.gif" width="12px" height="12px" alt="" border="0"/>&nbsp;<span class="menuInsertText backTransparent">Подключение</span>
    </c:when>
    <c:when test="${not empty login.blocks and not isCancelation and not isErrorCancelation and not isSignAgreement}">
        <img src="${imagePath}/lock.gif" width="12px" height="12px" alt="" border="0"/>&nbsp;<span class="menuInsertText backTransparent">Заблокирован</span>
    </c:when>
	<c:when test="${isSignAgreement}">
		<span class="menuInsertText backTransparent">Подписание договора</span>
	</c:when>
	<c:otherwise>
		<span class="menuInsertText backTransparent">Активный</span>
	</c:otherwise>
</c:choose>

</p>
</td></tr>
<tiles:importAttribute/>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'Edit'}"/>
    <tiles:put name="action"  value="/persons/edit.do?person=${personId}"/>
    <tiles:put name="text"    value="Общие сведения"/>
	<tiles:put name="title"   value="Общие сведения о пользователе"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="GetResourcesOperation">
	<tiles:put name="enabled" value="${submenu != 'Resources'}"/>
    <tiles:put name="action"  value="/persons/resources.do?person=${personId}"/>
	<tiles:put name="text"    value="Счета и карты"/>
	<tiles:put name="title"   value="Счета и пластиковые карты пользователя"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ViewPersonAccessOperation">
	<tiles:put name="enabled" value="${submenu != 'Operations'}"/>
    <tiles:put name="action"  value="/persons/useroperations.do?person=${personId}"/>
	<tiles:put name="text"    value="Права доступа"/>
	<tiles:put name="title"   value="Права пользователя на доступ к операциям системы"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="GetEmpoweredPersonsListOperation">
	<tiles:put name="enabled" value="${submenu != 'EmpoweredPersons'}"/>
    <tiles:put name="action"  value="/persons/empowered/list.do?person=${personId}"/>
	<tiles:put name="text"    value="Представители"/>
	<tiles:put name="title"   value="Список доверенных лиц"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="GetPaymentReceiverListOperation">
	<tiles:put name="enabled" value="${submenu != 'PaymentReceiversI'}"/>
    <tiles:put name="action"  value="/persons/receivers/list.do?kind=P&person=${personId}"/>
	<tiles:put name="text"    value="Получатели (физ.лица)"/>
	<tiles:put name="title"   value="Получатели платежей (физ.лица)"/>
</tiles:insert>


<tiles:insert definition="leftMenuInset" operation="GetPaymentReceiverListOperation">
	<tiles:put name="enabled" value="${submenu != 'PaymentReceiversJ'}"/>
    <tiles:put name="action"  value="/persons/receivers/list.do?kind=J&person=${personId}"/>
	<tiles:put name="text"    value="Получатели (юр.лица)"/>
	<tiles:put name="title"   value="Получатели платежей (юр.лица)"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" operation="GetPaymentRegisterOperation">
	<tiles:put name="enabled" value="${submenu != 'ChargeOffLog'}"/>
    <tiles:put name="action"  value="/persons/chargeoff/list.do?person=${personId}"/>
	<tiles:put name="text"    value="Журнал оплаты"/>
	<tiles:put name="title"   value="Журнал оплаты"/>
</tiles:insert>



<script type="text/javascript">
function printClientInfo(event, personId, operation)
		{
			if (personId != null && personId != ''
					<c:if test="${(empty ListPersonResourcesForm)}">&& !isDataChanged()</c:if>)
			{
				openWindow(event,'${path}'+'print.do?person=' + personId);
			}
			else
			{
                window.alert("Перед печатью анкеты клиента необходимо ее сохранить");
			}
		}
function printContract (event, personId)
{
    if (personId != null && personId != '' && personId != 'null'
			<c:if test="${(empty ListPersonResourcesForm)}">&& !isDataChanged()</c:if>)
	{
       openWindow(event,'${path}'+'printContract2.do?person=' + personId,'3',null);
       openWindow(event,'${path}'+'printContract8_pr5.do?person=' + personId,'2',null);
       openWindow(event,'${path}'+'printContract.do?person=' + personId,'1',null);
    }
	else
	{
        window.alert("Перед печатью заявления клиента необходимо сохранить анкету");
	}
}
function printAdditionalContract (event, personId)
{
    if (personId != null && personId != '')
	{
       openWindow(event,'${path}'+'printContract9.do?person=' + personId,'5',null);
       openWindow(event,'${path}'+'printContract8_pr5.do?person=' + personId,'2',null);
    }
	else
	{
        window.alert("Перед печатью заявления клиента необходимо сохранить анкету");
	}
}
function printRecession (event, personId)
{
   openWindow(event,'${path}'+'../persons/printRecession.do?person=' + personId,"","resizable=1,menubar=1,toolbar=0,scrollbars=1,width=0,height=0");
}
</script>
<tr><td>
<c:if test="${phiz:impliesOperation('PrintPersonOperation','PersonManagement') or phiz:impliesOperation('PrintRecessionOperation','PersonManagement')}">
	<span class="infoTitle asterisk"><img src="${imagePath}/print.gif" alt="" width="12px" height="12px">&nbsp;Печать&nbsp;документов</span>
</c:if>
	<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
	  <tr><td>

	<c:if test="${isNewOrTemplate or isSignAgreement}">
		  <tiles:insert definition="clientButton" flush="false" operation="PrintPersonOperation">
			  <tiles:put name="commandTextKey" value="button.print"/>
			  <tiles:put name="commandHelpKey" value="button.print.help"/>
			  <tiles:put name="bundle"         value="personsBundle"/>
			  <tiles:put name="onclick">
				printClientInfo(event,${empty personId || personId=='$$newId'? 'null': personId})
			  </tiles:put>
			  <tiles:put name="width"         value="150px"/>
		  </tiles:insert><br><br>

		  <tiles:insert definition="clientButton" flush="false" operation="PrintPersonOperation">
			  <tiles:put name="commandTextKey" value="button.printContract"/>
			  <tiles:put name="commandHelpKey" value="button.printContract.help"/>
			  <tiles:put name="bundle"         value="personsBundle"/>
			  <tiles:put name="onclick">
				  printContract(event,${empty personId || personId=='$$newId'? 'null': personId});
			  </tiles:put>
			  <tiles:put name="width"         value="150px"/>
		  </tiles:insert><br><br>
	</c:if>
	<c:if test="${not isNewOrTemplate and not isDelete and not isSignAgreement}">
		  <tiles:insert definition="clientButton" flush="false" operation="PrintRecessionOperation">
			  <tiles:put name="commandTextKey" value="button.printRecession"/>
			  <tiles:put name="commandHelpKey" value="button.printRecession.help"/>
			  <tiles:put name="bundle"         value="personsBundle"/>
			  <tiles:put name="onclick">
				printRecession(event,${empty personId ? 'null': personId})
			  </tiles:put>
			  <tiles:put name="width"         value="150px"/>

	  </tiles:insert><br><br>
	  <tiles:insert definition="clientButton" flush="false" operation="PrintPersonOperation">
		  <tiles:put name="commandTextKey" value="button.printContract"/>
		  <tiles:put name="commandHelpKey" value="button.printContract.help"/>
		  <tiles:put name="bundle"         value="personsBundle"/>
		  <tiles:put name="onclick">
			printContract(event,${empty personId ? 'null': personId})
		  </tiles:put>
		  <tiles:put name="width"         value="150px"/>

		  </tiles:insert>
	</c:if>
	<c:if test="${isDelete}">
		  <tiles:insert definition="clientButton" flush="false" operation="PrintRecessionOperation">
			  <tiles:put name="commandTextKey" value="button.printRecession"/>
			  <tiles:put name="commandHelpKey" value="button.printRecession.help"/>
			  <tiles:put name="bundle"         value="personsBundle"/>
			  <tiles:put name="onclick">
				printRecession(event,${empty personId ? 'null': personId})
			  </tiles:put>
			  <tiles:put name="width"         value="150px"/>
		  </tiles:insert>
	 </c:if>

		  </td></tr>
	 </table>
</td></tr>