<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:su="java://com.rssl.phizic.utils.StringHelper"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil"
                xmlns:dh="java://com.rssl.phizic.business.documents.DocumentHelper"
                xmlns:xalan = "http://xml.apache.org/xalan">

<xsl:import href="commonFieldTypes.template.xslt"/>
<xsl:output method="html" version="1.0"  indent="yes"/>
<xsl:param name="mode" select="'edit'"/>
<xsl:param name="webRoot" select="'webRoot'"/>
<xsl:param name="application" select="'application'"/>
<xsl:param name="app">
   <xsl:value-of select="$application"/>
</xsl:param>
<xsl:param name="resourceRoot" select="'resourceRoot'"/>
<xsl:param name="longOffer" select="false()"/>
<xsl:param name="personAvailable" select="true()"/>
<xsl:param name="isTemplate" select="'isTemplate'"/>
<xsl:param name="byTemplate" select="'byTemplate'"/>
<xsl:param name="receiverInfo" select="'receiverInfo'"/>
<xsl:param name="documentState" select="'documentState'"/>
<xsl:param name="postConfirmCommission" select="postConfirmCommission"/>

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

    <xsl:variable name="recType" select="./form-data/receiverSubType"/>

    <xsl:choose>
        <xsl:when test="$mode = 'edit' and not($longOffer) and $recType = 'externalAccount'">
            <xsl:apply-templates mode="edit-externalAccount"/>
        </xsl:when>
        <xsl:when test="$mode = 'edit' and not($longOffer) and ($recType = 'ourCard' or $recType = 'ourAccount' or  $recType = 'ourPhone')">
            <xsl:apply-templates mode="edit-ourBankPayment"/>
        </xsl:when>
        <xsl:when test="$mode = 'edit' and not($longOffer) and $documentState = 'WAIT_CLIENT_MESSAGE'">
           <xsl:apply-templates mode="edit-message-externalCard"/>
        </xsl:when>
        <xsl:when test="$mode = 'edit' and not($longOffer)">
           <xsl:apply-templates mode="edit-externalCard"/>
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

        <select id="{$name}" name="{$name}" onchange="refreshForm();">
            <xsl:variable name="recType">
                <xsl:choose>
		            <xsl:when test="receiverSubType = ''">
		                <xsl:value-of select="'ourCard'" />
		            </xsl:when>
		            <xsl:otherwise>
			            <xsl:value-of select="receiverSubType" />
		            </xsl:otherwise>
	            </xsl:choose>
            </xsl:variable>

            <xsl:choose>
                <xsl:when test="count($activeCards) = 0 and ($recType='ourCard' or $recType='ourPhone')">
                    <option value="">Нет доступных карт</option>
                    <script type="text/javascript">$(document).ready(function(){hideOrShowMakeLongOfferButton(true)});</script>
                </xsl:when>
                <xsl:when test="count($activeCards) = 0 and count($activeAccounts) = 0">
                    <option value="">Нет доступных карт и счетов</option>
                    <script type="text/javascript">$(document).ready(function(){hideOrShowMakeLongOfferButton(true)});</script>
                </xsl:when>
                <xsl:when test="string-length($accountNumber) = 0 and string-length($linkId) = 0">
                    <xsl:choose>
                        <xsl:when test="$recType='ourCard' or $recType='ourPhone' or $recType='masterCardExternalCard' or $recType='visaExternalCard'">
                            <xsl:if test="count($activeCards) > 0" >
                                <option value="">Выберите карту списания</option>
                            </xsl:if>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:choose>
                                <xsl:when test="count($activeCards) = 0 and count($activeAccounts) > 0">
                                    <option value="">Выберите счет списания</option>
                                </xsl:when>
                                <xsl:when test="count($activeAccounts) + count($activeCards) > 0">
                                    <option value="">Выберите счет/карту списания</option>
                                </xsl:when>
                            </xsl:choose>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
            </xsl:choose>
            <xsl:if test="$recType='ourAccount' or $recType='externalAccount'">
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

    <xsl:template name="cardResources">
        <xsl:param name="name"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="linkId"/>
        <xsl:param name="activeCards"/>

        <select id="{$name}" name="{$name}" onchange="refreshForm();">
            <xsl:choose>
                <xsl:when test="count($activeCards) = 0">
                    <option value="">Нет доступных карт</option>
                    <script type="text/javascript">$(document).ready(function(){hideOrShowMakeLongOfferButton(true)});</script>
                </xsl:when>
                <xsl:when test="string-length($accountNumber) = 0 and string-length($linkId) = 0">
                    <xsl:if test="count($activeCards) > 0" >
                        <option value="">Выберите карту списания</option>
                    </xsl:if>
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

<xsl:template match="/form-data" mode="edit-externalAccount">
    <xsl:variable name="activeAccounts" select="document('stored-active-debit-allowed-external-phiz-accounts.xml')/entity-list/*"/>
    <xsl:variable name="activeCards" select="document('stored-active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>

    <input type="hidden" name="exactAmount" value="{exactAmount}" id="exactAmount"/>
    <input type="hidden" name="buyAmountCurrency"  value="{buyAmountCurrency}"/>
    <input type="hidden" name="isErrorCurrency" id="isErrorCurrency" value="false"/>
    <input type="hidden" name="isCardTransfer" value="{isCardTransfer}"/>
    <input type="hidden" name="receiverSubType" value="{receiverSubType}"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Номер документа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="documentNumber" value="{documentNumber}"/>
            <b><xsl:value-of  select="documentNumber"/></b>
        </xsl:with-param>
        <xsl:with-param name="isAllocate" select="'false'"/>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Дата документа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="documentDate" value="{documentDate}"/>
            <b><xsl:value-of  select="documentDate"/></b>
        </xsl:with-param>
        <xsl:with-param name="isAllocate" select="'false'"/>
    </xsl:call-template>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Получатель</b></xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:if test="$isTemplate != 'true'">
                <a class="blueGrayLink size13" onclick="javascript:openTemplateList('INDIVIDUAL_TRANSFER');">выбрать из шаблонов платежей</a>
            </xsl:if>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'receiverAccountRow'"/>
        <xsl:with-param name="description"><cut/>Введите номер счета получателя (не менее 20, но и не более 25 цифр без точек и пробелов).</xsl:with-param>
        <xsl:with-param name="rowName">Номер счета</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input name="receiverAccountCurrency" type="hidden"/>
            <input id="receiverAccountInternal" name="receiverAccountInternal" type="text" value="{receiverAccountInternal}"  size="26" maxlength="25"
                   onchange="javascript:checkAccountCurrency(getElementValue('receiverAccountInternal'));" onkeyup="javascript:checkAccountCurrency(getElementValue('receiverAccountInternal'));">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'receiverSurnameRow'"/>
        <xsl:with-param name="description">Введите фамилию получателя перевода.</xsl:with-param>
        <xsl:with-param name="rowName">Фамилия</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverSurname" name="receiverSurname" type="text" size="20" maxlength="20">
                <xsl:attribute name="value">
                    <xsl:value-of select="receiverSurname"/>
                </xsl:attribute>
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'receiverFirstNameRow'"/>
        <xsl:with-param name="description">Введите имя получателя перевода.</xsl:with-param>
        <xsl:with-param name="rowName">Имя</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverFirstName" name="receiverFirstName" type="text" size="20" maxlength="20">
                <xsl:attribute name="value">
                    <xsl:value-of select="receiverFirstName"/>
                </xsl:attribute>
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="description">Введите отчество получателя перевода. Если у получателя есть отчество, то для исполнения операции его обязательно необходимо указать. Обратите внимание, если у получателя нет отчества, его вводить не нужно.</xsl:with-param>
        <xsl:with-param name="lineId"   select="'receiverPatrNameRow'"/>
        <xsl:with-param name="rowName">Отчество</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverPatrName" name="receiverPatrName" type="text" size="20" maxlength="20">
                <xsl:attribute name="value">
                    <xsl:value-of select="receiverPatrName"/>
                </xsl:attribute>
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="id" select="'receiverINN'"/>
        <xsl:with-param name="lineId" select="'receiverINNRow'"/>
        <xsl:with-param name="description"><cun/>Укажите Идентификационный Номер Налогоплательщика. У частных лиц он состоит из 12 цифр.</xsl:with-param>
        <xsl:with-param name="rowName">ИНН</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverINN" name="receiverINN" type="text" value="{receiverINN}" size="14" maxlength="12">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="false"/>
        <xsl:with-param name="lineId" select="'receiverAddressRow'"/>
        <xsl:with-param name="description">Введите адрес получателя перевода.</xsl:with-param>
        <xsl:with-param name="rowName">Адрес</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverAddress" name="receiverAddress" type="text" value="{receiverAddress}" weigth="250px" size="35" maxlength="200">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:if test="$byTemplate != 'true'">
        <xsl:call-template name="titleRow">
            <xsl:with-param name="lineId" select="'bankTitleRow'"/>
            <xsl:with-param name="rowName"><b>Банк получателя</b></xsl:with-param>
            <xsl:with-param name="rowValue">
                <a class="blueGrayLink size13" onclick="javascript:openNationalBanksDictionary(setBankInfo,getFieldValue('bank'),getFieldValue('receiverBIC'), false);">выбрать из справочника</a>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'bankNameRow'"/>
        <xsl:with-param name="description"><cut/>Выберите банк, в котором открыт счет получателя. Для этого нажмите на ссылку «Выбрать из справочника». В открывшемся окне установите флажок напротив нужного банка и нажмите на кнопку «Выбрать». Поля «БИК» и «Корр. счет» заполнятся автоматически.</xsl:with-param>
        <xsl:with-param name="rowName">Наименование</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="bank" name="bank" type="text" value="{bank}" size="35" maxlength="100">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'bankBIKRow'"/>
        <xsl:with-param name="description">Введите Банковский Идентификационный Код. БИК может состоять только из 9 цифр.</xsl:with-param>
        <xsl:with-param name="rowName">БИК</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverBIC" name="receiverBIC" type="text" value="{receiverBIC}" maxlength="9" size="24" onchange="getBankByBIC();">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'bankCorAccountRow'"/>
        <xsl:with-param name="description"><cut/>Укажите корреспондентский счет банка (20 цифр без точек и пробелов)</xsl:with-param>
        <xsl:with-param name="rowName">Корр. счет</xsl:with-param>
        <xsl:with-param name="required" select="false"/>
        <xsl:with-param name="rowValue">
            <input id="receiverCorAccount" name="receiverCorAccount" type="text" value="{receiverCorAccount}" maxlength="20" size="24">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Перевод</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id">fromResource</xsl:with-param>
        <xsl:with-param name="lineId" select="'fromResourceRow'"/>
        <xsl:with-param name="rowName">Счет списания</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:if test="$personAvailable">
                <xsl:call-template name="resources">
                    <xsl:with-param name="name">fromResource</xsl:with-param>
                    <xsl:with-param name="accountNumber" select="fromAccountSelect"/>
                    <xsl:with-param name="linkId" select="fromResource"/>
                    <xsl:with-param name="activeCards" select="$activeCards"/>
                    <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
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
        <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
        <xsl:with-param name="id">sellAmount</xsl:with-param>
        <xsl:with-param name="description">Введите сумму, которую необходимо перевести</xsl:with-param>
        <xsl:with-param name="rowName">Сумма</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="sellAmount" name="sellAmount" type="text" value="{sellAmount}" size="24"
                   onchange="javascript:sellCurrency();" onkeyup="javascript:sellCurrency();" class="moneyField"/>&nbsp;
            <span id="sellAmountCurrency"></span>
        </xsl:with-param>
    </xsl:call-template>
    <div class="float">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
            <xsl:with-param name="id">buyAmount</xsl:with-param>
            <xsl:with-param name="description">Введите сумму, которую необходимо перевести</xsl:with-param>
            <xsl:with-param name="rowName">Сумма в валюте зачисления</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input id="buyAmount" name="buyAmount" type="text" value="{buyAmount}" size="24"
                       onchange="javascript:buyCurrency();" onkeyup="javascript:buyCurrency();" class="moneyField"/>&nbsp;
                <span id="buyAmountCurrency"></span>
            </xsl:with-param>
        </xsl:call-template>
    </div>
    <div class="clear"></div>
    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'postConfirmCommissionMessage'"/>
        <xsl:with-param name="rowName">Комиссия</xsl:with-param>
        <xsl:with-param name="rowValue">
            <i><xsl:value-of select="dh:getSettingMessage('commission.prepare.transfer.message')"/></i>
        </xsl:with-param>
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowStyle">display: none</xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'groundRow'"/>
        <xsl:with-param name="description"><cut/>Укажите, с какой целью Вы переводите деньги. Например, благотворительный взнос.</xsl:with-param>
        <xsl:with-param name="rowName">Назначение платежа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <textarea id="ground" name="ground" rows="4" cols="40"><xsl:value-of select="ground"/></textarea>
        </xsl:with-param>
    </xsl:call-template>

    <script type="text/javascript" language="JavaScript">

        var EMPTY_ACCOUNT_ERROR = "Вы неправильно указали счет получателя. Пожалуйста, проверьте номер счета.";
        var REGEXP_BIC_MESSAGE = "Вы неправильно указали БИК. Пожалуйста, введите ровно 9 цифр.";
        var BANKS_NOT_FOUND_MESSAGE = "БИК банка получателя не найден в справочнике банков.";

        var currencies  = new Array();
        var resourceCurrencies  = new Array();

        function hideError()
        {
            $("#fromResourceRow .paymentValue .errorDiv").hide();
            var fromResourceRow = document.getElementById("fromResourceRow");
            fromResourceRow.error = false;
            fromResourceRow.className = "form-row";
        }

        function init()
        {
            var countNotEmptyAcc = 0;
            var countAcc = 0;
            var indexNotEmptyAcc = 0;
            var countNotEmptyCards = 0;
            var countCards = 0;
            var indexNotEmptyCards = 0;

            <xsl:for-each select="$activeAccounts">
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                resourceCurrencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                countAcc++;
                <xsl:if test="field[@name='amountDecimal'] > 0 ">
                    countNotEmptyAcc++;
                    indexNotEmptyAcc = countAcc;
                </xsl:if>
                <xsl:if test="field[@name='isUseStoredResource'] = 'true'">
                    addMessage('Информация по Вашим счетам может быть неактуальной.');
                </xsl:if>
            </xsl:for-each>

            <xsl:for-each select="$activeCards">
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                resourceCurrencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                countCards++;
                <xsl:if test="field[@name='amountDecimal'] > 0 ">
                    countNotEmptyCards++;
                    indexNotEmptyCards = countCards;
                </xsl:if>
                <xsl:if test="field[@name='isUseStoredResource'] = 'true' ">
                    addMessage('Информация по Вашим картам может быть неактуальной.');
                </xsl:if>
            </xsl:for-each>

            //массив с разрешенными валютами
            currencies["810"] = "RUB";
            currencies["840"] = "USD";
            currencies["978"] = "EUR";

            refreshForm();

            if (!isEmpty(document.getElementById("sellAmount").value))
                sellCurrency();
            if (!isEmpty(document.getElementById("buyAmount").value))
                buyCurrency();

            var account = getElementValue('receiverAccountInternal');
            if (!isEmpty(account))
                checkAccountCurrency(account);

            selectDefaultFromResource(countNotEmptyCards + countNotEmptyAcc, indexNotEmptyAcc > 0 ? indexNotEmptyAcc : countAcc + indexNotEmptyCards);
        }

        function setBankInfo(bankInfo)
        {
            removeError(REGEXP_BIC_MESSAGE);
            removeError(BANKS_NOT_FOUND_MESSAGE);
            setElement("bank", bankInfo["name"]);
            setElement("receiverBIC", bankInfo["BIC"]);
            setElement("receiverCorAccount", bankInfo["account"]);
        }

        function Payment(isCardToAccount, fromCurrency, toCurrency)
        {
            this.fromCurrency   = fromCurrency;
            this.toCurrency = toCurrency;
            this.isCardToAccount = isCardToAccount;

            this.refresh = function()
            {
                hideOrShow(ensureElement("buyAmountRow"),  !isCardToAccount)
                hideOrShow(ensureElement("sellAmountRow"), isCardToAccount);

                if (isCardToAccount)
                    buyCurrency();
                else
                    sellCurrency();

                $("#sellAmountCurrency").text(currencySignMap.get(this.fromCurrency));
                $("#buyAmountCurrency").text(currencySignMap.get(this.toCurrency));
                getElement('buyAmountCurrency').value = this.toCurrency;

                //показываем/скрываем кнопку "Сохранить как регулярный платеж"
                //ДП доступны для переводов: карта/вклад - физ. лицо, только если счет списания не пуст
                var fromResource = ensureElement('fromResource').value
                hideOrShowMakeLongOfferButton(!<xsl:value-of select="dh:isExternalPhizAccountPaymentsAllowed()"/> &amp;&amp; fromResource.indexOf('account') != -1);

                <xsl:if test="$isTemplate != 'true'">
                    var supportedCommissionPaymentTypes = "<xsl:value-of select="dh:getSupportedCommissionPaymentTypes()"/>".split('|');
                    var paymentType = null;
                    if(fromResource.indexOf('card') != -1)
                        paymentType = "CardToOtherBank";
                    if(paymentType!=null &amp;&amp; currencies[fromResource] == 'RUB' || currencies[fromResource] == 'RUR')
                        paymentType = 'Rur' + paymentType;

                    var isSupportedCommissionPaymentType = supportedCommissionPaymentTypes.contains(paymentType);
                    hideOrShow(ensureElement("postConfirmCommissionMessage"), !isSupportedCommissionPaymentType);
                    if (isSupportedCommissionPaymentType)
                        $("#buyAmountRow .paymentTextLabel").text("Сумма перевода:");
                </xsl:if>
            };
        }

        function refreshForm()
        {
            var fromResource = getElement("fromResource");
            var fromCurrency = resourceCurrencies[fromResource.value];

            //перевод на карту в сбербанке или через номер мобильного телефона
            var toCurrency  = getElementValue("receiverAccountCurrency");

            var payment = new Payment(fromResource.value.indexOf('card:') != -1, fromCurrency, toCurrency);
            payment.refresh();
        }

        function buyCurrency()
        {
            var sellAmount  = ensureElement("sellAmount");
            var exactAmount = ensureElement("exactAmount");

            sellAmount.value = "";
            exactAmount.value  = "destination-field-exact";
        }

        function sellCurrency()
        {
            var buyAmount   = ensureElement("buyAmount");
            var exactAmount = ensureElement("exactAmount");

            buyAmount.value = "";
            exactAmount.value  = "charge-off-field-exact";
        }

        function checkAccountCurrency(account)
        {
            if (account.length &lt; 19)
                return;

            var accountCurrency = getElement("receiverAccountCurrency");
            var currency = currencies[account.substring(5, 8)];

            if (isEmpty(currency))
            {
                addError(EMPTY_ACCOUNT_ERROR);
                accountCurrency.value = "";
            }
            else
            {
                removeError(EMPTY_ACCOUNT_ERROR);
                accountCurrency.value = currency;
            }

            refreshForm();
        }

        function getBankByBIC()
        {
            var receiverBIC = getFieldValue("receiverBIC");
            ensureElement("bank").value = "";
            ensureElement("receiverCorAccount").value = "";
            if (!(/^\d{9}$/.test(receiverBIC)))
            {
                addError(REGEXP_BIC_MESSAGE);
                removeError(BANKS_NOT_FOUND_MESSAGE);
                return;
            }

            var url = document.webRoot + "/private/dictionary/banks/national.do";
            var params = "operation=button.getByBIC&amp;field(BIC)=" + receiverBIC;

            ajaxQuery(params, url, updateBankInfo, "json");
        }

        function updateBankInfo(bank)
        {
            removeError(REGEXP_BIC_MESSAGE);
            if (isEmpty(bank))
            {
                addError(BANKS_NOT_FOUND_MESSAGE);
                return;
            }
            removeError(BANKS_NOT_FOUND_MESSAGE);

            ensureElement("bank").value = bank.name;
            ensureElement("receiverCorAccount").value = bank.account;
        }

        doOnLoad(function(){
            init();
        });
    </script>

</xsl:template>

<xsl:template match="/form-data" mode="edit-externalCard">
    <xsl:variable name="activeCards" select="document('stored-active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>

    <input type="hidden" name="exactAmount" value="destination-field-exact" id="exactAmount"/>
    <input type="hidden" name="buyAmountCurrency"  value="RUB"/>
    <input type="hidden" name="isErrorCurrency" id="isErrorCurrency" value="false"/>
    <input type="hidden" name="isCardTransfer" value="{isCardTransfer}"/>
    <input type="hidden" name="receiverSubType" value="{receiverSubType}"/>
    <input type="hidden" name="messageToReceiver" value="{messageToReceiver}"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Номер документа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="documentNumber" value="{documentNumber}"/>
            <b><xsl:value-of  select="documentNumber"/></b>
        </xsl:with-param>
        <xsl:with-param name="isAllocate" select="'false'"/>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Дата документа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="documentDate" value="{documentDate}"/>
            <b><xsl:value-of  select="documentDate"/></b>
        </xsl:with-param>
        <xsl:with-param name="isAllocate" select="'false'"/>
    </xsl:call-template>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Получатель</b></xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:if test="$isTemplate != 'true'">
                <span class="simpleLink" onclick="javascript:openTemplateList('INDIVIDUAL_TRANSFER');">
                    <span class="text-green">выбрать из шаблонов платежей</span>
                </span>
            </xsl:if>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id"     select="'externalCardNumber'"/>
        <xsl:with-param name="lineId" select="'externalCardNumberRow'"/>
        <xsl:with-param name="description"><cut />Введите номер карты, на которую Вы хотите перевести деньги. Это может быть карта VISA  или MasterCard любого банка. Номер карты должен состоять из 15, 16, 18 или 19 цифр.</xsl:with-param>
        <xsl:with-param name="rowName">Номер карты получателя</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input name="externalCardCurrency" type="hidden" value="RUB"/>
            <input id="externalCardNumber" name="externalCardNumber" type="text" value="{externalCardNumber}" size="27" maxlength="19" onblur="checkNumber();" onkeyup="updateReceiverSubType();" onchange="updateReceiverSubType();" onkeydown="doIfEnterKeyPress(event, updateReceiverSubType);">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
            <xsl:variable name="externalCardTypeImageSrc">
                <xsl:choose>
                    <xsl:when test="receiverSubType = 'masterCardExternalCard'">
                        <xsl:value-of select="concat($resourceRoot, '/commonSkin/images/p2p_masterCardExternalCard.png')"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="concat($resourceRoot, '/commonSkin/images/p2p_visaExternalCard.png')"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:variable>
            <img id="externalCardTypeImage" alt="" src="{$externalCardTypeImageSrc}">
                <xsl:if test="receiverSubType != 'visaExternalCard' and receiverSubType != 'masterCardExternalCard'">
                    <xsl:attribute name="style">display:none;</xsl:attribute>
                </xsl:if>
            </img>
            <div style="display: none;" id="currencyErrorDiv"></div>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Перевод</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id">fromResource</xsl:with-param>
        <xsl:with-param name="lineId" select="'fromResourceRow'"/>
        <xsl:with-param name="rowName">Счет списания</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:if test="$personAvailable">
                <xsl:call-template name="cardResources">
                    <xsl:with-param name="name">fromResource</xsl:with-param>
                    <xsl:with-param name="accountNumber" select="fromAccountSelect"/>
                    <xsl:with-param name="linkId" select="fromResource"/>
                    <xsl:with-param name="activeCards" select="$activeCards"/>
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
        <xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
        <xsl:with-param name="id">buyAmount</xsl:with-param>
        <xsl:with-param name="description">Введите сумму, которую необходимо перевести</xsl:with-param>
        <xsl:with-param name="rowName">Сумма</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="buyAmount" name="buyAmount" type="text" value="{buyAmount}" size="24" class="moneyField"/>&nbsp;
            <span id="buyAmountCurrency"></span>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'postConfirmCommissionMessage'"/>
        <xsl:with-param name="rowName">Комиссия</xsl:with-param>
        <xsl:with-param name="rowValue">
            <i><xsl:value-of select="dh:getSettingMessage('commission.prepare.transfer.message')"/></i>
        </xsl:with-param>
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowStyle">display: none</xsl:with-param>
    </xsl:call-template>

    <script type="text/javascript" language="JavaScript">
        var cardAccounts = new Array();
        var cardList = new Array();
        var wasShowLink = false;
        var countNotEmptyCards = 0;
        var countCards = 0;
        var indexNotEmptyCards = 0;

        function init()
        {
            hideOrShowMakeLongOfferButton(true);
            $("#buyAmountCurrency").text(currencySignMap.get('RUB'));

            <xsl:for-each select="$activeCards">
                countCards++;
                <xsl:if test="field[@name='amountDecimal'] > 0 ">
                    countNotEmptyCards ++;
                    indexNotEmptyCards = countCards;
                </xsl:if>
                <xsl:if test="field[@name='isUseStoredResource'] = 'true' ">
                    addMessage('Информация по Вашим картам может быть неактуальной.');
                </xsl:if>
            </xsl:for-each>

            updateReceiverSubType();
            refreshForm();
            selectDefaultFromResource(countNotEmptyCards, indexNotEmptyCards);
        }

        function updateReceiverSubType()
        {
            <xsl:choose>
                <xsl:when test="pu:impliesService('MastercardMoneySendService')">
                    var receiverSubType = 'masterCardExternalCard';
                </xsl:when>
                <xsl:otherwise>
                    var receiverSubType = 'visaExternalCard';
                </xsl:otherwise>
            </xsl:choose>
            <xsl:choose>
                <xsl:when test="pu:impliesService('MastercardMoneySendService') and pu:impliesService('VisaMoneySendService')">
                    var mastercardAndVisa = true;
                </xsl:when>
                <xsl:otherwise>
                    var mastercardAndVisa = false;
                </xsl:otherwise>
            </xsl:choose>

            var cardNumberValue = $('#externalCardNumber').val();
            var firstSymbol = cardNumberValue.substr(0, 1);
            if(mastercardAndVisa)
            if (firstSymbol == '4')
                receiverSubType = 'visaExternalCard';
            else if (firstSymbol == '5' || firstSymbol == '6')
                receiverSubType = 'masterCardExternalCard';
            else
                receiverSubType = 'both';

            $('[name=receiverSubType]').val(receiverSubType);
            var cardImgContainer = $('#externalCardTypeImage');
            if ((firstSymbol == '4' &amp;&amp; receiverSubType == 'visaExternalCard') || (firstSymbol == '5' || firstSymbol == '6') &amp;&amp; receiverSubType == 'masterCardExternalCard')
            {
                cardImgContainer.attr('src', '<xsl:value-of select="$resourceRoot"/>/commonSkin/images/p2p_' + receiverSubType + '.png');
                cardImgContainer.show();
                cardImgContainer.attr('style', 'display: inline;');
            }
            else
            {
                cardImgContainer.hide();
                cardImgContainer.attr('src', '<xsl:value-of select="$resourceRoot"/>/commonSkin/images/p2p_visaExternalCard.png');
            }

            var externalCardNumberRow = document.getElementById("externalCardNumberRow");
            var detail = findChildByClassName(externalCardNumberRow, "detail");
            if (detail != undefined)
            {
                var cardNumberRowHint = "Введите номер карты, на которую Вы хотите перевести деньги.";
                if (receiverSubType == 'masterCardExternalCard')
                    cardNumberRowHint = "Введите номер карты, на которую Вы хотите перевести деньги. Это может быть карта MasterCard или Maestro любого банка. Номер карты должен состоять из 15, 16, 18 или 19 цифр.";
                if (receiverSubType == 'visaExternalCard')
                    cardNumberRowHint = "Введите номер карты, на которую Вы хотите перевести деньги. Это может быть карта VISA любого банка. Номер карты должен состоять из 15, 16, 18 или 19 цифр.";
                if (receiverSubType == 'both')
                    cardNumberRowHint = "Введите номер карты, на которую Вы хотите перевести деньги. Это может быть карта VISA  или MasterCard любого банка. Номер карты должен состоять из 15, 16, 18 или 19 цифр.";
                detail.innerHTML = cardNumberRowHint;
            }
        }


        function refreshForm()
        {
            <xsl:if test="$isTemplate != 'true'">
                var supportedCommissionPaymentTypes = "<xsl:value-of select="dh:getSupportedCommissionPaymentTypes()"/>".split('|');
                var isSupportedCommissionPaymentType = supportedCommissionPaymentTypes.contains(null);
                hideOrShow(ensureElement("postConfirmCommissionMessage"), !isSupportedCommissionPaymentType);
                $("#buyAmountRow .paymentTextLabel").text(isSupportedCommissionPaymentType? "Сумма перевода:": "Сумма:");
            </xsl:if>
        }

        function hideError()
        {
            $("#fromResourceRow .paymentValue .errorDiv").hide();
            var fromResourceRow = document.getElementById("fromResourceRow");
            fromResourceRow.error = false;
            fromResourceRow.className = "form-row";
        }

        var oldExternalNumber = "";
        var externalNumber = "";
        var oldCurrencyMessage = [];

        function checkNumber()
        {
            externalNumber = getElementValue("externalCardNumber");
            externalNumber = externalNumber.replace(/ /g,'');
            //не слать ajax запрос если реально номер не изменился или в поле номера карты введено не 16 или 18 символов
            if ((oldExternalNumber == externalNumber) || (!(externalNumber.length == 16 || externalNumber.length == 18 || externalNumber.length == 15 || externalNumber.length == 19)))
            {
                 oldExternalNumber = externalNumber;
                 return;
            }
            oldExternalNumber = externalNumber;
            getElement("externalCardNumber").value = externalNumber;

            var errorText =  'Вы указали некорректный номер карты получателя. Пожалуйста, проверьте номер карты.';
            if (!checkViaLuhn(externalNumber))
                addError(errorText);
            else
                removeError(errorText, null);
        }

        function checkViaLuhn(number)
        {
            var zero = '0';


        		var checksum = 0;

        		for (var i = 0; number.length > i; i++)
        		{

        			var currentDigitCost = number.charAt(i) - zero;

        			if (i % 2 == number.length % 2)
        			{
        				currentDigitCost *= 2;
                    if (currentDigitCost > 9)
                        currentDigitCost-=9;
        			}
        			checksum += currentDigitCost;
        		}

        		checksum %= 10;

        		return (checksum == 0)
        }




        doOnLoad(function(){
            init();
        });

    </script>

</xsl:template>

<xsl:template match="/form-data" mode="edit-message-externalCard">
    <input type="hidden" name="exactAmount" value="destination-field-exact" id="exactAmount"/>
    <input type="hidden" name="isErrorCurrency" id="isErrorCurrency" value="false"/>
    <input type="hidden" name="isCardTransfer" value="{isCardTransfer}"/>
    <input type="hidden" name="receiverSubType" value="{receiverSubType}"/>
    <input type="hidden" name="documentNumber" value="{documentNumber}"/>
    <input type="hidden" name="documentDate" value="{documentDate}"/>
    <input type="hidden" name="externalCardCurrency" value="{externalCardCurrency}"/>
    <input type="hidden" name="externalCardNumber" value="{externalCardNumber}"/>
    <input type="hidden" name="fromResource" value="{fromResourceLink}"/>
    <input type="hidden" name="fromResourceLink" value="{fromResourceLink}"/>
    <input type="hidden" name="buyAmount" value="{buyAmount}"/>
    <input type="hidden" name="buyAmountCurrency"  value="{buyAmountCurrency}"/>
    <input type="hidden" name="receiverAccount"     value="{receiverAccount}"/>
    <input type="hidden" name="receiverFirstName"   value="{receiverFirstName}"/>
    <input type="hidden" name="receiverSurname"     value="{receiverSurname}"/>
    <input type="hidden" name="receiverPatrName"    value="{receiverPatrName}"/>
    <input type="hidden" name="fromAccountSelect"    value="{mask:getCutCardNumber(fromAccountSelect)}"/>
    <input type="hidden" name="fromAccountName"    value="{fromAccountName}"/>
    <input type="hidden" name="fromResourceCurrency"    value="{fromResourceCurrency}"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Номер документа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of  select="documentNumber"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Дата документа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of  select="documentDate"/></b>
        </xsl:with-param>
    </xsl:call-template>

     <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Получатель</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Номер карты получателя</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of  select="mask:getCutCardNumber(receiverAccount)"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">ФИО</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of  select="ph:getFormattedPersonName(receiverFirstName, receiverSurname, receiverPatrName)" disable-output-escaping="no"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Перевод</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Счет списания</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b>
                <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
            </b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="longOfferSumType"   select="longOfferSumType"/>
    <xsl:variable name="sumTypes" select="document('long-offer-sum-types.xml')/entity-list/entity"/>
    <xsl:variable name="isFullNameAmount" select="fromResourceCurrency != buyAmountCurrency"/>

    <xsl:choose>
        <xsl:when test="$postConfirmCommission">
            <xsl:call-template name="transferSumRows">
                <xsl:with-param name="fromResourceCurrency" select="fromResourceCurrency"/>
                <xsl:with-param name="toResourceCurrency" select="buyAmountCurrency"/>
                <xsl:with-param name="chargeOffAmount" select="sellAmount"/>
                <xsl:with-param name="destinationAmount" select="buyAmount"/>
                <xsl:with-param name="documentState" select="state"/>
                <xsl:with-param name="exactAmount" select="exactAmount"/>
                <xsl:with-param name="needUseTotalRow" select="'false'"/>
                <xsl:with-param name="tariffPlanESB" select="tariffPlanESB"/>
            </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
            <xsl:if test="(exactAmount = 'charge-off-field-exact') or (exactAmount != 'destination-field-exact')">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
                    <xsl:with-param name="rowName">
                         Сумма<xsl:if test="$isFullNameAmount"> в валюте списания</xsl:if>
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
                                        <xsl:value-of select="format-number(translate(sellAmount, ',','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                        <xsl:value-of select="mu:getCurrencySign(sellAmountCurrency)"/>
                                    </span>
                                </xsl:if>
                            </xsl:if>
                        </xsl:if>
                        <xsl:if test="not ($longOffer) or ($longOffer and isSumModify != 'true')">
                            <xsl:if test="string-length(sellAmount)>0">
                                <span class="summ">
                                    <xsl:value-of select="format-number(translate(sellAmount, ',','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                    <xsl:value-of select="mu:getCurrencySign(sellAmountCurrency)"/>
                                </span>
                            </xsl:if>
                        </xsl:if>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>

            <xsl:if test="exactAmount = 'destination-field-exact'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
                    <xsl:with-param name="rowName">Сумма<xsl:if test="not(receiverSubType = 'visaExternalCard' or receiverSubType = 'masterCardExternalCard')"> в валюте зачисления</xsl:if></xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:if test="$longOffer and (isSumModify = 'true')">
                            <b><xsl:value-of select="$sumTypes[@key=$longOfferSumType]"/></b>&nbsp;
                        </xsl:if>
                        <xsl:if test="string-length(buyAmount)>0">
                            <span class="summ">
                                <xsl:value-of select="format-number(translate(buyAmount, ',','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                <xsl:value-of select="mu:getCurrencySign(buyAmountCurrency)"/>
                            </span>
                        </xsl:if>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>

            <xsl:if test="string-length(commission)>0 and $isTemplate!='true'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Комиссия</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <span name="commission" class="bold">
                            <b><xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(commissionCurrency)"/></b>
                        </span>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
        </xsl:otherwise>
    </xsl:choose>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id">messageToReceiver</xsl:with-param>
        <xsl:with-param name="lineId">messageToReceiverRow</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="description">Введите сообщение для получателя средств, которое будет направлено ему на мобильный телефон.</xsl:with-param>
        <xsl:with-param name="rowName">Сообщение получателю</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="messageToReceiver" name="messageToReceiver" type="text" value="{messageToReceiver}" size="45" maxlength="40"/>
        </xsl:with-param>
    </xsl:call-template>

    <script type="text/javascript" language="JavaScript">
        doOnLoad(function(){
            $('#editTemplateButton').show();
            hideOrShowMakeLongOfferButton(true);
        });
    </script>
</xsl:template>

<xsl:template match="/form-data" mode="edit-ourBankPayment">
    <xsl:variable name="activeAccounts" select="document('stored-active-debit-allowed-external-phiz-accounts.xml')/entity-list/*"/>
    <xsl:variable name="activeCards" select="document('stored-active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>

    <input type="hidden" name="exactAmount" value="{exactAmount}" id="exactAmount"/>
    <input type="hidden" name="buyAmountCurrency"  value="{buyAmountCurrency}"/>
    <input type="hidden" name="isErrorCurrency" id="isErrorCurrency" value="false"/>
    <input type="hidden" name="isCardTransfer" value="{isCardTransfer}"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Номер документа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="documentNumber" value="{documentNumber}"/>
            <b><xsl:value-of  select="documentNumber"/></b>
        </xsl:with-param>
        <xsl:with-param name="isAllocate" select="'false'"/>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Дата документа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="documentDate" value="{documentDate}"/>
            <b><xsl:value-of  select="documentDate"/></b>
        </xsl:with-param>
        <xsl:with-param name="isAllocate" select="'false'"/>
    </xsl:call-template>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Получатель</b></xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:if test="$isTemplate != 'true'">
                <span class="simpleLink" onclick="javascript:openTemplateList('INDIVIDUAL_TRANSFER');">
                    <span class="text-green">выбрать из шаблонов платежей</span>
                </span>
            </xsl:if>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="recType" select="receiverSubType"/>
    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Куда</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="receiverSubType" id="receiverSubType" value="{receiverSubType}"/>
            <div id='receiverSubTypeControl'>
                <div id="ourCardReceiverSubTypeButton" class="inner firstButton">
                    <xsl:if test="$byTemplate != 'true'">
                        <xsl:attribute name="onclick">changeReceiverSubType('ourCard');</xsl:attribute>
                    </xsl:if>
                    <xsl:if test="$recType='ourCard'">
                        <xsl:attribute name="class">inner firstButton activeButton</xsl:attribute>
                    </xsl:if>
                        Карта
                </div>
                <div id="ourAccountReceiverSubTypeButton" class="inner hoverButton">
                    <xsl:if test="$byTemplate != 'true'">
                        <xsl:attribute name="onclick">changeReceiverSubType('ourAccount');</xsl:attribute>
                    </xsl:if>
                    <xsl:if test="$recType='ourAccount'">
                        <xsl:attribute name="class">inner hoverButton activeButton</xsl:attribute>
                    </xsl:if>
                        Счет
                </div>
                <div id="ourPhoneReceiverSubTypeButton" class="inner lastButton">
                    <xsl:if test="$byTemplate != 'true'">
                        <xsl:attribute name="onclick">changeReceiverSubType('ourPhone');</xsl:attribute>
                    </xsl:if>
                    <xsl:if test="$recType='ourPhone'">
                        <xsl:attribute name="class">inner lastButton activeButton</xsl:attribute>
                    </xsl:if>
                    Карта по номеру моб. тел.
                </div>
            </div>
        </xsl:with-param>
        <xsl:with-param name="isAllocate" select="'false'"/>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id"     select="'externalPhoneNumber'"/>
        <xsl:with-param name="lineId" select="'externalPhoneNumberRow'"/>
        <xsl:with-param name="description">Номер телефона абонента оператора сотовых услуг<cut/>Введите номер телефона - 10 цифр. Например, (906) 555-22-33</xsl:with-param>
        <xsl:with-param name="rowName">Номер телефона</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b>+7 </b>
            <input id="externalPhoneNumber" name="externalPhoneNumber" type="text" value="{externalPhoneNumber}" size="20" maxlength="10" onblur="checkNumber();" class="phone-template-p2p">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
            <xsl:if test="pu:impliesService('ContactSyncService') and pu:impliesService('ChooseContactService') and $byTemplate != 'true'">
                &nbsp;<span class="text-green" style="text-decoration: underline; cursor: pointer;" onclick="openContacts();">выбрать из контактной книги</span>
                <script type="text/javascript">
                    function openContacts()
                    {
                        var win  =  window.open(document.webRoot + "/private/contacts/list.do", 'Contacts', "resizable=1,menubar=0,toolbar=0,scrollbars=1, width=800, height=500");
                        win.moveTo(screen.width / 2 - 400, screen.height / 2 - 250);
                        return false;
                    }

                    function setPhoneNumber(phone)
                    {
                        document.getElementById("externalPhoneNumber").value = phone;
                        document.getElementById("externalPhoneNumber").focus();
                        checkNumber();
                    }
                </script>
            </xsl:if>
        </xsl:with-param>
    </xsl:call-template>
    <div class="relative">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="id"     select="'externalCardNumber'"/>
            <xsl:with-param name="lineId" select="'externalCardNumberRow'"/>
            <xsl:with-param name="description"><cut />Введите номер карты другого клиента Сбербанка, на которую Вы хотите перевести деньги. Номер карты может содержать 15, 16, 18 или 19 цифр.</xsl:with-param>
            <xsl:with-param name="rowName">Номер карты получателя</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input name="externalCardCurrency" type="hidden" value="{buyAmountCurrency}"/>
                <input id="externalCardNumber" name="externalCardNumber" type="text" value="{externalCardNumber}" size="20" maxlength="19" onblur="checkNumber();">
                    <xsl:if test="$byTemplate = 'true'">
                        <xsl:attribute name="disabled"/>
                    </xsl:if>
                </input>
                <div style="display: none;" id="currencyErrorDiv"></div>
            </xsl:with-param>
        </xsl:call-template>
    </div>
    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'receiverAccountRow'"/>
        <xsl:with-param name="description"><cut/>Введите номер счета получателя (не менее 20, но и не более 25 цифр без точек и пробелов).</xsl:with-param>
        <xsl:with-param name="rowName">Номер счета</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input name="receiverAccountCurrency" type="hidden"/>
            <input id="receiverAccountInternal" name="receiverAccountInternal" type="text" value="{receiverAccountInternal}"  size="26" maxlength="25"
                   onchange="javascript:checkAccountCurrency(getElementValue('receiverAccountInternal'));" onkeyup="javascript:checkAccountCurrency(getElementValue('receiverAccountInternal'));">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'receiverSurnameRow'"/>
        <xsl:with-param name="description">Введите фамилию получателя перевода.</xsl:with-param>
        <xsl:with-param name="rowName">Фамилия</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverSurname" name="receiverSurname" type="text" size="20" maxlength="20">
               <xsl:if test="$recType != 'ourCard' and $recType != 'ourPhone'">
                    <xsl:attribute name="value">
                        <xsl:value-of select="receiverSurname"/>
                    </xsl:attribute>
                    <xsl:if test="$byTemplate = 'true'">
                        <xsl:attribute name="disabled"/>
                    </xsl:if>
               </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'receiverFirstNameRow'"/>
        <xsl:with-param name="description">Введите имя получателя перевода.</xsl:with-param>
        <xsl:with-param name="rowName">Имя</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverFirstName" name="receiverFirstName" type="text" size="20" maxlength="20">
               <xsl:if test="$recType != 'ourCard' and $recType != 'ourPhone'">
                    <xsl:attribute name="value">
                        <xsl:value-of select="receiverFirstName"/>
                    </xsl:attribute>
               </xsl:if>
               <xsl:if test="$byTemplate = 'true'">
                   <xsl:attribute name="disabled"/>
               </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="description">Введите отчество получателя перевода. Если у получателя есть отчество, то для исполнения операции его обязательно необходимо указать. Обратите внимание, если у получателя нет отчества, его вводить не нужно.</xsl:with-param>
        <xsl:with-param name="lineId"   select="'receiverPatrNameRow'"/>
        <xsl:with-param name="rowName">Отчество</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverPatrName" name="receiverPatrName" type="text" size="20" maxlength="20">
               <xsl:if test="$recType != 'ourCard' and $recType != 'ourPhone'">
                    <xsl:attribute name="value">
                        <xsl:value-of select="receiverPatrName"/>
                    </xsl:attribute>
               </xsl:if>
               <xsl:if test="$byTemplate = 'true'">
                   <xsl:attribute name="disabled"/>
               </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="id" select="'receiverINN'"/>
        <xsl:with-param name="lineId" select="'receiverINNRow'"/>
        <xsl:with-param name="description"><cun/>Укажите Идентификационный Номер Налогоплательщика. У частных лиц он состоит из 12 цифр.</xsl:with-param>
        <xsl:with-param name="rowName">ИНН</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverINN" name="receiverINN" type="text" value="{receiverINN}" size="14" maxlength="12">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="false"/>
        <xsl:with-param name="lineId" select="'receiverAddressRow'"/>
        <xsl:with-param name="description">Введите адрес получателя перевода.</xsl:with-param>
        <xsl:with-param name="rowName">Адрес</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverAddress" name="receiverAddress" type="text" value="{receiverAddress}" weigth="250px" size="35" maxlength="200">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:if test="$byTemplate != 'true'">
        <xsl:call-template name="titleRow">
            <xsl:with-param name="lineId" select="'bankTitleRow'"/>
            <xsl:with-param name="rowName"><b>Банк получателя</b></xsl:with-param>
            <xsl:with-param name="rowValue">
                <span class="simpleLink" onclick="javascript:openNationalBanksDictionary(setBankInfo,getFieldValue('bank'),getFieldValue('receiverBIC'), true);">
                    <u class="text-green">выбрать из справочника</u>
                </span>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'bankNameRow'"/>
        <xsl:with-param name="description"><cut/>Выберите банк, в котором открыт счет получателя. Для этого нажмите на ссылку «Выбрать из справочника». В открывшемся окне установите флажок напротив нужного банка и нажмите на кнопку «Выбрать». Поля «БИК» и «Корр. счет» заполнятся автоматически.</xsl:with-param>
        <xsl:with-param name="rowName">Наименование</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="bank" name="bank" type="text" value="{bank}" size="35" maxlength="100">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'bankBIKRow'"/>
        <xsl:with-param name="description">Введите Банковский Идентификационный Код. БИК может состоять только из 9 цифр.</xsl:with-param>
        <xsl:with-param name="rowName">БИК</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverBIC" name="receiverBIC" type="text" value="{receiverBIC}" maxlength="9" size="24" onchange="getBankByBIC();">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'bankCorAccountRow'"/>
        <xsl:with-param name="description"><cut/>Укажите корреспондентский счет банка (20 цифр без точек и пробелов)</xsl:with-param>
        <xsl:with-param name="rowName">Корр. счет</xsl:with-param>
        <xsl:with-param name="required" select="false"/>
        <xsl:with-param name="rowValue">
            <input id="receiverCorAccount" name="receiverCorAccount" type="text" value="{receiverCorAccount}" maxlength="20" size="24">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Перевод</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id">fromResource</xsl:with-param>
        <xsl:with-param name="lineId" select="'fromResourceRow'"/>
        <xsl:with-param name="rowName">Счет списания</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:if test="$personAvailable">
                <xsl:call-template name="resources">
                    <xsl:with-param name="name">fromResource</xsl:with-param>
                    <xsl:with-param name="accountNumber" select="fromAccountSelect"/>
                    <xsl:with-param name="linkId" select="fromResource"/>
                    <xsl:with-param name="activeCards" select="$activeCards"/>
                    <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
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
        <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
        <xsl:with-param name="id">sellAmount</xsl:with-param>
        <xsl:with-param name="description">Введите сумму, которую необходимо перевести</xsl:with-param>
        <xsl:with-param name="rowName">Сумма в валюте списания</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="sellAmount" name="sellAmount" type="text" value="{sellAmount}" size="24"
                   onchange="javascript:sellCurrency();" onkeyup="javascript:sellCurrency();" class="moneyField"/>&nbsp;
            <span id="sellAmountCurrency"></span>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
        <xsl:with-param name="id">buyAmount</xsl:with-param>
        <xsl:with-param name="description">Введите сумму, которую необходимо перевести</xsl:with-param>
        <xsl:with-param name="rowName">Сумма в валюте зачисления</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="buyAmount" name="buyAmount" type="text" value="{buyAmount}" size="24"
                   onchange="javascript:buyCurrency();" onkeyup="javascript:buyCurrency();" class="moneyField"/>&nbsp;
            <span id="buyAmountCurrency"></span>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'postConfirmCommissionMessage'"/>
        <xsl:with-param name="rowName">Комиссия</xsl:with-param>
        <xsl:with-param name="rowValue">
            <i><xsl:value-of select="dh:getSettingMessage('commission.prepare.transfer.message')"/></i>
        </xsl:with-param>
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowStyle">display: none</xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'groundRow'"/>
        <xsl:with-param name="description"><cut/>Укажите, с какой целью Вы переводите деньги. Например, благотворительный взнос.</xsl:with-param>
        <xsl:with-param name="rowName">Назначение платежа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <textarea id="ground" name="ground" rows="4" cols="40"><xsl:value-of select="ground"/></textarea>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:if test="pu:impliesService('MessageWithCommentToReceiverService')">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">messageToReceiver</xsl:with-param>
            <xsl:with-param name="lineId">messageToReceiverRow</xsl:with-param>
            <xsl:with-param name="required">false</xsl:with-param>
            <xsl:with-param name="description">Введите сообщение для получателя средств, которое будет направлено ему на мобильный телефон.</xsl:with-param>
            <xsl:with-param name="rowName">Сообщение получателю</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input id="messageToReceiver" name="messageToReceiver" type="text" value="{messageToReceiver}" size="45" maxlength="40"/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <script type="text/javascript" language="JavaScript">

        var EMPTY_ACCOUNT_ERROR = "Вы неправильно указали счет получателя. Пожалуйста, проверьте номер счета.";
        var REGEXP_BIC_MESSAGE = "Вы неправильно указали БИК. Пожалуйста, введите ровно 9 цифр.";
        var BANKS_NOT_FOUND_MESSAGE = "БИК банка получателя не найден в справочнике банков.";

        var lastReceiverSubType = getElementValue("receiverSubType");
        var accounts    = new Array();
        var currencies  = new Array();
        var resourceCurrencies  = new Array();
        var cardAccounts = new Array();
        var accountList = new Array();
        var cardList = new Array();
        var wasShowLink = false;
        var countNotEmptyAcc = 0;
        var indexNotEmptyAcc = 0;
        var countNotEmptyCards = 0;
        var indexNotEmptyCards = 0;

        function init()
        {
            <xsl:for-each select="$activeAccounts">
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                accounts['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="@key"/>';
                resourceCurrencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';

                var account = new Object();
                account.id = '<xsl:value-of select="field[@name='code']/text()"/>';
                account.number = '<xsl:value-of select="au:getShortAccountNumber(./@key)"/>';
                account.name = '[<xsl:value-of select="su:formatStringForJavaScript(./field[@name = 'name'], true())"/>]';
                <xsl:choose>
                    <xsl:when test="./field[@name='amountDecimal'] != ''">
                        account.amount = '<xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>';
                    </xsl:when>
                    <xsl:otherwise>
                        account.amount = '';
                    </xsl:otherwise>
                </xsl:choose>
                account.currency = '<xsl:value-of select="mu:getCurrencySign(./field[@name='currencyCode'])"/>';
                accountList[accountList.length] = account;
                <xsl:if test="field[@name='amountDecimal'] > 0 ">
                    countNotEmptyAcc ++;
                    indexNotEmptyAcc = accountList.length;
                </xsl:if>
                <xsl:if test="field[@name='isUseStoredResource'] = 'true'">
                    addMessage('Информация по Вашим счетам может быть неактуальной.');
                </xsl:if>
            </xsl:for-each>

            <xsl:for-each select="$activeCards">
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                resourceCurrencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                cardAccounts['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='cardAccount']/text()"/>';

                var card = new Object();
                card.id = '<xsl:value-of select="field[@name='code']/text()"/>';
                card.number = '<xsl:value-of select="mask:getCutCardNumber(./@key)"/>';
                card.name = '[<xsl:value-of select="su:formatStringForJavaScript(./field[@name='name'], true())"/>]';
                <xsl:choose>
                    <xsl:when test="./field[@name='amountDecimal'] != ''">
                        card.amount = '<xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>';
                    </xsl:when>
                    <xsl:otherwise>
                        card.amount = '';
                    </xsl:otherwise>
                </xsl:choose>
                card.currency = '<xsl:value-of select="mu:getCurrencySign(./field[@name='currencyCode'])"/>';
                card.type = '<xsl:value-of select="./field[@name='cardType']"/>';
                cardList[cardList.length] = card;
                <xsl:if test="field[@name='isUseStoredResource'] = 'true' ">
                    addMessage('Информация по Вашим картам может быть неактуальной.');
                </xsl:if>
                <xsl:if test="field[@name='amountDecimal'] > 0 ">
                    countNotEmptyCards ++;
                    indexNotEmptyCards = cardList.length;
                </xsl:if>
            </xsl:for-each>

            //массив с разрешенными валютами
            currencies["810"] = "RUB";
            currencies["840"] = "USD";
            currencies["978"] = "EUR";

            refreshForm();

            if (!isEmpty(document.getElementById("sellAmount").value))
            {
                sellCurrency();
            }
            if (!isEmpty(document.getElementById("buyAmount").value))
            {
                buyCurrency();
            }

            var account = getElementValue('receiverAccountInternal');
            if (!isEmpty(account))
            {
                checkAccountCurrency(account);
            }
            else if(!isEmpty(document.getElementById("externalCardNumber").value) &amp;&amp; (lastReceiverSubType == 'ourCard'))
            {
                checkNumber();
            }
            else if(lastReceiverSubType=='ourPhone')
            {
                checkNumber();
            }
            updateFromResource();
            <xsl:choose>
                <xsl:when test="$recType='ourAccount' or $recType='externalAccount'">
                    selectDefaultFromResource(countNotEmptyCards+countNotEmptyAcc, indexNotEmptyAcc > 0 ? indexNotEmptyAcc : accountList.length + indexNotEmptyCards);
                </xsl:when>
                <xsl:otherwise>
                    selectDefaultFromResource(countNotEmptyCards, indexNotEmptyCards);
                </xsl:otherwise>
            </xsl:choose>
        }

        function setBankInfo(bankInfo)
        {
            removeError(REGEXP_BIC_MESSAGE);
            removeError(BANKS_NOT_FOUND_MESSAGE);
            setElement("bank", bankInfo["name"]);
            setElement("receiverBIC", bankInfo["BIC"]);
            setElement("receiverCorAccount", bankInfo["account"]);
        }

        function Payment(isCardToAccount, fromCurrency, toCurrency)
        {
            this.fromCurrency   = fromCurrency;
            this.toCurrency = toCurrency;
            this.isCardToAccount = isCardToAccount;

            this.refresh = function()
            {
                var receiverSubType = ensureElement('receiverSubType');

                //Перевод с карты на карту клиента Сбербанка РФ
                var isPhizCardPaymentType    = (receiverSubType.value == "ourCard");
                //Перевод  на счет в Сбербанке
                var isPhizOurAccountPaymentType = (receiverSubType.value == "ourAccount");
                //Перевод на карту по номеру мобильного телефона
                var isPhizMobileNumberType = (receiverSubType.value == "ourPhone");
                // Карточный перевод
                var isCardTransfer = isPhizCardPaymentType || isPhizMobileNumberType;

                var isConversion    = isConversion(this.fromCurrency, this.toCurrency);   //конверсия, разные валюты

                hideOrShow(ensureElement("externalCardNumberRow"),  !(isPhizCardPaymentType));
                hideOrShow(ensureElement("receiverAccountRow"),     isCardTransfer);
                hideOrShow(ensureElement("receiverSurnameRow"),     isCardTransfer);
                hideOrShow(ensureElement("receiverFirstNameRow"),   isCardTransfer);
                hideOrShow(ensureElement("receiverPatrNameRow"),    isCardTransfer);
                hideOrShow(ensureElement("receiverINNRow"),         isCardTransfer);
                hideOrShow(ensureElement("receiverAddressRow"),     isCardTransfer);
                hideOrShow(ensureElement("bankTitleRow"),           isCardTransfer);
                hideOrShow(ensureElement("bankNameRow"),            isCardTransfer);
                hideOrShow(ensureElement("bankBIKRow"),             isCardTransfer);
                hideOrShow(ensureElement("bankCorAccountRow"),      isCardTransfer);
                hideOrShow(ensureElement("groundRow"),              isCardTransfer);
                hideOrShow(ensureElement("externalPhoneNumberRow"), !isPhizMobileNumberType);
                hideOrShow(ensureElement("messageToReceiverRow"),   !(isPhizCardPaymentType || isPhizMobileNumberType));

                var isBothAmount = (isPhizCardPaymentType || isPhizMobileNumberType)&amp;&amp; isConversion;
                hideOrShow(ensureElement("buyAmountRow"),           !isBothAmount  &amp;&amp; !(isCardToAccount))
                hideOrShow(ensureElement("sellAmountRow"),          !isBothAmount  &amp;&amp; (isCardToAccount));

                if (!isBothAmount  &amp;&amp; !(isCardToAccount))
                {
                    sellCurrency();
                }
                else if (!isBothAmount  &amp;&amp; (isCardToAccount))
                {
                    buyCurrency();
                }

                <!-- меняем значение метки у суммы, для перевода c вклада на счет в сбербанке и на счет в другом банке
                должно отобаражаться метка "Сумма", а также для перевода на карту если валюты совпадают -->
                $("#sellAmountRow .paymentTextLabel").text(isBothAmount ? "Сумма в валюте списания" : "Сумма:");

                $("#sellAmountCurrency").text(currencySignMap.get(this.fromCurrency));
                $("#buyAmountCurrency").text(currencySignMap.get(this.toCurrency));
                getElement('buyAmountCurrency').value = this.toCurrency;

                //показываем/скрываем кнопку "Сохранить как регулярный платеж"
                //ДП доступны для переводов: карта/вклад - физ. лицо, только если счет списания не пуст
                var fromResource = ensureElement('fromResource').value
                if (!<xsl:value-of select="dh:isExternalPhizAccountPaymentsAllowed()"/> &amp;&amp; fromResource.indexOf('account') != -1)
                    hideOrShowMakeLongOfferButton(true);
                else
                    hideOrShowMakeLongOfferButton(!(isPhizOurAccountPaymentType) &amp;&amp;<xsl:value-of select="not(count($activeCards) = 0 and count($activeAccounts) = 0)"/>);

                <xsl:if test="$isTemplate != 'true'">
                    var supportedCommissionPaymentTypes = "<xsl:value-of select="dh:getSupportedCommissionPaymentTypes()"/>".split('|');
                    var paymentType = null;
                    if(receiverSubType.value == "ourAccount" &amp;&amp; fromResource.indexOf('card') != -1)
                        paymentType = "CardToOurBank";

                    if(paymentType!=null &amp;&amp; (resourceCurrencies[fromResource] == 'RUB' || resourceCurrencies[fromResource] == 'RUR'))
                        paymentType = 'Rur' + paymentType;

                    var isSupportedCommissionPaymentType = supportedCommissionPaymentTypes.contains(paymentType);
                    hideOrShow(ensureElement("postConfirmCommissionMessage"), !isSupportedCommissionPaymentType);
                    $("#buyAmountRow .paymentTextLabel").text(isSupportedCommissionPaymentType? "Сумма перевода:": "Сумма в валюте зачисления");
                </xsl:if>

                function isConversion(fromCurrency, toCurrency)
                {
                    if (isEmpty(fromCurrency) || isEmpty(toCurrency))
                        return false;
                    return fromCurrency != toCurrency;
                }
            };
        }

        function changeReceiverSubType(newType)
        {
            if (newType == "ourCard")
            {
                $("#externalCardNumber").removeAttr("disabled")
                $("#receiverAccountInternal").attr("disabled", "disabled");
                $("#externalPhoneNumber").attr("disabled", "disabled");
            }
            else if (newType == "ourAccount")
            {
                $("#externalCardNumber").attr("disabled", "disabled");
                $("#receiverAccountInternal").removeAttr("disabled")
                $("#externalPhoneNumber").attr("disabled", "disabled");
            }
            else if (newType == "ourPhone")
            {
                $("#externalCardNumber").attr("disabled", "disabled");
                $("#receiverAccountInternal").attr("disabled", "disabled");
                $("#externalPhoneNumber").removeAttr("disabled")
            }

            $('#receiverSubType').val(newType);
            $('#receiverSubTypeControl').find('div').removeClass('activeButton');
            $('#' + newType + 'ReceiverSubTypeButton').addClass('activeButton');
            updateFromResource();
            refreshForm();
        }

        var toResource;

        function refreshForm()
        {
            var receiverSubType = getElementValue('receiverSubType');

            var toCurrency;
            var fromResource = getElement("fromResource");
            var fromCurrency = resourceCurrencies[fromResource.value];

            //перевод на карту в сбербанке или через номер мобильного телефона
            if (receiverSubType == "ourCard" || receiverSubType == "ourPhone")
            {
                toResource  = getElement("externalCardNumber");
                toCurrency  = getElementValue("externalCardCurrency");
            }
            else
            {
                toResource  = getElement("receiverAccountInternal");
                toCurrency  = getElementValue("receiverAccountCurrency");
            }

            var isCardToAccount = (fromResource.value.indexOf('card:') != -1) &amp;&amp; (receiverSubType == "ourAccount");

            var payment = new Payment(isCardToAccount, fromCurrency, toCurrency);
            payment.refresh();
        }

        //обновить список ресурсов клиента
        function updateFromResource()
        {
            <!-- функция на равество типов переводов(если типы равны, обновление ресурсов не требуется) -->
            this.equalTypeTransfer = function(receiverSubType1, receiverSubType2)
            {
                 if((receiverSubType1 == 'ourAccount') &amp;&amp; (receiverSubType2 == 'ourAccount'))
                    return true;

                 if((receiverSubType1 == 'ourCard' || receiverSubType1 == 'ourPhone') &amp;&amp; (receiverSubType2 == 'ourCard' || receiverSubType2 == 'ourPhone'))
                    return true;

                 return false;
            };

            var receiverSubType = getElementValue('receiverSubType');

            //обновляем если происходит смена receiverSubType
            //c переводов на карту на переводы на счет  и наоборот
            if(!equalTypeTransfer(lastReceiverSubType, receiverSubType))
            {
                var fromResource = getElement("fromResource");
                var lastSelected = fromResource.options[fromResource.selectedIndex].value;
                fromResource.options.length = 0;
                var index = 0;

                for (var i=0; i &lt; cardList.length; i++)
                {
                    var card = cardList[i];

                    fromResource.options[index++] = createOption(cardList[i]);
                    if (fromResource.options[index - 1].value == lastSelected)
                        fromResource.selectedIndex = index - 1;
                }

                if (receiverSubType == 'ourAccount')
                {
                   for (var i=0; i &lt; accountList.length; i++)
                   {
                       fromResource.options[index++] = createOption(accountList[i]);
                       if (fromResource.options[index - 1].value == lastSelected)
                            fromResource.selectedIndex = index - 1;
                   }
                }

                if(fromResource.length == 0)
                {
                    fromResource.options[0] = new Option(receiverSubType == 'ourCard' || receiverSubType == 'ourPhone'? "Нет доступных карт" : "Нет доступных счетов и карт","");
                }
                else if (lastSelected.indexOf('card:') == -1 &amp;&amp; lastSelected.indexOf('account:') == -1)
                {
                    fromResource.options.add(new Option(receiverSubType == 'ourCard' || receiverSubType == 'ourPhone'? "Выберите карту списания" : "Выберите счет/карту списания",""), 0);
                    fromResource.selectedIndex = 0;
                }
                hideError();
            }

            lastReceiverSubType = receiverSubType;
        }

        function hideError()
        {
            $("#fromResourceRow .paymentValue .errorDiv").hide();
            var fromResourceRow = document.getElementById("fromResourceRow");
            fromResourceRow.error = false;
            fromResourceRow.className = "form-row";
        }

        //запись счета/карты в списке ресурсов клиента
        function createOption(resource)
        {
            var name = resource.number + ' ' + resource.name + ' ' + resource.amount + ' ' + resource.currency;
            return new Option(name, resource.id);
        }

        function buyCurrency()
        {
            var sellAmount  = ensureElement("sellAmount");
            var exactAmount = ensureElement("exactAmount");

            sellAmount.value = "";
            exactAmount.value  = "destination-field-exact";
        }

        function sellCurrency()
        {
            var buyAmount   = ensureElement("buyAmount");
            var exactAmount = ensureElement("exactAmount");

            buyAmount.value = "";
            exactAmount.value  = "charge-off-field-exact";
        }

        var oldExternalNumber = "";
        var externalNumber = "";
        var oldCurrencyMessage = [];

        function checkNumber()
        {
            var subtype = ensureElement('receiverSubType');

            if(subtype.value == "ourPhone")
            {
                externalNumber = getElementValue("externalPhoneNumber");
                //не слать ajax запрос если реально номер не изменился или в поле номера телефона введено некорректно
                if((oldExternalNumber == externalNumber) || !(/^\((\d{3})\) (\d{3}-\d{2}-\d{2})$/.test(externalNumber)))
                {
                    oldExternalNumber = externalNumber;
                    return;
                }
                oldExternalNumber = externalNumber;
                getElement("externalPhoneNumber").value = externalNumber;

                checkCurrency("field(type)=phone&amp;field(phoneNumber)=" + externalNumber)
            }
            else if(subtype.value =="ourCard")
            {
                externalNumber = getElementValue("externalCardNumber");
                externalNumber = externalNumber.replace(/ /g,'');
                //не слать ajax запрос если реально номер не изменился или в поле номера карты введено не 16 или 18 символов
                if ((oldExternalNumber == externalNumber) || (!(externalNumber.length == 16 || externalNumber.length == 18 || externalNumber.length == 15 || externalNumber.length == 19)))
                {
                     oldExternalNumber = externalNumber;
                     return;
                }
                oldExternalNumber = externalNumber;
                getElement("externalCardNumber").value = externalNumber;

                checkCurrency("field(type)=card&amp;field(cardNumber)=" + externalNumber)
            }
        }

        function checkCurrency(params)
        {
            var url = document.webRoot + "/private/cards/currency/code.do";
            ajaxQuery(params, url, updateReceiverCardCurency, null, true);

            function updateReceiverCardCurency(currencyData)
            {
                if (trim(currencyData) == '')
                {
                    return false;
                }
                var htmlData = trim(currencyData).replace(/^&nbsp;+/, "");
                $("#currencyErrorDiv").html(htmlData);
                for (var i=0; i &lt; oldCurrencyMessage.length; i++)
                {
                    removeError(oldCurrencyMessage[i]);
                }
                for (var i=0; i &lt; currencyErrorMessageAr.length; i++)
                {
                    addError(currencyErrorMessageAr[i]);
                }
                oldCurrencyMessage = currencyErrorMessageAr;

                //обработка данных о валюте
                var currencyVal = $("#currency").val();
                var currency = getElement("externalCardCurrency");
                if (currencyErrorMessageAr.length > 0)   //пришла ошибка с описанием
                {
                    ensureElement("isErrorCurrency").value = "true";
                    currency.value = "";
                }
                else
                {
                    ensureElement("isErrorCurrency").value = "false";
                    currency.value = currencyVal;
                }
                refreshForm();
                return true;
            }
        }

        function checkAccountCurrency(account)
        {
            if (account.length &lt; 19)
                return;

            var accountCurrency = getElement("receiverAccountCurrency");
            var currency = currencies[account.substring(5, 8)];

            if (isEmpty(currency))
            {
                addError(EMPTY_ACCOUNT_ERROR);
                accountCurrency.value = "";
            }
            else
            {
                removeError(EMPTY_ACCOUNT_ERROR);
                accountCurrency.value = currency;
            }

            refreshForm();
        }

        function getBankByBIC()
        {
            var receiverBIC = getFieldValue("receiverBIC");
            ensureElement("bank").value = "";
            ensureElement("receiverCorAccount").value = "";
            if (!(/^\d{9}$/.test(receiverBIC)))
            {
                addError(REGEXP_BIC_MESSAGE);
                removeError(BANKS_NOT_FOUND_MESSAGE);
                return;
            }

            var url = document.webRoot + "/private/dictionary/banks/national.do";
            var params = "operation=button.getByBIC" + "&amp;field(BIC)=" + receiverBIC;

            ajaxQuery(params, url, updateBankInfo, "json");
        }

        function updateBankInfo(bank)
        {
            removeError(REGEXP_BIC_MESSAGE);
            if (isEmpty(bank))
            {
                addError(BANKS_NOT_FOUND_MESSAGE);
                return;
            }
            removeError(BANKS_NOT_FOUND_MESSAGE);

            ensureElement("bank").value = bank.name;
            ensureElement("receiverCorAccount").value = bank.account;
        }

        function selectOurCard()
        {
            ensureElement('receiverSubType').value = "ourCard";
            updateFromResource();
            refreshForm();
        }

        doOnLoad(function()
        {
            init();
        });

    </script>

</xsl:template>

<xsl:template match="/form-data" mode="edit-long-offer">

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Номер документа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="documentNumber" value="{documentNumber}"/>
            <b><xsl:value-of  select="documentNumber"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Дата документа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="documentDate" value="{documentDate}"/>
            <b><xsl:value-of  select="documentDate"/></b>
        </xsl:with-param>
    </xsl:call-template>

     <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Получатель</b></xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="receiverSubType" select="receiverSubType"/>

    <input type="hidden" name="receiverSubType" value="{receiverSubType}"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Номер счета</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="receiverAccountInternal" value="{receiverAccountInternal}"/>
            <input type="hidden" name="buyAmountCurrency" value="{buyAmountCurrency}"/>
            <b><xsl:value-of  select="receiverAccountInternal"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Фамилия</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="receiverSurname" value="{receiverSurname}"/>
            <b><xsl:value-of  select="substring(receiverSurname, 1, 1)"/>.</b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Имя</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="receiverFirstName" value="{receiverFirstName}"/>
            <b><xsl:value-of  select="receiverFirstName"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Отчество</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="receiverPatrName" value="{receiverPatrName}"/>
            <b><xsl:value-of  select="receiverPatrName"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:if test="$receiverSubType != 'ourCard'">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">ИНН</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="receiverINN" value="{receiverINN}"/>
                <b><xsl:value-of  select="receiverINN"/></b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Адрес</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="receiverAddress" value="{receiverAddress}"/>
            <b><xsl:value-of  select="receiverAddress"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Банк получателя</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Наименование</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="bank" value="{bank}"/>
            <b><xsl:value-of  select="bank"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">БИК</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="receiverBIC" value="{receiverBIC}"/>
            <b><xsl:value-of  select="receiverBIC"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Корр. счет</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="receiverCorAccount" value="{receiverCorAccount}"/>
            <b><xsl:value-of  select="receiverCorAccount"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Перевод</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Назначение платежа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="ground" value="{ground}"/>
            <b><xsl:value-of select="ground"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="accounts" select="document('stored-active-debit-allowed-external-phiz-accounts.xml')/entity-list/*"/>
    <xsl:variable name="cards" select="document('stored-active-not-virtual-cards.xml')/entity-list/*"/>

    <xsl:variable name="fromResource" select="fromResource"/>

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
        <xsl:with-param name="rowName">Счет списания</xsl:with-param>
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
                    <xsl:value-of select="mu:getCurrencySign($fromResourceCurrency)"/>
                </span>
            </b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="sumTypes" select="document('long-offer-sum-types.xml')/entity-list/entity"/>

    <xsl:if test="(exactAmount = 'charge-off-field-exact' and string-length(sellAmount)>0) or (exactAmount != 'destination-field-exact')">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
            <xsl:with-param name="rowName">Сумма</xsl:with-param>
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
                                    <xsl:value-of select="mu:getCurrencySign($fromResourceCurrency)"/>
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
                            <xsl:value-of select="mu:getCurrencySign($fromResourceCurrency)"/>
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
            <xsl:with-param name="rowName">Сумма в валюте зачисления</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="isSumModify = 'true'">
                    <b><xsl:value-of select="$sumTypes[@key=longOfferSumType]"/></b>&nbsp;
                    <xsl:choose>
                        <xsl:when test="longOfferSumType = 'FIXED_SUMMA_IN_RECIP_CURR' or longOfferSumType = 'REMAIND_IN_RECIP'">
                            <input type="hidden" name="buyAmount" value="{buyAmount}"/>
                            <xsl:if test="string-length(buyAmount)>0">
                                <span class="summ">
                                    <xsl:value-of select="format-number(translate(buyAmount, ', ','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                    <xsl:value-of select="mu:getCurrencySign(buyAmountCurrency)"/>
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
                            <xsl:value-of select="mu:getCurrencySign(buyAmountCurrency)"/>
                        </span>
                    </xsl:if>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:if test="$isTemplate != 'true'">
        <xsl:if test="$mode='view' and not($postConfirmCommission)">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Статус платежа</xsl:with-param>
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
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowStyle">display: none</xsl:with-param>
            <xsl:with-param name="rowName">Плановая дата исполнения</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="admissionDate" name="admissionDate" value="{admissionDate}"/>
                <xsl:value-of  select="admissionDate"/>
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
            <xsl:when test="contains(firstPaymentDate, '-')">
                <xsl:copy-of select="concat(substring(firstPaymentDate, 9, 2), '.', substring(firstPaymentDate, 6, 2), '.', substring(firstPaymentDate, 1, 4))"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="firstPaymentDate"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>
    <xsl:variable name="month"  select="substring($firstPaymentDate, 4, 2)"/>

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
                       onchange="javascript:refreshReport(getFormEvent(getElementValue('longOfferEventSelect')));"
                       onkeyup="javascript:refreshReport(getFormEvent(getElementValue('longOfferEventSelect')));"
                   />
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

    <xsl:variable name="eventTypes" select="document('long-offer-events-types.xml')/entity-list/entity"/>
    <xsl:variable name="months" select="document('months.xml')/entity-list/entity"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Повторяется</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="longOfferSumType"    value="{longOfferSumType}"/>
            <input type="hidden" name="longOfferEventType"  value="{longOfferEventType}"/>

            <xsl:variable name="eventType"  select="longOfferEventType"/>

            <select id="longOfferEventSelect" name="longOfferEventSelect"
                    onchange="javascript:refreshForm(getElementValue('longOfferEventSelect'));"
                    onkeyup="javascript:refreshForm(getElementValue('longOfferEventSelect'));"
                />
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId">everyMonthRow</xsl:with-param>
        <xsl:with-param name="rowName">Число месяца</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="text" name="ONCE_IN_MONTH_DAY" value="{longOfferPayDay}" size="1" maxlength="2"
                   onchange="javascript:refreshReport(getElementValue('longOfferEventSelect'));"
                   onkeyup="javascript:refreshReport(getElementValue('longOfferEventSelect'));"/>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId">everyQuarterRow</xsl:with-param>
        <xsl:with-param name="rowName">Дата оплаты</xsl:with-param>
        <xsl:with-param name="rowValue">
            <nobr>
                месяц&nbsp;
                <select name="ONCE_IN_QUARTER_MONTHS"
                        onchange="javascript:refreshReport(getElementValue('longOfferEventSelect'));"
                        onkeyup="javascript:refreshReport(getElementValue('longOfferEventSelect'));"
                    >

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
                       onchange="javascript:refreshReport(getElementValue('longOfferEventSelect'));"
                       onkeyup="javascript:refreshReport(getElementValue('longOfferEventSelect'));"/>
            </nobr>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId">halfYearRow</xsl:with-param>
        <xsl:with-param name="rowName">Дата оплаты</xsl:with-param>
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
                       onchange="javascript:refreshReport(getElementValue('longOfferEventSelect'));"
                       onkeyup="javascript:refreshReport(getElementValue('longOfferEventSelect'));"/>
            </nobr>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId">everyYearRow</xsl:with-param>
        <xsl:with-param name="rowName">Дата оплаты</xsl:with-param>
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
                       onchange="javascript:refreshReport(getElementValue('longOfferEventSelect'));"
                       onkeyup="javascript:refreshReport(getElementValue('longOfferEventSelect'));"/>
            </nobr>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId">onEventSelectRow</xsl:with-param>
        <xsl:with-param name="rowName">Событие</xsl:with-param>
        <xsl:with-param name="rowValue">
            <select name="onEventSelect" style="width: 250px;"
                    onchange="javascript:refreshSumType(getElementValue('onEventSelect'), getElementValue('longOfferSumType'));"
                    onkeyup="javascript:refreshSumType(getElementValue('onEventSelect'), getElementValue('longOfferSumType'));"
                />
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
        <xsl:with-param name="rowName">Ближайший платеж</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><div id="firstPaymentDate"></div></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="lineId">paymentSumChange</xsl:with-param>
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
            <xsl:with-param name="rowName">Тип суммы</xsl:with-param>
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
                <span id="simpleSumm">Сумма</span>
                <span id="remaindOverSumm">Сумма остатка</span>
            </xsl:with-param>
            <xsl:with-param name="rowValue">
                <span id="sellAmountBlock">
                    <input type="text" name="sellAmount" value="{sellAmount}"
                           size="10" maxlength="10" class="moneyField"/>
                    &nbsp;
                    <span  id="sellAmountCurrency">
                        <xsl:value-of select="mu:getCurrencySign($fromResourceCurrency)"/>
                    </span>
                </span>
                <span id="buyAmountBlock">
                    <input type="text" name="buyAmount"  value="{buyAmount}"
                           size="10" maxlength="10" class="moneyField"/>
                    &nbsp;
                    <span id="buyAmountCurrency">
                        <xsl:value-of select="mu:getCurrencySign(buyAmountCurrency)"/>
                    </span>
                </span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">longOfferPercentRow</xsl:with-param>
            <xsl:with-param name="rowName">Процент</xsl:with-param>
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
                var receiverSubType = getElementValue("receiverSubType");
                var fromResource    = getElementValue("fromResource");

                //заполняем название поля с датой, чтоб правильно выводить ошибку
                if (!isPeriodic(event))
                    nameFieldDate = '';
                else
                    nameFieldDate = event + '_DAY';

                var exactAmount = getElementValue("exactAmount");
                var isSumModify = getElementValue("isSumModify") == "true";

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

                var longOfferEventSelect = getElement("longOfferEventSelect");
                var longOfferEventLength = 0;
                longOfferEventSelect.options.length = longOfferEventLength;

                //перезаполняем селект "Повторяется"
                //для переводов карта - юр. лицо; карта/вклад - физ. лицо (счет в нашем банке/внешний счет)
                if (receiverSubType == "ourAccount" || receiverSubType == "externalAccount")
                {
                    longOfferEventSelect.options[longOfferEventLength++] = new Option(eventTypes["ONCE_IN_MONTH"],  "ONCE_IN_MONTH");
                    longOfferEventSelect.options[longOfferEventLength++] = new Option(eventTypes["ONCE_IN_QUARTER"],    "ONCE_IN_QUARTER");
                    longOfferEventSelect.options[longOfferEventLength++] = new Option(eventTypes["ONCE_IN_HALFYEAR"],   "ONCE_IN_HALFYEAR");
                    longOfferEventSelect.options[longOfferEventLength++] = new Option(eventTypes["ONCE_IN_YEAR"],   "ONCE_IN_YEAR");
                }

                //перевод частному лицу в Сбербанк или другой ком. банк
                if (receiverSubType == "ourAccount" || receiverSubType == "externalAccount")
                    longOfferEventSelect.options[longOfferEventLength++] = new Option(eventTypes["ON_EVENT"],   "ON_EVENT");

                for (var i=0; i < longOfferEventSelect.length; i++)
                {
                    if (longOfferEventSelect[i].value == event)
                        longOfferEventSelect[i].selected = true;
                    if (!isPeriodic(event))
                        longOfferEventSelect[i].selected = true;
                }

                //перезаполняем селект "Событие"
                //перевод частному лицу в Сбербанк или другой ком. банк
                if (!isPeriodic(event) && (receiverSubType == "ourAccount" || receiverSubType == "externalAccount"))
                {
                    var onEventSelectLength = 0;
                    var onEventSelect = getElement("onEventSelect");
                    onEventSelect.options.length = onEventSelectLength;

                    //для переводов (вклад-вклад) (карта-вклад)
                    onEventSelect.options[onEventSelectLength++] = new Option(eventTypes["BY_ANY_RECEIPT"],    "BY_ANY_RECEIPT");
                    onEventSelect.options[onEventSelectLength++] = new Option(eventTypes["ON_REMAIND"], "ON_REMAIND");

                    if (receiverSubType == "ourAccount" && getElementValue("receiverAccountInternal").indexOf("40817") != -1)
                        onEventSelect.options[onEventSelectLength++] = new Option(eventTypes["ON_OVER_DRAFT"], "ON_OVER_DRAFT");

                    //для переводов (вклад - вклад)
                    if (fromResource.indexOf('account:') != -1)
                    {
                        onEventSelect.options[onEventSelectLength++] = new Option(eventTypes["BY_CAPITAL"],    "BY_CAPITAL");
                        onEventSelect.options[onEventSelectLength++] = new Option(eventTypes["BY_PERCENT"],    "BY_PERCENT");
                    }
                    //для переводов (карта - вклад)
                    if (fromResource.indexOf('card:') != -1)
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
                refreshReport(event);
                refreshPayTypeBlock(getElementValue("longOfferSumSelect"));
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

                var receiverSubType = getElementValue("receiverSubType");
                var fromResource = getElementValue("fromResource");

                //для периодических платежей
                if (isPeriodic(event))
                {
                    //карта - вклад-физ. лицо (счет в нашем банке/внешний счет)
                    if (receiverSubType == "ourAccount" || receiverSubType == "externalAccount")
                    {
                        //если платеж карта-вклад, то нельзя выбрать Фиксированную сумму в валюте списания
                        if (fromResource.indexOf('card:') == -1)
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["FIXED_SUMMA"],  "FIXED_SUMMA");

                        //если валюта счета списания и валюта счета зачисления различны, либо перевод с карты на счет
                        if ($("#sellAmountCurrency").text() != $("#buyAmountCurrency").text() || (fromResource.indexOf('card:') != -1))
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["FIXED_SUMMA_IN_RECIP_CURR"],    "FIXED_SUMMA_IN_RECIP_CURR");

                        sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["PERCENT_OF_REMAIND"],   "PERCENT_OF_REMAIND");
                        //для внутрибанковских переводов
                        if (receiverSubType == "ourAccount")
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["REMAIND_IN_RECIP"], "REMAIND_IN_RECIP");
                        sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["SUMMA_OF_RECEIPT"], "SUMMA_OF_RECEIPT");

                        //для перевода вклад-вклад
                        if (fromResource.indexOf('account:') != -1)
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["REMAIND_OVER_SUMMA"],   "REMAIND_OVER_SUMMA");
                    }
                }

                //для внутрибанковских/межбанковских переводов
                if ((receiverSubType == "ourAccount") || (receiverSubType == "externalAccount"))
                {
                    if (event == "BY_ANY_RECEIPT")
                    {
                        //если платеж карта-вклад, то нельзя выбрать Фиксированную сумму в валюте списания
                        if (fromResource.indexOf('card:') == -1)
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["FIXED_SUMMA"],  "FIXED_SUMMA");

                        //если валюта счета списания и валюта счета зачисления различны, либо перевод с карты на счет
                        if ($("#sellAmountCurrency").text() != $("#buyAmountCurrency").text() || (fromResource.indexOf('card:') != -1))
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["FIXED_SUMMA_IN_RECIP_CURR"],    "FIXED_SUMMA_IN_RECIP_CURR");

                        sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["PERCENT_OF_REMAIND"],   "PERCENT_OF_REMAIND");
                        //для внутрибанковских переводов
                        if (receiverSubType == "ourAccount")
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["REMAIND_IN_RECIP"], "REMAIND_IN_RECIP");
                        sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["SUMMA_OF_RECEIPT"], "SUMMA_OF_RECEIPT");

                        //для перевода вклад-вклад
                        if (fromResource.indexOf('account:') != -1)
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["REMAIND_OVER_SUMMA"],   "REMAIND_OVER_SUMMA");
                    }

                    if (event == "BY_PENSION" || event == "BY_SALARY")
                    {
                        //для переводов с карты
                        if (fromResource.indexOf('card:') != -1)
                        {
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["FIXED_SUMMA_IN_RECIP_CURR"],    "FIXED_SUMMA_IN_RECIP_CURR");
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["PERCENT_OF_REMAIND"],   "PERCENT_OF_REMAIND");
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["SUMMA_OF_RECEIPT"], "SUMMA_OF_RECEIPT");
                            //для внутрибанковских переводов
                            if (receiverSubType == "ourAccount")
                            {
                                sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["REMAIND_IN_RECIP"], "REMAIND_IN_RECIP");
                                sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["REMAIND_OVER_SUMMA"],   "REMAIND_OVER_SUMMA");
                            }
                        }
                    }

                    if (event == "ON_REMAIND")
                    {
                        sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["REMAIND_OVER_SUMMA"],   "REMAIND_OVER_SUMMA");

                        $("#simpleSumm").hide();
                        $("#remaindOverSumm").show();
                        $('#paymentSumChange').click();
                    }

                    if (event == "BY_PERCENT")
                    {
                        //для переводов со счета
                        if (fromResource.indexOf('account:') != -1)
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["SUMMA_OF_RECEIPT"], "SUMMA_OF_RECEIPT");
                    }

                    //только для внутрибанковских переводов
                    if (event == "ON_OVER_DRAFT" && receiverSubType == "ourAccount")
                    {
                        //для переводов на счет, который начинается на 40817
                        if (getElementValue("receiverAccountInternal").indexOf("40817") != -1)
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["OVER_DRAFT"],   "OVER_DRAFT");
                    }

                    if (event == "BY_CAPITAL")
                    {
                        //данные типы только для перевода вклад-вклад
                        if (fromResource.indexOf('account:') != -1)
                            sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["PERCENT_OF_CAPITAL"],   "PERCENT_OF_CAPITAL");
                    }
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
                var isSellAmount    = (type == "FIXED_SUMMA" || type == "REMAIND_OVER_SUMMA");
                var isBuyAmount = (type == "FIXED_SUMMA_IN_RECIP_CURR" || type == "REMAIND_IN_RECIP");
                var isPercent   = (type == "PERCENT_OF_REMAIND");

                hideOrShow(ensureElement("longOfferAmountRow"), !(isSellAmount || isBuyAmount));
                hideOrShow(ensureElement("longOfferPercentRow"),    !isPercent);
                hideOrShow(ensureElement("sellAmountBlock"), !isSellAmount);
                hideOrShow(ensureElement("buyAmountBlock"),  !isBuyAmount);

                var sellAmount  = getElement("sellAmount");
                var buyAmount   = getElement("buyAmount");
                var exactAmount = getElement("exactAmount")
                var percent = getElement("longOfferPercent");

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

    <xsl:variable name="receiverSubType" select="receiverSubType"/>
    <xsl:variable name="isReceiverCardSubType" select="receiverSubType='ourCard' or receiverSubType='ourPhone' or receiverSubType = 'visaExternalCard' or receiverSubType = 'masterCardExternalCard'"/>
    <xsl:variable name="isReceiverInternalCardSubType" select="receiverSubType='ourCard' or receiverSubType='ourPhone'"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Номер документа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of  select="documentNumber"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Дата документа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of  select="documentDate"/></b>
        </xsl:with-param>
    </xsl:call-template>

     <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Получатель</b></xsl:with-param>
    </xsl:call-template>

    <xsl:if test="$isReceiverCardSubType">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Номер карты получателя</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="mask:getCutCardNumber(receiverAccount)"/></b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:if test="$receiverSubType = 'ourPhone'">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Номер телефона</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>+7 <xsl:value-of  select="externalPhoneNumber"/></b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:if test="not($isReceiverCardSubType) ">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Номер счета</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="au:getFormattedAccountNumber(receiverAccount)"/></b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>
    <xsl:if test="not(receiverSubType = 'visaExternalCard' or receiverSubType = 'masterCardExternalCard') or isOurBankCard = 'true'">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">ФИО</xsl:with-param>
            <xsl:with-param name="rowValue">
                <!-- BUG039967 -->
                <!-- <b>
                <xsl:choose>
                    <xsl:when test="$isReceiverCardSubType=false">
                       <xsl:value-of  select="ph:getFormattedPersonName(receiverFirstName, receiverSurname, receiverPatrName)" disable-output-escaping="no"/>
                    </xsl:when>
                    <xsl:otherwise>
                       <xsl:value-of  select="ph:getExtraFormattedPersonName(receiverFirstName, receiverSurname, receiverPatrName)" disable-output-escaping="no"/>
                    </xsl:otherwise>
                </xsl:choose>
                </b> -->
                <b><xsl:value-of  select="ph:getFormattedPersonName(receiverFirstName, receiverSurname, receiverPatrName)" disable-output-escaping="no"/></b>
                <!-- ..... -->
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>


    <xsl:if test="not($isReceiverCardSubType)">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">ИНН</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="receiverINN"/></b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:if test="not($isReceiverCardSubType)">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Адрес</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="receiverAddress"/></b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:if test="not($isReceiverCardSubType)">
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Банк получателя</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Наименование</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="bank"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">БИК</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="receiverBIC"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Корр. счет</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="receiverCorAccount"/></b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Перевод</b></xsl:with-param>
    </xsl:call-template>

    <xsl:if test="not($isReceiverCardSubType)">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Назначение платежа</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="ground"/></b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Счет списания</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:if test="not($fromAccountSelect= '')">
                <b>
                    <xsl:choose>
                        <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                            <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                        </xsl:when>
                        <xsl:otherwise><xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/></xsl:otherwise>
                    </xsl:choose>
                    &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                    <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
                </b>
            </xsl:if>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="longOfferSumType"   select="longOfferSumType"/>
    <xsl:variable name="sumTypes" select="document('long-offer-sum-types.xml')/entity-list/entity"/>
    <xsl:variable name="isFullNameAmount" select="$isReceiverCardSubType and fromResourceCurrency != buyAmountCurrency"/>

    <xsl:choose>
        <xsl:when test="$postConfirmCommission">
            <xsl:call-template name="transferSumRows">
                <xsl:with-param name="fromResourceCurrency" select="fromResourceCurrency"/>
                <xsl:with-param name="toResourceCurrency" select="buyAmountCurrency"/>
                <xsl:with-param name="chargeOffAmount" select="sellAmount"/>
                <xsl:with-param name="destinationAmount" select="buyAmount"/>
                <xsl:with-param name="documentState" select="state"/>
                <xsl:with-param name="exactAmount" select="exactAmount"/>
                <xsl:with-param name="needUseTotalRow" select="'false'"/>
                <xsl:with-param name="tariffPlanESB" select="tariffPlanESB"/>
            </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
            <xsl:if test="(exactAmount = 'charge-off-field-exact') or (exactAmount != 'destination-field-exact')">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
                    <xsl:with-param name="rowName">
                         Сумма<xsl:if test="$isFullNameAmount"> в валюте списания</xsl:if>
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
                                        <xsl:value-of select="format-number(translate(sellAmount, ',','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                        <xsl:value-of select="mu:getCurrencySign(sellAmountCurrency)"/>
                                    </span>
                                </xsl:if>
                            </xsl:if>
                        </xsl:if>
                        <xsl:if test="not ($longOffer) or ($longOffer and isSumModify != 'true')">
                            <xsl:if test="string-length(sellAmount)>0">
                                <span class="summ">
                                    <xsl:value-of select="format-number(translate(sellAmount, ',','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                    <xsl:value-of select="mu:getCurrencySign(sellAmountCurrency)"/>
                                </span>
                            </xsl:if>
                        </xsl:if>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>

            <xsl:if test="exactAmount = 'destination-field-exact'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
                    <xsl:with-param name="rowName">Сумма<xsl:if test="not(receiverSubType = 'visaExternalCard' or receiverSubType = 'masterCardExternalCard')"> в валюте зачисления</xsl:if></xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:if test="$longOffer and (isSumModify = 'true')">
                            <b><xsl:value-of select="$sumTypes[@key=$longOfferSumType]"/></b>&nbsp;
                        </xsl:if>
                        <xsl:if test="string-length(buyAmount)>0">
                            <span class="summ">
                                <xsl:value-of select="format-number(translate(buyAmount, ',','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                <xsl:value-of select="mu:getCurrencySign(buyAmountCurrency)"/>
                            </span>
                        </xsl:if>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>

            <xsl:if test="string-length(commission)>0 and $isTemplate!='true'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Комиссия</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <span name="commission" class="bold">
                            <b><xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(commissionCurrency)"/></b>
                        </span>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
        </xsl:otherwise>
    </xsl:choose>
    <xsl:if test="$isReceiverInternalCardSubType or isOurBankCard = 'true'">
        <xsl:if test="string-length(messageToReceiver) > 0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">messageToReceiverRow</xsl:with-param>
                <xsl:with-param name="rowName">Сообщение получателю</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="messageToReceiver"/></b>&nbsp;<img src="{concat($resourceRoot, '/images/ERMB_sms.png')}" alt="sms"/>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:if test="(state = 'EXECUTED' or state = 'DISPATCHED' or state = 'UNKNOW')">
                <xsl:variable name="existMessageToReceiverStatus" select="string-length(messageToReceiverStatus) > 0"/>
                <xsl:variable name="sentMessageToReceiverStatus" select="$existMessageToReceiverStatus and messageToReceiverStatus = 'sent'"/>
                <xsl:variable name="notSentMessageToReceiverStatus" select="$existMessageToReceiverStatus and messageToReceiverStatus = 'not_sent'"/>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">messageToReceiverStatusRow</xsl:with-param>
                    <xsl:with-param name="rowName">Статус SMS-сообщения</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:choose>
                            <xsl:when test="$sentMessageToReceiverStatus">
                                <div id="messageToReceiverStatus">
                                    <span class="messageToReceiverStatus"
                                          onmouseover="showLayer('messageToReceiverStatus','messageToReceiverStatusDescription');"
                                          onmouseout="hideLayer('messageToReceiverStatusDescription');">
                                        Сообщение отправлено
                                    </span>
                                </div>
                            </xsl:when>
                            <xsl:when test="$notSentMessageToReceiverStatus">
                                <div id="messageToReceiverStatus">
                                    <span class="messageToReceiverStatus"
                                          onmouseover="showLayer('messageToReceiverStatus','messageToReceiverStatusDescription');"
                                          onmouseout="hideLayer('messageToReceiverStatusDescription');">
                                        Сообщение не отправлено
                                    </span>
                                </div>
                            </xsl:when>
                            <xsl:when test="$existMessageToReceiverStatus">
                                <xsl:value-of select="messageToReceiverStatus"/>
                            </xsl:when>
                            <xsl:otherwise>сообщение будет отправлено</xsl:otherwise>
                        </xsl:choose>
                    </xsl:with-param>
                </xsl:call-template>
                <div id="messageToReceiverStatusDescription"
                     onmouseover="showLayer('messageToReceiverStatus','messageToReceiverStatusDescription','default');"
                     onmouseout="hideLayer('messageToReceiverStatusDescription');" class="layerFon stateDescription">
                    <div class="floatMessageHeader"></div>
                    <div class="layerFonBlock">
                        <xsl:choose>
                            <xsl:when test="$sentMessageToReceiverStatus">
                                Сообщение успешно отправлено получателю средств.
                            </xsl:when>
                            <xsl:when test="$notSentMessageToReceiverStatus">
                                Сообщение не удалось отправить получателю средств.
                            </xsl:when>
                        </xsl:choose>
                    </div>
                </div>
            </xsl:if>
        </xsl:if>
    </xsl:if>

    <xsl:if test="not($postConfirmCommission and state = 'SAVED')">
     <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">
            Статус
            <xsl:choose>
                <xsl:when test="$isTemplate != 'true'">
                    платежа
                </xsl:when>
                <xsl:otherwise>
                    шаблона
                </xsl:otherwise>
            </xsl:choose>
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
    </xsl:if>

    <xsl:if test="$isTemplate != 'true'">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowStyle">display: none</xsl:with-param>
            <xsl:with-param name="rowName">Плановая дата исполнения</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="admissionDate"/>
            </xsl:with-param>
        </xsl:call-template>
     </xsl:if>

</xsl:template>

<xsl:template match="/form-data" mode="view-long-offer">

    <xsl:apply-templates select="/form-data" mode="view-simple-payment"/>

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

    <xsl:variable name="event"  select="longOfferEventType"/>
    <xsl:variable name="months" select="document('months.xml')/entity-list/entity"/>
    <xsl:variable name="eventTypes" select="document('long-offer-events-types.xml')/entity-list/entity[@key=$event]/text()"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Повторяется</xsl:with-param>
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
            <xsl:with-param name="rowName">Дата оплаты</xsl:with-param>
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
                        &nbsp;<xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
                    </xsl:otherwise>
                </xsl:choose>
            </b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Ближайший платеж</xsl:with-param>
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
        <xsl:when test="$code='WAIT_CLIENT_MESSAGE'">Введен (статус для клиента: "Черновик")</xsl:when>
        <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
        <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
        <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
        <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
        <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
        <xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
        <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")<xsl:if test="checkStatusCountResult = 'true'"> (Превышение количества проверок статуса)</xsl:if></xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM'">Ожидает дополнительной обработки (статус для клиента: "Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if>")</xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">Активный</xsl:when>
        <xsl:when test="$code='TEMPLATE'">Сверхлимитный</xsl:when>
        <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
        <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
        <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
        <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
    </xsl:choose>
</xsl:template>

<xsl:template name="clientState2text">
    <xsl:param name="code"/>
    <xsl:choose>
        <xsl:when test="$code='SAVED'">Черновик</xsl:when>
        <xsl:when test="$code='WAIT_CLIENT_MESSAGE'">Черновик</xsl:when>
        <xsl:when test="$code='INITIAL'">Черновик</xsl:when>
        <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
        <xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
        <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
        <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
        <xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
        <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM'">Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if></xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">Активный</xsl:when>
        <xsl:when test="$code='TEMPLATE'">Сверхлимитный</xsl:when>
        <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
        <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
        <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
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
    <!-- Необязательный параметр -->
	<xsl:param name="fieldName"/>                   <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->
    <xsl:param name="isAllocate" select="'true'"/>  <!-- Выделить при нажатии -->

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
            <xsl:if test="string-length($rowName) > 0">
                <xsl:copy-of select="$rowName"/><xsl:if test="not(contains($rowName,':'))">:</xsl:if>
            </xsl:if>
	    </span>
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
