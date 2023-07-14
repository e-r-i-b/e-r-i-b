<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                exclude-result-prefixes="xalan">
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="mobileApiVersion"/>
    <xsl:param name="changedFields"/>

    <xsl:template match="/">
        <xsl:apply-templates mode="view"/>
	</xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>RollbackOrderClaim</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>

            <RollbackOrderClaimDocument>
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
                        <xsl:with-param name="title">Дата возврата</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentDate"/>
                    </xsl:call-template>
                </documentDate>
                <recalledDocumentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">recalledDocumentNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер документа оплаты заказа</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="recalledDocumentNumber"/>
                    </xsl:call-template>
                </recalledDocumentNumber>
                <xsl:if test="string-length(amount)>0">
                    <amount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">amount</xsl:with-param>
                            <xsl:with-param name="title">Сумма возврата</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="amount"/>
                        </xsl:call-template>
                    </amount>
                    <currency>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">amountCurrency</xsl:with-param>
                            <xsl:with-param name="title">Валюта</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="amountCurrency"/>
                        </xsl:call-template>
                    </currency>
                </xsl:if>

            </RollbackOrderClaimDocument>
        </document>
    </xsl:template>

</xsl:stylesheet>