<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
  Created by IntelliJ IDEA.
  User: akrenev
  Date: 27.01.2012
  Time: 17:07:56
  To change this template use File | Settings | File Templates.
--%>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="department" value="${phiz:getCurrentDepartment()}"/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="personId"           value="${form.fields['personId']}"/>
<c:set var="personStatus"       value="${form.fields['personStatus']}"/>
<c:set var="creationType"       value="${form.fields['creationType']}"/>
<c:set var="isDelete"           value="${personStatus == 'D'}"/>
<c:set var="isNewOrTemplate"    value="${personStatus == 'T'}"/>
<c:set var="isSignAgreement"    value="${personStatus == 'S'}"/>
<c:set var="isUDBO"             value="${creationType == 'UDBO'}"/>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'Edit'}"/>
    <tiles:put name="action"  value="/persons/edit.do?person=${personId}"/>
    <tiles:put name="text"    value="Общие сведения"/>
	<tiles:put name="title"   value="Общие сведения о пользователе"/>
</tiles:insert>

<c:if test="${not empty department && department.synchKey != null}">
<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'Resources'}"/>
    <tiles:put name="action"  value="/persons/resources.do?person=${personId}"/>
	<tiles:put name="text"    value="Счета и карты"/>
	<tiles:put name="title"   value="Счета и пластиковые карты пользователя"/>
</tiles:insert>
<c:if test="${isUDBO}">
    <tiles:insert definition="leftMenuInset">
        <tiles:put name="enabled" value="${submenu != 'ProductsVisibility'}"/>
        <tiles:put name="action"  value="/persons/resources/visibility.do?person=${personId}"/>
        <tiles:put name="text"    value="Настройка видимости продуктов"/>
        <tiles:put name="title"   value="Настройка видимости продуктов"/>
    </tiles:insert>
</c:if>
</c:if>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'Operations'}"/>
    <tiles:put name="action"  value="/persons/useroperations.do?person=${personId}"/>
	<tiles:put name="text"    value="Права доступа"/>
	<tiles:put name="title"   value="Права пользователя на доступ к операциям системы"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="EmpoweredPersonManagement">
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
    <tiles:put name="action"  value="/persons/receivers/list.do?kind=JB&person=${personId}"/>
	<tiles:put name="text"    value="Получатели (юр.лица)"/>
	<tiles:put name="title"   value="Получатели платежей (юр.лица)"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="GetPersonalPaymentCardOperation">
	<tiles:put name="enabled" value="${submenu != 'PersonalPayments'}"/>
    <tiles:put name="action"  value="/persons/personal-payment-card/list.do?person=${personId}"/>
    <tiles:put name="text"    value="Персональные платежи"/>
	<tiles:put name="title"   value="Персональные платежи пользователя"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ListPayWaitingConfirmOperation">
	<tiles:put name="enabled" value="${submenu != 'OperationsForConfirm'}"/>
    <tiles:put name="action"  value="/persons/payment_wait_confirm/list.do?person=${personId}"/>
    <tiles:put name="text"    value="Операции для подтверждения"/>
	<tiles:put name="title"   value="Операции для подтверждения"/>
</tiles:insert>

<c:set var="needChargeOff" value="${phiz:isNeedChargeOff()}"/>
<c:if test="${not empty needChargeOff && needChargeOff}">
    <tiles:insert definition="leftMenuInset" operation="GetPaymentRegisterOperation">
        <tiles:put name="enabled" value="${submenu != 'ChargeOffLog'}"/>
        <tiles:put name="action"  value="/persons/chargeoff/list.do?person=${personId}"/>
        <tiles:put name="text"    value="Журнал оплаты"/>
        <tiles:put name="title"   value="Журнал оплаты"/>
    </tiles:insert>
</c:if>

<tiles:insert definition="leftMenuInset" operation="ViewPersonUIOperation">
    <tiles:put name="enabled" value="${submenu != 'CustomizeUI'}"/>
    <tiles:put name="action"  value="/skins/customizeUI.do?person=${personId}"/>
    <tiles:put name="title"   value="Настройка интерфейса"/>
    <tiles:put name="text"    value="Настройка интерфейса клиента"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ListPersonGroupsOperation">
    <tiles:put name="enabled" value="${submenu != 'PersonGroups'}"/>
    <tiles:put name="action"  value="/person/groups.do?person=${personId}&mode=personGroups"/>
    <tiles:put name="title"   value="Группы"/>
    <tiles:put name="text"    value="Группы"/>
</tiles:insert>

<script type="text/javascript">
    function printClientInfo(event, personId, operation)
    {
        if (personId != null && personId != '')
        {
            <c:set var="url" value="persons/print.do?person=${personId}"/>
            openWindow(event, "${phiz:calculateActionURL(pageContext, url)}");
        }
        else
        {
            window.alert("Перед печатью анкеты клиента необходимо ее сохранить");
        }
    }

    function printContract (event, personId)
    {
        if (personId != null && personId != '' && personId != 'null')
        {
           <c:set var="url" value="persons/printContract.do?person=${personId}"/>
           openWindow(event,"${phiz:calculateActionURL(pageContext, url)}",'1',null);
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
           <c:set var="url" value="persons/printContract9.do?person=${personId}"/>
           openWindow(event,"${phiz:calculateActionURL(pageContext, url)}",'5',null);
        }
        else
        {
            window.alert("Перед печатью заявления клиента необходимо сохранить анкету");
        }
    }

    function printRecession (event, personId)
    {
       <c:set var="url" value="persons/printRecession.do?person=${personId}"/>
       openWindow(event,"${phiz:calculateActionURL(pageContext, url)}","","resizable=1,menubar=1,toolbar=0,scrollbars=1,width=0,height=0");
    }
</script>
<tiles:insert definition="leftMenuInsetGroup">
	<tiles:put name="text"    value="Печать документов"/>
	<tiles:put name="name"    value="Print"/>
	<tiles:put name="data">
        <tr>
            <td>
                <c:choose>
                    <c:when test="${isUDBO}">
                        <tiles:insert definition="leftMenuInset" flush="false" operation="PrintPersonOperation">
                            <tiles:put name="enabled"       value="true"/>
                            <tiles:put name="text"          value="Печать"/>
                            <tiles:put name="title"         value="Печать"/>
                            <tiles:put name="parentName"    value="Print"/>
                            <tiles:put name="onclick">
                                printClientInfo(event, ${empty personId? 'null': personId});
                            </tiles:put>
                        </tiles:insert>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${isNewOrTemplate or isSignAgreement}">
                            <tiles:insert definition="leftMenuInset" flush="false" operation="PrintPersonOperation">
                                <tiles:put name="enabled"       value="true"/>
                                <tiles:put name="text"          value="Печать"/>
                                <tiles:put name="title"         value="Печать"/>
                                <tiles:put name="parentName"    value="Print"/>
                                <tiles:put name="onclick">
                                    printClientInfo(event, ${empty personId? 'null': personId});
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="leftMenuInset" flush="false" operation="PrintPersonOperation">
                                <tiles:put name="enabled"       value="true"/>
                                <tiles:put name="text"          value="Печать заявления"/>
                                <tiles:put name="title"         value="Печать заявления"/>
                                <tiles:put name="parentName"    value="Print"/>
                                <tiles:put name="onclick">
                                    printContract(event, ${empty personId? 'null': personId});
                                </tiles:put>
                            </tiles:insert>

                        </c:if>
                        <c:if test="${not isNewOrTemplate and not isDelete and not isSignAgreement}">
                            <tiles:insert definition="leftMenuInset" flush="false" operation="PrintRecessionOperation">
                                <tiles:put name="enabled"       value="true"/>
                                <tiles:put name="text"          value="Прекращение обслуживания"/>
                                <tiles:put name="title"         value="Печать заявления прекращении обслуживания"/>
                                <tiles:put name="parentName"    value="Print"/>
                                <tiles:put name="onclick">
                                    printRecession(event,${empty personId ? 'null': personId});
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="leftMenuInset" flush="false" operation="PrintPersonOperation">
                                <tiles:put name="enabled"       value="true"/>
                                <tiles:put name="text"          value="Печать заявления"/>
                                <tiles:put name="title"         value="Печать заявления"/>
                                <tiles:put name="parentName"    value="Print"/>
                                <tiles:put name="onclick">
                                    printContract(event, ${empty personId ? 'null': personId});
                                </tiles:put>
                            </tiles:insert>
                        </c:if>
                        <c:if test="${isDelete}">
                            <tiles:insert definition="leftMenuInset" flush="false" operation="PrintRecessionOperation">
                                <tiles:put name="enabled"       value="true"/>
                                <tiles:put name="text"          value="Прекращение обслуживания"/>
                                <tiles:put name="title"         value="Печать заявления прекращении обслуживания"/>
                                <tiles:put name="parentName"    value="Print"/>
                                <tiles:put name="onclick">
                                    printRecession(event,${empty personId ? 'null': personId});
                                </tiles:put>
                            </tiles:insert>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
</tiles:put>
</tiles:insert>