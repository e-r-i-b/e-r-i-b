<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                exclude-result-prefixes="xalan mask ph">
    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="templateAvailable" select="''"/>
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
            <form>RefuseLongOffer</form>
            <RefuseLongOffer>
                <xsl:call-template name="fields"/>
            </RefuseLongOffer>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>RefuseLongOffer</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <id><xsl:value-of select="$documentId"/></id>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
            </xsl:if>
            <xsl:if test="string-length($templateAvailable)>0">
                <templateAvailable><xsl:value-of select="$templateAvailable"/></templateAvailable>
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
            <RefuseLongOfferDocument>
                <xsl:call-template name="fields"/>
            </RefuseLongOfferDocument>
        </document>
    </xsl:template>

    <xsl:template name="fields">
        <xsl:variable name="type" select="longOfferType"/>
        <longOfferType>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">longOfferType</xsl:with-param>
                <xsl:with-param name="title">Вид операции</xsl:with-param>
                <xsl:with-param name="required" select="false()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="document('operationDictionary')/entity-list/entity[@key=$type]/text()"/>
            </xsl:call-template>
        </longOfferType>
        <longOfferId>
            <xsl:call-template name="integerField">
                <xsl:with-param name="name">longOfferId</xsl:with-param>
                <xsl:with-param name="title">Номер документа</xsl:with-param>
                <xsl:with-param name="required" select="false()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="longOfferId"/>
            </xsl:call-template>
        </longOfferId>
        <receiver>
            <xsl:choose>
                <xsl:when test="receiverResourceType = 'CARD'">
                    <card>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverResource</xsl:with-param>
                            <xsl:with-param name="title">Номер карты получателя</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverResource"/>
                        </xsl:call-template>
                    </card>
                </xsl:when>
                <xsl:when test="receiverResourceType = 'ACCOUNT'">
                    <account>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverResource</xsl:with-param>
                            <xsl:with-param name="title">Номер счета получателя</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverResource"/>
                        </xsl:call-template>
                    </account>
                </xsl:when>
                <xsl:otherwise>
                    <loan>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverResource</xsl:with-param>
                            <xsl:with-param name="title">Кредит</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverResource"/>
                        </xsl:call-template>
                    </loan>
                    <loanName>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverResourceName</xsl:with-param>
                            <xsl:with-param name="title">Наименование кредита</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverResourceName"/>
                        </xsl:call-template>
                    </loanName>
                </xsl:otherwise>
            </xsl:choose>
        </receiver>
        <payment>
            <payResource>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">payResource</xsl:with-param>
                    <xsl:with-param name="title">Перевести с</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="payerResource"/>
                </xsl:call-template>
            </payResource>
            <payResourceName>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">payResourceName</xsl:with-param>
                    <xsl:with-param name="title">Название ресурса списания</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="payerResourceName"/>
                </xsl:call-template>
            </payResourceName>
            <amount>
                <xsl:call-template name="moneyField">
                    <xsl:with-param name="name">amount</xsl:with-param>
                    <xsl:with-param name="title">Сумма перевода</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="amount"/>
                </xsl:call-template>
            </amount>
            <xsl:if test="amountCurrency != ''">
                <currency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">amountCurrency</xsl:with-param>
                        <xsl:with-param name="title">Валюта перевода</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="amountCurrency"/>
                    </xsl:call-template>
                </currency>
            </xsl:if>
        </payment>
        <longOfferDetails>
            <startDate>
                <xsl:call-template name="dateField">
                    <xsl:with-param name="name">startDate</xsl:with-param>
                    <xsl:with-param name="title">Дата начала</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="startDate"/>
                </xsl:call-template>
            </startDate>
            <endDate>
                <xsl:call-template name="dateField">
                    <xsl:with-param name="name">endDate</xsl:with-param>
                    <xsl:with-param name="title">Дата окончания</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="endDate"/>
                </xsl:call-template>
            </endDate>
            <executionEventType>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">executionEventType</xsl:with-param>
                    <xsl:with-param name="title">Повторяется</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="executionEventType"/>
                </xsl:call-template>
            </executionEventType>
            <office>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">longOfferOffice</xsl:with-param>
                    <xsl:with-param name="title">Оформлено в</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="longOfferOffice"/>
                </xsl:call-template>
            </office>
            <name>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">longOfferName</xsl:with-param>
                    <xsl:with-param name="title">Название</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="longOfferName"/>
                </xsl:call-template>
            </name>
        </longOfferDetails>
    </xsl:template>

</xsl:stylesheet>
