<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<payment>
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

			<ground>
				<xsl:value-of select="ground"/>
			</ground>

			<extra-parameters>

				<parameter name="amount-currency"
						   value="{amountCurrency}"
						   type="string"
						   />
				<parameter name="receiver-country-code"
						   value="{receiverCountryCode}"
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
				<parameter name="receiver-bank-SWIFT"
						   value="{receiverBankSWIFT}"
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
				<parameter name="ground-add"
						   value=", настоящий платеж не относится к предпринимательской деятельности"
						   type="string"
						   />
				<parameter name="payment-conditions"
						   value="{paymentConditions}"
						   type="string"
						   />
				<parameter name="receiver-city"
						   value="{receiverCity}"
						   type="string"
						   />
				<parameter name="from-account-type"
                           value="{fromAccountType}"
                           type="string"
                           />
				<parameter name="from-account-amount"
                           value="{fromAccountAmount}"
                           type="string"
                           />
			</extra-parameters>
		</payment>
	</xsl:template>
</xsl:stylesheet>

<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="form&#x2D;data &#x2D;> xml" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
