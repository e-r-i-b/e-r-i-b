<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
        <!ENTITY nbsp  "&#160;">
        <!ENTITY mdash "&#8212;">
        <!ENTITY ensp  "&#8194;">
        ]>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:sh="java://com.rssl.phizic.utils.StringHelper"
                xmlns:dh="java://com.rssl.phizic.utils.DateHelper"
                xmlns:gh="com.rssl.phizic.business.login.GuestHelper"
                xmlns:lch="com.rssl.phizic.business.loanclaim.LoanClaimHelper"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil">

    <xsl:import href="commonTypes.html.template.xslt"/>
    <xsl:output method="html" version="1.0" indent="yes"/>
    <xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentState" select="documentState"/>
    <xsl:param name="personAvailable" select="true()"/>
    <xsl:variable name="styleClass" select="'Width220 LabelAll'"/>
   	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
    <xsl:variable name="styleSpecial" select="''"/>
    <xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="isGuest" select="isGuest"/>
    <xsl:param name="isAdminApplication">
        <xsl:value-of select="$application = 'PhizIA'"/>
    </xsl:param>

    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>

    <xsl:variable name="formData" select="/form-data"/>

    <xsl:variable name="inWaitTM" select="$formData/inWaitTM"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'edit' and ($formData/inWaitTM !='true' or $isAdminApplication='true')">
                    <xsl:apply-templates mode="edit"/>
            </xsl:when>
            <xsl:when test="$mode = 'view' or $formData/inWaitTM ='true'">
                <xsl:apply-templates mode="view"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <input type="hidden" name="state" value="{$documentState}" id="state"/>
        <xsl:variable name="claimDictionaries" select="document('loan-claim-dictionaries.xml')"/>
        <xsl:choose>
            <!--
                *********************************************************************************************
                                                1 шаг: выбор условий
                *********************************************************************************************
            -->
            <xsl:when test="$documentState = 'INITIAL'">
                <script type="text/javascript" src="{$resourceRoot}/scripts/extendedLoanClaimControls.js"/>
                <xsl:variable name="loanOffers"            select="document('loan-claim-loan-offers.xml')/entity-list/*"/>
                <xsl:variable name="loanConditions"        select="document('loan-claim-loan-conditions.xml')/entity-list/*"/>
                <xsl:variable name="loanOfferDescriptions" select="document('loan-offer-descriptions.xml')/entity-list/*"/>

                <div class="Title">
                    <div class="subTitleBlock">
                        <span class="size24">Выбор кредита</span>
                        <div>
                            <p>Для оформления кредита просто выберите подходящие Вам условия! Узнать подробнее о кредитовании в Сбербанке можно на <a class="orangeText" target="_blank" href="http://sberbank.ru/moscow/ru/person/credits/"><span>сайте</span></a>.</p>
                        </div>
                    </div>
                </div>

                <xsl:if test="count($loanOffers) > 0 and count($loanConditions) > 0">
                    <div id='receiverSubTypeControl' class="bigTypecontrol">
                        <div id="loanOffersButton" class="inner firstButton">
                            <xsl:attribute name="onclick">
                                <xsl:variable name="showLoanOffersBlock">hideOrShow('loanOffersBlock', false);</xsl:variable>
                                <xsl:value-of select="concat('changeLoanTypeConditions(this.id);', 'selectLoanOfferCondition();', $showLoanOffersBlock)"/>
                            </xsl:attribute>
                            <xsl:attribute name="class">inner firstButton activeButton</xsl:attribute>
                            Специальные предложения
                        </div>
                        <div id="usualLoanButton" class="inner lastButton">
                            <xsl:attribute name="onclick">
                                <xsl:variable name="hideLoanOffersBlock">hideOrShow('loanOffersBlock', true);</xsl:variable>
                                <xsl:value-of select="concat('changeLoanTypeConditions(this.id);', 'pageControls.onSelectCondition();', $hideLoanOffersBlock)"/>
                            </xsl:attribute>
                            <xsl:attribute name="class">inner lastButton</xsl:attribute>
                            Обычные условия кредита
                        </div>
                    </div>
                    <div class="clear"></div>
                </xsl:if>

                <xsl:if test="count($loanOffers) > 0">
                    <div id="loanOffersBlock">
                        <div class="changeBlockClaim">

                            <xsl:variable name="hasTopUp">
                                <xsl:for-each select="$loanOffers">
                                    <xsl:variable name="loanOffer" select="current()"/>
                                    <xsl:for-each select="current()/entity-list/entity[@key='TOPUP']">
                                        <xsl:value-of select="'true'"/>
                                    </xsl:for-each>
                                </xsl:for-each>
                            </xsl:variable>

                            <xsl:choose>
                                <xsl:when test="$hasTopUp != ''">
                                    <div class="Title subheadingClaim">
                                        <span>
                                            Предодобренный кредит с погашением текущих кредитов
                                        </span>
                                    </div>
                                    <span>
                                        Предодобренный кредит предоставляет денежные средства, а также покрывает выплаты по текущим кредитам (основной долг, проценты, штрафы и просрочки).
                                    </span>
                                    <br/>
                                    <br/>
                                    <div class="Title subsubheadingClaim">
                                        <span>Выберите новую сумму кредита</span>
                                    </div>
                                </xsl:when>
                                <xsl:otherwise>
                                    <div class="Title subheadingClaim">
                                        <span>
                                            Специальные предложения
                                        </span>
                                    </div>
                                </xsl:otherwise>
                            </xsl:choose>
                            <div class="clear"></div>

                            <div class="changeBlockTitleInnerClaim">
                                <xsl:for-each select="$loanOffers">
                                    <xsl:variable name="loanOffer" select="current()"/>

                                    <xsl:for-each select="current()/entity-list/entity[@key='CONDITION']">
                                        <xsl:variable name="conditionAmount" select="current()/field[@name='amount']/text()"/>

                                        <xsl:if test="not($conditionAmount = '' or $conditionAmount = 0)">
                                            <xsl:call-template name="loanTile">
                                                <xsl:with-param name="offer"             select="$loanOffer"/>
                                                <xsl:with-param name="condition"         select="current()"/>
                                                <xsl:with-param name="onSelectLoanOffer" select="'pageControls.onSelectLoanOffer(this);'"/>
                                            </xsl:call-template>
                                        </xsl:if>
                                    </xsl:for-each>
                                </xsl:for-each>
                                <div class="clear"></div>

                                <div id="topUpRegion"></div>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                </xsl:if>

                <div>
                    <div class="loanClass">

                        <div class="loanClass Title subheadingClaim">
                            <span>Кредит на обычных условиях</span>
                        </div>

                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="id"     select="'creditTypeIdRow'"/>
                            <xsl:with-param name="lineId" select="'creditTypeIdRow'"/>
                            <xsl:with-param name="rowName">Вид кредита:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <select id="creditTypeId" name="creditTypeId" onchange="pageControls.onChangeCreditType();">
                                </select>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="id"     select="'creditProductIdRow'"/>
                            <xsl:with-param name="lineId" select="'creditProductIdRow'"/>
                            <xsl:with-param name="rowName">Наименование кредита:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <select id="creditProductId" name="creditProductId" onchange="pageControls.onChangeCreditProduct();">
                                </select>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="id"       select="'loanDescriptionsRow'"/>
                            <xsl:with-param name="lineId"   select="'loanDescriptionsRow'"/>
                            <xsl:with-param name="required" select="'false'"/>
                            <xsl:with-param name="rowName"  select="''"/>
                            <xsl:with-param name="rowValue">
                                <ul id="loanDescriptions" class="terms"></ul>
                            </xsl:with-param>
                        </xsl:call-template>
                    </div>

                    <div class="offerClass">
                        <div class="parameterBlock">
                            <div>
                                <span class="productTitleDetailInfoText size18">Условия кредита</span>
                            </div>
                            <br/>
                            <div>
                                <ul class="terms">
                                    <xsl:for-each select="$loanOfferDescriptions">
                                        <li>
                                            &mdash;&nbsp;<xsl:value-of select="current()/@description"/>
                                        </li>
                                    </xsl:for-each>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="clear"/>

                <div>
                    <div class="parameterBlock">
                        <span class="productTitleDetailInfoText size18">Параметры кредита</span>
                    </div>

                    <div class="loanClass">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="id"     select="'loanCurrencyRow'"/>
                            <xsl:with-param name="lineId" select="'loanCurrencyRow'"/>
                            <xsl:with-param name="rowName">Валюта:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <select id="loanCurrency" name="loanCurrency" onchange="pageControls.onChangeLoanCurrency();">
                                </select>
                            </xsl:with-param>
                        </xsl:call-template>
                    </div>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="id"     select="'loanPeriodRow'"/>
                        <xsl:with-param name="lineId" select="'loanPeriodRow'"/>
                        <xsl:with-param name="rowName">На срок:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <div class="loanClass">
                                <xsl:call-template name="valueSlider">
                                    <xsl:with-param name="id"        select="'loanPeriod'"/>
                                    <xsl:with-param name="inputName" select="'loanPeriod'"/>
                                    <xsl:with-param name="data">
                                        &nbsp;<span>мес.</span><span id="formattedLoanDuration" class="standing"></span>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </div>

                            <div class="offerClass" style="font: bold 13px Arial">
                                <span id="loanDurationSpan"></span>&nbsp;мес.
                            </div>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="id"     select="'loanAmountRow'"/>
                        <xsl:with-param name="lineId" select="'loanAmountRow'"/>
                        <xsl:with-param name="rowName">Сумма кредита:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <div class="loanClass">
                                <xsl:call-template name="valueSlider">
                                    <xsl:with-param name="id"                  select="'loanAmount'"/>
                                    <xsl:with-param name="inputName"           select="'loanAmount'"/>
                                    <xsl:with-param name="formattedFieldClass" select="'loanAmount'"/>
                                    <xsl:with-param name="data">
                                        &nbsp;<span id="currencySign">руб.</span>
                                    </xsl:with-param>
                                </xsl:call-template>
                                <div>
                                    <span id="percentageOfCostDesc" class="standing" style="display: none; margin: 0"></span>
                                </div>
                            </div>

                            <div class="offerClass" style="font: bold 13px Arial">
                                <xsl:call-template name="valueSlider">
                                    <xsl:with-param name="id"                  select="'loanOfferAmount'"/>
                                    <xsl:with-param name="inputName"           select="'loanOfferAmount'"/>
                                    <xsl:with-param name="formattedFieldClass" select="'loanOfferAmount'"/>
                                    <xsl:with-param name="data">
                                        &nbsp;<span id="loanOfferCurrencySign">руб.</span>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </div>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="id"       select="'loanRateRow'"/>
                        <xsl:with-param name="lineId"   select="'loanRateRow'"/>
                        <xsl:with-param name="required" select="'false'"/>
                        <xsl:with-param name="rowName">Процентная ставка в год:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <div class="simpleHintBlock float bold size13">
                                <span id="loanRate"></span>%
                            </div>
                        </xsl:with-param>
                    </xsl:call-template>
                </div>

                <input type="hidden" name="condCurrId"      value="{condCurrId}"/>
                <input type="hidden" name="condId"          value="{condId}"/>
                <input type="hidden" name="useLoanOffer"    value="{useLoanOffer}"/>
                <input type="hidden" name="usePercent"      value="{usePercent}"/>
                <input type="hidden" id="loanOfferId"       name="loanOfferId"     value="{loanOfferId}"/>
                <input type="hidden" id="loanOfferPeriod"   name="loanOfferPeriod" value="{loanPeriod}"/>
                <input type="hidden" name="fromLoanAmount"  value="{fromLoanAmount}"/>
                <input type="hidden" name="jsonCondition"   value="{jsonCondition}"/>
                <input type="hidden" name="jsonCurrency"    value="{jsonCurrency}"/>

                <script type="text/javascript">
                function initByUserValues()
                {
                    var period =  '<xsl:value-of select="loanPeriod"/>';
                    if(isNotEmpty(period))
                    {
                        pageControls.getDurationSlider.elements.fakeSliderInput.value = period;
                        pageControls.getDurationSlider.elements.sliderInput.value = period ;
                        pageControls.getDurationSlider.valueChangeListener({source: period, number: period});
                        pageControls.getDurationSlider.onkeyup(window.event, period);
                    }


                    var amount =  '<xsl:value-of select="loanAmount"/>';
                    if(isNotEmpty(amount))
                    {
                        pageControls.getAmountSlider.elements.fakeSliderInput.value = amount;
                        pageControls.getAmountSlider.elements.sliderInput.value = amount ;
                        pageControls.getAmountSlider.valueChangeListener({source: amount, number: amount});
                        pageControls.getAmountSlider.onkeyup(window.event, amount);
                        pageControls.getLoanOfferAmountSlider.elements.fakeSliderInput.value = amount;
                        pageControls.getLoanOfferAmountSlider.elements.sliderInput.value = amount ;
                        pageControls.getLoanOfferAmountSlider.valueChangeListener({source: amount, number: amount});
                        pageControls.getLoanOfferAmountSlider.onkeyup(window.event, amount);
                    }
                }

                doOnLoad(function() {
                    var conditions  = new Conditions();
                    var creditTypes = new CreditTypes();
                    var loanOffers  = [];

                    <xsl:for-each select="$loanOffers">
                        loanOffers.push({
                            id           : '<xsl:value-of select="current()/field[@name='id']/text()"/>',
                            rate         :  <xsl:value-of select="current()/field[@name='rate']/text()"/>,
                            terbank      :  <xsl:value-of select="current()/field[@name='terbank']/text()"/>,
                            duration     : '<xsl:value-of select="current()/field[@name='duration']/text()"/>',
                            maxLimit     :  <xsl:value-of select="current()/field[@name='maxLimit']/text()"/>,
                            currency     : '<xsl:value-of select="current()/field[@name='currency']/text()"/>',
                            currencySign : '<xsl:value-of select="mu:getCurrencySign(current()/field[@name='currency']/text())"/>',
                            productName  : '<xsl:value-of select="current()/field[@name='productName']/text()"/>',
                            topUps       : [
                                <xsl:for-each select="current()/entity-list/entity[@key='TOPUP']">
                                {
                                    name            : '<xsl:value-of select="current()/field[@name='name']"/>',
                                    agreementNumber : '<xsl:value-of select="current()/field[@name='agreementNumber']"/>',
                                    amount          : '<xsl:value-of select="current()/field[@name='amount']"/>',
                                    currency        : '<xsl:value-of select="current()/field[@name='currency']"/>',
                                    termStart       : '<xsl:value-of select="current()/field[@name='termStart']"/>',
                                    termEnd         : '<xsl:value-of select="current()/field[@name='termEnd']"/>',
                                    creditingRate   : '<xsl:value-of select="current()/field[@name='creditingRate']"/>',
                                    repaymentAmount : '<xsl:value-of select="current()/field[@name='repaymentAmount']"/>'
                                },
                                </xsl:for-each>
                            ]

                            <xsl:for-each select="current()/entity-list/entity[@key='CONDITION']">
                                 <xsl:variable name="period" select="current()/field[@name='period']/text()"/>
                                ,<xsl:value-of select="concat('month', $period)"/> : <xsl:value-of select="current()/field[@name='amount']"/>
                            </xsl:for-each>
                        });
                    </xsl:for-each>

                    <xsl:for-each select="$loanConditions">
                        <xsl:variable name="condition"      select="current()"/>
                        <xsl:variable name="creditType"     select="current()/entity-list/entity[@key='CREDIT_PRODUCT_TYPE']"/>
                        <xsl:variable name="credit"         select="current()/entity-list/entity[@key='CREDIT_PRODUCT']"/>
                        <xsl:variable name="currConditions" select="current()/entity-list/entity[@key='CURRENCY_CONDITION']"/>

                        conditions.addCondition(
                        '<xsl:value-of select="current()/field[@name='id']/text()"/>',
                        '<xsl:value-of select="$creditType/field[@name='id']/text()"/>',
                        '<xsl:value-of select="$credit/field[@name='id']/text()"/>',
                         <xsl:value-of select="current()/field[@name='includeMaxDuration']/text()"/>,
                        '<xsl:value-of select="current()/field[@name='minDuration']/text()"/>',
                        '<xsl:value-of select="current()/field[@name='maxDuration']/text()"/>',
                        '<xsl:value-of select="current()/field[@name='additionalConditions']/text()"/>',
                        '<xsl:value-of select="current()/field[@name='json']/text()"/>'
                        );

                        <xsl:for-each select="$currConditions">
                            conditions.addCurrCondition(
                            '<xsl:value-of select="$condition/field[@name='id']/text()"/>',
                            '<xsl:value-of select="current()/field[@name='currConditionId']/text()"/>',
                            '<xsl:value-of select="current()/field[@name='currencyCode']/text()"/>',
                            '<xsl:value-of select="current()/field[@name='minPercentRate']/text()"/>',
                            '<xsl:value-of select="current()/field[@name='maxPercentRate']/text()"/>',
                            '<xsl:value-of select="current()/field[@name='minAmount']/text()"/>',
                            '<xsl:value-of select="current()/field[@name='maxAmount']/text()"/>',
                             <xsl:value-of select="current()/field[@name='includeMaxAmount']/text()"/>,
                             <xsl:value-of select="current()/field[@name='percentageOfCost']/text()"/>,
                            '<xsl:value-of select="current()/field[@name='maxLimitPercent']/text()"/>',
                            '<xsl:value-of select="mu:getCurrencySign(current()/field[@name='currencyCode']/text())"/>',
                            '<xsl:value-of select="current()/field[@name='json']/text()"/>'
                            );
                        </xsl:for-each>

                        creditTypes.addCreditType   (
                         <xsl:value-of select="$creditType/field[@name='id']/text()"/>,
                        '<xsl:value-of select="$creditType/field[@name='name']/text()"/>',
                        '<xsl:value-of select="$creditType/field[@name='code']/text()"/>' );

                        creditTypes.addCreditProduct(
                        <xsl:value-of select="$creditType/field[@name='id']/text()"/>,
                        <xsl:value-of select="$credit/field[@name='id']/text()"/>,
                        '<xsl:value-of select="$credit/field[@name='name']/text()"/>',
                        <xsl:value-of select="$credit/field[@name='ensuring']/text()"/> );
                    </xsl:for-each>

                    pageControls.init(conditions, creditTypes, loanOffers);
                    if (!$("div:hidden").is('.selectConditions') &amp;&amp; $('#loanOfferId')[0].value == '')
                    {
                        var firstCondition = $('.selectConditions')[0];
                        if (firstCondition)
                            firstCondition.click();
                    }
                    initByUserValues();
                });

                function changeLoanTypeConditions(selectedType)
                {
                    $('#receiverSubTypeControl').find('div').removeClass('activeButton');
                    $('#' + selectedType).addClass('activeButton');
                }

                function selectLoanOfferCondition()
                {
                    if (ensureElement(loanOffersBlock).style.display == 'none')
                    {
                        var firstCondition = $('.selectConditions')[0];
                        if (firstCondition)
                            firstCondition.click();
                    }
                }
                </script>
                <script data-main="{$resourceRoot}/scripts/require/data/extendedLoanClaimData" src="{$resourceRoot}/scripts/require/require.js"></script>

                <script type="text/template" id="topUpsLayout">
                    <div class="mainWorkspace productListLink">
                        <div name="claimsStatuses">
                            <div class="gp-claimsList cardClaims" style="width: auto">
                                <div class="gp-title" style="width: 180px">КРЕДИТЫ ДЛЯ ПОГАШЕНИЯ</div>
                                <div class="gp-claims">
                                    <div id="topUpsRegion"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="descriptionRegion">
                    </div>
                </script>

                <script type="text/template" id="topUpsView">
                </script>

                <script type="text/template" id="topUpView">
                    <div class="gp-claim lastClaims">
                        <div class="gp-claimIcon floatLeft gp-top-up"></div>
                        <div class="floatLeft gp-claimNameInfo" style="max-width: none">
                            <div class="gp-name">
                                <span>
                                    <b style="color: black">{{= name }}</b> {{= creditingRate}}%
                                </span>
                            </div>
                            <div class="gp-info">
                                <span>
                                    Договор №{{= agreementNumber}} заключён {{= termStart}} года на сумму {{= amount}} {{= currency}}<br/>
                                    Дата окончания {{= termEnd}} год
                                </span>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </script>

                <script type="text/template" id="descriptionView">
                    <div class="gp-claim lastClaims">
                        <div class="floatLeft gp-credit"></div>
                        <div class="gp-claimNameInfo floatLeft" style="max-width: none">
                            <div class="gp-name">
                                <span class="size16">Предварительная сумма к выдаче:</span>
                                <span class="size16">&nbsp;<span id="provisionalSum" style="font-weight: bold"></span></span>
                                <span class="size16">&nbsp;<span id="provisionalCurr"></span></span>
                            </div>
                            <div class="gp-info">
                                Сумма расчитана на <span id="descriptionDate"></span> с вычетом текущих кредитов
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </script>
            </xsl:when>
            <!--
                *********************************************************************************************
                                                2 шаг: персональные данные
                *********************************************************************************************
            -->
            <xsl:when test="$documentState = 'INITIAL2'">
                <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.js"/>
                <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.customCreationBlock.js"/>

                <xsl:variable name="activeAccounts" select="document('active-accounts.xml')/entity-list/*"/>
                <xsl:variable name="activeDebitMainCards" select="document('active-debit-overdraft-main-cards.xml')/entity-list/*"/>

                <xsl:variable name="educationTypes" select="$claimDictionaries/config/list-education"/>

                <xsl:variable name="personData" select="document('currentPersonData.xml')/entity-list/entity"/>

                <input type="hidden" name="loanOfferId" id="loanOffer" value="{loanOfferId}"/>

                <!--блок "принадлежность к Сбербанку"-->
                <xsl:if test="loanOfferId = '' or $isGuest">
                    <div id="sberRelation" class="blueContentBlock">

                    <xsl:call-template name="standartRow">
                       <xsl:with-param name="required" select="'true'"/>
                       <xsl:with-param name="lineId"   select="'sbrfRelationRow'"/>
                       <xsl:with-param name="rowName">Принадлежность к Сбербанку:</xsl:with-param>

                       <xsl:with-param name="rowValue">
                            <xsl:variable name="sbrfRelationType" select="sbrfRelationType"/>

                            <div class="textTick">
                                <xsl:choose>
                                    <xsl:when test="$sbrfRelationType = 'getPaidOnSbrfAccount'">
                                        <input type="checkbox" id="getPaidOnSbrfAccount" class="float" name="getPaidOnSbrfAccount" value="getPaidOnSbrfAccount" onclick="FormObject.onSbrfRelatedChange(this);" checked="checked"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <input type="checkbox" id="getPaidOnSbrfAccount" class="float" name="getPaidOnSbrfAccount" value="getPaidOnSbrfAccount" onclick="FormObject.onSbrfRelatedChange(this);"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                                <label class="float" for="getPaidOnSbrfAccount">&nbsp;Получаю з/п (пенсию) на карту/счет в Сбербанке</label>
                            </div>
                            <br/>
                            <div class="textTick">
                                <xsl:choose>
                                    <xsl:when test="$sbrfRelationType = 'workInSbrf'">
                                        <input type="checkbox" id="workInSbrf" class="float" name="workInSbrf" value="workInSbrf" onclick="FormObject.onSbrfRelatedChange(this);" checked="checked"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <input type="checkbox" id="workInSbrf" class="float" name="workInSbrf" value="workInSbrf" onclick="FormObject.onSbrfRelatedChange(this);"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                                <label class="float" for="workInSbrf">&nbsp;Являюсь сотрудником Сбербанка</label>
                            </div>
                            <xsl:if test="lch:isAvailableOtherSbrfRelationType()">
                                <br/>
                                <div class="textTick">
                                    <xsl:choose>
                                        <xsl:when test="$sbrfRelationType = 'otherSbrfRelationType'">
                                            <input type="checkbox" id="otherSbrfRelationType" class="float" name="otherSbrfRelationType" value="otherSbrfRelationType" onclick="FormObject.onSbrfRelatedChange(this);" checked="checked"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <input type="checkbox" id="otherSbrfRelationType" class="float" name="otherSbrfRelationType" value="otherSbrfRelationType" onclick="FormObject.onSbrfRelatedChange(this);"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                    <label class="float" for="otherSbrfRelationType">&nbsp;Другое</label>
                                </div>
                            </xsl:if>

                            <input type="hidden" id="sbrfRelationSelectType" name="sbrfRelationType" value="{$sbrfRelationType}"/>
                       </xsl:with-param>
                    </xsl:call-template>
                    <div id="accountInformationBlock">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="id" select="'sbrfAccountSelectRow'"/>
                            <xsl:with-param name="lineId" select="'sbrfAccountSelectRow'"/>
                            <xsl:with-param name="rowName">Укажите счет или карту:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <xsl:call-template name="resources">
                                    <xsl:with-param name="name">sbrfResource</xsl:with-param>
                                    <xsl:with-param name="accountNumber" select="sbrfAccountSelect"/>
                                    <xsl:with-param name="linkId" select="sbrfResource"/>
                                    <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
                                    <xsl:with-param name="activeDebitMainCards" select="$activeDebitMainCards"/>
                                    <xsl:with-param name="needAccountEmptyOption" select="true()"/>
                                    <xsl:with-param name="needCardEmptyOption" select="$isGuest"/>
                                </xsl:call-template>

                                <span id="salaryCountDesc">Вы можете оформить расширенную заявку, если  количество начислений зарплаты  не менее 4х</span>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="id" select="'sbrfAccountNumberRow'"/>
                            <xsl:with-param name="lineId" select="'sbrfAccountNumberRow'"/>
                            <xsl:with-param name="rowName">
                                <xsl:choose>
                                    <xsl:when test="$isGuest">
                                        Номер счета/карты:
                                    </xsl:when>
                                    <xsl:otherwise>
                                        Номер счета:
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <input   id="sbrfAccount" type="text"   size="22" maxlength="24" value="{sbrfAccount}"/>
                                <input name="sbrfAccount" type="hidden" value="{sbrfAccount}"/>
                                <div style="display: none;" id="currencyErrorDiv"></div>
                            </xsl:with-param>
                        </xsl:call-template>
                    </div>
                </div>
                </xsl:if>

                <!--блок "информация о клиенте"-->
                <div id="clientInformationBlock">
                    <xsl:call-template name="titleRow">
                         <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Краткая информация о Вас</b></xsl:with-param>
                    </xsl:call-template>

                    <xsl:variable name="guestWithoutMBK" select="$isGuest and gh:hasPhoneInMB() = false()"/>
                    <xsl:variable name="adminWithGuestClient" select="$isAdminApplication='true' and $isGuest"/>

                    <xsl:choose>
                        <xsl:when test="$guestWithoutMBK or $adminWithGuestClient">

                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="required" select="'true'"/>
                                <xsl:with-param name="id" select="'surNameRow'"/>
                                <xsl:with-param name="lineId" select="'surNameRow'"/>
                                <xsl:with-param name="rowName">Фамилия:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <input name="surName" type="text" size="20" maxlength="20" value="{surName}"/>
                                </xsl:with-param>
                            </xsl:call-template>

                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="required" select="'true'"/>
                                <xsl:with-param name="id" select="'firstNameRow'"/>
                                <xsl:with-param name="lineId" select="'firstNameRow'"/>
                                <xsl:with-param name="rowName">Имя:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <input name="firstName" type="text" size="20" maxlength="20" value="{firstName}"/>
                                </xsl:with-param>
                            </xsl:call-template>

                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="required" select="'false'"/>
                                <xsl:with-param name="id" select="'patrNameRow'"/>
                                <xsl:with-param name="lineId" select="'patrNameRow'"/>
                                <xsl:with-param name="rowName">Отчество:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <input name="patrName" type="text" size="20" maxlength="20" value="{patrName}"/>
                                </xsl:with-param>
                            </xsl:call-template>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="required" select="'true'"/>
                                <xsl:with-param name="rowName">ФИО:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <b class="float">
                                        <xsl:value-of select="firstName"/>&nbsp;<xsl:value-of select="patrName"/>&nbsp;<xsl:value-of select="surName"/>
                                    </b>
                                    <xsl:if test="$isAdminApplication='false'">
                                        <div class="simpleHintBlock float">
                                            <a class="imgHintBlock imageWidthText simpleHintLabel" onclick="return false;"></a>
                                            <div class="clear"></div>
                                            <div class="layerFon simpleHint">
                                                <div class="floatMessageHeader"></div>
                                                <div class="layerFonBlock"> В целях безопасности Ваши личные данные маскированы </div>
                                            </div>
                                        </div>
                                    </xsl:if>
                                    <input type="hidden" name="firstName" value="{firstName}"/>
                                    <input type="hidden" name="patrName" value="{patrName}"/>
                                    <input type="hidden" name="surName" value="{surName}"/>
                                </xsl:with-param>
                                <xsl:with-param name="isAllocate" select="'false'"/>
                            </xsl:call-template>
                        </xsl:otherwise>
                    </xsl:choose>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">Ваш пол:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="gender" select="gender"/>
                            <div class="textTick separatedRadio">
                                <input class="float" type="radio" name="gender" id="male" value="MALE">
                                    <xsl:if test="$gender = 'MALE'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label class="float" for="male">Мужчина</label>
                            </div>
                            <div class="textTick separatedRadio">
                                <input class="float" type="radio" name="gender" id="female" value="FEMALE">
                                    <xsl:if test="$gender = 'FEMALE'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label class="float" for="female">Женщина</label>
                            </div>
                        </xsl:with-param>
                        <xsl:with-param name="isAllocate" select="'false'"/>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="id" select="'birthDay'"/>
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="lineId" select="'birthDayRow'"/>
                        <xsl:with-param name="rowName">Дата рождения:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:choose>
                                <xsl:when test="$guestWithoutMBK or $adminWithGuestClient">
                                    <input type="text" name="birthDay" value="{dh:formatXsdDateToString(birthDay)}" maxlength="20" size="20" class="dot-date-pick"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:choose>
                                        <!--Не маскировать для АРМ сотрудника-->
                                        <xsl:when test="$isAdminApplication='true'">
                                            <span class="floatLeft"><b><xsl:value-of select="birthDay"/></b></span>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <span class="floatLeft"><b><xsl:value-of select="mask:getMaskedDate()"/></b></span>
                                            <xsl:call-template name="popUpMessage">
                                                <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                                            </xsl:call-template>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                    <input type="hidden" name="birthDay" value="{dh:formatXsdDateToString(birthDay)}"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                        <xsl:with-param name="isAllocate" select="'false'"/>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">Гражданство:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                           <b><xsl:value-of select="'Российская Федерация'"/></b>
                           <input type="hidden" name="citizenShip" value="RUSSIA"/>
                        </xsl:with-param>
                        <xsl:with-param name="isAllocate" select="'false'"/>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">Место рождения</xsl:with-param>
                         <xsl:with-param name="rowValue">
                            <input type="text" name="birthPlace" value="{birthPlace}" maxlength="100" size="50"/>
                         </xsl:with-param>
                        <xsl:with-param name="isAllocate" select="'false'"/>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">Номер мобильного телефона:</xsl:with-param>
                         <xsl:with-param name="rowValue">
                             <span class="floatLeft">
                                 <span>+</span>
                                 <xsl:variable name="mobileCountry" select="mobileCountry"/>
                                 <xsl:choose>
                                     <xsl:when test="$mobileCountry = ''">
                                         &nbsp;<input name="mobileCountry" maxlength="4" size="4" type="text" value="7"/>
                                     </xsl:when>
                                     <xsl:otherwise>
                                         &nbsp;<input name="mobileCountry" maxlength="4" size="4" type="text" value="{$mobileCountry}"/>
                                     </xsl:otherwise>
                                 </xsl:choose>
                                 &nbsp;<input name="mobileTelecom" maxlength="3" size="7" type="text" value="{mobileTelecom}"/>
                                 &nbsp;<input name="mobileNumber"  maxlength="7" size="7" type="text" value="{mobileNumber}"/>
                             </span>
                             <xsl:if test="mobileNumber != ''">
                                 <xsl:call-template name="popUpMessage">
                                     <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                                 </xsl:call-template>
                             </xsl:if>
                             <div class="clear"></div>
                         </xsl:with-param>
                        <xsl:with-param name="isAllocate" select="'true'"/>
                        <xsl:with-param name="description">Введите Ваш номер телефона без дефисов в формате (код страны до 4 цифр) (префикс до 3 цифр) (ваш номер до 7 цифр)</xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">Номер рабочего телефона:</xsl:with-param>
                         <xsl:with-param name="rowValue">
                             <span class="floatLeft">
                                 <span>+</span>
                                 <xsl:variable name="jobphoneCountry" select="jobphoneCountry"/>
                                 <xsl:choose>
                                     <xsl:when test="$jobphoneCountry = ''">
                                         &nbsp;<input name="jobphoneCountry" maxlength="4" size="4" type="text" value="7"/>
                                     </xsl:when>
                                     <xsl:otherwise>
                                         &nbsp;<input name="jobphoneCountry" maxlength="4" size="4" type="text" value="{$jobphoneCountry}"/>
                                     </xsl:otherwise>
                                 </xsl:choose>
                                 &nbsp;<input name="jobphoneTelecom" maxlength="7" size="7" type="text" value="{jobphoneTelecom}"/>
                                 &nbsp;<input name="jobphoneNumber"  maxlength="7" size="7" type="text" value="{jobphoneNumber}"/>
                             </span>
                             <xsl:if test="jobphoneNumber != ''">
                                 <xsl:call-template name="popUpMessage">
                                     <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                                 </xsl:call-template>
                             </xsl:if>
                             <div class="clear"></div>
                         </xsl:with-param>
                        <xsl:with-param name="isAllocate" select="'true'"/>
                        <xsl:with-param name="description">Если Вы являетесь пенсионером, то в данном поле укажите номер мобильного телефона</xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'false'"/>
                        <xsl:with-param name="rowName">Домашний по адресу проживания:</xsl:with-param>
                         <xsl:with-param name="rowValue">
                             <span>+</span>
                             <xsl:variable name="residencePhoneCountry" select="residencePhoneCountry"/>
                             <xsl:variable name="residencePhoneTelecom" select="residencePhoneTelecom"/>
                             <xsl:variable name="residencePhoneNumber" select="residencePhoneNumber"/>
                             <xsl:choose>
                                 <xsl:when test="$residencePhoneCountry = '' and $residencePhoneTelecom = '' and $residencePhoneNumber = ''">
                                     &nbsp;<input name="residencePhoneCountry" maxlength="4" size="4" type="text" value="7"/>
                                 </xsl:when>
                                 <xsl:otherwise>
                                     &nbsp;<input name="residencePhoneCountry" maxlength="4" size="4" type="text" value="{$residencePhoneCountry}"/>
                                 </xsl:otherwise>
                             </xsl:choose>

                             &nbsp;<input name="residencePhoneTelecom" maxlength="7" size="7" type="text" value="{$residencePhoneTelecom}"/>
                             &nbsp;<input name="residencePhoneNumber"  maxlength="7" size="7" type="text" value="{$residencePhoneNumber}"/>
                         </xsl:with-param>
                        <xsl:with-param name="isAllocate" select="'true'"/>
                        <xsl:with-param name="description">Введите Ваш номер телефона без дефисов в формате (код страны до 4 цифр) (префикс до 7 цифр) (ваш номер до 7 цифр)</xsl:with-param>
                    </xsl:call-template>

                    <span id="phoneBlankTypes" style="display:none;">
                        <select id="phoneBlankType" name="phoneBlankType">
                            <option value="MOBILE">Мобильный</option>
                            <option value="RESIDENCE">Домашний по адресу проживания</option>
                            <option value="REGISTRATION">Домашний по адресу регистрации</option>
                            <option value="WORK">Рабочий</option>
                        </select>
                    </span>

                    <span id="popUpMessage" style="display:none;">
                        <xsl:call-template name="popUpMessage">
                            <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                        </xsl:call-template>
                    </span>

                    <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.js"/>
                    <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.phoneBlock.js"/>
                    <!--Область для блоков, предназначенных для добавления телефонов. Создается в подключаемых скриптах-->
                    <div id="additionalPhonesArea" class="paymentBigLabel"></div>

                    <div id="temporaryPhonesFields">
                        <input type="hidden" value="{phoneBlank_1_type}" id="phoneBlankType1"/>
                        <input type="hidden" value="{phoneBlank_1_country}" id="phoneBlankCountry1"/>
                        <input type="hidden" value="{phoneBlank_1_telecom}" id="phoneBlankTelecom1"/>
                        <input type="hidden" value="{phoneBlank_1_number}" id="phoneBlankNumber1"/>
                    </div>


                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'false'"/>
                        <xsl:with-param name="rowName">Электронная почта:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="text" name="email" value="{email}" maxlength="70" size="50"/>
                        </xsl:with-param>
                        <xsl:with-param name="isAllocate" select="'false'"/>
                        <xsl:with-param name="description">Адрес может состоять из букв латинского алфавита, цифр, дефиса и символа подчеркивания, а так же в нем должен присутствовать символ «@» и «.».</xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                       <xsl:with-param name="required" select="'false'"/>
                       <xsl:with-param name="rowName">Укажите Ваш ИНН:</xsl:with-param>
                       <xsl:with-param name="rowValue">
                           <input type="text" name="inn" value="{inn}" maxlength="12" size="20" class="numberField"/>
                       </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                          <xsl:with-param name="required" select="'false'"/>
                          <xsl:with-param name="rowName">&nbsp;</xsl:with-param>
                          <xsl:with-param name="rowValue">
                              <div class="textTick">
                                  <xsl:choose>
                                    <xsl:when test="fioChanged ='true'">
                                        <input type="checkbox" class="float" id="fioChanged" onclick="FormObject.onChgFIOChangedBlock();" name="fioChanged" value="{fioChanged}" checked="checked"/>
                                    </xsl:when>
                                      <xsl:otherwise>
                                        <input type="checkbox" class="float" id="fioChanged" onclick="FormObject.onChgFIOChangedBlock();" name="fioChanged" value="{fioChanged}"/>
                                      </xsl:otherwise>
                                  </xsl:choose>
                                  <label class="float" for="fioChanged">&nbsp;Я менял (-а) ФИО</label>
                              </div>
                          </xsl:with-param>
                    </xsl:call-template>

                    <!--блок с данными об изменении ФИО-->
                    <div id="fioChangedBlock">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="required" select="'true'"/>
                            <xsl:with-param name="rowName">Дата изменения ФИО:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <input type="text" name="fioChangedDate" value="{dh:formatXsdDateToString(fioChangedDate)}" maxlength="70" size="10" class="dot-date-pick"/>
                            </xsl:with-param>
                            <xsl:with-param name="isAllocate" select="'false'"/>
                        </xsl:call-template>

                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="required" select="'true'"/>
                           <xsl:with-param name="rowName">Предыдущая фамилия:</xsl:with-param>
                           <xsl:with-param name="rowValue">
                               <input type="text" name="previosSurname" value="{previosSurname}" maxlength="120" size="50"/>
                           </xsl:with-param>
                           <xsl:with-param name="isAllocate" select="'false'"/>
                        </xsl:call-template>

                        <xsl:call-template name="standartRow">
                           <xsl:with-param name="required" select="'true'"/>
                           <xsl:with-param name="rowName">Предыдущее имя:</xsl:with-param>
                           <xsl:with-param name="rowValue">
                               <input type="text" name="previosFirstname" value="{previosFirstname}" maxlength="120" size="50"/>
                           </xsl:with-param>
                           <xsl:with-param name="isAllocate" select="'false'"/>
                        </xsl:call-template>

                        <xsl:call-template name="standartRow">
                           <xsl:with-param name="required" select="'false'"/>
                           <xsl:with-param name="rowName">Предыдущее отчество:</xsl:with-param>
                           <xsl:with-param name="rowValue">
                               <input type="text" name="previosPatr" value="{previosPatr}" maxlength="120" size="50"/>
                           </xsl:with-param>
                           <xsl:with-param name="isAllocate" select="'false'"/>
                        </xsl:call-template>

                        <xsl:call-template name="standartRow">
                           <xsl:with-param name="required" select="'true'"/>
                           <xsl:with-param name="rowName">Причина изменения:</xsl:with-param>
                           <xsl:with-param name="rowValue">
                               <xsl:variable name="fioChangedReasonType" select="fioChangedReasonType"/>
                                <div class="textTick separatedTick">
                                    <input class="float" type="radio" name="fioChangedReasonType" value="MARRIAGE" id="marriage">
                                        <xsl:if test="$fioChangedReasonType = 'MARRIAGE'">
                                            <xsl:attribute name="checked">true</xsl:attribute>
                                        </xsl:if>
                                    </input>
                                    <label class="float" for="marriage">Вступление в брак</label>
                                </div>
                                <div class="textTick separatedTick">
                                    <input class="float" type="radio" id="otherReason" name="fioChangedReasonType" value="OTHER">
                                        <xsl:if test="$fioChangedReasonType = 'OTHER'">
                                            <xsl:attribute name="checked">true</xsl:attribute>
                                        </xsl:if>
                                    </input>
                                    <input name="fioChangedOtherReason" id="other_reason_text" class="hidableTextValue" type="text" value="{fioChangedOtherReason}" placeholder="Другая причина" maxlength="255" size="40" onfocus="FormObject.onFocusOtherReason()"/>
                                </div>
                           </xsl:with-param>
                           <xsl:with-param name="isAllocate" select="'false'"/>
                        </xsl:call-template>
                    </div>

                    <xsl:call-template name="titleRow">
                        <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Паспортные данные</b></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">Дата выдачи:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="personDataPassportIssueDate" select="dh:formatXsdDateToString($personData/field[@name = 'REGULAR_PASSPORT_RF-issue-date'])"/>
                            <xsl:variable name="passportIssueDate" select="dh:formatXsdDateToString(passportIssueDate)"/>
                            <xsl:variable name="issueDate">
                                <xsl:choose>
                                    <xsl:when test="$passportIssueDate != ''"><xsl:value-of select="$passportIssueDate"/></xsl:when>
                                    <xsl:otherwise><xsl:value-of select="$personDataPassportIssueDate"/></xsl:otherwise>
                                </xsl:choose>
                            </xsl:variable>
                            <xsl:variable name="issueDateFieldEditable" select="$personDataPassportIssueDate != $issueDate or $issueDate = ''"/>
                            <xsl:choose>
                                <xsl:when test="$issueDateFieldEditable">
                                    <input type="text" name="passportIssueDate" value="{$issueDate}" maxlength="20" size="20" class="dot-date-pick"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <b><xsl:value-of select="$issueDate"/></b>
                                    <input type="hidden" id="passportIssueDate" name="passportIssueDate" value="{$issueDate}"/>
                                </xsl:otherwise>
                            </xsl:choose>
                            <xsl:if test="$isAdminApplication='false'">
                                <div class="loanDescData">Сверить паспортные данные Вы можете только по дате выдачи паспорта, так как остальная информация отображается в маскированном виде.</div>
                            </xsl:if>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">Код подразделения:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="personDataPassportIssueByCode" select="$personData/field[@name = 'REGULAR_PASSPORT_RF-issue-by-code']"/>
                            <xsl:variable name="passportIssueByCode" select="passportIssueByCode"/>
                            <xsl:variable name="issueByCode">
                                <xsl:choose>
                                    <xsl:when test="$passportIssueByCode != ''"><xsl:value-of select="$passportIssueByCode"/></xsl:when>
                                    <xsl:otherwise><xsl:value-of select="$personDataPassportIssueByCode"/></xsl:otherwise>
                                </xsl:choose>
                            </xsl:variable>
                            <xsl:variable name="issueByCodeFieldEditable" select="sh:isEmpty($personDataPassportIssueByCode) or $personDataPassportIssueByCode != $issueByCode or $issueByCode = ''"/>
                            <xsl:choose>
                                <xsl:when test="$issueByCodeFieldEditable">
                                    <input type="text" name="passportIssueByCode" value="{$issueByCode}" maxlength="6" size="20" class="passport-issue_by_code-template"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <b><xsl:value-of select="$issueByCode"/></b>
                                    <input type="hidden" id="passportIssueByCode" name="passportIssueByCode" value="{$issueByCode}"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:choose>
                        <xsl:when test="$guestWithoutMBK or $adminWithGuestClient">
                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="required" select="'true'"/>
                                <xsl:with-param name="id" select="'passportSeriesRow'"/>
                                <xsl:with-param name="lineId" select="'passportSeriesRow'"/>
                                <xsl:with-param name="rowName">Серия паспорта:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <input name="passportSeries" type="text" size="20" maxlength="20" value="{passportSeries}"/>
                                </xsl:with-param>
                            </xsl:call-template>

                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="required" select="'true'"/>
                                <xsl:with-param name="id" select="'passportNumberRow'"/>
                                <xsl:with-param name="lineId" select="'passportNumberRow'"/>
                                <xsl:with-param name="rowName">Номер паспорта:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <input name="passportNumber" type="text" size="20" maxlength="20" value="{passportNumber}"/>
                                </xsl:with-param>
                            </xsl:call-template>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="required" select="'true'"/>
                                <xsl:with-param name="rowName">Серия и номер паспорта:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <xsl:variable name="passportSeries" select="$personData/field[@name = 'REGULAR_PASSPORT_RF-series']"/>
                                    <xsl:variable name="passportNumber" select="$personData/field[@name = 'REGULAR_PASSPORT_RF-number']"/>
                                    <xsl:choose>
                                        <!--Не маскировать для АРМ сотрудника-->
                                        <xsl:when test="$isAdminApplication='true'">
                                            <b><span class="floatLeft"><xsl:value-of select="$passportSeries"/> &nbsp; <xsl:value-of select="$passportNumber"/></span></b>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <span class="floatLeft"><xsl:value-of select="sh:maskText($passportSeries)"/> &nbsp; <xsl:value-of select="sh:maskText($passportNumber)"/></span>
                                            <xsl:call-template name="popUpMessage">
                                                <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                                            </xsl:call-template>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                    <input type="hidden" name="passportSeries" value="{$passportSeries}"/>
                                    <input type="hidden" name="passportNumber" value="{$passportNumber}"/>
                                </xsl:with-param>
                            </xsl:call-template>
                        </xsl:otherwise>
                    </xsl:choose>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">Кем выдан:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="personDataPassportIssueBy" select="$personData/field[@name = 'REGULAR_PASSPORT_RF-issue-by']"/>
                            <xsl:variable name="passportIssueBy" select="passportIssueBy"/>
                            <xsl:variable name="issueBy">
                               <xsl:choose>
                                   <xsl:when test="$passportIssueBy != ''"><xsl:value-of select="$passportIssueBy"/></xsl:when>
                                   <xsl:otherwise><xsl:value-of select="$personDataPassportIssueBy"/></xsl:otherwise>
                               </xsl:choose>
                            </xsl:variable>
                            <xsl:variable name="issueByFieldEditable" select="sh:isEmpty($personDataPassportIssueBy) or $personDataPassportIssueBy != $issueBy or $issueBy = ''"/>
                            <xsl:choose>
                               <xsl:when test="$issueByFieldEditable">
                                   <input type="text" name="passportIssueBy" value="{$issueBy}" maxlength="255" size="20"/>
                               </xsl:when>
                               <xsl:otherwise>
                                   <b><xsl:value-of select="$issueBy"/></b>
                                   <input type="hidden" id="passportIssueBy" name="passportIssueBy" value="{$issueBy}"/>
                               </xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                       <xsl:with-param name="required" select="'false'"/>
                       <xsl:with-param name="rowValue">
                           <div class="loan-claim-button old-passport-data-block" onclick="FormObject.hideOrShowOldPassportDataBlock(false);">
                               <div class="buttonSelect">
                                    <div class="buttonSelectLeft"></div>
                                    <div class="buttonSelectCenter">
                                        Добавить данные старого паспорта
                                    </div>
                                    <div class="buttonSelectRight"></div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                           <xsl:choose>
                               <xsl:when test="hasOldPassport != ''">
                                    <input type="hidden" id="hasOldPassport" name="hasOldPassport" value="{hasOldPassport}"/>
                               </xsl:when>
                               <xsl:otherwise>
                                    <input type="hidden" id="hasOldPassport" name="hasOldPassport" value="false"/>
                               </xsl:otherwise>
                           </xsl:choose>
                       </xsl:with-param>
                   </xsl:call-template>

                   <div id="oldPassportDataBlock" style="display: none;">
                        <xsl:call-template name="standartRow">
                          <xsl:with-param name="required" select="'false'"/>
                          <xsl:with-param name="rowValue">
                              <span class="paymentTextlabel blockTitle"><b>Данные старого паспорта</b></span>
                              <xsl:call-template name="button">
                                  <xsl:with-param name="id" select="'oldPassportDataBlockDelete'"/>
                                  <xsl:with-param name="onclick" select="'FormObject.hideOrShowOldPassportDataBlock(true);'"/>
                                  <xsl:with-param name="typeBtn" select="'blueLink'"/>
                              </xsl:call-template>
                          </xsl:with-param>
                        </xsl:call-template>

                        <xsl:call-template name="standartRow">
                           <xsl:with-param name="required" select="'true'"/>
                           <xsl:with-param name="rowName">Дата выдачи:</xsl:with-param>
                           <xsl:with-param name="rowValue">
                                <input type="text" name="oldPassportIssueDate" value="{dh:formatXsdDateToString(oldPassportIssueDate)}" maxlength="70" size="10" class="dot-date-pick"/>
                           </xsl:with-param>
                        </xsl:call-template>


                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="required" select="'true'"/>
                            <xsl:with-param name="rowName">Серия и номер паспорта:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <input type="text" name="oldPassportSeriesAndNumber" value="{oldPassportSeriesAndNumber}" maxlength="12" size="20" class="passport-number-and-series-template"/>
                            </xsl:with-param>
                        </xsl:call-template>


                        <xsl:call-template name="standartRow">
                           <xsl:with-param name="required" select="'true'"/>
                           <xsl:with-param name="rowName">Кем выдан:</xsl:with-param>
                           <xsl:with-param name="rowValue">
                                  <input type="text" name="oldPassportIssueBy" value="{oldPassportIssueBy}" maxlength="100" size="50"/>
                           </xsl:with-param>
                        </xsl:call-template>
                   </div>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">У вас есть загранпаспорт?</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="hasForeignPassport" select="hasForeignPassport"/>
                            <div class="textTick">
                                <input class="float" type="radio" name="hasForeignPassport" value="true" id="ForeignPassportY">
                                    <xsl:if test="$hasForeignPassport = 'true'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label class="float" for="ForeignPassportY">Да&nbsp;</label>
                            </div>
                            <div class="textTick">
                                <input class="float" type="radio" name="hasForeignPassport" value="false" id="ForeignPassportN">
                                    <xsl:if test="$hasForeignPassport = 'false'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label class="float" for="ForeignPassportN">Нет</label>
                            </div>
                        </xsl:with-param>
                    </xsl:call-template>

                <!--блок "образование"-->
                    <xsl:call-template name="titleRow">
                        <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Образование</b></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                       <xsl:with-param name="required" select="'true'"/>
                       <xsl:with-param name="rowName">Образование:</xsl:with-param>
                       <xsl:with-param name="rowValue">
                           <xsl:variable name="educationSelected" select="educationTypeSelect"/>

                           <xsl:call-template name="selectFromDictionary">
                               <xsl:with-param name="selectId" select="'educationTypeSelect'"/>
                               <xsl:with-param name="elementsFromDictionary" select="$educationTypes/*"/>
                               <xsl:with-param name="selectedElement" select="$educationSelected"/>
                               <xsl:with-param name="onchange" select="'FormObject.hideOrShowHigherEducationCourse(this.value);'"/>
                           </xsl:call-template>
                       </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="lineId" select="'higherEducationCourseRow'"/>
                        <xsl:with-param name="rowName">Курс неоконченного высшего образования</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="text" name="higherEducationCourse" value="{higherEducationCourse}" maxlength="1" size="5"/>
                        </xsl:with-param>
                        <xsl:with-param name="isAllocate" select="'false'"/>
                    </xsl:call-template>
                </div>

                <xsl:call-template name="button">
                    <xsl:with-param name="id" select="'templateRemoveBtn'"/>
                    <xsl:with-param name="onclick" select="''"/>
                    <xsl:with-param name="typeBtn" select="'blueLink'"/>
                </xsl:call-template>

                <script type="text/javascript">
                    var phones;

                    $(document).ready(function(){
                        window.scrollTo(0, 0);
                    var info = fillSavedInfo('phoneBlank', 1);
                    relatives = createPhonesList(1, info);

                        $('.loan-claim-button .buttonSelect').hover(function(){
                            $(this).addClass('hoverOperation');
                        }, function() {
                           $(this).removeClass('hoverOperation');
                        });
                        var loanClaimButton = $(".loan-claim-button.old-passport-data-block");
                        loanClaimButton.width(loanClaimButton.find(".buttonSelectLeft").width()
                            + loanClaimButton.find(".buttonSelectCenter").width()
                            + loanClaimButton.find(".buttonSelectRight").width());
                    });

                    function fillSavedInfo(fieldPrefix, maxCount)
                    {
                        var info = [];
                        for (var i=1; i &lt; maxCount+1; i++)
                        {
                            var data = {};

                            var phoneType = ensureElement(fieldPrefix+"Type"+i).value;
                            if (phoneType != "")
                            {
                                data.phoneType = phoneType;

                                var country = ensureElement(fieldPrefix+"Country"+i).value;
                                if (country != "")
                                    data.country = country;

                                var telecom = ensureElement(fieldPrefix+"Telecom"+i).value;
                                if (telecom != "")
                                    data.telecom = telecom;

                                var number = ensureElement(fieldPrefix+"Number"+i).value;
                                if (number != "")
                                    data.number = number;

                                info.push(data);
                            }
                        }
                        return info;
                    }

                    var FormObject =
                    {
                        sbrfRelated          : "false",
                        isLoanOffer          : false,
                        fieldSbrfRelated     : null,
                        highEducationCourseRequiredCode : null,

                        init: function()
                        {
                            var isGuest = false;
                            <xsl:if test="$isGuest">
                                isGuest = true;
                            </xsl:if>
                            FormObject.isLoanOffer = !(document.getElementById('loanOffer').value == '');
                            if (!FormObject.isLoanOffer || isGuest)
                            {
                                FormObject.fieldSbrfRelated = document.getElementById('sbrfRelationSelectType');
                                var fieldSbrfRelatedValue   = FormObject.fieldSbrfRelated.value;
                                if (fieldSbrfRelatedValue == null || fieldSbrfRelatedValue == '')
                                {
                                    FormObject.onSbrfRelatedChange( null );
                                }
                                else
                                {
                                    FormObject.onSbrfRelatedChange( document.getElementById(fieldSbrfRelatedValue) );
                                }

                                FormObject.hideOrShowAccountNumberRow('<xsl:value-of select="sbrfResource"/>');

                                $('#sbrfAccount').keyup(function()
                                {
                                    $("input[name='sbrfAccount']").val( this.value.replace(/[^\d]*/g, '') );
                                });

                                $('#sbrfAccount').createMask('9999 9999 9999 9999 9999');
                            }

                            FormObject.hideOrShowHigherEducationCourse('<xsl:value-of select="educationTypeSelect"/>');
                            FormObject.onChgFIOChangedBlock();

                            if ($('#hasOldPassport').val() == 'true')
                            {
                                $('#hasOldPassport').val('true');
                                $('#oldPassportDataBlock').css('display', '');
                                $('#oldPassportDataBlockDelete').css('display', '');
                            }
                            else
                            {
                                $('#hasOldPassport').val('false');
                                $('#oldPassportDataBlock').css('display', 'none');
                                $('#oldPassportDataBlockDelete').css('display', 'none');
                            }
                            addMessage('У Вас изменились паспортные данные? Если да – перед оформлением нужно сообщить их в отделение Сбербанка. Мы не сможем выдать Вам кредит по старому паспорту');
                        },

                        <!--вызывается при изменении чекбокса "принадлежность к сбербанку"-->
                        onSbrfRelatedChange: function(element)
                        {
                            if (element == null)
                            {
                                FormObject.hideOrShowInformationBlock();
                            }
                            else
                            {
                                var getPaidOnSbrfAccount = document.getElementById('getPaidOnSbrfAccount');
                                var workInSbrf           = document.getElementById('workInSbrf');
                                var otherSbrfRelationType= document.getElementById('otherSbrfRelationType');
                                var salaryCountDesc      = document.getElementById('salaryCountDesc');

                                switch(element.id)
                                {
                                    case 'getPaidOnSbrfAccount':
                                    {
                                        workInSbrf.checked           = false;
                                        getPaidOnSbrfAccount.checked = element.checked;
                                        if (otherSbrfRelationType)
                                            otherSbrfRelationType.checked= false;
                                        hideOrShow(salaryCountDesc, false);
                                        break;
                                    }

                                    case 'workInSbrf':
                                    {
                                        workInSbrf.checked           = element.checked;
                                        getPaidOnSbrfAccount.checked = false;
                                        if (otherSbrfRelationType)
                                            otherSbrfRelationType.checked= false;
                                        hideOrShow(salaryCountDesc, true);
                                        break;
                                    }

                                    case 'otherSbrfRelationType':
                                    {
                                        workInSbrf.checked           = false;
                                        getPaidOnSbrfAccount.checked = false;
                                        if (otherSbrfRelationType)
                                            otherSbrfRelationType.checked= element.checked;
                                        hideOrShow(salaryCountDesc, true);
                                        break;
                                    }
                                }

                                if (element.checked)
                                {
                                    FormObject.fieldSbrfRelated.value = element.id;
                                }
                                else
                                {
                                    FormObject.fieldSbrfRelated.value = '';
                                }

                                FormObject.hideOrShowInformationBlock();
                            }
                        },

                        <!--скрыть или показать поле номер счета, зависит от селекта "принадлежность к сбербанку"-->
                        hideOrShowAccountNumberRow: function(value)
                        {
                           var sbrfResource = document.getElementById('sbrfResource');
                           if (value == null || value == '')
                           {
                               $('#sbrfAccountNumberRow').css('display', '');
                           }
                           else
                           {
                               $('#sbrfAccountNumberRow').css('display', 'none');
                           }
                        },

                        <!--вызывается при нажатии на чекбокс "я менял фио"-->
                        onChgFIOChangedBlock: function()
                        {
                            var element = document.getElementById('fioChanged');

                            if (element.checked)
                                $(element).val('true');
                            else
                                $(element).val('false');

                            $('#fioChangedBlock').css('display', $(element).val() == 'true' ? '' : 'none');
                        },

                        <!--скрыть или показать блок со старыми паспортными данными-->
                        hideOrShowOldPassportDataBlock: function(hide)
                        {
                            var button = $(".old-passport-data-block").get(0);
                            if (hide == false)
                            {
                                button.disabled = true;
                                document.getElementById("hasOldPassport").value= "true";
                            }
                            else
                            {
                                button.disabled = false;
                                document.getElementById("hasOldPassport").value= "false";
                            }

                            hideOrShow(document.getElementById("oldPassportDataBlock"), hide);
                            hideOrShow(document.getElementById("oldPassportDataBlockDelete"), hide);
                        },

                        <!--вызывается при фокусе на поле с другой причины изменения фио-->
                        onFocusOtherReason: function()
                        {
                            document.getElementById('otherReason').checked = 'true';
                        },

                        hideOrShowInformationBlock : function()
                        {
                            var sbrfRelationTypeValue = $('#sbrfRelationSelectType').val();
                            var isGuest = true;
                            <xsl:if test="$isGuest">
                                isGuest = true;
                            </xsl:if>
                            if (!FormObject.isLoanOffer || isGuest)
                            {
                                if (sbrfRelationTypeValue == '')
                                {
                                    $('#clientInformationBlock').css('display', 'none');
                                    $('#accountInformationBlock').css('display', 'none');
                                }
                                else if (sbrfRelationTypeValue == 'otherSbrfRelationType')
                                {
                                    $('#clientInformationBlock').css('display', '');
                                    $('#accountInformationBlock').css('display', 'none');
                                    getButtonWidth();
                                }
                                else
                                {
                                    $('#clientInformationBlock').css('display',  '');
                                    $('#accountInformationBlock').css('display', '');
									getButtonWidth();
                                }
                            }
                        },

                        hideOrShowHigherEducationCourse: function(value)
                        {
                            var highEducationCourseRequiredCode;
                            <xsl:for-each select="$educationTypes/education">
                                <xsl:if test="highEducationCourseRequired = 'true'">
                                    highEducationCourseRequiredCode = '<xsl:value-of select="code"/>';
                                </xsl:if>
                            </xsl:for-each>

                            if (highEducationCourseRequiredCode == value)
                            {
                                $('#higherEducationCourseRow').css('display', '');
                            }
                            else
                            {
                                $('#higherEducationCourseRow').css('display', 'none');
                            }
                        }
                    };

					 function getButtonWidth()
                    {
						$('.loan-claim-button').each(function()
                        {
                         var loanClaimButton = $(this).width();
                         var buttonSelectLeft = $(this).find('.buttonSelectLeft').width();
                         var buttonSelectCenter = $(this).find('.buttonSelectCenter').width();
                         var buttonSelectRight = $(this).find('.buttonSelectRight').width();
                         buttonWidth =  buttonSelectLeft + buttonSelectCenter + buttonSelectRight;

                         $(this).css('width', buttonWidth);
                        });
                    }

                    function showDate(id, dateValue)
                    {
                        document.getElementById(id).value = dateValue;
                    }

                    <!--Валидирующая ф-я, привязанная к кнопке save-->
                    function checkPayment()
                    {
                        relatives.updateInputInfo();
                        <!--Удаляем все скрытые поля.-->
                        $('div#temporaryPhonesFields').remove();
                        $('div#sbrfAccountSelectRow:hidden').remove();
                        $('div#sbrfAccountNumberRow:hidden').remove();
                        $('div#oldPassportDataBlock:hidden').remove();
                        $('div#temporaryPhonesFields').remove();
                        $('div#fioChangedBlock:hidden').remove();
                        $('div#higherEducationCourseRow:hidden').remove();
                        $('#passportIssueDate:disabled').removeAttr('disabled');
                        return true;
                    }

                    <!--
                       - Не использовать без крайней нужды данный метод в doOnLoad.
                       - Метод итак внизу страницы, поэтому исполняться будет после загрузки необходимых
                       - элементов страницы. Если все таки нужен будет doOnLoad, просьба сообщить автору комментария.
                       -->
                    FormObject.init();
                </script>
            </xsl:when>

            <xsl:when test="$documentState = 'INITIAL3'">

                <xsl:variable name="familyStatuses" select="$claimDictionaries/config/list-family-status"/>
                <xsl:variable name="familyRelation" select="$claimDictionaries/config/list-family-relation/*"/>

                <div class="Title">
                    <span class="size24">Семья</span>
                </div>

                <xsl:call-template name="titleRow">
                    <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Семейное положение</b></xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="required" select="'true'"/>
                    <xsl:with-param name="rowName">Семейное положение</xsl:with-param>
                    <xsl:with-param name="rowValue">

                        <xsl:variable name="selectedValue" select="familyStatusSelect"/>

                        <xsl:variable name="familyStatusSelected">
                            <xsl:choose>
                                <xsl:when test="$selectedValue != ''">
                                  <xsl:value-of select="$selectedValue"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="'0'"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>

                      <xsl:call-template name="selectFromDictionary">
                          <xsl:with-param name="selectId" select="'familyStatusSelect'"/>
                          <xsl:with-param name="elementsFromDictionary" select="$familyStatuses/*"/>
                          <xsl:with-param name="selectedElement" select="$familyStatusSelected"/>
                          <xsl:with-param name="onchange" select="'FormObjectInitial3.hideOrShowMarriedBlock(this.value);'"/>
                      </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>

                <div id="marriedBlock">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">Фамилия супруга(-и)</xsl:with-param>
                        <xsl:with-param name="rowValue">
                           <input type="text" name="partnerSurname" value="{partnerSurname}" maxlength="120" size="50"/>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">Имя супруга(-и)</xsl:with-param>
                        <xsl:with-param name="rowValue">
                           <input type="text" name="partnerFirstname" value="{partnerFirstname}" maxlength="120" size="50"/>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'false'"/>
                        <xsl:with-param name="rowName">Отчество супруга(-и)</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="text" name="partnerPatr" value="{partnerPatr}" maxlength="120" size="50"/>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                       <xsl:with-param name="required" select="'true'"/>
                       <xsl:with-param name="rowName">Дата рождения супруга(-и)</xsl:with-param>
                       <xsl:with-param name="rowValue">
                            <input type="text" name="partnerBirthday" value="{dh:formatXsdDateToString(partnerBirthday)}" maxlength="20" size="20" class="dot-date-pick"/>
                       </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">Супруг находится на иждивении</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="partnerOnDependent" select="partnerOnDependent"/>
                            <div class="textTick">
                                <input class="float" type="radio" name="partnerOnDependent" value="true" id="partnerOnDependentY">
                                    <xsl:if test="$partnerOnDependent = 'true'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label class="float" for="partnerOnDependentY">Да &nbsp;</label>
                            </div>
                            <div class="textTick">
                                <input class="float" type="radio" name="partnerOnDependent" value="false" id="partnerOnDependentN">
                                    <xsl:if test="$partnerOnDependent= 'false'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label class="float" for="partnerOnDependentN">Нет</label>
                            </div>
                        </xsl:with-param>
                        <xsl:with-param name="isAllocate" select="'false'"/>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">Супруг имеет кредиты в Сбербанке</xsl:with-param>
                        <xsl:with-param name="rowValue">
                           <xsl:variable name="partnerHasLoansInSber" select="partnerHasLoansInSber"/>
                            <div class="textTick">
                               <input class="float" type="radio" name="partnerHasLoansInSber" value="1" id="loansInSber1">
                                   <xsl:if test="$partnerHasLoansInSber = '1'">
                                       <xsl:attribute name="checked">true</xsl:attribute>
                                   </xsl:if>
                               </input>
                               <label class="float" for="loansInSber1">Да &nbsp;</label>
                            </div>
                            <div class="textTick">
                               <input class="float" type="radio" name="partnerHasLoansInSber" value="2" id="loansInSber2">
                                   <xsl:if test="$partnerHasLoansInSber= '2'">
                                       <xsl:attribute name="checked">true</xsl:attribute>
                                   </xsl:if>
                               </input>
                               <label class="float" for="loansInSber2">Нет &nbsp;</label>
                            </div>
                            <div class="textTick">
                               <input class="float" type="radio" name="partnerHasLoansInSber" value="3" id="loansInSber3">
                                  <xsl:if test="$partnerHasLoansInSber= '3'">
                                      <xsl:attribute name="checked">true</xsl:attribute>
                                  </xsl:if>
                               </input>
                               <label class="float" for="loansInSber3">Не знаю</label>
                            </div>
                        </xsl:with-param>
                        <xsl:with-param name="isAllocate" select="'false'"/>
                    </xsl:call-template>

                    <div id="needHavePrenup">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="required" select="'true'"/>
                            <xsl:with-param name="rowName">Есть ли брачный контракт</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <xsl:variable name="hasPrenup" select="hasPrenup"/>
                                <div class="textTick">
                                   <input class="float" type="radio" name="hasPrenup" value="true" id="hasPrenup1">
                                       <xsl:if test="$hasPrenup = 'true'">
                                           <xsl:attribute name="checked">true</xsl:attribute>
                                       </xsl:if>
                                   </input>
                                   <label class="float" for="hasPrenup1">Да &nbsp;</label>
                                </div>
                                <div class="textTick">
                                    <input class="float" type="radio" name="hasPrenup" value="false" id="hasPrenup2">
                                        <xsl:if test="$hasPrenup= 'false'">
                                            <xsl:attribute name="checked">true</xsl:attribute>
                                        </xsl:if>
                                    </input>
                                    <label class="float" for="hasPrenup2">Нет</label>
                                </div>
                            </xsl:with-param>
                            <xsl:with-param name="isAllocate" select="'false'"/>
                        </xsl:call-template>
                    </div>

                </div>


                <script type="text/javascript">

                   var FormObjectInitial3 =
                   {
                       marriedBlock: null,
                       havePrenupBlock: null,

                       init: function()
                       {
                           this.marriedBlock = ensureElement('marriedBlock');
                           this.havePrenupBlock = ensureElement('needHavePrenup');
                           this.hideOrShowMarriedBlock(ensureElement('familyStatusSelect').value);
                       },

                    hideOrShowMarriedBlock: function(familyStatus)
                    {
                         var spouseInfoRequiredSourceCodes = [];
                         var haveNeedPrenupCodes = [];
                         <xsl:for-each select="$familyStatuses/family-status">
                             <xsl:if test="spouseInfoRequired != 'NOT'">
                                 spouseInfoRequiredSourceCodes.push('<xsl:value-of select="code"/>');
                             </xsl:if>
                             <xsl:if test="spouseInfoRequired = 'REQUIRED'">
                                 haveNeedPrenupCodes.push('<xsl:value-of select="code"/>');
                             </xsl:if>
                         </xsl:for-each>

                         if (spouseInfoRequiredSourceCodes.contains(familyStatus))
                             hideOrShow(this.marriedBlock, false);
                         else
                             hideOrShow(this.marriedBlock, true);

                         if (haveNeedPrenupCodes.contains(familyStatus))
                             hideOrShow(this.havePrenupBlock, false);
                         else
                             hideOrShow(this.havePrenupBlock, true);
                    }

                   };

                 $(document).ready(function(){
                    window.scrollTo(0, 0);
                    FormObjectInitial3.init();
                    $('.loan-claim-button .buttonSelect').hover(function(){
                        $(this).addClass('hoverOperation');
                    }, function() {
                       $(this).removeClass('hoverOperation');
                    });
                 });

                </script>

                <xsl:call-template name="button">
                    <xsl:with-param name="id" select="'templateRemoveBtn'"/>
                    <xsl:with-param name="onclick" select="''"/>
                    <xsl:with-param name="btnId" select="'removeBtn'"/>
                    <xsl:with-param name="typeBtn" select="'blueLink'"/>
                </xsl:call-template>

                <!--Значения справочника "родственники" -->
                <xsl:call-template name="familyRelation">
                    <xsl:with-param name="relation" select="$familyRelation"/>
                    <xsl:with-param name="isChild" select="'false'"/>
                    <xsl:with-param name="baseFieldId" select="'relativeTypes'"/>
                </xsl:call-template>

                <!--Значения справочника "степень родства. дети" -->
                <xsl:call-template name="familyRelation">
                    <xsl:with-param name="relation" select="$familyRelation"/>
                    <xsl:with-param name="isChild" select="'true'"/>
                    <xsl:with-param name="baseFieldId" select="'childrenTypes'"/>
                </xsl:call-template>

                <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.js"/>
                <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.childrenBlock.js"/>
                <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.relativesBlock.js"/>

                <!--Область для блоков, предназначенных для добавления информации о детях. Создается в подключаемых скриптах-->
                <div id="childrenArea" class="paymentBigLabel"></div>

                <!--Область для блоков, предназначенных для добавления информации о родственниках. Создается в подключаемых скриптах-->
                <div id="relativeArea" class="paymentBigLabel"></div>

                <div id="temporaryChildrenFields">
                    <input type="hidden" name="childType1" value="{child_1_relativeType}" id="childType1"/>
                    <input type="hidden" name="childSurname1" value="{child_1_surname}" id="childSurname1"/>
                    <input type="hidden" name="childName1" value="{child_1_name}" id="childName1"/>
                    <input type="hidden" name="childPatrName1" value="{child_1_patrName}" id="childPatrName1"/>
                    <input type="hidden" name="childBirthday1" value="{dh:formatXsdDateToString(child_1_birthday)}" id="childBirthday1"/>
                    <input type="hidden" name="childDependent1" value="{child_1_dependent}" id="childDependent1"/>
                    <input type="hidden" name="childCredit1" value="{child_1_credit}" id="childCredit1"/>
                    <input type="hidden" name="childEmployee1" value="{child_1_employee}" id="childEmployee1"/>
                    <input type="hidden" name="childEmployeePlace1" value="{child_1_employeePlace}" id="childEmployeePlace1"/>

                    <input type="hidden" name="childType2" value="{child_2_relativeType}" id="childType2"/>
                    <input type="hidden" name="childSurname2" value="{child_2_surname}" id="childSurname2"/>
                    <input type="hidden" name="childName2" value="{child_2_name}" id="childName2"/>
                    <input type="hidden" name="childPatrName2" value="{child_2_patrName}" id="childPatrName2"/>
                    <input type="hidden" name="childBirthday2" value="{dh:formatXsdDateToString(child_2_birthday)}" id="childBirthday2"/>
                    <input type="hidden" name="childDependent2" value="{child_2_dependent}" id="childDependent2"/>
                    <input type="hidden" name="childCredit2" value="{child_2_credit}" id="childCredit2"/>
                    <input type="hidden" name="childEmployee2" value="{child_2_employee}" id="childEmployee2"/>
                    <input type="hidden" name="childEmployeePlace2" value="{child_2_employeePlace}" id="childEmployeePlace2"/>

                    <input type="hidden" name="childType3" value="{child_3_relativeType}" id="childType3"/>
                    <input type="hidden" name="childSurname3" value="{child_3_surname}" id="childSurname3"/>
                    <input type="hidden" name="childName3" value="{child_3_name}" id="childName3"/>
                    <input type="hidden" name="childPatrName3" value="{child_3_patrName}" id="childPatrName3"/>
                    <input type="hidden" name="childBirthday3" value="{dh:formatXsdDateToString(child_3_birthday)}" id="childBirthday3"/>
                    <input type="hidden" name="childDependent3" value="{child_3_dependent}" id="childDependent3"/>
                    <input type="hidden" name="childCredit3" value="{child_3_credit}" id="childCredit3"/>
                    <input type="hidden" name="childEmployee3" value="{child_3_employee}" id="childEmployee3"/>
                    <input type="hidden" name="childEmployeePlace3" value="{child_3_employeePlace}" id="childEmployeePlace3"/>

                    <input type="hidden" name="childType4" value="{child_4_relativeType}" id="childType4"/>
                    <input type="hidden" name="childSurname4" value="{child_4_surname}" id="childSurname4"/>
                    <input type="hidden" name="childName4" value="{child_4_name}" id="childName4"/>
                    <input type="hidden" name="childPatrName4" value="{child_4_patrName}" id="childPatrName4"/>
                    <input type="hidden" name="childBirthday4" value="{dh:formatXsdDateToString(child_4_birthday)}" id="childBirthday4"/>
                    <input type="hidden" name="childDependent4" value="{child_4_dependent}" id="childDependent4"/>
                    <input type="hidden" name="childCredit4" value="{child_4_credit}" id="childCredit4"/>
                    <input type="hidden" name="childEmployee4" value="{child_4_employee}" id="childEmployee4"/>
                    <input type="hidden" name="childEmployeePlace4" value="{child_4_employeePlace}" id="childEmployeePlace4"/>

                    <input type="hidden" name="childType5" value="{child_5_relativeType}" id="childType5"/>
                    <input type="hidden" name="childSurname5" value="{child_5_surname}" id="childSurname5"/>
                    <input type="hidden" name="childName5" value="{child_5_name}" id="childName5"/>
                    <input type="hidden" name="childPatrName5" value="{child_5_patrName}" id="childPatrName5"/>
                    <input type="hidden" name="childBirthday5" value="{dh:formatXsdDateToString(child_5_birthday)}" id="childBirthday5"/>
                    <input type="hidden" name="childDependent5" value="{child_5_dependent}" id="childDependent5"/>
                    <input type="hidden" name="childCredit5" value="{child_5_credit}" id="childCredit5"/>
                    <input type="hidden" name="childEmployee5" value="{child_5_employee}" id="childEmployee5"/>
                    <input type="hidden" name="childEmployeePlace5" value="{child_5_employeePlace}" id="childEmployeePlace5"/>

                    <input type="hidden" name="childType6" value="{child_6_relativeType}" id="childType6"/>
                    <input type="hidden" name="childSurname6" value="{child_6_surname}" id="childSurname6"/>
                    <input type="hidden" name="childName6" value="{child_6_name}" id="childName6"/>
                    <input type="hidden" name="childPatrName6" value="{child_6_patrName}" id="childPatrName6"/>
                    <input type="hidden" name="childBirthday6" value="{dh:formatXsdDateToString(child_6_birthday)}" id="childBirthday6"/>
                    <input type="hidden" name="childDependent6" value="{child_6_dependent}" id="childDependent6"/>
                    <input type="hidden" name="childCredit6" value="{child_6_credit}" id="childCredit6"/>
                    <input type="hidden" name="childEmployee6" value="{child_6_employee}" id="childEmployee6"/>
                    <input type="hidden" name="childEmployeePlace6" value="{child_6_employeePlace}" id="childEmployeePlace6"/>

                    <input type="hidden" name="childType7" value="{child_7_relativeType}" id="childType7"/>
                    <input type="hidden" name="childSurname7" value="{child_7_surname}" id="childSurname7"/>
                    <input type="hidden" name="childName7" value="{child_7_name}" id="childName7"/>
                    <input type="hidden" name="childPatrName7" value="{child_7_patrName}" id="childPatrName7"/>
                    <input type="hidden" name="childBirthday7" value="{dh:formatXsdDateToString(child_7_birthday)}" id="childBirthday7"/>
                    <input type="hidden" name="childDependent7" value="{child_7_dependent}" id="childDependent7"/>
                    <input type="hidden" name="childCredit7" value="{child_7_credit}" id="childCredit7"/>
                    <input type="hidden" name="childEmployee7" value="{child_7_employee}" id="childEmployee7"/>
                    <input type="hidden" name="childEmployeePlace7" value="{child_7_employeePlace}" id="childEmployeePlace7"/>

                    <input type="hidden" name="childType8" value="{child_8_relativeType}" id="childType8"/>
                    <input type="hidden" name="childSurname8" value="{child_8_surname}" id="childSurname8"/>
                    <input type="hidden" name="childName8" value="{child_8_name}" id="childName8"/>
                    <input type="hidden" name="childPatrName8" value="{child_8_patrName}" id="childPatrName8"/>
                    <input type="hidden" name="childBirthday8" value="{dh:formatXsdDateToString(child_8_birthday)}" id="childBirthday8"/>
                    <input type="hidden" name="childDependent8" value="{child_8_dependent}" id="childDependent8"/>
                    <input type="hidden" name="childCredit8" value="{child_8_credit}" id="childCredit8"/>
                    <input type="hidden" name="childEmployee8" value="{child_8_employee}" id="childEmployee8"/>
                    <input type="hidden" name="childEmployeePlace8" value="{child_8_employeePlace}" id="childEmployeePlace8"/>

                    <input type="hidden" name="childType9" value="{child_9_relativeType}" id="childType9"/>
                    <input type="hidden" name="childSurname9" value="{child_9_surname}" id="childSurname9"/>
                    <input type="hidden" name="childName9" value="{child_9_name}" id="childName9"/>
                    <input type="hidden" name="childPatrName9" value="{child_9_patrName}" id="childPatrName9"/>
                    <input type="hidden" name="childBirthday9" value="{dh:formatXsdDateToString(child_9_birthday)}" id="childBirthday9"/>
                    <input type="hidden" name="childDependent9" value="{child_9_dependent}" id="childDependent9"/>
                    <input type="hidden" name="childCredit9" value="{child_9_credit}" id="childCredit9"/>
                    <input type="hidden" name="childEmployee9" value="{child_9_employee}" id="childEmployee9"/>
                    <input type="hidden" name="childEmployeePlace9" value="{child_9_employeePlace}" id="childEmployeePlace9"/>

                    <input type="hidden" name="childType10" value="{child_10_relativeType}" id="childType10"/>
                    <input type="hidden" name="childSurname10" value="{child_10_surname}" id="childSurname10"/>
                    <input type="hidden" name="childName10" value="{child_10_name}" id="childName10"/>
                    <input type="hidden" name="childPatrName10" value="{child_10_patrName}" id="childPatrName10"/>
                    <input type="hidden" name="childBirthday10" value="{dh:formatXsdDateToString(child_10_birthday)}" id="childBirthday10"/>
                    <input type="hidden" name="childDependent10" value="{child_10_dependent}" id="childDependent10"/>
                    <input type="hidden" name="childCredit10" value="{child_10_credit}" id="childCredit10"/>
                    <input type="hidden" name="childEmployee10" value="{child_10_employee}" id="childEmployee10"/>
                    <input type="hidden" name="childEmployeePlace10" value="{child_10_employeePlace}" id="childEmployeePlace10"/>

                    <input type="hidden" name="childType11" value="{child_11_relativeType}" id="childType11"/>
                    <input type="hidden" name="childSurname11" value="{child_11_surname}" id="childSurname11"/>
                    <input type="hidden" name="childName11" value="{child_11_name}" id="childName11"/>
                    <input type="hidden" name="childPatrName11" value="{child_11_patrName}" id="childPatrName11"/>
                    <input type="hidden" name="childBirthday11" value="{dh:formatXsdDateToString(child_11_birthday)}" id="childBirthday11"/>
                    <input type="hidden" name="childDependent11" value="{child_11_dependent}" id="childDependent11"/>
                    <input type="hidden" name="childCredit11" value="{child_11_credit}" id="childCredit11"/>
                    <input type="hidden" name="childEmployee11" value="{child_11_employee}" id="childEmployee11"/>
                    <input type="hidden" name="childEmployeePlace11" value="{child_11_employeePlace}" id="childEmployeePlace11"/>

                    <input type="hidden" name="childType12" value="{child_12_relativeType}" id="childType12"/>
                    <input type="hidden" name="childSurname12" value="{child_12_surname}" id="childSurname12"/>
                    <input type="hidden" name="childName12" value="{child_12_name}" id="childName12"/>
                    <input type="hidden" name="childPatrName12" value="{child_12_patrName}" id="childPatrName12"/>
                    <input type="hidden" name="childBirthday12" value="{dh:formatXsdDateToString(child_12_birthday)}" id="childBirthday12"/>
                    <input type="hidden" name="childDependent12" value="{child_12_dependent}" id="childDependent12"/>
                    <input type="hidden" name="childCredit12" value="{child_12_credit}" id="childCredit12"/>
                    <input type="hidden" name="childEmployee12" value="{child_12_employee}" id="childEmployee12"/>
                    <input type="hidden" name="childEmployeePlace12" value="{child_12_employeePlace}" id="childEmployeePlace12"/>

                    <input type="hidden" name="relativeType1" value="{relative_1_relativeType}" id="relativeType1"/>
                    <input type="hidden" name="relativeSurname1" value="{relative_1_surname}" id="relativeSurname1"/>
                    <input type="hidden" name="relativeName1" value="{relative_1_name}" id="relativeName1"/>
                    <input type="hidden" name="relativePatrName1" value="{relative_1_patrName}" id="relativePatrName1"/>
                    <input type="hidden" name="relativeBirthday1" value="{dh:formatXsdDateToString(relative_1_birthday)}" id="relativeBirthday1"/>
                    <input type="hidden" name="relativeDependent1" value="{relative_1_dependent}" id="relativeDependent1"/>
                    <input type="hidden" name="relativeCredit1" value="{relative_1_credit}" id="relativeCredit1"/>
                    <input type="hidden" name="relativeEmployee1" value="{relative_1_employee}" id="relativeEmployee1"/>
                    <input type="hidden" name="relativeEmployeePlace1" value="{relative_1_employeePlace}" id="relativeEmployeePlace1"/>

                    <input type="hidden" name="relativeType2" value="{relative_2_relativeType}" id="relativeType2"/>
                    <input type="hidden" name="relativeSurname2" value="{relative_2_surname}" id="relativeSurname2"/>
                    <input type="hidden" name="relativeName2" value="{relative_2_name}" id="relativeName2"/>
                    <input type="hidden" name="relativePatrName2" value="{relative_2_patrName}" id="relativePatrName2"/>
                    <input type="hidden" name="relativeBirthday2" value="{dh:formatXsdDateToString(relative_2_birthday)}" id="relativeBirthday2"/>
                    <input type="hidden" name="relativeDependent2" value="{relative_2_dependent}" id="relativeDependent2"/>
                    <input type="hidden" name="relativeCredit2" value="{relative_2_credit}" id="relativeCredit2"/>
                    <input type="hidden" name="relativeEmployee2" value="{relative_2_employee}" id="relativeEmployee2"/>
                    <input type="hidden" name="relativeEmployeePlace2" value="{relative_2_employeePlace}" id="relativeEmployeePlace2"/>

                    <input type="hidden" name="relativeType3" value="{relative_3_relativeType}" id="relativeType3"/>
                    <input type="hidden" name="relativeSurname3" value="{relative_3_surname}" id="relativeSurname3"/>
                    <input type="hidden" name="relativeName3" value="{relative_3_name}" id="relativeName3"/>
                    <input type="hidden" name="relativePatrName3" value="{relative_3_patrName}" id="relativePatrName3"/>
                    <input type="hidden" name="relativeBirthday3" value="{dh:formatXsdDateToString(relative_3_birthday)}" id="relativeBirthday3"/>
                    <input type="hidden" name="relativeDependent3" value="{relative_3_dependent}" id="relativeDependent3"/>
                    <input type="hidden" name="relativeCredit3" value="{relative_3_credit}" id="relativeCredit3"/>
                    <input type="hidden" name="relativeEmployee3" value="{relative_3_employee}" id="relativeEmployee3"/>
                    <input type="hidden" name="relativeEmployeePlace3" value="{relative_3_employeePlace}" id="relativeEmployeePlace3"/>

                    <input type="hidden" name="relativeType4" value="{relative_4_relativeType}" id="relativeType4"/>
                    <input type="hidden" name="relativeSurname4" value="{relative_4_surname}" id="relativeSurname4"/>
                    <input type="hidden" name="relativeName4" value="{relative_4_name}" id="relativeName4"/>
                    <input type="hidden" name="relativePatrName4" value="{relative_4_patrName}" id="relativePatrName4"/>
                    <input type="hidden" name="relativeBirthday4" value="{dh:formatXsdDateToString(relative_4_birthday)}" id="relativeBirthday4"/>
                    <input type="hidden" name="relativeDependent4" value="{relative_4_dependent}" id="relativeDependent4"/>
                    <input type="hidden" name="relativeCredit4" value="{relative_4_credit}" id="relativeCredit4"/>
                    <input type="hidden" name="relativeEmployee4" value="{relative_4_employee}" id="relativeEmployee4"/>
                    <input type="hidden" name="relativeEmployeePlace4" value="{relative_4_employeePlace}" id="relativeEmployeePlace4"/>

                    <input type="hidden" name="relativeType5" value="{relative_5_relativeType}" id="relativeType5"/>
                    <input type="hidden" name="relativeSurname5" value="{relative_5_surname}" id="relativeSurname5"/>
                    <input type="hidden" name="relativeName5" value="{relative_5_name}" id="relativeName5"/>
                    <input type="hidden" name="relativePatrName5" value="{relative_5_patrName}" id="relativePatrName5"/>
                    <input type="hidden" name="relativeBirthday5" value="{dh:formatXsdDateToString(relative_5_birthday)}" id="relativeBirthday5"/>
                    <input type="hidden" name="relativeDependent5" value="{relative_5_dependent}" id="relativeDependent5"/>
                    <input type="hidden" name="relativeCredit5" value="{relative_5_credit}" id="relativeCredit5"/>
                    <input type="hidden" name="relativeEmployee5" value="{relative_5_employee}" id="relativeEmployee5"/>
                    <input type="hidden" name="relativeEmployeePlace5" value="{relative_5_employeePlace}" id="relativeEmployeePlace5"/>

                    <input type="hidden" name="relativeType6" value="{relative_6_relativeType}" id="relativeType6"/>
                    <input type="hidden" name="relativeSurname6" value="{relative_6_surname}" id="relativeSurname6"/>
                    <input type="hidden" name="relativeName6" value="{relative_6_name}" id="relativeName6"/>
                    <input type="hidden" name="relativePatrName6" value="{relative_6_patrName}" id="relativePatrName6"/>
                    <input type="hidden" name="relativeBirthday6" value="{dh:formatXsdDateToString(relative_6_birthday)}" id="relativeBirthday6"/>
                    <input type="hidden" name="relativeDependent6" value="{relative_6_dependent}" id="relativeDependent6"/>
                    <input type="hidden" name="relativeCredit6" value="{relative_6_credit}" id="relativeCredit6"/>
                    <input type="hidden" name="relativeEmployee6" value="{relative_6_employee}" id="relativeEmployee6"/>
                    <input type="hidden" name="relativeEmployeePlace6" value="{relative_6_employeePlace}" id="relativeEmployeePlace6"/>

                    <input type="hidden" name="relativeType7" value="{relative_7_relativeType}" id="relativeType7"/>
                    <input type="hidden" name="relativeSurname7" value="{relative_7_surname}" id="relativeSurname7"/>
                    <input type="hidden" name="relativeName7" value="{relative_7_name}" id="relativeName7"/>
                    <input type="hidden" name="relativePatrName7" value="{relative_7_patrName}" id="relativePatrName7"/>
                    <input type="hidden" name="relativeBirthday7" value="{dh:formatXsdDateToString(relative_7_birthday)}" id="relativeBirthday7"/>
                    <input type="hidden" name="relativeDependent7" value="{relative_7_dependent}" id="relativeDependent7"/>
                    <input type="hidden" name="relativeCredit7" value="{relative_7_credit}" id="relativeCredit7"/>
                    <input type="hidden" name="relativeEmployee7" value="{relative_7_employee}" id="relativeEmployee7"/>
                    <input type="hidden" name="relativeEmployeePlace7" value="{relative_7_employeePlace}" id="relativeEmployeePlace7"/>

                    <input type="hidden" name="relativeType8" value="{relative_8_relativeType}" id="relativeType8"/>
                    <input type="hidden" name="relativeSurname8" value="{relative_8_surname}" id="relativeSurname8"/>
                    <input type="hidden" name="relativeName8" value="{relative_8_name}" id="relativeName8"/>
                    <input type="hidden" name="relativePatrName8" value="{relative_8_patrName}" id="relativePatrName8"/>
                    <input type="hidden" name="relativeBirthday8" value="{dh:formatXsdDateToString(relative_8_birthday)}" id="relativeBirthday8"/>
                    <input type="hidden" name="relativeDependent8" value="{relative_8_dependent}" id="relativeDependent8"/>
                    <input type="hidden" name="relativeCredit8" value="{relative_8_credit}" id="relativeCredit8"/>
                    <input type="hidden" name="relativeEmployee8" value="{relative_8_employee}" id="relativeEmployee8"/>
                    <input type="hidden" name="relativeEmployeePlace8" value="{relative_8_employeePlace}" id="relativeEmployeePlace8"/>

                    <input type="hidden" name="relativeType9" value="{relative_9_relativeType}" id="relativeType9"/>
                    <input type="hidden" name="relativeSurname9" value="{relative_9_surname}" id="relativeSurname9"/>
                    <input type="hidden" name="relativeName9" value="{relative_9_name}" id="relativeName9"/>
                    <input type="hidden" name="relativePatrName9" value="{relative_9_patrName}" id="relativePatrName9"/>
                    <input type="hidden" name="relativeBirthday9" value="{dh:formatXsdDateToString(relative_9_birthday)}" id="relativeBirthday9"/>
                    <input type="hidden" name="relativeDependent9" value="{relative_9_dependent}" id="relativeDependent9"/>
                    <input type="hidden" name="relativeCredit9" value="{relative_9_credit}" id="relativeCredit9"/>
                    <input type="hidden" name="relativeEmployee9" value="{relative_9_employee}" id="relativeEmployee9"/>
                    <input type="hidden" name="relativeEmployeePlace9" value="{relative_9_employeePlace}" id="relativeEmployeePlace9"/>

                    <input type="hidden" name="relativeType10" value="{relative_10_relativeType}" id="relativeType10"/>
                    <input type="hidden" name="relativeSurname10" value="{relative_10_surname}" id="relativeSurname10"/>
                    <input type="hidden" name="relativeName10" value="{relative_10_name}" id="relativeName10"/>
                    <input type="hidden" name="relativePatrName10" value="{relative_10_patrName}" id="relativePatrName10"/>
                    <input type="hidden" name="relativeBirthday10" value="{dh:formatXsdDateToString(relative_10_birthday)}" id="relativeBirthday10"/>
                    <input type="hidden" name="relativeDependent10" value="{relative_10_dependent}" id="relativeDependent10"/>
                    <input type="hidden" name="relativeCredit10" value="{relative_10_credit}" id="relativeCredit10"/>
                    <input type="hidden" name="relativeEmployee10" value="{relative_10_employee}" id="relativeEmployee10"/>
                    <input type="hidden" name="relativeEmployeePlace10" value="{relative_10_employeePlace}" id="relativeEmployeePlace10"/>

                    <input type="hidden" name="relativeType11" value="{relative_11_relativeType}" id="relativeType11"/>
                    <input type="hidden" name="relativeSurname11" value="{relative_11_surname}" id="relativeSurname11"/>
                    <input type="hidden" name="relativeName11" value="{relative_11_name}" id="relativeName11"/>
                    <input type="hidden" name="relativePatrName11" value="{relative_11_patrName}" id="relativePatrName11"/>
                    <input type="hidden" name="relativeBirthday11" value="{dh:formatXsdDateToString(relative_11_birthday)}" id="relativeBirthday11"/>
                    <input type="hidden" name="relativeDependent11" value="{relative_11_dependent}" id="relativeDependent11"/>
                    <input type="hidden" name="relativeCredit11" value="{relative_11_credit}" id="relativeCredit11"/>
                    <input type="hidden" name="relativeEmployee11" value="{relative_11_employee}" id="relativeEmployee11"/>
                    <input type="hidden" name="relativeEmployeePlace11" value="{relative_11_employeePlace}" id="relativeEmployeePlace11"/>

                    <input type="hidden" name="relativeType12" value="{relative_12_relativeType}" id="relativeType12"/>
                    <input type="hidden" name="relativeSurname12" value="{relative_12_surname}" id="relativeSurname12"/>
                    <input type="hidden" name="relativeName12" value="{relative_12_name}" id="relativeName12"/>
                    <input type="hidden" name="relativePatrName12" value="{relative_12_patrName}" id="relativePatrName12"/>
                    <input type="hidden" name="relativeBirthday12" value="{dh:formatXsdDateToString(relative_12_birthday)}" id="relativeBirthday12"/>
                    <input type="hidden" name="relativeDependent12" value="{relative_12_dependent}" id="relativeDependent12"/>
                    <input type="hidden" name="relativeCredit12" value="{relative_12_credit}" id="relativeCredit12"/>
                    <input type="hidden" name="relativeEmployee12" value="{relative_12_employee}" id="relativeEmployee12"/>
                    <input type="hidden" name="relativeEmployeePlace12" value="{relative_12_employeePlace}" id="relativeEmployeePlace12"/>
                 </div>

                <script type="text/javascript">
                    <!--Переменные, в которых хранятся блоки для введения данных о детях и родственниках-->
                    var children;
                    var relatives;
                    var maxTotalBlockCount = 12;

                    $(document).ready(function(){
                    window.scrollTo(0, 0);
                    <!--Собрать введенную информацию о детях и создать область для введения данных о детях-->
                    var childInfo = fillSavedInfo('child', 12);
                    children = createChildrenList(12, childInfo);

                    <!--Собрать введенную информацию о родственниках и создать область для введения данных о родственниках-->
                    var relativesInfo = fillSavedInfo('relative', 12);
                    relatives = createRelativesList(12, relativesInfo);

                    $('input[name="addNewBlockBtn"]').click(reserveRemoveBtnFunction);
                    reserveRemoveBtnFunction();
                        $('.loan-claim-button .buttonSelect').hover(function(){
                            $(this).addClass('hoverOperation');
                        }, function() {
                           $(this).removeClass('hoverOperation');
                        });
                    });

                    <!--Функция, делающая активными/неактивными кнопки добавления блоков. Переопределяет ф-ю fbLib.js-->
                    function reserveRemoveBtnFunction()
                    {
                        var childBlocks = children.componentList.length;
                        var relativeBlocks = relatives.componentList.length;

                        var fillBlocksCount = childBlocks + relativeBlocks;

                        if (fillBlocksCount >= maxTotalBlockCount)
                        {
                            children.addNewChildButton.disabled = "true";
                            relatives.addNewChildButton.disabled = "true";
                        }
                        else
                        {
                            if (childBlocks &lt; children.maxBlockCount)
                                children.addNewChildButton.disabled = "";
                            if (relativeBlocks &lt; relatives.maxBlockCount)
                                relatives.addNewChildButton.disabled = "";
                        }
                    }

                    function fillSavedInfo(fieldPrefix, maxCount)
                    {
                        var info = [];
                        for (var i=1; i &lt; maxCount+1; i++)
                        {
                            var data = {};

                            var familyRelationType = ensureElement(fieldPrefix+"Type"+i).value;
                            if (familyRelationType != "")
                            {
                                data.relativeType = familyRelationType;

                                var surname = ensureElement(fieldPrefix+"Surname"+i).value;
                                if (surname != "")
                                    data.surname = surname;

                                var name = ensureElement(fieldPrefix+"Name"+i).value;
                                if (name != "")
                                    data.name = name;

                                var patrName = ensureElement(fieldPrefix+"PatrName"+i).value;
                                if (patrName != "")
                                    data.patrName = patrName;

                                var birthday = ensureElement(fieldPrefix+"Birthday"+i).value;
                                if (birthday != "")
                                    data.birthday = birthday;

                                var dependent = ensureElement(fieldPrefix+"Dependent"+i).value;
                                if (dependent != "")
                                    data.dependent = dependent;

                                var credit = ensureElement(fieldPrefix+"Credit"+i).value;
                                if (credit != "")
                                    data.credit = credit;

                                var employee = ensureElement(fieldPrefix+"Employee"+i).value;
                                if (employee != "")
                                    data.employee = employee;

                                var employeePlace = ensureElement(fieldPrefix+"EmployeePlace"+i).value;
                                if (employeePlace != "")
                                    data.employeePlace = employeePlace;

                                info.push(data);
                            }
                        }
                        return info;
                    }
                    <!--Валидирующая ф-я, привязанная к кнопке save-->
                    function checkPayment()
                    {
                        children.updateInputInfo();
                        relatives.updateInputInfo();
                        $('#temporaryChildrenFields').remove();
                        $('div#marriedBlock:hidden').remove();
                        // удаляем скрытые поля
                        $('div:hidden').find('input').remove();
                        return true;
                    }
                </script>



            </xsl:when>
            <!--
                *********************************************************************************************
                                                4 шаг: Прописка
                *********************************************************************************************
            -->
            <xsl:when test="$documentState = 'INITIAL4'">
                <script type="text/javascript">
                    function codeAsObject(code)
                    {
                        var reg    = new RegExp("(\\d{1,4})(\\d{1,4})(\\d{1,4})(\\d{1,4})(\\d{1,4})");
                        var result = null;
                        if ((result = reg.exec(code)))
                        {
                            return {
                                region:   result[1],
                                area:     result[2].replace(0, ''),
                                city:     result[3].replace(0, ''),
                                location: result[4].replace(0, ''),
                                street:   result[5].replace(0, '')
                            }
                        }
                    }

                    /**
                        Получить код региона (района, города, населенного пункта, улицы) по индексу.
                        Код разбиваем на массив строк и получаем нужный по индексу.
                        @param code составной код, содержащий в себе коды района, города...
                        @param index индекс кода района, города...
                    */
                    function codeByIndex(code, index)
                    {
                        var reg    = new RegExp("(\\d{1,4})(\\d{1,4})(\\d{1,4})(\\d{1,4})(\\d{1,4})");
                        var result = null;
                        if ((result = reg.exec(code)))
                            return index == 1 ? result[1] : result[index].replace(0, '');

                    }

                    function changeAddressFieldType(fnName, index, type)
                    {
                        if (fnName &amp;&amp; typeof fnName == 'string')
                        {
                            var changeTypeFn = window[fnName + index];
                            if (changeTypeFn &amp;&amp; typeof changeTypeFn == 'function')
                            {
                                changeTypeFn(type);
                            }
                        }
                    }

                    function addQueryParameters(name, parameter, fields)
                    {
                        for (var i=0; i&lt;fields.length; i++)
                        {
                            fields[i].addParameter(name, parameter);
                        }
                    }

                    function getQueryParameters(codeAsString, index)
                    {
                        var codes = codeAsString.split(",");
                        var parameterValue = "";
                        for (var i = 0; i&lt;codes.length; i++)
                            parameterValue += codeByIndex(codes[i], index) + ",";
                        return parameterValue.substring(0, parameterValue.length - 1);
                    }

                    function onSelectRegion(selected)
                    {
                        var region = selected ? selected.key : '';

                        if (arguments.length > 1)
                        {
                            var args = Array.prototype.slice.call(arguments, 1);
                            addQueryParameters('field(region)', region, args);
                        }
                    }

                    function onSelectArea(selected, index)
                    {
                        var parameterValue = selected.codes;

                        var code = codeAsObject(selected.code);
                        var area = selected ? selected.key : '';

                        if (arguments.length > 2)
                        {
                            var args = Array.prototype.slice.call(arguments, 2);
                            addQueryParameters('field(area)', parameterValue, args);
                        }

                        var areaCodeElement = document.getElementById('districtCode' + index);
                        if (areaCodeElement)
                        {
                           areaCodeElement.value = selected.code;
                        }

                        var areaTypeElement = document.getElementById('districtTypeSelect' + index);
                        if (areaTypeElement)
                        {
                            $(areaTypeElement).val(selected.type);
                            changeAddressFieldType('changeDistrictTypeSelect', index, selected.type);
                        }
                    }

                    function clearArea(selected, index)
                    {
                        if (arguments.length > 2)
                        {
                            var args = Array.prototype.slice.call(arguments, 2);
                            addQueryParameters('field(area)', '', args);
                        }
                        var areaCodeElement = document.getElementById('districtCode' + index);
                        if (areaCodeElement)
                        {
                           areaCodeElement.value = '';
                        }
                        $("input[name=district" + index + "]").val("");
                    }

                    function keyDownArea(index)
                    {
                        $('#districtTypeSelect' + index).val('');
                        changeAddressFieldType('changeDistrictTypeSelect', index, '');
                    }

                    function onSelectCity(selected, index)
                    {
                        var parameterValue = selected.codes;

                        var code            = codeAsObject(selected.code);
                        var city            = selected ? selected.key : '';

                        if (arguments.length > 2)
                        {
                            var args = Array.prototype.slice.call(arguments, 2);
                            addQueryParameters('field(city)', parameterValue, args);
                        }

                        var cityCodeElement = document.getElementById('cityCode' + index);
                        if (cityCodeElement)
                        {
                           cityCodeElement.value = selected.code;
                        }

                        var cityTypeElement = document.getElementById('cityTypeSelect' + index);
                        if (cityTypeElement)
                        {
                            $(cityTypeElement).val(selected.type);
                            changeAddressFieldType('changeCityTypeSelect', index, selected.type);
                        }
                    }

                    function clearCity(selected, index)
                    {
                        if (arguments.length > 2)
                        {
                            var args = Array.prototype.slice.call(arguments, 2);
                            addQueryParameters('field(city)', '', args);
                        }
                        var codeElement = document.getElementById('cityCode' + index);
                        if (codeElement)
                        {
                           codeElement.value = '';
                        }
                        $("input[name=city" + index + "]").val("");
                    }

                    function keyDownCity(index)
                    {
                        $('#cityTypeSelect' + index).val('');
                        changeAddressFieldType('changeCityTypeSelect', index, '');
                    }

                    function onSelectLocality(selected, index)
                    {
                        var parameterValue = selected.codes;

                        var code           = codeAsObject(selected.code);
                        var city           = selected ? selected.key : '';

                        if (arguments.length > 2)
                        {
                            var args = Array.prototype.slice.call(arguments, 2);
                            addQueryParameters('field(settlement)', parameterValue, args);
                        }

                        var localityCodeElement = document.getElementById('localityCode' + index);
                        if (localityCodeElement)
                        {
                           localityCodeElement.value = selected.code;
                        }

                        var locTypeElement = document.getElementById('localityTypeSelect' + index);
                        if (locTypeElement)
                        {
                            $(locTypeElement).val(selected.type);
                            changeAddressFieldType('changeLocalityTypeSelect', index, selected.type);
                        }
                    }

                    function clearLocality(selected, index)
                    {
                        if (arguments.length > 2)
                        {
                            var args = Array.prototype.slice.call(arguments, 2);
                            addQueryParameters('field(settlement)', '', args);
                        }
                        var codeElement = document.getElementById('localityCode' + index);
                        if (codeElement)
                        {
                           codeElement.value = '';
                        }
                        $("input[name=locality" + index + "]").val("");
                    }

                    function keyDownLocality(index)
                    {
                        $('#localityTypeSelect' + index).val('');
                        changeAddressFieldType('changeLocalityTypeSelect', index, '');
                    }

                    function onSelectStreet(selected, index)
                    {
                        var code              = codeAsObject(selected.code);
                        var city              = selected ? selected.key : '';
                        var streetTypeElement = document.getElementById('streetTypeSelect' + index);

                        if (streetTypeElement)
                        {
                            $(streetTypeElement).val(selected.type);
                            changeAddressFieldType('changeStreetTypeSelect', index, selected.type);
                        }
                    }

                    function clearStreet(selected, index)
                    {
                        $("input[name=street" + index + "]").val("");
                    }

                    function checkWithFlatNum(element, blockNum)
                    {
                        if (element.checked)
                        {
                            element.value = "true";
                            var el = document.getElementById('flat'+blockNum);
                            el.setAttribute('disabled', 'disabled');
                        }
                        else
                        {
                            element.value = "false";
                            var el = document.getElementById('flat'+blockNum);
                            el.removeAttribute('disabled');
                        }
                    }

                </script>

                <div class="Title">
                    <span class="size24">Прописка</span>
                </div>
                <xsl:call-template name="titleRow">
                   <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Адрес регистрации</b></xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Тип регистрации</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        Постоянная
                    </xsl:with-param>
                    <xsl:with-param name="isAllocate" select="'false'"/>
                </xsl:call-template>

                <div id="firstAddress" class="liveSearchContainer">
                    <xsl:call-template name="addressInfo">
                        <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                        <xsl:with-param name="blockIndex">1</xsl:with-param>
                        <xsl:with-param name="registrationTypeValue"><xsl:value-of select="'FIXED'"/></xsl:with-param>
                        <xsl:with-param name="regionSelectFieldValue"><xsl:value-of select="regionSelect1"/></xsl:with-param>
                        <xsl:with-param name="districtTypeSelectValue"><xsl:value-of select="districtTypeSelect1"/></xsl:with-param>
                        <xsl:with-param name="districtCodeFieldValue"><xsl:value-of select="districtCode1"/></xsl:with-param>
                        <xsl:with-param name="districtFieldValue"><xsl:value-of select="district1"/></xsl:with-param>
                        <xsl:with-param name="cityCodeFieldValue"><xsl:value-of select="cityCode1"/></xsl:with-param>
                        <xsl:with-param name="cityTypeSelectValue"><xsl:value-of select="cityTypeSelect1"/></xsl:with-param>
                        <xsl:with-param name="cityFieldValue"><xsl:value-of select="city1"/></xsl:with-param>
                        <xsl:with-param name="localityTypeSelectValue"><xsl:value-of select="localityTypeSelect1"/></xsl:with-param>
                        <xsl:with-param name="localityFieldValue"><xsl:value-of select="locality1"/></xsl:with-param>
                        <xsl:with-param name="localityCodeFieldValue"><xsl:value-of select="localityCode1"/></xsl:with-param>
                        <xsl:with-param name="streetTypeSelectValue"><xsl:value-of select="streetTypeSelect1"/></xsl:with-param>
                        <xsl:with-param name="streetFieldValue"><xsl:value-of select="street1"/></xsl:with-param>
                        <xsl:with-param name="houseFieldValue"><xsl:value-of select="house1"/></xsl:with-param>
                        <xsl:with-param name="buildingFieldValue"><xsl:value-of select="building1"/></xsl:with-param>
                        <xsl:with-param name="constructionFieldValue"><xsl:value-of select="construction1"/></xsl:with-param>
                        <xsl:with-param name="flatFieldValue"><xsl:value-of select="flat1"/></xsl:with-param>
                        <xsl:with-param name="flatWithNumFieldValue"><xsl:value-of select="flatWithNum1"/></xsl:with-param>
                        <xsl:with-param name="indexFieldValue"><xsl:value-of select="index1"/></xsl:with-param>
                        <xsl:with-param name="webRoot" select="$webRoot"/>
                    </xsl:call-template>
                </div>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">&nbsp;</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="isAllocate" select="false()"/>
                    <xsl:with-param name="rowValue">
                        <div id="addSecondAddress" class="loan-claim-button second-address buttWhite" onclick="formObject.addSecondAddressBlock();">
                           <div class="buttonSelect">
                                <div class="buttonSelectLeft"></div>
                                <div class="buttonSelectCenter">
                                    Добавить временную регистрацию
                                </div>
                                <div class="buttonSelectRight"></div>
                                <div class="clear"></div>
                           </div>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>

                <!--Дополнительный адрес регистрации-->
                <div id="secondAddress" class="liveSearchContainer" style="display:none;">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="rowValue">
                            <span id="secondAddressTitle" class="paymentTextlabel blockTitle">Информация о временной регистрации</span>
                            <xsl:call-template name="button">
                                <xsl:with-param name="style" select="'display: inline;'"/>
                                <xsl:with-param name="onclick" select="'formObject.removeSecondAddressBlock();'"/>
                                <xsl:with-param name="typeBtn" select="'blueLink'"/>
                            </xsl:call-template>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="addressInfo">
                        <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                        <xsl:with-param name="blockIndex">2</xsl:with-param>
                        <xsl:with-param name="registrationTypeValue"><xsl:value-of select="registrationType2"/></xsl:with-param>
                        <xsl:with-param name="regionSelectFieldValue"><xsl:value-of select="regionSelect2"/></xsl:with-param>
                        <xsl:with-param name="districtTypeSelectValue"><xsl:value-of select="districtTypeSelect2"/></xsl:with-param>
                        <xsl:with-param name="districtCodeFieldValue"><xsl:value-of select="districtCode2"/></xsl:with-param>
                        <xsl:with-param name="districtFieldValue"><xsl:value-of select="district2"/></xsl:with-param>
                        <xsl:with-param name="cityCodeFieldValue"><xsl:value-of select="cityCode2"/></xsl:with-param>
                        <xsl:with-param name="cityTypeSelectValue"><xsl:value-of select="cityTypeSelect2"/></xsl:with-param>
                        <xsl:with-param name="cityFieldValue"><xsl:value-of select="city2"/></xsl:with-param>
                        <xsl:with-param name="localityTypeSelectValue"><xsl:value-of select="localityTypeSelect2"/></xsl:with-param>
                        <xsl:with-param name="localityFieldValue"><xsl:value-of select="locality2"/></xsl:with-param>
                        <xsl:with-param name="localityCodeFieldValue"><xsl:value-of select="localityCode2"/></xsl:with-param>
                        <xsl:with-param name="streetTypeSelectValue"><xsl:value-of select="streetTypeSelect2"/></xsl:with-param>
                        <xsl:with-param name="streetFieldValue"><xsl:value-of select="street2"/></xsl:with-param>
                        <xsl:with-param name="houseFieldValue"><xsl:value-of select="house2"/></xsl:with-param>
                        <xsl:with-param name="buildingFieldValue"><xsl:value-of select="building2"/></xsl:with-param>
                        <xsl:with-param name="constructionFieldValue"><xsl:value-of select="construction2"/></xsl:with-param>
                        <xsl:with-param name="flatFieldValue"><xsl:value-of select="flat2"/></xsl:with-param>
                        <xsl:with-param name="flatWithNumFieldValue"><xsl:value-of select="flatWithNum2"/></xsl:with-param>
                        <xsl:with-param name="indexFieldValue"><xsl:value-of select="index2"/></xsl:with-param>
                        <xsl:with-param name="webRoot" select="$webRoot"/>
                    </xsl:call-template>
                </div>

                <!--Информация о фактическом месте проживания-->
                <xsl:call-template name="titleRow">
                   <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Адрес фактического проживания</b></xsl:with-param>
                </xsl:call-template>

                <script type="text/javascript" src="{$resourceRoot}/scripts/extendedLoanClaim.step3.js"/>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Адрес проживания</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <select id="factAddressTypeSelect" name="registrationType3" style="width: 370px;" onchange="formObject.addFactAddressBlock();">
                            <option value="FACT">Другой</option>
                        </select>
                    </xsl:with-param>
                    <xsl:with-param name="isAllocate" select="'false'"/>
                </xsl:call-template>

                <div id="factAddress" style="display:none;">
                    <xsl:call-template name="addressInfo">
                        <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                        <xsl:with-param name="blockIndex">3</xsl:with-param>
                        <xsl:with-param name="registrationTypeValue"><xsl:value-of select="registrationType3"/></xsl:with-param>
                        <xsl:with-param name="regionSelectFieldValue"><xsl:value-of select="regionSelect3"/></xsl:with-param>
                        <xsl:with-param name="districtTypeSelectValue"><xsl:value-of select="districtTypeSelect3"/></xsl:with-param>
                        <xsl:with-param name="districtCodeFieldValue"><xsl:value-of select="districtCode3"/></xsl:with-param>
                        <xsl:with-param name="districtFieldValue"><xsl:value-of select="district3"/></xsl:with-param>
                        <xsl:with-param name="cityCodeFieldValue"><xsl:value-of select="cityCode3"/></xsl:with-param>
                        <xsl:with-param name="cityTypeSelectValue"><xsl:value-of select="cityTypeSelect3"/></xsl:with-param>
                        <xsl:with-param name="cityFieldValue"><xsl:value-of select="city3"/></xsl:with-param>
                        <xsl:with-param name="localityTypeSelectValue"><xsl:value-of select="localityTypeSelect3"/></xsl:with-param>
                        <xsl:with-param name="localityFieldValue"><xsl:value-of select="locality3"/></xsl:with-param>
                        <xsl:with-param name="localityCodeFieldValue"><xsl:value-of select="localityCode3"/></xsl:with-param>
                        <xsl:with-param name="streetTypeSelectValue"><xsl:value-of select="streetTypeSelect3"/></xsl:with-param>
                        <xsl:with-param name="streetFieldValue"><xsl:value-of select="street3"/></xsl:with-param>
                        <xsl:with-param name="houseFieldValue"><xsl:value-of select="house3"/></xsl:with-param>
                        <xsl:with-param name="buildingFieldValue"><xsl:value-of select="building3"/></xsl:with-param>
                        <xsl:with-param name="constructionFieldValue"><xsl:value-of select="construction3"/></xsl:with-param>
                        <xsl:with-param name="flatFieldValue"><xsl:value-of select="flat3"/></xsl:with-param>
                        <xsl:with-param name="flatWithNumFieldValue"><xsl:value-of select="flatWithNum3"/></xsl:with-param>
                        <xsl:with-param name="indexFieldValue"><xsl:value-of select="index3"/></xsl:with-param>
                        <xsl:with-param name="webRoot" select="$webRoot"/>
                    </xsl:call-template>
                </div>

                <xsl:variable name="residenceRights" select="$claimDictionaries/config/list-residence-right/*"/>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Право проживания</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="residenceRightSelected" select="residenceRightSelect"/>
                        <xsl:call-template name="selectFromDictionary">
                            <xsl:with-param name="selectId" select="'residenceRightSelect'"/>
                            <xsl:with-param name="elementsFromDictionary" select="$residenceRights"/>
                            <xsl:with-param name="selectedElement" select="$residenceRightSelected"/>
                            <xsl:with-param name="selectStyle" select="'width: 370px;'"/>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Срок проживания в населенном пункте на момент заполнения анкеты</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input type="text" name="residenceDuration" value="{residenceDuration}" maxlength="2" size="5" class="numberField"/> &nbsp; полных лет
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Срок проживания по фактическому адресу</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input type="text" name="factAddressDuration" value="{factAddressDuration}" maxlength="2" size="5" class="numberField"/> &nbsp; полных лет
                    </xsl:with-param>
                </xsl:call-template>

                <script type="text/javascript">
                    <!--Все в подключаемом скрипте-->
                    $(document).ready(function(){
                        window.scrollTo(0, 0);
                        init();
                        var loanClaimButton = $(".loan-claim-button.second-address");
                        loanClaimButton.width(loanClaimButton.find(".buttonSelectLeft").width()
                            + loanClaimButton.find(".buttonSelectCenter").width()
                            + loanClaimButton.find(".buttonSelectRight").width() + 5); // "+5" добавлено для ИЕ9. Иначе не хватает ширины для отображения кнопки);
                        $('.loan-claim-button .buttonSelect').hover(function(){
                            $(this).addClass('hoverOperation');
                        }, function() {
                           $(this).removeClass('hoverOperation');
                        });
                    });
                    function checkPayment()
                    {
                        formObject.updateFields();
                        return true;
                    }

                </script>

            </xsl:when>
            <!--
                *********************************************************************************************
                                                5 шаг: Работа и доход
                *********************************************************************************************
            -->
            <xsl:when test="$documentState = 'INITIAL5'">
                <div class="Title">
                    <span class="size24">Работа и доход</span>
                </div>

                <xsl:variable name="contractTypes" select="$claimDictionaries/config/list-work-on-contract"/>
                <xsl:variable name="employeePositionTypes" select="$claimDictionaries/config/list-category-of-position/*"/>
                <xsl:variable name="numberOfEmployeesList" select="$claimDictionaries/config/list-number-of-employees/*"/>
                <xsl:variable name="seniorityTypes" select="$claimDictionaries/config/list-experience-on-current-job/*"/>
                <xsl:variable name="incorporationTypes" select="$claimDictionaries/config/list-form-of-incorporation/*"/>
                <xsl:variable name="companyActivityTypes" select="$claimDictionaries/config/list-types-of-company"/>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Тип занятости</xsl:with-param>
                    <xsl:with-param name="lineId" select="'contractTypeId'"/>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="contractType" select="contractType"/>
                        <xsl:call-template name="selectFromDictionary">
                            <xsl:with-param name="selectId" select="'contractType'"/>
                            <xsl:with-param name="elementsFromDictionary" select="$contractTypes/*"/>
                            <xsl:with-param name="selectedElement" select="$contractType"/>
                            <xsl:with-param name="onchange" select="'FormObject.hideOrShowOrganizationBlock(this.value);'"/>
                            <xsl:with-param name="selectStyle" select="'width: 370px;'"/>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">privatePracticeRow</xsl:with-param>
                    <xsl:with-param name="rowName">Частная практика</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input type="text" name="privatePractice" value="{privatePractice}" maxlength="255" size="57"/>
                    </xsl:with-param>
                </xsl:call-template>

<!--
                <xsl:call-template name="standartRow">
                   <xsl:with-param name="required" select="'false'"/>
                   <xsl:with-param name="rowName">Укажите Ваш ИНН</xsl:with-param>
                   <xsl:with-param name="rowValue">
                       <input type="text" name="inn" value="{inn}" maxlength="12" size="20" class="numberField"/>
                   </xsl:with-param>
                </xsl:call-template>
-->

                <div id="organizationBlock">

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId" select="'isSberEmployeeRow'"/>
                        <xsl:with-param name="isAllocate" select="'false'"/>
                        <xsl:with-param name="required" select="'false'"/>
                        <xsl:with-param name="rowValue">
                            <input type="hidden" id="sberName" value="Сбербанк России"/>
                            <input type="hidden" id="otherCompanyName" value="{companyFullName}"/>
                            <div class="textTick">
                                <xsl:choose>
                                    <xsl:when test="sbrfRelationType = 'workInSbrf'">
                                        <input class="float" type="checkbox" id="isSberEmployee" name="isSberEmployee" value="true" checked="checked" disabled="true"/>
                                        <input type="hidden" name="sbrfRelationType" value="{sbrfRelationType}"/>
                                    </xsl:when>
                                    <xsl:when test="isSberEmployee ='true'">
                                        <input class="float" type="checkbox" id="isSberEmployee" onclick="FormObject.showSberEmployeeBlock(this);" name="isSberEmployee" value="{isSberEmployee}" checked="checked"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <input class="float" type="checkbox" id="isSberEmployee" onclick="FormObject.showSberEmployeeBlock(this);" name="isSberEmployee" value="{isSberEmployee}"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                                <label class="float" for="isSberEmployee">&nbsp;Я работаю в Сбербанке России</label>
                            </div>
                        </xsl:with-param>
                    </xsl:call-template>

                    <!--Текущее место работы-->
                    <input type="hidden" id="notRelatedToSber" value="{sbrfRelationType = 'otherSbrfRelationType'}"/>
                    <xsl:call-template name="titleRow">
                         <xsl:with-param name="rowName">&nbsp;
                             <b class="rowTitle18 size20">Текущее место работы</b>
                             <div id="experienceNotification">
                                <br/>
                                <span>Общий стаж работы за последние 5 лет должен быть не меньше 12 месяцев.</span>
                                <br/>
                             </div>
                         </xsl:with-param>
                         <xsl:with-param name="rowValue">
                         </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Организационно – правовая форма</xsl:with-param>
                        <xsl:with-param name="lineId" select="'incorporationTypeRow'"/>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="incorporationTypeValue" select="incorporationType"/>
                            <xsl:call-template name="selectFromDictionary">
                                <xsl:with-param name="selectId" select="'incorporationTypeSelect'"/>
                                <xsl:with-param name="elementsFromDictionary" select="$incorporationTypes"/>
                                <xsl:with-param name="selectedElement" select="$incorporationTypeValue"/>
                                <xsl:with-param name="selectStyle" select="'width: 370px;'"/>
                                <xsl:with-param name="onchange" select="'FormObject.onIncorporationTypeChange(this.value);'"/>
                            </xsl:call-template>
                            <input type="hidden" id="sberIncorporationType" value="002"/>
                            <input type="hidden" id="otherCompanyIncorporationType" value="{incorporationType}"/>
                            <input type="hidden" id="incorporationType" name="incorporationType"/>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">companyFullNameRow</xsl:with-param>
                        <xsl:with-param name="rowName">Полное наименование компании /организации</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="text" id="companyFullName" name="companyFullName" value="{companyFullName}" maxlength="120" size="57"/>
                        </xsl:with-param>
                    </xsl:call-template>

                    <div id="workInSberBlock">
                        <xsl:variable name="departments" select="document('tbList.xml')/entity-list/*"/>

                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Подразделение</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <xsl:variable name="department" select="department"/>
                                <xsl:call-template name="departments">
                                    <xsl:with-param name="selectId" select="'department'"/>
                                    <xsl:with-param name="elementsFromDictionary" select="$departments"/>
                                    <xsl:with-param name="selectedElement" select="$department"/>
                                    <xsl:with-param name="selectStyle" select="'width: 370px;'"/>
                                </xsl:call-template>
                            </xsl:with-param>
                            <xsl:with-param name="description">Для ЦА выберите «ЦА или Оперу Московского банка»</xsl:with-param>
                        </xsl:call-template>

                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Полное наименование подразделения</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <input type="text" name="departmentFullName" id ="departmentFullName"  value="{departmentFullName}" maxlength="255" size="57"/>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="required" select="'false'"/>
                            <xsl:with-param name="rowValue">
                                <div class="textTick">
                                    <xsl:choose>
                                        <xsl:when test="isTBChairman ='true'">
                                            <input class="float" type="checkbox" id="isTBChairman" name="isTBChairman" checked="checked"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <input class="float" type="checkbox" id="isTBChairman" name="isTBChairman"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                    <label class="float" for="isTBChairman">&nbsp;Я председатель Территориального банка</label>
                                </div>
                            </xsl:with-param>
                        </xsl:call-template>
                    </div>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Сфера деятельности компании</xsl:with-param>
                        <xsl:with-param name="lineId" select="'companyActivityScopeRow'"/>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="companyActivityScope" select="companyActivityScope"/>
                            <xsl:call-template name="selectFromDictionary">
                                <xsl:with-param name="selectId" select="'companyActivityScopeSelect'"/>
                                <xsl:with-param name="elementsFromDictionary" select="$companyActivityTypes/*"/>
                                <xsl:with-param name="selectedElement" select="$companyActivityScope"/>
                                <xsl:with-param name="onchange" select="'FormObject.hideOrShowActivityCommentRow(this.value);'"/>
                                <xsl:with-param name="selectStyle" select="'width: 370px;'"/>
                            </xsl:call-template>
                            <input type="hidden" id="sberActivityScope" value="1"/>
                            <input type="hidden" id="otherCompanyActivityScope" value="{companyActivityScope}"/>
                            <input type="hidden" id="companyActivityScope" name="companyActivityScope"/>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">companyActivityCommentRow</xsl:with-param>
                        <xsl:with-param name="rowStyle">display:none</xsl:with-param>
                        <xsl:with-param name="rowName">Комментарий</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="text" name="companyActivityComment" value="{companyActivityComment}" maxlength="255" size="57"/>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">companyInnRow</xsl:with-param>
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">ИНН организации работодателя</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="text" id="companyInn" name="companyInn" value="{companyInn}" maxlength="10" size="57" class="numberField"/>
                            <input type="hidden" id="sberInn" value="7707083893"/>
                            <input type="hidden" id="otherCompanyInn" value="{companyInn}"/>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Занимаемая должность</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="text" name="employeePosition" value="{employeePosition}" maxlength="60" size="57"/>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Категория занимаемой должности</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="employeePositionType" select="employeePositionType"/>
                            <xsl:call-template name="selectFromDictionary">
                                <xsl:with-param name="selectId" select="'employeePositionType'"/>
                                <xsl:with-param name="elementsFromDictionary" select="$employeePositionTypes"/>
                                <xsl:with-param name="selectedElement" select="$employeePositionType"/>
                                <xsl:with-param name="selectStyle" select="'width: 370px;'"/>
                            </xsl:call-template>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Как долго вы работаете в компании</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="seniority" select="seniority"/>
                            <xsl:call-template name="selectFromDictionary">
                                <xsl:with-param name="selectId" select="'seniority'"/>
                                <xsl:with-param name="elementsFromDictionary" select="$seniorityTypes"/>
                                <xsl:with-param name="selectedElement" select="$seniority"/>
                                <xsl:with-param name="selectStyle" select="'width: 370px;'"/>
                            </xsl:call-template>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Количество сотрудников в компании</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="numberOfEmployees" select="numberOfEmployees"/>
                            <xsl:call-template name="selectFromDictionary">
                                <xsl:with-param name="selectId" select="'numberOfEmployeesSelect'"/>
                                <xsl:with-param name="elementsFromDictionary" select="$numberOfEmployeesList"/>
                                <xsl:with-param name="selectedElement" select="$numberOfEmployees"/>
                                <xsl:with-param name="selectStyle" select="'width: 370px;'"/>
                                <xsl:with-param name="onchange" select="'FormObject.onNumberOfEmployeesChange(this.value);'"/>
                            </xsl:call-template>
                            <input type="hidden" id="sberNumberOfEmployees" value="5"/>
                            <input type="hidden" id="otherCompanyNumberOfEmployees" value="{numberOfEmployees}"/>
                            <input type="hidden" id="numberOfEmployees" name="numberOfEmployees"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </div>

                <!--Ежемесячный доход и расход-->
                <xsl:call-template name="titleRow">
                     <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Ежемесячный доход и расход</b></xsl:with-param>
                     <xsl:with-param name="rowValue">
                     </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                   <xsl:with-param name="lineId" select="'basicIncomeRow'"/>
                   <xsl:with-param name="rowName">Среднемесячный основной доход</xsl:with-param>
                   <xsl:with-param name="rowValue">
                       <input type="text" name="basicIncome" value="{basicIncome}" size="20" maxlength="13" class="moneyField"/>&nbsp;руб.
                   </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                   <xsl:with-param name="lineId" select="'pensionerBasicIncomeRow'"/>
                   <xsl:with-param name="rowName">Среднемесячный основной доход</xsl:with-param>
                   <xsl:with-param name="rowValue">
                       <input type="text" name="basicIncome" value="{basicIncome}" size="20" maxlength="13" class="moneyField"/>&nbsp;руб.
                   </xsl:with-param>
                   <xsl:with-param name="description">Укажите размер Вашей пенсии</xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                   <xsl:with-param name="rowName">Дополнительный доход</xsl:with-param>
                   <xsl:with-param name="rowValue">
                       <input type="text" name="additionalIncome" value="{additionalIncome}" size="20" maxlength="13" class="moneyField"/>&nbsp;руб.
                   </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                   <xsl:with-param name="rowName">Среднемесячный доход семьи</xsl:with-param>
                   <xsl:with-param name="rowValue">
                       <input type="text" name="monthlyIncome" value="{monthlyIncome}" size="20" maxlength="13" class="moneyField"/>&nbsp;руб.
                   </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Среднемесячный расход</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input type="text" name="monthlyExpense" value="{monthlyExpense}" size="20" maxlength="13" class="moneyField"/>&nbsp;руб.
                    </xsl:with-param>
                    <xsl:with-param name="description">В расходы необходимо учитывать следующие данные – выплачиваемые алименты, плата за образование, арендные платежи, страховые выплаты и т.п., а также Ваша доля в общих доходах семьи по собственной оценке. Не указываются – НДФЛ, расходы на погашение долговых обязательств (кредиты), расходы на проживание (питание, одежда и т.д). </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                   <xsl:with-param name="lineId" select="'workPlacesCountRow'"/>
                   <xsl:with-param name="rowName">Количество рабочих мест за последние 3 года</xsl:with-param>
                   <xsl:with-param name="rowValue">
                       <input type="text" name="workPlacesCount" value="{workPlacesCount}" maxlength="2" size="20" class="numberField"/>
                   </xsl:with-param>
                </xsl:call-template>

                <script type="text/javascript">

                    var FormObject =
                    {
                        contractType: null,
                        isSberEmployee: null,
                        organizationBlock: null,
                        notRelatedToSber: null,
                        workPlacesCountRow: null,
                        privatePracticeRow: null,
                        workInSberBlock: null,
                        basicIncomeRow: null,
                        pensionerBasicIncomeRow: null,
                        companyActivityCommentRow: null,
                        incorporationType: null,
                        companyActivityScope: null,
                        numberOfEmployees: null,

                        init: function()
                        {
                            this.contractType = ensureElement('contractType').value;
                            this.isSberEmployee = ensureElement('isSberEmployee');
                            this.notRelatedToSber = ensureElement('notRelatedToSber').value;
                            this.experienceNotification = ensureElement('experienceNotification');
                            this.organizationBlock = ensureElement('organizationBlock');
                            this.workPlacesCountRow = ensureElement('workPlacesCountRow');
                            this.privatePracticeRow = ensureElement('privatePracticeRow');
                            this.workInSberBlock = ensureElement('workInSberBlock');
                            this.basicIncomeRow = ensureElement('basicIncomeRow');
                            this.pensionerBasicIncomeRow = ensureElement('pensionerBasicIncomeRow');
                            this.companyActivityCommentRow = ensureElement('companyActivityCommentRow');
                            this.incorporationType = ensureElement('incorporationType');
                            this.companyActivityScope = ensureElement('companyActivityScope');
                            this.numberOfEmployees = ensureElement('numberOfEmployees');
                            this.updateFieldsView();
                        },

                        isOrganization: function()
                        {
                           if (this.isPrivatePractice() || this.isPensioner())
                               return false;
                           else return true;
                        },

                        isPrivatePractice: function()
                        {
                           var privatePracticeCode;
                           <xsl:for-each select="$contractTypes/work-on-contract">
                               <xsl:if test="privatePractice = 'true'">
                                   privatePracticeCode = '<xsl:value-of select="code"/>';
                               </xsl:if>
                           </xsl:for-each>
                           if (this.contractType == privatePracticeCode)
                               return true;
                           else return false;
                        },

                        isPensioner: function()
                        {
                           var pensionerCode;
                           <xsl:for-each select="$contractTypes/work-on-contract">
                               <xsl:if test="pensioner = 'true'">
                                   pensionerCode = '<xsl:value-of select="code"/>';
                               </xsl:if>
                           </xsl:for-each>
                           if (this.contractType == pensionerCode)
                               return true;
                           else return false;
                        },

                        hideOrShowOrganizationBlock: function(value)
                        {
                            this.contractType = value;

                            var organization = this.isOrganization();

                            if (organization)
                            {
                                hideOrShow(this.organizationBlock, false);
                                hideOrShow(this.workPlacesCountRow, false);
                                hideOrShow(this.privatePracticeRow, true);
                                hideOrShow(this.pensionerBasicIncomeRow, true);
                                hideOrShow(this.basicIncomeRow, false);
                                if (this.notRelatedToSber == 'true')
                                    hideOrShow(this.experienceNotification, false);
                                else
                                    hideOrShow(this.experienceNotification, true);
                            }
                            else
                            {
                                hideOrShow(this.organizationBlock, true);
                                hideOrShow(this.workPlacesCountRow, true);
                                if (this.isPrivatePractice(value))
                                {
                                    hideOrShow(this.privatePracticeRow, false);
                                    hideOrShow(this.pensionerBasicIncomeRow, true);
                                    hideOrShow(this.basicIncomeRow, false);
                                }
                                else if (this.isPensioner())
                                {
                                    hideOrShow(this.pensionerBasicIncomeRow, false);
                                    hideOrShow(this.basicIncomeRow, true);
                                    hideOrShow(this.privatePracticeRow, true);
                                }
                            }
                            var temp = ensureElement('pensionerBasicIncomeRow');
                            $(temp).click();
                            temp = ensureElement('contractTypeId');
                            $(temp).click();
                        },

                        showSberEmployeeBlock: function(element)
                        {
                            element.value =  element.checked;
                            this.isSberEmployee = element;

                            <!--показать или скрыть поля, которые относятся только к сотруднику сбера-->
                            hideOrShow(this.workInSberBlock, !element.checked);

                            <!--заполнить значения полей "название компании", "организационно-правовая форма", "сфера деятельности компании", "ИНН организации работодателя", "количество сотрудников компании"-->
                            var sberName = ensureElement('sberName');
                            var otherCompanyName = ensureElement('otherCompanyName');
                            var companyFullName = ensureElement('companyFullName');

                            if (companyFullName != null)
                            {
                                if (element.checked)
                                {
                                    otherCompanyName.value = companyFullName.value;
                                    companyFullName.value = sberName.value;
                                    companyFullName.readOnly = true;
                                }
                                else
                                {
                                    companyFullName.value = otherCompanyName.value;
                                    companyFullName.readOnly = false;
                                }
                            }

                            var sberIncorporationType = ensureElement('sberIncorporationType');
                            var otherCompanyIncorporationType = ensureElement('otherCompanyIncorporationType');
                            var incorporationTypeSelect = ensureElement('incorporationTypeSelect');
                            if (incorporationTypeSelect != null)
                            {
                                if (element.checked)
                                {
                                    otherCompanyIncorporationType.value = incorporationTypeSelect.value;
                                    incorporationTypeSelect.value = sberIncorporationType.value;
                                    this.incorporationType.value = incorporationTypeSelect.value;
                                    incorporationTypeSelect.disabled = true;
                                }
                                else
                                {
                                    incorporationTypeSelect.value = otherCompanyIncorporationType.value;
                                    this.incorporationType.value = incorporationTypeSelect.value;
                                    incorporationTypeSelect.disabled = false;
                                }
                            }

                            var sberActivityScope = ensureElement('sberActivityScope');
                            var otherCompanyActivityScope = ensureElement('otherCompanyActivityScope');
                            var companyActivityScopeSelect = ensureElement('companyActivityScopeSelect');
                            if (companyActivityScopeSelect != null)
                            {
                                if (element.checked)
                                {
                                    otherCompanyActivityScope.value = companyActivityScopeSelect.value;
                                    companyActivityScopeSelect.value = sberActivityScope.value;
                                    companyActivityScopeSelect.disabled = true;
                                    this.hideOrShowActivityCommentRow(companyActivityScopeSelect.value);
                                }
                                else
                                {
                                    companyActivityScopeSelect.value = otherCompanyActivityScope.value;
                                    companyActivityScopeSelect.disabled = false;
                                    this.hideOrShowActivityCommentRow(companyActivityScopeSelect.value);
                                }
                            }

                            var sberInn = ensureElement('sberInn');
                            var otherCompanyInn = ensureElement('otherCompanyInn');
                            var companyInn= ensureElement('companyInn');

                            if (companyInn != null)
                            {
                                if (element.checked)
                                {
                                    otherCompanyInn.value = companyInn.value;
                                    companyInn.value = sberInn.value;
                                }
                                else
                                {
                                    companyInn.value = otherCompanyInn.value;
                                }
                            }


                            var sberNumberOfEmployees = ensureElement('sberNumberOfEmployees');
                            var otherCompanyNumberOfEmployees = ensureElement('otherCompanyNumberOfEmployees');
                            var numberOfEmployeesSelect = ensureElement('numberOfEmployeesSelect');
                            if (numberOfEmployeesSelect != null)
                            {
                                if (element.checked)
                                {
                                    otherCompanyNumberOfEmployees.value = numberOfEmployeesSelect.value;
                                    numberOfEmployeesSelect.value = sberNumberOfEmployees.value;
                                    this.numberOfEmployees.value = numberOfEmployeesSelect.value;
                                    numberOfEmployeesSelect.disabled = true;
                                }
                                else
                                {
                                    numberOfEmployeesSelect.value = otherCompanyNumberOfEmployees.value;
                                    this.numberOfEmployees.value = numberOfEmployeesSelect.value;
                                    numberOfEmployeesSelect.disabled = false;
                                }
                            }

                        },

                        updateFieldsView: function()
                        {
                            this.showSberEmployeeBlock(this.isSberEmployee);

                            var companyActivityScope = ensureElement('companyActivityScope');
                            this.hideOrShowActivityCommentRow(companyActivityScope.value);

                            var contractType = ensureElement('contractType').value;
                            this.hideOrShowOrganizationBlock(contractType)
                        },

                        hideOrShowActivityCommentRow: function(value)
                        {
                            var activityCommentRequiredCodes = [];

                            <xsl:for-each select="$companyActivityTypes/types-of-company">
                                <xsl:if test="orgActivityDescRequired = 'true'">
                                    activityCommentRequiredCodes.push('<xsl:value-of select="code"/>');
                                </xsl:if>
                            </xsl:for-each>

                            if (activityCommentRequiredCodes.contains(value))
                                hideOrShow(this.companyActivityCommentRow, false);
                            else
                                hideOrShow(this.companyActivityCommentRow, true);

                            if (this.companyActivityScope != null)
                                this.companyActivityScope.value = value;
                        },

                        onIncorporationTypeChange: function(value)
                        {
                            this.incorporationType.value = value;
                        },

                        onNumberOfEmployeesChange: function(value)
                        {
                            this.numberOfEmployees.value = value;
                        }
                    };

                    $(document).ready(function(){
                    window.scrollTo(0, 0);
                    FormObject.init();
                        $('.loan-claim-button .buttonSelect').hover(function(){
                            $(this).addClass('hoverOperation');
                        }, function() {
                           $(this).removeClass('hoverOperation');
                        });
                        $("#contractTypeId").removeClass("active-help");
                        $("#isSberEmployeeRow").addClass("active-help");
                        $("#isSberEmployeeRow").removeClass("active-help");
                        $("#incorporationTypeRow").addClass("active-help");
                        $("#incorporationTypeRow").removeClass("active-help");
                        $("#contractTypeId").addClass("active-help");
                    });

                    <!--Валидирующая ф-я, привязанная к кнопке save-->
                    function checkPayment()
                    {
                        <!--Удаляем все скрытые поля.-->
                        $('div:hidden').find('select').remove();
                        $('div:hidden').find('input').remove();
                        $('#isSberEmployee').removeAttr('disabled');
                        return true;
                    }

                </script>

            </xsl:when>
            <!--
                *********************************************************************************************
                                                5 шаг: Собственность
                *********************************************************************************************
            -->
            <xsl:when test="$documentState = 'INITIAL6'">

                <div class="Title">
                    <span class="size24">Собственность</span>
                </div>
                <div id="formMessages">
                        <div class="workspace-box roundBorder infMesOrange">
                            <div class="infoMessage">
                                <div class="messageContainer"/>
                            </div>
                            <div class="clear"/>
                        </div>
                </div>

                <xsl:call-template name="button">
                    <xsl:with-param name="id" select="'templateRemoveBtn'"/>
                    <xsl:with-param name="onclick" select="''"/>
                    <xsl:with-param name="typeBtn" select="'blueLink'"/>
                </xsl:call-template>

                <!--Область для блоков, предназначенных для добавления информации о недвижимости. Создается в подключаемых скриптах-->
                <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.js"/>
                <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.realtyBlock.js"/>
                <script type="text/javascript" src="{$resourceRoot}/scripts/formatedInput.js"/>
                <div id="realtyArea" class="paymentBigLabel"></div>


                <!--Значения справочника "вид недвижимости в собственности" -->
                <xsl:variable name="realtyTypes" select="$claimDictionaries/config/list-type-of-realty/*"/>
                <xsl:call-template name="selectFromDictionary">
                   <xsl:with-param name="elementsFromDictionary" select="$realtyTypes"/>
                   <xsl:with-param name="chooseFirstElementAsDefault" select="'true'"/>
                   <xsl:with-param name="selectId" select="'realtyType'"/>
                   <xsl:with-param name="spanId" select="'realtyTypes'"/>
                   <xsl:with-param name="style" select="'display:none;'"/>
                </xsl:call-template>

                <xsl:variable name="typeOfSquareUnits" select="$claimDictionaries/config/square-units/*"/>
                <xsl:call-template name="selectFromDictionary">
                   <xsl:with-param name="elementsFromDictionary" select="$typeOfSquareUnits"/>
                   <xsl:with-param name="chooseFirstElementAsDefault" select="'true'"/>
                   <xsl:with-param name="selectId" select="'typeOfSquareUnit'"/>
                   <xsl:with-param name="spanId" select="'typeOfSquareUnits'"/>
                   <xsl:with-param name="style" select="'display:none;'"/>
                </xsl:call-template>

                <!--Область для блоков, предназначенных для добавления информации о транспортном средстве. Создается в подключаемых скриптах-->
                <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.transportBlock.js"/>
                <div id="transportArea" class="paymentBigLabel"></div>


                <!--Значения справочника "вид недвижимости в собственности" -->
                <xsl:variable name="transportTypes" select="$claimDictionaries/config/list-type-of-vehicle/*"/>
                <xsl:call-template name="selectFromDictionary">
                   <xsl:with-param name="elementsFromDictionary" select="$transportTypes"/>
                   <xsl:with-param name="chooseFirstElementAsDefault" select="'true'"/>
                   <xsl:with-param name="selectId" select="'transportType'"/>
                   <xsl:with-param name="spanId" select="'transportTypes'"/>
                   <xsl:with-param name="style" select="'display:none;'"/>
                </xsl:call-template>

                <div id="temporaryFields">
                    <input type="hidden" name="realtyType1" value="{realty_1_realtyType}" id="realtyType1"/>
                    <input type="hidden" name="address1" value="{realty_1_address}" id="address1"/>
                    <input type="hidden" name="yearOfPurchase1" value="{realty_1_yearOfPurchase}" id="yearOfPurchase1"/>
                    <input type="hidden" name="square1" value="{realty_1_square}" id="square1"/>
                    <input type="hidden" name="typeOfSquareUnit1" value="{realty_1_typeOfSquareUnit}" id="typeOfSquareUnit1"/>
                    <input type="hidden" name="approxMarketValue1" value="{realty_1_approxMarketValue}" id="approxMarketValue1"/>

                    <input type="hidden" name="realtyType2" value="{realty_2_realtyType}" id="realtyType2"/>
                    <input type="hidden" name="address2" value="{realty_2_address}" id="address2"/>
                    <input type="hidden" name="yearOfPurchase2" value="{realty_2_yearOfPurchase}" id="yearOfPurchase2"/>
                    <input type="hidden" name="square2" value="{realty_2_square}" id="square2"/>
                    <input type="hidden" name="typeOfSquareUnit2" value="{realty_2_typeOfSquareUnit}" id="typeOfSquareUnit2"/>
                    <input type="hidden" name="approxMarketValue2" value="{realty_2_approxMarketValue}" id="approxMarketValue2"/>

                    <input type="hidden" name="realtyType3" value="{realty_3_realtyType}" id="realtyType3"/>
                    <input type="hidden" name="address3" value="{realty_3_address}" id="address3"/>
                    <input type="hidden" name="yearOfPurchase3" value="{realty_3_yearOfPurchase}" id="yearOfPurchase3"/>
                    <input type="hidden" name="square3" value="{realty_3_square}" id="square3"/>
                    <input type="hidden" name="typeOfSquareUnit3" value="{realty_3_typeOfSquareUnit}" id="typeOfSquareUnit3"/>
                    <input type="hidden" name="approxMarketValue3" value="{realty_3_approxMarketValue}" id="approxMarketValue3"/>

                    <input type="hidden" name="realtyType4" value="{realty_4_realtyType}" id="realtyType4"/>
                    <input type="hidden" name="address4" value="{realty_4_address}" id="address4"/>
                    <input type="hidden" name="yearOfPurchase4" value="{realty_4_yearOfPurchase}" id="yearOfPurchase4"/>
                    <input type="hidden" name="square4" value="{realty_4_square}" id="square4"/>
                    <input type="hidden" name="typeOfSquareUnit4" value="{realty_4_typeOfSquareUnit}" id="typeOfSquareUnit4"/>
                    <input type="hidden" name="approxMarketValue4" value="{realty_4_approxMarketValue}" id="approxMarketValue4"/>

                    <input type="hidden" name="realtyType5" value="{realty_5_realtyType}" id="realtyType5"/>
                    <input type="hidden" name="address5" value="{realty_5_address}" id="address5"/>
                    <input type="hidden" name="yearOfPurchase5" value="{realty_5_yearOfPurchase}" id="yearOfPurchase5"/>
                    <input type="hidden" name="square5" value="{realty_5_square}" id="square5"/>
                    <input type="hidden" name="typeOfSquareUnit5" value="{realty_5_typeOfSquareUnit}" id="typeOfSquareUnit5"/>
                    <input type="hidden" name="approxMarketValue5" value="{realty_5_approxMarketValue}" id="approxMarketValue5"/>

                    <input type="hidden" name="realtyType6" value="{realty_6_realtyType}" id="realtyType6"/>
                    <input type="hidden" name="address6" value="{realty_6_address}" id="address6"/>
                    <input type="hidden" name="yearOfPurchase6" value="{realty_6_yearOfPurchase}" id="yearOfPurchase6"/>
                    <input type="hidden" name="square6" value="{realty_6_square}" id="square6"/>
                    <input type="hidden" name="typeOfSquareUnit6" value="{realty_6_typeOfSquareUnit}" id="typeOfSquareUnit6"/>
                    <input type="hidden" name="approxMarketValue6" value="{realty_6_approxMarketValue}" id="approxMarketValue6"/>

                    <input type="hidden" name="realtyType7" value="{realty_7_realtyType}" id="realtyType7"/>
                    <input type="hidden" name="address7" value="{realty_7_address}" id="address7"/>
                    <input type="hidden" name="yearOfPurchase7" value="{realty_7_yearOfPurchase}" id="yearOfPurchase7"/>
                    <input type="hidden" name="square7" value="{realty_7_square}" id="square7"/>
                    <input type="hidden" name="typeOfSquareUnit7" value="{realty_7_typeOfSquareUnit}" id="typeOfSquareUnit7"/>
                    <input type="hidden" name="approxMarketValue7" value="{realty_7_approxMarketValue}" id="approxMarketValue7"/>

                    <input type="hidden" name="transportType1" value="{transport_1_transportType}" id="transportType1"/>
                    <input type="hidden" name="registrationNumber1" value="{transport_1_registrationNumber}" id="registrationNumber1"/>
                    <input type="hidden" name="brand1" value="{transport_1_brand}" id="brand1"/>
                    <input type="hidden" name="transportApproxMarketValue1" value="{transport_1_approxMarketValue}" id="transportApproxMarketValue1"/>
                    <input type="hidden" name="ageOfTransport1" value="{transport_1_ageOfTransport}" id="ageOfTransport1"/>
                    <input type="hidden" name="transportYearOfPurchase1" value="{transport_1_yearOfPurchase}" id="transportYearOfPurchase1"/>

                    <input type="hidden" name="transportType2" value="{transport_2_transportType}" id="transportType2"/>
                    <input type="hidden" name="registrationNumber2" value="{transport_2_registrationNumber}" id="registrationNumber2"/>
                    <input type="hidden" name="brand2" value="{transport_2_brand}" id="brand2"/>
                    <input type="hidden" name="transportApproxMarketValue2" value="{transport_2_approxMarketValue}" id="transportApproxMarketValue2"/>
                    <input type="hidden" name="ageOfTransport2" value="{transport_2_ageOfTransport}" id="ageOfTransport2"/>
                    <input type="hidden" name="transportYearOfPurchase2" value="{transport_2_yearOfPurchase}" id="transportYearOfPurchase2"/>

                    <input type="hidden" name="transportType3" value="{transport_3_transportType}" id="transportType3"/>
                    <input type="hidden" name="registrationNumber3" value="{transport_3_registrationNumber}" id="registrationNumber3"/>
                    <input type="hidden" name="brand3" value="{transport_3_brand}" id="brand3"/>
                    <input type="hidden" name="transportApproxMarketValue3" value="{transport_3_approxMarketValue}" id="transportApproxMarketValue3"/>
                    <input type="hidden" name="ageOfTransport3" value="{transport_3_ageOfTransport}" id="ageOfTransport3"/>
                    <input type="hidden" name="transportYearOfPurchase3" value="{transport_3_yearOfPurchase}" id="transportYearOfPurchase3"/>

                    <input type="hidden" name="transportType4" value="{transport_4_transportType}" id="transportType4"/>
                    <input type="hidden" name="registrationNumber4" value="{transport_4_registrationNumber}" id="registrationNumber4"/>
                    <input type="hidden" name="brand4" value="{transport_4_brand}" id="brand4"/>
                    <input type="hidden" name="transportApproxMarketValue4" value="{transport_4_approxMarketValue}" id="transportApproxMarketValue4"/>
                    <input type="hidden" name="ageOfTransport4" value="{transport_4_ageOfTransport}" id="ageOfTransport4"/>
                    <input type="hidden" name="transportYearOfPurchase4" value="{transport_4_yearOfPurchase}" id="transportYearOfPurchase4"/>

                    <input type="hidden" name="transportType5" value="{transport_5_transportType}" id="transportType5"/>
                    <input type="hidden" name="registrationNumber5" value="{transport_5_registrationNumber}" id="registrationNumber5"/>
                    <input type="hidden" name="brand5" value="{transport_5_brand}" id="brand5"/>
                    <input type="hidden" name="transportApproxMarketValue5" value="{transport_5_approxMarketValue}" id="transportApproxMarketValue5"/>
                    <input type="hidden" name="ageOfTransport5" value="{transport_5_ageOfTransport}" id="ageOfTransport5"/>
                    <input type="hidden" name="transportYearOfPurchase5" value="{transport_5_yearOfPurchase}" id="transportYearOfPurchase5"/>

                    <input type="hidden" name="transportType6" value="{transport_6_transportType}" id="transportType6"/>
                    <input type="hidden" name="registrationNumber6" value="{transport_6_registrationNumber}" id="registrationNumber6"/>
                    <input type="hidden" name="brand6" value="{transport_6_brand}" id="brand6"/>
                    <input type="hidden" name="transportApproxMarketValue6" value="{transport_6_approxMarketValue}" id="transportApproxMarketValue6"/>
                    <input type="hidden" name="ageOfTransport6" value="{transport_6_ageOfTransport}" id="ageOfTransport6"/>
                    <input type="hidden" name="transportYearOfPurchase6" value="{transport_6_yearOfPurchase}" id="transportYearOfPurchase6"/>

                    <input type="hidden" name="transportType7" value="{transport_7_transportType}" id="transportType7"/>
                    <input type="hidden" name="registrationNumber7" value="{transport_7_registrationNumber}" id="registrationNumber7"/>
                    <input type="hidden" name="brand7" value="{transport_7_brand}" id="brand7"/>
                    <input type="hidden" name="transportApproxMarketValue7" value="{transport_7_approxMarketValue}" id="transportApproxMarketValue7"/>
                    <input type="hidden" name="ageOfTransport7" value="{transport_7_ageOfTransport}" id="ageOfTransport7"/>
                    <input type="hidden" name="transportYearOfPurchase7" value="{transport_7_yearOfPurchase}" id="transportYearOfPurchase7"/>

                </div>

                <script type="text/javascript">
                    <!--Переменная, в которой хранится блок для введения данных о недвижимости-->
                    var realty;

                    $(document).ready(function(){
                    window.scrollTo(0, 0);
                    <!--Собрать введенную информацию о недвижимости-->
                    var info = [];
                    for (var i=1; i &lt; 8; i++)
                    {
                        var data = {};

                        var realtyType = ensureElement("realtyType"+i).value;
                        if (realtyType != "")
                        {
                            data.realtyType = realtyType;

                            var address = ensureElement("address"+i).value;
                            if (address != "")
                                data.address = address;

                            var yearOfPurchase = ensureElement("yearOfPurchase"+i).value;
                            if (yearOfPurchase != "")
                                data.yearOfPurchase = yearOfPurchase;

                            var square = ensureElement("square"+i).value;
                            if (square != "")
                                data.square = square;

                            var typeOfSquareUnit = ensureElement("typeOfSquareUnit"+i).value;
                            if (typeOfSquareUnit != "")
                                data.typeOfSquareUnit = typeOfSquareUnit;

                            var approxMarketValue = ensureElement("approxMarketValue"+i).value;
                            if (approxMarketValue != "")
                                data.approxMarketValue = approxMarketValue;

                            info.push(data);
                        }
                    }
                    addMessage('Данная информация не является обязательной для заполнения, но поможет нам рассмотреть Вашу заявку на кредит','formMessages', true);
                    <!--Создать область для введения данных о недвижимости-->
                    realty = createRealtyList(7, info);
                        $('.loan-claim-button .buttonSelect').hover(function(){
                            $(this).addClass('hoverOperation');
                        }, function() {
                           $(this).removeClass('hoverOperation');
                        });
                    });


                    <!--Переменная, в которой хранится блок для введения данных о транспортных средствах-->
                    var transport;

                    $(document).ready(function(){
                        window.scrollTo(0, 0);
                    <!--Собрать введенную информацию о транспортных средствах-->
                    var info = [];
                    for (var i=1; i &lt; 8; i++)
                    {
                       var data = {};

                       var transportType = ensureElement("transportType"+i).value;
                       if (transportType != "")
                       {
                           data.transportType = transportType;

                           var registrationNumber = ensureElement("registrationNumber"+i).value;
                           if (registrationNumber != "")
                               data.registrationNumber = registrationNumber;

                           var brand = ensureElement("brand"+i).value;
                           if (brand != "")
                               data.brand = brand;

                           var approxMarketValue = ensureElement("transportApproxMarketValue"+i).value;
                           if (approxMarketValue != "")
                               data.approxMarketValue = approxMarketValue;

                           var ageOfTransport = ensureElement("ageOfTransport"+i).value;
                           if (ageOfTransport != "")
                               data.ageOfTransport = ageOfTransport;

                           var yearOfPurchase = ensureElement("transportYearOfPurchase"+i).value;
                           if (yearOfPurchase != "")
                               data.yearOfPurchase = yearOfPurchase;

                           info.push(data);
                       }
                    }
                    <!--Создать область для введения данных о транспортных средствах-->
                    transport = createTransportList(7, info);
                        $('.loan-claim-button .buttonSelect').hover(function(){
                            $(this).addClass('hoverOperation');
                        }, function() {
                           $(this).removeClass('hoverOperation');
                        });
                    });

                    <!--Валидирующая ф-я, привязанная к кнопке save-->
                    function checkPayment()
                    {
                       realty.updateInputInfo();
                       transport.updateInputInfo();
                       $('#temporaryFields').remove();
                       return true;
                    }

                </script>

            </xsl:when>

            <!--
                *********************************************************************************************
                                                7 шаг:
                *********************************************************************************************
            -->
            <xsl:when test="$documentState = 'INITIAL7'">
                <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.customCreationBlock.js"/>

                <xsl:variable name="personData"           select="document('currentPersonData.xml')/entity-list/entity"/>
                <xsl:variable name="activeAccountsForCredit" select="document('active-not-deposit-accounts.xml')/entity-list/*"/>
                <xsl:variable name="activeAccounts"       select="document('active-accounts.xml')/entity-list/*"/>
                <xsl:variable name="activeArrestedDebitMainCards" select="document('active-or-arrested-debit-overdraft-main-cards.xml')/entity-list/*"/>
                <xsl:variable name="activeNotCreditCards" select="document('active-not-credit-cards.xml')/entity-list/*"/>
                <xsl:variable name="isNeedConfirmDebitOperationERKC" select="lch:isNeedConfirmDebitOperationERKC()"/>
                <xsl:variable name="isLockOperationDebit" select="lch:isLockOperationDebit()"/>

                <div class="Title">
                    <span class="size24">Дополнительные условия выдачи кредита</span>
                </div>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName" select="'Отделение для обслуживания кредита:'"/>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="rowValue">
                        <span id="receivingDepartmentLabel">
                            <xsl:choose>
                                <xsl:when test="string-length(receivingDepartmentName) > 0">
                                    <xsl:value-of select="receivingDepartmentName"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:if test="$personData/field[@name = 'departmentPossibleLoan'] = 'true'">
                                        <xsl:value-of select="$personData/field[@name = 'tbName']"/>
                                    </xsl:if>
                                </xsl:otherwise>
                            </xsl:choose>
                        </span>

                        <xsl:choose>
                            <xsl:when test="string-length(receivingDepartmentName) > 0">
                                <input type="hidden" id="receivingDepartmentName" name="receivingDepartmentName" value="{receivingDepartmentName}"/>
                            </xsl:when>
                            <xsl:when test="$personData/field[@name = 'departmentPossibleLoan'] = 'true'">
                                <input type="hidden" id="receivingDepartmentName" name="receivingDepartmentName" value="{$personData/field[@name = 'tbName']}"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <input type="hidden" id="receivingDepartmentName" name="receivingDepartmentName"/>
                            </xsl:otherwise>
                        </xsl:choose>

                        <input type="hidden" name="department" value="{department}"/>
                        <input type="hidden" name="sbrfRelationType" value="{sbrfRelationType}"/>
                        &nbsp;<a id="showDepartment" class="orangeText" href="#" onclick="FormObject.showDepartments(); return false;"><span>Изменить подразделение</span></a>

                        <xsl:choose>
                            <xsl:when test="string-length(receivingRegion) > 0">
                                <input type="hidden" id="receivingRegion" name="receivingRegion" value="{receivingRegion}"/>
                                <input type="hidden" id="receivingOffice" name="receivingOffice" value="{receivingOffice}"/>
                                <input type="hidden" id="receivingBranch" name="receivingBranch" value="{receivingBranch}"/>
                            </xsl:when>
                            <xsl:when test="$personData/field[@name = 'departmentPossibleLoan'] = 'true'">
                                <input type="hidden" id="receivingRegion" name="receivingRegion" value="{$personData/field[@name = 'region']}"/>
                                <input type="hidden" id="receivingOffice" name="receivingOffice" value="{$personData/field[@name = 'office']}"/>
                                <input type="hidden" id="receivingBranch" name="receivingBranch" value="{$personData/field[@name = 'branch']}"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <input type="hidden" id="receivingRegion" name="receivingRegion"/>
                                <input type="hidden" id="receivingOffice" name="receivingOffice"/>
                                <input type="hidden" id="receivingBranch" name="receivingBranch"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:with-param>
                </xsl:call-template>

                <div class="Title">
                    <div class="subTitleBlock">
                        <span class="size24">Способ получения кредита</span>
                        <div>
                            <p>Кредитные средства могут быть перечислены на имеющуюся или новую карту (счет)</p>
                        </div>
                    </div>
                </div>

                <xsl:variable name="isNewCardLoanIssueMetodAvailable" select="lch:isNewCardLoanIssueMetodAvailable()"/>
                <xsl:variable name="isCurrentCardLoanIssueMetodAvailable" select="lch:isCurrentCardLoanIssueMetodAvailable()"/>
                <xsl:variable name="isNewAccountLoanIssueMetodAvailable" select="lch:isNewAccountLoanIssueMetodAvailable()"/>
                <xsl:variable name="isCurrentAccountLoanIssueMetodAvailable" select="lch:isCurrentAccountLoanIssueMetodAvailable()"/>

                <div id='receiverSubTypeControl' class="bigTypecontrol">
                    <xsl:if test="$isNewCardLoanIssueMetodAvailable or $isCurrentCardLoanIssueMetodAvailable">
                        <div id="receiveOnCardButton" class="inner firstButton">
                            <xsl:attribute name="onclick">
                                <xsl:variable name="showLoanOffersBlock">FormObject.showCardBlock();</xsl:variable>
                                <xsl:value-of select="($showLoanOffersBlock)"/>
                            </xsl:attribute>
                            <xsl:attribute name="class">inner firstButton activeButton</xsl:attribute>
                            Получить на карту
                        </div>
                    </xsl:if>
                    <xsl:if test="$isNewAccountLoanIssueMetodAvailable or $isCurrentAccountLoanIssueMetodAvailable">
                        <div id="receiveOnAccountButton" class="inner lastButton">
                            <xsl:attribute name="onclick">
                                <xsl:variable name="hideLoanOffersBlock">FormObject.selectCreditMethodObtaining(&quot;ON_CURRENT_ACCOUNT&quot;);FormObject.showAccountBlock();</xsl:variable>
                                <xsl:value-of select="$hideLoanOffersBlock"/>
                            </xsl:attribute>
                            <xsl:attribute name="class">inner lastButton</xsl:attribute>
                            Получить на счет
                        </div>
                    </xsl:if>
                </div>
                <div class="clear"></div>

                <input type="hidden" id="creditMethodObtaining" name="creditMethodObtaining" value="{creditMethodObtaining}"/>
                <input type="hidden" id="receivingResource" name="receivingResource" value="{receivingResource}"/>

                <xsl:variable name="cardReceiveMethod" select="cardReceiveMethod"/>
                <div id="receiveOnCardBlock">
                    <xsl:if test="$isCurrentCardLoanIssueMetodAvailable">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">
                                <div class="textTick separatedRadio">
                                    <input class="float" type="radio" name="cardReceiveMethod" id="onCurrentCard" value="ON_CURRENT_CARD" onclick="FormObject.selectCreditMethodObtaining(this.value)">
                                        <xsl:if test="$cardReceiveMethod = 'ON_CURRENT_CARD'">
                                            <xsl:attribute name="checked">true</xsl:attribute>
                                        </xsl:if>
                                    </input>
                                    <label class="float" for="onCurrentCard">На текущую</label>
                                </div>
                            </xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="rowValue">
                                <xsl:call-template name="resources">
                                    <xsl:with-param name="name">receivingResource</xsl:with-param>
                                    <xsl:with-param name="linkId"  select="'cardReceivingResource'"/>
                                    <xsl:with-param name="activeDebitMainCards" select="$activeNotCreditCards"/>
                                    <xsl:with-param name="defaultHideOrShowAccountNumberRow" select="'FormObject.onChangeResource(this.value);'"/>
                                </xsl:call-template>
                            </xsl:with-param>
                            <xsl:with-param name="isAllocate" select="'false'"/>
                        </xsl:call-template>

                        <div id="onCurrentCardMessage" style="display:none;">
                            <div class="workspace-box roundBorder infMesOrange">
                                <div class="infoMessage">
                                    <div class="messageContainer">
                                        Для противодействия мошенничеству операции по счёту/карте в Сбербанк Онлайн, мобильных приложениях и мобильном банке могут быть ограничены. Операции в отделениях, банкоматах, терминалах без ограничений.
                                    </div>
                                </div>
                                <div class="clear"/>
                            </div>
                        </div>
                    </xsl:if>
                    <xsl:if test="$isNewCardLoanIssueMetodAvailable">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">
                                <div class="textTick separatedRadio">
                                    <input class="float" type="radio" name="cardReceiveMethod" id="onNewCard" value="ON_NEW_CARD" onclick="FormObject.selectCreditMethodObtaining(this.value)">
                                        <xsl:if test="$cardReceiveMethod = 'ON_NEW_CARD'">
                                            <xsl:attribute name="checked">true</xsl:attribute>
                                        </xsl:if>
                                    </input>
                                    <label class="float" for="onNewCard">На новую карту</label>
                                </div>
                            </xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="rowValue">
                                <label class="float" for="onNewCard">(потребуется визит в отделение)</label>
                            </xsl:with-param>
                            <xsl:with-param name="isAllocate" select="'false'"/>
                        </xsl:call-template>

                        <div id="onNewCardMessage" style="display:none;">
                            <div class="workspace-box roundBorder infMesOrange">
                                <div class="infoMessage">
                                    <div class="messageContainer">Для получения кредита на новую карту потребуется визит в отделение Банка</div>
                                </div>
                                <div class="clear"/>
                            </div>
                        </div>
                    </xsl:if>
                </div>

                <xsl:variable name="accountReceiveMethod" select="accountReceiveMethod"/>
                <div id="receiveOnAccountBlock">
                    <xsl:if test="$isCurrentAccountLoanIssueMetodAvailable">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">На текущий счет</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="rowValue">
                               <xsl:call-template name="resources">
                                   <xsl:with-param name="name">receivingResource</xsl:with-param>
                                   <xsl:with-param name="linkId"  select="'accountReceivingResource'"/>
                                   <xsl:with-param name="activeAccounts"  select="$activeAccountsForCredit"/>
                                   <xsl:with-param name="defaultHideOrShowAccountNumberRow" select="'FormObject.selectCreditMethodObtaining(&quot;ON_CURRENT_ACCOUNT&quot;);FormObject.onChangeResource(this.value);'"/>
                               </xsl:call-template>
                            </xsl:with-param>
                            <xsl:with-param name="isAllocate" select="'false'"/>
                        </xsl:call-template>

                        <div id="onCurrentAccountMessage" style="display:none;">
                            <div class="workspace-box roundBorder infMesOrange">
                                <div class="infoMessage">
                                    <div class="messageContainer">
                                        Для противодействия мошенничеству операции по счёту/карте в Сбербанк Онлайн, мобильных приложениях и мобильном банке могут быть ограничены. Операции в отделениях, банкоматах, терминалах без ограничений.
                                    </div>
                                </div>
                                <div class="clear"/>
                            </div>
                        </div>
                    </xsl:if>
                    <xsl:if test="$isNewAccountLoanIssueMetodAvailable">
                        <div id="onNewAccountMessage" style="display:none;">
                            <div class="workspace-box roundBorder infMesOrange">
                                <div class="infoMessage">
                                    <div class="messageContainer">
                                        Новый Сберегательный счёт открыт. После подачи и одобрения заявки кредитные средства будут перечислены на этот счёт.
                                    </div>
                                </div>
                                <div class="clear"/>
                            </div>
                        </div>

                        <xsl:variable name="openedAccountsCount" select="openedAccountsCount"/>
                        <input type="hidden" name="openedAccountsCount" value="{$openedAccountsCount}"/>
                        <xsl:if test="$openedAccountsCount = '' or $openedAccountsCount &lt; lch:getNewAccountsCount()">
                            <xsl:call-template name="standartRow">
                               <xsl:with-param name="required" select="'false'"/>
                               <xsl:with-param name="rowValue">
                                   <div class="loan-claim-button" onclick="FormObject.openNewAccount(); return false;">
                                       <div class="buttonSelect">
                                            <div class="buttonSelectLeft"></div>
                                            <div class="buttonSelectCenter">
                                                Открыть новый счет
                                            </div>
                                            <div class="buttonSelectRight"></div>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                               </xsl:with-param>
                            </xsl:call-template>
                        </xsl:if>
                    </xsl:if>
                </div>

                <xsl:variable name="showCardOrAcountBlock" select="sbrfRelationType != 'otherSbrfRelationType'"/>
                <xsl:if test="$showCardOrAcountBlock">

                    <div class="Title">
                        <span class="size18">Зарплатная/пенсионная карта или счет в Сбербанке</span>
                    </div>

                    <xsl:call-template name="button">
                        <xsl:with-param name="id" select="'templateRemoveBtn'"/>
                        <xsl:with-param name="onclick" select="''"/>
                        <xsl:with-param name="typeBtn" select="'blueLink'"/>
                    </xsl:call-template>

                    <span id="additionalCardTypes" style="display:none;">
                        <select id="additionalCardType" name="additionalCardType">
                            <option value="EMPTY">Выберите тип карты</option>
                            <option value="SALARY">Зарплатная</option>
                            <option value="PENSION">Пенсионная</option>
                        </select>
                    </span>

                    <span id="additionalAccountTypes" style="display:none;">
                        <select id="additionalAccountType" name="additionalAccountType">
                            <option value="EMPTY">Выберите тип счета</option>
                            <option value="SALARY">Зарплатный</option>
                            <option value="PENSION">Пенсионный</option>
                        </select>
                    </span>

                    <span id="additionalCards" style="display:none;">
                        <xsl:call-template name="resources">
                            <xsl:with-param name="name"                 select="'cardBlankResource'"/>
                            <xsl:with-param name="linkId"               select="cardBlankResource"/>
                            <xsl:with-param name="activeDebitMainCards" select="$activeNotCreditCards"/>
                            <xsl:with-param name="needCardEmptyOption"  select="$isGuest"/>
                            <xsl:with-param name="useDefaultHideOrShow" select="false()"/>
                            <xsl:with-param name="customHideOrShowAccountNumberRow" select="'hideOrShowNumberRow(this);'"/>
                        </xsl:call-template>
                    </span>

                    <span id="additionalAccounts" style="display:none;">
                        <xsl:call-template name="resources">
                            <xsl:with-param name="name"                 select="'accountBlankResource'"/>
                            <xsl:with-param name="linkId"               select="accountBlankResource"/>
                            <xsl:with-param name="activeAccounts"       select="$activeAccounts"/>
                            <xsl:with-param name="needAccountEmptyOption"   select="true()"/>
                            <xsl:with-param name="useDefaultHideOrShow" select="false()"/>
                            <xsl:with-param name="customHideOrShowAccountNumberRow" select="'hideOrShowNumberRow(this);'"/>
                        </xsl:call-template>
                    </span>

                    <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.js"/>
                    <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.additionalCardBlock.js"/>
                    <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.additionalAccountBlock.js"/>
                    <!--Область для блоков, предназначенных для добавления доп. карт и вкладов. Создается в подключаемых скриптах-->
                    <div id="additionalCardsArea" class="paymentBigLabel"></div>
                    <div id="additionalAccountsArea" class="paymentBigLabel"></div>

                    <div id="temporaryProductsFields">
                        <input type="hidden" value="{cardBlank_1_type}" id="cardBlankType1"/>
                        <input type="hidden" value="{cardBlank_1_resource}" id="cardBlankResource1"/>
                        <input type="hidden" value="{cardBlank_1_id}" id="cardBlankId1"/>
                        <input type="hidden" value="{cardBlank_1_customNumber}" id="cardBlankCustomNumber1"/>

                        <input type="hidden" value="{cardBlank_2_type}" id="cardBlankType2"/>
                        <input type="hidden" value="{cardBlank_2_resource}" id="cardBlankResource2"/>
                        <input type="hidden" value="{cardBlank_2_id}" id="cardBlankId2"/>
                        <input type="hidden" value="{cardBlank_2_customNumber}" id="cardBlankCustomNumber2"/>

                        <input type="hidden" value="{cardBlank_3_type}" id="cardBlankType3"/>
                        <input type="hidden" value="{cardBlank_3_resource}" id="cardBlankResource3"/>
                        <input type="hidden" value="{cardBlank_3_id}" id="cardBlankId3"/>
                        <input type="hidden" value="{cardBlank_3_customNumber}" id="cardBlankCustomNumber3"/>

                        <input type="hidden" value="{accountBlank_1_type}" id="accountBlankType1"/>
                        <input type="hidden" value="{accountBlank_1_resource}" id="accountBlankResource1"/>
                        <input type="hidden" value="{accountBlank_1_id}" id="accountBlankId1"/>
                        <input type="hidden" value="{accountBlank_1_customNumber}" id="accountBlankCustomNumber1"/>

                        <input type="hidden" value="{accountBlank_2_type}" id="accountBlankType2"/>
                        <input type="hidden" value="{accountBlank_2_resource}" id="accountBlankResource2"/>
                        <input type="hidden" value="{accountBlank_2_id}" id="accountBlankId2"/>
                        <input type="hidden" value="{accountBlank_2_customNumber}" id="accountBlankCustomNumber2"/>

                        <input type="hidden" value="{accountBlank_3_type}" id="accountBlankType3"/>
                        <input type="hidden" value="{accountBlank_3_resource}" id="accountBlankResource3"/>
                        <input type="hidden" value="{accountBlank_3_id}" id="accountBlankId3"/>
                        <input type="hidden" value="{accountBlank_3_customNumber}" id="accountBlankCustomNumber3"/>
                    </div>
                </xsl:if>

                <div class="darkGrayUnderline"></div>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">&nbsp;</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="rowValue">
                        <div class="textTick">
                            <input class="float" id="stockholder" name="stockholder" type="checkbox" onclick="FormObject.hideOrShowInfoAboutStock(this);"/>
                            <label class="float" for="stockholder">&nbsp;Я акционер Сбербанка России</label>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>

                <div id="infoAboutStock" style="display: none">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Количество обыкновенных акций Сбербанка России</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="rowValue">
                           <input id="ordinaryStockCount" name="ordinaryStockCount" type="text"  maxLength="10" value="{ordinaryStockCount}"/>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Количество привилигированных акций Сбербанка России</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="rowValue">
                           <input id="preferredStockCount" name="preferredStockCount" type="text" maxLength="10" value="{preferredStockCount}"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </div>

                <xsl:if test="($isGuest and gh:hasPhoneInMB() = true()) or not($isGuest)">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">&nbsp;</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <div class="textTick">
                                <input class="float" id="agreeRequestBKI" name="agreeRequestBKI" type="checkbox"/>
                                <label class="float maxTickText" for="agreeRequestBKI">&nbsp;Я согласен на <a class="orangeText" id="popupTerms" href="#"><span>запрос информации банком из БКИ (бюро кредитных историй)</span></a></label>
                           </div>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName" select="'Укажите Ваш код субъекта кредитной истории:'"/>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="rowValue">
                        <input id="subjectCreditHistoryCode" name="subjectCreditHistoryCode" type="text" value="{subjectCreditHistoryCode}"/>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">&nbsp;</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="rowValue">
                        <div class="textTick">
                            <input class="float" id="agreeRequestPFP" name="agreeRequestPFP" type="checkbox" onclick="FormObject.onChgAgreeRequestPFP(this)"/>
                            <label class="float" for="agreeRequestPFP">&nbsp;Я согласен на запрос информации банком из ПФР</label>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId" select="'snilsRow'"/>
                    <xsl:with-param name="rowName" select="'Ваш СНИЛС:'"/>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="rowValue">
                        <input id="snils" name="snils" type="text" value="{snils}"/>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">&nbsp;</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="rowValue">
                        <div class="textTick">
                            <input class="float" id="agreeReceiveCard" name="agreeReceiveCard" type="checkbox"/>
                            <label class="float maxTickText" for="agreeReceiveCard">&nbsp;Я согласен на получение кредитной карты ОАО "Сбербанк России" при принятии такого решения банком</label>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="loanOffersClaimTerms">
                    <xsl:with-param name="element" select="'popupTerms'"/>
                </xsl:call-template>

                <script type="text/javascript">
                    var accounts;
                    var cards;

                    function fillSavedInfo(fieldPrefix, maxCount)
                    {
                        var info = [];
                        for (var i=1; i &lt; maxCount+1; i++)
                        {
                            var data = {};

                            var type = ensureElement(fieldPrefix+"Type"+i).value;
                            if (type != "")
                            {
                                data.type = type;

                                var code = ensureElement(fieldPrefix+"Id"+i).value;
                                if (code != "")
                                    data.code = code;
                                else
                                {
                                    code = ensureElement(fieldPrefix+"Resource"+i).value;
                                    if (code != "")
                                        data.code = code;
                                }

                                if (fieldPrefix == "accountBlank" || fieldPrefix == "cardBlank")
                                {
                                    var customNumber = ensureElement(fieldPrefix+"CustomNumber"+i).value;
                                    if (customNumber != "")
                                        data.customNumber = customNumber;
                                }

                                info.push(data);
                            }
                        }
                        return info;
                    }



                    var FormObject = new (function() {
                        var source                 = null;
                        var methodObtaining;
                        var receivingResource;
                        var accountNumber;
                        var resourceRow;
                        var methodObtainings = new Object();
                        var accountNumberRow;
                        var cardReceivingResource;
                        var accountReceivingResource;
                        var onCurrentCardMessage;
                        var onNewCardMessage;
                        var onNewAccountMessage;
                        var onCurrentAccountMessage;

                        function lock(lock)
                        {
                            if (lock)
                            {
                                $(lock).css('display',   'none')
                            }
                        }

                        function unlock(unlock)
                        {
                            if (unlock)
                            {
                                $(unlock).css('display', '')
                            }
                        }

                        return {
                            init : function()
                            {
                                 <xsl:for-each select="$claimDictionaries/config/list-loan-issue-method/*">
                                        <xsl:variable name="id" select="./code"/>
                                        <xsl:variable name="productForLoan" select="./productForLoan"/>
                                        <xsl:variable name="newProductForLoan" select="./newProductForLoan"/>
                                        <xsl:choose>
                                            <xsl:when test="$productForLoan = 'CARD' and $newProductForLoan = 'true'">
                                                methodObtainings['<xsl:value-of select="$id"/>'] = 'ON_NEW_CARD';
                                            </xsl:when>
                                            <xsl:when test="$productForLoan = 'CARD' and $newProductForLoan='false'">
                                                methodObtainings['<xsl:value-of select="$id"/>'] = 'ON_CURRENT_CARD';
                                            </xsl:when>
                                            <xsl:when test="$productForLoan = 'CURRENT_ACCOUNT' and $newProductForLoan = 'true'">
                                                methodObtainings['<xsl:value-of select="$id"/>'] = 'ON_NEW_ACCOUNT';
                                            </xsl:when>
                                            <xsl:when test="$productForLoan = 'CURRENT_ACCOUNT' and $newProductForLoan = 'false'">
                                                methodObtainings['<xsl:value-of select="$id"/>'] = 'ON_CURRENT_ACCOUNT';
                                            </xsl:when>
                                        </xsl:choose>
                                </xsl:for-each>
                                methodObtaining      = $('#creditMethodObtaining');
                                receivingResource    = $('#receivingResource');
                                cardReceivingResource = $('#cardReceivingResource');
                                accountReceivingResource = $('#accountReceivingResource');
                                onCurrentCardMessage = $('#onCurrentCardMessage')[0];
                                onNewCardMessage     = $('#onNewCardMessage')[0];
                                onNewAccountMessage = $('#onNewAccountMessage')[0];
                                onCurrentAccountMessage = $('#onCurrentAccountMessage')[0];

                                var stockholder      = '<xsl:value-of select="stockholder"/>';
                                var agreeRequestBKI  = '<xsl:value-of select="agreeRequestBKI"/>';
                                var agreeRequestPFP  = '<xsl:value-of select="agreeRequestPFP"/>';
                                var agreeReceiveCard = '<xsl:value-of select="agreeReceiveCard"/>';

                                if (stockholder == 'on' || stockholder == 'true')
                                {
                                    $('#infoAboutStock').css('display', '');
                                    $('#stockholder').attr('checked', 'checked');
                                }

                                if (agreeRequestBKI == 'on' || agreeRequestBKI == 'true')
                                {
                                    $('#agreeRequestBKI').attr('checked', 'checked');
                                }

                                if (agreeRequestPFP == 'on' || agreeRequestPFP == 'true')
                                {
                                    hideOrShow(ensureElement('snilsRow'), false);
                                    $('#agreeRequestPFP').attr('checked', 'checked');
                                }

                                if (agreeRequestPFP == 'off' || agreeRequestPFP == 'false'|| agreeRequestPFP == ''|| agreeRequestPFP == 'null')
                                {
                                    hideOrShow(ensureElement('snilsRow'), true);
                                }

                                if (agreeReceiveCard == 'on' || agreeReceiveCard == 'true')
                                {
                                    $('#agreeReceiveCard').attr('checked', 'checked');
                                }
                                var methodCode = '<xsl:value-of select="creditMethodObtaining"/>';
                                var methodName = methodObtainings[methodCode];
                                this.selectCreditMethodObtaining(methodName);
                                var receivingRecourceId = '<xsl:value-of select="receivingResourceId"/>';
                                if (receivingRecourceId != "")
                                    this.onChangeResource(receivingRecourceId);
                                else
                                    this.onChangeResource('<xsl:value-of select="receivingResource"/>');

                                $('#accountNumber').keyup(function()
                                {
                                    $("input[name='accountNumber']").val( this.value.replace(/[^\d]*/g, '') );
                                });

                                $('#accountNumber').createMask('9999 9999 9999 9999 9999');
                                $('#snils').createMask('999-999-999 99');
                            },

                            /*
                            * В зависимости от сохраненного значения выставляем указатель в выпадающем списке
                            * elementName id вададающего списка
                            * value значение
                            */
                            onChangeAdditional : function(elementName, value)
                            {
                                // находим элемент на форме
                                var element = $('#' + elementName);
                                // находим пустое значение в списке
                                var emptyOption = element.find('option[value=""]')[0];
                                // если значение не определено, выставляем пустое значение в списке
                                if (!value)
                                {
                                    emptyOption.selected = true;
                                }
                                else
                                {
                                    // если значение определено, находим его в списке, если не нашли, выставляем пустое значение
                                    var option = element.find('option[value="' + value + '"]')[0];
                                    if (!option)
                                    {
                                        emptyOption.selected = true;
                                    }
                                    else
                                    {
                                        option.selected = true;
                                    }
                                }
                            },

                            onChangeResource : function(value)
                            {
                                var methodCode = methodObtaining.val();
                                var methodName = methodObtainings[methodCode];
                                if (value)
                                {
                                    switch(methodName)
                                    {
                                        case 'ON_CURRENT_CARD':
                                        {
                                            var option = cardReceivingResource.find('option[value="' + value + '"]')[0];
                                            if (option)
                                            {
                                               option.selected = true;
                                            }
                                            removeInformation('Новый счет открыт. Кредитные средства будут перечислены на этот счет', 'warnings');
                                            break;
                                        }
                                        case 'ON_NEW_CARD':
                                        {
                                            cardReceivingResource[0].disabled = true;
                                            removeInformation('Новый счет открыт. Кредитные средства будут перечислены на этот счет', 'warnings');
                                            break;
                                        }
                                        case 'ON_CURRENT_ACCOUNT':
                                        {
                                            var option = accountReceivingResource.find('option[value="' + value + '"]')[0];
                                            if (option)
                                            {
                                               option.selected = true;
                                            }
                                            removeInformation('Новый счет открыт. Кредитные средства будут перечислены на этот счет', 'warnings');
                                            break;
                                        }
                                        case 'ON_NEW_ACCOUNT':
                                        {
                                            addMessage('Новый счет открыт. Кредитные средства будут перечислены на этот счет');
                                            var option = accountReceivingResource.find('option[value="' + value + '"]')[0];
                                            if (option)
                                            {
                                               option.selected = true;
                                            }
                                            break;
                                        }
                                    }
                                }
                            },

                            hideOrShowMessages :function()
                            {
                                var methodCode = methodObtaining.val();
                                var methodName = methodObtainings[methodCode];
                                switch(methodName)
                                {
                                    case 'ON_CURRENT_CARD':
                                    {
                                        <xsl:if test="$isNeedConfirmDebitOperationERKC or $isLockOperationDebit">
                                            onCurrentCardMessage.style.display = '';
                                        </xsl:if>
                                        onNewCardMessage.style.display = 'none';
                                        break;
                                    }
                                    case 'ON_NEW_CARD':
                                    {
                                        onCurrentCardMessage.style.display = 'none';
                                        onNewCardMessage.style.display = '';
                                        break;
                                    }
                                    case 'ON_CURRENT_ACCOUNT':
                                    {
                                        <xsl:if test="$isNeedConfirmDebitOperationERKC or $isLockOperationDebit">
                                           onCurrentAccountMessage.style.display = '';
                                        </xsl:if>
                                        onNewAccountMessage.style.display = 'none';
                                        break;
                                    }
                                    case 'ON_NEW_ACCOUNT':
                                    {
                                        onCurrentAccountMessage.style.display = 'none';
                                        onNewAccountMessage.style.display = '';
                                        break;
                                    }
                                }
                            },

                            hideOrShowInfoAboutStock : function(element)
                            {
                                if (element.checked)
                                {
                                    $(infoAboutStock).css('display', '');
                                }
                                else
                                {
                                    $(infoAboutStock).css('display', 'none');
                                    $('#ordinaryStockCount').val('');
                                    $('#preferredStockCount').val('');
                                }
                            },

                            showDepartments : function()
                            {
                                var windowWidth  = 820;
                                var windowHeight = 900;
                                var arg =  'filter(relationType)=<xsl:value-of select="sbrfRelationType"/>&amp;filter(isLoanOffer)=<xsl:value-of select="loanOfferId != ''"/>';
                                var app;
                                if (<xsl:value-of select="$isAdminApplication"/>)
                                    app = "/PhizIA";
                                else
                                    app = "/PhizIC";
                                var win  =  window.open(app + "/private/loanclaim/office/list.do?" + arg, 'Departments', "resizable=1, menubar=0, toolbar=0, scrollbars=1, width=" + windowWidth + ", height=" + windowHeight);
                                win.moveTo((screen.width - windowWidth)/2, (screen.height - windowHeight)/2);

                                return false;
                            },

                            openNewAccount: function()
                            {
                                removeAllMessages();
                                onNewAccountMessage.style.display = 'none';

                                var tb = $('#receivingRegion')[0].value;
                                if (!tb)
                                {
                                    removeAllMessages();
                                    addMessage('Пожалуйста выберите подразделение в котором хотите получить кредит');
                                }
                                else
                                {
                                    var paymentInputs = $(":input");
                                    for (var index = 0; index &lt; paymentInputs.length; index++)
                                    {
                                        paymentInputs[index].disabled = true;
                                    }

                                    var token = $(":input[name='org.apache.struts.taglib.html.TOKEN']")[0].value;

                                    var osb = $('#receivingBranch')[0].value;
                                    var vsp = $('#receivingOffice')[0].value;
                                    var url = document.webRoot + "/private/payments/accountOpeningClaim/payment.do";
                                    var params = "id=" + getQueryStringParameter("id")+ "&amp;org.apache.struts.taglib.html.TOKEN=" + token + "&amp;tb=" + tb + "&amp;osb=" + osb + "&amp;vsp=" + vsp;
                                    ajaxQuery(params, url, null, null, true);
                                }
                            },

                            showCardBlock: function()
                            {
                                hideOrShow('receiveOnCardBlock', false);
                                hideOrShow('receiveOnAccountBlock', true);

                                $('#receiverSubTypeControl').find('div').removeClass('activeButton');
                                $('#receiveOnCardButton').addClass('activeButton');
                            },

                            showAccountBlock: function()
                            {
                                hideOrShow('receiveOnCardBlock', true);
                                hideOrShow('receiveOnAccountBlock', false);

                                $('#receiverSubTypeControl').find('div').removeClass('activeButton');
                                $('#receiveOnAccountButton').addClass('activeButton');
                            },

                            selectCreditMethodObtaining : function(valueMethod)
                            {
                                for (key in methodObtainings) {
                                    if (methodObtainings[key]===valueMethod)
                                       $('#creditMethodObtaining')[0].value = key;
                                }

                                switch(valueMethod)
                                {
                                    case 'ON_NEW_CARD':
                                    {
                                        $('#onNewCard').attr('checked', 'true');
                                        this.showCardBlock();
                                        cardReceivingResource[0].disabled = true;
                                        removeInformation('Новый счет открыт. Кредитные средства будут перечислены на этот счет', 'warnings');
                                        break;
                                    }
                                    case 'ON_CURRENT_CARD':
                                    {
                                        $('#onCurrentCard').attr('checked', 'true');
                                        this.showCardBlock();
                                        cardReceivingResource[0].disabled = false;
                                        removeInformation('Новый счет открыт. Кредитные средства будут перечислены на этот счет', 'warnings');
                                        break;
                                    }
                                    case 'ON_CURRENT_ACCOUNT':
                                    case 'ON_NEW_ACCOUNT':
                                    {
                                        this.showAccountBlock();
                                        cardReceivingResource[0].disabled = true;
                                        break;
                                    }
                                    default:
                                    {
                                        this.showCardBlock();
                                        cardReceivingResource[0].disabled = true;
                                        break;
                                    }
                                }
                                this.hideOrShowMessages();
                            },

                            onChgAgreeRequestPFP: function(element)
                            {
                              if (element.checked)
                              {
                                   element.value = "true";
                                    var el = document.getElementById('snils');
                                    el.removeAttribute('disabled');
                              }
                              else
                              {
                                   element.value = "false";
                                    var el = document.getElementById('snils');
                                    el.setAttribute('disabled', 'disabled');
                              }
                              hideOrShow(ensureElement('snilsRow'), !element.checked);
                            }
                        }
                    }) ();

                    function setOfficeInfo(a)
                    {
                        $('#receivingRegion').val(a["region"]);
                        $('#receivingOffice').val(a["office"]);
                        $('#receivingBranch').val(a["branch"]);

                        $('#receivingDepartmentLabel').text(a["name"]);
                        $('#receivingDepartmentName').val(a["name"]);
                    }


                    function checkPayment()
                    {
                        if (<xsl:value-of select="$showCardOrAcountBlock"/>)
                        {
                            cards.updateInputInfo();
                            accounts.updateInputInfo();
                        }
                        $('#receivingResource:hidden').remove();
                        $('#receiveOnCardBlock:hidden').remove();
                        $('#receiveOnAccountBlock:hidden').remove();
                        return true;
                    }

                    function selectDepartment()
                    {
                        var showDepartment = $("#showDepartment");
                        <xsl:choose>
                            <xsl:when test="pu:impliesService('SelectionOfficeByMapService')">
                                var error = function (httpObj, textStatus)
                                {
                                    showDepartment.attr("onclick", "");
                                    showDepartment.bind("click", function() { FormObject.showDepartments(); return false; });
                                    showDepartment.html("<span>Изменить подразделение</span>");
                                };
                                var success = function (data, status, xhr)
                                {
                                    if (!data || data == null)
                                    {
                                        showDepartment.attr("onclick", "");
                                        showDepartment.bind("click", function() { FormObject.showDepartments(); return false; });
                                        showDepartment.html("<span>Изменить подразделение</span>");
                                    }
                                    else
                                    {
                                        showDepartment.attr("onclick", "");
                                        showDepartment.bind("click", function() { win.open('selectDepartment'); return false; });
                                        showDepartment.html("<span>Показать на карте</span>");
                                    }
                                };
                                try
                                {
                                    loadData([3354], "042", success, error);
                                }
                                catch (e)
                                {
                                    showDepartment.attr("onclick", "");
                                    showDepartment.bind("click", function() { FormObject.showDepartments(); return false; });
                                    showDepartment.html("<span>Изменить подразделение</span>");
                                }
                            </xsl:when>
                            <xsl:otherwise>
                                showDepartment.attr("onclick", "");
                                showDepartment.bind("click", function() { FormObject.showDepartments(); return false; });
                                showDepartment.html("<span>Изменить подразделение</span>");
                            </xsl:otherwise>
                        </xsl:choose>
                    }

                    $(document).ready(function()
                    {
                        window.scrollTo(0, 0);

                        FormObject.init();

                        selectDepartment();

                        if (<xsl:value-of select="$showCardOrAcountBlock"/>)
                        {
                            var cardsInfo = fillSavedInfo('cardBlank', 3);
                            cards = createCardsList(3, cardsInfo);

                            var accountsInfo = fillSavedInfo('accountBlank', 3);
                            accounts = createAccountsList(3, accountsInfo);
                                $('.loan-claim-button .buttonSelect').hover(function(){
                                    $(this).addClass('hoverOperation');
                                }, function() {
                                   $(this).removeClass('hoverOperation');
                                });
                        }
                    });

                </script>
            </xsl:when>

            <!--
                *********************************************************************************************
                                                8 шаг:
                *********************************************************************************************
            -->
            <xsl:when test="$documentState = 'INITIAL8'">
                <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.customCreationBlock.js"/>

                <div class="questionaryTable">
                    <h4 class="questionaryTitle">Анкета</h4>
                    <p class="descQuestion">Пожалуйста, ответьте на несколько вопросов анкеты для завершения заявки на кредит.</p>

                    <xsl:call-template name="button">
                        <xsl:with-param name="id" select="'templateRemoveBtn'"/>
                        <xsl:with-param name="onclick" select="''"/>
                        <xsl:with-param name="typeBtn" select="'blueLink'"/>
                    </xsl:call-template>

                    <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.js"/>
                    <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.additionalCardBlock.js"/>
                    <script type="text/javascript" src="{$resourceRoot}/scripts/fblib.additionalAccountBlock.js"/>

                    <xsl:variable name="questionCount" select="question_count"/>

                    <input type="hidden" name="question_count" value="{$questionCount}"/>
                    <xsl:if test="$questionCount != ''">
                        <div class="questionTableStyle">
                            <table class="questionTable">
                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_1_id"/>
                                    <xsl:with-param name="answerType" select="question_1_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_1_text"/>
                                    <xsl:with-param name="answer" select="question_1_answer"/>
                                    <xsl:with-param name="index" select="1"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_2_id"/>
                                    <xsl:with-param name="answerType" select="question_2_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_2_text"/>
                                    <xsl:with-param name="answer" select="question_2_answer"/>
                                    <xsl:with-param name="index" select="2"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_3_id"/>
                                    <xsl:with-param name="answerType" select="question_3_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_3_text"/>
                                    <xsl:with-param name="answer" select="question_3_answer"/>
                                    <xsl:with-param name="index" select="3"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_4_id"/>
                                    <xsl:with-param name="answerType" select="question_4_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_4_text"/>
                                    <xsl:with-param name="answer" select="question_4_answer"/>
                                    <xsl:with-param name="index" select="4"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_5_id"/>
                                    <xsl:with-param name="answerType" select="question_5_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_5_text"/>
                                    <xsl:with-param name="answer" select="question_5_answer"/>
                                    <xsl:with-param name="index" select="5"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_6_id"/>
                                    <xsl:with-param name="answerType" select="question_6_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_6_text"/>
                                    <xsl:with-param name="answer" select="question_6_answer"/>
                                    <xsl:with-param name="index" select="6"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_7_id"/>
                                    <xsl:with-param name="answerType" select="question_7_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_7_text"/>
                                    <xsl:with-param name="answer" select="question_7_answer"/>
                                    <xsl:with-param name="index" select="7"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_8_id"/>
                                    <xsl:with-param name="answerType" select="question_8_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_8_text"/>
                                    <xsl:with-param name="answer" select="question_8_answer"/>
                                    <xsl:with-param name="index" select="8"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_9_id"/>
                                    <xsl:with-param name="answerType" select="question_9_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_9_text"/>
                                    <xsl:with-param name="answer" select="question_9_answer"/>
                                    <xsl:with-param name="index" select="9"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_10_id"/>
                                    <xsl:with-param name="answerType" select="question_10_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_10_text"/>
                                    <xsl:with-param name="answer" select="question_10_answer"/>
                                    <xsl:with-param name="index" select="10"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_11_id"/>
                                    <xsl:with-param name="answerType" select="question_11_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_11_text"/>
                                    <xsl:with-param name="answer" select="question_11_answer"/>
                                    <xsl:with-param name="index" select="11"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_12_id"/>
                                    <xsl:with-param name="answerType" select="question_12_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_12_text"/>
                                    <xsl:with-param name="answer" select="question_12_answer"/>
                                    <xsl:with-param name="index" select="12"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_13_id"/>
                                    <xsl:with-param name="answerType" select="question_13_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_13_text"/>
                                    <xsl:with-param name="answer" select="question_13_answer"/>
                                    <xsl:with-param name="index" select="13"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_14_id"/>
                                    <xsl:with-param name="answerType" select="question_14_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_14_text"/>
                                    <xsl:with-param name="answer" select="question_14_answer"/>
                                    <xsl:with-param name="index" select="14"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_15_id"/>
                                    <xsl:with-param name="answerType" select="question_15_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_15_text"/>
                                    <xsl:with-param name="answer" select="question_15_answer"/>
                                    <xsl:with-param name="index" select="15"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_16_id"/>
                                    <xsl:with-param name="answerType" select="question_16_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_16_text"/>
                                    <xsl:with-param name="answer" select="question_16_answer"/>
                                    <xsl:with-param name="index" select="16"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_17_id"/>
                                    <xsl:with-param name="answerType" select="question_17_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_17_text"/>
                                    <xsl:with-param name="answer" select="question_17_answer"/>
                                    <xsl:with-param name="index" select="17"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_18_id"/>
                                    <xsl:with-param name="answerType" select="question_18_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_18_text"/>
                                    <xsl:with-param name="answer" select="question_18_answer"/>
                                    <xsl:with-param name="index" select="18"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_19_id"/>
                                    <xsl:with-param name="answerType" select="question_19_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_19_text"/>
                                    <xsl:with-param name="answer" select="question_19_answer"/>
                                    <xsl:with-param name="index" select="19"/>
                                </xsl:call-template>

                                <xsl:call-template name="questionEditRow">
                                    <xsl:with-param name="questionId" select="question_20_id"/>
                                    <xsl:with-param name="answerType" select="question_20_answer_type"/>
                                    <xsl:with-param name="questionText" select="question_20_text"/>
                                    <xsl:with-param name="answer" select="question_20_answer"/>
                                    <xsl:with-param name="index" select="20"/>
                                </xsl:call-template>

                                <script type="text/javascript">
                                    function yesClick(index)
                                    {
                                        var yes = $(".question" + index + " .answerTick");
                                        if (!yes.hasClass("tickActive"))
                                        {
                                            yes.addClass("tickActive");
                                            var no = $(".question" + index + " .answerCross");
                                            no.removeClass("crossActive");
                                            var field = $(".question" + index + " .question_" + index + "_answer");
                                            field.val("1");
                                        }
                                    }

                                    function noClick(index)
                                    {
                                        var no = $(".question" + index + " .answerCross");
                                        if (!no.hasClass("crossActive"))
                                        {
                                            no.addClass("crossActive");
                                            var yes = $(".question" + index + " .answerTick");
                                            yes.removeClass("tickActive");
                                            var field = $(".question" + index + " .question_" + index + "_answer");
                                            field.val("0");
                                        }
                                    }
                                </script>
                            </table>
                        </div>
                    </xsl:if>

                </div>
            </xsl:when>
        </xsl:choose>

        <!--<div class="darkGrayUnderline"></div>-->
        <xsl:call-template name="state">
            <xsl:with-param name="stateCode" select="state"/>
            <xsl:with-param name="refuseReason" select="refuseReason"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <xsl:variable name="claimDictionaries" select="document('loan-claim-dictionaries.xml')"/>
        <xsl:variable name="departments" select="document('tbList.xml')"/>

        <script type="text/javascript">
            var formObjectSavedInfo =
            {
                familyInfoBlock: null,
                personInfoBlock: null,
                addressInfoBlock: null,
                workInfoBlock: null,
                propertyAndDebtsInfoBlock: null,
                workInfoBlock: null,
                questionnaireBlock: null,

                init: function()
                {
                    this.familyInfoBlock = ensureElement('familyInfo');
                    this.personInfoBlock = ensureElement('personInfo');
                    this.addressInfoBlock = ensureElement('addressInfo');
                    this.workInfoBlock = ensureElement('workInfo');
                    this.propertyAndDebtsInfoBlock = ensureElement('propertyAndDebtsInfo');
                    this.additionalInfoBlock = ensureElement('additionalInfo');
                    this.questionnaireBlock = ensureElement('questionnaire');
                },

                hideOrShowBlock: function(elem)
                {
                    hideOrShow(elem, elem.style.display != 'none');
                }

            };
            $(document).ready(function(){
                window.scrollTo(0, 0);
            formObjectSavedInfo.init();
                $('.loan-claim-button .buttonSelect').hover(function(){
                    $(this).addClass('hoverOperation');
                }, function() {
                   $(this).removeClass('hoverOperation');
                });
                $(".forBankStamp").addClass("forLoanStamp");
            });

        </script>
        <xsl:if test="stepFilled-INITIAL2='true'">
            <!--Персональные данные-->
            <xsl:call-template name="button">
                <xsl:with-param name="style" select="'display: block;'"/>
                <xsl:with-param name="btnText" select="'Личные данные'"/>
                <xsl:with-param name="onclick" select="'formObjectSavedInfo.hideOrShowBlock(formObjectSavedInfo.personInfoBlock);'"/>
                <xsl:with-param name="typeBtn" select="'blueLink openBlocks'"/>
            </xsl:call-template>
            <div class="clear"></div>

            <input type="hidden" name="loanOfferId" id="loanOffer" value="{loanOfferId}"/>

            <!--блок "принадлежность к Сбербанку"-->
            <div id="personInfo" style="display: none;">
                <xsl:if test="loanOfferId = ''">
                    <xsl:variable name="sbrfRelationType" select="sbrfRelationType"/>
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Принадлежность к Сбербанку:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <span><b>
                                <xsl:choose>
                                    <xsl:when test="$sbrfRelationType = 'getPaidOnSbrfAccount'">
                                        <xsl:value-of select="'Получаю з/п (пенсию) на карту/счет в Сбербанке'"/>
                                    </xsl:when>
                                    <xsl:when test="$sbrfRelationType = 'workInSbrf'">
                                        <xsl:value-of select="'Являюсь сотрудником Сбербанка'"/>
                                    </xsl:when>
                                    <xsl:when test="$sbrfRelationType = 'otherSbrfRelationType'">
                                        <xsl:value-of select="'Другое'"/>
                                    </xsl:when>
                                </xsl:choose>
                            </b></span>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Счёт или карта:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <span><b>
                                <xsl:choose>
                                    <xsl:when test="sbrfResource != ''">
                                        <xsl:value-of select="sbrfResource"/>
                                    </xsl:when>
                                    <xsl:when test="sbrfAccount != ''">
                                        Счёт №<xsl:value-of select="sbrfAccount"/>
                                    </xsl:when>
                                </xsl:choose>
                            </b></span>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
                <xsl:call-template name="titleRow">
                     <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Краткая информация о Вас</b></xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">ФИО:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:choose>
                           <!--Не маскировать для АРМ сотрудника-->
                            <xsl:when test="$isAdminApplication='true'">
                                <span class="floatLeft">
                                    <b>
                                        <xsl:value-of select="firstName" disable-output-escaping="no"/>&nbsp;
                                        <xsl:value-of select="patrName" disable-output-escaping="no"/>&nbsp;
                                        <xsl:value-of select="surName" disable-output-escaping="no"/>
                                    </b>
                                </span>
                            </xsl:when>
                            <xsl:otherwise>
                                <span class="floatLeft"><b><xsl:value-of select="ph:getFormattedPersonName(firstName, surName, patrName)" disable-output-escaping="no"/></b></span>
                                <xsl:call-template name="popUpMessage">
                                    <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                                    <xsl:with-param name="app" select="$app"/>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Ваш пол:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="gender" select="gender"/>
                        <div class="textTick separatedRadio">
                            <input class="float" type="radio" disabled="disabled">
                                <xsl:if test="$gender = 'MALE'">
                                    <xsl:attribute name="checked">true</xsl:attribute>
                                </xsl:if>
                            </input>
                           <label class="float">&nbsp;Мужчина</label>
                        </div>
                        <div class="textTick separatedRadio">
                            <input class="float" type="radio" disabled="disabled">
                                <xsl:if test="$gender = 'FEMALE'">
                                    <xsl:attribute name="checked">true</xsl:attribute>
                                </xsl:if>
                            </input>
                            <label class="float">&nbsp;Женщина</label>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Дата рождения:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:choose>
                            <xsl:when test="$isAdminApplication='true'">
                                <span class="floatLeft"><b><xsl:value-of  select="dh:formatXsdDateToString(birthDay)"/></b></span>
                            </xsl:when>
                            <xsl:otherwise>
                                <span class="floatLeft"><b><xsl:value-of  select="mask:getMaskedDate()"/></b></span>
                                <xsl:call-template name="popUpMessage">
                                    <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                                    <xsl:with-param name="app" select="$app"/>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Гражданство:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b>Российская Федерация</b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Место рождения:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="birthPlace"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Номер мобильного телефона:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <span class="floatLeft"><b>+<xsl:value-of  select="mobileCountry"/>&nbsp;<xsl:value-of  select="mobileTelecom"/>&nbsp;<xsl:value-of  select="mobileNumber"/></b></span>
                        <xsl:call-template name="popUpMessage">
                            <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                            <xsl:with-param name="app" select="$app"/>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Номер рабочего телефона:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <span class="floatLeft"><b>+<xsl:value-of  select="jobphoneCountry"/>&nbsp;<xsl:value-of  select="jobphoneTelecom"/>&nbsp;<xsl:value-of  select="jobphoneNumber"/></b></span>
                        <xsl:call-template name="popUpMessage">
                            <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                            <xsl:with-param name="app" select="$app"/>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="residencePhoneCountry != '' and residencePhoneTelecom != '' and residencePhoneNumber != ''">
                    <xsl:call-template name="additionalPhoneViewInfo">
                        <xsl:with-param name="phoneBlankType">RESIDENCE</xsl:with-param>
                        <xsl:with-param name="phoneBlankCountry"><xsl:value-of  select="residencePhoneCountry"/></xsl:with-param>
                        <xsl:with-param name="phoneBlankTelecom"><xsl:value-of  select="residencePhoneTelecom"/></xsl:with-param>
                        <xsl:with-param name="phoneBlankNumber"><xsl:value-of  select="residencePhoneNumber"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:if test="phoneBlank_1_type != ''">
                    <xsl:call-template name="additionalPhoneViewInfo">
                        <xsl:with-param name="phoneBlankType"><xsl:value-of  select="phoneBlank_1_type"/></xsl:with-param>
                        <xsl:with-param name="phoneBlankCountry"><xsl:value-of  select="phoneBlank_1_country"/></xsl:with-param>
                        <xsl:with-param name="phoneBlankTelecom"><xsl:value-of  select="phoneBlank_1_telecom"/></xsl:with-param>
                        <xsl:with-param name="phoneBlankNumber"><xsl:value-of  select="phoneBlank_1_number"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Электронная почта:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="email"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Ваш ИНН:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="inn"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="fioChanged ='true'">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">&nbsp;</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <div class="textTick">
                                <input class="float" type="checkbox" disabled="disabled" checked="checked"/>
                                <label class="float">&nbsp;<b>Я менял (-а) ФИО</b></label>
                            </div>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Дата изменения ФИО:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of  select="dh:formatXsdDateToString(fioChangedDate)"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Предыдущая фамилия:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of  select="previosSurname"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Предыдущее имя:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of  select="previosFirstname"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Предыдущее отчество:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of  select="previosPatr"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Причина изменения:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:choose>
                                <xsl:when test="fioChangedReasonType = 'MARRIAGE'">
                                    <b>Вступление в брак</b>
                                </xsl:when>
                                <xsl:when test="fioChangedReasonType = 'OTHER'">
                                    <b><xsl:value-of  select="fioChangedOtherReason"/></b>
                                </xsl:when>
                            </xsl:choose>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:call-template name="titleRow">
                    <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Паспортные данные</b></xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Дата выдачи:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="dh:formatXsdDateToString(passportIssueDate)"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Код подразделения:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="passportIssueByCode"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Серия и номер паспорта:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:choose>
                            <!--Не маскировать для АРМ сотрудника-->
                            <xsl:when test="$isAdminApplication='true'">
                                <span class="floatLeft"><xsl:value-of select="passportSeries"/> &nbsp; <xsl:value-of select="passportNumber"/></span>
                            </xsl:when>
                            <xsl:otherwise>
                                <span class="floatLeft"><b><xsl:value-of select="sh:maskText(passportSeries)"/> &nbsp; <xsl:value-of select="sh:maskText(passportNumber)"/></b></span>
                                <xsl:call-template name="popUpMessage">
                                    <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                                    <xsl:with-param name="app" select="$app"/>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Кем выдан:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="passportIssueBy"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="hasOldPassport = 'true'">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowValue">
                            <span class="blockTitle">Данные старого паспорта</span>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Дата выдачи:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of  select="dh:formatXsdDateToString(oldPassportIssueDate)"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Серия и номер паспорта:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of select="oldPassportSeriesAndNumber"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Кем выдан:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of select="oldPassportIssueBy"/></b>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">У вас есть загранпаспорт?:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="hasForeignPassport" select="hasForeignPassport"/>
                        <div class="textTick">
                            <input class="float" type="radio" disabled="disabled">
                                <xsl:if test="$hasForeignPassport = 'true'">
                                    <xsl:attribute name="checked">true</xsl:attribute>
                                </xsl:if>
                            </input>
                            <label class="float">Да &nbsp;</label>
                        </div>
                        <div class="textTick">
                            <input class="float" type="radio" disabled="disabled">
                                <xsl:if test="$hasForeignPassport = 'false'">
                                    <xsl:attribute name="checked">true</xsl:attribute>
                                </xsl:if>
                            </input>
                            <label class="float">Нет</label>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="titleRow">
                    <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Образование</b></xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Образование:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="educationType" select="educationTypeSelect"/>
                        <b><xsl:value-of  select="$claimDictionaries/config/list-education/education[code = $educationType]/name"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="educationTypeSelect = '4'">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Курс неоконченного высшего образования:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of  select="higherEducationCourse"/></b>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

            </div>
        </xsl:if>

        <!--Семейное положение-->
        <xsl:if test="stepFilled-INITIAL3='true'">
            <xsl:call-template name="button">
                <xsl:with-param name="style" select="'display: block;'"/>
                <xsl:with-param name="btnText" select="'Семья'"/>
                <xsl:with-param name="onclick" select="'formObjectSavedInfo.hideOrShowBlock(formObjectSavedInfo.familyInfoBlock);'"/>
                <xsl:with-param name="typeBtn" select="'blueLink openBlocks'"/>
            </xsl:call-template>
            <div class="clear"></div>

            <div id="familyInfo" style="display: none;">
                <xsl:call-template name="titleRow">
                    <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Семейное положение</b></xsl:with-param>
                </xsl:call-template>

                <xsl:variable name="familyStatuses" select="$claimDictionaries/config/list-family-status"/>

                <xsl:variable name="familyStatus"><xsl:value-of  select="familyStatusSelect"/></xsl:variable>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Семейное положение:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="$familyStatuses/family-status[code=$familyStatus]/name"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <!--Информация о супруге-->
                <xsl:variable name="spouseInfoRequired"><xsl:value-of  select="$familyStatuses/family-status[code=$familyStatus]/spouseInfoRequired"/></xsl:variable>
                <xsl:if test="$spouseInfoRequired != 'NOT'">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Фамилия супруга(-и):</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of  select="partnerSurname"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Имя супруга(-и):</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of  select="partnerFirstname"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Отчество супруга(-и):</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <b><xsl:value-of  select="partnerPatr"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Дата рождения супруга(-и):</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of  select="dh:formatXsdDateToString(partnerBirthday)"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Супруг находится на иждивении:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="partnerOnDependent" select="partnerOnDependent"/>
                            <div class="textTick">
                                <input class="float" type="radio" disabled="disabled">
                                    <xsl:if test="$partnerOnDependent = 'true'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label class="float">Да &nbsp;</label>
                            </div>
                            <div class="textTick">
                                <input class="float" type="radio" disabled="disabled">
                                    <xsl:if test="$partnerOnDependent= 'false'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label class="float">Нет</label>
                            </div>
                        </xsl:with-param>
                        <xsl:with-param name="isAllocate" select="'false'"/>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Супруг имеет кредиты в Сбербанке</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="partnerHasLoansInSber" select="partnerHasLoansInSber"/>
                            <div class="textTick">
                                <input class="float" type="radio" disabled="disabled">
                                    <xsl:if test="$partnerHasLoansInSber = '1'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label class="float">Да &nbsp;</label>
                            </div>
                            <div class="textTick">
                                <input class="float" type="radio" disabled="disabled">
                                    <xsl:if test="$partnerHasLoansInSber= '2'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label class="float">Нет &nbsp;</label>
                            </div>
                            <div class="textTick">
                                <input class="float" type="radio" disabled="disabled">
                                    <xsl:if test="$partnerHasLoansInSber= '3'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label class="float">Не знаю</label>
                            </div>
                        </xsl:with-param>
                        <xsl:with-param name="isAllocate" select="'false'"/>
                    </xsl:call-template>

                    <xsl:if test="$spouseInfoRequired='REQUIRED'">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="required" select="'true'"/>
                            <xsl:with-param name="rowName">Есть ли брачный контракт</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <xsl:variable name="hasPrenup" select="hasPrenup"/>
                                <div class="textTick">
                                    <input class="float" type="radio" disabled="disabled">
                                        <xsl:if test="$hasPrenup = 'true'">
                                            <xsl:attribute name="checked">true</xsl:attribute>
                                        </xsl:if>
                                    </input>
                                    <label class="float">Да &nbsp;</label>
                                </div>
                                <div class="textTick">
                                    <input class="float" type="radio" disabled="disabled">
                                        <xsl:if test="$hasPrenup= 'false'">
                                            <xsl:attribute name="checked">true</xsl:attribute>
                                        </xsl:if>
                                    </input>
                                    <label class="float">Нет</label>
                                </div>
                            </xsl:with-param>
                            <xsl:with-param name="isAllocate" select="'false'"/>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:if>

                <xsl:variable name="familyRelations" select="$claimDictionaries/config/list-family-relation"/>
                <!--Информация о детях-->
                <xsl:variable name="childType1" select="child_1_relativeType"/>
                <xsl:if test="childType1 != ''">
                    <xsl:call-template name="titleRow">
                        <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Дети</b></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="relativesViewInfo">
                        <xsl:with-param name="dictionary" select="$familyRelations"/>
                        <xsl:with-param name="title" select="'Информация о ребенке'"/>
                        <xsl:with-param name="relativeType"><xsl:value-of  select="$childType1"/></xsl:with-param>
                        <xsl:with-param name="relativeSurname"><xsl:value-of select="child_1_surname"/></xsl:with-param>
                        <xsl:with-param name="relativeName"><xsl:value-of select="child_1_name"/></xsl:with-param>
                        <xsl:with-param name="relativePatrName"><xsl:value-of select="child_1_patrName"/></xsl:with-param>
                        <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_1_birthday)"/></xsl:with-param>
                        <xsl:with-param name="relativeDependent"><xsl:value-of select="child_1_dependent"/></xsl:with-param>
                        <xsl:with-param name="relativeCredit"><xsl:value-of select="child_1_credit"/></xsl:with-param>
                        <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_1_employee"/></xsl:with-param>
                        <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_1_employeePlace"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:variable name="childType2" select="child_2_relativeType"/>
                    <xsl:if test="$childType2 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType2"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_2_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_2_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_2_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_2_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_2_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_2_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_2_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_2_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType3" select="child_3_relativeType"/>
                    <xsl:if test="$childType3 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType3"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_3_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_3_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_3_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_3_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_3_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_3_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_3_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_3_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType4" select="child_4_relativeType"/>
                    <xsl:if test="$childType4 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType4"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_4_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_4_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_4_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_4_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_4_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_4_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_4_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_4_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType5" select="child_5_relativeType"/>
                    <xsl:if test="$childType5 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType5"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_5_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_5_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_5_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_5_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_5_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_5_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_5_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_5_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType6" select="child_6_relativeType"/>
                    <xsl:if test="$childType6 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType6"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_6_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_6_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_6_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_6_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_6_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_6_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_6_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_6_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType7" select="child_7_relativeType"/>
                    <xsl:if test="$childType7 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType7"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_7_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_7_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_7_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_7_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_7_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_7_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_7_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_7_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType8" select="child_8_relativeType"/>
                    <xsl:if test="$childType8 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType8"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_8_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_8_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_8_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_8_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_8_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_8_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_8_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_8_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType9" select="child_9_relativeType"/>
                    <xsl:if test="$childType9 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType9"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_9_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_9_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_9_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_9_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_9_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_9_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_9_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_9_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType10" select="child_10_relativeType"/>
                    <xsl:if test="$childType10 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType10"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_10_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_10_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_10_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_10_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_10_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_10_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_10_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_10_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType11" select="child_11_relativeType"/>
                    <xsl:if test="$childType11 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType11"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_11_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_11_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_11_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_11_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_11_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_11_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_11_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_11_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType12" select="child_12_relativeType"/>
                    <xsl:if test="$childType12 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType12"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_12_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_12_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_12_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_12_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_12_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_12_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_12_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_12_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:if>

                <!--Информация о родственниках-->
                <xsl:variable name="relativeType1" select="relative_1_relativeType"/>
                <xsl:if test="$relativeType1 != ''">
                    <xsl:call-template name="titleRow">
                        <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Родственники</b></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="relativesViewInfo">
                        <xsl:with-param name="dictionary" select="$familyRelations"/>
                        <xsl:with-param name="title" select="'Информация о родственнике'"/>
                        <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType1"/></xsl:with-param>
                        <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_1_surname"/></xsl:with-param>
                        <xsl:with-param name="relativeName"><xsl:value-of select="relative_1_name"/></xsl:with-param>
                        <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_1_patrName"/></xsl:with-param>
                        <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_1_birthday)"/></xsl:with-param>
                        <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_1_dependent"/></xsl:with-param>
                        <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_1_credit"/></xsl:with-param>
                        <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_1_employee"/></xsl:with-param>
                        <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_1_employeePlace"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:variable name="relativeType2" select="relative_2_relativeType"/>
                    <xsl:if test="$relativeType2 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о родственнике'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType2"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_2_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_2_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_2_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_2_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_2_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_2_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_2_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_2_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType3" select="relative_3_relativeType"/>
                    <xsl:if test="$relativeType3 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о родственнике'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType3"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_3_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_3_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_3_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_3_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_3_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_3_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_3_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_3_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType4" select="relative_4_relativeType"/>
                    <xsl:if test="$relativeType4 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType4"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_4_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_4_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_4_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_4_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_4_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_4_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_4_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_4_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType5" select="relative_5_relativeType"/>
                    <xsl:if test="$relativeType5 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType5"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_5_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_5_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_5_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_5_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_5_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_5_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_5_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_5_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType6" select="relative_6_relativeType"/>
                    <xsl:if test="$relativeType6 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType6"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_6_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_6_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_6_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_6_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_6_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_6_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_6_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_6_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType7" select="relative_7_relativeType"/>
                    <xsl:if test="$relativeType7 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType7"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_7_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_7_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_7_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_7_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_7_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_7_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_7_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_7_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType8" select="relative_8_relativeType"/>
                    <xsl:if test="$relativeType8 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType8"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_8_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_8_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_8_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_8_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_8_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_8_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_8_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_8_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType9" select="relative_9_relativeType"/>
                    <xsl:if test="$relativeType9 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType9"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_9_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_9_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_9_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_9_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_9_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_9_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_9_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_9_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType10" select="relative_10_relativeType"/>
                    <xsl:if test="$relativeType10 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType10"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_10_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_10_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_10_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_10_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_10_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_10_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_10_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_10_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType11" select="relative_11_relativeType"/>
                    <xsl:if test="$relativeType11 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType11"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_11_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_11_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_11_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_11_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_11_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_11_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_11_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_11_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType12" select="relative_12_relativeType"/>
                    <xsl:if test="$relativeType12 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType12"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_12_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_12_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_12_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_12_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_12_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_12_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_12_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_12_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:if>

            </div>
        </xsl:if>

        <!--Прописка-->
        <xsl:if test="stepFilled-INITIAL4='true'">
            <xsl:call-template name="button">
                <xsl:with-param name="style" select="'display: block;'"/>
                <xsl:with-param name="btnText" select="'Прописка'"/>
                <xsl:with-param name="onclick" select="'formObjectSavedInfo.hideOrShowBlock(formObjectSavedInfo.addressInfoBlock);'"/>
                <xsl:with-param name="typeBtn" select="'blueLink openBlocks'"/>
            </xsl:call-template>
            <div class="clear"></div>

            <div id="addressInfo" style="display: none;">
                <xsl:call-template name="titleRow">
                    <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Адрес регистрации</b></xsl:with-param>
                </xsl:call-template>

                <xsl:variable name="registrationType1"><xsl:value-of select="registrationType1"/></xsl:variable>
                <xsl:if test="$registrationType1 != ''">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="rowValue">
                            <span class="blockTitle">
                                <xsl:choose>
                                    <xsl:when test="$registrationType1 = 'FIXED'">
                                        <xsl:value-of select="'Иформация о постоянной регистрации'"/>
                                    </xsl:when>
                                    <xsl:when test="$registrationType1 = 'TEMPORARY'">
                                        <xsl:value-of select="'Иформация о временной регистрации'"/>
                                    </xsl:when>
                                </xsl:choose>
                            </span>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="addressViewInfo">
                        <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                        <xsl:with-param name="regionSelectFieldValue"><xsl:value-of select="regionSelect1"/></xsl:with-param>
                        <xsl:with-param name="districtTypeSelectValue"><xsl:value-of select="districtTypeSelect1"/></xsl:with-param>
                        <xsl:with-param name="districtFieldValue"><xsl:value-of select="district1"/></xsl:with-param>
                        <xsl:with-param name="cityTypeSelectValue"><xsl:value-of select="cityTypeSelect1"/></xsl:with-param>
                        <xsl:with-param name="cityFieldValue"><xsl:value-of select="city1"/></xsl:with-param>
                        <xsl:with-param name="localityTypeSelectValue"><xsl:value-of select="localityTypeSelect1"/></xsl:with-param>
                        <xsl:with-param name="localityFieldValue"><xsl:value-of select="locality1"/></xsl:with-param>
                        <xsl:with-param name="streetTypeSelectValue"><xsl:value-of select="streetTypeSelect1"/></xsl:with-param>
                        <xsl:with-param name="streetFieldValue"><xsl:value-of select="street1"/></xsl:with-param>
                        <xsl:with-param name="houseFieldValue"><xsl:value-of select="house1"/></xsl:with-param>
                        <xsl:with-param name="buildingFieldValue"><xsl:value-of select="building1"/></xsl:with-param>
                        <xsl:with-param name="constructionFieldValue"><xsl:value-of select="construction1"/></xsl:with-param>
                        <xsl:with-param name="flatFieldValue"><xsl:value-of select="flat1"/></xsl:with-param>
                        <xsl:with-param name="flatWithNumFieldValue"><xsl:value-of select="flatWithNum1"/></xsl:with-param>
                        <xsl:with-param name="indexFieldValue"><xsl:value-of select="index1"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:variable name="registrationType2"><xsl:value-of select="registrationType2"/></xsl:variable>
                <xsl:if test="$registrationType2 != ''">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="rowValue">
                            <span class="blockTitle">
                                <xsl:choose>
                                    <xsl:when test="$registrationType2 = 'FIXED'">
                                        <xsl:value-of select="'Иформация о постоянной регистрации'"/>
                                    </xsl:when>
                                    <xsl:when test="$registrationType2 = 'TEMPORARY'">
                                        <xsl:value-of select="'Иформация о временной регистрации'"/>
                                    </xsl:when>
                                </xsl:choose>
                            </span>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="addressViewInfo">
                        <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                        <xsl:with-param name="regionSelectFieldValue"><xsl:value-of select="regionSelect2"/></xsl:with-param>
                        <xsl:with-param name="districtTypeSelectValue"><xsl:value-of select="districtTypeSelect2"/></xsl:with-param>
                        <xsl:with-param name="districtFieldValue"><xsl:value-of select="district2"/></xsl:with-param>
                        <xsl:with-param name="cityTypeSelectValue"><xsl:value-of select="cityTypeSelect2"/></xsl:with-param>
                        <xsl:with-param name="cityFieldValue"><xsl:value-of select="city2"/></xsl:with-param>
                        <xsl:with-param name="localityTypeSelectValue"><xsl:value-of select="localityTypeSelect2"/></xsl:with-param>
                        <xsl:with-param name="localityFieldValue"><xsl:value-of select="locality2"/></xsl:with-param>
                        <xsl:with-param name="streetTypeSelectValue"><xsl:value-of select="streetTypeSelect2"/></xsl:with-param>
                        <xsl:with-param name="streetFieldValue"><xsl:value-of select="street2"/></xsl:with-param>
                        <xsl:with-param name="houseFieldValue"><xsl:value-of select="house2"/></xsl:with-param>
                        <xsl:with-param name="buildingFieldValue"><xsl:value-of select="building2"/></xsl:with-param>
                        <xsl:with-param name="constructionFieldValue"><xsl:value-of select="construction2"/></xsl:with-param>
                        <xsl:with-param name="flatFieldValue"><xsl:value-of select="flat2"/></xsl:with-param>
                        <xsl:with-param name="flatWithNumFieldValue"><xsl:value-of select="flatWithNum2"/></xsl:with-param>
                        <xsl:with-param name="indexFieldValue"><xsl:value-of select="index2"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:call-template name="titleRow">
                    <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Адрес фактического проживания</b></xsl:with-param>
                </xsl:call-template>
                <xsl:variable name="registrationType3"><xsl:value-of select="registrationType3"/></xsl:variable>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Адрес проживания</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <span><b>
                            <xsl:choose>
                                <xsl:when test="$registrationType3 = 'FACT'">
                                    <xsl:value-of select="'Другой'"/>
                                </xsl:when>
                                <xsl:when test="$registrationType3 = 'FIXED'">
                                    <xsl:value-of select="'Совпадает с постоянной регистрацией'"/>
                                </xsl:when>
                                <xsl:when test="$registrationType3 = 'TEMPORARY'">
                                    <xsl:value-of select="'Совпадает с временной регистрацией'"/>
                                </xsl:when>
                            </xsl:choose>
                        </b></span>
                    </xsl:with-param>
                    <xsl:with-param name="isAllocate" select="'false'"/>
                </xsl:call-template>

                <xsl:if test="$registrationType3 = 'FACT'">
                    <xsl:call-template name="addressViewInfo">
                        <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                        <xsl:with-param name="regionSelectFieldValue"><xsl:value-of select="regionSelect3"/></xsl:with-param>
                        <xsl:with-param name="districtTypeSelectValue"><xsl:value-of select="districtTypeSelect3"/></xsl:with-param>
                        <xsl:with-param name="districtFieldValue"><xsl:value-of select="district3"/></xsl:with-param>
                        <xsl:with-param name="cityTypeSelectValue"><xsl:value-of select="cityTypeSelect3"/></xsl:with-param>
                        <xsl:with-param name="cityFieldValue"><xsl:value-of select="city3"/></xsl:with-param>
                        <xsl:with-param name="localityTypeSelectValue"><xsl:value-of select="localityTypeSelect3"/></xsl:with-param>
                        <xsl:with-param name="localityFieldValue"><xsl:value-of select="locality3"/></xsl:with-param>
                        <xsl:with-param name="streetTypeSelectValue"><xsl:value-of select="streetTypeSelect3"/></xsl:with-param>
                        <xsl:with-param name="streetFieldValue"><xsl:value-of select="street3"/></xsl:with-param>
                        <xsl:with-param name="houseFieldValue"><xsl:value-of select="house3"/></xsl:with-param>
                        <xsl:with-param name="buildingFieldValue"><xsl:value-of select="building3"/></xsl:with-param>
                        <xsl:with-param name="constructionFieldValue"><xsl:value-of select="construction3"/></xsl:with-param>
                        <xsl:with-param name="flatFieldValue"><xsl:value-of select="flat3"/></xsl:with-param>
                        <xsl:with-param name="flatWithNumFieldValue"><xsl:value-of select="flatWithNum3"/></xsl:with-param>
                        <xsl:with-param name="indexFieldValue"><xsl:value-of select="index3"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:variable name="residenceRight"><xsl:value-of  select="residenceRightSelect"/></xsl:variable>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Право проживания:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="$claimDictionaries/config/list-residence-right/residence-right[code = $residenceRight]/name"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Срок проживания в населенном пункте на момент заполнения анкеты:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="residenceDuration"/>&nbsp; полных лет</b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Срок проживания по фактическому адресу:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="factAddressDuration"/>&nbsp; полных лет</b>
                    </xsl:with-param>
                </xsl:call-template>

            </div>
        </xsl:if>

        <!--Работа и доход-->
        <xsl:if test="stepFilled-INITIAL5='true'">
            <xsl:call-template name="button">
                <xsl:with-param name="style" select="'display: block;'"/>
                <xsl:with-param name="btnText" select="'Работа и доход'"/>
                <xsl:with-param name="onclick" select="'formObjectSavedInfo.hideOrShowBlock(formObjectSavedInfo.workInfoBlock);'"/>
                <xsl:with-param name="typeBtn" select="'blueLink openBlocks'"/>
            </xsl:call-template>
            <div class="clear"></div>

            <div id="workInfo" style="display: none;">
                <xsl:variable name="contractType"><xsl:value-of  select="contractType"/></xsl:variable>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Тип занятости:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="$claimDictionaries/config/list-work-on-contract/work-on-contract[code=$contractType]/name"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:variable name="privatePracticeRequired"><xsl:value-of  select="$claimDictionaries/config/list-work-on-contract/work-on-contract[code=$contractType]/privatePractice"/></xsl:variable>
                <xsl:variable name="pensioner"><xsl:value-of  select="$claimDictionaries/config/list-work-on-contract/work-on-contract[code=$contractType]/pensioner"/></xsl:variable>
                <xsl:choose>
                    <xsl:when test="$privatePracticeRequired = 'true'">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Частная практика:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <b><xsl:value-of  select="privatePractice"/></b>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:when test="$pensioner != 'true' and $privatePracticeRequired != 'true'">

                        <xsl:if test="isSberEmployee ='true'">
                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="rowValue">
                                    <div class="textTick">
                                        <input class="float" type="checkbox" disabled="disabled" checked="checked"/>
                                        <label class="float">&nbsp;Я работаю в Сбербанке России</label>
                                    </div>
                                </xsl:with-param>
                            </xsl:call-template>
                        </xsl:if>

                        <xsl:call-template name="titleRow">
                            <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Текущее место работы</b></xsl:with-param>
                        </xsl:call-template>

                        <xsl:variable name="incorporationType"><xsl:value-of  select="incorporationType"/></xsl:variable>
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Организационно – правовая форма:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <b><xsl:value-of  select="$claimDictionaries/config/list-form-of-incorporation/form-of-incorporation[code=$incorporationType]/name"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Полное наименование компании /организации:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <b><xsl:value-of  select="companyFullName"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:if test="isSberEmployee ='true'">
                            <xsl:variable name="department"><xsl:value-of  select="department"/></xsl:variable>
                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="rowName">Подразделение:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <b><xsl:value-of  select="$departments/entity-list/entity[@key=$department]/field[@name='name']/text()"/></b>
                                </xsl:with-param>
                            </xsl:call-template>

                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="rowName">Полное наименование подразделения:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <b><xsl:value-of  select="departmentFullName"/></b>
                                </xsl:with-param>
                            </xsl:call-template>

                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="rowValue">
                                    <div class="textTick">
                                        <xsl:choose>
                                            <xsl:when test="isTBChairman ='true'">
                                                <input class="float" type="checkbox" disabled="disabled" checked="checked"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <input class="float" type="checkbox" disabled="disabled"/>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                        <label class="float">&nbsp;Я председатель Территориального банка</label>
                                    </div>
                                </xsl:with-param>
                            </xsl:call-template>
                        </xsl:if>

                        <xsl:variable name="companyActivityScope"><xsl:value-of  select="companyActivityScope"/></xsl:variable>
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Сфера деятельности компании:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <b><xsl:value-of  select="$claimDictionaries/config/list-types-of-company/types-of-company[code=$companyActivityScope]/name"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:variable name="orgActivityDescRequired"><xsl:value-of  select="$claimDictionaries/config/list-types-of-company/types-of-company[code=$companyActivityScope]/orgActivityDescRequired"/></xsl:variable>
                        <xsl:if test="$orgActivityDescRequired = 'true'">
                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="rowName">Комментарий:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <b><xsl:value-of  select="employeePosition"/></b>
                                </xsl:with-param>
                            </xsl:call-template>
                        </xsl:if>

                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">ИНН организации работодателя:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <b><xsl:value-of  select="companyInn"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Занимаемая должность:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <b><xsl:value-of  select="employeePosition"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:variable name="employeePositionType"><xsl:value-of  select="employeePositionType"/></xsl:variable>
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Категория занимаемой должности:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <b><xsl:value-of  select="$claimDictionaries/config/list-category-of-position/category-of-position[code=$employeePositionType]/name"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:variable name="seniority"><xsl:value-of  select="seniority"/></xsl:variable>
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Как долго вы работаете в компании:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <b><xsl:value-of  select="$claimDictionaries/config/list-experience-on-current-job/experience-on-current-job[code=$seniority]/name"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:variable name="numberOfEmployees"><xsl:value-of  select="numberOfEmployees"/></xsl:variable>
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Количество сотрудников в компании:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <b><xsl:value-of  select="$claimDictionaries/config/list-number-of-employees/number-of-employees[code=$numberOfEmployees]/name"/></b>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:when>
                </xsl:choose>

                <xsl:call-template name="titleRow">
                    <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Ежемесячный доход и расход</b></xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Среднемесячный основной доход:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="basicIncome"/>&nbsp;руб.</b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Дополнительный доход:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="additionalIncome"/>&nbsp;руб.</b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Среднемесячный доход семьи:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="monthlyIncome"/>&nbsp;руб.</b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Среднемесячный расход:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="monthlyExpense"/>&nbsp;руб.</b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="$pensioner != 'true' and $privatePracticeRequired != 'true'">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Количество рабочих мест за последние 3 года:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of  select="workPlacesCount"/></b>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </div>
        </xsl:if>

        <!--Собственность-->
        <xsl:if test="stepFilled-INITIAL6='true'">
            <xsl:variable name="realtyType1" select="realty_1_realtyType"/>
            <xsl:variable name="transportType1" select="transport_1_transportType"/>
            <xsl:variable name="debtType1" select="debt_1_debtType"/>
            <xsl:if test="$realtyType1 != '' or $transportType1 != '' or $debtType1 != ''">
                <xsl:call-template name="button">
                    <xsl:with-param name="style" select="'display: block;'"/>
                    <xsl:with-param name="btnText" select="'Собственность'"/>
                    <xsl:with-param name="onclick" select="'formObjectSavedInfo.hideOrShowBlock(formObjectSavedInfo.propertyAndDebtsInfoBlock);'"/>
                    <xsl:with-param name="typeBtn" select="'blueLink openBlocks'"/>
                </xsl:call-template>
            </xsl:if>
            <div class="clear"></div>

            <div id="propertyAndDebtsInfo" style="display: none;">
                <xsl:if test="$realtyType1 != ''">
                    <xsl:call-template name="titleRow">
                        <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Недвижимость</b></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="realtyViewInfo">
                        <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                        <xsl:with-param name="realtyType"><xsl:value-of  select="$realtyType1"/></xsl:with-param>
                        <xsl:with-param name="address"><xsl:value-of select="realty_1_address"/></xsl:with-param>
                        <xsl:with-param name="yearOfPurchase"><xsl:value-of select="realty_1_yearOfPurchase"/></xsl:with-param>
                        <xsl:with-param name="square"><xsl:value-of select="realty_1_square"/></xsl:with-param>
                        <xsl:with-param name="typeOfSquareUnit"><xsl:value-of select="realty_1_typeOfSquareUnit"/></xsl:with-param>
                        <xsl:with-param name="approxMarketValue"><xsl:value-of select="realty_1_approxMarketValue"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:variable name="realtyType2" select="realty_2_realtyType"/>
                    <xsl:if test="$realtyType2 != ''">
                        <xsl:call-template name="realtyViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="realtyType"><xsl:value-of  select="$realtyType2"/></xsl:with-param>
                            <xsl:with-param name="address"><xsl:value-of select="realty_2_address"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="realty_2_yearOfPurchase"/></xsl:with-param>
                            <xsl:with-param name="square"><xsl:value-of select="realty_2_square"/></xsl:with-param>
                            <xsl:with-param name="typeOfSquareUnit"><xsl:value-of select="realty_2_typeOfSquareUnit"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="realty_2_approxMarketValue"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="realtyType3" select="realty_3_realtyType"/>
                    <xsl:if test="$realtyType3 != ''">
                        <xsl:call-template name="realtyViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="realtyType"><xsl:value-of  select="$realtyType3"/></xsl:with-param>
                            <xsl:with-param name="address"><xsl:value-of select="realty_3_address"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="realty_3_yearOfPurchase"/></xsl:with-param>
                            <xsl:with-param name="square"><xsl:value-of select="realty_3_square"/></xsl:with-param>
                            <xsl:with-param name="typeOfSquareUnit"><xsl:value-of select="realty_3_typeOfSquareUnit"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="realty_3_approxMarketValue"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="realtyType4" select="realty_4_realtyType"/>
                    <xsl:if test="$realtyType4 != ''">
                        <xsl:call-template name="realtyViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="realtyType"><xsl:value-of  select="$realtyType4"/></xsl:with-param>
                            <xsl:with-param name="address"><xsl:value-of select="realty_4_address"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="realty_4_yearOfPurchase"/></xsl:with-param>
                            <xsl:with-param name="square"><xsl:value-of select="realty_4_square"/></xsl:with-param>
                            <xsl:with-param name="typeOfSquareUnit"><xsl:value-of select="realty_4_typeOfSquareUnit"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="realty_4_approxMarketValue"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="realtyType5" select="realty_5_realtyType"/>
                    <xsl:if test="$realtyType5 != ''">
                        <xsl:call-template name="realtyViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="realtyType"><xsl:value-of  select="$realtyType5"/></xsl:with-param>
                            <xsl:with-param name="address"><xsl:value-of select="realty_5_address"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="realty_5_yearOfPurchase"/></xsl:with-param>
                            <xsl:with-param name="square"><xsl:value-of select="realty_5_square"/></xsl:with-param>
                            <xsl:with-param name="typeOfSquareUnit"><xsl:value-of select="realty_5_typeOfSquareUnit"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="realty_5_approxMarketValue"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="realtyType6" select="realty_6_realtyType"/>
                    <xsl:if test="$realtyType6 != ''">
                        <xsl:call-template name="realtyViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="realtyType"><xsl:value-of  select="$realtyType6"/></xsl:with-param>
                            <xsl:with-param name="address"><xsl:value-of select="realty_6_address"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="realty_6_yearOfPurchase"/></xsl:with-param>
                            <xsl:with-param name="square"><xsl:value-of select="realty_6_square"/></xsl:with-param>
                            <xsl:with-param name="typeOfSquareUnit"><xsl:value-of select="realty_6_typeOfSquareUnit"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="realty_6_approxMarketValue"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="realtyType7" select="realty_7_realtyType"/>
                    <xsl:if test="$realtyType7 != ''">
                        <xsl:call-template name="realtyViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="realtyType"><xsl:value-of  select="$realtyType7"/></xsl:with-param>
                            <xsl:with-param name="address"><xsl:value-of select="realty_7_address"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="realty_7_yearOfPurchase"/></xsl:with-param>
                            <xsl:with-param name="square"><xsl:value-of select="realty_7_square"/></xsl:with-param>
                            <xsl:with-param name="typeOfSquareUnit"><xsl:value-of select="realty_7_typeOfSquareUnit"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="realty_7_approxMarketValue"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:if>

                <xsl:if test="$transportType1 != ''">
                    <xsl:call-template name="titleRow">
                        <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Транспортное средство</b></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="transportViewInfo">
                        <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                        <xsl:with-param name="transportType"><xsl:value-of  select="$transportType1"/></xsl:with-param>
                        <xsl:with-param name="registrationNumber"><xsl:value-of select="transport_1_registrationNumber"/></xsl:with-param>
                        <xsl:with-param name="brand"><xsl:value-of select="transport_1_brand"/></xsl:with-param>
                        <xsl:with-param name="approxMarketValue"><xsl:value-of select="transport_1_approxMarketValue"/></xsl:with-param>
                        <xsl:with-param name="ageOfTransport"><xsl:value-of select="transport_1_ageOfTransport"/></xsl:with-param>
                        <xsl:with-param name="yearOfPurchase"><xsl:value-of select="transport_1_yearOfPurchase"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:variable name="transportType2" select="transport_2_transportType"/>
                    <xsl:if test="$transportType2 != ''">
                        <xsl:call-template name="transportViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="transportType"><xsl:value-of  select="$transportType2"/></xsl:with-param>
                            <xsl:with-param name="registrationNumber"><xsl:value-of select="transport_2_registrationNumber"/></xsl:with-param>
                            <xsl:with-param name="brand"><xsl:value-of select="transport_2_brand"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="transport_2_approxMarketValue"/></xsl:with-param>
                            <xsl:with-param name="ageOfTransport"><xsl:value-of select="transport_2_ageOfTransport"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="transport_2_yearOfPurchase"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="transportType3" select="transport_3_transportType"/>
                    <xsl:if test="$transportType3 != ''">
                        <xsl:call-template name="transportViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="transportType"><xsl:value-of  select="$transportType3"/></xsl:with-param>
                            <xsl:with-param name="registrationNumber"><xsl:value-of select="transport_3_registrationNumber"/></xsl:with-param>
                            <xsl:with-param name="brand"><xsl:value-of select="transport_3_brand"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="transport_3_approxMarketValue"/></xsl:with-param>
                            <xsl:with-param name="ageOfTransport"><xsl:value-of select="transport_3_ageOfTransport"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="transport_3_yearOfPurchase"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="transportType4" select="transport_4_transportType"/>
                    <xsl:if test="$transportType4 != ''">
                        <xsl:call-template name="transportViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="transportType"><xsl:value-of  select="$transportType4"/></xsl:with-param>
                            <xsl:with-param name="registrationNumber"><xsl:value-of select="transport_4_registrationNumber"/></xsl:with-param>
                            <xsl:with-param name="brand"><xsl:value-of select="transport_4_brand"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="transport_4_approxMarketValue"/></xsl:with-param>
                            <xsl:with-param name="ageOfTransport"><xsl:value-of select="transport_4_ageOfTransport"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="transport_4_yearOfPurchase"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="transportType5" select="transport_5_transportType"/>
                    <xsl:if test="$transportType5 != ''">
                        <xsl:call-template name="transportViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="transportType"><xsl:value-of  select="$transportType5"/></xsl:with-param>
                            <xsl:with-param name="registrationNumber"><xsl:value-of select="transport_5_registrationNumber"/></xsl:with-param>
                            <xsl:with-param name="brand"><xsl:value-of select="transport_5_brand"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="transport_5_approxMarketValue"/></xsl:with-param>
                            <xsl:with-param name="ageOfTransport"><xsl:value-of select="transport_5_ageOfTransport"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="transport_5_yearOfPurchase"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="transportType6" select="transport_6_transportType"/>
                    <xsl:if test="$transportType6 != ''">
                        <xsl:call-template name="transportViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="transportType"><xsl:value-of  select="$transportType6"/></xsl:with-param>
                            <xsl:with-param name="registrationNumber"><xsl:value-of select="transport_6_registrationNumber"/></xsl:with-param>
                            <xsl:with-param name="brand"><xsl:value-of select="transport_6_brand"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="transport_6_approxMarketValue"/></xsl:with-param>
                            <xsl:with-param name="ageOfTransport"><xsl:value-of select="transport_6_ageOfTransport"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="transport_6_yearOfPurchase"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="transportType7" select="transport_7_transportType"/>
                    <xsl:if test="$transportType7 != ''">
                        <xsl:call-template name="transportViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="transportType"><xsl:value-of  select="$transportType7"/></xsl:with-param>
                            <xsl:with-param name="registrationNumber"><xsl:value-of select="transport_7_registrationNumber"/></xsl:with-param>
                            <xsl:with-param name="brand"><xsl:value-of select="transport_7_brand"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="transport_7_approxMarketValue"/></xsl:with-param>
                            <xsl:with-param name="ageOfTransport"><xsl:value-of select="transport_7_ageOfTransport"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="transport_7_yearOfPurchase"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:if>
            </div>
        </xsl:if>

        <!--Дополнительная информация-->
        <xsl:if test="stepFilled-INITIAL7='true'">
            <xsl:call-template name="button">
                <xsl:with-param name="style" select="'display: block;'"/>
                <xsl:with-param name="btnText" select="'Доп. информация'"/>
                <xsl:with-param name="onclick" select="'formObjectSavedInfo.hideOrShowBlock(formObjectSavedInfo.additionalInfoBlock);'"/>
                <xsl:with-param name="typeBtn" select="'blueLink openBlocks'"/>
            </xsl:call-template>
            <div class="clear"></div>

            <div id="additionalInfo" style="display: none;">

                <xsl:variable name="creditMethodObtaining"><xsl:value-of select="creditMethodObtaining"/></xsl:variable>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Как вы хотите получить кредит:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="$claimDictionaries/config/list-loan-issue-method/creditMethodObtaining[code = $creditMethodObtaining]/name"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:variable name="newProductForLoan">
                    <xsl:value-of select="$claimDictionaries/config/list-loan-issue-method/creditMethodObtaining[code = $creditMethodObtaining]/newProductForLoan"/>
                </xsl:variable>
                <xsl:if test="$newProductForLoan != 'true'">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Карта\счет для зачисления кредита:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:choose>
                                <xsl:when test="receivingResource != ''">
                                    <b><xsl:value-of select="receivingResource"/></b>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:if test="accountNumber != ''">
                                        <b>Счет №<xsl:value-of select="accountNumber"/></b>
                                    </xsl:if>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName"  select="'Подразделение, в котором хотите получить кредит:'"/>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="receivingDepartmentName"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:variable name="cardBlankType_1" select="cardBlank_1_type"/>
                <xsl:variable name="cardBlankType_2" select="cardBlank_2_type"/>
                <xsl:variable name="cardBlankType_3" select="cardBlank_3_type"/>
                <xsl:variable name="accountBlankType_1" select="accountBlank_1_type"/>
                <xsl:variable name="accountBlankType_2" select="accountBlank_2_type"/>
                <xsl:variable name="accountBlankType_3" select="accountBlank_3_type"/>

                <xsl:if test="$accountBlankType_1 != '' or $accountBlankType_2 != '' or $accountBlankType_3 != ''
                              or $cardBlankType_1 != '' or $cardBlankType_2 != '' or $cardBlankType_3 != ''">
                    <xsl:call-template name="titleRow">
                        <xsl:with-param name="rowName">&nbsp;<b class="rowTitle18 size20">Зарплатная/пенсионная карта или счет в Сбербанке</b></xsl:with-param>
                    </xsl:call-template>

                    <xsl:if test="$cardBlankType_1 != ''">
                        <xsl:call-template name="additionalCardViewInfo">
                            <xsl:with-param name="cardBlankType"><xsl:value-of select="$cardBlankType_1"/></xsl:with-param>
                            <xsl:with-param name="cardBlankResource"><xsl:value-of select="cardBlank_1_resource"/></xsl:with-param>
                            <xsl:with-param name="cardBlankCustomNumber"><xsl:value-of select="cardBlank_1_customNumber"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:if test="$cardBlankType_2 != ''">
                        <xsl:call-template name="additionalCardViewInfo">
                            <xsl:with-param name="cardBlankType"><xsl:value-of select="$cardBlankType_2"/></xsl:with-param>
                            <xsl:with-param name="cardBlankResource"><xsl:value-of select="cardBlank_2_resource"/></xsl:with-param>
                            <xsl:with-param name="cardBlankCustomNumber"><xsl:value-of select="cardBlank_2_customNumber"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:if test="$cardBlankType_3 != ''">
                        <xsl:call-template name="additionalCardViewInfo">
                            <xsl:with-param name="cardBlankType"><xsl:value-of select="$cardBlankType_3"/></xsl:with-param>
                            <xsl:with-param name="cardBlankResource"><xsl:value-of select="cardBlank_3_resource"/></xsl:with-param>
                            <xsl:with-param name="cardBlankCustomNumber"><xsl:value-of select="cardBlank_3_customNumber"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:if test="$accountBlankType_1 != ''">
                        <xsl:call-template name="additionalAccountViewInfo">
                            <xsl:with-param name="accountBlankType"><xsl:value-of select="$accountBlankType_1"/></xsl:with-param>
                            <xsl:with-param name="accountBlankResource"><xsl:value-of select="accountBlank_1_resource"/></xsl:with-param>
                            <xsl:with-param name="accountBlankCustomNumber"><xsl:value-of select="accountBlank_1_customNumber"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:if test="$accountBlankType_2 != ''">
                        <xsl:call-template name="additionalAccountViewInfo">
                            <xsl:with-param name="accountBlankType"><xsl:value-of select="$accountBlankType_2"/></xsl:with-param>
                            <xsl:with-param name="accountBlankResource"><xsl:value-of select="accountBlank_2_resource"/></xsl:with-param>
                            <xsl:with-param name="accountBlankCustomNumber"><xsl:value-of select="accountBlank_2_customNumber"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:if test="$accountBlankType_3 != ''">
                        <xsl:call-template name="additionalAccountViewInfo">
                            <xsl:with-param name="accountBlankType"><xsl:value-of select="$accountBlankType_3"/></xsl:with-param>
                            <xsl:with-param name="accountBlankResource"><xsl:value-of select="accountBlank_3_resource"/></xsl:with-param>
                            <xsl:with-param name="accountBlankCustomNumber"><xsl:value-of select="accountBlank_3_customNumber"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                </xsl:if>

                <div class="darkGrayUnderline"></div>

                <xsl:variable name="stockholder"><xsl:value-of select="stockholder"/></xsl:variable>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">&nbsp;</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <div class="textTick">
                            <xsl:choose>
                                <xsl:when test="$stockholder ='true'">
                                    <input class="float" id="stockholder" type="checkbox" checked="checked" disabled="disabled"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <input class="float" id="stockholder" type="checkbox" disabled="disabled"/>
                                </xsl:otherwise>
                            </xsl:choose>
                            <label class="float" for="stockholder">&nbsp;Я акционер Сбербанка России</label>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="$stockholder = 'true'">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Количество обыкновенных акций Сбербанка России</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of select="ordinaryStockCount"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Количество привилигированных акций Сбербанка России</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of select="preferredStockCount"/></b>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:if test="($isGuest and gh:hasPhoneInMB() = true()) or not($isGuest)">
                    <xsl:variable name="agreeRequestBKI"><xsl:value-of select="agreeRequestBKI"/></xsl:variable>
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">&nbsp;</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <div class="textTick">
                                <xsl:choose>
                                    <xsl:when test="$agreeRequestBKI ='true'">
                                        <input class="float" type="checkbox" checked="checked" disabled="disabled"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <input class="float" type="checkbox" disabled="disabled"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                                <label class="float">&nbsp;Я согласен на запрос информации банком из БКИ</label>
                            </div>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:if test="subjectCreditHistoryCode != ''">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Ваш код субъекта кредитной истории:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of select="subjectCreditHistoryCode"/></b>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:variable name="agreeRequestPFP"><xsl:value-of select="agreeRequestPFP"/></xsl:variable>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">&nbsp;</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <div class="textTick">
                            <xsl:choose>
                                <xsl:when test="$agreeRequestPFP ='true'">
                                    <input class="float" type="checkbox" checked="checked" disabled="disabled"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <input class="float" type="checkbox" disabled="disabled"/>
                                </xsl:otherwise>
                            </xsl:choose>
                            <label class="float">&nbsp;Я согласен на запрос информации банком из ПФР</label>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="$agreeRequestPFP ='true'">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Ваш СНИЛС:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of select="snils"/></b>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

            </div>
        </xsl:if>

        <!-- Анкета -->
        <xsl:if test="stepFilled-INITIAL8='true'">
            <xsl:variable name="questionCount" select="question_count"/>
            <xsl:if test="$questionCount != ''">
                <xsl:call-template name="button">
                    <xsl:with-param name="style" select="'display: block;'"/>
                    <xsl:with-param name="btnText" select="'Анкета'"/>
                    <xsl:with-param name="onclick" select="'formObjectSavedInfo.hideOrShowBlock(formObjectSavedInfo.questionnaireBlock);'"/>
                    <xsl:with-param name="typeBtn" select="'blueLink openBlocks'"/>
                </xsl:call-template>
                <div class="clear"></div>

                <div id="questionnaire" style="display: none;">
                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_1_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_1_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_1_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_2_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_2_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_2_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_3_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_3_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_3_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_4_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_4_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_4_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_5_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_5_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_5_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_6_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_6_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_6_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_7_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_7_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_7_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_8_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_8_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_8_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_9_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_9_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_9_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_10_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_10_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_10_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_11_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_11_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_11_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_12_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_12_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_12_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_13_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_13_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_13_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_14_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_14_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_14_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_15_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_15_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_15_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_16_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_16_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_16_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_17_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_17_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_17_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_18_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_18_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_18_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_19_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_19_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_19_answer"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="questionViewInfo">
                        <xsl:with-param name="questionText"><xsl:value-of select="question_20_text"/></xsl:with-param>
                        <xsl:with-param name="answerType"><xsl:value-of select="question_20_answer_type"/></xsl:with-param>
                        <xsl:with-param name="answer"><xsl:value-of select="question_20_answer"/></xsl:with-param>
                    </xsl:call-template>
                </div>
            </xsl:if>
        </xsl:if>


        <xsl:call-template name="state">
            <xsl:with-param name="stateCode" select="state"/>
            <xsl:with-param name="refuseReason" select="refuseReason"/>
        </xsl:call-template>

    </xsl:template>

    <xsl:template name="state">
   		<xsl:param name="stateCode"/>
   		<xsl:param name="refuseReason"/>
        <xsl:if test="$stateCode!='INITIAL' or $app = 'PhizIA'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName"  select="'Статус заявки:'"/>
                <xsl:with-param name="required" select="false()"/>
                <xsl:with-param name="rowValue">
                    <div class="stateWidth">
                        <div id="divState" style="width: 220px;">
                            <a class="blueGrayLinkDotted">
                            <span onmouseover="showLayer('divState','stateDescription','hand');" onmouseout="hideLayer('stateDescription');">
                                <xsl:choose>
                                    <xsl:when test="$app = 'PhizIA'">
                                        <xsl:choose>
                                            <xsl:when test="$stateCode='INITIAL'">Ввод данных СБОЛ</xsl:when>
                                            <xsl:when test="$stateCode='INITIAL2'">Ввод данных СБОЛ</xsl:when>
                                            <xsl:when test="$stateCode='INITIAL3'">Ввод данных СБОЛ</xsl:when>
                                            <xsl:when test="$stateCode='INITIAL4'">Ввод данных СБОЛ</xsl:when>
                                            <xsl:when test="$stateCode='INITIAL5'">Ввод данных СБОЛ</xsl:when>
                                            <xsl:when test="$stateCode='INITIAL6'">Ввод данных СБОЛ</xsl:when>
                                            <xsl:when test="$stateCode='INITIAL7'">Ввод данных СБОЛ</xsl:when>
                                            <xsl:when test="$stateCode='INITIAL8'">Ввод данных СБОЛ</xsl:when>
                                            <xsl:when test="$stateCode='SAVED'">Ввод данных СБОЛ</xsl:when>
                                            <xsl:when test="$stateCode='DISPATCHED'">Обрабатывается</xsl:when>
                                            <xsl:when test="$stateCode='APPROVED'">Одобрено</xsl:when>
                                            <xsl:when test="$stateCode='ISSUED'">Кредит выдан</xsl:when>
                                            <xsl:when test="$stateCode='PREADOPTED'">Принято предварительное решение</xsl:when>
                                            <xsl:when test="$stateCode='REFUSED'">Отказано</xsl:when>
                                            <xsl:when test="$stateCode='WAIT_TM'">В работе ТМ</xsl:when>
                                            <xsl:when test="$stateCode='VISIT_OFFICE'">Передана в ВСП</xsl:when>
                                        </xsl:choose>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:choose>
                                            <xsl:when test="$isGuest">
                                                <xsl:choose>
                                                    <xsl:when test="$stateCode='DISPATCHED'">Обрабатывается банком</xsl:when>
                                                    <xsl:when test="$stateCode='APPROVED'">Кредит одобрен</xsl:when>
                                                    <xsl:when test="$stateCode='ISSUED'">Кредит выдан</xsl:when>
                                                    <xsl:when test="$stateCode='PREADOPTED'">Принято предварительное решение</xsl:when>
                                                    <xsl:when test="$stateCode='REFUSED'">Заявка отклонена банком</xsl:when>
                                                    <xsl:when test="$stateCode='WAIT_TM'">Ожидайте звонка сотрудника банка</xsl:when>
                                                    <xsl:when test="$stateCode='VISIT_OFFICE'">Требуется визит в отделение банка</xsl:when>
                                                    <xsl:when test="$inWaitTM='true'">Ожидайте звонка сотрудника банка</xsl:when>
                                                    <xsl:otherwise>Не отправлено</xsl:otherwise>
                                                </xsl:choose>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:choose>
                                                    <xsl:when test="$stateCode='DISPATCHED'">Обрабатывается банком</xsl:when>
                                                    <xsl:when test="$stateCode='APPROVED'">Кредит одобрен</xsl:when>
                                                    <xsl:when test="$stateCode='APPROVED_MUST_CONFIRM'">Одобрено, требуется подтверждение для выдачи</xsl:when>
                                                    <xsl:when test="$stateCode='ISSUED'">Кредит выдан</xsl:when>
                                                    <xsl:when test="$stateCode='PREADOPTED'">Принято предварительное решение</xsl:when>
                                                    <xsl:when test="$stateCode='REFUSED'">Заявка отклонена банком</xsl:when>
                                                    <xsl:when test="$stateCode='WAIT_TM'">Ожидайте звонка сотрудника банка</xsl:when>
                                                    <xsl:when test="$stateCode='VISIT_OFFICE'">Требуется визит в отделение банка</xsl:when>
                                                    <xsl:when test="$inWaitTM='true'">Ожидайте звонка сотрудника банка</xsl:when>
                                                    <xsl:otherwise>Черновик</xsl:otherwise>
                                                </xsl:choose>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </span>
                            </a>
                        </div>
                    </div>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
   	</xsl:template>

    <!--Выпадающий список со значениями справочника "родственные связи"-->
    <xsl:template name="familyRelation">
        <xsl:param name="relation"/>
        <xsl:param name="isChild"/>
        <xsl:param name="baseFieldId"/>
        <span id="{$baseFieldId}" style="display:none">
            <select id="familyRelation" name="familyRelation">
                <xsl:for-each select="$relation">
                    <xsl:choose>
                        <xsl:when test="$isChild = ''">
                            <option>
                                <xsl:variable name="id" select="./code"/>
                                <xsl:attribute name="value">
                                    <xsl:value-of select="$id"/>
                                </xsl:attribute>
                                <xsl:value-of select="./name"/>
                            </option>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:if test="./children = $isChild">
                                <option>
                                    <xsl:variable name="id" select="./code"/>
                                    <xsl:attribute name="value">
                                        <xsl:value-of select="$id"/>
                                    </xsl:attribute>
                                    <xsl:value-of select="./name"/>
                                </option>
                            </xsl:if>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:for-each>
            </select>
        </span>
    </xsl:template>

    <!--Выпадающий список со значениями справочника "Подразделения"-->
    <xsl:template name="departments">
        <xsl:param name="elementsFromDictionary"/>
        <xsl:param name="selectedElement"/>
        <xsl:param name="divId"/>
        <xsl:param name="selectId"/>
        <xsl:param name="style"/>
        <xsl:param name="selectStyle"/>
        <xsl:param name="onchange"/>
        <span id="{$divId}" style="{$style}">
            <select id="{$selectId}" name="{$selectId}" onchange="{$onchange}" style="{$selectStyle}">
                <xsl:if test="$selectedElement= ''">
                    <option value=""/>
                </xsl:if>
                <xsl:for-each select="$elementsFromDictionary">
                    <option>
                        <xsl:variable name="id" select="./@key"/>
                        <xsl:if test="$selectedElement = $id">
                            <xsl:attribute name="selected">true</xsl:attribute>
                        </xsl:if>
                        <xsl:attribute name="value">
                            <xsl:value-of select="$id"/>
                        </xsl:attribute>
                        <xsl:choose>
                            <xsl:when test="./field[@name='name'] = 'Оперу Московского банка'">
                                <xsl:value-of select="'ЦА или Оперу Московского банка'"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="./field[@name='name']"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </option>
                </xsl:for-each>
             </select>
         </span>
    </xsl:template>

    <!--listBox со значениями из справочника. вызывать только для тех справочников, в которых у элементов есть два тега: code и name-->
    <xsl:template name="selectFromDictionary">
         <xsl:param name="elementsFromDictionary"/>
         <xsl:param name="selectedElement"/>
         <xsl:param name="chooseFirstElementAsDefault"/>
         <xsl:param name="spanId"/>
         <xsl:param name="selectId"/>
         <xsl:param name="style"/>
         <xsl:param name="selectStyle"/>
         <xsl:param name="onchange"/>
         <xsl:param name="oninit"  select="''"/>

         <xsl:param name="hasEmptyElement"/>
         <span id="{$spanId}" style="{$style}">
             <select id="{$selectId}" name="{$selectId}" onchange="{$onchange}" style="{$selectStyle}">
                 <xsl:if test="($selectedElement= '' and $chooseFirstElementAsDefault = false()) or $hasEmptyElement=true()">
                      <option value="">
                          <xsl:attribute name="style">
                              <xsl:choose>
                                  <xsl:when test="$hasEmptyElement=true()">
                                    height: 14px;
                                  </xsl:when>
                                  <xsl:otherwise>
                                    display: none;
                                  </xsl:otherwise>
                              </xsl:choose>
                          </xsl:attribute>
                      </option>
                  </xsl:if>
                 <xsl:for-each select="$elementsFromDictionary">
                     <option>
                         <xsl:variable name="id" select="./code"/>
                         <xsl:if test="$selectedElement = $id">
                             <xsl:attribute name="selected">true</xsl:attribute>
                         </xsl:if>
                         <xsl:attribute name="value">
                             <xsl:value-of select="$id"/>
                         </xsl:attribute>
                         <xsl:value-of select="./name"/>
                     </option>
                 </xsl:for-each>
             </select>
         </span>
         <xsl:if test="not($oninit = '')">
             <script type="text/javascript">
                $(document).ready(function() {
                    <xsl:if test="not($selectedElement = '')">
                       <xsl:value-of select="$oninit"/>(<xsl:value-of select="$selectedElement"/>);
                    </xsl:if>
                 });
             </script>
         </xsl:if>
     </xsl:template>

    <xsl:template name="resources">
        <xsl:param name="name"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="linkId" select="$name"/>
        <xsl:param name="activeAccounts"/>
        <xsl:param name="activeDebitMainCards"/>

        <xsl:param name="needAccountEmptyOption"            select="false()"/>
        <xsl:param name="needCardEmptyOption"               select="false()"/>
        <xsl:param name="useDefaultHideOrShow"              select="true()"/>
        <xsl:param name="defaultHideOrShowAccountNumberRow" select="'FormObject.hideOrShowAccountNumberRow(this.value);'"/>
        <xsl:param name="customHideOrShowAccountNumberRow"  select="''"/>

        <select id="{$linkId}" name="{$name}" style="width:400px">
            <xsl:attribute name="onchange">
                <xsl:choose>
                    <xsl:when test="$useDefaultHideOrShow = true()">
                        <xsl:value-of select="$defaultHideOrShowAccountNumberRow"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$customHideOrShowAccountNumberRow"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>

            <xsl:choose>
                <xsl:when test="$needAccountEmptyOption and $needCardEmptyOption">
                    <option value="">
                        <xsl:value-of select="'Укажите счет/карту вручную'"/>
                    </option>
                </xsl:when>
                <xsl:when test="$needAccountEmptyOption">
                    <option value="">
                        <xsl:value-of select="'Укажите счет вручную'"/>
                    </option>
                </xsl:when>
                <xsl:when test="$needCardEmptyOption">
                    <option value="">
                        <xsl:value-of select="'Укажите карту вручную'"/>
                    </option>
                </xsl:when>
            </xsl:choose>

            <xsl:if test="$activeAccounts != ''">
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
                         <xsl:value-of select="mu:getCurrencySign(./field[@name='currencyCode'])"/>
                     </option>
                </xsl:for-each>
            </xsl:if>

            <xsl:if test="$activeDebitMainCards != ''">
                <xsl:for-each select="$activeDebitMainCards">
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    <option>
                        <xsl:attribute name="value">
                            <xsl:value-of select="$id"/>
                        </xsl:attribute>
                        <xsl:if test="$accountNumber= ./@key or $linkId=$id">
                            <xsl:attribute name="selected"/>
                        </xsl:if>
                        <xsl:value-of select="mask:getCutCardNumber(./@key)"/>&nbsp;
                        [<xsl:value-of select="./field[@name='name']"/>]
                        <xsl:if test="./field[@name='amountDecimal'] != ''">
                            <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                        </xsl:if>
                        <xsl:value-of select="mu:getCurrencySign(./field[@name='currencyCode'])"/>
                    </option>
                </xsl:for-each>
            </xsl:if>
        </select>
    </xsl:template>

    <xsl:template name="addressInfo">
        <xsl:param name="dictionaries"/>
        <xsl:param name="blockIndex"/>
        <xsl:param name="registrationTypeValue"/>
        <xsl:param name="regionSelectFieldValue"/>
        <xsl:param name="districtTypeSelectValue"/>
        <xsl:param name="districtFieldValue"/>
        <xsl:param name="cityCodeFieldValue"/>
        <xsl:param name="districtCodeFieldValue"/>
        <xsl:param name="localityCodeFieldValue"/>
        <xsl:param name="cityTypeSelectValue"/>
        <xsl:param name="cityFieldValue"/>
        <xsl:param name="localityTypeSelectValue"/>
        <xsl:param name="localityFieldValue"/>
        <xsl:param name="streetTypeSelectValue"/>
        <xsl:param name="streetFieldValue"/>
        <xsl:param name="houseFieldValue"/>
        <xsl:param name="buildingFieldValue"/>
        <xsl:param name="constructionFieldValue"/>
        <xsl:param name="flatFieldValue"/>
        <xsl:param name="flatWithNumFieldValue"/>
        <xsl:param name="indexFieldValue"/>
        <xsl:param name="webRoot" select="''"/>

        <xsl:variable name="districtTypes" select="$dictionaries/config/list-types-of-regions/*"/>
        <xsl:variable name="regions" select="$dictionaries/config/list-regions/*"/>
        <xsl:variable name="cityTypes" select="$dictionaries/config/list-types-of-cities/*"/>
        <xsl:variable name="streetTypes" select="$dictionaries/config/list-types-of-streets/*"/>
        <xsl:variable name="localityTypes" select="$dictionaries/config/list-types-of-localities/*"/>

        <xsl:variable name="liveSearchClientInfoUrl" select="concat($webRoot, '/private/async/search/clientAddress.do')"/>
        <xsl:variable name="areaLiveSearchVarName"   select="concat('areaLiveSearch',     $blockIndex)"/>
        <xsl:variable name="cityLiveSearchVarName"   select="concat('cityLiveSearch',     $blockIndex)"/>
        <xsl:variable name="locLiveSearchVarName"    select="concat('localityLiveSearch', $blockIndex)"/>
        <xsl:variable name="streetLiveSearchVarName" select="concat('streetLiveSearch',   $blockIndex)"/>

        <input type="hidden" name="{concat('registrationType', $blockIndex)}" id="{concat('registrationType', $blockIndex)}" value="{$registrationTypeValue}"/>
        <xsl:variable name="variableName" select="concat('regionSelect', $blockIndex)"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId"  select="$variableName"/>
            <xsl:with-param name="rowName">Регион</xsl:with-param>
            <xsl:with-param name="rowValue">

                <script type="text/javascript">
                    var <xsl:value-of select="$variableName"/> = new LiveSearchData();

                    <xsl:for-each select="$regions">
                        <xsl:variable name="code" select="./code"/>

                        <xsl:value-of select="$variableName"/>.appendData('<xsl:value-of select="$code"/>', '<xsl:value-of select="./name"/>', <xsl:value-of select="$regionSelectFieldValue = $code"/>);
                    </xsl:for-each>
                </script>
                <div style="width: 375px; height: 26px">
                    <xsl:call-template name="liveSearchTemplate">
                        <xsl:with-param name="boundToData"          select="$variableName"/>
                        <xsl:with-param name="boundToElement"       select="concat('', $variableName)"/>
                        <xsl:with-param name="postedValueInputName" select="concat('', $variableName)"/>
                        <xsl:with-param name="onSelect"             select="'onSelectRegion'"/>
                        <xsl:with-param name="changedElements"      select="concat('', $areaLiveSearchVarName, ',', $cityLiveSearchVarName, ',', $locLiveSearchVarName, ',', $streetLiveSearchVarName)"/>
                    </xsl:call-template>
                </div>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId"   select="concat('areaElement', $blockIndex)"/>
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Район/Округ</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div style="width: 375px; height: 26px">
                    <xsl:call-template name="liveSearchInput">
                        <xsl:with-param name="pageType"           select="'AREA'"/>
                        <xsl:with-param name="boundToElement"     select="concat('areaElement', $blockIndex)"/>
                        <xsl:with-param name="inputName"          select="concat('district',    $blockIndex)"/>
                        <xsl:with-param name="varName"            select="$areaLiveSearchVarName"/>
                        <xsl:with-param name="url"                select="$liveSearchClientInfoUrl"/>
                        <xsl:with-param name="initialValue"       select="$districtFieldValue"/>
                        <xsl:with-param name="z-index"            select="'8'"/>
                        <xsl:with-param name="onSelect"           select="'onSelectArea'"/>
                        <xsl:with-param name="onClear"            select="'clearArea'"/>
                        <xsl:with-param name="onKeyDown"          select="'keyDownArea'"/>
                        <xsl:with-param name="mutableJSObjects"   select="concat($cityLiveSearchVarName, ',', $locLiveSearchVarName, ',', $streetLiveSearchVarName)"/>
                        <xsl:with-param name="blockIndex"         select="$blockIndex"/>
                        <xsl:with-param name="requiredParameters" select="'&quot;field(region)&quot;'"/>
                        <xsl:with-param name="length"             select="50"/>
                    </xsl:call-template>

                    <input id="{concat('districtCode', $blockIndex)}" name="{concat('districtCode', $blockIndex)}" type="hidden" value="{$districtCodeFieldValue}"/>
                </div>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Тип Района/Округа</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:call-template name="selectFromDictionary">
                    <xsl:with-param name="selectId"               select="concat('districtTypeSelect', $blockIndex)"/>
                    <xsl:with-param name="elementsFromDictionary" select="$districtTypes"/>
                    <xsl:with-param name="selectedElement"        select="$districtTypeSelectValue"/>
                    <xsl:with-param name="selectStyle"            select="'width: 110px;'"/>
                    <xsl:with-param name="hasEmptyElement"        select="'true'"/>

                    <xsl:with-param name="oninit"                 select="concat('changeDistrictTypeSelect', $blockIndex)"/>
                    <xsl:with-param name="onchange"               select="concat('changeDistrictTypeSelect', $blockIndex, '(this.value)')"/>
                </xsl:call-template>

                <script type="text/javascript">
                    function changeDistrictTypeSelect<xsl:value-of select="$blockIndex"/>(value)
                    {
                        <xsl:value-of select="$areaLiveSearchVarName"/>.addParameter('field(typeofarea)', value);
                    }
                </script>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId"   select="concat('cityElement', $blockIndex)"/>
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Город</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div style="width: 375px; height: 26px">
                    <xsl:call-template name="liveSearchInput">
                        <xsl:with-param name="pageType"           select="'CITY'"/>
                        <xsl:with-param name="boundToElement"     select="concat('cityElement', $blockIndex)"/>
                        <xsl:with-param name="inputName"          select="concat('city',        $blockIndex)"/>
                        <xsl:with-param name="varName"            select="$cityLiveSearchVarName"/>
                        <xsl:with-param name="url"                select="$liveSearchClientInfoUrl"/>
                        <xsl:with-param name="initialValue"       select="$cityFieldValue"/>
                        <xsl:with-param name="z-index"            select="'7'"/>
                        <xsl:with-param name="onSelect"           select="'onSelectCity'"/>
                        <xsl:with-param name="onClear"            select="'clearCity'"/>
                        <xsl:with-param name="onKeyDown"          select="'keyDownCity'"/>
                        <xsl:with-param name="mutableJSObjects"   select="concat('', $locLiveSearchVarName, ',', $streetLiveSearchVarName)"/>
                        <xsl:with-param name="blockIndex"         select="$blockIndex"/>
                        <xsl:with-param name="requiredParameters" select="'&quot;field(region)&quot;'"/>
                        <xsl:with-param name="length"             select="50"/>
                    </xsl:call-template>

                    <input id="{concat('cityCode', $blockIndex)}" name="{concat('cityCode', $blockIndex)}" type="hidden" value="{$cityCodeFieldValue}"/>
                </div>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Тип города</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:call-template name="selectFromDictionary">
                    <xsl:with-param name="selectId"               select="concat('cityTypeSelect', $blockIndex)"/>
                    <xsl:with-param name="elementsFromDictionary" select="$cityTypes"/>
                    <xsl:with-param name="selectedElement"        select="$cityTypeSelectValue"/>
                    <xsl:with-param name="selectStyle"            select="'width: 110px;'"/>
                    <xsl:with-param name="hasEmptyElement"        select="'true'"/>

                    <xsl:with-param name="oninit"                 select="concat('changeCityTypeSelect', $blockIndex)"/>
                    <xsl:with-param name="onchange"               select="concat('changeCityTypeSelect', $blockIndex, '(this.value)')"/>
                </xsl:call-template>

                <script type="text/javascript">
                    function changeCityTypeSelect<xsl:value-of select="$blockIndex"/>(value)
                    {
                        <xsl:value-of select="$cityLiveSearchVarName"/>.addParameter('field(typeofcity)', value);
                    }
                </script>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId"   select="concat('localityElement', $blockIndex)"/>
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Населенный пункт</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div style="width: 375px; height: 26px">
                    <xsl:call-template name="liveSearchInput">
                        <xsl:with-param name="pageType"           select="'LOCALITY'"/>
                        <xsl:with-param name="boundToElement"     select="concat('localityElement', $blockIndex)"/>
                        <xsl:with-param name="inputName"          select="concat('locality',        $blockIndex)"/>
                        <xsl:with-param name="varName"            select="$locLiveSearchVarName"/>
                        <xsl:with-param name="url"                select="$liveSearchClientInfoUrl"/>
                        <xsl:with-param name="initialValue"       select="$localityFieldValue"/>
                        <xsl:with-param name="z-index"            select="'6'"/>
                        <xsl:with-param name="onSelect"           select="'onSelectLocality'"/>
                        <xsl:with-param name="onClear"            select="'clearLocality'"/>
                        <xsl:with-param name="onKeyDown"          select="'keyDownLocality'"/>
                        <xsl:with-param name="mutableJSObjects"   select="concat('', $streetLiveSearchVarName)"/>
                        <xsl:with-param name="blockIndex"         select="$blockIndex"/>
                        <xsl:with-param name="requiredParameters" select="'&quot;field(region)&quot;'"/>
                        <xsl:with-param name="length"             select="50"/>
                    </xsl:call-template>

                    <input id="{concat('localityCode', $blockIndex)}" name="{concat('localityCode', $blockIndex)}" type="hidden" value="{$localityCodeFieldValue}"/>
                </div>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Тип населенного пункта</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:call-template name="selectFromDictionary">
                    <xsl:with-param name="selectId"               select="concat('localityTypeSelect', $blockIndex)"/>
                    <xsl:with-param name="elementsFromDictionary" select="$localityTypes"/>
                    <xsl:with-param name="selectedElement"        select="$localityTypeSelectValue"/>
                    <xsl:with-param name="selectStyle"            select="'width: 110px;'"/>
                    <xsl:with-param name="hasEmptyElement"        select="'true'"/>

                    <xsl:with-param name="oninit"                 select="concat('changeLocalityTypeSelect', $blockIndex)"/>
                    <xsl:with-param name="onchange"               select="concat('changeLocalityTypeSelect', $blockIndex, '(this.value)')"/>
                </xsl:call-template>

                <script type="text/javascript">
                    function changeLocalityTypeSelect<xsl:value-of select="$blockIndex"/>(value)
                    {
                        <xsl:value-of select="$locLiveSearchVarName"/>.addParameter('field(typeoflocality)', value);
                    }
                </script>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId"   select="concat('streetElement', $blockIndex)"/>
            <xsl:with-param name="rowName">Улица</xsl:with-param>
            <xsl:with-param name="rowValue">
                <span class="floatLeft">
                    <div style="width: 375px; height: 26px">
                        <xsl:call-template name="liveSearchInput">
                            <xsl:with-param name="pageType"           select="'STREET'"/>
                            <xsl:with-param name="boundToElement"     select="concat('streetElement', $blockIndex)"/>
                            <xsl:with-param name="inputName"          select="concat('street',        $blockIndex)"/>
                            <xsl:with-param name="varName"            select="$streetLiveSearchVarName"/>
                            <xsl:with-param name="url"                select="$liveSearchClientInfoUrl"/>
                            <xsl:with-param name="initialValue"       select="$streetFieldValue"/>
                            <xsl:with-param name="z-index"            select="'5'"/>
                            <xsl:with-param name="onSelect"           select="'onSelectStreet'"/>
                            <xsl:with-param name="onClear"            select="'clearStreet'"/>
                            <xsl:with-param name="blockIndex"         select="$blockIndex"/>
                            <xsl:with-param name="requiredParameters" select="'&quot;field(region)&quot;'"/>
                            <xsl:with-param name="length"             select="50"/>
                        </xsl:call-template>
                    </div>
                </span>
                <xsl:if test="$streetFieldValue != ''">
                    <xsl:call-template name="popUpMessage">
                        <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                        <xsl:with-param name="app" select="$app"/>
                    </xsl:call-template>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Тип улицы</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:call-template name="selectFromDictionary">
                    <xsl:with-param name="selectId"               select="concat('streetTypeSelect', $blockIndex)"/>
                    <xsl:with-param name="elementsFromDictionary" select="$streetTypes"/>
                    <xsl:with-param name="selectedElement"        select="$streetTypeSelectValue"/>
                    <xsl:with-param name="selectStyle"            select="'width: 110px;'"/>
                    <xsl:with-param name="hasEmptyElement"        select="'true'"/>

                    <xsl:with-param name="oninit"                 select="concat('changeStreetTypeSelect', $blockIndex)"/>
                    <xsl:with-param name="onchange"               select="concat('changeStreetTypeSelect', $blockIndex, '(this.value)')"/>
                </xsl:call-template>

                <script type="text/javascript">
                    function changeStreetTypeSelect<xsl:value-of select="$blockIndex"/>(value)
                    {
                        <xsl:value-of select="$streetLiveSearchVarName"/>.addParameter('field(typeofstreet)', value);
                    }
                </script>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Дом</xsl:with-param>
            <xsl:with-param name="rowValue">
                <span class="floatLeft"><input type="text" name="{concat('house', $blockIndex)}" value="{$houseFieldValue}" maxlength="20" size="5"/></span>
                <xsl:if test="$houseFieldValue != ''">
                    <xsl:call-template name="popUpMessage">
                        <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                        <xsl:with-param name="app" select="$app"/>
                    </xsl:call-template>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Корпус</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="text" name="{concat('building', $blockIndex)}" value="{$buildingFieldValue}" maxlength="20" size="5"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Строение</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="text" name="{concat('construction', $blockIndex)}" value="{$constructionFieldValue}" maxlength="20" size="5"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Квартира</xsl:with-param>
            <xsl:with-param name="rowValue">
                <span class="floatLeft">
                    <input type="text" id="{concat('flat', $blockIndex)}" name="{concat('flat', $blockIndex)}" value="{$flatFieldValue}" maxlength="20" size="5">
                        <xsl:if test="$flatWithNumFieldValue = 'true'">
                            <xsl:attribute name="disabled">true</xsl:attribute>
                        </xsl:if>
                    </input>
                </span>
                <xsl:if test="$flatFieldValue != ''">
                    <xsl:call-template name="popUpMessage">
                        <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                        <xsl:with-param name="app" select="$app"/>
                    </xsl:call-template>
                </xsl:if>
                <div class="textTick">
                    <input class="float" id="noNum" name="{concat('flatWithNum', $blockIndex)}" type="checkbox" onclick="checkWithFlatNum(this, '{$blockIndex}')" value="{$flatWithNumFieldValue}">
                        <xsl:if test="$flatWithNumFieldValue = 'true'">
                            <xsl:attribute name="checked">true</xsl:attribute>
                        </xsl:if>
                    </input>
                    <label class="float" for="noNum">Квартира без номера</label>
                </div>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Индекс</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="text" name="{concat('index', $blockIndex)}" value="{$indexFieldValue}" maxlength="20" size="13" class="numberField"/>
            </xsl:with-param>
        </xsl:call-template>

        <script type="text/javascript">
            <xsl:if test="not($districtCodeFieldValue = '')">
                addQueryParameters('field(area)', '<xsl:value-of select="$districtCodeFieldValue"/>', [<xsl:value-of select="concat($cityLiveSearchVarName, ',', $locLiveSearchVarName, ',', $streetLiveSearchVarName)"/>]);
            </xsl:if>
            <xsl:if test="not($cityCodeFieldValue = '')">
                addQueryParameters('field(city)', '<xsl:value-of select="$cityCodeFieldValue"/>', [<xsl:value-of select="concat($locLiveSearchVarName, ',', $streetLiveSearchVarName)"/>]);
            </xsl:if>
            <xsl:if test="not($localityCodeFieldValue = '')">
                addQueryParameters('field(settlement)', '<xsl:value-of select="$localityCodeFieldValue"/>', [<xsl:value-of select="concat('', $streetLiveSearchVarName)"/>]);
            </xsl:if>
        </script>

        <xsl:if test="$blockIndex = '2'">
            <xsl:choose>
                <xsl:when test="$registrationTypeValue = 'TEMPORARY'">
                    <div id="tempRegistrationExpiryDate">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="lineId" select="'registrationExpiryDateRow'"/>
                            <xsl:with-param name="rowName">Дата истечения регистрации</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="rowValue">
                                <input type="text" name="registrationExpiryDate" value="{dh:formatXsdDateToString(registrationExpiryDate)}" maxlength="10" size="10" class="dot-date-pick"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </div>
                </xsl:when>
                <xsl:otherwise>
                    <div id="tempRegistrationExpiryDate">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="lineId" select="'registrationExpiryDateRow'"/>
                            <xsl:with-param name="rowName">Дата истечения регистрации</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="rowValue">
                                <input type="text" name="registrationExpiryDate" maxlength="10" size="10" class="dot-date-pick"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </div>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:if>
    </xsl:template>

    <!--Шаблон отображения данных о родственниках/детях. Для страницы подтверждения-->
    <xsl:template name="relativesViewInfo">
        <xsl:param name="dictionary"/>
        <xsl:param name="title"/>

        <xsl:param name="relativeType"/>
        <xsl:param name="relativeSurname"/>
        <xsl:param name="relativeName"/>
        <xsl:param name="relativePatrName"/>
        <xsl:param name="relativeBirthday"/>
        <xsl:param name="relativeDependent"/>
        <xsl:param name="relativeCredit"/>
        <xsl:param name="relativeEmployee"/>
        <xsl:param name="relativeEmployeePlace"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="false()"/>
            <xsl:with-param name="rowValue">
                <span class="blockTitle">
                    <xsl:value-of select="$title"/>
                </span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Степень родства:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="$dictionary/family-relation[code=$relativeType]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Фамилия:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$relativeSurname"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Имя:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$relativeName"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Отчество:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$relativePatrName"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата рождения:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$relativeBirthday"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Находится на иждивении?:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div class="textTick">
                    <input class="float" type="radio" disabled="disabled">
                        <xsl:if test="$relativeDependent = '1'">
                            <xsl:attribute name="checked">true</xsl:attribute>
                        </xsl:if>
                    </input>
                    <label class="float">Да &nbsp;</label>
                </div>
                <div class="textTick">
                    <input class="float" type="radio" disabled="disabled">
                        <xsl:if test="$relativeDependent= '2'">
                            <xsl:attribute name="checked">true</xsl:attribute>
                        </xsl:if>
                    </input>
                    <label class="float">Нет</label>
                </div>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Есть кредиты в Сбербанке?:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div class="textTick">
                    <input class="float" type="radio" disabled="disabled">
                        <xsl:if test="$relativeCredit = '1'">
                            <xsl:attribute name="checked">true</xsl:attribute>
                        </xsl:if>
                    </input>
                    <label class="float">Да &nbsp;</label>
                </div>
                <div class="textTick">
                    <input class="float" type="radio" disabled="disabled">
                        <xsl:if test="$relativeCredit= '2'">
                            <xsl:attribute name="checked">true</xsl:attribute>
                        </xsl:if>
                    </input>
                    <label class="float">Нет &nbsp;</label>
                </div>
                <div class="textTick">
                    <input class="float" type="radio" disabled="disabled">
                        <xsl:if test="$relativeCredit= '3'">
                            <xsl:attribute name="checked">true</xsl:attribute>
                        </xsl:if>
                    </input>
                    <label class="float">Не знаю</label>
                </div>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="isAllocate" select="'false'"/>
            <xsl:with-param name="rowValue">
                <div class="textTick">
                    <xsl:choose>
                        <xsl:when test="$relativeEmployee ='true'">
                            <input class="float" type="checkbox" checked="checked" disabled="disabled"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <input class="float" type="checkbox" disabled="disabled"/>
                        </xsl:otherwise>
                    </xsl:choose>
                    <label class="float">&nbsp;Является сотрудником Сбербанка</label>
                </div>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="$relativeEmployee ='true'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Место работы:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of  select="$relativeEmployeePlace"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <!--Шаблон отображения данных о прописке и адресе. Для страницы подтверждения-->
    <xsl:template name="addressViewInfo">
        <xsl:param name="dictionaries"/>
        <xsl:param name="regionSelectFieldValue"/>
        <xsl:param name="districtTypeSelectValue"/>
        <xsl:param name="districtFieldValue"/>
        <xsl:param name="cityTypeSelectValue"/>
        <xsl:param name="cityFieldValue"/>
        <xsl:param name="localityTypeSelectValue"/>
        <xsl:param name="localityFieldValue"/>
        <xsl:param name="streetTypeSelectValue"/>
        <xsl:param name="streetFieldValue"/>
        <xsl:param name="houseFieldValue"/>
        <xsl:param name="buildingFieldValue"/>
        <xsl:param name="constructionFieldValue"/>
        <xsl:param name="flatFieldValue"/>
        <xsl:param name="flatWithNumFieldValue"/>
        <xsl:param name="indexFieldValue"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Регион:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="$dictionaries/config/list-regions/regions[code=$regionSelectFieldValue]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Тип Района/Округа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="$dictionaries/config/list-types-of-regions/types-of-regions[code=$districtTypeSelectValue]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Район/Округ:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$districtFieldValue"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Тип города:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="$dictionaries/config/list-types-of-cities/types-of-cities[code=$cityTypeSelectValue]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Город:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$cityFieldValue"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Тип населенного пункта:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="$dictionaries/config/list-types-of-localities/types-of-localities[code=$localityTypeSelectValue]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Населенный пункт:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$localityFieldValue"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Тип улицы:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="$dictionaries/config/list-types-of-streets/types-of-streets[code=$streetTypeSelectValue]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Улица:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="$streetFieldValue != ''">
                    <span class="floatLeft"><b><xsl:value-of  select="$streetFieldValue"/></b></span>
                    <xsl:call-template name="popUpMessage">
                        <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                        <xsl:with-param name="app" select="$app"/>
                    </xsl:call-template>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Дом:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="$houseFieldValue != ''">
                    <span class="floatLeft"><b><xsl:value-of  select="$houseFieldValue"/></b></span>
                    <xsl:call-template name="popUpMessage">
                        <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                        <xsl:with-param name="app" select="$app"/>
                    </xsl:call-template>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Корпус:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$buildingFieldValue"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Строение:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$constructionFieldValue"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Квартира:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="$flatFieldValue != ''">
                    <span class="floatLeft"><b><xsl:value-of  select="$flatFieldValue"/></b></span>
                    <xsl:call-template name="popUpMessage">
                        <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                        <xsl:with-param name="app" select="$app"/>
                    </xsl:call-template>
                </xsl:if>
                <div class="textTick">
                    <input  class="float" id="noNumb" type="checkbox" disabled="disabled">
                        <xsl:if test="$flatWithNumFieldValue = 'true'">
                            <xsl:attribute name="checked">true</xsl:attribute>
                        </xsl:if>
                    </input>
                    <label class="float" for="noNumb">Квартира без номера</label>
                </div>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Индекс:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$indexFieldValue"/></b>
            </xsl:with-param>
        </xsl:call-template>

    </xsl:template>

    <!--Шаблон отображения дополнительных телефонов заявителя. Для страницы подтверждения-->
    <xsl:template name="additionalPhoneViewInfo">
        <xsl:param name="phoneBlankType"/>
        <xsl:param name="phoneBlankCountry"/>
        <xsl:param name="phoneBlankTelecom"/>
        <xsl:param name="phoneBlankNumber"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowValue">
                <span class="blockTitle">Дополнительный телефон</span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Тип телефона:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:choose>
                    <xsl:when test="$phoneBlankType = 'MOBILE'">Мобильный</xsl:when>
                    <xsl:when test="$phoneBlankType = 'RESIDENCE'">Домашний по адресу проживания</xsl:when>
                    <xsl:when test="$phoneBlankType = 'REGISTRATION'">Домашний по адресу регистрации</xsl:when>
                    <xsl:when test="$phoneBlankType = 'WORK'">Рабочий</xsl:when>
                </xsl:choose></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Номер телефона:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <span class="floatLeft"><b>+<xsl:value-of  select="$phoneBlankCountry"/>&nbsp;<xsl:value-of  select="$phoneBlankTelecom"/>&nbsp;<xsl:value-of  select="$phoneBlankNumber"/></b></span>
                <xsl:call-template name="popUpMessage">
                    <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                    <xsl:with-param name="app" select="$app"/>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!--Шаблон отображения данных о недвижимости. Для страницы подтверждения-->
    <xsl:template name="realtyViewInfo">
        <xsl:param name="dictionaries"/>
        <xsl:param name="realtyType"/>
        <xsl:param name="address"/>
        <xsl:param name="yearOfPurchase"/>
        <xsl:param name="square"/>
        <xsl:param name="typeOfSquareUnit"/>
        <xsl:param name="approxMarketValue"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowValue">
                <span class="blockTitle">Недвижимость</span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Вид недвижимости:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="$dictionaries/config/list-type-of-realty/type-of-realty[code=$realtyType]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Адрес:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$address"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Год приобретения:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$yearOfPurchase"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Площадь:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:value-of  select="$square"/>&nbsp;<xsl:value-of select="$dictionaries/config/square-units/square-unit[code=$typeOfSquareUnit]/name"/>
                </b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Примерная рыночная стоимость:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$approxMarketValue"/>&nbsp;руб.</b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!--Шаблон отображения данных о транспортном средстве. Для страницы подтверждения-->
    <xsl:template name="transportViewInfo">
        <xsl:param name="dictionaries"/>
        <xsl:param name="transportType"/>
        <xsl:param name="registrationNumber"/>
        <xsl:param name="brand"/>
        <xsl:param name="approxMarketValue"/>
        <xsl:param name="ageOfTransport"/>
        <xsl:param name="yearOfPurchase"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowValue">
                <span class="blockTitle">Транспортное средство (ТС)</span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Тип транспортного средства:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="$dictionaries/config/list-type-of-vehicle/type-of-vehicle[code=$transportType]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Регистрационный номер:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$registrationNumber"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Марка:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$brand"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Примерная рыночная стоимость:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$approxMarketValue"/>&nbsp;руб.</b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Возраст ТС:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$ageOfTransport"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Год покупки:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$yearOfPurchase"/></b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!--Шаблон отображения данных о долговом обязательстве. Для страницы подтверждения-->
    <xsl:template name="debtViewInfo">
        <xsl:param name="dictionaries"/>
        <xsl:param name="debtType"/>
        <xsl:param name="agreementNumber"/>
        <xsl:param name="startDate"/>
        <xsl:param name="endDate"/>
        <xsl:param name="creditor"/>
        <xsl:param name="totalAmount"/>
        <xsl:param name="rate"/>
        <xsl:param name="currency"/>
        <xsl:param name="debtAmount"/>
        <xsl:param name="paymentAmount"/>
        <xsl:param name="paymentMethod"/>
        <xsl:param name="paymentPeriod"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowValue">
                <span class="blockTitle">Долговое обязательство</span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Вид обязательства:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="$dictionaries/config/list-type-of-debit/type-of-debit[code=$debtType]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">№ договора:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$agreementNumber"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Дата заключения:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="dh:formatXsdDateToString($startDate)"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Дата окончания:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="dh:formatXsdDateToString($endDate)"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Кредитор:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$creditor"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Сумма кредита:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$totalAmount"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Ставка:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$rate"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Валюта:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$dictionaries/config/currencies/currency[code=$currency]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Остаток задолженности:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$debtAmount"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Сумма платежа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$paymentAmount"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Способ погашения:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$dictionaries/config/list-loan-payment-method/loan-payment-method[code=$paymentMethod]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Периодичность погашения:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$dictionaries/config/list-loan-payment-period/loan-payment-period[code=$paymentPeriod]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!--Шаблон отображения данных о зарплатных/пенсионных вкладах. Для страницы подтверждения-->
    <xsl:template name="additionalAccountViewInfo">
        <xsl:param name="accountBlankType"/>
        <xsl:param name="accountBlankResource"/>
        <xsl:param name="accountBlankCustomNumber"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">&nbsp;</xsl:with-param>
            <xsl:with-param name="rowValue">
                <span class="blockTitle">Зарплатный/пенсионный счёт</span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Тип:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="$accountBlankType = 'SALARY'"><b>Зарплатный</b></xsl:if>
                <xsl:if test="$accountBlankType = 'PENSION'"><b>Пенсионный</b></xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счёт:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="$accountBlankResource != ''">
                        <b><xsl:value-of select="$accountBlankResource"/></b>
                    </xsl:when>
                    <xsl:otherwise>
                        <b>Счет №<xsl:value-of select="$accountBlankCustomNumber"/></b>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!--Шаблон редактирования анкеты-->
    <xsl:template name="questionEditRow">
        <xsl:param name="questionId"/>
        <xsl:param name="questionText"/>
        <xsl:param name="answerType"/>
        <xsl:param name="answer"/>
        <xsl:param name="index"/>

        <xsl:if test="$questionText != ''">
            <tr class="question{$index}">
                <td><xsl:value-of select="$questionText"/></td>
                <xsl:choose>
                    <xsl:when test="$answerType = '1'">
                        <td colspan="2">
                            <input name="{concat('question', '_', $index, '_answer')}" type="text" class="answerText" value="{$answer}"/>
                        </td>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:variable name="tickClass">
                            <xsl:choose>
                                <xsl:when test="$answer = '1'">
                                    answerTick tickActive
                                </xsl:when>
                                <xsl:otherwise>
                                    answerTick
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <xsl:variable name="crossClass">
                            <xsl:choose>
                                <xsl:when test="$answer = '0'">
                                    answerCross crossActive
                                </xsl:when>
                                <xsl:otherwise>
                                    answerCross
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <td>
                            <div class="answerBlock">
                                <div class="{$tickClass}" onclick="yesClick('{$index}');"></div>
                                <span class="tickCrossDesc">Да</span>
                            </div>
                        </td>
                        <td>
                            <div class="answerBlock">
                                <div class="{$crossClass}" onclick="noClick('{$index}');"></div>
                                <span class="tickCrossDesc">Нет</span>
                            </div>
                        </td>
                        <input name="{concat('question', '_', $index, '_answer')}" type="hidden" class="{concat('question', '_', $index, '_answer')}" value="{$answer}"/>
                    </xsl:otherwise>
                </xsl:choose>
                <input type="hidden" name="{concat('question', '_', $index, '_text')}" value="{$questionText}"/>
                <input type="hidden" name="{concat('question', '_', $index, '_answer_type')}" value="{$answerType}"/>
                <input type="hidden" name="{concat('question', '_', $index, '_id')}" value="{$questionId}"/>
            </tr>
        </xsl:if>
    </xsl:template>

    <!--Шаблон отображения анкеты. Для страницы подтверждения-->
    <xsl:template name="questionViewInfo">
        <xsl:param name="questionText"/>
        <xsl:param name="answerType"/>
        <xsl:param name="answer"/>

        <xsl:if test="$answer != ''">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName"><xsl:value-of select="$questionText"/>:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:choose>
                        <xsl:when test="$answerType = '1'">
                            <b><xsl:value-of select="$answer"/></b>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:choose>
                                <xsl:when test="$answer = '0'">
                                    <b>Нет</b>
                                </xsl:when>
                                <xsl:otherwise>
                                    <b>Да</b>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <!--Шаблон отображения данных о зарплатных/пенсионных карт. Для страницы подтверждения-->
    <xsl:template name="additionalCardViewInfo">
        <xsl:param name="cardBlankType"/>
        <xsl:param name="cardBlankResource"/>
        <xsl:param name="cardBlankCustomNumber"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">&nbsp;</xsl:with-param>
            <xsl:with-param name="rowValue">
                <span class="blockTitle">Зарплатная/пенсионная карта</span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Тип:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="$cardBlankType = 'SALARY'"><b>Зарплатная</b></xsl:if>
                <xsl:if test="$cardBlankType = 'PENSION'"><b>Пенсионная</b></xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Карта:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="$cardBlankResource != ''">
                        <b><xsl:value-of select="$cardBlankResource"/></b>
                    </xsl:when>
                    <xsl:otherwise>
                        <b>Карта №<xsl:value-of select="$cardBlankCustomNumber"/></b>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="loanOffersClaimTerms">
        <xsl:param name="element" select="''"/>

        <xsl:variable name="loanOffersTerm" select="document('loan-offers-terms.xml')/entity-list/entity/field/text()"/>

        <script type="text/javascript">
            doOnLoad(function() {
                $('#<xsl:value-of select="$element"/>').click(function() {

                    openPopupTerms();
                    return false;
                });
            });

            function openPopupTerms()
            {
                var width   = 900;
                var height  = 600;
                var top     = (screen.height - height) /2;
                var left    = (screen.width  - width)  /2;

                var options = 'height=' + height + ', width=' + width + ',top=' + top + ', left=' + left + 'directories=0, titlebar=0, toolbar=0, location=0, status=0, menubar=0';

                var win = window.open('', 'terms', options, false);
                    win.document.write(document.getElementById('popupTermsWindowDiv').innerHTML);
                    win.document.close();
            }

            function onProcessingEnabledClick(element)
            {
                element.value = element.checked;
            }
        </script>

        <div id="popupTermsWindowDiv" style="display: none;">
            <div style="margin: 50px">
                <h1>Обработка персональных данных</h1>

                <p>
                    <xsl:value-of select="$loanOffersTerm"/>
                </p>

                <div style="text-align: center; margin-top: 50px">
                    <a href="#" onclick="window.close();">Закрыть</a>
                </div>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>