<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
        <!ENTITY nbsp "&#160;">
        ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:bn="java://com.rssl.phizic.business.util.BankInfoUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:pnu="java://com.rssl.phizic.utils.PhoneNumberUtil"
                xmlns:mpnu="java://com.rssl.phizic.business.util.MaskPhoneNumberUtil"
                xmlns:msk="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:dh="java://com.rssl.phizic.utils.DateHelper"
                extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="mode" select="'view'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:variable name="formData" select="/form-data"/>

	<xsl:template match="/">
        <xsl:apply-templates mode="view"/>
    </xsl:template>

	<xsl:template match="/form-data" mode="view">

        <xsl:choose>
            <xsl:when test="$formData/partial = 'true'">
                <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">Частичное досрочное погашение</xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">Полное досрочное погашение</xsl:with-param>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>


        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">ДАТА ОПЕРАЦИИ</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="operationDate"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">время операции (МСК)</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="operationTime"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">идентификатор операции</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="documentNumber"/></xsl:with-param>
        </xsl:call-template>
        <br/>

        <xsl:call-template name="printText">
            <xsl:with-param name="value"><xsl:value-of select="fromAccountName"/>,</xsl:with-param>
        </xsl:call-template>
        <xsl:choose>
            <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                <xsl:call-template name="printText">
                    <xsl:with-param name="value"><xsl:value-of select="mask:getCutCardNumberPrint(fromAccountSelect)"/></xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="printText">
                    <xsl:with-param name="value"><xsl:value-of  select="fromAccountSelect"/></xsl:with-param>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Дата погашения</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="documentDate"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Сумма погашения</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="amount"/>&nbsp;<xsl:value-of select="fromResourceCurrency"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="stamp"/>

	</xsl:template>

    <xsl:template name="printTitle">
        <xsl:param name="value"/>
        <div class="checkSize title"><xsl:copy-of select="$value"/></div>
        <br/>
    </xsl:template>

    <xsl:template name="paramsCheck">
        <xsl:param name="title"/>
        <xsl:param name="value"/>
        <div class="checkSize"><xsl:copy-of select="$title"/>: <xsl:copy-of select="$value"/></div>
    </xsl:template>

    <xsl:template name="printText">
        <xsl:param name="value"/>
        <div class="checkSize"><xsl:copy-of select="$value"/></div>
    </xsl:template>

    <xsl:template name="stamp">
        <br/>
        <div id="stamp" class="stamp">
            <xsl:value-of select="bn:getBankNameForPrintCheck()"/>
            <xsl:variable name="imagesPath" select="concat($resourceRoot, '/images/')"/>
            <xsl:choose>
                <xsl:when test="state='DELAYED_DISPATCH'">
                    <img src="{concat($imagesPath, 'stampDelayed_blue.gif')}" alt=""/>
                </xsl:when>
                <xsl:when test="state='DISPATCHED' or state='ERROR' or state ='UNKNOW'">
                    <img src="{concat($imagesPath, 'stampDispatched_blue.gif')}" alt=""/>
                </xsl:when>
                <xsl:when test="state='EXECUTED'">
                    <img src="{concat($imagesPath, 'stampExecuted_blue.gif')}" alt=""/>
                </xsl:when>
                <xsl:when test="state='REFUSED'">
                    <img src="{concat($imagesPath, 'stampRefused_blue.gif')}" alt=""/>
                </xsl:when>
            </xsl:choose>
        </div>
        <br/>
    </xsl:template>

</xsl:stylesheet>