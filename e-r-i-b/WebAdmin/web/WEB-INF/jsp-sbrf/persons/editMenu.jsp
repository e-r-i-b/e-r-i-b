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
  Внимание!!!
  Не забываем добавлять в ...\WebAdmin\web\WEB-INF\jsp-sbrf\persons\personData.jsp
  условие "<c:when ..." для соответствующей формы!!!
--%>
<%@ include file="personData.jsp"%>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="department" value="${phiz:getCurrentDepartment()}"/>

<tiles:insert definition="leftMenuInset" group="PersonInformation">
	<tiles:put name="enabled" value="${submenu != 'Edit'}"/>
    <tiles:put name="action"  value="/persons/edit.do?person=${personId}"/>
    <tiles:put name="text"    value="Общие сведения"/>
	<tiles:put name="title"   value="Общие сведения о пользователе"/>
</tiles:insert>

<c:if test="${not empty department && department.synchKey != null}">
<tiles:insert definition="leftMenuInset" operation="GetResourcesOperation">
	<tiles:put name="enabled" value="${submenu != 'Resources'}"/>
    <tiles:put name="action"  value="/persons/resources.do?person=${personId}"/>
	<tiles:put name="text"    value="Счета и карты"/>
	<tiles:put name="title"   value="Счета и пластиковые карты пользователя"/>
</tiles:insert>
<c:if test="${person.creationType == 'UDBO'}">
    <tiles:insert definition="leftMenuInset" operation="GetResourcesOperation">
        <tiles:put name="enabled" value="${submenu != 'ProductsVisibility'}"/>
        <tiles:put name="action"  value="/persons/resources/visibility.do?person=${personId}"/>
        <tiles:put name="text"    value="Настройка видимости продуктов"/>
        <tiles:put name="title"   value="Настройка видимости продуктов"/>
    </tiles:insert>
</c:if>
</c:if>

<tiles:insert definition="leftMenuInset" group="PersonOperations">
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

<c:if test="${phiz:impliesService('EmployeeTemplateManagement') || phiz:impliesService('EmployeeTemplateVisibilitySettings')}">
    <tiles:insert definition="leftMenuInsetGroup" flush="false">
        <tiles:put name="text"    value="Шаблоны"/>
        <tiles:put name="name"    value="Templates"/>
        <tiles:put name="enabled" value="${submenu != 'TemplatesList' && submenu != 'internet' && submenu != 'mobile' && submenu != 'atm' && submenu != 'sms'}"/>
        <tiles:put name="data">
            <tiles:insert definition="leftMenuInset" service="EmployeeTemplateManagement" flush="false">
                <tiles:put name="enabled"       value="${submenu != 'TemplatesList'}"/>
                <tiles:put name="action"        value="/persons/temlates/list.do?person=${personId}"/>
                <tiles:put name="text"          value="Список шаблонов"/>
                <tiles:put name="title"         value="Список шаблонов"/>
                <tiles:put name="parentName"    value="Templates"/>
            </tiles:insert>
            <tiles:insert definition="leftMenuSubInsetGroup" service="EmployeeTemplateVisibilitySettings" flush="false">
                <tiles:put name="text"    value="Настройка видимости шаблонов"/>
                <tiles:put name="name"    value="TemplatesVisibility"/>
                <tiles:put name="parentName" value="Templates"/>
                <tiles:put name="enabled" value="${submenu != 'internet' && submenu != 'mobile' && submenu != 'atm' && submenu != 'sms'}"/>
                <tiles:put name="data">
                    <tiles:insert definition="leftMenuInset" flush="false">
                        <tiles:put name="enabled"       value="${submenu != 'internet'}"/>
                        <tiles:put name="action"        value="/persons/temlates/showsettings.do?person=${personId}&field(channel)=internet"/>
                        <tiles:put name="text"          value="ЕРИБ"/>
                        <tiles:put name="title"         value="ЕРИБ"/>
                        <tiles:put name="parentName"    value="TemplatesVisibility"/>
                    </tiles:insert>
                    <tiles:insert definition="leftMenuInset" flush="false">
                        <tiles:put name="enabled"       value="${submenu != 'mobile'}"/>
                        <tiles:put name="action"        value="/persons/temlates/showsettings.do?person=${personId}&field(channel)=mobile"/>
                        <tiles:put name="text"          value="Мобильные приложения"/>
                        <tiles:put name="title"         value="Мобильные приложения"/>
                        <tiles:put name="parentName"    value="TemplatesVisibility"/>
                    </tiles:insert>
                    <tiles:insert definition="leftMenuInset" flush="false">
                        <tiles:put name="enabled"       value="${submenu != 'atm'}"/>
                        <tiles:put name="action"        value="/persons/temlates/showsettings.do?person=${personId}&field(channel)=atm"/>
                        <tiles:put name="text"          value="Терминалы"/>
                        <tiles:put name="title"         value="Терминалы"/>
                        <tiles:put name="parentName"    value="TemplatesVisibility"/>
                    </tiles:insert>
                    <c:if test="${phiz:isERMBConnected(person)}">
                        <tiles:insert definition="leftMenuInset" flush="false">
                            <tiles:put name="enabled"       value="${submenu != 'sms'}"/>
                            <tiles:put name="action"        value="/persons/temlates/showsettings.do?person=${personId}&field(channel)=sms"/>
                            <tiles:put name="text"          value="СМС - команды"/>
                            <tiles:put name="title"         value="СМС - команды"/>
                            <tiles:put name="parentName"    value="TemplatesVisibility"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>
    </tiles:put>
    </tiles:insert>
</c:if>

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

<tiles:insert definition="leftMenuInset" operation="ListSBNKDClaimOperation">
    <tiles:put name="enabled" value="${submenu != 'sbnkdClaim'}"/>
    <tiles:put name="action"  value="/persons/sbnkd/list.do?person=${personId}"/>
    <tiles:put name="title"   value="Заявки СБНКД"/>
    <tiles:put name="text"    value="Заявки СБНКД"/>
</tiles:insert>

<c:if test="${phiz:isExistMobileApplication(person)}">
    <tiles:insert definition="leftMenuInset" operation="ResetMobileWalletOperation">
        <tiles:put name="enabled" value="${submenu != 'MobileWallet'}"/>
        <tiles:put name="action"  value="/persons/mobilewallet.do?person=${personId}"/>
        <tiles:put name="title"   value="Мобильный кошелек"/>
        <tiles:put name="text"    value="Мобильный кошелек"/>
    </tiles:insert>
</c:if>

<tiles:insert definition="leftMenuInset" operation="MobileApplicationsLockOperation"
              service="ShowClientMobileDevicesService">
    <tiles:put name="enabled" value="${submenu != 'MobileApplicationsLock'}"/>
    <tiles:put name="action"  value="/persons/mobileAplicationsLock.do?person=${personId}"/>
    <tiles:put name="title"   value="Мобильные приложения"/>
    <tiles:put name="text"    value="Мобильные приложения"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="SocialApplicationsLockOperation"
              service="ShowClientSocialAppService">
    <tiles:put name="enabled" value="${submenu != 'SocialApplicationsLock'}"/>
    <tiles:put name="action"  value="/persons/socialAplicationsLock.do?person=${personId}"/>
    <tiles:put name="title"   value="Социальные приложения"/>
    <tiles:put name="text"    value="Социальные приложения"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ErmbProfileOperation">
    <tiles:put name="enabled" value="${submenu != 'ErmbProfileConnect'}"/>
    <tiles:put name="action"  value="/ermb/profile/connection.do?person=${personId}"/>
    <tiles:put name="title"   value="Мобильный банк"/>
    <tiles:put name="text"    value="Мобильный банк"/>
</tiles:insert>

<c:if test="${phiz:isERMBConnected(person)}">
    <tiles:insert definition="leftMenuInset" operation="PersonIdentityHistoryListOperation">
        <tiles:put name="enabled" value="${submenu != 'PersonIdentityHistory'}"/>
        <tiles:put name="action"  value="/private/person/documents/history.do?person=${personId}"/>
        <tiles:put name="title"   value="История изменения идентификационных данных"/>
        <tiles:put name="text"    value="История изменения идентификационных данных"/>
    </tiles:insert>
</c:if>

<c:if test="${person.creationType == 'UDBO'}">
    <tiles:insert definition="leftMenuInset" operation="ViewOTPRestrictionOperation">
        <tiles:put name="enabled" value="${submenu != 'OTPRestrictionsList'}"/>
        <tiles:put name="action"  value="/persons/otpRestrictions.do?person=${personId}"/>
        <tiles:put name="title"   value="Ограничения на печать одноразовых паролей"/>
        <tiles:put name="text"    value="Ограничения на печать одноразовых паролей"/>
    </tiles:insert>
</c:if>

<tiles:insert definition="leftMenuInsetGroup" flush="false" service="ViewUsersLimits">
	<tiles:put name="text"    value="Лимиты"/>
	<tiles:put name="name"    value="UserLimits"/>
    <tiles:put name="enabled" value="${submenu != 'UserLimitsMain' && submenu != 'UserLimitsMobile' && submenu != 'UserLimitsSelfServiceDevice'}"/>
	<tiles:put name="data">
        <tiles:insert definition="leftMenuInset" service="ViewUsersLimits" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'UserLimitsMain'}"/>
            <tiles:put name="action"        value="/persons/limits.do?person=${personId}&type=INTERNET_CLIENT"/>
            <tiles:put name="text"          value="Лимиты  (основная версия)"/>
            <tiles:put name="title"         value="Лимиты  (основная версия)"/>
            <tiles:put name="parentName"    value="UserLimits"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" service="ViewUsersLimits" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'UserLimitsMobile'}"/>
            <tiles:put name="action"        value="/persons/limits.do?person=${personId}&type=MOBILE_API"/>
            <tiles:put name="text"          value="Лимиты (приложение API)"/>
            <tiles:put name="title"         value="Лимиты (приложение API)"/>
            <tiles:put name="parentName"    value="UserLimits"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" service="ViewUsersLimits" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'UserLimitsSocial'}"/>
            <tiles:put name="action"        value="/persons/limits.do?person=${personId}&type=SOCIAL_API"/>
            <tiles:put name="text"          value="Лимиты (соц.платформы)"/>
            <tiles:put name="title"         value="Лимиты (соц.платформы)"/>
            <tiles:put name="parentName"    value="UserLimits"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" service="ViewUsersLimits" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'UserLimitsSelfServiceDevice'}"/>
            <tiles:put name="action"        value="/persons/limits.do?person=${personId}&type=SELF_SERVICE_DEVICE"/>
            <tiles:put name="text"          value="Лимиты (устройства самообслуживания)"/>
            <tiles:put name="title"         value="Лимиты (устройства самообслуживания)"/>
            <tiles:put name="parentName"    value="UserLimits"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" service="ViewUsersLimits" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'UserLimitsErmb'}"/>
            <tiles:put name="action"        value="/persons/limits.do?person=${personId}&type=ERMB_SMS"/>
            <tiles:put name="text"          value="Лимиты (ЕРМБ)"/>
            <tiles:put name="title"         value="Лимиты (ЕРМБ)"/>
            <tiles:put name="parentName"    value="UserLimits"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" service="ViewUsersLimits" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'UserLimitsOverallAmountPerDay'}"/>
            <tiles:put name="action"        value="/persons/limits.do?person=${personId}&type=ALL"/>
            <tiles:put name="text"          value="Лимиты (Единый суточный кумулятивный лимит)"/>
            <tiles:put name="title"         value="Лимиты (Единый суточный кумулятивный лимит)"/>
            <tiles:put name="parentName"    value="UserLimits"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>

<script type="text/javascript">
function printClientInfo(event, personId, operation)
		{
			if (personId != null && personId != ''
					<c:if test="${(empty ListPersonResourcesForm)}">&& !isDataChanged()</c:if>)
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
    if (personId != null && personId != '' && personId != 'null'
			<c:if test="${(empty ListPersonResourcesForm)}">&& !isDataChanged()</c:if>)
	{
       <%--openWindow(event,'${path}'+'printContract2.do?person=' + personId,'3',null);--%>
       <%--openWindow(event,'${path}'+'printContract8_pr5.do?person=' + personId,'2',null);--%>
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
       <%--openWindow(event,'${path}'+'printContract8_pr5.do?person=' + personId,'2',null);--%>
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
<tiles:insert definition="leftMenuInsetGroup" group="PrintDocuments">
	<tiles:put name="text"    value="Печать документов"/>
	<tiles:put name="name"    value="Print"/>
	<tiles:put name="enabled"    value="true"/>
	<tiles:put name="data">
        <c:choose>
            <c:when test="${isUDBO}">
                <tiles:insert definition="leftMenuInset" flush="false" operation="PrintPersonOperation">
                    <tiles:put name="enabled"       value="true"/>
                    <tiles:put name="text"          value="Печать"/>
                    <tiles:put name="title"         value="Печать"/>
                    <tiles:put name="parentName"    value="Print"/>
                    <tiles:put name="onclick">
                        printClientInfo(event, ${empty person.id? 'null': personId})
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
                            printClientInfo(event, ${empty person.id? 'null': personId})
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="leftMenuInset" flush="false" operation="PrintPersonOperation">
                        <tiles:put name="enabled"       value="true"/>
                        <tiles:put name="text"          value="Печать заявления"/>
                        <tiles:put name="title"         value="Печать заявления"/>
                        <tiles:put name="parentName"    value="Print"/>
                        <tiles:put name="onclick">
                            printContract(event, ${empty person.id? 'null': personId});
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
                            printRecession(event,${empty personId ? 'null': personId})
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
                            printRecession(event,${empty personId ? 'null': personId})
                        </tiles:put>
                    </tiles:insert>
                </c:if>
            </c:otherwise>
        </c:choose>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="leftMenuInsetGroup" flush="false">
    <tiles:put name="text"    value="Корзина платежей"/>
    <tiles:put name="name"    value="PaymentBasket"/>
    <tiles:put name="enabled" value="${submenu != 'BasketRequisites' && submenu != 'InvoicesList'}"/>
    <tiles:put name="data">
        <tiles:insert definition="leftMenuInset" flush="false" operation="ViewBasketRequisitesOperation">
            <tiles:put name="enabled" value="${submenu != 'BasketRequisites'}"/>
            <tiles:put name="action"  value="/persons/basketrequisites.do?person=${personId}"/>
            <tiles:put name="title"   value="Реквизиты корзины"/>
            <tiles:put name="text"    value="Реквизиты корзины"/>
            <tiles:put name="parentName"    value="PaymentBasket"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" flush="false" operation="EmployeeInvoicesListOperation">
            <tiles:put name="enabled" value="${submenu != 'InvoicesList'}"/>
            <tiles:put name="action"  value="/persons/basket/invoices?person=${personId}"/>
            <tiles:put name="title"   value="Счета в корзине"/>
            <tiles:put name="text"    value="Счета в корзине"/>
            <tiles:put name="parentName"    value="PaymentBasket"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" flush="false" operation="ViewPaymentsBasketOperation">
            <tiles:put name="enabled" value="${submenu != 'EditBasket'}"/>
            <tiles:put name="action"  value="/persons/basket/editbasket?person=${personId}"/>
            <tiles:put name="title"   value="Настройки корзины"/>
            <tiles:put name="text"    value="Настройки корзины"/>
            <tiles:put name="parentName"    value="PaymentBasket"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>