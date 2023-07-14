<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:spu="java://com.rssl.phizic.web.util.ServiceProviderUtil"
                exclude-result-prefixes="xalan mask spu">
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="checkAvailable" select="''"/>
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
            <form>ExternalProviderPayment</form>
            <ExternalProviderPayment>
                <xsl:call-template name="payment"/>
            </ExternalProviderPayment>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>ExternalProviderPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
            </xsl:if>

            <ExternalProviderPaymentDocument>
                <xsl:call-template name="payment"/>
                <xsl:if test="string-length(promoCode)>0">
                    <promoCode>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">promoCode</xsl:with-param>
                            <xsl:with-param name="title">Промо-код</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="promoCode"/>
                        </xsl:call-template>
                    </promoCode>
                </xsl:if>
            </ExternalProviderPaymentDocument>
        </document>
    </xsl:template>

    <xsl:template name="payment">
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
        <receiverName>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">receiverName</xsl:with-param>
                <xsl:with-param name="title">Получатель</xsl:with-param>
                <xsl:with-param name="required" select="false()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="receiverName"/>
            </xsl:call-template>
        </receiverName>
        <fromResource>
            <xsl:call-template name="resourceField">
                <xsl:with-param name="name">fromResource</xsl:with-param>
                <xsl:with-param name="title">Оплата с</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="$mode = 'edit'"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="cardNumber" select="fromAccountSelect"/>
                <xsl:with-param name="link" select="fromResource"/>
                <xsl:with-param name="isView" select="$mode = 'view'"/>
            </xsl:call-template>
        </fromResource>
        <amount>
            <xsl:call-template name="moneyField">
                <xsl:with-param name="name">amount</xsl:with-param>
                <xsl:with-param name="title">Сумма</xsl:with-param>
                <xsl:with-param name="required" select="false()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="amount"/>
            </xsl:call-template>
        </amount>
        <currency>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">currency</xsl:with-param>
                <xsl:with-param name="title">Валюта</xsl:with-param>
                <xsl:with-param name="required" select="false()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="currency"/>
            </xsl:call-template>
        </currency>
        <recIdentifier>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">RecIdentifier</xsl:with-param>
                <xsl:with-param name="title">Номер заказа</xsl:with-param>
                <xsl:with-param name="required" select="false()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="RecIdentifier"/>
            </xsl:call-template>
        </recIdentifier>

        <xsl:variable name="fields" select="document('internetOrderFields.xml')/fields/field"/>
        <xsl:if test="count($fields)>0">
            <itemList>
                <xsl:for-each select="$fields">
                    <item>
                        <xsl:if test="string-length(name)>0">
                            <name><xsl:value-of select="name"/></name>
                        </xsl:if>
                        <xsl:if test="string-length(description)>0">
                            <description><xsl:value-of select="description"/></description>
                        </xsl:if>
                        <xsl:if test="string-length(count)>0">
                            <count><xsl:value-of select="count"/></count>
                        </xsl:if>
                        <xsl:if test="string-length(price)>0">
                            <price>
                                <xsl:call-template name="simpleMoneyType">
                                    <xsl:with-param name="amount" select="price/amount"/>
                                    <xsl:with-param name="currency" select="price/currency"/>
                                </xsl:call-template>
                            </price>
                        </xsl:if>
                    </item>
                </xsl:for-each>
            </itemList>
        </xsl:if>

        <xsl:variable name="refund" select="document('refundGoods.xml')/RefundList/Refund"/>
        <xsl:if test="count($refund)>0">
            <refundList>
                <xsl:for-each select="$refund">
                    <refund>
                        <xsl:variable name="refundFields" select="./fields/field"/>
                        <xsl:if test="count($refundFields)>0">
                            <itemList>
                                <xsl:for-each select="$refundFields">
                                    <item>
                                        <xsl:if test="string-length(name)>0">
                                            <name><xsl:value-of select="name"/></name>
                                        </xsl:if>
                                        <xsl:if test="string-length(description)>0">
                                            <description><xsl:value-of select="description"/></description>
                                        </xsl:if>
                                        <xsl:if test="string-length(count)>0">
                                            <count><xsl:value-of select="count"/></count>
                                        </xsl:if>
                                        <xsl:if test="string-length(price)>0">
                                            <price>
                                                <xsl:call-template name="simpleMoneyType">
                                                    <xsl:with-param name="amount" select="price/amount"/>
                                                    <xsl:with-param name="currency" select="price/currency"/>
                                                </xsl:call-template>
                                            </price>
                                        </xsl:if>
                                    </item>
                                </xsl:for-each>
                            </itemList>
                        </xsl:if>
                        <total>
                            <xsl:call-template name="simpleMoneyType">
                                <xsl:with-param name="amount" select="Price/Amount"/>
                                <xsl:with-param name="currency" select="Price/Currency"/>
                            </xsl:call-template>
                        </total>
                    </refund>
                </xsl:for-each>
            </refundList>
        </xsl:if>
    </xsl:template>

    <xsl:template name="resourceField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="cardNumber"/> <!--номер карты-->
        <xsl:param name="link"/> <!--код карты-->
        <xsl:param name="isView" select="false()"/> <!--false - редактирование, true - просмотр-->

        <xsl:variable name="activeCardsDictionaryName">
            <xsl:choose>
                <xsl:when test="spu:isCreditCardSupported(recipient)">active-not-virtual-cards.xml</xsl:when>
                <xsl:otherwise>active-not-virtual-not-credit-cards.xml</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="activeCards" select="document($activeCardsDictionaryName)/entity-list/*"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'resource'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <resourceType>
                    <xsl:if test="count($activeCards)>0">
                        <availableValues>
                            <xsl:for-each select="$activeCards">
                                <xsl:variable name="selected" select="$cardNumber=./@key or $link=./field[@name='code']/text()"/>
                                <xsl:if test="not($isView) or $selected">
                                    <valueItem>
                                        <value><xsl:value-of select="./field[@name='code']/text()"/></value>
                                        <selected><xsl:value-of select="string($selected)"/></selected>
                                        <displayedValue>
                                            <xsl:value-of select="mask:getCutCardNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                                        </displayedValue>
                                        <currency><xsl:value-of select="field[@name='currencyCode']"/></currency>
                                    </valueItem>
                                </xsl:if>
                            </xsl:for-each>
                        </availableValues>
                    </xsl:if>
                </resourceType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

</xsl:stylesheet>