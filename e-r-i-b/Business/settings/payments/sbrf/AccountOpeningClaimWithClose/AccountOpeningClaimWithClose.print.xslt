<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
<!ENTITY laquo "&#171;">
<!ENTITY raquo "&#187;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                extension-element-prefixes="phizic">
    <xsl:import href="commonFieldTypes.template.xslt"/>
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="postConfirmCommission" select="postConfirmCommission"/>
    <xsl:variable name="extendedFields" select="document('extendedFields.xml')/entity-list/*"/>
    <xsl:variable name="formData" select="/form-data"/>
    <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'printCheck'">
				<xsl:apply-templates mode="printCheck"/>
			</xsl:when>
            <xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="printCheck">

        <xsl:variable name="title">
            <xsl:choose>
                <xsl:when test="needInitialFee = 'true'">
                    <xsl:text>открытие вклада путем перевода средств</xsl:text>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:text>открытие счета</xsl:text>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:call-template name="printTitle">
            <xsl:with-param name="value" select="$title"/>
        </xsl:call-template>

        <xsl:variable name="fromResourceType" select="fromResourceType"/>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">дата операции</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="documentDate"/></xsl:with-param>
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

        <xsl:if test="needInitialFee = 'true'">
            <xsl:call-template name="printText">
                <xsl:with-param name="value">отправитель:</xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="printText">
                <xsl:with-param name="value">
                    <xsl:choose>
                        <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                            <xsl:value-of select="mask:getCutCardNumberPrint(fromAccountSelect)"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/>
                            &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:with-param>
            </xsl:call-template>
            <br/>
        </xsl:if>

        <xsl:call-template name="printText">
            <xsl:with-param name="value">получатель:</xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="printText">
            <xsl:with-param name="value"><xsl:value-of  select="depositName"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="printText">
            <xsl:with-param name="value"><xsl:value-of  select="au:getFormattedAccountNumber(receiverAccountSelect)"/></xsl:with-param>
        </xsl:call-template>
        <br/>

        <xsl:if test="needInitialFee = 'true'">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">сумма</xsl:with-param>
                <xsl:with-param name="value"><xsl:value-of  select="buyAmount"/>&nbsp;<xsl:value-of  select="toResourceCurrency"/></xsl:with-param>
            </xsl:call-template>
            <br/>
        </xsl:if>

        <xsl:if test="$postConfirmCommission">
            <xsl:call-template name="writeDownOperationsCheck">
                <xsl:with-param name="fromResourceCurrency" select="fromResourceCurrency"/>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="authorizeCode!=''">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">Код авторизации</xsl:with-param>
                <xsl:with-param name="value"><xsl:value-of  select="authorizeCode"/></xsl:with-param>
            </xsl:call-template>                                         4
            <br/>
        </xsl:if>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Статус операции</xsl:with-param>
            <xsl:with-param name="value">
                <xsl:call-template name="state2text">
                        <xsl:with-param name="code">
                            <xsl:value-of select="state"/>
                        </xsl:with-param>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>
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

    <xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Введен</xsl:when>
			<xsl:when test="$code='INITIAL'">Введен</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидание обработки</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Обрабатывается</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='RECALLED'">Отозван</xsl:when>
			<xsl:when test="$code='REFUSED'">Отказан</xsl:when>
			<xsl:when test="$code='ERROR'">Обрабатывается</xsl:when>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>
