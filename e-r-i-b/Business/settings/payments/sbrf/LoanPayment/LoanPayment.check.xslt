<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask = "java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au   = "java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu   = "java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:ai   = "java://com.rssl.phizic.common.types.ApplicationInfo"
                extension-element-prefixes="mask au mu ai">

	<xsl:import href="commonFieldTypes.atm.template.xslt"/>
	<xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>

	<xsl:variable name="extendedFields" select="document('extendedFields.xml')/entity-list/*"/>
	<xsl:variable name="currentPerson"  select="document('currentPerson.xml')/entity-list"/>
	<xsl:variable name="formData"       select="/form-data"/>

	<xsl:param name="documentStatus"/>
	<xsl:param name="changedFields"/>
	<xsl:param name="webRoot"           select="'webRoot'"/>
	<xsl:param name="resourceRoot"      select="'resourceRoot'"/>
	<xsl:param name="mode"              select="'edit'"/>

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
			<form>LoanPayment</form>
			<status><xsl:value-of select="$documentStatus"/></status>
			<title>Погашение кредита</title>

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
						<xsl:with-param name="visible"  select="true()"/>
						<xsl:with-param name="changed"  select="false()"/>
						<xsl:with-param name="value"    select="billingDocumentNumber"/>
					</xsl:call-template>
				</billingDocumentNumber>
			</xsl:if>

			<LoanPaymentDocumentCheck>

				<fromAccountSelect>
					<xsl:call-template name="resourceCheckField">
						<xsl:with-param name="name">fromAccountSelect</xsl:with-param>
						<xsl:with-param name="title">карта списания</xsl:with-param>
						<xsl:with-param name="required"       select="true()"/>
						<xsl:with-param name="editable"       select="false()"/>
						<xsl:with-param name="visible"        select="true()"/>
						<xsl:with-param name="changed"        select="false()"/>
						<xsl:with-param name="value"          select="fromAccountSelect"/>
						<xsl:with-param name="displayedValue" select="mask:getCutCardNumberPrint(fromAccountSelect)"/>
					</xsl:call-template>
				</fromAccountSelect>

				<xsl:if test="string-length(agreementNumber) > 0">
				<agreementNumber>
					<xsl:call-template name="stringField">
						<xsl:with-param name="name">agreementNumber</xsl:with-param>
						<xsl:with-param name="title">номер договора</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="false()"/>
						<xsl:with-param name="visible"  select="true()"/>
						<xsl:with-param name="changed"  select="false()"/>
						<xsl:with-param name="value"    select="agreementNumber"/>
					</xsl:call-template>
				</agreementNumber>
				</xsl:if>

				<xsl:if test="string-length(loanAccountNumber) > 0">
				<loanAccountNumber>
					<xsl:call-template name="stringField">
						<xsl:with-param name="name">loanAccountNumber</xsl:with-param>
						<xsl:with-param name="title">номер ссудного счета</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="false()"/>
						<xsl:with-param name="visible"  select="true()"/>
						<xsl:with-param name="changed"  select="false()"/>
						<xsl:with-param name="value"    select="loanAccountNumber"/>
					</xsl:call-template>
				</loanAccountNumber>
				</xsl:if>

				<xsl:if test="string-length(office) > 0">
				<office>
					<xsl:call-template name="stringField">
						<xsl:with-param name="name">office</xsl:with-param>
						<xsl:with-param name="title">номер отделения</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="false()"/>
						<xsl:with-param name="visible"  select="true()"/>
						<xsl:with-param name="changed"  select="false()"/>
						<xsl:with-param name="value"    select="office"/>
					</xsl:call-template>
				</office>
				</xsl:if>

				<xsl:if test="string-length(otherCostsAmount) > 0">
				<otherCostsAmount>
					<xsl:call-template name="moneyField">
						<xsl:with-param name="name">otherCostsAmount</xsl:with-param>
						<xsl:with-param name="title">расходы по взиманию задолженности</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="false()"/>
						<xsl:with-param name="visible"  select="true()"/>
						<xsl:with-param name="changed"  select="false()"/>
						<xsl:with-param name="value"    select="otherCostsAmount"/>
					</xsl:call-template>
				</otherCostsAmount>
				</xsl:if>

				<xsl:if test="string-length(penaltyDelayDebtAmount) > 0">
				<penaltyDelayDebtAmount>
					<xsl:call-template name="moneyField">
						<xsl:with-param name="name">penaltyDelayDebtAmount</xsl:with-param>
						<xsl:with-param name="title">неустойка за просроч. осн. долга</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="false()"/>
						<xsl:with-param name="visible"  select="true()"/>
						<xsl:with-param name="changed"  select="false()"/>
						<xsl:with-param name="value"    select="penaltyDelayDebtAmount"/>
					</xsl:call-template>
				</penaltyDelayDebtAmount>
				</xsl:if>

				<xsl:if test="string-length(penaltyDelayPercentAmount) > 0">
				<penaltyDelayPercentAmount>
					<xsl:call-template name="moneyField">
						<xsl:with-param name="name">penaltyDelayPercentAmount</xsl:with-param>
						<xsl:with-param name="title">неустойка за просроч. проценты</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="false()"/>
						<xsl:with-param name="visible"  select="true()"/>
						<xsl:with-param name="changed"  select="false()"/>
						<xsl:with-param name="value"    select="penaltyDelayPercentAmount"/>
					</xsl:call-template>
				</penaltyDelayPercentAmount>
				</xsl:if>

				<xsl:if test="string-length(penaltyUntimelyInsurance) > 0">
				<penaltyUntimelyInsurance>
					<xsl:call-template name="moneyField">
						<xsl:with-param name="name">penaltyUntimelyInsurance</xsl:with-param>
						<xsl:with-param name="title">неустойка за страхование</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="false()"/>
						<xsl:with-param name="visible"  select="true()"/>
						<xsl:with-param name="changed"  select="false()"/>
						<xsl:with-param name="value"    select="penaltyUntimelyInsurance"/>
					</xsl:call-template>
				</penaltyUntimelyInsurance>
				</xsl:if>

				<xsl:if test="string-length(delayedPercentsAmount) > 0">
				<delayedPercentsAmount>
					<xsl:call-template name="moneyField">
						<xsl:with-param name="name">delayedPercentsAmount</xsl:with-param>
						<xsl:with-param name="title">просроч. проценты</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="false()"/>
						<xsl:with-param name="visible"  select="true()"/>
						<xsl:with-param name="changed"  select="false()"/>
						<xsl:with-param name="value"    select="delayedPercentsAmount"/>
					</xsl:call-template>
				</delayedPercentsAmount>
				</xsl:if>

				<interestsAmount>
					<xsl:call-template name="moneyField">
						<xsl:with-param name="name">interestsAmount</xsl:with-param>
						<xsl:with-param name="title">срочные проценты</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="false()"/>
						<xsl:with-param name="visible"  select="true()"/>
						<xsl:with-param name="changed"  select="false()"/>
						<xsl:with-param name="value"    select="interests-amount"/>
					</xsl:call-template>
				</interestsAmount>

				<xsl:if test="string-length(delayedDebtAmount) > 0">
				<delayedDebtAmount>
					<xsl:call-template name="moneyField">
						<xsl:with-param name="name">delayedDebtAmount</xsl:with-param>
						<xsl:with-param name="title">просроч.основной долг</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="false()"/>
						<xsl:with-param name="visible"  select="true()"/>
						<xsl:with-param name="changed"  select="false()"/>
						<xsl:with-param name="value"    select="delayedDebtAmount"/>
					</xsl:call-template>
				</delayedDebtAmount>
				</xsl:if>

				<principalAmount>
					<xsl:call-template name="moneyField">
						<xsl:with-param name="name">principalAmount</xsl:with-param>
						<xsl:with-param name="title">осн. долг</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="false()"/>
						<xsl:with-param name="visible"  select="true()"/>
						<xsl:with-param name="changed"  select="false()"/>
						<xsl:with-param name="value"    select="principal-amount"/>
					</xsl:call-template>
				</principalAmount>

				<xsl:if test="string-length(amount) > 0">
				<amount>
					<xsl:call-template name="moneyField">
						<xsl:with-param name="name">amount</xsl:with-param>
						<xsl:with-param name="title">сумма платежа</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="false()"/>
						<xsl:with-param name="visible"  select="true()"/>
						<xsl:with-param name="changed"  select="false()"/>
						<xsl:with-param name="value"    select="amount"/>
					</xsl:call-template>
				</amount>
				</xsl:if>
			</LoanPaymentDocumentCheck>
		</DocumentCheck>
	</xsl:template>
</xsl:stylesheet>