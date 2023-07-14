<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:dh="java://com.rssl.phizic.business.documents.DocumentHelper"
                exclude-result-prefixes="xalan mask au mu ph dh">
    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="longOffer" select="false()"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="templateAvailable" select="''"/>
    <xsl:param name="quickLongOfferAvailable" select="''"/>
    <xsl:param name="postConfirmCommission" select="postConfirmCommission"/> <!-- поддержка отображения комисий из ЦОД -->
    <xsl:param name="changedFields"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'edit'">
                <xsl:apply-templates mode="edit"/>
            </xsl:when>
            <xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="view"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <xsl:variable name="activeAccounts"    select="document('active-money-box-available-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards"       select="document('active-not-credit-main-cards-with-scs.xml')/entity-list/*"/>

        <initialData>
            <form>CreateMoneyBoxPayment</form>
            <CreateMoneyBoxPayment>
                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер документа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>
                <moneyBoxName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">moneyBoxName</xsl:with-param>
                        <xsl:with-param name="title">Название копилки</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="moneyBoxName"/>
                    </xsl:call-template>
                </moneyBoxName>
                <toResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">toResource</xsl:with-param>
                        <xsl:with-param name="title">Счет зачисления</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="accounts" select="$activeAccounts"/>
                        <xsl:with-param name="linkCode" select="toResource"/>
                        <xsl:with-param name="linkNumber" select="toAccountSelect"/>
                    </xsl:call-template>
                </toResource>
                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Карта списания</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="cards" select="$activeCards"/>
                        <xsl:with-param name="linkCode" select="fromResource"/>
                        <xsl:with-param name="linkNumber" select="fromAccountSelect"/>
                    </xsl:call-template>
                </fromResource>
                <moneyBoxSumType>
                    <xsl:call-template name="simpleField">
                        <xsl:with-param name="name" select="'moneyBoxSumType'"/>
                        <xsl:with-param name="title">Вид копилки</xsl:with-param>
                        <xsl:with-param name="type" select="'list'"/>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="valueTag">
                            <listType>
                                <availableValues>
                                    <valueItem>
                                        <value>FIXED_SUMMA</value>
                                        <title>Фиксированная сумма</title>
                                        <selected>
                                            <xsl:value-of select="string(moneyBoxSumType = 'FIXED_SUMMA')"/>
                                        </selected>
                                    </valueItem>
                                    <valueItem>
                                        <value>PERCENT_BY_ANY_RECEIPT</value>
                                        <title>Процент от зачислений</title>
                                        <selected>
                                            <xsl:value-of select="string(moneyBoxSumType =  'PERCENT_BY_ANY_RECEIPT')"/>
                                        </selected>
                                    </valueItem>
                                    <valueItem>
                                        <value>PERCENT_BY_DEBIT</value>
                                        <title>Процент от расходов</title>
                                        <selected>
                                            <xsl:value-of select="string(moneyBoxSumType =  'PERCENT_BY_DEBIT')"/>
                                        </selected>
                                    </valueItem>
                                </availableValues>
                            </listType>
                        </xsl:with-param>
                    </xsl:call-template>
                </moneyBoxSumType>
                <fixedSumma>
                    <eventType>
                        <xsl:call-template name="simpleField">
                            <xsl:with-param name="name">longOfferEventType</xsl:with-param>
                            <xsl:with-param name="title">Периодичность</xsl:with-param>
                            <xsl:with-param name="type" select="'list'"/>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="valueTag">
                                <listType>
                                    <availableValues>
                                        <xsl:variable name="eventTypes" select="document('money-box-events-types.xml')/entity-list/entity"/>
                                        <xsl:variable name="eventType"  select="longOfferEventType"/>
                                        <xsl:for-each select="$eventTypes">
                                            <xsl:if test="./@key = 'ONCE_IN_MONTH' or ./@key = 'ONCE_IN_QUARTER' or ./@key = 'ONCE_IN_WEEK' or ./@key = 'ONCE_IN_YEAR'">
                                                <valueItem>
                                                    <value><xsl:value-of select="./@key"/></value>
                                                    <title><xsl:value-of select="./text()"/></title>
                                                    <selected>
                                                        <xsl:value-of select="string($eventType = ./@key)"/>
                                                    </selected>
                                                </valueItem>
                                            </xsl:if>
                                        </xsl:for-each>
                                    </availableValues>
                                </listType>
                            </xsl:with-param>
                        </xsl:call-template>
                    </eventType>
                    <nextPayDate>
                        <xsl:call-template name="dateField">
                            <xsl:with-param name="name">longOfferStartDate</xsl:with-param>
                            <xsl:with-param name="title">Дата ближайшего пополнения</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="longOfferStartDate"/>
                        </xsl:call-template>
                    </nextPayDate>
                </fixedSumma>
                <byPercent>
                    <percent>
                        <xsl:variable name="percentValue">
                            <xsl:choose>
                                <xsl:when test="string-length(percent)>0">
                                    <xsl:value-of select="percent"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="dh:getMoneyBoxDefaultPercent()"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">percent</xsl:with-param>
                            <xsl:with-param name="title">% от суммы</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="$percentValue"/>
                        </xsl:call-template>
                    </percent>
                </byPercent>
                <amount>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">sellAmount</xsl:with-param>
                        <xsl:with-param name="title">Сумма пополнения</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="sellAmount"/>
                    </xsl:call-template>
                </amount>
            </CreateMoneyBoxPayment>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
        <xsl:variable name="fromResourceLink" select="fromResource"/>
        <xsl:variable name="toAccountSelect" select="toAccountSelect"/>
        <xsl:variable name="toResourceLink" select="toResource"/>
        <xsl:variable name="activeAccounts"    select="document('active-money-box-available-accounts.xml')/entity-list/entity[$toResourceLink=./field[@name='code']/text() or $toAccountSelect/text() = ./@key]"/>
        <xsl:variable name="activeCards"       select="document('active-not-credit-main-cards-with-scs.xml')/entity-list/entity[$fromResourceLink=./field[@name='code']/text() or $fromAccountSelect/text() = ./@key]"/>

        <document>
            <form>CreateMoneyBoxPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="false()"/></checkAvailable>
            </xsl:if>
            <xsl:if test="string-length($templateAvailable)>0">
                <templateAvailable><xsl:value-of select="false"/></templateAvailable>
            </xsl:if>
            <xsl:if test="string-length($quickLongOfferAvailable)>0">
                <autopayable><xsl:value-of select="false"/></autopayable>
            </xsl:if>
            <xsl:if test="string-length(documentNumber)>0">
                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер документа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>
            </xsl:if>

            <CreateMoneyBoxPaymentDocument>
                <moneyBoxName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">moneyBoxName</xsl:with-param>
                        <xsl:with-param name="title">Название копилки</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="moneyBoxName"/>
                    </xsl:call-template>
                </moneyBoxName>
                <toResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">toResource</xsl:with-param>
                        <xsl:with-param name="title">Счет зачисления</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="accounts" select="$activeAccounts"/>
                        <xsl:with-param name="cards"/>
                        <xsl:with-param name="linkCode" select="toResource"/>
                        <xsl:with-param name="linkNumber" select="toAccountSelect"/>
                    </xsl:call-template>
                </toResource>
                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Карта списания</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="cards" select="$activeCards"/>
                        <xsl:with-param name="linkCode" select="fromResource"/>
                        <xsl:with-param name="linkNumber" select="fromAccountSelect"/>
                        <xsl:with-param name="accounts"/>
                    </xsl:call-template>
                </fromResource>
                <moneyBoxSumType>
                    <xsl:call-template name="simpleField">
                        <xsl:with-param name="name" select="'moneyBoxSumType'"/>
                        <xsl:with-param name="title">Вид копилки</xsl:with-param>
                        <xsl:with-param name="type" select="'list'"/>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="valueTag">
                            <listType>
                                <availableValues>
                                    <xsl:choose>
                                        <xsl:when test="moneyBoxSumType = 'FIXED_SUMMA'">
                                            <valueItem>
                                                <value>FIXED_SUMMA</value>
                                                <title>Фиксированная сумма</title>
                                                <selected>true</selected>
                                            </valueItem>
                                        </xsl:when>
                                        <xsl:when test="moneyBoxSumType = 'PERCENT_BY_ANY_RECEIPT'">
                                            <valueItem>
                                                <value>PERCENT_BY_ANY_RECEIPT</value>
                                                <title>Процент от зачислений</title>
                                                <selected>true</selected>
                                            </valueItem>
                                        </xsl:when>
                                        <xsl:when test="moneyBoxSumType = 'PERCENT_BY_DEBIT'">
                                            <valueItem>
                                                <value>PERCENT_BY_DEBIT</value>
                                                <title>Процент от расходов</title>
                                                <selected>true</selected>
                                            </valueItem>
                                        </xsl:when>
                                    </xsl:choose>
                                </availableValues>
                            </listType>
                        </xsl:with-param>
                    </xsl:call-template>
                </moneyBoxSumType>
                <xsl:choose>
                    <xsl:when test="moneyBoxSumType = 'FIXED_SUMMA'">
                        <fixedSumma>
                            <eventType>
                                <xsl:call-template name="simpleField">
                                    <xsl:with-param name="name">longOfferEventType</xsl:with-param>
                                    <xsl:with-param name="title">Периодичность</xsl:with-param>
                                    <xsl:with-param name="type" select="'list'"/>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="changed" select="false()"/>
                                    <xsl:with-param name="valueTag">
                                        <listType>
                                            <availableValues>
                                                <xsl:variable name="eventTypes" select="document('money-box-events-types.xml')/entity-list/entity"/>
                                                <xsl:variable name="eventType"  select="longOfferEventType"/>
                                                <xsl:for-each select="$eventTypes">
                                                    <xsl:if test="$eventType = ./@key">
                                                        <valueItem>
                                                            <value><xsl:value-of select="./@key"/></value>
                                                            <title><xsl:value-of select="./text()"/></title>
                                                            <selected>true</selected>
                                                        </valueItem>
                                                    </xsl:if>
                                                </xsl:for-each>
                                            </availableValues>
                                        </listType>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </eventType>
                            <nextPayDate>
                                <xsl:call-template name="dateField">
                                    <xsl:with-param name="name">longOfferStartDate</xsl:with-param>
                                    <xsl:with-param name="title">Дата ближайшего пополнения</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="longOfferStartDate"/>
                                </xsl:call-template>
                            </nextPayDate>
                        </fixedSumma>
                    </xsl:when>
                    <xsl:otherwise>
                        <byPercent>
                            <percent>
                                <xsl:call-template name="integerField">
                                    <xsl:with-param name="name">percent</xsl:with-param>
                                    <xsl:with-param name="title">% от суммы</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="percent"/>
                                </xsl:call-template>
                            </percent>
                        </byPercent>
                    </xsl:otherwise>
                </xsl:choose>
                <amount>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">sellAmount</xsl:with-param>
                        <xsl:with-param name="title">
                            <xsl:choose>
                                <xsl:when test="moneyBoxSumType = 'FIXED_SUMMA'">
                                    Сумма пополнения
                                </xsl:when>
                                <xsl:otherwise>
                                    Максимальная сумма
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="sellAmount"/>
                    </xsl:call-template>
                </amount>
            </CreateMoneyBoxPaymentDocument>
        </document>
    </xsl:template>

    <xsl:template name="resourceField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="linkCode"/> <!--Код ресурса (card:1234, account:4321). при редактировании-->
        <xsl:param name="visible"/>
        <xsl:param name="cards"/>
        <xsl:param name="accounts"/>
        <xsl:param name="isView" select="false()"/> <!--false=редактирование; true=просмотр-->
        <xsl:param name="linkNumber"/> <!--Номер карты или счета. при просмотре-->
        <xsl:param name="validators" select="''"/>

        <xsl:variable name="isValidLinkCode" select="string-length($linkCode)>0 and (starts-with($linkCode, 'card:') or starts-with($linkCode, 'account:'))"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'resource'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="valueTag">
                <resourceType>
                    <availableValues>

                        <xsl:choose>
                            <xsl:when test="not($isView)">

                                <xsl:if test="string-length($accounts)>0">
                                    <xsl:for-each select="$accounts">
                                        <xsl:variable name="code" select="field[@name='code']/text()"/>
                                        <valueItem>
                                            <value><xsl:value-of select="$code"/></value>
                                            <productValue>
                                                <accounts>
                                                    <xsl:call-template name="products-type-account">
                                                        <xsl:with-param name="activeAccount" select="."/>
                                                    </xsl:call-template>
                                                </accounts>
                                            </productValue>
                                            <selected><xsl:value-of select="string(($isValidLinkCode and $code=$linkCode) or (not($isValidLinkCode) and @key=$linkNumber))"/></selected>
                                            <displayedValue>
                                                <xsl:variable name="accNumber" select="au:getFormattedAccountNumber(./@key)"/>
                                                <xsl:variable name="accName" select="./field[@name='name']"/>
                                                <xsl:variable name="accBal">
                                                    <xsl:choose>
                                                        <xsl:when test="./field[@name='amountDecimal'] != ''">
                                                            <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <xsl:value-of select="''"/>
                                                        </xsl:otherwise>
                                                    </xsl:choose>
                                                </xsl:variable>
                                                <xsl:variable name="accCur" select="mu:getCurrencySign(./field[@name='currencyCode'])"/>
                                                <xsl:value-of select="concat($accNumber, ' [', $accName, '] ', $accBal, ' ', $accCur)"/>
                                            </displayedValue>
                                        </valueItem>
                                    </xsl:for-each>
                                </xsl:if>

                                <xsl:if test="string-length($cards)>0">
                                    <xsl:for-each select="$cards">
                                        <xsl:variable name="code" select="field[@name='code']/text()"/>
                                        <valueItem>
                                            <value><xsl:value-of select="$code"/></value>
                                            <productValue>
                                                <cards>
                                                    <xsl:call-template name="products-type-card">
                                                        <xsl:with-param name="activeCard" select="."/>
                                                    </xsl:call-template>
                                                </cards>
                                            </productValue>
                                            <selected><xsl:value-of select="string(($isValidLinkCode and $code=$linkCode) or (not($isValidLinkCode) and @key=$linkNumber))"/></selected>
                                            <displayedValue>
                                                <xsl:variable name="cardNumber" select="mask:getCutCardNumber(./@key)"/>
                                                <xsl:variable name="cardName" select="./field[@name='name']"/>
                                                <xsl:variable name="cardBal">
                                                    <xsl:choose>
                                                        <xsl:when test="./field[@name='amountDecimal'] != ''">
                                                            <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <xsl:value-of select="''"/>
                                                        </xsl:otherwise>
                                                    </xsl:choose>
                                                </xsl:variable>
                                                <xsl:variable name="cardCur" select="mu:getCurrencySign(./field[@name='currencyCode'])"/>
                                                <xsl:value-of select="concat($cardNumber, ' [', $cardName, '] ', $cardBal, ' ', $cardCur)"/>
                                            </displayedValue>
                                        </valueItem>
                                    </xsl:for-each>
                                </xsl:if>

                            </xsl:when>
                            <xsl:otherwise>

                                <!--Просмотр. Доступен только номер карты/счета, поэтому код нужно вычислять -->
                                <xsl:variable name="accountNode" select="$accounts[@key = $linkNumber]"/>
                                <xsl:variable name="cardNode" select="$cards[@key = $linkNumber]"/>

                                <xsl:variable name="code">
                                    <xsl:choose>
                                        <xsl:when test="$accountNode">
                                            <xsl:value-of select="$accountNode/field[@name='code']/text()"/>
                                        </xsl:when>
                                        <xsl:when test="$cardNode">
                                            <xsl:value-of select="$cardNode/field[@name='code']/text()"/>
                                        </xsl:when>
                                    </xsl:choose>
                                </xsl:variable>

                                <xsl:variable name="displayedValue">
                                    <xsl:choose>
                                        <xsl:when test="$accountNode">
                                            <xsl:variable name="accNumber" select="au:getFormattedAccountNumber($accountNode/@key)"/>
                                            <xsl:variable name="accName" select="$accountNode/field[@name='name']"/>
                                            <xsl:variable name="accBal">
                                                <xsl:choose>
                                                    <xsl:when test="$accountNode/field[@name='amountDecimal'] != ''">
                                                        <xsl:value-of select="format-number($accountNode/field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:value-of select="''"/>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </xsl:variable>
                                            <xsl:variable name="accCur" select="mu:getCurrencySign($accountNode/field[@name='currencyCode'])"/>
                                            <xsl:value-of select="concat($accNumber, ' [', $accName, '] ', $accBal, ' ', $accCur)"/>
                                        </xsl:when>
                                        <xsl:when test="$cardNode">
                                            <xsl:variable name="cardNumber" select="mask:getCutCardNumber($cardNode/@key)"/>
                                            <xsl:variable name="cardName" select="$cardNode/field[@name='name']"/>
                                            <xsl:variable name="cardBal">
                                                <xsl:choose>
                                                    <xsl:when test="$cardNode/field[@name='amountDecimal'] != ''">
                                                        <xsl:value-of select="format-number($cardNode/field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:value-of select="''"/>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </xsl:variable>
                                            <xsl:variable name="cardCur" select="mu:getCurrencySign($cardNode/field[@name='currencyCode'])"/>
                                            <xsl:value-of select="concat($cardNumber, ' [', $cardName, '] ', $cardBal, ' ', $cardCur)"/>
                                        </xsl:when>
                                    </xsl:choose>
                                </xsl:variable>

                                <valueItem>
                                    <value><xsl:value-of select="$code"/></value>
                                    <productValue>
                                        <xsl:choose>
                                            <xsl:when test="starts-with($code,'card')">
                                                <cards>
                                                    <xsl:call-template name="products-type-card">
                                                        <xsl:with-param name="activeCard" select="$cardNode"/>
                                                    </xsl:call-template>
                                                </cards>
                                            </xsl:when>
                                            <xsl:when test="starts-with($code,'account')">
                                                <accounts>
                                                    <xsl:call-template name="products-type-account">
                                                        <xsl:with-param name="activeAccount" select="$accountNode"/>
                                                    </xsl:call-template>
                                                </accounts>
                                            </xsl:when>
                                       </xsl:choose>
                                    </productValue>
                                    <selected>true</selected>
                                    <displayedValue><xsl:value-of select="$displayedValue"/></displayedValue>
                                </valueItem>

                            </xsl:otherwise>
                        </xsl:choose>

                    </availableValues>
                </resourceType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

</xsl:stylesheet>