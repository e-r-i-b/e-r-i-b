<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                              xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                              xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                              xmlns:ai="java://com.rssl.phizic.common.types.ApplicationInfo"
                              extension-element-prefixes="mask mu ai">
    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="changedFields"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'printDocumentCheck'">
				<xsl:apply-templates mode="printDocumentCheck"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="printDocumentCheck">
        <xsl:variable name="application"><xsl:value-of select="ai:getCurrentApplication()"/></xsl:variable>
        <DocumentCheck>
            <form>AccountClosingPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <title>ЗАКРЫТИЕ ВКЛАДА ПУТЕМ ПЕРЕВОДА СРЕДСТВ</title>
            <xsl:if test="string-length(operationDate)>0">
                <operationDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">operationDate</xsl:with-param>
                        <xsl:with-param name="title">ДАТА ОПЕРАЦИИ</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="operationDate"/>
                    </xsl:call-template>
                </operationDate>
            </xsl:if>
            <xsl:if test="string-length(operationTime)>0">
                <operationTime>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">operationTime</xsl:with-param>
                        <xsl:with-param name="title">время операции (МСК)</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="operationTime"/>
                    </xsl:call-template>
                </operationTime>
            </xsl:if>
            <xsl:if test="string-length(documentNumber)>0">
                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">идентификатор операции</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>
            </xsl:if>

            <AccountClosingPaymentDocumentCheck>

                <fromResource>
                    <xsl:call-template name="resourceCheckField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">ОТПРАВИТЕЛЬ</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="fromResourceCode"/>
                        <xsl:with-param name="displayedValue" select="concat(fromAccountName, ': ', fromAccountSelect)"/>
                    </xsl:call-template>
                </fromResource>

                <xsl:variable name="toAccountSelect"><xsl:value-of select="toAccountSelect"/></xsl:variable>
                <xsl:variable name="displayedValue">
                    <xsl:choose>
                        <xsl:when test="contains(toResourceType, 'Account')">
                            <xsl:value-of select="concat(toAccountName, ': ', $toAccountSelect)"/>
                        </xsl:when>
                        <xsl:when test="contains(toResourceType, 'Card')">
                            <xsl:value-of select="concat('№ КАРТЫ: ', mask:getCutCardNumberPrint($toAccountSelect))"/>
                        </xsl:when>
                    </xsl:choose>
                </xsl:variable>

                <receiver>
                    <xsl:call-template name="resourceCheckField">
                        <xsl:with-param name="name">receiver</xsl:with-param>
                        <xsl:with-param name="title">ПОЛУЧАТЕЛЬ</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="toResourceLink"/>
                        <xsl:with-param name="displayedValue" select="$displayedValue"/>
                    </xsl:call-template>
                </receiver>

                <xsl:variable name="amountString" select="concat(amount, ' ', mu:getCurrencySign(fromResourceCurrency))"/>
                <amount>
                    <xsl:call-template name="stringField">
                            <xsl:with-param name="name">amount</xsl:with-param>
                            <xsl:with-param name="title">СУММА</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="$amountString"/>
                        </xsl:call-template>
                </amount>

            </AccountClosingPaymentDocumentCheck>

        </DocumentCheck>

    </xsl:template>

</xsl:stylesheet>