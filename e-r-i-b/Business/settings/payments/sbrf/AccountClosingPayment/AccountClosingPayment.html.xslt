<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet
        [
         <!ENTITY nbsp "&#160;">
         <!ENTITY rarr "&#8594;">
        ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mf="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:sh="java://com.rssl.phizic.utils.StringHelper"
                xmlns:dh="java://com.rssl.phizic.business.documents.DocumentHelper"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:mu="java://com.rssl.phizic.business.util.MoneyUtil"
                xmlns:xalan="http://xml.apache.org/xalan"
        >
    <xsl:import href="commonFieldTypes.template.xslt"/>
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
    <!--Тарифный план клиента-->
    <xsl:variable name="tarifPlanCodeType">
        <xsl:value-of select="ph:getActivePersonTarifPlanCode()"/>
    </xsl:variable>
    <!--Показывать ли обычный курс для тарифного плана клиента-->
    <xsl:variable name="needShowStandartRate">
        <xsl:value-of select="ph:needShowStandartRate($tarifPlanCodeType)"/>
    </xsl:variable>
    <xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="postConfirmCommission" select="postConfirmCommission"/>

    <xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

    <xsl:template match="/">
        <script type="text/javascript">
            <!--Отображение или скрытие информации о льготном курсе-->
            function showOrHidePremierWarning(rateShowMsg)
            {
            <xsl:variable name="tarifPlanConfigMessage">
                <xsl:value-of select="ph:getTarifPlanConfigMeessage($tarifPlanCodeType)"/>
            </xsl:variable>
                var SBR_PRIVILEGED_WARNING_MESSAGE = "<xsl:value-of select="$tarifPlanConfigMessage"/>";

                var premierShowMsgElement = document.getElementById('premierShowMsg');
                var removePremierMsg      = false;
            <xsl:choose>
                <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
                <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                    if ( String(rateShowMsg).toLowerCase() == 'true' &amp;&amp;
                         !(SBR_PRIVILEGED_WARNING_MESSAGE == null || SBR_PRIVILEGED_WARNING_MESSAGE == '') )
                    {   <!--сообщение отображается если:-->
                        <!--1. валюта операции "USD" или "EUR"; -->
                        <!--2. курс отличается от курса с обычным тарифным планом "UNKNOWN";-->
                        <!--3. нужно отображать сообщение (настраивается в админке)-->
                        addMessage(SBR_PRIVILEGED_WARNING_MESSAGE);
                    }
                    <!--Скрываем сообщение-->
                    else
                    {
                        removePremierMsg = true;
                    }
                </xsl:when>
                <!--В противном случае -->
                <xsl:otherwise>
                    <!--Для тарифного плана "Сбербанк Премьер" и "Сбербанк Первый" отображаем информационные сообщения-->
                    <xsl:if test="$tarifPlanCodeType = '1' or $tarifPlanCodeType = '3'" >
                       var courseRowPaymentsLegend = ensureElement("courseRow-payments-legend");
                        if (courseRowPaymentsLegend == null)
                            return;
                        if ( String(rateShowMsg).toLowerCase() == 'true' &amp;&amp;
                             !(SBR_PRIVILEGED_WARNING_MESSAGE == null || SBR_PRIVILEGED_WARNING_MESSAGE == '') )
                        {   <!--сообщение отображается если:-->
                            <!--1. валюта операции "USD" или "EUR"; -->
                            <!--2. курс отличается от курса с обычным тарифным планом "UNKNOWN";-->
                            <!--3. тарифный план клиента "PREMIER" или "FIRST"-->
                            addMessage(SBR_PRIVILEGED_WARNING_MESSAGE);
                            courseRowPaymentsLegend.style.display = "";
                        }
                        <!--Скрываем сообщение-->
                        else
                        {
                            removePremierMsg = true;
                            courseRowPaymentsLegend.style.display = "none";
                        }
                    </xsl:if>
                </xsl:otherwise>
            </xsl:choose>

                if (removePremierMsg)
                {
                    removeInformation(SBR_PRIVILEGED_WARNING_MESSAGE, 'warnings');

                    if (premierShowMsgElement)
                    {
                        premierShowMsgElement.value = '';
                    }
                }
            }
            <!--Выгода от льготного курса-->
            function getGainValue(cource1, cource2)
            {
                var diff = Math.abs(parseFloat(cource2) - parseFloat(cource1));
                var fromResourceValue = getElementValue('fromResource');
                if (fromResourceValue != undefined &amp;&amp; accounts != undefined)
                {
                    var balance = accounts[fromResourceValue].balance;
                    var fromResourceCurrency = accounts[fromResourceValue].currencyCode;
                    diff = diff*balance;
                }
                return String(diff.toFixed(2));
            }
        </script>

        <xsl:choose>
            <xsl:when test="$mode = 'edit'">
                <xsl:apply-templates mode="edit"/>
            </xsl:when>
            <xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="confirm"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="resources">
        <xsl:param name="name"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="linkId"/>
        <xsl:param name="accounts"/>
        <xsl:param name="activeCards"/>

        <xsl:variable name="isEmptyTo" select="string-length($activeCards)=0 and string-length($accounts)=0 and $name = 'toResource'"/>
        <xsl:variable name="isEmptyFrom" select="string-length($accounts)=0 and $name = 'fromResource'"/>
        <xsl:variable name="defaultFunctions" select="'hideOrShowCourse();hideOrShowToResourceRow();setAccountTarget();hideOrShowCommissionRow();'"/>

        <select id="{$name}" name="{$name}">
            <xsl:attribute name="onchange">
                <xsl:value-of select="$defaultFunctions"/>
            </xsl:attribute>

            <xsl:if test="$isEmptyFrom or $isEmptyTo">
                <xsl:attribute name="disabled"/>
            </xsl:if>

            <xsl:choose>
                <xsl:when test="$isEmptyFrom">
                    <option value="">Нет доступных вкладов</option>
                </xsl:when>
                <xsl:when test="$isEmptyTo">
                    <option selected='true' value="">Нет доступных счетов и карт</option>
                </xsl:when>
                <xsl:when test="string-length($accountNumber) = 0 and string-length($linkId) = 0">
                    <option value="">Выберите
                        <xsl:choose>
                            <xsl:when test="string-length($activeCards) = 0"> счет</xsl:when>
                            <xsl:when test="string-length($accounts) = 0"> карту</xsl:when>
                            <xsl:otherwise> счет/карту</xsl:otherwise>
                        </xsl:choose>
                        <xsl:choose>
                            <xsl:when test="$name = 'fromResource'"> для закрытия</xsl:when>
                            <xsl:when test="$name = 'toResource'"> зачисления</xsl:when>
                        </xsl:choose>
                    </option>
                </xsl:when>
            </xsl:choose>

            <xsl:for-each select="$accounts">
                <option>
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    <xsl:attribute name="value">
                        <xsl:value-of select="$id"/>
                    </xsl:attribute>
                    <xsl:if test="$accountNumber=./@key or $linkId=$id">
                        <xsl:attribute name="selected"/>
                    </xsl:if>

                    <xsl:value-of select="au:getShortAccountNumber(./@key)"/>&nbsp;

                    <xsl:variable name="accountName" select="./field[@name='name']/text()"/>
                    [
                    <xsl:choose>
                        <xsl:when test="sh:isEmpty($accountName) = 'true'">
                            <xsl:value-of select="./field[@name='type']"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$accountName"/>
                        </xsl:otherwise>
                    </xsl:choose>
                    ]&nbsp;

                    <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                    <xsl:value-of select="mf:getCurrencySign(./field[@name='currencyCode'])"/>
                </option>
            </xsl:for-each>

            <xsl:if test="$name = 'toResource'">
                <xsl:call-template name="resourcesCards">
                    <xsl:with-param name="activeCards" select="$activeCards"/>
                    <xsl:with-param name="name" select="$name"/>
                    <xsl:with-param name="accountNumber" select="$accountNumber"/>
                    <xsl:with-param name="linkId" select="$linkId"/>
                </xsl:call-template>
            </xsl:if>
        </select>
    </xsl:template>

    <xsl:template name="resourcesCards">
        <xsl:param name="activeCards"/>
        <xsl:param name="name"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="linkId"/>

        <xsl:for-each select="$activeCards">
            <xsl:if test="./field[@name='isMain'] = 'true' or $name = 'toResource'">
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                <option>
                    <xsl:if test="./field[@name='isMain'] = 'false' and $name = 'toResource'">
                        <xsl:attribute name="class">hideable</xsl:attribute>
                    </xsl:if>
                    <xsl:attribute name="value">
                        <xsl:value-of select="$id"/>
                    </xsl:attribute>
                    <xsl:if test="$accountNumber= ./@key or $linkId=$id">
                        <xsl:attribute name="selected"/>
                    </xsl:if>
                    <xsl:value-of select="mask:getCutCardNumber(./@key)"/>
                    &nbsp;
                    [
                    <xsl:value-of select="./field[@name='name']"/>
                    ]
                    <xsl:value-of
                            select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
                    &nbsp;
                    <xsl:value-of select="mf:getCurrencySign(./field[@name='currencyCode'])"/>
                </option>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit">

        <xsl:variable name="activeAccounts"   select="document('active-or-arrested-accounts.xml')/entity-list/*"/>
        <xsl:variable name="possibleClosingAccounts" select="document('possible-closing-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards" select="document('active-or-arrested-possible-transfer-cards-at-closing.xml')/entity-list/*"/>

        <input type="hidden" name="fromPersonalFinance" value="{fromPersonalFinance}" id="fromPersonalFinance"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentNumber" value="{documentNumber}"/>
                <b><xsl:value-of  select="documentNumber"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentDate" value="{documentDate}"/>
                <b><xsl:value-of  select="documentDate"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
			<xsl:with-param name="id">fromResource</xsl:with-param>
			<xsl:with-param name="rowName">Закрыть вклад:</xsl:with-param>
			<xsl:with-param name="rowValue">
                    <xsl:call-template name="resources">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="accountNumber" select="fromAccountSelect"/>
                        <xsl:with-param name="linkId" select="fromResource"/>
                        <xsl:with-param name="accounts" select="$possibleClosingAccounts"/>
                    </xsl:call-template>
                <input type="hidden" value="{amount}" name="amount"/>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">toResource</xsl:with-param>
            <xsl:with-param name="lineId">toResourceRow</xsl:with-param>
            <xsl:with-param name="rowName">Остаток средств перевести на:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:call-template name="resources">
                    <xsl:with-param name="name">toResource</xsl:with-param>
                    <xsl:with-param name="accountNumber" select="toAccountSelect"/>
                    <xsl:with-param name="linkId" select="toResource"/>
                    <xsl:with-param name="accounts" select="$activeAccounts"/>
                    <xsl:with-param name="activeCards" select="$activeCards"/>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="id">closingDate</xsl:with-param>
            <xsl:with-param name="rowName">Дата закрытия:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="closingDate" value="{closingDate}"/>
                <b><xsl:value-of select="closingDate"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length($activeAccounts)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="id">accountBalance</xsl:with-param>
                <xsl:with-param name="lineId">accountBalanceRow</xsl:with-param>
                <xsl:with-param name="rowName">Остаток средств:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <span id="accountBalanceLabel"></span>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId" select="'postConfirmCommissionMessage'"/>
                <xsl:with-param name="rowName">Комиссия и перерасчет:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <i><xsl:value-of select="dh:getSettingMessage('commission.prepare.claim.message')"/></i>
                </xsl:with-param>
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowStyle">display: none</xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="id">amountOfEnrollment</xsl:with-param>
                <xsl:with-param name="lineId">amountOfEnrollmentRow</xsl:with-param>
                <xsl:with-param name="rowName">Сумма зачисления:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <span id="amountOfEnrollmentLabel"></span>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:choose>
                <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
                <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                    <!--Поле льготный курс-->
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">courseRow</xsl:with-param>
                        <xsl:with-param name="required">false</xsl:with-param>
                        <xsl:with-param name="rowName">Льготный курс конверсии:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="hidden" value="{course}" name="course" id="courseValue"/>
                            <input type="hidden" value="{premierShowMsg}" name="premierShowMsg" id="premierShowMsg"/>
                            <b><span id="courseLabel"></span></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <!--Поле обычный курс-->
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">standartCourseRow</xsl:with-param>
                        <xsl:with-param name="required">false</xsl:with-param>
                        <xsl:with-param name="rowName">Обычный курс конверсии:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="hidden" value="{standartCourse}" name="standartCourse" id="standartCourseValue"/>
                            <span id="standartCourseSpan" class="crossed"></span>
                        </xsl:with-param>
                    </xsl:call-template>

                    <!--Моя выгода-->
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">courseGainRow</xsl:with-param>
                        <xsl:with-param name="required">false</xsl:with-param>
                        <xsl:with-param name="rowName">Моя выгода:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <span id="courseGainSpan"></span>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:when>
                <!--В противном случае -->
                <xsl:otherwise>
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'false'"/>
                        <xsl:with-param name="lineId">courseRow</xsl:with-param>
                        <xsl:with-param name="rowName">Курс конверсии:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="hidden" value="{course}" name="course" id="courseValue"/>
                            <input type="hidden" value="{premierShowMsg}" name="premierShowMsg" id="premierShowMsg"/>
                            <span id="courseLabel"></span>
                            <xsl:if test="$tarifPlanCodeType = '1'">
                                <div class="payments-legend" id="courseRow-payments-legend" style="display: none">
                                    <div class="italic">Используется льготный курс покупки/продажи</div>
                                </div>
                            </xsl:if>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:if>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="lineId">clientTargetTitleRow</xsl:with-param>
            <xsl:with-param name="rowName"><b>Параметры цели клиента</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="id">clientTargetName</xsl:with-param>
            <xsl:with-param name="lineId">clientTargetNameRow</xsl:with-param>
            <xsl:with-param name="rowName">Название цели:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="clientTargetName"/>
                <input type="hidden" name="clientTargetNameComment"/>
                <div id="clientTargetNameValue"></div>
                <div id="clientTargetNameCommentValue"></div>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="id">clientTargetDate</xsl:with-param>
            <xsl:with-param name="lineId">clientTargetDateRow</xsl:with-param>
            <xsl:with-param name="rowName">Дата достижения:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="clientTargetDate"/>
                <span id="clientTargetDateValue"></span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="id">clientTargetAmount</xsl:with-param>
            <xsl:with-param name="lineId">clientTargetAmountRow</xsl:with-param>
            <xsl:with-param name="rowName">Сумма цели:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="clientTargetAmount"/>
                <span id="clientTargetAmountValue"></span>
            </xsl:with-param>
        </xsl:call-template>

        <script type="text/javascript" src="{$resourceRoot}/scripts/conversionPayment.js"/>
        <script type="text/javascript">
            var rates    = new Array();
            var accounts = {};
            var clientTargets = {};

            function init()
            {
                <xsl:for-each select="document('account-card-currency.xml')/entity-list/entity">
                    rate = new Array()
                    <xsl:for-each select="*">
                        rate['<xsl:value-of select="@name"/>'] = '<xsl:value-of select="./text()"/>';
                    </xsl:for-each>
                    rates['<xsl:value-of select="@key"/>'] = rate;
                </xsl:for-each>
                <xsl:for-each select="$activeAccounts">
                    account = new Object();
                        <xsl:variable name="id" select="field[@name='code']/text()"/>
                        <xsl:variable name="balance" select="field[@name='amountDecimal']/text()"/>

                        account.balance = '<xsl:value-of select="$balance"/>';
                        account.currencyCode = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                        account.accountNumber = '<xsl:value-of select="@key"/>';

                        <xsl:choose>
                             <xsl:when test="$balance=0.00">

                                var amount = ensureElementByName('amount');
                                if(amount.value == null || amount.value == '')
                                {
                                    amount.value = account.balance;
                                }
                             </xsl:when>
                        </xsl:choose>
                        accounts['<xsl:value-of select="$id"/>'] = account;
                </xsl:for-each>

                <xsl:for-each select="$activeCards">
                    card = new Object();
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    card.currencyCode = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                    card.accountNumber = '<xsl:value-of select="field[@name='cardAccount']/text()"/>';
                    accounts['<xsl:value-of select="$id"/>'] = card;
                </xsl:for-each>

                <xsl:variable name="clientTargets" select="document('client-account-targets.xml')/entity-list/*"/>
                <xsl:for-each select="$clientTargets">
                    var clientTarget = new Object();                    
                    clientTarget.name = '<xsl:value-of select="./field[@name='name']"/>';
                    clientTarget.comment = '<xsl:value-of select="./field[@name='comment']"/>';
                    clientTarget.plannedDate = '<xsl:value-of select="./field[@name='plannedDate']"/>';
                    clientTarget.amount = '<xsl:value-of select="./field[@name='amount']"/>';
                    clientTarget.currency = '<xsl:value-of select="./field[@name='currency']"/>';
                    clientTargets['<xsl:value-of select="./field[@name='linkId']"/>'] = clientTarget;
                </xsl:for-each>

                var fromResourceValue = ensureElement('fromResource').value;
                if (fromResourceValue != "")
                {
                    var balance = accounts[fromResourceValue].balance;
                    var amount  = ensureElementByName('amount').value;

                    var savedAmount = '<xsl:value-of select="amount"/>';
                    if (savedAmount)
                    {
                        accounts[fromResourceValue].balance = savedAmount;
                    }
                    else if (balance == 0 &amp;&amp; amount != 0)
                    {
                        accounts[fromResourceValue].balance = amount;
                    }
                }

                hideOrShowToResourceRow();
            };

            function hideOrShowCourse()
            {
                showOrHidePremierWarning(false);
                var course    = ensureElementByName('course');
                var courseRow = ensureElement('courseRow');
                var standartCourseRow = ensureElement('standartCourseRow');
                var courseGainRow = ensureElement('courseGainRow');
                var amountOfEnrollmentRow  = ensureElement('amountOfEnrollmentRow');

                // Если список "Закрыть вклад" пустой, то закрывать нечего и делаем возврат
                <xsl:if test="string-length($possibleClosingAccounts)=0">
                    return;
                </xsl:if>

                var toResourceValue         = getElementValue('toResource');
                var fromResourceValue       = getElementValue('fromResource');
                var notEmptyResources       = ensureElement('fromResource').options.length != 0 &amp;&amp; ensureElement('toResource').options.length != 0;
                var notEmptyResourcesValues = toResourceValue != "" &amp;&amp; fromResourceValue != ""

                if (notEmptyResourcesValues &amp;&amp; notEmptyResources)
                {
                    var balance              = accounts[fromResourceValue].balance;
                    var toResourceCurrency   = accounts[toResourceValue].currencyCode;
                    var fromResourceCurrency = accounts[fromResourceValue].currencyCode;

                    if (toResourceCurrency != fromResourceCurrency &amp;&amp; balance != 0)
                    {
                        var rate;
                        var standartRate;
                        var showAmountOfEnrollment = !(toResourceValue.indexOf('card') == -1);
                        var rateShowMsg;
                        if(fromResourceCurrency == "RUB")
                        {
                            rate = rates[toResourceCurrency + "|" + fromResourceCurrency]["SALE"];
                            if (rate != null &amp;&amp; rate != "")
                            {
                                $("#amountOfEnrollmentLabel").html($().number_format(getBuyAmount(balance, rate), moneyFormat) + '&nbsp;' + currencySignMap.get(toResourceCurrency));
                            }
                            rateShowMsg = rates[toResourceCurrency + "|" + fromResourceCurrency]["SALE_SHOW_MSG"];
                            standartRate = rates[toResourceCurrency + "|" + fromResourceCurrency]["SALE_0"];
                        }
                        else
                        {
                            rate = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY"];
                            if (rate != null &amp;&amp; rate != "")
                            {
                                $("#amountOfEnrollmentLabel").html($().number_format(getBuyAmount(balance, rate), moneyFormat) + '&nbsp;' + currencySignMap.get(toResourceCurrency));
                            }
                            rateShowMsg = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY_SHOW_MSG"];
                            standartRate = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY_0"];
                        }

                        if (rate != null &amp;&amp; rate != "")
                        {
                            course.value = rate;
                            showCourse(fromResourceCurrency,  toResourceCurrency, rate, standartRate, rateShowMsg);
                            hideOrShow(amountOfEnrollmentRow, showAmountOfEnrollment);
                            return;
                        }
                    }
                }
                course.value = "";
                hideOrShow(courseRow, true);
                hideOrShow(standartCourseRow,   true);
                hideOrShow(courseGainRow,   true);
                hideOrShow(amountOfEnrollmentRow, true);
            };

            function hideOrShowCommissionRow()
            {
                var toResourceValue         = getElementValue('toResource');
                var fromResourceValue       = getElementValue('fromResource');
                var postConfirmCommissionMessageDiv = ensureElement('postConfirmCommissionMessage');
                var supportedCommissionPaymentTypes = "<xsl:value-of select="dh:getSupportedCommissionPaymentTypes()"/>".split('|');
                var paymentType;
                if(fromResourceValue.indexOf('account') != -1)
                {
                    if(toResourceValue.indexOf('account') != -1)
                        paymentType = "AcctCloseToAcct";
                    else if(toResourceValue.indexOf('card') != -1)
                        paymentType = "AcctCloseToCard";
                    else
                        paymentType = null;
                }
                else
                    paymentType = null;

                if(paymentType!=null &amp;&amp; (accounts[fromResourceValue].currencyCode == 'RUB' || accounts[fromResourceValue].currencyCode == 'RUR'))
                   paymentType = 'Rur' + paymentType;

                var accountBalance = ensureElement('accountBalanceTextLabel');
                var amountOfEnrollment = ensureElement('amountOfEnrollmentRow');
                var isCalcCommission = supportedCommissionPaymentTypes.contains(paymentType);
                $("#accountBalanceRow .paymentTextLabel").text(isCalcCommission?"Сумма на вкладе:":"Остаток средств:");
                $("#accountBalanceRow .paymentTextLabel").css(isCalcCommission?{'font-weight':'bolder', 'font-size':'16px'}:{'font-weight':'', 'font-size':''});
                $("#accountBalanceRow").css(isCalcCommission?{'font-weight':'bolder','font-size':'16px'}:{'font-weight':'', 'font-size':''});
                hideOrShow(postConfirmCommissionMessageDiv, !(supportedCommissionPaymentTypes.contains(paymentType)));
            }

            function hideOrShowDestination(balance, hide)
            {
                hideOrShow(ensureElement('toResourceRow'), hide);
                ensureElementByName("amount").value = balance;
                disabledEnsureElement("toResource", hide);
                disabledEnsureElement("courseRow", hide);
                disabledEnsureElement("standartCourseRow", hide);
                disabledEnsureElement("courseGainRow", hide);
                disabledEnsureElement("amountOfEnrollmentRow", hide);
            };

            function disabledEnsureElement(ensureElementId, hide)
            {
                var element = ensureElement(ensureElementId);
                if (element != null)
                    element.disabled  = hide;
            }

            function hideOrShowAccountBalance(balance, currencyCode, hide)
            {
                hideOrShow(ensureElement('accountBalanceRow'), hide);
                $("#accountBalanceLabel").html( $().number_format(balance, moneyFormat) + "&nbsp;" + currencySignMap.get(currencyCode) );
            }

            function hideOrShowToResourceRow()
            {
                var fromResourceValue = ensureElement('fromResource').value;
                if (fromResourceValue != "")
                {
                    var balance  = accounts[fromResourceValue].balance;
                    var fromCurrency = accounts[fromResourceValue].currencyCode;
                    hideOrShowDestination(balance, balance == 0);
                    hideOrShowAccountBalance(balance, fromCurrency, balance == 0)
                }
                else
                    hideOrShow(ensureElement('accountBalanceRow'), true);
            };

            function showCourse(fromCurrency, toCurrency, rate, standartRate, rateShowMsg)
       		{
               hideOrShow(ensureElement("courseRow"), false);
               hideOrShow(ensureElement("standartCourseRow"), false);
               hideOrShow(ensureElement("courseGainRow"), false);
               hideOrShow(ensureElement("amountOfEnrollmentRow"), false);
               $("#courseLabel").html(rate + " " + currencySignMap.get(fromCurrency) + "<i>&#8594;</i>" + currencySignMap.get(toCurrency));
               $("#standartCourseSpan").html(standartRate + " " + currencySignMap.get(fromCurrency) + "&#8594;" + currencySignMap.get(toCurrency));

               if (isNotEmpty(standartRate))
               {
                   var curRate = rate;
                   var curStandartRate = standartRate;
                   if (fromCurrency == "RUB")
                   {
                        curRate = 1 / rate;
                        curStandartRate = 1 / standartRate;
                   }
                   if (parseFloat(curRate) > (curStandartRate)) {
                       $("#courseGainRow").hide();
                   }
                   else {
                       $("#courseGainRow").show()
                       $("#courseGainSpan").html(getGainValue(curStandartRate, curRate) + " " + currencySignMap.get(toCurrency));
                    }
               }
               ensureElement("courseValue").value = rate;
               ensureElement("premierShowMsg").value = rateShowMsg;
               var standartCourseElement = ensureElement("standartCourseValue");
               if (standartCourseElement != null &amp;&amp; standartCourseElement != undefined)
                   standartCourseElement.value = standartRate;

               showOrHidePremierWarning(rateShowMsg);
       		};

            function setAccountTarget()
            {
                var fromResourceValue = ensureElement('fromResource').value;
                var clientTarget = clientTargets[fromResourceValue];
                if(clientTarget)
                {
                    $('input[name="clientTargetName"]').val(clientTarget.name);
                    $('input[name="clientTargetNameComment"]').val(clientTarget.comment);
                    $('input[name="clientTargetDate"]').val(clientTarget.plannedDate);
                    $('input[name="clientTargetAmount"]').val($().number_format(clientTarget.amount, moneyFormat) + "&nbsp;" + currencySignMap.get(clientTarget.currency));

                    $('#clientTargetNameValue').text(clientTarget.name);
                    $('#clientTargetNameCommentValue').text(clientTarget.comment);
                    $('#clientTargetDateValue').text(clientTarget.plannedDate);
                    $('#clientTargetAmountValue').text($().number_format(clientTarget.amount, moneyFormat) + "&nbsp;" + currencySignMap.get(clientTarget.currency));

                    $('#clientTargetTitleRow').show();
                    $('#clientTargetNameRow').show();
                    $('#clientTargetDateRow').show();
                    $('#clientTargetAmountRow').show();
                }
                else
                {
                    $('#clientTargetTitleRow').hide();
                    $('#clientTargetNameRow').hide();
                    $('#clientTargetDateRow').hide();
                    $('#clientTargetAmountRow').hide();

                    $('input[name="clientTargetName"]').val('');
                    $('input[name="clientTargetNameComment"]').val('');
                    $('input[name="clientTargetDate"]').val('');
                    $('input[name="clientTargetAmount"]').val('');
                }
            }

            init();
            hideOrShowCourse();
            setAccountTarget();
            hideOrShowCommissionRow();

            <!--Валюта источника списания средств-->
            function getFromResourceCurrencyCode()
            {
                var fromResource = getElementValue("fromResource");
                if (fromResource != undefined &amp;&amp; accounts != undefined)
                    return accounts[fromResource].currencyCode;
                return "";
            }
        </script>
    </xsl:template>

    <!-- Подтверждение -->
    <xsl:template match="/form-data" mode="confirm">
        <script type="text/javascript">
            doOnLoad(function()
            {
                showOrHidePremierWarning(<xsl:value-of select="premierShowMsg"/>);
            });
        </script>
        
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentNumber" value="{documentNumber}"/>
                <b><xsl:value-of  select="documentNumber"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentDate" value="{documentDate}"/>
                <b><xsl:value-of  select="documentDate"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Закрыть вклад:</xsl:with-param>
            <xsl:with-param name="lineId">fromResource</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                <xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/>
                &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                &nbsp;<xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                </b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="not(toAccountSelect='')">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="lineId">toResource</xsl:with-param>
                <xsl:with-param name="rowName">Остаток средств перевести на:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b>
                    <xsl:choose>
                        <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                            <xsl:value-of select="mask:getCutCardNumber(toAccountSelect)"/>
                        </xsl:when>
                        <xsl:otherwise><xsl:value-of select="au:getFormattedAccountNumber(toAccountSelect)"/></xsl:otherwise>
                    </xsl:choose>
                    &nbsp;[<xsl:value-of select="toAccountName"/>]&nbsp;
                    <xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/>
                    </b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата закрытия:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="closingDate" value="{closingDate}"/>
                <b><xsl:value-of select="closingDate"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="not($postConfirmCommission)">
            <xsl:if test="not(amount='' or amount=0.00)">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="required" select="'false'"/>
                    <xsl:with-param name="lineId">chargeOffAmountRow</xsl:with-param>
                    <xsl:with-param name="rowName">Остаток средств:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b>
                            <input type="hidden" name="chargeOffAmount" value="{amount}"/>
                            <xsl:value-of select="format-number(amount, '### ##0,00', 'sbrf')"/>&nbsp;
                            <xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                        </b>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>

            <xsl:if test="not(toAccountSelect='')">
                <xsl:if test="not(course='' or course=0.00)">

                    <xsl:if test="courseChanged = 'true' and state = 'SAVED'">
                        <script type="text/javascript">
                            doOnLoad(function()
                            {
                                removeError("Курс валюты банка изменился! Пожалуйста, отредактируйте платеж. ");
                                addError("Изменился курс конверсии.", null, true);
                                payInput.fieldError('confirmCourse');
                            });
                        </script>
                    </xsl:if>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'false'"/>
                        <xsl:with-param name="lineId">destinationAmountRow</xsl:with-param>
                        <xsl:with-param name="rowName">Сумма зачисления:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b>
                                <input type="hidden" name="destinationAmount" value="{destinationAmount}"/>
                                <xsl:value-of select="format-number(destinationAmount, '### ##0,00', 'sbrf')"/>&nbsp;
                                <xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/>
                            </b>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:if>
        </xsl:if>

        <xsl:if test="not(toAccountSelect='')">
            <xsl:if test="not(course='' or course=0.00)">
                <xsl:choose>
                    <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
                    <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true') and fromResourceCurrency != toResourceCurrency">
                        <!--Поле льготный курс-->
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Льготный курс конверсии:</xsl:with-param>
                            <xsl:with-param name="id">confirmCourse</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <input type="hidden" name="confirmCourse"/>
                                <b>
                                    <xsl:value-of select="format-number(course, '### ##0,00##', 'sbrf')"/>&nbsp;
                                    <span id="course">
                                        <xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                                        <span class="normal">&rarr;</span>
                                        <xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/>
                                    </span>
                                </b>
                            </xsl:with-param>
                        </xsl:call-template>

                        <!--Поле обычный курс-->
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Обычный курс конверсии:</xsl:with-param>
                            <xsl:with-param name="id">confirmStandartCourse</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <span class="crossed">
                                    <xsl:value-of select="format-number(standartCourse, '### ##0,00##', 'sbrf')"/>&nbsp;
                                    <span id="standartCourseSpan">
                                        <xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                                        <span class="normal">&rarr;</span>
                                        <xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/>
                                    </span>
                                </span>
                            </xsl:with-param>
                        </xsl:call-template>

                        <!--Моя выгода-->
                        <xsl:if test="standartCourse > course">
                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="id">courseGain</xsl:with-param>
                                <xsl:with-param name="rowName">Моя выгода:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <xsl:choose>
                                        <xsl:when test="fromResourceCurrency = 'RUB'">
                                            <xsl:call-template name="abs">
                                                <xsl:with-param name="input" select="mu:roundDestinationAmount((1 div course - 1 div standartCourse) * amount)" />
                                            </xsl:call-template>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:call-template name="abs">
                                                <xsl:with-param name="input" select="mu:roundDestinationAmount((course - standartCourse) * amount)" />
                                            </xsl:call-template>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                    &nbsp;
                                    <xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/>
                                </xsl:with-param>
                            </xsl:call-template>
                        </xsl:if>
                    </xsl:when>
                    <!--В противном случае -->
                    <xsl:otherwise>
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="required" select="'false'"/>
                            <xsl:with-param name="lineId">courseRow</xsl:with-param>
                            <xsl:with-param name="id">confirmCourse</xsl:with-param>
                            <xsl:with-param name="rowName">Курс конверсии:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <input type="hidden" name="confirmCourse"/>
                                <b>
                                    <xsl:value-of select="format-number(course, '### ##0,00##', 'sbrf')"/>&nbsp;
                                    <span id="course">
                                        <xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                                        <span class="normal">&rarr;</span>
                                        <xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/>
                                    </span>
                                </b>
                                <xsl:if test="$tarifPlanCodeType = '1'">
                                    <div class="payments-legend" id="courseRow-payments-legend" style="display: none">
                                        <div class="italic">Используется льготный курс покупки/продажи</div>
                                    </div>
                                </xsl:if>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
        </xsl:if>

        <xsl:if test="clientTargetName!=''">
            <xsl:call-template name="titleRow">
                <xsl:with-param name="lineId">clientTargetTitleRow</xsl:with-param>
                <xsl:with-param name="rowName"><b>Параметры цели клиента</b></xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="clientTargetName!=''">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">clientTargetNameRow</xsl:with-param>
                <xsl:with-param name="rowName">Название цели:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div>
                        <xsl:value-of select="clientTargetName"/>
                    </div>
                    <div>
                        <xsl:value-of select="clientTargetNameComment"/>
                    </div>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="clientTargetDate!=''">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">clientTargetDateRow</xsl:with-param>
                <xsl:with-param name="rowName">Дата достижения:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:value-of select="clientTargetDate"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="clientTargetAmount!=''">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">clientTargetAmountRow</xsl:with-param>
                <xsl:with-param name="rowName">Сумма цели:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:value-of select="clientTargetAmount"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>


    <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Статус документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
               <div id="state">
                    <span onmouseover="showLayer('state','stateDescription');"
                       onmouseout="hideLayer('stateDescription');" class="link">
                        <xsl:choose>
                            <xsl:when test="$app = 'PhizIA'">
                                <xsl:call-template name="employeeState2text">
                                    <xsl:with-param name="code">
                                        <xsl:value-of select="state"/>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:call-template name="clientState2text">
                                    <xsl:with-param name="code">
                                        <xsl:value-of select="state"/>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                    </span>
                </div>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="$postConfirmCommission">
            <xsl:call-template name="accountsSumRows">
                <xsl:with-param name="fromResourceCurrency" select="fromResourceCurrency"/>
                <xsl:with-param name="toResourceCurrency" select="toResourceCurrency"/>
                <xsl:with-param name="useIncludeWord">false</xsl:with-param>
                <xsl:with-param name="dstCurAmt" select="destinationAmount"/>
                <xsl:with-param name="documentState" select="state"/>
            </xsl:call-template>
        </xsl:if>

    </xsl:template>

	<xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
		</xsl:choose>
	</xsl:template>

    <xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='INITIAL'">Черновик</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
			<xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!--добавление option для select'a-->
	<xsl:template name="addOption">
	    <xsl:param name="value"/>
	    <xsl:param name="selected"/>
	    <xsl:param name="source"/>

        <option>
            <xsl:attribute name="value">
                <xsl:value-of select="$value"/>
            </xsl:attribute>
            <xsl:if test="contains($value, $selected)">
                <xsl:attribute name="selected"/>
            </xsl:if>
            <xsl:call-template name="monthsToString">
                <xsl:with-param name="value"  select="$value"/>
                <xsl:with-param name="source" select="$source"/>
            </xsl:call-template>
        </option>
	</xsl:template>

	<!--получение списка месяцев в строку-->
	<xsl:template name="monthsToString">
	    <xsl:param name="value"/>
	    <xsl:param name="source"/>

        <xsl:variable name="delimiter" select="'|'"/>

        <xsl:choose>
            <xsl:when test="contains($value, $delimiter)">
                <xsl:for-each select="xalan:tokenize($value, $delimiter)">
                    <xsl:value-of select="concat($source[@key = current()]/text(), ' ')"/>
                </xsl:for-each>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="concat($source[@key = $value]/text(), ' ')"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- шаблон формирующий div описания -->
<xsl:template name="buildDescription">
   <xsl:param name="text"/>

   <xsl:variable name="delimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">Подробнее.</a>
   <div class="detail" style="display: none">
   ]]>
   </xsl:variable>

   <xsl:variable name="firstDelimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">Как заполнить это поле?</a>
   <div class="detail" style="display: none">
   ]]>
   </xsl:variable>

   <xsl:variable name="end">
   <![CDATA[ </div>
   ]]>
   </xsl:variable>

   <div class="description" style="display: none">

    <xsl:variable name="nodeText" select="xalan:nodeset($text)"/>

   <xsl:for-each select="$nodeText/node()">

   <xsl:choose>
		<xsl:when test="name() = 'cut' and position() = 1 ">
		    <xsl:value-of select="$firstDelimiter" disable-output-escaping="yes"/>
		</xsl:when>
        <xsl:when test="name() = 'cut' and position() != 1">
            <xsl:value-of select="$delimiter" disable-output-escaping="yes"/>
        </xsl:when>
   		<xsl:otherwise>
		<xsl:copy />
		</xsl:otherwise>
   </xsl:choose>
   </xsl:for-each>

   <xsl:if test="count($nodeText/cut) > 0">
   <xsl:value-of select="$end" disable-output-escaping="yes"/>
   </xsl:if>
	</div>

</xsl:template>

<xsl:template name="standartRow">

	<xsl:param name="id"/>
    <xsl:param name="lineId"/>                      <!--идентификатор строки-->
	<xsl:param name="required" select="'true'"/>    <!--параметр обязатьльности заполнения-->
	<xsl:param name="rowName"/>                     <!--описание поля-->
	<xsl:param name="rowValue"/>                    <!--данные-->
	<xsl:param name="description"/>                	<!-- Описание поля, данные после <cut />  будут прятаться под ссылку подробнее -->
	<xsl:param name="rowStyle"/>                    <!-- Стиль поля -->
    <xsl:param name="isAllocate" select="'true'"/>  <!-- Выделить при нажатии -->
    <!-- Необязательный параметр -->
	<xsl:param name="fieldName"/>                   <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->

	<xsl:variable name="nodeRowValue" select="xalan:nodeset($rowValue)"/>
	<!-- Определение имени инпута или селекта передаваемого в шаблон -->
	<!-- inputName - fieldName или имя поле вытащеное из rowValue -->
	<xsl:variable name="inputName">
	<xsl:choose>
		<xsl:when test="string-length($fieldName) = 0">
				<xsl:if test="(count($nodeRowValue/input[@name]) + count($nodeRowValue/select[@name]) + count($nodeRowValue/textarea[@name])) = 1">
					<xsl:value-of select="$nodeRowValue/input/@name" />
					<xsl:if test="count($nodeRowValue/select[@name]) = 1">
						<xsl:value-of select="$nodeRowValue/select/@name" />
					</xsl:if>
                    <xsl:if test="count($nodeRowValue/textarea[@name]) = 1">
						<xsl:value-of select="$nodeRowValue/textarea/@name" />
					</xsl:if>
				</xsl:if>
		</xsl:when>
		<xsl:otherwise>
				<xsl:copy-of select="$fieldName"/>
		</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>

    <xsl:variable name="readonly">
		<xsl:choose>
			<xsl:when test="string-length($inputName)>0">
				<xsl:value-of select="count($nodeRowValue/input[@name=$inputName]/@readonly ) + count($nodeRowValue/select[@name=$inputName]/@readonly )+ count($nodeRowValue/textarea[@name=$inputName]/@readonly )"/>
			</xsl:when>
			<xsl:otherwise>
				0
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

    <xsl:variable name="styleClass">
        <xsl:choose>
            <xsl:when test="$isAllocate = 'true'">
                <xsl:value-of select="'form-row'"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="'form-row-addition'"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <div class="{$styleClass}">
        <xsl:if test="string-length($lineId) > 0">
            <xsl:attribute name="id">
                <xsl:copy-of select="$lineId"/>
            </xsl:attribute>
        </xsl:if>
        <xsl:if test="string-length($rowStyle) > 0">
            <xsl:attribute name="style">
                <xsl:copy-of select="$rowStyle"/>
            </xsl:attribute>
        </xsl:if>
        <xsl:if test="$readonly = 0 and $mode = 'edit' and $isAllocate='true'">
            <xsl:attribute name="onclick">payInput.onClick(this)</xsl:attribute>
        </xsl:if>

        <div class="paymentLabel">
	    <span class="paymentTextLabel">
	        <xsl:if test="string-length($id) > 0">
                <xsl:attribute name="id">
                    <xsl:copy-of select="$id"/>
                    TextLabel
                </xsl:attribute>
            </xsl:if>
	        <xsl:copy-of select="$rowName"/>
	    </span>
            <xsl:if test="$required = 'true' and $mode = 'edit'">
                <span id="asterisk_{$id}" class="asterisk">*</span>
            </xsl:if>
        </div>

        <div class="paymentValue">
            <div class="paymentInputDiv">
                <xsl:copy-of select="$rowValue"/>
            </div>

            <xsl:if test="$readonly = 0 and $mode = 'edit'">
                <xsl:call-template name="buildDescription">
                    <xsl:with-param name="text" select="$description"/>
                </xsl:call-template>
            </xsl:if>
            <div class="errorDiv" style="display: none;">
            </div>
        </div>
        <div class="clear"></div>
    </div>

<!-- Устанавливаем события onfocus поля -->
	<xsl:if test="string-length($inputName) > 0 and $readonly = 0 and $mode = 'edit'">
		<script type="text/javascript">
		if (document.getElementsByName('<xsl:value-of select="$inputName"/>').length == 1)
		document.getElementsByName('<xsl:value-of select="$inputName"/>')[0].onfocus = function() { payInput.onFocus(this); };
		</script>
	</xsl:if>
</xsl:template>

    <xsl:template name="titleRow">
        <xsl:param name="lineId"/>
        <xsl:param name="rowName"/>
        <div id="{$lineId}" class="{$styleClassTitle}">
            <xsl:copy-of select="$rowName"/>
        </div>
    </xsl:template>

</xsl:stylesheet>
