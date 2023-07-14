<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:cu="java://com.rssl.phizic.utils.CurrencyUtils"
                xmlns:imau="java://com.rssl.phizic.business.ima.IMAccountUtils"
                extension-element-prefixes="mask au mu cu imau">
    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:variable name="extendedFields" select="document('extendedFields.xml')/entity-list/*"/>
    <xsl:variable name="formData" select="/form-data"/>
    <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
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

         <DocumentCheck>
            <form>IMAPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>

            <xsl:variable name="isBuy" select="contains(toResourceType, 'IMA')"/>
            <xsl:variable name="titleText">
                <xsl:choose>
                    <xsl:when test="$isBuy">
                        <xsl:text>Покупка обезличенного металла</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>Продажа обезличенного металла</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:variable>

            <title><xsl:value-of select="$titleText"/></title>
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
             <xsl:if test="string-length(billingDocumentNumber)>0">
				<billingDocumentNumber>
					<xsl:call-template name="integerField">
						<xsl:with-param name="name">billingDocumentNumber</xsl:with-param>
						<xsl:with-param name="title">номер операции</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="false()"/>
						<xsl:with-param name="visible" select="true()"/>
						<xsl:with-param name="changed" select="false()"/>
						<xsl:with-param name="value" select="billingDocumentNumber"/>
					</xsl:call-template>
				</billingDocumentNumber>
			</xsl:if>

            <IMAPaymentDocumentCheck>

                <xsl:variable name="fromAccountSelect"><xsl:value-of select="fromAccountSelect"/></xsl:variable>
                <xsl:variable name="fromResourceCode">
                    <xsl:choose>
                        <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                             Карта №
                        </xsl:when>
                        <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                             <xsl:value-of select="fromAccountType"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="fromAccountName"/>(<xsl:value-of select="cu:normalizeCurrencyCode(fromResourceCurrency)"/>)
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <xsl:variable name="displayedValue">
                    <xsl:choose>
                        <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                            <xsl:value-of select="mask:getCutCardNumberPrint($fromAccountSelect)"/>
                        </xsl:when>
                        <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                           <xsl:value-of select="au:getFormattedAccountNumber($fromAccountSelect)"/>
                        </xsl:when>
                        <xsl:otherwise>
                           <xsl:value-of select="imau:getFormattedIMAccountNumber($fromAccountSelect)"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <fromResource>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Отправитель</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="concat($fromResourceCode, ': ', $displayedValue)"/>
                    </xsl:call-template>
                </fromResource>

                <xsl:variable name="toAccountSelect"><xsl:value-of select="toAccountSelect"/></xsl:variable>
                <xsl:variable name="toResourceCode">
                    <xsl:choose>
                        <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                             Карта №
                        </xsl:when>
                        <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                             <xsl:value-of select="toAccountType"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="toAccountName"/>(<xsl:value-of select="cu:normalizeCurrencyCode(toResourceCurrency)"/>)
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <xsl:variable name="toDisplayedValue">
                    <xsl:choose>
                        <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                            <xsl:value-of select="mask:getCutCardNumberPrint($toAccountSelect)"/>
                        </xsl:when>
                        <xsl:when test="toResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                           <xsl:value-of select="au:getFormattedAccountNumber($toAccountSelect)"/>
                        </xsl:when>
                        <xsl:otherwise>
                           <xsl:value-of select="imau:getFormattedIMAccountNumber($toAccountSelect)"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <receiver>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">receiver</xsl:with-param>
                        <xsl:with-param name="title">Получатель</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="concat($toResourceCode, ': ', $toDisplayedValue)"/>
                    </xsl:call-template>
                </receiver>

                <xsl:variable name="amountString">
                    <xsl:choose>
                        <xsl:when test="$isBuy">
                            <xsl:value-of select="concat(sellAmount, ' ', mu:getCurrencySign(sellCurrency))"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="concat(buyAmount, ' ', mu:getCurrencySign(buyCurrency))"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <amount>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">amount</xsl:with-param>
                        <xsl:with-param name="title">сумма</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="$amountString"/>
                    </xsl:call-template>
                </amount>

                <xsl:variable name="buyIMAString">
                     <xsl:choose>
                        <xsl:when test="$isBuy">
                            <xsl:value-of select="concat(format-number(buyAmount, '### ##0.0'), ' ', cu:normalizeCurrencyCode(buyCurrency))"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="concat(format-number(sellAmount, '### ##0.0'), ' ', cu:normalizeCurrencyCode(sellCurrency))"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <buyIMA>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">buyIMA</xsl:with-param>
                        <xsl:with-param name="title">Количество ОМ</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="$buyIMAString"/>
                    </xsl:call-template>
                </buyIMA>

            </IMAPaymentDocumentCheck>

        </DocumentCheck>
    </xsl:template>

</xsl:stylesheet>
