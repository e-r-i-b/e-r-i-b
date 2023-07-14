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
        <xsl:variable name="fromResourceType" select="fromResourceType"/>
        <xsl:variable name="toResourceType" select="toResourceType"/>
        <xsl:choose>
            <xsl:when test="contains($fromResourceType, 'Card') and contains($toResourceType, 'Card')">
                <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">перевод с карты на карту</xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="contains($fromResourceType, 'Card') and contains($toResourceType, 'Account')">
                <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">перевод со счета карты на счет вклада</xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="contains($fromResourceType, 'Account') and contains($toResourceType, 'Card')">
                <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">перевод со счета вклада на счет карты</xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="contains($fromResourceType, 'Account') and contains($toResourceType, 'Account')">
                <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">Перевод со счета вклада на счет вклада</xsl:with-param>
                </xsl:call-template>
            </xsl:when>
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
            <xsl:with-param name="value">Отправитель:</xsl:with-param>
        </xsl:call-template>


        <xsl:if test="contains($fromResourceType, 'Account')">
               <xsl:call-template name="printText">
                    <xsl:with-param name="value"><xsl:value-of  select="fromAccountType"/>,</xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="printText">
                    <xsl:with-param name="value"><xsl:value-of  select="fromAccountSelect"/></xsl:with-param>
                </xsl:call-template>
        </xsl:if>
        <xsl:if test="contains($fromResourceType, 'Card')">
                <xsl:variable name="fromAccountSelect"><xsl:value-of  select="fromAccountSelect"/></xsl:variable>
                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">№ карты</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="mask:getCutCardNumberPrint($fromAccountSelect)"/></xsl:with-param>
                </xsl:call-template>
        </xsl:if>

        <br/>

        <xsl:call-template name="printText">
            <xsl:with-param name="value">Получатель:</xsl:with-param>
        </xsl:call-template>


        <xsl:if test="contains($toResourceType, 'Account')">
               <xsl:call-template name="printText">
                    <xsl:with-param name="value"><xsl:value-of  select="toAccountType"/>,</xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="printText">
                    <xsl:with-param name="value"><xsl:value-of  select="toAccountSelect"/></xsl:with-param>
                </xsl:call-template>
        </xsl:if>
        <xsl:if test="contains($toResourceType, 'Card')">
                <xsl:variable name="toAccountSelect"><xsl:value-of  select="toAccountSelect"/></xsl:variable>
                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">№ карты</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="mask:getCutCardNumberPrint($toAccountSelect)"/></xsl:with-param>
                </xsl:call-template>
        </xsl:if>
        <br/>

       <xsl:choose>
            <xsl:when test="sellAmount != '' and sellAmount != '0.00'">
                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">сумма списания</xsl:with-param>
                    <xsl:with-param name="value">
                        <xsl:value-of  select="sellAmount"/>&nbsp;<xsl:value-of select="sellAmountCurrency"/></xsl:with-param>
                    </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">сумма зачисления</xsl:with-param>
                    <xsl:with-param name="value">
                        <xsl:value-of  select="buyAmount"/>&nbsp;<xsl:value-of select="buyAmountCurrency"/></xsl:with-param>
                    </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>


        <!--<xsl:call-template name="paramsCheck">-->
            <!--<xsl:with-param name="title">сумма</xsl:with-param>-->
            <!--<xsl:with-param name="value"><xsl:value-of  select="amount"/>&nbsp;<xsl:value-of select="amountCurrency"/></xsl:with-param>-->
        <!--</xsl:call-template>-->
        <xsl:choose>
            <xsl:when test="$postConfirmCommission">
                <xsl:call-template name="writeDownOperationsCheck">
                    <xsl:with-param name="fromResourceCurrency" select="sellAmountCurrency"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:if test="commission!=''">
                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">комиссия сб рф</xsl:with-param>
                        <xsl:with-param name="value"><xsl:value-of  select="commission"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:otherwise>
        </xsl:choose>

        <br/>
        <xsl:if test="authorizeCode!=''">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">Код авторизации</xsl:with-param>
                <xsl:with-param name="value"><xsl:value-of  select="authorizeCode"/></xsl:with-param>
            </xsl:call-template>
            <br/>
        </xsl:if>
    </xsl:template>

    <xsl:template name="printTitle">
        <xsl:param name="value"/>
        <div class="checkSize title"><xsl:copy-of select="$value"/></div>
        <br/>
    </xsl:template>

    <xsl:template name="printTitleAdditional">
        <xsl:param name="value"/>
        <div class="checkSize titleAdditional"><xsl:copy-of select="$value"/></div>
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