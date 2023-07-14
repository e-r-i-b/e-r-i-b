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

			<extra-parameters>
				<parameter name="recalled-document-number"
						   value="{recalledDocumentNumber}"
						   type="string"
						   />
				<parameter name="recalled-document-date"
						   value="{recalledDocumentDate}"
						   type="string"
						   />
				<parameter name="receiver-sur-name"
						   value="{receiverSurName}"
						   type="string"
						   />
				<parameter name="receiver-first-name"
						   value="{receiverFirstName}"
						   type="string"
						   />
				<parameter name="receiver-patr-name"
						   value="{receiverPatrName}"
						   type="string"
						   />
				<parameter name="receiver-bank-code"
						   value="{receiverBankCode}"
						   type="string"
						   />
				<parameter name="receiver-bank-name"
						   value="{receiverBankName}"
						   type="string"
						   />
				<parameter name="receiver-bank-phone"
						   value="{receiverBankPhone}"
						   type="string"
						   />
				<parameter name="add-information"
						   value="{addInformation}"
						   type="string"
						   />
				<parameter name="parent-id"
						   value="{parentId}"
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