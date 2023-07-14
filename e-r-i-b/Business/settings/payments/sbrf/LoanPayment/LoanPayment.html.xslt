<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:xalan = "http://xml.apache.org/xalan">
<xsl:output method="html" version="1.0"  indent="yes"/>
<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
<xsl:param name="mode" select="'edit'"/>
<xsl:param name="webRoot" select="'webRoot'"/>
<xsl:param name="application" select="'application'"/>
<xsl:param name="app">
   <xsl:value-of select="$application"/>
</xsl:param>
<xsl:param name="resourceRoot" select="'resourceRoot'"/>
<xsl:param name="personAvailable" select="true()"/>
<xsl:param name="longOffer" select="false()"/>
<xsl:param name="isTemplate" select="'isTemplate'"/>

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

        function isNan(value)
        {
            return isNaN(parseInt(value));
        }

    </script>

    <xsl:choose>
        <xsl:when test="$mode = 'edit' and not($longOffer)">
            <xsl:apply-templates mode="edit"/>
        </xsl:when>
        <xsl:when test="$mode = 'edit' and $longOffer">
            <xsl:apply-templates mode="edit-long-offer"/>
        </xsl:when>
        <xsl:when test="$mode = 'view' and not($longOffer)">
            <xsl:apply-templates mode="view"/>
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
        <xsl:param name="activeCards"/>

         <select id="{$name}" name="{$name}" style="width:400px">
           <xsl:choose>
                <xsl:when test="$isTemplate = 'true'">
                    <option value="">Не задан</option>
                </xsl:when>
                <xsl:when test="count($activeCards) = 0">
                    <option value="">Нет доступных карт</option>
                    <script type="text/javascript">$(document).ready(function(){hideOrShowMakeLongOfferButton(true)});</script>
                </xsl:when>
                <xsl:when test="string-length($accountNumber) = 0 and string-length($linkId) = 0 and count($activeCards) > 1">
                    <option value="">Выберите карту списания</option>
                </xsl:when>
            </xsl:choose>
            <xsl:for-each select="$activeCards">
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
        </select>
    </xsl:template>

<xsl:template match="/form-data" mode="edit">
    <xsl:variable name="activeCards" select="document('stored-active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>
    <xsl:variable name="loans" select="document('different-loans.xml')/entity-list/*"/>
    <xsl:variable name="allLoans" select="document('all-loans.xml')/entity-list/*"/>

    <xsl:if test="$isTemplate != 'true'">
        <span class="simpleLink" onclick="javascript:openTemplateList('LOAN_PAYMENT');">
              <span class="text-green">выбрать из шаблонов платежей</span>
        </span>
    </xsl:if>
    <xsl:variable name="hint"><![CDATA[ На данной странице нельзя оплатить аннуитетные кредиты. Подробности Вы можете уточнить <a href="#" onclick=win.open('annLoanMessage')>здесь</a> или по телефону 8(800)555-5550 ]]></xsl:variable>
    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Кредит</xsl:with-param>
        <xsl:with-param name="description">
             <xsl:value-of select="$hint" disable-output-escaping="yes"/>
        </xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:variable name="currentLoan" select="loan"/>
            <xsl:variable name="numb" select="loanAccountNumber"/>
            <xsl:if test="count($loans) = 0">
                <script type="text/javascript">$(document).ready(function(){hideOrShowMakeLongOfferButton(true)});</script>
            </xsl:if>
            <select id="loan" name="loan" onchange="showLoanInfo(true);" style="width:400px">
                <xsl:choose>
                    <xsl:when test="$isTemplate = 'true'">
                        <option value="">Не задан</option>
                    </xsl:when>
                    <xsl:when test="count($allLoans) = 0">
                        <option value="">Нет открытых кредитов</option>
                    </xsl:when>
                    <xsl:when test="count($loans) = 0">
                        <option value="">Нет кредитов, доступных для погашения</option>
                    </xsl:when>
                    <xsl:when test="string-length($numb) = 0 and string-length($currentLoan) = 0">
                        <option value="">Выберите кредит</option>
                    </xsl:when>
                </xsl:choose>
                <xsl:for-each select="$loans">
                    <xsl:variable name="loanId" select="concat('loan:',field[@name='loanLinkId']/text())"/>
                    <option>
                        <xsl:attribute name="value"><xsl:value-of select="$loanId"/></xsl:attribute>
                        <xsl:if test="$numb=./@key or $currentLoan=$loanId">
                            <xsl:attribute name="selected"/>
                        </xsl:if>
                        <xsl:value-of select="./field[@name='type']"/>&nbsp;
                        <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                        <xsl:value-of select="mu:getCurrencySign(./field[@name='currencyCode'])"/>&nbsp;
                        (<xsl:value-of select="./field[@name='name']"/>)
                    </option>
                </xsl:for-each>
            </select>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Карта списания</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:if test="$personAvailable">
                <xsl:call-template name="resources">
                    <xsl:with-param name="name">fromResource</xsl:with-param>
                    <xsl:with-param name="accountNumber" select="fromAccountSelect"/>
                    <xsl:with-param name="linkId" select="fromResource"/>
                    <xsl:with-param name="activeCards" select="$activeCards"/>
                </xsl:call-template>
            </xsl:if>
            <xsl:if test="not($personAvailable)">
                <select id="fromAccountSelect" name="fromAccountSelect" disabled="disabled"><option
                        selected="selected">Счет клиента</option></select>
            </xsl:if>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
        <xsl:with-param name="id">sellAmount</xsl:with-param>
        <xsl:with-param name="description">Введите сумму</xsl:with-param>
        <xsl:with-param name="rowName">Сумма</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="amount" name="amount" type="text" value="{amount}" size="20" class="moneyField"/>&nbsp;<span id="loanCurrencyCode"/>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:if test="$isTemplate != 'true'">
        <xsl:call-template name="showDebts">
            <xsl:with-param name="title">Рекомендуемая сумма платежа</xsl:with-param>
            <xsl:with-param name="isEdit">true</xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <script type="text/javascript">
        var loans = new Array();
        function initLoans()
        {
            <xsl:for-each select="$loans">
                var loan = new Object();
                loan.currency = '<xsl:value-of select="./field[@name='currencyCode']"/>';
                loan.currencySign = currencySignMap.get(loan.currency);
                loan.nextPayment = '<xsl:value-of select="./field[@name='totalPaymentAmount']"/>';

                loan.principalAmount = '<xsl:value-of select="format-number(./field[@name='principalAmount'], '### ##0,00', 'sbrf')"/>' + ' ' + loan.currencySign;
                loan.interestsAmount = '<xsl:value-of select="format-number(./field[@name='interestsAmount'], '### ##0,00', 'sbrf')"/>' + ' ' + loan.currencySign;
                loan.totalPaymentAmount = '<xsl:value-of select="format-number(./field[@name='totalPaymentAmount'], '### ##0,00', 'sbrf')"/>' + ' ' + loan.currencySign;
                <xsl:if test="$isTemplate != 'true'">
                    <xsl:if test="./field[@name='otherCostsAmount']">
                        loan.otherCostsAmount = '<xsl:value-of select="format-number(./field[@name='otherCostsAmount'], '### ##0,00', 'sbrf')"/>' + ' ' + loan.currencySign;
                    </xsl:if>
                    <xsl:if test="./field[@name='penaltyDelayDebtAmount']">
                        loan.penaltyDelayDebtAmount = '<xsl:value-of select="format-number(./field[@name='penaltyDelayDebtAmount'], '### ##0,00', 'sbrf')"/>' + ' ' + loan.currencySign;
                    </xsl:if>
                    <xsl:if test="./field[@name='penaltyDelayPercentAmount']">
                        loan.penaltyDelayPercentAmount = '<xsl:value-of select="format-number(./field[@name='penaltyDelayPercentAmount'], '### ##0,00', 'sbrf')"/>' + ' ' + loan.currencySign;
                    </xsl:if>
                    <xsl:if test="./field[@name='penaltyUntimelyInsurance']">
                        loan.penaltyUntimelyInsurance = '<xsl:value-of select="format-number(./field[@name='penaltyUntimelyInsurance'], '### ##0,00', 'sbrf')"/>' + ' ' + loan.currencySign;
                    </xsl:if>
                    <xsl:if test="./field[@name='delayedPercentsAmount']">
                        loan.delayedPercentsAmount = '<xsl:value-of select="format-number(./field[@name='delayedPercentsAmount'], '### ##0,00', 'sbrf')"/>' + ' ' + loan.currencySign;
                    </xsl:if>
                    <xsl:if test="./field[@name='delayedDebtAmount']">
                        loan.delayedDebtAmount = '<xsl:value-of select="format-number(./field[@name='delayedDebtAmount'], '### ##0,00', 'sbrf')"/>' + ' ' + loan.currencySign;
                    </xsl:if>
                    <xsl:if test="./field[@name='earlyReturnAmount']">
                        loan.earlyReturnAmount = '<xsl:value-of select="format-number(./field[@name='earlyReturnAmount'], '### ##0,00', 'sbrf')"/>' + ' ' + loan.currencySign;
                    </xsl:if>
                    <xsl:if test="./field[@name='accountOperationsAmount']">
                        loan.accountOperationsAmount = '<xsl:value-of select="format-number(./field[@name='accountOperationsAmount'], '### ##0,00', 'sbrf')"/>' + ' ' + loan.currencySign;
                    </xsl:if>
                    <xsl:if test="./field[@name='penaltyAccountOperationsAmount']">
                        loan.penaltyAccountOperationsAmount = '<xsl:value-of select="format-number(./field[@name='penaltyAccountOperationsAmount'], '### ##0,00', 'sbrf')"/>' + ' ' + loan.currencySign;
                    </xsl:if>
                    <xsl:if test="./field[@name='earlyBaseDebtAmount']">
                        loan.earlyBaseDebtAmount = '<xsl:value-of select="format-number(./field[@name='earlyBaseDebtAmount'], '### ##0,00', 'sbrf')"/>' + ' ' + loan.currencySign;
                    </xsl:if>
                    <xsl:if test="./field[@name='overpayment']">
                        loan.overpayment = '<xsl:value-of select="format-number(./field[@name='overpayment'], '### ##0,00', 'sbrf')"/>' + ' ' + loan.currencySign;
                    </xsl:if>
                </xsl:if>

                loans['loan:' + '<xsl:value-of select="./field[@name='loanLinkId']"/>'] = loan;
            </xsl:for-each>
        }

        function showLoanInfo(change)
        {
            var amount = getElement("amount");
            var selectedLoan = getElementValue("loan");
            var loan = loans[selectedLoan];
            if (loan == null)
            {
                amount.value = "";
                var currencyField = document.getElementById("loanCurrencyCode");
                currencyField.innerHTML = "";
                <xsl:if test="$isTemplate != 'true'">
                    var principalAmountField = document.getElementById("principalAmountTD");
                    principalAmountField.innerHTML = "";
                    var interestsAmountField = document.getElementById("interestsAmountTD");
                    interestsAmountField.innerHTML = "";
                    var overpaymentFieldRow = document.getElementById("overpaymentFieldRow");
                    overpaymentFieldRow.style.display = 'none';
                    var penaltyField = document.getElementById("penaltyTR");
                    penaltyField.style.display = 'none';
                    initDebtField(penaltyField, 'otherCostsAmount');
                    initDebtField(penaltyField, 'penaltyDelayDebtAmount');
                    initDebtField(penaltyField, 'penaltyDelayPercentAmount');
                    initDebtField(penaltyField, 'penaltyUntimelyInsurance');
                    initDebtField(penaltyField, 'delayedPercentsAmount');
                    initDebtField(penaltyField, 'delayedDebtAmount');
                    initDebtField(penaltyField, 'earlyReturnAmount');
                    initDebtField(penaltyField, 'accountOperationsAmount');
                    initDebtField(penaltyField, 'penaltyAccountOperationsAmount');
                    initDebtField(penaltyField, 'earlyBaseDebtAmount');

                    var totalPaymentAmountField = document.getElementById("totalPaymentAmountTD");
                    totalPaymentAmountField.innerHTML = "";

                </xsl:if>
                return;
            }
            //устанавливаем в поле Сумма рекмендуемую сумму платежа
            if(isEmpty(amount) || change)
                amount.value = loan.nextPayment;
            var currencyField = document.getElementById("loanCurrencyCode");
            currencyField.innerHTML = loan.currencySign;
            <xsl:if test="$isTemplate != 'true'">
                showDebts(loan);
            </xsl:if>
        }

        <xsl:if test="$isTemplate != 'true'">
            function showDebts(loan)
            {
                //отображаем задолженности по кредиту
                var principalAmountField = document.getElementById("principalAmountTD");
                principalAmountField.innerHTML = loan.principalAmount;

                var interestsAmountField = document.getElementById("interestsAmountTD");
                interestsAmountField.innerHTML = loan.interestsAmount;

                var totalPaymentAmountField = document.getElementById("totalPaymentAmountTD");
                totalPaymentAmountField.innerHTML = '<span class="summ">' + loan.totalPaymentAmount + '</span>' ;

                var overpaymentFieldRow = document.getElementById("overpaymentFieldRow");
                overpaymentFieldRow.style.display = 'none';
                if (loan.overpayment != null &amp;&amp; loan.overpayment.length>0)
                {
                    var overpaymentField = document.getElementById("overpaymentTD");
                    overpaymentField.innerHTML = loan.overpayment;
                    overpaymentFieldRow.style.display = 'block';
                }
                var penaltyField = document.getElementById("penaltyTR");
                penaltyField.style.display = 'none';

                initDebtField(penaltyField, 'otherCostsAmount', loan.otherCostsAmount);
                initDebtField(penaltyField, 'penaltyDelayDebtAmount', loan.penaltyDelayDebtAmount);
                initDebtField(penaltyField, 'penaltyDelayPercentAmount', loan.penaltyDelayPercentAmount);
                initDebtField(penaltyField, 'penaltyUntimelyInsurance', loan.penaltyUntimelyInsurance);
                initDebtField(penaltyField, 'delayedPercentsAmount', loan.delayedPercentsAmount);
                initDebtField(penaltyField, 'delayedDebtAmount', loan.delayedDebtAmount);
                initDebtField(penaltyField, 'earlyReturnAmount', loan.earlyReturnAmount);
                initDebtField(penaltyField, 'accountOperationsAmount', loan.accountOperationsAmount);
                initDebtField(penaltyField, 'penaltyAccountOperationsAmount', loan.penaltyAccountOperationsAmount);
                initDebtField(penaltyField, 'earlyBaseDebtAmount', loan.earlyBaseDebtAmount);
            }

            function initDebtField(titleField, fieldName, value)
            {
                var tr = document.getElementById(fieldName + "TR");
                if (value != null &amp;&amp; value.length>0)
                {
                    titleField.style.display = '';
                    tr.style.display = '';
                    var field = document.getElementById(fieldName + "TD");
                    field.innerHTML = value;
                }
                else
                {
                    tr.style.display = 'none';
                }
            }
        </xsl:if>
        var countNotEmptyCards = 0;
        var countCards = 0;
        var indexNotEmptyCards = 0;
        function init()
        {
            var USING_STORED_CARDS_RESOURCE_MESSAGE    = 'Информация по Вашим картам может быть неактуальной.';
        
            var selectedLoan = getElementValue("loan");
            var loan = loans[selectedLoan];
            if (loan == null)
                return;

            var amountField = getElement("amount");
            if (isEmpty(amountField.value))
            {
                amountField.value = loan.nextPayment;
            }
            var currencyField = document.getElementById("loanCurrencyCode");
            currencyField.innerHTML = loan.currencySign;

            <xsl:if test="$isTemplate != 'true'">
                if (isEmpty('<xsl:value-of select="principal-amount"/>') || isEmpty('<xsl:value-of select="interests-amount"/>'))
                {
                    showDebts(loan);
                }
            </xsl:if>
            <xsl:for-each select="$activeCards">
                countCards++;
                <xsl:if test="field[@name='isUseStoredResource'] = 'true' ">
                    addMessage(USING_STORED_CARDS_RESOURCE_MESSAGE);
                </xsl:if>
                <xsl:if test="field[@name='amountDecimal'] > 0 ">
                    countNotEmptyCards ++;
                    indexNotEmptyCards = countCards;
                </xsl:if>
            </xsl:for-each>
            selectDefaultFromResource(countNotEmptyCards, indexNotEmptyCards);
        }

        function isEmpty(value)
        {
            return value == null || value.length==0;
        }

        function openAnnDesc(){
            win.open('annLoanDesc');
        }
        initLoans();
        init();
        showLoanInfo(false);
    </script>
</xsl:template>

<xsl:template match="/form-data" mode="edit-long-offer">

    <input type="hidden" name="loanName" value="{loanName}"/>
    <input type="hidden" name="loanCurrency" value="{loanCurrency}"/>
    <input type="hidden" name="loanAmount" value="{loanAmount}"/>
    <input type="hidden" name="loanType" value="{loanType}"/>
    <input type="hidden" name="fromAccountSelect" value="{fromAccountSelect}"/>
    <input type="hidden" name="fromAccountName" value="{fromAccountName}"/>
    <input type="hidden" name="resourseCurrency" value="{resourseCurrency}"/>

    <xsl:variable name="loan" select="loan"/>
    <xsl:variable name="fromResource" select="fromResource"/>
    <xsl:variable name="cards" select="document('stored-active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>
    <xsl:variable name="loans" select="document('different-loans.xml')/entity-list/*"/>

    <xsl:choose>
        <!--приводим fromResource к виду card:linkId-->
        <xsl:when test="not (starts-with($fromResource, 'card:'))">
            <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
            <xsl:variable name="resource">
                <xsl:value-of select="concat('card:', $cards[@key = $fromAccountSelect]/field[@name='cardLinkId']/text())"/>
            </xsl:variable>
            <input type="hidden" name="fromResource" value="{$resource}"/>
        </xsl:when>
        <xsl:otherwise>
            <input type="hidden" name="fromResource" value="{fromResource}"/>
        </xsl:otherwise>
    </xsl:choose>

    <xsl:choose>
        <!--приводим loan к виду loan:linkId-->
        <xsl:when test="not (starts-with($loan, 'loan:'))">
            <xsl:variable name="loanAccountNumber" select="loanAccountNumber"/>
            <xsl:variable name="loanResource">
                <xsl:value-of select="concat('loan:', $loans[@key = $loanAccountNumber]/field[@name='loanLinkId']/text())"/>
            </xsl:variable>
            <input type="hidden" name="loan" value="{$loanResource}"/>
        </xsl:when>
        <xsl:otherwise>
            <input type="hidden" name="loan" value="{loan}"/>
        </xsl:otherwise>
    </xsl:choose>

    <xsl:variable name="loanType">
        <xsl:choose>
            <xsl:when test="starts-with($loan, 'loan:')">
                <xsl:value-of select="$loans[field[@name = 'loanLinkId']/text() = substring($loan, 6, string-length($loan))]/field[@name = 'type']/text()"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="loanType"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:variable name="loanAmount">
        <xsl:choose>
             <xsl:when test="starts-with($loan, 'loan:')">
                <xsl:value-of select="$loans[field[@name = 'loanLinkId']/text() = substring($loan, 6, string-length($loan))]/field[@name = 'amountDecimal']/text()"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="amount"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:variable name="loanCurrency">
        <xsl:choose>
             <xsl:when test="starts-with($loan, 'loan:')">
                <xsl:value-of select="$loans[field[@name = 'loanLinkId']/text() = substring($loan, 6, string-length($loan))]/field[@name = 'currencyCode']/text()"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="loanCurrency"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:variable name="loanName">
        <xsl:choose>
             <xsl:when test="starts-with($loan, 'loan:')">
                <xsl:value-of select="$loans[field[@name = 'loanLinkId']/text() = substring($loan, 6, string-length($loan))]/field[@name = 'name']/text()"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="loanName"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Кредит</xsl:with-param>
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowValue">
            <xsl:value-of select="$loanType"/>&nbsp;
            <xsl:value-of select="format-number($loanAmount, '### ##0,00', 'sbrf')"/>&nbsp;
            <xsl:value-of select="mu:getCurrencySign($loanCurrency)"/>&nbsp;
            (<xsl:value-of select="$loanName"/>)
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="fromResourceNumber">
        <xsl:choose>
            <xsl:when test="starts-with($fromResource, 'card:')">
                <xsl:value-of select="$cards[field[@name = 'cardLinkId']/text() = substring($fromResource, 6, string-length($fromResource))]/@key"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="fromAccountSelect"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:variable name="fromResourceName">
        <xsl:choose>
            <xsl:when test="starts-with($fromResource, 'card:')">
                <xsl:value-of select="$cards[field[@name = 'cardLinkId']/text() = substring($fromResource, 6, string-length($fromResource))]/field[@name = 'name']/text()"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="fromAccountName"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:variable name="fromResourceCurrency">
        <xsl:choose>
            <xsl:when test="starts-with($fromResource, 'card:')">
                <xsl:value-of select="$cards[field[@name = 'cardLinkId']/text() = substring($fromResource, 6, string-length($fromResource))]/field[@name = 'currencyCode']/text()"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="resourceCurrency"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Карта списания</xsl:with-param>
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowValue">
            <xsl:value-of select="mask:getCutCardNumber($fromResourceNumber)"/>&nbsp;
            [<xsl:value-of select="$fromResourceName"/>]&nbsp;
            <xsl:value-of select="mu:getCurrencySign($fromResourceCurrency)"/>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
        <xsl:with-param name="rowName">Сумма</xsl:with-param>
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowValue">
            <xsl:if test="string-length(amount)>0">
                <span class="summ">
                    <xsl:value-of select="format-number(translate(amount, ', ','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                    <xsl:value-of select="mu:getCurrencySign($loanCurrency)"/>
                </span>
            </xsl:if>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="titleRow">
         <xsl:with-param name="rowName">&nbsp;<b>Параметры автоплатежа (регулярной операции)</b></xsl:with-param>
    </xsl:call-template>

    <input type="hidden" name="longOfferPayDay"/>
    <input type="hidden" name="firstPaymentDate"  value="{firstPaymentDate}"/>
    <input type="hidden" name="isSumModify" value="{isSumModify}"/>

     <xsl:call-template name="standartRow">
       <xsl:with-param name="rowName">Дата начала действия</xsl:with-param>
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
                      onchange="javascript:refreshReport();" onkeyup="javascript:refreshReport();"/>
       </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Дата окончания</xsl:with-param>
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

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Повторяется</xsl:with-param>
        <xsl:with-param name="rowValue">
            Каждый месяц
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId">everyMonthRow</xsl:with-param>
        <xsl:with-param name="rowName">Число месяца</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="text" name="ONCE_IN_MONTH_DAY" value="{longOfferPayDay}" size="1" maxlength="2"
                   onchange="refreshReport();" onkeyup="refreshReport();"/>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Выполняется</xsl:with-param>
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
        <xsl:with-param name="rowName">Сводка</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><div id="report"></div></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="lineId">nextPaymentDateRow</xsl:with-param>
        <xsl:with-param name="rowName">Ближайший платеж</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><div id="firstPaymentDate"></div></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:if test="isSumModify != 'true'">
        <input type="hidden" name="amount" value="{amount}"/>
    </xsl:if>

    <div id="paymentSumChangeBlock">
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Расчет суммы платежа</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">modifySumRow</xsl:with-param>
            <xsl:with-param name="rowName">Тип суммы</xsl:with-param>
            <xsl:with-param name="rowValue">
            <select id="longOfferSumType" name="longOfferSumType" style="width: 400px;"
                        onchange="javascript:refreshPayTypeBlock(getElementValue('longOfferSumType'));"
                        onkeyup="javascript:refreshPayTypeBlock(getElementValue('longOfferSumType'));"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">longOfferAmountRow</xsl:with-param>
            <xsl:with-param name="rowName">Сумма</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input id="amount" name="amount" value="{amount}" type="text" size="10" maxlength="10" class="moneyField"/>&nbsp;
                <xsl:value-of select="mu:getCurrencySign($loanCurrency)"/>
            </xsl:with-param>
        </xsl:call-template>
    </div>

    <script type="text/javascript">
        <![CDATA[

            Date.format = 'dd.mm.yyyy';         //устанавливаем для календаря формат даты

            var WRONG_DAY_ERROR = "Вы неправильно указали число месяца для оплаты. Пожалуйста, введите число от 1 до 28.";
            getElement("isSumModify").value = false;

            function refreshForm()
            {
                var event = "ONCE_IN_MONTH";   //для кредита открываем форму для типа оплаты "ежемесячно"
                refreshSumType(event);
                refreshReport();
            }

            $('#amount').click(function(){
                getElement("isSumModify").value = "true";
                $('#sellAmountRow').remove();
            });

            ]]>
            <xsl:if test="isSumModify = 'true'">
                $('#amount').click();
            </xsl:if>
            <![CDATA[

            //обновление сводки и ближайшей даты платежа
            function refreshReport()
            {
                var event = "ONCE_IN_MONTH";
                var longOfferStartDate = getElement("longOfferStartDate");
                getElement("firstPaymentDate").value = "";
                getElement("longOfferPayDay").value = "";
                $("#firstPaymentDate").text("");
                $("#report").text("");

                var day = getElementValue(event + "_DAY");

                var longOfferDate = new LongOfferDate(event, createDate(longOfferStartDate.value));

                if (longOfferDate.validate() == undefined)
                {
                    removeError(WRONG_DAY_ERROR);
                    getElement("firstPaymentDate").value = "";
                    getElement("longOfferPayDay").value = "";
                    $("#firstPaymentDate").text("");
                    $("#report").text("");
                    return;
                }

                if (longOfferDate.validate())
                {
                    var date = longOfferDate.toString();

                    getElement("firstPaymentDate").value = date;
                    getElement("longOfferPayDay").value = day;
                    $("#firstPaymentDate").text(date);
                    $("#report").text("ежемесячно " + parseInt(day) + "-го числа");
                    removeError(WRONG_DAY_ERROR);
                    return;
                }

                addError(WRONG_DAY_ERROR);
            }

            function refreshSumType(event)
            {
                var isSumModify = getElement("isSumModify");
                isSumModify.value = "true";

                var length = 0;
                var select = getElement("longOfferSumType");
                select.options.length = length;
                select.options[length++] = new Option(sumTypes["FIXED_SUMMA"], "FIXED_SUMMA");
                select.options[length++] = new Option(sumTypes["CREDIT_MINIMUM"], "CREDIT_MINIMUM");
                select.options[length++] = new Option(sumTypes["CREDIT_MANUAL"], "CREDIT_MANUAL");

                refreshPayTypeBlock(select.value);
                refreshReport();
            }

            function refreshPayTypeBlock(type)
            {
                var isSumModify = getElement("isSumModify");
                isSumModify.value = type == "FIXED_SUMMA";
                hideOrShow(ensureElement("longOfferAmountRow"),  type != "FIXED_SUMMA");
            }
        ]]>

            var sumTypes    = new Array();

            function initSumType()
            {
                <xsl:for-each select="document('sumTypesDictionary')/entity-list/entity">
                    sumTypes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./text()"/>';
                </xsl:for-each>
            }

            setCurrentDateToInput("longOfferStartDate");
            initSumType();
            refreshForm();

    </script>

</xsl:template>

<xsl:template match="/form-data" mode="view">

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Кредит</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:if test="string-length(loanAmount)>0">
                <b>
                    <xsl:value-of select="loanType"/>&nbsp;
                    <xsl:value-of select="format-number(loanAmount, '### ##0,00', 'sbrf')"/>&nbsp;
                    <xsl:value-of select="mu:getCurrencySign(loanCurrency)"/>&nbsp;
                    (<xsl:value-of select="loanName"/>)
                </b>
            </xsl:if>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Карта списания</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:if test="not($fromAccountSelect = '')">
                <b>
                    <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                    &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                    <xsl:value-of select="mu:getCurrencySign(resourceCurrency)"/>
                </b>
            </xsl:if>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="longOfferSumType" select="longOfferSumType"/>
    <xsl:variable name="sumTypes" select="document('sumTypesDictionary')/entity-list/entity"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
        <xsl:with-param name="rowName">Сумма</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b>
                <xsl:if test="$longOffer">
                    <xsl:value-of select="$sumTypes[@key=$longOfferSumType]"/>&nbsp;
                </xsl:if>
                <xsl:if test="not ($longOffer) or ($longOffer and (isSumModify = 'true'))">
                    <xsl:if test="string-length(amount)>0">
                        <span class="summ">
                            <xsl:value-of select="format-number(translate(amount, ',','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                            <xsl:value-of select="mu:getCurrencySign(amountCurrency)"/>
                        </span>
                    </xsl:if>
                </xsl:if>
            </b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">
            Статус <xsl:choose><xsl:when test="$isTemplate != 'true'"> платежа</xsl:when><xsl:otherwise> шаблона</xsl:otherwise></xsl:choose>
        </xsl:with-param>
        <xsl:with-param name="rowValue">
            <div id="state">
                <span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link">
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

    <xsl:if test="not($longOffer) and $isTemplate != 'true'">
        <xsl:call-template name="showDebts">
            <xsl:with-param name="title">Разбивка платежа</xsl:with-param>
        </xsl:call-template>
    </xsl:if>
</xsl:template>

<xsl:template match="/form-data" mode="view-long-offer">

    <xsl:apply-templates select="/form-data" mode="view"/>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName">&nbsp;<b>Параметры автоплатежа (регулярной операции)</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Дата начала действия</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b>
                <xsl:variable name="startDate" select="longOfferStartDate"/>
                <xsl:value-of select="concat(substring($startDate, 9, 2), '.', substring($startDate, 6, 2), '.', substring($startDate, 1, 4))"/>
            </b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Дата окончания</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b>
                <xsl:variable name="endDate" select="longOfferEndDate"/>
                <xsl:value-of select="concat(substring($endDate, 9, 2), '.', substring($endDate, 6, 2), '.', substring($endDate, 1, 4))"/>
            </b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Повторяется</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b>Каждый месяц</b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="firstDate"  select="firstPaymentDate"/>
    <xsl:variable name="day"    select="substring($firstDate, 9, 2)"/>
    <xsl:variable name="month"  select="substring($firstDate, 6, 2)"/>
    <xsl:variable name="year"   select="substring($firstDate, 1, 4)"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Дата оплаты</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b>
                <xsl:variable name="monthDescription" select="document('monthsDictionary')/entity-list/entity[@key=$month]/text()"/>
                <xsl:value-of select="concat($monthDescription, '. ', $day, ' число')"/>
            </b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Выполняется</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b>
                <xsl:variable name="priority" select="longOfferPrioritySelect"/>
                <xsl:value-of select="document('priority.xml')/entity-list/entity[@key=$priority]/text()"/>
            </b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Сводка</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b>
                <xsl:value-of select="concat('Каждый месяц', ' ' , $day, '-го числа')"/>
            </b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Ближайший платеж</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b>
                <xsl:choose>
                    <xsl:when test="isStartDateChanged = 'true'">
                        <span class="text-red">
                            <xsl:value-of select="concat($day, '.', $month, '.', $year)"/>
                        </span>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="concat($day, '.', $month, '.', $year)"/>
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
        <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
        <xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
        <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
        <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE' or $code='TEMPLATE'">Активный</xsl:when>
        <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
        <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
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
        <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
        <xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
        <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
        <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE' or $code='TEMPLATE'">Активный</xsl:when>
        <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
        <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
    </xsl:choose>
</xsl:template>

<xsl:template name="showDebts">
    <xsl:param name="title"/>
    <xsl:param name="isEdit"/>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b><xsl:value-of select="$title"/></b></xsl:with-param>
    </xsl:call-template>


    <xsl:call-template name="standartRow">
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowName">Основной долг</xsl:with-param>
        <xsl:with-param name="rowValue">
            <span id="principalAmountTD">
                <xsl:if test="string-length(principal-amount)>0">
                    <xsl:value-of select="format-number(principal-amount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(loanCurrency)"/>
                </xsl:if>
            </span>
            <xsl:if test="$longOffer">
                <input type="hidden" name="principal-amount" value="{principal-amount}"/>
            </xsl:if>
        </xsl:with-param>
    </xsl:call-template>
    <xsl:call-template name="standartRow">
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowName">Выплаты по процентам</xsl:with-param>
        <xsl:with-param name="rowValue">
            <span id="interestsAmountTD">
                <xsl:if test="string-length(interests-amount)>0">
                    <xsl:value-of select="format-number(interests-amount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(loanCurrency)"/>
                </xsl:if>
            </span>
            <xsl:if test="$longOffer">
                <input type="hidden" name="interests-amount" value="{interests-amount}"/>
            </xsl:if>
        </xsl:with-param>
    </xsl:call-template>
    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId">overpaymentFieldRow</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowName">Переплата</xsl:with-param>
        <xsl:with-param name="rowValue">
            <span id="overpaymentTD">
                <xsl:if test="string-length(overpayment)>0">
                    <xsl:value-of select="format-number(overpayment, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(loanCurrency)"/>
                </xsl:if>
            </span>
        </xsl:with-param>
        <xsl:with-param name="rowStyle"><xsl:if test="string-length(overpayment)=0">display: none</xsl:if></xsl:with-param>
    </xsl:call-template>


    <xsl:call-template name="standartRow">
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowName"><b>Пени и штрафы</b></xsl:with-param>
        <xsl:with-param name="invisibleFieldName">penaltyTR</xsl:with-param>
        <xsl:with-param name="isAllocate">'false'</xsl:with-param>
        <xsl:with-param name="rowStyle">
            <xsl:if test="not(string-length(otherCostsAmount)>0 or string-length(penaltyDelayDebtAmount)>0 or
                  string-length(delayedPercentsAmount)>0 or string-length(penaltyDelayPercentAmount)>0 or
                  string-length(penaltyUntimelyInsurance)>0 or string-length(delayedDebtAmount)>0 or
                  string-length(earlyReturnAmount)>0 or string-length(accountOperationsAmount)>0 or
                  string-length(penaltyAccountOperationsAmount)>0 or string-length(earlyBaseDebtAmount)>0)">display: none</xsl:if>
        </xsl:with-param>
        <xsl:with-param name="rowClass">subTitle</xsl:with-param>
    </xsl:call-template>


    <xsl:call-template name="addDebt">
        <xsl:with-param name="text">Расходы по взысканию задолженности</xsl:with-param>
        <xsl:with-param name="element" select="otherCostsAmount"/>
        <xsl:with-param name="elemName">otherCostsAmount</xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="addDebt">
        <xsl:with-param name="text">Просрочка основного долга</xsl:with-param>
        <xsl:with-param name="element" select="penaltyDelayDebtAmount"/>
        <xsl:with-param name="elemName">penaltyDelayDebtAmount</xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="addDebt">
        <xsl:with-param name="text">Просрочка процентов</xsl:with-param>
        <xsl:with-param name="element" select="penaltyDelayPercentAmount"/>
        <xsl:with-param name="elemName">penaltyDelayPercentAmount</xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="addDebt">
        <xsl:with-param name="text">Несвоевременное возобновление страхования предмета залога</xsl:with-param>
        <xsl:with-param name="element" select="penaltyUntimelyInsurance"/>
        <xsl:with-param name="elemName">penaltyUntimelyInsurance</xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="addDebt">
        <xsl:with-param name="text">Просроченные проценты за пользование кредитом</xsl:with-param>
        <xsl:with-param name="element" select="delayedPercentsAmount"/>
        <xsl:with-param name="elemName">delayedPercentsAmount</xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="addDebt">
        <xsl:with-param name="text">Просроченный основной долг</xsl:with-param>
        <xsl:with-param name="element" select="delayedDebtAmount"/>
        <xsl:with-param name="elemName">delayedDebtAmount</xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="addDebt">
        <xsl:with-param name="text">Плата за досрочный возврат</xsl:with-param>
        <xsl:with-param name="element" select="earlyReturnAmount"/>
        <xsl:with-param name="elemName">earlyReturnAmount</xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="addDebt">
        <xsl:with-param name="text">Плата за операции по ссудному счету</xsl:with-param>
        <xsl:with-param name="element" select="accountOperationsAmount"/>
        <xsl:with-param name="elemName">accountOperationsAmount</xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="addDebt">
        <xsl:with-param name="text">Просрочка платы за операции по ссудному счету</xsl:with-param>
        <xsl:with-param name="element" select="penaltyAccountOperationsAmount"/>
        <xsl:with-param name="elemName">penaltyAccountOperationsAmount</xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="addDebt">
        <xsl:with-param name="text">Досрочно, в счет основного долга</xsl:with-param>
        <xsl:with-param name="element" select="earlyBaseDebtAmount"/>
        <xsl:with-param name="elemName">earlyBaseDebtAmount</xsl:with-param>
    </xsl:call-template>

    <xsl:if test="$isEdit">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required">false</xsl:with-param>
            <xsl:with-param name="rowName"><span class="bold">Итого</span></xsl:with-param>
            <xsl:with-param name="rowValue">
                <span id="totalPaymentAmountTD">
                    <xsl:if test="string-length(total-payment-amount)>0">
                        <xsl:value-of select="format-number(total-payment-amount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(loanCurrency)"/>
                    </xsl:if>
                </span>
                <xsl:if test="$longOffer">
                    <input type="hidden" name="total-payment-amount" value="{total-payment-amount}"/>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>
</xsl:template>

<xsl:template name="addDebt">
   <xsl:param name="text"/>
   <xsl:param name="element"/>
   <xsl:param name="elemName"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowName"><xsl:value-of select="$text"/></xsl:with-param>
        <xsl:with-param name="rowValue">
            <span id="{$elemName}TD">
                <xsl:if test="string-length($element)>0">
                    <xsl:value-of select="format-number($element, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(loanCurrency)"/>
                </xsl:if>
            </span>
        </xsl:with-param>
        <xsl:with-param name="invisibleFieldName"><xsl:value-of select="$elemName"/>TR</xsl:with-param>
        <xsl:with-param name="rowStyle"><xsl:if test="string-length($element)=0">display: none</xsl:if></xsl:with-param>
    </xsl:call-template>
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
    <!-- Необязательный параметр -->
	<xsl:param name="fieldName"/>                   <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->
    <xsl:param name="isAllocate" select="'true'"/>  <!-- Выделить при нажатии -->
    <xsl:param name="invisibleFieldName"/>  <!-- Указывает на поля, которые нужно скрыть  -->
    <xsl:param name="rowClass"/>            <!-- Класс поля  -->

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

<!--  Поиск ошибки по имени поля
В данной реализации ошибки обрабатывает javascript
                <xsl:if test="$mode = 'edit'">
                    <xsl:if test="boolean($validationErrors/entity[@key=$fieldName])">
                        <xsl:copy-of select="$validationErrors/entity[@key=$fieldName]"/>
                    </xsl:if>
                </xsl:if>
-->
 <xsl:variable name="styleClass">
		<xsl:choose>
            <xsl:when test="string-length($rowClass) > 0">
                <xsl:value-of select="$rowClass"/>
            </xsl:when>
			<xsl:when test="$isAllocate = 'true'">
				<xsl:value-of select="'form-row'"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="'form-row-addition'"/>
			</xsl:otherwise>
		</xsl:choose>
 </xsl:variable>

<div id="{$invisibleFieldName}" class="{$styleClass}">
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

	<div class="paymentLabel"><span class="paymentTextLabel"><xsl:copy-of select="$rowName"/>:</span>
        <xsl:if test="$required = 'true' and $mode = 'edit'">
            <span id="asterisk_{$id}" class="asterisk" name="asterisk_{$lineId}">*</span>
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
        <xsl:param name="rowValue"/>
        <div id="{$lineId}" class="{$styleClassTitle}">
            <xsl:copy-of select="$rowName"/>&nbsp;<xsl:copy-of select="$rowValue"/>
        </div>
    </xsl:template>
</xsl:stylesheet>
