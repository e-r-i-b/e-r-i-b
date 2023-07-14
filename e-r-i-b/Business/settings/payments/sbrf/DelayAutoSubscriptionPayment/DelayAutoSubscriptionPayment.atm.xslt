<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                exclude-result-prefixes="xalan mask au">
    <xsl:import href="billingFieldTypes.atm.template.xslt"/>
    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
    <xsl:import href="AutoSubscriptionClaimFields.atm.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="changedFields"/>
    
    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'edit'">
                <xsl:apply-templates mode="edit-auto-sub"/>
            </xsl:when>
            <xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="view-auto-sub"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit-auto-sub">
        <initialData>
            <form>DelayAutoSubscriptionPayment</form>
            <DelayAutoSubscriptionPayment>
                <xsl:call-template name="autoSubClaimFields"/>
            </DelayAutoSubscriptionPayment>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view-auto-sub">
        <document>
            <form>DelayAutoSubscriptionPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
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

            <DelayAutoSubscriptionDocument>
                <xsl:call-template name="autoSubClaimFields"/>
            </DelayAutoSubscriptionDocument>
        </document>
    </xsl:template>
</xsl:stylesheet>