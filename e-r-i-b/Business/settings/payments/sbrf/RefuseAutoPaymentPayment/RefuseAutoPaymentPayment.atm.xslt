<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:recipient="java://com.rssl.phizic.web.util.RecipientUtil"
                xmlns:xalan = "http://xml.apache.org/xalan"
                exclude-result-prefixes="xalan">
    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId" select="''"/>
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
            <form>RefuseAutoPaymentPayment</form>
            <RefuseAutoPaymentPayment>
                <xsl:call-template name="initialData"/>
            </RefuseAutoPaymentPayment>
        </initialData>
    </xsl:template>

    <xsl:template name="initialData">
        <xsl:variable name="cardNumber" select="cardNumber"/>
        <xsl:variable name="card" select="document('cards.xml')/entity-list/entity[@key=$cardNumber]"/>
        <cardId>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">cardId</xsl:with-param>
                <xsl:with-param name="title">Идентификатор карты</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="$card/field[@name='cardLinkId']"/>
            </xsl:call-template>
        </cardId>
        <xsl:call-template name="paymentBody"/>
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
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>RefuseAutoPaymentPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
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

            <RefuseAutoPaymentDocument>
                <xsl:call-template name="document"/>
             </RefuseAutoPaymentDocument>
        </document>
    </xsl:template>

    <xsl:template name="document">
        <cardId>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">cardId</xsl:with-param>
                <xsl:with-param name="title">Идентификатор карты</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="cardId"/>
            </xsl:call-template>
        </cardId>
        <xsl:call-template name="paymentBody"/>
    </xsl:template>

    <xsl:template name="paymentBody">
        <card>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">card</xsl:with-param>
                <xsl:with-param name="title">Номер карты</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="mask:getCutCardNumber(cardNumber)"/>
            </xsl:call-template>
        </card>
        <receiverName>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">receiverName</xsl:with-param>
                <xsl:with-param name="title">Наименование получателя</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="receiverName"/>
            </xsl:call-template>
        </receiverName>
        <requisite>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">requisite</xsl:with-param>
                <xsl:with-param name="title">Значение ключевого поля</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="requisite"/>
            </xsl:call-template>
        </requisite>

        <xsl:if test="string-length(sellAmount) > 0">
            <amount>
                <xsl:call-template name="moneyField">
                    <xsl:with-param name="name">sellAmount</xsl:with-param>
                    <xsl:with-param name="title">Сумма</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="sellAmount"/>
                </xsl:call-template>
            </amount>
        </xsl:if>

        <executionEventType>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">executionEventType</xsl:with-param>
                <xsl:with-param name="title">Тип автоплатежа</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="executionEventType"/>
            </xsl:call-template>
        </executionEventType>

        <xsl:if test="string-length(autoPaymentFloorLimit) > 0 and executionEventType = 'REDUSE_OF_BALANCE'">
            <autoPaymentFloorLimit>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">autoPaymentFloorLimit</xsl:with-param>
                    <xsl:with-param name="title">Пороговый лимит</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="autoPaymentFloorLimit"/>
                </xsl:call-template>
            </autoPaymentFloorLimit>
        </xsl:if>

        <xsl:if test="string-length(autoPaymentFloorCurrency) > 0 and executionEventType = 'REDUSE_OF_BALANCE'">
            <autoPaymentFloorCurrency>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">autoPaymentFloorCurrency</xsl:with-param>
                    <xsl:with-param name="title">Валюта порогового лимита (для порогового регулярного платежа)</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="autoPaymentFloorCurrency"/>
                </xsl:call-template>
            </autoPaymentFloorCurrency>
        </xsl:if>

        <xsl:if test="executionEventType = 'REDUSE_OF_BALANCE'">
            <xsl:variable name="totalPeriod">
                <xsl:choose>
                    <xsl:when test="string-length(autoPaymentTotalAmountPeriod) > 0">
                        <xsl:value-of select="autoPaymentTotalAmountPeriod"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="recipient:getAutoPayTotalAmountPeriod(linkId)"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:variable>
            <xsl:variable name="totalLimit">
                <xsl:if test="string-length(autoPaymentTotalAmountPeriod) > 0">
                    <xsl:value-of select="autoPaymentTotalAmountLimit"/>
                </xsl:if>
            </xsl:variable>

            <xsl:if test="string-length($totalPeriod) > 0">
                <autoPaymentTotalAmountLimit>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">autoPaymentTotalAmountLimit</xsl:with-param>
                        <xsl:with-param name="title">
                            Максимальная сумма платежей в
                            <xsl:call-template name="periodTotalAmount2text">
                                <xsl:with-param name="code" select="$totalPeriod"/>
                            </xsl:call-template>
                        </xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$totalLimit"/>
                    </xsl:call-template>
                </autoPaymentTotalAmountLimit>
                <autoPaymentTotalAmountCurrency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">autoPaymentTotalAmountCurrency</xsl:with-param>
                        <xsl:with-param name="title">Валюта максимальной суммы платетежей (для порогового регулярного платежа)</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="autoPaymentTotalAmountCurrency"/>
                    </xsl:call-template>
                </autoPaymentTotalAmountCurrency>
            </xsl:if>
        </xsl:if>

        <xsl:variable name="isPeriodic" select="isPeriodic"/>
        <xsl:if test="$isPeriodic='true'">
            <autoPaymentStartDate>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">autoPaymentStartDate</xsl:with-param>
                    <xsl:with-param name="title">Дата оформления заявки на автоплатеж</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="autoPaymentStartDate"/>
                </xsl:call-template>
            </autoPaymentStartDate>
        </xsl:if>

        <autoPaymentName>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">autoPaymentName</xsl:with-param>
                <xsl:with-param name="title">Наименование автоплатежа</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="autoPaymentName"/>
            </xsl:call-template>
        </autoPaymentName>
    </xsl:template>

</xsl:stylesheet>