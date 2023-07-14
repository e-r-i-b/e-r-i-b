<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<document-date>
				<xsl:value-of select="documentDate"/>
			</document-date>

			<payer-account>
				<xsl:value-of select="payerAccountSelect"/>
			</payer-account>

			<receiver-account>
				<xsl:value-of select="receiverAccountSelect"/>
			</receiver-account>

			<receiver-name>
				<xsl:value-of select="receiverName"/>
			</receiver-name>

			<amount>
				<xsl:value-of select="amount"/>
			</amount>

			<amount-currency>
				<xsl:value-of select="currency"/>
			</amount-currency>

			<ground>
				<xsl:value-of select="ground"/>
			</ground>
			<extra-parameters>
				<parameter name="receiver-bank-corr-account" value="{receiverBankСorrAccount}" type="string"/>
				<xsl:variable name="payerAccountId" select="payerAccountSelect"/>
				<xsl:variable name="payerAccount" select="document('all-accounts.xml')/entity-list/entity[@key=$payerAccountId]"/>
				<xsl:variable name="payerAccountType" select="$payerAccount/field[@name='type']/text()"/>
				<xsl:variable name="payerAccountCurrency" select="$payerAccount/field[@name='currencyCode']/text()"/>
				<xsl:variable name="commissionAccountId" select="commissionAccount"/>
				<xsl:variable name="commissionAcc" select="document('all-accounts.xml')/entity-list/entity[@key=$commissionAccountId]"/>
				<xsl:variable name="commissionAccountType" select="$commissionAcc/field[@name='type']/text()"/>
				<xsl:variable name="commissionAccountCurrency" select="$commissionAcc/field[@name='currencyCode']/text()"/>
                <parameter name="payer-name"
						   value="{payerName}"
						   type="string"
						   />
                <parameter name="payer-address"
						   value="{payerAddress}"
						   type="string"
						   />
				<parameter name="amount-currency"
						   value="{currency}"
						   type="string"
						   />
				<parameter name="receiver-country-code"
						   value="{receiverCountryCode}"
						   type="string"
						   />
				<parameter name="receiver-city"
						   value="{receiverCity}"
						   type="string"
						   />
				<parameter name="receiver-address"
						   value="{receiverAddress}"
						   type="string"
						   />
				<parameter name="receiver-bank-name"
						   value="{receiverBankName}"
						   type="string"
						   />
				<parameter name="receiver-bank-country-code"
						   value="{receiverBankCountryCode}"
						   type="string"
						   />
				<parameter name="receiver-bank-city"
						   value="{receiverBankCity}"
						   type="string"
						   />
				<parameter name="receiver-bank-address"
						   value="{receiverBankAddress}"
						   type="string"
						   />
				<parameter name="intermediary-bank-name"
						   value="{intermediaryBankName}"
						   type="string"
						   />
				<parameter name="intermediary-bank-country-code"
						   value="{intermediaryBankCountryCode}"
						   type="string"
						   />
				<parameter name="intermediary-bank-city"
						   value="{intermediaryBankCity}"
						   type="string"
						   />
				<parameter name="intermediary-bank-address"
						   value="{intermediaryBankAddress}"
						   type="string"
						   />
				<parameter name="payment-conditions"
						   value="{paymentConditions}"
						   type="string"
						   />
				<parameter name="commission"
						   value="{commission}"
						   type="string"
						   />
				<parameter name="commission-account"
						   value="{commissionAccount}"
						   type="string"
						   />
				<parameter name="ground-add"
						   value=", настоящий платеж не относится к предпринимательской деятельности"
						   type="string"
						   />
				<parameter name="payer-account-type"
						   value="{$payerAccountType}"
						   type="string"
						   />
				<parameter name="payer-account-currency"
						   value="{$payerAccountCurrency}"
						   type="string"
						   />
				<parameter name="commission-account-type"
						   value="{$commissionAccountType}"
						   type="string"
						   />
				<parameter name="commission-account-currency"
						   value="{$commissionAccountCurrency}"
						   type="string"
						   />

			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>

<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="form&#x2D;data &#x2D;> xml" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
