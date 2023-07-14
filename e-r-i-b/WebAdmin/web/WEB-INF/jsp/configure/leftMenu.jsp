<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset" service="EditImageSettingsManagment">
	<tiles:put name="enabled" value="${submenu != 'EditImageSettings'}"/>
	<tiles:put name="action" value="/settings/image/edit.do"/>
	<tiles:put name="text" value="Настройка изображений"/>
	<tiles:put name="title" value="Настройка изображений"/>
</tiles:insert>

<c:if test="${phiz:impliesServiceRigid('ALFManagement')}">
    <tiles:insert definition="leftMenuInsetGroup" flush="false" >
        <c:set var="enabledCategoriesList" value="${submenu != 'ListCardOperationCategory'}"/>
        <c:set var="enabledReCategorization" value="${submenu != 'AutoReCategorizationSettings'}"/>
        <c:set var="enabledPersonalFinanceSettings" value="${submenu != 'PersonalFinanceSettings'}"/>
        <c:set var="enabledInternalOperationsSettings" value="${submenu != 'InternalOperationsSettings'}"/>
        <c:set var="enabledEditCardOperationLifetime" value="${submenu != 'EditCardOperationLifetime'}"/>

        <tiles:put name="enabled" value="${enabledCategoriesList and enabledInternalOperationsSettings and enabledPersonalFinanceSettings and enabledReCategorization}"/>
        <tiles:put name="name"    value="settings_ALF"/>
        <tiles:put name="text"    value="Настройки АЛФ"/>

        <tiles:put name="data">
            <tiles:insert definition="leftMenuInset" flush="false" operation="AutoReCategorizationSettingsOperation">
                <tiles:put name="enabled" value="${enabledReCategorization}"/>
                <tiles:put name="action"  value="/finances/automaticReCategorization"/>
                <tiles:put name="text"    value="Автоматическая перекатегоризация"/>
                <tiles:put name="title"   value="Автоматическая перекатегоризация"/>
                <tiles:put name="parentName" value="settings_ALF"/>
            </tiles:insert>

            <tiles:insert definition="leftMenuInset" flush="false" operation="ListCardOperationCategoryOperation">
                <tiles:put name="enabled" value="${enabledCategoriesList}"/>
                <tiles:put name="action"  value="/finances/categories/list.do"/>
                <tiles:put name="text"    value="Справочник категорий операций"/>
                <tiles:put name="title"   value="Справочник категорий операций"/>
                <tiles:put name="parentName" value="settings_ALF"/>
            </tiles:insert>
            <tiles:insert definition="leftMenuInset" flush="false" operation="EditInternalOperationsSettingsOperation">
                <tiles:put name="enabled" value="${enabledInternalOperationsSettings}"/>
                <tiles:put name="action"  value="/finances/settings/internalOperations.do"/>
                <tiles:put name="text"    value="Настройки категории «Переводы между своими картами»"/>
                <tiles:put name="title"   value="Настройки категории «Переводы между своими картами»"/>
                <tiles:put name="parentName" value="settings_ALF"/>
            </tiles:insert>
            <tiles:insert definition="leftMenuInset" flush="false" operation="EditPersonalFinanceSettingsOperation">
                <tiles:put name="enabled" value="${enabledPersonalFinanceSettings}"/>
                <tiles:put name="action"  value="/finances/settings/edit.do"/>
                <tiles:put name="text"    value="Настройки АЛФ"/>
                <tiles:put name="title"   value="Настройки АЛФ"/>
                <tiles:put name="parentName" value="settings_ALF"/>
            </tiles:insert>
            <tiles:insert definition="leftMenuInset" flush="false" operation="EditPersonalFinanceSettingsOperation">
                <tiles:put name="enabled" value="${enabledEditCardOperationLifetime}"/>
                <tiles:put name="action"  value="/finances/settings/editCardOperationsLifetime.do"/>
                <tiles:put name="text"    value="Настройка времени жизни авторизаций"/>
                <tiles:put name="title"   value="Настройка времени жизни авторизаций"/>
                <tiles:put name="parentName" value="settings_ALF"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</c:if>

<tiles:insert definition="leftMenuInset" service="EmployeeSettingsService">
    <tiles:put name="enabled" value="${submenu != 'EmployeeSettings'}"/>
    <tiles:put name="action"  value="/employee/settings/configure.do"/>
    <tiles:put name="text"><bean:message bundle="configureBundle" key="settings.employee.menuitem"/></tiles:put>
    <tiles:put name="title"><bean:message bundle="configureBundle" key="settings.employee.menuitem"/></tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ConnectUDBOSettingsService">
    <tiles:put name="enabled" value="${submenu != 'ConnectUDBOSettings'}"/>
    <tiles:put name="action"  value="/settings/connect/udbo.do"/>
    <tiles:put name="text"><bean:message bundle="configureBundle" key="settings.connect.udbo.menuitem"/></tiles:put>
    <tiles:put name="title"><bean:message bundle="configureBundle" key="settings.connect.udbo.menuitem"/></tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="SBNKDSettingsService">
    <tiles:put name="enabled" value="${submenu != 'SBNKDSettings'}"/>
    <tiles:put name="action"  value="/settings/sbnkd.do"/>
    <tiles:put name="text"><bean:message bundle="configureBundle" key="settings.sbnkd.menuitem"/></tiles:put>
    <tiles:put name="title"><bean:message bundle="configureBundle" key="settings.sbnkd.menuitem"/></tiles:put>
</tiles:insert>

<c:if test="${phiz:impliesServiceRigid('SystemSettingsManagement')}">
    <tiles:insert definition="leftMenuInset">
        <tiles:put name="enabled" value="${submenu != 'ClientsEmployees'}"/>
        <tiles:put name="action" value="/persons/configure.do"/>
        <tiles:put name="text" value="Клиенты и сотрудники"/>
        <tiles:put name="title" value="Клиенты и сотрудники"/>
    </tiles:insert>

    <tiles:insert definition="leftMenuInset">
        <tiles:put name="enabled" value="${submenu != 'Schemes'}"/>
        <tiles:put name="action" value="/schemes/configure.do"/>
        <tiles:put name="text" value="Права доступа сотрудника"/>
        <tiles:put name="title" value="Настройка прав доступа сотрудников по умолчанию"/>
    </tiles:insert>

    <tiles:insert definition="leftMenuInset">
        <tiles:put name="enabled" value="${submenu != 'ClientTemplate'}"/>
        <tiles:put name="action" value="/policy/clientTemplate.do"/>
        <tiles:put name="text" value="Права доступа клиента"/>
        <tiles:put name="title" value="Настройка прав доступа клиентов по умолчанию"/>
    </tiles:insert>
</c:if>

<tiles:insert definition="leftMenuInset" service="PersonPasswordCardManagment">
	<tiles:put name="enabled" value="${submenu != 'Cards'}"/>
	<tiles:put name="action" value="/passwordcards/configure.do"/>
	<tiles:put name="text" value="Карты ключей"/>
	<tiles:put name="title" value="Карты ключей"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ViewSmsSettingsOperation">
	<tiles:put name="enabled" value="${submenu != 'SmsService'}"/>
	<tiles:put name="action" value="/sms/configure.do"/>
	<tiles:put name="text" value="Одноразовые пароли (SMS, PUSH)"/>
	<tiles:put name="title" value="Одноразовые пароли (SMS, PUSH)"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ContactDictionariesSettingsManagement">
	<tiles:put name="enabled" value="${submenu != 'Dictionaries'}"/>
	<tiles:put name="action" value="/dictionaries/configureContact.do"/>
	<tiles:put name="text" value="Справочники CONTACT"/>
	<tiles:put name="title" value="Справочники CONTACT"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="SetDictionariesConfigsOperation">
	<tiles:put name="enabled" value="${submenu != 'Dictionaries'}"/>
	<tiles:put name="action" value="/dictionaries/configure.do"/>
	<tiles:put name="text" value="Справочники"/>
	<tiles:put name="title" value="Справочники"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="Schedules">
	<tiles:put name="enabled" value="${submenu != 'SchedulesE'}"/>
	<tiles:put name="action" value="/schedules/list.do?kind=E"/>
	<tiles:put name="text" value="Расписания сотрудников"/>
	<tiles:put name="title" value="Расписания сотрудников"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="Schedules">
    <tiles:put name="enabled" value="${submenu != 'SchedulesC'}"/>
	<tiles:put name="action" value="/schedules/list.do?kind=C"/>
	<tiles:put name="text" value="Расписания клиентов"/>
	<tiles:put name="title" value="Расписания клиентов"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="CurrentSkins">
    <tiles:put name="enabled" value="${submenu != 'CurrentSkin'}"/>
	<tiles:put name="action" value="/skins/current.do"/>
	<tiles:put name="text"  value="Стандартный стиль"/>
	<tiles:put name="title" value="Стандартный стиль"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="SkinsManager">
    <tiles:put name="enabled" value="${submenu != 'ManageSkins'}"/>
	<tiles:put name="action" value="/skins/list.do"/>
	<tiles:put name="text" value="Управление стилями"/>
	<tiles:put name="title" value="Управление стилями"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ViewSystemSettingsOperation">
	<tiles:put name="enabled" value="${submenu != 'SysSettings'}"/>
	<tiles:put name="action" value="/systemSettings/configure.do"/>
	<tiles:put name="text" value="Параметры системы"/>
	<tiles:put name="title" value="Параметры системы"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="StoredProductTimeUsing">
    <tiles:put name="enabled" value="${submenu != 'StoredProductsTimeConfig'}"/>
    <tiles:put name="action" value="/productSettings/configure.do"/>
    <tiles:put name="text"   value="Настройка времени актуальности данных"/>
    <tiles:put name="title"  value="Настройка времени актуальности данных"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="PromoCodesManagmentAdmin">
	<tiles:put name="enabled" value="${submenu != 'PromoCodeSettings'}"/>
	<tiles:put name="action"  value="/promocodes/list.do"/>
	<tiles:put name="text"    value="Настройка  промо-акций"/>
	<tiles:put name="title"   value="Настройка  промо-акций"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="DepartmentsManagement">
	<tiles:put name="enabled" value="${submenu != 'SendSMSPreferredMethod'}"/>
	<tiles:put name="action"  value="/sendsmsmethod/configure.do"/>
	<tiles:put name="text"    value="Номер телефона для рассылки SMS"/>
	<tiles:put name="title"   value="Номер телефона для рассылки SMS"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="WhiteListUrlService">
	<tiles:put name="enabled" value="${submenu != 'WhiteListUrl'}"/>
	<tiles:put name="action"  value="/whitelist/list.do"/>
	<tiles:put name="text"    value="Разрешенные URL-адреса"/>
	<tiles:put name="title"   value="Разрешенные URL-адреса"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="AdminMailSettingsManagement">
	<tiles:put name="enabled" value="${submenu != 'MailSettings'}"/>
	<tiles:put name="action"  value="/mailSettings/configure.do"/>
	<tiles:put name="text"    value="Ограничение количества символов, вводимых при создании письма"/>
	<tiles:put name="title"   value="Ограничение количества символов, вводимых при создании письма"/>
</tiles:insert>

<c:if test="${phiz:impliesServiceRigid('MobileApiSettingsService') or phiz:impliesServiceRigid('AtmApiSettingsService')}">
    <tiles:insert definition="leftMenuInsetGroup" flush="false" >
        <c:set var="enabledMobileApi" value="${submenu != 'MobileApiSettings'}"/>
        <c:set var="enabledMobilePlatform" value="${submenu != 'MobilePlatformSettings'}"/>
        <c:set var="enabledSocialPlatform" value="${submenu != 'SocialPlatformSettings'}"/>
        <c:set var="enabledAtmApi" value="${submenu != 'AtmApiSettings'}"/>

        <tiles:put name="name"    value="settings_api"/>
        <tiles:put name="text"    value="Настройки API"/>
        <tiles:put name="enabled" value="${enabledMobileApi and enabledAtmApi and enabledMobilePlatform and enabledSocialPlatform}"/>

        <tiles:put name="data">
            <tiles:insert definition="leftMenuInset" flush="false" operation="MobileApiSettingsOperation">
                <tiles:put name="enabled" value="${enabledMobileApi}"/>
                <tiles:put name="action"  value="/mobileApi/configure.do"/>
                <tiles:put name="text"    value="Общие настройки mAPI"/>
                <tiles:put name="title"   value="Общие настройки mAPI"/>
                <tiles:put name="parentName" value="settings_api"/>
            </tiles:insert>
            <tiles:insert definition="leftMenuInset" flush="false" operation="MobilePlatformSettingsOperation">
                <tiles:put name="enabled" value="${enabledMobilePlatform}"/>
                <tiles:put name="action"  value="/mobileApi/configurePlatform.do"/>
                <tiles:put name="text"    value="Настройки mAPI в разрезе платформ"/>
                <tiles:put name="title"   value="Настройки mAPI в разрезе платформ"/>
                <tiles:put name="parentName" value="settings_api"/>
            </tiles:insert>
            <tiles:insert definition="leftMenuInset" flush="false" operation="MobilePlatformSettingsOperation">
                <tiles:put name="enabled" value="${enabledSocialPlatform}"/>
                <tiles:put name="action"  value="/socialApi/configurePlatform.do"/>
                <tiles:put name="text"    value="Социальные приложения"/>
                <tiles:put name="title"   value="Социальные приложения"/>
                <tiles:put name="parentName" value="settings_api"/>
            </tiles:insert>
            <tiles:insert definition="leftMenuInset" flush="false" operation="AtmApiSettingsOperation">
                <tiles:put name="enabled" value="${enabledAtmApi}"/>
                <tiles:put name="action"  value="/atmApi/configure.do"/>
                <tiles:put name="text"    value="atmApi"/>
                <tiles:put name="title"   value="atmApi"/>
                <tiles:put name="parentName" value="settings_api"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</c:if>

<tiles:insert definition="leftMenuInset" operation="MobileBankSettingsOperation">
	<tiles:put name="enabled" value="${submenu != 'MobileBankSettings'}"/>
	<tiles:put name="action"  value="/mobileBank/configure.do"/>
	<tiles:put name="text"    value="Мобильный банк"/>
	<tiles:put name="title"   value="Мобильный банк"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditPaymentRestrictionsOperation">
	<tiles:put name="enabled" value="${submenu != 'EditPaymentRestrictionsSettings'}"/>
	<tiles:put name="action"  value="/payments/restrictions.do"/>
	<tiles:put name="text"    value="Ограничения на операции"/>
	<tiles:put name="title"   value="Ограничения на операции"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="WidgetCatalogManagementOperation">
	<tiles:put name="enabled" value="${submenu != 'WidgetCatalogSettings'}"/>
	<tiles:put name="action"  value="/widgets/management.do"/>
	<tiles:put name="text"    value="Управление каталогом виджетов"/>
	<tiles:put name="title"   value="Управление каталогом виджетов"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="OperationalSettingsManagement">
	<tiles:put name="enabled" value="${submenu != 'UrlListSettings'}"/>
	<tiles:put name="action"  value="/urlAddress/configure.do"/>
	<tiles:put name="text"    value="URL-адреса"/>
	<tiles:put name="title"   value="URL-адреса"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="OperationalSettingsManagement">
	<tiles:put name="enabled" value="${submenu != 'LoyaltyProgramSettings'}"/>
	<tiles:put name="action"  value="/loyaltyProgram/configure.do"/>
	<tiles:put name="text"    value="Программа лояльности"/>
	<tiles:put name="title"   value="Программа лояльности"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditSettingsLoansOperation">
	<tiles:put name="enabled" value="${submenu != 'EditSettingsLoansOperation'}"/>
	<tiles:put name="action"  value="/settings/loans.do"/>
	<tiles:put name="text"    value="Кредиты"/>
	<tiles:put name="title"   value="Кредиты"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditOuterTemplateOperation">
    <tiles:put name="enabled" value="${submenu != 'EditOuterTemplateOperation'}"/>
    <tiles:put name="action"  value="/settings/outerTemplate.do"/>
    <tiles:put name="text"    value="Настройки внешнего шаблона"/>
    <tiles:put name="title"   value="Настройки внешнего шаблона"/>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup" flush="false" service="SMSManagement">
    <c:set var="enabledErib" value="${not(submenu eq 'SmsSettingsErib')}"/>
    <c:set var="enabledCSA"  value="${not(submenu eq 'SmsSettingsCSA')}"/>
    <c:set var="enabledErmb"  value="${not(submenu eq 'SmsSettingsErmb')}"/>

    <tiles:put name="name"    value="setting_sms"/>
    <tiles:put name="text"    value="Настройка SMS"/>
    <tiles:put name="enabled" value="${enabledErib && enabledCSA}"/>

	<tiles:put name="data">
        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="text"       value="Настройка SMS ЕРИБ"/>
            <tiles:put name="title"      value="Настройка SMS ЕРИБ"/>
            <tiles:put name="action"     value="/sms/erib/settings/list.do"/>
            <tiles:put name="enabled"    value="${enabledErib}"/>
            <tiles:put name="parentName" value="setting_sms"/>
        </tiles:insert>

        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="text"       value="Настройка SMS ЦСА"/>
            <tiles:put name="title"      value="Настройка SMS ЦСА"/>
            <tiles:put name="action"     value="/sms/csa/settings/list.do"/>
            <tiles:put name="enabled"    value="${enabledCSA}"/>
            <tiles:put name="parentName" value="setting_sms"/>
        </tiles:insert>

        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="text"       value="Настройка SMS ЕРМБ"/>
            <tiles:put name="title"      value="Настройка SMS ЕРМБ"/>
            <tiles:put name="action"     value="/sms/ermb/settings/list.do"/>
            <tiles:put name="enabled"    value="${enabledErmb}"/>
            <tiles:put name="parentName" value="setting_sms"/>
        </tiles:insert>
	</tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup" flush="false" service="DepositsManagement">
    <c:set var="enabledDepositSettings" value="${not(submenu eq 'EditSettingsDepositsOperation')}"/>
    <c:set var="enabledMessageSettings" value="${not(submenu eq 'AccountMessageConfig')}"/>

    <tiles:put name="name"    value="deposit_setting"/>
    <tiles:put name="text"    value="Депозиты"/>
    <tiles:put name="enabled" value="${enabledDepositSettings && enabledMessageSettings}"/>

	<tiles:put name="data">
        <tiles:insert definition="leftMenuInset" operation="EditSettingsDepositsOperation" flush="false">
            <tiles:put name="text"       value="Настройки депозитов"/>
            <tiles:put name="title"      value="Настройки депозитов"/>
            <tiles:put name="action"     value="/settings/deposits.do"/>
            <tiles:put name="enabled"    value="${enabledDepositSettings}"/>
            <tiles:put name="parentName" value="deposit_setting"/>
        </tiles:insert>

        <tiles:insert definition="leftMenuInset" operation="AccountMessageConfigureOperation" flush="false">
            <tiles:put name="text"       value="Настройки подсказок"/>
            <tiles:put name="title"      value="Настройки подсказок"/>
            <tiles:put name="action"     value="/accountMessage/configure.do"/>
            <tiles:put name="enabled"    value="${enabledMessageSettings}"/>
            <tiles:put name="parentName" value="deposit_setting"/>
        </tiles:insert>

	</tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditSettingsMenuOperation">
	<tiles:put name="enabled" value="${submenu != 'EditSettingsMenuOperation'}"/>
	<tiles:put name="action"  value="/settings/menu.do"/>
	<tiles:put name="text"    value="Новости"/>
	<tiles:put name="title"   value="Новости"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ArchivingExceptionOperation">
	<tiles:put name="enabled" value="${submenu != 'EditExceptionArchivingSettings'}"/>
	<tiles:put name="action"  value="/settings/archiving/exception.do"/>
	<tiles:put name="text"    value="Отчеты об ошибках"/>
	<tiles:put name="title"   value="Отчеты об ошибках"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ExceptionDefaultMessageEditOperation">
	<tiles:put name="enabled" value="${submenu != 'ExceptionDefaultMessageEdit'}"/>
	<tiles:put name="action"  value="/configure/exceptions/defaultMessages.do"/>
	<tiles:put name="text"    value="Сообщение об ошибке"/>
	<tiles:put name="title"   value="Сообщение об ошибке"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditSettingsSecurityOperation">
	<tiles:put name="enabled" value="${submenu != 'EditSettingsSecurityOperation'}"/>
	<tiles:put name="action"  value="/settings/security.do"/>
	<tiles:put name="text"    value="Настройка параметров для уровня безопасности"/>
	<tiles:put name="title"   value="Настройка параметров для уровня безопасности"/>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup" flush="false" service="EditAddressBookSynchronizationSettings">
    <c:set var="enabledAddressBookSettings" value="${not(submenu eq 'EditThresholdNotificationSettingsOperation')}"/>
    <c:set var="enabledInformMessageSettings" value="${not(submenu eq 'EditInformMessageOperation')}"/>

    <tiles:put name="name"    value="addressBookSettings"/>
    <tiles:put name="text"    value="Настройка синхронизации АК"/>
    <tiles:put name="enabled" value="${enabledAddressBookSettings and enabledInformMessageSettings}"/>

    <tiles:put name="data">
        <tiles:insert definition="leftMenuInset" flush="false" operation="EditThresholdNotificationOperation" service="EditAddressBookSynchronizationSettings">
            <tiles:put name="text"       value="Оповещение о превышении порога"/>
            <tiles:put name="title"      value="Оповещение о превышении порога"/>
            <tiles:put name="action"     value="/settings/thresholdNotification.do"/>
            <tiles:put name="enabled"    value="${enabledAddressBookSettings}"/>
            <tiles:put name="parentName" value="addressBookSettings"/>
        </tiles:insert>

        <tiles:insert definition="leftMenuInset" flush="false" operation="EditInformMessageOperation" service="EditAddressBookSynchronizationSettings">
            <tiles:put name="text"       value="Настройка информирующего сообщения"/>
            <tiles:put name="title"      value="Настройка информирующего сообщения"/>
            <tiles:put name="action"     value="/settings/editInformMessage.do"/>
            <tiles:put name="enabled"    value="${enabledInformMessageSettings}"/>
            <tiles:put name="parentName" value="addressBookSettings"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="EditSmartAddressBookSynchronizationSettings">
    <tiles:put name="enabled" value="${submenu != 'EditSmartAddressBookSynchronizationSettings'}"/>
    <tiles:put name="action"  value="/settings/addressbook/synchronization.do"/>
    <tiles:put name="text"><bean:message bundle="configureBundle" key="title.UAK.sync.setting"/></tiles:put>
    <tiles:put name="title"><bean:message bundle="configureBundle" key="title.UAK.sync.setting"/></tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="BankDetailsConfigureService">
    <tiles:put name="enabled" value="${submenu != 'BankDetailsConfig'}"/>
	<tiles:put name="action"  value="/bankDetails/configure.do"/>
	<tiles:put name="text"    value="Настройка реквизитов банка"/>
	<tiles:put name="title"   value="Настройка реквизитов банка"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="SPOOBKConfigureService">
    <tiles:put name="enabled" value="${submenu != 'SPOOBKConfig'}"/>
	<tiles:put name="action"  value="/spoobk/configure.do"/>
	<tiles:put name="text"    value="Параметры СПООБК"/>
	<tiles:put name="title"   value="Параметры СПООБК"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="CurrencyRateConfigureService">
    <tiles:put name="enabled" value="${submenu != 'CurrencyRateConfig'}"/>
	<tiles:put name="action"  value="/currencyRate/configure.do"/>
	<tiles:put name="text"    value="Курсы"/>
	<tiles:put name="title"   value="Курсы"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ConfirmSelfOperationsOperation" service="*">
    <tiles:put name="enabled" value="${submenu != 'ConfirmSelfOperations'}"/>
	<tiles:put name="action"  value="/person/confirmSelfOperations.do"/>
    <tiles:put name="text"><bean:message bundle="configureBundle" key="left.menu.confirm.selfoperations"/></tiles:put>
    <tiles:put name="title"><bean:message bundle="configureBundle" key="left.menu.confirm.selfoperations"/></tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ChangeTemplateSettingsService">
    <tiles:put name="enabled" value="${submenu != 'Templates'}"/>
	<tiles:put name="action"  value="/configure/Templates.do"/>
	<tiles:put name="text"><bean:message bundle="configureBundle" key="settings.clienttemplates.menuitem"/></tiles:put>
	<tiles:put name="title"><bean:message bundle="configureBundle" key="settings.clienttemplates.menuitem"/></tiles:put>
</tiles:insert>

<c:if test="${phiz:impliesServiceRigid('EditAutoPaymentsNearestDateRestrictionService')}">
    <tiles:insert definition="leftMenuInsetGroup" flush="false">
        <c:set var="enabledAP" value="${submenu != 'AutoPaymentDateRestriction' and submenu != 'AutoPaymentP2PNewCommission'}"/>

        <tiles:put name="name"    value="autoPaymentSettings"/>
        <tiles:put name="text"    value="Автоплатежи"/>
        <tiles:put name="enabled" value="${enabledAP}"/>

        <tiles:put name="data">
            <tiles:insert definition="leftMenuInset" operation="EditAutoPaymentsNearestDateRestrictionOperation" flush="false">
                <tiles:put name="enabled" value="${submenu != 'AutoPaymentDateRestriction'}"/>
                <tiles:put name="action"  value="/settings/autopayments/nearestDateRestriction.do"/>
                <tiles:put name="text"><bean:message bundle="configureBundle" key="settings.autopayments.nearestDateRestriction.activity"/></tiles:put>
                <tiles:put name="title"><bean:message bundle="configureBundle" key="settings.autopayments.nearestDateRestriction.activity"/></tiles:put>
            </tiles:insert>

            <tiles:insert definition="leftMenuInset" operation="EditAutoPaymentsP2PNewCommissionOperation" flush="false">
                <tiles:put name="enabled" value="${submenu != 'AutoPaymentP2PNewCommission'}"/>
                <tiles:put name="action"  value="/settings/autopayments/p2pcommission.do"/>
                <tiles:put name="text"><bean:message bundle="configureBundle" key="settings.autopayments.p2pcommission"/></tiles:put>
                <tiles:put name="title"><bean:message bundle="configureBundle" key="settings.autopayments.p2pcommission"/></tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</c:if>

<tiles:insert definition="leftMenuInset" operation="EditASVypiskaActivityOperation">
    <tiles:put name="enabled" value="${submenu != 'ASVypiska'}"/>
	<tiles:put name="action"  value="/settings/cards/delivery/asvypiska.do"/>
	<tiles:put name="text"><bean:message bundle="configureBundle" key="settings.cards.delivery.asvypiska"/></tiles:put>
	<tiles:put name="title"><bean:message bundle="configureBundle" key="settings.cards.delivery.asvypiska"/></tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="LocaleManagement">
    <tiles:put name="enabled" value="${submenu != 'Locales'}"/>
	<tiles:put name="action"  value="/configure/locale/list.do"/>
	<tiles:put name="text"><bean:message bundle="configureBundle" key="settings.locales.menuitem"/></tiles:put>
	<tiles:put name="title"><bean:message bundle="configureBundle" key="settings.locales.menuitem"/></tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="LocaleManagement">
    <tiles:put name="enabled" value="${submenu != 'LocalesCSA'}"/>
    <tiles:put name="action"  value="/configure/locale/csa/list.do"/>
    <tiles:put name="text"><bean:message bundle="configureBundle" key="settings.locales.menuitem.csa"/></tiles:put>
    <tiles:put name="title"><bean:message bundle="configureBundle" key="settings.locales.menuitem.csa"/></tiles:put>
</tiles:insert>

<c:if test="${phiz:impliesServiceRigid('ClientProfileConfigureService') || phiz:impliesServiceRigid('ClientProfileConfigureServiceEmployee')}">
    <tiles:insert definition="leftMenuInsetGroup" flush="false">
        <c:set var="enabledProfile" value="${submenu != 'СlientProfileConfig'}"/>
        <c:set var="enabledIdent" value="${submenu != 'IdentConfig'}"/>

        <tiles:put name="name"    value="clientProfileSettings"/>
        <tiles:put name="text"    value="Настройка профиля клиента"/>
        <tiles:put name="enabled" value="${enabledProfile and enabledIdent}"/>

        <tiles:put name="data">
            <tiles:insert definition="leftMenuInset" flush="false">
                <tiles:put name="enabled" value="${submenu != 'СlientProfileConfig'}"/>
                <tiles:put name="action"  value="/clientProfile/configure.do"/>
                <tiles:put name="text"><bean:message bundle="configureBundle" key="settings.clientProfile.menuitem"/></tiles:put>
                <tiles:put name="title"><bean:message bundle="configureBundle" key="settings.clientProfile.menuitem"/></tiles:put>
                <tiles:put name="parentName" value="clientProfileSettings"/>
            </tiles:insert>

            <tiles:insert definition="leftMenuInset" flush="false">
                   <tiles:put name="enabled" value="${submenu != 'IdentConfig'}"/>
                   <tiles:put name="action"  value="/clientProfile/ident/configure.do"/>
                   <tiles:put name="text"><bean:message bundle="configureBundle" key="settings.clientident.menuitem"/></tiles:put>
                   <tiles:put name="title"><bean:message bundle="configureBundle" key="settings.clientident.menuitem"/></tiles:put>
                   <tiles:put name="parentName" value="clientProfileSettings"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</c:if>

<c:if test="${phiz:impliesService('EditInfoMessage')}">
    <tiles:insert definition="leftMenuInset" flush="false">
        <tiles:put name="enabled" value="${submenu != 'InfoConfig'}"/>
        <tiles:put name="action"  value="/clientProfile/basketinfo/configure.do"/>
        <tiles:put name="text"><bean:message bundle="configureBundle" key="settings.basketinfo.menuitem"/></tiles:put>
        <tiles:put name="title"><bean:message bundle="configureBundle" key="settings.basketinfo.menuitem"/></tiles:put>
    </tiles:insert>

    <tiles:insert definition="leftMenuInset" flush="false">
        <tiles:put name="enabled" value="${submenu != 'ClientInfoConfig'}"/>
        <tiles:put name="action"  value="/clientProfile/clientbasketinfo/configure.do"/>
        <tiles:put name="text"><bean:message bundle="configureBundle" key="settings.clientbasketinfo.menuitem"/></tiles:put>
        <tiles:put name="title"><bean:message bundle="configureBundle" key="settings.clientbasketinfo.menuitem"/></tiles:put>
    </tiles:insert>
</c:if>

<c:if test="${phiz:impliesServiceRigid('PaymentsConfigureService') || phiz:impliesServiceRigid('PaymentsConfigureServiceEmployee')}">
    <tiles:insert definition="leftMenuInset">
        <tiles:put name="enabled" value="${submenu != 'PaymentsConfig'}"/>
        <tiles:put name="action"  value="/payments/configure.do"/>
        <tiles:put name="text"><bean:message bundle="configureBundle" key="settings.payments.menuitem"/></tiles:put>
        <tiles:put name="title"><bean:message bundle="configureBundle" key="settings.payments.menuitem"/></tiles:put>
    </tiles:insert>
</c:if>

<tiles:insert definition="leftMenuInset" service="FastRefreshConfigService">
    <tiles:put name="enabled" value="${submenu != 'FastRefreshConfig'}"/>
	<tiles:put name="action"  value="/settings/fastRefreshConfigService.do"/>
	<tiles:put name="text"    value="Применение настроек"/>
	<tiles:put name="title"   value="Применение настроек"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="MoneyboxConfigureService">
    <tiles:put name="enabled" value="${submenu != 'MoneyboxConfig'}"/>
    <tiles:put name="action"  value="/moneybox/configure.do"/>
    <tiles:put name="text"><bean:message bundle="configureBundle" key="settings.moneybox.menuitem"/></tiles:put>
    <tiles:put name="title"><bean:message bundle="configureBundle" key="settings.moneybox.menuitem"/></tiles:put>
</tiles:insert>
