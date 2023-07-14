<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:imau="java://com.rssl.phizic.business.ima.IMAccountUtils"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:mu="java://com.rssl.phizic.business.util.MoneyUtil"
                exclude-result-prefixes="xalan mask au imau pu ph mu">
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="mobileApiVersion"/>
    <xsl:param name="changedFields"/>
    <xsl:param name="postConfirmCommission" select="postConfirmCommission"/> <!-- поддержка отображения комисий из ЦОД -->
    <!--Тарифный план клиента-->
    <xsl:variable name="tarifPlanCodeType">
        <xsl:value-of select="ph:getActivePersonTarifPlanCode()"/>
    </xsl:variable>
    <!--Показывать ли стандартный курс для тарифного плана клиента-->
    <xsl:variable name="needShowStandartRate">
        <xsl:value-of select="ph:needShowStandartRate($tarifPlanCodeType)"/>
    </xsl:variable>

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
        <xsl:variable name="activeDebitAccounts" select="document('stored-active-debit-rur-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCreditAccounts" select="document('stored-active-rur-credit-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeDebitCards" select="document('stored-active-rur-not-credit-cards.xml')/entity-list/*"/>
        <xsl:variable name="activeIMAccounts" select="document('stored-active-imaccounts.xml')/entity-list/*"/>

        <initialData>
            <form>IMAPayment</form>
            <IMAPayment>
                <xsl:variable name="toResource" select="toResource"/>
                <xsl:variable name="toAccountSelect" select="toAccountSelect"/>
                <xsl:variable name="toAccountNode" select="$activeCreditAccounts[$toResource=./field[@name='code'] or $toAccountSelect/text() = ./@key]"/>
                <xsl:variable name="toCardNode" select="$activeDebitCards[$toResource=./field[@name='code'] or $toAccountSelect/text() = ./@key]"/>
                <xsl:variable name="IMANodeTo" select="$activeIMAccounts[$toResource=./field[@name='code'] or $toAccountSelect/text() = ./@key]"/>
                <xsl:variable name="toResourceCode">
                    <xsl:choose>
                        <xsl:when test="$toAccountNode">
                            <xsl:value-of select="$toAccountNode/field[@name='code']/text()"/>
                        </xsl:when>
                        <xsl:when test="$toCardNode">
                            <xsl:value-of select="$toCardNode/field[@name='code']/text()"/>
                        </xsl:when>
                        <xsl:when test="$IMANodeTo">
                            <xsl:value-of select="$IMANodeTo/field[@name='code']/text()"/>
                        </xsl:when>
                    </xsl:choose>
                </xsl:variable>
                <toResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">toResource</xsl:with-param>
                        <xsl:with-param name="title">Ресурс зачисления</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="activeAccounts" select="$activeCreditAccounts"/>
                        <xsl:with-param name="activeCardsNotCredit" select="$activeDebitCards"/>
                        <xsl:with-param name="activeIMAccounts" select="$activeIMAccounts"/>
                        <xsl:with-param name="linkId" select="$toResourceCode"/>
                    </xsl:call-template>
                </toResource>

                <xsl:variable name="fromResource" select="fromResource"/>
                <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
                <xsl:variable name="accountNode" select="$activeDebitAccounts[$fromResource=./field[@name='code']  or $fromAccountSelect/text()= ./@key]"/>
                <xsl:variable name="cardNode" select="$activeDebitCards[$fromResource=./field[@name='code']  or $fromAccountSelect/text()= ./@key]"/>
                <xsl:variable name="IMANodeFrom" select="$activeIMAccounts[$fromResource=./field[@name='code']  or $fromAccountSelect/text()= ./@key]"/>
                <xsl:variable name="fromResourceCode">
                    <xsl:choose>
                        <xsl:when test="$accountNode">
                            <xsl:value-of select="$accountNode/field[@name='code']/text()"/>
                        </xsl:when>
                        <xsl:when test="$cardNode">
                            <xsl:value-of select="$cardNode/field[@name='code']/text()"/>
                        </xsl:when>
                        <xsl:when test="$IMANodeFrom">
                            <xsl:value-of select="$IMANodeFrom/field[@name='code']/text()"/>
                        </xsl:when>
                    </xsl:choose>
                </xsl:variable>
                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Ресурс списания</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="activeAccounts" select="$activeDebitAccounts"/>
                        <xsl:with-param name="activeCardsNotCredit" select="$activeDebitCards"/>
                        <xsl:with-param name="activeIMAccounts" select="$activeIMAccounts"/>
                        <xsl:with-param name="linkId" select="$fromResourceCode"/>
                    </xsl:call-template>
                </fromResource>

                <documentDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">documentDate</xsl:with-param>
                        <xsl:with-param name="title">Дата документа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentDate"/>
                    </xsl:call-template>
                </documentDate>

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

                <buyAmount>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">buyAmount</xsl:with-param>
                        <xsl:with-param name="title">Сумма зачисления</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="buyAmount"/>
                    </xsl:call-template>
                </buyAmount>

                <sellAmount>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">sellAmount</xsl:with-param>
                        <xsl:with-param name="title">Сумма списания</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="sellAmount"/>
                    </xsl:call-template>
                </sellAmount>

                <exactAmount>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">exactAmount</xsl:with-param>
                        <xsl:with-param name="title">Признак, обозначающий какое из полей суммы заполнил пользователь</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="exactAmount"/>
                    </xsl:call-template>
                </exactAmount>

            </IMAPayment>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <document>
            <form>IMAPayment</form>

            <status><xsl:value-of select="$documentStatus"/></status>

            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
            </xsl:if>

            <IMAPaymentDocument>

                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер документа</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>

                <documentDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">documentDate</xsl:with-param>
                        <xsl:with-param name="title">Дата документа</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentDate"/>
                    </xsl:call-template>
                </documentDate>

                <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>

                <xsl:variable name="fromDisplayedValue">
                    <xsl:choose>
                        <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                            <xsl:value-of select="au:getFormattedAccountNumber($fromAccountSelect)"/> [<xsl:value-of select="fromAccountName"/>]
                        </xsl:when>
                        <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                            <xsl:value-of select="mask:getCutCardNumber($fromAccountSelect)"/> [<xsl:value-of select="fromAccountName"/>]
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="imau:getFormattedIMAccountNumber($fromAccountSelect)"/> [<xsl:value-of select="fromAccountName"/>]
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Ресурс списания</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="selectedResource" select="fromResourceLink"/>
                        <xsl:with-param name="displayedValue" select="$fromDisplayedValue"/>
                    </xsl:call-template>
                </fromResource>

                <xsl:variable name="toAccountSelect" select="toAccountSelect"/>

                <xsl:variable name="toDisplayedValue">
                    <xsl:choose>
                        <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                            <xsl:value-of select="au:getFormattedAccountNumber($toAccountSelect)"/> [<xsl:value-of select="toAccountName"/>]
                        </xsl:when>
                        <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                            <xsl:value-of select="mask:getCutCardNumber($toAccountSelect)"/> [<xsl:value-of select="toAccountName"/>]
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="imau:getFormattedIMAccountNumber($toAccountSelect)"/> [<xsl:value-of select="toAccountName"/>]
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <toResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">toResource</xsl:with-param>
                        <xsl:with-param name="title">Ресурс зачисления</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="selectedResource" select="toResourceLink"/>
                        <xsl:with-param name="displayedValue" select="$toDisplayedValue"/>
                    </xsl:call-template>
                </toResource>

                <xsl:if test="string-length(buyAmount) > 0">
                    <buyAmount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">buyAmount</xsl:with-param>
                            <xsl:with-param name="title">Сумма зачисления</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="buyAmount"/>
                        </xsl:call-template>
                    </buyAmount>
                </xsl:if>

                <xsl:if test="string-length(sellAmount) > 0">
                    <sellAmount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">sellAmount</xsl:with-param>
                            <xsl:with-param name="title">Сумма списания</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="sellAmount"/>
                        </xsl:call-template>
                    </sellAmount>
                </xsl:if>

                <xsl:choose>
                    <xsl:when test="$postConfirmCommission and number($mobileApiVersion) >= 7.0">
                        <xsl:variable name="operations" select="document('writeDownOperations.xml')/Operations/Operation[./Name != 'Частичная выдача' and ./Name != 'Закрытие счета']"/>  <!-- отображаемые микрооперации списания -->
                        <xsl:call-template name="writeDownOperations">
                            <xsl:with-param name="operations" select="$operations"/>
                        </xsl:call-template>
                    </xsl:when>
                </xsl:choose>

                <xsl:if test="string-length(course) > 0">
                    <xsl:choose>
                        <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
                        <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">

                            <xsl:variable name="operationTitle">
                                <xsl:choose>
                                    <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.IMAccountLink'">покупки</xsl:when>
                                    <xsl:otherwise>продажи</xsl:otherwise>
                                </xsl:choose>
                            </xsl:variable>

                            <!--Поле льготный курс-->
                            <course>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">course</xsl:with-param>
                                    <xsl:with-param name="title">Льготный курс <xsl:value-of select="$operationTitle"/></xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="course"/>
                                </xsl:call-template>
                            </course>

                            <!--Поле обычный курс-->
                            <standartCourse>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">standartCourse</xsl:with-param>
                                    <xsl:with-param name="title">Обычный курс <xsl:value-of select="$operationTitle"/></xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="standartCourse"/>
                                </xsl:call-template>
                            </standartCourse>

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
                                    <gain>
                                        <xsl:call-template name="moneyField">
                                            <xsl:with-param name="name">gain</xsl:with-param>
                                            <xsl:with-param name="title">Моя выгода</xsl:with-param>
                                            <xsl:with-param name="required" select="false()"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="value" select="$courseGainValue"/>
                                        </xsl:call-template>
                                    </gain>
                                </xsl:if>
                            </xsl:if>
                        </xsl:when>
                        <!--В противном случае -->
                        <xsl:otherwise>
                            <course>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">course</xsl:with-param>
                                    <xsl:with-param name="title">Курс покупки/продажи</xsl:with-param>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="course"/>
                                </xsl:call-template>
                            </course>
                        </xsl:otherwise>
                    </xsl:choose>
               </xsl:if>

            </IMAPaymentDocument>

        </document>

    </xsl:template>

    <xsl:template name="resourceField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="activeAccounts"/>
        <xsl:param name="activeCardsNotCredit"/>
        <xsl:param name="linkId"/>
        <xsl:param name="selectedResource"/>
        <xsl:param name="activeIMAccounts"/>
        <xsl:param name="displayedValue"/>
        <xsl:variable name="value">
            <resourceType>
                <availableValues>
                   <xsl:if test="pu:impliesService('IMAPaymentWithAccount')">
                        <xsl:if test="$activeAccounts != ''">
                            <xsl:call-template name="availableValue">
                                <xsl:with-param name="resource" select="$activeAccounts"/>
                                <xsl:with-param name="linkId" select="$linkId"/>
                                <xsl:with-param name="type">account</xsl:with-param>
                                <xsl:with-param name="name" select="$name"/>
                            </xsl:call-template>
                        </xsl:if>
                    </xsl:if>

                    <xsl:if test="$activeIMAccounts != ''">
                        <xsl:call-template name="availableValue">
                            <xsl:with-param name="resource" select="$activeIMAccounts"/>
                            <xsl:with-param name="linkId" select="$linkId"/>
                            <xsl:with-param name="type">imaccount</xsl:with-param>
                                <xsl:with-param name="name" select="$name"/>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:if test="pu:impliesService('IMAPaymentWithCard')">
                        <xsl:if test="$activeCardsNotCredit != ''">
                            <xsl:call-template name="availableValue">
                                <xsl:with-param name="resource" select="$activeCardsNotCredit"/>
                                <xsl:with-param name="linkId" select="$linkId"/>
                                <xsl:with-param name="type">card</xsl:with-param>
                                <xsl:with-param name="name" select="$name"/>
                            </xsl:call-template>
                        </xsl:if>
                    </xsl:if>

                    <xsl:if test="string-length($selectedResource)>0">
                        <valueItem>
                            <value><xsl:value-of select="$selectedResource"/></value>
                            <selected>true</selected>
                            <xsl:if test="string-length($displayedValue)>0">
                                <displayedValue><xsl:value-of select="$displayedValue"/></displayedValue>
                            </xsl:if>
                            <xsl:if test="$name = 'fromResource' and string-length(fromResourceCurrency)>0">
                                <currency><xsl:value-of select="fromResourceCurrency"/></currency>
                            </xsl:if>
                            <xsl:if test="$name = 'toResource' and string-length(toResourceCurrency)>0">
                                <currency><xsl:value-of select="toResourceCurrency"/></currency>
                            </xsl:if>
                        </valueItem>
                    </xsl:if>
                </availableValues>
            </resourceType>
        </xsl:variable>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'resource'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag" select="$value"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="availableValue">
        <xsl:param name="resource"/>
        <xsl:param name="linkId"/>
        <xsl:param name="type"/>
        <xsl:param name="name"/>
        <xsl:if test="count($resource)>0">
            <xsl:for-each select="$resource">
                <valueItem>
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    <value><xsl:value-of select="$id"/></value>
                    <selected><xsl:value-of select="string($linkId=$id)"/></selected>
                    <displayedValue>
                        <xsl:choose>
                            <xsl:when test="$type = 'card'">
                                <xsl:value-of select="mask:getCutCardNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                            </xsl:when>
                            <xsl:when test="$type = 'imaccount'">
                                <xsl:value-of select="imau:getFormattedIMAccountNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="au:getFormattedAccountNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                            </xsl:otherwise>
                        </xsl:choose>
                    </displayedValue>
                    <xsl:if test="$name = 'fromResource' and string-length(fromResourceCurrency)>0">
                        <currency><xsl:value-of select="fromResourceCurrency"/></currency>
                    </xsl:if>
                    <xsl:if test="$name = 'toResource' and string-length(toResourceCurrency)>0">
                        <currency><xsl:value-of select="toResourceCurrency"/></currency>
                    </xsl:if>
                </valueItem>
            </xsl:for-each>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>