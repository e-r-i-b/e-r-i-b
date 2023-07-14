<%--
  Created by IntelliJ IDEA.
  User: Pakhomova
  Date: 15.07.2008
  Time: 17:57:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"  %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset" service="BankListManagement">
	<tiles:put name="enabled" value="${submenu != 'BanksList'}"/>
	<tiles:put name="action"  value="/private/multiblock/dictionary/banks/national"/>
	<tiles:put name="text"    value="Справочник банков"/>
	<tiles:put name="title"   value="Справочник банков"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="SynchronizeDictionariesOperation" service="*">
    <tiles:put name="enabled" value="${submenu != 'Synchronize'}"/>
    <tiles:put name="action"  value="/dictionaries/synchronize.do"/>
    <tiles:put name="text"    value="Загрузка справочников"/>
    <tiles:put name="title"   value="Загрузка справочников"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditDepositSynchronizeSettingsOperation">
    <tiles:put name="enabled" value="${submenu != 'DepositSettings'}"/>
    <tiles:put name="action"  value="/dictionaries/synchronize/deposit_settings.do"/>
    <tiles:put name="text"    value="Настройка вкладов для загрузки"/>
    <tiles:put name="title"   value="Настройка вкладов для загрузки"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditCardSynchronizeSettingsOperation">
    <tiles:put name="enabled" value="${submenu != 'CardSettings'}"/>
    <tiles:put name="action"  value="/dictionaries/synchronize/card_settings.do"/>
    <tiles:put name="text"    value="Настройка карт для загрузки"/>
    <tiles:put name="title"   value="Настройка карт для загрузки"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditIMASynchronizeSettingsOperation">
    <tiles:put name="enabled" value="${submenu != 'IMASettings'}"/>
    <tiles:put name="action"  value="/dictionaries/synchronize/ima_settings.do"/>
    <tiles:put name="text"    value="Настройка ОМС для загрузки"/>
    <tiles:put name="title"   value="Настройка ОМС для загрузки"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ViewMobileOperators">
	<tiles:put name="enabled" value="${submenu != 'MobileOperators'}"/>
	<tiles:put name="action"  value="/dictionaries/mobileoperators/list.do"/>
	<tiles:put name="text"    value="Справочник мобильных операторов"/>
	<tiles:put name="title"   value="Справочник мобильных операторов"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="GroupRiskManagment" operation="ListGroupRiskOperation">
    <tiles:put name="enabled" value="${submenu != 'GroupRiskList'}"/>
    <tiles:put name="action"  value="/group/risk/list.do"/>
    <tiles:put name="text"    value="Группы риска"/>
    <tiles:put name="title"   value="Просмотр групп риска"/>
</tiles:insert>

<c:set var="impliesTariffTransferOtherBankService" value="${phiz:impliesService('TariffTransferOtherBankService')}"/>
<c:set var="impliesTariffTransferOtherTBService" value="${phiz:impliesService('TariffTransferOtherTBService')}"/>

<c:if test="${impliesTariffTransferOtherBankService || impliesTariffTransferOtherTBService}">
    <tiles:insert definition="leftMenuInsetGroup">
        <tiles:put name="text" value="Справочник тарифов"/>
        <tiles:put name="name" value="tariffs"/>
        <tiles:put name="enabled" value="${submenu != 'tariffTransferOtherTB' and submenu != 'tariffTransferOtherBank'}"/>
        <tiles:put name="data">
            <tiles:insert definition="leftMenuInset" flush="false" service="TariffTransferOtherTBService">
                <tiles:put name="enabled" value="${submenu != 'tariffTransferOtherTB'}"/>
                <tiles:put name="action" value="/dictionary/tariffs/transferOtherTB.do"/>
                <tiles:put name="text" value="Перевод в другой ТБ"/>
                <tiles:put name="title" value="Перевод в другой ТБ"/>
                <tiles:put name="parentName" value="tariffs"/>
            </tiles:insert>
            <tiles:insert definition="leftMenuInset" flush="false" service="TariffTransferOtherBankService">
                <tiles:put name="enabled" value="${submenu != 'tariffTransferOtherBank'}"/>
                <tiles:put name="action" value="/dictionary/tariffs/transferOtherBank.do"/>
                <tiles:put name="text" value="Перевод в другой банк"/>
                <tiles:put name="title" value="Перевод в другой банк"/>
                <tiles:put name="parentName" value="tariffs"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</c:if>

<tiles:insert definition="leftMenuInset" operation="DictionaryInformationOperation">
    <tiles:put name="enabled" value="${submenu != 'DictionariesSynchronizationInformation'}"/>
    <tiles:put name="action"  value="/dictionaries/synchronization/information"/>
    <tiles:put name="text"><bean:message bundle="synchronizationInformationBundle" key="left.menu.item.text"/></tiles:put>
    <tiles:put name="title"><bean:message bundle="synchronizationInformationBundle" key="left.menu.item.title"/></tiles:put>
</tiles:insert>
