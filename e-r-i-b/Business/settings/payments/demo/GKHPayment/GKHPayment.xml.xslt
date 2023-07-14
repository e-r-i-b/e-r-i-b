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

			<payer-name>
				<xsl:value-of select="payerName"/>
			</payer-name>

			<amount>
				<xsl:value-of select="amount"/>
			</amount>

			<amount-currency>
				<xsl:value-of select="currency"/>
			</amount-currency>

			<extra-parameters>
				<xsl:variable name="accountId" select="payerAccountSelect"/>
				<xsl:variable name="payerAccounts" select="document('rur-accounts.xml')/entity-list/entity[@key=$accountId]"/>
				<xsl:variable name="payerAccountType" select="$payerAccounts/field[@name='type']/text()"/>
				<parameter name="payer-address" value="{payerAddress}" type="string"/>
				<parameter name="payer-code" value="{payerCode}" type="string"/>
				<parameter name="year" value="{yearSelect}" type="string"/>
				<parameter name="month" value="{monthSelect}" type="string"/>
				<parameter name="insurance-amount" value="{insuranceAmount}" type="string"/>
				<parameter name="payer-account-type"
						   value="{$payerAccountType}"
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
