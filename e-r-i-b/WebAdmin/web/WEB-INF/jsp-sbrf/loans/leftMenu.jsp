<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset" operation="LoanOfferLoadOperation">
	<tiles:put name="enabled" value="${submenu != 'LoanOffersLoad'}"/>
	<tiles:put name="action"  value="/loans/offers/loanLoad.do"/>
	<tiles:put name="text"    value="Загрузка предложений по кредитам"/>
	<tiles:put name="title"   value="Загрузка предложений по кредитам"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="UnloadOffersProduct">
	<tiles:put name="enabled" value="${submenu != 'UnloadAcceptOffers'}"/>
	<tiles:put name="action"  value="/loans/offers/unloading.do"/>
	<tiles:put name="text"    value="Ручная выгрузка предложений по кредитам"/>
	<tiles:put name="title"   value="Ручная выгрузка предложений по кредитам"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="LoanCardOfferLoadOperation">
	<tiles:put name="enabled" value="${submenu != 'CardOffersLoad'}"/>
	<tiles:put name="action"  value="/loans/offers/cardLoad.do"/>
	<tiles:put name="text"    value="Загрузка предложений по кредитным картам"/>
	<tiles:put name="title"   value="Загрузка предложений по кредитным картам"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="SetupAutoUnloadOfferProductOperation">
	<tiles:put name="enabled" value="${submenu != 'AutoUnloadAcceptOffers'}"/>
	<tiles:put name="action"  value="/loans/offers/avtoUnloading.do"/>
	<tiles:put name="text"    value="Настройка выгрузки заявок"/>
	<tiles:put name="title"   value="Настройка выгрузки заявок"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="SettingLoanOfferLoadOperation">
	<tiles:put name="enabled" value="${submenu != 'SettingLoanOfferLoad'}"/>
	<tiles:put name="action"  value="/loans/offers/settingLoanLoad.do"/>
	<tiles:put name="text"    value="Настройка загрузки предложений по кредитам"/>
	<tiles:put name="title"   value="Настройка загрузки предложений по кредитам"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="SettingLoanCardOfferLoadOperation">
	<tiles:put name="enabled" value="${submenu != 'SettingLoanCardOfferLoad'}"/>
	<tiles:put name="action"  value="/loans/offers/settingLoanCardLoad.do"/>
	<tiles:put name="text"    value="Настройка загрузки предложений по кредитным картам"/>
	<tiles:put name="title"   value="Настройка загрузки предложений по кредитным картам"/>
</tiles:insert>

<c:choose>
    <c:when test="${phiz:isUseNewLoanClaimAlgorithm()}">
        <tiles:insert definition="leftMenuInset" service="CreditProductTypeService">
            <tiles:put name="enabled" value="${submenu != 'CreditProductType'}"/>
            <tiles:put name="action"  value="/loanclaim/credit/type/list.do"/>
            <tiles:put name="text"    value="Типы кредитных продуктов"/>
            <tiles:put name="title"   value="Типы кредитных продуктов"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" service="CreditProductService">
            <tiles:put name="enabled" value="${submenu != 'CreditProduct'}"/>
            <tiles:put name="action"  value="/loanclaim/credit/product/list.do"/>
            <tiles:put name="text"    value="Кредитные продукты"/>
            <tiles:put name="title"   value="Кредитные продукты"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" service="CreditProductConditionService">
            <tiles:put name="enabled" value="${submenu != 'CreditProductCondition'}"/>
            <tiles:put name="action"  value="/loanclaim/credit/condition/list.do"/>
            <tiles:put name="text"    value="Условия по кредитным продуктам"/>
            <tiles:put name="title"   value="Условия по кредитным продуктам"/>
        </tiles:insert>
    </c:when>
    <c:otherwise>
        <tiles:insert definition="leftMenuInset" operation="ListLoanKindOperation">
            <tiles:put name="enabled" value="${submenu != 'LoanKinds'}"/>
            <tiles:put name="action"  value="/loans/kinds/list.do"/>
            <tiles:put name="text"    value="Виды продуктов"/>
            <tiles:put name="title"   value="Виды продуктов"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListLoanProductOperation">
            <tiles:put name="enabled" value="${submenu != 'LoanProducts'}"/>
            <tiles:put name="action"  value="/loans/products/list.do"/>
            <tiles:put name="text"    value="Кредитные продукты"/>
            <tiles:put name="title"   value="Кредитные продукты"/>
        </tiles:insert>
    </c:otherwise>
</c:choose>

<tiles:insert definition="leftMenuInset" operation="EditBKIProviderIdOperation">
    <tiles:put name="enabled" value="${submenu != 'OKBProviderId'}"/>
    <tiles:put name="action"  value="/loanreport/okb/editProviderId.do"/>
    <tiles:put name="text"    value="ИД Поставщика услуги для ОКБ"/>
    <tiles:put name="title"   value="ИД Поставщика услуги для ОКБ"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditBKITimeOperation">
    <tiles:put name="enabled" value="${submenu != 'EditCreditBureauTime'}"/>
    <tiles:put name="action"  value="/loanreport/bki/timeSettings.do"/>
    <tiles:put name="text">
        <bean:message key="left.menu.bki.settings.time.text" bundle="creditBureauBundle"/>
    </tiles:put>
    <tiles:put name="title">
        <bean:message key="left.menu.bki.settings.time.title" bundle="creditBureauBundle"/>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ListLoanClaimQuestionOperation">
    <tiles:put name="enabled" value="${submenu != 'questionList'}"/>
    <tiles:put name="action"  value="/loans/question/list.do"/>
    <tiles:put name="text"    value="Конструктор вопросов"/>
    <tiles:put name="title"   value="Конструктор вопросов"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditWaitingTimeOperation">
    <tiles:put name="enabled" value="${submenu != 'WaitingTimeSetting'}"/>
    <tiles:put name="action"  value="/crediting/editWaitingTime.do"/>
    <tiles:put name="text"    value="Настройка времени ожидания получения предодобренных предложений из CRM"/>
    <tiles:put name="title"   value="Настройка времени ожидания получения предодобренных предложений из CRM"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="LoanClaimSettingsEditOperation">
    <tiles:put name="enabled" value="${submenu != 'LoanClaimSettings'}"/>
    <tiles:put name="action"  value="/loanClaim/settings.do"/>
    <tiles:put name="text"    value="Заявки"/>
    <tiles:put name="title"   value="Заявки"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="LoanSettingsEditOperation">
    <tiles:put name="enabled" value="${submenu != 'LoanSettings'}"/>
    <tiles:put name="action"  value="/loan/settings.do"/>
    <tiles:put name="text"    value="Настройки"/>
    <tiles:put name="title"   value="Настройки"/>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup">
    <tiles:put name="title"   value="Шаблоны"/>
    <tiles:put name="text"    value="Шаблоны"/>
    <tiles:put name="enabled" value="${submenu != 'CreditOfferTemplate'}"/>

    <tiles:put name="data">
        <tiles:insert definition="leftMenuInset" flush="false" service="CreditOfferTemplateManagement">
            <tiles:put name="enabled" value="${submenu != 'CreditOfferTemplate'}"/>
            <tiles:put name="action"  value="/templates/offer/list.do"/>
            <tiles:put name="title"   value="Шаблон кредитной оферты"/>
            <tiles:put name="text"    value="Шаблон кредитной оферты"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" flush="false" service="CreditOfferPdpTemplateManagement">
            <tiles:put name="enabled" value="${submenu != 'CreditOfferPdpTemplate'}"/>
            <tiles:put name="action"  value="/templates/offer/pdp/edit.do"/>
            <tiles:put name="title"   value="Шаблон на полное досрочное погашение кредита"/>
            <tiles:put name="text"    value="Шаблон на полное досрочное погашение кредита"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="LoanOffersSettingsEditOperation">
    <tiles:put name="enabled" value="${submenu != 'LoanOffersSettings'}"/>
    <tiles:put name="action"  value="/loanOffer/settings.do"/>
    <tiles:put name="text"    value="Оферты"/>
    <tiles:put name="title"   value="Оферты"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="LoanClaimFindOperation">
    <tiles:put name="enabled" value="${submenu != 'LoanClaimFind'}"/>
    <tiles:put name="action"  value="/loanClaim/find.do"/>
    <tiles:put name="text"    value="Кредитные заявки"/>
    <tiles:put name="title"   value="Кредитные заявки"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation = "EarlyLoanPaymentSettingsOperation">
    <tiles:put name="enabled" value="${submenu != 'loansEarlyRepay'}"/>
    <tiles:put name="action"  value="/loans/configure.do"/>
    <tiles:put name="text"    value="Досрочное погашение"/>
    <tiles:put name="title"   value="Досрочное погашение"/>
</tiles:insert>