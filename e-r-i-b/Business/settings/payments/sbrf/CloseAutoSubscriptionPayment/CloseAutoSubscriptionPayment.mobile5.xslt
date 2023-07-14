<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                exclude-result-prefixes="xalan mask au">
    <xsl:import href="billingFieldTypes.mobile.template.xslt"/>
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:import href="AutoSubscriptionClaimFields.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="mobileApiVersion"/>
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
            <form>CloseAutoSubscriptionPayment</form>
            <CloseAutoSubscriptionPayment>
                <xsl:call-template name="autoSubClaimFields"/>
            </CloseAutoSubscriptionPayment>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view-auto-sub">
        <document>
            <form>CloseAutoSubscriptionPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>

            <CloseAutoSubscriptionDocument>
                <xsl:call-template name="autoSubClaimFields"/>
            </CloseAutoSubscriptionDocument>
        </document>
    </xsl:template>
</xsl:stylesheet>