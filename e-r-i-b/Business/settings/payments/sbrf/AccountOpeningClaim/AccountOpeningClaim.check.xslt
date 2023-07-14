<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:ai="java://com.rssl.phizic.common.types.ApplicationInfo"
                extension-element-prefixes="mask au mu ai">
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
         <xsl:variable name="application"><xsl:value-of select="ai:getCurrentApplication()"/></xsl:variable>
         <DocumentCheck>
            <form>AccountOpeningClaim</form>
            <status><xsl:value-of select="$documentStatus"/></status>

            <xsl:variable name="titleText">
                <xsl:choose>
                    <xsl:when test="needInitialFee = 'true'">
                        <xsl:text>открытие вклада путем перевода средств</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>открытие счета</xsl:text>
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

            <AccountOpeningClaimDocumentCheck>

                <xsl:if test="needInitialFee = 'true'">
                    <xsl:variable name="fromAccountSelect"><xsl:value-of  select="fromAccountSelect"/></xsl:variable>
                    <xsl:variable name="fromResourceCode">
                        <xsl:choose>
                            <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                 Карта №
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="fromAccountName"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>
                    <xsl:variable name="displayedValue">
                        <xsl:choose>
                            <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                <xsl:value-of select="mask:getCutCardNumberPrint($fromAccountSelect)"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="au:getFormattedAccountNumber($fromAccountSelect)"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>

                    <fromResource>
                        <xsl:call-template name="resourceCheckField">
                            <xsl:with-param name="name">fromResource</xsl:with-param>
                            <xsl:with-param name="title">Отправитель</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="fromResourceLink"/>
                            <xsl:with-param name="displayedValue" select="concat($fromResourceCode, ': ', $displayedValue)"/>
                        </xsl:call-template>
                    </fromResource>
                </xsl:if>

                <receiver>
                    <xsl:call-template name="resourceCheckField">
                        <xsl:with-param name="name">receiver</xsl:with-param>
                        <xsl:with-param name="title">Получатель</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="'account:0'"/>
                        <xsl:with-param name="displayedValue" select="concat(depositName, ': ', au:getFormattedAccountNumber(receiverAccountSelect))"/>
                    </xsl:call-template>
                </receiver>

                <xsl:if test="needInitialFee = 'true'">
                    <xsl:variable name="amountString" select="concat(buyAmount, ' ', mu:getCurrencySign(toResourceCurrency))"/>
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
                </xsl:if>

                <xsl:if test="string-length(authCode)>0">
                    <authCode>
                        <xsl:call-template name="stringField">
                        <xsl:with-param name="name">authCode</xsl:with-param>
                        <xsl:with-param name="title">Код авторизации</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="authCode"/>
                    </xsl:call-template>
                    </authCode>
                </xsl:if>

            </AccountOpeningClaimDocumentCheck>

        </DocumentCheck>
    </xsl:template>

</xsl:stylesheet>
