<%--
  Created by IntelliJ IDEA.
  User: Zhuravleva
  Date: 20.01.2009
  Time: 15:47:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute name="mainmenu" ignore="true"/>
<c:set var="mode" scope="request" value="${mainmenu}"/>

<c:choose>
    <c:when test="${phiz:impliesOperation('GetPersonsListOperation', 'PersonsViewing') || phiz:impliesOperation('GetPersonsListOperation', 'PersonManagement')}">
        <tiles:insert definition="mainMenuInset">
            <tiles:put name="activity" value="${mode == 'Users'}"/>
            <tiles:put name="action" value="/persons/list"/>
            <tiles:put name="text" value="Клиенты"/>
        </tiles:insert>
    </c:when>
    <c:when test="${phiz:impliesOperation('ListClientOperation', '*')}">
        <tiles:insert definition="mainMenuInset">
            <tiles:put name="activity" value="${mode == 'Users'}"/>
            <tiles:put name="action" value="/persons/list/full"/>
            <tiles:put name="text" value="Клиенты"/>
        </tiles:insert>
    </c:when>
    <c:when test="${phiz:impliesOperation('ListGroupsOperationC', '*')}">
        <tiles:insert definition="mainMenuInset">
            <tiles:put name="activity" value="${mode == 'Users'}"/>
            <tiles:put name="action" value="/groups/list?category=C"/>
            <tiles:put name="text" value="Клиенты"/>
        </tiles:insert>
    </c:when>
    <c:when test="${phiz:impliesOperation('EditMigrationInfoOperation', '*')}">
        <tiles:insert definition="mainMenuInset">
            <tiles:put name="activity" value="${mode == 'Users'}"/>
            <tiles:put name="action" value="/persons/migration/info"/>
            <tiles:put name="text"><bean:message key="main.menu.item" bundle="migrationClientsBundle"/></tiles:put>
        </tiles:insert>
    </c:when>
    <c:when test="${phiz:impliesOperation('ListMigrationClientsOperation', '*')}">
        <tiles:insert definition="mainMenuInset">
            <tiles:put name="activity" value="${mode == 'Users'}"/>
            <tiles:put name="action" value="/persons/migration/list"/>
            <tiles:put name="text"><bean:message key="main.menu.item" bundle="migrationClientsBundle"/></tiles:put>
        </tiles:insert>
    </c:when>
</c:choose>

<tiles:insert definition="mainMenuInset" module="Schemes">
	<tiles:put name="activity" value="${mode == 'Schemes'}"/>
	<tiles:put name="action" value="/schemes/list"/>
	<tiles:put name="text" value="Схемы прав"/>
</tiles:insert>

<tiles:insert definition="mainMenuInset" service="PersonPasswordCardManagment">
	<tiles:put name="activity" value="${mode == 'PersonPasswordCardManagment'}"/>
	<tiles:put name="action" value="/passwordcards/list"/>
	<tiles:put name="text" value="Карты ключей"/>
</tiles:insert>

<%-- если можем посмотреть список, то проверяем на доступность сервисов из Employees --%>
<c:if test="${phiz:impliesOperation('GetEmployeeListOperation', '*')}">
    <tiles:insert definition="mainMenuInset" module="Employees">
        <tiles:put name="activity" value="${mode == 'Employees'}"/>
        <tiles:put name="action" value="/employees/list"/>
        <tiles:put name="text" value="Сотрудники"/>
    </tiles:insert>
</c:if>

<tiles:insert definition="mainMenuInset" module="Services">
	<tiles:put name="activity" value="${mode == 'Services'}"/>
	<tiles:put name="action" value="/service/index"/>
	<tiles:put name="text" value="Сервис"/>
</tiles:insert>

<tiles:insert definition="mainMenuInset" operation="GetEmployeePaymentListOperation" service="ViewPaymentList">
	<tiles:put name="activity" value="${mode == 'ViewPaymentList'}"/>
	<tiles:put name="action" value="/audit/businessDocument?status=admin"/>
	<tiles:put name="text" value="Аудит"/>
</tiles:insert>

<tiles:insert definition="mainMenuInset" service="PINEnvelopeManagement">
	<tiles:put name="activity" value="${mode == 'PINEnvelopeManagement'}"/>
	<tiles:put name="action" value="/pin/createRequest"/>
	<tiles:put name="text" value="ПИН-конверты"/>
</tiles:insert>

<c:if test="${phiz:impliesServiceRigid('AutoSubscriptionManagment') || phiz:impliesServiceRigid('AutoTransfersManagement') || phiz:impliesServiceRigid('EmployeeMoneyBoxManagement')}">
<tiles:insert definition="mainMenuInset">
	<tiles:put name="activity" value="${mode == 'AutoPayments'}"/>
	<tiles:put name="action" value="/person/search"/>
	<tiles:put name="text" value="Автоплатежи"/>
</tiles:insert>
</c:if>

<c:set var="impliesBankList" value="${phiz:impliesService('BankListManagement')}"/>
<tiles:insert definition="mainMenuInset" module="Dictionaries">
	<tiles:put name="activity" value="${mode == 'Dictionaries'}"/>
	<tiles:put name="action" value="${impliesBankList ? '/private/multiblock/dictionary/banks/national' : '/dictionaries/index'}"/>
	<tiles:put name="text" value="Справочники"/>
</tiles:insert>

<c:if test="${phiz:impliesServiceRigid('ServiceProvidersDictionaryManagement') || phiz:impliesServiceRigid('RegionDictionaryManagement')
                    || phiz:impliesServiceRigid('PaymentServicesManagement') || phiz:impliesServiceRigid('UnloadJBTManagement')
                    || phiz:impliesServiceRigid('ServiceProvidersDictionaryEmployeeService') || phiz:impliesServiceRigid('ViewServiceProvidersDictionaryAdminService')
                    || phiz:impliesServiceRigid('ServiceProvidersPartialReplicateService') || phiz:impliesServiceRigid('ServiceProvidersFullReplicateService')
                    || phiz:impliesServiceRigid('KBKEmployeeManagement')}">
    <tiles:insert definition="mainMenuInset">
        <tiles:put name="activity" value="${mode == 'ServiceProviders'}"/>
        <tiles:put name="action" value="/private/dictionaries/provider/list"/>
        <tiles:put name="text" value="Поставщики услуг"/>
    </tiles:insert>
</c:if>

<tiles:insert definition="mainMenuInset" service="NodesManager">
	<tiles:put name="activity" value="${mode == 'ExternalSystems'}"/>
	<tiles:put name="action" value="/dictionaries/routing/node/list"/>
	<tiles:put name="text" value="Внешние системы"/>
</tiles:insert>

<c:choose>
    <c:when test="${phiz:impliesService('BusinessSettingsManagement')}">
        <c:set var="optionsUrl" value="/payments/restrictions"/>
    </c:when>
    <c:otherwise>
        <c:set var="optionsUrl" value="/configure/index"/>
    </c:otherwise>
</c:choose>

<tiles:insert definition="mainMenuInset" group="SettingsA,SettingsE">
    <tiles:put name="activity" value="${mode == 'Options'}"/>
    <tiles:put name="action" value="${optionsUrl}"/>
    <tiles:put name="text" value="Настройки"/>
</tiles:insert>

<c:if test="${phiz:impliesService('InformationMessageService') || phiz:impliesServiceRigid('QuickPaymentPanelService') || phiz:impliesService('AdvertisingBlockManagment') || phiz:impliesService('OfferNotificationManagment')}">
    <tiles:insert definition="mainMenuInset">
        <tiles:put name="activity" value="${mode == 'InformManagment'}"/>
        <tiles:put name="action" value="/configure/inform/service"/>
        <tiles:put name="text" value="Реклама и сообщения"/>
    </tiles:insert>
</c:if>

<c:if test="${phiz:impliesServiceRigid('DepartmentsManagement') || phiz:impliesService('LimitsManagment')}">
    <tiles:insert definition="mainMenuInset">
        <tiles:put name="activity" value="${mode == 'Departments'}"/>
        <tiles:put name="action" value="/departments/list"/>
        <tiles:put name="text" value="Подразделения"/>
    </tiles:insert>
</c:if>

<tiles:insert definition="mainMenuInset" service="CertDemandControl">
	<tiles:put name="activity" value="${mode == 'CertDemandControl'}"/>
	<tiles:put name="action" value="/certification/list"/>
	<tiles:put name="text" value="Сертификаты"/>
</tiles:insert>

<tiles:insert definition="mainMenuInset" service="BankTemplatesManagement">
	<tiles:put name="activity" value="${mode == 'BankTemplatesManagement'}"/>
	<tiles:put name="action" value="/templates/templates"/>
	<tiles:put name="text" value="Шаблоны платежей"/>
</tiles:insert>

<c:choose>
    <c:when test="${phiz:impliesService('NewsManagment')}">
        <tiles:insert definition="mainMenuInset">
            <tiles:put name="activity" value="${mode == 'NewsManagment' || mode == 'NewsLoginPageManagment'}"/>
            <tiles:put name="action" value="/news/list"/>
            <tiles:put name="text" value="События"/>
        </tiles:insert>
    </c:when>
    <c:when test="${phiz:impliesService('NewsLoginPageManagment')}">
        <tiles:insert definition="mainMenuInset">
            <tiles:put name="activity" value="${mode == 'NewsLoginPageManagment'}"/>
            <tiles:put name="action" value="/news/login/page/list"/>
            <tiles:put name="text" value="События"/>
        </tiles:insert>
    </c:when>
</c:choose>

<c:set var="mailUrl" value=""/>
<c:choose>
    <c:when test="${phiz:impliesServiceRigid('MailManagment')}">
        <c:set var="mailUrl" value="/mail/list"/>
    </c:when>
    <c:when test="${phiz:impliesServiceRigid('MailArchiveManagment')}">
        <c:set var="mailUrl" value="/mail/archiving/edit"/>
    </c:when>
    <c:when test="${phiz:impliesServiceRigid('MailMessagesManagment')}">
        <c:set var="mailUrl" value="/mail/stmessages/edit"/>
    </c:when>
    <c:when test="${phiz:impliesServiceRigid('SubjectManagment')}">
        <c:set var="mailUrl" value="/mail/subjects/list"/>
    </c:when>
    <c:when test="${phiz:impliesServiceRigid('ContactCenterAreaManagment')}">
        <c:set var="mailUrl" value="/mail/area/list"/>
    </c:when>
</c:choose>
<c:if test="${not empty mailUrl}">
    <tiles:insert definition="mainMenuInset">
        <tiles:put name="activity" value="${mode == 'MailManagment'}"/>
        <tiles:put name="action" value="${mailUrl}"/>
        <tiles:put name="text" value="Письма"/>
    </tiles:insert>
</c:if>

<tiles:insert definition="mainMenuInset" service="Bankcells">
	<tiles:put name="activity" value="${mode == 'Bankcells'}"/>
	<tiles:put name="action" value="/bankcells/presence"/>
	<tiles:put name="text" value="Сейфы"/>
</tiles:insert>

<tiles:insert definition="mainMenuInset" service="Claims">
	<tiles:put name="activity" value="${mode == 'Claims'}"/>
	<tiles:put name="action" value="/claims/list"/>
	<tiles:put name="text" value="Заявки"/>
</tiles:insert>
<%--Депозиты--%>

<c:set var="action" value="${showDepositList == 'false' && showPromoCode == 'true' ? '/deposits/promoCodeDepositSettings' : '/deposits/list'}"/>
<tiles:insert definition="mainMenuInset" group="Deposits,DepositSettingsNodeE">
    <tiles:put name="activity" value="${mode == 'DepositsManagement' or mode == 'PromoCodeDepositSettingsService'}"/>
    <tiles:put name="action" value="${action}"/>
    <tiles:put name="text" value="Депозиты"/>
</tiles:insert>

<%-- Вкладка "Кредиты" для сотрудника --%>
<c:set var="loansUrl" value=""/>
<c:choose>
    <c:when test="${phiz:impliesService('OffersLoad')}">
        <c:set var="loansUrl" value="/loans/offers/loanLoad"/>
    </c:when>
    <c:when test="${phiz:impliesService('UnloadOffersProduct')}">
        <c:set var="loansUrl" value="/loans/offers/unloading"/>
    </c:when>
    <c:when test="${phiz:isUseNewLoanClaimAlgorithm()}">
        <c:choose>
            <c:when test="${phiz:impliesService('CreditProductTypeService')}">
                <c:set var="loansUrl" value="/loanclaim/credit/type/list"/>
            </c:when>
            <c:when test="${phiz:impliesService('CreditProductService')}">
                <c:set var="loansUrl" value="/loanclaim/credit/product/list"/>
            </c:when>
            <c:when test="${phiz:impliesService('CreditProductConditionService')}">
                <c:set var="loansUrl" value="/loanclaim/credit/condition/list"/>
            </c:when>
        </c:choose>
    </c:when>
    <c:when test="${!phiz:isUseNewLoanClaimAlgorithm() && phiz:impliesService('LoanProducts')}">
        <c:set var="loansUrl" value="/loans/products/list"/>
    </c:when>
</c:choose>
<c:if test="${not empty loansUrl}">
    <tiles:insert definition="mainMenuInset">
        <tiles:put name="activity" value="${mode == 'LoansManagement'}"/>
        <tiles:put name="action" value="${loansUrl}"/>
        <tiles:put name="text" value="Кредиты"/>
    </tiles:insert>
</c:if>

<%-- Вкладка "Кредиты" для администратора --%>
<c:set var="loansUrl" value=""/>
<c:choose>
    <c:when test="${phiz:impliesService('SetupAutoUnloadOfferProduct')}">
        <c:set var="loansUrl" value="/loans/offers/avtoUnloading"/>
    </c:when>
    <c:when test="${phiz:impliesService('SetttingLoanLoanService')}">
        <c:set var="loansUrl" value="/loans/offers/settingLoanLoad"/>
    </c:when>
</c:choose>
<c:if test="${not empty loansUrl}">
    <tiles:insert definition="mainMenuInset">
        <tiles:put name="activity" value="${mode == 'LoansManagement'}"/>
        <tiles:put name="action" value="${loansUrl}"/>
        <tiles:put name="text" value="Кредиты"/>
    </tiles:insert>
</c:if>

<tiles:insert definition="mainMenuInset" service="Monitiring">
	<tiles:put name="activity" value="${mode == 'ReportsAndMonitoring'}"/>
	<tiles:put name="action" value="/reports/index"/>
	<tiles:put name="text" value="Отчеты и мониторинг"/>
</tiles:insert>

<c:if test="${phiz:impliesService('CreditCardProducts') || phiz:impliesService('CardProducts') || phiz:impliesService('CreateCardsLimits') || phiz:impliesService('IncomeLevelService')}">
<tiles:insert definition="mainMenuInset">
    <tiles:put name="activity"  value="${mode == 'CreditCardsManagement'}"/>
    <c:choose>
        <c:when test="${phiz:impliesService('CreditCardProducts')}">
            <tiles:put name="action" value="/creditcards/products/list"/>
        </c:when>
        <c:when test="${phiz:impliesService('CreateCardsLimits')}">
            <tiles:put name="action" value="/cards/limits/list"/>
        </c:when>
        <c:when test="${phiz:impliesService('IncomeLevelService')}">
            <tiles:put name="action" value="/creditcards/incomes/list"/>
        </c:when>
        <c:otherwise>
            <tiles:put name="action" value="/card/products/list"/>
        </c:otherwise>
    </c:choose>
    <tiles:put name="text" value="Карты"/>
</tiles:insert>
</c:if>

<c:if test="${phiz:impliesOperation('ListTechnoBreakOperation', 'ManageTechnoBreaks')}">
    <tiles:insert definition="mainMenuInset">
        <tiles:put name="activity" value="${mode == 'TechnoBreaksManagment'}"/>
        <tiles:put name="action" value="technobreak/list"/>
        <tiles:put name="text" value="Технологические перерывы"/>
    </tiles:insert>
</c:if>


<tiles:insert definition="mainMenuInset" group="PFPSettings">
    <tiles:put name="activity" value="${mode == 'PFPManagment'}"/>
    <c:choose>
        <c:when test="${phiz:impliesService('PFPManagment')}">
            <tiles:put name="action" value="/pfp/targets/list"/>
        </c:when>
        <c:otherwise>
            <tiles:put name="action" value="/pfp/configure/segments"/>
        </c:otherwise>
    </c:choose>
    <tiles:put name="text" value="Настройка ПФП"/>
</tiles:insert>


<tiles:insert definition="mainMenuInset" group="PfpPassing">
    <tiles:put name="activity" value="${mode == 'pfpPassing'}"/>
    <tiles:put name="action" value="/pfp/person/search"/>
    <tiles:put name="text" value="Проведение ПФП"/>
</tiles:insert>


<tiles:insert definition="mainMenuInset" group="PfpJournal">
    <tiles:put name="activity" value="${mode == 'pfpJournalList'}"/>
    <tiles:put name="action" value="/journal/pfp/passingPfpJournal"/>
    <tiles:put name="text" value="Журнал ПФП"/>
</tiles:insert>


<c:if test="${phiz:impliesService('ErmbProfileManagment')}">
    <tiles:insert definition="mainMenuInset">
    <tiles:put name="activity" value="${mode == 'ErmbMain'}"/>
    <tiles:put name="action" value="ermb/person/search"/>
     <tiles:put name="text" value="ЕРМБ"/>
    </tiles:insert>
</c:if>

<c:if test="${phiz:impliesService('ErmbMigrationSettingsService') || phiz:impliesService('ErmbMigrationService')}">
    <tiles:insert definition="mainMenuInset">
        <tiles:put name="text" value="Миграция"/>
        <c:choose>
            <c:when test="${phiz:impliesService('ErmbMigrationSettingsService')}">
                    <tiles:put name="activity" value="${mode == 'MigrationSettings'}"/>
                    <tiles:put name="action" value="/ermb/migration/settings"/>
            </c:when>
            <c:otherwise>
                    <tiles:put name="activity" value="${mode == 'Load'}"/>
                    <tiles:put name="action" value="/ermb/migration/loading"/>
            </c:otherwise>
        </c:choose>
    </tiles:insert>
</c:if>

<c:if test="${phiz:impliesService('AddressBookContactsCountReportManagement') or phiz:impliesService('AddressBookRequestsCountReportManagement')
             or phiz:impliesService('AddressBookSyncCountExceedReportManagement')}">
    <tiles:insert definition="mainMenuInset">
        <tiles:put name="text"><bean:message bundle="addressBookReportsBundle" key="label.menu.main"/></tiles:put>
        <tiles:put name="activity" value="${mode == 'AddressBookReports'}"/>
        <tiles:put name="action" value="/addressBook/reports/index"/>
    </tiles:insert>
</c:if>


<tiles:insert definition="mainMenuInset" group="BusinessReportParamsSettings">
    <tiles:put name="activity" value="${mode == 'BusinessReportsConfigure'}"/>
    <tiles:put name="action" value="/reports/business/configure"/>
    <tiles:put name="text"><bean:message bundle="configureBundle" key="reports.business.label.menu.main"/></tiles:put>
</tiles:insert>
