<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
    <c:set var="departmentId">
        <c:choose>
            <c:when test="${not empty param.departmentId}">${param.departmentId}</c:when>
            <c:when test="${not empty param.id}">${param.id}</c:when>
            <c:otherwise>$$newId</c:otherwise>
        </c:choose>
    </c:set>

	<tiles:insert definition="leftMenuInset">
		<tiles:put name="enabled" value="${submenu != 'Edit'}"/>
		<tiles:put name="action"  value="/departments/edit.do?id=${departmentId}"/>
		<tiles:put name="text"    value="Общие сведения"/>
		<tiles:put name="title"   value="Общие сведения о подразделении"/>
	</tiles:insert>

<%--todo сделать операцию для просмотра.--%>
    <tiles:insert definition="leftMenuInset" operation="ViewDocumentsReceptionTimeOperation">
        <tiles:put name="enabled" value="${submenu != 'EditDocumentsReceptionTime'}"/>
        <tiles:put name="action"  value="/documents/receptiontime/edit.do?id=${departmentId}"/>
        <tiles:put name="text"    value="Время приема документов"/>
        <tiles:put name="title"   value="Задание времени приема документов"/>
    </tiles:insert>

    <c:if test="${(not empty departmentId) and (departmentId!='$$newId')}">
        <c:set var="department" value="${phiz:getDepartmentByIdConsiderMultiBlock(departmentId)}"/>

        <c:if test="${phiz:isTB(department)}">
            <c:if test="${phiz:impliesOperation('ListLimitsOperation', 'LimitsManagment') || phiz:impliesOperation('ListLimitsOperation', 'ConfirmLimitsManagment') || phiz:impliesOperation('ListLimitsOperation', 'ViewLimitsManagment')}">

                <%--лимиты основного приложения--%>
                <tiles:insert definition="leftMenuInsetGroup" flush="false">
                    <c:set var="enabledLevelGeneral" value="${submenu != 'GeneralGroupRiskList/general/HIGHT' &&  submenu != 'GeneralGroupRiskList/general/MIDDLE'
                                                    && submenu != 'GeneralGroupRiskList/general/LOW'}"/>
                    <tiles:put name="text"    value="Лимиты (основное приложение)"/>
                    <tiles:put name="name"    value="GeneralLimitGroup"/>
                    <tiles:put name="enabled" value="${submenu != 'GeneralGroupRiskList/general' && submenu != 'GeneralList/general' && enabledLevelGeneral}"/>
                    <tiles:put name="data">
                        <tiles:insert definition="leftMenuSubInsetGroup" flush="false">
                            <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/general' && enabledLevelGeneral}"/>
                            <tiles:put name="text"          value="Лимиты по группам риска"/>
                            <tiles:put name="title"         value="Задание кумулятивных лимитов"/>
                            <tiles:put name="name"          value="GeneralGroupRiskLimitGroup"/>
                            <tiles:put name="parentName"    value="GeneralLimitGroup"/>
                            <tiles:put name="data">
                                <tiles:insert definition="leftMenuInset" flush="false">
                                    <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/general/HIGHT'}"/>
                                    <tiles:put name="action"        value="/limits/group/risk/general/list.do?departmentId=${departmentId}&channel=general&securityType=HIGHT"/>
                                    <tiles:put name="text"          value="Высокий уровень безопасности"/>
                                    <tiles:put name="parentName"    value="GeneralGroupRiskLimitGroup"/>
                                </tiles:insert>
                                <tiles:insert definition="leftMenuInset" flush="false">
                                    <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/general/MIDDLE'}"/>
                                    <tiles:put name="action"        value="/limits/group/risk/general/list.do?departmentId=${departmentId}&channel=general&securityType=MIDDLE"/>
                                    <tiles:put name="text"          value="Средний уровень безопасности"/>
                                    <tiles:put name="parentName"    value="GeneralGroupRiskLimitGroup"/>
                                </tiles:insert>
                                <tiles:insert definition="leftMenuInset" flush="false">
                                    <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/general/LOW'}"/>
                                    <tiles:put name="action"        value="/limits/group/risk/general/list.do?departmentId=${departmentId}&channel=general&securityType=LOW"/>
                                    <tiles:put name="text"          value="Низкий уровень безопасности"/>
                                    <tiles:put name="parentName"    value="GeneralGroupRiskLimitGroup"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="leftMenuInset" flush="false">
                            <tiles:put name="enabled"       value="${submenu != 'GeneralList/general'}"/>
                            <tiles:put name="action"        value="/limits/general/list.do?departmentId=${departmentId}&channel=general"/>
                            <tiles:put name="text"          value="Суточные лимиты"/>
                            <tiles:put name="title"         value="Задание суточных лимитов"/>
                            <tiles:put name="parentName"    value="GeneralLimitGroup"/>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>

                <%--лимиты канала АТМ--%>
                <tiles:insert definition="leftMenuInsetGroup" flush="false">
                    <c:set var="enabledLevelATM" value="${submenu != 'GeneralGroupRiskList/atm/HIGHT' &&  submenu != 'GeneralGroupRiskList/atm/MIDDLE'
                                                    && submenu != 'GeneralGroupRiskList/atm/LOW'}"/>
                    <tiles:put name="text"    value="Лимиты (устройства самообслуживания)"/>
                    <tiles:put name="name"    value="ATMLimitGroup"/>
                    <tiles:put name="enabled" value="${submenu != 'GeneralGroupRiskList/atm' && submenu != 'GeneralList/atm' && enabledLevelATM}"/>
                    <tiles:put name="data">
                        <tiles:insert definition="leftMenuSubInsetGroup" flush="false">
                            <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/atm' && enabledLevelATM}"/>
                            <tiles:put name="text"          value="Лимиты по группам риска"/>
                            <tiles:put name="title"         value="Задание кумулятивных лимитов"/>
                            <tiles:put name="name"          value="ATMGroupRiskLimitGroup"/>
                            <tiles:put name="parentName"    value="ATMLimitGroup"/>
                            <tiles:put name="data">
                                <tiles:insert definition="leftMenuInset" flush="false">
                                    <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/atm/HIGHT'}"/>
                                    <tiles:put name="action"        value="/limits/group/risk/atm/list.do?departmentId=${departmentId}&channel=atm&securityType=HIGHT"/>
                                    <tiles:put name="text"          value="Высокый уровень безопасности"/>
                                    <tiles:put name="parentName"    value="ATMGroupRiskLimitGroup"/>
                                </tiles:insert>
                                <tiles:insert definition="leftMenuInset" flush="false">
                                    <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/atm/MIDDLE'}"/>
                                    <tiles:put name="action"        value="/limits/group/risk/atm/list.do?departmentId=${departmentId}&channel=atm&securityType=MIDDLE"/>
                                    <tiles:put name="text"          value="Средний уровень безопасности"/>
                                    <tiles:put name="parentName"    value="ATMGroupRiskLimitGroup"/>
                                </tiles:insert>
                                <tiles:insert definition="leftMenuInset" flush="false">
                                    <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/atm/LOW'}"/>
                                    <tiles:put name="action"        value="/limits/group/risk/atm/list.do?departmentId=${departmentId}&channel=atm&securityType=LOW"/>
                                    <tiles:put name="text"          value="Низкий уровень безопасности"/>
                                    <tiles:put name="parentName"    value="ATMGroupRiskLimitGroup"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="leftMenuInset" flush="false">
                            <tiles:put name="enabled"       value="${submenu != 'GeneralList/atm'}"/>
                            <tiles:put name="action"        value="/limits/atm/list.do?departmentId=${departmentId}&channel=atm"/>
                            <tiles:put name="text"          value="Суточные лимиты"/>
                            <tiles:put name="title"         value="Задание суточных лимитов"/>
                            <tiles:put name="parentName"    value="ATMLimitGroup"/>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>

                <%--лимиты канала мАПИ--%>
                <tiles:insert definition="leftMenuInsetGroup" flush="false">
                    <c:set var="enabledLevelMobileAPI" value="${submenu != 'GeneralGroupRiskList/mobile/HIGHT' &&  submenu != 'GeneralGroupRiskList/mobile/MIDDLE'
                                                    && submenu != 'GeneralGroupRiskList/mobile/LOW'}"/>
                    <tiles:put name="text"    value="Лимиты (мобильное приложение)"/>
                    <tiles:put name="name"    value="MobileAPILimitGroup"/>
                    <tiles:put name="enabled" value="${submenu != 'GeneralGroupRiskList/mobile' && submenu != 'GeneralList/mobile' && submenu != 'MobileWallet' && enabledLevelMobileAPI}"/>
                    <tiles:put name="data">
                        <tiles:insert definition="leftMenuSubInsetGroup" flush="false">
                            <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/mobile' && enabledLevelMobileAPI}"/>
                            <tiles:put name="text"          value="Лимиты по группам риска"/>
                            <tiles:put name="title"         value="Задание кумулятивных лимитов"/>
                            <tiles:put name="name"          value="MobileGroupRiskLimitGroup"/>
                            <tiles:put name="parentName"    value="MobileAPILimitGroup"/>
                            <tiles:put name="data">
                                <tiles:insert definition="leftMenuInset" flush="false">
                                    <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/mobile/HIGHT'}"/>
                                    <tiles:put name="action"        value="/limits/group/risk/mobile/list.do?departmentId=${departmentId}&channel=mobile&securityType=HIGHT"/>
                                    <tiles:put name="text"          value="Высокый уровень безопасности"/>
                                    <tiles:put name="parentName"    value="MobileGroupRiskLimitGroup"/>
                                </tiles:insert>
                                <tiles:insert definition="leftMenuInset" flush="false">
                                    <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/mobile/MIDDLE'}"/>
                                    <tiles:put name="action"        value="/limits/group/risk/mobile/list.do?departmentId=${departmentId}&channel=mobile&securityType=MIDDLE"/>
                                    <tiles:put name="text"          value="Средний уровень безопасности"/>
                                    <tiles:put name="parentName"    value="MobileGroupRiskLimitGroup"/>
                                </tiles:insert>
                                <tiles:insert definition="leftMenuInset" flush="false">
                                    <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/mobile/LOW'}"/>
                                    <tiles:put name="action"        value="/limits/group/risk/mobile/list.do?departmentId=${departmentId}&channel=mobile&securityType=LOW"/>
                                    <tiles:put name="text"          value="Низкий уровень безопасности"/>
                                    <tiles:put name="parentName"    value="MobileGroupRiskLimitGroup"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="leftMenuInset" flush="false">
                            <tiles:put name="enabled"       value="${submenu != 'GeneralList/mobile'}"/>
                            <tiles:put name="action"        value="/limits/mobile/list.do?departmentId=${departmentId}&channel=mobile"/>
                            <tiles:put name="text"          value="Суточные лимиты"/>
                            <tiles:put name="title"         value="Задание суточных лимитов"/>
                            <tiles:put name="parentName"    value="MobileAPILimitGroup"/>
                        </tiles:insert>
                        <tiles:insert definition="leftMenuInset" service="MobileWalletManagment" operation="EditMobileWalletLimitOperation" flush="false">
                            <tiles:put name="enabled"       value="${submenu != 'MobileWallet'}"/>
                            <tiles:put name="action"        value="/limits/mobileWallet/setAmount.do?departmentId=${departmentId}"/>
                            <tiles:put name="text"          value="Настройка мобильного кошелька"/>
                            <tiles:put name="title"         value="Изменение мобильного кошелька"/>
                            <tiles:put name="parentName"    value="MobileAPILimitGroup"/>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>

                <%--лимиты канала социального приложения--%>
                <tiles:insert definition="leftMenuInsetGroup" flush="false">
                    <c:set var="enabledLevelSocialAPI" value="${submenu != 'GeneralGroupRiskList/social/HIGHT' &&  submenu != 'GeneralGroupRiskList/social/MIDDLE'
                                                    && submenu != 'GeneralGroupRiskList/social/LOW'}"/>
                    <tiles:put name="text"    value="Лимиты (социальное приложение)"/>
                    <tiles:put name="name"    value="SocialAPILimitGroup"/>
                    <tiles:put name="enabled" value="${submenu != 'GeneralGroupRiskList/social' && submenu != 'GeneralList/social' && enabledLevelSocialAPI}"/>
                    <tiles:put name="data">
                        <tiles:insert definition="leftMenuSubInsetGroup" flush="false">
                            <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/social' && enabledLevelSocialAPI}"/>
                            <tiles:put name="text"          value="Лимиты по группам риска"/>
                            <tiles:put name="title"         value="Задание кумулятивных лимитов"/>
                            <tiles:put name="name"          value="SocialGroupRiskLimitGroup"/>
                            <tiles:put name="parentName"    value="SocialAPILimitGroup"/>
                            <tiles:put name="data">
                                <tiles:insert definition="leftMenuInset" flush="false">
                                    <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/social/HIGHT'}"/>
                                    <tiles:put name="action"        value="/limits/group/risk/social/list.do?departmentId=${departmentId}&channel=social&securityType=HIGHT"/>
                                    <tiles:put name="text"          value="Высокый уровень безопасности"/>
                                    <tiles:put name="parentName"    value="SocialGroupRiskLimitGroup"/>
                                </tiles:insert>
                                <tiles:insert definition="leftMenuInset" flush="false">
                                    <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/social/MIDDLE'}"/>
                                    <tiles:put name="action"        value="/limits/group/risk/social/list.do?departmentId=${departmentId}&channel=social&securityType=MIDDLE"/>
                                    <tiles:put name="text"          value="Средний уровень безопасности"/>
                                    <tiles:put name="parentName"    value="SocialGroupRiskLimitGroup"/>
                                </tiles:insert>
                                <tiles:insert definition="leftMenuInset" flush="false">
                                    <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/social/LOW'}"/>
                                    <tiles:put name="action"        value="/limits/group/risk/social/list.do?departmentId=${departmentId}&channel=social&securityType=LOW"/>
                                    <tiles:put name="text"          value="Низкий уровень безопасности"/>
                                    <tiles:put name="parentName"    value="SocialGroupRiskLimitGroup"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="leftMenuInset" flush="false">
                            <tiles:put name="enabled"       value="${submenu != 'GeneralList/social'}"/>
                            <tiles:put name="action"        value="/limits/social/list.do?departmentId=${departmentId}&channel=social"/>
                            <tiles:put name="text"          value="Суточные лимиты"/>
                            <tiles:put name="title"         value="Задание суточных лимитов"/>
                            <tiles:put name="parentName"    value="SocialAPILimitGroup"/>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>

                <%--лимиты канала ЕРМБ--%>
                <tiles:insert definition="leftMenuInsetGroup" flush="false">
                    <c:set var="enabledLevelERMB" value="${submenu != 'GeneralGroupRiskList/ermb/HIGHT' &&  submenu != 'GeneralGroupRiskList/ermb/MIDDLE' && submenu != 'GeneralGroupRiskList/ermb/LOW'}"/>
                    <tiles:put name="text"    value="Лимиты (приложение ЕРМБ)"/>
                    <tiles:put name="name"    value="ERMBLimitGroup"/>
                    <tiles:put name="enabled" value="${submenu != 'GeneralGroupRiskList/ermb' && submenu != 'GeneralList/ermb' && enabledLevelERMB}"/>
                    <tiles:put name="data">
                        <tiles:insert definition="leftMenuSubInsetGroup" flush="false">
                            <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/ermb' && enabledLevelERMB}"/>
                            <tiles:put name="text"          value="Лимиты по группам риска"/>
                            <tiles:put name="title"         value="Задание кумулятивных лимитов"/>
                            <tiles:put name="name"          value="ERMBGroupRiskLimitGroup"/>
                            <tiles:put name="parentName"    value="ERMBLimitGroup"/>
                            <tiles:put name="data">
                                <tiles:insert definition="leftMenuInset" flush="false">
                                    <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/ermb/HIGHT'}"/>
                                    <tiles:put name="action"        value="/limits/group/risk/ermb/list.do?departmentId=${departmentId}&channel=ermb&securityType=HIGHT"/>
                                    <tiles:put name="text"          value="Высокый уровень безопасности"/>
                                    <tiles:put name="parentName"    value="ERMBGroupRiskLimitGroup"/>
                                </tiles:insert>
                                <tiles:insert definition="leftMenuInset" flush="false">
                                    <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/ermb/MIDDLE'}"/>
                                    <tiles:put name="action"        value="/limits/group/risk/ermb/list.do?departmentId=${departmentId}&channel=ermb&securityType=MIDDLE"/>
                                    <tiles:put name="text"          value="Средний уровень безопасности"/>
                                    <tiles:put name="parentName"    value="ERMBGroupRiskLimitGroup"/>
                                </tiles:insert>
                                <tiles:insert definition="leftMenuInset" flush="false">
                                    <tiles:put name="enabled"       value="${submenu != 'GeneralGroupRiskList/ermb/LOW'}"/>
                                    <tiles:put name="action"        value="/limits/group/risk/ermb/list.do?departmentId=${departmentId}&channel=ermb&securityType=LOW"/>
                                    <tiles:put name="text"          value="Низкий уровень безопасности"/>
                                    <tiles:put name="parentName"    value="ERMBGroupRiskLimitGroup"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="leftMenuInset" flush="false">
                            <tiles:put name="enabled"       value="${submenu != 'GeneralList/ermb'}"/>
                            <tiles:put name="action"        value="/limits/ermb/list.do?departmentId=${departmentId}&channel=ermb"/>
                            <tiles:put name="text"          value="Суточные лимиты"/>
                            <tiles:put name="title"         value="Задание суточных лимитов"/>
                            <tiles:put name="parentName"    value="ERMBLimitGroup"/>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <tiles:insert definition="leftMenuInset" operation="EditMonitoringThresholdValuesOperation">
                <tiles:put name="enabled" value="${submenu != 'MonitoringThreshold'}"/>
                <tiles:put name="action"  value="/monitoring/thresholdvalues/edit.do?id=${departmentId}"/>
                <tiles:put name="text"    value="Мониторинг"/>
                <tiles:put name="title"   value="Задание пороговых значений для мониторинга"/>
            </tiles:insert>

            <tiles:insert definition="leftMenuInset" operation="EditTBCommissionsOperation">
                <tiles:put name="enabled" value="${submenu != 'EditTBCommissions'}"/>
                <tiles:put name="action"  value="/departments/commissions/edit.do?id=${departmentId}"/>
                <tiles:put name="text"    value="Настройка комиссий"/>
                <tiles:put name="title"   value="Настройка отображения сумм комиссий в платежах"/>
            </tiles:insert>

            <tiles:insert definition="leftMenuInset" service = "EditLimitPaymentsLinkManagment" operation="EditLimitPaymentsLinkOperation">
                <tiles:put name="enabled" value="${submenu != 'GroupsRisk'}"/>
                <tiles:put name="action"  value="/departments/groupsRiskPersonalTransfer.do?id=${departmentId}"/>
                <tiles:put name="text"    value="Группа риска для операций"/>
                <tiles:put name="title"   value="Задание групп риска для различных операций"/>
            </tiles:insert>

            <tiles:insert definition="leftMenuInset" service = "TemplatesFactorManagement" operation="TemplatesFactorOperation">
                <tiles:put name="enabled" value="${submenu != 'TemplateFactor'}"/>
                <tiles:put name="action"  value="/departments/templatesfactor.do?id=${departmentId}"/>
                <tiles:put name="text"    value="Кратность суммы для шаблонов"/>
                <tiles:put name="title"   value="Задание кратности суммы для шаблонов"/>
            </tiles:insert>

            <tiles:insert definition="leftMenuInset" service = "TemplatesConfirmSettings" operation="TemplatesConfirmSettingsOperation">
                <tiles:put name="enabled" value="${submenu != 'TemplateConfirm'}"/>
                <tiles:put name="action"  value="/departments/templateconfirm.do?id=${departmentId}"/>
                <tiles:put name="text"    value="Подтверждение  операций  по шаблону смс  - паролем"/>
                <tiles:put name="title"   value="Задание настройки подтверждения операций по шаблонам"/>
            </tiles:insert>

            <tiles:insert definition="leftMenuInset" service = "EditUseTemplateFactorInFullMAPI" operation="EditUseTemplateFactorInFullMAPIOperation">
                <tiles:put name="enabled" value="${submenu != 'UseTemplateFactorInFullMAPI'}"/>
                <tiles:put name="action"  value="/departments/useTemplateFactorInFullMAPI.do?id=${departmentId}"/>
                <tiles:put name="text"    value="Проверка кратности суммы в PRO–зоне"/>
                <tiles:put name="title"   value="Проверка кратности суммы в PRO–зоне"/>
            </tiles:insert>
        </c:if>

        <tiles:insert definition="leftMenuInset" operation="ShowIMAccountRateOperation">
            <tiles:put name="enabled" value="${submenu != 'ShowIMAccountRate'}"/>
            <tiles:put name="action"  value="/departments/rates/show.do?id=${departmentId}"/>
            <tiles:put name="text"    value="Курсы покупки/продажи металлов"/>
            <tiles:put name="title"   value="Просмотр курсов покупки/продажи металлов"/>
        </tiles:insert>

        <tiles:insert definition="leftMenuInset" operation="ShowCurrenciesRateOperation">
            <tiles:put name="enabled" value="${submenu != 'ShowCurrenciesRate'}"/>
            <tiles:put name="action"  value="/departments/currencyRates/show.do?id=${departmentId}"/>
            <tiles:put name="text"    value="Курсы покупки/продажи валют"/>
            <tiles:put name="title"   value="Просмотр курсов покупки/продажи валют"/>
        </tiles:insert>
    </c:if>
