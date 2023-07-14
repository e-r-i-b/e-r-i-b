<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:imau="java://com.rssl.phizic.business.ima.IMAccountUtils"
                xmlns:mf="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:cu="java://com.rssl.phizic.utils.CurrencyUtils"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil"
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
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="isTemplate" select="'isTemplate'"/>
	<xsl:param name="longOffer" select="false()"/>
	<xsl:param name="personAvailable" select="true()"/>
	<xsl:param name="showCommission" select="'showCommission'"/>
    <xsl:param name="postConfirmCommission" select="postConfirmCommission"/>
    <!--Тарифный план клиента-->
    <xsl:variable name="tarifPlanCodeType">
        <xsl:value-of select="ph:getActivePersonTarifPlanCode()"/>
    </xsl:variable>
    <!--Показывать ли обычный курс для тарифного плана клиента-->
    <xsl:variable name="needShowStandartRate">
        <xsl:value-of select="ph:needShowStandartRate($tarifPlanCodeType)"/>
    </xsl:variable>

	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

	<xsl:template match="/">
		<script type="text/javascript">
            var bundledAcc   = new Array();
            var selectedIMA  = null;

            var toResource   = document.getElementById('toResource');
            var fromResource = document.getElementById('fromResource');

            var imaccount    =
            {
                id: '',
                currency: '',
                createIMAccount : function(id, currency)
                {
                    this.id       = id;
                    this.currency = currency;

                    return this;
                }
            };

            function removeOrPullOptions(currentElement, altElementId)
            {
                <!--Remove options-->
                var selectedOption = currentElement.options[currentElement.selectedIndex];
                if (selectedOption.value.indexOf('im') == -1 &amp;&amp; selectedOption.value != '')
                {
                    var altSelect = document.getElementById(altElementId);

                    for (var i=0; i&lt;altSelect.options.length; i++)
                    {
                        if(altSelect.options[i].value.indexOf('im') == -1 &amp;&amp; altSelect.options[i].value != '')
                        {
                            altSelect.options[i].selected = false;

                            bundledAcc.push(altSelect.options[i]);
                        }
                    }

                    $(altSelect).find('option').each(
                        function()
                        {
                            if($(this).val() != '' &amp;&amp; $(this).val().indexOf('im') == -1)
                            {
                                $(this).remove();
                            }
                        }
                    );
                }
                <!--Pull options-->
                else
                {
                    var altSelect = document.getElementById(altElementId);
                    for(var i=0; i&lt;bundledAcc.length; i++)
                    {
                        var found = false;

                        for(var j=0; j&lt;altSelect.options.length; j++)
                        {
                            found = (altSelect.options[j].value == bundledAcc[i].value);

                            if(found)
                            {
                                break;
                            }
                        }

                        if(!found)
                        {
                            altSelect.appendChild( bundledAcc[i] );
                        }
                    }
                }
            }

            function removeNonIMAccountOnChange(currentElement)
            {
                if (currentElement.id == 'fromResource')
                {
                    removeOrPullOptions(currentElement, 'toResource');
                }
                else
                {
                    removeOrPullOptions(currentElement, 'fromResource');
                }
            }

            function findIMAccountForSelect(id, currency)
            {
                var rewrite  = false;

                switch(currency)
                {
                    case 'AUR':
                    case 'ARG':
                    {
                        rewrite = (selectedIMA == null || selectedIMA.currency != 'AUR');
                        break;
                    }
                    case 'PTR':
                    case 'PDR':
                    {
                        rewrite = (selectedIMA == null || (selectedIMA.currency != 'AUR' &amp;&amp;
                                                           selectedIMA.currency != 'ARG' &amp;&amp;
                                                           selectedIMA.currency != 'PTR'));
                    }
                }

                if(rewrite)
                {
                    selectedIMA = imaccount.createIMAccount(id, currency);
                }
            };

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
                    <!--Для тарифного плана "Сбербанк Премьер" отображаем информационные сообщения-->
                    <xsl:if test="$tarifPlanCodeType = '1'" >
                       var courseRowPaymentsLegend = ensureElement("courseRow-payments-legend");
                        if (courseRowPaymentsLegend == null)
                            return;
                        if ( String(rateShowMsg).toLowerCase() == 'true' &amp;&amp;
                             !(SBR_PRIVELEGED_WARNING_MESSAGE == null || SBR_PRIVELEGED_WARNING_MESSAGE == '') )
                        {   <!--сообщение отображается если:-->
                            <!--1. валюта операции "USD" или "EUR"; -->
                            <!--2. курс отличается от курса с обычным тарифным планом "UNKNOWN";-->
                            <!--3. тарифный план клиента "PREMIER"-->
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
			<xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="view"/>
            </xsl:when>
            <xsl:when test="$mode = 'edit'">
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="resources">
		<xsl:param name="name"/>
		<xsl:param name="accountNumber"/>
		<xsl:param name="linkId"/>
		<xsl:param name="activeAccounts"/>
		<xsl:param name="activeIMAccounts"/>
        <xsl:param name="activeCards"/>
        <xsl:param name="additionFunctions"/>

		<xsl:variable name="defaultFunctions"
                      select="'removeNonIMAccountOnChange(this);showHideOperationTypeRow();fillCurrency();hideOrShowCourse();setPrecision();'"/>
		<select id="{$name}" name="{$name}">
			<xsl:attribute name="onchange">
				<xsl:value-of select="$defaultFunctions"/>
                <xsl:value-of select="$additionFunctions"/>
			</xsl:attribute>
            <xsl:choose>
                <xsl:when test="not($activeIMAccounts) and not($activeAccounts)">
                    <option value="">Нет доступных счетов</option>
                </xsl:when>
                <xsl:when test="string-length($accountNumber) = 0 and string-length($linkId) = 0">
                    <option value="">Выберите счет
                    <xsl:choose>
                        <xsl:when test="$name = 'toResource'"> зачисления</xsl:when>
                        <xsl:otherwise> списания</xsl:otherwise>
                    </xsl:choose>
                    </option>
                </xsl:when>
            </xsl:choose>
            <xsl:choose>
                <xsl:when test="$name = 'toResource'">
                        <xsl:call-template name="activeIMAccounts">
                            <xsl:with-param name="accountNumber" select="$accountNumber"/>
                            <xsl:with-param name="linkId" select="$linkId"/>
                            <xsl:with-param name="activeIMAccounts" select="$activeIMAccounts"/>
                        </xsl:call-template>
                        <xsl:if test="pu:impliesService('IMAPaymentWithAccount')">
                            <xsl:call-template name="activeAccounts">
                                <xsl:with-param name="accountNumber" select="$accountNumber"/>
                                <xsl:with-param name="linkId" select="$linkId"/>
                                <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
                            </xsl:call-template>
                        </xsl:if>
                        <xsl:if test="pu:impliesService('IMAPaymentWithCard')">
                            <xsl:call-template name="activeCards">
                                <xsl:with-param name="accountNumber" select="$accountNumber"/>
                                <xsl:with-param name="linkId" select="$linkId"/>
                                <xsl:with-param name="activeCards" select="$activeCards"/>
                            </xsl:call-template>
                        </xsl:if>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:if test="pu:impliesService('IMAPaymentWithAccount')">
                        <xsl:call-template name="activeAccounts">
                            <xsl:with-param name="accountNumber" select="$accountNumber"/>
                            <xsl:with-param name="linkId" select="$linkId"/>
                            <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
                        </xsl:call-template>
                    </xsl:if>
                        <xsl:call-template name="activeIMAccounts">
                            <xsl:with-param name="accountNumber" select="$accountNumber"/>
                            <xsl:with-param name="linkId" select="$linkId"/>
                            <xsl:with-param name="activeIMAccounts" select="$activeIMAccounts"/>
                        </xsl:call-template>
                        <xsl:if test="pu:impliesService('IMAPaymentWithCard')">
                            <xsl:call-template name="activeCards">
                                <xsl:with-param name="accountNumber" select="$accountNumber"/>
                                <xsl:with-param name="linkId" select="$linkId"/>
                                <xsl:with-param name="activeCards" select="$activeCards"/>
                            </xsl:call-template>
                        </xsl:if>
                </xsl:otherwise>
            </xsl:choose>
        </select>

        <xsl:choose>
            <xsl:when test="$name = 'toResource'">
                <script type="text/javascript">
                    <xsl:for-each select="$activeIMAccounts">
                        var id       = '<xsl:value-of select="field[@name='code']/text()"/>';
                        var currency = '<xsl:value-of select="cu:normalizeCurrencyCode(./field[@name='currencyCode'])"/>';

                        findIMAccountForSelect(id, currency);
                    </xsl:for-each>

                    if (selectedIMA != null)
                    {
                        var select = document.getElementById('toResource');

                        var index       = -1;
                        var hasSelected = false;

                        for (var j=0; j&lt;select.options.length; j++)
                        {
                            if(select.options[j].selected)
                            {
                                hasSelected = true;
                                break;
                            }

                            if(select.options[j].value == selectedIMA.id)
                            {
                                index = j;
                            }
                        }

                        if (index > -1 &amp;&amp; !hasSelected)
                        {
                            select.options[index].selected = true;
                        }
                    }
                </script>
            </xsl:when>
        </xsl:choose>
	</xsl:template>

    <xsl:template name="activeAccounts">
        <xsl:param name="accountNumber"/>
		<xsl:param name="linkId"/>
        <xsl:param name="activeAccounts"/>
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
                        <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,0', 'sbrf')"/>&nbsp;
                    </xsl:if>
                    <xsl:value-of select="mf:getCurrencySign(./field[@name='currencyCode'])"/>
                </option>
            </xsl:for-each>
    </xsl:template>

    <xsl:template name="activeIMAccounts">
        <xsl:param name="accountNumber"/>
		<xsl:param name="linkId"/>
        <xsl:param name="activeIMAccounts"/>
            <xsl:for-each select="$activeIMAccounts">
                <option>
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    <xsl:attribute name="value">
                        <xsl:value-of select="$id"/>
                    </xsl:attribute>
                    <xsl:if test="$accountNumber=./@key or $linkId=$id">
                        <xsl:attribute name="selected"/>
                    </xsl:if>
                    <xsl:value-of select="imau:getFormattedIMAccountNumber(./@key)"/>&nbsp;
                    <xsl:value-of select="./field[@name='name']"/>&nbsp;
                    <xsl:if test="./field[@name='amountDecimal'] != ''">
                        <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,0', 'sbrf')"/>&nbsp;г.&nbsp;
                    </xsl:if>
                    (<xsl:value-of select="cu:normalizeCurrencyCode(./field[@name='currencyCode'])"/>)
                </option>
            </xsl:for-each>
    </xsl:template>

    <xsl:template name="activeCards">
        <xsl:param name="accountNumber"/>
		<xsl:param name="linkId"/>
        <xsl:param name="activeCards"/>
        <xsl:for-each select="$activeCards">
            <option>
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                <xsl:attribute name="value">
                    <xsl:value-of select="$id"/>
                </xsl:attribute>
                <xsl:if test="$accountNumber=./@key or $linkId=$id">
                    <xsl:attribute name="selected"/>
                </xsl:if>                
                <xsl:value-of select="mask:getCutCardNumber(./@key)"/>
                &nbsp;
                [
                    <xsl:value-of select="./field[@name='name']"/>
                ]
                <xsl:if test="./field[@name='amountDecimal'] != ''">
                    <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
                </xsl:if>
                &nbsp;
                <xsl:value-of select="mf:getCurrencySign(./field[@name='currencyCode'])"/>
            </option>
        </xsl:for-each>
    </xsl:template>

	<xsl:template match="/form-data" mode="edit">
		<xsl:variable name="activeDebitAccounts" select="document('stored-active-debit-rur-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeDebitCards" select="document('stored-active-rur-not-credit-cards.xml')/entity-list/*"/>
        <xsl:variable name="activeOrArrestedDebitCards" select="document('stored-active-or-arrested-rur-not-credit-cards.xml')/entity-list/*"/>
		<xsl:variable name="activeCreditAccounts" select="document('stored-active-or-arrested-rur-credit-accounts.xml')/entity-list/*"/>
		<xsl:variable name="activeIMAccounts" select="document('stored-active-imaccounts.xml')/entity-list/*"/>
        <xsl:variable name="activeOrArrestedIMAccounts" select="document('stored-active-or-arrested-imaccounts.xml')/entity-list/*"/>

		<input type="hidden" name="exactAmount" value="{exactAmount}" id="exactAmount"/>
        <input type="hidden" name="valueOfExactAmount" value="{valueOfExactAmount}" id="valueOfExactAmount"/>
        <script type="text/javascript" src="{$resourceRoot}/scripts/conversionPayment.js"/>

		<xsl:if test="$isTemplate != 'true'">
			&nbsp;
			<span class="simpleLink" onclick="javascript:openTemplateList('IMA_PAYMENT');">
				<span class="text-green">выбрать из шаблонов платежей</span>
			</span>
		</xsl:if>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Номер документа:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="hidden" name="documentNumber" value="{documentNumber}"/>
				<b>
					<xsl:value-of select="documentNumber"/>
				</b>
			</xsl:with-param>
			<xsl:with-param name="isAllocate" select="'false'"/>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Дата документа:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <input type="hidden" name="documentDate" value="{documentDate}"/>
				<b>
					<xsl:value-of select="documentDate"/>
				</b>
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
						<xsl:with-param name="activeIMAccounts" select="$activeIMAccounts"/>
                        <xsl:with-param name="activeCards" select="$activeDebitCards"/>
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
						<xsl:with-param name="activeIMAccounts" select="$activeOrArrestedIMAccounts"/>
                        <xsl:with-param name="activeCards" select="$activeOrArrestedDebitCards"/>
                        <xsl:with-param name="additionFunctions" select="'sellCurrency();'"/>
					</xsl:call-template>
				</xsl:if>
				<xsl:if test="not($personAvailable)">
					<select id="toResource" name="toResource" disabled="disabled">
						<option selected="selected">Счет клиента</option>
					</select>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
			<xsl:with-param name="id">sellAmount</xsl:with-param>
			<xsl:with-param name="rowName">Сумма:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="sellAmount" name="sellAmount" type="text" value="{sellAmount}" size="24" class="moneyField"
                       onchange="javascript:sellCurrency();setValueOfExactAmount(this);" onkeyup="javascript:sellCurrency();setValueOfExactAmount(this);"/>&nbsp;
				<span id="sellAmountCurrency"></span>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
			<xsl:with-param name="id">buyAmount</xsl:with-param>
			<xsl:with-param name="rowName">Сумма в валюте зачисления:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="buyAmount" name="buyAmount" type="text" value="{buyAmount}" size="24" class="moneyField"
                       onchange="javascript:buyCurrency();setValueOfExactAmount(this);" onkeyup="javascript:buyCurrency();setValueOfExactAmount(this);"/>&nbsp;
				<span id="buyAmountCurrency"></span>
			</xsl:with-param>
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
            <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
            <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                <!--Поле льготный курс-->
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">courseRow</xsl:with-param>
                    <xsl:with-param name="required">false</xsl:with-param>
                    <xsl:with-param name="description"></xsl:with-param>
                    <xsl:with-param name="rowName">
                        Льготный курс <span id="operationType"></span>:
                    </xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input type="hidden" value="{course}" name="course" id="courseValue"/>
                        <input type="hidden" value="{premierShowMsg}" name="premierShowMsg" id="premierShowMsg"/>
                        <b><span id="courseSpan"></span></b>
                    </xsl:with-param>
                </xsl:call-template>

                <!--Поле обычный курс-->
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">standartCourseRow</xsl:with-param>
                    <xsl:with-param name="required">false</xsl:with-param>
                    <xsl:with-param name="description"></xsl:with-param>
                    <xsl:with-param name="rowName">
                        Обычный курс <span id="standartOperationType"></span>:
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
                    <xsl:with-param name="description"></xsl:with-param>
                    <xsl:with-param name="rowName">Моя выгода:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <span id="courseGainSpan"></span>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <!--В противном случае -->
            <xsl:otherwise>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">courseRow</xsl:with-param>
                    <xsl:with-param name="description"></xsl:with-param>
                    <xsl:with-param name="rowName">Курс покупки/продажи:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input type="hidden" value="{course}" name="course" id="courseValue"/>
                        <input type="hidden" value="{premierShowMsg}" name="premierShowMsg" id="premierShowMsg"/>
                        <span id="courseSpan"></span>
                        <xsl:if test="$tarifPlanCodeType = '1'">
                            <div class="payments-legend" id="courseRow-payments-legend" style="display: none">
                                <div class="italic">Используется льготный курс покупки/продажи</div>
                            </div>
                        </xsl:if>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>

        <script type="text/javascript">
            function hideOrShowCourse()
            {
                showOrHidePremierWarning(false);
                var courseRow    = ensureElement('courceRow');
                var standartCourseRow = ensureElement('standartCourseRow');
                var courseGainRow    = ensureElement('courseGainRow');
                var toResource   = getElementValue('toResource');
                var fromResource = getElementValue('fromResource');

                if (!(isEmpty(toResource) || isEmpty(fromResource)))
                {
                    var toResourceCurrency   = currencies[toResource];
                    var fromResourceCurrency = currencies[fromResource];

                    if (toResourceCurrency != fromResourceCurrency)
                    {
                        var rate;
                        var rateShowMsg;
                        var standartRate;
                        if (toResourceCurrency == "RUB")
                        {
                            rate = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY"];
                            rateShowMsg = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY_SHOW_MSG"];
                            standartRate = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY_0"];
                        }
                        else if(fromResourceCurrency == "RUB")
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

                            hideOrShow(courseGainRow, false);
                            isCourseGainHide(standartRate, rate);
                        }
                            else
                        {
                            hideOrShow(ensureElement("courseRow"),   true);
                            hideOrShow(ensureElement("standartCourseRow"),   true);
                            hideOrShow(ensureElement("courseGainRow"),   true);
                        }
                    }
                }
            }

       		function showHideOperationTypeRow()
       		{
                var fromResource    = getElementValue("fromResource");
                var toResource      = getElementValue("toResource");
                var courseRow       = ensureElement("courseRow");
                var standartCourseRow = ensureElement("standartCourseRow");
                var courseGainRow   = ensureElement("courseGainRow");
                var buyAmountRow    = ensureElement("buyAmountRow");
                var sellAmountRow   = ensureElement("sellAmountRow");
                var sellAmountLabel = ensureElement("sellAmountTextLabel");

                hideOrShow(buyAmountRow,   true);
                hideOrShow(sellAmountRow, false);
                sellAmountLabel.innerHTML   = "Сумма:";
                hideOrShow(courseRow,   true);
                hideOrShow(standartCourseRow,   true);
                hideOrShow(courseGainRow,   true);
                $("#sellAmountCurrency").text('');
                $("#buyAmountCurrency").text('');

                // очищаем конверсию, т.к нужная установится в hideOrShowCourse
                ensureElement("courseValue").value = "";

                var standartCourseElement = ensureElement("standartCourseValue");
                if (standartCourseElement != null &amp;&amp; standartCourseElement != undefined)
                    standartCourseElement.value = "";

                ensureElement("premierShowMsg").value = "";

                if (!(isEmpty(toResource) || isEmpty(fromResource)))
                {
                    var isIMAFrom = isIMAccount(fromResource);
                    var isIMATo   = isIMAccount(toResource);

                    var fromCurrency = currencies[fromResource];
                    var toCurrency   = currencies[toResource];

                    $("#sellAmountCurrency").text(isIMAFrom ? 'г' : currencySignMap.get(fromCurrency));
                    $("#buyAmountCurrency").text(isIMATo ? 'г' : currencySignMap.get(toCurrency));

                    if (fromCurrency != toCurrency)//для неравных валют 2 суммы
                    {
                        hideOrShow(buyAmountRow,  false);
                        hideOrShow(sellAmountRow, false);
                        hideOrShow(courseRow,     false);
                        hideOrShow(standartCourseRow, false);
                        hideOrShow(courseGainRow, false);
                        if (isIMAFrom)
                        {
                            sellAmountLabel.innerHTML = "Масса:";
                        }
                        else
                        {
                            sellAmountLabel.innerHTML = "Сумма в валюте списания:";
                        }
                    }
                    else //валюты равны ->1 сумма (списания)
                    {
                        hideOrShow(buyAmountRow,  true);
                        hideOrShow(sellAmountRow, false);
                        if (isIMAFrom)
                        {
                            sellAmountLabel.innerHTML = "Масса:";
                        }
                        else
                        {
                            sellAmountLabel.innerHTML = "Сумма:";
                        }
                    }
                    if (isIMATo)
                    {
                        ensureElement("buyAmountTextLabel").innerHTML = "Масса:";
                    }
                    else
                    {
                        ensureElement("buyAmountTextLabel").innerHTML = "Сумма в валюте зачисления:";
                    }
                }
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
                    if(toResource.indexOf('im-account:') != -1)
                    {
                        paymentType = "AcctToIMA";
                        if(paymentType!=null &amp;&amp; (currencies[fromResource] == 'RUB' || currencies[fromResource] == 'RUR'))
                            paymentType = 'Rur' + paymentType;
                    }
                }
                var supportedCommissionPaymentTypes = "<xsl:value-of select="dh:getSupportedCommissionPaymentTypes()"/>".split('|');
                hideOrShow(ensureElement("postConfirmCommissionMessage"), !(supportedCommissionPaymentTypes.contains(paymentType)));
            }

       		function buyCurrency()
       		{
                var toResource   = getElementValue("toResource");
                var toResourceCurrency   = currencies[toResource];
                var fromResource = getElementValue("fromResource");
                var fromResourceCurrency = currencies[fromResource];

                buyCurrencyCommon(fromResourceCurrency, toResourceCurrency, ensureElement("buyAmount"),
                    ensureElement("sellAmount"), toResource != "" &amp;&amp; fromResource != "");

                setOperationType(toResource);
       		}

       		function sellCurrency()
       		{
                var toResource   = getElementValue("toResource");
                var fromResource = getElementValue("fromResource");

                sellCurrencyCommon(currencies[fromResource], currencies[toResource], ensureElement("buyAmount"),
                    ensureElement("sellAmount"), toResource != "" &amp;&amp; fromResource != "");

                setOperationType(toResource);
       		}

            function getBuyAmount(amount, rate)
            {
                return getAmountByResource(amount, rate, getElementValue("toResource"));
            }

            function getSellAmount(amount, rate)
            {
                return getAmountByResource(amount, rate, getElementValue("fromResource"));
            }
            function getAmountByResource(amount, rate, resource)
            {
                return !isIMAccount(resource) ? getAmount(amount, rate) : getWeight(amount, rate, resource);
            }
            <!--определение типа операции покупка или продажа-->
            function setOperationType(toResource)
            {
                var operType = isIMAccount(toResource) ? "покупки" : "продажи";
                $("#operationType").text(operType);
                $("#standartOperationType").text(operType);
            }

            function isIMAccount(resource)
            {
                return resource.indexOf('im-account') != -1;
            }

            function showCourse(fromCurrency, toCurrency, rate, standartRate, rateShowMsg)
       		{
                $("#courseSpan").html(rate + " " + normalizeCurrency(fromCurrency) + "<i> &#8594; </i>" + normalizeCurrency(toCurrency));
                $("#standartCourseSpan").html(standartRate + " " + normalizeCurrency(fromCurrency) + " &#8594; " + normalizeCurrency(toCurrency));
                setCourseGain(standartRate, rate);

                ensureElement("courseValue").value = rate;
                ensureElement("premierShowMsg").value = rateShowMsg;

                var standartCourseElement = ensureElement("standartCourseValue");
                if (standartCourseElement != null &amp;&amp; standartCourseElement != undefined)
                    standartCourseElement.value = standartRate;

                showOrHidePremierWarning(rateShowMsg);
       		}

            function normalizeCurrency(currency)
            {
                if (!isEmpty(currency))
                {
                    switch (currency)
                    {
                        case 'A33': return 'PDR';
                        case 'A76': return 'PTR';
                        case 'A98': return 'AUR';
                        case 'A99': return 'ARG';
                        default: return currencySignMap.get(currency);
                    }
                }
                return currency;
            }

            <![CDATA[
            //Сумма покупки/продажи металла
       		function getAmount(amount, rate)
       		{
                //Для продажи металла берем обратный курс
       		    if (amount != '' && !isNaN(amount))
       		        return parseFloat((parseFloat(amount) * rate).toFixed(4)).toFixed(2);
       		    return "";
       		}
            //Вес металла
       		function getWeight(amount, rate, resource)
       		{
       		    if (amount != '' && !isNaN(amount))
       		    {
                    //При продаже металла для расчета его массы всегда берем обратный курс
                    if ( currencies[resource] != 'RUB' )
                        rate = 1 / rate;
       		        var result = (parseFloat(amount) * rate);
       		        if ( currencies[resource] == 'A99' )
       		        {
       		            return result.toFixed(0);
       		        }
       		        return (parseFloat(amount) * rate).toFixed(1);
                }
       		    return "";
       		}
            ]]>

            var rates       = new Array();
            var accounts    = new Array();
            var currencies  = new Array();

            function init()
            {
                var USING_STORED_CARDS_RESOURCE_MESSAGE    = 'Информация по Вашим картам может быть неактуальной.';
                var USING_STORED_ACCOUNTS_RESOURCE_MESSAGE = 'Информация по Вашим счетам может быть неактуальной.';
                var countAcc = 0;
                var countNotEmptyAcc = 0;
                var indexNotEmptyAcc = 0;
                <xsl:if test="pu:impliesService('IMAPaymentWithAccount')">
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
                </xsl:if>
                <xsl:for-each select="$activeIMAccounts">
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    currencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                    accounts['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="@key"/>';
                    <xsl:if test="field[@name='isUseStoredResource'] = 'true'">
                        addMessage(USING_STORED_ACCOUNTS_RESOURCE_MESSAGE);
                    </xsl:if>
                    countAcc++;
                     <xsl:if test="field[@name='amountDecimal'] > 0 ">
                        countNotEmptyAcc ++;
                        indexNotEmptyAcc = countAcc;
                    </xsl:if>
                </xsl:for-each>
                <xsl:if test="pu:impliesService('IMAPaymentWithCard')">
                    <xsl:for-each select="$activeDebitCards">
                        <xsl:variable name="id" select="field[@name='code']/text()"/>
                        currencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                        accounts['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="@key"/>';
                        <xsl:if test="field[@name='isUseStoredResource'] = 'true' ">
                            addMessage(USING_STORED_CARDS_RESOURCE_MESSAGE);
                        </xsl:if>
                        countAcc++;
                        <xsl:if test="field[@name='amountDecimal'] > 0 ">
                            countNotEmptyAcc ++;
                            indexNotEmptyAcc = countAcc;
                        </xsl:if>
                    </xsl:for-each>
                </xsl:if>

                <xsl:for-each select="document('account-ima-currency.xml')/entity-list/entity">
                    rate = new Array()
                    <xsl:for-each select="*">
                        rate['<xsl:value-of select="@name"/>'] = '<xsl:value-of select="./text()"/>';
                    </xsl:for-each>
                    rates['<xsl:value-of select="@key"/>'] = rate;
                </xsl:for-each>

                showHideOperationTypeRow();
                hideOrShowCourse();
                fillCurrency();
                selectDefaultFromResource(countNotEmptyAcc, indexNotEmptyAcc);
            }

            function setPrecision()
            {
                var toResource   = getElementValue("toResource");
                var fromResource = getElementValue("fromResource");

                var toResourceCurrency = currencies[toResource];
                var fromResourceCurrency = currencies[fromResource];

                if(fromResourceCurrency == 'A99')
                    $(ensureElement('sellAmount')).setPrecision(0);
                else if(fromResourceCurrency == 'A98' || fromResourceCurrency == 'A76' || fromResourceCurrency == 'A33')
                    $(ensureElement('sellAmount')).setPrecision(1);
                else
                    $(ensureElement('sellAmount')).setPrecision(2);

                if(toResourceCurrency == 'A99')
                    $(ensureElement('buyAmount')).setPrecision(0);
                else if(toResourceCurrency == 'A98' || toResourceCurrency == 'A76' || toResourceCurrency == 'A33')
                    $(ensureElement('buyAmount')).setPrecision(1);
                else
                    $(ensureElement('buyAmount')).setPrecision(2);
            }

            function fillCurrency()
            {
                var exactAmount = ensureElement("exactAmount");
                var valueOfExactAmount = ensureElement("valueOfExactAmount");
                if (!isEmpty(exactAmount))
                {
                    if (exactAmount.value == "charge-off-field-exact")
                    {
                        if (!isEmpty(valueOfExactAmount))
                        {
                            $(ensureElement('sellAmount')).setMoneyValue(valueOfExactAmount.value);
                        }
                        sellCurrency();
                        buyCurrency();
                        exactAmount.value = "charge-off-field-exact";
                    }
                    if (exactAmount.value == "destination-field-exact")
                    {
                        if (!isEmpty(valueOfExactAmount))
                        {
                            $(ensureElement('buyAmount')).setMoneyValue(valueOfExactAmount.value);
                        }
                        buyCurrency();
                        sellCurrency();
                        exactAmount.value = "destination-field-exact";
                    }
                }
            }

            function setValueOfExactAmount(amount)
            {
                ensureElement("valueOfExactAmount").value = amount.value;
            }

            $(document).ready(init);

        </script>
	</xsl:template>

    <xsl:template match="/form-data" mode="view">
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
                            <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                                <xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/>
                                &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                                <xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                            </xsl:when>

                            <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                                &nbsp;<xsl:value-of select="fromAccountName"/>&nbsp;
                                (<xsl:value-of select="cu:normalizeCurrencyCode(fromResourceCurrency)"/>)
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="imau:getFormattedIMAccountNumber(fromAccountSelect)"/>&nbsp;
                                <xsl:value-of select="fromAccountName"/>
					            (<xsl:value-of select="cu:normalizeCurrencyCode(fromResourceCurrency)"/>)
                            </xsl:otherwise>
                        </xsl:choose>
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
                            <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                                <xsl:value-of select="au:getFormattedAccountNumber(toAccountSelect)"/>
                                &nbsp;[<xsl:value-of select="toAccountName"/>]&nbsp;
                                <xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/>
                            </xsl:when>
                            <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                <xsl:value-of select="mask:getCutCardNumber(toAccountSelect)"/>
                                &nbsp;<xsl:value-of select="toAccountName"/>&nbsp;
                                (<xsl:value-of select="cu:normalizeCurrencyCode(toResourceCurrency)"/>)
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="imau:getFormattedIMAccountNumber(toAccountSelect)"/>&nbsp;
                                <xsl:value-of select="toAccountName"/>
					            (<xsl:value-of select="cu:normalizeCurrencyCode(toResourceCurrency)"/>)
                            </xsl:otherwise>
                        </xsl:choose>
                    </b>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

		<xsl:variable name="isConversion" select="string-length(sellAmount)>0 and string-length(buyAmount)>0"/>

        <xsl:if test="courseChanged = 'true' and state = 'SAVED'">
              <script type="text/javascript">
                  doOnLoad(function()
                  {
                    removeError('Курс покупки/продажи металла изменился. Вам необходимо создать новый платеж. ');
                    addError('Обратите внимание! Изменился курс конверсии.');
                    payInput.fieldError('confirmCourse');
                  });
              </script>
        </xsl:if>

        <xsl:variable name="isChargeOff" select="exactAmount = 'charge-off-field-exact' or exactAmount != 'destination-field-exact'"/>

        <xsl:if test="(($isChargeOff and number(valueOfExactAmount) != number(sellAmount)) or (not($isChargeOff) and number(valueOfExactAmount) != number(buyAmount))) and state = 'SAVED'">
              <script type="text/javascript">
                  doOnLoad(function()
                  {
                    addMessage('Обратите внимание! Введенная Вами сумма была пересчитана в соответствии с курсом конверсии.');
                  });
              </script>
        </xsl:if>

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
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:if test="$isChargeOff or $isConversion">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
                        <xsl:with-param name="rowName">
                            <xsl:choose>
                                <xsl:when test="$isConversion and fromResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                                    Сумма в валюте списания:
                                </xsl:when>
                                <xsl:when test="$isConversion and fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                    Сумма в валюте списания:
                                </xsl:when>
                                <xsl:when test="$isConversion and fromResourceType='com.rssl.phizic.business.resources.external.IMAccountLink'">
                                    Масса:
                                </xsl:when>
                                <xsl:otherwise>
                                    Сумма:
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:if test="string-length(sellAmount)>0">
                                <span>
                                    <xsl:attribute name="class">
                                        summ <xsl:if test="exactAmount = 'destination-field-exact' and courseChanged = 'true'">red</xsl:if>
                                    </xsl:attribute>
                                    <xsl:choose>
                                        <xsl:when test="$isConversion and fromResourceType='com.rssl.phizic.business.resources.external.IMAccountLink'">
                                            <xsl:value-of select="format-number(sellAmount, '### ##0,0', 'sbrf')"/>&nbsp;
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of select="format-number(sellAmount, '### ##0,00', 'sbrf')"/>&nbsp;
                                        </xsl:otherwise>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                                            <xsl:value-of select="mf:getCurrencySign(sellAmountCurrency)"/>
                                        </xsl:when>
                                        <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                            <xsl:value-of select="mf:getCurrencySign(sellAmountCurrency)"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:text>г.</xsl:text>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </span>
                            </xsl:if>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:variable name="isDestination" select="exactAmount = 'destination-field-exact'"/>
                <xsl:if test="$isDestination or $isConversion">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
                        <xsl:with-param name="rowName">
                            <xsl:choose>
                                <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                                    Сумма в валюте зачисления:
                                </xsl:when>
                                <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                    Сумма в валюте зачисления:
                                </xsl:when>
                                <xsl:otherwise>
                                    Масса:
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:if test="string-length(buyAmount)>0">
                                <span>
                                    <xsl:attribute name="class">
                                         summ <xsl:if test="exactAmount = 'charge-off-field-exact' and courseChanged = 'true'">red</xsl:if>
                                    </xsl:attribute>
                                    <xsl:choose>
                                        <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                                            <xsl:value-of select="format-number(buyAmount, '### ##0,00', 'sbrf')"/>&nbsp;
                                        </xsl:when>
                                        <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                            <xsl:value-of select="format-number(buyAmount, '### ##0,00', 'sbrf')"/>&nbsp;
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of select="format-number(buyAmount, '### ##0,0', 'sbrf')"/>&nbsp;
                                        </xsl:otherwise>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                                            <xsl:value-of select="mf:getCurrencySign(buyAmountCurrency)"/>
                                        </xsl:when>
                                        <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                            <xsl:value-of select="mf:getCurrencySign(buyAmountCurrency)"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:text>г.</xsl:text>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </span>
                            </xsl:if>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:otherwise>
        </xsl:choose>

        <!--Нормализованное представление валюты счета списания-->
        <xsl:variable name="fromResourceCurrency">
            <xsl:choose>
                <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                    <xsl:value-of select="mf:getCurrencySign(sellAmountCurrency)"/>
                </xsl:when>
                <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                    <xsl:value-of select="mf:getCurrencySign(sellAmountCurrency)"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="cu:normalizeCurrencyCode(sellAmountCurrency)"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <!--Нормализованное представление валюты счета зачисления-->
        <xsl:variable name="toResourceCurrency">
            <xsl:choose>
                <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                    <xsl:value-of select="mf:getCurrencySign(buyAmountCurrency)"/>
                </xsl:when>
                <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                    <xsl:value-of select="mf:getCurrencySign(buyAmountCurrency)"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="cu:normalizeCurrencyCode(buyAmountCurrency)"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <!--Тип операции (покупка/продажа)-->
        <xsl:variable name="operationType">
            <xsl:choose>
                <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.IMAccountLink'">
                    продажи:
                </xsl:when>
                <xsl:otherwise>
                    покупки:
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:choose>
            <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
            <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                <!--Поле льготный курс-->
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Льготный курс&nbsp;<xsl:value-of select="$operationType"/></xsl:with-param>
                    <xsl:with-param name="id">confirmCourse</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input type="hidden" name="confirmCourse"/>
                        <b><xsl:value-of select="format-number(course, '### ##0,00##', 'sbrf')"/></b>
                        &nbsp;
                        <span  id="courseSpan">
                            <b><xsl:value-of select="$fromResourceCurrency"/></b>
                            &#8594;
                            <b><xsl:value-of select="$toResourceCurrency"/></b>
                        </span>
                    </xsl:with-param>
                </xsl:call-template>
                <!--Поле обычный курс-->
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Обычный курс&nbsp;<xsl:value-of select="$operationType"/></xsl:with-param>
                    <xsl:with-param name="id">confirmStandartCourse</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <span class="crossed">
                            <xsl:value-of select="format-number(standartCourse, '### ##0,00##', 'sbrf')"/>
                            &nbsp;
                            <span  id="standartCourseSpan">
                                <xsl:value-of select="$fromResourceCurrency"/>
                                &#8594;
                                <xsl:value-of select="$toResourceCurrency"/>
                            </span>
                        </span>
                    </xsl:with-param>
                </xsl:call-template>

                <!--Моя выгода-->
                <xsl:if test="string-length(sellAmount) > 0">
                    <!--Величина моей выгоды-->
                    <xsl:variable name="courseGainValue">
                        <xsl:choose>
                            <xsl:when test="fromResourceCurrency = 'RUB'">
                                <xsl:value-of select="mu:roundDestinationAmount((1 div course - 1 div standartCourse) * sellAmount)"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="mu:roundDestinationAmount((course - standartCourse) * sellAmount)"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>

                    <!--Показывать поле "Моя выгода" только при положительной выгоде-->
                    <xsl:if test="$courseGainValue > 0">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="id">courseGain</xsl:with-param>
                            <xsl:with-param name="rowName">Моя выгода:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <xsl:value-of select="$courseGainValue"/>
                                &nbsp;<xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:if>
        	</xsl:when>
            <!--В противном случае -->
            <xsl:otherwise>
                <xsl:if test="$isConversion and string-length(course)>0">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Курс покупки/продажи:</xsl:with-param>
                        <xsl:with-param name="id">confirmCourse</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="hidden" name="confirmCourse"/>
                                <xsl:value-of select="format-number(course, '### ##0,00##', 'sbrf')"/>&nbsp;
                                <span  id="courseSpan">
                                    <b><xsl:value-of select="$fromResourceCurrency"/></b>
                                    &#8594;
                                    <b><xsl:value-of select="$toResourceCurrency"/></b>
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

        <xsl:if test="$showCommission = 'true' and $isTemplate!='true'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Комиссия:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:if test="string-length(commission)>0">
                        <xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mf:getCurrencySign(commissionCurrency)"/>
                    </xsl:if>
                </xsl:with-param>
            </xsl:call-template>
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
                           onmouseout="hideLayer('stateDescription');" style="text-decoration:underline" class="link">
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

        <script type="text/javascript">
            doOnLoad(function()
            {
                showOrHidePremierWarning(<xsl:value-of select="premierShowMsg"/>);
            });
        </script>
    </xsl:template>

    <xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
			<xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">Ожидает дополнительной обработки (статус для клиента: "Подтвердите в контактном центре")</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
			<xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
			<xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='TEMPLATE'">Активный</xsl:when>
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
            <xsl:when test="$code='WAIT_CONFIRM'">Подтвердите в контактном центре</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
            <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='TEMPLATE'">Активный</xsl:when>
            <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
		</xsl:choose>
	</xsl:template>

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
            <![CDATA[</div>]]>
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
						<xsl:copy/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>

			<xsl:if test="count($nodeText/cut) &gt; 0">
				<xsl:value-of select="$end" disable-output-escaping="yes"/>
			</xsl:if>
		</div>
	</xsl:template>

	<xsl:template name="standartRow">

		<xsl:param name="id"/>
		<xsl:param name="lineId"/>
        <!--идентификатор строки-->
        <xsl:param name="required" select="'true'"/>
        <!--параметр обязатьльности заполнения-->
        <xsl:param name="rowName"/>
        <!--описание поля-->
        <xsl:param name="rowValue"/>
        <!--данные-->
        <xsl:param name="description"/>
        <!-- Описание поля, данные после <cut />  будут прятаться под ссылку подробнее -->
        <xsl:param name="rowStyle"/>
        <!-- Стиль поля -->
        <xsl:param name="isAllocate" select="'true'"/>
        <!-- Выделить при нажатии -->
        <!-- Необязательный параметр -->
        <xsl:param name="fieldName"/>
        <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->

        <xsl:variable name="nodeRowValue" select="xalan:nodeset($rowValue)"/>
        <!-- Определение имени инпута или селекта передаваемого в шаблон -->
        <!-- inputName - fieldName или имя поле вытащеное из rowValue -->
        <xsl:variable name="inputName">
			<xsl:choose>
				<xsl:when test="string-length($fieldName) = 0">
					<xsl:if test="(count($nodeRowValue/input[@name]) + count($nodeRowValue/select[@name]) + count($nodeRowValue/textarea[@name])) = 1">
						<xsl:value-of select="$nodeRowValue/input/@name"/>
						<xsl:if test="count($nodeRowValue/select[@name]) = 1">
							<xsl:value-of select="$nodeRowValue/select/@name"/>
						</xsl:if>
						<xsl:if test="count($nodeRowValue/textarea[@name]) = 1">
							<xsl:value-of select="$nodeRowValue/textarea/@name"/>
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
				<xsl:when test="string-length($inputName)&gt;0">
					<xsl:value-of
                            select="count($nodeRowValue/input[@name=$inputName]/@readonly ) + count($nodeRowValue/select[@name=$inputName]/@readonly )+ count($nodeRowValue/textarea[@name=$inputName]/@readonly )"/>
				</xsl:when>
				<xsl:otherwise>0</xsl:otherwise>
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
			<xsl:if test="string-length($lineId) &gt; 0">
				<xsl:attribute name="id">
					<xsl:copy-of select="$lineId"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="string-length($rowStyle) &gt; 0">
				<xsl:attribute name="style">
					<xsl:copy-of select="$rowStyle"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="$readonly = 0 and $mode = 'edit' and $isAllocate='true'">
				<xsl:attribute name="onclick">payInput.onClick(this)</xsl:attribute>
			</xsl:if>

			<div class="paymentLabel">
				<span class="paymentTextLabel">
					<xsl:if test="string-length($id) &gt; 0">
						<xsl:attribute name="id">
							<xsl:copy-of select="$id"/>TextLabel</xsl:attribute>
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
        <xsl:if test="string-length($inputName) &gt; 0 and $readonly = 0 and $mode = 'edit'">
			<script type="text/javascript">if (document.getElementsByName('<xsl:value-of select="$inputName"/>').length == 1)
		    document.getElementsByName('<xsl:value-of select="$inputName"/>')[0].onfocus = function() { payInput.onFocus(this); };</script>
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
