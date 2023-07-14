<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                xmlns:cu="java://com.rssl.phizic.utils.CurrencyUtils"
                xmlns:imau="java://com.rssl.phizic.business.ima.IMAccountUtils"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:bn="java://com.rssl.phizic.business.util.BankInfoUtil"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                extension-element-prefixes="phizic">
    <xsl:import href="commonFieldTypes.template.xslt"/>
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="mode" select="'view'"/>
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

        <xsl:call-template name="printTitle">
             <xsl:with-param name="value">ОТКРЫТИЕ ОБЕЗЛИЧЕННОГО МЕТАЛЛИЧЕСКОГО СЧЕТА</xsl:with-param>
         </xsl:call-template>

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

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Получатель</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="buyIMAProduct"/><br/><xsl:value-of select="mask:cutStringByLastSevenSymbols(toAccountSelect)"/></xsl:with-param>
        </xsl:call-template>
        <br/>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Сумма</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="sellAmount"/>&nbsp;<xsl:value-of select="sellCurrency"/></xsl:with-param>
        </xsl:call-template>

        <xsl:if test="$postConfirmCommission">
            <xsl:call-template name="writeDownOperationsCheck">
                <xsl:with-param name="fromResourceCurrency" select="sellCurrency"/>
            </xsl:call-template>
        </xsl:if>
        
       <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Количество ОМ</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="format-number(buyAmount, '### ##0.0')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(buyCurrency)"/>&nbsp;<xsl:value-of select="cu:normalizeCurrencyCode(buyCurrency)"/></xsl:with-param>
        </xsl:call-template>

        <xsl:if test="authorizeCode!=''">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">Код авторизации</xsl:with-param>
                <xsl:with-param name="value"><xsl:value-of  select="authorizeCode"/></xsl:with-param>
            </xsl:call-template>

        </xsl:if>
        <br/>
        <xsl:call-template name="paramsCheck">
           <xsl:with-param name="title">Статус</xsl:with-param>
           <xsl:with-param name="value">
                <xsl:call-template name="state2text">
                    <xsl:with-param name="code">
                        <xsl:value-of select="state"/>
                    </xsl:with-param>
                </xsl:call-template>
           </xsl:with-param>
        </xsl:call-template>

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
            <xsl:when test="$code='UNKNOW'">Обрабатывается</xsl:when>
		</xsl:choose>
	</xsl:template>
    
</xsl:stylesheet>