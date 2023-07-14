<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
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
<xsl:param name="longOffer" select="false()"/>
<xsl:param name="personAvailable" select="true()"/>
<xsl:param name="byTemplate" select="'byTemplate'"/>
<xsl:param name="isTemplate" select="'isTemplate'"/>
<xsl:param name="receiverInfo" select="'receiverInfo'"/>

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
        <xsl:param name="accounts"/>
        <xsl:param name="isDepoDebtPayment"/>

        <select id="{$name}" name="{$name}" onchange="refreshForm();">
            <xsl:choose>
                <xsl:when test="$isTemplate = 'true'">
                    <option value="">Не задан</option>
                </xsl:when>
                <xsl:when test="count($accounts) = 0">
                    <option value="">Нет доступных счетов</option>
                </xsl:when>
                <xsl:when test="string-length($accountNumber) = 0 and string-length($linkId) = 0">
                    <option value="">Выберите счет списания</option>
                </xsl:when>
            </xsl:choose>
            <xsl:if test="not($isDepoDebtPayment)">
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

<xsl:template match="/form-data" mode="edit-simple-payment">
    <input type="hidden" name="depoLinkId" value="{depoLinkId}"/>
    <xsl:variable name="activeAccounts" select="document('stored-active-debit-rur-allowed-external-jur-accounts.xml')/entity-list/*"/>
    <xsl:variable name="isDepoDebtPayment" select="not(depoLinkId = '')"/>
    <xsl:variable name="taxPayment" select="taxPayment"/>
    <input type="hidden" id="taxPayment" name="taxPayment" value="false"/>

    <script type="text/javascript" language="JavaScript">
        document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';
        $(document).ready(function(){hideOrShowMakeLongOfferButton(true)});
    </script>

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

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Получатель</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id" select="'receiverName'"/>
        <xsl:with-param name="lineId" select="'receiverNameRow'"/>
        <xsl:with-param name="description"><xsl:if test="not($isDepoDebtPayment)"><cun/>Введите наименование получателя перевода.</xsl:if></xsl:with-param>
        <xsl:with-param name="rowName">Наименование:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:choose>
                <xsl:when test="$isDepoDebtPayment">
                    <input type="hidden" id="receiverName" name="receiverName" value="{receiverName}"/>
                    <b><xsl:value-of select="receiverName"/></b>
                </xsl:when>
                <xsl:otherwise>
                    <input id="receiverName" name="receiverName" type="text" value="{receiverName}" size="40" maxlength="100"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id" select="'receiverAccount'"/>
        <xsl:with-param name="lineId" select="'receiverAccountRow'"/>
        <xsl:with-param name="rowName">Номер счета:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="buyAmountCurrency"  value="{buyAmountCurrency}"/>
            <input type="hidden" id="receiverAccount" name="receiverAccount" value="{receiverAccount}"/>
            <b><xsl:value-of select="au:getFormattedAccountNumber(receiverAccount)"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id" select="'receiverINN'"/>
        <xsl:with-param name="lineId" select="'receiverINNRow'"/>
        <xsl:with-param name="rowName">ИНН:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" id="receiverINN" name="receiverINN" value="{receiverINN}"/>
            <b><xsl:value-of select="receiverINN"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id" select="'receiverKPP'"/>
        <xsl:with-param name="lineId" select="'receiverKPPRow'"/>
        <xsl:with-param name="rowName">КПП:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:choose>
                <xsl:when test="$isDepoDebtPayment">
                    <input type="hidden" id="receiverKPP" name="receiverKPP" value="{receiverKPP}"/>
                    <b><xsl:value-of select="receiverKPP"/></b>
                </xsl:when>
                <xsl:otherwise>
                    <input id="receiverKPP" name="receiverKPP" type="text" value="{receiverKPP}" size="12" maxlength="9"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Банк получателя</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id" select="'receiverBankName'"/>
        <xsl:with-param name="lineId" select="'receiverBankNameRow'"/>
        <xsl:with-param name="rowName">Наименование:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" id="receiverBankName" name="receiverBankName" value="{receiverBankName}"/>
            <b><xsl:value-of select="receiverBankName"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id" select="'receiverBIC'"/>
        <xsl:with-param name="lineId" select="'receiverBICRow'"/>
        <xsl:with-param name="rowName">БИК:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" id="receiverBIC" name="receiverBIC" value="{receiverBIC}"/>
            <b><xsl:value-of select="receiverBIC"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="id" select="'receiverCorAccount'"/>
        <xsl:with-param name="lineId" select="'receiverCorAccountRow'"/>
        <xsl:with-param name="rowName">Корр. счет:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" id="receiverCorAccount" name="receiverCorAccount" value="{receiverCorAccount}"/>
            <b><xsl:value-of select="receiverCorAccount"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:if test="not($isDepoDebtPayment)">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="id" select="'taxPayment'"/>
            <xsl:with-param name="lineId" select="'taxPaymentRow'"/>
            <xsl:with-param name="rowValue">
                <input type="checkbox" id="taxCheckbox" name="taxCheckbox" style="border:none" onclick="refreshTaxPaymentForm();updateKPPNecessity();">
                    <xsl:if test="$taxPayment = 'true'">
                        <xsl:attribute name="checked"/>
                    </xsl:if>
                    <b> оплата налогов</b>
                </input>
                <a class="imgHintBlock" title="Часто задаваемые вопросы" onclick="javascript:openFAQ('/PhizIC/faq.do#r1');"></a>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Оплата</b></xsl:with-param>
    </xsl:call-template>

    <input type="hidden" name="exactAmount"        value="{exactAmount}"    id="exactAmount"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id">fromResource</xsl:with-param>
        <xsl:with-param name="rowName">Счет списания:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:if test="$personAvailable">
                <xsl:call-template name="resources">
                    <xsl:with-param name="name">fromResource</xsl:with-param>
                    <xsl:with-param name="accountNumber" select="fromAccountSelect"/>
                    <xsl:with-param name="linkId" select="fromResource"/>
                    <xsl:with-param name="accounts" select="$activeAccounts"/>
                    <xsl:with-param name="isDepoDebtPayment" select="$isDepoDebtPayment"/>
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
        <xsl:with-param name="description"><xsl:if test="not($isDepoDebtPayment)">Введите сумму, которую необходимо перевести</xsl:if></xsl:with-param>
        <xsl:with-param name="rowName">Сумма в валюте зачисления:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:choose>
                <xsl:when test="$isDepoDebtPayment">
                    <input type="hidden" id="buyAmount" name="buyAmount" value="{buyAmount}"/>
                    <b><xsl:value-of select="buyAmount"/>&nbsp;<span id="buyAmountCurrency"></span></b>
                </xsl:when>
                <xsl:otherwise>
                    <input id="buyAmount" name="buyAmount" type="text" value="{buyAmount}" size="24" class="moneyField"/>
                    &nbsp;<span id="buyAmountCurrency"></span>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'groundRow'"/>
        <xsl:with-param name="description"><xsl:if test="not($isDepoDebtPayment)"><cut/>Укажите, с какой целью Вы переводите деньги. Например, благотворительный взнос.</xsl:if></xsl:with-param>
        <xsl:with-param name="rowName">Назначение перевода:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:choose>
                <xsl:when test="$isDepoDebtPayment">
                    <input type="hidden" id="ground" name="ground" value="{ground}"/>
                    <b><xsl:value-of select="ground"/></b>
                </xsl:when>
                <xsl:otherwise>
                    <textarea id="ground" name="ground" rows="4" cols="40">
                        <xsl:value-of select="ground"/>
                    </textarea>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:with-param>
    </xsl:call-template>

    <div id="taxPaymentForm" style="display:none">
        <!--    поля налогового платежа    -->

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Налоговые поля</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxStatus</xsl:with-param>
            <xsl:with-param name="rowName">Статус составителя:</xsl:with-param>
            <xsl:with-param name="description"><cut/> Выберите из выпадающего списка или из справочника характеризующий Вас статус. Для того чтобы выбрать статус из справочника, нажмите на ссылку «Справочник» и в открывшемся окне выберите необходимый статус, установив напротив него флажок. Затем нажмите на кнопку «Выбрать».</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="dictionary" select="document('taxStatusesDictionary')/entity-list/entity"/>
                <xsl:variable name="taxStatus"  select="taxStatus"/>

                <select id="taxStatus" name="taxStatus">
                    <xsl:for-each select="$dictionary">
                        <option>
                            <xsl:if test="$taxStatus = ./@key">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            <xsl:attribute name="value">
                                <xsl:value-of select="./@key"/>
                            </xsl:attribute>
                            <xsl:value-of select="./text()"/>
                        </option>
                    </xsl:for-each>
                </select>
                <span class="simpleLink" onclick="javascript:displayPeriodList(event,'taxstatus');">
                    &nbsp;<u class="text-green">справочник</u>
                </span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxKBK</xsl:with-param>
            <xsl:with-param name="description"><cut/> Введите вручную или выберите из справочника Код Бюджетной Классификации. Для того чтобы выбрать КБК из справочника, нажмите на ссылку «Справочник». В открывшемся окне установите флажок напротив нужного Вам КБК и нажмите на кнопку «Выбрать». Поле «Назначение платежа» заполнится автоматически.</xsl:with-param>
            <xsl:with-param name="rowName">КБК:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input id="taxKBK" name="taxKBK" type="text" value="{taxKBK}" size="25" maxLength="20"/>
                <span class="simpleLink" onclick="javascript:openKBK();">
                    &nbsp;<u class="text-green">справочник</u>
                </span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxOKATO</xsl:with-param>
            <xsl:with-param name="description">Укажите код района по ОКАТО. Код ОКАТО может содержать не более 11 символов.</xsl:with-param>
            <xsl:with-param name="rowName">ОКАТО:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input id="taxOKATO" name="taxOKATO" type="text" value="{taxOKATO}" size="25" maxLength="11"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxGround</xsl:with-param>
            <xsl:with-param name="description"><cut/>Выберите из выпадающего списка или из справочника основание налогового платежа. Для того чтобы выбрать основание из справочника, нажмите на кнопку «Справочник». В открывшемся окне установите флажок напротив интересующего Вас основания платежа и нажмите на кнопку «Выбрать».</xsl:with-param>
            <xsl:with-param name="rowName">Основание налогового платежа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="dictionary" select="document('taxGroundsDictionary')/entity-list/entity"/>
                <xsl:variable name="taxGround"  select="taxGround"/>

                <select id="taxGround" name="taxGround">
                    <xsl:for-each select="$dictionary">
                        <option>
                            <xsl:if test="$taxGround = ./@key">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            <xsl:attribute name="value">
                                <xsl:value-of select="./@key"/>
                            </xsl:attribute>
                            <xsl:value-of select="./text()"/>
                        </option>
                    </xsl:for-each>
                </select>
                <span class="simpleLink" onclick="javascript:displayPeriodList(event,'taxfund');">
                    &nbsp;<u class="text-green">справочник</u>
                </span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxPeriod</xsl:with-param>
            <xsl:with-param name="description">Укажите период, за который Вы хотите оплатить налог. <cut/> Из выпадающего списка выберите периодичность совершения платежа (месячный платеж, квартальный, полугодовой, годовой). Далее для месячных платежей укажите номер месяца (01-12), для квартальных - номер квартала (01-04), для полугодовых - номер полугодия (01-02). Затем введите год, за который оплачиваете налог.</xsl:with-param>
            <xsl:with-param name="rowName">Налоговый период:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="dictionary" select="document('taxPeriodsDictionary')/entity-list/entity"/>
                <xsl:variable name="taxPeriod1" select="taxPeriod1"/>

                <select id="taxPeriod1" name="taxPeriod1">
                    <xsl:for-each select="$dictionary">
                        <option>
                            <xsl:if test="$taxPeriod1 = ./@key">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            <xsl:attribute name="value">
                                <xsl:value-of select="./@key"/>
                            </xsl:attribute>
                            <xsl:value-of select="./text()"/>
                        </option>
                    </xsl:for-each>
                </select>
                &nbsp;
                <input id="taxPeriod2" name="taxPeriod2" type="text" maxLength="7" size="7" value="{taxPeriod2}" onkeydown="enterNumericTemplateFld(event,this, document.getElementById('taxPeriod1').value=='ГД' ? '____' : '__/____');"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxDocumentDate</xsl:with-param>
            <xsl:with-param name="description"><cut/>Укажите дату оплаты налога. Также для выбора даты можно воспользоваться календарем, расположенным рядом с полем.</xsl:with-param>
            <xsl:with-param name="rowName">Дата налогового платежа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input id="taxDocumentDate" name="taxDocumentDate" type="text" value="{taxDocumentDate}" size="17" class="date-pick"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxDocumentNumber</xsl:with-param>
            <xsl:with-param name="description">Укажите номер налогового платежа. Номер платежа может состоять только из 15 символов.</xsl:with-param>
            <xsl:with-param name="rowName">Номер налогового платежа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input id="taxDocumentNumber" name="taxDocumentNumber" type="text" value="{taxDocumentNumber}" size="17" maxLength="15"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxType</xsl:with-param>
            <xsl:with-param name="description"><cut/>Выберите из выпадающего списка или из справочника тип налогового платежа. Для того чтобы воспользоваться справочником, нажмите на ссылку «Справочник». В открывшемся окне установите флажок напротив нужного Вам типа платежа и нажмите на кнопку «Выбрать».</xsl:with-param>
            <xsl:with-param name="rowName">Тип налогового платежа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="dictionary" select="document('taxTypesDictionary')/entity-list/entity"/>
                <xsl:variable name="taxType" select="taxType"/>

                <select id="taxType" name="taxType">
                    <xsl:for-each select="$dictionary">
                        <option>
                            <xsl:if test="$taxType = ./@key">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            <xsl:attribute name="value">
                                <xsl:value-of select="./@key"/>
                            </xsl:attribute>
                            <xsl:value-of select="./text()"/>
                        </option>
                    </xsl:for-each>
                </select>
                <span class="simpleLink" onclick="javascript:displayPeriodList(event,'taxtype');">
                    &nbsp;<u class="text-green">справочник</u>
                </span>
            </xsl:with-param>
        </xsl:call-template>
    </div>

    <script type="text/javascript" language="JavaScript">

        var accounts = new Array();
        var resourceCurrencies = new Array();

        function init()
        {
            <xsl:if test="not($isDepoDebtPayment)">
                <xsl:for-each select="$activeAccounts">
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    accounts['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="@key"/>';
                    resourceCurrencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                    <xsl:if test="field[@name='isUseStoredResource'] = 'true'">
                        addMessage('Информация по Вашим счетам может быть неактуальной.');
                    </xsl:if>
                </xsl:for-each>
                <xsl:choose>
                    <xsl:when test="substring(receiverAccount, 1, 5) = '40101'">
                        ensureElement("taxCheckbox").checked = true;
                        updateKPPNecessity();
                    </xsl:when>
                    <xsl:when test="$taxPayment">
                        updateKPPNecessity();
                    </xsl:when>
                </xsl:choose>
            </xsl:if>

            refreshForm();
            <xsl:if test="$isDepoDebtPayment">
                addMessage("Ваш платеж будет обработан в течение 3-х рабочих дней.");
            </xsl:if>
        }

        function displayPeriodList(event,action)
        {
            tempWin=window.open(document.webRootPrivate + "/PD4.do?action="+action,"","resizable=0,menubar=0,toolbar=0,scrollbars=1,status=0");
            tempWin.resizeTo(width=750,height=500);
            tempWin.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
        }

        function openKBK()
        {
            tempWin=window.open(document.webRootPrivate + '/dictionary/kbk/list.do?action=taxKBK&amp;noResize=1', 'KBK', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
            tempWin.resizeTo(width=850,height=600);
            tempWin.moveTo(screen.width / 2 - 410, screen.height / 2 - 275);
        }

        function setKBKInfo(info)
        {
            setElement("taxKBK", info["code"]);
        }

        function setTaxStatus(info)
        {
            setElement('taxStatus', info["taxStatus"]);
        }

        function setTaxType(info)
        {
            setElement('taxType', info["taxType"]);
        }

        function setTaxFund(info)
        {
            setElement('taxGround', info["taxFund"]);
        }

        function refreshTaxPaymentForm()
        {
            var taxPayment = ensureElement("taxCheckbox").checked;
            hideOrShow(ensureElement("taxPaymentForm"), !taxPayment);
            ensureElement("taxPayment").value = taxPayment;
        }

        function refreshForm()
        {
            var fromResource = getElement("fromResource").value;
            var fromCurrency = resourceCurrencies[fromResource];

            $("#buyAmountRow span.paymentTextLabel").html("Сумма:");

            //переводим поставщику услуг (счет всегда рублевый)
            $("#buyAmountCurrency").text(currencySignMap.get('RUB'));
            getElement('buyAmountCurrency').value = 'RUB';

            <xsl:if test="not($isDepoDebtPayment)">
                refreshTaxPaymentForm();
            </xsl:if>
        }

        function updateKPPNecessity()
        {
            var isTaxPayment = ensureElement("taxCheckbox").checked;
            var row = document.getElementById("receiverKPPRow");
            var payLabel = findChildByClassName(row, "paymentLabel");
            if (isTaxPayment)
            {
                var previousAsterisk = findChildByClassName(payLabel, "asterisk");
                if(previousAsterisk != null)
                    return;
                var asterisk = document.createElement("span");
                asterisk.className = "asterisk";
                asterisk.innerHTML = "*";

                payLabel.appendChild(asterisk);
            }
            else
            {
                var asterisk = findChildByClassName(payLabel, "asterisk");
                if(asterisk != null)
                    payLabel.removeChild(asterisk);
            }
        }

        init();

    </script>

</xsl:template>

<xsl:template match="/form-data" mode="edit-long-offer">

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" id="documentNumber" name="documentNumber" value="{documentNumber}"/>
            <b><xsl:value-of select="documentNumber"/></b>
        </xsl:with-param>
        <xsl:with-param name="isAllocate" select="'false'"/>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="documentDate" value="{documentDate}"/>
            <b><xsl:value-of select="documentDate"/></b>
        </xsl:with-param>
        <xsl:with-param name="isAllocate" select="'false'"/>
    </xsl:call-template>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Получатель</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="id" select="'receiverName'"/>
        <xsl:with-param name="lineId" select="'receiverNameRow'"/>
        <xsl:with-param name="rowName">Наименование:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" id="receiverName" name="receiverName" value="{receiverName}"/>
            <b><xsl:value-of select="receiverName"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="id" select="'receiverAccount'"/>
        <xsl:with-param name="lineId" select="'receiverAccountRow'"/>
        <xsl:with-param name="rowName">Номер счета:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="buyAmountCurrency" value="{buyAmountCurrency}"/>
            <input type="hidden" id="receiverAccount" name="receiverAccount" value="{receiverAccount}"/>
            <b><xsl:value-of select="receiverAccount"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="id" select="'receiverINN'"/>
        <xsl:with-param name="lineId" select="'receiverINNRow'"/>
        <xsl:with-param name="rowName">ИНН:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" id="receiverINN" name="receiverINN" value="{receiverINN}"/>
            <b><xsl:value-of select="receiverINN"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="id" select="'receiverKPP'"/>
        <xsl:with-param name="lineId" select="'receiverKPPRow'"/>
        <xsl:with-param name="rowName">КПП:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" id="receiverKPP" name="receiverKPP" value="{receiverKPP}"/>
            <b><xsl:value-of select="receiverKPP"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Банк получателя</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="id" select="'receiverBankName'"/>
        <xsl:with-param name="lineId" select="'receiverBankNameRow'"/>
        <xsl:with-param name="rowName">Наименование:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" id="receiverBankName" name="receiverBankName" value="{receiverBankName}"/>
            <b><xsl:value-of select="receiverBankName"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="id" select="'receiverBIC'"/>
        <xsl:with-param name="lineId" select="'receiverBICRow'"/>
        <xsl:with-param name="rowName">БИК:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" id="receiverBIC" name="receiverBIC" value="{receiverBIC}"/>
            <b><xsl:value-of select="receiverBIC"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="id" select="'receiverCorAccount'"/>
        <xsl:with-param name="lineId" select="'receiverCorAccountRow'"/>
        <xsl:with-param name="rowName">Корр. счет:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" id="receiverCorAccount" name="receiverCorAccount" value="{receiverCorAccount}"/>
            <b><xsl:value-of select="receiverCorAccount"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="taxPayment" select="taxPayment"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="id" select="'taxPayment'"/>
        <xsl:with-param name="lineId" select="'taxPaymentRow'"/>
        <xsl:with-param name="rowValue">
            <input name="taxPayment" type="checkbox" style="border:none" disabled="true">
                <xsl:if test="$taxPayment = 'true'">
                    <xsl:attribute name="checked"/>
                </xsl:if>
                <b> оплата налогов</b>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Оплата</b></xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="accounts" select="document('stored-active-debit-rur-allowed-external-jur-accounts.xml')/entity-list/*"/>

    <xsl:variable name="fromResource" select="fromResource"/>

    <xsl:variable name="fromResourceNumber">
        <xsl:choose>
            <xsl:when test="starts-with($fromResource, 'account:')">
                <xsl:value-of select="$accounts[field[@name='linkId']/text()  = substring($fromResource, 9, string-length($fromResource))]/@key"/>
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
            <xsl:otherwise>
                <xsl:value-of select="fromResourceCurrency"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:choose>
        <!--необходимо привести fromResource к виду account/card:linkId-->
        <xsl:when test="not (starts-with($fromResource, 'account:'))">
            <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
            <xsl:variable name="resource">
                <xsl:if test="fromResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                    <xsl:value-of select="concat('account:', $accounts[@key = $fromAccountSelect]/field[@name='linkId']/text())"/>
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
                    <xsl:value-of select="au:getFormattedAccountNumber($fromResourceNumber)"/>
                    &nbsp;[<xsl:value-of select="$fromResourceName"/>]&nbsp;
                    <xsl:value-of select="mu:getCurrencySign($fromResourceCurrency)"/>
                </span>
            </b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="sumTypes" select="document('long-offer-sum-types.xml')/entity-list/entity"/>

    <!--длительное поручение для юридического лица с карты всегда в валюте счета зачисления-->
    <xsl:if test="exactAmount = 'destination-field-exact' and string-length(buyAmount)>0">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
            <xsl:with-param name="rowName">Сумма в валюте зачисления:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="isSumModify = 'true'">
                    <b><xsl:value-of select="$sumTypes[@key=longOfferSumType]"/></b>&nbsp;
                </xsl:if>
                <xsl:if test="isSumModify != 'true'">
                    <xsl:if test="string-length(buyAmount)>0">
                        <input type="hidden" name="buyAmount" value="{buyAmount}"/>
                        <span class="summ">
                            <xsl:value-of select="format-number(translate(buyAmount, ', ','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                            <xsl:value-of select="mu:getCurrencySign(buyAmountCurrency)"/>
                        </span>
                    </xsl:if>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="lineId" select="'groundRow'"/>
        <xsl:with-param name="rowName">Назначение перевода:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" id="ground" name="ground" value="{ground}"/>
            <b><xsl:value-of select="ground"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:if test="$isTemplate != 'true'">
        <xsl:if test="$mode='view'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Статус платежа:</xsl:with-param>
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
            <xsl:with-param name="rowName">Плановая дата исполнения:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="admissionDate" name="admissionDate" value="{admissionDate}"/>
                <b><xsl:value-of  select="admissionDate"/></b>
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
                       onkeyup="javascript:refreshReport(getFormEvent(getElementValue('longOfferEventSelect')));"/>
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
    <xsl:variable name="monthsDictionary" select="document('months.xml')/entity-list/entity"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Повторяется:</xsl:with-param>
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
        <xsl:with-param name="rowName">Число месяца:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="text" name="ONCE_IN_MONTH_DAY" value="{longOfferPayDay}" size="1" maxlength="2"
                   onchange="javascript:refreshReport(getElementValue('longOfferEventSelect'));"
                   onkeyup="javascript:refreshReport(getElementValue('longOfferEventSelect'));"/>
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
                        onkeyup="javascript:refreshReport(getElementValue('longOfferEventSelect'));"
                    >

                    <xsl:call-template name="addOption">
                        <xsl:with-param name="value"    select="'01|04|07|10'"/>
                        <xsl:with-param name="selected" select="$month"/>
                        <xsl:with-param name="source"   select="$monthsDictionary"/>
                    </xsl:call-template>

                    <xsl:call-template name="addOption">
                        <xsl:with-param name="value"    select="'02|05|08|11'"/>
                        <xsl:with-param name="selected" select="$month"/>
                        <xsl:with-param name="source"   select="$monthsDictionary"/>
                    </xsl:call-template>

                    <xsl:call-template name="addOption">
                        <xsl:with-param name="value"    select="'03|06|09|12'"/>
                        <xsl:with-param name="selected" select="$month"/>
                        <xsl:with-param name="source"   select="$monthsDictionary"/>
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
                        <xsl:with-param name="source"   select="$monthsDictionary"/>
                    </xsl:call-template>

                    <xsl:call-template name="addOption">
                        <xsl:with-param name="value" select="'02|08'"/>
                        <xsl:with-param name="selected" select="$month"/>
                        <xsl:with-param name="source"   select="$monthsDictionary"/>
                    </xsl:call-template>

                    <xsl:call-template name="addOption">
                        <xsl:with-param name="value" select="'03|09'"/>
                        <xsl:with-param name="selected" select="$month"/>
                        <xsl:with-param name="source"   select="$monthsDictionary"/>
                    </xsl:call-template>

                    <xsl:call-template name="addOption">
                        <xsl:with-param name="value" select="'04|10'"/>
                        <xsl:with-param name="selected" select="$month"/>
                        <xsl:with-param name="source"   select="$monthsDictionary"/>
                    </xsl:call-template>

                    <xsl:call-template name="addOption">
                        <xsl:with-param name="value" select="'05|11'"/>
                        <xsl:with-param name="selected" select="$month"/>
                        <xsl:with-param name="source"   select="$monthsDictionary"/>
                    </xsl:call-template>

                    <xsl:call-template name="addOption">
                        <xsl:with-param name="value" select="'06|12'"/>
                        <xsl:with-param name="selected" select="$month"/>
                        <xsl:with-param name="source"   select="$monthsDictionary"/>
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
        <xsl:with-param name="rowName">Дата оплаты:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <nobr>
                месяц&nbsp;
                <select name="ONCE_IN_YEAR_MONTHS"
                        onchange="javascript:refreshReport(getElementValue('longOfferEventSelect'));"
                        onkeyup="javascript:refreshReport(getElementValue('longOfferEventSelect'));"
                    >

                    <xsl:for-each select="$monthsDictionary">
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
        <xsl:with-param name="rowName">Выполняется:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:variable name="dictionary" select="document('priority.xml')/entity-list/entity"/>
            <xsl:variable name="priority"   select="longOfferPrioritySelect"/>

            <select name="longOfferPrioritySelect">
                <xsl:for-each select="$dictionary">
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

            function refreshForm(formEvent)
            {
                var event = getFormEvent(formEvent);
                var fromResource = getElementValue("fromResource");
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
                longOfferEventSelect.options[longOfferEventLength++] = new Option(eventTypes["ONCE_IN_MONTH"],  "ONCE_IN_MONTH");
                longOfferEventSelect.options[longOfferEventLength++] = new Option(eventTypes["ONCE_IN_QUARTER"],    "ONCE_IN_QUARTER");
                longOfferEventSelect.options[longOfferEventLength++] = new Option(eventTypes["ONCE_IN_HALFYEAR"],   "ONCE_IN_HALFYEAR");
                longOfferEventSelect.options[longOfferEventLength++] = new Option(eventTypes["ONCE_IN_YEAR"],   "ONCE_IN_YEAR");

                for (var i=0; i < longOfferEventSelect.length; i++)
                {
                    if (longOfferEventSelect[i].value == event)
                        longOfferEventSelect[i].selected = true;
                }

                var sumTypeSelectLength = 0;
                var sumTypeSelect = getElement("longOfferSumSelect");
                sumTypeSelect.options.length = sumTypeSelectLength;
                sumTypeSelect.options[sumTypeSelectLength++] = new Option(sumTypes["FIXED_SUMMA_IN_RECIP_CURR"],    "FIXED_SUMMA_IN_RECIP_CURR");

                //валюта счета получателя всегда RUB
                getElement('buyAmountCurrency').value = 'RUB';
                getElement("longOfferEventType").value = event;
                refreshPayTypeBlock(getElementValue("longOfferSumSelect"));
                refreshReport(event);
            }

            //обновление сводки и ближайшей даты платежа
            function refreshReport(event)
            {
                var longOfferStartDate = getElement("longOfferStartDate");
                var firstPaymentDate = getElement("firstPaymentDate");
                var longOfferDate = new LongOfferDate(event, createDate(longOfferStartDate.value));

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

                function clearReportArea()
                {
                    getElement("firstPaymentDate").value = "";
                    getElement("longOfferPayDay").value = "";
                    $("#firstPaymentDate").text("");
                    $("#report").text("");
                }
            }

            function refreshPayTypeBlock(type)
            {
                hideOrShow(ensureElement("sellAmountBlock"), true);
                hideOrShow(ensureElement("buyAmountBlock"),  false);

                getElement("sellAmount").value  = "";
                getElement("exactAmount").value = "destination-field-exact";
                getElement("longOfferSumType").value = type;
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
        <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of  select="documentNumber"/></b>
        </xsl:with-param>
        <xsl:with-param name="isAllocate" select="'false'"/>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of  select="documentDate"/></b>
        </xsl:with-param>
        <xsl:with-param name="isAllocate" select="'false'"/>
    </xsl:call-template>

     <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Получатель</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id" select="'receiverName'"/>
        <xsl:with-param name="lineId" select="'receiverNameRow'"/>
        <xsl:with-param name="rowName">Наименование:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of select="receiverName"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id" select="'receiverAccount'"/>
        <xsl:with-param name="lineId" select="'receiverAccountRow'"/>
        <xsl:with-param name="rowName">Номер счета:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of select="au:getFormattedAccountNumber(receiverAccount)"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id" select="'receiverINN'"/>
        <xsl:with-param name="lineId" select="'receiverINNRow'"/>
        <xsl:with-param name="rowName">ИНН:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of select="receiverINN"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id" select="'receiverKPP'"/>
        <xsl:with-param name="lineId" select="'receiverKPPRow'"/>
        <xsl:with-param name="rowName">КПП:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of select="receiverKPP"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Банк получателя</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id" select="'receiverBankName'"/>
        <xsl:with-param name="lineId" select="'receiverBankNameRow'"/>
        <xsl:with-param name="rowName">Наименование:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of select="receiverBankName"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id" select="'receiverBIC'"/>
        <xsl:with-param name="lineId" select="'receiverBICRow'"/>
        <xsl:with-param name="rowName">БИК:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of select="receiverBIC"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required" select="'false'"/>
        <xsl:with-param name="id" select="'receiverCorAccount'"/>
        <xsl:with-param name="lineId" select="'receiverCorAccountRow'"/>
        <xsl:with-param name="rowName">Корр. счет:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of select="receiverCorAccount"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="taxPayment" select="taxPayment"/>

    <xsl:if test="depoLinkId = ''">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="id" select="'taxPayment'"/>
            <xsl:with-param name="lineId" select="'taxPaymentRow'"/>
            <xsl:with-param name="rowValue">
                <input name="taxPayment" type="checkbox" style="border:none" disabled="true">
                    <xsl:if test="$taxPayment = 'true'">
                        <xsl:attribute name="checked"/>
                    </xsl:if>
                    <b> оплата налогов</b>
                </input>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>Оплата</b></xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Счет списания:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:if test="not($fromAccountSelect= '')">
                <b>
                    <xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/>
                    &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                    <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
                </b>
            </xsl:if>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="longOfferSumType"   select="longOfferSumType"/>
    <xsl:variable name="sumTypes" select="document('long-offer-sum-types.xml')/entity-list/entity"/>

    <xsl:if test="exactAmount = 'destination-field-exact'">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
            <xsl:with-param name="rowName">Сумма:</xsl:with-param>
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

    <xsl:choose>
        <xsl:when test="string-length(commission)>0">
            <xsl:if test="format-number(commission, '0,00', 'sbrf') = '0,00' and state = 'SAVED'">
                <script type="text/javascript">
                  if(window.addMessage != undefined)
                        addMessage("За выполнение данной операции комиссия не взимается.");
                </script>
            </xsl:if>
            <xsl:if test="$isTemplate != 'true'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="required" select="'false'"/>
                    <xsl:with-param name="rowName">Комиссия:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                         <b><xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(commissionCurrency)"/></b>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
        </xsl:when>
        <xsl:when test="state = 'SAVED'">
            <script type="text/javascript">
            <![CDATA[
                if(window.addMessage != undefined)
                    addMessage("За данную операцию может взиматься комиссия в соответствии с тарифами банка. " +
                               "Сумму комиссии Вы можете посмотреть <a href='http://www.sberbank.ru/common/img/uploaded/files/pdf/person/bank_cards/Perevody__Tarify.pdf' class='paperEnterLink' target='_blank'>здесь</a>.");
                ]]>
            </script>
        </xsl:when>
    </xsl:choose>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'groundRow'"/>
        <xsl:with-param name="rowName">Назначение перевода:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of select="ground"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:if test="$taxPayment = 'true'">
        <!--    поля налогового платежа    -->

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Налоговые поля</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxStatus</xsl:with-param>
            <xsl:with-param name="rowName">Статус составителя:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="taxStatus"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxKBK</xsl:with-param>
            <xsl:with-param name="rowName">КБК:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="taxKBK"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxOKATO</xsl:with-param>
            <xsl:with-param name="rowName">ОКАТО:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="taxOKATO"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxGround</xsl:with-param>
            <xsl:with-param name="rowName">Основание налогового платежа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="taxGround"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxPeriod</xsl:with-param>
            <xsl:with-param name="rowName">Налоговый период:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="taxPeriod"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxDocumentDate</xsl:with-param>
            <xsl:with-param name="rowName">Дата налогового платежа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="taxDocumentDate"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxDocumentNumber</xsl:with-param>
            <xsl:with-param name="rowName">Номер налогового платежа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="taxDocumentNumber"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxType</xsl:with-param>
            <xsl:with-param name="rowName">Тип налогового платежа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="taxType"/></b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>
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
    
     <xsl:if test="$isTemplate != 'true'">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowStyle">display: none</xsl:with-param>
            <xsl:with-param name="rowName">Плановая дата исполнения:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="admissionDate"/></b>
            </xsl:with-param>
        </xsl:call-template>
     </xsl:if>

    <xsl:if test="string-length(promoCode)>0">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Промо-код:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div id="promoCode">
                    <xsl:value-of select="promoCode"/>
                    <img src="{concat($resourceRoot, '/images/hint.png')}" alt="подсказка" onclick="javascript:openFAQ('/PhizIC/faq.do#r2');"/>
                </div>
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
    <xsl:variable name="monthsDictionary" select="document('months.xml')/entity-list/entity"/>
    <xsl:variable name="eventTypes" select="document('long-offer-events-types.xml')/entity-list/entity[@key=$event]/text()"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Повторяется:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of select="$eventTypes"/></b>
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
                                <xsl:with-param name="source" select="$monthsDictionary"/>
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
                                <xsl:with-param name="source" select="$monthsDictionary"/>
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
                                <xsl:with-param name="source" select="$monthsDictionary"/>
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
            <xsl:variable name="priority" select="longOfferPrioritySelect"/>
            <b><xsl:value-of select="document('priority.xml')/entity-list/entity[@key=$priority]/text()"/></b>
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
                        &nbsp;<xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/>
                        &nbsp;[<xsl:value-of select="fromAccountName"/>]
                        &nbsp;<xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
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
        <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
        <xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
        <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM'">Ожидает дополнительной обработки (статус для клиента: "Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if>")</xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">Активный</xsl:when>
        <xsl:when test="$code='TEMPLATE'">Сверхлимитный</xsl:when>
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
        <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
        <xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
        <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM'">Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if></xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">Активный</xsl:when>
        <xsl:when test="$code='TEMPLATE'">Сверхлимитный</xsl:when>
        <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
        <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
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
	        <xsl:copy-of select="$rowName"/>
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
