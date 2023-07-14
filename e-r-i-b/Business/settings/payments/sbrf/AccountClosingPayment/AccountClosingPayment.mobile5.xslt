<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                exclude-result-prefixes="xalan mask au">
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="mobileApiVersion"/>
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
        <initialData>
            <form>AccountClosingPayment</form>
            <AccountClosingPayment>
                <xsl:variable name="activeAccounts" select="document('active-credit-accounts.xml')/entity-list/*"/>
                <xsl:variable name="possibleClosingAccounts" select="document('possible-closing-accounts.xml')/entity-list/*"/>
                <xsl:variable name="activeCards" select="document('possible-transfer-cards-at-closing.xml')/entity-list/*"/>

                <closingDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">closingDate</xsl:with-param>
                        <xsl:with-param name="title">Дата закрытия</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="closingDate"/>
                    </xsl:call-template>
                </closingDate>
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
                <xsl:variable name="fromResource" select="fromResource"/>
                <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
                <xsl:variable name="accountNode" select="$possibleClosingAccounts[$fromResource=./field[@name='code']  or $fromAccountSelect/text()= ./@key]"/>
                <xsl:variable name="fromResourceCode">
                    <xsl:choose>
                        <xsl:when test="$accountNode">
                            <xsl:value-of select="$accountNode/field[@name='code']/text()"/>
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
                        <xsl:with-param name="activeAccounts" select="$possibleClosingAccounts"/>
                        <xsl:with-param name="linkId" select="$fromResourceCode"/>
                    </xsl:call-template>
                </fromResource>

                <xsl:variable name="toResource" select="toResource"/>
                <xsl:variable name="toAccountSelect" select="toAccountSelect"/>
                <xsl:variable name="toAccountNode" select="$activeAccounts[$toResource=./field[@name='code'] or $toAccountSelect/text() = ./@key]"/>
                <xsl:variable name="toCardsNode" select="$activeCards[$toResource=./field[@name='code'] or $toAccountSelect/text() = ./@key]"/>
                <xsl:variable name="toResourceCode">
                    <xsl:choose>
                        <xsl:when test="$toAccountNode">
                            <xsl:value-of select="$toAccountNode/field[@name='code']/text()"/>
                        </xsl:when>
                        <xsl:when test="$toCardsNode">
                            <xsl:value-of select="$toCardsNode/field[@name='code']/text()"/>
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
                        <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
                        <xsl:with-param name="cards" select="$activeCards"/>
                        <xsl:with-param name="linkId" select="$toResourceCode"/>
                    </xsl:call-template>
                </toResource>
                <chargeOffAmount>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">amount</xsl:with-param>
                        <xsl:with-param name="title">Сумма списания</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="amount"/>
                    </xsl:call-template>
                </chargeOffAmount>
                <destinationAmount>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">destinationAmount</xsl:with-param>
                        <xsl:with-param name="title">Сумма зачисления</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="destinationAmount"/>
                    </xsl:call-template>
                </destinationAmount>
                <course>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">course</xsl:with-param>
                        <xsl:with-param name="title">Курс конверсии</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="course"/>
                    </xsl:call-template>
                </course>
            </AccountClosingPayment>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <xsl:variable name="activeAccounts" select="document('active-credit-accounts.xml')/entity-list/*"/>
        <xsl:variable name="possibleClosingAccounts" select="document('possible-closing-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards" select="document('possible-transfer-cards-at-closing.xml')/entity-list/*"/>

        <document>
            <form>AccountClosingPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>

            <AccountClosingPaymentDocument>

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

                <xsl:variable name="fromResourceCode" select="fromResourceCode"/>

                <xsl:variable name="fromDisplayedValue">
                    <xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/> [<xsl:value-of select="fromAccountName"/>]
                </xsl:variable>

                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Ресурс списания</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="selectedResource" select="$fromResourceCode"/>
                        <xsl:with-param name="displayedValue" select="$fromDisplayedValue"/>
                    </xsl:call-template>
                </fromResource>

                <xsl:variable name="toResource" select="toResource"/>
                <xsl:variable name="toAccountSelect" select="toAccountSelect"/>
                <xsl:variable name="toAccountNode" select="$activeAccounts[$toResource=./field[@name='code'] or $toAccountSelect/text() = ./@key]"/>
                <xsl:variable name="toCardsNode" select="$activeCards[$toResource=./field[@name='code'] or $toAccountSelect/text() = ./@key]"/>
                <xsl:variable name="toResourceCode">
                    <xsl:choose>
                        <xsl:when test="$toAccountNode">
                            <xsl:value-of select="$toAccountNode/field[@name='code']/text()"/>
                        </xsl:when>
                        <xsl:when test="$toCardsNode">
                            <xsl:value-of select="$toCardsNode/field[@name='code']/text()"/>
                        </xsl:when>
                    </xsl:choose>
                </xsl:variable>
                <toResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">toResource</xsl:with-param>
                        <xsl:with-param name="title">Ресурс зачисления</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
                        <xsl:with-param name="cards" select="$activeCards"/>
                        <xsl:with-param name="linkId" select="$toResourceCode"/>
                    </xsl:call-template>
                </toResource>

                <xsl:if test="string-length(course) > 0">
                    <course>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">course</xsl:with-param>
                            <xsl:with-param name="title">Курс конверсии</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="course"/>
                        </xsl:call-template>
                    </course>
                </xsl:if>

                <closingDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">closingDate</xsl:with-param>
                        <xsl:with-param name="title">Дата закрытия</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="closingDate"/>
                    </xsl:call-template>
                </closingDate>

                <chargeOffAmount>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">amount</xsl:with-param>
                        <xsl:with-param name="title">Сумма списания</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="amount"/>
                    </xsl:call-template>
                </chargeOffAmount>

                <destinationAmount>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">destinationAmount</xsl:with-param>
                        <xsl:with-param name="title">Сумма зачисления</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="destinationAmount"/>
                    </xsl:call-template>
                </destinationAmount>
            </AccountClosingPaymentDocument>

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
        <xsl:param name="cards"/>
        <xsl:param name="activeCardsNotCredit"/>
        <xsl:param name="linkId"/>
        <xsl:param name="selectedResource"/>
        <xsl:param name="displayedValue"/>
        <xsl:variable name="value">
            <resourceType>
                <availableValues>
                    <xsl:if test="$activeAccounts != ''">
                        <xsl:call-template name="availableValue">
                            <xsl:with-param name="resource" select="$activeAccounts"/>
                            <xsl:with-param name="linkId" select="$linkId"/>
                            <xsl:with-param name="type">account</xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:choose>
                        <xsl:when test="$name = 'fromResource'">
                            <xsl:if test="$activeCardsNotCredit != ''">
                                <xsl:call-template name="availableValue">
                                    <xsl:with-param name="resource" select="$activeCardsNotCredit"/>
                                    <xsl:with-param name="linkId" select="$linkId"/>
                                    <xsl:with-param name="type">card</xsl:with-param>
                                </xsl:call-template>
                            </xsl:if>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:if test="$cards != ''">
                                <xsl:call-template name="availableValue">
                                    <xsl:with-param name="resource" select="$cards"/>
                                    <xsl:with-param name="linkId" select="$linkId"/>
                                    <xsl:with-param name="type">card</xsl:with-param>
                                </xsl:call-template>
                            </xsl:if>
                        </xsl:otherwise>
                    </xsl:choose>

                    <xsl:if test="string-length($selectedResource)>0">
                        <valueItem>
                            <value><xsl:value-of select="$selectedResource"/></value>
                            <selected>true</selected>
                            <xsl:if test="string-length($displayedValue)>0">
                                <displayedValue><xsl:value-of select="$displayedValue"/></displayedValue>
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
                            <xsl:otherwise>
                                <xsl:value-of select="au:getFormattedAccountNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                            </xsl:otherwise>
                        </xsl:choose>
                    </displayedValue>
                </valueItem>
            </xsl:for-each>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>