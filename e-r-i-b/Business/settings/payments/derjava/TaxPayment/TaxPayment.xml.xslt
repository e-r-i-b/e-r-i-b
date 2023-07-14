<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<document-date>
				<xsl:value-of select="documentDate"/>
			</document-date>

			<payer-name>
				<xsl:value-of select="payerName"/>
			</payer-name>

			<payer-account>
				<xsl:value-of select="payerAccountSelect"/>
			</payer-account>

			<amount>
				<xsl:value-of select="amount"/>
			</amount>

			<amount-currency>
				<xsl:value-of select="currency"/>
			</amount-currency>

			<receiver-name>
				<xsl:value-of select="receiverName"/>
			</receiver-name>

			<receiver-account>
				<xsl:value-of select="receiverAccount"/>
			</receiver-account>

			<ground>
				<xsl:value-of select="ground"/>
			</ground>

			<extra-parameters>
				<xsl:variable name="payerAccountId" select="payerAccountSelect"/>
				<xsl:variable name="payerAccount" select="document('all-accounts.xml')/entity-list/entity[@key=$payerAccountId]"/>
				<xsl:variable name="payerAccountType" select="$payerAccount/field[@name='type']/text()"/>
				<parameter name="receiver-inn" value="{receiverINN}" type="string" />
				<parameter name="receiver-kpp" value="{receiverKPP}" type="string" />
				<parameter name="receiver-bic" value="{receiverBIC}" type="string" />
				<parameter name="receiver-cor-account" value="{receiverCorAccount}" type="string" />
				<parameter name="currency" value="{currency}" type="string" />
				<parameter name="receiver-bank" value="{receiverBank}" type="string"/>
				<parameter name="tax-status" value="{taxStatus}" type="string" />
				<parameter name="tax-kbk" value="{taxKBK}" type="string"/>
				<parameter name="tax-okato" value="{taxOKATO}" type="string"/>
				<parameter name="tax-ground" value="{taxGround}" type="string"/>
				<parameter name="tax-type" value="{taxType}" type="string"/>
				<parameter name="tax-document-date" value="{taxDocumentDate}" type="string"/>
				<parameter name="tax-document-number" value="{taxDocumentNumber}" type="string"/>
				<parameter name="tax-period1" value="{taxPeriod1}" type="string"/>
				<parameter name="tax-period2" value="{taxPeriod2}" type="string"/>
				<parameter name="tax-period3" value="{taxPeriod3}" type="string"/>
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
