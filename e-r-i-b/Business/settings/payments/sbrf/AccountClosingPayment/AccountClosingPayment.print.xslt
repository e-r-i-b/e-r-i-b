<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                              xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                              xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                              xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                              extension-element-prefixes="phizic">
    <xsl:import href="commonFieldTypes.template.xslt"/>
    <xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="postConfirmCommission" select="postConfirmCommission"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'printCheck'">
				<xsl:apply-templates mode="printCheck"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="printCheck">
        <xsl:variable name="toResourceType"   select="toResourceType"/>

        <xsl:call-template name="printTitle">
            <xsl:with-param name="value">ЗАКРЫТИЕ ВКЛАДА ПУТЕМ ПЕРЕВОДА СРЕДСТВ</xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">ДАТА ОПЕРАЦИИ</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="documentDate"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">ВРЕМЯ ОПЕРАЦИИ (МСК)</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="operationTime"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">ИДЕНТИФИКАТОР ОПЕРАЦИИ</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="documentNumber"/></xsl:with-param>
        </xsl:call-template>
        <br/>
        <xsl:call-template name="printText">
            <xsl:with-param name="value">ОТПРАВИТЕЛЬ:</xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="printText">
            <xsl:with-param name="value">
                <xsl:value-of select="fromAccountName"/>,
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="printText">
            <xsl:with-param name="value">
                <xsl:value-of select="fromAccountSelect"/>
            </xsl:with-param>
        </xsl:call-template>
        <br/>

        <xsl:if test="contains($toResourceType, 'Account')">
            <xsl:call-template name="printText">
                <xsl:with-param name="value">ПОЛУЧАТЕЛЬ:</xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="printText">
                <xsl:with-param name="value"><xsl:value-of  select="toAccountName"/>,</xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="printText">
                <xsl:with-param name="value"><xsl:value-of  select="toAccountSelect"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        
        <xsl:if test="contains($toResourceType, 'Card')">
            <xsl:call-template name="printText">
                <xsl:with-param name="value">ПОЛУЧАТЕЛЬ:</xsl:with-param>
            </xsl:call-template>
            <xsl:variable name="toAccountSelect"><xsl:value-of select="toAccountSelect"/></xsl:variable>
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">№ КАРТЫ</xsl:with-param>
                <xsl:with-param name="value"><xsl:value-of select="mask:getCutCardNumberPrint($toAccountSelect)"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <br/>


        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">СУММА</xsl:with-param>
            <xsl:with-param name="value">
                <xsl:value-of select="amount"/>
                &nbsp;
                <xsl:value-of select="fromResourceCurrency"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="$postConfirmCommission">
            <xsl:call-template name="writeDownOperationsCheck">
                <xsl:with-param name="fromResourceCurrency" select="fromResourceCurrency"/>
            </xsl:call-template>
        </xsl:if>

        <br/>
    </xsl:template>

    <xsl:template name="printTitle">
        <xsl:param name="value"/>
        <div class="checkSize title"><xsl:copy-of select="$value"/></div>
        <br/>
    </xsl:template>

    <xsl:template name="printText">
        <xsl:param name="value"/>
        <div class="checkSize"><xsl:copy-of select="$value"/></div>
    </xsl:template>

    <xsl:template name="paramsCheck">
        <xsl:param name="title"/>
        <xsl:param name="value"/>
        <div class="checkSize"><xsl:copy-of select="$title"/>: <xsl:copy-of select="$value"/></div>
    </xsl:template>
  
</xsl:stylesheet>