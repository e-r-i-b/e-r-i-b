<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mf="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:cu="java://com.rssl.phizic.utils.CurrencyUtils"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:mu="java://com.rssl.phizic.business.util.MoneyUtil"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:sh="java://com.rssl.phizic.utils.StringHelper"
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

            <!--Выгода от льготного курса-->
            function getGainValue(cource1, cource2)
            {
                var diff = parseFloat(cource1) - parseFloat(cource2);
                return String(diff.toFixed(2));
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
        <xsl:param name="activeCards"/>

		<select id="{$name}" name="{$name}" onChange="changeOffice(this.value);">
            <xsl:choose>
                <xsl:when test="not($activeAccounts) and not($activeCards)">
                    <option value="">Нет доступных счетов/карт</option>
                </xsl:when>
                <xsl:when test="$linkId = ''">
                    <option value="">Выберите счет/карту списания</option>
                </xsl:when>
            </xsl:choose>
            <xsl:if test="pu:impliesService('IMAOpeningFromAccountClaim')">
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
                        <xsl:value-of
                             select="format-number(./field[@name='amountDecimal'], '### ##0,0', 'sbrf')"/>&nbsp;
                        <xsl:value-of select="mf:getCurrencySign(./field[@name='currencyCode'])"/>
                    </option>
                    <script type="text/javascript">
                        offices['<xsl:value-of select="field[@name='code']/text()"/>'] = {
                            'name' : '<xsl:value-of select="./field[@name='officeName']"/>',
                            'address' : '<xsl:value-of select="./field[@name='officeAddress']"/>',
                            'region' : '<xsl:value-of select="./field[@name='officeRegionCode']"/>',
                            'branch' : '<xsl:value-of select="./field[@name='officeBranchCode']"/>',
                            'office' : '<xsl:value-of select="./field[@name='officeOfficeCode']"/>',
                            'isImaOpening' : '<xsl:value-of select="./field[@name='isImaOpening']"/>',
                            'parentSynchKey' : '<xsl:value-of select="./field[@name='officeSynchKey']"/>'
                        };
                    </script>
                </xsl:for-each>
            </xsl:if>
            <xsl:if test="pu:impliesService('IMAOpeningFromCardClaim')">
                <xsl:for-each select="$activeCards">
                    <xsl:if test="field[@name='isImaOpening'] != '0' or field[@name='officeSynchKey'] != ''">
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
                        <script type="text/javascript">
                            offices['<xsl:value-of select="field[@name='code']/text()"/>'] = {
                                'name' : '<xsl:value-of select="./field[@name='officeName']"/>',
                                'address' : '<xsl:value-of select="./field[@name='officeAddress']"/>',
                                'region' : '<xsl:value-of select="./field[@name='officeRegionCode']"/>',
                                'branch' : '<xsl:value-of select="./field[@name='officeBranchCode']"/>',
                                'office' : '<xsl:value-of select="./field[@name='officeOfficeCode']"/>',
                                'isImaOpening' : '<xsl:value-of select="./field[@name='isImaOpening']"/>',
                                'parentSynchKey' : '<xsl:value-of select="./field[@name='officeSynchKey']"/>'
                            };

                            <xsl:if test="field[@name='isUseStoredResource'] = 'true' ">
                                addMessage('Информация по Вашим картам может быть неактуальной.');
                            </xsl:if>
                        </script>
                    </xsl:if>
                </xsl:for-each>
            </xsl:if>
		</select>
	</xsl:template>

	<xsl:template match="/form-data" mode="edit">
       <script type="text/javascript">
           var offices = {};
           var officeIMAName = '<xsl:value-of select="sh:formatStringForJavaScript(officeName, true())"/>';
           var officeIMAAddress = '<xsl:value-of select="sh:formatStringForJavaScript(officeAddress, true())"/>';
           var officeRegion =   '<xsl:value-of select="sh:formatStringForJavaScript(officeRegion, true())"/>';
           var officeBranch =   '<xsl:value-of select="sh:formatStringForJavaScript(officeBranch, true())"/>';
           var officeVsp =   '<xsl:value-of select="sh:formatStringForJavaScript(office, true())"/>';
           var isImaOpening = '0';
           var parentSynchKey = '';

           function clearOffice()
           {
              setElement('officeAddress', "");
              setElement('officeName', "");
              setElement('officeRegion', "");
              setElement('officeBranch', "");
              setElement('office', "");
              isImaOpening = '0';
              document.getElementById("office-span").innerHTML = document.getElementById("office-link").innerHTML = "Для определения места открытия ОМС укажите счет списания.";

              officeIMAName =  officeIMAAddress = officeRegion = officeBranch = officeVsp = parentSynchKey = "";
           }

           function changeOffice(office)
           {

                clearOffice();
                if (office == '' || offices[office] == undefined || offices[office]['isImaOpening'] == '')
                {
                    $("#office-link").hide();
                    $("#office-span").show();
                    return;
                }

                officeIMAName = offices[office]['name'];
                officeIMAAddress = offices[office]['address'];
                officeRegion = offices[office]['region'];
                officeBranch = offices[office]['branch'];
                officeVsp = offices[office]['office'];
                isImaOpening = offices[office]['isImaOpening'];
                parentSynchKey = offices[office]['parentSynchKey'];

                if (isImaOpening == '1')
                {
                    // деактивируем ссылку
                    $("#office-link").hide();
                    $("#office-span").show();
                    setOfficeInfo(offices[office]);
                }
                else
                {
                    // активируем ссылку
                    $("#office-link").show();
                    $("#office-span").hide();
                }

           }

           function showDepartmetsList()
           {
	            var width = 820;
                var height = 900;
                var win  =  window.open("/PhizIC/private/ima/office/list.do?parentSynchKey=" + parentSynchKey, 'Departments', "resizable=1,menubar=0,toolbar=0,scrollbars=1, width="+width+", height="+height);
	            win.moveTo((screen.width - width)/2, (screen.height - height)/2);
                return false;
           };

           function setOfficeInfo(info)
           {
               setElement('officeAddress', info["address"]);
               setElement('officeName', info["name"]);
               setElement('officeRegion', info["region"]);
               setElement('officeBranch', info["branch"]);
               setElement('office', info["office"]);
               document.getElementById("office-link").innerHTML = document.getElementById("office-span").innerHTML = info["name"] + " " + info["address"];

           };
       </script>

        <input type="hidden" name="pfpId" value="{pfpId}" id="pfpId"/>
        <input type="hidden" name="pfpPortfolioId" value="{pfpPortfolioId}" id="pfpPortfolioId"/>
        
		<xsl:variable name="activeDebitAccounts" select="document('active-rur-debit-accounts.xml')/entity-list/*"/>
		<xsl:variable name="activeDebitCards" select="document('active-rur-not-credit-cards-with-corrected-offices.xml')/entity-list/*"/>
        <input type="hidden" name="exactAmount" value="{exactAmount}" id="exactAmount"/>
        <xsl:choose>
            <xsl:when test="exactAmount = 'charge-off-field-exact' and sellAmount != ''"><input type="hidden" name="valueOfExactAmount" value="{sellAmount}" id="valueOfExactAmount"/></xsl:when>
            <xsl:when test="exactAmount = 'destination-field-exact' and buyAmount != ''"><input type="hidden" name="valueOfExactAmount" value="{buyAmount}" id="valueOfExactAmount"/></xsl:when>
            <xsl:otherwise><input type="hidden" name="valueOfExactAmount" value="{valueOfExactAmount}" id="valueOfExactAmount"/></xsl:otherwise>
        </xsl:choose>


        <input type="hidden" name="toResourceCurrency" value="{buyCurrency}"/>

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
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Дата открытия:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <input type="hidden" name="openingDate" value="{openingDate}"/>
				<b>
					<xsl:value-of select="openingDate"/>
				</b>
			</xsl:with-param>
			<xsl:with-param name="isAllocate" select="'false'"/>
		</xsl:call-template>

       <!--Информация об операции-->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Информация об операции</b></xsl:with-param>
        </xsl:call-template>

		<xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="lineId">buyCurrencyRow</xsl:with-param>
			<xsl:with-param name="id">buyCurrency</xsl:with-param>
			<xsl:with-param name="rowName">Металл:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<b><xsl:value-of select="buyIMAProduct"/></b>
                <input type="hidden" id="buyIMAProduct" name="buyIMAProduct" value="{buyIMAProduct}"/>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:choose>
            <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
            <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                <!--Поле льготный курс-->
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">courseRow</xsl:with-param>
                    <xsl:with-param name="required">false</xsl:with-param>
                    <xsl:with-param name="id">course</xsl:with-param>
                    <xsl:with-param name="description"></xsl:with-param>
                    <xsl:with-param name="rowName">Льготный курс покупки:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b>
                            1.0 <xsl:value-of select="mf:getCurrencySign(buyCurrency)"/>&nbsp;
                            <span id="buyCurrency">
                                <xsl:value-of select="cu:normalizeMetalCode(buyCurrency)"/>
                            </span></b>
                            &#8594;
                        <b>
                            <span id="course">
                                <xsl:value-of select="format-number(course, '### ##0,00', 'sbrf')"/>
                            </span>&nbsp;
                            <xsl:value-of select="mf:getCurrencySign(sellCurrency)"/>
                        </b>
                        <input type="hidden" id="courseFullVal" name="courseVal" value="{course}"/>
                        <input type="hidden" id="buyCurrency" name="buyCurrency" value="{buyCurrency}"/>
                        <input type="hidden" id="course" name="course" value="{course}"/>
                        <input type="hidden" id="sellCurrency" name="sellCurrency" value="{sellCurrency}"/>
                        <input type="hidden" value="{sh:formatStringForJavaScript(premierShowMsg, false())}" name="premierShowMsg" id="premierShowMsg"/>
                    </xsl:with-param>
                </xsl:call-template>

                <!--Поле обычный курс-->
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">standartCourseRow</xsl:with-param>
                    <xsl:with-param name="required">false</xsl:with-param>
                    <xsl:with-param name="description"></xsl:with-param>
                    <xsl:with-param name="rowName">Обычный курс покупки:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input type="hidden" value="{standartCourse}" name="standartCourse" id="standartCourseValue"/>
                        <span class="crossed">
                            1.0 <xsl:value-of select="mf:getCurrencySign(buyCurrency)"/>&nbsp;
                            <xsl:value-of select="cu:normalizeMetalCode(buyCurrency)"/>
                            &#8594;
                            <xsl:value-of select="format-number(standartCourse, '### ##0,00', 'sbrf')"/>&nbsp;
                            <xsl:value-of select="mf:getCurrencySign(sellCurrency)"/>
                        </span>
                    </xsl:with-param>
                </xsl:call-template>

                <!--Моя выгода-->
                <xsl:if test="standartCourse > course">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">courseGainRow</xsl:with-param>
                        <xsl:with-param name="required">false</xsl:with-param>
                        <xsl:with-param name="description"></xsl:with-param>
                        <xsl:with-param name="rowName">Моя выгода:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <span id="courseGainSpan"></span>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:when>
            <!--В противном случае -->
            <xsl:otherwise>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="required" select="'false'"/>
                    <xsl:with-param name="lineId">courseRow</xsl:with-param>
                    <xsl:with-param name="id">course</xsl:with-param>
                    <xsl:with-param name="rowName">Курс покупки:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b>1.0 <xsl:value-of select="mf:getCurrencySign(buyCurrency)"/>&nbsp;
                        <span id="buyCurrency"><xsl:value-of select="cu:normalizeMetalCode(buyCurrency)"/></span> &#8594;
                        <span id="course"><xsl:value-of select="format-number(course, '### ##0,00', 'sbrf')"/></span>&nbsp;
                        <xsl:value-of select="mf:getCurrencySign(sellCurrency)"/></b>
                        <input type="hidden" id="courseFullVal" name="courseVal" value="{course}"/>
                        <input type="hidden" id="buyCurrency" name="buyCurrency" value="{buyCurrency}"/>
                        <input type="hidden" id="course" name="course" value="{course}"/>
                        <input type="hidden" id="sellCurrency" name="sellCurrency" value="{sellCurrency}"/>
                        <input type="hidden" value="{premierShowMsg}" name="premierShowMsg" id="premierShowMsg"/>
                        <xsl:if test="$tarifPlanCodeType = '1'">
                            <div class="payments-legend" id="courseRow-payments-legend" style="display: none">
                                <div class="italic">Используется льготный курс покупки/продажи</div>
                            </div>
                        </xsl:if>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
			<xsl:with-param name="id">buyAmount</xsl:with-param>
			<xsl:with-param name="rowName">Масса:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="buyAmount" name="buyAmount"
                       onchange="setSellAmount();clearBuyAmountFields();setValueOfExactAmount(this);setExactAmount('destination-field-exact');"
                       onkeyup="setSellAmount();clearBuyAmountFields();setValueOfExactAmount(this);setExactAmount('destination-field-exact');"
                       type="text" value="{buyAmount}" size="24" class="moneyField"/>&nbsp;<xsl:value-of select="mf:getCurrencySign(buyCurrency)"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="id">fromResource</xsl:with-param>
			<xsl:with-param name="rowName">Счет списания:</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <xsl:call-template name="resources">
			    	<xsl:with-param name="name">fromResource</xsl:with-param>
			    	<xsl:with-param name="accountNumber" select="fromAccountSelect"/>
			    	<xsl:with-param name="linkId" select="fromResource"/>
			    	<xsl:with-param name="activeAccounts" select="$activeDebitAccounts"/>
                    <xsl:with-param name="activeCards" select="$activeDebitCards"/>
			    </xsl:call-template>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
			<xsl:with-param name="id">sellAmount</xsl:with-param>
			<xsl:with-param name="rowName">Сумма:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="sellAmount" name="sellAmount" type="text" value="{sellAmount}" size="24" class="moneyField"
                       onchange="setBuyAmount();clearSellAmountFields();setValueOfExactAmount(this);setExactAmount('charge-off-field-exact');"
                       onkeyup="setBuyAmount();clearSellAmountFields();setValueOfExactAmount(this);setExactAmount('charge-off-field-exact');"/>&nbsp;<xsl:value-of select="mf:getCurrencySign(sellCurrency)"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="lineId">officeRow</xsl:with-param>
			<xsl:with-param name="rowName">Место открытия:</xsl:with-param>
			<xsl:with-param name="rowValue">
                            <a href="" id="office-link" class="orangeText" onclick="showDepartmetsList();return false;">
                                <span><xsl:value-of select="officeName"/>&nbsp;<xsl:value-of select="officeAddress"/></span>
                            </a>

                            <span id="office-span">Для определения места открытия ОМС укажите счет списания.</span>

                <input type="hidden" id="officeName" name="officeName" value="{sh:formatStringForJavaScript(officeName, false())}"/>
                <input type="hidden" id="officeAddress" name="officeAddress" value="{sh:formatStringForJavaScript(officeAddress, false())}"/>
                <input type="hidden" id="officeRegion" name="officeRegion" value="{sh:formatStringForJavaScript(officeRegion, false())}"/>
                <input type="hidden" id="officeBranch" name="officeBranch" value="{sh:formatStringForJavaScript(officeBranch, false())}"/>
                <input type="hidden" id="office" name="office" value="{sh:formatStringForJavaScript(office, false())}"/>
			</xsl:with-param>
		</xsl:call-template>

        <script type="text/javascript">
            doOnLoad(function()
            {
                showOrHidePremierWarning('<xsl:value-of select="sh:formatStringForJavaScript(premierShowMsg, true())"/>');
                <xsl:if test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                setCourseGain();
                </xsl:if>
            });
            function setExactAmount(exactAmount)
            {
                $("#exactAmount").val(exactAmount);
            }

            function setValueOfExactAmount(amount)
            {
                ensureElement("valueOfExactAmount").value = amount.value;
            }
            <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
            <xsl:if test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
            <!--Подсчет выгоды-->
            function setCourseGain()
            {
                <!--сумма списания-->
                <xsl:if test="standartCourse > course">
                    var sellAmountValue = getElementValue("sellAmount");
                    var rate = <xsl:value-of select="course"/>;
                    var standartRate = <xsl:value-of select="standartCourse"/>;
                    var fromCurrency = getElementValue("sellCurrency");
                    if ( isEmpty(sellAmountValue) )
                        sellAmountValue = '0';
                    <!--1/курс - кол-во металла за 1 рубль-->
                    var diff = Math.abs(parseFloat(1/rate) - parseFloat(1/standartRate));
                    if ( fromCurrency == 'RUB' &amp;&amp;
                         !isEmpty(sellAmountValue) &amp;&amp; !isEmpty(sellAmountValue.replace(/ /g,"")) )
                    {
                        diff = parseInt(diff * parseFloat(sellAmountValue.replace(",",".").replace(/ /g,"")) * 100) / 100;
                    }
                    $("#courseGainSpan").html(parseFloat(diff).toFixed(2) + ' <xsl:value-of select="mf:getCurrencySign(buyCurrency)"/>');
                </xsl:if>
            }
            </xsl:if>
            function setSellAmount()
            {
                var buyAmount = normalizeAmount($("#buyAmount").val());
                if (isAmountNotCorrect(buyAmount))
                    return;
                var sellAmount = (parseFloat(buyAmount) *  getCourse()).toFixed(2);
                if (isNaN(sellAmount))
                    sellAmount = "";
                $("#sellAmount").setMoneyValue(sellAmount);
                <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
                <xsl:if test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                    setCourseGain();
                </xsl:if>
            }

            function changeFormatInputRegexp()
            {
                var cur = $("#buyCurrency").text();
                if (cur == 'PTR' || cur == 'AUR' || cur == 'PDR')
                {
                    $("#buyAmount").setPrecision(1);
                }
                else if (cur == 'ARG')
                {
                    $("#buyAmount").setIntegerMoneyRegexp(/^-?\d+(^\.|,)$/);
                    $("#buyAmount").setPrecision(0);
                }
            }

            function setBuyAmount()
            {
                var sellAmount =  normalizeAmount($("#sellAmount").val());
                if (isAmountNotCorrect(sellAmount))
                    return;
                var buyAmount = ((Math.floor((parseFloat(sellAmount) / getCourse()) * getFixedCount())/getFixedCount())).toString();
                if (isNaN(buyAmount))
                    buyAmount = "";
                $("#buyAmount").setMoneyValue(buyAmount);
                <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
                <xsl:if test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                    setCourseGain();
                </xsl:if>
            }

            function fixAmount()
            {
                var buyAmount = normalizeAmount($("#buyAmount").val());
                if (isAmountNotCorrect(buyAmount))
                    return;
                var normalAmount = ((Math.floor(buyAmount* getFixedCount()))/ getFixedCount()).toString();
                if (isNaN(normalAmount))
                    normalAmount = "";
                $("#buyAmount").setMoneyValue(buyAmount);
            }

            function clearBuyAmountFields()
            {
                if (isAmountNotCorrect($("#buyAmount").val()))
                {
                    $("#buyAmount").val("");
                    $("#sellAmount").val("");
                    <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
                    <xsl:if test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                        setCourseGain();
                    </xsl:if>
                }
            }

            function clearSellAmountFields()
            {
                if (isAmountNotCorrect($("#sellAmount").val()))
                {
                    $("#buyAmount").val("");
                    $("#sellAmount").val("");
                    <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
                    <xsl:if test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                        setCourseGain();
                    </xsl:if>
                }
            }

            function isAmountNotCorrect(amount)
            {
                return (amount == undefined || amount == null || amount == "");
            }

            function getFixedCount()
            {
                if ($("#buyCurrency").text() == 'ARG')
                    return 1;
                return 10;
            }

            function getCourse()
            {
                var trueCourse = $("#courseFullVal").val();
                return parseFloat(trueCourse);s
            }

            function normalizeAmount(amount)
            {
                var newAmount = amount.replace(",", "\.");
                return newAmount.split(' ').join('');
            }

           doOnLoad(function()
           {
                changeFormatInputRegexp();
                fixAmount();
                var operationCode =  document.getElementById("fromResource");

                if (officeIMAName != '' &amp;&amp; operationCode.value != '' &amp;&amp; offices[operationCode.value]['isImaOpening'] == '0')
                {
                    $("#office-link").show();
                    $("#office-span").hide();
                    parentSynchKey = offices[operationCode.value]['parentSynchKey'];
                }else {
                    changeOffice(operationCode.value);
                }

           });
        </script>
	</xsl:template>

    <xsl:template match="/form-data" mode="view">
        <script type="text/javascript">
            function enbaleLicenseAgree()
            {
                var licenseAgree = document.getElementById("licenseAgree");
                if (licenseAgree != undefined &amp;&amp; licenseAgree != null )
                    licenseAgree.disabled = "";
            }

            function showLicense()
            {
                var win  =  window.open("<xsl:value-of select="$webRoot"/>/private/ima/opening/license.do?imaProductId=" + <xsl:value-of  select="imaId"/> + "&amp;documentId=" + getQueryStringParameter('id'), 'IMALicense', "resizable=1,menubar=0,toolbar=0,scrollbars=1, width=750, height=500");
	            win.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
            }

            function checkClientAgreesCondition(buttonName)
            {
                var checked = getElement('licenseAgree').checked;
                if (checked == false)
                {
                     addError('Для того чтобы подтвердить заявку, ознакомьтесь с условиями договора, щелкнув по ссылке «Просмотр условий договора». Если Вы с  условиями согласны, то установите флажок в поле «С условиями договора согласен» и нажмите на кнопку «Подтвердить».');
                     payInput.fieldError('licenseAgree');
                }
                return checked;

            }

            <!--Удаляет сообщения по названию кнопки-->
            function removeMessageByButtonName(buttonName)
            {
                var AGREEMENT_MESSAGE = getAgreementMessage(buttonName);
                removeMessage(AGREEMENT_MESSAGE);
            }

            function getAgreementMessage(buttonName)
            {
                if (buttonName!=undefined)
                {
                    return AGREEMENT_CONDITION + '«' + buttonName + '».';
                }
                else
                    return AGREEMENT_CONDITION + '«Подтвердить».';
            }

            doOnLoad(function()
            {
                showOrHidePremierWarning(<xsl:value-of select="premierShowMsg"/>);
            });
        </script>

        <xsl:if test="state = 'SAVED' and not($postConfirmCommission)">
            <script type="text/javascript">
                doOnLoad(function()
                {
                    if (window.addMessage != undefined)
                        addMessage("За выполнение данной операции комиссия не взимается.");
                });
            </script>
        </xsl:if>
        <xsl:if test="courseChanged = 'true' and state = 'SAVED'">
              <script type="text/javascript">
                  doOnLoad(function()
                  {
                    removeError('Курс покупки/продажи металла изменился. Вам необходимо создать новый платеж. ');
                    addError('Обратите внимание! Изменился курс конверсии.');
                    payInput.fieldError('course');
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

        <xsl:if test="toAccountSelect != ''">
            <xsl:call-template name="standartRow">
			    <xsl:with-param name="lineId">toAccountSelectRow</xsl:with-param>
			    <xsl:with-param name="rowName">Открыт ОМС:</xsl:with-param>
			    <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="toAccountSelect"/></b>
			    </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Дата открытия:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <input type="hidden" name="openingDate" value="{documentDate}"/>
				<b><xsl:value-of select="openingDate"/></b>
			</xsl:with-param>
			<xsl:with-param name="isAllocate" select="'false'"/>
		</xsl:call-template>

       <!--Информация об операции-->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Информация об операции</b></xsl:with-param>
        </xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="lineId">buyCurrencyRow</xsl:with-param>
			<xsl:with-param name="id">buyCurrency</xsl:with-param>
			<xsl:with-param name="rowName">Металл:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<b><xsl:value-of select="buyIMAProduct"/></b>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:choose>
            <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
            <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                <!--Поле льготный курс-->
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">courseRow</xsl:with-param>
                    <xsl:with-param name="id">course</xsl:with-param>
                    <xsl:with-param name="rowName">Льготный курс покупки:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b>1.0 <xsl:value-of select="mf:getCurrencySign(buyCurrency)"/>&nbsp;
                            <span id="buyCurrency">
                                <xsl:value-of select="cu:normalizeMetalCode(buyCurrency)"/>
                            </span>
                        </b>
                            &#8594;
                        <b><span id="course">
                                <xsl:value-of select="format-number(course, '### ##0,00', 'sbrf')"/>
                            </span>&nbsp;
                            <xsl:value-of select="mf:getCurrencySign(sellCurrency)"/>
                        </b>
                    </xsl:with-param>
                </xsl:call-template>

                <!--Поле обычный курс-->
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Обычный курс покупки:</xsl:with-param>
                    <xsl:with-param name="lineId">standartCourseRow</xsl:with-param>
                    <xsl:with-param name="id">standartCourse</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <span class="crossed">
                            1.0 <xsl:value-of select="mf:getCurrencySign(buyCurrency)"/>&nbsp;
                            <span id="buyCurrency">
                                <xsl:value-of select="cu:normalizeMetalCode(buyCurrency)"/>
                            </span>
                            &#8594;
                            <span id="standartCourseSpan">
                                <xsl:value-of select="format-number(standartCourse, '### ##0,00', 'sbrf')"/>
                            </span>&nbsp;
                            <xsl:value-of select="mf:getCurrencySign(sellCurrency)"/>
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
                            <xsl:value-of select="mf:getCurrencySign(buyCurrency)"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:when>
            <!--В противном случае -->
            <xsl:otherwise>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">courseRow</xsl:with-param>
                    <xsl:with-param name="id">course</xsl:with-param>
                    <xsl:with-param name="rowName">Курс покупки:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b>1.0 <xsl:value-of select="mf:getCurrencySign(buyCurrency)"/>&nbsp;
                            <span id="buyCurrency">
                                <xsl:value-of select="cu:normalizeMetalCode(buyCurrency)"/>
                            </span>
                            &#8594;
                            <span id="course">
                                <xsl:value-of select="format-number(course, '### ##0,00', 'sbrf')"/>
                            </span>&nbsp;
                            <xsl:value-of select="mf:getCurrencySign(sellCurrency)"/>
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

		<xsl:call-template name="standartRow">
			<xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
			<xsl:with-param name="id">buyAmount</xsl:with-param>
			<xsl:with-param name="rowName">Масса:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <b><xsl:value-of select="format-number(buyAmount, '### ##0.0')"/>&nbsp;<xsl:value-of select="mf:getCurrencySign(buyCurrency)"/></b>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счет списания:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="not($fromAccountSelect = '')">
                    <b>
                        <xsl:choose>
                            <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                                <xsl:value-of select="au:getShortAccountNumber(fromAccountSelect)"/>
                                &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                                <xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>&nbsp;
                                <xsl:value-of select="fromAccountName"/>&nbsp;
					            (<xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>)
                            </xsl:otherwise>
                        </xsl:choose>
                    </b>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:choose>
            <xsl:when test="$postConfirmCommission">
                <xsl:call-template name="transferSumRows">
                    <xsl:with-param name="fromResourceCurrency" select="sellCurrency"/>
                    <xsl:with-param name="toResourceCurrency" select="buyCurrency"/>
                    <xsl:with-param name="chargeOffAmount" select="sellAmount"/>
                    <xsl:with-param name="destinationAmount" select="buyAmount"/>
                    <xsl:with-param name="documentState" select="state"/>
                    <xsl:with-param name="exactAmount" select="exactAmount"/>
                    <xsl:with-param name="needUseTotalRow" select="'false'"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
                    <xsl:with-param name="id">sellAmount</xsl:with-param>
                    <xsl:with-param name="rowName">Сумма:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b class="summ"><xsl:value-of select="sellAmount"/>&nbsp;<xsl:value-of select="mf:getCurrencySign(sellCurrency)"/></b>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="lineId">officeRow</xsl:with-param>
			<xsl:with-param name="rowName">Место открытия:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <b><xsl:value-of select="officeName"/>&nbsp;<xsl:value-of select="officeAddress"/></b>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Статус документа:</xsl:with-param>
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

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="lineId">licenseShowRow</xsl:with-param>
		    <xsl:with-param name="rowName"/>
		    <xsl:with-param name="rowValue">
                <a class="orangeText" href="#" name="licenseShow" onclick="enbaleLicenseAgree();showLicense();return false;"><span>просмотр условий договора</span></a>
		    </xsl:with-param>
		</xsl:call-template>

        <xsl:if test="(state = 'SAVED' or state = 'INITIAL') and $app !='PhizIA'">
		    <xsl:call-template name="standartRow">
		    	<xsl:with-param name="lineId">licenseAgreeRow</xsl:with-param>
		    	<xsl:with-param name="rowName"/>
		    	<xsl:with-param name="rowValue">
                    <input type="checkbox" name="licenseAgree" id="licenseAgree" value="" style="vertical-align: middle;" disabled="disabled"/>
                    <b> с условиями договора согласен</b>
                    <div class="payments-legend">
                        Ознакомьтесь с условиями открытия ОМС, щелкнув по ссылке «Просмотр условий договора». Затем установите флажок в поле «С условием договора согласен» и нажмите на кнопку «Подтвердить».
                    </div>
		    	</xsl:with-param>
		    </xsl:call-template>
        </xsl:if>

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
		</xsl:choose>
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
            <xsl:when test="$code='WAIT_CONFIRM'">Ожидает дополнительной обработки (статус для клиента: "Подтвердите в контактном центре")</xsl:when>
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
