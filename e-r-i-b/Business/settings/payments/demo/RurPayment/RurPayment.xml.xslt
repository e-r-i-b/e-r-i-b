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

			<amount>
				<xsl:value-of select="amount"/>
			</amount>

			<amount-currency>
				<xsl:value-of select="amountCurrency"/>
			</amount-currency>

			<receiver-account>
				<xsl:value-of select="receiverAccount"/>
			</receiver-account>

			<receiver-name>
				<xsl:value-of select="receiverName"/>
			</receiver-name>

			<ground>
				<xsl:value-of select="ground"/>
			</ground>

			<extra-parameters>
				<parameter name="receiver-bank" value="{receiverBank}" type="string"/>
				<parameter name="receiver-type" value="{receiverType}" type="string"/>
				<parameter name="receiver-inn" value="{receiverINN}" type="string"/>
				<parameter name="receiver-kpp" value="{receiverKPP}" type="string"/>
				<parameter name="receiver-bic" value="{receiverBIC}" type="string"/>
				<parameter name="receiver-cor-account" value="{receiverCorAccount}" type="string"/>
				<xsl:variable name="payerAccountId" select="payerAccountSelect"/>
				<xsl:variable name="payerAccount" select="document('all-accounts.xml')/entity-list/entity[@key=$payerAccountId]"/>
				<xsl:variable name="payerAccountType" select="$payerAccount/field[@name='type']/text()"/>
				<xsl:variable name="payerAccountCurrency" select="$payerAccount/field[@name='currencyCode']/text()"/>

				<parameter name="receiver-bank"
						   value="{receiverBank}"
						   type="string"
						   />
				<parameter name="receiver-type"
						   value="{receiverType}"
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
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>

<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="form&#x2D;data &#x2D;> xml" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
