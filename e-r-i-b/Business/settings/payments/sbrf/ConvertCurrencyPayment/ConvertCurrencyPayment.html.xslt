<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mf="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:dh="java://com.rssl.phizic.business.documents.DocumentHelper"
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
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="isTemplate" select="'isTemplate'"/>
    <xsl:param name="longOffer" select="false()"/>
	<xsl:param name="personAvailable" select="true()"/>
    <xsl:param name="postConfirmCommission" select="postConfirmCommission"/>
    <!--Тарифный план клиента-->
    <xsl:variable name="tarifPlanCodeType">
        <xsl:value-of select="ph:getActivePersonTarifPlanCode()"/>
    </xsl:variable>
    <!--Показывать ли стандартный курс для тарифного плана клиента-->
    <xsl:variable name="needShowStandartRate">
        <xsl:value-of select="ph:needShowStandartRate($tarifPlanCodeType)"/>
    </xsl:variable>

    <xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

    <xsl:template match="/">

        <script type="text/javascript">

            function isEmpty(value)
            {
                if (value == null || value == "")
                    return true;
                return false;
            }

            <!--Отображение или скрытие информации о льготном курсе-->
            function showOrHidePremierWarning(rateShowMsg)
            {
            <xsl:variable name="tarifPlanConfigMessage">
                <xsl:value-of select="ph:getTarifPlanConfigMeessage($tarifPlanCodeType)"/>
            </xsl:variable>
                var SBR_PRIVELEGED_WARNING_MESSAGE = "<xsl:value-of select="$tarifPlanConfigMessage"/>";
            <xsl:choose>
                <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
                <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                    if ( String(rateShowMsg).toLowerCase() == 'true' &amp;&amp;
                         !(SBR_PRIVELEGED_WARNING_MESSAGE == null || SBR_PRIVELEGED_WARNING_MESSAGE == '') )
                    {   <!--сообщение отображается если:-->
                        <!--1. валюта операции "USD" или "EUR"; -->
                        <!--2. курс отличается от курса с обычным тарифным планом "UNKNOWN";-->
                        <!--3. нужно отображать сообщение (настраивается в админке)-->
                        addMessage(SBR_PRIVELEGED_WARNING_MESSAGE);
                    }
                    <!--Скрываем сообщение-->
                    else
                    {
                        removeInformation(SBR_PRIVELEGED_WARNING_MESSAGE,'warnings');
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
                             !(SBR_PRIVELEGED_WARNING_MESSAGE == null || SBR_PRIVELEGED_WARNING_MESSAGE == '') )
                        {   <!--сообщение отображается если:-->
                            <!--1. валюта операции "USD" или "EUR"; -->
                            <!--2. курс отличается от курса с обычным тарифным планом "UNKNOWN";-->
                            <!--3. тарифный план клиента "PREMIER" или "FIRST"-->
                            addMessage(SBR_PRIVELEGED_WARNING_MESSAGE);
                            courseRowPaymentsLegend.style.display = "";
                        }
                        <!--Скрываем сообщение-->
                        else
                        {
                            removeInformation(SBR_PRIVELEGED_WARNING_MESSAGE,'warnings');
                            courseRowPaymentsLegend.style.display = "none";
                        }
                    </xsl:if>
                </xsl:otherwise>
            </xsl:choose>
            }

        </script>

		<xsl:choose>
			<xsl:when test="$mode = 'edit' and not($longOffer)">
				<xsl:apply-templates mode="edit-simple-payment"/>
			</xsl:when>
			<xsl:when test="$mode = 'edit' and $longOffer">
				<xsl:apply-templates mode="edit-long-offer"/>
			</xsl:when>
			<xsl:when test="$mode = 'view' and not($longOffer)">
				<xsl:apply-templates mode="view-simple-payment"/>
			</xsl:when>
			<xsl:when test="$mode = 'view' and $longOffer">
				<xsl:apply-templates mode="view-long-offer"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template name="resources">
        <xsl:param name="name"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="linkId"/>
        <xsl:param name="activeAccounts"/>
        <xsl:param name="activeCards"/>

        <xsl:variable name="defaultFunctions" select="'showHideOperationTypeRow();fillCurrency();hideOrShowCourse();'"/>

        <select id="{$name}" name="{$name}">
            <xsl:attribute name="onchange">
                <xsl:choose>
                    <xsl:when test="$name = 'fromResource'">
                        <xsl:value-of select="concat($defaultFunctions, 'showHideAdditionalCards();')"/>
                    </xsl:when>
                    <xsl:when test="$name = 'toResource'">
                        <xsl:value-of select="$defaultFunctions"/>
                    </xsl:when>
                </xsl:choose>
            </xsl:attribute>
            <xsl:choose>
                <xsl:when test="not($activeCards)and not($activeAccounts)">
                    <option value="">Нет доступных счетов и карт</option>
                    <script type="text/javascript">$(document).ready(function(){hideOrShowMakeLongOfferButton(true)});</script>
                </xsl:when>
                <xsl:when test="string-length($accountNumber) = 0 and string-length($linkId) = 0">
                    <xsl:choose>
                        <xsl:when test="$name = 'fromResource'">
                            <xsl:choose>
                                <xsl:when test="not($activeCards) and count($activeAccounts)>0"><option value="">Выберите счет списания </option></xsl:when>
                                <xsl:when test="not($activeAccounts) and count($activeCards)>0"><option value="">Выберите карту списания </option></xsl:when>
                                <xsl:when test="count($activeAccounts)+count($activeCards) >0"><option value="">Выберите счет/карту списания </option></xsl:when>
                            </xsl:choose>
                        </xsl:when>
                        <xsl:otherwise>
                            <option value="">Выберите
                                <xsl:choose>
                                    <xsl:when test="not($activeCards)">счет</xsl:when>
                                    <xsl:when test="not($activeAccounts)">карту</xsl:when>
                                    <xsl:otherwise>счет/карту</xsl:otherwise>
                                </xsl:choose>
                                    зачисления
                            </option>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
            </xsl:choose>
            <xsl:for-each select="$activeAccounts">
                <option>
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    <xsl:attribute name="value">
                        <xsl:value-of select="$id"/>
                    </xsl:attribute>
                    <xsl:if test="$accountNumber=./@key or $linkId=$id">
                        <xsl:attribute name="selected"/>
                    </xsl:if>
                    <xsl:value-of select="au:getShortAccountNumber(./@key)"/>&nbsp;
                    [<xsl:value-of select="./field[@name='name']"/>]
                    <xsl:if test="./field[@name='amountDecimal'] != ''">
                        <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                    </xsl:if>
                    <xsl:value-of select="mf:getCurrencySign(./field[@name='currencyCode'])"/>
                </option>
            </xsl:for-each>

            <xsl:call-template name="resourcesCards">
                <xsl:with-param name="activeCards" select="$activeCards"/>
                <xsl:with-param name="name" select="$name"/>
                <xsl:with-param name="accountNumber" select="$accountNumber"/>
                <xsl:with-param name="linkId" select="$linkId"/>
            </xsl:call-template>
        </select>
    </xsl:template>

    <xsl:template name="resourcesCards">
        <xsl:param name="activeCards"/>
        <xsl:param name="name"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="linkId"/>

        <xsl:for-each select="$activeCards">
            <!-- Разрешен перевод с дополнительных карт типа OTHERTOCLIENT и с основных -->
            <xsl:variable name="isFromResourceAllowed" select="./field[@name='isMain'] = 'true' or (./field[@name='isMain'] = 'false' and ./field[@name='additionalCardType'] = 'OTHERTOCLIENT' and $name = 'fromResource')"/>
            <!-- Разрешен перевод на основные -->
            <xsl:variable name="isToResourceAllowed"   select="./field[@name='isMain'] = 'true' and $name = 'toResource'"/>

            <xsl:if test="$isFromResourceAllowed or $isToResourceAllowed">
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
                    <xsl:if test="./field[@name='amountDecimal'] != ''">
                        <xsl:value-of
                            select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
                    </xsl:if>
                    &nbsp;
                    <xsl:value-of select="mf:getCurrencySign(./field[@name='currencyCode'])"/>
                </option>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit-simple-payment">
        <xsl:variable name="activeDebitAccounts"  select="document('stored-active-debit-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCreditAccounts" select="document('stored-active-or-arrested-credit-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards"          select="document('stored-active-or-arrested-not-virtual-cards.xml')/entity-list/*"/>
        <xsl:variable name="activeNotArrestedCards"          select="document('stored-active-not-virtual-cards.xml')/entity-list/*"/>
        <xsl:variable name="rates"                select="document('stored-account-card-currency.xml')/entity-list/entity"/>

        <input type="hidden" name="exactAmount" value="{exactAmount}" id="exactAmount"/>
        <xsl:if test="$isTemplate != 'true'">
            &nbsp;
            <span class="simpleLink" onclick="javascript:openTemplateList('CONVERT_CURRENCY_TRANSFER');">
                 <span class="text-green">выбрать из шаблонов платежей</span>
            </span>
        </xsl:if>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentNumber" value="{documentNumber}"/>
                <b><xsl:value-of  select="documentNumber"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentDate" value="{documentDate}"/>
                <b><xsl:value-of  select="documentDate"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
			<xsl:with-param name="id">fromResource</xsl:with-param>
			<xsl:with-param name="rowName">Счет списания:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:if test="$personAvailable">
                    <xsl:call-template name="resources">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="accountNumber" select="fromAccountSelect"/>
                        <xsl:with-param name="linkId" select="fromResource"/>
                        <xsl:with-param name="activeAccounts" select="$activeDebitAccounts"/>
                        <xsl:with-param name="activeCards" select="$activeNotArrestedCards"/>
                    </xsl:call-template>
				</xsl:if>
				<xsl:if test="not($personAvailable)">
					<select id="fromAccountSelect" name="fromAccountSelect" disabled="disabled">
					    <option selected="selected">Счет клиента</option>
				    </select>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
			<xsl:with-param name="lineId">toResourceRow</xsl:with-param>
			<xsl:with-param name="id">toResource</xsl:with-param>
			<xsl:with-param name="rowName">Счет зачисления:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:if test="$personAvailable">
                    <xsl:call-template name="resources">
                        <xsl:with-param name="name">toResource</xsl:with-param>
                        <xsl:with-param name="accountNumber" select="toAccountSelect"/>
                        <xsl:with-param name="linkId" select="toResource"/>
                        <xsl:with-param name="activeAccounts" select="$activeCreditAccounts"/>
                        <xsl:with-param name="activeCards" select="$activeCards"/>

                    </xsl:call-template>
				</xsl:if>
				<xsl:if test="not($personAvailable)">
					<select id="toResource" name="toResource" disabled="disabled"><option selected="selected">Счет клиента</option></select>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
			<xsl:with-param name="id">sellAmount</xsl:with-param>
			<xsl:with-param name="description">Введите сумму, которую необходимо перевести</xsl:with-param>
			<xsl:with-param name="rowName">Сумма:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <input id="sellAmount" name="sellAmount" type="text" value="{sellAmount}" size="14"
                       onchange="javascript:sellCurrency();" onkeyup="javascript:sellCurrency();" class="moneyField"/>&nbsp;
                <span id="sellAmountCurrency"></span>
                <span id="buyAmountRow">
                    <span id="wordOr">
                        <b>&nbsp;&nbsp;или&nbsp;&nbsp;</b>
                    </span>
                    <input id="buyAmount" name="buyAmount" type="text" value="{buyAmount}" size="14"
                           onchange="javascript:buyCurrency();" onkeyup="javascript:buyCurrency();" class="moneyField"/>&nbsp;
                    <span id="buyAmountCurrency"></span>
                </span>
            </xsl:with-param>
            <!--Параметр для передачи в шаблон name поля, необходимо для определения, была ли в поле ошибка валидации-->
            <!--Возможно name можно как нить вытащить и из rowValue-->
            <!--<xsl:with-param name="fieldName">amount</xsl:with-param>-->
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId" select="'postConfirmCommissionMessage'"/>
            <xsl:with-param name="rowName">Комиссия:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <i><xsl:value-of select="dh:getSettingMessage('commission.prepare.transfer.message')"/></i>
            </xsl:with-param>
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowStyle">display: none</xsl:with-param>
        </xsl:call-template>

        <xsl:choose>
            <xsl:when test="$isTemplate = 'true'">
                <!--Если создаем шаблон, то поля с курсами не отображаем-->
            </xsl:when>
            <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
            <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                <!--Поле льготный курс-->
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">courseRow</xsl:with-param>
                    <xsl:with-param name="required">false</xsl:with-param>
                    <xsl:with-param name="rowName">
                        Льготный курс конверсии:
                    </xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input type="hidden" value="{course}" name="course" id="courseValue"/>
                        <input type="hidden" value="{premierShowMsg}" name="premierShowMsg" id="premierShowMsg"/>
                        <b><span id="course"></span></b>
                    </xsl:with-param>
                </xsl:call-template>

                <!--Поле обычный курс-->
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">standartCourseRow</xsl:with-param>
                    <xsl:with-param name="required">false</xsl:with-param>
                    <xsl:with-param name="rowName">
                        Обычный курс конверсии:
                    </xsl:with-param>
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
                    <xsl:with-param name="description"></xsl:with-param>
                    <xsl:with-param name="rowName">Курс конверсии:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input type="hidden" value="{course}" name="course" id="courseValue"/>
                        <input type="hidden" value="{premierShowMsg}" name="premierShowMsg" id="premierShowMsg"/>
                        <span  id="course"></span>
                        <xsl:if test="$tarifPlanCodeType = '1'">
                            <div class="payments-legend" id="courseRow-payments-legend" style="display: none">
                                <div class="italic">Используется льготный курс покупки/продажи</div>
                            </div>
                        </xsl:if>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>

        <script type="text/javascript" src="{$resourceRoot}/scripts/conversionPayment.js"/>
        <script type="text/javascript">
            function checkRates()
            {
                if (<xsl:value-of select="string-length($rates) = 0"/>)
                {
                    addError('Не удалось получить курсы валют. Операция конверсии временно недоступна.', null, true);
                    return false;
                }
                return true;
            }

            function hideOrShowCourse()
            {
                showOrHidePremierWarning(false);
                var courseRow    = ensureElement('courceRow');
                var standartCourseRow = ensureElement('standartCourseRow');
                var courseGainRow    = ensureElement('courseGainRow');
                var toResource   = getElementValue('toResource');
                var fromResource = getElementValue('fromResource');

                if (toResource != "" &amp;&amp; fromResource != "")
                {
                    var toResourceCurrency   = currencies[toResource];
                    var fromResourceCurrency = currencies[fromResource];

                    if (toResourceCurrency != fromResourceCurrency)
                    {
                        if (isShowCoursePayment())      //переводы поддерживающие курс конверсии
                        {
                            var rate;
                            var rateShowMsg;
                            var standartRate;
                            if (!checkRates())
                                return;

                            if (fromResourceCurrency == "RUB")
                            {
                                rate = rates[toResourceCurrency + "|" + fromResourceCurrency]["SALE"];
                                rateShowMsg = rates[toResourceCurrency + "|" + fromResourceCurrency]["SALE_SHOW_MSG"];
                                standartRate = rates[toResourceCurrency + "|" + fromResourceCurrency]["SALE_0"];
                            }
                            else
                            {
                                rate = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY"];
                                rateShowMsg = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY_SHOW_MSG"];
                                standartRate = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY_0"];
                            }

                            if (!isEmpty(rate))
                            {
                                showCourse(fromResourceCurrency, toResourceCurrency, rate, standartRate, rateShowMsg);
                                hideOrShow(courseRow, false);
                                hideOrShow(standartCourseRow, false);
                                return;
                            }
                        }
                    }
                    hideOrShow(ensureElement("courseRow"), true);
                    hideOrShow(ensureElement("standartCourseRow"), true);
                    hideOrShow(ensureElement("courseGainRow"), true);
                }
            }

       		function showHideOperationTypeRow()
       		{
                var fromResource    = getElementValue("fromResource");
                var toResource      = getElementValue("toResource");

                var courseRow           = ensureElement("courseRow");
                var standartCourseRow   = ensureElement("standartCourseRow");
                var courseGainRow       = ensureElement("courseGainRow");
                var buyAmountRow        = ensureElement("buyAmountRow");
                var sellAmountRow       = ensureElement("sellAmountRow");
                var sellAmount          = ensureElement("sellAmount");
                var sellAmountCurrency  = ensureElement("sellAmountCurrency");
                var wordOr              = ensureElement("wordOr");
                var sellAmountLabel     = ensureElement("sellAmountTextLabel");
            
                hideOrShow(buyAmountRow,        true);
                hideOrShow(sellAmountRow,       false);
                hideOrShow(sellAmount,          false);
                hideOrShow(courseRow,           true);
                hideOrShow(standartCourseRow,   true);
                hideOrShow(courseGainRow,       true);

                sellAmountLabel.innerHTML   = "Сумма:";

                // очищаем конверсию, т.к нужная установится в hideOrShowCourse
                var courseValue = ensureElement("courseValue");
                if (courseValue != null)
                    courseValue.value = "";
                var premierShowMsg = ensureElement("premierShowMsg");
                if (premierShowMsg != null)
                    premierShowMsg.value = "";
                var standartCourseElement = ensureElement("standartCourseValue");
                if (standartCourseElement != null &amp;&amp; standartCourseElement != undefined)
                    standartCourseElement.value = "";

                if (fromResource != "" &amp;&amp; toResource != "")
                {
                    var fromCurrency = currencies[fromResource];
                    var toCurrency   = currencies[toResource];

                    $("#sellAmountCurrency").text(currencySignMap.get(fromCurrency));
                    $("#buyAmountCurrency").text(currencySignMap.get(toCurrency));

                    if (isCardToAccountPayment())       //если перевод с карты на счет
                    {
                        hideOrShow(buyAmountRow, false);
                        hideOrShow(sellAmount, true);
                        hideOrShow(sellAmountCurrency, true);
                        hideOrShow(wordOr, true);
                        sellAmountLabel.innerHTML = "Сумма в валюте зачисления:";
                    }
                    else if (fromCurrency != toCurrency)//для неравных валют 2 суммы
                    {
                        hideOrShow(buyAmountRow, toCurrency == null);
                        hideOrShow(wordOr, false);
                        hideOrShow(courseRow, false);
                        hideOrShow(standartCourseRow, false);
                        hideOrShow(courseGainRow, false);
                    }
                    else //валюты равны ->1 сумма (списания)
                    {
                        hideOrShow(wordOr, true);
                    }
                }

                //показываем/скрываем кнопку "Сохранить как регулярный платеж"
                var isLongOffer = ((toResource.indexOf('card:') != -1 &amp;&amp; fromResource.indexOf('card:') != -1) ||
                                    (toResource.indexOf('card:') != -1 &amp;&amp; fromResource.indexOf('account:') != -1) ||
                                    (toResource.indexOf('account:') != -1 &amp;&amp; fromResource.indexOf('card:') != -1));
                //Показываем ссылку только если счет списания не пуст
                if(<xsl:value-of select="count($activeCards) = 0 and count($activeDebitAccounts) = 0"/>)
                {
                    isLongOffer = true;
                }
                hideOrShowMakeLongOfferButton(isLongOffer);
                <xsl:if test="$isTemplate!='true'">
                    hideOrShowPostConfirmCommission();
                </xsl:if>
       		}

            function hideOrShowPostConfirmCommission()
            {
                var toResource   = getElementValue("toResource");
                var fromResource = getElementValue("fromResource");
                var paymentType;
                if(fromResource.indexOf('account:') != -1)
                {
                    if(toResource.indexOf('account:') != -1)
                        paymentType = "AcctToAcct";
                    if(toResource.indexOf('card:') != -1)
                        paymentType = "AcctToCard";
                    if(paymentType!=null &amp;&amp; (currencies[fromResource] == 'RUB' || currencies[fromResource] == 'RUR'))
                        paymentType = 'Rur' + paymentType;
                }
                var supportedCommissionPaymentTypes = "<xsl:value-of select="dh:getSupportedCommissionPaymentTypes()"/>".split('|');
                hideOrShow(ensureElement("postConfirmCommissionMessage"), !(supportedCommissionPaymentTypes.contains(paymentType)));
            }

       		function buyCurrency()
       		{
                var toResource   = getElementValue("toResource");
                var fromResource = getElementValue("fromResource");
                var exactAmount  = ensureElement("exactAmount");
                buyCurrencyCommon(currencies[fromResource],currencies[toResource], ensureElement("buyAmount"),ensureElement("sellAmount"), toResource != "" &amp;&amp; fromResource != "");
       		}

       		function sellCurrency()
       		{
                var toResource   = getElementValue("toResource");
                var fromResource = getElementValue("fromResource");
                var exactAmount  = ensureElement("exactAmount");
                sellCurrencyCommon(currencies[fromResource], currencies[toResource], ensureElement("buyAmount"), ensureElement("sellAmount"), toResource != "" &amp;&amp; fromResource != "");
       		}

            function showCourse(fromCurrency, toCurrency, rate, standartRate, rateShowMsg)
       		{
                $("#course").html(rate + " " + currencySignMap.get(fromCurrency) + "<i> &#8594; </i>" + currencySignMap.get(toCurrency));
                $("#standartCourseSpan").html(standartRate + " " + currencySignMap.get(fromCurrency) + " &#8594; " + currencySignMap.get(toCurrency));
                setCourseGain(standartRate, rate);

                ensureElement("courseValue").value = rate;
                ensureElement("premierShowMsg").value = rateShowMsg;

                var standartCourseElement = ensureElement("standartCourseValue");
                if (standartCourseElement != null &amp;&amp; standartCourseElement != undefined)
                    standartCourseElement.value = standartRate;

                showOrHidePremierWarning(rateShowMsg);
       		}
            <![CDATA[
       		function getAmount(amount, rate)
       		{
       		    if (amount != '' && !isNaN(amount))
       		        return parseFloat((parseFloat(amount)*rate).toFixed(4)).toFixed(2);
       		    return "";
       		}
            ]]>
       		function isShowCoursePayment()
       		{
       		    var toResource   = getElementValue("toResource");
       		    var fromResource = getElementValue("fromResource");

                if(fromResource == '' || toResource == '')
                    return false;

       		    return !(toResource.indexOf('card:') != -1 &amp;&amp; fromResource.indexOf('card:') != -1 ||
                            toResource.indexOf('account:') != -1 &amp;&amp; fromResource.indexOf('card:') != -1);
       		}

       		function isCardToAccountPayment()
       		{
       		    var toResource   = getElementValue("toResource");
       		    var fromResource = getElementValue("fromResource");

       		    return fromResource.indexOf('card:') != -1 &amp;&amp; toResource.indexOf('account:') != -1 ;
       		}

            function isAccountToPayment()
            {
                var fromResource = getElementValue("fromResource");

                return fromResource.indexOf('account:') != -1;
            }

            var rates        = new Array();
            var accounts     = new Array();
            var currencies   = new Array();
            var cardAccounts = new Array();
            var countNotEmptyAcc = 0;
            var countAcc = 0;
            var indexNotEmptyAcc = 0;
            var isEmptyRates = true; //Заполнен ли масив rates, тк length в данном случае не срабатывает

            function init()
            {
                var USING_STORED_CARDS_RESOURCE_MESSAGE    = 'Информация по Вашим картам может быть неактуальной.';
                var USING_STORED_ACCOUNTS_RESOURCE_MESSAGE = 'Информация по Вашим счетам может быть неактуальной.';

                <xsl:for-each select="$activeDebitAccounts">
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    accounts['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="@key"/>';
                    currencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                    <xsl:if test="field[@name='isUseStoredResource'] = 'true'">
                        addMessage(USING_STORED_ACCOUNTS_RESOURCE_MESSAGE);
                    </xsl:if>
                    countAcc++;
                    <xsl:if test="field[@name='amountDecimal'] > 0 ">
                        countNotEmptyAcc ++;
                        indexNotEmptyAcc = countAcc;
                    </xsl:if>
                </xsl:for-each>

                <xsl:for-each select="$activeCreditAccounts">
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    accounts['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="@key"/>';
                    currencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                    <xsl:if test="field[@name='isUseStoredResource'] = 'true'">
                        addMessage(USING_STORED_ACCOUNTS_RESOURCE_MESSAGE);
                    </xsl:if>
                </xsl:for-each>

                <xsl:for-each select="$activeCards">
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    currencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                    cardAccounts['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='cardAccount']/text()"/>';
                    <xsl:if test="field[@name='isUseStoredResource'] = 'true' ">
                        addMessage(USING_STORED_CARDS_RESOURCE_MESSAGE);
                    </xsl:if>
                </xsl:for-each>

                <xsl:for-each select="$rates">
                    rate = new Array()
                    <xsl:for-each select="*">
                        rate['<xsl:value-of select="@name"/>'] = '<xsl:value-of select="./text()"/>';
                    </xsl:for-each>
                    rates['<xsl:value-of select="@key"/>'] = rate;
                </xsl:for-each>

                showHideAdditionalCards();
                showHideOperationTypeRow();
                hideOrShowCourse();
                fillCurrency();
                selectDefaultFromResource(countNotEmptyAcc, indexNotEmptyAcc);
            }

            function fillCurrency()
            {
                var exactAmount = ensureElement("exactAmount");
                if (!isEmpty(exactAmount))
                {
                    if (exactAmount.value == "charge-off-field-exact")
                        sellCurrency();
                    if (exactAmount.value == "destination-field-exact")
                        buyCurrency();
                }
            }

            var hiddenOptions = new Array();

            function showHideAdditionalCards()
            {
                var select          = ensureElement('toResource');
                var optionsStorage  = new Array();

                if (isAccountToPayment())
                {
                    var i = 0;
                    var j = 0;
                    for (k = 0; k &lt; select.length; k++)
                    {
                        var copyOption = select.options[k].cloneNode(true);
                        copyOption.selected = select.selectedIndex == k;
                        if (select.options[k].className == 'hideable')
                        {
                            hiddenOptions[j++] = copyOption;
                        }
                        else
                        {
                            optionsStorage[i++] = copyOption;
                        }
                    }
                    // удаление всех опций
                    select.length = 0;
                    
                    for (i = 0; i &lt; optionsStorage.length; i++)
                    {
                        select.appendChild(optionsStorage[i]);
                    }
                }
                else
                {
                    for (i = 0; i &lt; hiddenOptions.length; i++)
                    {
                        select.appendChild(hiddenOptions[i]);
                    }
                    hiddenOptions.length = 0;
                }
            }

            $(document).ready(init);
        </script>

    </xsl:template>

    <xsl:template match="/form-data" mode="edit-long-offer">

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

        <xsl:variable name="accounts" select="document('stored-active-or-arrested-accounts.xml')/entity-list/*"/>
        <xsl:variable name="cards" select="document('stored-active-or-arrested-not-virtual-cards.xml')/entity-list/*"/>

        <xsl:variable name="fromResource" select="fromResource"/>
        <xsl:variable name="toResource" select="toResource"/>

        <xsl:variable name="fromResourceNumber">
            <xsl:choose>
                <xsl:when test="starts-with($fromResource, 'account:')">
                    <xsl:value-of select="$accounts[field[@name='linkId']/text()  = substring($fromResource, 9, string-length($fromResource))]/@key"/>
                </xsl:when>
                <xsl:when test="starts-with($fromResource, 'card:')">
                    <xsl:value-of select="$cards[field[@name='cardLinkId']/text() = substring($fromResource, 6, string-length($fromResource))]/@key"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="fromAccountSelect"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="fromResourceName">
            <xsl:choose>
                <xsl:when test="starts-with($fromResource, 'account:')">
                    <xsl:value-of select="$accounts[field[@name='linkId']/text()  = substring($fromResource, 9, string-length($fromResource))]/field[@name='name']/text()"/>
                </xsl:when>
                <xsl:when test="starts-with($fromResource, 'card:')">
                    <xsl:value-of select="$cards[field[@name='cardLinkId']/text() = substring($fromResource, 6, string-length($fromResource))]/field[@name='name']/text()"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="fromAccountName"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="fromResourceCurrency">
            <xsl:choose>
                <xsl:when test="starts-with($fromResource, 'account:')">
                    <xsl:value-of select="$accounts[field[@name='linkId']/text()  = substring($fromResource, 9, string-length($fromResource))]/field[@name='currencyCode']/text()"/>
                </xsl:when>
                <xsl:when test="starts-with($fromResource, 'card:')">
                    <xsl:value-of select="$cards[field[@name='cardLinkId']/text() = substring($fromResource, 6, string-length($fromResource))]/field[@name='currencyCode']/text()"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="fromResourceCurrency"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:choose>
            <!--необходимо привести fromResource к виду account/card:linkId-->
            <xsl:when test="not (starts-with($fromResource, 'account:')) and not (starts-with($fromResource, 'card:'))">
                <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
                <xsl:variable name="resource">
                    <xsl:if test="fromResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                        <xsl:value-of select="concat('account:', $accounts[@key = $fromAccountSelect]/field[@name='linkId']/text())"/>
                    </xsl:if>
                    <xsl:if test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                        <xsl:value-of select="concat('card:', $cards[@key = $fromAccountSelect]/field[@name='cardLinkId']/text())"/>
                    </xsl:if>
                </xsl:variable>
                <input type="hidden" name="fromResource" value="{$resource}"/>
            </xsl:when>
            <xsl:otherwise>
                <input type="hidden" name="fromResource" value="{fromResource}"/>
            </xsl:otherwise>
        </xsl:choose>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Счет списания:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <span id="reportSource">
                        <xsl:choose>
                            <xsl:when test="starts-with($fromResource, 'card:') or fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                <xsl:value-of select="mask:getCutCardNumber($fromResourceNumber)"/>
                            </xsl:when>
                            <xsl:otherwise><xsl:value-of select="au:getFormattedAccountNumber($fromResourceNumber)"/></xsl:otherwise>
                        </xsl:choose>
                        &nbsp;[<xsl:value-of select="$fromResourceName"/>]&nbsp;
                        <xsl:value-of select="mf:getCurrencySign($fromResourceCurrency)"/>
                    </span>
                </b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="toResourceNumber">
            <xsl:choose>
                <xsl:when test="starts-with($toResource, 'account:')">
                    <xsl:value-of select="$accounts[field[@name='linkId']/text()  = substring($toResource, 9, string-length($toResource))]/@key"/>
                </xsl:when>
                <xsl:when test="starts-with($toResource, 'card:')">
                    <xsl:value-of select="$cards[field[@name='cardLinkId']/text() = substring($toResource, 6, string-length($toResource))]/@key"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="toAccountSelect"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="toResourceName">
            <xsl:choose>
                <xsl:when test="starts-with($toResource, 'account:')">
                    <xsl:value-of select="$accounts[field[@name='linkId']/text()  = substring($toResource, 9, string-length($toResource))]/field[@name='name']/text()"/>
                </xsl:when>
                <xsl:when test="starts-with($toResource, 'card:')">
                    <xsl:value-of select="$cards[field[@name='cardLinkId']/text() = substring($toResource, 6, string-length($toResource))]/field[@name='name']/text()"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="toAccountName"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="toResourceCurrency">
            <xsl:choose>
                <xsl:when test="starts-with($toResource, 'account:')">
                    <xsl:value-of select="$accounts[field[@name='linkId']/text()  = substring($toResource, 9, string-length($toResource))]/field[@name='currencyCode']/text()"/>
                </xsl:when>
                <xsl:when test="starts-with($toResource, 'card:')">
                    <xsl:value-of select="$cards[field[@name='cardLinkId']/text() = substring($toResource, 6, string-length($toResource))]/field[@name='currencyCode']/text()"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="toResourceCurrency"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:choose>
            <!--необходимо привести toResource к виду account/card:linkId-->
            <xsl:when test="not (starts-with($toResource, 'account:')) and not (starts-with($toResource, 'card:'))">
                <xsl:variable name="toAccountSelect" select="toAccountSelect"/>
                <xsl:variable name="resource">
                    <xsl:if test="toResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                        <xsl:value-of select="concat('account:', $accounts[@key = $toAccountSelect]/field[@name='linkId']/text())"/>
                    </xsl:if>
                    <xsl:if test="toResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                        <xsl:value-of select="concat('card:', $cards[@key = $toAccountSelect]/field[@name='cardLinkId']/text())"/>
                    </xsl:if>
                </xsl:variable>
                <input type="hidden" name="toResource" value="{$resource}"/>
            </xsl:when>
            <xsl:otherwise>
                <input type="hidden" name="toResource" value="{toResource}"/>
            </xsl:otherwise>
        </xsl:choose>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Счет зачисления:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:choose>
                        <xsl:when test="starts-with($toResource, 'card:')">
                            <xsl:value-of select="mask:getCutCardNumber($toResourceNumber)"/>
                        </xsl:when>
                        <xsl:otherwise><xsl:value-of select="au:getFormattedAccountNumber($toResourceNumber)"/></xsl:otherwise>
                    </xsl:choose>
                    &nbsp;[<xsl:value-of select="$toResourceName"/>]&nbsp;
                    <xsl:value-of select="mf:getCurrencySign($toResourceCurrency)"/>
                </b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="sumTypes" select="document('long-offer-sum-types.xml')/entity-list/entity"/>

        <xsl:if test="(exactAmount = 'charge-off-field-exact' and string-length(sellAmount)>0) or (exactAmount != 'destination-field-exact')">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
                <xsl:with-param name="rowName">Сумма:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:if test="isSumModify = 'true'">
                        <b><xsl:value-of select="$sumTypes[@key=longOfferSumType]"/></b>&nbsp;
                        <xsl:choose>
                            <xsl:when test="longOfferSumType = 'PERCENT_OF_REMAIND'">
                                <b><xsl:value-of select="longOfferPercent"/>&nbsp;%</b>
                            </xsl:when>
                            <xsl:when test="longOfferSumType = 'FIXED_SUMMA' or longOfferSumType = 'REMAIND_OVER_SUMMA'">
                                <input type="hidden" name="sellAmount" value="{sellAmount}"/>
                                <xsl:if test="string-length(sellAmount)>0">
                                    <span class="summ">
                                        <xsl:value-of select="format-number(translate(sellAmount, ', ','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                        <xsl:value-of select="mf:getCurrencySign($fromResourceCurrency)"/>
                                    </span>
                                </xsl:if>
                            </xsl:when>
                        </xsl:choose>
                    </xsl:if>
                    <xsl:if test="isSumModify != 'true'">
                        <input type="hidden" name="sellAmount" value="{sellAmount}"/>
                        <xsl:if test="string-length(sellAmount)>0">
                            <span class="summ">
                                <xsl:value-of select="format-number(translate(sellAmount, ', ','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                <xsl:value-of select="mf:getCurrencySign($fromResourceCurrency)"/>
                            </span>
                        </xsl:if>
                    </xsl:if>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

		<xsl:if test="exactAmount = 'destination-field-exact' and string-length(buyAmount)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
                <xsl:with-param name="rowName">Сумма в валюте зачисления:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:if test="isSumModify = 'true'">
                        <b><xsl:value-of select="$sumTypes[@key=longOfferSumType]"/></b>&nbsp;
                        <xsl:choose>
                            <xsl:when test="longOfferSumType = 'FIXED_SUMMA_IN_RECIP_CURR' or longOfferSumType = 'REMAIND_IN_RECIP'">
                                <input type="hidden" name="buyAmount" value="{buyAmount}"/>
                                <xsl:if test="string-length(buyAmount)>0">
                                    <span class="summ">
                                        <xsl:value-of select="format-number(translate(buyAmount, ', ','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                        <xsl:value-of select="mf:getCurrencySign($toResourceCurrency)"/>
                                    </span>
                                </xsl:if>
                            </xsl:when>
                        </xsl:choose>
                    </xsl:if>
                    <xsl:if test="isSumModify != 'true'">
                        <input type="hidden" name="buyAmount" value="{buyAmount}"/>
                        <xsl:if test="string-length(buyAmount)>0">
                            <span class="summ">
                                <xsl:value-of select="format-number(translate(buyAmount, ', ','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                <xsl:value-of select="mf:getCurrencySign($toResourceCurrency)"/>
                            </span>
                        </xsl:if>
                    </xsl:if>
                </xsl:with-param>
            </xsl:call-template>
		</xsl:if>

        <xsl:if test="$mode = 'view'">
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
		</xsl:if>

		<xsl:if test="$isTemplate != 'true'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowStyle">display: none</xsl:with-param>
                <xsl:with-param name="rowName">Плановая дата исполнения:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="admissionDate" value="{admissionDate}"/>
                    <xsl:value-of select="admissionDate"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Параметры автоплатежа (регулярной операции)</b></xsl:with-param>
        </xsl:call-template>

        <input type="hidden" name="longOfferPayDay"/>
        <input type="hidden" name="exactAmount"  value="{exactAmount}"/>
        <input type="hidden" name="isSumModify"  value="{isSumModify}"/>
        <input type="hidden" name="firstPaymentDate"  value="{firstPaymentDate}"/>

        <xsl:variable name="firstPaymentDate">
            <xsl:choose>
                <xsl:when test="contains(longOfferStartDate, '-')">
                    <xsl:copy-of select="concat(substring(firstPaymentDate, 9, 2), '.', substring(firstPaymentDate, 6, 2), '.', substring(firstPaymentDate, 1, 4))"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="firstPaymentDate"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="month"  select="substring($firstPaymentDate, 4, 2)"/>

        <xsl:call-template name="standartRow">
           <xsl:with-param name="rowName">Дата начала действия:</xsl:with-param>
           <xsl:with-param name="rowValue">
               <xsl:variable name="longOfferStartDate">
                    <xsl:choose>
                        <xsl:when test="contains(longOfferStartDate, '-')">
                            <xsl:copy-of select="concat(substring(longOfferStartDate, 9, 2), '.', substring(longOfferStartDate, 6, 2), '.', substring(longOfferStartDate, 1, 4))"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:copy-of select="longOfferStartDate"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <input name="longOfferStartDate" id="longOfferStartDate" class="dot-date-pick" size="10" value="{$longOfferStartDate}"
                      onchange="javascript:refreshReport(getFormEvent(getElementValue('longOfferEventSelect')));"
                      onkeyup="javascript:refreshReport(getFormEvent(getElementValue('longOfferEventSelect')));"
                    />
           </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Дата окончания:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="longOfferEndDate">
                    <xsl:choose>
                        <xsl:when test="contains(longOfferEndDate, '-')">
                            <xsl:copy-of select="concat(substring(longOfferEndDate, 9, 2), '.', substring(longOfferEndDate, 6, 2), '.', substring(longOfferEndDate, 1, 4))"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:copy-of select="longOfferEndDate"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <input name="longOfferEndDate" class="dot-date-pick" size="10" value="{$longOfferEndDate}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="eventTypes" select="document('long-offer-events-types.xml')/entity-list/entity"/>
        <xsl:variable name="months" select="document('months.xml')/entity-list/entity"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Повторяется:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="longOfferSumType"    value="{longOfferSumType}"/>
                <input type="hidden" name="longOfferEventType"  value="{longOfferEventType}"/>

                <xsl:variable name="eventType"  select="longOfferEventType"/>

                <select id="longOfferEventSelect" name="longOfferEventSelect"
                        onchange="javascript:refreshForm(getElementValue('longOfferEventSelect'));"
                        onkeyup="javascript:refreshForm(getElementValue('longOfferEventSelect'));">

                    <xsl:for-each select="$eventTypes">
                        <xsl:if test="./@key = 'ONCE_IN_MONTH' or ./@key = 'ONCE_IN_QUARTER' or ./@key = 'ONCE_IN_HALFYEAR' or ./@key = 'ONCE_IN_YEAR' or ./@key = 'ON_EVENT'">
                            <option>
                                <xsl:if test="$eventType = ./@key">
                                    <xsl:attribute name="selected"/>
                                </xsl:if>
                                <xsl:if test="($eventType = 'BY_ANY_RECEIPT' or $eventType = 'BY_CAPITAL' or $eventType = 'BY_SALARY' or $eventType = 'BY_PENSION' or $eventType = 'BY_PERCENT' or $eventType = 'ON_OVER_DRAFT' or $eventType = 'ON_REMAIND') and (./@key = 'ON_EVENT')">
                                    <xsl:attribute name="selected"/>
                                </xsl:if>
                                <xsl:attribute name="value">
                                    <xsl:value-of select="./@key"/>
                                </xsl:attribute>
                                <xsl:value-of select="./text()"/>
                            </option>
                        </xsl:if>
                    </xsl:for-each>
                </select>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">everyMonthRow</xsl:with-param>
            <xsl:with-param name="rowName">Число месяца:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="text" name="ONCE_IN_MONTH_DAY" value="{longOfferPayDay}" size="1" maxlength="2"
                       onchange="javascript:refreshReport(getElementValue('longOfferEventSelect'));" onkeyup="javascript:refreshReport(getElementValue('longOfferEventSelect'));"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">everyQuarterRow</xsl:with-param>
            <xsl:with-param name="rowName">Дата оплаты:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <nobr>
                    месяц&nbsp;
                    <select name="ONCE_IN_QUARTER_MONTHS"
                            onchange="javascript:refreshReport(getElementValue('longOfferEventSelect'));"
                            onkeyup="javascript:refreshReport(getElementValue('longOfferEventSelect'));">

                        <xsl:call-template name="addOption">
                            <xsl:with-param name="value"    select="'01|04|07|10'"/>
                            <xsl:with-param name="selected" select="$month"/>
                            <xsl:with-param name="source"   select="$months"/>
                        </xsl:call-template>

                        <xsl:call-template name="addOption">
                            <xsl:with-param name="value"    select="'02|05|08|11'"/>
                            <xsl:with-param name="selected" select="$month"/>
                            <xsl:with-param name="source"   select="$months"/>
                        </xsl:call-template>

                        <xsl:call-template name="addOption">
                            <xsl:with-param name="value"    select="'03|06|09|12'"/>
                            <xsl:with-param name="selected" select="$month"/>
                            <xsl:with-param name="source"   select="$months"/>
                        </xsl:call-template>

                    </select>
                    &nbsp;число&nbsp;
                    <input type="text" name="ONCE_IN_QUARTER_DAY" value="{longOfferPayDay}" size="1" maxlength="2"
                           onchange="javascript:refreshReport(getElementValue('longOfferEventSelect'));" onkeyup="javascript:refreshReport(getElementValue('longOfferEventSelect'));"/>
                </nobr>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">halfYearRow</xsl:with-param>
            <xsl:with-param name="rowName">Дата оплаты:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <nobr>
                    месяц&nbsp;
                    <select name="ONCE_IN_HALFYEAR_MONTHS"
                            onchange="javascript:refreshReport(getElementValue('longOfferEventSelect'));"
                            onkeyup="javascript:refreshReport(getElementValue('longOfferEventSelect'));"
                        >

                        <xsl:call-template name="addOption">
                            <xsl:with-param name="value" select="'01|07'"/>
                            <xsl:with-param name="selected" select="$month"/>
                            <xsl:with-param name="source"   select="$months"/>
                        </xsl:call-template>

                        <xsl:call-template name="addOption">
                            <xsl:with-param name="value" select="'02|08'"/>
                            <xsl:with-param name="selected" select="$month"/>
                            <xsl:with-param name="source"   select="$months"/>
                        </xsl:call-template>

                        <xsl:call-template name="addOption">
                            <xsl:with-param name="value" select="'03|09'"/>
                            <xsl:with-param name="selected" select="$month"/>
                            <xsl:with-param name="source"   select="$months"/>
                        </xsl:call-template>

                        <xsl:call-template name="addOption">
                            <xsl:with-param name="value" select="'04|10'"/>
                            <xsl:with-param name="selected" select="$month"/>
                            <xsl:with-param name="source"   select="$months"/>
                        </xsl:call-template>

                        <xsl:call-template name="addOption">
                            <xsl:with-param name="value" select="'05|11'"/>
                            <xsl:with-param name="selected" select="$month"/>
                            <xsl:with-param name="source"   select="$months"/>
                        </xsl:call-template>

                        <xsl:call-template name="addOption">
                            <xsl:with-param name="value" select="'06|12'"/>
                            <xsl:with-param name="selected" select="$month"/>
                            <xsl:with-param name="source"   select="$months"/>
                        </xsl:call-template>

                    </select>
                    &nbsp;число&nbsp;
                    <input type="text" name="ONCE_IN_HALFYEAR_DAY" value="{longOfferPayDay}" size="1" maxlength="2"
                           onchange="javascript:refreshReport(getElementValue('longOfferEventSelect'));" onkeyup="javascript:refreshReport(getElementValue('longOfferEventSelect'));"/>
                </nobr>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">everyYearRow</xsl:with-param>
            <xsl:with-param name="rowName">Дата оплаты:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <nobr>
                    месяц&nbsp;
                    <select name="ONCE_IN_YEAR_MONTHS"
                            onchange="javascript:refreshReport(getElementValue('longOfferEventSelect'));"
                            onkeyup="javascript:refreshReport(getElementValue('longOfferEventSelect'));"
                        >

                        <xsl:for-each select="$months">
                            <option>
                                <xsl:if test="$month = ./@key">
                                    <xsl:attribute name="selected"/>
                                </xsl:if>
                                <xsl:attribute name="value">
                                    <xsl:value-of select="./@key"/>
                                </xsl:attribute>
                                <xsl:value-of select="./text()"/>
                            </option>
                        </xsl:for-each>
                    </select>
                    &nbsp;число&nbsp;
                    <input type="text" name="ONCE_IN_YEAR_DAY" value="{longOfferPayDay}" size="1" maxlength="2"
                           onchange="javascript:refreshReport(getElementValue('longOfferEventSelect'));" onkeyup="javascript:refreshReport(getElementValue('longOfferEventSelect'));"/>
                </nobr>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">onEventSelectRow</xsl:with-param>
            <xsl:with-param name="rowName">Событие:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <select name="onEventSelect" style="width: 250px;"
                        onchange="javascript:refreshSumType(getElementValue('onEventSelect'), getElementValue('longOfferSumType'));"
                        onkeyup="javascript:refreshSumType(getElementValue('onEventSelect'), getElementValue('longOfferSumType'));"
                    />
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Выполняется:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="priorities" select="document('priority.xml')/entity-list/entity"/>
                <xsl:variable name="priority"   select="longOfferPrioritySelect"/>

                <select name="longOfferPrioritySelect">
                    <xsl:for-each select="$priorities">
                        <option>
                            <xsl:if test="$priority = ./@key">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            <xsl:attribute name="value">
                                <xsl:value-of select="./@key"/>
                            </xsl:attribute>
                            <xsl:value-of select="./text()"/>
                        </option>
                    </xsl:for-each>
                </select>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="lineId">reportRow</xsl:with-param>
            <xsl:with-param name="rowName">Сводка:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><div id="report"></div></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Ближайший платеж:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><div id="firstPaymentDate"></div></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="lineId">paymentSumChange</xsl:with-param>
            <xsl:with-param name="rowName">&nbsp;</xsl:with-param>
            <xsl:with-param name="rowValue">
                <span class="text-green">изменить сумму платежа</span>
            </xsl:with-param>
        </xsl:call-template>

        <div id="paymentSumChangeBlock">
            <xsl:call-template name="titleRow">
                <xsl:with-param name="rowName">&nbsp;<b>Расчет суммы платежа</b></xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">modifySumRow</xsl:with-param>
                <xsl:with-param name="rowName">Тип суммы:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <select name="longOfferSumSelect" style="width: 250px;"
                            onchange="javascript:refreshPayTypeBlock(getElementValue('longOfferSumSelect'));"
                            onkeyup="javascript:refreshPayTypeBlock(getElementValue('longOfferSumSelect'));"
                        />
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">longOfferAmountRow</xsl:with-param>
                <xsl:with-param name="rowName">
                    <span id="simpleSumm">Сумма:</span>
                    <span id="remaindOverSumm">Сумма остатка:</span>
                </xsl:with-param>
                <xsl:with-param name="rowValue">
                    <span id="sellAmountBlock">
                        <input type="text" name="sellAmount" value="{sellAmount}"
                               size="10" maxlength="10" class="moneyField"/>
                        &nbsp;
                        <span  id="sellAmountCurrency">
                            <xsl:value-of select="mf:getCurrencySign($fromResourceCurrency)"/>
                        </span>
                    </span>
                    <span id="buyAmountBlock">
                        <input type="text" name="buyAmount"  value="{buyAmount}"
                               size="10" maxlength="10" class="moneyField"/>
                        &nbsp;
                        <span id="buyAmountCurrency">
                            <xsl:value-of select="mf:getCurrencySign($toResourceCurrency)"/>
                        </span>
                    </span>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">longOfferPercentRow</xsl:with-param>
                <xsl:with-param name="rowName">Процент:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="text" name="longOfferPercent" value="{longOfferPercent}"
                           size="10" maxlength="5"/>&nbsp;
                    <span id="longOfferPercentSymbol"><b>%</b></span>
                </xsl:with-param>
            </xsl:call-template>
        </div>

        <script type="text/javascript">
            <![CDATA[

                Date.format = 'dd.mm.yyyy';         //устанавливаем для календаря формат даты

                var WRONG_DAY_ERROR = "Вы неправильно указали число месяца для оплаты. Пожалуйста, введите число от 1 до 28.";
                getElement("isSumModify").value = false;

                $('#paymentSumChange').click(function(){
                    $(this).hide();
                    $('#paymentSumChangeBlock').show();

                    var isSumModify = getElement("isSumModify");
                    isSumModify.value = "true";

                    var exactAmount = getElementValue("exactAmount");
                    if (exactAmount == "charge-off-field-exact")
                        $('#sellAmountRow').remove();
                    if (exactAmount == "destination-field-exact")
                        $('#buyAmountRow').remove();
                });

            ]]>
            <xsl:if test="isSumModify = 'true'">
                $('#paymentSumChange').click();
            </xsl:if>
            <![CDATA[

                function getFormEvent(formEvent)
                {
                    var event;
                    if (isEmpty(formEvent))
                    {
                        //первоначально открваем для "ежемесячно"
                        event = "ONCE_IN_MONTH";
                    }
                    else if (formEvent == "ON_EVENT")
                    {
                        //если выбрали повторяется "по событию", то заполняем значением из поля "по событию"
                        var selectedEvent = getElementValue('onEventSelect');
                        event = isEmpty(selectedEvent) ? "BY_ANY_RECEIPT" : selectedEvent;
                    }
                    else
                    {
                        event = formEvent;
                    }
                    return event;
                }

                var nameFieldDate = '';

                function refreshForm(formEvent)
                {
                    var event = getFormEvent(formEvent);
                    var exactAmount = getElementValue("exactAmount");
                    var isSumModify = getElementValue("isSumModify") == "true";

                    //заполняем название поля с датой, чтоб правильно выводить ошибку
                    if (!isPeriodic(event))
                        nameFieldDate = '';
                    else
                        nameFieldDate = event + '_DAY';

                    hideOrShow(ensureElement("everyMonthRow"),  event != "ONCE_IN_MONTH");
                    hideOrShow(ensureElement("everyQuarterRow"),    event != "ONCE_IN_QUARTER");
                    hideOrShow(ensureElement("halfYearRow"),    event != "ONCE_IN_HALFYEAR");
                    hideOrShow(ensureElement("everyYearRow"),   event != "ONCE_IN_YEAR");
                    hideOrShow(ensureElement("onEventSelectRow"),   isPeriodic(event));
                    hideOrShow(ensureElement("paymentSumChange"),   isSumModify);
                    hideOrShow(ensureElement("paymentSumChangeBlock"),  !isSumModify);

                    var sellAmountRow = ensureElement("sellAmountRow");
                    var buyAmountRow  = ensureElement("buyAmountRow");

                    if (buyAmountRow  != null && exactAmount == "destination-field-exact")
                        hideOrShow(ensureElement("buyAmountRow"),   isSumModify);
                    if (sellAmountRow != null && exactAmount == "charge-off-field-exact")
                        hideOrShow(ensureElement("sellAmountRow"),  isSumModify);

                    //перезаполняем селект "Событие"
                    if (!isPeriodic(event))
                    {
                        var onEventSelectLength = 0;
                        var onEventSelect = getElement("onEventSelect");
                        onEventSelect.options.length = onEventSelectLength;

                        //для переводов вклад-вклад, карта-вклад
                        onEventSelect.options[onEventSelectLength++] = new Option(eventTypes["BY_ANY_RECEIPT"],    "BY_ANY_RECEIPT");
                        onEventSelect.options[onEventSelectLength++] = new Option(eventTypes["ON_REMAIND"], "ON_REMAIND");

                        var toResource   = getElementValue("toResource");
                        var fromResource = getElementValue("fromResource");

                        //для переводов вклад-вклад
                        if (fromResource.indexOf('account:') != -1 && toResource.indexOf('account:') != -1)
                        {
                            onEventSelect.options[onEventSelectLength++] = new Option(eventTypes["BY_CAPITAL"], "BY_CAPITAL");
                            onEventSelect.options[onEventSelectLength++] = new Option(eventTypes["BY_PERCENT"], "BY_PERCENT");
                        }

                        //для переводов карта-вклад
                        if (fromResource.indexOf('card:') != -1 && toResource.indexOf('account:') != -1)
                        {
                            onEventSelect.options[onEventSelectLength++] = new Option(eventTypes["BY_SALARY"],  "BY_SALARY");
                            onEventSelect.options[onEventSelectLength++] = new Option(eventTypes["BY_PENSION"], "BY_PENSION");
                        }

                        for (var i=0; i < onEventSelect.length; i++)
                        {
                            if (onEventSelect[i].value == event)
                                onEventSelect[i].selected = true;
                        }
                    }

                    refreshSumType(event, getElementValue("longOfferSumType"));
                    refreshPayTypeBlock(getElementValue("longOfferSumSelect"));
                    refreshReport(event);
                }

                //обновление сводки и ближайшей даты платежа
                function refreshReport(event)
                {
                   var longOfferStartDate = getElement("longOfferStartDate");
                   var firstPaymentDate = getElement("firstPaymentDate");
                   var longOfferDate = new LongOfferDate(event, createDate(longOfferStartDate.value));

                    if (isPeriodic(event))
                    {
                        var day = getElementValue(event + "_DAY");

                        if (longOfferDate.validate() == undefined)
                        {
                            removeError(WRONG_DAY_ERROR);
                            clearReportArea();
                            return;
                        }

                        if (longOfferDate.validate())
                        {
                            var date = longOfferDate.toString();

                            firstPaymentDate.value = date;
                            getElement("longOfferPayDay").value = day;
                            $("#firstPaymentDate").text(date);
                            $("#report").text(eventTypes[event] + " " + parseInt(day) + "-го числа");
                            removeError(WRONG_DAY_ERROR);
                        }
                        else
                        {
                            addError(WRONG_DAY_ERROR);
                            clearReportArea();
                        }
                    }
                    else
                    {
                        $("#report").text(eventTypes[event] + " на " + $("#reportSource").text());
                        $("#firstPaymentDate").text(eventTypes[event]);
                        firstPaymentDate.value = longOfferDate.toString();
                    }

                    function clearReportArea()
                    {
                        getElement("firstPaymentDate").value = "";
                        getElement("longOfferPayDay").value = "";
                        $("#firstPaymentDate").text("");
                        $("#report").text("");
                    }
                }

                function refreshSumType(event, sumType)
                {
                    $("#simpleSumm").show();
                    $("#remaindOverSumm").hide();

                    var sumTypeSelectLength = 0;
                    var sumTypeSelect = getElement("longOfferSumSelect");
                    sumTypeSelect.options.length = sumTypeSelectLength;

                    var toResource   = getElementValue("toResource");
                    var fromResource = getElementValue("fromResource");

                    //ежемесячно, ежеквартально, раз в полгода, раз в год, по любому зачислению
                    if (isPeriodic(event) || event == "BY_ANY_RECEIPT")
                    {
                        //для переводов вклад-вклад, карта вклад
                        //если платеж карта-вклад, то нельзя выбрать Фиксированную сумму в валюте списания
                        if (!(fromResource.indexOf('card:') != -1 && toResource.indexOf('account:') != -1))
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["FIXED_SUMMA"],  "FIXED_SUMMA");

                        //если валюта счета списания и валюта счета зачисления различны, либо перевод с карты на счет
                        if ($("#sellAmountCurrency").text() != $("#buyAmountCurrency").text() || (fromResource.indexOf('card:') != -1 && toResource.indexOf('account:') != -1))
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["FIXED_SUMMA_IN_RECIP_CURR"],    "FIXED_SUMMA_IN_RECIP_CURR");
                        sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["PERCENT_OF_REMAIND"],   "PERCENT_OF_REMAIND");
                        sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["REMAIND_IN_RECIP"], "REMAIND_IN_RECIP");
                        sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["SUMMA_OF_RECEIPT"], "SUMMA_OF_RECEIPT");

                        //данные типы только для перевода вклад-вклад
                        if (fromResource.indexOf('card:') != -1 && toResource.indexOf('account:') != -1)
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["REMAIND_OVER_SUMMA"],   "REMAIND_OVER_SUMMA");
                    }

                    //по зачислению зарплаты, по зачислению пенсии
                    if (event == "BY_SALARY" || event == "BY_PENSION")
                    {
                        //для переводов карта вклад
                        if (fromResource.indexOf('card:') != -1 && toResource.indexOf('account:') != -1)
                        {
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["FIXED_SUMMA_IN_RECIP_CURR"],    "FIXED_SUMMA_IN_RECIP_CURR");
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["REMAIND_OVER_SUMMA"],   "REMAIND_OVER_SUMMA");
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["PERCENT_OF_REMAIND"],   "PERCENT_OF_REMAIND");
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["REMAIND_IN_RECIP"], "REMAIND_IN_RECIP");
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["SUMMA_OF_RECEIPT"], "SUMMA_OF_RECEIPT");
                        }
                    }

                    //остаток на счете списания больше минимального остатка
                    if (event == "ON_REMAIND")
                    {
                        //для переводов вклад-вклад, карта вклад
                        sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["REMAIND_OVER_SUMMA"],   "REMAIND_OVER_SUMMA");

                        $("#simpleSumm").hide();
                        $("#remaindOverSumm").show();
                        $('#paymentSumChange').click();
                    }

                    //по капитализации
                    if (event == "BY_CAPITAL")
                    {
                        //для переводов вклад-вклад
                        if (fromResource.indexOf('account:') != -1 && toResource.indexOf('account:') != -1)
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["PERCENT_OF_CAPITAL"],   "PERCENT_OF_CAPITAL");
                    }

                    //по зачислению процентов
                    if (event == "BY_PERCENT")
                    {
                        //для переводов вклад-вклад
                        if (fromResource.indexOf('account:') != -1 && toResource.indexOf('account:') != -1)
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["SUMMA_OF_RECEIPT"], "SUMMA_OF_RECEIPT");
                    }

                    for (var i=0; i < sumTypeSelect.length; i++)
                    {
                        if (sumTypeSelect[i].value == sumType)
                            sumTypeSelect[i].selected = true;
                    }

                    getElement("longOfferEventType").value = event;
                    refreshPayTypeBlock(sumTypeSelect.value);
                    refreshReport(event);
                }

                function refreshPayTypeBlock(type)
                {

                    var sellAmount  = getElement("sellAmount");
                    var buyAmount   = getElement("buyAmount");
                    var exactAmount = getElement("exactAmount")
                    var percent = getElement("longOfferPercent");

                    var isSellAmount    = (type == "FIXED_SUMMA" || type == "REMAIND_OVER_SUMMA");
                    var isBuyAmount = (type == "FIXED_SUMMA_IN_RECIP_CURR" || type == "REMAIND_IN_RECIP");
                    var isPercent   = (type == "PERCENT_OF_REMAIND");

                    hideOrShow(ensureElement("longOfferAmountRow"), !(isSellAmount || isBuyAmount));
                    hideOrShow(ensureElement("longOfferPercentRow"),    !isPercent);
                    hideOrShow(ensureElement("sellAmountBlock"), !isSellAmount);
                    hideOrShow(ensureElement("buyAmountBlock"),  !isBuyAmount);

                    if (isSellAmount)
                    {
                        percent.value = "";
                        buyAmount.value = "";
                        exactAmount.value = "charge-off-field-exact";
                    }
                    if (isBuyAmount)
                    {
                        percent.value = "";
                        sellAmount.value = "";
                        exactAmount.value = "destination-field-exact";
                    }
                    if (isPercent)
                    {
                        sellAmount.value = "";
                        buyAmount.value = ""
                    }

                    if (!isSellAmount && !isBuyAmount)
                        exactAmount.value = "";

                    getElement("longOfferSumType").value = type;
                    getElement("longOfferSumSelect").value = type;
                }

                //в некоторых формах требуется показывать ошибку в поле с другим названием(не с тем что пришло)
                function changeErrors(errors)
                {
                    var temp = new Object();
                    for (var field in errors)
                    {
                        if (field == 'firstPaymentDate' && nameFieldDate != '')
                            temp[nameFieldDate] = errors[field];
                        else
                            temp[field] = errors[field];
                    }
                    return temp;
                }
            ]]>

                var sumTypes    = new Array();
                var eventTypes = new Array();

                function init()
                {
                    <xsl:for-each select="$sumTypes">
                        sumTypes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./text()"/>';
                    </xsl:for-each>

                    <xsl:for-each select="$eventTypes">
                        eventTypes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./text()"/>';
                    </xsl:for-each>

                    setCurrentDateToInput("longOfferStartDate");
                }

                init();
                refreshForm("<xsl:value-of select="longOfferEventType"/>");

        </script>

    </xsl:template>

    <xsl:template match="/form-data" mode="view-simple-payment">

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

        <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
        <xsl:variable name="toAccountSelect" select="toAccountSelect"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счет списания:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="not($fromAccountSelect = '')">
                    <b>
                        <xsl:choose>
                            <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                            </xsl:when>
                            <xsl:otherwise><xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/></xsl:otherwise>
                        </xsl:choose>
                        &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                        <xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                    </b>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счет зачисления:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="not($toAccountSelect = '')">
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
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

		<xsl:variable name="isConversion" select="string-length(sellAmount)>0 and string-length(buyAmount)>0"/>
		<xsl:variable name="longOfferSumType"   select="longOfferSumType"/>
		<xsl:variable name="sumTypes" select="document('long-offer-sum-types.xml')/entity-list/entity"/>

        <xsl:if test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
            <script type="text/javascript">
                      addMessage("Обмен валюты выполняется по курсу, установленному банком для операций по банковским картам, с которым можно ознакомиться на сайте банка.");
            </script>
        </xsl:if>

        <xsl:if test="courseChanged = 'true'">
              <script type="text/javascript">
                  addError("Курс валюты банка изменился! Пожалуйста, отредактируйте платеж. ");
              </script>
        </xsl:if>
        <xsl:variable name="isChargeOff" select="exactAmount = 'charge-off-field-exact' or exactAmount != 'destination-field-exact'"/>

        <xsl:choose>
            <xsl:when test="$postConfirmCommission">
                <xsl:call-template name="transferSumRows">
                    <xsl:with-param name="fromResourceCurrency" select="fromResourceCurrency"/>
                    <xsl:with-param name="toResourceCurrency" select="toResourceCurrency"/>
                    <xsl:with-param name="chargeOffAmount" select="sellAmount"/>
                    <xsl:with-param name="destinationAmount" select="buyAmount"/>
                    <xsl:with-param name="documentState" select="state"/>
                    <xsl:with-param name="exactAmount" select="exactAmount"/>
                    <xsl:with-param name="needUseTotalRow" select="'true'"/>
                    <xsl:with-param name="tariffPlanESB" select="tariffPlanESB"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:if test="$isChargeOff or ($isConversion and not($longOffer))">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
                        <xsl:with-param name="rowName">
                            <xsl:choose>
                                <xsl:when test="$isConversion and not($longOffer)">
                                    Сумма в валюте списания:
                                </xsl:when>
                                <xsl:otherwise>
                                    Сумма:
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:if test="$longOffer and (isSumModify = 'true')">
                                <b><xsl:value-of select="$sumTypes[@key=$longOfferSumType]"/></b>&nbsp;
                                <xsl:if test="$longOfferSumType='PERCENT_OF_REMAIND'">
                                    <b><xsl:value-of select="longOfferPercent"/>&nbsp;%</b>
                                </xsl:if>
                                <xsl:if test="$longOfferSumType='FIXED_SUMMA' or $longOfferSumType='REMAIND_OVER_SUMMA'">
                                    <xsl:if test="string-length(sellAmount)>0">
                                        <span class="summ">
                                            <xsl:value-of select="format-number(sellAmount, '### ##0,00', 'sbrf')"/>&nbsp;
                                            <xsl:value-of select="mf:getCurrencySign(sellAmountCurrency)"/>
                                        </span>
                                    </xsl:if>
                                </xsl:if>
                            </xsl:if>
                            <xsl:if test="not ($longOffer) or ($longOffer and isSumModify != 'true')">
                                <xsl:if test="string-length(sellAmount)>0">
                                    <span>
                                        <xsl:attribute name="class">
                                            summ <xsl:if test="exactAmount = 'destination-field-exact' and courseChanged = 'true'">red</xsl:if>
                                        </xsl:attribute>
                                        <xsl:value-of select="format-number(sellAmount, '### ##0,00', 'sbrf')"/>&nbsp;
                                        <xsl:value-of select="mf:getCurrencySign(sellAmountCurrency)"/>
                                    </span>
                                </xsl:if>
                            </xsl:if>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:variable name="isDestination" select="exactAmount = 'destination-field-exact'"/>
                <xsl:if test="$isDestination or ($isConversion and not($longOffer))">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
                        <xsl:with-param name="rowName">Сумма в валюте зачисления:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:if test="$longOffer and (isSumModify = 'true')">
                                <b><xsl:value-of select="$sumTypes[@key=$longOfferSumType]"/></b>&nbsp;
                            </xsl:if>
                            <xsl:if test="string-length(buyAmount)>0">
                                <span>
                                    <xsl:attribute name="class">
                                         summ <xsl:if test="exactAmount = 'charge-off-field-exact' and courseChanged = 'true'">red</xsl:if>
                                    </xsl:attribute>
                                    <xsl:value-of select="format-number(buyAmount, '### ##0,00', 'sbrf')"/>&nbsp;
                                    <xsl:value-of select="mf:getCurrencySign(buyAmountCurrency)"/>
                                </span>
                            </xsl:if>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:otherwise>
        </xsl:choose>

        <xsl:choose>
            <xsl:when test="$isTemplate = 'true'">
                <!--Если создаем шаблон, курс не отображаем-->
            </xsl:when>
            <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
            <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true') and not ($longOffer) and string-length(course)>0">
                <!--Поле льготный курс-->
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Льготный курс конверсии:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:choose>
                            <xsl:when test="courseChanged = 'true'">
                                <span class="text-red">
                                    <b><xsl:value-of select="format-number(course, '### ##0,00##', 'sbrf')"/></b>&nbsp;
                                </span>
                            </xsl:when>
                            <xsl:otherwise>
                                <b><xsl:value-of select="format-number(course, '### ##0,00##', 'sbrf')"/></b>&nbsp;
                            </xsl:otherwise>
                        </xsl:choose>
                        <span  id="course">
                            <b><xsl:value-of select="mf:getCurrencySign(sellAmountCurrency)"/></b>
                            &#8594;
                            <b><xsl:value-of select="mf:getCurrencySign(buyAmountCurrency)"/></b>
                        </span>
                    </xsl:with-param>
                </xsl:call-template>

                <!--Поле обычный курс-->
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Обычный курс конверсии:</xsl:with-param>
                    <xsl:with-param name="id">confirmStandartCourse</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <span class="crossed">
                            <xsl:value-of select="format-number(standartCourse, '### ##0,00##', 'sbrf')"/>&nbsp;
                            <span id="standartCourse">
                                <xsl:value-of select="mf:getCurrencySign(sellAmountCurrency)"/>
                                &#8594;
                                <xsl:value-of select="mf:getCurrencySign(buyAmountCurrency)"/>
                            </span>
                        </span>
                    </xsl:with-param>
                </xsl:call-template>

                <!--Моя выгода-->
                <xsl:if test="standartCourse > course or (toResourceCurrency = 'RUB' and course > standartCourse)">
                    <xsl:if test="string-length(sellAmount) > 0">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="id">courseGain</xsl:with-param>
                            <xsl:with-param name="rowName">Моя выгода:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <xsl:choose>
                                    <xsl:when test="fromResourceCurrency = 'RUB'">
                                        <xsl:call-template name="abs">
                                            <xsl:with-param name="input" select="mu:roundDestinationAmount((1 div course - 1 div standartCourse) * sellAmount)" />
                                        </xsl:call-template>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:call-template name="abs">
                                            <xsl:with-param name="input" select="mu:roundDestinationAmount((course - standartCourse) * sellAmount)" />
                                        </xsl:call-template>
                                    </xsl:otherwise>
                                </xsl:choose>
                                &nbsp;
                                <xsl:value-of select="mf:getCurrencySign(buyAmountCurrency)"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:if>
            </xsl:when>
            <!--В противном случае -->
            <xsl:otherwise>
                <xsl:if test="$isConversion and not ($longOffer) and string-length(course)>0">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Курс конверсии:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:choose>
                                <xsl:when test="courseChanged = 'true'">
                                    <span class="text-red">
                                        <b><xsl:value-of select="format-number(course, '### ##0,00##', 'sbrf')"/></b>&nbsp;
                                    </span>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="format-number(course, '### ##0,00##', 'sbrf')"/>&nbsp;
                                </xsl:otherwise>
                            </xsl:choose>
                            <span  id="course">
                                <xsl:value-of select="mf:getCurrencySign(sellAmountCurrency)"/>&#8594;<xsl:value-of select="mf:getCurrencySign(buyAmountCurrency)"/>
                            </span>
                            <xsl:if test="$tarifPlanCodeType = '1'">
                                <div class="payments-legend" id="courseRow-payments-legend" style="display: none">
                                    <div class="italic">Используется льготный курс покупки/продажи</div>
                                </div>
                            </xsl:if>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:otherwise>
        </xsl:choose>

        <xsl:if test="$isTemplate!='true'">
            <xsl:choose>
                <xsl:when test="string-length(commission)>0 and not($postConfirmCommission)">
                    <xsl:if test="format-number(commission, '0,00', 'sbrf') = '0,00' and state = 'SAVED'">
                        <script type="text/javascript">
                            if(window.addMessage != undefined)
                                addMessage("За выполнение данной операции комиссия не взимается.");
                         </script>
                    </xsl:if>
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'false'"/>
                        <xsl:with-param name="rowName">Комиссия:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mf:getCurrencySign(commissionCurrency)"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:when>
                <xsl:when test="state = 'SAVED' and (not($postConfirmCommission) or ($postConfirmCommission and string-length(commission)=0))">
                    <script type="text/javascript">
                    <![CDATA[
                        if(window.addMessage != undefined)
                            addMessage("За данную операцию может взиматься комиссия в соответствии с тарифами банка. " +
                                       "Сумму комиссии Вы можете посмотреть <a href='http://www.sberbank.ru/common/img/uploaded/files/pdf/person/bank_cards/Perevody__Tarify.pdf' class='paperEnterLink' target='_blank'>здесь</a>.");
                        ]]>
                    </script>
                </xsl:when>
            </xsl:choose>
        </xsl:if>

        <xsl:if test="$mode = 'view' and not($postConfirmCommission and state = 'SAVED')">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">
                    Статус
                    <xsl:choose>
                        <xsl:when test="$isTemplate != 'true'">
                            платежа:
                        </xsl:when>
                        <xsl:otherwise>
                            шаблона:    
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:with-param>
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
		</xsl:if>

		<xsl:if test="$isTemplate != 'true'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowStyle">display: none</xsl:with-param>
                <xsl:with-param name="rowName">Плановая дата исполнения:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:value-of select="admissionDate"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <script type="text/javascript">
            doOnLoad(function()
            {
                showOrHidePremierWarning(<xsl:value-of select="premierShowMsg"/>);
            });
        </script>
    </xsl:template>

    <xsl:template match="/form-data" mode="view-long-offer">

        <xsl:apply-templates select="/form-data" mode="view-simple-payment"/>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Параметры автоплатежа (регулярной операции)</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Дата начала действия:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:variable name="startDate" select="longOfferStartDate"/>
                    <xsl:value-of select="concat(substring($startDate, 9, 2), '.', substring($startDate, 6, 2), '.', substring($startDate, 1, 4))"/>
                </b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Дата окончания:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:variable name="endDate" select="longOfferEndDate"/>
                    <xsl:value-of select="concat(substring($endDate, 9, 2), '.', substring($endDate, 6, 2), '.', substring($endDate, 1, 4))"/>
                </b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="event"  select="longOfferEventType"/>
        <xsl:variable name="months" select="document('months.xml')/entity-list/entity"/>
        <xsl:variable name="eventTypes" select="document('long-offer-events-types.xml')/entity-list/entity[@key=$event]/text()"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Повторяется:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:value-of select="$eventTypes"/>
                </b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="isPeriodic" select="$event = 'ONCE_IN_MONTH' or $event = 'ONCE_IN_YEAR' or $event = 'ONCE_IN_QUARTER' or $event = 'ONCE_IN_HALFYEAR'"/>
        <xsl:variable name="firstDate"  select="firstPaymentDate"/>
        <xsl:variable name="day"    select="substring($firstDate, 9, 2)"/>
        <xsl:variable name="month"  select="substring($firstDate, 6, 2)"/>
        <xsl:variable name="year"   select="substring($firstDate, 1, 4)"/>

        <xsl:if test="$isPeriodic">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Дата оплаты:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b>
                        <xsl:choose>
                            <xsl:when test="$event = 'ONCE_IN_MONTH' or $event = 'ONCE_IN_YEAR'">
                                <xsl:call-template name="monthsToString">
                                    <xsl:with-param name="value"  select="$month"/>
                                    <xsl:with-param name="source" select="$months"/>
                                </xsl:call-template>
                                <xsl:value-of select="concat('. ', $day, ' число')"/>
                            </xsl:when>
                            <xsl:when test="$event = 'ONCE_IN_QUARTER'">
                                <xsl:variable name="period">
                                    <xsl:if test="($month mod 3) = 1">
                                        <xsl:value-of select="'01|04|07|10'"/>
                                    </xsl:if>
                                    <xsl:if test="($month mod 3) = 2">
                                        <xsl:value-of select="'02|05|08|11'"/>
                                    </xsl:if>
                                    <xsl:if test="($month mod 3) = 0">
                                        <xsl:value-of select="'03|06|09|12'"/>
                                    </xsl:if>
                                </xsl:variable>

                                <xsl:call-template name="monthsToString">
                                    <xsl:with-param name="value"  select="$period"/>
                                    <xsl:with-param name="source" select="$months"/>
                                </xsl:call-template>
                                <xsl:value-of select="concat('. ', $day, ' число')"/>
                            </xsl:when>
                            <xsl:when test="$event = 'ONCE_IN_HALFYEAR'">
                                <xsl:variable name="period">
                                    <xsl:if test="($month mod 6) = 1">
                                        <xsl:value-of select="'01|07'"/>
                                    </xsl:if>
                                    <xsl:if test="($month mod 6) = 2">
                                        <xsl:value-of select="'02|08'"/>
                                    </xsl:if>
                                    <xsl:if test="($month mod 6) = 3">
                                        <xsl:value-of select="'03|09'"/>
                                    </xsl:if>
                                    <xsl:if test="($month mod 6) = 4">
                                        <xsl:value-of select="'04|10'"/>
                                    </xsl:if>
                                    <xsl:if test="($month mod 6) = 5">
                                        <xsl:value-of select="'05|11'"/>
                                    </xsl:if>
                                    <xsl:if test="($month mod 6) = 0">
                                        <xsl:value-of select="'06|12'"/>
                                    </xsl:if>
                                </xsl:variable>

                                <xsl:call-template name="monthsToString">
                                    <xsl:with-param name="value"  select="$period"/>
                                    <xsl:with-param name="source" select="$months"/>
                                </xsl:call-template>
                                <xsl:value-of select="concat('. ', $day, ' число')"/>
                            </xsl:when>
                        </xsl:choose>
                    </b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Выполняется:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:variable name="priority" select="longOfferPrioritySelect"/>
                    <xsl:value-of select="document('priority.xml')/entity-list/entity[@key=$priority]/text()"/>
                </b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Сводка:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:choose>
                        <xsl:when test="$isPeriodic">
                            <xsl:value-of select="concat($eventTypes, ' ' , $day, '-го числа')"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$eventTypes"/>
                            <xsl:choose>
                                <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                    &nbsp;<xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    &nbsp;<xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/>
                                </xsl:otherwise>
                            </xsl:choose>
                            &nbsp;[<xsl:value-of select="fromAccountName"/>]
                            &nbsp;<xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Ближайший платеж:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:choose>
                        <xsl:when test="$isPeriodic and isStartDateChanged = 'true'">
                            <span class="text-red">
                                <xsl:value-of select="concat($day, '.', $month, '.', $year)"/>
                            </span>
                        </xsl:when>
                        <xsl:when test="$isPeriodic and isStartDateChanged != 'true'">
                            <xsl:value-of select="concat($day, '.', $month, '.', $year)"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$eventTypes"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

	<xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='BILLING_CONFIRM_TIMEOUT'">Таймаут при подтверждении в биллинге (ЕРИБ) (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='BILLING_GATE_CONFIRM_TIMEOUT'">Таймаут при подтверждении в биллинге (шлюз) (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='ABS_RECALL_TIMEOUT'">Таймаут при отзыве в АБС (ЕРИБ) (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='ABS_GATE_RECALL_TIMEOUT'">Таймаут при отзыве в АБС (шлюз) (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">Ожидает дополнительной обработки (статус для клиента: "Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if>")</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE' or $code='TEMPLATE'">Активный</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
		</xsl:choose>
	</xsl:template>

    <xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='INITIAL'">Черновик</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
            <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
            <xsl:when test="$code='BILLING_CONFIRM_TIMEOUT'">Исполняется банком</xsl:when>
            <xsl:when test="$code='BILLING_GATE_CONFIRM_TIMEOUT'">Исполняется банком</xsl:when>
            <xsl:when test="$code='ABS_RECALL_TIMEOUT'">Исполняется банком</xsl:when>
            <xsl:when test="$code='ABS_GATE_RECALL_TIMEOUT'">Исполняется банком</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if></xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE' or $code='TEMPLATE'">Активный</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
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
		<xsl:when test=" name() = 'cut' and position() = 1 ">
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
	            <xsl:attribute name="id"><xsl:copy-of select="$id"/>TextLabel</xsl:attribute>
            </xsl:if>
	        <xsl:copy-of select="$rowName"/>
	    </span>
        <xsl:if test="$required = 'true' and $mode = 'edit'">
            <span id="asterisk_{$id}" class="asterisk">*</span>
		</xsl:if>
    </div>
	<div class="paymentValue">
                <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/> </div>

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
        <!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'edit'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="form&#x2D;data.xml" srcSchemaRoot="form&#x2D;data" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/><SourceSchema srcSchemaPath="validatorsError.xml" srcSchemaRoot="" AssociatedInstance="file:///c:/Flash/v1.18/Business/settings/payments/SBRF/InternalPayment/validatorsError.xml" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->