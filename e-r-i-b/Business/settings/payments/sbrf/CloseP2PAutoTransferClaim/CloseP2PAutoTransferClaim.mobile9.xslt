<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                exclude-result-prefixes="xalan mask">

    <xsl:import href="billingFieldTypes.mobile.template.xslt"/>
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:import href="P2PAutoTransferClaimFields.mobile.template.xslt"/>

    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="mobileApiVersion"/>
    <xsl:param name="changedFields"/>
    <xsl:param name="mode" select="'eidt'"/>
    <xsl:param name="documentStatus" select="'documentStatus'"/>
    <xsl:param name="documentId" select="''"/>

    <xsl:param name="longOffer" select="true()"/>
    <xsl:param name="personAvailable" select="true()"/>

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
            <form>CloseP2PAutoTransferClaim</form>
            <CloseP2PAutoTransferClaim>
                <xsl:call-template name="autoTransferClaimFields"/>
            </CloseP2PAutoTransferClaim>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>CloseP2PAutoTransferClaim</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>

            <CloseP2PAutoTransferClaimDocument>
                <xsl:call-template name="autoTransferClaimFields"/>
            </CloseP2PAutoTransferClaimDocument>
        </document>
    </xsl:template>

</xsl:stylesheet>